package csdc.action.project.key;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.KeyApplication;
import csdc.bean.KeyFunding;
import csdc.bean.KeyGranted;
import csdc.bean.KeyMember;
import csdc.bean.KeyTopicSelection;
import csdc.bean.Person;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.tool.ApplicationContainer;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * @author 肖雅
 */
public class ApplicationApplyAction extends csdc.action.project.ApplicationApplyAction {

	private static final long serialVersionUID = -700148736686965249L;
//	//管理人员使用
//	private static final String HQL1 = "select app.id, app.name, app.applicantId, app.applicantName, " +
//			"uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, " +
//			"app.status from KeyApplication app left join app.granted gra left join app.university uni " +
//			"left join app.subtype so left join gra.university unin left join app.topic top " +
//			"where 1=1 ";
//	//研究人员使用
//	private static final String HQL2 = "select app.id, app.name, app.applicantId, app.applicantName, " +
//			"uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, " +
//			"app.status from KeyApplication app left outer join app.university uni " +
//			"left outer join app.topic top left outer join app.subtype so, KeyMember mem " +
//			"where mem.application.id=app.id ";
	//管理人员使用
	private static final String HQL2 = "from KeyApplication app left join app.university uni " +
			"left join app.researchType so , KeyMember mem where mem.application.id=app.id and 1=1 ";
	//研究人员使用
	private static final String HQL3 = "from KeyApplication app left outer join app.university uni " +
			"left outer join app.researchType so, KeyMember mem " +
			"where mem.application.id=app.id ";
	private static final String PAGE_NAME = "keyApplicationPage";//列表页面名称
	private static final String PROJECT_TYPE = "key";
	private static final String BUISINESS_TYPE = "041";
	private static final String GRANTED_CLASS_NAME = "KeyGranted";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private KeyApplication application1;//项目申请对象
	private KeyGranted granted;//项目对象
	private List<KeyMember> rms;//项目成员
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	
	public String pageName() {
		return ApplicationApplyAction.PAGE_NAME;
	}
	public String projectType(){
		return ApplicationApplyAction.PROJECT_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationApplyAction.CHECK_GRANTED_FLAG;
	}
	@Override
	public String businessType() {
		return ApplicationApplyAction.BUISINESS_TYPE;
	}
	public String grantedClassName(){
		return ApplicationApplyAction.GRANTED_CLASS_NAME;
	}
	public String listHql2(){
		return ApplicationApplyAction.HQL2;
	}
	public String listHql3(){
		return ApplicationApplyAction.HQL3;
	}
	
