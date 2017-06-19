package csdc.action.project.key;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.project.key.EndinspectionApplyAction;
import csdc.bean.KeyEndinspection;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.ProjectFee;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 结项
 * @author 肖雅
 */
public class EndinspectionApplyAction extends csdc.action.project.EndinspectionApplyAction {

	private static final long serialVersionUID = -4553441992499898172L;
	//管理人员使用
	private static final String HQL2 = "from KeyEndinspection endi, KeyEndinspection all_endi, KeyGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so , KeyMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	
	/*//管理人员使用
	private static final String HQL2 = "from KeyEndinspection endi left outer join endi.granted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where 1=1 and not exists" +
		"(select 1 from KeyEndinspection genend where genend.granted.id=gra.id and genend.applicantSubmitDate>endi.applicantSubmitDate)";*/
	
	//研究人员使用
	private static final String HQL3 = "from KeyEndinspection endi, KeyEndinspection all_endi, KeyGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so, KeyMember mem " +
		"where app.id = mem.application.id and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
	
	/*//研究人员使用
	private static final String HQL3 = "from KeyEndinspection endi left outer join endi.granted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so, KeyMember mem " +
		"where app.id = mem.application.id and not exists" +
		"(select 1 from KeyEndinspection genend where genend.granted.id=gra.id and genend.applicantSubmitDate>endi.applicantSubmitDate)";*/

	private static final String PAGE_NAME = "keyEndinspectionPage";//列表页面名称
	private static final String ENDINSPECTION_CLASS_NAME = "KeyEndinspection";
	private static final String MIDINSPECTION_CLASS_NAME = "KeyMidinspection";
	private static final String GRANTED_CLASS_NAME = "KeyGranted";
	private static final String FUNDING_CLASS_NAME = "KeyFunding";
	private static final String BUSINESS_TYPE = "043";//重大攻关项目结项业务编号
	private static final String PROJECT_TYPE = "key";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private KeyEndinspection endinspection;//结项
	
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
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeEnd.setType(5);
		dao.add(projectFeeEnd);
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class,projectid);
		entityId = granted.getApplication().getId();
		endinspection = new KeyEndinspection();
		granted.addEndinspection(endinspection);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection = (KeyEndinspection)this.doWithAdd(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeEnd = new ProjectFee();
		projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
		if (projectFeeEnd != null) {
			projectFeeEnd.setType(5);
			dao.add(projectFeeEnd);
		}
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class, this.projectid);
		endinspection = new KeyEndinspection();
		endinspection.setGranted(granted);
		endinspection.setProjectFee(projectFeeEnd);
		endinspection.setMemberName(this.keyService.MutipleToFormat(personExtService.regularNames(this.endMember)));
		endinspection = (KeyEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 1);
		//设置成果相关信息
		endinspection = (KeyEndinspection) this.doWithProductInfo(endProductInfo, endinspection);
		dao.add(endinspection);
		
//		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
//			
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//			if (keyFunding != null) {
//				//结项则添加结项拨款申请，金额默认为批准经费的10%
////				keyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				keyFunding.setStatus(0);
//				dao.modify(keyFunding);
//			}else {
//				KeyFunding newKeyFunding = new KeyFunding();
////				newKeyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//				newKeyFunding.setStatus(0);
//				newKeyFunding.setType(3);
//				newKeyFunding.setGranted(granted);
//				newKeyFunding.setGrantedId(granted.getId());
//				newKeyFunding.setProjectType(granted.getProjectType());
//				dao.add(newKeyFunding);	
//			}
//		}
		return SUCCESS;
	}

	public KeyEndinspection getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(KeyEndinspection endinspection) {
		this.endinspection = endinspection;
	}
}
