package csdc.action.project.entrust;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.project.entrust.EndinspectionApplyAction;
import csdc.bean.EntrustEndinspection;
import csdc.bean.EntrustFunding;
import csdc.bean.EntrustGranted;
import csdc.bean.ProjectFee;
import csdc.service.IEntrustService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项
 * @author 肖雅
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from EntrustEndinspection endi, EntrustEndinspection all_endi, EntrustGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, EntrustMember mem " +
		"where app.id = mem.application.id and  endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from EntrustEndinspection endi, EntrustEndinspection all_endi, EntrustGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, EntrustMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	private static final String PAGE_NAME = "entrustEndinspectionPage";//列表页面名称
	private static final String ENDINSPECTION_CLASS_NAME = "EntrustEndinspection";
	private static final String MIDINSPECTION_CLASS_NAME = "EntrustMidinspection";
	private static final String GRANTED_CLASS_NAME = "EntrustGranted";
	private static final String FUNDING_CLASS_NAME = "EntrustFunding";
	private static final String BUSINESS_TYPE = "053";//委托应急课题结项业务编号
	private static final String PROJECT_TYPE = "entrust";
	private static final int CHECK_APPLICATION_FLAG = 27;
	private static final int CHECK_GRANTED_FLAG = 28;
	private IEntrustService entrustService;
	private EntrustEndinspection endinspection;//结项
	
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
	 * 添加结项信息，设置上传的文件路径
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception {
		if(!this.entrustService.checkIfUnderControl(loginer, this.entrustService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		EntrustGranted granted = (EntrustGranted) this.dao.query(EntrustGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new EntrustEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection = (EntrustEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果
	 * @author 肖雅
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.entrustService.checkIfUnderControl(loginer, this.entrustService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		EntrustGranted granted = (EntrustGranted) this.dao.query(EntrustGranted.class, this.projectid);
		endinspection = new EntrustEndinspection();
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.entrustService.MutipleToFormat(personExtService.regularNames(this.endMember)));
		endinspection = (EntrustEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		//设置成果相关信息
		endinspection = (EntrustEndinspection) this.doWithProductInfo(endProductInfo, endinspection);
		dao.add(endinspection);
		
//		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
//			
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//			if (entrustFunding != null) {
//				//结项则添加结项拨款申请，金额默认为批准经费的10%
////				entrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				entrustFunding.setStatus(0);
//				dao.modify(entrustFunding);
//			}else {
//				EntrustFunding newEntrustFunding = new EntrustFunding();
////				newEntrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				newEntrustFunding.setStatus(0);
//				newEntrustFunding.setType(3);
//				newEntrustFunding.setGranted(granted);
//				newEntrustFunding.setGrantedId(granted.getId());
//				newEntrustFunding.setProjectType(granted.getProjectType());
//				dao.add(newEntrustFunding);	
//			}
//		}
		return SUCCESS;
	}

	public EntrustEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(EntrustEndinspection endinspection) {
		this.endinspection = endinspection;
	}
	public IEntrustService getEntrustService() {
		return entrustService;
	}
	public void setEntrustService(IEntrustService entrustService) {
		this.entrustService = entrustService;
	}
	
}
