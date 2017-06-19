package csdc.action.project.special;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.SpecialApplication;
import csdc.bean.SpecialFunding;
import csdc.bean.SpecialGranted;
import csdc.bean.SpecialMember;
import csdc.bean.Person;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.bean.SystemOption;
import csdc.service.ISpecialService;
import csdc.tool.ApplicationContainer;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 专项任务项目申请申请子类
 * 实现了父类所有的抽象方法及专项任务项目申请申请特有的方法
 * @author wangming
 */
public class ApplicationApplyAction extends csdc.action.project.ApplicationApplyAction {

	private static final long serialVersionUID = -700148736686965249L;
	//管理人员使用
	private static final String HQL2 = "from SpecialApplication app left join app.university uni " +
			"left join app.subtype so, SpecialMember mem where mem.application.id=app.id and 1=1 ";
	//研究人员使用
	private static final String HQL3 = "from SpecialApplication app left outer join app.university uni " +
			"left outer join app.subtype so, SpecialMember mem " +
			"where mem.application.id=app.id ";
	private static final String PAGE_NAME = "specialApplicationPage";//列表页面名称
	private static final String PROJECT_TYPE = "special";//项目类别
	private static final String BUISINESS_TYPE = "011";//获得业务类型名称
	private static final String GRANTED_CLASS_NAME = "SpecialGranted";////项目立项类类名
	private static final int CHECK_APPLICATION_FLAG = 29;//获得判断申请项目是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//获得判断立项项目是否在管辖范围内的标志位
	private ISpecialService specialService;
//	private SpecialApplication application;//项目申请对象
	private SpecialApplication application1;//项目申请对象
//	private ProjectFee projectFeeApply,projectFeeGranted;//项目申请经费对象，项目立项经费对象
//	private ProjectFunding projectFunding;	//	拨款申请
	
	private SpecialGranted granted;//项目对象
	private List<SpecialMember> rms;//项目成员
	
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
	
