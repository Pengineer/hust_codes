package csdc.action.award.moesocial;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.AwardApplication;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 社科奖励申请数据评审审核管理
 * @author 余潜玉   王燕
 */
public class ApplicationReviewAuditAction extends ApplicationAction {

	private static final long serialVersionUID = -7767286815879876682L;
	public String pageName() {
		return null;
	}
	public String simpleSearch(){
		return null;
	}
	//准备评审结果审核
	public String toAdd(){
		return SUCCESS;
	}
	
	//保存评审结果审核
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add(){
		for(int i = 0; i < entityIds.size(); i++){
			if(!this.awardService.checkIfUnderControl(loginer, entityIds.get(i),18, true)){
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		if(auditOpinion != null){
			auditOpinion = ("A" + auditOpinion).trim().substring(1);
		}
		if(auditResult == 1 && auditOpinionFeedback != null){
    		auditOpinionFeedback = ("A" + auditOpinionFeedback).trim().substring(1);
		}
    	for(int i = 0; i < entityIds.size(); i++){
    		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityIds.get(i));
    		if(awardApplication.getStatus() == 7 && awardApplication.getReviewAuditStatus() != 3 && awardApplication.getFinalAuditStatus() != 3){
    			Map auditMap = new HashMap();
				AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, auditResult, auditStatus, auditOpinion);
				awardApplication.setFinalAuditOpinionFeedback(auditOpinionFeedback);
				auditMap.put("auditInfo", auditInfo);
				auditMap.put("isSubUni", 0);
				awardApplication.edit(auditMap);//保存操作结果
    			dao.modify(awardApplication);
    		}
    	}
		return SUCCESS;
	}
	
	//评审结果审核校验
	@SuppressWarnings("unchecked")
	public void validateAdd(){
		String info = "";
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
			info += AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL;
		}
		if(1 != auditResult && 2 != auditResult){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AUDIT_RESULT_NULL);
			info += AwardInfo.ERROR_AUDIT_RESULT_NULL;
		}
		if(2 != auditStatus && 3 != auditStatus){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_SUBMIT_STATUS_NULL);
			info += AwardInfo.ERROR_SUBMIT_STATUS_NULL;
		}
		if(auditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,AwardInfo.ERROR_AUDIT_OPINION_OUT);
			info += AwardInfo.ERROR_AUDIT_OPINION_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
		if(auditOpinionFeedback != null && auditOpinionFeedback.length() > 200){
			this.addFieldError("auditOpinionFeedback", ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT);
		}
	}
	
	//准备修改评审结果审核
	public String toModify(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		if(awardApplication.getStatus() != 7 || awardApplication.getReviewAuditStatus() == 3 || awardApplication.getFinalAuditStatus() == 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
			return INPUT;
		}
		auditResult = awardApplication.getReviewAuditResult();
		auditOpinion = awardApplication.getReviewAuditOpinion();
		auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
		return SUCCESS;

	}
	//校验准备修改评审结果审核
	public void validateToModify(){
		publicValidate(AwardInfo.ERROR_MODIFY_REVIEWAUDIT_NULL);
	}
	
	//修改评审结果审核
	@Transactional
	public String modify(){
		return this.add();
	}
	//校验修改评审结果审核
	public void validateModify(){
		this.validateAdd();
	}
	
	//查看评审结果审核详情
	public String view(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class,entityId.trim());
		return SUCCESS;
	}
	//校验查看
	public void validateView(){
		publicValidate(AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
	}
	
	//提交评审结果审核
	@Transactional
	public String submit(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		awardApplication = (AwardApplication)dao.query(AwardApplication.class,entityId.trim());
		if(awardApplication.getStatus() == 7 && awardApplication.getReviewAuditStatus() != 3 && awardApplication.getReviewAuditStatus() > 0 && awardApplication.getFinalAuditStatus() != 3){//评审结果审核提交
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.submit(auditMap);//提交操作结果
			dao.modify(awardApplication);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
		}
		return SUCCESS;
	}
	//校验提交评审审核结果
	public void validateSubmit(){
		publicValidate(AwardInfo.ERROR_SUBMIT_REVIEWAUDIT_NULL);
	}

}
