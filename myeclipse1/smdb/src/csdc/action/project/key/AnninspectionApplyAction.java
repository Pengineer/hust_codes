package csdc.action.project.key;



import org.springframework.transaction.annotation.Transactional;


import csdc.bean.KeyGranted;
import csdc.bean.KeyAnninspection;
import csdc.bean.ProjectFee;
import csdc.service.IKeyService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 年检
 * @author 王懿
 */
public class AnninspectionApplyAction extends csdc.action.project.AnninspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from KeyAnninspection ann, KeyAnninspection all_ann left join ann.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so , KeyMember mem " +
		"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from KeyAnninspection ann, KeyAnninspection all_ann, KeyMember mem, KeyGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and ann.granted.id = gra.id and all_ann.granted.id = gra.id "; 
	private static final String PAGE_NAME = "keyAnninspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "key";
	private static final String BUSINESS_TYPE = "045";//重大攻关项目年检业务编号
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private IKeyService keyService;

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
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeAnn.setType(3);
		dao.add(projectFeeAnn);
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class, projectid);
		KeyAnninspection anninspection = new KeyAnninspection();
		anninspection.setProjectFee(projectFeeAnn);
		granted.addAnninspection(anninspection);
		anninspection = (KeyAnninspection)this.doWithAdd(anninspection);
		annFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加年检结果
	 * @author 王懿
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeAnn = new ProjectFee();
		projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
		if (projectFeeAnn != null) {
			projectFeeAnn.setType(3);
			dao.add(projectFeeAnn);
		}
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class, this.projectid);
		KeyAnninspection anninspection = new KeyAnninspection();
		anninspection.setGranted(granted);
		anninspection.setProjectFee(projectFeeAnn);
		anninspection = (KeyAnninspection)this.doWithAddResult(anninspection);
		dao.add(anninspection);
		return SUCCESS;
	}
	
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}
	public IKeyService getKeyService() {
		return keyService;
	}
	
}
