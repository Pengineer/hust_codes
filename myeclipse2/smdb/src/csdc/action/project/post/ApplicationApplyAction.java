package csdc.action.project.post;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Person;
import csdc.bean.PostApplication;
import csdc.bean.PostFunding;
import csdc.bean.PostGranted;
import csdc.bean.PostMember;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.bean.SystemOption;
import csdc.service.IPostService;
import csdc.tool.ApplicationContainer;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * @author 余潜玉、肖雅
 */
public class ApplicationApplyAction extends csdc.action.project.ApplicationApplyAction {

	private static final long serialVersionUID = -700148736686965249L;
	//管理人员使用
	private static final String HQL2 = "from PostApplication app left join app.university uni " +
			"left join app.subtype so left join app.topic top, PostMember mem where mem.application.id=app.id ";
	//研究人员使用
	private static final String HQL3 = "from PostApplication app left outer join app.university uni " +
			"left outer join app.topic top left outer join app.subtype so, PostMember mem " +
			"where mem.application.id=app.id ";
	private static final String PAGE_NAME = "postApplicationPage";//列表页面名称
	private static final String GRANTED_CLASS_NAME = "PostGranted";
	private static final String PROJECT_TYPE = "post";
	private static final String BUISINESS_TYPE = "031";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;
	
