package csdc.action.project.key;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.project.ApplicationApplyAction;
import csdc.action.project.ProjectBaseAction;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.KeyApplication;
import csdc.bean.KeyTopic;
import csdc.bean.ProjectGranted;
import csdc.service.IMessageAuxiliaryService;
import csdc.tool.HqlTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

@SuppressWarnings("unchecked")
public class TopicSelectionApplyAction extends ProjectBaseAction{
	
	private static final long serialVersionUID = -700148736686965249L;
	
	private static final String HQL1 = "select tops.id, tops.name, tops.topicSource, tops.applicantId, tops.applicantName, uni.id, uni.name, tops.year, " +
			"tops.finalAuditResult, tops.status, tops.finalAuditStatus, expUni.id ";
			
	private static final String HQL2 = " from KeyTopic tops left outer join tops.university uni left outer join tops.expUniversity expUni where 1=1 ";
	
	private static final String PAGE_NAME = "keyTopicSelectionPage";//列表页面名称
	private static final String PAGE_BUFFER_ID = "tops.id";//缓存id
	private static final String PROJECT_TYPE = "key";
	private static final String BUISINESS_TYPE = "046";
	private static final int CHECK_TOPIC_SELECTION_FLAG = 26;
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	private int topsResult;//选题结果：1不同意；2同意
	protected Integer year;//项目年度
	private Date topsDate;//选题申请时间
	private Integer topsYear;//年检年度
	private String universityName;//依托高校名称
	private String projectId;//依托项目id
	private int projectTypeFlag;//项目类型1：一般项目2：基地
	private String projectName;//依托项目名称
	private int topsImportedStatus;//选题结果录入状态
	private int topsApplicantSubmitStatus;//专家提交状态
	private int topsUniversitySubmitStatus;//高校提交状态
	private String topicName,englishName,applicantName,provinceName;//高级检索条件
	private int isAdopt,startYear,endYear,auditStatus;//高级检索条件
	private Integer topicSource;//高级检索条件
	private Date startDate,endDate;////高级检索条件
	private List<String> topsIds;// 多个实体ID
	private int appFlag;//走流程标识1：专家2：高校
	
	private static final String[] CCOLUMNNAME = {
		"课题名称",
		"项目课题来源",
		"选题年度",
		"提交状态",
		"审核状态",
		"审核状态",
		"审核状态",
		"选题状态",
		"提交时间",
		"审核时间",
		"审核时间",
		"审核时间",
		"选题时间"
	};
	
