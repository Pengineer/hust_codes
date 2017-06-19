package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectGranted;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目立项审核父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目立项审核共用的相关方法
 * @author yangfq
 */
public abstract class ApplicationGrantedAuditAction extends ProjectBaseAction{


	private static final long serialVersionUID = 1L;
	
	protected int graAuditStatus;//立项状态
	protected String graAuditOpinion;//立项意见
	protected String graAuditOpinionFeedback;//立项意见（反馈给项目负责人）
	protected int graAuditResult;//立项结果
	protected String grantedId;//立项id
	protected String graAuditPerson;//立项审核人
	protected Date graAuditDate;//立项审核时间
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
	 * 查看项目立项审核
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(grantedId).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, grantedId);
		if(vtp == 1) {//高校院系或研究机构
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != granted.getDeptInstAuditorDept() && account.getDepartment().getId().equals(granted.getDeptInstAuditorDept().getId()) || (null != granted.getDeptInstAuditorInst() && account.getInstitute().getId().equals(granted.getDeptInstAuditorInst().getId())))))){
				graAuditOpinion = granted.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != granted.getDeptInstAuditor() && account.getOfficer().getId().equals(granted.getDeptInstAuditor().getId()))) {
				graAuditOpinion = granted.getDeptInstAuditOpinion();
			}else {
				graAuditOpinion = "";
			}
			graAuditStatus = granted.getDeptInstAuditStatus();
			graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
			graAuditPerson = granted.getDeptInstAuditorName();
			graAuditDate = granted.getDeptInstAuditDate();
			graAuditResult = granted.getDeptInstAuditResult();
		}
		else if(vtp == 2) {//地方或部署高校
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != granted.getUniversityAuditorAgency() && account.getAgency().getId().equals(granted.getUniversityAuditorAgency().getId()))) {
				graAuditOpinion = granted.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != granted.getUniversityAuditor() && account.getOfficer().getId().equals(granted.getUniversityAuditor().getId()))) {
				graAuditOpinion = granted.getUniversityAuditOpinion();
			}else {
				graAuditOpinion = "";
			}
			graAuditStatus = granted.getUniversityAuditStatus();
			graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
			graAuditPerson = granted.getUniversityAuditorName();
			graAuditDate = granted.getUniversityAuditDate();
			graAuditResult = granted.getUniversityAuditResult();
		}
		else if(vtp == 3) {//省厅
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != granted.getProvinceAuditorAgency() && account.getAgency().getId().equals(granted.getProvinceAuditorAgency().getId()))) {
				graAuditOpinion = granted.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != granted.getProvinceAuditor() && account.getOfficer().getId().equals(granted.getProvinceAuditor().getId()))) {
				graAuditOpinion = granted.getProvinceAuditOpinion();
			}else {
				graAuditOpinion = "";
			}
			graAuditStatus = granted.getProvinceAuditStatus();
			graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
			graAuditPerson = granted.getProvinceAuditorName();
			graAuditDate = granted.getProvinceAuditDate();
			graAuditResult = granted.getProvinceAuditResult();
		}
		else if(vtp == 4) {//教育部
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != granted.getFinalAuditorAgency() && (account.getAgency().getId().equals(granted.getFinalAuditorAgency().getId())))) {
				graAuditOpinion = granted.getFinalAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != granted.getFinalAuditor() && account.getOfficer().getId().equals(granted.getFinalAuditor().getId()))) {
				graAuditOpinion = granted.getFinalAuditOpinion();
			}else {
				graAuditOpinion = "";
			}
			graAuditStatus = granted.getFinalAuditStatus();
			graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
			graAuditPerson = granted.getFinalAuditorName();
			graAuditDate = granted.getFinalAuditDate();
			graAuditResult = granted.getFinalAuditResult();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入项目立项审核添加页面预处理
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//立项业务状态
		graStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//立项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		return SUCCESS;
	}
	
	/**
	 * 添加项目立项审核，批量时用id串拼接
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
			}
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, gids[i]);
			int graType = granted.getAuditstatus();
			if(granted.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && graType == 5) || (accountType.equals(AccountType.PROVINCE) && graType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && graType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && graType==2))){
				if(graAuditOpinion != null){
					graAuditOpinion = ("A"+graAuditOpinion).trim().substring(1);
				}
		    	if((accountType.equals(AccountType.MINISTRY) || graAuditResult == 1) && graAuditOpinionFeedback != null){//部级审核或部级以下审核不同意
		    		granted.setFinalAuditOpinionFeedback(("A" + graAuditOpinionFeedback).trim().substring(1));
		        }else{
		        	granted.setFinalAuditOpinionFeedback(null);
		        }
				int isSubUni = 0;
	        	if(graType == 3){
	        		isSubUni = projectService.isSubordinateUniversityGranted(projectid);
	        	}
	        	Map auditMap = new HashMap();
	        	AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, graAuditResult, graAuditStatus, graAuditOpinion);
	        	auditMap.put("auditInfo", auditInfo);
	        	auditMap.put("isSubUni", isSubUni);
	        	granted.edit(auditMap);
				this.dao.modify(granted);
			}
		}
		return SUCCESS;
	}

	/**
	 * 添加立项审核校验
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 进入项目立项审核修改页面预处理
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//立项业务状态
		graStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//立项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != granted.getDeptInstAuditorDept() && account.getDepartment().getId().equals(granted.getDeptInstAuditorDept().getId()) || (null != granted.getDeptInstAuditorInst() && account.getInstitute().getId().equals(granted.getDeptInstAuditorInst().getId()))))){
				graAuditOpinion = granted.getDeptInstAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getDeptInstAuditStatus();
				graAuditResult = granted.getDeptInstAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != granted.getDeptInstAuditor() && account.getOfficer().equals(granted.getDeptInstAuditor().getId())) {
				graAuditOpinion = granted.getDeptInstAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getDeptInstAuditStatus();
				graAuditResult = granted.getDeptInstAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != granted.getUniversityAuditorAgency() && account.getAgency().getId().equals(granted.getUniversityAuditorAgency().getId())) {
				graAuditOpinion = granted.getUniversityAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getUniversityAuditStatus();
				graAuditResult = granted.getUniversityAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != granted.getUniversityAuditor() && account.getOfficer().getId().equals(granted.getUniversityAuditor().getId())) {
				graAuditOpinion = granted.getUniversityAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getUniversityAuditStatus();
				graAuditResult = granted.getUniversityAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != granted.getProvinceAuditorAgency() && account.getAgency().getId().equals(granted.getProvinceAuditorAgency().getId())) {
				graAuditOpinion = granted.getProvinceAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getProvinceAuditStatus();
				graAuditResult = granted.getProvinceAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != granted.getProvinceAuditor() && account.getOfficer().getId().equals(granted.getProvinceAuditor().getId())) {
				graAuditOpinion = granted.getProvinceAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getProvinceAuditStatus();
				graAuditResult = granted.getProvinceAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != granted.getFinalAuditorAgency() && account.getAgency().getId().equals(granted.getFinalAuditorAgency().getId())) {
				graAuditOpinion = granted.getFinalAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getFinalAuditStatus();
				graAuditResult = granted.getFinalAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != granted.getFinalAuditor() && account.getOfficer().getId().equals(granted.getFinalAuditor().getId())) {
				graAuditOpinion = granted.getFinalAuditOpinion();
				graAuditOpinionFeedback = granted.getFinalAuditOpinionFeedback();
				graAuditStatus = granted.getFinalAuditStatus();
				graAuditResult = granted.getFinalAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			
		}
		return SUCCESS;
	}
	
	/**
	 * 修改项目立项审核
	 * @author yangfq
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改立项审核校验
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 退回项目立项申请
	 * @author yangfq
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType =loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断立项成果是否已审核完毕
			String midId = this.projectService.getCurrentMidinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(1, midId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
		int midType = granted.getStatus();
		if(granted.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && midType == 5) || (accountType.equals(AccountType.PROVINCE) && midType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && midType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && midType==2))){
			granted.back(projectService.isSubordinateUniversityGranted(projectid));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				granted.back(0);
			}
			/* 结束 */
			dao.modify(granted);
		}
		return SUCCESS;
	}
	
	/**
	 * 退回项目立项申请校验
	 */
	public void validateBackAudit(){
		this.validateEdit(3);
	}
	
	/**
	 * 提交项目立项审核
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断立项成果是否已审核完毕
			String midId = this.projectService.getCurrentMidinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(1, midId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectGranted g = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
		int graType = g.getStatus();
		if(g.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && graType == 5) || (accountType.equals(AccountType.PROVINCE) && graType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && graType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && graType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(projectid));
			g.submit(auditMap);
			dao.modify(g);
		}
		return SUCCESS;
	}
	
	/**
	 * 提交立项审核校验
	 */
	public void validateSubmit(){
		this.validateEdit(4);
	}
	
	/**
	 * 用于检验审核
	 * @param type 1：添加	2：修改	3：退回	4：提交
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked" })
	public void validateEdit(int type){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_MID_AUDIT_SUBMIT_NULL;
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
			if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0){//立项已通过
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_ALREADY);
				info += ProjectInfo.ERROR_MID_ALREADY;
			}
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0 || this.projectService.getPendingEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过或待处理结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
		}
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//校验业务设置状态
		graStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (graStatus == 0){
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
			if(graAuditStatus!=1 && graAuditStatus!=2 && graAuditStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(graAuditResult!=1 && graAuditResult!=2){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(graAuditOpinion!=null && graAuditOpinion.length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT);
				info += ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	public int getGraAuditStatus() {
		return graAuditStatus;
	}
	public void setGraAuditStatus(int graAuditStatus) {
		this.graAuditStatus = graAuditStatus;
	}
	public String getGraAuditOpinion() {
		return graAuditOpinion;
	}
	public void setGraAuditOpinion(String graAuditOpinion) {
		this.graAuditOpinion = graAuditOpinion;
	}
	public String getGraAuditOpinionFeedback() {
		return graAuditOpinionFeedback;
	}
	public void setGraAuditOpinionFeedback(String graAuditOpinionFeedback) {
		this.graAuditOpinionFeedback = graAuditOpinionFeedback;
	}
	public int getGraAuditResult() {
		return graAuditResult;
	}
	public void setGraAuditResult(int graAuditResult) {
		this.graAuditResult = graAuditResult;
	}
	public String getGrantedId() {
		return grantedId;
	}
	public void setGrantedId(String grantedId) {
		this.grantedId = grantedId;
	}
	public String getGraAuditPerson() {
		return graAuditPerson;
	}
	public void setGraAuditPerson(String graAuditPerson) {
		this.graAuditPerson = graAuditPerson;
	}
	public Date getGraAuditDate() {
		return graAuditDate;
	}
	public void setGraAuditDate(Date graAuditDate) {
		this.graAuditDate = graAuditDate;
	}
	public int getVtp() {
		return vtp;
	}
	public void setVtp(int vtp) {
		this.vtp = vtp;
	}



}
