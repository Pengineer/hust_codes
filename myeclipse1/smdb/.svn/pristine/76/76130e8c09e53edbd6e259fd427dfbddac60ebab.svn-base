package csdc.action.project.general;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.ProjectFee;
import csdc.service.IGeneralService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 一般项目中检子类
 * 实现了父类所有的抽象方法及一般项目中检申请特有的方法
 * @author 雷达,余潜玉
 */
public class MidinspectionApplyAction extends csdc.action.project.MidinspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from GeneralMidinspection all_midi, GeneralMidinspection midi left join midi.granted gra " +
		"left join gra.application app left join gra.university uni left outer join gra.subtype so , GeneralMember mem " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from GeneralMidinspection midi, GeneralMidinspection all_midi, GeneralMember mem, GeneralGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
	private static final String PAGE_NAME = "generalMidinspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final String MIDINSPECTION_CLASS_NAME_TYPE = "GeneralMidinspection";
	private static final String BUSINESS_TYPE = "012";//一般项目中检申请对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//判断一般项目立项是否在管辖范围内的标志位
	private IGeneralService generalService;

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
	 * 添加一般项目中检申请
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception {
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeMid.setType(4);
		dao.add(projectFeeMid);
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		GeneralMidinspection midinspection = new GeneralMidinspection();
		midinspection.setProjectFee(projectFeeMid);
		granted.addMidinspection(midinspection);
		midinspection = (GeneralMidinspection)this.doWithAdd(midinspection);
		midFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加录入的一般项目中检结果
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeMid = new ProjectFee();
		projectFeeMid = this.doWithAddResultFee(projectFeeMid);
		if (projectFeeMid != null) {
			projectFeeMid.setType(4);
			dao.add(projectFeeMid);
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, this.projectid);
		GeneralMidinspection midinspection = new GeneralMidinspection();
		midinspection.setGranted(granted);
		midinspection.setProjectFee(projectFeeMid);
		midinspection = (GeneralMidinspection)this.doWithAddResult(midinspection);
		dao.add(midinspection);
		
//		if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//			if (generalFunding != null) {
//				//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////				generalFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//				generalFunding.setStatus(0);
//				dao.modify(generalFunding);
//			}else {
//				GeneralFunding newGeneralFunding = new GeneralFunding();
////				newGeneralFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//				newGeneralFunding.setStatus(0);
//				newGeneralFunding.setType(2);
//				newGeneralFunding.setGranted(granted);
//				newGeneralFunding.setGrantedId(granted.getId());
//				newGeneralFunding.setProjectType(granted.getProjectType());
//				dao.add(newGeneralFunding);
//			}
//		}
		
		return SUCCESS;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
	public String midinspectionClassName() {
		return MidinspectionApplyAction.MIDINSPECTION_CLASS_NAME_TYPE;
	}
}