	private static final String[] COLUMN = {
		"tops.name",
		"tops.topicSource",
		"tops.year desc",
		"tops.applicantSubmitStatus",
		"tops.deptInstAuditStatus, tops.deptInstAuditResult",
		"tops.universityAuditStatus, tops.universityAuditResult",
		"tops.provinceAuditStatus, tops.provinceAuditResult",
		"tops.finalAuditStatus, tops.finalAuditResult",
		"tops.applicantSubmitDate desc",
		"tops.deptInstAuditDate desc",
		"tops.universityAuditDate desc",
		"tops.provinceAuditDate desc",
		"tops.finalAuditDate desc"
	};//排序列
	public String pageName() {
		return TopicSelectionApplyAction.PAGE_NAME;
	}
	public String pageBufferId() {
		return TopicSelectionApplyAction.PAGE_BUFFER_ID;
	}
	public String[] columnName() {
		return TopicSelectionApplyAction.CCOLUMNNAME;
	}
	public String[] column() {
		return TopicSelectionApplyAction.COLUMN;
	}
	public String businessType() {
		return TopicSelectionApplyAction.BUISINESS_TYPE;
	}
	public int checkApplicationFlag() {
		return TopicSelectionApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag() {
		return TopicSelectionApplyAction.CHECK_GRANTED_FLAG;
	}
	public int checkTopicSelectionFlag() {
		return TopicSelectionApplyAction.CHECK_TOPIC_SELECTION_FLAG;
	}
	public String projectType() {
		return TopicSelectionApplyAction.PROJECT_TYPE;
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
		if (null == session.get("topsAssistMap") && cloumn != 2 && cloumn != 3 ) {
			cloumn =1;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 ||cloumn == 1) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "tops.id");
		} else {
			jsonMap = (Map) session.get("topsAssistMap");
		}
		session.put("topsAssistMap", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings("rawtypes")
	public Object[] simpleSearchCondition() {
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		int columnLabel = 0;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(this.keyService.getKeyTopicSimpleSearchHQLWordAdd(accountType));
		hql.append(HQL2);
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.keyService.getKeyTopicSimpleSearchHQL(searchType));
		}
		hql.append(this.keyService.getKeyTopicSimpleSearchHQLAdd(accountType));
		session.put("topicSelectionMap", map);
		//处理查询范围
		String addHql = this.keyService.topicSelectionInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("topicSelectionMap");
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 9;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 10;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 11;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 12;
		}else{
			columnLabel =  0;
		}
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
			null
		};
	}
	
	//高级检索
	@SuppressWarnings("rawtypes")
	public Object[] advSearchCondition(){
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(this.keyService.getKeyTopicSimpleSearchHQLWordAdd(accountType));
		hql.append(HQL2);
		if(topicName!=null && !topicName.isEmpty()){
			topicName = topicName.toLowerCase();
			hql.append(" and LOWER(tops.name) like :topicName");
			map.put("topicName", "%" + topicName + "%");
		}
		if(englishName!=null && !englishName.isEmpty()){
			topicName = topicName.toLowerCase();
			hql.append(" and LOWER(tops.englishName) like :englishName");
			map.put("englishName", "%" + englishName + "%");
		}
		if(accountType.compareTo(AccountType.DEPARTMENT) < 0){
			if(topicSource != null && topicSource != -1){
				hql.append(" and tops.topicSource=:topicSource");
				map.put("topicSource", topicSource);
			}
		}
		if(startYear!=-1){
			hql.append(" and tops.year>=:startYear");
			map.put("startYear", startYear);
		}
		if(endYear!=-1){
			hql.append(" and tops.year<=:endYear");
			map.put("endYear", endYear);
		}
		if(accountType.compareTo(AccountType.EXPERT) < 0){
			if(applicantName!=null && !applicantName.isEmpty()){
				applicantName = applicantName.toLowerCase();
				hql.append(" and LOWER(tops.applicantName) like :applicantName");
				map.put("applicantName", "%" + applicantName + "%");
			}
		}
		if(accountType.compareTo(AccountType.MINISTRY_UNIVERSITY) < 0){
			if(universityName!=null && !universityName.isEmpty()){
				universityName = universityName.toLowerCase();
				hql.append(" and LOWER(uni.name) like :universityName");
				map.put("universityName", "%" + universityName + "%");
			}
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(tops.provinceName) like :provinceName");
			map.put("provinceName", "%" + provinceName + "%");
		}
		int resultStatus,saveStatus;
		if(auditStatus!=-1){
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			map.put("auditStatus",  saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){ 
				hql.append("  and tops.deptInstAuditStatus =:auditStatus and tops.deptInstAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append("  and tops.universityAuditStatus =:auditStatus and tops.universityAuditResult =:auditResult and tops.applicantName is not null");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append("  and tops.provinceAuditStatus =:auditStatus and tops.provinceAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and tops.finalAuditStatus =:auditStatus and tops.finalAuditResult =:auditResult");
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				hql.append(" and tops.deptInstAuditDate is not null and to_char(tops.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and tops.universityAuditDate is not null and to_char(tops.universityAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and tops.provinceAuditDate is not null and to_char(tops.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and tops.finalAuditDate is not null and to_char(tops.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				if(startDate == null){
					hql.append(" and tops.deptInstAuditDate is not null ");
				}
				hql.append(" and to_char(tops.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(startDate == null){
					hql.append(" and tops.universityAuditDate is not null ");
				}
				hql.append(" and to_char(tops.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				if(startDate == null){
					hql.append(" and tops.provinceAuditDate is not null ");
				}
				hql.append(" and to_char(tops.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				if(startDate == null){
					hql.append(" and tops.finalAuditDate is not null ");
				}
				hql.append(" and to_char(tops.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isAdopt != -1){
			if(isAdopt == 0){
				hql.append(" and tops.finalAuditStatus != 3 ");
			}else{
				hql.append(" and tops.finalAuditStatus = 3 and tops.finalAuditResult =:isAdopt ");
				map.put("isAdopt", isAdopt);
			}
		}
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" and (tops.status >= 1 or tops.createMode = 1 or tops.createMode = 2) ");
			if(topsApplicantSubmitStatus > 0){
				map.put("submitStatus",  topsApplicantSubmitStatus);
				hql.append(" and tops.applicantSubmitStatus =:submitStatus");
			}
			if(topsUniversitySubmitStatus > 0){
				map.put("submitStatus",  topsUniversitySubmitStatus);
				hql.append(" and tops.universitySubmitStatus =:submitStatus");
			}
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql.append(" and (tops.status >= 2 or tops.createMode = 1 or tops.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" and (tops.status >= 3 or tops.createMode = 1 or tops.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql.append(" and (tops.status >= 4 or tops.createMode = 1 or tops.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql.append(" and (tops.status >= 5 or tops.createMode = 1 or tops.createMode = 2) ");
		}
		session.put("topicSelectionMap", map);
		//处理查询范围
		String addHql = this.keyService.topicSelectionInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("topicSelectionMap");
		return new Object[]{
			hql.toString(),
			map,
			5,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if (topicName != null && !topicName.isEmpty()) {
			searchQuery.put("topicName", topicName);
		}
		if(englishName!=null && !englishName.isEmpty()){
			searchQuery.put("englishName", englishName);
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			searchQuery.put("provinceName", provinceName);
		}
		
		if(accountType.compareTo(AccountType.DEPARTMENT) < 0 &&  topicSource!= null && topicSource != -1){
			searchQuery.put("topicSource", topicSource);
		}
		if(startYear!=-1){
			searchQuery.put("startYear", startYear);
		}
		if(endYear!=-1){
			searchQuery.put("endYear", endYear);
		}
		if(accountType.compareTo(AccountType.EXPERT) < 0 && applicantName!=null && !applicantName.isEmpty()){
			searchQuery.put("applicantName", applicantName);	
		}
		if(accountType.compareTo(AccountType.MINISTRY_UNIVERSITY) < 0 && universityName!=null && !universityName.isEmpty()){
			searchQuery.put("universityName", universityName);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
		if (auditStatus != -1) {
			searchQuery.put("auditStatus", auditStatus);
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isAdopt != -1){
			searchQuery.put("isAdopt",  isAdopt);
		}
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			if(topsApplicantSubmitStatus > 0){
				searchQuery.put("submitStatus",  topsApplicantSubmitStatus);
			}
			if(topsUniversitySubmitStatus > 0){
				searchQuery.put("submitStatus",  topsUniversitySubmitStatus);
			}
		}
	}
	
	/**
	 * 选题申请添加预处理
	 */
	public String toAdd(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		setTopsDate(new Date());
		topsYear = this.projectService.getBusinessDefaultYear(businessType(), "businessType");
		if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校推荐
			topicSelection = new KeyTopic();
			Agency uni = (Agency) this.dao.query(Agency.class, loginer.getCurrentBelongUnitId());
			topicSelection.setUniversity(uni);
			setAppFlag(2);
		}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){
			setAppFlag(1);
		}else{
			return INPUT;
		}
		year = this.keyService.getBusinessDefaultYear(businessType(), "businessType");
		return SUCCESS;
	}
	
	/**
	 * 选题申请添加
	 */
	@SuppressWarnings("rawtypes")
	public String add(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		if(topicSelection != null){
			if(topicSelection.getName() != null && !topicSelection.getName().isEmpty()){//中文名
				topicSelection.setName(topicSelection.getName().trim());
			}
			if(topicSelection.getEnglishName() != null && !topicSelection.getEnglishName().isEmpty()){//英文名
				topicSelection.setEnglishName(topicSelection.getEnglishName().trim());
			}
			if(topicSelection.getSummary() != null && !topicSelection.getSummary().isEmpty()){//简要论证
				topicSelection.setSummary(("A"+topicSelection.getSummary()).trim().substring(1));
			}else{
				topicSelection.setSummary(null);
			}
			topicSelection.setYear(topsYear);
			Date submitDate = this.projectService.setDateHhmmss(topsDate);//设置指定日期时分秒为当前系统时间时分秒
			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){//专家推荐
				topicSelection.setTopicSource(2);
				topicSelection.setApplicantType((accountType.equals(AccountType.TEACHER) ) ? 1 : 2);
				topicSelection.setApplicantName(loginer.getCurrentPersonName());
				topicSelection.setApplicantId(loginer.getPerson().getId());//获得人员id
				topicSelection = this.keyService.setApplicantIdFromPerson(topicSelection);//处理专家所属高校、院系、研究机构等信，ApplicantId为教师、专家id
				topicSelection.setApplicantId(loginer.getPerson().getId());//重新设置该字段为人员id
				Map auditMap = new HashMap();
				AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, topsApplicantSubmitStatus, null);
				auditMap.put("auditInfo",auditInfo);
				auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
				topicSelection.edit(auditMap);//保存操作结果
				topicSelection.setApplicantSubmitDate(submitDate);
				/* 以下代码为跳过部门审核*/
				if(topsApplicantSubmitStatus == 3){//提交申请
					AuditInfo auditInfoDept = new AuditInfo();
					auditInfoDept.setAuditResult(2);
					auditInfoDept.setAuditStatus(3);
					auditMap.put("auditInfo",auditInfoDept);
					topicSelection.edit(auditMap);//部门审核通过
				}
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校推荐
				topicSelection.setTopicSource(1);
				Agency uni = (Agency) this.dao.query(Agency.class, loginer.getCurrentBelongUnitId());
				topicSelection.setUniversity(uni);
				topicSelection.setProvinceName(uni != null ? uni.getProvince().getName() : null);
				topicSelection.setProvince(uni != null ? uni.getProvince() : null);
				ProjectGranted project= (ProjectGranted)this.dao.query(ProjectGranted.class, projectId);
				topicSelection.setProject(project);
				topicSelection.setStatus(3);//直接进入高校审核阶段
				topicSelection.setUniversitySubmitDate(submitDate);
				topicSelection.setUniversitySubmitStatus(topsUniversitySubmitStatus);
				if(topsUniversitySubmitStatus == 3){//提交申请
					if(uni.getType() == 3){//部属高校
						topicSelection.setStatus(5);
					}else{//地方高校
						topicSelection.setStatus(4);
					}
				}
			}else{
				return INPUT;
			}
		}
		topicSelection.setCreateMode(0);
		topicSelection.setCreateDate(new Date());
		topsId = dao.add(topicSelection);
		return SUCCESS;
	}
	
	public void validateAdd(){
//		//校验业务设置状态
//		topsStatus = this.keyService.getBusinessStatus(businessType());
//		if (topsStatus == 0){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
//		}
//		//校验业务时间是否有效
//		int accountType = loginer.getCurrentType();
//		Date date = new Date();
//		deadline  = this.keyService.checkIfTimeValidate(accountType, businessType());
//		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
//		}
		topicSelection.setYear(year);
		validateEdit();
	}
	
	/**
	 * 选题申请修改预处理
	 */
	public String toModify(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		if(!this.keyService.checkIfUnderControl(loginer, topsId.trim(), checkTopicSelectionFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		topicSelection = (KeyTopic)dao.query(KeyTopic.class, topsId);//entityId为项目申请id
		if(topicSelection==null){
			addActionError("选题申请对象为空");
			return ERROR;
		}
		if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校申请选题
			ProjectGranted project = (ProjectGranted) this.dao.query(ProjectGranted.class, topicSelection.getProject().getId());
			projectTypeFlag = (project.getProjectType().equals("general")) ? 1 : (project.getProjectType().equals("instp") ? 3 : 0);
			projectId = project.getId();
			projectName = project.getName();
			setAppFlag(2);
		}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){//专家申请选题
			setAppFlag(1);
		}else{
			return INPUT;
		}
		topsId = topicSelection.getId(); 
		topsDate = (topicSelection.getApplicantSubmitDate() == null) ? topicSelection.getUniversitySubmitDate() : topicSelection.getApplicantSubmitDate();
		topsYear = this.projectService.getBusinessDefaultYear(businessType(), "businessType");
		topsApplicantSubmitStatus = topicSelection.getApplicantSubmitStatus();
		topsUniversitySubmitStatus = topicSelection.getUniversitySubmitStatus();
		return SUCCESS;
	}
	
	/**
	 * 选题申请修改
	 */
	@SuppressWarnings("rawtypes")
	public String modify(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		KeyTopic oldTopicSelection = (KeyTopic) this.dao.query(KeyTopic.class, topsId);
		if(topicSelection != null){
			if(topicSelection.getName() != null && !topicSelection.getName().isEmpty()){//中文名
				oldTopicSelection.setName(topicSelection.getName().trim());
			}
			if(topicSelection.getEnglishName() != null && !topicSelection.getEnglishName().isEmpty()){//英文名
				oldTopicSelection.setEnglishName(topicSelection.getEnglishName().trim());
			}
			if(topicSelection.getSummary() != null && !topicSelection.getSummary().isEmpty()){//简要论证
				oldTopicSelection.setSummary(("A"+topicSelection.getSummary()).trim().substring(1));
			}else{
				oldTopicSelection.setSummary(null);
			}
			oldTopicSelection.setYear(topsYear);
			Date submitDate = this.projectService.setDateHhmmss(topsDate);
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校推荐
				oldTopicSelection.setUniversitySubmitDate(submitDate);
				ProjectGranted project= (ProjectGranted)this.dao.query(ProjectGranted.class, projectId);
				oldTopicSelection.setProject(project);
				oldTopicSelection.setUniversitySubmitStatus(topsUniversitySubmitStatus);
				int isSubUni = this.keyService.isSubordinateUniversityTopicSelection(topsId);
				if(topsUniversitySubmitStatus == 3){//提交申请
					oldTopicSelection.setStatus((isSubUni == 1) ? 5 : 4);
				}
			}else{//专家推荐
				oldTopicSelection.setApplicantSubmitDate(submitDate);
				Map auditMap = new HashMap();
				AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, topsApplicantSubmitStatus, null);
				auditMap.put("auditInfo",auditInfo);
				auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
				topicSelection.edit(auditMap);//保存操作结果
				/* 以下代码为跳过部门审核*/
				if(topsApplicantSubmitStatus == 3){//提交申请
					AuditInfo auditInfoDept = new AuditInfo();
					auditInfoDept.setAuditResult(2);
					auditInfoDept.setAuditStatus(3);
					auditMap.put("auditInfo",auditInfoDept);
					oldTopicSelection.edit(auditMap);//部门审核通过
				}
				
			}
		}
		dao.modify(oldTopicSelection);
		return SUCCESS;
	}
	
	
	public void validateModify(){
		validateEdit();
	}
	
	/**
	 * 提交中检申请
	 * @author 肖雅
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String submit(){
		if(!this.keyService.checkIfUnderControl(loginer, topsId.trim(), checkTopicSelectionFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		KeyTopic topicSelection = (KeyTopic)this.dao.query(KeyTopic.class, topsId);
		if(appFlag == 1){//专家申请提交
			if(topicSelection.getStatus() > 1){//未提交的申请才可提交
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				return INPUT;
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			topicSelection.submit(auditMap);//提交操作结果
			/* 以下代码为跳过部门审核*/
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			topicSelection.edit(auditMap);//部门审核通过
			/* 结束 */
		}else if(appFlag == 2){//高校申请提交
			if(topicSelection.getStatus() > 3){//未提交的申请才可提交
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				return INPUT;
			}
			int isSubUni = this.keyService.isSubordinateUniversityTopicSelection(topsId);
			topicSelection.setStatus((isSubUni == 1) ? 5 : 4);
			topicSelection.setUniversitySubmitStatus(3);
		}else{
			return INPUT;
		}
		dao.modify(topicSelection);
		return SUCCESS;
	}
	
	public void validateSubmit(){
		validateEdit();
	}
	
	/**
	 * 选题录入添加预处理
	 */
	public String toAddResult(){
		setTopsDate(new Date());
		return SUCCESS;
	}
	
	/**
	 * 选题录入添加
	 */
	@Transactional
	public String addResult(){
		if(topicSelection != null){
			if(topicSelection.getName() != null && !topicSelection.getName().isEmpty()){//中文名
				topicSelection.setName(topicSelection.getName().trim());
			}
			if(topicSelection.getEnglishName() != null && !topicSelection.getEnglishName().isEmpty()){//英文名
				topicSelection.setEnglishName(topicSelection.getEnglishName().trim());
			}
			if(topicSelection.getSummary() != null && !topicSelection.getSummary().isEmpty()){//简要论证
				topicSelection.setSummary(("A"+topicSelection.getSummary()).trim().substring(1));
			}else{
				topicSelection.setSummary(null);
			}
			if(topicSelection.getYear() != -1){//选题年度
				topicSelection.setYear(topicSelection.getYear());
			}
			if(topicSelection.getUniversity().getId() != null && !topicSelection.getUniversity().getId().isEmpty()){//依托高校id
				Agency university = (Agency) this.dao.query(Agency.class, topicSelection.getUniversity().getId());
				ProjectGranted project= (ProjectGranted)this.dao.query(ProjectGranted.class, projectId);
				topicSelection.setUniversity(university);
				topicSelection.setProvinceName(university != null ? university.getProvince().getName() : null);
				topicSelection.setProvince(university != null ? university.getProvince() : null);
				topicSelection.setProject(project);
			}
			if(topicSelection.getApplicantType() != -1){//申请者类型
				topicSelection.setApplicantType(topicSelection.getApplicantType());
			}
			if(topicSelection.getApplicantId() != null && !topicSelection.getApplicantId().isEmpty()){//申请者id
				topicSelection = this.keyService.setApplicantIdFromTopicSelection(topicSelection, topicSelection);
			}
			if(topicSelection.getApplicantName() != null && !topicSelection.getApplicantName().isEmpty()){//申请者姓名
				topicSelection.setApplicantName(topicSelection.getApplicantName().trim());
			}
			if(null !=topicSelection.getTopicSource() && topicSelection.getTopicSource() != -1){//选题来源
				topicSelection.setTopicSource(topicSelection.getTopicSource());
				if(topicSelection.getTopicSource() == 1){
					topicSelection.setApplicantId(null);
					topicSelection.setApplicantName(null);
				}else if(topicSelection.getTopicSource() == 2){
					topicSelection.setUniversity(null);
					topicSelection.setProject(null);
				}else{
					topicSelection.setUniversity(null);
					topicSelection.setProject(null);
					topicSelection.setApplicantId(null);
					topicSelection.setApplicantName(null);
				}
			}
		}
		if(topsResult != -1){//选题采纳结果
			topicSelection.setFinalAuditResult(topsResult);
		}
		Date submitDate = this.projectService.setDateHhmmss(topsDate);
		topicSelection.setFinalAuditDate(submitDate);
		topicSelection.setCreateMode(1);
		topicSelection.setCreateDate(new Date());
		topicSelection.setFinalAuditStatus(3);
		topsId = dao.add(topicSelection);
		return SUCCESS;
	}
	
	public void validateAddResult(){
		this.validateResult();
	}
	
	/**
	 * 准备录入修改时校验
	 */
	public void validateToModifyResult(){
		if (topsId == null || topsId.isEmpty()) {//选题id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
	}
	
	/**
	 * 录入修改前准备
	 */
	public String toModifyResult(){
		if(!this.keyService.checkIfUnderControl(loginer, topsId.trim(), checkTopicSelectionFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return ERROR;
		}
		topicSelection = (KeyTopic)dao.query(KeyTopic.class, topsId);//entityId为项目申请id
		topicSelection = this.keyService.setApplicantIdFromPerson(topicSelection);
		if(topicSelection==null){
			addActionError("选题申请对象为空");
			return ERROR;
		}
		if(null != topicSelection.getUniversity() && null != topicSelection.getUniversity().getId() && !topicSelection.getUniversity().getId().isEmpty()){
			Agency university = (Agency) this.dao.query(Agency.class, topicSelection.getUniversity().getId());
			universityName = (null != university)? university.getName() : "";
			ProjectGranted project = (ProjectGranted) this.dao.query(ProjectGranted.class, topicSelection.getProject().getId());
			projectTypeFlag = (project.getProjectType().equals("general")) ? 1 : (project.getProjectType().equals("instp") ? 2 : 0);
			projectId = project.getId();
			projectName = project.getName();
		}
		topsId = topicSelection.getId(); 
		topsDate = topicSelection.getFinalAuditDate();
		topsResult = topicSelection.getFinalAuditResult();
		topsImportedStatus = topicSelection.getFinalAuditStatus();
		return SUCCESS;
	}
	
	/**
	 * 选题录入修改
	 */
	@Transactional
	public String modifyResult(){
		KeyTopic oldTopicSelection = (KeyTopic) this.dao.query(KeyTopic.class, topsId);
		if(topicSelection != null){
			if(topicSelection.getName() != null){//中文名
				oldTopicSelection.setName(topicSelection.getName().trim());
			}
			if(topicSelection.getEnglishName() != null){//英文名
				oldTopicSelection.setEnglishName(topicSelection.getEnglishName().trim());
			}
			if(topicSelection.getSummary() != null){//简要论证
				oldTopicSelection.setSummary(("A"+topicSelection.getSummary()).trim().substring(1));
			}else{
				oldTopicSelection.setSummary(null);
			}
			if(topicSelection.getYear() != -1){//选题年度
				oldTopicSelection.setYear(topicSelection.getYear());
			}
			if(topicSelection.getUniversity().getId() != null){//申请者id
				Agency university = (Agency) this.dao.query(Agency.class, topicSelection.getUniversity().getId());
				oldTopicSelection.setUniversity(university);
				oldTopicSelection.setProvinceName(university != null ? university.getProvince().getName() : null);
				oldTopicSelection.setProvince(university != null ? university.getProvince() : null);
				ProjectGranted project = (ProjectGranted) this.dao.query(ProjectGranted.class, projectId);
				oldTopicSelection.setProject(project);
			}
			if(topicSelection.getApplicantType() != -1){//申请者类型
				oldTopicSelection.setApplicantType(topicSelection.getApplicantType());
			}
			if(topicSelection.getApplicantId() != null){//申请者id
				oldTopicSelection = this.keyService.setApplicantIdFromTopicSelection(oldTopicSelection, topicSelection);
			}
			if(topicSelection.getApplicantName() != null){//申请者姓名
				oldTopicSelection.setApplicantName(topicSelection.getApplicantName().trim());
			}
			if(null !=topicSelection.getTopicSource() && topicSelection.getTopicSource() != -1){//选题来源
				oldTopicSelection.setTopicSource(topicSelection.getTopicSource());
				if(topicSelection.getTopicSource() == 1){
					oldTopicSelection.setApplicantId(null);
					oldTopicSelection.setApplicantName(null);
				}else if(topicSelection.getTopicSource() == 2){
					oldTopicSelection.setUniversity(null);
				}else{
					oldTopicSelection.setUniversity(null);
					oldTopicSelection.setApplicantId(null);
					oldTopicSelection.setApplicantName(null);
				}
			}
		}
		if(topsResult != -1){//选题采纳结果
			oldTopicSelection.setFinalAuditResult(topsResult);
		}
		Date submitDate = this.projectService.setDateHhmmss(topsDate);
		oldTopicSelection.setApplicantSubmitDate(submitDate);
		oldTopicSelection.setFinalAuditDate(submitDate);
		dao.modify(oldTopicSelection);
		return SUCCESS;
	}
	
	public void validateModifyResult(){
		this.validateResult();
	}
	
	/**
	 * 编辑选题申请走流程校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	public void validateEdit(){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String info ="";
		if(topicSelection == null){
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			if(topicSelection.getName() == null || topicSelection.getName().trim().isEmpty() ){
				this.addFieldError("topicSelection.name", ProjectInfo.ERROR_TOPIC_NAME_NULL);
				info += ProjectInfo.ERROR_TOPIC_NAME_NULL;
			}else if(topicSelection.getName().length()>50){
				this.addFieldError("topicSelection.name", ProjectInfo.ERROR_TOPIC_NAME_OUT);
				info += ProjectInfo.ERROR_TOPIC_NAME_OUT;
			}
			if(topicSelection.getEnglishName() != null && topicSelection.getEnglishName().length()>200){
				this.addFieldError("topicSelection.englishName", ProjectInfo.ERROR_PROJECT_ENG_NAME_OUT);
				info += ProjectInfo.ERROR_PROJECT_ENG_NAME_OUT;
			}
			if(topsYear<=0 || topsYear>=10000){ 
				this.addFieldError("topicSelection.year", ProjectInfo.ERROR_TOPIC_YEAR_WRONG);
				info += ProjectInfo.ERROR_TOPIC_YEAR_WRONG;
			}
			if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(null == projectId || projectId.isEmpty()){
					this.addFieldError("topicSelection.project.id", ProjectInfo.ERROR_TOPIC_PROJECT_NULL);
					info += ProjectInfo.ERROR_TOPIC_PROJECT_NULL;
				}
			}
			if(topicSelection.getSummary() != null && topicSelection.getSummary().length()>300){
				this.addFieldError("topicSelection.summary", ProjectInfo.ERROR_TOPIC_SUMMARY_OUT);
				info += ProjectInfo.ERROR_TOPIC_SUMMARY_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加录入时校验
	 */
	public void validateResult(){
		if(topicSelection == null){
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
		}else{
			if(topicSelection.getName() == null || topicSelection.getName().trim().isEmpty() ){
				this.addFieldError("topicSelection.name", ProjectInfo.ERROR_TOPIC_NAME_NULL);
			}else if(topicSelection.getName().length()>50){
				this.addFieldError("topicSelection.name", ProjectInfo.ERROR_TOPIC_NAME_OUT);
			}
			if(topicSelection.getEnglishName() != null && topicSelection.getEnglishName().length()>200){
				this.addFieldError("topicSelection.englishName", ProjectInfo.ERROR_PROJECT_ENG_NAME_OUT);
			}
			if(topicSelection.getYear()<=0 || topicSelection.getYear()>=10000){
				this.addFieldError("topicSelection.year", ProjectInfo.ERROR_TOPIC_YEAR_WRONG);
			}
			if(topicSelection.getTopicSource() == -1){
				this.addFieldError("topicSelection.topicSource", ProjectInfo.ERROR_TOPIC_SOURCE_NULL);
			}else if(topicSelection.getTopicSource() == 1){
				if(null == topicSelection.getUniversity() || topicSelection.getUniversity().getId() == null){
					this.addFieldError("topicSelection.university.id", ProjectInfo.ERROR_TOPIC_UNIVERSITY_NULL);
				}
			}else if(topicSelection.getTopicSource() == 2){
				if(topicSelection.getApplicantType() == null){
					this.addFieldError("topicSelection.applcantType", ProjectInfo.ERROR_TOPIC_APPLICANT_TYPE_NULL);
				}else if(topicSelection.getApplicantName() == null){
					this.addFieldError("topicSelection.applcantName", ProjectInfo.ERROR_TOPIC_APPLICANT_NAME_NULL);
				}else if(topicSelection.getApplicantId() == null){
					this.addFieldError("topicSelection.applcantId", ProjectInfo.ERROR_TOPIC_APPLICANT_ID_NULL);
				}
			}
			if(topicSelection.getSummary() != null && topicSelection.getSummary().length()>300){
				this.addFieldError("topicSelection.summary", ProjectInfo.ERROR_TOPIC_SUMMARY_OUT);
			}
		}
	}
	
	/**
	 * 删除选题
	 * @author 肖雅
	 */
	@Transactional
	public String delete(){
		for(int i = 0; i < topsIds.size(); i++){
			KeyTopic topicSelection = (KeyTopic)(dao.query(KeyTopic.class, topsIds.get(i)));
			if(topicSelection == null){//校验选题是否存在
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NOT_EXIST);
				return INPUT;
			}
			//判断选题是否有项目记录
			List<KeyApplication> list = dao.query("select app from KeyApplication app where app.topicSelection.id=?", topsIds.get(i));
			if(null != list && !list.isEmpty()){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_TOPIC_PROJECT_EXIST);
				return INPUT;
			}
			//判断管理范围
			if(!this.projectService.checkIfUnderControl(loginer, topsIds.get(i).trim(), checkTopicSelectionFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
			this.dao.delete(topicSelection);
		}
		return SUCCESS;
	}
	
	public String toView() {
		//业务设置状态1：业务激活2：业务停止
		topsStatus = this.keyService.getBusinessStatus(businessType());
		if(null != topsId){
			if(!this.projectService.checkIfUnderControl(loginer, topsId.trim(), checkTopicSelectionFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return ERROR;
			}
		}
		topicSelection = (KeyTopic) this.dao.query(KeyTopic.class, topsId);
		if(null != topicSelection.getUniversity()){//高校推荐选题
			setAppFlag(2);
		}else if(null != topicSelection.getApplicantId()){//专家推荐选题
			setAppFlag(1);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String viewTopic(){
		//选题信息
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		if(null != topsId){//重大攻关项目选题
			topicSelection = (KeyTopic) this.dao.query(KeyTopic.class, topsId);
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
				setAppFlag(2);
			}else{
				setAppFlag(1);
			}
			jsonMap.put("accountType", accountType);//账号类型
			jsonMap.put("selectedTab", selectedTab);//查看标签
			jsonMap.put("appFlag", appFlag);//走流程类型
			jsonMap.put("universityName", universityName);//选题所属高校名称
			jsonMap.put("universityId", universityId);//选题所属高校id
			jsonMap.put("utype", utype);//选题所属高校是否是部属高校
			jsonMap.put("projectName", projectName);//选题所属项目名称
			jsonMap.put("projectId", projectId);//选题所属项目id
			jsonMap.put("projectTypeId", projectTypeId);//选题所属项目类型
			//选题业务信息
			deadline = this.projectService.checkIfTimeValidate(accountType, businessType());
			List topsInfo = new ArrayList();
			topsInfo.add(0, businessType());
			topsInfo.add(1,deadline);
			jsonMap.put("topicSelection", topicSelection);//选题申请
			jsonMap.put("topsInfo", topsInfo);//选题申请业务信息
			jsonMap.put("application", null);//项目申请
		}
		session.put("projectViewJson", this.jsonMap);
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public void doWithViewTopic(String topsId){
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		if(null != topsId){//重大攻关项目选题
			topicSelection = (KeyTopic) this.dao.query(KeyTopic.class, topsId);
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
				setAppFlag(2);
			}else{
				setAppFlag(1);
			}
			jsonMap.put("accountType", accountType);//账号类型
			jsonMap.put("appFlag", appFlag);//走流程类型
			jsonMap.put("universityName", universityName);//选题所属高校名称
			jsonMap.put("universityId", universityId);//选题所属高校id
			jsonMap.put("utype", utype);//选题所属高校是否是部属高校
			jsonMap.put("projectName", projectName);//选题所属项目名称
			jsonMap.put("projectId", projectId);//选题所属项目id
			jsonMap.put("projectTypeId", projectTypeId);//选题所属项目类型
			//选题业务信息
			deadline = this.projectService.checkIfTimeValidate(accountType, businessType());
			List topsInfo = new ArrayList();
			topsInfo.add(0, businessType());
			topsInfo.add(1,deadline);
			jsonMap.put("topicSelection", topicSelection);//选题申请
			jsonMap.put("topsInfo", topsInfo);//选题申请业务信息
			jsonMap.put("application", null);//项目申请
		}
	}
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopsResult(int topsResult) {
		this.topsResult = topsResult;
	}
	public int getTopsResult() {
		return topsResult;
	}
	public void setTopsDate(Date topsDate) {
		this.topsDate = topsDate;
	}
	public Date getTopsDate() {
		return topsDate;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public Integer getTopsYear() {
		return topsYear;
	}
	public void setTopsYear(Integer topsYear) {
		this.topsYear = topsYear;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectId() {
		return projectId;
	}
	public int getProjectTypeFlag() {
		return projectTypeFlag;
	}
	public void setProjectTypeFlag(int projectTypeFlag) {
		this.projectTypeFlag = projectTypeFlag;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getTopsImportedStatus() {
		return topsImportedStatus;
	}
	public void setTopsImportedStatus(int topsImportedStatus) {
		this.topsImportedStatus = topsImportedStatus;
	}
	public int getTopsApplicantSubmitStatus() {
		return topsApplicantSubmitStatus;
	}
	public void setTopsApplicantSubmitStatus(int topsApplicantSubmitStatus) {
		this.topsApplicantSubmitStatus = topsApplicantSubmitStatus;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public Integer getTopicSource() {
		return topicSource;
	}
	public void setTopicSource(Integer topicSource) {
		this.topicSource = topicSource;
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
	public int getIsAdopt() {
		return isAdopt;
	}
	public void setIsAdopt(int isAdopt) {
		this.isAdopt = isAdopt;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public void setTopsUniversitySubmitStatus(int topsUniversitySubmitStatus) {
		this.topsUniversitySubmitStatus = topsUniversitySubmitStatus;
	}
	public int getTopsUniversitySubmitStatus() {
		return topsUniversitySubmitStatus;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public List<String> getTopsIds() {
		return topsIds;
	}
	public void setTopsIds(List<String> topsIds) {
		this.topsIds = topsIds;
	}
	public void setAppFlag(int appFlag) {
		this.appFlag = appFlag;
	}
	public int getAppFlag() {
		return appFlag;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	

}
