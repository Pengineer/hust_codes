package csdc.action.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.FundList;
import csdc.bean.KeyTopicSelection;
import csdc.bean.Person;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionReview;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.ProjectData;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.service.IKeyService;
import csdc.service.IProductService;
import csdc.service.IProjectService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DoubleTool;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目管理基类
 * 定义了子类需要实现的抽象方法并实现所有类别项目的公用方法
 * @author 余潜玉、肖雅
 */
public abstract class ProjectBaseAction extends BaseAction {

	private static final long serialVersionUID = 8829071233244273703L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";//列表页面时间格式
	private static final String PAGE_BUFFER_ID = "app.id";//缓存id
	protected IProductService productService;//成果管理接口
	@Autowired
	protected IKeyService keyService;//重大攻关项目管理接口
	protected IProjectService projectService;//项目管理接口
	protected String topsId;//选题id
	protected String projectid;//项目id
	protected String mainFlag;//首页进入列表参数
	protected String selectedTab;//查看项目默认显示标签
	protected Integer listType;//项目列表类型(1.申报列表；2.立项列表；3.中检列表；4.结项列表；5.变更列表；6项目管理系统首页进入；7我的项目列表；8评审项目列表；9用户信息中心首页进入；10评审项目申报列表；11年检列表);
	protected boolean isDirector = false;//是否项目负责人
	protected List<Integer> isEndReviewer;//是否是项目结项评审人，0.不是；1.是评审人但不是评审组长；2.是评审人且是评审组长
	protected int isAppReviewer;//是否是项目申报评审人，0.不是；1.是评审人但不是评审组长；2.是评审人且是评审组长
	protected String projectType;//项目类型
	protected String deadline;//业务截止时间 
	protected Integer appStatus;//申报业务设置状态
	protected Integer annStatus;//年检业务设置状态
	protected Integer midStatus;//中检业务设置状态
	protected Integer endStatus;//结项业务设置状态
	protected Integer varStatus;//变更业务设置状态
	protected Integer topsStatus;//选题业务设置状态
	protected Integer graStatus;//立项业务设置状态
	@Autowired
	protected SystemOptionDao soDao;
	
	public abstract String projectType();//项目类别
	public abstract int checkApplicationFlag();//获得判断申请项目是否在管辖范围内的标志位
	public abstract int checkGrantedFlag();//获得判断立项项目是否在管辖范围内的标志位
	public abstract String businessType();//获得业务类型名称
	protected KeyTopicSelection topicSelection;//选题申报对象
	
	protected Double applyFee,otherFee,approveFee,bookFee,dataFee,travelFee,conferenceFee,internationalFee,deviceFee,consultationFee,laborFee,printFee,indirectFee,otherFeeD,totalFee,surplusFee,fundedFee,toFundFee;
	protected String bookNote,dataNote,travelNote,conferenceNote,internationalNote,deviceNote,consultationNote,laborNote,printNote,indirectNote,otherNote,feeNote;
	protected ProjectFee projectFeeApply,projectFeeGranted,projectFeeAnn,projectFeeMid,projectFeeEnd,newFee;//项目申报经费对象，项目立项经费对象，项目年检经费对象，项目中检经费对象
	protected ProjectFunding projectFunding;//拨款对象
	
	public ProjectFunding getProjectFunding() {
		return projectFunding;
	}
	public void setProjectFunding(ProjectFunding projectFunding) {
		this.projectFunding = projectFunding;
	}
	public ProjectFee getNewFee() {
		return newFee;
	}
	public void setNewFee(ProjectFee newFee) {
		this.newFee = newFee;
	}
	public ProjectFee getProjectFeeEnd() {
		return projectFeeEnd;
	}
	public void setProjectFeeEnd(ProjectFee projectFeeEnd) {
		this.projectFeeEnd = projectFeeEnd;
	}
	public ProjectFee getProjectFeeMid() {
		return projectFeeMid;
	}
	public void setProjectFeeMid(ProjectFee projectFeeMid) {
		this.projectFeeMid = projectFeeMid;
	}
	protected ProjectGranted projectGranted;
	
