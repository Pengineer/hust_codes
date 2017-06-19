package csdc.action.project.special;


import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.ProjectEndinspection;
import csdc.bean.SpecialEndinspection;
import csdc.bean.SpecialEndinspectionReview;
import csdc.service.ISpecialService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 专项任务结项申请评审管理
 * 实现了父类所有的抽象方法及专项任务项目结项评审审核特有方法
 * @author wangming
 */
public class EndinspectionReviewAction extends csdc.action.project.EndinspectionReviewAction {
	
	private static final long serialVersionUID = 1L;
	private static final String HQL = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
		"so.name, app.disciplineType, app.year, endRev.submitStatus, endRev.date, endRev.score, endi.file, endi.id, endi.reviewStatus, endi.reviewResult " +
		"from SpecialEndinspectionReview endRev, SpecialEndinspection endi, SpecialEndinspection all_endi, SpecialGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where endRev.reviewer.id=:belongId and endRev.endinspection.id=endi.id and endRev.endinspection.id=all_endi.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "specialEndReviewPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final String BUSINESS_TYPE = "013";//专项任务项目结项评审审核对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 29;//判断专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//判断专项任务项目立项是否在管辖范围内的标志位
	private List<SpecialEndinspectionReview> reviews;
	private ISpecialService specialService;
	
	
	public String pageName() {
		return EndinspectionReviewAction.PAGE_NAME;
	}
	public String projectType(){
		return EndinspectionReviewAction.PROJECT_TYPE;
	}
	public String businessType(){
		return EndinspectionReviewAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return EndinspectionReviewAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return EndinspectionReviewAction.CHECK_GRANTED_FLAG;
	}
	public String listHql(){
		return EndinspectionReviewAction.HQL;
	}
	/**
	 * 选择结项评审专家
	 * @author yangfq 
	 */
	public String addResultExpert(){
		for(int i = 0; i < reviews.size(); i++){
			SpecialEndinspectionReview endRev = reviews.get(i);
			endinspection = dao.query(ProjectEndinspection.class, endId);
			endRev.setEndinspection(endinspection);
			endRev.setEndinspectionId(endId);
			endRev.setReviewType(0);
			endRev.setProjectType(PROJECT_TYPE);
			endRev = (SpecialEndinspectionReview) this.specialService.setEndReviewInfoFromEndReview(endRev);
			endRev.setReviewerSn(i+1);
			endRev.setSubmitStatus(0);
			this.dao.add(endRev);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 录入评审
	 * @author 余潜玉
	 */
	@Transactional
	public String addResult(){
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(this.specialService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//保存各个专家的评审信息
		SpecialEndinspection endinspection = (SpecialEndinspection)this.dao.query(SpecialEndinspection.class,endId);
		int reviewType = this.specialService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev = (SpecialEndinspectionReview) this.specialService.setEndReviewInfoFromEndReview(endRev);
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				this.dao.add(endRev);
			}
		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev.setReviewerName(personExtService.regularNames(endRev.getReviewerName()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				endRev.setDate(new Date());
				endRev.setIsManual(1);//手动分配专家
				double score = endRev.getInnovationScore() + endRev.getScientificScore() + endRev.getBenefitScore();
				endRev.setScore(score);
				endRev.setGrade(this.specialService.getReviewGrade(score));
				this.dao.add(endRev);
			}
		}
		//保存总评信息
		endinspection = (SpecialEndinspection) this.doWithAddOrModifyResult(endinspection);
		this.dao.modify(endinspection);
		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
			this.specialService.doWithNewReview(endId);
   		}
		revResultFlag = 1;
//		opinionList = this.specialService.getSOByParentName("专项任务项目结项评审定性意见");
		return SUCCESS;
	}
	
	/**
	 *准备修改录入评审
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(this.specialService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		reviews = this.specialService.getAllEndReviewByEndId(endId);
		//教育部录入时对评审人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				SpecialEndinspectionReview review = reviews.get(i);
				review = (SpecialEndinspectionReview)this.specialService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		doWithToModifyResult();
		return SUCCESS;
	}
	
	/**
	 * 修改录入评审
	 * @author 余潜玉
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modifyResult(){
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(this.specialService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有评审信息
		List endReviews = this.specialService.getAllEndReviewByEndId(endId);
		for (Object endReview : endReviews) {
			dao.delete(endReview);
		}
//		this.dao.deleteMore(endReviews);
		//保存各个专家的评审信息
		SpecialEndinspection endinspection = (SpecialEndinspection)this.dao.query(SpecialEndinspection.class,endId);
		int reviewType = this.specialService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev = (SpecialEndinspectionReview) this.specialService.setEndReviewInfoFromEndReview(endRev);
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				this.dao.add(endRev);
			}
		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
			for(int i = 0; i < reviews.size(); i++){
				SpecialEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev.setReviewerName(personExtService.regularNames(endRev.getReviewerName()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				endRev.setDate(new Date());
				endRev.setIsManual(1);//手动分配专家
				double score = endRev.getInnovationScore() + endRev.getScientificScore() + endRev.getBenefitScore();
				endRev.setScore(score);
				endRev.setGrade(this.specialService.getReviewGrade(score));
				this.dao.add(endRev);
			}
		}
		//保存总评信息
		endinspection = (SpecialEndinspection) this.doWithAddOrModifyResult(endinspection);
		this.dao.modify(endinspection);
		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
			this.specialService.doWithNewReview(endId);
   		}
		revResultFlag = 1;
//		opinionList = this.specialService.getSOByParentName("专项任务项目结项评审定性意见");
		return SUCCESS;
	}
	
	/**
	 * 录入评审校验
	 * @author 余潜玉
	 */
	public void validateAddResult(){
		this.validateEditResult(1);
	}
	
	/**
	 * 修改录入评审结果校验
	 * @author 余潜玉 
	 */
	public void validateModifyResult(){
		this.validateEditResult(2);
	}
	
	/**
	 * 提交录入评审校验
	 * @author 余潜玉
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
	
	public ISpecialService getSpecialService() {
		return specialService;
	}
	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	public List<SpecialEndinspectionReview> getReviews() {
		return reviews;
	}
	public void setReviews(List<SpecialEndinspectionReview> reviews) {
		this.reviews = reviews;
	}
	
}
