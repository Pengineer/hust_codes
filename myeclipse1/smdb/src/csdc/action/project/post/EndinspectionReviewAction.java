package csdc.action.project.post;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.KeyEndinspectionReview;
import csdc.bean.PostEndinspection;
import csdc.bean.PostEndinspectionReview;
import csdc.bean.PostGranted;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.service.IPostService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public class EndinspectionReviewAction extends csdc.action.project.EndinspectionReviewAction {
	
	private static final long serialVersionUID = 1L;
	
	private static final String HQL = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
		"so.name, app.disciplineType, app.year, endRev.submitStatus, endRev.date, endRev.score, endi.file, endi.id, endi.reviewStatus, endi.reviewResult " +
		"from PostEndinspectionReview endRev, PostEndinspection endi, PostEndinspection all_endi, PostGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where endRev.reviewer.id=:belongId and endRev.endinspection.id=endi.id and endRev.endinspection.id=all_endi.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "postEndinspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "post";
	private static final String BUSINESS_TYPE = "033";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;
	private List<PostEndinspectionReview> reviews;
	private IPostService postService;
	
	private int reviewFlag;//鉴定类别	1：走流程  2：录入
	private int isApplyExcellent;//是否申请优秀成果
	private String endProductInfo;//结项成果信息
	private String endMember;//结项主要参加人姓名
	private String reviewOpinionFeedback;//小组鉴定意见（反馈给 项目负责人）
	
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
	 * @author yangfq 4028d89a3f120bb0013f124e391300c3
	 */
	public String addResultExpert(){
		for(int i = 0; i < reviews.size(); i++){
			PostEndinspectionReview endRev = reviews.get(i);
			endinspection = dao.query(ProjectEndinspection.class, endId);
			endRev.setEndinspection(endinspection);
			endRev.setEndinspectionId(endId);
			endRev.setReviewType(0);
			endRev.setProjectType(PROJECT_TYPE);
			endRev = (PostEndinspectionReview) this.postService.setEndReviewInfoFromEndReview(endRev);
			endRev.setReviewerSn(i+1);
			endRev.setSubmitStatus(0);
			this.dao.add(endRev);
		}
		return SUCCESS;
	}
	
	/**
	 * 准备录入鉴定
	 * @author 余潜玉
	 */
	public String toAddResult() {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		setReviewDate(new Date());
		return SUCCESS;
	}
	
	/**
	 * 录入鉴定
	 * @author 肖雅
	 */
	@Transactional
	public String addResult(){
		if (!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		if(reviewFlag == 2){//录入鉴定信息，新建结项数据
			endinspection = new PostEndinspection();
			endinspection.setCreateMode(1);
			endinspection.setCreateDate(new Date());
			endinspection.setGranted((PostGranted)this.dao.query(PostGranted.class, projectid));
			String endId = this.dao.add(endinspection);
			endinspection = (PostEndinspection)this.dao.query(PostEndinspection.class, endId);
		}else{
			endinspection = (PostEndinspection)this.dao.query(PostEndinspection.class,this.endId);
		}
		//保存各个专家的鉴定信息
		int reviewType = this.postService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				PostEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev = (PostEndinspectionReview) this.postService.setEndReviewInfoFromEndReview(endRev);
				endRev.setDate(this.postService.setDateHhmmss(getReviewDate()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				this.dao.add(endRev);
			}
		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
			for(int i = 0; i < reviews.size(); i++){
				PostEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev.setReviewerName(personExtService.regularNames(endRev.getReviewerName()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				endRev.setDate(this.postService.setDateHhmmss(getReviewDate()));
				endRev.setIsManual(1);//手动分配专家
				double score = endRev.getInnovationScore() + endRev.getScientificScore() + endRev.getBenefitScore();
				endRev.setScore(score);
				endRev.setGrade(this.postService.getReviewGrade(score));
				this.dao.add(endRev);
			}
		}
		//保存总评信息
		endinspection = (PostEndinspection) this.doWithAddOrModifyResult(endinspection);
		endinspection = (PostEndinspection) this.doWithProductInfo(endProductInfo, endinspection);//设置成果相关信息
		this.doWithEndResult();//处理结项其他信息
		this.dao.addOrModify(endinspection);
		if((reviewType == 3 || reviewType == 4) && submitStatus == 3){//提交则对评审人信息进行入库处理
			this.postService.doWithNewReview(endinspection.getId());
   		}
		revResultFlag = 1;
//		opinionList = this.projectService.getSOByParentName("后期资助项目结项评审定性意见");
		return SUCCESS;
	}
	
	/**
	 * 录入鉴定校验
	 * @author 鉴定
	 */
	public void validateAddResult(){
		this.validateEditResult(1);
	}
	
	
	/**
	 * 准备修改鉴定结果
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		endId = this.postService.getCurrentEndinspectionByGrantedId(projectid).getId();
		reviews = this.postService.getAllEndReviewByEndId(endId);
		//教育部录入时对评审人人员id的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && reviews.size() > 0){
			for (int i=0; i < reviews.size(); i++){
				PostEndinspectionReview review = reviews.get(i);
				review = (PostEndinspectionReview)this.postService.setReviewPersonInfoFromReview(review);
				reviews.set(i, review);
			}
		}
		//评审信息预处理
		endinspection = (PostEndinspection) this.dao.query(PostEndinspection.class, endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		endProductInfo = this.postService.getProductTypeReal(endinspection.getImportedProductInfo(), endinspection.getImportedProductTypeOther());
		endMember = endinspection.getMemberName();
		reviewOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		reviewOpinionQualitative = endinspection.getReviewOpinionQualitative();
		setReviewDate(endinspection.getReviewDate());
		doWithToModifyResult();
		session.put("projectid", projectid);
		return SUCCESS;
	}
	
	/**
	 * 修改鉴定结果
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modifyResult(){
		if(!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(this.postService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//删除原有鉴定信息
		List endReviews = this.postService.getAllEndReviewByEndId(endId);
		for (Object member : endReviews) {
			dao.delete(member);
		}
//		this.postService.deleteMore(endReviews);
		//保存各个专家的评审信息
		endinspection = (PostEndinspection)this.dao.query(PostEndinspection.class,endId);
		int reviewType = this.postService.getReviewTypeByAccountType(loginer.getCurrentType());
		if(reviewType == 2){//教育部录入
			for(int i = 0; i < reviews.size(); i++){
				PostEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev = (PostEndinspectionReview) this.postService.setEndReviewInfoFromEndReview(endRev);
				endRev.setDate(this.postService.setDateHhmmss(getReviewDate()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				this.dao.add(endRev);
			}
		}else if(reviewType == 3 || reviewType == 4){//省厅或高校录入
			for(int i = 0; i < reviews.size(); i++){
				PostEndinspectionReview endRev = reviews.get(i);
				endRev.setEndinspection(endinspection);
				endRev.setReviewType(reviewType);
				endRev.setReviewerName(personExtService.regularNames(endRev.getReviewerName()));
				endRev.setReviewerSn(i+1);
				endRev.setSubmitStatus(submitStatus);
				endRev.setDate(this.postService.setDateHhmmss(getReviewDate()));
				endRev.setIsManual(1);//手动分配专家
				double score = endRev.getInnovationScore() + endRev.getScientificScore() + endRev.getBenefitScore();
				endRev.setScore(score);
				endRev.setGrade(this.postService.getReviewGrade(score));
				this.dao.add(endRev);
			}
		}
		//保存总评信息
		endinspection = (PostEndinspection) this.doWithAddOrModifyResult(endinspection);
		endinspection = (PostEndinspection) this.doWithProductInfo(endProductInfo, endinspection);//设置成果相关信息
		this.doWithEndResult();//处理结项其他信息
		this.dao.modify(endinspection);
		revResultFlag = 1;
		projectid = (String)session.get("projectid");
		this.doWithToModifyResult();;
		return SUCCESS;
	}
	
	public void validateModifyResult(){
		this.validateEditResult(2);
	}

	/**
	 * 提交录入鉴定
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		doWithSubmitResult();
		endinspection = (PostEndinspection)this.dao.query(PostEndinspection.class, endId);
		if(reviewFlag == 2){//教育部录入鉴定不同意
			if(endinspection.getReviewResult() == 1){
				AuditInfo auditInfo = this.postService.getAuditInfo(loginer, 1, 3, null);
				endinspection = this.postService.fillFinalAuditInfos(endinspection, auditInfo);
				endinspection.setFinalAuditDate(endinspection.getReviewDate());
				endinspection.setApplicantSubmitDate(endinspection.getReviewDate());
			}
			if(endinspection.getReviewResult() == 2){//鉴定结果为同意，设置立项表状态为鉴定
				PostGranted granted = (PostGranted) this.dao.query(PostGranted.class, projectid);
				granted.setStatus(5);
				this.dao.modify(granted);
			}
		}
		int reviewType = this.projectService.getReviewTypeByAccountType(loginer.getCurrentType());
		if((reviewType == 3 || reviewType == 4)){//提交则对评审人信息进行入库处理
			this.projectService.doWithNewReview(endId);
   		}
		this.dao.modify(endinspection);
		return SUCCESS;
	}
	
	/**
	 * 提交录入鉴定校验
	 * @author 肖雅
	 */
	public void validateSubmitResult(){
		this.validateEditResult(3);
	}
	
	/**
	 * 编辑鉴定校验
	 * @param type 校验类型：1添加鉴定; 2修改鉴定; 3提交鉴定;  4添加小组鉴定; 5修改小组鉴定; 6提交小组鉴定
	 * @author 王燕、肖雅
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info = "";
		info = this.doWithValidateEdit(type);
		if (this.postService.getPassReviewByGrantedId(projectid).size() > 0){//有已通过鉴定
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_PASS);
			info += ProjectInfo.ERROR_END_REVIEW_PASS;
		}
		if(type == 4 || type == 5){
			if(reviewWay != 1){//只能是通讯鉴定
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_WAY_NULL1);
				info += ProjectInfo.ERROR_REVIEW_WAY_NULL;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 录入鉴定结果校验
	 * type 校验类型：1添加录入鉴定; 2修改录入鉴定; 3提交录入鉴定
	 * @author 肖雅
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
			if(reviewFlag == 2 && reviewOpinionFeedback != null && reviewOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_OPINION_OUT);
			}
			if(reviewWay != 1){//只能是通讯鉴定
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEW_WAY_NULL1);
			}
		}
	}
	
	public PostEndinspection doWithProductInfo(String endProductInfo, ProjectEndinspection endinspection){
		if(endProductInfo != null && endProductInfo.contains("其他")){
			String[] proTypes = endProductInfo.split("\\(");
			if(proTypes.length > 1){
				String productTypeNames = proTypes[0];
				String[] productTypeothers = proTypes[1].split("\\)");
				String productTypeOther = productTypeothers[0];
				productTypeNames += productTypeothers[1];
				endinspection.setImportedProductInfo(productTypeNames);
				endinspection.setImportedProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
			}
		}else{
			endinspection.setImportedProductInfo(endProductInfo);
			endinspection.setImportedProductTypeOther(null);
		}
		return (PostEndinspection) endinspection;
	}
	
	/**
	 * 录入鉴定基本校验
	 * @param type 1:添加	2：修改	3：提交
	 */
	public void validateReviewBasic(int type){
		if(reviewFlag == 1 && (endId == null || endId.trim().isEmpty())){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NULL);
		}else{
			if (projectid == null || projectid.isEmpty()) {//项目id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}else{
				ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
				if(granted == null){
					this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				}else if(granted.getStatus() == 3){//中止
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				}else if(granted.getStatus() == 4){//撤项
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				}
			}
			if(reviewFlag == 1){
				endinspection = (PostEndinspection) this.postService.getCurrentEndinspectionByGrantedId(projectid);
				AccountType accountType = loginer.getCurrentType();
				int status = endinspection.getStatus();
				if(endinspection.getFinalAuditStatus() == 3){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_STOP);
				}else if((accountType.equals(AccountType.MINISTRY) && status < 6) || (accountType.equals(AccountType.PROVINCE) && status < 5) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status < 4)){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NO_RIGHRT);
				}
				int reviewflag = this.projectService.checkReview(endId);
				if(type == 1){//添加录入鉴定
					//校验是否已存在鉴定信息
					if(reviewflag != -1){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_EXIST);
					}
				}else if(type == 2 || type == 3){
					if(reviewflag == 23 || reviewflag == 33 || reviewflag == 43){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_SUBMIT);
					}
				}
			}else if (reviewFlag == 2 && (type == 1 || type == 3)){//添加或提交
				if(this.postService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
				}
			}
			if(this.postService.getPassReviewByGrantedId(projectid).size() > 0){//鉴定已通过
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_PASS);
			}
		}
	}
	
	public void doWithEndResult(){//状态为提交对结项的处理
		Date submitDate = this.postService.setDateHhmmss(reviewDate);
		endinspection.setReviewDate(submitDate);
		endinspection.setApplicantSubmitDate(submitDate);
		endinspection.setIsApplyNoevaluation(0);
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setFinalAuditOpinionFeedback((reviewOpinionFeedback != null && !reviewOpinionFeedback.trim().isEmpty())? ("A" + reviewOpinionFeedback).trim().substring(1) : null);//反馈意见
		if(reviewFlag == 2){//鉴定类别	1：走流程  2：录入
			if(this.endMember != null){
				endinspection.setMemberName(this.postService.MutipleToFormat(personExtService.regularNames(this.endMember)));//设置鉴定人信息
			}
			if (submitStatus ==3 && reviewResult == 1){//鉴定结果为不同意并提交
				AuditInfo auditInfo = this.postService.getAuditInfo(loginer, 1, 3, null);
				endinspection = this.postService.fillFinalAuditInfos(endinspection, auditInfo);
				endinspection.setFinalAuditDate(submitDate);
			}
			if(submitStatus ==3 && reviewResult == 2){//鉴定结果为同意并提交，设置立项状态为鉴定
				PostGranted granted = (PostGranted) this.dao.query(PostGranted.class, projectid);
				granted.setStatus(5);
				dao.modify(granted);
			}
		}
	}
	
	public IPostService getPostService() {
		return postService;
	}
	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
	public String getReviewOpinionFeedback() {
		return reviewOpinionFeedback;
	}
	public void setReviewOpinionFeedback(String reviewOpinionFeedback) {
		this.reviewOpinionFeedback = reviewOpinionFeedback;
	}
	public int getReviewFlag() {
		return reviewFlag;
	}
	public void setReviewFlag(int reviewFlag) {
		this.reviewFlag = reviewFlag;
	}
	public int getIsApplyExcellent() {
		return isApplyExcellent;
	}
	public void setIsApplyExcellent(int isApplyExcellent) {
		this.isApplyExcellent = isApplyExcellent;
	}
	public String getEndProductInfo() {
		return endProductInfo;
	}
	public void setEndProductInfo(String endProductInfo) {
		this.endProductInfo = endProductInfo;
	}
	public String getEndMember() {
		return endMember;
	}
	public void setEndMember(String endMember) {
		this.endMember = endMember;
	}
	public void setReviews(List<PostEndinspectionReview> reviews) {
		this.reviews = reviews;
	}
	public List<PostEndinspectionReview> getReviews() {
		return reviews;
	}
}
