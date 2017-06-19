package csdc.action.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.common.Visitor;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public abstract class EndinspectionAction extends ProjectAction {

	private static final long serialVersionUID = 1L;
	protected ProjectEndinspection endinspection;
	protected String endId;//结项id
	protected int isApplyExcellent;//是否申请优秀成果 1:是	0：否
	protected int isApplyNoevaluation;//是否申请免鉴定1:是	0：否
	protected int endAuditResult;//审核结果
	protected int endNoauditResult;//免鉴定结果
	protected int endExcellentResult;//优秀成果结果
	protected Date endAuditDate;//审核时间
	protected String endAuditOpinion;//审核意见
	protected String endAuditOpinionFeedback;//审核意见（反馈给项目负责人）
	
	//项目评审审核
	private int reviewAuditResultEnd;//评审审核结果
	private int reviewAuditResultNoevalu;//评审审核免鉴定结果
	private int reviewAuditResultExcelle;//评审审核优秀成果结果
	private String certificate;//结项证书编号
	private Date reviewAuditDate;//评审审核时间
	private String reviewAuditOpinion;//评审审核意见
	private String reviewAuditOpinionFeedback;//评审审核意见（反馈给项目负责人）
	
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected Date endDate;//结项时间
	protected String endCertificate;//结项证书编号
	protected String endMember;//结项主要参加人姓名
	protected int endResult;//结项结果：1不同意；2同意
	protected String endImportedOpinion;//录入结项意见
	protected String endOpinionFeedback;//录入结项意见（反馈给项目负责人）
	protected String endProductInfo;//结项成果信息
	protected ProjectFee projectFeeEnd;
	
	public String popAudit() {
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getFinalAuditResultExcellent() == 1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getFinalAuditResultNoevaluation() == 1){//审核未通过
			isApplyNoevaluation = 0;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加变更审核信息校验
	 */
	public void validateAudit() {
		String info ="";
		if (endId == null || endId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL;
		} else{
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
		if(endAuditResult != 1 && endAuditResult != 2){
			this.addFieldError("endAuditResult", GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(isApplyExcellent == 1 && endExcellentResult != 1 && endExcellentResult != 2){
			this.addFieldError("endExcellentResult", GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(isApplyNoevaluation == 1 &&  endNoauditResult != 1 && endNoauditResult != 2){
			this.addFieldError("endNoauditResult", GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(endAuditOpinion != null && endAuditOpinion.length() > 2000){
			this.addFieldError("endAuditOpinion", ProjectInfo.ERROR_END_AUDIT_OPINION_OUT);
			info += ProjectInfo.ERROR_END_AUDIT_OPINION_OUT;
		}
		if(endAuditOpinionFeedback != null && endAuditOpinionFeedback.length() > 200){
			this.addFieldError("endAuditOpinionFeedback", ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT);
			info += ProjectInfo.ERROR_END_AUDIT_OPINION_FEEDBACK_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加项目结项申请审核
	 */
	@Transactional
	public String audit() {
		ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		if(endinspection.getIsApplyExcellent() == 1){
			endinspection.setMinistryResultExcellent(endExcellentResult);
		}
		if(endinspection.getIsApplyNoevaluation() == 1){
			endinspection.setMinistryResultNoevaluation(endNoauditResult);
		}
		if(endAuditOpinion != null){
			endAuditOpinion = ("A"+endAuditOpinion).trim().substring(1);
		}
		if(endAuditResult == 1 && endAuditOpinionFeedback != null){//审核不同意
			endinspection.setFinalAuditOpinionFeedback(("A" + endAuditOpinionFeedback).trim().substring(1));
        }else{
        	endinspection.setFinalAuditOpinionFeedback(null);
        }
		Date approveDate = this.projectService.setDateHhmmss(endAuditDate);
		endinspection.setMinistryAuditDate(approveDate);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		endinspection.setMinistryAuditorName(visitor.getUser().getUsername());
		endinspection.setMinistryResultEnd(endAuditResult);
		if(endAuditResult == 1) {//审核不同意
			endinspection.setFinalAuditResultEnd(1);
			if(endinspection.getIsApplyExcellent() == 1){//申请优秀成果
				endinspection.setFinalAuditResultExcellent(1);
			}
			if(endinspection.getIsApplyNoevaluation() == 1){//申请免鉴定
				endinspection.setFinalAuditResultNoevaluation(1);
			}
			endinspection.setFinalAuditOpinion(endinspection.getMinistryAuditOpinion());
			endinspection.setFinalAuditDate(endinspection.getMinistryAuditDate());
			endinspection.setFinalAuditorName(visitor.getUser().getUsername());
		}
		baseService.modify(endinspection);
		return SUCCESS;
	}
	
	/**
	 * 查看结项审核详情
	 * @return
	 */
	public String auditView() {
		ProjectEndinspection endinspection = (ProjectEndinspection) baseService.query(ProjectEndinspection.class, endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		endAuditOpinion = endinspection.getMinistryAuditOpinion();
		endAuditResult = endinspection.getMinistryResultEnd();
		endNoauditResult = endinspection.getMinistryResultNoevaluation();
		endExcellentResult = endinspection.getMinistryResultExcellent();
		endAuditDate = endinspection.getMinistryAuditDate();
		endAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		return SUCCESS;
	}

	/**
	 * 结项评审审核弹出层
	 * @return
	 */
	public String popAuditReview() {
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		if(endinspection.getFinalAuditResultExcellent() == 1){//审核未通过
			isApplyExcellent = 0;
		}
		if(endinspection.getFinalAuditResultNoevaluation() == 1){//审核未通过
			isApplyNoevaluation = 0;
		}
		this.certificate = this.projectService.getDefaultEndCertificate();
		return SUCCESS;
	}
	
	/**
	 * 添加项目结项评审审核校验
	 */
	public void validateAuditReview(){
		String info ="";
		if (endId == null || endId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL);
			info += ProjectInfo.ERROR_END_AUDIT_SUBMIT_NULL;
		} else{
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
				}else if(!this.projectService.isEndNumberUnique(certificate, endId)){
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
	/**
	 * 添加项目结项评审审核
	 */
	@Transactional
	public String auditReview() {
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		if(endinspection.getMinistryResultEnd() == 2) {
			if(reviewAuditOpinion != null){
				endinspection.setFinalAuditOpinion(("A"+reviewAuditOpinion).trim().substring(1));
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
			Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
			endinspection.setFinalAuditDate(approveDate);
			endinspection.setFinalAuditResultEnd(reviewAuditResultEnd);
			Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
			endinspection.setFinalAuditorName(visitor.getUser().getUsername());
			baseService.modify(endinspection);
			if(endinspection.getFinalAuditResultEnd()==2) {
				//修改立项表
				ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, this.projectService.getGrantedIdByEndId(this.endId));
				granted.setStatus(2);
				granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
				granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
				granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
				granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
				baseService.modify(granted);
			}
		} else {
			jsonMap.put(GlobalInfo.ERROR_INFO, "部级审核尚未通过");
		}
		return SUCCESS;
	}
	
	/**
	 * 查看项目结项评审审核
	 */
	public String auditReviewView() {
		ProjectEndinspection endinspection=(ProjectEndinspection)this.dao.query(ProjectEndinspection.class,this.endId);
		isApplyExcellent = endinspection.getIsApplyExcellent();
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		reviewAuditResultEnd = endinspection.getFinalAuditResultEnd();
		reviewAuditResultNoevalu = endinspection.getFinalAuditResultNoevaluation();
		reviewAuditResultExcelle = endinspection.getFinalAuditResultExcellent();
//		reviewAuditorName = endinspection.getFinalAuditorName();
		reviewAuditDate = endinspection.getFinalAuditDate();
		reviewAuditOpinion = endinspection.getFinalAuditOpinion();
		reviewAuditOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		certificate = endinspection.getCertificate();
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项结果录入添加页面预处理
	 * @author 余潜玉
	 */
	public String toAddResult(){
		endDate = new Date();
		endCertificate = this.projectService.getDefaultEndCertificate();
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果校验
	 * @author 余潜玉
	 */
	public void validateAddResult() {
		String info ="";
		int endAllow = 0;
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else {
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
			if(!"post".equals(projectType()) && !"entrust".equals(projectType())){//后期资助项目和委托应急课题无中检
				int grantedYear = this.projectService.getGrantedYear(projectid);
				endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
				if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
					info += ProjectInfo.ERROR_END_CANNOT;
				}
				if(this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){//有未处理中检
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
					info += ProjectInfo.ERROR_ENDM_DEALING;
				}
			}
		}
		if(!this.projectService.getPendingEndinspectionByGrantedId(projectid).isEmpty()){//有未处理结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DEALING);
			info += ProjectInfo.ERROR_END_CANNOT;
		}
		if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDV_DEALING);
			info += ProjectInfo.ERROR_ENDV_DEALING;
		}
		if(isApplyExcellent != 0 && isApplyExcellent != 1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL);
			info += ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL;
		}
		if(isApplyNoevaluation != 0 && isApplyNoevaluation != 1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL);
			info += ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL;
		}
		if(null != endMember && !endMember.trim().isEmpty() && endMember.trim().length()>200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_MEMBER_OUT);
			info+=ProjectInfo.ERROR_END_MEMBER_OUT;
		}
		if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//结项已通过
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_ALREADY);
			info += ProjectInfo.ERROR_END_ALREADY;
		}
		if(endResult != 1 && endResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESULT_NULL);
			info+=ProjectInfo.ERROR_END_RESULT_NULL;
		}
		if(endResult ==2){
			if(endCertificate == null || endCertificate.trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_NULL);
				info+=ProjectInfo.ERROR_END_CERTIFICATE_NULL;
			}else if(endCertificate.trim().length()>40){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_OUT);
				info+=ProjectInfo.ERROR_END_CERTIFICATE_OUT;
			}
			if(!this.projectService.isEndNumberUnique(endCertificate, "")){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
				info+=ProjectInfo.ERROR_END_NUMBER_EXIST;
			}
			if(isApplyNoevaluation == 1 && endNoauditResult != 1 && endNoauditResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL;
			}
			if(isApplyExcellent == 1 && endExcellentResult != 1 && endExcellentResult != 2 && endExcellentResult != 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL;
			}
			if(endDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DATE_NULL);
				info += ProjectInfo.ERROR_END_DATE_NULL;
			}
			if(endImportedOpinion != null && endImportedOpinion.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}
			if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}		
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加录入的一般项目结项结果
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() {
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setFeeType(5);
			dao.add(projectFeeEnd);
		}
		ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, projectid);
		endinspection = new ProjectEndinspection();
		endinspection.setType(projectType());
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.projectService.MutipleToFormat(projectService.regularNames(this.endMember)));
		Date submitDate = this.projectService.setDateHhmmss(endDate);
		endinspection.setImportedDate(new Date());
		endinspection.setApplicantSubmitDate(submitDate);
		endinspection.setFinalAuditDate(submitDate);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		endinspection.setFinalAuditorName(visitor.getUser().getUsername());
		endinspection.setIsImported(1);//设为导入数据
		endinspection.setFinalAuditResultEnd(this.endResult);
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setIsApplyNoevaluation(isApplyNoevaluation);	
		if(endImportedOpinion != null && endImportedOpinion.trim().length() > 0){
			endinspection.setFinalAuditOpinion(("A" + endImportedOpinion).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinion(null);
		}
		if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 0){
			endinspection.setFinalAuditOpinionFeedback(("A" + endOpinionFeedback).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinionFeedback(null);
		}
		if(isApplyExcellent == 1){
			endinspection.setFinalAuditResultExcellent(endExcellentResult);
		}
		if(isApplyNoevaluation == 1){
			endinspection.setFinalAuditResultNoevaluation(endNoauditResult);
		}
		if(endResult == 2){
			endinspection.setCertificate(endCertificate.trim());
		}else{
			endinspection.setCertificate(null);
		}
		if(endResult == 2){
			granted.setStatus(2);
			granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
			this.dao.modify(granted);
		}
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		uploadService.flush(groupId);
		if(endProductInfo != null && endProductInfo.contains("其他")){
			String[] proTypes = endProductInfo.split("\\(");
			if(proTypes.length > 1){
				String productTypeNames = proTypes[0];
				String[] productTypeothers = proTypes[1].split("\\)");
				String productTypeOther = productTypeothers[0];
				productTypeNames += productTypeothers[1];
				//TODO 成果信息未加入结项表中
//				endinspection.setImportedProductInfo(productTypeNames);
//				endinspection.setImportedProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
			}
		}else{
//			endinspection.setImportedProductInfo(endProductInfo);
//			endinspection.setImportedProductTypeOther(null);
		}
		baseService.add(endinspection);
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项结果录入修改页面预处理
	 * @author 余潜玉
	 */
	public String toModifyResult() {
		ProjectEndinspection endinspection;
		endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(this.projectid);
		if (endinspection.getProjectFee() != null) {
			projectFeeEnd = (ProjectFee) baseService.query(ProjectFee.class, endinspection.getProjectFee().getId());
		}
		this.endResult = endinspection.getFinalAuditResultEnd();
		this.endExcellentResult = endinspection.getFinalAuditResultExcellent();
		this.isApplyExcellent = endinspection.getIsApplyExcellent();
		if(endinspection.getFinalAuditResultEnd()==2){
			this.endCertificate = endinspection.getCertificate();
		}else{
			this.endCertificate = this.projectService.getDefaultEndCertificate();
		}
		this.endDate = endinspection.getFinalAuditDate();
		this.endImportedOpinion = endinspection.getFinalAuditOpinion();
		this.endOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		request.setAttribute("endId", endinspection.getId());
		this.endNoauditResult = endinspection.getFinalAuditResultNoevaluation();
		this.isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
//		this.endProductInfo = this.projectService.getProductTypeReal(endinspection.getImportedProductInfo(), endinspection.getImportedProductTypeOther());
		this.endMember = endinspection.getMemberName();

		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + endinspection.getId();
		uploadService.resetGroup(groupId);
		if (endinspection.getFile() != null) {
			String[] tempFileRealpath = endinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null) {
					uploadService.addFile(groupId, new File(fileRealpath));
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改结项结果校验
	 * @author 刘雅琴
	 */
	public void validateModifyResult() {
		String info ="";
		int endAllow = 0;
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else {
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
			if(!"post".equals(projectType()) && !"entrust".equals(projectType())){//后期资助项目和委托应急课题无中检
				int grantedYear = this.projectService.getGrantedYear(projectid);
				endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
				if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
					info += ProjectInfo.ERROR_END_CANNOT;
				}
			}
		}
		if(isApplyExcellent != 0 && isApplyExcellent != 1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL);
			info += ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL;
		}
		if(isApplyNoevaluation != 0 && isApplyNoevaluation != 1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL);
			info += ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL;
		}
		if(null != endMember && !endMember.trim().isEmpty() && endMember.trim().length()>200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_MEMBER_OUT);
			info+=ProjectInfo.ERROR_END_MEMBER_OUT;
		}
		if(endResult != 1 && endResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESULT_NULL);
			info+=ProjectInfo.ERROR_END_RESULT_NULL;
		}
		if(endResult ==2){
			if(endCertificate == null || endCertificate.trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_NULL);
				info+=ProjectInfo.ERROR_END_CERTIFICATE_NULL;
			}else if(endCertificate.trim().length()>40){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_OUT);
				info+=ProjectInfo.ERROR_END_CERTIFICATE_OUT;
			}
			if(!this.projectService.isEndNumberUnique(endCertificate, this.projectService.getCurrentEndinspectionByGrantedId(this.projectid).getId())){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
				info+=ProjectInfo.ERROR_END_NUMBER_EXIST;
			}
			if(isApplyNoevaluation == 1 && endNoauditResult != 1 && endNoauditResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL;
			}
			if(isApplyExcellent == 1 && endExcellentResult != 1 && endExcellentResult != 2 && endExcellentResult != 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL;
			}
			if(endDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DATE_NULL);
				info += ProjectInfo.ERROR_END_DATE_NULL;
			}
			if(endImportedOpinion != null && endImportedOpinion.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}
			if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}		
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 修改录入的项目结项结果
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String modifyResult(){
		ProjectEndinspection endinspection;
		endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(this.projectid);
		if (endinspection.getProjectFee() != null) {
			projectFeeEnd = (ProjectFee) baseService.query(ProjectFee.class, endinspection.getProjectFee().getId());
			projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
			dao.modify(projectFeeEnd);
		}
		if(!"post".equals(projectType())){//非后期资助项目（以下操作后期资助项目是在录入评审结果时处理的）
			endinspection.setMemberName(this.projectService.MutipleToFormat(this.projectService.regularNames(this.endMember)));
			//设置成果相关信息
			if(endProductInfo != null && endProductInfo.contains("其他")){
				String[] proTypes = endProductInfo.split("\\(");
				if(proTypes.length > 1){
					String productTypeNames = proTypes[0];
					String[] productTypeothers = proTypes[1].split("\\)");
					String productTypeOther = productTypeothers[0];
					productTypeNames += productTypeothers[1];
					//TODO 成果信息未加入结项表中
//					endinspection.setImportedProductInfo(productTypeNames);
//					endinspection.setImportedProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
				}
			}else{
//				endinspection.setImportedProductInfo(endProductInfo);
//				endinspection.setImportedProductTypeOther(null);
			}
		}
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		Date submitDate = this.projectService.setDateHhmmss(endDate);
		endinspection.setImportedDate(new Date());
		endinspection.setApplicantSubmitDate(submitDate);
		endinspection.setFinalAuditDate(submitDate);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		endinspection.setFinalAuditorName(visitor.getUser().getUsername());
		endinspection.setIsImported(1);//设为导入数据
		endinspection.setFinalAuditResultEnd(this.endResult);
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setIsApplyNoevaluation(isApplyNoevaluation);
		if(endImportedOpinion != null && endImportedOpinion.trim().length() > 0){
			endinspection.setFinalAuditOpinion(("A" + endImportedOpinion).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinion(null);
		}
		if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 0){
			endinspection.setFinalAuditOpinionFeedback(("A" + endOpinionFeedback).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinionFeedback(null);
		}
		if(isApplyExcellent == 1){
			endinspection.setFinalAuditResultExcellent(endExcellentResult);
		}
		if(isApplyNoevaluation == 1){
			endinspection.setFinalAuditResultNoevaluation(endNoauditResult);
		}
		if(endResult == 2){
			endinspection.setCertificate(endCertificate.trim());
		}else{
			endinspection.setCertificate(null);
		}
		if(endResult == 2){
			granted.setStatus(2);
			granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
			this.dao.modify(granted);
		}
		String groupId = "file_" + endinspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		uploadService.flush(groupId);
		if(endResult == 1){//结项终审结束后修改
			granted.setStatus(1);
		}
		dao.modify(endinspection);
		return SUCCESS;
	}
	
	public ProjectEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = endinspection;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public int getIsApplyExcellent() {
		return isApplyExcellent;
	}

	public void setIsApplyExcellent(int isApplyExcellent) {
		this.isApplyExcellent = isApplyExcellent;
	}

	public int getIsApplyNoevaluation() {
		return isApplyNoevaluation;
	}

	public void setIsApplyNoevaluation(int isApplyNoevaluation) {
		this.isApplyNoevaluation = isApplyNoevaluation;
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

	public String getEndAuditOpinionFeedback() {
		return endAuditOpinionFeedback;
	}

	public void setEndAuditOpinionFeedback(String endAuditOpinionFeedback) {
		this.endAuditOpinionFeedback = endAuditOpinionFeedback;
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

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Date getReviewAuditDate() {
		return reviewAuditDate;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndCertificate() {
		return endCertificate;
	}

	public void setEndCertificate(String endCertificate) {
		this.endCertificate = endCertificate;
	}

	public String getEndMember() {
		return endMember;
	}

	public void setEndMember(String endMember) {
		this.endMember = endMember;
	}

	public int getEndResult() {
		return endResult;
	}

	public void setEndResult(int endResult) {
		this.endResult = endResult;
	}

	public String getEndImportedOpinion() {
		return endImportedOpinion;
	}

	public void setEndImportedOpinion(String endImportedOpinion) {
		this.endImportedOpinion = endImportedOpinion;
	}

	public String getEndOpinionFeedback() {
		return endOpinionFeedback;
	}

	public void setEndOpinionFeedback(String endOpinionFeedback) {
		this.endOpinionFeedback = endOpinionFeedback;
	}

	public String getEndProductInfo() {
		return endProductInfo;
	}

	public void setEndProductInfo(String endProductInfo) {
		this.endProductInfo = endProductInfo;
	}

	public ProjectFee getProjectFeeEnd() {
		return projectFeeEnd;
	}

	public void setProjectFeeEnd(ProjectFee projectFeeEnd) {
		this.projectFeeEnd = projectFeeEnd;
	}
	
	
}
