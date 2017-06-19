package csdc.action.award.moesocial;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.AwardApplication;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 社科奖励申请数据审核管理
 * @author 余潜玉 王燕
 */
public class ApplicationApplyAuditAction extends ApplicationAction {

	private static final long serialVersionUID = -3307841909214586697L;
	private static final String PAGE_NAME = "awardApplicationpages";// 列表页面名称
	public String pageName() {
		return ApplicationApplyAuditAction.PAGE_NAME;
	}
	public String simpleSearch(){
		return null;
	}
	
	//准备审核奖励申请
	public String toAdd() {
		return SUCCESS;
	}
	
    //审核奖励申请(包括批量审核和单条审核)
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
    	if(auditResult == 1 && auditOpinionFeedback != null){//审核不同意才需要填写反馈意见
    		auditOpinionFeedback = ("A" + auditOpinionFeedback).trim().substring(1);
	        }
    	for(int i = 0; i < entityIds.size(); i++){
    		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityIds.get(i));
    		AccountType  accountType = loginer.getCurrentType();
    		int status = awardApplication.getStatus();
    		if(((accountType.equals(AccountType.MINISTRY) && status == 5) || (accountType.equals(AccountType.PROVINCE) && status == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) || ((accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)) && status == 2)) && awardApplication.getFinalAuditResult() != 3){
    			Map auditMap = new HashMap();
    			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, auditResult, auditStatus, auditOpinion);
    			awardApplication.setFinalAuditOpinionFeedback(auditOpinionFeedback);
    			auditMap.put("auditInfo", auditInfo);
    			auditMap.put("isSubUni", this.awardService.getIsSubUniByApp(awardApplication));
    			awardApplication.edit(auditMap);//保存操作结果
    			dao.modify(awardApplication);
    			if(awardApplication.getUniversityAuditStatus() == 3 && awardApplication.getUniversityAuditResult() == 2){//审核通过则默认报奖成果的审核也通过
    				productService.auditProduct(awardApplication.getProduct().getId(), 2, loginer.getAccount());
    			}
    		}
    	}
    	return SUCCESS;
    }

    //校验填写的奖励审核信息
	@SuppressWarnings("unchecked")
	public void validateAdd(){
		String info = "";
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AUDIT_NULL);
			info += AwardInfo.ERROR_AUDIT_NULL;
		}
		if(1!= auditResult && 2!= auditResult){
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
	
	//准备修改审核信息
	public String toModify(){
		//判断是否在当前账号的权限管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//获取奖励申请对象
		awardApplication = (AwardApplication) dao.query(AwardApplication.class,entityId.trim());
		AccountType  accountType = loginer.getCurrentType();
		int status = awardApplication.getStatus();
		//判断是否能修改审核信息
		if(!(((accountType.equals(AccountType.MINISTRY) && status == 5) || (accountType.equals(AccountType.PROVINCE) && status == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) || ((accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)) && status == 2)) && awardApplication.getFinalAuditResult() != 3)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_CANNOT_DO);
			return ERROR;
		}
		if(accountType.equals(AccountType.MINISTRY) && awardApplication.getStatus() == 5) {//社科司审核意见
			auditOpinion = awardApplication.getMinistryAuditOpinion();
			auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
			auditResult = awardApplication.getMinistryAuditResult();
		}else if(accountType.equals(AccountType.PROVINCE) && awardApplication.getStatus() == 4) {//省厅
			auditOpinion = awardApplication.getProvinceAuditOpinion();  
			auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
			auditResult = awardApplication.getProvinceAuditResult();
		}else if((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && awardApplication.getStatus() == 3) {//地方或部署高校
	        auditOpinion = awardApplication.getUniversityAuditOpinion();
	        auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
	        auditResult = awardApplication.getUniversityAuditResult();
		}else if((accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)) && awardApplication.getStatus() == 2) {//高校院系或研究机构
			auditOpinion = awardApplication.getDeptInstAuditOpinion();
			auditOpinionFeedback = awardApplication.getFinalAuditOpinionFeedback();
			auditResult = awardApplication.getDeptInstAuditResult();
		}
    	return SUCCESS;
    }
	
	//修改审核信息
	@Transactional
	public String modify(){
    	return this.add();
    }
	//校验修改审核信息
	public void validateModify(){
		this.validateAdd();
	}

	//查看审核详情
	public String view(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
    	awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		return SUCCESS;
	}
	//校验查看
	public void validateView() {
		publicValidate(AwardInfo.ERROR_VIEW_AWARDAPPLY_NULL);
	}
	
	//提交审核审核
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//获取奖励申请对象
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
		AccountType  accountType = loginer.getCurrentType();
		int status = awardApplication.getStatus();
    	if((accountType.equals(AccountType.MINISTRY) && status == 5) || (accountType.equals(AccountType.PROVINCE) && status == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) || ((accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)) && status == 2) && awardApplication.getFinalAuditResult() != 3){
    		Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.awardService.getIsSubUniByApp(awardApplication));
			awardApplication.submit(auditMap);//提交操作结果
			dao.modify(awardApplication);
			if((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && awardApplication.getFinalAuditResult() == 0){//审核通过则默认报奖成果的审核也通过
				productService.auditProduct(awardApplication.getProduct().getId(), awardApplication.getUniversityAuditResult(), loginer.getAccount());
			}
    	}
		return SUCCESS;
	}
	//校验提交方法
	public void validateSubmit() {
		publicValidate(AwardInfo.ERROR_SUBMIT_AUDIT_NULL);
	}
	
	//退回申请
	@SuppressWarnings("unchecked")
	@Transactional
	public String back() throws Exception{
		//判断是否在当前账号的管辖范围内
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//获取奖励申请对象
    	awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
    	AccountType  accountType = loginer.getCurrentType();
		int status = awardApplication.getStatus();
    	if((accountType.equals(AccountType.MINISTRY) && status == 5) || (accountType.equals(AccountType.PROVINCE) && status == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) || ((accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)) && status == 2) && awardApplication.getFinalAuditResult() != 3){
			awardApplication.back(this.awardService.getIsSubUniByApp(awardApplication));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				awardApplication.back(0);
			}else if(accountType.equals(AccountType.MINISTRY) && awardApplication.getUniversity() == null){//外部专家的奖励申请被教育部直接退回到申报状态
				awardApplication.setApplicantSubmitStatus(1);
				awardApplication.setStatus(1);
				awardApplication.setMinistryAuditStatus(0);
				awardApplication.setMinistryAuditResult(0);
			}
			/* 结束 */
			dao.modify(awardApplication);
			this.next();
			jsonMap.put("entityId", entityId);
    	}else{
    		jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NO_RIGHRT);
    	}
		return SUCCESS;
	}
	//校验退回方法
	public void validateBack() throws Exception{
		publicValidate(AwardInfo.ERROR_BACK_AUDIT_NULL);
	}
}
