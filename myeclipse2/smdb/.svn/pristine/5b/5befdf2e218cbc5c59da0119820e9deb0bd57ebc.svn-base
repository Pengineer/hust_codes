package csdc.action.project.key;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.KeyGranted;
import csdc.bean.KeyFunding;
import csdc.bean.KeyApplication;
import csdc.bean.KeyMember;
import csdc.bean.KeyTopicSelection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * @author 肖雅
 */
public class ApplicationGrantedAction extends csdc.action.project.ApplicationGrantedAction {

	private static final long serialVersionUID = -700148736686965249L;
//	//管理人员使用
//	private static final String HQL1 = "select app.id, gra.id, gra.number, gra.name, gra.applicantId, " +
//		"gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, " +
//		"gra.approveFee, gra.status from KeyGranted gra left outer join gra.application app "+ 
//		"left outer join gra.university uni left outer join gra.subtype so left outer join app.topic top " +
//		"where 1=1";
//	//研究人员使用
//	private static final String HQL2 = "select app.id, gra.id, gra.number, gra.name, gra.applicantId, " +
//		"gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, " +
//		"gra.approveFee, gra.status from KeyGranted gra left outer join gra.application app "+ 
//		"left outer join gra.university uni left outer join app.topic top left outer join gra.subtype so, KeyMember mem " +
//		"where mem.application.id = app.id ";
	//管理人员使用
	private static final String HQL2 = "from KeyGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.researchType so, KeyMember mem where mem.application.id = app.id " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 ";
	//研究人员使用
	private static final String HQL3 = "from KeyGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.researchType so, KeyMember mem " +
		"where mem.application.id = app.id ";
	private static final String PAGE_NAME = "keyGrantedPage";//列表页面名称
	private static final String GRANTED_CLASS_NAME = "KeyGranted";
	private static final String PROJECT_TYPE = "key";
	private static final String BUISINESS_TYPE = "041";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	private KeyGranted granted;//项目对象
	private KeyApplication application1;//项目招标对象
	private List<KeyMember> rms;//项目成员

	public String pageName() {
		return ApplicationGrantedAction.PAGE_NAME;
	}
	public String projectType(){
		return ApplicationGrantedAction.PROJECT_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationGrantedAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationGrantedAction.CHECK_GRANTED_FLAG;
	}
	public String grantedClassName(){
		return ApplicationGrantedAction.GRANTED_CLASS_NAME;
	}
	public String businessType(){
		return ApplicationGrantedAction.BUISINESS_TYPE;
	}
	public String listHql2(){
		return ApplicationGrantedAction.HQL2;
	}
	public String listHql3(){
		return ApplicationGrantedAction.HQL3;
	}
	

