package csdc.action.project;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectMidinspection;
import csdc.bean.SystemOption;
import csdc.service.IMessageAuxiliaryService;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.HSSFExport;
import csdc.tool.HqlTool;
import csdc.tool.StringTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.FileRecord;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 项目立项父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目立项共用的相关方法
 * @author 余潜玉
 */
public abstract class ApplicationGrantedAction extends ProjectBaseAction {

	private static final long serialVersionUID = -700148736686965249L;
	private static final String HQL1 = "select app.id, gra.id, gra.number, gra.name, gra.applicantId, " +
			"gra.applicantName, uni.id, uni.name, so.name, app.disciplineType, app.year, gra.approveFee, gra.status ";
	protected String savePath;//文件上传路径
    protected String projectNumber,projectName,researchType,projectSubtype,dtypeNames,applicant,university,projectTopic,divisionName,provinceName,memberName;//高级检索条件
	protected int projectStatus,startYear,endYear;//高级检索条件
	protected int deptInstFlag;//院系或研究机构标志位	1：研究机构	2：院系
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;//标题提交上来的特征码list
	protected String uploadKey;//文件上传授权码
	protected Date endStopWithdrawDate;//审核时间
	protected String projectOpinion;//设置项目状态的原因
	protected String projectOpinionFeedback;//设置项目状态的原因(反馈意见)
	protected int addflag;//添加类别	1：从已申请未立项项目中选择， 2：新建
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	protected Date startDate,endDate;
	private String fileFileName;
	private int expFlag;//0：普通列表导出；1:高级检索导出
	private int midiStatus;//1:中检通过；2：中检未通过
	protected int timeFlag = 1;//业务时间是否有效标志位  1：有效
	protected int graFlag;//添加立项申请是否成功标志位	1：添加成功
	protected int garApplicantSubmitStatus;//提交状态
	protected String note;//备注
	protected ProjectGranted granted;
	protected Date graDate;//录入时间
	private String graId;
	protected int graResult;//录入结果	1：不同意	2：同意
	protected int graImportedStatus;//录入状态
	protected String graImportedOpinion;//录入意见
	protected String graOpinionFeedback;//录入意见（反馈给项目负责人）
	
	private static final String[] CCOLUMNNAME = {
		"批准号",
		"项目名称",
		"负责人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"批准经费",
		"项目状态"
	};
	private static final String[] COLUMN = {
		"gra.number",
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.disciplineType",
		"app.year desc",
		"gra.approveFee",
		"gra.status"
	};//排序列
	public abstract String grantedClassName();//项目立项类类名
	public abstract String listHql2();
	public abstract String listHql3();
	
	public String[] columnName() {
		return ApplicationGrantedAction.CCOLUMNNAME;
	}
	public String[] column() {
		return ApplicationGrantedAction.COLUMN;
	}
	
