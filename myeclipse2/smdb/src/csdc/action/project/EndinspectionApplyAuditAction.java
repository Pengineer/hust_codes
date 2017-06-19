package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectGranted;
import csdc.service.IInstpService;
import csdc.service.IEntrustService;
import csdc.service.IGeneralService;
import csdc.service.IKeyService;
import csdc.service.IPostService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 社科项目结项申请审核管理
 * 定义了子类需要实现的抽象方法并实现了所有项目结项申请审核共用的相关方法
 * @author 余潜玉
 */
public abstract class EndinspectionApplyAuditAction extends ProjectBaseAction {

	private static final long serialVersionUID = 1L;
	
	protected IGeneralService generalService;
	protected IInstpService instpService;
	protected IPostService postService;
	protected IKeyService keyService;
	protected IEntrustService entrustService;
	protected String endId;//结项id
	protected int vtp;//查看审核参数
	protected int isApplyExcellent;//是否申请优秀成果 1:是	0：否
	protected int isApplyNoevaluation;//是否申请免鉴定1:是	0：否
	protected int endInspectionStatus;//结项状态
	protected int endAuditStatus;//审核状态
	protected int endAuditResult;//审核结果
	protected int endNoauditResult;//免鉴定结果
	protected int endExcellentResult;//优秀成果结果
	protected String endAuditorName;//审核人
	protected Date endAuditDate;//审核时间
	protected String endAuditOpinion;//审核意见
	protected String endAuditOpinionFeedback;//审核意见（反馈给项目负责人）
	
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
	 * 查看项目结项申请审核
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectEndinspection endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(vtp == 1) {//高校院系或研究机构
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != endinspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(endinspection.getDeptInstAuditorDept().getId()) || (null != endinspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(endinspection.getDeptInstAuditorInst().getId())))))){
				endAuditOpinion = endinspection.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != endinspection.getDeptInstAuditor() && account.getOfficer().getId().equals(endinspection.getDeptInstAuditor().getId()))) {
				endAuditOpinion = endinspection.getDeptInstAuditOpinion();
			}else {
				endAuditOpinion = "";
			}
			endInspectionStatus = endinspection.getStatus();
			endAuditStatus = endinspection.getDeptInstAuditStatus();
			endAuditResult = endinspection.getDeptInstResultEnd();
			endNoauditResult = endinspection.getDeptInstResultNoevaluation();
			endExcellentResult = endinspection.getDeptInstResultExcellent();
			endAuditDate = endinspection.getDeptInstAuditDate();
			endAuditorName = endinspection.getDeptInstAuditorName();
			endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		}
		else if(vtp == 2) {//地方或部署高校
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != endinspection.getUniversityAuditorAgency() &&  account.getAgency().getId().equals(endinspection.getUniversityAuditorAgency().getId()))) {
				endAuditOpinion = endinspection.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != endinspection.getUniversityAuditor() && account.getOfficer().getId().equals(endinspection.getUniversityAuditor().getId()))) {
				endAuditOpinion = endinspection.getUniversityAuditOpinion();
			}else {
				endAuditOpinion = "";
			}
			endInspectionStatus = endinspection.getStatus();
			endAuditStatus = endinspection.getUniversityAuditStatus();
			endAuditResult = endinspection.getUniversityResultEnd();
			endNoauditResult = endinspection.getUniversityResultNoevaluation();
			endExcellentResult = endinspection.getUniversityResultExcellent();
			endAuditDate = endinspection.getUniversityAuditDate();
			endAuditorName = endinspection.getUniversityAuditorName();
			endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		}
		else if(vtp == 3) {//省厅
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != endinspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(endinspection.getProvinceAuditorAgency().getId()))) {
				endAuditOpinion = endinspection.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != endinspection.getProvinceAuditor() && account.getOfficer().getId().equals(endinspection.getProvinceAuditor().getId()))) {
				endAuditOpinion = endinspection.getProvinceAuditOpinion();
			}else {
				endAuditOpinion = "";
			}
			endInspectionStatus = endinspection.getStatus();
			endAuditStatus = endinspection.getProvinceAuditStatus();
			endAuditResult = endinspection.getProvinceResultEnd();
			endNoauditResult = endinspection.getProvinceResultNoevaluation();
			endExcellentResult = endinspection.getProvinceResultExcellent();
			endAuditDate = endinspection.getProvinceAuditDate();
			endAuditorName = endinspection.getProvinceAuditorName();
			endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		}
		else if(vtp == 4) {//教育部
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != endinspection.getMinistryAuditorAgency() && account.getAgency().getId().equals(endinspection.getMinistryAuditorAgency().getId()))) {
				endAuditOpinion = endinspection.getMinistryAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != endinspection.getMinistryAuditor() && account.getOfficer().getId().equals(endinspection.getMinistryAuditor().getId()))) {
				endAuditOpinion = endinspection.getMinistryAuditOpinion();
			}else {
				endAuditOpinion = "";
			}
			endInspectionStatus = endinspection.getStatus();
			endAuditStatus = endinspection.getMinistryAuditStatus();
			endAuditResult = endinspection.getMinistryResultEnd();
			endNoauditResult = endinspection.getMinistryResultNoevaluation();
			endExcellentResult = endinspection.getMinistryResultExcellent();
			endAuditDate = endinspection.getMinistryAuditDate();
			endAuditorName = endinspection.getMinistryAuditorName();
			endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		}
		return SUCCESS;
	}

	/**
	 * 进入项目结项申请审核添加页面预处理
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getFinalAuditResultExcellent() == 1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getFinalAuditResultNoevaluation() == 1){//审核未通过
			isApplyNoevaluation = 0;
		}
//		return isTimeValidate();
		return SUCCESS;
	}
	
	/**
	 * 添加项目结项申请审核，批量时用id串拼接
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
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//判断结项成果是否已审核完毕
				String endId = this.projectService.getCurrentEndinspectionByGrantedId(gids[i]).getId();
				if(!this.productService.isProductAuditedOfInspection(2, endId)){
					jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_AUDIT_PRODUCT);
					return INPUT;
				}
			}
			ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(gids[i]);
			int endType = endinspection.getStatus();
			if(endinspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && endType == 5) || (accountType.equals(AccountType.PROVINCE) && endType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && endType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && endType==2))){
				//高校院系或研究机构
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
					if(endinspection.getIsApplyExcellent() == 1){
						endinspection.setDeptInstResultExcellent(endExcellentResult);
					}
					if(endinspection.getIsApplyNoevaluation() == 1){
						endinspection.setDeptInstResultNoevaluation(endNoauditResult);
					}
				}
				//地方或部署高校
				else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
					if(endinspection.getIsApplyExcellent() == 1){
						endinspection.setUniversityResultExcellent(endExcellentResult);
					}
					if(endinspection.getIsApplyNoevaluation() == 1){
						endinspection.setUniversityResultNoevaluation(endNoauditResult);
					}
				}
				//省厅
				else if(accountType.equals(AccountType.PROVINCE)) {
					if(endinspection.getIsApplyExcellent() == 1){
						endinspection.setProvinceResultExcellent(endExcellentResult);
					}
					if(endinspection.getIsApplyNoevaluation() == 1){
						endinspection.setProvinceResultNoevaluation(endNoauditResult);
					}
				}
				//教育部
				else if(accountType.equals(AccountType.MINISTRY)) {
					if(endinspection.getIsApplyExcellent() == 1){
						endinspection.setMinistryResultExcellent(endExcellentResult);
					}
					if(endinspection.getIsApplyNoevaluation() == 1){
						endinspection.setMinistryResultNoevaluation(endNoauditResult);
					}
				}
				if(endAuditOpinion != null){
					endAuditOpinion = ("A"+endAuditOpinion).trim().substring(1);
				}
				if(endAuditResult == 1 && endAuditOpinionFeedback != null){//审核不同意
					endinspection.setFinalAuditOpinionFeedback(("A" + endAuditOpinionFeedback).trim().substring(1));
		        }else{
		        	endinspection.setFinalAuditOpinionFeedback(null);
		        }
				Map auditMap = new HashMap();
				AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, endAuditResult, endAuditStatus, endAuditOpinion);
				auditMap.put("auditInfo", auditInfo);
				auditMap.put("isSubUni",  this.projectService.isSubordinateUniversityGranted(projectid));
				endinspection.edit(auditMap);
				//教育部审核提交时判断是否非导入数据，评审是否已提交或者是否申请免鉴定且审核同意
				if(endinspection.getStatus() == 6 && endinspection.getIsImported() != 1 && (endinspection.getReviewStatus() == 3 ||(endinspection.getIsApplyNoevaluation() == 1 && endNoauditResult == 2))){
					endinspection.setStatus(endinspection.getStatus() + 1);//结项状态跳一级
				}
				this.dao.modify(endinspection);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加审核校验
	 */
	public void validateAdd(){
		this.validateEdit();
		if(endAuditStatus != 1 && endAuditStatus != 2 && endAuditStatus != 3){
			this.addFieldError("endAuditStatus", GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if(endAuditResult != 1 && endAuditResult != 2){
			this.addFieldError("endAuditResult", GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if(isApplyExcellent == 1 && endExcellentResult != 1 && endExcellentResult != 2){
			this.addFieldError("endExcellentResult", GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if(isApplyNoevaluation == 1 &&  endNoauditResult != 1 && endNoauditResult != 2){
			this.addFieldError("endNoauditResult", GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if(endAuditOpinion != null && endAuditOpinion.length() > 2000){
			this.addFieldError("endAuditOpinion", ProjectInfo.ERROR_END_AUDIT_OPINION_OUT);
		}
		if(endAuditOpinionFeedback != null && endAuditOpinionFeedback.length() > 200){
			this.addFieldError("endAuditOpinionFeedback", ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT);
		}
	}
	
	/**
	 * 进入项目结项申请审核修改页面预处理
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getFinalAuditResultExcellent() == 1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getFinalAuditResultNoevaluation() == 1){//审核未通过
			isApplyNoevaluation = 0;
		}
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != endinspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(endinspection.getDeptInstAuditorDept().getId()) || (null != endinspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(endinspection.getDeptInstAuditorInst().getId()))))){
				endAuditOpinion = endinspection.getDeptInstAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getDeptInstAuditStatus();
				endAuditResult = endinspection.getDeptInstResultEnd();
				endNoauditResult = endinspection.getDeptInstResultNoevaluation();
				endExcellentResult = endinspection.getDeptInstResultExcellent();
			}else if (account.getIsPrincipal() == 0 && null != endinspection.getDeptInstAuditor() && account.getOfficer().getId().equals(endinspection.getDeptInstAuditor().getId())) {
				endAuditOpinion = endinspection.getDeptInstAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getDeptInstAuditStatus();
				endAuditResult = endinspection.getDeptInstResultEnd();
				endNoauditResult = endinspection.getDeptInstResultNoevaluation();
				endExcellentResult = endinspection.getDeptInstResultExcellent();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != endinspection.getUniversityAuditorAgency() && account.getAgency().getId().equals(endinspection.getUniversityAuditorAgency().getId())) {
				endAuditOpinion = endinspection.getUniversityAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getUniversityAuditStatus();
				endAuditResult = endinspection.getUniversityResultEnd();
				endNoauditResult = endinspection.getUniversityResultNoevaluation();
				endExcellentResult = endinspection.getUniversityResultExcellent();
			}else if (account.getIsPrincipal() == 0 && null != endinspection.getUniversityAuditor() && account.getOfficer().getId().equals(endinspection.getUniversityAuditor().getId())) {
				endAuditOpinion = endinspection.getUniversityAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getUniversityAuditStatus();
				endAuditResult = endinspection.getUniversityResultEnd();
				endNoauditResult = endinspection.getUniversityResultNoevaluation();
				endExcellentResult = endinspection.getUniversityResultExcellent();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != endinspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(endinspection.getProvinceAuditorAgency().getId())) {
				endAuditOpinion = endinspection.getProvinceAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getProvinceAuditStatus();
				endAuditResult = endinspection.getProvinceResultEnd();
				endNoauditResult = endinspection.getProvinceResultNoevaluation();
				endExcellentResult = endinspection.getProvinceResultExcellent();
			}else if (account.getIsPrincipal() == 0 && null != endinspection.getProvinceAuditor() && account.getOfficer().getId().equals(endinspection.getProvinceAuditor().getId())) {
				endAuditOpinion = endinspection.getProvinceAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getProvinceAuditStatus();
				endAuditResult = endinspection.getProvinceResultEnd();
				endNoauditResult = endinspection.getProvinceResultNoevaluation();
				endExcellentResult = endinspection.getProvinceResultExcellent();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != endinspection.getMinistryAuditorAgency() && account.getAgency().getId().equals(endinspection.getMinistryAuditorAgency().getId())) {
				endAuditOpinion = endinspection.getMinistryAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getMinistryAuditStatus();
				endAuditResult = endinspection.getMinistryResultEnd();
				endNoauditResult = endinspection.getMinistryResultNoevaluation();
				endExcellentResult = endinspection.getMinistryResultExcellent();
			}else if (account.getIsPrincipal() == 0 && null != endinspection.getMinistryAuditor() && account.getOfficer().getId().equals(endinspection.getMinistryAuditor().getId())) {
				endAuditOpinion = endinspection.getMinistryAuditOpinion();
				endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
				endInspectionStatus = endinspection.getStatus();
				endAuditStatus = endinspection.getMinistryAuditStatus();
				endAuditResult = endinspection.getMinistryResultEnd();
				endNoauditResult = endinspection.getMinistryResultNoevaluation();
				endExcellentResult = endinspection.getMinistryResultExcellent();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			
		}
//		return isTimeValidate();
		return SUCCESS;
	}

	/**
	 * 修改项目结项申请审核
	 * @author 余潜玉
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改审核校验
	 */
	public void validateModify(){
		this.validateAdd();
	}
	
	/**
	 * 退回项目结项申请
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//判断结项成果是否已审核完毕
			String endId = this.projectService.getCurrentEndinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(2, endId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		int endType = endinspection.getStatus();
		if(endinspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && endType == 5) || (accountType.equals(AccountType.PROVINCE) && endType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && endType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && endType==2))){
			endinspection.back(projectService.isSubordinateUniversityGranted(projectid));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				endinspection.back(0);
			}
			/* 结束 */
			dao.modify(endinspection);
		}
		return SUCCESS;
	}
	
	/**
	 * 退回申请校验
	 */
	public void validateBackAudit(){
		this.validateEdit();
	}
	
	/**
	 * 提交项目结项申请审核
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//结项业务状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//结项各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//判断结项成果是否已审核完毕
			String endId = this.projectService.getCurrentEndinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(2, endId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		int endType = endinspection.getStatus();
		if(endinspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && endType == 5) || (accountType.equals(AccountType.PROVINCE) && endType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && endType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && endType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(projectid));
			endinspection.submit(auditMap);
			//教育部审核提交时判断是否非导入数据，评审是否已提交或者是否申请免鉴定且审核同意
			if(endinspection.getStatus() == 6 && endinspection.getIsImported() != 1 && (endinspection.getReviewStatus() == 3 ||(endinspection.getIsApplyNoevaluation() == 1 && endinspection.getMinistryResultNoevaluation() == 2))){
				endinspection.setStatus(endinspection.getStatus() + 1);//结项状态跳一级
			}
			dao.modify(endinspection);
		}
		return SUCCESS;
	}
	
	/**
	 * 提交审核校验
	 */
	public void validateSubmit(){
		this.validateEdit();
	}
	
	/**
	 * 审核校验公用方法
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL;
		}else{
			if (projectid == null || projectid.isEmpty()) {//项目id不得为空
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
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
			}
			String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
			//校验业务设置状态
			endStatus = this.projectService.getBusinessStatus(businessType(), appId);
			if (endStatus == 0){
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
			if(!this.projectService.getPassEndinspectionByGrantedId(projectid).isEmpty()){//有已通过结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_ALREADY);
				info += ProjectInfo.ERROR_END_ALREADY;
			}
			if(!"post".equals(projectType()) && !"entrust".equals(projectType())){
				int grantedYear = this.projectService.getGrantedYear(projectid);
				int endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
				if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
					info += ProjectInfo.ERROR_END_CANNOT;
				}
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public int getVtp() {
		return vtp;
	}
	public void setVtp(int vtp) {
		this.vtp = vtp;
	}
	public int getEndInspectionStatus() {
		return endInspectionStatus;
	}
	public void setEndInspectionStatus(int endInspectionStatus) {
		this.endInspectionStatus = endInspectionStatus;
	}
	public int getEndAuditStatus() {
		return endAuditStatus;
	}
	public void setEndAuditStatus(int endAuditStatus) {
		this.endAuditStatus = endAuditStatus;
	}

	public int getEndAuditResult() {
		return endAuditResult;
	}

	public void setEndAuditResult(int endAuditResult) {
		this.endAuditResult = endAuditResult;
	}

	public int getEndNoauditResult() {
		return endNoauditResult;
	}
	public void setEndNoauditResult(int endNoauditResult) {
		this.endNoauditResult = endNoauditResult;
	}

	public int getEndExcellentResult() {
		return endExcellentResult;
	}

	public void setEndExcellentResult(int endExcellentResult) {
		this.endExcellentResult = endExcellentResult;
	}

	public String getEndAuditorName() {
		return endAuditorName;
	}
	public void setEndAuditorName(String endAuditorName) {
		this.endAuditorName = endAuditorName;
	}
	public Date getEndAuditDate() {
		return endAuditDate;
	}
	public void setEndAuditDate(Date endAuditDate) {
		this.endAuditDate = endAuditDate;
	}
	public String getEndAuditOpinion() {
		return endAuditOpinion;
	}
	public void setEndAuditOpinion(String endAuditOpinion) {
		this.endAuditOpinion = endAuditOpinion;
	}

	public int getIsApplyExcellent() {
		return isApplyExcellent;
	}

	public int getIsApplyNoevaluation() {
		return isApplyNoevaluation;
	}

	public void setIsApplyExcellent(int isApplyExcellent) {
		this.isApplyExcellent = isApplyExcellent;
	}

	public void setIsApplyNoevaluation(int isApplyNoevaluation) {
		this.isApplyNoevaluation = isApplyNoevaluation;
	}

	public String getEndAuditOpinionFeedback() {
		return endAuditOpinionFeedback;
	}

	public void setEndAuditOpinionFeedback(String endAuditOpinionFeedback) {
		this.endAuditOpinionFeedback = endAuditOpinionFeedback;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}

	public IInstpService getInstpService() {
		return instpService;
	}

	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}

	public IPostService getPostService() {
		return postService;
	}

	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
	public IKeyService getKeyService() {
		return keyService;
	}
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}
	public IEntrustService getEntrustService() {
		return entrustService;
	}
	public void setEntrustService(IEntrustService entrustService) {
		this.entrustService = entrustService;
	}
	

}
