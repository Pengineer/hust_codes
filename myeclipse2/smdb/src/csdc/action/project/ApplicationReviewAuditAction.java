package csdc.action.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.EntrustFunding;
import csdc.bean.GeneralFunding;
import csdc.bean.InstpFunding;
import csdc.bean.KeyFunding;
import csdc.bean.PostFunding;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectGranted;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public abstract class ApplicationReviewAuditAction extends ProjectBaseAction{

	private static final long serialVersionUID = 1L;
	protected int reviewAuditStatus;//评审审核状态
	protected int reviewAuditResult;//评审审核结果
	protected String number;//批准号编号
	protected String reviewAuditorName;//评审审核人
	protected Date reviewAuditDate;//评审审核时间
	protected String reviewAuditOpinion;//评审审核意见
	protected String reviewAuditOpinionFeedback;//评审审核意见（反馈给项目负责人）
	protected Double approveFee;//项目批准经费
	
	protected abstract String applicationClassName();//项目申请类类名
	protected abstract String grantedClassName();//项目立项类类名
	
	//准备添加评审审核
	public String toAdd() {
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class,this.entityId);
		approveFee = this.projectService.getDefaultFee(application);
		this.number = this.projectService.getDefaultProjectNumber(grantedClassName(), applicationClassName(), entityId);
		reviewAuditDate = new Date();
		return SUCCESS;
	}
	
	//准备修改评审审核
	public String toModify(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectApplication application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		reviewAuditStatus = application.getFinalAuditStatus();
		reviewAuditResult = application.getFinalAuditResult();
		reviewAuditOpinion = application.getFinalAuditOpinion();
		reviewAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
		reviewAuditDate = application.getFinalAuditDate();
		approveFee = this.projectService.getDefaultFee(application);
		if(application.getFinalAuditResult()==2){
			ProjectGranted granted = this.projectService.getGrantedByAppId(entityId);
			this.number = granted.getNumber();
			this.approveFee = granted.getApproveFee();
		}else{
			this.number = this.projectService.getDefaultProjectNumber(grantedClassName(), applicationClassName(), entityId);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 处理评审审核添加、修改公共部分
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectApplication doWithAddOrModify(ProjectApplication application){
		if(application.getStatus() == 7 && application.getFinalAuditStatus() != 3){
			if(reviewAuditOpinion != null){
				reviewAuditOpinion = ("A"+reviewAuditOpinion).trim().substring(1);
			}
			if(reviewAuditOpinionFeedback != null){
				application.setFinalAuditOpinionFeedback(("A" + reviewAuditOpinionFeedback).trim().substring(1));
			}else{
				application.setFinalAuditOpinionFeedback(null);
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, reviewAuditResult, reviewAuditStatus, reviewAuditOpinion);
			int isSubUni = (Integer)this.projectService.isSubordinateUniversityGranted(projectid);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni",isSubUni);
			application.edit(auditMap);
			application.setFinalAuditDate(reviewAuditDate);
			dao.modify(application);
		}
		return application;
	}
	
	public void validateAdd(){
		this.validateEdit(1);
	}

	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 查看评审审核
	 */
	public String view(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		reviewAuditStatus = application.getFinalAuditStatus();
		reviewAuditResult = application.getFinalAuditResult();
		reviewAuditOpinion = application.getFinalAuditOpinion();
		reviewAuditorName = application.getFinalAuditorName();
		reviewAuditDate = application.getFinalAuditDate();
		reviewAuditOpinion = application.getFinalAuditOpinion();
		reviewAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
		ProjectGranted granted = this.projectService.getGrantedByAppId(entityId);
		number = (null != granted) ? granted.getNumber() : "";
		approveFee = (null != granted && null !=granted.getApproveFee()) ? granted.getApproveFee() : 0;
		return SUCCESS;
	}

	/**
	 * 提交评审审核
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		Date submitDate = application.getFinalAuditDate();
		ProjectGranted granted = (ProjectGranted)this.projectService.getGrantedByAppId(entityId);
		if(application.getStatus() == 7 && application.getFinalAuditStatus() != 3){
			if(application.getFinalAuditResult() == 2 && !this.projectService.isGrantedNumberUnique(grantedClassName(), granted.getNumber(), entityId)){
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NUMBER_EXIST);
				return INPUT;
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			application.submit(auditMap);//提交操作结果
			application.setFinalAuditDate(submitDate);
			this.dao.modify(application);
		}
		
		if(application.getFinalAuditResult()==2 && application.getFinalAuditStatus()==3){//同意立项
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			if (granted.getProjectType().equals("general")) {
				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (generalFunding != null) {
					//立项通过则添加立项拨款申请，金额默认为批准经费的50%
//					generalFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					generalFunding.setStatus(0);
					dao.modify(generalFunding);
				}else {
					GeneralFunding newGeneralFunding = new GeneralFunding();
//					newGeneralFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					newGeneralFunding.setStatus(0);
					newGeneralFunding.setType(1);
					newGeneralFunding.setGranted(granted);
					newGeneralFunding.setGrantedId(granted.getId());
					newGeneralFunding.setProjectType(granted.getProjectType());
					dao.add(newGeneralFunding);
				}
			}else if (granted.getProjectType().equals("instp")) {
				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (instpFunding != null) {
					//立项通过则添加立项拨款申请，金额默认为批准经费的50%
//					instpFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					instpFunding.setStatus(0);
					dao.modify(instpFunding);
				}else {
					InstpFunding newInstpFunding = new InstpFunding();
//					newInstpFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					newInstpFunding.setStatus(0);
					newInstpFunding.setType(1);
					newInstpFunding.setGranted(granted);
					newInstpFunding.setGrantedId(granted.getId());
					newInstpFunding.setProjectType(granted.getProjectType());
					dao.add(newInstpFunding);
				}
			}else if (granted.getProjectType().equals("key")) {
				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (keyFunding != null) {
					//立项通过则添加立项拨款申请，金额默认为批准经费的50%
//					keyFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					keyFunding.setStatus(0);
					dao.modify(keyFunding);
				}else {
					KeyFunding newKeyFunding = new KeyFunding();
//					newKeyFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					newKeyFunding.setStatus(0);
					newKeyFunding.setType(1);
					newKeyFunding.setGranted(granted);
					newKeyFunding.setGrantedId(granted.getId());
					newKeyFunding.setProjectType(granted.getProjectType());
					dao.add(newKeyFunding);
				}
			}else if (granted.getProjectType().equals("entrust")) {
				EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (entrustFunding != null) {
					//立项通过则添加立项拨款申请，金额默认为批准经费的50%
//					entrustFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					entrustFunding.setStatus(0);
					dao.modify(entrustFunding);
				}else {
					EntrustFunding newEntrustFunding = new EntrustFunding();
//					newEntrustFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					newEntrustFunding.setStatus(0);
					newEntrustFunding.setType(1);
					newEntrustFunding.setGranted(granted);
					newEntrustFunding.setGrantedId(granted.getId());
					newEntrustFunding.setProjectType(granted.getProjectType());
					dao.add(newEntrustFunding);
				}
			}else if (granted.getProjectType().equals("post")) {
				PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
				if (postFunding != null) {
					//立项通过则添加立项拨款申请，金额默认为批准经费的50%
//					postFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					postFunding.setStatus(0);
					dao.modify(postFunding);
				}else {
					PostFunding newPostFunding = new PostFunding();
//					newPostFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
					newPostFunding.setStatus(0);
					newPostFunding.setType(1);
					newPostFunding.setGranted(granted);
					newPostFunding.setGrantedId(granted.getId());
					newPostFunding.setProjectType(granted.getProjectType());
					dao.add(newPostFunding);
				}
			}
		}
		return SUCCESS;
	}
	
	public void validateSubmit(){
		this.validateEdit(3);
	}
	 /**
	 * 编辑评审审核校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info = "";
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info+=GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(type == 1 ||type== 2){
			if(reviewAuditResult != 1 && reviewAuditResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_RESULT_NULL);
				info+=ProjectInfo.ERROR_PROJECT_RESULT_NULL;
			}
			if(reviewAuditResult == 2){
				if(number == null || number.trim().isEmpty()){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NUMBER_NULL);
					info+=ProjectInfo.ERROR_PROJECT_NUMBER_NULL;
				}else if(number.trim().length() > 40){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NUMBER_OUT);
					info+=ProjectInfo.ERROR_PROJECT_NUMBER_OUT;
				}else if(!this.projectService.isGrantedNumberUnique(grantedClassName(), number, entityId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NUMBER_EXIST);
					info+=ProjectInfo.ERROR_PROJECT_NUMBER_EXIST;
				}
			}
			if(reviewAuditDate == null){
				this.addFieldError("reviewAuditDate", ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL);
				info+=ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL;
			}
			if(reviewAuditOpinion != null && reviewAuditOpinion.length() > 2000){
				this.addFieldError("reviewAuditOpinion", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT);
				info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT;
			}
			if(reviewAuditOpinionFeedback != null && reviewAuditOpinionFeedback.length() > 200){
				this.addFieldError("reviewAuditOpinionFeedback", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT);
				info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	public int getReviewAuditStatus() {
		return reviewAuditStatus;
	}
	public void setReviewAuditStatus(int reviewAuditStatus) {
		this.reviewAuditStatus = reviewAuditStatus;
	}
	public String getReviewAuditorName() {
		return reviewAuditorName;
	}
	public void setReviewAuditorName(String reviewAuditorName) {
		this.reviewAuditorName = reviewAuditorName;
	}
	public Date getReviewAuditDate() {
		return reviewAuditDate;
	}
	public int getReviewAuditResult() {
		return reviewAuditResult;
	}
	public void setReviewAuditResult(int reviewAuditResult) {
		this.reviewAuditResult = reviewAuditResult;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}
	public void setReviewAuditDate(Date reviewAuditDate) {
		this.reviewAuditDate = reviewAuditDate;
	}
	public String getReviewAuditOpinion() {
		return reviewAuditOpinion;
	}
	public void setReviewAuditOpinion(String reviewAuditOpinion) {
		this.reviewAuditOpinion = reviewAuditOpinion;
	}


	public String getReviewAuditOpinionFeedback() {
		return reviewAuditOpinionFeedback;
	}

	public void setReviewAuditOpinionFeedback(String reviewAuditOpinionFeedback) {
		this.reviewAuditOpinionFeedback = reviewAuditOpinionFeedback;
	}
}
