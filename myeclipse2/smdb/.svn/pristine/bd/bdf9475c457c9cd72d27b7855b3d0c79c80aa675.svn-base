package csdc.action.project.key;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMidinspection;
import csdc.bean.ProjectFee;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 中检
 * @author 雷达,肖雅
 */
public class MidinspectionApplyAction extends csdc.action.project.MidinspectionApplyAction  {
	private static final long serialVersionUID = 1L;
	//管理人员使用
	private static final String HQL2 = "from KeyMidinspection all_midi, KeyMidinspection midi left join midi.granted gra " +
		"left join gra.application app left join gra.university uni left outer join app.researchType so , KeyMember mem " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id ";
	
	//管理人员使用
	/*private static final String HQL2 = "from KeyMidinspection midi left outer join midi.granted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where 1=1 not exists" +
		"(select 1 from KeyMidinspection genmid where genmid.granted.id=gra.id and genmid.applicantSubmitDate>midi.applicantSubmitDate)";*/
	
	//研究人员使用
	private static final String HQL3 = "from KeyMidinspection midi, KeyMidinspection all_midi, KeyMember mem, KeyGranted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join app.researchType so " +
		"where app.id = mem.application.id and midi.granted.id = gra.id and all_midi.granted.id = gra.id "; 
	
	//研究人员使用
	/*private static final String HQL3 = "from KeyMidinspection midi, KeyMember mem left outer join midi.granted gra " +
		"left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and not exists" +
		"(select 1 from KeyMidinspection genmid where genmid.granted.id=gra.id and genmid.applicantSubmitDate>midi.applicantSubmitDate)";*/
	
	private static final String PAGE_NAME = "keyMidinspectionPage";//列表页面名称
	private static final String PROJECT_TYPE = "key";
	private static final String BUSINESS_TYPE = "042";//重大攻关项目中检业务编号
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;

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
	 * 添加中检
	 */
	@Transactional
	public String add() {
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeMid.setType(4);
		dao.add(projectFeeMid);
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class, projectid);
		KeyMidinspection midinspection = new KeyMidinspection();
		midinspection.setProjectFee(projectFeeMid);
		granted.addMidinspection(midinspection);
		midinspection = (KeyMidinspection)this.doWithAdd(midinspection);
		midFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 添加中检结果
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeMid = new ProjectFee();
		projectFeeMid = this.doWithAddResultFee(projectFeeMid);
		if (projectFeeMid != null) {
			projectFeeMid.setType(4);
			dao.add(projectFeeMid);
		}
		KeyGranted granted = (KeyGranted) this.dao.query(KeyGranted.class, this.projectid);
		KeyMidinspection midinspection = new KeyMidinspection();
		midinspection.setGranted(granted);
		midinspection.setProjectFee(projectFeeMid);
		midinspection = (KeyMidinspection)this.doWithAddResult(midinspection);
		dao.add(midinspection);
		
		if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
			
			Map parmap = new HashMap();
			parmap.put("grantedId", granted.getId());
			KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
			if (keyFunding != null) {
				//中检通过则添加中检拨款申请，金额默认为批准经费的40%
//				keyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
				keyFunding.setStatus(0);
				dao.modify(keyFunding);
			}else {
				KeyFunding newKeyFunding = new KeyFunding();
//				newKeyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
				newKeyFunding.setStatus(0);
				newKeyFunding.setType(2);
				newKeyFunding.setGranted(granted);
				newKeyFunding.setGrantedId(granted.getId());
				newKeyFunding.setProjectType(granted.getProjectType());
				dao.add(newKeyFunding);
			}
		}
		return SUCCESS;
	}
}
