package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectAnninspection;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 项目年检申请审核父类管理
 * @author 肖雅
 */
public abstract class AnninspectionApplyAuditAction extends ProjectBaseAction{

	private static final long serialVersionUID = 1L;
	
	protected int annAuditStatus;//年检状态
	protected String annAuditOpinion;//年检意见
	protected String annAuditOpinionFeedback;//年检意见（反馈给项目负责人）
	protected int annAuditResult;//年检结果
	protected String annId;//年检id
	protected String annAuditPerson;//年检审核人
	protected Date annAuditDate;//年检审核时间
	protected int vtp;//查看审核参数

	public String[] column() {
		return null;
	}
	public String pageName() {
		return null;
	}
	public String simpleSearch() {
		return null;
	}
	
	/**
	 * 查看项目年检审核详细信息
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByAnnId(annId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectAnninspection anninspection = (ProjectAnninspection) this.dao.query(ProjectAnninspection.class, annId);
		if(vtp == 1) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != anninspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(anninspection.getDeptInstAuditorDept().getId()) || (null != anninspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(anninspection.getDeptInstAuditorInst().getId())))))){
				annAuditOpinion = anninspection.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != anninspection.getDeptInstAuditor() && account.getOfficer().getId().equals(anninspection.getDeptInstAuditor().getId()))) {
				annAuditOpinion = anninspection.getDeptInstAuditOpinion();
			}else {
				annAuditOpinion = "";
			}
			annAuditStatus = anninspection.getDeptInstAuditStatus();
			annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
			annAuditPerson = anninspection.getDeptInstAuditorName();
			annAuditDate = anninspection.getDeptInstAuditDate();
			annAuditResult = anninspection.getDeptInstAuditResult();
		}
		else if(vtp == 2) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != anninspection.getUniversityAuditorAgency() && account.getAgency().getId().equals(anninspection.getUniversityAuditorAgency().getId()))) {
				annAuditOpinion = anninspection.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != anninspection.getUniversityAuditor() && account.getOfficer().getId().equals(anninspection.getUniversityAuditor().getId()))) {
				annAuditOpinion = anninspection.getUniversityAuditOpinion();
			}else {
				annAuditOpinion = "";
			}
			annAuditStatus = anninspection.getUniversityAuditStatus();
			annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
			annAuditPerson = anninspection.getUniversityAuditorName();
			annAuditDate = anninspection.getUniversityAuditDate();
			annAuditResult = anninspection.getUniversityAuditResult();
		}
		else if(vtp == 3) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != anninspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(anninspection.getProvinceAuditorAgency().getId()))) {
				annAuditOpinion = anninspection.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != anninspection.getProvinceAuditor() && account.getOfficer().getId().equals(anninspection.getProvinceAuditor().getId()))) {
				annAuditOpinion = anninspection.getProvinceAuditOpinion();
			}else {
				annAuditOpinion = "";
			}
			annAuditStatus = anninspection.getProvinceAuditStatus();
			annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
			annAuditPerson = anninspection.getProvinceAuditorName();
			annAuditDate = anninspection.getProvinceAuditDate();
			annAuditResult = anninspection.getProvinceAuditResult();
		}
		else if(vtp == 4) {
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != anninspection.getFinalAuditorAgency() && (account.getAgency().getId().equals(anninspection.getFinalAuditorAgency().getId())))) {
				annAuditOpinion = anninspection.getFinalAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != anninspection.getFinalAuditor() && account.getOfficer().getId().equals(anninspection.getFinalAuditor().getId()))) {
				annAuditOpinion = anninspection.getFinalAuditOpinion();
			}else {
				annAuditOpinion = "";
			}
			annAuditStatus = anninspection.getFinalAuditStatus();
			annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
			annAuditPerson = anninspection.getFinalAuditorName();
			annAuditDate = anninspection.getFinalAuditDate();
			annAuditResult = anninspection.getFinalAuditResult();
		}
		return SUCCESS;
	}
	
	/**
	 * 准备添加年检审核
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//年检业务状态
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//年检各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		return SUCCESS;
	}
	
	/**
	 * 准备修改年检审核
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//年检业务状态
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//年检各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectAnninspection anninspection = this.projectService.getCurrentAnninspectionByGrantedId(projectid);
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != anninspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(anninspection.getDeptInstAuditorDept().getId()) || (null != anninspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(anninspection.getDeptInstAuditorInst().getId()))))){
				annAuditOpinion = anninspection.getDeptInstAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getDeptInstAuditStatus();
				annAuditResult = anninspection.getDeptInstAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != anninspection.getDeptInstAuditor() && account.getOfficer().getId().equals(anninspection.getDeptInstAuditor().getId())) {
				annAuditOpinion = anninspection.getDeptInstAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getDeptInstAuditStatus();
				annAuditResult = anninspection.getDeptInstAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != anninspection.getUniversityAuditorAgency() && account.getAgency().getId().equals(anninspection.getUniversityAuditorAgency().getId())) {
				annAuditOpinion = anninspection.getUniversityAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getUniversityAuditStatus();
				annAuditResult = anninspection.getUniversityAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != anninspection.getUniversityAuditor() && account.getOfficer().getId().equals(anninspection.getUniversityAuditor().getId())) {
				annAuditOpinion = anninspection.getUniversityAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getUniversityAuditStatus();
				annAuditResult = anninspection.getUniversityAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != anninspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(anninspection.getProvinceAuditorAgency().getId())) {
				annAuditOpinion = anninspection.getProvinceAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getProvinceAuditStatus();
				annAuditResult = anninspection.getProvinceAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != anninspection.getProvinceAuditor() && account.getOfficer().getId().equals(anninspection.getProvinceAuditor().getId())) {
				annAuditOpinion = anninspection.getProvinceAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getProvinceAuditStatus();
				annAuditResult = anninspection.getProvinceAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != anninspection.getFinalAuditorAgency() && account.getAgency().getId().equals(anninspection.getFinalAuditorAgency().getId())) {
				annAuditOpinion = anninspection.getFinalAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getFinalAuditStatus();
				annAuditResult = anninspection.getFinalAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != anninspection.getFinalAuditor() && account.getOfficer().getId().equals(anninspection.getFinalAuditor().getId())) {
				annAuditOpinion = anninspection.getFinalAuditOpinion();
				annAuditOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
				annAuditStatus = anninspection.getFinalAuditStatus();
				annAuditResult = anninspection.getFinalAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加年检审核信息，批量时用id串拼接
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add() {
		AccountType accountType = loginer.getCurrentType();
		String[] gids = projectid.split(", ");
		for(int i = 0; i < gids.length; i++) {
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(gids[i]).trim(), checkGrantedFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//判断年检成果是否已审核完毕
				String annId = this.projectService.getCurrentAnninspectionByGrantedId(gids[i]).getId();
				if(!this.productService.isProductAuditedOfInspection(3, annId)){
					jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_ANN_AUDIT_PRODUCT);
					return INPUT;
				}
			}
			ProjectAnninspection anninspection = this.projectService.getCurrentAnninspectionByGrantedId(gids[i]);
			int annType = anninspection.getStatus();
			if(anninspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && annType == 5) || (accountType.equals(AccountType.PROVINCE) && annType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && annType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && annType==2))){
				if(annAuditOpinion != null){
					annAuditOpinion = ("A"+annAuditOpinion).trim().substring(1);
				}
		    	if((accountType.equals(AccountType.MINISTRY) || annAuditResult == 1) && annAuditOpinionFeedback != null){//部级审核或部级以下审核不同意
		    		anninspection.setFinalAuditOpinionFeedback(("A" + annAuditOpinionFeedback).trim().substring(1));
		        }else{
		        	anninspection.setFinalAuditOpinionFeedback(null);
		        }
				int isSubUni = 0;
	        	if(annType == 3){
	        		isSubUni = projectService.isSubordinateUniversityGranted(projectid);
	        	}
	        	Map auditMap = new HashMap();
	        	AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, annAuditResult, annAuditStatus, annAuditOpinion);
	        	auditMap.put("auditInfo", auditInfo);
	        	auditMap.put("isSubUni", isSubUni);
	        	anninspection.edit(auditMap);
				this.dao.modify(anninspection);
			}
		}
		return SUCCESS;
	}

	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 修改年检审核
	 * @author 肖雅
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * @author 肖雅
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 退回年检申请
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType =loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断年检成果是否已审核完毕
			annId = this.projectService.getCurrentAnninspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(3, annId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_ANN_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectAnninspection anninspection = projectService.getCurrentAnninspectionByGrantedId(projectid);
		int annType = anninspection.getStatus();
		if(anninspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && annType == 5) || (accountType.equals(AccountType.PROVINCE) && annType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && annType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && annType==2))){
			anninspection.back(projectService.isSubordinateUniversityGranted(projectid));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				anninspection.back(0);
			}
			/* 结束 */
			dao.modify(anninspection);
		}
		return SUCCESS;
	}
	
	public void validateBackAudit(){
		this.validateEdit(3);
	}
	/**
	 * 提交年检审核
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断年检成果是否已审核完毕
			annId = this.projectService.getCurrentAnninspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(3, annId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_ANN_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectAnninspection g = projectService.getCurrentAnninspectionByGrantedId(projectid);
		int annType = g.getStatus();
		if(g.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && annType == 5) || (accountType.equals(AccountType.PROVINCE) && annType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && annType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && annType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(projectid));
			g.submit(auditMap);
			dao.modify(g);
		}
		return SUCCESS;
	}
	
	public void validateSubmit(){
		this.validateEdit(4);
	}
	
	
	/**
	 * 业务时限判断
	 * @param type 校验类型：1添加；;3提交
	 */
