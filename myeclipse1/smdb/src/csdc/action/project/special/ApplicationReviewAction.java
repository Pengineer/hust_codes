package csdc.action.project.special;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.SpecialApplication;
import csdc.bean.SpecialApplicationReview;
import csdc.service.ISpecialService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 专项任务项目申请申请鉴定管理
 * @author wangming
 */
public class ApplicationReviewAction extends csdc.action.project.ApplicationReviewAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, app.name, uni.id, app.agencyName, app.applicantId, app.applicantName, " +
		"so.name, app.disciplineType, app.year, appRev.submitStatus, appRev.date, appRev.score, app.file, app.reviewStatus, app.reviewResult " +
		"from SpecialApplicationReview appRev, SpecialApplication app left outer join app.university uni left outer join app.subtype so " +
		"where appRev.reviewer.id=:belongId and appRev.application.id=app.id ";
	
	private static final String PAGE_NAME = "specialAppReviewPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final String BUSINESS_TYPE = "011";//专项任务项目结项鉴定对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 29;//专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//专项任务项目立项是否在管辖范围内的标志位
	private List<SpecialApplicationReview> reviews;
	private ISpecialService specialService;
	
	
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
		SpecialApplication application = (SpecialApplication)this.dao.query(SpecialApplication.class, entityId);
		for(int i = 0; i < reviews.size(); i++){
			SpecialApplicationReview appRev = reviews.get(i);
			appRev.setApplication(application);
			appRev.setApplicationId(entityId);
			appRev = (SpecialApplicationReview) this.specialService.setAppReviewInfoFromAppReview(appRev);
			List<SpecialApplicationReview> gappRev = this.dao.query("from SpecialApplicationReview where applicationId = ?",entityId);
			for (SpecialApplicationReview agp : gappRev) {
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
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//保存各个专家的鉴定信息
		SpecialApplication application = (SpecialApplication)this.dao.query(SpecialApplication.class, entityId);
		int reviewType = this.specialService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (SpecialApplicationReview) this.specialService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
		//保存总评信息
		application = (SpecialApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
		revResultFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 进入专项任务项目结项鉴定录入修改页面预处理
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		reviews = this.specialService.getAllAppReviewByAppId(entityId);
		//教育部录入时对鉴定人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				SpecialApplicationReview review = reviews.get(i);
				review = (SpecialApplicationReview)this.specialService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		doWithToModifyResult();
		return SUCCESS;
	}
	
	/**
	 * 专项任务项目结项鉴定录入修改
	 * @author 肖雅
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modifyResult(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有鉴定信息
		List appReviews = this.specialService.getAllAppReviewByAppId(entityId);
		for (Object appReview : appReviews) {
			dao.delete(appReview);
		}
//		this.specialService.deleteMore(appReviews);
		//保存各个专家的鉴定信息
		SpecialApplication application = (SpecialApplication)this.dao.query(SpecialApplication.class, entityId);
		int reviewType = this.specialService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialApplicationReview appRev = reviews.get(i);
				appRev.setApplication(application);
				appRev.setReviewType(reviewType);
				appRev = (SpecialApplicationReview) this.specialService.setAppReviewInfoFromAppReview(appRev);
				appRev.setReviewerSn(i+1);
				appRev.setSubmitStatus(submitStatus);
				this.dao.add(appRev);
			}
		}
		//保存总评信息
		application = (SpecialApplication) this.doWithAddOrModifyResult(application);
		this.dao.modify(application);
		revResultFlag = 1;
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
	
	public ISpecialService getSpecialService() {
		return specialService;
	}
	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	public List<SpecialApplicationReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<SpecialApplicationReview> reviews) {
		this.reviews = reviews;
	}
}
