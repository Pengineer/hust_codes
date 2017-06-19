package csdc.action.project.instp;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMidinspection;
import csdc.bean.ProjectFee;
import csdc.service.IInstpService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 中检
 * @author 王燕、肖雅
 */
public class MidinspectionApplyAction extends csdc.action.project.MidinspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from InstpMidinspection midi, InstpMidinspection all_midi, InstpGranted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.institute ins left outer join gra.subtype so , InstpMember mem " +
		"where app.id = mem.application.id and  midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from InstpMidinspection midi, InstpMidinspection all_midi, InstpMember mem, InstpGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
	private static final String PAGE_NAME = "instpMidinspectionPage";//列表页面名称
	private static final String BUSINESS_TYPE = "022";//基地项目中检业务编号
	private static final String PROJECT_TYPE = "instp";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	private IInstpService instpService;

	public String pageName() {
		return MidinspectionApplyAction.PAGE_NAME;
	}
	public String projectType(){
		return MidinspectionApplyAction.PROJECT_TYPE;
	}
	public String businessType(){
		return MidinspectionApplyAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return MidinspectionApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return MidinspectionApplyAction.CHECK_GRANTED_FLAG;
	}
	public String listHql2(){
		return MidinspectionApplyAction.HQL2;
	}
	public String listHql3(){
		return MidinspectionApplyAction.HQL3;
	}
	
	/**
	 * 添加中检申请
	 * @author 肖雅
	 */
	@Transactional
	public String add() {
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeMid.setType(4);
		dao.add(projectFeeMid);
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, projectid);
		InstpMidinspection midinspection = new InstpMidinspection();
		midinspection.setProjectFee(projectFeeMid);
		granted.addMidinspection(midinspection);
		midinspection = (InstpMidinspection)this.doWithAdd(midinspection);
		midFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加中检结果
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeMid = new ProjectFee();
		projectFeeMid = this.doWithAddResultFee(projectFeeMid);
		if (projectFeeMid != null) {
			projectFeeMid.setType(4);
			dao.add(projectFeeMid);
		}
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, this.projectid);
		InstpMidinspection midinspection = new InstpMidinspection();
		midinspection.setGranted(granted);
		midinspection.setProjectFee(projectFeeMid);
		midinspection = (InstpMidinspection)this.doWithAddResult(midinspection);
		dao.add(midinspection);
		
		if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
			
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
			if (instpFunding != null) {
				//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//				instpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
				instpFunding.setStatus(0);
				dao.modify(instpFunding);
			}else {
				InstpFunding newInstpFunding = new InstpFunding();
//				newInstpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
				newInstpFunding.setStatus(0);
				newInstpFunding.setType(2);
				newInstpFunding.setGranted(granted);
				newInstpFunding.setGrantedId(granted.getId());
				newInstpFunding.setProjectType(granted.getProjectType());
				dao.add(newInstpFunding);
			}
		}
		return SUCCESS;
	}
	/**
	 * 文件下载流(后续word宏做出来后可以合并到父action中)
	 * @author 余潜玉
	 */
	public InputStream getTargetTemplate() throws Exception{
		String filename = "/data/template/general/tpl_gen_mid_2008.doc";
//		String filename = "/data/template/general/2011midinspection.exe";
		filepath = new String("教育部人文社会科学研究项目中检报告书.exe".getBytes(), "ISO-8859-1");
		return ServletActionContext.getServletContext().getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验(后续word宏做出来后可以合并到父action中)
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String filename = "/data/template/general/tpl_gen_mid_2008.doc";
//		String filename = "/data/template/general/2011midinspection.exe";
		if(null == ServletActionContext.getServletContext().getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		return SUCCESS;
	}
	public IInstpService getInstpService() {
		return instpService;
	}

	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
}