	private IPostService postService;
	private PostApplication application1;//项目申请对象
	private PostGranted granted;//项目对象
	private List<PostMember> rms;//项目成员
	
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	
	public String pageName() {
		return ApplicationApplyAction.PAGE_NAME;
	}
	public String projectType(){
		return ApplicationApplyAction.PROJECT_TYPE;
	}
	public String businessType() {
		return ApplicationApplyAction.BUISINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationApplyAction.CHECK_GRANTED_FLAG;
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
			PostMember applicant = new PostMember();
			applicant = (PostMember) this.postService.setApplicantInfoFromMember(applicant, loginer);
			rms = new ArrayList<PostMember>(); 
			rms.add(applicant);
		}
		year = this.postService.getBusinessDefaultYear(businessType(), "businessType");
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 申请项目添加
	 */
	@Transactional
	public String add(){
		//申报信息
		projectFeeApply = (ProjectFee)this.postService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply); 
		application1 = (PostApplication)this.postService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
		application1.setYear(year);
		if(application1.getTopic() != null && !"-1".equals(application1.getTopic().getId())){//项目主题
			SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
			application1.setTopic(topic);
		}else {
			application1.setTopic(null);
		}
		application1.setIsImported(0);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			PostMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.postService.getAppDirectorIdAndName(member);
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
		application1 = (PostApplication)this.postService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存文件信息
		String groupId = "file_add";
		application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (PostMember)this.postService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		application1 = (PostApplication)this.doWithAddOrModify(application1);
		entityId = dao.add(application1);
		for(Object rm : rms){
			dao.add(rm);
		}
		postService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.postService.doWithNewMember(application1.getId(), 1);
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
		appStatus = this.postService.getBusinessStatus(businessType());
		if (appStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.postService.checkIfTimeValidate(accountType, businessType());
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
		if(!this.postService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (PostApplication)dao.query(PostApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		year = application1.getYear();
		int groupNumber = 1; 
		rms = this.postService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("项目申报信息为空或者项目成员信息为空");
			return ERROR;
		}else{
			deptInstFlag = this.postService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				member = (PostMember)this.postService.setMemberPersonInfoFromMember(member);
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
	}
	
	/**
	 * 申请项目修改
	 */
	@Transactional
	public String modify(){
		if(!this.postService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申报信息
		ProjectFee oldProjectFee = null;
		this.entityId = application1.getId();
		PostApplication oldApplication = (PostApplication)dao.query(PostApplication.class, application1.getId());
		oldApplication = (PostApplication)this.postService.updateAppBaseInfoFromApp(oldApplication, application1);
		oldApplication.setYear(year);
		if(oldApplication.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		if (oldApplication.getProjectFee() != null) {
			oldProjectFee = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFee = this.postService.updateProjectFee(oldProjectFee,projectFeeApply);
		}else {
			oldProjectFee = this.postService.setProjectFee(projectFeeApply);
			oldProjectFee.setType(1);
			dao.add(oldProjectFee); 
		}
		oldApplication.setProjectFee(oldProjectFee);
		if(application1.getTopic().getId() != null && !application1.getTopic().getId().equals("-1")){//主题
			SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
			oldApplication.setTopic(topic);
		}else{
			oldApplication.setTopic(null);
		}
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			PostMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.postService.getAppDirectorIdAndName(member);
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
		oldApplication = (PostApplication)this.postService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.postService.getMember(application1.getId(), 1)) {
			dao.delete(member);
		}
//		this.postService.deleteMore(this.postService.getMember(application.getId(), 1));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (PostMember)this.postService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		oldApplication = (PostApplication)this.doWithAddOrModify(oldApplication);
		dao.modify(oldApplication);
		for(Object rm : rms){
			dao.add(rm);
		}
		postService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.postService.doWithNewMember(application1.getId(), 1);
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
		projectFeeApply = (ProjectFee)this.postService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply); 
		//申报信息
		application1 = (PostApplication)this.postService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
		if(application1.getTopic() != null && !"-1".equals(application1.getTopic().getId())){//项目主题
			SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
			application1.setTopic(topic);
		}else {
			application1.setTopic(null);
		}
		if(application1.getFinalAuditResult() == 2){//立项
			//添加数据等同导入数据，不设置流程数据	
			application1.setFinalAuditResult(2);
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
		}else{//未立项
			//添加数据等同导入数据，不设置流程数据	
			application1.setFinalAuditResult(1);
			application1.setFinalAuditStatus(3);
			application1.setFinalAuditDate(null);
		}
		application1.setIsImported(1);
		
		//立项信息
		if(application1.getFinalAuditResult() == 2){//立项则处理立项信息
			if(granted != null){
				//立项经费
				projectFeeGranted = (ProjectFee)this.postService.setProjectFee(projectFeeGranted);
				projectFeeGranted.setType(2);
				dao.add(projectFeeGranted);
				granted.setProjectFee(projectFeeGranted);
				granted.setNumber(granted.getNumber().trim());
				if(granted.getSubtype().getId() != null && !"-1".equals(granted.getSubtype().getId())){
					SystemOption subtype = (SystemOption) dao.query(SystemOption.class, granted.getSubtype().getId());
					granted.setSubtype(subtype);
				}else {
					granted.setSubtype(null);
				}
				granted.setApplication(application1);
				granted.setMemberGroupNumber(1);
				granted.setStatus(1);
				granted.setIsImported(1);
			}else{
				return INPUT;
			}
		}
		//从成员列表中获取申请人信息
		if(rms!=null && rms.size()>0){
			String applicantId = "";
			String applicantName = "";
			for(int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				if(member.getIsDirector() == 1){//主要负责人
					Object[] idAndName = this.postService.getAppDirectorIdAndName(member);
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
		application1 = (PostApplication)this.postService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存上传的文件
		String groupId = "file_add";
		application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				member = (PostMember)this.postService.setMemberInfoFromMember(member);
				member.setMemberSn(i+1);//序号
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		entityId = dao.add(application1);
		if(application1.getFinalAuditResult() == 2){//立项则添加立项信息
			application1 = (PostApplication)this.postService.getApplicationFetchDetailByAppId(entityId);
			granted = (PostGranted)this.postService.setGrantedInfoFromAppForImported(application1, granted);
			dao.add(granted);
			PostFunding postFunding = new PostFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			postFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			postFunding.setGranted(granted);
			postFunding.setGrantedId(granted.getId());
			postFunding.setProjectType(application1.getType());
			postFunding.setStatus(0);
			postFunding.setType(1);
			dao.add(postFunding);
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		postService.refreshMemberSn(entityId, 1);
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
			validatePostInfo();
		}
		validateMemberInfo(1);
	}

	/**
	 * 录入修改前准备
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.postService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (PostApplication)dao.query(PostApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		granted = (PostGranted)postService.getGrantedByAppId(entityId);
		if (granted != null) {
			if (granted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			}
		}
//		int groupNumber = (granted != null) ? granted.getMemberGroupNumber() : 1; //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		rms = this.postService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("项目申报信息为空或者项目成员信息为空");
			return ERROR;
		}else if(granted==null && application1.getFinalAuditResult()==2){
			addActionError("项目申报已通过审核但是项目立项信息为空");
			return ERROR;
		}else{
			deptInstFlag = this.postService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				PostMember member = rms.get(i);
				member = (PostMember)this.postService.setMemberPersonInfoFromMember(member);
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
	 * 申请项目录入修改
	 */
	@Transactional
	public String modifyResult(){
		if(!this.postService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申报信息
		this.entityId = application1.getId();
		PostApplication oldApplication = (PostApplication)dao.query(PostApplication.class, application1.getId());
		oldApplication = (PostApplication)this.postService.updateAppBaseInfoFromApp(oldApplication, application1);
		if(application1.getTopic().getId() != null && !application1.getTopic().getId().equals("-1")){//主题
			SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
			oldApplication.setTopic(topic);
		}else{
			oldApplication.setTopic(null);
		}
		ProjectFee oldProjectFeeApply = new ProjectFee();
		ProjectFee oldProjectFeeGranted = new ProjectFee();
		
		if (oldApplication.getProjectFee() != null) {
			oldProjectFeeApply = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFeeApply = this.postService.updateProjectFee(oldProjectFeeApply,projectFeeApply);
			dao.modify(oldProjectFeeApply);
		}else {
			oldProjectFeeApply = this.postService.setProjectFee(projectFeeApply);
			oldProjectFeeApply.setType(1);
			dao.add(oldProjectFeeApply); 
		}
		oldApplication.setProjectFee(oldProjectFeeApply);
		//立项信息
		PostGranted oldGranted = (PostGranted)this.postService.getGrantedByAppId(application1.getId());
//		int groupNumber = (oldGranted != null) ? oldGranted.getMemberGroupNumber() : 1; //查项目最新组编号
		int groupNumber = 1;//查项目最原始组编号
		if(application1.getFinalAuditResult() == 2){//立项
			if(granted != null){
				if(oldGranted == null){//以前没立项
					oldGranted = new PostGranted();//新建立项对象
				}
				oldGranted = (PostGranted)this.postService.updateGrantedInfoFromGranted(oldGranted, granted);
				if (oldGranted.getProjectFee() != null) {
					oldProjectFeeGranted = dao.query(ProjectFee.class, oldGranted.getProjectFee().getId());
					oldProjectFeeGranted = this.postService.updateProjectFee(oldProjectFeeGranted,projectFeeGranted);
					dao.modify(oldProjectFeeGranted);
				}else {
					oldProjectFeeGranted = this.postService.setProjectFee(projectFeeGranted);
					oldProjectFeeGranted.setType(2);
					dao.add(oldProjectFeeGranted); 
				}
				oldGranted.setProjectFee(oldProjectFeeGranted);
				oldApplication.setFinalAuditResult(2);//已立项
				oldApplication.setFinalAuditStatus(3);
				Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
				oldApplication.setFinalAuditDate(approveDate);
				oldGranted.setApplication(oldApplication);
				oldGranted.setMemberGroupNumber(groupNumber);
				oldGranted.setIsImported(1);
			}else{
				return INPUT;
			}
		}else{//不立项
			if(oldGranted != null){//存在立项信息
				if (oldGranted.getProjectFee() != null) {
					oldProjectFeeGranted = dao.query(ProjectFee.class, oldGranted.getProjectFee().getId());
				}
				this.dao.delete(oldGranted);//删除立项信息
				dao.delete(oldProjectFeeGranted);
			}
			oldApplication.setFinalAuditResult(1);//未立项
			oldApplication.setFinalAuditStatus(3);
			oldApplication.setFinalAuditDate(null);
		}
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			PostMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.postService.getAppDirectorIdAndName(member);
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
		oldApplication = (PostApplication)this.postService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.postService.getMember(application1.getId(), groupNumber)) {
			dao.delete(member);
		}
//		this.postService.deleteMore(this.postService.getMember(application.getId(), groupNumber));
		//成员信息
		for (int i=0; i<rms.size(); i++){
			PostMember member = rms.get(i);
			member = (PostMember)this.postService.setMemberInfoFromMember(member);
			member.setMemberSn(i + 1);
			member.setGroupNumber(groupNumber);
			member.setApplication(application1);//项目申请
			rms.set(i, member);
		}
		dao.modify(oldApplication);
		if(application1.getFinalAuditResult() == 2){//立项则添加或修改立项信息
			oldApplication = (PostApplication)this.postService.getApplicationFetchDetailByAppId(entityId);
			oldGranted = (PostGranted)this.postService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
			dao.addOrModify(oldGranted);
			
			Map parmap = new HashMap();
			parmap.put("grantedId", oldGranted.getId());
			PostFunding postGrantedFundingold = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
			if (postGrantedFundingold != null) {
				if (postGrantedFundingold.getStatus() == 0) {
					//立项则添加立项拨款申请，金额默认为批准经费的50%
//					postGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
					postGrantedFundingold.setGrantedId(oldGranted.getId());
					postGrantedFundingold.setProjectType(application1.getType());
					postGrantedFundingold.setStatus(0);
					dao.modify(postGrantedFundingold);
				}
			}else {
				PostFunding postGrantedFunding = new PostFunding();
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				postGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
				postGrantedFunding.setGranted(oldGranted);
				postGrantedFunding.setGrantedId(oldGranted.getId());
				postGrantedFunding.setProjectType(application1.getType());
				postGrantedFunding.setStatus(0);
				postGrantedFunding.setType(1);
				dao.add(postGrantedFunding);
			}
			
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		postService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 准备修改录入时校验
	 */
	public void validateToModifyResult(){
		if (entityId == null || entityId.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}
	
	/**
	 * 修改录入时校验
	 */
	public void validateModifyResult(){
		if(this.application1==null || this.application1.getId()==null || this.application1.getId().isEmpty()){
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		validateAppInfo();
		if(application1 != null && application1.getFinalAuditResult() == 2){
			validatePostInfo();
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
			this.validateAppInfo(application1);
			Double applyTotal = 0.0;
			if (application1.getApplyFee() != null) {
				applyTotal += application1.getApplyFee();
			}
			if (application1.getOtherFee() != null) {
				applyTotal += application1.getOtherFee();
			}
			this.validateProjectFee(projectFeeApply,applyTotal);
//			if(application.getTopic().getId() == null || application.getTopic().getId().equals("-1")){
//				this.addFieldError("application.topic.id", ProjectInfo.ERROR_PROJECT_TOPIC_NULL);
//			}
		}
	}
	
	/**
	 * 校验立项信息
	 */
	public void validatePostInfo(){
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
				PostMember member = rms.get(i);
				this.validateMemberInfo(member, type);
			}
		}
	}

	public PostApplication getApplication1() {
		return application1;
	}
	public void setApplication1(PostApplication application1) {
		this.application1 = application1;
	}
	public PostGranted getGranted() {
		return granted;
	}
	public void setGranted(PostGranted granted) {
		this.granted = granted;
	}
	public List<PostMember> getRms() {
		return rms;
	}
	public void setRms(List<PostMember> rms) {
		this.rms = rms;
	}
	public IPostService getPostService() {
		return postService;
	}
	public void setPostService(IPostService postService) {
		this.postService = postService;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}