	public String toAdd(){
		//默认责任人信息
		AccountType accountType = loginer.getCurrentType();
		if(accountType.compareTo(AccountType.INSTITUTE) > 0){
			KeyMember applicant = new KeyMember();
			applicant = (KeyMember) this.keyService.setApplicantInfoFromMember(applicant, loginer);
			rms = new ArrayList<KeyMember>(); 
			rms.add(applicant);
		}
		year = this.keyService.getBusinessDefaultYear(businessType(), "businessType");
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 招标项目添加
	 */
	@Transactional
	public String add(){
		//招标信息
		projectFeeApply = (ProjectFee)this.keyService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply); 
		application1 = (KeyApplication)this.keyService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
		//选题信息
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
		application1.setTopicSelection(topicSelection);
		application1.setName(topicSelection.getName());
//		application1.setYear(year);
		application1.setIsImported(0);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			KeyMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.keyService.getAppDirectorIdAndName(member);
					if(idAndName != null){
						applicantId = applicantId + idAndName[0] + "; ";
						applicantName = applicantName + idAndName[1] + "; ";
					}
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
		//设置项目所在机构信息
		if (loginer.getAccount().getType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			deptInstFlag = Integer.parseInt(teInstFlag);
		} 
		application1 = (KeyApplication)this.keyService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存文件信息
		String groupId = "file_add";
		application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				KeyMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (KeyMember)this.keyService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		application1 = (KeyApplication)this.doWithAddOrModify(application1);
		entityId = dao.add(application1);
		for(Object rm : rms){
			dao.add(rm);
		}
		keyService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.keyService.doWithNewMember(application1.getId(), 1);
   		}
		appFlag = 1;
		uploadService.flush(groupId);
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
		//校验业务设置状态
		appStatus = this.keyService.getBusinessStatus(businessType());
		if (appStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.keyService.checkIfTimeValidate(accountType, businessType());
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
		}
		if(fileIds != null && fileIds.length > 1){
			this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
		}
		validateAppInfo();
		validateMemberInfo(1);
	}
	
	/**
	 * 修改前准备
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.keyService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (KeyApplication)dao.query(KeyApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		year = application1.getYear();
		int groupNumber = 1; 
		rms = this.keyService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("所选项目信息为空或者项目成员信息为空");
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
	 * 准备修改时校验
	 */
	public void validateToModify(){
		if (entityId == null || entityId.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		if (topsId == null || topsId.isEmpty()) {//课题id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}
	
	/**
	 * 申请项目修改
	 */
	@Transactional
	public String modify(){
		if(!this.keyService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//招标信息
		ProjectFee oldProjectFee = null;
		this.entityId = application1.getId();
		KeyApplication oldApplication = (KeyApplication)dao.query(KeyApplication.class, application1.getId());
		oldApplication = (KeyApplication)this.keyService.updateAppBaseInfoFromApp(oldApplication, application1);
//		oldApplication.setYear(year);
		//选题信息
		if(application1.getTopicSelection().getId() != null){
			KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
			oldApplication.setTopicSelection(topicSelection);
			oldApplication.setName(topicSelection.getName());
		}else{
			return INPUT;
		}
		if (oldApplication.getProjectFee() != null) {
			oldProjectFee = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFee = this.keyService.updateProjectFee(oldProjectFee,projectFeeApply);
		}else {
			oldProjectFee = this.keyService.setProjectFee(projectFeeApply);
			oldProjectFee.setType(1);
			dao.add(oldProjectFee); 
		}
		oldApplication.setProjectFee(oldProjectFee);
		if(oldApplication.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			KeyMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.keyService.getAppDirectorIdAndName(member);
					if(idAndName != null){
						applicantId = applicantId + idAndName[0] + "; ";
						applicantName = applicantName + idAndName[1] + "; ";
					}
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
		if (loginer.getAccount().getType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			deptInstFlag = Integer.parseInt(teInstFlag);
		} 
		oldApplication = (KeyApplication)this.keyService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.keyService.getMember(application1.getId(), 1)) {
			dao.delete(member);
		}
//		this.keyService.deleteMore(this.keyService.getMember(application.getId(), 1));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				KeyMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (KeyMember)this.keyService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		oldApplication = (KeyApplication)this.doWithAddOrModify(oldApplication);
		dao.modify(oldApplication);
		for(Object rm : rms){
			dao.add(rm);
		}
		keyService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.keyService.doWithNewMember(application1.getId(), 1);
   		}
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
		validateMemberInfo(2);
	}
	
	/**
	 * 申请项目录入添加
	 */
	@Transactional
	public String addResult(){
		//申报经费
		projectFeeApply = (ProjectFee)this.keyService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply); 
		//招标信息
		application1 = (KeyApplication)this.keyService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
		KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
		//选题信息
		application1.setTopicSelection(topicSelection);
		application1.setName(topicSelection.getName());
		if(application1.getFinalAuditResult() == 2){//中标
			//添加数据等同导入数据，不设置流程数据	
			application1.setFinalAuditResult(2);
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
		}else{//未中标
			//添加数据等同导入数据，不设置流程数据	
			application1.setFinalAuditResult(1);
			application1.setFinalAuditStatus(3);
			application1.setFinalAuditDate(null);
		}
		application1.setIsImported(1);
		//中标信息
		if(application1.getFinalAuditResult() == 2){//中标则处理中标信息
			if(granted != null){
				//立项经费
				projectFeeGranted = (ProjectFee)this.keyService.setProjectFee(projectFeeGranted);
				projectFeeGranted.setType(2);
				dao.add(projectFeeGranted);
				granted.setProjectFee(projectFeeGranted);
				granted.setNumber(granted.getNumber().trim());
				granted.setApplication(application1);
				granted.setMemberGroupNumber(1);
				granted.setStatus(1);//设为在研
				granted.setIsImported(1);
			}else{
				return INPUT;
			}
		}
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
		application1.setApplicantId(applicantId);
		application1.setApplicantName(applicantName);
		//设置项目所在机构信息
		application1 = (KeyApplication)this.keyService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存文件信息
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
		//中标则添加中标信息
		if(application1.getFinalAuditResult() == 2){
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
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		keyService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 添加录入时校验
	 */
	public void validateAddResult(){
//		if(null==fileIds||fileIds.length==0){
//			this.addFieldError("file", ProjectInfo.ERROR_FILE_NULL);
//		}else if(fileIds.length > 1){
//			this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
//		}
		if(fileIds != null && fileIds.length > 1){
			this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
		}
		validateAppInfo();
		if(application1 != null && application1.getFinalAuditResult() == 2){
			validateGrantedInfo();
		}
		validateMemberInfo(1);
	}

	/**
	 * 准备录入修改时校验
	 */
	public void validateToModifyResult(){
		if (entityId == null || entityId.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}

	/**
	 * 录入修改前准备
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.keyService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (KeyApplication)dao.query(KeyApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		granted =(KeyGranted)keyService.getGrantedByAppId(entityId);
		if (granted != null) {
			if (granted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			}
		}
//		int groupNumber = (granted != null) ? granted.getMemberGroupNumber() : 1; //查项目最新组编号
		int groupNumber = 1;//查项目最原始最编号
		rms = this.keyService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("所选项目信息为空或者项目成员信息为空");
			return ERROR;
		}else if(granted==null && application1.getFinalAuditResult()==2){
			addActionError("所选项目立项信息为空并且项目申报审核已通过");
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
	 * 录入申请项目修改
	 */
	@Transactional
	public String modifyResult(){
		if(!this.keyService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//招标信息
		this.entityId = application1.getId();
		KeyApplication oldApplication = (KeyApplication)dao.query(KeyApplication.class, application1.getId());
		oldApplication = (KeyApplication)this.keyService.updateAppBaseInfoFromApp(oldApplication, application1);
		//选题信息
		if(application1.getTopicSelection().getId() != null){
			KeyTopicSelection topicSelection = (KeyTopicSelection) this.dao.query(KeyTopicSelection.class, application1.getTopicSelection().getId());
			oldApplication.setTopicSelection(topicSelection);
			oldApplication.setName(topicSelection.getName());
		}else{
			return INPUT;
		}
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
//		int groupNumber = (oldGranted != null) ? oldGranted.getMemberGroupNumber() : 1; //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(application1.getFinalAuditResult() == 2){//中标
			if(granted != null){
				if(oldGranted == null){//以前没中标
					oldGranted = new KeyGranted();//新建中标对象
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
				oldApplication.setFinalAuditResult(2);//已中标
				oldApplication.setFinalAuditStatus(3);
				Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
				oldApplication.setFinalAuditDate(approveDate);
				oldGranted.setApplication(oldApplication);
				oldGranted.setMemberGroupNumber(groupNumber);
				oldGranted.setIsImported(1);
			}else{
				return INPUT;
			}
		}else{//不中标
			if(oldGranted != null){//存在中标信息
				if (oldGranted.getProjectFee() != null) {
					oldProjectFeeGranted = dao.query(ProjectFee.class, oldGranted.getProjectFee().getId());
				}
				this.dao.delete(oldGranted);//删除立项信息
				dao.delete(oldProjectFeeGranted);
			}
			oldApplication.setFinalAuditResult(1);//未中标
			oldApplication.setFinalAuditStatus(3);
			oldApplication.setFinalAuditDate(null);
		}
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
		if(application1.getFinalAuditResult() == 2){//中标则添加或修改中标信息
			oldApplication = (KeyApplication)this.keyService.getApplicationFetchDetailByAppId(entityId);
			oldGranted = (KeyGranted)this.keyService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
			dao.addOrModify(oldGranted);
			
			Map parmap = new HashMap();
			parmap.put("grantedId", oldGranted.getId());
			KeyFunding keyGrantedFundingold = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
			if (keyGrantedFundingold != null) {
				if (keyGrantedFundingold.getStatus() == 0) {
					//立项则添加立项拨款申请，金额默认为批准经费的50%
//					keyGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
					keyGrantedFundingold.setGrantedId(oldGranted.getId());
					keyGrantedFundingold.setProjectType(application1.getType());
					keyGrantedFundingold.setStatus(0);
					dao.modify(keyGrantedFundingold);
				}
			}else {
				KeyFunding keyGrantedFunding = new KeyFunding();
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				keyGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
				keyGrantedFunding.setGranted(oldGranted);
				keyGrantedFunding.setGrantedId(oldGranted.getId());
				keyGrantedFunding.setProjectType(application1.getType());
				keyGrantedFunding.setStatus(0);
				keyGrantedFunding.setType(1);
				dao.add(keyGrantedFunding);
			}
			
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		keyService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 录入修改时校验
	 */
	public void validateModifyResult(){
		if(this.application1==null || this.application1.getId()==null || this.application1.getId().isEmpty()){
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		validateAppInfo();
		if(application1 != null && application1.getFinalAuditResult() == 2){
			validateGrantedInfo();
		}
		validateMemberInfo(2);
	}

	/**
	 * 校验申请信息
	 */
	public void validateAppInfo(){
		if(application1 == null){
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
		}else{
			if(application1.getTopicSelection() == null || application1.getTopicSelection().getId().isEmpty()){
				this.addFieldError("application.topicSelection", ProjectInfo.ERROR_TOPIC_NULL);
			}
			this.validateAppInfo(application1);
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
	 * @param type 1:添加时校验	2：修改时校验
	 */
	public void validateMemberInfo(int type){
		if(rms==null || rms.size()<=0){
			this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NULL);
		}else{
			int isDirector = 0;
			for(int i=0; i<rms.size(); i++){
				if(rms.get(i).getIsDirector() == 1){
					isDirector++;
				}
			}
			if(isDirector == 0){
				this.addActionError(ProjectInfo.ERROR_PROJECT_DIRECTOR_NULL);
			}
			if(isDirector > 1){
				this.addActionError(ProjectInfo.ERROR_PROJECT_DIRECTOR_ONE);
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

	public KeyGranted getGranted() {
		return granted;
	}

	public void setGranted(KeyGranted granted) {
		this.granted = granted;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

}