	/**
	 *  列表辅助信息
	 * @return json
	 */
	public String assist() {
		List lData = new ArrayList();
		Pager pager = (Pager) session.get(pageName());
		HqlTool hqlTool = new HqlTool(pager.getHql());
		int cloumn = pager.getSortColumn();
		if (null == session.get("graAssistMap") && cloumn != 3 &&  cloumn != 4 && cloumn != 6 && cloumn != 8) {
			cloumn =5 ;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 6 || cloumn == 3 || cloumn == 4 || cloumn == 5 || cloumn == 8) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "gra.id");
		} else {
			jsonMap = (Map) session.get("graAssistMap");
		}
		session.put("graAssistMap", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType accountType = loginer.getCurrentType();
		hql.append(HQL1);
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getGrantedSimpleSearchHQL(searchType));
		}
		Map session = ActionContext.getContext().getSession();
		session.put("grantedMap", map);
		Account account = loginer.getAccount();
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		//处理查询范围
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.selectClause);
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(57);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			6,
			null
		};
	}
	
	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType accountType = loginer.getCurrentType();
		hql.append(HQL1);
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		if (projectNumber != null && !projectNumber.isEmpty()) {
			projectNumber = projectNumber.toLowerCase();
			hql.append(" and LOWER(gra.number) like :projectNumber");
			map.put("projectNumber", "%" + projectNumber + "%");
		}
		if (projectName != null && !projectName.isEmpty()) {
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(gra.name) like :projectName");
			map.put("projectName", "%" + projectName + "%");
		}
		if (memberName != null && !memberName.isEmpty()) {
			memberName = memberName.toLowerCase();
			hql.append(" and LOWER(mem.memberName) like :memberName");
			map.put("memberName", "%" + memberName + "%");
		}
		
		if(!projectType().equals("key")){
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				projectSubtype = projectSubtype.toLowerCase();
				hql.append(" and LOWER(so.id) like :projectSubtype");
				map.put("projectSubtype", "%" + projectSubtype + "%");
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
				researchType = researchType.toLowerCase();
				hql.append(" and LOWER(so.id) like :researchType");
				map.put("researchType", "%" + researchType + "%");
			}
		}
		if (!projectType().equals("instp") && !projectType().equals("key") && projectTopic != null && !projectTopic.equals("-1")) {
			projectTopic = projectTopic.toLowerCase();
			hql.append(" and LOWER(top.name) like :projectTopic");
			map.put("projectTopic", "%" + projectTopic + "%");
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			String[] dtypes = dtypeNames.split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("dtype" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(app.disciplineType) like :dtype" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		}
		if (startYear != -1) {
			hql.append(" and app.year>=:startYear");
			map.put("startYear", startYear);
		}
		if (endYear != -1) {
			hql.append(" and app.year<=:endYear");
			map.put("endYear", endYear);
		}
		if ( applicant!= null && !applicant.isEmpty()) {
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(gra.applicantName) like :applicant");
			map.put("applicant", "%" + applicant + "%");
		}
		if (university != null && !university.isEmpty()) {
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university");
			map.put("university", "%" + university + "%");
		}
		if (divisionName != null && !divisionName.isEmpty()) {
			divisionName = divisionName.toLowerCase();
			hql.append(" and LOWER(gra.divisionName) like :divisionName");
			map.put("divisionName", "%" + divisionName + "%");
		}
		if (provinceName != null && !provinceName.isEmpty()) {
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(gra.provinceName) like :provinceName");
			map.put("provinceName", "%" + provinceName + "%");
		}
		if (projectStatus != -1) {
			if(projectStatus == 1){
				hql.append(" and gra.status = 1");
			}
			if(projectStatus == 2){
				hql.append(" and gra.status = 2");
			}
			if(projectStatus == 3){
				hql.append(" and gra.status = 3");
			}
			if(projectStatus == 4){
				hql.append(" and gra.status = 4");
			}
			map.put("projectStatus", projectStatus);
		}
		if (midiStatus != -1) {
			if(midiStatus == 1){
				hql.append(" and exists(select midi.id from ProjectMidinspection midi where midi.grantedId = gra.id and midi.finalAuditResult = 2)");
			}
			if(midiStatus == 2){
				hql.append(" and not exists(select midi.id from ProjectMidinspection midi where midi.grantedId = gra.id and midi.finalAuditResult = 2)");
			}
			map.put("midiStatus", midiStatus);
		}
		Map session = ActionContext.getContext().getSession();
		session.put("grantedMap", map);
		Account account = loginer.getAccount();
		//处理查询范围
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		expFlag =1;
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(57);
		String groupBy  = hqlToolWhere.getSelectClause();
		hql.append(" group by " + groupBy);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			6,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if (projectNumber != null && !projectNumber.isEmpty()) {
			searchQuery.put("projectNumber", projectNumber);
		}
		if(projectName!=null && !projectName.isEmpty()){
			searchQuery.put("projectName", projectName);
		}
		if(!projectType().equals("key")){
			if (projectSubtype != null && !projectSubtype.equals("-1")) {
				searchQuery.put("projectSubtype", projectSubtype);
			}
		}else{//重大攻关
			if (researchType != null && !researchType.equals("-1")) {
				searchQuery.put("researchType", researchType);
			}
		}
		if(!projectType().equals("instp") && !projectType().equals("key") && projectTopic != null && !projectTopic.equals("-1")){
			searchQuery.put("projectTopic", projectTopic);
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			searchQuery.put("dtypeNames", dtypeNames);
		}
		if(startYear!=-1){
			searchQuery.put("startYear", startYear);
		}
		if(endYear!=-1){
			searchQuery.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			searchQuery.put("applicant", applicant);
		}
		if(university!=null && !university.isEmpty()){
			searchQuery.put("university", university);
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			searchQuery.put("provinceName", provinceName);
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			searchQuery.put("divisionName", divisionName);
		}
		if (projectStatus != -1) {
			searchQuery.put("projectStatus", projectStatus);
		}
		if (midiStatus != -1) {
			searchQuery.put("midiStatus", midiStatus);
		}
		if (memberName !=null && !memberName.isEmpty()) {
			searchQuery.put("memberName", memberName);
		}
		
		searchQuery.put("expFlag", expFlag);
		session.put("searchQuery", searchQuery);
	}
	
	public String publish() {
		for(int i = 0; i < entityIds.size(); i++){
			ProjectGranted application = (ProjectGranted)(dao.query(ProjectGranted.class, projectService.getGrantedIdByAppId(entityIds.get(i))));
			if(application == null){//校验申请项目是否存在
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NOT_EXIST);
				return INPUT;
			}
			//判断管理范围
			if(!this.projectService.checkIfUnderControl(loginer, entityIds.get(i).trim(), checkApplicationFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectGranted application = (ProjectGranted) dao.query(ProjectGranted.class, projectService.getGrantedIdByAppId(entityIds.get(i)));
			application.setIsPublished(1);
			dao.modify(application);
		}
		return SUCCESS;
	}
	//取消公开发布
	public String notPublish() {
		for(int i = 0; i < entityIds.size(); i++){
			ProjectGranted application = (ProjectGranted) dao.query(ProjectGranted.class, projectService.getGrantedIdByAppId(entityIds.get(i)));
			if(application == null){//校验申请项目是否存在
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NOT_EXIST);
				return INPUT;
			}
			//判断管理范围
			if(!this.projectService.checkIfUnderControl(loginer, entityIds.get(i).trim(), checkApplicationFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectGranted application = (ProjectGranted)(dao.query(ProjectGranted.class, projectService.getGrantedIdByAppId(entityIds.get(i))));
			application.setIsPublished(0);
			dao.modify(application);
		}
		return SUCCESS;
	}
	
	/**
	 * 进入添加页面预处理
	 */
	public String toAdd(){
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	
	/**
	 * 进入项目立项计划书申请添加页面预处理
	 * @author yangfq
	 */
	public String toAddGra(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		graStatus = this.projectService.getBusinessStatus(businessType(), appId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		ProjectGranted projectGranted = dao.query(ProjectGranted.class, projectid);
		projectService.doWithToAdd(projectGranted);
		dao.modify(projectGranted);
		return isTimeValidate();
	}
	
	/**
	 * 进入项目立项计划录入添加页面预处理
	 * @author yangfq
	 */
	public String toAddResult(){
		graDate = new Date();
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	/**
	 * 进入项目立项申请修改页面预处理
	 * @author yangfq
	 */
	public String toModifyGra(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//中检业务状态1：业务激活0：业务停止
		AccountType accountType = loginer.getCurrentType();
		graStatus = this.projectService.getBusinessStatus(businessType(), appId);
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		
		if (granted.getProjectFee() != null) {
			projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
		}
		
		note = granted.getNote();
		//将已有附件加入文件组，在编辑页面显示
		graId = granted.getId();
		String groupId = "file_" + graId;
		uploadService.resetGroup(groupId);
		if (granted.getFile() != null) {
			String[] tempFileRealpath = granted.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null) {
					uploadService.addFile(groupId, new File(fileRealpath));
				}
			}
		}
		
		return isTimeValidate();
	}
	/**
	 * 删除立项申请
	 */
	public String deleteGra(){
		ProjectGranted projectGranted = dao.query(ProjectGranted.class, projectid);
		projectService.doWithToAdd(projectGranted);
		dao.modify(projectGranted);
		return SUCCESS;
	}
	/**
	 * 提交项目立项申请
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		/*if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}*/
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		if(granted.getAuditstatus() < 0){//未提交的申请才可提交
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		granted.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		granted.edit(auditMap);//部门审核通过
		/* 结束 */
		dao.modify(granted);
		return SUCCESS;
	}
	
	
	/**
	 * 业务时限判断
	 * @param type 校验类型：1添加；;3提交
	 */
	@SuppressWarnings("unchecked")
	public String isTimeValidate() {
		AccountType accountType = loginer.getCurrentType();
		Date date =  new Date();
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_TIME_INVALIDATE);
			setTimeFlag(0);
			return INPUT;
		}
		setTimeFlag(1);
		return SUCCESS;
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
	 * 根据项目申请 id删除项目相关信息
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete(){
		for(int i = 0; i < entityIds.size(); i++){
			ProjectApplication application = (ProjectApplication)(dao.query(ProjectApplication.class, entityIds.get(i)));
			if(application == null){//校验申请项目是否存在
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NOT_EXIST);
				return INPUT;
			}
			//判断管理范围
			if(!this.projectService.checkIfUnderControl(loginer, entityIds.get(i).trim(), checkGrantedFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			this.projectService.deleteProject(entityIds.get(i));
		}
		return SUCCESS;
	}

	/**
	 * 删除项目校验
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete(){
		if (entityIds == null || entityIds.isEmpty()) {//删除项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_GRANTED_DELETE_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_GRANTED_DELETE_NULL);
		}
	}

	/**
	 * 设置项目状态预处理
	 */
	@SuppressWarnings("unchecked")
	public String toSetUpProjectStatus(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		projectStatus = granted.getStatus();
		endStopWithdrawDate = (null == granted.getEndStopWithdrawDate() ? new Date() : granted.getEndStopWithdrawDate());
		projectOpinion = granted.getEndStopWithdrawOpinion();
		projectOpinionFeedback = granted.getEndStopWithdrawOpinionFeedback();
		//是否有待审变更
		int varPending = this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0 ? 1 : 0;
		request.setAttribute("varPending", varPending);
		if(!"post".equals(projectType()) && !"entrust".equals(projectType())){
			//是否有待审中检
			int midPending = this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
			request.setAttribute("midPending", midPending);
		}
		return SUCCESS;
	}
	
	/**
	 * 设置项目立项状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String setUpProjectStatus(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		String personName;
		AccountType accountType = loginer.getCurrentType();
		int isPrincipal = loginer.getIsPrincipal();
		if((accountType.within(AccountType.MINISTRY, AccountType.INSTITUTE) && isPrincipal == 0) || accountType.within(AccountType.EXPERT, AccountType.STUDENT)){
			personName = loginer.getCurrentPersonName();
		}else{
			personName = loginer.getCurrentBelongUnitName();
		}
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		granted.setStatus(projectStatus);
		granted.setEndStopWithdrawDate(this.projectService.setDateHhmmss(endStopWithdrawDate));
		granted.setEndStopWithdrawPerson(personName);
		granted.setEndStopWithdrawOpinion(("A" + projectOpinion).trim().substring(1));
		granted.setEndStopWithdrawOpinionFeedback(("A" + projectOpinionFeedback).trim().substring(1));
		this.dao.modify(granted);
		return SUCCESS;
	}
	
	/**
	 * 校验设置项目状态
	 * @return
	 */
	public void validateSetUpProjectStatus(){
		if (projectid == null || projectid.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
//		if(projectStatus < 1 || projectStatus > 4){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_STATUS_NULL);
//		}
		if(projectStatus != 4){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_STATUS_NULL);
		}
		if(!"post".equals(projectType())){
			//是否有待审中检
			int midPending = this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
			if(midPending == 1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
			}
		}
		//是否有待审变更
		int varPending = this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0 ? 1 : 0;
		if(varPending == 1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDV_DEALING);
		}
		if(projectOpinion == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_OPINION_NULL);
		}else if(projectOpinion.length()>200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_OPINION_OUT);
		}
		if(projectOpinionFeedback.length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_OPINION_OUT);
		}
	}
	

	/**
	 * 添加、修改修改项目项目立项计划公共处理
	 * @author yangfq
	 * @return 处理后的项目中检对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectGranted doWithAddOrMdoify(int type ,ProjectGranted projectGranted){
		 //保存上传的文件
		String groupId = null;
		if (type == 1) {
			groupId = "file_add";
		} else {
			groupId = "file_" + projectGranted.getId();
		}
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadGraFile(projectType(), projectid, garApplicantSubmitStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		projectGranted.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		if(note != null){
			projectGranted.setNote(("A"+note).trim().substring(1));
		}else{
			projectGranted.setNote(null);
		}
		projectGranted.setAuditType(0);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, garApplicantSubmitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
		projectGranted.edit(auditMap);//保存操作结果
		/* 以下代码为跳过部门审核*/
		if(garApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			projectGranted.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		uploadService.flush(groupId);
		return projectGranted;
	}
	/**
	 * 添加录入的项目立项计划的公共处理部分
	 * @param ProjectGranted
	 * @author yangfq
	 * @return 处理后的立项对象
	 */
	@SuppressWarnings({ "rawtypes" })
	public ProjectGranted doWithAddResult(ProjectGranted projectGranted){
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadGraFile(projectType(), projectid, graImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		projectGranted.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		/**
		 * 处理各级审核的状态；因为是教育部直接录入，所以各级审核的状态赋值为初始值
		 */
		projectGranted.setDeptInstAuditResult(0);
		projectGranted.setDeptInstAuditStatus(0);
		
		projectGranted.setUniversityAuditResult(0);
		projectGranted.setUniversityAuditStatus(0);
		
		projectGranted.setProvinceAuditResult(0);
		projectGranted.setProvinceAuditStatus(0);
		
		Date submitDate = this.projectService.setDateHhmmss(graDate);
		projectGranted.setApplicantSubmitDate(submitDate);
		projectGranted.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, graResult, graImportedStatus, null);
		projectGranted.setImportAuditDate(new Date());
		projectGranted.setFinalAuditor(auditInfo.getAuditor());
		projectGranted.setFinalAuditorName(auditInfo.getAuditorName());
		projectGranted.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		projectGranted.setFinalAuditorDept(auditInfo.getAuditorDept());
		projectGranted.setFinalAuditorInst(auditInfo.getAuditorInst());
		projectGranted.setAuditType(1);//设为导入数据
		projectGranted.setFinalAuditStatus(this.graImportedStatus);
		projectGranted.setFinalAuditResult(this.graResult);
		if(graImportedOpinion != null && graImportedOpinion.trim().length() > 0){
			projectGranted.setFinalAuditOpinion(("A" + graImportedOpinion).trim().substring(1));
		}else{
			projectGranted.setFinalAuditOpinion(null);
		}
		if(graOpinionFeedback != null && graOpinionFeedback.trim().length() > 0){
			projectGranted.setFinalAuditOpinionFeedback(("A" + graOpinionFeedback).trim().substring(1));
		}else{
			projectGranted.setFinalAuditOpinionFeedback(null);
		}
		uploadService.flush(groupId);
		return projectGranted;
	}

	/**
	 * 进入项目立项计划录入修改页面预处理
	 */
	public String toModifyResult(){
		ProjectGranted projectGranted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		if (projectGranted.getProjectFee() != null) {
			projectFeeGranted = dao.query(ProjectFee.class, projectGranted.getProjectFee().getId());
		}
		graDate = projectGranted.getFinalAuditDate();
		graId = projectGranted.getId();
		graImportedOpinion = projectGranted.getFinalAuditOpinion();
		graOpinionFeedback = projectGranted.getFinalAuditOpinionFeedback();
		this.graResult = projectGranted.getFinalAuditResult();
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + projectGranted.getId();
		uploadService.resetGroup(groupId);
		if (projectGranted.getFile() != null) {
			String[] tempFileRealpath = projectGranted.getFile().split("; ");
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
	/**
	 * 修改录入的项目立项计划结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modifyResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		
		if (granted.getProjectFee() != null) {
			projectFeeGranted = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			projectFeeGranted = this.doWithAddResultFee(projectFeeGranted);
			dao.modify(projectFeeGranted);
		}
		
		//保存上传的文件
		String groupId = "file_" + granted.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadGraFile(projectType(), granted.getId(), graImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		granted.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		Date submitDate = this.projectService.setDateHhmmss(graDate);
		granted.setApplicantSubmitDate(submitDate);
		granted.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, graResult, graImportedStatus, null);
		granted.setImportedDate(new Date());
		granted.setFinalAuditor(auditInfo.getAuditor());
		granted.setFinalAuditorName(auditInfo.getAuditorName());
		granted.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		granted.setFinalAuditorDept(auditInfo.getAuditorDept());
		granted.setFinalAuditorInst(auditInfo.getAuditorInst());
		granted.setFinalAuditStatus(this.graImportedStatus);
		granted.setFinalAuditResult(this.graResult);
		if(graImportedOpinion != null && graImportedOpinion.trim().length() > 0){
			granted.setFinalAuditOpinion(("A" + graImportedOpinion).trim().substring(1));
		}else{
			granted.setFinalAuditOpinion(null);
		}
		if(graOpinionFeedback != null && graOpinionFeedback.trim().length() > 0){
			granted.setFinalAuditOpinionFeedback(("A" + graOpinionFeedback).trim().substring(1));
		}else{
			granted.setFinalAuditOpinionFeedback(null);
		}
		dao.modify(granted);
		uploadService.flush(groupId);
		return SUCCESS;
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		granted.setImportedDate(new Date());
		granted.setFinalAuditor(auditInfo.getAuditor());
		granted.setFinalAuditorName(auditInfo.getAuditorName());
		granted.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		granted.setFinalAuditorDept(auditInfo.getAuditorDept());
		granted.setFinalAuditorInst(auditInfo.getAuditorInst());
		granted.setFinalAuditStatus(3);
		dao.modify(granted);
		return SUCCESS;
	}
	

	/**
	 * 项目申请表下载
	 * @author 余潜玉
	 */
	public String downloadApply()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 文件下载流
	 * @author 余潜玉
	 */
	public InputStream getTargetFile() throws Exception{
		ProjectGranted projectGranted = dao.query(ProjectGranted.class, entityId);
		savePath = projectGranted.getFile();
		String filename="";
		if(savePath!=null && savePath.length()!=0){
			filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
			savePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			return ServletActionContext.getServletContext().getResourceAsStream(filename);
		 }
		return null;
	}
	
	/**
	 * 文件是否存在校验
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String validateFile()throws Exception{
		if (null == entityId || entityId.trim().isEmpty()) {//id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} else {
			ProjectGranted projectGranted = dao.query(ProjectGranted.class, entityId);
			if(null == projectGranted){
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
			} else if(projectGranted.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				savePath = projectGranted.getFile();
				String filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
				if(null == ServletActionContext.getServletContext().getResourceAsStream(filename)){
					jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
				}
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 保存上传文件
	 * @param appId 项目申请id
	 * @param universityId 学校id
	 * @param oldFileName 原文件名
	 * @return 上传文件名
	 */
	@SuppressWarnings("rawtypes")
	public String saveAppFile(ProjectApplication application, String universityId, String oldFileName, String groupId){
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAppFile(projectType(), application, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		return StringTool.joinString(files.toArray(new String[0]), "; ");
	}
	/**
	 * 用于检验申报信息
	 * @param application 项目申报对象
	 */
	public void validateAppInfo(ProjectApplication application){
		if(!application.getType().equals("key")){
			if(application.getName() == null || application.getName().trim().isEmpty() ){
				this.addFieldError("application.name", ProjectInfo.ERROR_PROJECT_NAME_NULL);
			}else if(application.getName().length()>50){
				this.addFieldError("application.name", ProjectInfo.ERROR_PROJECT_NAME_OUT);
			}
		}
		if(application.getEnglishName() != null && application.getEnglishName().length()>200){
			this.addFieldError("application.englishName", ProjectInfo.ERROR_PROJECT_ENG_NAME_OUT);
		}
		if((deptInstFlag != 1 && deptInstFlag != 2) || (deptInstFlag == 1 && application.getInstitute().getId() == null) || (deptInstFlag == 2 && application.getDepartment().getId() == null)){
			this.addFieldError("application.agencyName", ProjectInfo.ERROR_AGENCY_NULL);
		}
		if(application.getYear()<=0 || application.getYear()>=10000){
			this.addFieldError("application.year", ProjectInfo.ERROR_PROJECT_YEAR_WRONG);
		}
//		if(application.getSubtype().getId() == null || application.getSubtype().getId().equals("-1")){
//			this.addFieldError("application.subType.id", ProjectInfo.ERROR_PROJECT_SUBTYPE_NULL);
//		}
//		if(application.getProductType() == null || application.getProductType().trim().isEmpty()){
//			this.addFieldError("application.productType", ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_NULL);
//		}else{
//			if(application.getProductType().contains("其他")){
//				if(application.getProductTypeOther() == null || application.getProductTypeOther().trim().isEmpty()){
//					this.addFieldError("application.productTypeOther", ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_NULL);
//				}else if(application.getProductTypeOther().trim().length() > 50){
//					this.addFieldError("application.productTypeOther", ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_OUT);
//				}
//			}
//		}
		if(application.getProductType() != null && application.getProductType().contains("其他")){
			if(application.getProductTypeOther() == null || application.getProductTypeOther().trim().isEmpty()){
				this.addFieldError("application.productTypeOther", ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_NULL);
			}else if(application.getProductTypeOther().trim().length() > 50){
				this.addFieldError("application.productTypeOther", ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_OUT);
			}
		}
//		if(application.getPlanEndDate() == null){
//			this.addFieldError("application.planEndDate", ProjectInfo.ERROR_PROJECT_PLAN_END_DATE_NULL);
//		}
//		if(application.getApplyFee() == null){
//			this.addFieldError("application.applyFee", ProjectInfo.ERROR_PROJECT_APPLY_FEE_NULL);
//		}else if(application.getApplyFee() < 0){
//			this.addFieldError("application.applyFee", ProjectInfo.ERROR_PROJECT_APPLY_FEE_WRONG);
//		}
		if(application.getApplyFee() != null && application.getApplyFee() < 0){
			this.addFieldError("application.applyFee", ProjectInfo.ERROR_PROJECT_APPLY_FEE_WRONG);
		}
//		if(application.getOtherFee() == null){
//			this.addFieldError("application.otherFee", ProjectInfo.ERROR_PROJECT_OTHER_FEE_NULL);
//		}else if(application.getOtherFee() < 0){
//			this.addFieldError("application.otherFee", ProjectInfo.ERROR_PROJECT_OTHER_FEE_WRONG);
//		}
		if(application.getOtherFee() != null && application.getOtherFee() < 0){
			this.addFieldError("application.otherFee", ProjectInfo.ERROR_PROJECT_OTHER_FEE_WRONG);
		}
//		if(application.getResearchType().getId() == null || application.getResearchType().getId().equals("-1")){
//			this.addFieldError("application.researchType.id", ProjectInfo.ERROR_PROJECT_RESEARCH_TYPE_NULL);
//		}
//		if(application.getDisciplineType() == null || application.getDisciplineType().trim().isEmpty() ){
//			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_NULL);
//		}else if(application.getDisciplineType().length()>100){
//			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_OUT);
//		}
		if(application.getDisciplineType() != null && application.getDisciplineType().length()>100){
			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_OUT);
		}
//		if(application.getDiscipline() == null || application.getDiscipline().trim().isEmpty() ){
//			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_NULL);
//		}else if(application.getDiscipline().length()>100){
//			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_OUT);
//		}
		if(application.getDiscipline() != null && application.getDiscipline().length()>100){
			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_OUT);
		}
		if(application.getRelativeDiscipline() != null && application.getRelativeDiscipline().length()>100 ){
			this.addFieldError("application.relativedispline", ProjectInfo.ERROR_PROJECT_RELATIVE_DISCIPLINE_OUT);
		}
		if(application.getKeywords() != null && application.getKeywords().length()>100){
			this.addFieldError("application.keywords", ProjectInfo.ERROR_PROJECT_KEYWORDS_OUT);
		}
		if(application.getSummary() != null && application.getSummary().length()>200){
			this.addFieldError("application.summary", ProjectInfo.ERROR_PROJECT_SUMMARY_OUT);
		}
	}
	
	/**
	 * 用于检验经费信息
	 * @param projectFee 项目申报经费概算明细
	 * @param applyFee 项目申报总经费
	 */
	public void validateProjectFee(ProjectFee projectFee,Double applyFee){
		if(projectFee.getBookFee() != null && (projectFee.getBookFee() < 0 || projectFee.getBookFee() > applyFee)){
			this.addFieldError("projectFee.bookFee", ProjectInfo.ERROR_PROJECTFEE_BOOKFEE_OVER);
		}
		if(projectFee.getBookNote()!= null && projectFee.getBookNote().length() > 50){
			this.addFieldError("projectFee.bookNote", ProjectInfo.ERROR_PROJECTFEE_BOOKNOTE_OUT);
		}
		if(projectFee.getDataFee() != null &&(projectFee.getDataFee() < 0 || projectFee.getDataFee() > applyFee)){
			this.addFieldError("projectFee.dataFee", ProjectInfo.ERROR_PROJECTFEE_DATAFEE_OVER);
		}
		if(projectFee.getDataNote()!= null && projectFee.getDataNote().length() > 50){
			this.addFieldError("projectFee.dataNote", ProjectInfo.ERROR_PROJECTFEE_DATANOTE_OUT);
		}
		if(projectFee.getTravelFee() != null &&(projectFee.getTravelFee() < 0 || projectFee.getTravelFee() > applyFee)){
			this.addFieldError("projectFee.travelFee", ProjectInfo.ERROR_PROJECTFEE_TRAVELFEE_OVER);
		}
		if(projectFee.getTravelNote()!= null && projectFee.getTravelNote().length() > 50){
			this.addFieldError("projectFee.travelNote", ProjectInfo.ERROR_PROJECTFEE_TRAVELNOTE_OUT);
		}
		if(projectFee.getDeviceFee() != null &&(projectFee.getDeviceFee() < 0 || projectFee.getDeviceFee() > applyFee)){
			this.addFieldError("projectFee.deviceFee", ProjectInfo.ERROR_PROJECTFEE_DEVICEFEE_OVER);
		}
		if(projectFee.getDeviceNote()!= null && projectFee.getDeviceNote().length() > 50){
			this.addFieldError("projectFee.deviceNote", ProjectInfo.ERROR_PROJECTFEE_DEVICENOTE_OUT);
		}
		if(projectFee.getConferenceFee() != null &&(projectFee.getConferenceFee() < 0 || projectFee.getConferenceFee() > applyFee)){
			this.addFieldError("projectFee.conferenceFee", ProjectInfo.ERROR_PROJECTFEE_CONFERENCEFEE_OVER);
		}
		if(projectFee.getConferenceNote()!= null && projectFee.getConferenceNote().length() > 50){
			this.addFieldError("projectFee.conferenceNote", ProjectInfo.ERROR_PROJECTFEE_CONFERENCENOTE_OUT);
		}
		if(projectFee.getConsultationFee() != null &&(projectFee.getConsultationFee() < 0 || projectFee.getConsultationFee() > applyFee)){
			this.addFieldError("projectFee.consultationFee", ProjectInfo.ERROR_PROJECTFEE_CONSULTATIONFEE_OVER);
		}
		if(projectFee.getConsultationNote()!= null && projectFee.getConsultationNote().length() > 50){
			this.addFieldError("projectFee.consultationNote", ProjectInfo.ERROR_PROJECTFEE_CONSULTATIONNOTE_OUT);
		}
		if(projectFee.getLaborFee() != null &&(projectFee.getLaborFee() < 0 || projectFee.getLaborFee() > applyFee)){
			this.addFieldError("projectFee.laborFee", ProjectInfo.ERROR_PROJECTFEE_LABORFEE_OVER);
		}
		if(projectFee.getLaborNote()!= null && projectFee.getLaborNote().length() > 50){
			this.addFieldError("projectFee.laborNote", ProjectInfo.ERROR_PROJECTFEE_LABORNOTE_OUT);
		}
		if(projectFee.getPrintFee() != null &&(projectFee.getPrintFee() < 0 || projectFee.getPrintFee() > applyFee)){
			this.addFieldError("projectFee.printFee", ProjectInfo.ERROR_PROJECTFEE_PRINTFEE_OVER);
		}
		if(projectFee.getPrintNote()!= null && projectFee.getPrintNote().length() > 50){
			this.addFieldError("projectFee.printNote", ProjectInfo.ERROR_PROJECTFEE_PRINTNOTE_OUT);
		}
		if(projectFee.getInternationalFee() != null &&(projectFee.getInternationalFee() < 0 || projectFee.getInternationalFee() > applyFee)){
			this.addFieldError("projectFee.internationalFee", ProjectInfo.ERROR_PROJECTFEE_INTERNATIONALFEE_OVER);
		}
		if(projectFee.getInternationalNote()!= null && projectFee.getInternationalNote().length() > 50){
			this.addFieldError("projectFee.internationalNote", ProjectInfo.ERROR_PROJECTFEE_INTERNATIONALNOTE_OUT);
		}
		if(projectFee.getIndirectFee() != null &&(projectFee.getIndirectFee() < 0 || projectFee.getIndirectFee() > applyFee)){
			this.addFieldError("projectFee.indirectFee", ProjectInfo.ERROR_PROJECTFEE_INDIRECTFEE_OVER);
		}
		if(projectFee.getIndirectNote()!= null && projectFee.getIndirectNote().length() > 50){
			this.addFieldError("projectFee.indirectNote", ProjectInfo.ERROR_PROJECTFEE_INDIRECTNOTE_OUT);
		}
		if(projectFee.getOtherFee() != null &&(projectFee.getOtherFee() < 0 || projectFee.getOtherFee() > applyFee)){
			this.addFieldError("projectFee.otherFee", ProjectInfo.ERROR_PROJECTFEE_OTHERFEE_OVER);
		}
		if(projectFee.getOtherNote()!= null && projectFee.getOtherNote().length() > 50){
			this.addFieldError("projectFee.otherNote", ProjectInfo.ERROR_PROJECTFEE_OTHERNOTE_OUT);
		}
		if(projectFee.getTotalFee()!= null){
			if (projectFee.getTotalFee().compareTo(applyFee) != 0) {
				this.addFieldError("application.applyFee", ProjectInfo.ERROR_PROJECT_FEE_WRONG);
			}
		}
		
	}
	
	/**
	 * 用于校验项目立项信息
	 * @param granted 项目立项对象
	 * @param application 项目申报对象
	 */
	public void validateGrantedInfo(ProjectGranted granted, ProjectApplication application){
		if(granted == null){
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
		}else{
			if(granted.getNumber() == null || granted.getNumber().trim().isEmpty()){
				this.addFieldError("granted.number", ProjectInfo.ERROR_PROJECT_NUMBER_NULL);
			}else if(granted.getNumber().length()>40){
				this.addFieldError("granted.number", ProjectInfo.ERROR_PROJECT_NUMBER_OUT);
			}else if(!this.projectService.isGrantedNumberUnique(grantedClassName(), granted.getNumber(), (addflag == 1) ? entityId : application.getId())){
				this.addFieldError("granted.number", ProjectInfo.ERROR_PROJECT_NUMBER_EXIST);
			}
//			if(granted.getSubtype().getId() == null || granted.getSubtype().getId().equals("-1")){
//				this.addFieldError("granted.subType.id", ProjectInfo.ERROR_PROJECT_SUBTYPE_NULL);
//			}
//			if(granted.getApproveDate() == null){
//				this.addFieldError("granted.approveDate", ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL);
//			}
//			if(granted.getApproveFee() == null){
//				this.addFieldError("granted.approveFee", ProjectInfo.ERROR_PROJECT_APPROVE_FEE_NULL);
//			}else if(granted.getApproveFee() < 0){
//				this.addFieldError("granted.approveFee", ProjectInfo.ERROR_PROJECT_APPROVE_FEE_WRONG);
//			}
			if(granted.getApproveFee() != null && granted.getApproveFee() < 0){
				this.addFieldError("granted.approveFee", ProjectInfo.ERROR_PROJECT_APPROVE_FEE_WRONG);
			}
		}
	}
	
	/**
	 * 用于校验项目成员信息
	 * @param member 项目成员对象
	 * @param type 1：添加	2：修改
	 */
	public void validateMemberInfo(ProjectMember member, int type){
		if(member.getMemberType() != 1 && member.getMemberType() != 2 && member.getMemberType() != 3){
			this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_TYPE_NULL);
		}
		if(type == 1){
			if (loginer.getCurrentType().equals(AccountType.MINISTRY)) {
				if(member.getMemberName()==null || member.getMemberName().trim().isEmpty() || member.getMember()== null || member.getMember().getId()==null || member.getMember().getId().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
				}
			} else {
				if(member.getMemberName()==null || member.getMemberName().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
				}
			}
		}else if(type == 2){
			if(member.getMember()!= null && member.getMember().getId()!= null && !member.getMember().getId().trim().isEmpty()){
				if(member.getMemberName()==null || member.getMemberName().trim().isEmpty() || member.getMember().getId()==null || member.getMember().getId().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
				}
			}else{
				if(member.getMemberName()==null || member.getMemberName().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
				}
				if(member.getAgencyName()==null || member.getAgencyName().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_UNIT_NULL);
				}
				if(member.getDivisionName()==null || member.getDivisionName().trim().isEmpty()){
					this.addActionError(ProjectInfo.ERROR_PROJECT_DEPT_INST_NULL);
				}
			}
		}
