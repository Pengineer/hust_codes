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
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AuditInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项鉴定审核父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目结项评审审核共用的相关方法
 */
public abstract class EndinspectionReviewAuditAction extends ProjectBaseAction{

	private static final long serialVersionUID = 1L;
	private String endId;//结项id
	private int isApplyExcellent;//是否申请优秀成果 1:是	0：否
	private int isApplyNoevaluation;//是否申请免鉴定1:是	0：否
	private int reviewAuditStatus;//评审审核状态
	private int reviewAuditResultEnd;//评审审核结果
	private int reviewAuditResultNoevalu;//评审审核免鉴定结果
	private int reviewAuditResultExcelle;//评审审核优秀成果结果
	private String certificate;//结项证书编号
	private String reviewAuditorName;//评审审核人
	private Date reviewAuditDate;//评审审核时间
	private String reviewAuditOpinion;//评审审核意见
	private String reviewAuditOpinionFeedback;//评审审核意见（反馈给项目负责人）
	
	protected abstract String endinspectionClassName();//项目结项类类名
	
	/**
	 * 进入项目结项评审审核添加页面预处理
	 */
	public String toAdd() {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getFinalAuditResultExcellent() == 1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getFinalAuditResultNoevaluation() == 1){//审核未通过
			isApplyNoevaluation = 0;
		}
		this.certificate = this.projectService.getDefaultEndCertificate(endinspectionClassName());
		return SUCCESS;
	}
	
	/**
	 * 添加项目结项评审审核
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String add(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		if(endinspection.getStatus() == 7 && endinspection.getFinalAuditStatus() != 3){
			if(reviewAuditOpinion != null){
				reviewAuditOpinion = ("A"+reviewAuditOpinion).trim().substring(1);
			}
			if(reviewAuditOpinionFeedback != null){
				endinspection.setFinalAuditOpinionFeedback(("A" + reviewAuditOpinionFeedback).trim().substring(1));
			}else{
				endinspection.setFinalAuditOpinionFeedback(null);
			}
			if(endinspection.getIsApplyExcellent() == 1){
				endinspection.setFinalAuditResultExcellent(reviewAuditResultExcelle);
			}
			if(endinspection.getIsApplyNoevaluation() == 1){
				endinspection.setFinalAuditResultNoevaluation(reviewAuditResultNoevalu);
			}
			if(reviewAuditResultEnd ==2){
				endinspection.setCertificate(certificate.trim());
			}else{
				endinspection.setCertificate(null);
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, reviewAuditResultEnd, reviewAuditStatus, reviewAuditOpinion);
			int isSubUni = (Integer)this.projectService.isSubordinateUniversityGranted(projectid);
			auditMap.put("auditInfo", auditInfo);
			auditMap.put("isSubUni",isSubUni);
			endinspection.edit(auditMap);
			dao.modify(endinspection);
			if(endinspection.getFinalAuditResultEnd()==2 && endinspection.getFinalAuditStatus()==3){//同意结项
				//修改立项表
				ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, this.projectService.getGrantedIdByEndId(this.endId));
				granted.setStatus(2);
				granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
				granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
				granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
				granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
				this.dao.modify(granted);
				
				Map parmap = new HashMap();
				parmap.put("grantedId", granted.getId());
				if (granted.getProjectType().equals("general")) {
					GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (generalFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的10%
//						generalFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						generalFunding.setStatus(0);
						dao.modify(generalFunding);
					}else {
						GeneralFunding newGeneralFunding = new GeneralFunding();
//						newGeneralFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newGeneralFunding.setStatus(0);
						newGeneralFunding.setType(3);
						newGeneralFunding.setGranted(granted);
						newGeneralFunding.setGrantedId(granted.getId());
						newGeneralFunding.setProjectType(granted.getProjectType());
						dao.add(newGeneralFunding);
					}
				}else if (granted.getProjectType().equals("instp")) {
					InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (instpFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的10%
//						instpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						instpFunding.setStatus(0);
						dao.modify(instpFunding);
					}else {
						InstpFunding newInstpFunding = new InstpFunding();
//						newInstpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newInstpFunding.setStatus(0);
						newInstpFunding.setType(3);
						newInstpFunding.setGranted(granted);
						newInstpFunding.setGrantedId(granted.getId());
						newInstpFunding.setProjectType(granted.getProjectType());
						dao.add(newInstpFunding);
					}
				}else if (granted.getProjectType().equals("key")) {
					KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (keyFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的10%
//						keyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						keyFunding.setStatus(0);
						dao.modify(keyFunding);
					}else {
						KeyFunding newKeyFunding = new KeyFunding();
//						newKeyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newKeyFunding.setStatus(0);
						newKeyFunding.setType(3);
						newKeyFunding.setGranted(granted);
						newKeyFunding.setGrantedId(granted.getId());
						newKeyFunding.setProjectType(granted.getProjectType());
						dao.add(newKeyFunding);
					}
				}else if (granted.getProjectType().equals("entrust")) {
					EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (entrustFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的10%
//						entrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						entrustFunding.setStatus(0);
						dao.modify(entrustFunding);
					}else {
						EntrustFunding newEntrustFunding = new EntrustFunding();
//						newEntrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						newEntrustFunding.setStatus(0);
						newEntrustFunding.setType(3);
						newEntrustFunding.setGranted(granted);
						newEntrustFunding.setGrantedId(granted.getId());
						newEntrustFunding.setProjectType(granted.getProjectType());
						dao.add(newEntrustFunding);
					}
				}else if (granted.getProjectType().equals("post")) {
					PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (postFunding != null) {
						//中检通过则添加中检拨款申请，金额默认为批准经费的10%
//						postFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						postFunding.setStatus(0);
						dao.modify(postFunding);
					}else {
						PostFunding newPostFunding = new PostFunding();
//						newPostFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						newPostFunding.setStatus(0);
						newPostFunding.setType(3);
						newPostFunding.setGranted(granted);
						newPostFunding.setGrantedId(granted.getId());
						newPostFunding.setProjectType(granted.getProjectType());
						dao.add(newPostFunding);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加项目结项评审审核校验
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}

	/**
	 * 进入项目结项评审审核修改页面预处理
	 */
	public String toModify(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectEndinspection endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getDeptInstResultExcellent()==1 || endinspection.getUniversityResultExcellent()==1 || endinspection.getProvinceResultExcellent()==1 || endinspection.getMinistryResultExcellent()==1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getDeptInstResultNoevaluation()==1 || endinspection.getUniversityResultNoevaluation()==1 || endinspection.getProvinceResultNoevaluation()==1 || endinspection.getMinistryResultNoevaluation()==1){//审核未通过
			isApplyNoevaluation = 0;
		}
		reviewAuditStatus = endinspection.getFinalAuditStatus();
		reviewAuditResultEnd = endinspection.getFinalAuditResultEnd();
		reviewAuditResultNoevalu = endinspection.getFinalAuditResultNoevaluation();
		reviewAuditResultExcelle = endinspection.getFinalAuditResultExcellent();
		reviewAuditOpinion = endinspection.getFinalAuditOpinion();
		reviewAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		if(endinspection.getFinalAuditResultEnd()==2){
			this.certificate = endinspection.getCertificate();
		}else{
			this.certificate = this.projectService.getDefaultEndCertificate(endinspectionClassName());
		}
		return SUCCESS;
	}

	/**
	 * 修改项目结项评审审核
	 */
	@Transactional
	public String modify(){
		return this.add();
	}
	
	/**
	 * 修改项目结项评审审核校验
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	/**
	 * 查看项目结项评审审核
	 */
	public String view(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		reviewAuditStatus = endinspection.getFinalAuditStatus();
		reviewAuditResultEnd = endinspection.getFinalAuditResultEnd();
		reviewAuditResultNoevalu = endinspection.getFinalAuditResultNoevaluation();
		reviewAuditResultExcelle = endinspection.getFinalAuditResultExcellent();
		reviewAuditOpinion = endinspection.getFinalAuditOpinion();
		reviewAuditorName = endinspection.getFinalAuditorName();
		reviewAuditDate = endinspection.getFinalAuditDate();
		reviewAuditOpinion = endinspection.getFinalAuditOpinion();
		reviewAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		certificate = endinspection.getCertificate();
		return SUCCESS;
	}

	/**
	 * 提交评审审核
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(this.projectService.getGrantedIdByEndId(endId)).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		if(endinspection.getStatus() == 7 && endinspection.getFinalAuditStatus() != 3){
			if(endinspection.getFinalAuditResultEnd() == 2 && !this.projectService.isEndNumberUnique(endinspectionClassName(), endinspection.getCertificate(), endinspection.getId())){
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
				return INPUT;
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			endinspection.submit(auditMap);//提交操作结果
			this.dao.modify(endinspection);
			if(endinspection.getFinalAuditResultEnd()==2 && endinspection.getFinalAuditStatus()==3){//同意结项
				//修改立项表
				ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, this.projectService.getGrantedIdByEndId(this.endId));
				granted.setStatus(2);
				granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
				granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
				granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
				granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
				this.dao.modify(granted);
				
				Map parmap = new HashMap();
				parmap.put("grantedId", granted.getId());
				if (granted.getProjectType().equals("general")) {
					GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (generalFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的10%
//						generalFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						generalFunding.setStatus(0);
						dao.modify(generalFunding);
					}else {
						GeneralFunding newGeneralFunding = new GeneralFunding();
//						newGeneralFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newGeneralFunding.setStatus(0);
						newGeneralFunding.setType(3);
						newGeneralFunding.setGranted(granted);
						newGeneralFunding.setGrantedId(granted.getId());
						newGeneralFunding.setProjectType(granted.getProjectType());
						dao.add(newGeneralFunding);
					}
				}else if (granted.getProjectType().equals("instp")) {
					InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (instpFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的10%
//						instpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						instpFunding.setStatus(0);
						dao.modify(instpFunding);
					}else {
						InstpFunding newInstpFunding = new InstpFunding();
//						newInstpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newInstpFunding.setStatus(0);
						newInstpFunding.setType(3);
						newInstpFunding.setGranted(granted);
						newInstpFunding.setGrantedId(granted.getId());
						newInstpFunding.setProjectType(granted.getProjectType());
						dao.add(newInstpFunding);
					}
				}else if (granted.getProjectType().equals("key")) {
					KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (keyFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的10%
//						keyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						keyFunding.setStatus(0);
						dao.modify(keyFunding);
					}else {
						KeyFunding newKeyFunding = new KeyFunding();
//						newKeyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
						newKeyFunding.setStatus(0);
						newKeyFunding.setType(3);
						newKeyFunding.setGranted(granted);
						newKeyFunding.setGrantedId(granted.getId());
						newKeyFunding.setProjectType(granted.getProjectType());
						dao.add(newKeyFunding);
					}
				}else if (granted.getProjectType().equals("entrust")) {
					EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (entrustFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的50%
//						entrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						entrustFunding.setStatus(0);
						dao.modify(entrustFunding);
					}else {
						EntrustFunding newEntrustFunding = new EntrustFunding();
//						newEntrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						newEntrustFunding.setStatus(0);
						newEntrustFunding.setType(3);
						newEntrustFunding.setGranted(granted);
						newEntrustFunding.setGrantedId(granted.getId());
						newEntrustFunding.setProjectType(granted.getProjectType());
						dao.add(newEntrustFunding);
					}
				}else if (granted.getProjectType().equals("post")) {
					PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
					if (postFunding != null) {
						//结项通过则添加结项拨款申请，金额默认为批准经费的50%
//						postFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						postFunding.setStatus(0);
						dao.modify(postFunding);
					}else {
						PostFunding newPostFunding = new PostFunding();
//						newPostFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
						newPostFunding.setStatus(0);
						newPostFunding.setType(3);
						newPostFunding.setGranted(granted);
						newPostFunding.setGrantedId(granted.getId());
						newPostFunding.setProjectType(granted.getProjectType());
						dao.add(newPostFunding);
					}
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
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info = "";
		if (endId == null || endId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info+=GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			projectid = this.projectService.getGrantedIdByEndId(endId);
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
		}
		if(type == 1 ||type== 2){
			if(reviewAuditResultEnd != 1 && reviewAuditResultEnd != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_RESULT_NULL;
			}
			if(reviewAuditResultEnd == 2){
				if(certificate == null || certificate.trim().isEmpty()){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_NULL);
					info+=ProjectInfo.ERROR_END_CERTIFICATE_NULL;
				}else if(certificate.trim().length() > 40){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_OUT);
					info+=ProjectInfo.ERROR_END_CERTIFICATE_OUT;
				}else if(!this.projectService.isEndNumberUnique(endinspectionClassName(), certificate, endId)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
					info+=ProjectInfo.ERROR_END_NUMBER_EXIST;
				}
			}
			if(isApplyNoevaluation == 1 && reviewAuditResultNoevalu != 1 && reviewAuditResultNoevalu != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL;
			}
			if(isApplyExcellent == 1 && reviewAuditResultExcelle != 1 && reviewAuditResultExcelle != 2){
				this.addFieldError("reviewAuditResultExcelle", ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL;
			}
			if(reviewAuditOpinion != null && reviewAuditOpinion.length() > 2000){
				this.addFieldError("reviewAuditOpinion", ProjectInfo.ERROR_END_AUDIT_OPINION_OUT);
				info+=ProjectInfo.ERROR_END_AUDIT_OPINION_OUT;
			}
			if(reviewAuditOpinionFeedback != null && reviewAuditOpinionFeedback.length() > 200){
				this.addFieldError("reviewAuditOpinionFeedback", ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT);
				info+=ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT;
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

	public int getReviewAuditResultEnd() {
		return reviewAuditResultEnd;
	}

	public void setReviewAuditResultEnd(int reviewAuditResultEnd) {
		this.reviewAuditResultEnd = reviewAuditResultEnd;
	}

	public int getReviewAuditResultNoevalu() {
		return reviewAuditResultNoevalu;
	}

	public void setReviewAuditResultNoevalu(int reviewAuditResultNoevalu) {
		this.reviewAuditResultNoevalu = reviewAuditResultNoevalu;
	}

	public int getReviewAuditResultExcelle() {
		return reviewAuditResultExcelle;
	}

	public void setReviewAuditResultExcelle(int reviewAuditResultExcelle) {
		this.reviewAuditResultExcelle = reviewAuditResultExcelle;
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

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
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

	public String getReviewAuditOpinionFeedback() {
		return reviewAuditOpinionFeedback;
	}

	public void setReviewAuditOpinionFeedback(String reviewAuditOpinionFeedback) {
		this.reviewAuditOpinionFeedback = reviewAuditOpinionFeedback;
	}
}
