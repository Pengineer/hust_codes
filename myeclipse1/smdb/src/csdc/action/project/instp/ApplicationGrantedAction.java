package csdc.action.project.instp;

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

import csdc.bean.InstpApplication;
import csdc.bean.InstpFunding;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.bean.SystemOption;
import csdc.service.IInstpService;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * @author 肖雅
 */
public class ApplicationGrantedAction extends csdc.action.project.ApplicationGrantedAction {

	private static final long serialVersionUID = -700148736686965249L;
	//管理人员使用
	private static final String HQL2 = "from InstpGranted gra left outer join gra.application app " +
		"left outer join gra.university uni left outer join gra.subtype so, InstpMember mem where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and mem.application.id=app.id ";
	//研究人员使用
	private static final String HQL3 = "from InstpGranted gra left outer join gra.application app " +
		"left outer join gra.university uni left outer join gra.subtype so, InstpMember mem  " +
		"where mem.application.id=app.id ";
	private static final String PAGE_NAME = "instpGrantedPage";//列表页面名称
	private static final String GRANTED_CLASS_NAME = "InstpGranted";
	private static final String PROJECT_TYPE = "instp";
	private static final String BUSINESS_TYPE = "021";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	private IInstpService instpService;
	private InstpGranted granted;//项目对象
	private InstpApplication application1;//项目申请对象
	private List<InstpMember> rms;//项目成员

