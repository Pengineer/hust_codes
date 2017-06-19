package csdc.action.project.instp;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.EntrustApplicationReview;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralApplicationReview;
import csdc.bean.InstpApplication;
import csdc.bean.InstpApplicationReview;
import csdc.service.IInstpService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 社科项目申请申请评审管理
 * @author 肖雅
 */
public class ApplicationReviewAction extends csdc.action.project.ApplicationReviewAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, app.name, uni.id, app.agencyName, app.applicantId, app.applicantName, " +
		"so.name, app.disciplineType, app.year, appRev.submitStatus, appRev.date, appRev.score, app.file, app.reviewStatus, app.reviewResult " +
		"from InstpApplicationReview appRev, InstpApplication app left outer join app.university uni left outer join app.subtype so " +
		"where appRev.reviewer.id=:belongId and appRev.application.id=app.id ";
	
	private static final String PAGE_NAME = "instpAppReviewPage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";
	private static final String BUSINESS_TYPE = "021";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	private List<InstpApplicationReview> reviews;
	private IInstpService instpService;
	
	
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
		InstpApplication application = (InstpApplication)this.dao.query(InstpApplication.class, entityId);
		for(int i = 0; i < reviews.size(); i++){
			InstpApplicationReview appRev = reviews.get(i);
			appRev.setApplication(application);
			appRev.setApplicationId(entityId);
			appRev = (InstpApplicationReview) this.instpService.setAppReviewInfoFromAppReview(appRev);
			List<InstpApplicationReview> gappRev = this.dao.query("from InstpApplicationReview where applicationId = ?",entityId);
			for (InstpApplicationReview agp : gappRev) {
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
		return SUCCESS;
	}
	
	
	/**
	 * 录入评审
	 * @author 肖雅
	 */
	@Transactional
	public String addResult(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//保存各个专家的评审信息
		InstpApplication application = (InstpApplication)this.dao.query(InstpApplication.class, entityId);
		int reviewType = this.instpService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				InstpApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (InstpApplicationReview) this.instpService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
//		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
//			for(int i = 0; i < reviews.size(); i++){
//				InstpApplicationReview appRev = reviews.get(i);
//				appRev.setApplication(application);
//				appRev.setReviewType(reviewType);
//				appRev.setReviewerSn(i+1);
//				appRev.setSubmitStatus(submitStatus);
//				appRev.setDate(new Date());
//				appRev.setIsManual(1);//手动分配专家
//				double score = appRev.getInnovationScore() + appRev.getScientificScore() + appRev.getBenefitScore();
//				appRev.setScore(score);
//				appRev.setGrade(this.instpService.getReviewGrade(score));
//				this.instpService.add(appRev);
//			}
//		}
		//保存总评信息
		application = (InstpApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
//			this.instpService.doWithNewReviewFromAppReview(entityId);
//   		}
		revResultFlag = 1;
//		opinionList = this.instpService.getSOByParentName("基地项目申请评审定性意见");
		return SUCCESS;
	}
	
	/**
	 *准备修改录入评审
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		reviews = this.instpService.getAllAppReviewByAppId(entityId);
		//教育部录入时对评审人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				InstpApplicationReview review = reviews.get(i);
				review = (InstpApplicationReview)this.instpService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		doWithToModifyResult();
		return SUCCESS;
	}
	
	/**
	 * 修改录入评审
	 * @author 肖雅
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modifyResult(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有评审信息
		List appReviews = this.instpService.getAllAppReviewByAppId(entityId);
		for (Object appReview : appReviews) {
			dao.delete(appReview);
		}
//		this.instpService.deleteMore(appReviews);
		//保存各个专家的评审信息
		InstpApplication application = (InstpApplication)this.dao.query(InstpApplication.class, entityId);
		int reviewType = this.instpService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				InstpApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (InstpApplicationReview) this.instpService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
//		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
//			for(int i = 0; i < reviews.size(); i++){
//				InstpApplicationReview appRev = reviews.get(i);
//				appRev.setApplication(application);
//				appRev.setReviewType(reviewType);
//				appRev.setReviewerSn(i+1);
//				appRev.setSubmitStatus(submitStatus);
//				appRev.setDate(new Date());
//				appRev.setIsManual(1);//手动分配专家
//				double score = appRev.getInnovationScore() + appRev.getScientificScore() + appRev.getBenefitScore();
//				appRev.setScore(score);
//				appRev.setGrade(this.instpService.getReviewGrade(score));
//				this.instpService.add(appRev);
//			}
//		}
		//保存总评信息
		application = (InstpApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
//		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
//			this.instpService.doWithNewReviewFromAppReview(entityId);
//   	}
		revResultFlag = 1;
//		opinionList = this.instpService.getSOByParentName("基地项目申请评审定性意见");
		return SUCCESS;
	}
	
	/**
	 * 录入评审校验
	 * @author 肖雅
	 */
	public void validateAddResult(){
		this.validateEditResult(1);
	}
	
	/**
	 * 修改录入评审结果校验
	 * @author 肖雅
	 */
	public void validateModifyResult(){
		this.validateEditResult(2);
	}
	
	/**
	 * 提交录入评审校验
	 * @author 肖雅
	 */
	public void validateSubmitResult(){
		this.validateEditResult(3);
	}
	
	/**
	 * 录入评审（鉴定）校验公用方法
	 * @param type 校验类型：1添加录入评审; 2修改录入评审; 3提交录入评审
	 * @author 余潜玉
	 */
	public void validateEditResult(int type){
		this.validateReviewBasic(type);
		if(type == 1 || type == 2){//添加录入评审或修改录入评审
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
	
	public IInstpService getInstpService() {
		return instpService;
	}
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	public List<InstpApplicationReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<InstpApplicationReview> reviews) {
		this.reviews = reviews;
	}
}