	/**
	 * 添加项目立项计划书和经费申请
	 */
	@Transactional
	public String addGra() {
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeGranted.setType(2);
		dao.add(projectFeeGranted);
		granted = (KeyGranted) this.dao.query(KeyGranted.class, projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (KeyGranted)this.doWithAddOrMdoify(1,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加项目立项计划书和经费申请
	 */
	@Transactional
	public String modifyGra() {
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		granted = (KeyGranted) this.dao.query(KeyGranted.class, projectid);
		ProjectFee oldprojectFeeGranted = null;
		if (granted.getProjectFee() != null) {
			oldprojectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			oldprojectFeeGranted = this.projectService.updateProjectFee(oldprojectFeeGranted,projectFeeGranted);
			dao.modify(oldprojectFeeGranted);
		}else {
			oldprojectFeeGranted = this.projectService.setProjectFee(projectFeeGranted);
			oldprojectFeeGranted.setType(2);
			dao.add(oldprojectFeeGranted);
		}
		granted.setProjectFee(oldprojectFeeGranted);
		granted = (KeyGranted)this.doWithAddOrMdoify(2,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加录入的项目立项计划结果
	 * @author yangfq
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.keyService.checkIfUnderControl(loginer, this.keyService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeGranted = new ProjectFee();
		projectFeeGranted = this.doWithAddResultFee(projectFeeGranted);
		if (projectFeeGranted != null) {
			projectFeeGranted.setType(2);
			dao.add(projectFeeGranted);
		}
		granted = (KeyGranted) this.dao.query(KeyGranted.class, this.projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (KeyGranted)this.doWithAddResult(granted);
		dao.add(granted);
		return SUCCESS;
	}

	/**
	 * 添加重大攻关项目
	 */
	@Transactional
	public String add(){
		if(addflag == 1){//从已申请未中标项目中选择
			application1 = (KeyApplication)this.keyService.getApplicationFetchDetailByAppId(entityId);
			application1.setFinalAuditResult(2);//已中标
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
			granted.setApplication(application1);
			granted.setNumber(granted.getNumber().trim());//批准号
			
			projectFeeGranted = (ProjectFee)this.keyService.setProjectFee(projectFeeGranted);
			projectFeeGranted.setType(2);
			dao.add(projectFeeGranted);
			granted.setProjectFee(projectFeeGranted);
			
			granted.setStatus(1);
			granted.setIsImported(1);
			granted.setMemberGroupNumber(1);
			granted = (KeyGranted)this.keyService.setGrantedInfoFromAppForImported(application1, granted);
			this.dao.modify(application1);
			this.dao.add(granted);
			KeyFunding keyFunding = new KeyFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			keyFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			keyFunding.setGranted(granted);
			keyFunding.setGrantedId(granted.getId());
			keyFunding.setProjectType(application1.getType());
			keyFunding.setStatus(0);
			keyFunding.setType(1);
			dao.add(keyFunding);
		}else{//新建
			//申报信息
			projectFeeApply = (ProjectFee)this.keyService.setProjectFee(projectFeeApply);
			projectFeeApply.setType(1);
			dao.add(projectFeeApply);
			application1 = (KeyApplication)this.keyService.setAppBaseInfoFromApp(application1);
			application1.setProjectFee(projectFeeApply);
			//选题信息
			topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
			application1.setTopicSelection(topicSelection);
			application1.setName(topicSelection.getName());
			application1.setFinalAuditResult(2);//已中标
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
			application1.setIsImported(1);
			//中标信息
			if(granted != null){
				projectFeeGranted = (ProjectFee)this.keyService.setProjectFee(projectFeeGranted);
				projectFeeGranted.setType(2);
				dao.add(projectFeeGranted);
				granted.setProjectFee(projectFeeGranted);
				granted.setNumber(granted.getNumber().trim());
				granted.setApplication(application1);
				granted.setMemberGroupNumber(1);
				granted.setStatus(1);
				granted.setIsImported(1);
			}else{
				return INPUT;
			}
			//从成员列表中获取申请人信息
			if(rms!=null && rms.size()>0){
				String applicantId = "";
				String applicantName = "";
				for(int i=0; i<rms.size(); i++){
					KeyMember member = rms.get(i);
					if(member.getIsDirector() == 1){//主要负责人
						Object[] idAndName = this.keyService.getAppDirectorIdAndName(member);
						if(idAndName != null){
							applicantId = applicantId + idAndName[0] + "; ";
							applicantName = applicantName + idAndName[1] + "; ";
						}
					}
				}
				if(!applicantId.equals("") && applicantId.contains("; ")){
					applicantId = applicantId.substring(0, applicantId.lastIndexOf("; "));
				}
				if(!applicantName.equals("") && applicantName.contains("; ")){
					applicantName = applicantName.substring(0, applicantName.lastIndexOf("; "));
				}
				application1.setApplicantId(applicantId);
				application1.setApplicantName(applicantName);
			}else{//没有成员信息
				return INPUT;
			}
			//设置项目所在机构信息
			application1 = (KeyApplication)this.keyService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
			//保存上传的文件
			String groupId = "file_add";
			application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
			//成员信息
			if(rms!=null && rms.size()>0){
				for (int i=0; i<rms.size(); i++){
					KeyMember member = rms.get(i);
					member = (KeyMember)this.keyService.setMemberInfoFromMember(member);
					member.setMemberSn(i + 1);
					member.setApplication(application1);//项目申请
					rms.set(i, member);
				}
			}
			entityId = dao.add(application1);
			application1 = (KeyApplication)this.keyService.getApplicationFetchDetailByAppId(entityId);
			granted = (KeyGranted)this.keyService.setGrantedInfoFromAppForImported(application1, granted);
			dao.add(granted);
			KeyFunding keyFunding = new KeyFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			keyFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			keyFunding.setGranted(granted);
			keyFunding.setGrantedId(granted.getId());
			keyFunding.setProjectType(application1.getType());
			keyFunding.setStatus(0);
			keyFunding.setType(1);
			dao.add(keyFunding);
			for(Object rm : rms){
				dao.add(rm);
			}
			keyService.refreshMemberSn(entityId, 1);
			uploadService.flush(groupId);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加时校验
	 */
	public void validateAdd(){
//		if(null==fileIds||fileIds.length==0){
//			this.addFieldError("file", ProjectInfo.ERROR_FILE_NULL);
//		}else if(fileIds.length > 1){
//			this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
//		}
		if(addflag == 1){//从已申请未中标项目中选择
			if(null == entityId || entityId.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_GRANTED_DELETE_NULL);
			}
			validateGrantedInfo();
		}else{//新建
			if(fileIds != null && fileIds.length > 1){
				this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
			}
			validateAppInfo();
			validateGrantedInfo();
			validateMemberInfo(1);
		}
	}

	/**
	 * 准备修改
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.keyService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (KeyApplication)dao.query(KeyApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		granted = (KeyGranted)keyService.getGrantedByAppId(entityId);
		if (granted != null) {
			if (granted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			}
		}
//		int groupNumber = granted.getMemberGroupNumber(); //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		rms = this.keyService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || granted==null || rms==null || rms.size()<=0){
			addActionError("项目立项信息为空或者申报信息为空或者项目成员信息为空");
			return ERROR;
		}else{
			deptInstFlag = this.keyService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				KeyMember member = rms.get(i);
				member = (KeyMember)this.keyService.setMemberPersonInfoFromMember(member);
				rms.set(i, member);
			}
			
			//文件上传
			flag = 1;
			
			//将已有附件加入文件组，在编辑页面显示
			String groupId = "file_" + application1.getId();
			uploadService.resetGroup(groupId);
			if (application1.getFile() != null) {
				String[] tempFileRealpath = application1.getFile().split("; ");
				//遍历要修改的已有的文件
				for (int i = 0; i < tempFileRealpath.length; i++) {
					String filePath = tempFileRealpath[i];
					String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
					if (fileRealpath != null) {
						uploadService.addFile(groupId, new File(fileRealpath));
					}
				}
			}
			
			return SUCCESS;			
		}
	}
	
	/**
	 * 重大攻关项目修改
	 */
	@Transactional
	public String modify(){
		if(!this.keyService.checkIfUnderControl(loginer, application1.getId().trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申报信息
		this.entityId = application1.getId();
		KeyApplication oldApplication = (KeyApplication)dao.query(KeyApplication.class, application1.getId());
		oldApplication = (KeyApplication)this.keyService.updateAppBaseInfoFromApp(oldApplication, application1);
		//选题信息
		topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
		application1.setTopicSelection(topicSelection);
		application1.setName(topicSelection.getName());
		ProjectFee oldProjectFeeApply = new ProjectFee();
		ProjectFee oldProjectFeeGranted = new ProjectFee();
		
		if (oldApplication.getProjectFee() != null) {
			oldProjectFeeApply = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFeeApply = this.keyService.updateProjectFee(oldProjectFeeApply,projectFeeApply);
			dao.modify(oldProjectFeeApply);
		}else {
			oldProjectFeeApply = this.keyService.setProjectFee(projectFeeApply);
			oldProjectFeeApply.setType(1);
			dao.add(oldProjectFeeApply); 
		}
		oldApplication.setProjectFee(oldProjectFeeApply);
		//中标信息
		KeyGranted oldGranted = (KeyGranted)this.keyService.getGrantedByAppId(application1.getId());
//		int groupNumber = oldGranted.getMemberGroupNumber(); //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(oldGranted==null){
			addActionError("项目立项信息为空");
			return ERROR;
		}
		oldGranted = (KeyGranted)this.keyService.updateGrantedInfoFromGranted(oldGranted, granted);
		if (oldGranted.getProjectFee() != null) {
			oldProjectFeeGranted = dao.query(ProjectFee.class, oldGranted.getProjectFee().getId());
			oldProjectFeeGranted = this.keyService.updateProjectFee(oldProjectFeeGranted,projectFeeGranted);
			dao.modify(oldProjectFeeGranted);
		}else {
			oldProjectFeeGranted = this.keyService.setProjectFee(projectFeeGranted);
			oldProjectFeeGranted.setType(2);
			dao.add(oldProjectFeeGranted); 
		}
		oldGranted.setProjectFee(oldProjectFeeGranted);
		Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
		oldApplication.setFinalAuditDate(approveDate);
		oldGranted.setApplication(oldApplication);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			KeyMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.keyService.getAppDirectorIdAndName(member);
				if(idAndName != null){
					applicantId = applicantId + idAndName[0] + "; ";
					applicantName = applicantName + idAndName[1] + "; ";
				}
			}
		}
		if(!applicantId.equals("") && applicantId.contains("; ")){
			applicantId = applicantId.substring(0, applicantId.lastIndexOf("; "));
		}
		if(!applicantName.equals("") && applicantName.contains("; ")){
			applicantName = applicantName.substring(0, applicantName.lastIndexOf("; "));
		}
		oldApplication.setApplicantId(applicantId);
		oldApplication.setApplicantName(applicantName);
		//设置项目所在机构信息
		oldApplication = (KeyApplication)this.keyService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.keyService.getMember(application1.getId(), groupNumber)) {
			dao.delete(member);
		}
//		this.keyService.deleteMore(this.keyService.getMember(application.getId(), groupNumber));
		//成员信息
		for (int i=0; i<rms.size(); i++){
			KeyMember member = rms.get(i);
			member = (KeyMember)this.keyService.setMemberInfoFromMember(member);
			member.setMemberSn(i + 1);
			member.setGroupNumber(groupNumber);
			member.setApplication(application1);//项目申请
			rms.set(i, member);
		}
		dao.modify(oldApplication);
		oldApplication = (KeyApplication)this.keyService.getApplicationFetchDetailByAppId(entityId);
		oldGranted = (KeyGranted)this.keyService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
		dao.modify(oldGranted);
		Map parmap = new HashMap();
		parmap.put("grantedId", oldGranted.getId());
		KeyFunding keyGrantedFundingold = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
		if (keyGrantedFundingold != null) {
			if (keyGrantedFundingold.getStatus() == 0) {
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				keyGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
				keyGrantedFundingold.setGrantedId(oldGranted.getId());
				keyGrantedFundingold.setProjectType(application1.getType());
				keyGrantedFundingold.setStatus(0);
				dao.modify(keyGrantedFundingold);
			}
		}else {
			KeyFunding keyGrantedFunding = new KeyFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			keyGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
			keyGrantedFunding.setGranted(oldGranted);
			keyGrantedFunding.setGrantedId(oldGranted.getId());
			keyGrantedFunding.setProjectType(application1.getType());
			keyGrantedFunding.setStatus(0);
			keyGrantedFunding.setType(1);
			dao.add(keyGrantedFunding);
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		keyService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 修改时校验
	 */
	public void validateModify(){
		if(this.application1==null || this.application1.getId()==null || this.application1.getId().isEmpty()){
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		validateAppInfo();
		validateGrantedInfo();
		validateMemberInfo(2);
	}

	/**
	 * 校验申请信息
	 */
	public void validateAppInfo(){
		if(application1 == null){
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
		}else{
			this.validateAppInfo(application1);
			if(application1.getTopicSelection().getId() == null || application1.getTopicSelection().getId().equals("-1")){
				this.addFieldError("application.topicSelection.id", ProjectInfo.ERROR_PROJECT_TOPIC_NULL);
			}
			Double applyTotal = 0.0;
			if (application1.getApplyFee() != null) {
				applyTotal += application1.getApplyFee();
			}
			if (application1.getOtherFee() != null) {
				applyTotal += application1.getOtherFee();
			}
			this.validateProjectFee(projectFeeApply,applyTotal);
		}
	}
	
	/**
	 * 校验中标信息
	 */
	public void validateGrantedInfo(){
		this.validateGrantedInfo(granted, application1);
		if (granted != null) {
			this.validateProjectFee(projectFeeGranted,granted.getApproveFee());
		}
	}
	
	/**
	 * 校验成员信息
	 * @param type 1:添加是校验	2：修改时校验
	 */
	public void validateMemberInfo(int type){
		if(rms==null || rms.size()<=0){
			this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NULL);
		}else{
			int isDirector = 0;
			for(int i=0; i<rms.size(); i++){
				if(rms.get(i).getIsDirector() == 1){
					isDirector = 1;
				}
			}
			if(isDirector == 0){
				this.addActionError(ProjectInfo.ERROR_PROJECT_DIRECTOR_NULL);
			}
			for(int i=0; i<rms.size(); i++){
				KeyMember member = rms.get(i);
				this.validateMemberInfo(member, type);
				if(member.getIsSubprojectDirector()!=0 && member.getIsSubprojectDirector()!=1){
					this.addActionError(ProjectInfo.ERROR_PROJECT_IS_SUBTOPIC_DIRECTOR_NULL);
				}
			}
		}
	}

	public KeyGranted getGranted() {
		return granted;
	}
	public void setGranted(KeyGranted granted) {
		this.granted = granted;
	}
	public KeyApplication getApplication1() {
		return application1;
	}
	public void setApplication1(KeyApplication application1) {
		this.application1 = application1;
	}
	public List<KeyMember> getRms() {
		return rms;
	}
	public void setRms(List<KeyMember> rms) {
		this.rms = rms;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}