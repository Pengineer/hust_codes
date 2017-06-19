package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectVariation;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目变更审核父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目变更申请审核共用的相关方法
 * @author 余潜玉
 */
public abstract class VariationApplyAuditAction extends ProjectBaseAction{

	private static final long serialVersionUID = 6501329881611241590L;
	
	protected ProjectVariation variation;// 一般项目变更
	protected String varId;// 项目变更id
	protected int varAuditResult;//审核结果	1:不通过		2：通过
	protected int varAuditStatus;//审核状态	1:退回	2:暂存	3:提交
	protected String varAuditOpinion;//审核意见
	protected String varAuditOpinionFeedback;//审核意见（反馈给项目负责人）
	protected String varAuditSelectIssue;//同意的变更事项
	@SuppressWarnings("rawtypes")
	protected List varItems;//可以同意的变更事项
	protected int auditViewFlag;//查看审核详细信息的标志位	1:院系/研究机构	2:校级	3:省厅	4:部级
	
	public String pageName() {
		return null;
	}
	public String[] column() {
		return null;
	}
	public String simpleSearch() {
		return null;
	}
	/**
	 * 进入项目变更申请审核添加页面预处理
	 * @author 余潜玉
	 */
	public String toAdd(){
		AccountType accountType = loginer.getCurrentType();
		variation = (ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
		String appId = this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByVarId(varId)).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		//变更审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		String varCanApproveItem = "";//可以同意的变更事项，多个以','隔开
//		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系或研究基地
//			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
//		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
//			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getDeptInstAuditResultDetail());
		//现在跳过院系审核
		if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
		}else if(accountType.equals(AccountType.PROVINCE)){//省厅
			if (variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("0000000000000000000000000000000000000000")) {//高校审核有同意变更的记录
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
			} else {
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
			}
		}else if(accountType.equals(AccountType.MINISTRY)){//教育部
			int	isSubUni = projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(variation.getId()));
			if(isSubUni == 1 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("0000000000000000000000000000000000000000")){//部属高校;高校审核有同意变更的记录
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
			} else if (isSubUni == 0 && variation.getProvinceAuditResultDetail() != null && !variation.getProvinceAuditResultDetail().equals("0000000000000000000000000000000000000000")){//地方高校;省厅审核有同意变更的记录
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getProvinceAuditResultDetail());
			} else if (isSubUni == 0 && variation.getUniversityAuditResultDetail() != null && !variation.getUniversityAuditResultDetail().equals("0000000000000000000000000000000000000000")){//地方高校;省厅审核无同意变更的记录或者省厅无审核记录就读取高校的审核记录) 
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
			} else {
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
			}
		}
		// 获取变更事项
		varItems = this.projectService.getVarItemList(varCanApproveItem);
		return SUCCESS;
	}

	/**
	 * 预处理添加变更审核校验
	 */
	public void validateToAdd(){
		this.validateEdit();
	}
	/**
	 * 添加项目变更申请审核
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add(){
        variation=(ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByVarId(varId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		AccountType accountType = loginer.getCurrentType();//账号类别
        int varType = variation.getStatus();
        if(variation.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && varType == 5) || (accountType.equals(AccountType.PROVINCE) && varType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && varType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && varType == 2))){
        	if(varAuditOpinion != null){
				varAuditOpinion = ("A"+varAuditOpinion).trim().substring(1);
			}
	    	if((accountType.equals(AccountType.MINISTRY) || varAuditResult == 1) && varAuditOpinionFeedback != null){//部级审核或部级以下审核不同意
	    		variation.setFinalAuditOpinionFeedback(("A" + varAuditOpinionFeedback).trim().substring(1));
	        }else{
	        	variation.setFinalAuditOpinionFeedback(null);
	        }
        	int	isSubUni = projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(variation.getId()));
        	Map auditMap = new HashMap();
        	AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, varAuditResult, varAuditStatus, varAuditOpinion);
        	auditMap.put("auditInfo",auditInfo);
        	auditMap.put("isSubUni", isSubUni);
        	String varApproveIssue;//同意变更事项
        	if(varAuditResult == 2){//同意
        		varApproveIssue = this.projectService.getVarApproveItem(varAuditSelectIssue);
        	}else{
        		varApproveIssue = null;
        	}
        	auditMap.put("varApproveIssue", varApproveIssue);
        	variation.edit(auditMap);//保存操作结果
    		this.dao.modify(variation);
    		if(variation.getFinalAuditResult() == 2 && variation.getFinalAuditStatus() == 3)//部级同意变更
    	        this.projectService.variationProject(variation);
        }
        return SUCCESS;
	}
	
	/**
	 * 添加变更审核信息校验
	 */
	public void validateAdd(){
		this.validateEdit();
		if(varAuditStatus != 2 && varAuditStatus != 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if(1 != varAuditResult && 2 != varAuditResult){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_AUDIT_RESULT_NULL);
		}
		if(varAuditResult == 2 && (varAuditSelectIssue == null || varAuditSelectIssue.trim().length() == 0)){//同意变更
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_SELECT_ISSUE_NULL);
		}
		if(varAuditOpinion != null && varAuditOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_VAR_AUDIT_OPINION_OUT);
		}
		if(varAuditOpinionFeedback != null && varAuditOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_OPINION_FEEDBACK_OUT);
		}

	}
	/**
	 * 修改项目变更申请审核
	 * @author 余潜玉
	 */
	public String toModify(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		variation = (ProjectVariation) this.dao.query(ProjectVariation.class,varId.trim());
		String appId = this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByVarId(varId)).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
    	//变更各个级别审核截止时间
    	deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
    	int varType = variation.getStatus();
    	String varCanApproveItem = "";//可以同意的变更事项，多个以','隔开
    	if((accountType.equals(AccountType.MINISTRY)) && varType == 5 && ((account.getIsPrincipal() == 1 && null != variation.getFinalAuditorAgency() && account.getAgency().getId().equals(variation.getFinalAuditorAgency().getId())) || (account.getIsPrincipal() == 0 && null != variation.getFinalAuditor() && account.getOfficer().getId().equals(variation.getFinalAuditor().getId())))) {//社科司审核意见
			varAuditOpinion = variation.getFinalAuditOpinion();
			varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
			varAuditResult = variation.getFinalAuditResult();
			varAuditSelectIssue = this.projectService.getVarCanApproveItem(variation.getFinalAuditResultDetail());
			int	isSubUni = projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(variation.getId()));
			if(isSubUni == 1){//部属高校
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
			}else{//地方高校
				varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getProvinceAuditResultDetail());
			}
		}else if((accountType.equals(AccountType.PROVINCE)) && varType == 4 && ((account.getIsPrincipal() == 1 && null != variation.getProvinceAuditorAgency() && account.getAgency().getId().equals(variation.getProvinceAuditorAgency().getId())) || (account.getIsPrincipal() == 0 && null != variation.getProvinceAuditor() && account.getOfficer().getId().equals(variation.getProvinceAuditor().getId())))) {	//省厅审核
			varAuditOpinion = variation.getProvinceAuditOpinion();
			varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
			varAuditResult = variation.getProvinceAuditResult();
			varAuditSelectIssue = this.projectService.getVarCanApproveItem(variation.getProvinceAuditResultDetail());
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
		}else if((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && varType == 3 && ((account.getIsPrincipal() == 1 && null != variation.getUniversityAuditorAgency() && account.getAgency().getId().equals(variation.getUniversityAuditorAgency().getId())) || (account.getIsPrincipal() == 0 && null != variation.getUniversityAuditor() && account.getOfficer().getId().equals(variation.getUniversityAuditor().getId())))) {//高校审核
	        varAuditOpinion = variation.getUniversityAuditOpinion();
			varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
	        varAuditResult = variation.getUniversityAuditResult();
	        varAuditSelectIssue = this.projectService.getVarCanApproveItem(variation.getUniversityAuditResultDetail());
//			varCanApproveItem = this.instpService.getVarCanApproveItem(variation.getDeptInstAuditResultDetail());
	      //现跳过院系
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
		}else if((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && varType == 2 && ((account.getIsPrincipal() == 0 && null != variation.getDeptInstAuditor() && account.getOfficer().getId().equals(variation.getDeptInstAuditor().getId())) || (account.getIsPrincipal() == 1 && (null != variation.getDeptInstAuditorDept() && account.getDepartment().getId().equals(variation.getDeptInstAuditorDept().getId()) || (null != variation.getDeptInstAuditorInst() && account.getInstitute().getId().equals(variation.getDeptInstAuditorInst().getId())))))) {//院系或研究基地审核
			varAuditOpinion = variation.getDeptInstAuditOpinion();
			varAuditOpinionFeedback = variation.getFinalAuditOpinionFeedback();
			varAuditResult = variation.getDeptInstAuditResult();
			varAuditSelectIssue = this.projectService.getVarCanApproveItem(variation.getDeptInstAuditResultDetail());
			varCanApproveItem = this.projectService.getVarCanApproveItem(variation);
		}else {
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		// 获取变更事项
		varItems = this.projectService.getVarItemList(varCanApproveItem);
    	return SUCCESS;
	}

	/**
	 * 预处理修改变更审核校验
	 * @author 余潜玉
	 */
	public void validateToModify(){
		this.validateEdit();
	}
	
	/**
	 * 修改变更审核
	 * @author 余潜玉
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改变更审核校验
	 * @author 余潜玉
	 */
	public void validateModify(){
		this.validateAdd();
	}
	
	/**
	 * 查看项目变更申请审核
	 * @author 余潜玉
	 */
	public String view(){//1:院系/研究机构	2:校级	3:省厅	4:部级
		variation = (ProjectVariation) this.dao.query(ProjectVariation.class,varId.trim());
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(auditViewFlag == 1) {//高校院系或研究机构
			if (!(accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != variation.getDeptInstAuditor() && account.getOfficer().getId().equals(variation.getDeptInstAuditor().getId())) || (account.getIsPrincipal() == 1 && ((null != variation.getDeptInstAuditorDept() && account.getDepartment().getId().equals(variation.getDeptInstAuditorDept().getId()) || (null != variation.getDeptInstAuditorInst() && account.getInstitute().getId().equals(variation.getDeptInstAuditorInst().getId()))))))){
				variation.setDeptInstAuditOpinion("");
			}
		}else if (auditViewFlag == 2) {
			if (!(accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != variation.getUniversityAuditor() && account.getOfficer().getId().equals(variation.getUniversityAuditor().getId())) || (account.getIsPrincipal() == 1 && null != variation.getUniversityAuditorAgency() && account.getAgency().getId().equals(variation.getUniversityAuditorAgency().getId())))) {
				variation.setUniversityAuditOpinion("");
			}
		}else if (auditViewFlag == 3) {
			if (!(accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != variation.getProvinceAuditor() && account.getOfficer().getId().equals(variation.getProvinceAuditor().getId())) || (account.getIsPrincipal() == 1 && null != variation.getProvinceAuditorAgency() && account.getAgency().getId().equals(variation.getProvinceAuditorAgency().getId())))) {
				variation.setProvinceAuditOpinion("");
			}
		}else if (auditViewFlag == 4) {
			if (!(accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != variation.getFinalAuditor() && account.getOfficer().getId().equals(variation.getFinalAuditor().getId())) || (account.getIsPrincipal() == 1 && null != variation.getFinalAuditorAgency() && account.getAgency().getId().equals(variation.getFinalAuditorAgency().getId())))) {
				variation.setFinalAuditOpinion("");
			}
		}
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByVarId(varId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		variation.setDeptInstAuditResultDetail(this.projectService.getVarApproveName(variation.getDeptInstAuditResultDetail()));
		variation.setUniversityAuditResultDetail(this.projectService.getVarApproveName(variation.getUniversityAuditResultDetail()));
		variation.setProvinceAuditResultDetail(this.projectService.getVarApproveName(variation.getProvinceAuditResultDetail()));
		variation.setFinalAuditResultDetail(this.projectService.getVarApproveName(variation.getFinalAuditResultDetail()));
		return SUCCESS;
	}

	/**
	 * 查看变更审核校验
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public void validateView(){
		if (varId == null || varId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
		}
	}

	/**
	 * 提交项目变更申请审核
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
        variation = (ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
        String graId = this.projectService.getGrantedIdByVarId(varId);
        if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(graId).trim(), checkGrantedFlag(), true)){
        	jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
        	return INPUT;
		}
        int varType = variation.getStatus();
        if(variation.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && varType == 5) || (accountType.equals(AccountType.PROVINCE) && varType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && varType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && varType == 2))){
        	Map auditMap = new HashMap();
        	AuditInfo auditInfo = projectService.getAuditInfo(loginer, 0, 3, null);
        	auditMap.put("auditInfo", auditInfo);
        	auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(graId));
        	variation.submit(auditMap);//提交操作结果
        }
        this.dao.modify(variation);
        if(variation.getFinalAuditResult() == 2 && variation.getFinalAuditStatus() == 3)//部级同意变更
        	this.projectService.variationProject(variation);
		return SUCCESS;
	}

	/**
	 * 提交变更审核校验
	 * @author 余潜玉
	 */
	public void validateSubmit(){
		this.validateEdit();
	}

	/**
	 * 退回项目变更申请
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType = loginer.getCurrentType();
        variation = (ProjectVariation)this.dao.query(ProjectVariation.class, varId.trim());
        if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByVarId(varId)).trim(), checkGrantedFlag(), true)){
        	jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
        	return INPUT;
		}
        int varType = variation.getStatus();
        if( variation.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && varType == 5) || (accountType.equals(AccountType.PROVINCE) && varType == 4)||((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && varType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && varType == 2))){
        	variation.back(projectService.isSubordinateUniversityGranted(this.projectService.getGrantedIdByVarId(varId)));
        	/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				variation.back(0);
			}
			/* 结束 */
			dao.modify(variation);
        }
		return SUCCESS;
	}

	/**
	 * 退回变更审核校验
	 * @author 余潜玉
	 */
	public void validateBackAudit(){
		this.validateEdit();
	}
	
	/**
	 * 审核校验公用方法
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(){
		String info = "";
		if (varId == null || varId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
			info += ProjectInfo.ERROR_VAR_VIEW_NULL;
		}else{
			projectid = this.projectService.getGrantedIdByVarId(varId);
			if (projectid == null || projectid.isEmpty()) {//项目id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else{
				ProjectGranted general = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
				if(general == null){
					this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
					info += GlobalInfo.ERROR_EXCEPTION_INFO;
				}else if(general.getStatus() == 3){//中止
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
					info += ProjectInfo.ERROR_PROJECT_STOP;
				}else if(general.getStatus() == 4){//撤项
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
					info += ProjectInfo.ERROR_PROJECT_REVOKE;
				}
			}
		}
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//校验业务设置状态
		varStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (varStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if(this.projectService.getPassEndinspectionByGrantedId(projectid).size()>0){//存在已通过结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
			info += ProjectInfo.ERROR_END_PASS;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	public ProjectVariation getVariation() {
		return variation;
	}
	public void setVariation(ProjectVariation variation) {
		this.variation = variation;
	}
	public int getVarAuditResult() {
		return varAuditResult;
	}
	public void setVarAuditResult(int varAuditResult) {
		this.varAuditResult = varAuditResult;
	}
	public String getVarAuditOpinion() {
		return varAuditOpinion;
	}
	public void setVarAuditOpinion(String varAuditOpinion) {
		this.varAuditOpinion = varAuditOpinion;
	}
	public String getVarId() {
		return varId;
	}
	public void setVarId(String varId) {
		this.varId = varId;
	}
	public int getAuditViewFlag() {
		return auditViewFlag;
	}
	public void setAuditViewFlag(int auditViewFlag) {
		this.auditViewFlag = auditViewFlag;
	}
	public int getVarAuditStatus() {
		return varAuditStatus;
	}
	public void setVarAuditStatus(int varAuditStatus) {
		this.varAuditStatus = varAuditStatus;
	}
	public String getVarAuditOpinionFeedback() {
		return varAuditOpinionFeedback;
	}
	public void setVarAuditOpinionFeedback(String varAuditOpinionFeedback) {
		this.varAuditOpinionFeedback = varAuditOpinionFeedback;
	}
	public String getVarAuditSelectIssue() {
		return varAuditSelectIssue;
	}
	public void setVarAuditSelectIssue(String varAuditSelectIssue) {
		this.varAuditSelectIssue = varAuditSelectIssue;
	}
	@SuppressWarnings("rawtypes")
	public List getVarItems() {
		return varItems;
	}
	@SuppressWarnings("rawtypes")
	public void setVarItems(List varItems) {
		this.varItems = varItems;
	}

}