package csdc.action.project.special;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.SpecialEndinspection;
import csdc.bean.SpecialFunding;
import csdc.bean.SpecialGranted;
import csdc.bean.ProjectFee;
import csdc.service.ISpecialService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 专项任务项目结项子类管理
 * 实现了父类所有的抽象方法及专项任务项目结项申请特有方法
 * @author 雷达,余潜玉
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from SpecialEndinspection endi, SpecialEndinspection all_endi, SpecialGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so , SpecialMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from SpecialEndinspection endi, SpecialEndinspection all_endi, SpecialGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, SpecialMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "specialEndinspectionPage";//列表页面名称
	private static final String ENDINSPECTION_CLASS_NAME = "SpecialEndinspection";//专项任务项目结项类类名
	private static final String MIDINSPECTION_CLASS_NAME = "SpecialMidinspection";//专项任务项目中检类类名
	private static final String GRANTED_CLASS_NAME = "SpecialGranted";//专项任务项目立项类类名
	private static final String FUNDING_CLASS_NAME = "SpecialFunding";
	private static final String BUSINESS_TYPE = "013";//专项任务项目结项申请对应的业务类型名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final int CHECK_APPLICATION_FLAG = 29;//判断专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//专项任务项目立项是否在管辖范围内的标志位
	private ISpecialService specialService;
	private SpecialEndinspection endinspection;//结项
	
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
	 * 添加专项任务项目结项申请，设置上传的文件路径
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception {
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new SpecialEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection = (SpecialEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加录入的专项任务项目结项结果
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
		
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, this.projectid);
		endinspection = new SpecialEndinspection();
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.specialService.MutipleToFormat(personExtService.regularNames(this.endMember)));
		endinspection = (SpecialEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		//设置成果相关信息
		endinspection = (SpecialEndinspection) this.doWithProductInfo(endProductInfo, endinspection);
		dao.add(endinspection);
		
//		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
//			
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			SpecialFunding specialFunding = (SpecialFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//			if (specialFunding != null) {
//				//结项通过则添加结项拨款申请，金额默认为批准经费的10%
////				specialFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				specialFunding.setStatus(0);
//				dao.modify(specialFunding);
//			}else {
//				SpecialFunding newSpecialFunding = new SpecialFunding();
////				newSpecialFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				newSpecialFunding.setStatus(0);
//				newSpecialFunding.setType(3);
//				newSpecialFunding.setGranted(granted);
//				newSpecialFunding.setGrantedId(granted.getId());
//				newSpecialFunding.setProjectType(granted.getProjectType());
//				dao.add(newSpecialFunding);
//			}
//		}
		return SUCCESS;
	}

	public SpecialEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(SpecialEndinspection endinspection) {
		this.endinspection = endinspection;
	}
	public ISpecialService getSpecialService() {
		return specialService;
	}
	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	
}
