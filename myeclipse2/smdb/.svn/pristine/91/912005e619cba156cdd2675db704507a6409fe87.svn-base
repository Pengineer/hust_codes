package csdc.action.project.general;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectMember;
import csdc.bean.SystemOption;
import csdc.service.IGeneralService;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 一般项目立项子类
 * 实现了父类所有的抽象方法及一般项目立项特有的方法
 * @author 雷达、刘雅琴、余潜玉
 */
public class ApplicationGrantedAction extends csdc.action.project.ApplicationGrantedAction {

	private static final long serialVersionUID = -700148736686965249L;
	//管理人员使用
	private static final String HQL2 = "from GeneralGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join gra.subtype so left outer join app.topic top, GeneralMember mem where 1=1 " +
		"and app.finalAuditStatus=3 and app.finalAuditResult=2 and mem.application.id = app.id";
	//研究人员使用
	private static final String HQL3 = "from GeneralGranted gra left outer join gra.application app "+ 
		"left outer join gra.university uni left outer join app.topic top left outer join gra.subtype so, GeneralMember mem " +
		"where mem.application.id = app.id ";
	private static final String PAGE_NAME = "generalGrantedPage";//列表页面名称
	private static final String GRANTED_CLASS_NAME = "GeneralGranted";//一般项目立项类类名
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final String BUISINESS_TYPE = "016";//一般项目立项对应的业务类型名称
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//判断一般项目立项是否在管辖范围内的标志位
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	private IGeneralService generalService;
	private GeneralGranted granted;//项目对象
	private GeneralApplication application1;//项目申请对象
	private List<GeneralMember> rms;//项目成员
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
	 * 添加一般项目立项计划书和经费申请
	 */
	@Transactional
	public String addGra() {
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		projectFeeGranted.setType(2);
		dao.add(projectFeeGranted);
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (GeneralGranted)this.doWithAddOrMdoify(1,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加一般项目立项计划书和经费申请
	 */
	@Transactional
	public String modifyGra() {
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
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
		granted = (GeneralGranted)this.doWithAddOrMdoify(2,granted);
		graFlag= 1;
		return SUCCESS;
	}
	/**
	 * 添加录入的一般项目立项计划结果
	 * @author yangfq
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String addResult(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectFee projectFeeGranted = new ProjectFee();
		projectFeeGranted = this.doWithAddResultFee(projectFeeGranted);
		if (projectFeeGranted != null) {
			projectFeeGranted.setType(2);
			dao.add(projectFeeGranted);
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, this.projectid);
		granted.setProjectFee(projectFeeGranted);
		granted = (GeneralGranted)this.doWithAddResult(granted);
		dao.add(granted);
		return SUCCESS;
	}
	/**
	 * 添加一般项目立项
	 */
	@Transactional
	public String add(){
		GeneralGranted granted = new GeneralGranted();
		if(addflag == 1){//从已申请未立项项目中选择
			application1 = (GeneralApplication)this.generalService.getApplicationFetchDetailByAppId(entityId);
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
			granted.setIsImported(1);
			granted.setMemberGroupNumber(1);
			granted = (GeneralGranted)this.generalService.setGrantedInfoFromAppForImported(application1, granted);
			this.dao.modify(application1);
			this.dao.add(granted);
			
			GeneralFunding generalFunding = new GeneralFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			generalFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			generalFunding.setGranted(granted);
			generalFunding.setGrantedId(granted.getId());
			generalFunding.setProjectType(application1.getType());
			generalFunding.setStatus(0);
			generalFunding.setType(1);
			dao.add(generalFunding);
		}else{//新建
			//申报信息
			projectFeeApply = (ProjectFee)this.generalService.setProjectFee(projectFeeApply);
			projectFeeApply.setType(1);
			dao.add(projectFeeApply);
			application1 = (GeneralApplication)this.generalService.setAppBaseInfoFromApp(application1);
			application1.setProjectFee(projectFeeApply);
			if(application1.getTopic() != null && !"-1".equals(application1.getTopic().getId())){//项目主题
				SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
				application1.setTopic(topic);
			}else {
				application1.setTopic(null);
			}
			application1.setFinalAuditResult(2);//已立项
			application1.setFinalAuditStatus(3);
//			Date approveDate = this.projectService.setDateHhmmss(granted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
			Date proveDate = this.projectService.setDateHhmmss(approveDate);//设置指定日期时分秒为当前系统时间时分秒
			application1.setFinalAuditDate(proveDate);
			application1.setIsImported(1);
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
				granted.setIsImported(1);
			}else{
				return INPUT;
			}
			//从成员列表中获取申请人信息
			if(rms!=null && rms.size()>0){
				String applicantId = "";
				String applicantName = "";
				for(int i=0; i<rms.size(); i++){
					GeneralMember member = rms.get(i);
					if(member.getIsDirector() == 1){//主要负责人
						Object[] idAndName = this.generalService.getAppDirectorIdAndName(member);
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
			application1 = (GeneralApplication)this.generalService.setAppAgencyInfoFromApp(application1, application1, deptInstFlag);
			//保存上传的文件
			String groupId = "file_add";
			application1.setFile(this.saveAppFile(application1, application1.getUniversity().getId(), application1.getFile(), groupId));
			//成员信息
			if(rms!=null && rms.size()>0){
				for (int i=0; i<rms.size(); i++){
					GeneralMember member = rms.get(i);
					member = (GeneralMember)this.generalService.setMemberInfoFromMember(member);
					member.setMemberSn(i + 1);
					member.setApplication(application1);//项目申请
					rms.set(i, member);
				}
			}
			entityId = dao.add(application1);
			application1 = (GeneralApplication)this.generalService.getApplicationFetchDetailByAppId(entityId);
			granted = (GeneralGranted)this.generalService.setGrantedInfoFromAppForImported(application1, granted);
			dao.add(granted);
			GeneralFunding generalFunding = new GeneralFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			generalFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
			generalFunding.setGranted(granted);
			generalFunding.setGrantedId(granted.getId());
			generalFunding.setProjectType(application1.getType());
			generalFunding.setStatus(0);
			generalFunding.setType(1);
			dao.add(generalFunding);
			for(Object rm : rms){
				dao.add(rm);
			}
			generalService.refreshMemberSn(entityId, 1);
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
	 * 进入一般项目立项修改页面预处理
	 */
	@SuppressWarnings("unchecked")
	public String toModify(){
		if(!this.generalService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		application1 = (GeneralApplication) dao.query(GeneralApplication.class, entityId);//entityId为项目申请id
		if (application1.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, application1.getProjectFee().getId());
		}
		granted = (GeneralGranted)generalService.getGrantedByAppId(entityId);
		int groupNumber = granted.getMemberGroupNumber(); //最新项目成员组编号
//		int groupNumber = 1;//最原始项目成员组编号
		rms = this.generalService.getMemberFetchUnit(entityId, groupNumber);
		if(application1==null || granted==null || rms==null || rms.size()<=0){
			addActionError("所选的项目申报信息为空或者项目成员为空！");
			return ERROR;
		}else{
			deptInstFlag = this.generalService.getDeptInstFlagByApp(application1);
			for(int i=0; i<rms.size(); i++){
				GeneralMember member = rms.get(i);
				member = (GeneralMember)this.generalService.setMemberPersonInfoFromMember(member);
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
	 * 修改一般项目立项
	 */
	@Transactional
	public String modify(){
		if(!this.generalService.checkIfUnderControl(loginer, application1.getId().trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		//申报信息
		this.entityId = application1.getId();
		GeneralApplication oldApplication = (GeneralApplication) dao.query(GeneralApplication.class, application1.getId());
		oldApplication = (GeneralApplication)this.generalService.updateAppBaseInfoFromApp(oldApplication, application1);
		if(application1.getTopic().getId() != null && !application1.getTopic().getId().equals("-1")){//主题
			SystemOption topic = (SystemOption) dao.query(SystemOption.class, application1.getTopic().getId());
			oldApplication.setTopic(topic);
		}else{
			oldApplication.setTopic(null);
		}
		ProjectFee oldProjectFeeApply = new ProjectFee();
		
		if (oldApplication.getProjectFee() != null) {
			oldProjectFeeApply = dao.query(ProjectFee.class, oldApplication.getProjectFee().getId());
			oldProjectFeeApply = this.generalService.updateProjectFee(oldProjectFeeApply,projectFeeApply);
			dao.modify(oldProjectFeeApply);
		}else {
			oldProjectFeeApply = this.generalService.setProjectFee(projectFeeApply);
			oldProjectFeeApply.setType(1);
			dao.add(oldProjectFeeApply); 
		}
		oldApplication.setProjectFee(oldProjectFeeApply);
		//立项信息
		GeneralGranted oldGranted = (GeneralGranted)this.generalService.getGrantedByAppId(application1.getId());
//		int groupNumber = oldGranted.getMemberGroupNumber(); //最新项目成员组编号
		int groupNumber = 1;//最原始项目成员组编号
		if(oldGranted==null){
			addActionError("立项信息为空！");
			return ERROR;
		}
		oldGranted = (GeneralGranted)this.generalService.updateGrantedInfoFromGranted(oldGranted, granted);
		Date approveDate = this.projectService.setDateHhmmss(oldGranted.getApproveDate());//设置指定日期时分秒为当前系统时间时分秒
		oldApplication.setFinalAuditDate(approveDate);
		oldGranted.setApplication(oldApplication);
		//从成员列表中获取申请人信息
		String applicantId = "";
		String applicantName = "";
		for(int i=0; i<rms.size(); i++){
			GeneralMember member = rms.get(i);
			if(member.getIsDirector() == 1){//主要负责人
				Object[] idAndName = this.generalService.getAppDirectorIdAndName(member);
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
		oldApplication = (GeneralApplication)this.generalService.setAppAgencyInfoFromApp(oldApplication, application1, deptInstFlag);
		//修改申请书文件
		String groupId = "file_" + application1.getId();
		String newFileName = this.saveAppFile(oldApplication, oldApplication.getUniversity().getId(), oldApplication.getFile(), groupId);
		if(newFileName != null){
			oldApplication.setFile(newFileName);
		}
		//删除原成员信息
		for (ProjectMember member : this.generalService.getMember(application1.getId(), groupNumber)) {
			dao.delete(member);
		}
		//成员信息
		for (int i=0; i<rms.size(); i++){
			GeneralMember member = rms.get(i);
			member = (GeneralMember)this.generalService.setMemberInfoFromMember(member);
			member.setMemberSn(i + 1);
			member.setGroupNumber(groupNumber);
			member.setApplication(application1);//项目申请
			rms.set(i, member);
		}
		dao.modify(oldApplication);
		oldApplication = (GeneralApplication)this.generalService.getApplicationFetchDetailByAppId(entityId);
		oldGranted = (GeneralGranted)this.generalService.setGrantedInfoFromAppForImported(oldApplication, oldGranted);
		dao.modify(oldGranted);
		
		Map parmap = new HashMap();
		parmap.put("grantedId", oldGranted.getId());
		GeneralFunding generalGrantedFundingold = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 1 ", parmap);
		if (generalGrantedFundingold != null) {
			if (generalGrantedFundingold.getStatus() == 0) {
				//立项则添加立项拨款申请，金额默认为批准经费的50%
//				generalGrantedFundingold.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
				generalGrantedFundingold.setGrantedId(oldGranted.getId());
				generalGrantedFundingold.setProjectType(application1.getType());
				generalGrantedFundingold.setStatus(0);
				dao.modify(generalGrantedFundingold);
			}
		}else {
			GeneralFunding generalGrantedFunding = new GeneralFunding();
			//立项则添加立项拨款申请，金额默认为批准经费的50%
//			generalGrantedFunding.setGrantedFundFee(DoubleTool.mul(oldGranted.getApproveFee(),0.5));
			generalGrantedFunding.setGranted(oldGranted);
			generalGrantedFunding.setGrantedId(oldGranted.getId());
			generalGrantedFunding.setProjectType(application1.getType());
			generalGrantedFunding.setStatus(0);
			generalGrantedFunding.setType(1);
			dao.add(generalGrantedFunding);
		}
		
		for(Object rm : rms){
			dao.add(rm);
		}
		generalService.refreshMemberSn(entityId, 1);
		uploadService.flush(groupId);
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
//			if(application.getTopic().getId() == null || application.getTopic().getId().equals("-1")){
//				this.addFieldError("application.topic.id", ProjectInfo.ERROR_PROJECT_TOPIC_NULL);
//			}
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
				GeneralMember member = rms.get(i);
				this.validateMemberInfo(member, type);
			}
		}
	}

	public GeneralGranted getGranted() {
		return granted;
	}
	public void setGranted(GeneralGranted granted) {
		this.granted = granted;
	}
	public GeneralApplication getApplication1() {
		return application1;
	}
	public void setApplication1(GeneralApplication application1) {
		this.application1 = application1;
	}
	public List<GeneralMember> getRms() {
		return rms;
	}
	public void setRms(List<GeneralMember> rms) {
		this.rms = rms;
	}
	public IGeneralService getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
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