package csdc.action.project.special;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.SpecialFunding;
import csdc.bean.SpecialGranted;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialMember;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.bean.SystemOption;
import csdc.service.ISpecialService;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 专项任务项目立项子类
 * 实现了父类所有的抽象方法及专项任务项目立项特有的方法
 * @author wangming
 */
public class ApplicationGrantedAction extends csdc.action.project.ApplicationGrantedAction {

	private static final long serialVersionUID = -700148736686965249L;
	//管理人员使用
	private static final String HQL2 = "from SpecialGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so, SpecialMember mem where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and mem.application.id = app.id";
	//研究人员使用
	private static final String HQL3 = "from SpecialGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so, SpecialMember mem " +
		"where mem.application.id = app.id ";
	private static final String PAGE_NAME = "specialGrantedPage";//列表页面名称
	private static final String GRANTED_CLASS_NAME = "SpecialGranted";//专项任务项目立项类类名
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final String BUISINESS_TYPE = "016";//专项任务项目立项对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 29;//判断专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//判断专项任务项目立项是否在管辖范围内的标志位
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	private ISpecialService specialService;
	private SpecialGranted granted;//项目对象
	private SpecialApplication application1;//项目申请对象
	private List<SpecialMember> rms;//项目成员
	private String number;
	private String subtypeId;
	private Date approveDate;
	private Double approveFee;
	
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
	 * 添加专项任务项目立项计划书和经费申请
	 * @throws Exception 
	 */
	@Transactional
	public String addGra() throws Exception {
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeGranted.setType(2);
		dao.add(projectFeeGranted);
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (SpecialGranted)this.doWithAddOrMdoify(1,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加专项任务项目立项计划书和经费申请
	 * @throws Exception 
	 */
	@Transactional
	public String modifyGra() throws Exception {
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, projectid);
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
		granted = (SpecialGranted)this.doWithAddOrMdoify(2,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加录入的专项任务项目立项计划结果
	 * @author yangfq
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, this.specialService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeGranted = new ProjectFee();
		projectFeeGranted = this.doWithAddResultFee(projectFeeGranted);
		if (projectFeeGranted != null) {
			projectFeeGranted.setType(2);
			dao.add(projectFeeGranted);
		}
		SpecialGranted granted = (SpecialGranted) this.dao.query(SpecialGranted.class, this.projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (SpecialGranted)this.doWithAddResult(granted);
		dao.add(granted);
		return SUCCESS;
	}
	/**
	 * 添加专项任务项目立项
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception{
		SpecialGranted granted = new SpecialGranted();
		if(addflag == 1){//从已申请未立项项目中选择
			application1 = (SpecialApplication)this.specialService.getApplicationFetchDetailByAppId(entityId);
			application1.setFinalAuditResult(2);//已立项
			application1.setFinalAuditStatus(3);
			Date proveDate = this.projectService.setDateHhmmss(approveDate);//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(proveDate);
			granted.setApplication(application1);
			granted.setNumber(number.trim());//批准号
			if(subtypeId != null && !"-1".equals(subtypeId)){
				SystemOption subtype = (SystemOption) dao.query(SystemOption.class, subtypeId);
				granted.setSubtype(subtype);
			}else {
				granted.setSubtype(null);
			}
			
//			if(granted.getSubtype().getId() != null && !"-1".equals(granted.getSubtype().getId())){
//				SystemOption subtype = (SystemOption) dao.query(SystemOption.class, granted.getSubtype().getId());
//				granted.setSubtype(subtype);
//			}else {
//				granted.setSubtype(null);
//			}
			granted.setStatus(1);
			granted.setCreateDate(new Date());
			granted.setCreateMode(1);
			granted.setMemberGroupNumber(1);
			granted = (SpecialGranted)this.specialService.setGrantedInfoFromAppForImported(application1, granted);
			this.dao.modify(application1);
			this.dao.add(granted);
			
//			SpecialFunding specialFunding = new SpecialFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			specialFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			specialFunding.setGranted(granted);
//			specialFunding.setGrantedId(granted.getId());
//			specialFunding.setProjectType(application1.getType());
//			specialFunding.setStatus(0);
//			specialFunding.setType(1);
//			dao.add(specialFunding);
		}else{//新建
			//申请信息
			projectFeeApply = (ProjectFee)this.specialService.setProjectFee(projectFeeApply);
			projectFeeApply.setType(1);
			dao.add(projectFeeApply);
			application1 = (SpecialApplication)this.specialService.setAppBaseInfoFromApp(application1);
			application1.setProjectFee(projectFeeApply);
			application1.setFinalAuditResult(2);//已立项
			application1.setFinalAuditStatus(3);
//			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			Date proveDate = this.projectService.setDateHhmmss(approveDate);//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(proveDate);
			application1.setCreateMode(1);
			application1.setCreateDate(new Date());
			//立项信息
			if(granted != null){
//				granted.setNumber(granted.getNumber().trim());
				granted.setNumber(number.trim());
				if(subtypeId != null && !"-1".equals(subtypeId)){
					SystemOption subtype = (SystemOption) dao.query(SystemOption.class, subtypeId);
					granted.setSubtype(subtype);
				}else {
					granted.setSubtype(null);
				}
				granted.setApplication(application1);
				granted.setMemberGroupNumber(1);
				granted.setStatus(1);
				granted.setCreateMode(1);
				granted.setCreateDate(new Date());
			}else{
				return INPUT;
			}
			//从成员列表中获取申请人信息
			if(rms!=null && rms.size()>0){
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
			}else{//没有成员信息
				return INPUT;
			}
			//设置项目所在机构信息
			application1 = (SpecialApplication)this.specialService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
			//保存上传的文件
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
			for(Object rm : rms){
				dao.add(rm);
			}
			specialService.refreshMemberSn(entityId, 1);
			uploadService.flush(groupId);
			String dfsId = projectService.uploadToDmss(granted);
			granted.setDfs(dfsId);
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
		if(addflag == 1){//从已申请未立项项目中选择
			if(null == entityId || entityId.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_GRANTED_DELETE_NULL);
			}
			validateGrantedInfo();
		}else{//新建
			if(fileIds != null && fileIds.length > 1){
				this.addFieldError("file", ProjectInfo.ERROR_FILE_OUT);
			}
			validateAppInfo();
//			validateGrantedInfo();
			validateMemberInfo(1);
		}
	}

	/**
	 * 进入专项任务项目立项修改页面预处理
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.specialService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (SpecialApplication) dao.query(SpecialApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		granted = (SpecialGranted)specialService.getGrantedByAppId(entityId);
		int groupNumber = granted.getMemberGroupNumber(); //最新项目成员组编号
//		int groupNumber = 1;//最原始项目成员组编号
		rms = this.specialService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || granted==null || rms==null || rms.size()<=0){
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
	 * 修改专项任务项目立项
	 * @throws Exception 
	 */
	@Transactional
	public String modify() throws Exception{
		if(!this.specialService.checkIfUnderControl(loginer, application1.getId().trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申请信息
		this.entityId = application1.getId();
		SpecialApplication oldApplication = (SpecialApplication) dao.query(SpecialApplication.class, application1.getId());
		String orignFile = oldApplication.getFile();
		oldApplication = (SpecialApplication)this.specialService.updateAppBaseInfoFromApp(oldApplication, application1);
		ProjectFee oldProjectFeeApply = new ProjectFee();
		
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
//		int groupNumber = oldGranted.getMemberGroupNumber(); //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(oldGranted==null){
			addActionError("立项信息为空！");
			return ERROR;
		}
		oldGranted = (SpecialGranted)this.specialService.updateGrantedInfoFromGranted(oldGranted, granted);
		Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
		oldApplication.setFinalAuditDate(approveDate);
		oldGranted.setApplication(oldApplication);
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
		oldApplication = (SpecialApplication)this.specialService.getApplicationFetchDetailByAppId(entityId);
		oldGranted = (SpecialGranted)this.specialService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
		dao.modify(oldGranted);
		
//		Map parmap = new HashMap();
//		parmap.put("grantedId", oldGranted.getId());
//		SpecialFunding specialGrantedFundingold = (SpecialFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//		if (specialGrantedFundingold != null) {
//			if (specialGrantedFundingold.getStatus() == 0) {
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				specialGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//				specialGrantedFundingold.setGrantedId(oldGranted.getId());
//				specialGrantedFundingold.setProjectType(application1.getType());
//				specialGrantedFundingold.setStatus(0);
//				dao.modify(specialGrantedFundingold);
//			}
//		}else {
//			SpecialFunding specialGrantedFunding = new SpecialFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			specialGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//			specialGrantedFunding.setGranted(oldGranted);
//			specialGrantedFunding.setGrantedId(oldGranted.getId());
//			specialGrantedFunding.setProjectType(application1.getType());
//			specialGrantedFunding.setStatus(0);
//			specialGrantedFunding.setType(1);
//			dao.add(specialGrantedFunding);
//		}
		
		for(Object rm : rms){
			dao.add(rm);
		}
		specialService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != oldApplication.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != oldApplication.getFile()){ //现在有文件
					projectService.checkInToDmss(oldApplication);
				}else{ //现在没文件
					dmssService.deleteFile(oldApplication.getDfs());
					oldApplication.setDfs(null);
					dao.modify(oldApplication);
				}
			}else{ //原来没有文件
				if(oldApplication.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(oldApplication);
					oldApplication.setDfs(dfsId);
					dao.modify(oldApplication);
				}
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 修改立项时校验
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
				SpecialMember member = rms.get(i);
				this.validateMemberInfo(member, type);
			}
		}
	}

	public SpecialGranted getGranted() {
		return granted;
	}
	public void setGranted(SpecialGranted granted) {
		this.granted = granted;
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
	public ISpecialService getSpecialService() {
		return specialService;
	}
	public void setSpecialService(ISpecialService specialService) {
		this.specialService = specialService;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSubtypeId() {
		return subtypeId;
	}
	public void setSubtypeId(String subtypeId) {
		this.subtypeId = subtypeId;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}

}