	/**
	 * 进入专项任务项目申请添加页面预处理
	 */
	public String toAdd(){
		//默认责任人信息
		AccountType accountType = loginer.getCurrentType();
		if(accountType.compareTo(AccountType.INSTITUTE) > 0){
			SpecialMember applicant = new SpecialMember();
			applicant = (SpecialMember) this.specialService.setApplicantInfoFromMember(applicant, loginer);
			rms = new ArrayList<SpecialMember>(); 
			rms.add(applicant);
		}
		year = this.specialService.getBusinessDefaultYear(businessType(), "businessType");
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 添加专项任务项目申请
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception{
		//申请信息
//		ProjectFee projectFee = new ProjectFee(); 
		
		projectFeeApply = (ProjectFee)this.specialService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply);
		application1 = (SpecialApplication)this.specialService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
		application1.setYear(year);
		application1.setCreateMode(0);
		application1.setCreateDate(new Date());
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			SpecialMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.specialService.getAppDirectorIdAndName(member);
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
			if (null != teInstFlag) {
				String fString = teInstFlag.substring(0, 1);
				deptInstFlag = Integer.parseInt(fString);
			} 
		}
		application1 = (SpecialApplication)this.specialService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存文件信息
		String groupId = "file_add";
		application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (SpecialMember)this.specialService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		application1 = (SpecialApplication)this.doWithAddOrModify(application1);
		entityId = dao.add(application1);
		for(Object rm : rms){
			dao.add(rm);
		}
		specialService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.specialService.doWithNewMember(application1.getId(), 1);
   		}
		appFlag = 1;
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(application1);
		application1.setDfs(dfsId);
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
		appStatus = this.specialService.getBusinessStatus(businessType());
		if (appStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.specialService.checkIfTimeValidate(accountType, businessType());
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
	 * 进入专项任务项目申请修改页面预处理
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (SpecialApplication)dao.query(SpecialApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		year = application1.getYear();
		int groupNumber = 1; 
		rms = this.specialService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("所选的项目申请信息为空或者项目成员为空！");
			return ERROR;
		}else{
			deptInstFlag = this.specialService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				member = (SpecialMember)this.specialService.setMemberPersonInfoFromMember(member);
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
					if (fileRealpath != null && new File(fileRealpath).exists()) {
						uploadService.addFile(groupId, new File(fileRealpath));
					} else if(application1.getDfs() != null && !application1.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
						try {
							InputStream downloadStream = dmssService.download(application1.getDfs());
							String sessionId = ServletActionContext.getRequest().getSession().getId();
							File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
							dir.mkdirs();
							String fileName = application1.getFile().substring(application1.getFile().lastIndexOf("/") + 1);
							File downloadFile = new File(dir, fileName);
							FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
							uploadService.addFile(groupId, downloadFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
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
	 * 修改专项任务项目申请
	 * @throws Exception 
	 */
	@Transactional
	public String modify() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申请信息
		ProjectFee oldProjectFee = null;
		this.entityId = application1.getId();
		SpecialApplication oldApplication = (SpecialApplication) dao.query(SpecialApplication.class, application1.getId());
		String orignFile = oldApplication.getFile();
		oldApplication = (SpecialApplication)this.specialService.updateAppBaseInfoFromApp(oldApplication, application1);
//		oldApplication.setYear(year);
		if(oldApplication.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		if (oldApplication.getProjectFee() != null) {
			oldProjectFee = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFee = this.specialService.updateProjectFee(oldProjectFee,projectFeeApply);
		}else {
			oldProjectFee = this.specialService.setProjectFee(projectFeeApply);
			oldProjectFee.setType(1);
			dao.add(oldProjectFee); 
		}
		oldApplication.setProjectFee(oldProjectFee);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			SpecialMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//当前登录人员为负责人
					member.setMember((Person)this.dao.query(Person.class, loginer.getPerson().getId()));
					applicantId = applicantId + loginer.getPerson().getId() + "; ";
					applicantName = applicantName + loginer.getCurrentPersonName() + "; ";
				}else{//其他主要负责人
					Object[] idAndName = this.specialService.getAppDirectorIdAndName(member);
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
		oldApplication = (SpecialApplication)this.specialService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.specialService.getMember(application1.getId(), 1)) {
			dao.delete(member);
		}
//		this.specialService.deleteMore(this.specialService.getMember(application.getId(), 1));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				if(member.getMemberName().equals(loginer.getCurrentPersonName())){//设置当前登录者成员信息
					member = (SpecialMember)this.specialService.setMemberInfoFromPerson(member);
				}
				member.setGroupNumber(1);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		oldApplication = (SpecialApplication)this.doWithAddOrModify(oldApplication);
		dao.modify(oldApplication);
		for(Object rm : rms){
			dao.add(rm);
		}
		specialService.refreshMemberSn(entityId, 1);
		if(proApplicantSubmitStatus == 3){//提交则对新增人员信息进行入库处理
			this.specialService.doWithNewMember(application1.getId(), 1);
   		}
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != oldApplication.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != oldApplication.getFile()&& !oldApplication.getFile().isEmpty()){ //现在有文件
					projectService.checkInToDmss(oldApplication);
				}else{ //现在没文件
					dmssService.deleteFile(oldApplication.getDfs());
					oldApplication.setDfs(null);
					dao.modify(oldApplication);
				}
			}else{ //原来没有文件
				if(oldApplication.getFile()!=null && !oldApplication.getFile().isEmpty()){ //现在有文件
					String dfsId = projectService.uploadToDmss(oldApplication);
					oldApplication.setDfs(dfsId);
					dao.modify(oldApplication);
				}
			}
		}
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
	 * 专项任务项目申请录入添加
	 * @throws Exception 
	 */
	@Transactional
	public String addResult() throws Exception{
		//申请经费
		projectFeeApply = (ProjectFee)this.specialService.setProjectFee(projectFeeApply);
		projectFeeApply.setType(1);
		dao.add(projectFeeApply); 
		
		//申请信息
		application1 = (SpecialApplication)this.specialService.setAppBaseInfoFromApp(application1);
		application1.setProjectFee(projectFeeApply);
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
		application1.setCreateMode(1);
		application1.setCreateDate(new Date());
		//立项信息
		if(application1.getFinalAuditResult() == 2){//立项则处理立项信息
			if(granted != null){
				
//				//立项经费
//				projectFeeGranted = (ProjectFee)this.specialService.setProjectFee(projectFeeGranted);
//				projectFeeGranted.setType(2);
//				dao.add(projectFeeGranted);
//				granted.setProjectFee(projectFeeGranted);
				granted.setNumber(granted.getNumber().trim());
				if(granted.getSubtype().getId() != null && !"-1".equals(granted.getSubtype().getId())){
					SystemOption subtype = (SystemOption) dao.query(SystemOption.class, granted.getSubtype().getId());
					granted.setSubtype(subtype);
				}else {
					granted.setSubtype(null);
				}
				granted.setApplication(application1);
				granted.setMemberGroupNumber(1);
				granted.setStatus(1);//设为在研
				granted.setCreateMode(1);
				granted.setCreateDate(new Date());
			}else{
				return INPUT;
			}
		}
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			SpecialMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.specialService.getAppDirectorIdAndName(member);
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
		application1 = (SpecialApplication)this.specialService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
		//保存文件信息
		String groupId = "file_add";
		application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
		//成员信息
		if(rms!=null && rms.size()>0){
			for (int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				member = (SpecialMember)this.specialService.setMemberInfoFromMember(member);
				member.setMemberSn(i + 1);
				member.setApplication(application1);//项目申请
				rms.set(i, member);
			}
		}
		entityId = dao.add(application1);
		//立项则添加立项信息
		if(application1.getFinalAuditResult() == 2){
			application1 = (SpecialApplication)this.specialService.getApplicationFetchDetailByAppId(entityId);
			granted = (SpecialGranted)this.specialService.setGrantedInfoFromAppForImported(application1, granted);
			dao.add(granted);	
			
//			SpecialFunding specialFunding = new SpecialFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			specialFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			specialFunding.setGranted(granted);
//			specialFunding.setGrantedId(granted.getId());
//			specialFunding.setProjectType(application1.getType());
//			specialFunding.setStatus(0);
//			specialFunding.setType(1);
//			dao.add(specialFunding);
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		specialService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(application1);
		application1.setDfs(dfsId);
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
		System.out.println(application1);
		System.out.println(application1 == null);
		validateAppInfo();
		if(application1 != null && application1.getFinalAuditResult() == 2){
			validateGrantedInfo();
//			validateProjectFee(projectFeeGranted,granted.getApproveFee());
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
	 * 进入专项任务项目录入修改页面预处理
	 */
	@SuppressWarnings("unchecked")
	public String toModifyResult(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (SpecialApplication) dao.query(SpecialApplication.class, entityId);//entityId为项目申请id
		
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		
		granted =(SpecialGranted)specialService.getGrantedByAppId(entityId);
		
//		if (granted != null) {
//			if (granted.getProjectFee() != null) {
//				projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
//			}
//		}
		
//		int groupNumber = (granted != null) ? granted.getMemberGroupNumber() : 1; //查项目最新组编号
		int groupNumber = 1;//查项目最原始最编号
		rms = this.specialService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || rms==null || rms.size()<=0){
			addActionError("所选的项目申请信息为空或者项目成员为空！");
			return ERROR;
		}else if(granted==null && application1.getFinalAuditResult()==2){
			addActionError("所选项目立项信息为空或并且项目申请已经同意申请");
			return ERROR;
		}else{
			deptInstFlag = this.specialService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				member = (SpecialMember)this.specialService.setMemberPersonInfoFromMember(member);
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
					if (fileRealpath != null && new File(fileRealpath).exists()) {
						uploadService.addFile(groupId, new File(fileRealpath));
					} else if(application1.getDfs() != null && !application1.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
						try {
							InputStream downloadStream = dmssService.download(application1.getDfs());
							String sessionId = ServletActionContext.getRequest().getSession().getId();
							File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
							dir.mkdirs();
							String fileName = application1.getFile().substring(application1.getFile().lastIndexOf("/") + 1);
							File downloadFile = new File(dir, fileName);
							FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
							uploadService.addFile(groupId, downloadFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return SUCCESS;
		}
	}

	/**
	 * 修改专项任务项目录入申请
	 * @throws Exception 
	 */
	@Transactional
	public String modifyResult() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, application1.getId().trim(), checkApplicationFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申请信息
		this.entityId = application1.getId();
		SpecialApplication oldApplication = (SpecialApplication) dao.query(SpecialApplication.class, application1.getId());
		String orignFile = oldApplication.getFile();
		oldApplication = (SpecialApplication)this.specialService.updateAppBaseInfoFromApp(oldApplication, application1);
		ProjectFee oldProjectFeeApply = new ProjectFee();
		ProjectFee oldProjectFeeGranted = new ProjectFee();
		
		if (oldApplication.getProjectFee() != null) {
			oldProjectFeeApply = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFeeApply = this.specialService.updateProjectFee(oldProjectFeeApply,projectFeeApply);
			dao.modify(oldProjectFeeApply);
		}else {
			oldProjectFeeApply = this.specialService.setProjectFee(projectFeeApply);
			oldProjectFeeApply.setType(1);
			dao.add(oldProjectFeeApply); 
		}
		oldApplication.setProjectFee(oldProjectFeeApply);
		
		//立项信息
		SpecialGranted oldGranted = (SpecialGranted)this.specialService.getGrantedByAppId(application1.getId());
//		int groupNumber = (oldGranted != null) ? oldGranted.getMemberGroupNumber() : 1; //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(application1.getFinalAuditResult() == 2){//立项
			if(granted != null){
				if(oldGranted == null){//以前没立项
					oldGranted = new SpecialGranted();//新建立项对象
				}
				oldGranted = (SpecialGranted)this.specialService.updateGrantedInfoFromGranted(oldGranted, granted);
				
//				if (oldGranted.getProjectFee() != null) {
//					oldProjectFeeGranted = dao.query(ProjectFee.class, oldGranted.getProjectFee().getId());
//					oldProjectFeeGranted = this.specialService.updateProjectFee(oldProjectFeeGranted,projectFeeGranted);
//					dao.modify(oldProjectFeeGranted);
//				}else {
//					oldProjectFeeGranted = this.specialService.setProjectFee(projectFeeGranted);
//					oldProjectFeeGranted.setType(2);
//					dao.add(oldProjectFeeGranted); 
//				}
//				oldGranted.setProjectFee(oldProjectFeeGranted);
				
				oldApplication.setFinalAuditResult(2);//已立项
				oldApplication.setFinalAuditStatus(3);
				Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
				oldApplication.setFinalAuditDate(approveDate);
				oldGranted.setApplication(oldApplication);
				oldGranted.setMemberGroupNumber(groupNumber);
				oldGranted.setCreateMode(1);
				oldGranted.setUpdateDate(new Date());
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
			SpecialMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.specialService.getAppDirectorIdAndName(member);
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
		if (loginer.getAccount().getType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			deptInstFlag = Integer.parseInt(teInstFlag);
		} 
		oldApplication = (SpecialApplication)this.specialService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.specialService.getMember(application1.getId(), groupNumber)) {
			dao.delete(member);
		}
//		this.specialService.deleteMore(this.specialService.getMember(application.getId(), groupNumber));
		//成员信息
		for (int i=0; i<rms.size(); i++){
			SpecialMember member = rms.get(i);
			member = (SpecialMember)this.specialService.setMemberInfoFromMember(member);
			member.setMemberSn(i + 1);
			member.setGroupNumber(groupNumber);
			member.setApplication(application1);//项目申请
			rms.set(i, member);
		}
		dao.modify(oldApplication);
		if(application1.getFinalAuditResult() == 2){//立项则添加或修改立项信息
			oldApplication = (SpecialApplication)this.specialService.getApplicationFetchDetailByAppId(entityId);
			oldGranted = (SpecialGranted)this.specialService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
			dao.addOrModify(oldGranted);
			
//			Map parmap = new HashMap();
//			parmap.put("grantedId", oldGranted.getId());
//			SpecialFunding specialGrantedFundingold = (SpecialFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//			if (specialGrantedFundingold != null) {
//				if (specialGrantedFundingold.getStatus() == 0) {
//					//立项则添加立项拨款申请，金额默认为批准经费的50%
////					specialGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//					specialGrantedFundingold.setGrantedId(oldGranted.getId());
//					specialGrantedFundingold.setProjectType(application1.getType());
//					specialGrantedFundingold.setStatus(0);
//					dao.modify(specialGrantedFundingold);
//				}
//			}else {
//				SpecialFunding specialGrantedFunding = new SpecialFunding();
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				specialGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//				specialGrantedFunding.setGranted(oldGranted);
//				specialGrantedFunding.setGrantedId(oldGranted.getId());
//				specialGrantedFunding.setProjectType(application1.getType());
//				specialGrantedFunding.setStatus(0);
//				specialGrantedFunding.setType(1);
//				dao.add(specialGrantedFunding);
//			}
			
		}
		for(Object rm : rms){
			dao.add(rm);
		}
		specialService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != oldApplication.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != oldApplication.getFile() && !oldApplication.getFile().isEmpty()){ //现在有文件
					projectService.checkInToDmss(oldApplication);
				}else{ //现在没文件
					dmssService.deleteFile(oldApplication.getDfs());
					oldApplication.setDfs(null);
					dao.modify(oldApplication);
				}
			}else{ //原来没有文件
				if(oldApplication.getFile()!=null && !oldApplication.getFile().isEmpty()){ //现在有文件
					String dfsId = projectService.uploadToDmss(oldApplication);
					oldApplication.setDfs(dfsId);
					dao.modify(oldApplication);
				}
			}
		}
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
	 * 校验立项信息
	 */
	public void validateGrantedInfo(){
		this.validateGrantedInfo(granted, application1);
//		if (granted != null) {
//			this.validateProjectFee(projectFeeGranted,granted.getApproveFee());
//		}
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
					isDirector = 1;
				}
			}
			if(isDirector == 0){
				this.addActionError(ProjectInfo.ERROR_PROJECT_DIRECTOR_NULL);
			}
			for(int i=0; i<rms.size(); i++){
				SpecialMember member = rms.get(i);
				this.validateMemberInfo(member, type);
			}
		}
	}
	

	public SpecialApplication getApplication1() {
		return application1;
	}
	
	public void setApplication1(SpecialApplication application1) {
		this.application1 = application1;
	}

	public List<SpecialMember> getRms() {
		return rms;
	}

	public void setRms(List<SpecialMember> rms) {
		this.rms = rms;
	}

	public SpecialGranted getGranted() {
		return granted;
	}

	public void setGranted(SpecialGranted granted) {
		this.granted = granted;
	}
	
	public ISpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	
	public int getFlag() {
		return flag;
	}
//	public void setProjectFee(ProjectFee projectFee) {
//		this.projectFee = projectFee;
//	}
//	public ProjectFee getProjectFee() {
//		return projectFee;
//	}
//	public void setProjectFeeApply(ProjectFee projectFeeApply) {
//		this.projectFeeApply = projectFeeApply;
//	}
//	public ProjectFee getProjectFeeApply() {
//		return projectFeeApply;
//	}
//	public void setProjectFeeGranted(ProjectFee projectFeeGranted) {
//		this.projectFeeGranted = projectFeeGranted;
//	}
//	public ProjectFee getProjectFeeGranted() {
//		return projectFeeGranted;
//	}
	
}