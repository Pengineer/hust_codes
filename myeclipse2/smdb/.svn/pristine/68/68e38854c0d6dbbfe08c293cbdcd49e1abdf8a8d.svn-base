package csdc.action.project.instp;



import org.springframework.transaction.annotation.Transactional;


import csdc.bean.InstpGranted;
import csdc.bean.InstpAnninspection;
import csdc.bean.ProjectFee;
import csdc.service.IInstpService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 年检
 * @author 王懿
 */
public class AnninspectionApplyAction extends csdc.action.project.AnninspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from InstpAnninspection all_ann, InstpAnninspection ann left join ann.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so, InstpMember mem " +
		"where ann.granted.id = gra.id and all_ann.granted.id = gra.id and app.id = mem.application.id ";
	//研究人员使用
	private static final String HQL3 = "from InstpAnninspection ann, InstpAnninspection all_ann, InstpMember mem, InstpGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
	private static final String PAGE_NAME = "instpAnninspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "instp";
	private static final String BUSINESS_TYPE = "025";//基地项目年检业务编号
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	private IInstpService instpService;

	public String pageName() {
		return AnninspectionApplyAction.PAGE_NAME;
	}
	public String projectType(){
		return AnninspectionApplyAction.PROJECT_TYPE;
	}
	public String businessType(){
		return AnninspectionApplyAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return AnninspectionApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return AnninspectionApplyAction.CHECK_GRANTED_FLAG;
	}
	public String listHql2(){
		return AnninspectionApplyAction.HQL2;
	}
	public String listHql3(){
		return AnninspectionApplyAction.HQL3;
	}
	
	/**
	 * 添加年检申请
	 */
	@Transactional
	public String add() {
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeAnn.setType(3);
		dao.add(projectFeeAnn);
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, projectid);
		InstpAnninspection anninspection = new InstpAnninspection();
		anninspection.setProjectFee(projectFeeAnn);
		granted.addAnninspection(anninspection);
		anninspection = (InstpAnninspection)this.doWithAdd(anninspection);
		annFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加年检结果
	 * @author 王懿
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeAnn = new ProjectFee();
		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		if (projectFeeAnn != null) {
			projectFeeAnn.setType(3);
			dao.add(projectFeeAnn);
		}
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, this.projectid);
		InstpAnninspection anninspection = new InstpAnninspection();
		anninspection.setGranted(granted);
		anninspection.setProjectFee(projectFeeAnn);
		anninspection = (InstpAnninspection)this.doWithAddResult(anninspection);
		dao.add(anninspection);
		return SUCCESS;
	}
	
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	public IInstpService getInstpService() {
		return instpService;
	}
	
}