	public ProjectGranted getProjectGranted() {
		return projectGranted;
	}
	public void setProjectGranted(ProjectGranted projectGranted) {
		this.projectGranted = projectGranted;
	}
	public ProjectFee getProjectFeeAnn() {
		return projectFeeAnn;
	}
	public void setProjectFeeAnn(ProjectFee projectFeeAnn) {
		this.projectFeeAnn = projectFeeAnn;
	}
	public String[] column(){
		return null;
	}
	public Double getSurplusFee() {
		return surplusFee;
	}
	public void setSurplusFee(Double surplusFee) {
		this.surplusFee = surplusFee;
	}
	public Double getToFundFee() {
		return toFundFee;
	}
	public void setToFundFee(Double toFundFee) {
		this.toFundFee = toFundFee;
	}
	public Double getApplyFee() {
		return applyFee;
	}
	public void setApplyFee(Double applyFee) {
		this.applyFee = applyFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}
	public Double getBookFee() {
		return bookFee;
	}
	public void setBookFee(Double bookFee) {
		this.bookFee = bookFee;
	}
	public Double getDataFee() {
		return dataFee;
	}
	public void setDataFee(Double dataFee) {
		this.dataFee = dataFee;
	}
	public Double getTravelFee() {
		return travelFee;
	}
	public void setTravelFee(Double travelFee) {
		this.travelFee = travelFee;
	}
	public Double getConferenceFee() {
		return conferenceFee;
	}
	public void setConferenceFee(Double conferenceFee) {
		this.conferenceFee = conferenceFee;
	}
	public Double getInternationalFee() {
		return internationalFee;
	}
	public void setInternationalFee(Double internationalFee) {
		this.internationalFee = internationalFee;
	}
	public Double getDeviceFee() {
		return deviceFee;
	}
	public void setDeviceFee(Double deviceFee) {
		this.deviceFee = deviceFee;
	}
	public Double getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(Double consultationFee) {
		this.consultationFee = consultationFee;
	}
	public Double getLaborFee() {
		return laborFee;
	}
	public void setLaborFee(Double laborFee) {
		this.laborFee = laborFee;
	}
	public Double getPrintFee() {
		return printFee;
	}
	public void setPrintFee(Double printFee) {
		this.printFee = printFee;
	}
	public Double getIndirectFee() {
		return indirectFee;
	}
	public void setIndirectFee(Double indirectFee) {
		this.indirectFee = indirectFee;
	}
	public Double getOtherFeeD() {
		return otherFeeD;
	}
	public void setOtherFeeD(Double otherFeeD) {
		this.otherFeeD = otherFeeD;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public String getBookNote() {
		return bookNote;
	}
	public void setBookNote(String bookNote) {
		this.bookNote = bookNote;
	}
	public String getDataNote() {
		return dataNote;
	}
	public void setDataNote(String dataNote) {
		this.dataNote = dataNote;
	}
	public String getTravelNote() {
		return travelNote;
	}
	public void setTravelNote(String travelNote) {
		this.travelNote = travelNote;
	}
	public String getConferenceNote() {
		return conferenceNote;
	}
	public void setConferenceNote(String conferenceNote) {
		this.conferenceNote = conferenceNote;
	}
	public String getInternationalNote() {
		return internationalNote;
	}
	public void setInternationalNote(String internationalNote) {
		this.internationalNote = internationalNote;
	}
	public String getDeviceNote() {
		return deviceNote;
	}
	public void setDeviceNote(String deviceNote) {
		this.deviceNote = deviceNote;
	}
	public String getConsultationNote() {
		return consultationNote;
	}
	public void setConsultationNote(String consultationNote) {
		this.consultationNote = consultationNote;
	}
	public String getLaborNote() {
		return laborNote;
	}
	public void setLaborNote(String laborNote) {
		this.laborNote = laborNote;
	}
	public String getPrintNote() {
		return printNote;
	}
	public void setPrintNote(String printNote) {
		this.printNote = printNote;
	}
	public String getIndirectNote() {
		return indirectNote;
	}
	public void setIndirectNote(String indirectNote) {
		this.indirectNote = indirectNote;
	}
	public String getOtherNote() {
		return otherNote;
	}
	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
	public Double getFundedFee() {
		return fundedFee;
	}
	public void setFundedFee(Double fundedFee) {
		this.fundedFee = fundedFee;
	}
	public String getFeeNote() {
		return feeNote;
	}
	public void setFeeNote(String feeNote) {
		this.feeNote = feeNote;
	}
	public String dateFormat() {
		return ProjectBaseAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return ProjectBaseAction.PAGE_BUFFER_ID;
	}
	public Object[] simpleSearchCondition(){
		return null;
	}
	public Object[] advSearchCondition(){
		return null;
	}
	public String pageName(){
		return null;
	}
	
	/**
	 * 进入项目查看页面预处理
	 */
	public String toView() {
		if(null != entityId && !entityId.isEmpty()){
			ProjectApplication application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
			if(application.getFinalAuditStatus() == 3 && application.getFinalAuditResult() == 2){//已立项
				if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
					return ERROR;
				}
			}else{//未立项
				if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
					return ERROR;
				}
			}
			//业务设置状态1：业务激活2：业务停止
			String type = "";
			if(application.getType().equals("general")){
				type = "01";
			}else if(application.getType().equals("instp")){
				type = "02";
			}else if(application.getType().equals("post")){
				type = "03";
			}else if(application.getType().equals("key")){
				type = "04";
			}else if(application.getType().equals("entrust")){
				type = "05";
			}
			appStatus = this.projectService.getBusinessStatus(type + "1");
			annStatus = this.projectService.getBusinessStatus(type + "5", application.getId());
			midStatus = this.projectService.getBusinessStatus(type + "2", application.getId());
			endStatus = this.projectService.getBusinessStatus(type + "3", application.getId());
			varStatus = this.projectService.getBusinessStatus(type + "4", application.getId());
			graStatus = this.projectService.getBusinessStatus(type + "6", application.getId());
			this.projectid = this.projectService.getGrantedIdByAppId(entityId);
		}
		if(null != topsId){
			if(!this.projectService.checkIfUnderControl(loginer, topsId.trim(), 26, true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
			topsStatus = this.projectService.getBusinessStatus("046");
		}
		return SUCCESS;
	}
	
	/**
	 * 查看项目
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public String view(){
		Map session = ActionContext.getContext().getSession();
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		String belongId = baseService.getBelongIdByAccount(account);//账号所属id，若为教师，则是personId，若是机构，则是机构id，若是管理者，则是officorId
		int isPrincipal = account.getIsPrincipal();//是否主账号 1：是0：否
		List<String> appFileSizeList = new ArrayList<String>();//申报书大小
		List<String> endiFileSizeList = new ArrayList<String>();//结项申报书大小
		List<String> resFileSizeList = new ArrayList<String>();//研究数据包
		List<String> postponementPlanFileSizeList = new ArrayList<String>();//延期计划书大小   
		List<String> midFileSizeList = new ArrayList<String>();//中检申报书大小
		List<String> varFileSizeList = new ArrayList<String>();//变更申请书大小  
		List<String> annFileSizeList = new ArrayList<String>();//年检申请书大小
		List<String> graFileSizeList = new ArrayList<String>();//立项计划书大小
		if(null != entityId && !entityId.isEmpty()){
			//申报信息
//			ProjectFee projectFeeApply = null;
			ProjectApplication application = this.projectService.getApplicationFetchDetailByAppId(entityId);
			if (application.getProjectFee() != null) {
				projectFeeApply = dao.query(ProjectFee.class, application.getProjectFee().getId());
			}
			if (application.getFile() != null) {
				String[] attachPath = application.getFile().split("; ");
				InputStream is = null;
				for (String path : attachPath) {
					is = ServletActionContext.getServletContext().getResourceAsStream(path);
					if (null != is) {
						try {
							appFileSizeList.add(baseService.accquireFileSize(is.available()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {// 附件不存在
						appFileSizeList.add(null);
					}
					jsonMap.put("appFileSizeList", appFileSizeList);
				}
			}
			//申报各级审核人和审核人所在机构信息
			List<String> appAuditorInfo = new ArrayList<String>();
			appAuditorInfo= this.projectService.getAppAuditorInfo(application,loginer);
			//立项信息
			
//			ProjectFee projectFeeGranted = null;
			this.projectid = this.projectService.getGrantedIdByAppId(entityId);
			ProjectGranted granted = (ProjectGranted)this.projectService.getGrantedFetchDetailByGrantedId(projectid);
			if (granted != null) {
				if (granted.getProjectFee() != null) {
					projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
				}
			}
			//立项计划书
			if (null != granted && granted.getFile() != null) {
				String[] attachPath = granted.getFile().split("; ");
				InputStream is = null;
				for (String path : attachPath) {
					is = ServletActionContext.getServletContext().getResourceAsStream(path);
					if (null != is) {
						try {
							graFileSizeList.add(baseService.accquireFileSize(is.available()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} 
				}
			}
			jsonMap.put("graFileSizeList", graFileSizeList);
			//当前项目立项计划书申请是否通过
			int graPassAlready = dao.query("from ProjectGranted pg where pg.applicantSubmitStatus = 3 and pg.finalAuditStatus = 3 and pg.finalAuditResult = 2 and pg.id = ?",projectid).size();
			//当前项目是否有待审立项计划书
			int graPending = dao.query("from ProjectGranted pg where (pg.applicantSubmitStatus = 3 or pg.auditType =1) and pg.finalAuditStatus != 3 and pg.id = ?", projectid).size();
			jsonMap.put("graPassAlready", graPassAlready);
			jsonMap.put("graPending", graPending);
			
			if(application == null){
				return INPUT;
			}
			List<String> dirPersonIds;//负责人id的list
			if(application.getFinalAuditStatus() == 3 && application.getFinalAuditResult() == 2){//已立项
				if(granted == null){
					return INPUT;
				}
				if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkGrantedFlag(), true)){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
					return ERROR;
				}
				dirPersonIds = this.projectService.getDireIdByAppId(entityId, granted.getMemberGroupNumber());
			}else{//未立项
				if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
					this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
					return ERROR;
				}
				dirPersonIds = this.projectService.getDireIdByAppId(entityId, 1);
			}
			//申报各级别截止时间业务信息
			deadline = this.projectService.checkIfTimeValidate(accountType, businessType());//用于申报
			List appInfo = new ArrayList();
			appInfo.add(0, businessType());
			appInfo.add(1,deadline);
			//申报是否通过
			int appPassAlready = this.projectService.getPassApplicationByAppId(entityId).size() > 0 ? 1 : 0;//1：通过；0：未通过
			//判断项目申报是否有评审记录-1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
			int reviewflagFromApp = this.projectService.checkReviewFromAppReview(entityId);
			StringBuffer hql = new StringBuffer();
			hql.append("select count(appRev.id) from ").append(application.getApplicationReviewClassName())
			.append(" appRev left outer join appRev.application app where app.id=? ");
			List numList = dao.query(hql.toString(), entityId);
			int reviewNum = 0;
			if (numList.size() != 0) {
				Long Num = (Long) numList.get(0);
				int numReview = Integer.valueOf(ApplicationContainer.sc.getAttribute("numReview").toString());
				if (Num >= numReview) {
					reviewNum = 1;
				}
			}
			//申报评审信息
			int applicationReviewStatus = 0;//个人评审信息提交状态
			String applicationPersonalGrade = "";//个人评审等级信息
			List applicationReviews = new ArrayList();//所有人评审信息
			String applicationReviewGrade = "";//评审等级信息
			int allAppReviewSubmit = 0;//对于当前申报，是否所有评审专家都提交了个人评审
			List applicationReviewExpert = new ArrayList();//所有人评审信息
			if (accountType.equals(AccountType.MINISTRY)) {
				//评审专家信息
				StringBuffer hql1 = new StringBuffer();
				hql1.append("select appRev.reviewerName, appRev.submitStatus, appRev.reviewer.id from ").append(application.getApplicationReviewClassName())
				.append(" appRev left outer join appRev.application app where app.id=? ");
				applicationReviewExpert = dao.query(hql1.toString(), entityId);
				
			}
			//个人评审信息
			ProjectApplicationReview applicationReview = (ProjectApplicationReview)this.projectService.getPersonalAppReview(entityId,projectService.getBelongIdByLoginer(loginer));
			//当前是否是项目评审人 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
			isAppReviewer = (accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)) ? this.projectService.isAppReviewer(entityId, projectService.getBelongIdByLoginer(loginer)) : 0;
			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){
				if(applicationReview!=null){
					applicationReviewStatus = applicationReview.getSubmitStatus();
					//个人评审等级
					if((listType == 10 && isAppReviewer != 0) || accountType.compareTo(AccountType.DEPARTMENT) < 0){//评审人、管理人员才获取评审等级
						if(applicationReview.getGrade() != null && applicationReview.getGrade().getId() != null){
							applicationPersonalGrade = ((SystemOption)this.dao.query(SystemOption.class, applicationReview.getGrade().getId())).getName();
						}
					}
				}
			}
			//申报评审各级别截止时间业务信息
			deadline = this.projectService.checkIfTimeValidate(businessType());//用于专家评审
			List appRevInfo = new ArrayList();
			appRevInfo.add(0, businessType());
			appRevInfo.add(1,deadline);
			//所有人评审信息
			if(((null != listType && listType == 10 && isAppReviewer == 2) || accountType.compareTo(AccountType.DEPARTMENT) < 0)){//评审组长、管理人员才获取所有人评审信息
				applicationReviews = this.projectService.getAllAppReviewList(entityId, accountType);
			}else{
				applicationReviews = null;
			}
			//判断当前申报是否所有评审专家都提交了个人评审 0所有专家已提交；-1还有专家未提交
			allAppReviewSubmit = this.projectService.isAllAppReviewSubmit(entityId);
			//评审等级
			if((null != listType && listType == 10 && isAppReviewer == 2) || accountType.compareTo(AccountType.DEPARTMENT) < 0){//评审组长、管理人员才获取评审等级
				if(null != application.getReviewGrade()){
					SystemOption grad= (SystemOption) this.dao.query(SystemOption.class, application.getReviewGrade().getId());
					applicationReviewGrade = grad.getName();
				}
			}else{
				applicationReviewGrade = "";
			}
			application = this.projectService.hideAppInfo(application, accountType, isAppReviewer);
			//成员信息(默认申请时的成员)
			List memberList = this.projectService.getMemberListByAppId(entityId, 1);
			//负责人信息
			List<Person> dirPersons = new ArrayList();// 项目负责人人员信息
			List<Academic> dirAcademics = new ArrayList();// 项目负责人学术信息
			if(dirPersonIds.size() > 0){
				for(int i = 0; i < dirPersonIds.size(); i++){
					if(dirPersonIds.get(i) != null && dirPersonIds.get(i) != ""){
						Person dirPerson = (Person)this.dao.query(Person.class, dirPersonIds.get(i));
						Academic dirAcademic = this.projectService.getAcademicByPersonId(dirPersonIds.get(i));
						if(dirPerson != null && dirAcademic != null){
							dirPersons.add(dirPerson);
							dirAcademics.add(dirAcademic);
						}
					}
				}
			}
			//当前是否是项目负责人
			isDirector = (dirPersonIds.contains(projectService.getBelongIdByLoginer(loginer)));
			//原始申报信息
			String researchTypeName = (application.getResearchType() != null) ? application.getResearchType().getName() : "";//研究类别名称
			String subTypeNameOld = (application.getSubtype() != null) ? application.getSubtype().getName() : "";//原始项目子类名称
			String researchTypeNameOld = (application.getResearchType() != null) ? application.getResearchType().getName() : "";//原始研究类型名称
			String universityIdOld = (application.getUniversity() != null) ? application.getUniversity().getId() : "";//原始项目所属高校id
			String departmentIdOld = (application.getDepartment()!= null) ? application.getDepartment().getId() : "";//原始项目所属院系id
			String instituteIdOld = (application.getInstitute()!= null) ? application.getInstitute().getId() : "";//原始项目所属研究基地id
			int utypeOld = this.projectService.isSubordinateUniversityApplication(entityId);//原始高校是否部属高校
			if(application.getFinalAuditStatus() == 3 && application.getFinalAuditResult() == 2){//已立项
				
				//成员信息(最新的)
				memberList = this.projectService.getMemberListByAppId(entityId, granted.getMemberGroupNumber());
				//最新信息
				String subTypeNameNew = (granted.getSubtype() != null) ? granted.getSubtype().getName() : "";//最新项目子类名称
				String researchTypeNameNew = (application.getResearchType() != null) ? application.getResearchType().getName() : "";//最新研究类型名称
				String universityNameNew = (granted.getUniversity() != null) ? granted.getUniversity().getName() : "";//最新项目子类名称
				String universityIdNew = (granted.getUniversity() != null) ? granted.getUniversity().getId() : "";//最新项目所属高校id
				String departmentIdNew = (granted.getDepartment()!= null) ? granted.getDepartment().getId() : "";//最新项目所属院系id
				String instituteIdNew = (granted.getInstitute()!= null) ? granted.getInstitute().getId() : "";//最新项目所属研究基地id
				int utypeNew = this.projectService.isSubordinateUniversityGranted(projectid);;//最新高校是否部属高校1：部属高校；0：地方高校
				//拨款信息
				List fundList= this.projectService.getFundListByGrantedId(projectid);
//				projectFunding = this.projectService.getFundListByGrantedId(projectid);
				
				//结项是否通过
				int endPassAlready = this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;  //1：通过；0：未通过
				//是否有待审结项
				int endPending = this.projectService.getPendingEndinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
				//是否有待审变更
				int varPending = this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0 ? 1 : 0;
				//结项信息
				List<ProjectEndinspection> endList= (listType == 8) ? this.projectService.getAllEndinspectionByGrantedIdAndReviewerId(projectid, projectService.getBelongIdByLoginer(loginer)) : this.projectService.getAllEndinspectionByGrantedIdInScope(projectid, accountType);
				ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
				//显示结项申请书大小
				for (ProjectEndinspection pendi : endList) {
					if (pendi.getFile() != null) {
						String[] attachPath = pendi.getFile().split("; ");
						InputStream is = null;
						for (String path : attachPath) {
							is = ServletActionContext.getServletContext().getResourceAsStream(path);
							if (null != is) {
								try {
									endiFileSizeList.add(baseService.accquireFileSize(is.available()));
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {// 附件不存在
								endiFileSizeList.add(null);
							}
						}
					}
				}
				jsonMap.put("endiFileSizeList", endiFileSizeList);
				//结项各级审核人和审核人所在机构信息
				List<String> endAuditorInfo = new ArrayList<String>();
				endAuditorInfo= this.projectService.getEndAuditorInfo(endList.isEmpty() ? null : endList.get(0),loginer);
				//研究数据信息
				List<ProjectData> projectDatas = new ArrayList();
				//判断项目结项是否有评审记录-1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
				int reviewflag = this.projectService.checkReview((endList != null && endList.size() > 0) ? endList.get(0).getId() : null);
				//当前是否是项目评审人 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
				isEndReviewer = new ArrayList();
				//结项评审信息
				List<ProjectEndinspectionReview> endinspectionReview = new ArrayList();//个人评审信息
				List<Integer> endinspectionReviewStatus = new ArrayList();//个人评审信息提交状态
				List endinspectionReviews = new ArrayList();//所有人评审信息
				List<String> endinspectionReviewGrade = new ArrayList();//评审等级信息
				List<String> endinspectionPersonalGrade = new ArrayList();//个人评审等级信息
				int allReviewSubmit = 0;//对于当前结项，是否所有评审专家都提交了个人评审
				for(int i=0; i<endList.size(); i++){//循环查询每次结项的评审信息
					ProjectData resData = (ProjectData)this.projectService.getProjectDataByEndId(endList.get(i).getId());//研究数据信息
					projectDatas.add(i, resData);
					if (null != resData) {
						if (null != resData.getFile()) {
							String[] attachPath = resData.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										resFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									resFileSizeList.add(null);
								}
							}
						}
					}
					jsonMap.put("resFileSizeList", resFileSizeList);
					//当前是否是项目评审人 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
					int isReviewertemp = (accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)) ? this.projectService.isEndReviewer(endList.get(i).getId(), projectService.getBelongIdByLoginer(loginer)) : 0;
					isEndReviewer.add(i, isReviewertemp);
					if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){
						//个人评审信息
						ProjectEndinspectionReview endinspectionReviewDetail = (ProjectEndinspectionReview)this.projectService.getPersonalEndReview(((ProjectEndinspection)endList.get(i)).getId(), projectService.getBelongIdByLoginer(loginer));
						if(endinspectionReviewDetail!=null){
							endinspectionReview.add(i, endinspectionReviewDetail);
							endinspectionReviewStatus.add(i, endinspectionReviewDetail.getSubmitStatus());
							//个人评审等级
							if((listType == 8 && isReviewertemp != 0) || accountType.compareTo(AccountType.DEPARTMENT) < 0){//评审人、管理人员才获取评审等级
								String personalGradeDetail = "";
								if(endinspectionReviewDetail.getGrade() != null && endinspectionReviewDetail.getGrade().getId() != null){
									personalGradeDetail = ((SystemOption)this.dao.query(SystemOption.class, endinspectionReviewDetail.getGrade().getId())).getName();
								}
								endinspectionPersonalGrade.add(i, personalGradeDetail);
							}else{
								endinspectionPersonalGrade.add(i, "");
							}
						}else{
							endinspectionReview.add(i, null);
							endinspectionReviewStatus.add(i, 0);
							endinspectionPersonalGrade.add(i, "");
						}
					}
					//所有人评审信息
					List endinspectionReviewsDetail = this.projectService.getAllEndReviewList(endList.get(i).getId(), accountType);
					if(endinspectionReviewsDetail.size() > 0 && ((listType == 8 && isReviewertemp == 2) || accountType.compareTo(AccountType.DEPARTMENT) < 0)){//评审组长、管理人员才获取所有人评审信息
						endinspectionReviews.add(i, endinspectionReviewsDetail);
					}else{
						endinspectionReviews.add(i, null);
					}
					if(i == 0){//判断当前结项是否所有评审砖家都提交了个人评审 0所有专家已提交；-1还有专家未提交
						allReviewSubmit = this.projectService.isAllEndReviewSubmit(endList.get(0).getId());
					}
					//评审等级
					if((listType == 8 && isReviewertemp == 2) || accountType.compareTo(AccountType.DEPARTMENT) < 0){//评审组长、管理人员才获取评审等级
						SystemOption gra = (endList.get(i)).getReviewGrade();
						String reviewGradeDetail = "";
						if(gra != null){
							reviewGradeDetail = endList.get(i).getReviewGrade().getName();
						}
						endinspectionReviewGrade.add(i, reviewGradeDetail);
					}else{
						endinspectionReviewGrade.add(i, "");
					}
				}
				int isCurrentReviewer = (!isEndReviewer.isEmpty()) ? isEndReviewer.get(0) : 0;//是否当前结项评审人
				endList = this.projectService.hideEndInfo(endList, accountType, isCurrentReviewer);
				//变更信息
				List<ProjectVariation> varList = (listType == 8) ? new ArrayList() : this.projectService.getAllVariationByGrantedIdInScope(projectid, accountType);
				ProjectVariation variation = this.projectService.getCurrentVariationByGrantedId(projectid);
				//显示变更申请书的大小
				for (ProjectVariation var : varList) {
					if (var.getFile() != null) {
						String[] attachPath = var.getFile().split("; ");
						InputStream is = null;
						for (String path : attachPath) {
							is = ServletActionContext.getServletContext().getResourceAsStream(path);
							if (null != is) {
								try {
									varFileSizeList.add(baseService.accquireFileSize(is.available()));
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {// 附件不存在
								varFileSizeList.add(null);
							}
						}
					}
					if (var.getPostponementPlanFile() != null) {
						String[] attachPath = var.getPostponementPlanFile().split("; ");
						InputStream is = null;
						for (String path : attachPath) {
							is = ServletActionContext.getServletContext().getResourceAsStream(path);
							if (null != is) {
								try {
									postponementPlanFileSizeList.add(baseService.accquireFileSize(is.available()));
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {// 附件不存在
								postponementPlanFileSizeList.add(null);
							}
						}
					}
					
				}
				jsonMap.put("postponementPlanFileSizeList", postponementPlanFileSizeList);
				jsonMap.put("varFileSizeList", varFileSizeList);
				//变更各级审核人和审核人所在机构信息
				List<String> varAuditorInfo = new ArrayList<String>();
				varAuditorInfo= this.projectService.getVarAuditorInfo(varList.isEmpty() ? null : varList.get(0),loginer);
				//查看变更详情所需要的id的List，每条记录为Object[8] //0、1:变更前、后责任人id 	2、3、4、5、6、7:变更前、后学校、院系、基地id
				List varRelIdNames = this.projectService.getAllVariationRelIdNames(varList);
				varList = this.projectService.hideVarInfo(varList, accountType);
				//项目成果
				List<String> endIds = new ArrayList<String>();
				for(ProjectEndinspection ge : endList) {
					endIds.add(ge.getId());//获取当前所有结项ids
				}
				//结项成果
				List<Map> endProd = productService.getEndProducts(projectid, endIds);
				//相关成果
				List<Map> relProd = productService.getRelProducts(projectid);
				
				List<Map> projectEndFees = projectService.getProjectFeeEndByEndId(projectid, endIds);//结项经费
				jsonMap.put("projectEndFees", projectEndFees);//结项经费
				
				/* 以下代码为各类业务截止时间信息*/
				//结项各级别截止时间业务信息
				deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), entityId);
				List endInfo = new ArrayList();
				endInfo.add(0, businessType());
				endInfo.add(1,deadline);
				//结项评审截止时间业务信息
				deadline = this.projectService.checkIfTimeValidate(businessType());
				List endRevInfo = new ArrayList();
				endRevInfo.add(0, businessType());
				endRevInfo.add(1,deadline);
				//变更各级别截止时间业务信息
				deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), entityId);
				List varInfo = new ArrayList();
				varInfo.add(0, businessType());
				varInfo.add(1,deadline);
				/* 结束 */
				
				Map<String, Object> sc = ActionContext.getContext().getApplication();
				//能否审核结项成果
				jsonMap.put("canAuditEndProduct", productService.canAuditEndInspectionProduct(endList, loginer.getAccount(), 
						Integer.parseInt((String)sc.get("productFirstAuditLevel")), Integer.parseInt((String)sc.get("productFinalAuditLevel"))));
				jsonMap.put("fundList", fundList);//拨款信息