//		if(member.getSpecialistTitle()==null || member.getSpecialistTitle().equals("-1") || member.getSpecialistTitle().trim().isEmpty()){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_SPECIALIST_TITLE_NULL);
//		}
		if(member.getMajor()!=null && member.getMajor().length()> 50){
			this.addActionError(ProjectInfo.ERROR_PROJECT_MAJOR_OUT);
		}
//		if(member.getWorkMonthPerYear() == null){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_NULL);
//		}else if(member.getWorkMonthPerYear()<0 || member.getWorkMonthPerYear()>12){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_WRONG);
//		}
		if(member.getWorkMonthPerYear() != null && (member.getWorkMonthPerYear()<0 || member.getWorkMonthPerYear()>12)){
			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_WRONG);
		}
		if(member.getWorkDivision()!=null && member.getWorkDivision().length()>200){
			this.addActionError(ProjectInfo.ERROR_PROJECT_DIVISION_OUT);
		}
		if(member.getIsDirector()!=0 && member.getIsDirector()!=1){
			this.addActionError(ProjectInfo.ERROR_PROJECT_IS_DIRECTOR_NULL);
		}
	}
	
	/**
	 * 导出立项一览表
	 * @return
	 * @author 王燕
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String exportOverView(){
		//项目子类
		Map map = new HashMap();
		List<SystemOption> list = soDao.queryChildren(soDao.query("projectType", projectService.getProjectCodeByType(projectType())));
		for(SystemOption systemOption : list){
			map.put(systemOption.getId(), systemOption.getName());
		}
		request.setAttribute("subTypes", map);
		return SUCCESS;
	}
	
	/**
	 * 确认导出立项一览表
	 * @return
	 * @author 王燕
	 */
	public String confirmExportOverView(){
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		if("general".equals(projectType())){
			header = "教育部人文社会科学研究一般项目立项情况一览表";
		}else if ("instp".equals(projectType())){
			header = "教育部人文社会科学研究基地项目立项情况一览表";
		}else if ("post".equals(projectType())){
			header = "教育部人文社会科学研究后期资助项目立项情况一览表";
		}else if ("key".equals(projectType())){
			header = "教育部人文社会科学研究重大攻关项目立项情况一览表";
		}else if ("entrust".equals(projectType())){
			header = "教育部人文社会科学研究委托应急课题立项情况一览表";
		}
		StringBuffer hql4Export = new StringBuffer();
		String[] title = new String[]{};//标题
		if ("instp".equals(projectType())){//若是基地项目则需要查询基地名称
			title = new String[]{
					"序号",
					"项目立项ID",
					"项目名称",
					"项目类别",
					"项目年度",
					"项目批准号",
					"依托高校",
					"基地名称",
					"项目负责人",
					"负责人所在高校",
					"最终成果形式",
					"批准经费（万元）",
					"计划完成时间",
					"项目状态"
				};
			hql4Export.append("select gra.id, uni.name, ins.name, gra.name, so.name, app.year, gra.number, gra.applicantName, mem.agencyName, gra.productType, "+
						"gra.productTypeOther, gra.approveFee, gra.status,to_char(gra.planEndDate,'yyyy-MM-dd') from "  + grantedClassName() + " gra left outer join gra.university uni left outer join gra.institute ins " +
						" left outer join gra.application app left outer join gra.subtype so, InstpMember mem  where mem.application.id = app.id and "+
						"gra.memberGroupNumber = mem.groupNumber and mem.isDirector =1 and app.finalAuditStatus=3 and app.finalAuditResult=2 " );
		}else {
			title = new String[]{
					"序号",
					"项目立项ID",
					"项目名称",
					"项目类别",
					"项目年度",
					"项目批准号",
					"依托高校",
					"项目负责人",
					"最终成果形式",
					"批准经费（万元）",
					"计划完成时间",
					"项目状态"
				};
			
//			hql4Export = "select gra.id, uni.name, gra.name, so.name, app.year, gra.number, gra.applicantName, gra.productType, gra.productTypeOther, gra.approveFee, gra.status, to_char(gra.planEndDate,'yyyy-MM-dd') from "  + grantedClassName() + " gra left outer join gra.university uni left outer join gra.application app left outer join app.topic top left outer join gra.subtype so where app.finalAuditStatus=3 and app.finalAuditResult=2 ";
			hql4Export.append("select gra.id, uni.name, gra.name, so.name, app.year, gra.number, gra.applicantName, gra.productType, gra.productTypeOther, gra.approveFee, gra.status, to_char(gra.planEndDate,'yyyy-MM-dd') " );
			AccountType accountType = loginer.getCurrentType();
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				hql4Export.append(listHql3());
			}else{//管理人员
				hql4Export.append(listHql2());
			}
		}
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		StringBuffer sb = new StringBuffer();
		sb.append(hql4Export);
		Map parMap = (Map) session.get("grantedMap");
		/*if (null!= (String) session.get("whereHql")) {
			HqlTool hqlToolWhere = new HqlTool((String) session.get("whereHql"));
			sb.append(hqlToolWhere.getWhereClause().substring(57));
		}*/
		if (null!= (String) session.get("whereHql")) {
			sb.append((String) session.get("whereHql"));
		}
		HqlTool hqlTool = new HqlTool(sb.toString());
		if("instp".equals(projectType())) {
			sb.append(" group by " + hqlTool.getSelectClause() + ", mem.memberSn");
			sb.append(" order by uni.name asc, gra.applicantName asc, mem.memberSn asc");
		} else {
			sb.append(" group by " + hqlTool.getSelectClause());
			sb.append(" order by uni.name asc, gra.applicantName asc");
		}
		System.out.println(sb);
		List<Object[]> list = dao.query(sb.toString(), parMap);
		List dataList = new ArrayList();
		Map<Object, Object[]> lastData = new HashMap<Object, Object[]>();
		int index = 1;
		//若是基地项目则需要查询基地名称
		if("instp".equals(projectType())){
			for (Object[] o : list) {
				Object[] data = null;
				if (lastData.containsKey(o[6])) {
					data = lastData.get(o[6]);
					String univNames = data[8].toString(); 
					if(!univNames.contains((String)o[8])){
						univNames += "; " + o[8];
						data[8] = univNames;
					}
				}else{
					data = new Object[o.length];
					data[0] = index++;
					data[1] = o[0];//项目立项ID
					data[2] = o[3];//项目名称
					data[3] = o[4];//项目类别
					data[4] = o[5];//项目年度
					data[5] = o[6];//项目批准号
					data[6] = o[1];//依托高校
					data[7] = o[2];//基地名称
					data[8] = o[7];//项目负责人
					data[9] = o[8];//负责人所在高校
					if(null != o[10] && null != o[10]){
						data[10] = o[9] + ";" + o[10];//最终成果形式
					}else{
						if(null != o[9]){
							data[10] = o[9];
						}else{
							data[10] = o[10];
						}
					}
					data[11] = o[11];//批准经费（万元）
					data[12] = o[13];//计划完成时间
					data[13] = o[12] != null && (Integer)o[12] == 1 ? "在研" : o[12] != null && (Integer)o[12] == 2 ? "已结项" : o[12] != null && (Integer)o[12] == 3 ? "已中止" : o[12] != null && (Integer)o[12] == 4 ? "已撤项" : "";//项目状态
					dataList.add(data);
				}
				lastData.put(o[6], data);
			}
		}else {
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];
				data[0] = index++;
				data[1] = o[0];//项目立项ID
				data[2] = o[2];//项目名称
				data[3] = o[3];//项目类别
				data[4] = o[4];//项目年度
				data[5] = o[5];//项目批准号
				data[6] = o[1];//依托高校
				data[7] = o[6];//项目负责人
				if(null != o[7] && null != o[8]){//data[8] ： 最终成果形式
					data[8] = o[7] + "；" + o[8];
				}else{
					if(null != o[7]){
						data[8] = o[7];
					}else{
						data[8] = o[8];
					}
				}
				data[9] = o[9];//批准经费（万元）
				data[10] = o[11];//计划完成时间
				data[11] = o[10] != null && (Integer)o[10] == 1 ? "在研" : o[10] != null && (Integer)o[10] == 2 ? "已结项" : o[10] != null && (Integer)o[10] == 3 ? "已中止" : o[10] != null && (Integer)o[10] == 4 ? "已撤项" : "";//项目状态
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				data[9] = df.format(o[11]);//批准经费（万元）
				dataList.add(data);
			}
		}
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getProjectSubtype() {
		return projectSubtype;
	}
	public void setProjectSubtype(String projectSubtype) {
		this.projectSubtype = projectSubtype;
	}
	public String getDtypeNames() {
		return dtypeNames;
	}
	public void setDtypeNames(String dtypeNames) {
		this.dtypeNames = dtypeNames;
	}

	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getExpFlag() {
		return expFlag;
	}
	public void setExpFlag(int expFlag) {
		this.expFlag = expFlag;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public String getProjectOpinion() {
		return projectOpinion;
	}
	public void setProjectOpinion(String projectOpinion) {
		this.projectOpinion = projectOpinion;
	}
	public String getProjectOpinionFeedback() {
		return projectOpinionFeedback;
	}
	public void setProjectOpinionFeedback(String projectOpinionFeedback) {
		this.projectOpinionFeedback = projectOpinionFeedback;
	}
	public int getAddflag() {
		return addflag;
	}
	public void setAddflag(int addflag) {
		this.addflag = addflag;
	}
	public int getDeptInstFlag() {
		return deptInstFlag;
	}
	public void setDeptInstFlag(int deptInstFlag) {
		this.deptInstFlag = deptInstFlag;
	}
	public String getProjectTopic() {
		return projectTopic;
	}
	public void setProjectTopic(String projectTopic) {
		this.projectTopic = projectTopic;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public Date getEndStopWithdrawDate() {
		return endStopWithdrawDate;
	}
	public void setEndStopWithdrawDate(Date endStopWithdrawDate) {
		this.endStopWithdrawDate = endStopWithdrawDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getMidiStatus() {
		return midiStatus;
	}
	public void setMidiStatus(int midiStatus) {
		this.midiStatus = midiStatus;
	}
	public int getTimeFlag() {
		return timeFlag;
	}
	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}
	public int getGraFlag() {
		return graFlag;
	}
	public void setGraFlag(int graFlag) {
		this.graFlag = graFlag;
	}
	public int getGarApplicantSubmitStatus() {
		return garApplicantSubmitStatus;
	}
	public void setGarApplicantSubmitStatus(int garApplicantSubmitStatus) {
		this.garApplicantSubmitStatus = garApplicantSubmitStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ProjectGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = granted;
	}
	public Date getGraDate() {
		return graDate;
	}
	public void setGraDate(Date graDate) {
		this.graDate = graDate;
	}
	public int getGraResult() {
		return graResult;
	}
	public void setGraResult(int graResult) {
		this.graResult = graResult;
	}
	public int getGraImportedStatus() {
		return graImportedStatus;
	}
	public void setGraImportedStatus(int graImportedStatus) {
		this.graImportedStatus = graImportedStatus;
	}
	public String getGraImportedOpinion() {
		return graImportedOpinion;
	}
	public void setGraImportedOpinion(String graImportedOpinion) {
		this.graImportedOpinion = graImportedOpinion;
	}
	public String getGraOpinionFeedback() {
		return graOpinionFeedback;
	}
	public void setGraOpinionFeedback(String graOpinionFeedback) {
		this.graOpinionFeedback = graOpinionFeedback;
	}
	public String getGraId() {
		return graId;
	}
	public void setGraId(String graId) {
		this.graId = graId;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}