	public String pageName() {
		return ApplicationGrantedAction.PAGE_NAME;
	}
	public String projectType(){
		return ApplicationGrantedAction.PROJECT_TYPE;
	}
	public String businessType(){
		return ApplicationGrantedAction.BUSINESS_TYPE;
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
	public String listHql2(){
		return ApplicationGrantedAction.HQL2;
	}
	public String listHql3(){
		return ApplicationGrantedAction.HQL3;
	}

	
	/**
	 * 添加一般项目立项计划书和经费申请
	 * @throws Exception 
	 */
	@Transactional
	public String addGra() throws Exception {
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeGranted.setType(2);
		dao.add(projectFeeGranted);
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (InstpGranted)this.doWithAddOrMdoify(1,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加一般项目立项计划书和经费申请
	 * @throws Exception 
	 */
	@Transactional
	public String modifyGra() throws Exception {
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, projectid);
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
		granted = (InstpGranted)this.doWithAddOrMdoify(2,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加录入的一般项目立项计划结果
	 * @author yangfq
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult() throws Exception{
		if(!this.instpService.checkIfUnderControl(loginer, this.instpService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeGranted = new ProjectFee();
		projectFeeGranted = this.doWithAddResultFee(projectFeeGranted);
		if (projectFeeGranted != null) {
			projectFeeGranted.setType(2);
			dao.add(projectFeeGranted);
		}
		InstpGranted granted = (InstpGranted) this.dao.query(InstpGranted.class, this.projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (InstpGranted)this.doWithAddResult(granted);
		dao.add(granted);
		return SUCCESS;
	}

	/**
	 * 添加基地项目立项
	 * @throws Exception 
	 */
	@Transactional
	public String add() throws Exception{
		if(addflag == 1){//从已申请未立项项目中选择
			application1 = (InstpApplication)this.dao.query(InstpApplication.class, entityId);
			application1.setFinalAuditResult(2);//已立项
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
			granted.setApplication(application1);
			granted.setNumber(granted.getNumber().trim());//批准号
			if(granted.getSubtype().getId() != null && !"-1".equals(granted.getSubtype().getId())){
				SystemOption subtype = (SystemOption) dao.query(SystemOption.class, granted.getSubtype().getId());
				granted.setSubtype(subtype);
			}else {
				granted.setSubtype(null);
			}
			granted.setStatus(1);
			granted.setCreateMode(1);
			granted.setCreateDate(new Date());
			granted.setMemberGroupNumber(1);
			granted = (InstpGranted)this.instpService.setGrantedInfoFromAppForImported(application1, granted);
			this.dao.modify(application1);
			this.dao.add(granted);
			
//			InstpFunding instpFunding = new InstpFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			instpFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			instpFunding.setGranted(granted);
//			instpFunding.setGrantedId(granted.getId());
//			instpFunding.setProjectType(application1.getType());
//			instpFunding.setStatus(0);
//			instpFunding.setType(1);
//			dao.add(instpFunding);
		}else{//新建
			//申请信息
			projectFeeApply = (ProjectFee)this.instpService.setProjectFee(projectFeeApply);
			projectFeeApply.setType(1);
			dao.add(projectFeeApply);
			application1 = (InstpApplication)this.instpService.setAppBaseInfoFromApp(application1);
			application1.setProjectFee(projectFeeApply);
			application1.setFinalAuditResult(2);//已立项
			application1.setFinalAuditStatus(3);
			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(approveDate);
			application1.setCreateMode(1);
			application1.setCreateDate(new Date());
			//立项信息
			if(granted != null){
//			projectFeeGranted = (ProjectFee)this.instpService.setProjectFee(projectFeeGranted);
//			projectFeeGranted.setType(2);
//			dao.add(projectFeeGranted);
//			granted.setProjectFee(projectFeeGranted);
			
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
			granted.setCreateMode(1);
			granted.setCreateDate(new Date());
			}else{
				return INPUT;
			}
			//从成员列表中获取申请人信息
			String applicantId = "";
			String applicantName = "";
			for(int i=0; i<rms.size(); i++){
				InstpMember member = rms.get(i);
				if(member.getIsDirector() == 1){//主要负责人
					Object[] idAndName = this.instpService.getAppDirectorIdAndName(member);
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
			application1 = (InstpApplication)this.instpService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
			//保存上传的文件
			String groupId = "file_add";
			application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
			//成员信息
			if(rms!=null && rms.size()>0){
				for (int i=0; i<rms.size(); i++){
					InstpMember member = rms.get(i);
					member = (InstpMember)this.instpService.setMemberInfoFromMember(member);
					member.setMemberSn(i + 1);//序号
					member.setApplication(application1);//项目申请
					rms.set(i, member);
				}
			}
			entityId = dao.add(application1);
			application1 = (InstpApplication)this.instpService.getApplicationFetchDetailByAppId(entityId);
			granted = (InstpGranted)this.instpService.setGrantedInfoFromAppForImported(application1, granted);
			dao.add(granted);
			
//			InstpFunding instpFunding = new InstpFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			instpFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//			instpFunding.setGranted(granted);
//			instpFunding.setGrantedId(granted.getId());
//			instpFunding.setProjectType(application1.getType());
//			instpFunding.setStatus(0);
//			instpFunding.setType(1);
//			dao.add(instpFunding);
			
			for(Object rm : rms){
				dao.add(rm);
			}
			instpService.refreshMemberSn(entityId, 1);
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
			validateGrantedInfo();
			validateMemberInfo(1);
		}
	}

	/**
	 * 准备修改基地项目立项
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.instpService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (InstpApplication) dao.query(InstpApplication.class, entityId);//entityId为项目申请id
		
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		
		granted = (InstpGranted)instpService.getGrantedByAppId(entityId);
//		int groupNumber = granted.getMemberGroupNumber(); //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		rms = this.instpService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || granted==null || rms==null || rms.size()<=0){
			addActionError("所选项目的申请信息为空或者立项信息为空或者项目成员信息为空");
			return ERROR;
		}else{
			deptInstFlag = this.instpService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				InstpMember member = rms.get(i);
				member = (InstpMember)this.instpService.setMemberPersonInfoFromMember(member);
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
	 * 修改基地项目立项信息
	 * @throws Exception 
	 */
	@Transactional
	public String modify() throws Exception{
		if(!this.instpService.checkIfUnderControl(loginer, application1.getId().trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申请信息
		this.entityId = application1.getId();
		InstpApplication oldApplication = (InstpApplication) dao.query(InstpApplication.class, application1.getId());
		String orignFile = oldApplication.getFile();
		oldApplication = (InstpApplication)this.instpService.updateAppBaseInfoFromApp(oldApplication, application1);
		
		ProjectFee oldProjectFeeApply = new ProjectFee();
		
		if (oldApplication.getProjectFee() != null) {
			oldProjectFeeApply = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFeeApply = this.instpService.updateProjectFee(oldProjectFeeApply,projectFeeApply);
			dao.modify(oldProjectFeeApply);
		}else {
			oldProjectFeeApply = this.instpService.setProjectFee(projectFeeApply);
			oldProjectFeeApply.setType(1);
			dao.add(oldProjectFeeApply); 
		}
		oldApplication.setProjectFee(oldProjectFeeApply);
		
		//立项信息
		InstpGranted oldGranted = (InstpGranted)this.instpService.getGrantedByAppId(application1.getId());
//		int groupNumber = oldGranted.getMemberGroupNumber();//最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(oldGranted == null){
			addActionError("项目立项信息为空");
			return ERROR;
		}
		oldGranted = (InstpGranted)this.instpService.updateGrantedInfoFromGranted(oldGranted, granted);
		Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
		oldApplication.setFinalAuditDate(approveDate);
		oldGranted.setApplication(oldApplication);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			InstpMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.instpService.getAppDirectorIdAndName(member);
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
		oldApplication = (InstpApplication)this.instpService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.instpService.getMember(application1.getId(), groupNumber)) {
			dao.delete(member);
		}
//		this.instpService.deleteMore(this.instpService.getMember(application.getId(), groupNumber));
		//成员信息
		for (int i=0; i<rms.size(); i++){
			InstpMember member = rms.get(i);
			member = (InstpMember)this.instpService.setMemberInfoFromMember(member);
			member.setMemberSn(i + 1);//序号
			member.setGroupNumber(groupNumber);
			member.setApplication(oldApplication);//项目申请
			rms.set(i, member);
		}
		dao.modify(oldApplication);
		oldApplication = (InstpApplication)this.instpService.getApplicationFetchDetailByAppId(entityId);
		oldGranted = (InstpGranted)this.instpService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
		dao.modify(oldGranted);
		
//		Map parmap = new HashMap();
//		parmap.put("grantedId", oldGranted.getId());
//		InstpFunding instpGrantedFundingold = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
//		if (instpGrantedFundingold != null) {
//			if (instpGrantedFundingold.getStatus() == 0) {
//				//立项则添加立项拨款申请，金额默认为批准经费的50%
////				instpGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//				instpGrantedFundingold.setGrantedId(oldGranted.getId());
//				instpGrantedFundingold.setProjectType(application1.getType());
//				instpGrantedFundingold.setStatus(0);
//				dao.modify(instpGrantedFundingold);
//			}
//		}else {
//			InstpFunding instpGrantedFunding = new InstpFunding();
//			//立项则添加立项拨款申请，金额默认为批准经费的50%
////			instpGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
//			instpGrantedFunding.setGranted(oldGranted);
//			instpGrantedFunding.setGrantedId(oldGranted.getId());
//			instpGrantedFunding.setProjectType(application1.getType());
//			instpGrantedFunding.setStatus(0);
//			instpGrantedFunding.setType(1);
//			dao.add(instpGrantedFunding);
//		}
		
		for(Object rm : rms){
			dao.add(rm);
		}
		instpService.refreshMemberSn(entityId, 1);
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
				InstpMember member = rms.get(i);
				this.validateMemberInfo(member,  type);
				if(member.getIsSubprojectDirector()!=0 && member.getIsSubprojectDirector()!=1){
					this.addActionError(ProjectInfo.ERROR_PROJECT_IS_SUBDIRECTOR_NULL);
				}
			}
		}
	}

	public InstpGranted getGranted() {
		return granted;
	}
	public void setGranted(InstpGranted granted) {
		this.granted = granted;
	}
	public InstpApplication getApplication1() {
		return application1;
	}
	public void setApplication1(InstpApplication application1) {
		this.application1 = application1;
	}
	public List<InstpMember> getRms() {
		return rms;
	}
	public void setRms(List<InstpMember> rms) {
		this.rms = rms;
	}
	public IInstpService getInstpService() {
		return instpService;
	}
	public void setInstpService(IInstpService instpService) {
		this.instpService = instpService;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

}