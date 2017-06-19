package csdc.action.project.general;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralApplicationReview;
import csdc.bean.GeneralEndinspectionReview;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectEndinspection;
import csdc.service.IGeneralService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 社科项目申请申请鉴定管理
 * @author 肖雅
 */
public class ApplicationReviewAction extends csdc.action.project.ApplicationReviewAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, app.name, uni.id, app.agencyName, app.applicantId, app.applicantName, " +
		"so.name, app.disciplineType, app.year, appRev.submitStatus, appRev.date, appRev.score, app.file, app.reviewStatus, app.reviewResult " +
		"from GeneralApplicationReview appRev, GeneralApplication app left outer join app.university uni left outer join app.subtype so " +
		"where appRev.reviewer.id=:belongId and appRev.application.id=app.id ";
	
//	private static final String HQL2 = "from GeneralEndinspectionReview appRev left outer join appRev.endinspection endi " +
//		"left outer join endi.granted gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
//		"where appRev.reviewer.id=:belongId and not exists " +
//		"(select 1 from GeneralEndinspectionReview gener where gener.endinspection.granted.id=gra.id and gener.reviewer.id=:belongId and gener.date>appRev.date)";

	private static final String PAGE_NAME = "generalAppReviewPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final String BUSINESS_TYPE = "011";//一般项目结项鉴定对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 14;//一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//一般项目立项是否在管辖范围内的标志位
	private List<GeneralApplicationReview> reviews;
	private IGeneralService generalService;
	
	
	public String pageName() {
		return ApplicationReviewAction.PAGE_NAME;
	}
	public String projectType(){
		return ApplicationReviewAction.PROJECT_TYPE;
	}
	public String businessType(){
		return ApplicationReviewAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationReviewAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationReviewAction.CHECK_GRANTED_FLAG;
	}
	public String listHql(){
		return ApplicationReviewAction.HQL;
	}
	
	
	/**
	 * 选择申请评审专家
	 * @author yangfq 
	 */
	public String addResultExpert(){
//		application = dao.query(ProjectApplication.class, entityId);
		GeneralApplication application = (GeneralApplication)this.dao.query(GeneralApplication.class, entityId);
		for(int i = 0; i < reviews.size(); i++){
			GeneralApplicationReview appRev = reviews.get(i);
			appRev.setApplication(application);
			appRev.setApplicationId(entityId);
			appRev = (GeneralApplicationReview) this.generalService.setAppReviewInfoFromAppReview(appRev);
			List<GeneralApplicationReview> gappRev = this.dao.query("from GeneralApplicationReview where applicationId = ?",entityId);
			for (GeneralApplicationReview agp : gappRev) {
				int sn = agp.getReviewerSn();
				if (sn == 1) {
					i++;
				}
			}
			appRev.setReviewerSn(i+1);
			appRev.setProjectType(PROJECT_TYPE);
			appRev.setSubmitStatus(0);
			appRev.setReviewType(1);
			this.dao.add(appRev);
		}
		
		revResultFlag = 1;
		return SUCCESS;
	}
	
	
	/**
	 * 添加录入的项目结项鉴定
	 * @author 肖雅
	 */
	@Transactional
	public String addResult(){
		if(!this.generalService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//保存各个专家的鉴定信息
		GeneralApplication application = (GeneralApplication)this.dao.query(GeneralApplication.class, entityId);
		int reviewType = this.generalService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				GeneralApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (GeneralApplicationReview) this.generalService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
//		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
//			for(int i = 0; i < reviews.size(); i++){
//				GeneralApplicationReview appRev = reviews.get(i);
//				appRev.setApplication(application);
//				appRev.setReviewType(reviewType);
//				appRev.setReviewerSn(i+1);
//				appRev.setSubmitStatus(submitStatus);
//				appRev.setDate(new Date());
//				appRev.setIsManual(1);//手动分配专家
//				double score = appRev.getInnovationScore() + appRev.getScientificScore() + appRev.getBenefitScore();
//				appRev.setScore(score);
//				appRev.setGrade(this.generalService.getReviewGrade(score));
//				this.generalService.add(appRev);
//			}
//		}
		//保存总评信息
		application = (GeneralApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对鉴定人信息进行入库处理
//			this.generalService.doWithNewReviewFromAppReview(entityId);
//   		}
		revResultFlag = 1;
//		opinionList = (List) this.generalService.getSystemOptionMapAsName("reviewOpinionQualitative", "null")};
		return SUCCESS;
	}
	
	/**
	 * 进入一般项目结项鉴定录入修改页面预处理
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.generalService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		reviews = this.generalService.getAllAppReviewByAppId(entityId);
		//教育部录入时对鉴定人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				GeneralApplicationReview review = reviews.get(i);
				review = (GeneralApplicationReview)this.generalService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		doWithToModifyResult();
		return SUCCESS;
	}
	
	/**
	 * 一般项目结项鉴定录入修改
	 * @author 肖雅
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modifyResult(){
		if(!this.generalService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有鉴定信息
		List appReviews = this.generalService.getAllAppReviewByAppId(entityId);
		for (Object appReview : appReviews) {
			dao.delete(appReview);
		}
//		this.generalService.deleteMore(appReviews);
		//保存各个专家的鉴定信息
		GeneralApplication application = (GeneralApplication)this.dao.query(GeneralApplication.class, entityId);
		int reviewType = this.generalService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				GeneralApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (GeneralApplicationReview) this.generalService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
//		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
//			for(int i = 0; i < reviews.size(); i++){
//				GeneralApplicationReview appRev = reviews.get(i);
//				appRev.setApplication(application);
//				appRev.setReviewType(reviewType);
//				appRev.setReviewerSn(i+1);
//				appRev.setSubmitStatus(submitStatus);
//				appRev.setDate(new Date());
//				appRev.setIsManual(1);//手动分配专家
//				double score = appRev.getInnovationScore() + appRev.getScientificScore() + appRev.getBenefitScore();
//				appRev.setScore(score);
//				appRev.setGrade(this.generalService.getReviewGrade(score));
//				this.generalService.add(appRev);
//			}
//		}
		//保存总评信息
		application = (GeneralApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对鉴定人信息进行入库处理
//			this.generalService.doWithNewReviewFromAppReview(entityId);
//   		}
		revResultFlag = 1;
//		opinionList = this.generalService.getSOByParentName("一般项目申请鉴定定性意见");
		return SUCCESS;
	}
	
	/**
	 * 添加录入鉴定校验
	 * @author 肖雅
	 */
	public void validateAddResult(){
		this.validateEditResult(1);
	}
	
	/**
	 * 修改录入鉴定结果校验
	 * @author 肖雅
	 */
	public void validateModifyResult(){
		this.validateEditResult(2);
	}
	
	/**
	 * 提交录入鉴定校验
	 * @author 肖雅
	 */
	public void validateSubmitResult(){
		this.validateEditResult(3);
	}
	
	/**
	 * 录入鉴定校验公用方法
	 * @param type 校验类型：1添加录入鉴定; 2修改录入鉴定; 3提交录入鉴定
	 * @author 余潜玉
	 */
	public void validateEditResult(int type){
		this.validateReviewBasic(type);
		if(type == 1 || type == 2){//添加录入鉴定或修改录入鉴定
			if(reviews == null ||reviews.size() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}else{
				for(int i=0 ;i <reviews.size();i++){
					this.validateReview(reviews.get(i));
				}
			}
			validateReviewGroup();
		}
	}
	
	public IGeneralService getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	public List<GeneralApplicationReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<GeneralApplicationReview> reviews) {
		this.reviews = reviews;
	}
}
