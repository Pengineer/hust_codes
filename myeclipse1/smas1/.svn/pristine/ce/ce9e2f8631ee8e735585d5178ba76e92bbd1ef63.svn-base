package csdc.action.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.common.Visitor;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

public abstract class MidinspectionAction  extends ProjectAction {
	
	private static final long serialVersionUID = 1L;
	protected ProjectMidinspection midinspection;//项目中检
	protected String midAuditOpinion;//中检意见
	protected String midAuditOpinionFeedback;//中检意见（反馈给项目负责人）
	protected int midAuditResult;//中检结果
	protected String midId;//中检id
	protected Date midAuditDate;//中检审核时间
	//中检录入相关字段
	protected Date midDate;//中检时间
	protected int midResult;//中检结果	1：不同意	2：同意
	protected String midImportedOpinion;//录入中检意见
	protected String midOpinionFeedback;//录入中检意见（反馈给项目负责人）

	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	public String popAudit() {
		return SUCCESS;
	}
	
	/**
	 * 检验审核
	 */
	public void validateAudit() {
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
		if(midAuditResult!=1 && midAuditResult!=2){
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(midAuditOpinion!=null && midAuditOpinion.length()>200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT);
			info += ProjectInfo.ERROR_MID_AUDIT_OPINION_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	@Transactional
	public String audit() {
		ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, projectid);
		ProjectMidinspection midinspection = this.projectService.getCurrentMidinspectionByGrantedId(projectid);
		if(midAuditOpinion != null){
			midinspection.setFinalAuditOpinion(("A"+midAuditOpinion).trim().substring(1));
		} else {
			midinspection.setFinalAuditOpinion(null);
		}
		if(midAuditOpinionFeedback != null){
			midinspection.setFinalAuditOpinionFeedback(("A" + midAuditOpinionFeedback).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinionFeedback(null);
		}
		Date approveDate = this.projectService.setDateHhmmss(midAuditDate);
		midinspection.setFinalAuditDate(approveDate);
		midinspection.setFinalAuditResult(midAuditResult);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		midinspection.setFinalAuditorName(visitor.getUser().getUsername());
		baseService.modify(midinspection);
		return SUCCESS;
	}
	
	/**
	 * 查看中检审核详情
	 * @return
	 */
	public String auditView() {
		ProjectMidinspection midinspection = (ProjectMidinspection) baseService.query(ProjectMidinspection.class, midId);
		midAuditOpinion = midinspection.getFinalAuditOpinion();
		midAuditOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
		midAuditDate = midinspection.getFinalAuditDate();
		midAuditResult = midinspection.getFinalAuditResult();
		return SUCCESS;
	}
	
	/**
	 * 准备修改中检审核
	 */
	public String toAuditModify() {
		return this.auditView();
	}
	
	/**
	 * 修改中检审核校验
	 */
	public void validateAuditModify() {
		this.validateAudit();
	}
	
	/**
	 * 项目中检审核修改
	 * @return
	 */
	@Transactional
	public String auditModify() {
		return this.audit();
	}
	
	/**
	 * 进入项目中检结果录入添加页面预处理
	 */
	public String toAddResult(){
		midDate = new Date();
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	
	/**
	 * 添加中检结果校验
	 * @author 余潜玉
	 */
	public void validateAddResult() {
		String info ="";
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			int grantedYear = this.projectService.getGrantedYear(projectid);
			int midForbid = ((new Date()).getYear() + 1900 > grantedYear + 3) ? 1 : 0;
			if(midForbid == 1){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_FORBID);
				info += ProjectInfo.ERROR_MID_FORBID;
			}
		}
		if(!this.projectService.getPendingMidinspectionByGrantedId(projectid).isEmpty()){//没有未处理中检
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DEALING);
			info += ProjectInfo.ERROR_MID_DEALING;
		}
		if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//项目已经结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
			info += ProjectInfo.ERROR_END_PASS;
		}
		if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0){//中检尚未通过
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_ALREADY);
			info += ProjectInfo.ERROR_MID_ALREADY;
		}
		if(midResult!=2 && midResult!=1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_RESULT_NULL);
			info += ProjectInfo.ERROR_MID_RESULT_NULL;
		}
		if(midDate == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DATE_NULL);
			info += ProjectInfo.ERROR_MID_DATE_NULL;
		}
		if(midImportedOpinion != null && midImportedOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_OPINION_OUT);
			info += ProjectInfo.ERROR_MID_OPINION_OUT;
		}
		if(midOpinionFeedback != null && midOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_OPINION_OUT);
			info += ProjectInfo.ERROR_MID_OPINION_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加中检结果
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() {
		ProjectFee projectFeeMid = new ProjectFee();
		projectFeeMid = this.doWithAddResultFee(projectFeeMid);
		if (projectFeeMid != null) {
			projectFeeMid.setFeeType(4);
			baseService.add(projectFeeMid);
		}
		ProjectGranted granted = (ProjectGranted) baseService.query(ProjectGranted.class, projectid);
		ProjectMidinspection midinspection = new ProjectMidinspection();
		midinspection.setType(projectType());
		midinspection.setGranted(granted);
		midinspection.setProjectFee(projectFeeMid);
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFile(projectType(), projectid, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		Date submitDate = this.projectService.setDateHhmmss(midDate);
		midinspection.setApplicantSubmitDate(submitDate);
		midinspection.setFinalAuditDate(submitDate);
		midinspection.setImportedDate(new Date());
		midinspection.setIsImported(1);//设为导入数据
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		midinspection.setFinalAuditorName(visitor.getUser().getUsername());		
		midinspection.setFinalAuditResult(this.midResult);
		if(midImportedOpinion != null && midImportedOpinion.trim().length() > 0){
			midinspection.setFinalAuditOpinion(("A" + midImportedOpinion).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinion(null);
		}
		if(midOpinionFeedback != null && midOpinionFeedback.trim().length() > 0){
			midinspection.setFinalAuditOpinionFeedback(("A" + midOpinionFeedback).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinionFeedback(null);
		}
		uploadService.flush(groupId);
		baseService.modify(midinspection);
		return SUCCESS;
	}
	
	public ProjectMidinspection getMidinspection() {
		return midinspection;
	}

	public void setMidinspection(ProjectMidinspection midinspection) {
		this.midinspection = midinspection;
	}

	public String getMidAuditOpinion() {
		return midAuditOpinion;
	}
	public void setMidAuditOpinion(String midAuditOpinion) {
		this.midAuditOpinion = midAuditOpinion;
	}
	public String getMidAuditOpinionFeedback() {
		return midAuditOpinionFeedback;
	}
	public void setMidAuditOpinionFeedback(String midAuditOpinionFeedback) {
		this.midAuditOpinionFeedback = midAuditOpinionFeedback;
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
	public Date getMidAuditDate() {
		return midAuditDate;
	}
	public void setMidAuditDate(Date midAuditDate) {
		this.midAuditDate = midAuditDate;
	}

	public Date getMidDate() {
		return midDate;
	}

	public void setMidDate(Date midDate) {
		this.midDate = midDate;
	}

	public int getMidResult() {
		return midResult;
	}

	public void setMidResult(int midResult) {
		this.midResult = midResult;
	}

	public String getMidImportedOpinion() {
		return midImportedOpinion;
	}

	public void setMidImportedOpinion(String midImportedOpinion) {
		this.midImportedOpinion = midImportedOpinion;
	}

	public String getMidOpinionFeedback() {
		return midOpinionFeedback;
	}

	public void setMidOpinionFeedback(String midOpinionFeedback) {
		this.midOpinionFeedback = midOpinionFeedback;
	}
	
	
	
}
