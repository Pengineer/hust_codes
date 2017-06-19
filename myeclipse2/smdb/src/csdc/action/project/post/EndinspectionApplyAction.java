package csdc.action.project.post;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.project.post.EndinspectionApplyAction;
import csdc.bean.PostEndinspection;
import csdc.bean.PostFunding;
import csdc.bean.PostGranted;
import csdc.bean.ProjectFee;
import csdc.service.IPostService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项
 * @author 肖雅，王燕
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from PostEndinspection endi, PostEndinspection all_endi, PostGranted gra, PostMember mem  " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from PostEndinspection endi, PostEndinspection all_endi, PostGranted gra, PostMember mem " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "postEndinspectionPage";//列表页面名称
	private static final String ENDINSPECTION_CLASS_NAME = "PostEndinspection";
	private static final String MIDINSPECTION_CLASS_NAME = "PostMidinspection";
	private static final String GRANTED_CLASS_NAME = "PostGranted";
	private static final String FUNDING_CLASS_NAME = "PostFunding";
	private static final String BUSINESS_TYPE = "033";//后期资助项目结项业务编号
	private static final String PROJECT_TYPE = "post";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;
	private IPostService postService;
	private PostEndinspection endinspection;//结项
	
	public String pageName() {
		return EndinspectionApplyAction.PAGE_NAME;
	}
	public String endinspectionClassName() {
		return EndinspectionApplyAction.ENDINSPECTION_CLASS_NAME;
	}
	public String midinspectionClassName() {
		return EndinspectionApplyAction.MIDINSPECTION_CLASS_NAME;
	}
	public String grantedClassName() {
		return EndinspectionApplyAction.GRANTED_CLASS_NAME;
	}
	public String fundingClassName() {
		return EndinspectionApplyAction.FUNDING_CLASS_NAME;
	}
	public String projectType(){
		return EndinspectionApplyAction.PROJECT_TYPE;
	}
	public String businessType(){
		return EndinspectionApplyAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return EndinspectionApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return EndinspectionApplyAction.CHECK_GRANTED_FLAG;
	}
	public String listHql2(){
		return EndinspectionApplyAction.HQL2;
	}
	public String listHql3(){
		return EndinspectionApplyAction.HQL3;
	}

	/**
	 * 文件下载流(后续word宏做出来后可以合并到父action中)
	 */
	public InputStream getTargetTemplate() throws Exception{
		String filename = "/data/template/general/2011endinspection.exe";
		filepath = new String("教育部人文社会科学研究项目结项报告书.exe".getBytes(), "ISO-8859-1");
		return ServletActionContext.getServletContext().getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验(后续word宏做出来后可以合并到父action中)
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String filename = "/data/template/general/2011endinspection.exe";
		if(null == ServletActionContext.getServletContext().getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加结项信息
	 * @author 王燕
	 */
	@Transactional
	public String add() {
		if(!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		PostGranted granted = (PostGranted) this.dao.query(PostGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new PostEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setGranted(granted);
		endinspection = (PostEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加结项申请的检验
	 * @author 王燕
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 修改结项申请的检验
	 * @author 王燕
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	public void validateSubmit(){
		this.validateEdit(3);
	}
	
	/**
	 * 编辑结项校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 王燕
	 */
	@SuppressWarnings("unchecked")
	public void validateEdit(int type){
		String info ="";
		info = this.doWithValidateEdit(type);
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 准备添加结项结果
	 * @author 王燕
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toAddResult(){
		endDate = new Date();
		Map session = ActionContext.getContext().getSession();
		endinspection = (PostEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		session.put("isApplyExcellent", endinspection.getIsApplyExcellent());
		endCertificate = this.postService.getDefaultEndCertificate(endinspectionClassName());
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.postService.checkIfUnderControl(loginer, this.postService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		PostGranted granted = (PostGranted) this.dao.query(PostGranted.class, this.projectid);
		endinspection = (PostEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		endinspection = (PostEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		endinspection.setProjectFee(projectFeeEnd);
		dao.modify(endinspection);
		
		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
			if (postFunding != null) {
				//结项则添加结项拨款申请，金额默认为批准经费的10%
//				postFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				postFunding.setStatus(0);
				dao.modify(postFunding);
			}else {
				PostFunding newPostFunding = new PostFunding();
//				newPostFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				newPostFunding.setStatus(0);
				newPostFunding.setType(3);
				newPostFunding.setGranted(granted);
				newPostFunding.setGrantedId(granted.getId());
				newPostFunding.setProjectType(granted.getProjectType());
				dao.add(newPostFunding);	
			}
		}	
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果校验
	 * @author 王燕
	 */
	public void validateAddResult(){
		validateEditResult(1);
	}
	
	/**
	 * 准备修改结项结果
	 * @author 王燕
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModifyResult(){
		PostEndinspection endinspection;
		if(modifyFlag == 0){
			endinspection = (PostEndinspection)this.postService.getCurrentPendingImpEndinspectionByGrantedId(this.projectid);
		}else{
			endinspection = (PostEndinspection)this.postService.getCurrentEndinspectionByGrantedId(this.projectid);
		}
		this.doWithToModifyResult(endinspection);
		Map session = ActionContext.getContext().getSession();
		session.put("isApplyExcellent", endinspection.getIsApplyExcellent());
		return SUCCESS;
	}
	
	/**
	 * 修改结项结果校验
	 * @author 王燕
	 */
	public void validateModifyResult(){
		validateEditResult(2);
	}
	
	/**
	 * 提交结项结果校验
	 * @author 王燕
	 */
	public void validateSubmitResult(){
		validateEditResult(3);
	}
	
	/**
	 * 编辑结项结果校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 王燕
	 */
	@SuppressWarnings("unchecked")
	public void validateEditResult(int type){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			PostGranted granted = (PostGranted) this.dao.query(PostGranted.class, projectid);
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
		if(type == 1){
			if(this.postService.getPassReviewByGrantedId(this.projectid) == null){//鉴定未通过
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_REVIEW_PASS_NULL);
			}
		}
		if(type == 1 || type == 3){//添加或提交
			if(this.postService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
			}
		}
		info += this.doWithValidateEditResult(type);
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	public PostEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(PostEndinspection endinspection) {
		this.endinspection = endinspection;
	}
	public IPostService getPostService() {
		return postService;
	}
	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
}