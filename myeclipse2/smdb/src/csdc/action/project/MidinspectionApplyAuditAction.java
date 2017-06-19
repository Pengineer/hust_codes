package csdc.action.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.InstpFunding;
import csdc.bean.KeyFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 项目中检申请审核父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目中检审核共用的相关方法
 * @author 余潜玉
 */
public abstract class MidinspectionApplyAuditAction extends ProjectBaseAction{

	private static final long serialVersionUID = 1L;
	
	protected int midAuditStatus;//中检状态
	protected String midAuditOpinion;//中检意见
	protected String midAuditOpinionFeedback;//中检意见（反馈给项目负责人）
	protected int midAuditResult;//中检结果
	protected String midId;//中检id
	protected String midAuditPerson;//中检审核人
	protected Date midAuditDate;//中检审核时间
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
	 * 查看项目中检审核
	 */
	public String view() {
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByMidId(midId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectMidinspection midinspection = (ProjectMidinspection) this.dao.query(ProjectMidinspection.class, midId);
		if(vtp == 1) {//高校院系或研究机构
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && ((null != midinspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(midinspection.getDeptInstAuditorDept().getId()) || (null != midinspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(midinspection.getDeptInstAuditorInst().getId())))))){
				midAuditOpinion = midinspection.getDeptInstAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != midinspection.getDeptInstAuditor() && account.getOfficer().getId().equals(midinspection.getDeptInstAuditor().getId()))) {
				midAuditOpinion = midinspection.getDeptInstAuditOpinion();
			}else {
				midAuditOpinion = "";
			}
			midAuditStatus = midinspection.getDeptInstAuditStatus();
			midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
			midAuditPerson = midinspection.getDeptInstAuditorName();
			midAuditDate = midinspection.getDeptInstAuditDate();
			midAuditResult = midinspection.getDeptInstAuditResult();
		}
		else if(vtp == 2) {//地方或部署高校
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != midinspection.getUniversityAuditorAgency() && account.getAgency().getId().equals(midinspection.getUniversityAuditorAgency().getId()))) {
				midAuditOpinion = midinspection.getUniversityAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != midinspection.getUniversityAuditor() && account.getOfficer().getId().equals(midinspection.getUniversityAuditor().getId()))) {
				midAuditOpinion = midinspection.getUniversityAuditOpinion();
			}else {
				midAuditOpinion = "";
			}
			midAuditStatus = midinspection.getUniversityAuditStatus();
			midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
			midAuditPerson = midinspection.getUniversityAuditorName();
			midAuditDate = midinspection.getUniversityAuditDate();
			midAuditResult = midinspection.getUniversityAuditResult();
		}
		else if(vtp == 3) {//省厅
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != midinspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(midinspection.getProvinceAuditorAgency().getId()))) {
				midAuditOpinion = midinspection.getProvinceAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != midinspection.getProvinceAuditor() && account.getOfficer().getId().equals(midinspection.getProvinceAuditor().getId()))) {
				midAuditOpinion = midinspection.getProvinceAuditOpinion();
			}else {
				midAuditOpinion = "";
			}
			midAuditStatus = midinspection.getProvinceAuditStatus();
			midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
			midAuditPerson = midinspection.getProvinceAuditorName();
			midAuditDate = midinspection.getProvinceAuditDate();
			midAuditResult = midinspection.getProvinceAuditResult();
		}
		else if(vtp == 4) {//教育部
			if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 1 && null != midinspection.getFinalAuditorAgency() && (account.getAgency().getId().equals(midinspection.getFinalAuditorAgency().getId())))) {
				midAuditOpinion = midinspection.getFinalAuditOpinion();
			}else if (accountType.equals(AccountType.ADMINISTRATOR) || (account.getIsPrincipal() == 0 && null != midinspection.getFinalAuditor() && account.getOfficer().getId().equals(midinspection.getFinalAuditor().getId()))) {
				midAuditOpinion = midinspection.getFinalAuditOpinion();
			}else {
				midAuditOpinion = "";
			}
			midAuditStatus = midinspection.getFinalAuditStatus();
			midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
			midAuditPerson = midinspection.getFinalAuditorName();
			midAuditDate = midinspection.getFinalAuditDate();
			midAuditResult = midinspection.getFinalAuditResult();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入项目中检审核添加页面预处理
	 */
	public String toAdd() {
		AccountType accountType = loginer.getCurrentType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//中检业务状态
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//中检各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		return SUCCESS;
	}
	
	/**
	 * 添加项目中检审核，批量时用id串拼接
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
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//判断中检成果是否已审核完毕
				String midId = this.projectService.getCurrentMidinspectionByGrantedId(gids[i]).getId();
				if(!this.productService.isProductAuditedOfInspection(1, midId)){
					jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_AUDIT_PRODUCT);
					return INPUT;
				}
			}
			GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, gids[i]);
			ProjectMidinspection midinspection = this.projectService.getCurrentMidinspectionByGrantedId(gids[i]);
			int midType = midinspection.getStatus();
			if(midinspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && midType == 5) || (accountType.equals(AccountType.PROVINCE) && midType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && midType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && midType==2))){
				if(midAuditOpinion != null){
					midAuditOpinion = ("A"+midAuditOpinion).trim().substring(1);
				}
		    	if((accountType.equals(AccountType.MINISTRY) || midAuditResult == 1) && midAuditOpinionFeedback != null){//部级审核或部级以下审核不同意
		    		midinspection.setFinalAuditOpinionFeedback(("A" + midAuditOpinionFeedback).trim().substring(1));
		        }else{
		        	midinspection.setFinalAuditOpinionFeedback(null);
		        }
				int isSubUni = 0;
	        	if(midType == 3){
	        		isSubUni = projectService.isSubordinateUniversityGranted(projectid);
	        	}
	        	Map auditMap = new HashMap();
	        	AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, midAuditResult, midAuditStatus, midAuditOpinion);
	        	auditMap.put("auditInfo", auditInfo);
	        	auditMap.put("isSubUni", isSubUni);
	        	midinspection.edit(auditMap);
				this.dao.modify(midinspection);
			}
			
			if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
				Map parmap = new HashMap();
				parmap.put("grantedId", granted.getId());
				if (granted.getProjectType().equals("general")) {
					GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
					if (generalFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//						generalFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						generalFunding.setStatus(0);
						dao.modify(generalFunding);
					}else {
						GeneralFunding newGeneralFunding = new GeneralFunding();
//						newGeneralFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						newGeneralFunding.setStatus(0);
						newGeneralFunding.setType(2);
						newGeneralFunding.setGranted(granted);
						newGeneralFunding.setGrantedId(granted.getId());
						newGeneralFunding.setProjectType(granted.getProjectType());
						dao.add(newGeneralFunding);
					}
				}else if (granted.getProjectType().equals("instp")) {
					InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
					if (instpFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//						instpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						instpFunding.setStatus(0);
						dao.modify(instpFunding);
					}else {
						InstpFunding newInstpFunding = new InstpFunding();
//						newInstpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						newInstpFunding.setStatus(0);
						newInstpFunding.setType(2);
						newInstpFunding.setGranted(granted);
						newInstpFunding.setGrantedId(granted.getId());
						newInstpFunding.setProjectType(granted.getProjectType());
						dao.add(newInstpFunding);
					}
				}else if (granted.getProjectType().equals("key")) {
					KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
					if (keyFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//						keyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						keyFunding.setStatus(0);
						dao.modify(keyFunding);
					}else {
						KeyFunding newKeyFunding = new KeyFunding();
//						newKeyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
						newKeyFunding.setStatus(0);
						newKeyFunding.setType(2);
						newKeyFunding.setGranted(granted);
						newKeyFunding.setGrantedId(granted.getId());
						newKeyFunding.setProjectType(granted.getProjectType());
						dao.add(newKeyFunding);
					}
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 添加中检审核校验
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 进入项目中检审核修改页面预处理
	 */
	public String toModify() {
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if(!this.projectService.checkIfUnderControl(loginer, appId, checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//中检业务状态
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		//中检各级别审核截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectMidinspection midinspection = this.projectService.getCurrentMidinspectionByGrantedId(projectid);
		//高校院系或研究机构
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1 && ((null != midinspection.getDeptInstAuditorDept() && account.getDepartment().getId().equals(midinspection.getDeptInstAuditorDept().getId()) || (null != midinspection.getDeptInstAuditorInst() && account.getInstitute().getId().equals(midinspection.getDeptInstAuditorInst().getId()))))){
				midAuditOpinion = midinspection.getDeptInstAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getDeptInstAuditStatus();
				midAuditResult = midinspection.getDeptInstAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != midinspection.getDeptInstAuditor() && account.getOfficer().getId().equals(midinspection.getDeptInstAuditor().getId())) {
				midAuditOpinion = midinspection.getDeptInstAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getDeptInstAuditStatus();
				midAuditResult = midinspection.getDeptInstAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//地方或部署高校
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1 && null != midinspection.getUniversityAuditorAgency() && account.getAgency().getId().equals(midinspection.getUniversityAuditorAgency().getId())) {
				midAuditOpinion = midinspection.getUniversityAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getUniversityAuditStatus();
				midAuditResult = midinspection.getUniversityAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != midinspection.getUniversityAuditor() && account.getOfficer().getId().equals(midinspection.getUniversityAuditor().getId())) {
				midAuditOpinion = midinspection.getUniversityAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getUniversityAuditStatus();
				midAuditResult = midinspection.getUniversityAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//省厅
		else if(accountType.equals(AccountType.PROVINCE)) {
			if (account.getIsPrincipal() == 1 && null != midinspection.getProvinceAuditorAgency() && account.getAgency().getId().equals(midinspection.getProvinceAuditorAgency().getId())) {
				midAuditOpinion = midinspection.getProvinceAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getProvinceAuditStatus();
				midAuditResult = midinspection.getProvinceAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != midinspection.getProvinceAuditor() && account.getOfficer().getId().equals(midinspection.getProvinceAuditor().getId())) {
				midAuditOpinion = midinspection.getProvinceAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getProvinceAuditStatus();
				midAuditResult = midinspection.getProvinceAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		//教育部
		else if(accountType.equals(AccountType.MINISTRY)) {
			if (account.getIsPrincipal() == 1 && null != midinspection.getFinalAuditorAgency() && account.getAgency().getId().equals(midinspection.getFinalAuditorAgency().getId())) {
				midAuditOpinion = midinspection.getFinalAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getFinalAuditStatus();
				midAuditResult = midinspection.getFinalAuditResult();
			}else if (account.getIsPrincipal() == 0 && null != midinspection.getFinalAuditor() && account.getOfficer().getId().equals(midinspection.getFinalAuditor().getId())) {
				midAuditOpinion = midinspection.getFinalAuditOpinion();
				midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
				midAuditStatus = midinspection.getFinalAuditStatus();
				midAuditResult = midinspection.getFinalAuditResult();
			}else {
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			
		}
		return SUCCESS;
	}
	
	/**
	 * 修改项目中检审核
	 * @author 余潜玉
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改中检审核校验
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 退回项目中检申请
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String backAudit(){
		AccountType accountType =loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断中检成果是否已审核完毕
			String midId = this.projectService.getCurrentMidinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(1, midId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		ProjectMidinspection midinspection = projectService.getCurrentMidinspectionByGrantedId(projectid);
		int midType = midinspection.getStatus();
		if(midinspection.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && midType == 5) || (accountType.equals(AccountType.PROVINCE) && midType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && midType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && midType==2))){
			midinspection.back(projectService.isSubordinateUniversityGranted(projectid));
			/* 以下代码为高校跳过部门直接退回到申请者 */
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				midinspection.back(0);
			}
			/* 结束 */
			dao.modify(midinspection);
		}
		return SUCCESS;
	}
	
	/**
	 * 退回项目中检申请校验
	 */
	public void validateBackAudit(){
		this.validateEdit(3);
	}
	
	/**
	 * 提交项目中检审核
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		AccountType accountType = loginer.getCurrentType();
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//判断中检成果是否已审核完毕
			String midId = this.projectService.getCurrentMidinspectionByGrantedId(projectid).getId();
			if(!this.productService.isProductAuditedOfInspection(1, midId)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_AUDIT_PRODUCT);
				return INPUT;
			}
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		ProjectMidinspection g = projectService.getCurrentMidinspectionByGrantedId(projectid);
		int midType = g.getStatus();
		if(g.getFinalAuditStatus() != 3 && ((accountType.equals(AccountType.MINISTRY) && midType == 5) || (accountType.equals(AccountType.PROVINCE) && midType == 4) || ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && midType == 3) || ((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) && midType==2))){
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni", this.projectService.isSubordinateUniversityGranted(projectid));
			g.submit(auditMap);
			dao.modify(g);
		}
		
		if (g.getFinalAuditResult() == 2 && g.getFinalAuditStatus() == 3) {
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			if (granted.getProjectType().equals("general")) {
				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
				if (generalFunding != null) {
					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//					generalFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					generalFunding.setStatus(0);
					dao.modify(generalFunding);
				}else {
					GeneralFunding newGeneralFunding = new GeneralFunding();
//					newGeneralFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					newGeneralFunding.setStatus(0);
					newGeneralFunding.setType(2);
					newGeneralFunding.setGranted(granted);
					newGeneralFunding.setGrantedId(granted.getId());
					newGeneralFunding.setProjectType(granted.getProjectType());
					dao.add(newGeneralFunding);
				}
			}else if (granted.getProjectType().equals("instp")) {
				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
				if (instpFunding != null) {
					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//					instpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					instpFunding.setStatus(0);
					dao.modify(instpFunding);
				}else {
					InstpFunding newInstpFunding = new InstpFunding();
//					newInstpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					newInstpFunding.setStatus(0);
					newInstpFunding.setType(2);
					newInstpFunding.setGranted(granted);
					newInstpFunding.setGrantedId(granted.getId());
					newInstpFunding.setProjectType(granted.getProjectType());
					dao.add(newInstpFunding);
				}
			}else if (granted.getProjectType().equals("key")) {
				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
				if (keyFunding != null) {
					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//					keyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					keyFunding.setStatus(0);
					dao.modify(keyFunding);
				}else {
					KeyFunding newKeyFunding = new KeyFunding();
//					newKeyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
					newKeyFunding.setStatus(0);
					newKeyFunding.setType(2);
					newKeyFunding.setGranted(granted);
					newKeyFunding.setGrantedId(granted.getId());
					newKeyFunding.setProjectType(granted.getProjectType());
					dao.add(newKeyFunding);
				}
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 提交中检审核校验
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
			if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0){//中检已通过
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
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (midStatus == 0){
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
			if(midAuditStatus!=1 && midAuditStatus!=2 && midAuditStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(midAuditResult!=1 && midAuditResult!=2){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(midAuditOpinion!=null && midAuditOpinion.length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT);
				info += ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}

	public int getMidAuditStatus() {
		return midAuditStatus;
	}

	public void setMidAuditStatus(int midAuditStatus) {
		this.midAuditStatus = midAuditStatus;
	}

	public String getMidAuditOpinion() {
		return midAuditOpinion;
	}

	public void setMidAuditOpinion(String midAuditOpinion) {
		this.midAuditOpinion = midAuditOpinion;
	}

	public int getMidAuditResult() {
		return midAuditResult;
	}

	public void setMidAuditResult(int midAuditResult) {
		this.midAuditResult = midAuditResult;
	}

	public String getMidId() {
		return midId;
	}

	public void setMidId(String midId) {
		this.midId = midId;
	}

	public String getMidAuditPerson() {
		return midAuditPerson;
	}

	public void setMidAuditPerson(String midAuditPerson) {
		this.midAuditPerson = midAuditPerson;
	}

	public Date getMidAuditDate() {
		return midAuditDate;
	}

	public void setMidAuditDate(Date midAuditDate) {
		this.midAuditDate = midAuditDate;
	}

	public int getVtp() {
		return vtp;
	}
	public void setVtp(int vtp) {
		this.vtp = vtp;
	}

	public String getMidAuditOpinionFeedback() {
		return midAuditOpinionFeedback;
	}

	public void setMidAuditOpinionFeedback(String midAuditOpinionFeedback) {
		this.midAuditOpinionFeedback = midAuditOpinionFeedback;
	}

}
