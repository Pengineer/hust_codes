package csdc.action.project.general;



import org.springframework.transaction.annotation.Transactional;


import csdc.bean.GeneralGranted;
import csdc.bean.GeneralAnninspection;
import csdc.bean.ProjectFee;
import csdc.service.IGeneralService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 年检
 * @author 肖雅
 */
public class AnninspectionApplyAction extends csdc.action.project.AnninspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from GeneralAnninspection all_ann, GeneralAnninspection ann left join ann.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so, GeneralMember mem " +
		"where ann.granted.id = gra.id and all_ann.granted.id = gra.id and app.id = mem.application.id ";
	//研究人员使用
	private static final String HQL3 = "from GeneralAnninspection ann, GeneralAnninspection all_ann, GeneralMember mem, GeneralGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
	private static final String PAGE_NAME = "generalAnninspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";
	private static final String BUSINESS_TYPE = "015";//一般项目年检业务编号
	private static final int CHECK_APPLICATION_FLAG = 14;
	private static final int CHECK_GRANTED_FLAG = 21;
	private IGeneralService generalService;

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
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
//		ProjectFee projectFeeAnn = new ProjectFee();
//		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		projectFeeAnn.setType(3);
		dao.add(projectFeeAnn);
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		GeneralAnninspection anninspection = new GeneralAnninspection();
		anninspection.setProjectFee(projectFeeAnn);
		granted.addAnninspection(anninspection);
		anninspection = (GeneralAnninspection)this.doWithAdd(anninspection);
		annFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加年检结果
	 * @author 肖雅
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeAnn = new ProjectFee();
		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		if (projectFeeAnn != null) {
			projectFeeAnn.setType(3);
			dao.add(projectFeeAnn);
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, this.projectid);
		GeneralAnninspection anninspection = new GeneralAnninspection();
		anninspection.setGranted(granted);
		anninspection.setProjectFee(projectFeeAnn);
		anninspection = (GeneralAnninspection)this.doWithAddResult(anninspection);
		dao.add(anninspection);
		return SUCCESS;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
}
