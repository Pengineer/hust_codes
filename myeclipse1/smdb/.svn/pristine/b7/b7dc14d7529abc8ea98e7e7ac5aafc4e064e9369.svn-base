package csdc.action.project.special;



import org.springframework.transaction.annotation.Transactional;


import csdc.bean.SpecialGranted;
import csdc.bean.SpecialAnninspection;
import csdc.bean.ProjectFee;
import csdc.service.ISpecialService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 专项任务项目年检申请管理
 * @author wangming
 */
public class AnninspectionApplyAction extends csdc.action.project.AnninspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from SpecialAnninspection all_ann, SpecialAnninspection ann left join ann.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so, SpecialMember mem " +
		"where ann.granted.id = gra.id and all_ann.granted.id = gra.id and app.id = mem.application.id ";
	//研究人员使用
	private static final String HQL3 = "from SpecialAnninspection ann, SpecialAnninspection all_ann, SpecialMember mem, SpecialGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
	private static final String PAGE_NAME = "specialAnninspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";
	private static final String BUSINESS_TYPE = "015";//专项任务项目年检业务编号
	private static final int CHECK_APPLICATION_FLAG = 29;
	private static final int CHECK_GRANTED_FLAG = 30;
	private ISpecialService specialService;

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
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception {
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
//		ProjectFee projectFeeAnn = new ProjectFee();
//		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		projectFeeAnn.setType(3);
		dao.add(projectFeeAnn);
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, projectid);
		SpecialAnninspection anninspection = new SpecialAnninspection();
		anninspection.setProjectFee(projectFeeAnn);
		granted.addAnninspection(anninspection);
		anninspection = (SpecialAnninspection)this.doWithAdd(anninspection);
		annFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加年检结果
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeAnn = new ProjectFee();
		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		if (projectFeeAnn != null) {
			projectFeeAnn.setType(3);
			dao.add(projectFeeAnn);
		}
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, this.projectid);
		SpecialAnninspection anninspection = new SpecialAnninspection();
		anninspection.setGranted(granted);
		anninspection.setProjectFee(projectFeeAnn);
		anninspection = (SpecialAnninspection)this.doWithAddResult(anninspection);
		dao.add(anninspection);
		return SUCCESS;
	}

	public ISpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
}
