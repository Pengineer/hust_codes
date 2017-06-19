package csdc.action.project.general;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.ProjectFee;
import csdc.service.IGeneralService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 一般项目结项子类管理
 * 实现了父类所有的抽象方法及一般项目结项申请特有方法
 * @author 雷达,余潜玉
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from GeneralEndinspection endi, GeneralEndinspection all_endi, GeneralGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so , GeneralMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from GeneralEndinspection endi, GeneralEndinspection all_endi, GeneralGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, GeneralMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "generalEndinspectionPage";//列表页面名称
	private static final String ENDINSPECTION_CLASS_NAME = "GeneralEndinspection";//一般项目结项类类名
	private static final String MIDINSPECTION_CLASS_NAME = "GeneralMidinspection";//一般项目中检类类名
	private static final String GRANTED_CLASS_NAME = "GeneralGranted";//一般项目立项类类名
	private static final String FUNDING_CLASS_NAME = "GeneralFunding";
	private static final String BUSINESS_TYPE = "013";//一般项目结项申请对应的业务类型名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//一般项目立项是否在管辖范围内的标志位
	private IGeneralService generalService;
	private GeneralEndinspection endinspection;//结项
	
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
	public String businessType() {
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
	 * 添加一般项目结项申请，设置上传的文件路径
	 */
	@Transactional
	public String add() {
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new GeneralEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection = (GeneralEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加录入的一般项目结项结果
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, this.projectid);
		endinspection = new GeneralEndinspection();
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.generalService.MutipleToFormat(personExtService.regularNames(this.endMember)));
		endinspection = (GeneralEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		//设置成果相关信息
		endinspection = (GeneralEndinspection) this.doWithProductInfo(endProductInfo, endinspection);
		dao.add(endinspection);
		
		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
			
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
			if (generalFunding != null) {
				//结项通过则添加结项拨款申请，金额默认为批准经费的10%
//				generalFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				generalFunding.setStatus(0);
				dao.modify(generalFunding);
			}else {
				GeneralFunding newGeneralFunding = new GeneralFunding();
//				newGeneralFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
				newGeneralFunding.setStatus(0);
				newGeneralFunding.setType(3);
				newGeneralFunding.setGranted(granted);
				newGeneralFunding.setGrantedId(granted.getId());
				newGeneralFunding.setProjectType(granted.getProjectType());
				dao.add(newGeneralFunding);
			}
		}
		return SUCCESS;
	}

	public GeneralEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(GeneralEndinspection endinspection) {
		this.endinspection = endinspection;
	}
	public IGeneralService getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	
}
