package csdc.action.project.special;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.ProjectFee;
import csdc.bean.SpecialGranted;
import csdc.bean.SpecialMidinspection;
import csdc.service.ISpecialService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 专项任务项目中检子类
 * 实现了父类所有的抽象方法及专项任务项目中检申请特有的方法
 * @author wangming
 */
public class MidinspectionApplyAction extends csdc.action.project.MidinspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from SpecialMidinspection all_midi, SpecialMidinspection midi left join midi.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so , SpecialMember mem " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from SpecialMidinspection midi, SpecialMidinspection all_midi, SpecialMember mem, SpecialGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
	private static final String PAGE_NAME = "specialMidinspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final String MIDINSPECTION_CLASS_NAME_TYPE = "SpecialMidinspection";
	private static final String BUSINESS_TYPE = "012";//专项任务项目中检申请对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 29;//判断专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//判断专项任务项目立项是否在管辖范围内的标志位
	private ISpecialService specialService;

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
	 * 添加专项任务项目中检申请
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception {
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeMid.setType(4);
		dao.add(projectFeeMid);
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, projectid);
		SpecialMidinspection midinspection = new SpecialMidinspection();
		midinspection.setProjectFee(projectFeeMid);
		granted.addMidinspection(midinspection);
		midinspection = (SpecialMidinspection)this.doWithAdd(midinspection);
		midFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加录入的专项任务项目中检结果
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeMid = new ProjectFee();
		projectFeeMid = this.doWithAddResultFee(projectFeeMid);
		if (projectFeeMid != null) {
			projectFeeMid.setType(4);
			dao.add(projectFeeMid);
		}
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, this.projectid);
		SpecialMidinspection midinspection = new SpecialMidinspection();
		midinspection.setGranted(granted);
		midinspection.setProjectFee(projectFeeMid);
		midinspection = (SpecialMidinspection)this.doWithAddResult(midinspection);
		dao.add(midinspection);
		
		return SUCCESS;
	}

	public ISpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	public String midinspectionClassName() {
		return MidinspectionApplyAction.MIDINSPECTION_CLASS_NAME_TYPE;
	}
}