//	@SuppressWarnings("unchecked")
//	public String isTimeValidate() {
//		int accountType = loginer.getCurrentType();
//		Date date =  new Date();
//		String appId = this.generalService.getApplicationIdByGrantedId(projectid).trim();
//		deadline  = this.generalService.checkIfTimeValidate(accountType, businessType, appId);
//		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){
//			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_TIME_INVALIDATE);
//			return INPUT;
//		}
//		return SUCCESS;
//	}

	
	/**
	 * 用于检验审核
	 * @param type 1：添加	2：修改	3：退回	4：提交
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked" })
	public void validateEdit(int type){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_ANN_AUDIT_SUBMIT_NULL;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			if(this.projectService.getPassAnninspectionByGrantedId(this.projectid).equals(true)){//年检已通过
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_ALREADY);
				info += ProjectInfo.ERROR_ANN_ALREADY;
			}
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0 || this.projectService.getPendingEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过或待处理结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
		}
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//校验业务设置状态
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (annStatus == 0){
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
		if(type == 1 || type == 2){
			if(annAuditStatus!=1 && annAuditStatus!=2 && annAuditStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(annAuditResult!=1 && annAuditResult!=2){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(annAuditOpinion!=null && annAuditOpinion.length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_OPINION_OUT);
				info += ProjectInfo.ERROR_ANN_AUDIT_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}

	public String getAnnAuditOpinion() {
		return annAuditOpinion;
	}
	public void setAnnAuditOpinion(String annAuditOpinion) {
		this.annAuditOpinion = annAuditOpinion;
	}
	public String getAnnAuditOpinionFeedback() {
		return annAuditOpinionFeedback;
	}
	public void setAnnAuditOpinionFeedback(String annAuditOpinionFeedback) {
		this.annAuditOpinionFeedback = annAuditOpinionFeedback;
	}
	public int getAnnAuditStatus() {
		return annAuditStatus;
	}
	public void setAnnAuditStatus(int annAuditStatus) {
		this.annAuditStatus = annAuditStatus;
	}
	public int getAnnAuditResult() {
		return annAuditResult;
	}
	public void setAnnAuditResult(int annAuditResult) {
		this.annAuditResult = annAuditResult;
	}
	public String getAnnAuditPerson() {
		return annAuditPerson;
	}
	public void setAnnAuditPerson(String annAuditPerson) {
		this.annAuditPerson = annAuditPerson;
	}
	public Date getAnnAuditDate() {
		return annAuditDate;
	}
	public void setAnnAuditDate(Date annAuditDate) {
		this.annAuditDate = annAuditDate;
	}
	public String getAnnId() {
		return annId;
	}
	public void setAnnId(String annId) {
		this.annId = annId;
	}
	public int getVtp() {
		return vtp;
	}
	public void setVtp(int vtp) {
		this.vtp = vtp;
	}
}
