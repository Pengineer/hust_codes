package csdc.action.project.instp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.project.instp.EndinspectionApplyAction;
import csdc.bean.InstpEndinspection;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.ProjectFee;
import csdc.service.IInstpService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项
 * @author 余潜玉，肖雅
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from InstpEndinspection endi, InstpEndinspection all_endi, InstpGranted gra, InstpMember mem  " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from InstpEndinspection endi, InstpEndinspection all_endi, InstpGranted gra, InstpMember mem " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String ENDINSPECTION_CLASS_NAME = "InstpEndinspection";
	private static final String MIDINSPECTION_CLASS_NAME = "InstpMidinspection";
	private static final String GRANTED_CLASS_NAME = "InstpGranted";
	private static final String FUNDING_CLASS_NAME = "InstpFunding";
	private static final String PAGE_NAME = "instpEndinspactionPage";//列表页面名称
	private static final String BUSINESS_TYPE = "023";//基地项目结项业务编号
	private static final String PROJECT_TYPE = "instp";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	private IInstpService instpService;
	private InstpEndinspection endinspection;//结项对象
	
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
	 * @author 余潜玉
	 */
	public InputStream getTargetTemplate() throws Exception{
		String filename = "/data/template/general/tpl_gen_end_2008.doc";
		filepath = new String("教育部人文社会科学研究项目结项报告书.doc".getBytes(), "ISO-8859-1");
		return ServletActionContext.getServletContext().getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验(后续word宏做出来后可以合并到父action中)
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String filename = "/data/template/general/tpl_gen_end_2008.doc";
		if(null == ServletActionContext.getServletContext().getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加结项申请信息
	 * @author 余潜玉
	 */
	@Transactional
	public String add() {
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new InstpEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection = (InstpEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, this.projectid);
		endinspection = new InstpEndinspection();
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.instpService.MutipleToFormat(personExtService.regularNames(this.endMember)));
		endinspection = (InstpEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		//设置成果相关信息
		endinspection = (InstpEndinspection) this.doWithProductInfo(endProductInfo, endinspection);
		dao.add(endinspection);
		
		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
			
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
			if (instpFunding != null) {
				//结项则添加结项拨款申请，金额默认为批准经费的10%
//				instpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				instpFunding.setStatus(0);
				dao.modify(instpFunding);
			}else {
				InstpFunding newInstpFunding = new InstpFunding();
//				newInstpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				newInstpFunding.setStatus(0);
				newInstpFunding.setType(3);
				newInstpFunding.setGranted(granted);
				newInstpFunding.setGrantedId(granted.getId());
				newInstpFunding.setProjectType(granted.getProjectType());
				dao.add(newInstpFunding);	
			}
		}
		return SUCCESS;
	}
	
	public InstpEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(InstpEndinspection endinspection) {
		this.endinspection = endinspection;
	}
	public IInstpService getInstpService() {
		return instpService;
	}
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
}