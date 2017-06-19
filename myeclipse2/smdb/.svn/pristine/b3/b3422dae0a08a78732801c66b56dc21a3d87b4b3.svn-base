package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.project.ProjectBaseAction;
import csdc.bean.Account;
import csdc.bean.ProjectApplication;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目申请审核父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目申报审核共用的相关方法
 * @author 肖雅
 */
public abstract class ApplicationApplyAuditAction extends ProjectBaseAction {

	private static final long serialVersionUID = 1L;
	
	protected int appAuditStatus;//项目申报状态
	protected String appAuditOpinion;//项目申报意见
	protected String appAuditOpinionFeedback;//申报意见（反馈给项目负责人）
	protected int appAuditResult;//项目申报结果
	protected String entityId;//申报id
	protected String appAuditPerson;//项目申报审核人
	protected Date appAuditDate;//项目申报审核时间
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
	 * 查看项目申报审核详情
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		if(vtp == 1) {//高校院系或研究机构
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != application.getDeptInstAuditorDept() && account.getDepartment().getId().equals(application.getDeptInstAuditorDept().getId()) || (null != application.getDeptInstAuditorInst() && account.getInstitute().getId().equals(application.getDeptInstAuditorInst().getId())))))){
				appAuditOpinion = application.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != application.getDeptInstAuditor() && account.getOfficer().getId().equals(application.getDeptInstAuditor().getId()))) {
				appAuditOpinion = application.getDeptInstAuditOpinion();
			}else {
				appAuditOpinion = "";
			}
			appAuditStatus = application.getDeptInstAuditStatus();
			appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
			appAuditPerson = application.getDeptInstAuditorName();
			appAuditDate = application.getDeptInstAuditDate();
			appAuditResult = application.getDeptInstAuditResult();
		}
		else if(vtp == 2) {//地方或部署高校
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != application.getUniversityAuditorAgency() && account.getAgency().getId().equals(application.getUniversityAuditorAgency().getId()))) {
				appAuditOpinion = application.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != application.getUniversityAuditor() && account.getOfficer().getId().equals(application.getUniversityAuditor().getId()))) {
				appAuditOpinion = application.getUniversityAuditOpinion();
			}else {
				appAuditOpinion = "";
			}
			appAuditStatus = application.getUniversityAuditStatus();
			appAuditPerson = application.getUniversityAuditorName();
			appAuditDate = application.getUniversityAuditDate();
			appAuditResult = application.getUniversityAuditResult();
		}
		else if(vtp == 3) {//省厅
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != application.getProvinceAuditorAgency() && account.getAgency().getId().equals(application.getProvinceAuditorAgency().getId()))) {
				appAuditOpinion = application.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != application.getProvinceAuditor() && account.getOfficer().getId().equals(application.getProvinceAuditor().getId()))) {
				appAuditOpinion = application.getProvinceAuditOpinion();
			}else {
				appAuditOpinion = "";
			}
			appAuditStatus = application.getProvinceAuditStatus();
			appAuditPerson = application.getProvinceAuditorName();
			appAuditDate = application.getProvinceAuditDate();
			appAuditResult = application.getProvinceAuditResult();
		}
		else if(vtp == 4) {//教育部
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != application.getMinistryAuditorAgency() && (account.getAgency().getId().equals(application.getMinistryAuditorAgency().getId())))) {
				appAuditOpinion = application.getMinistryAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != application.getMinistryAuditor() && account.getOfficer().getId().equals(application.getMinistryAuditor().getId()))) {
				appAuditOpinion = application.getMinistryAuditOpinion();
			}else {
				appAuditOpinion = "";
			}
			appAuditStatus = application.getMinistryAuditStatus();
			appAuditPerson = application.getMinistryAuditorName();
			appAuditDate = application.getMinistryAuditDate();
			appAuditResult = application.getMinistryAuditResult();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入添加审核页面预处理
	 */
	@SuppressWarnings("unchecked")
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		//申报业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申报各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType());
//		Date date = new Date();
//		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
//			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_TIME_INVALIDATE);
//			return INPUT;
//		}
		return SUCCESS;
	}
	
	/**
	 * 添加审核意见，申报状态，批量时用id串拼接
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add() {
		AccountType accountType = loginer.getCurrentType();
		String[] appIds = entityId.split(", ");
		for(int i = 0; i < appIds.length; i++) {
			if(!this.projectService.checkIfUnderControl(loginer, appIds[i], checkApplicationFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
			ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, appIds[i]);
			int appType = application.getStatus();
			if(application.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && appType == 5) || (accountType.equals(AccountType.PROVINCE) && appType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && appType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && appType==2))){
				if(appAuditOpinion != null){
					appAuditOpinion = ("A" + appAuditOpinion).trim().substring(1);
				}else{
					appAuditOpinion = null;
				}
		    	if((accountType.equals(AccountType.MINISTRY) || appAuditResult == 1) && appAuditOpinionFeedback != null){//部级审核或部级以下审核不同意
		    		application.setFinalAuditOpinionFeedback(("A" + appAuditOpinionFeedback).trim().substring(1));
		        }else{
		        	application.setFinalAuditOpinionFeedback(null);
		        }
				int isSubUni = 0;
	        	if(appType == 3){
	        		isSubUni = projectService.isSubordinateUniversityApplication(entityId);
	        	}
	        	Map auditMap = new HashMap();
	        	AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, appAuditResult, appAuditStatus, appAuditOpinion);
	        	auditMap.put("auditInfo", auditInfo);
	        	auditMap.put("isSubUni", isSubUni);
	        	application.edit(auditMap);
	        	//教育部审核提交时判断是否非导入数据，评审是否已提交
				if(application.getStatus() == 6 && application.getIsImported() != 1 && application.getReviewStatus() == 3 ){
					application.setStatus(application.getStatus() + 1);//申请状态跳一级
				}
				this.dao.modify(application);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加审核校验
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 进入修改审核页面预处理
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		//申报业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申报各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType());
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != application.getDeptInstAuditorDept() && account.getDepartment().getId().equals(application.getDeptInstAuditorDept().getId()) || (null != application.getDeptInstAuditorInst() && account.getInstitute().getId().equals(application.getDeptInstAuditorInst().getId()))))){
				appAuditOpinion = application.getDeptInstAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getDeptInstAuditStatus();
				appAuditResult = application.getDeptInstAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != application.getDeptInstAuditor() && account.getOfficer().getId().equals(application.getDeptInstAuditor().getId())) {
				appAuditOpinion = application.getDeptInstAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getDeptInstAuditStatus();
				appAuditResult = application.getDeptInstAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != application.getUniversityAuditorAgency() && account.getAgency().getId().equals(application.getUniversityAuditorAgency().getId())) {
				appAuditOpinion = application.getUniversityAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getUniversityAuditStatus();
				appAuditResult = application.getUniversityAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != application.getUniversityAuditor() && account.getOfficer().getId().equals(application.getUniversityAuditor().getId())) {
				appAuditOpinion = application.getUniversityAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getUniversityAuditStatus();
				appAuditResult = application.getUniversityAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != application.getProvinceAuditorAgency() && account.getAgency().getId().equals(application.getProvinceAuditorAgency().getId())) {
				appAuditOpinion = application.getProvinceAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getProvinceAuditStatus();
				appAuditResult = application.getProvinceAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != application.getProvinceAuditor() && account.getOfficer().getId().equals(application.getProvinceAuditor().getId())) {
				appAuditOpinion = application.getProvinceAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getProvinceAuditStatus();
				appAuditResult = application.getProvinceAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != application.getMinistryAuditorAgency() && account.getAgency().getId().equals(application.getMinistryAuditorAgency().getId())) {
				appAuditOpinion = application.getMinistryAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getMinistryAuditStatus();
				appAuditResult = application.getMinistryAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != application.getMinistryAuditor() && account.getOfficer().getId().equals(application.getMinistryAuditor().getId())) {
				appAuditOpinion = application.getMinistryAuditOpinion();
				appAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
				appAuditStatus = application.getMinistryAuditStatus();
				appAuditResult = application.getMinistryAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改申报审核
	 * @author 肖雅
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改审核校验
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 退回申请
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType = loginer.getCurrentType();
		//申报业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申报各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType());		
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		int appType = application.getStatus();
		if(application.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && appType == 5) || (accountType.equals(AccountType.PROVINCE) && appType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && appType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && appType==2))){
			application.back(projectService.isSubordinateUniversityApplication(entityId));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				application.back(0);
			}
			/* 结束 */
			dao.modify(application);
		}
		return SUCCESS;
	}
	
	/**
	 * 退回申请校验
	 */
	public void validateBackAudit(){
		this.validateEdit(3);
	}
	
	/**
	 * 提交审核
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		//申报业务状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		//申报各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType());
		if(!this.projectService.checkIfUnderControl(loginer, entityId, checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		int appType = application.getStatus();
		if(application.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && appType == 5) || (accountType.equals(AccountType.PROVINCE) && appType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && appType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && appType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityApplication(entityId));
			application.submit(auditMap);
			//教育部审核提交时判断是否非导入数据，评审是否已提交
			if(application.getStatus() == 6 && application.getIsImported() != 1 && application.getReviewStatus() == 3){
				application.setStatus(application.getStatus() + 1);//申请状态跳一级
			}
			dao.modify(application);
		}
		return SUCCESS;
	}
	
	/**
	 * 提交审核校验
	 */
	public void validateSubmit(){
		this.validateEdit(4);
	}
	
	/**
	 * 用于检验审核
	 * @param type 1：添加	2：修改	3：退回	4：提交
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked" })
	public void validateEdit(int type){
		String info ="";
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_APP_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_APP_AUDIT_SUBMIT_NULL;
		}else{
			if (entityId.contains(", ")) {
				entityId = entityId.substring(0, entityId.indexOf(", "));
			}
			if(null != this.projectService.getGrantedIdByAppId(entityId) && !this.projectService.getGrantedIdByAppId(entityId).isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_GRANTED_EXIST);
				info += ProjectInfo.ERROR_GRANTED_EXIST;
			}
		}
		//校验业务设置状态
		appStatus = this.projectService.getBusinessStatus(businessType());
		if (appStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType());
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
		}
		if(type == 1 || type == 2){
			if(appAuditStatus!=1 && appAuditStatus!=2 && appAuditStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(appAuditResult!=1 && appAuditResult!=2){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(appAuditOpinion!=null && appAuditOpinion.length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT);
				info += ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	

	public int getAppAuditStatus() {
		return appAuditStatus;
	}

	public void setAppAuditStatus(int appAuditStatus) {
		this.appAuditStatus = appAuditStatus;
	}

	public String getAppAuditOpinion() {
		return appAuditOpinion;
	}

	public void setAppAuditOpinion(String appAuditOpinion) {
		this.appAuditOpinion = appAuditOpinion;
	}

	public String getAppAuditOpinionFeedback() {
		return appAuditOpinionFeedback;
	}

	public void setAppAuditOpinionFeedback(String appAuditOpinionFeedback){
		this.appAuditOpinionFeedback = appAuditOpinionFeedback;
	}

	public int getAppAuditResult() {
		return appAuditResult;
	}
	public void setAppAuditResult(int appAuditResult) {
		this.appAuditResult = appAuditResult;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getAppAuditPerson() {
		return appAuditPerson;
	}
	public void setAppAuditPerson(String appAuditPerson) {
		this.appAuditPerson = appAuditPerson;
	}
	public Date getAppAuditDate() {
		return appAuditDate;
	}
	public void setAppAuditDate(Date appAuditDate) {
		this.appAuditDate = appAuditDate;
	}

	public int getVtp() {
		return vtp;
	}

	public void setVtp(int vtp) {
		this.vtp = vtp;
	}
}