//				jsonMap.put("projectFunding", projectFunding);//拨款信息
				jsonMap.put("endList", endList);//结项信息
				jsonMap.put("projectDatas", projectDatas);//结项研究数据信息
				jsonMap.put("endAuditorInfo", endAuditorInfo);//结项各级审核人和审核人所在机构信息
				jsonMap.put("endinspectionReview", endinspectionReview);//个人评审信息
				jsonMap.put("endinspectionReviewStatus", endinspectionReviewStatus);//个人评审信息提交状态
				jsonMap.put("endinspectionReviews", endinspectionReviews);//所有人评审信息
				jsonMap.put("endinspectionReviewGrade", endinspectionReviewGrade);//评审等级信息
				jsonMap.put("endinspectionPersonalGrade", endinspectionPersonalGrade);//个人评审等级信息
				if(((accountType.equals(AccountType.EXPERT)||accountType.equals(AccountType.TEACHER))&&isCurrentReviewer==2) || accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//评审组长、系统管理员、部级管理人员才获取评审信息
					jsonMap.put("allReviewSubmit", allReviewSubmit);//是否所有评审专家都提交了个人评审
				}
				jsonMap.put("endPassAlready",endPassAlready);//结项是否通过
				jsonMap.put("endPending",endPending);//是否有待审结项
				jsonMap.put("varPending",varPending);//是否有待审变更
				jsonMap.put("varList", varList);//变更信息
				jsonMap.put("varAuditorInfo", varAuditorInfo);//变更各级审核人和审核人所在机构信息
				jsonMap.put("varRelIdNames", varRelIdNames);//变更相关id,name信息
				jsonMap.put("reviewflag", reviewflag);//判断项目结项是否有评审记录 -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
				jsonMap.put("isEndReviewer", isEndReviewer);//是否结项评审人
				jsonMap.put("subTypeNameNew", subTypeNameNew);//最新项目子类名称
				jsonMap.put("researchTypeNameNew", researchTypeNameNew);//最新研究类型名称
				jsonMap.put("universityNameNew", universityNameNew);//最新所属高校id
				jsonMap.put("universityIdNew", universityIdNew);//最新所属高校id
				jsonMap.put("departmentIdNew", departmentIdNew);//最新所属院系id
				jsonMap.put("instituteIdNew", instituteIdNew);//最新所属研究基地id
				jsonMap.put("utypeNew", utypeNew);//原始高校类型
				jsonMap.put("endProdInfo", endProd);//结项成果
				jsonMap.put("relProdInfo", relProd);//相关成果
				jsonMap.put("endInfo", endInfo);//结项各级别截止时间业务信息
				jsonMap.put("endRevInfo", endRevInfo);//结项评审截止时间业务信息
				jsonMap.put("varInfo", varInfo);//变更各级别截止时间业务信息
				
				//立项项目差异性部分
				if(!"post".equals(application.getType()) && !"entrust".equals(application.getType())){//一般项目或基地项目或重大攻关项目中检相关信息
					int midPassAlready = this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;//中检是否通过 1：通过；0：未通过
					int midPending = this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;////是否有待审中检 1：有；0：没有
					int grantedYear = (granted.getApproveDate() != null) ? (granted.getApproveDate().getYear() + 1900) : application.getYear();
					int midForbid = this.projectService.getMidForbidByGrantedDate(grantedYear);//是否可以中检，1：不可以 0：可以
					int endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);//是否可以结项，1：可以，0：不可以
					//中检信息
					List<ProjectMidinspection> midList = (listType == 8) ? new ArrayList() : this.projectService.getAllMidinspectionByGrantedIdInScope(projectid, accountType);
					midList = this.projectService.hideMidInfo(midList, accountType);
					ProjectMidinspection midinspection = this.projectService.getCurrentMidinspectionByGrantedId(projectid);
					//中检各级审核人和审核人所在机构信息
					List<String> midAuditorInfo = new ArrayList<String>();
					midAuditorInfo= this.projectService.getMidAuditorInfo(midList.isEmpty() ? null : midList.get(0),loginer);
					
					//立项计划各级审核人和审核人所在机构信息
					List<String> graAuditorInfo = new ArrayList<String>();
					graAuditorInfo= this.projectService.getGraAuditorInfo(granted,loginer);
					//中检业务各级别截止时间信息
					deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), entityId);
					List midInfo = new ArrayList();
					midInfo.add(0, businessType());
					midInfo.add(1,deadline);
					
					List<String> midIds = new ArrayList<String>();
					for(ProjectMidinspection gm : midList) {
						midIds.add(gm.getId());//获取当前所有中检ids
						if (gm.getFile() != null) {
							String[] attachPath = gm.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										midFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									midFileSizeList.add(null);
								}
							}
						}
					}
					jsonMap.put("midFileSizeList", midFileSizeList);
					//能否审核中检成果
					jsonMap.put("canAuditMidProduct", productService.canAuditMidInspectionProduct(midList, loginer.getAccount(), 
							Integer.parseInt((String)sc.get("productFirstAuditLevel")), Integer.parseInt((String)sc.get("productFinalAuditLevel"))));
					//中检成果
					List<Map> midProd = productService.getMidProducts(projectid, midIds);
					jsonMap.put("midProdInfo", midProd);	//中检成果
					List<Map> projectMidFees = projectService.getProjectFeeMidByMidId(projectid, midIds);//中检经费
					jsonMap.put("projectMidFees", projectMidFees);//中检经费
					jsonMap.put("midList", midList);//中检信息
					jsonMap.put("midAuditorInfo", midAuditorInfo);//中检各级审核人和审核人所在机构信息
					jsonMap.put("graAuditorInfo", graAuditorInfo);//立项计划各级审核人和审核人所在机构信息
					jsonMap.put("midPassAlready",midPassAlready);//中检是否通过
					jsonMap.put("midPending",midPending);//是否有待审中检
					jsonMap.put("midForbid",midForbid);//是否可以中检，1：不可以 0：可以
					jsonMap.put("endAllow",endAllow);//是否可以结项，1：可以，0：不可以
					jsonMap.put("midInfo", midInfo);//中检各级别截止时间业务信息
				}
				if(!"entrust".equals(application.getType())){//年检相关信息（委托应急无年检）
					//是否有待审年检
					int annPending = this.projectService.getPendingAnninspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
					//年检信息
					List<ProjectAnninspection> annList = (listType == 8) ? new ArrayList() : this.projectService.getAllAnninspectionByGrantedIdInScope(projectid, accountType);
					annList = this.projectService.hideAnnInfo(annList, accountType);
					ProjectAnninspection anninspection = (ProjectAnninspection)this.projectService.getCurrentAnninspectionByGrantedId(projectid);
					int isAnnPending =0;
					if (null != anninspection) {
					}
					//年检各级审核人和审核人所在机构信息
					List<String> annAuditorInfo = new ArrayList<String>();
					annAuditorInfo= this.projectService.getAnnAuditorInfo(annList.isEmpty() ? null : annList.get(0),loginer);
					//年检各级别截止时间业务信息
					deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), entityId);
					List annInfo = new ArrayList();
					annInfo.add(0, businessType());
					annInfo.add(1,deadline);
					
					List<String> annIds = new ArrayList<String>();
					for(ProjectAnninspection gm : annList) {
						if (gm.getFile() != null) {
							String[] attachPath = gm.getFile().split("; ");
							InputStream is = null;
							for (String path : attachPath) {
								is = ServletActionContext.getServletContext().getResourceAsStream(path);
								if (null != is) {
									try {
										annFileSizeList.add(baseService.accquireFileSize(is.available()));
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {// 附件不存在
									annFileSizeList.add(null);
								}
							}
						}
						annIds.add(gm.getId());//获取当前所有年检ids
					}
					jsonMap.put("annFileSizeList", annFileSizeList);
					//年检成果
					List<Map> annProd = productService.getAnnProducts(projectid, annIds);
					jsonMap.put("annProdInfo", annProd);//年检成果
					List<Map> projectAnnFees = projectService.getProjectFeeAnnByAnnId(projectid, annIds);//年检经费
					jsonMap.put("projectAnnFees", projectAnnFees);//年检经费
					jsonMap.put("annList", annList);//年检信息
					jsonMap.put("annAuditorInfo", annAuditorInfo);//年检各级审核人和审核人所在机构信息
					jsonMap.put("annPending",annPending);//是否有待审年检
					jsonMap.put("annInfo", annInfo);//年检各级别截止时间业务信息
					jsonMap.put("isAnnPending", isAnnPending);//中检是否搁置
					//能否审核年检成果
					jsonMap.put("canAuditAnnProduct", productService.canAuditAnninspectionProduct(annList, loginer.getAccount(), 
							Integer.parseInt((String)sc.get("productFirstAuditLevel")), Integer.parseInt((String)sc.get("productFinalAuditLevel"))));
				}
				if("post".equals(application.getType())){//后期资助项目鉴定相关信息
					//鉴定是否通过
					int reviewPassAlready = this.projectService.getPassReviewByGrantedId(this.projectid).size() > 0 ? 1 : 0;
					//是否有待审鉴定
					int reviewPending = this.projectService.getPendingReviewByGrantedId(this.projectid).size()>0 ? 1 : 0;
					//是否是导入数据 1: 走流程  2：导入
					int reviewFlag = this.projectService.getCurrentEndinspectionByGrantedId(this.projectid) == null ? 1 : 2;
					jsonMap.put("reviewPassAlready", reviewPassAlready);//是否已鉴定
					jsonMap.put("reviewPending", reviewPending);//是否有待审鉴定
					jsonMap.put("reviewFlag", reviewFlag);//是否是导入数据 1: 走流程  2：导入
				}
			}
			//存放json部分代码
			jsonMap.put("application", application);//项目申报
			jsonMap.put("projectFeeApply", projectFeeApply);	//经费概算
			jsonMap.put("projectFeeGranted", projectFeeGranted);	//经费预算
			jsonMap.put("appAuditorInfo", appAuditorInfo);//申报各级审核人和审核人所在机构信息
			jsonMap.put("appInfo", appInfo);//申报截止时间业务信息
			jsonMap.put("appPassAlready", appPassAlready);//申报是否通过
			jsonMap.put("reviewflagFromApp", reviewflagFromApp);//判断项目申报是否有评审记录 -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
			jsonMap.put("isAppReviewer", isAppReviewer);//是否结项评审人
			jsonMap.put("reviewNum", reviewNum);
			
			jsonMap.put("appRevInfo", appRevInfo);//申报评审截止时间业务信息
			jsonMap.put("applicationReview", applicationReview);//个人评审信息
			jsonMap.put("applicationReviewStatus", applicationReviewStatus);//个人评审信息提交状态
			jsonMap.put("applicationReviewExpert", applicationReviewExpert);//评审专家
			if (null !=applicationReviews && applicationReviews.size() != 0) {
				jsonMap.put("applicationReviews", applicationReviews);//所有人评审信息
			} else {
				jsonMap.put("applicationReviews", null);//所有人评审信息
			}
			jsonMap.put("applicationReviewGrade", applicationReviewGrade);//评审等级信息
			jsonMap.put("applicationPersonalGrade", applicationPersonalGrade);//个人评审等级信息
			if(((accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)) && isAppReviewer==2) || accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//评审组长、系统管理员、部级管理人员才获取评审信息
				jsonMap.put("allAppReviewSubmit", allAppReviewSubmit);//是否所有评审专家都提交了个人评审
			}
			jsonMap.put("granted", granted);//项目立项
			jsonMap.put("researchTypeName", researchTypeName);//研究类型
			jsonMap.put("memberList", memberList);//项目成员
			jsonMap.put("dirPersons", dirPersons); // 项目负责人一般信息
			jsonMap.put("dirAcademics", dirAcademics);// 项目负责人学术信息
			jsonMap.put("memberFlag", (memberList.size() > 0) ? 1: 0);//是否有成员 1：有 ；0：没有
			jsonMap.put("subTypeNameOld", subTypeNameOld);//原始项目子类名称
			jsonMap.put("researchTypeNameOld", researchTypeNameOld);//原始项目子类名称
			jsonMap.put("universityIdOld", universityIdOld);//原始所属高校id
			jsonMap.put("departmentIdOld", departmentIdOld);//原始所属院系id
			jsonMap.put("instituteIdOld", instituteIdOld);//原始所属研究基地id
			jsonMap.put("utypeOld", utypeOld);//原始高校类型
			jsonMap.put("accountType", accountType);//账号类型
			jsonMap.put("belongId", belongId);//账号所属id
			jsonMap.put("isPrincipal", isPrincipal);//是否主账号
			jsonMap.put("selectedTab", selectedTab);//查看标签
			jsonMap.put("isDirector", isDirector? 1 : 0);//是否负责人
			
			//个别字段差异性部分
			if("general".equals(application.getType()) || "post".equals(application.getType()) || "entrust".equals(application.getType())){//一般项目或后期资助项目
				String topicName = this.projectService.getProjectTopicNameByAppId(entityId);//项目主题名称
				jsonMap.put("topicName", topicName);//项目主题
			}
			
		}
		//选题信息
		topsId = this.keyService.getCurrentTopicSelectionByAppId(entityId);
		topicSelection = (KeyTopicSelection)dao.query(KeyTopicSelection.class, topsId);
		if(null != topicSelection){//重大攻关项目选题
			int appFlag = 0;
			String universityId = (topicSelection.getUniversity() != null) ? topicSelection.getUniversity().getId() : "";//选题所属高校id
			int utype = this.keyService.isSubordinateUniversityTopicSelection(topsId);;//选题所属高校是否部属高校
			String projectId = (topicSelection.getProject() != null) ? topicSelection.getProject().getId() : "";//选题所属项目id
			String universityName = "";
			String projectName = "";
			String projectTypeId = "";
			if(!universityId.isEmpty() && !projectId.isEmpty()){
				Agency university = (Agency) this.dao.query(Agency.class, universityId);
				universityName = (university != null) ? university.getName() : "";//选题所属高校id
				ProjectGranted project= (ProjectGranted) this.dao.query(ProjectGranted.class, projectId);
				projectName = project.getName();
				projectTypeId = project.getProjectType();
				appFlag = 2;
			}else{
				appFlag = 1;
			}
			//选题各级审核人和审核人所在机构信息
			List<String> topsAuditorInfo = new ArrayList<String>();
			topsAuditorInfo= this.keyService.getTopsAuditorInfo(topicSelection);
			jsonMap.put("accountType", accountType);//账号类型
			jsonMap.put("belongId", belongId);//账号id
			jsonMap.put("isPrincipal", isPrincipal);//是否主账号
			jsonMap.put("topsAuditorInfo", topsAuditorInfo);//选题各级审核人和审核人所在机构信息
			jsonMap.put("appFlag", appFlag);//走流程类型 1：专家推荐；2：高校推荐
			jsonMap.put("universityName", universityName);//选题所属高校名称
			jsonMap.put("universityId", universityId);//选题所属高校id
			jsonMap.put("utype", utype);//选题所属高校是否是部属高校
			jsonMap.put("projectName", projectName);//选题所属项目名称
			jsonMap.put("projectId", projectId);//选题所属项目id
			jsonMap.put("projectTypeId", projectTypeId);//选题所属项目类型
			//选题各级别截止时间业务信息
			deadline = this.projectService.checkIfTimeValidate(accountType, businessType());
			List topsInfo = new ArrayList();
			topsInfo.add(0, businessType());
			topsInfo.add(1,deadline);
			jsonMap.put("topicSelection", topicSelection);//选题申报
			jsonMap.put("topsInfo", topsInfo);//选题申报各级别截止时间业务信息
		}
		session.put("projectViewJson", this.jsonMap);
		return SUCCESS;
	}
	
	public String toAddApplyFee() throws UnsupportedEncodingException {
		if (!request.getParameter("applyFee").isEmpty() && !request.getParameter("applyFee").equals(null)) {
			applyFee = Double.parseDouble(request.getParameter("applyFee").trim());
		}
		if (!request.getParameter("otherFee").isEmpty() && !request.getParameter("otherFee").equals(null)) {
			otherFee = Double.parseDouble(request.getParameter("otherFee").trim());
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		return SUCCESS;
	}
	
	public String toAddGrantedFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			projectGranted = dao.query(ProjectGranted.class, projectid);
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		return SUCCESS;
	}
	
	public String toAddAnnFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			projectGranted = dao.query(ProjectGranted.class, projectid);
			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
			toFundFee = DoubleTool.sub(projectGranted.getApproveFee(),fundedFee);
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("surplusFee").isEmpty() && !request.getParameter("surplusFee").equals(null)) {
			surplusFee = (Double.parseDouble(request.getParameter("surplusFee").trim()));
		}
		return SUCCESS;
	}
	
	
	public String toAddMidFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			projectGranted = dao.query(ProjectGranted.class, projectid);
			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
			toFundFee = DoubleTool.sub(projectGranted.getApproveFee(),fundedFee);
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("surplusFee").isEmpty() && !request.getParameter("surplusFee").equals(null)) {
			surplusFee = (Double.parseDouble(request.getParameter("surplusFee").trim()));
		}
		return SUCCESS;
	}
	
	
	public String toAddEndFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			projectGranted = dao.query(ProjectGranted.class, projectid);
			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
			toFundFee = DoubleTool.sub(projectGranted.getApproveFee(),fundedFee);
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		if (!request.getParameter("surplusFee").isEmpty() && !request.getParameter("surplusFee").equals(null)) {
			surplusFee = (Double.parseDouble(request.getParameter("surplusFee").trim()));
		}
		return SUCCESS;
	}
	
	public String toAddVarFee() throws UnsupportedEncodingException {
		if (!request.getParameter("projectid").isEmpty() && !request.getParameter("projectid").equals(null)) {
			projectid = request.getParameter("projectid").trim();
			projectGranted = dao.query(ProjectGranted.class, projectid);
//			fundedFee = this.projectService.getFundedFeeByGrantedId(projectid);
//			toFundFee = projectGranted.getApproveFee()-fundedFee;
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class , projectGranted.getProjectFee().getId());
			}
		}
		if (!request.getParameter("feeNote").isEmpty() && !request.getParameter("feeNote").equals(null)) {
			feeNote = new String(request.getParameter("feeNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("bookFee").isEmpty() && !request.getParameter("bookFee").equals(null)) {
			bookFee = Double.parseDouble(request.getParameter("bookFee").trim());
		}
		if (!request.getParameter("bookNote").isEmpty() && !request.getParameter("bookNote").equals(null)) {
			bookNote = new String(request.getParameter("bookNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("dataFee").isEmpty() && !request.getParameter("dataFee").equals(null)) {
			dataFee = Double.parseDouble(request.getParameter("dataFee").trim());
		}
		if (!request.getParameter("dataNote").isEmpty() && !request.getParameter("dataNote").equals(null)) {
			dataNote = new String(request.getParameter("dataNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("travelFee").isEmpty() && !request.getParameter("travelFee").equals(null)) {
			travelFee = Double.parseDouble(request.getParameter("travelFee").trim());
		}
		if (!request.getParameter("travelNote").isEmpty() && !request.getParameter("travelNote").equals(null)) {
			travelNote = new String(request.getParameter("travelNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("conferenceFee").isEmpty() && !request.getParameter("conferenceFee").equals(null)) {
			conferenceFee = Double.parseDouble(request.getParameter("conferenceFee").trim());
		}
		if (!request.getParameter("conferenceNote").isEmpty() && !request.getParameter("conferenceNote").equals(null)) {
			conferenceNote = new String(request.getParameter("conferenceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("internationalFee").isEmpty() && !request.getParameter("internationalFee").equals(null)) {
			internationalFee = Double.parseDouble(request.getParameter("internationalFee").trim());
		}
		if (!request.getParameter("internationalNote").isEmpty() && !request.getParameter("internationalNote").equals(null)) {
			internationalNote = new String(request.getParameter("internationalNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("deviceFee").isEmpty() && !request.getParameter("deviceFee").equals(null)) {
			deviceFee = Double.parseDouble(request.getParameter("deviceFee").trim());
		}
		if (!request.getParameter("deviceNote").isEmpty() && !request.getParameter("deviceNote").equals(null)) {
			deviceNote = new String(request.getParameter("deviceNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("consultationFee").isEmpty() && !request.getParameter("consultationFee").equals(null)) {
			consultationFee = Double.parseDouble(request.getParameter("consultationFee").trim());
		}
		if (!request.getParameter("consultationNote").isEmpty() && !request.getParameter("consultationNote").equals(null)) {
			consultationNote = new String(request.getParameter("consultationNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("laborFee").isEmpty() && !request.getParameter("laborFee").equals(null)) {
			laborFee = Double.parseDouble(request.getParameter("laborFee").trim());
		}
		if (!request.getParameter("laborNote").isEmpty() && !request.getParameter("laborNote").equals(null)) {
			laborNote = new String(request.getParameter("laborNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("printFee").isEmpty() && !request.getParameter("printFee").equals(null)) {
			printFee = Double.parseDouble(request.getParameter("printFee").trim());
		}
		if (!request.getParameter("printNote").isEmpty() && !request.getParameter("printNote").equals(null)) {
			printNote = new String(request.getParameter("printNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("indirectFee").isEmpty() && !request.getParameter("indirectFee").equals(null)) {
			indirectFee = Double.parseDouble(request.getParameter("indirectFee").trim());
		}
		if (!request.getParameter("indirectNote").isEmpty() && !request.getParameter("indirectNote").equals(null)) {
			indirectNote = new String(request.getParameter("indirectNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("otherFeeD").isEmpty() && !request.getParameter("otherFeeD").equals(null)) {
			otherFeeD = Double.parseDouble(request.getParameter("otherFeeD").trim());
		}
		if (!request.getParameter("otherNote").isEmpty() && !request.getParameter("otherNote").equals(null)) {
			otherNote = new String(request.getParameter("otherNote").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("totalFee").isEmpty() && !request.getParameter("totalFee").equals(null)) {
			totalFee = (Double.parseDouble(request.getParameter("totalFee").trim()));
		}
		return SUCCESS;
	}
	
	
	public ProjectFee doWithAddResultFee(ProjectFee projectFeeAnn){
		if ((totalFee != null) && (fundedFee != null)) {
			projectFeeAnn.setBookFee(bookFee);
			projectFeeAnn.setBookNote(bookNote);
			projectFeeAnn.setConferenceFee(conferenceFee);
			projectFeeAnn.setConferenceNote(conferenceNote);
			projectFeeAnn.setConsultationFee(consultationFee);
			projectFeeAnn.setConsultationNote(consultationNote);
			projectFeeAnn.setDataFee(dataFee);
			projectFeeAnn.setDataNote(dataNote);
			projectFeeAnn.setDeviceFee(deviceFee);
			projectFeeAnn.setDeviceNote(deviceNote);
			projectFeeAnn.setIndirectFee(indirectFee);
			projectFeeAnn.setIndirectNote(indirectNote);
			projectFeeAnn.setInternationalFee(internationalFee);
			projectFeeAnn.setInternationalNote(internationalNote);
			projectFeeAnn.setLaborFee(laborFee);
			projectFeeAnn.setLaborNote(laborNote);
			projectFeeAnn.setOtherFee(otherFeeD);
			projectFeeAnn.setOtherNote(otherNote);
			projectFeeAnn.setPrintFee(printFee);
			projectFeeAnn.setPrintNote(printNote);
			projectFeeAnn.setTravelFee(travelFee);
			projectFeeAnn.setTravelNote(travelNote);
			projectFeeAnn.setFeeNote(feeNote);
			projectFeeAnn.setTotalFee(totalFee);
			projectFeeAnn.setFundedFee(fundedFee);
			return projectFeeAnn;
		} else {
			return null;
		}
	}
	
	public String toAddGrantedFeeApply(){
		projectid = request.getParameter("projectid").trim();
		approveFee = Double.parseDouble(request.getParameter("approveFee").trim());
		return SUCCESS;
	}
	
	public String addGrantedFeeApply() {
		System.out.println("0_____________1_________________2____________________3_____________4_________5");
		return SUCCESS;
	}
	
    
	public String getTopsId() {
		return topsId;
	}
	public void setTopsId(String topsId) {
		this.topsId = topsId;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getSelectedTab() {
		return selectedTab;
	}
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
	}
	public boolean getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}
	public List<Integer> getIsEndReviewer() {
		return isEndReviewer;
	}
	public void setIsEndReviewer(List<Integer> isEndReviewer) {
		this.isEndReviewer = isEndReviewer;
	}
	public int getIsAppReviewer() {
		return isAppReviewer;
	}
	public void setIsAppReviewer(int isAppReviewer) {
		this.isAppReviewer = isAppReviewer;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public Integer getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}
	public Integer getAnnStatus() {
		return annStatus;
	}
	public void setAnnStatus(Integer annStatus) {
		this.annStatus = annStatus;
	}
	public Integer getMidStatus() {
		return midStatus;
	}
	public void setMidStatus(Integer midStatus) {
		this.midStatus = midStatus;
	}
	public Integer getEndStatus() {
		return endStatus;
	}
	public void setEndStatus(Integer endStatus) {
		this.endStatus = endStatus;
	}
	public Integer getVarStatus() {
		return varStatus;
	}
	public void setVarStatus(Integer varStatus) {
		this.varStatus = varStatus;
	}
	public Integer getTopsStatus() {
		return topsStatus;
	}
	public void setTopsStatus(Integer topsStatus) {
		this.topsStatus = topsStatus;
	}
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public IKeyService getKeyService() {
		return keyService;
	}
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}
	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public void setDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}
	
	public void setTopicSelection(KeyTopicSelection topicSelection) {
		this.topicSelection = topicSelection;
	}
	public KeyTopicSelection getTopicSelection() {
		return topicSelection;
	}
	public void setProjectFeeApply(ProjectFee projectFeeApply) {
		this.projectFeeApply = projectFeeApply;
	}
	public ProjectFee getProjectFeeApply() {
		return projectFeeApply;
	}
	public void setProjectFeeGranted(ProjectFee projectFeeGranted) {
		this.projectFeeGranted = projectFeeGranted;
	}
	public ProjectFee getProjectFeeGranted() {
		return projectFeeGranted;
	}
	public Integer getGraStatus() {
		return graStatus;
	}
	public void setGraStatus(Integer graStatus) {
		this.graStatus = graStatus;
	}
}
