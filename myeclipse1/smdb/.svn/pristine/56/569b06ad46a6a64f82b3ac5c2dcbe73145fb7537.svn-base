package csdc.action.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectVariation;
import csdc.bean.SystemOption;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
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
 * 变更项目父类管理
 * 定义了子类需要实现的抽象方法并实现了所有变更申请共用的相关方法
 * @author 余潜玉
 */
public abstract class VariationApplyAction extends ProjectBaseAction {

	private static final long serialVersionUID = 6501329881611241590L;
	protected static final String HQL1 = "select app.id, gra.id, gra.name, gra.applicantId, gra.applicantName, " +
		"uni.id, uni.name, so.name, app.disciplineType, app.year, vari.status, vari.file, vari.id, vari.finalAuditStatus,vari.finalAuditResult ";
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.disciplineType",
		"app.year",
		"vari.applicantSubmitStatus",
		"vari.deptInstAuditStatus, vari.deptInstAuditResult",
		"vari.universityAuditStatus, vari.universityAuditResult",
		"vari.provinceAuditStatus, vari.provinceAuditResult",
		"vari.finalAuditStatus, vari.finalAuditResult",
		"vari.applicantSubmitDate desc",
		"vari.deptInstAuditDate desc",
		"vari.universityAuditDate desc",
		"vari.provinceAuditDate desc",
		"vari.finalAuditDate desc"
		
	};//排序列
	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"负责人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"提交状态",
		"变更状态",
		"变更状态",
		"变更状态",
		"变更状态",
		"变更状态",
		"变更时间",
		"变更时间",
		"变更时间",
		"变更时间"
	};
	
	private static final String PAGE_NAME = "variationPage";// 列表页面名称
	
	protected List<String> selectIssue;//变更事项
	protected List<String> selectProductType;//变更成果形式
	protected List<String> varSelectIssue;//同意变更事项
	protected String varId;//项目变更id
	protected String savePath;//文件路径
	protected String oldDeptInstId;//变更前院系或研究机构id
	protected String deptInstId;//院系或研究机构id
	protected String ageDeptInst;//变更机构名称
	protected int deptInstFlag;//院系或研究机构标志位	1：研究机构	2:院系
	protected String chineseName;//变更中文名
	protected String englishName;//变更英文名
	protected Date newDate1;//延期一次
	protected String planEndDate;//远计划完成时间
	protected String otherInfo;//变更其他信息
	protected String note;//备注
	
	protected ProjectFee oldFee;//变更前经费
//	protected ProjectFee newFee;//变更后经费
	protected ArrayList<Object> varListForSelect;//供JSP页面显示的变更事项
	
	protected String defaultSelectCode;//变更事项选中code
	protected String defaultSelectProductTypeCode;//变更成果形式默认选中;
	protected String defaultSelectApproveVarCode;//默认选中同意的变更事项
	protected String oldProductTypeCode;//变更前的成果形式
	protected String productTypeOther;//其他成果类别名称
	protected String oldProductTypeOther;//变更前的其他成果类别名称
	@SuppressWarnings("rawtypes")
	protected List projectList;//项目信息
	protected int times;//变更次数
	protected List<String> variationIds;//变更id列表
	protected int submitStatus;//审核操作状态	1:退回	2:暂存	3:提交
	protected int varResult;//变更结果	 1：不同意	2：同意
	protected int varResultFlag;//添加变更结果标志位	1：添加成功
	protected int varImportedStatus;//变更结果录入状态
	protected String varImportedOpinion;//录入变更结果意见
	protected String varOpinionFeedback;//录入变更结果意见
	protected String variationReason;//变更原因
	protected int varResultPublish; //变更审核结果是否允许发布标志位  0：不允许发布 1：允许发布
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;//标题提交上来的特征码list
	protected String[] fileIdsforVarPost;//标题提交上来的特征码list,用于变更延期上传延期计划书
	protected String[] varFileId;	//标题提交上来的特征码list,用于变更后上传申请书
	protected String uploadKey;//文件上传授权码
	protected String projectName,projectNumber,researchType,projectSubtype,dtypeNames,applicant,university,divisionName,provinceName,discipline;//高级检索条件
	protected int projectStatus,startYear,endYear,auditStatus,isApproved,applicantSubmitStatus;//高级检索条件
	protected Date startDate,endDate,newOnceDate;
	protected Date varDate;//变更时间
	private String fileFileName;
	private String varContent;//变更内容
	private String variId;//变更id，用于文件上传
	private String memberType;//变更项目成员时，成员类型
	private String memberUnit;//变更项目成员时，成员所在单位
	private String memberName;//变更项目成员时，成员姓名
	protected String type;
	
	protected Date applyStartDate;
	protected Date applyEndDate;
	protected Date auditStartDate;
	protected Date auditEndDate;
	protected String isPublish;
	
	public abstract String variationClassName();//项目变更类类名
	public abstract String listHql2();
	public abstract String listHql3();
	
	public String pageName() {
		return VariationApplyAction.PAGE_NAME;
	}
	public String[] column() {
		return VariationApplyAction.COLUMN;
	}
	
	public String[] columnName() {
		return VariationApplyAction.CCOLUMNNAME;
	}
	/**
	
	/**
	 *  列表辅助信息
	 * @return json
	 */
	public String assist() {
		List lData = new ArrayList();
		Pager pager = (Pager) session.get(pageName());
		HqlTool hqlTool = new HqlTool(pager.getHql());
		int cloumn = pager.getSortColumn();
		if (null == session.get("varAssistMap") && cloumn != 2 && cloumn != 3 && cloumn != 5) {
			cloumn = 4;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 || cloumn == 4 || cloumn == 5 ) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "app.id");
		} else {
			jsonMap = (Map) session.get("varAssistMap");
		}
		session.put("varAssistMap", jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		StringBuffer hql;
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		int columnLabel = 0;
		Map map = new HashMap();
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			map.put("belongId", baseService.getBelongIdByAccount(account));
			 hql = this.projectService.getVarHql(HQL1, listHql3(), accountType);
		}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
			hql = this.projectService.getVarHql(HQL1, listHql2(), accountType);
		}else{//教育部及系统管理员
			hql = this.projectService.getVarHql(HQL1, listHql2(), accountType);
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getVarSimpleSearchHQL(searchType));
		}
		session.put("grantedMap", map);
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 12;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 13;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 14;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 15;
		}else{
			columnLabel = 0;
		}
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.getSelectClause() + ", vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
		session.put("hqlGr", hql.toString());
		HqlTool hqlToolWhere = new HqlTool( hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(57);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
			null
		};
	}
	//选择单条变更数据发布状态
	public String switchPublish() {
		ProjectVariation variation = (ProjectVariation)dao.query(ProjectVariation.class, varId);
		if(variation != null){
			if(loginer.getCurrentType().compareWith(AccountType.ADMINISTRATOR) > 0  &&  variation.getStatus() > 1){//除系统管理员外未提交的申请才可删除
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}
			if(variation.getFinalAuditResultPublish()==0&&varResultPublish==0)
				variation.setFinalAuditResultPublish(1);
			else if(variation.getFinalAuditResultPublish()==1&&varResultPublish==1)
				variation.setFinalAuditResultPublish(0);
			dao.modify(variation);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
		}
		return SUCCESS;
	}
	
	/**
	 * 进入批量发布
	 * @return
	 */
	public String toBatchPublish() {
		return SUCCESS;
	}
	
	/**
	 * 批量发布
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public String batchPublish(){
		if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0){//只有系统管理员才可发布
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}
		StringBuffer pubSql = new StringBuffer();
		Map pubMap = new HashMap();
		pubSql.append("select var from " + variationClassName() + " var where 1=1 ");
		pubSql.append(" and var.finalAuditResult!=0 ");
		if (applyStartDate != null) {
			pubSql.append("and var.applicantSubmitDate >=:applyStartDate ");
			pubMap.put("applyStartDate", applyStartDate);
		}
		if (applyEndDate!=null) {
			pubSql.append("and var.applicantSubmitDate <=:applyEndDate ");
			pubMap.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate!=null) {
			pubSql.append("and var.finalAuditDate >=:auditStartDate ");
			pubMap.put("auditStartDate",auditStartDate);
		}
		if(auditEndDate!=null) {
			pubSql.append("and var.finalAuditDate <=:auditEndDate ");
			pubMap.put("auditEndDate",auditEndDate);
		}
		
		List<ProjectVariation> projectVariations = dao.query(pubSql.toString(), pubMap);
		if(projectVariations.size()!=0) {
			if(isPublish.equals("1")) {
				for (ProjectVariation projectVariation : projectVariations) {
					projectVariation.setFinalAuditResultPublish(1);
					dao.modify(projectVariation);
				}
			} else if(isPublish.equals("0")){
				for (ProjectVariation projectVariation : projectVariations) {
					projectVariation.setFinalAuditResultPublish(0);
					dao.modify(projectVariation);
				}
			}
		}
		return SUCCESS;
	}
	/**
//	取消公开发布
	public String notPublish() {
		for(int i = 0; i < entityIds.size(); i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i = 0; i < entityIds.size(); i++){
			ProjectVariation variation = (ProjectVariation)this.projectService.getCurrentVariationByGrantedId(entityIds.get(i));
			if(variation != null){
				if(loginer.getCurrentType().compareWith(AccountType.ADMINISTRATOR) > 0  &&  variation.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				variation.setFinalAuditResultPublish(0);
				dao.modify(variation);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
			}
		}
		return SUCCESS;
	}
	 **/
	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		int columnLabel = 0;
		Map map = new HashMap();
		StringBuffer hql;
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			map.put("belongId", baseService.getBelongIdByAccount(account));
			 hql = this.projectService.getVarHql(HQL1, listHql3(), accountType);
		}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
			hql = this.projectService.getVarHql(HQL1, listHql2(), accountType);
		}else{//教育部及系统管理员
			hql = this.projectService.getVarHql(HQL1, listHql2(), accountType);
		}
		if(selectIssue != null && selectIssue.size() > 0){
			hql.append(" and (");
			String selectIssues = "";
			for(int i = 0; i < selectIssue.size(); i++){
				selectIssues += selectIssue.get(i) + ";";
			}
			if(selectIssues.indexOf("01") != -1){//变更项目成员（含负责人）
				hql.append(" vari.changeMember = 1 or");
			}
			if(selectIssues.indexOf("02") != -1){//变更机构
				hql.append("  vari.changeAgency = 1 or");
			}
			if(selectIssues.indexOf("03") != -1){//变更成果形式
				hql.append(" vari.changeProductType = 1 or");
			}
			if(selectIssues.indexOf("04") != -1){//变更项目名称
				hql.append(" vari.changeName = 1 or");
			}
			if(selectIssues.indexOf("05") != -1){//研究内容有重大调整
				hql.append(" vari.changeContent = 1 or");
			}
			if(selectIssues.indexOf("06") != -1){//延期
				hql.append(" vari.postponement = 1 or");
			}
			if(selectIssues.indexOf("07") != -1){//自行中止项目
				hql.append(" vari.stop = 1 or");
			}
			if(selectIssues.indexOf("08") != -1){//申请撤项
				hql.append(" vari.withdraw = 1 or");
			}
			if(selectIssues.indexOf("20") != -1){//其他变更
				hql.append(" vari.other = 1 or");
			}
			hql.append(")");
			hql.replace(hql.lastIndexOf("or)"), hql.length(), ")");
			map.put("selectIssue", selectIssue);
		}
		if (projectNumber != null && !projectNumber.isEmpty()) {
			projectNumber = projectNumber.toLowerCase();
			hql.append(" and LOWER(gra.number) like :projectNumber");
			map.put("projectNumber", "%" + projectNumber + "%");
		}
		if (memberName != null && !memberName.isEmpty()) {
			memberName = memberName.toLowerCase();
			hql.append(" and LOWER(mem.memberName) like :memberName");
			map.put("memberName", "%" + memberName + "%");
		}
		if (projectName != null && !projectName.isEmpty()) {
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(gra.name) like :projectName ");
			map.put("projectName", "%" + projectName + "%");
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
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", startYear);
		}
		if (endYear != -1) {
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", endYear);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				hql.append(" and vari.deptInstAuditDate is not null and to_char(vari.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and vari.universityAuditDate is not null and to_char(vari.universityAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and vari.provinceAuditDate is not null and to_char(vari.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and vari.finalAuditDate is not null and to_char(vari.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				if(startDate == null){
					hql.append(" and vari.deptInstAuditDate is not null ");
				}
				hql.append(" and to_char(vari.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(startDate == null){
					hql.append(" and vari.universityAuditDate is not null ");
				}
				hql.append(" and to_char(vari.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				if(startDate == null){
					hql.append(" and vari.provinceAuditDate is not null ");
				}
				hql.append(" and to_char(vari.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				if(startDate == null){
					hql.append(" and vari.finalAuditDate is not null ");
				}
				hql.append(" and to_char(vari.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		if (applicant != null && !applicant.isEmpty()) {
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			map.put("applicant", "%" + applicant + "%");
		}
		if (university != null && !university.isEmpty()) {
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university ");
			map.put("university", "%" + university + "%");
		}
		if (divisionName != null && !divisionName.isEmpty()) {
			divisionName = divisionName.toLowerCase();
			hql.append(" and LOWER(gra.divisionName) like :divisionName ");
			map.put("divisionName", "%" + divisionName + "%");
		}
		if (provinceName != null && !provinceName.isEmpty()) {
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(gra.provinceName) like :provinceName ");
			map.put("provinceName", "%" + provinceName + "%");
		}
		if (discipline!=null && !discipline.isEmpty()) {
			discipline = discipline.toLowerCase();
			String[] disciplines = discipline.split("\\D+");
			hql.append(" and (1=0 ");
			for (int i = 0; i < disciplines.length; i++) {
				map.put("discipline0"+i, disciplines[i]+"%");
				hql.append(" or LOWER(app.discipline) like :discipline0" + i + " ");
				hql.append(" or LOWER(app.relativeDiscipline) like :discipline0" + i + " ");
				map.put("discipline1"+i, "; "+disciplines[i]+"%");
				hql.append(" or LOWER(app.discipline) like :discipline1" + i + " ");
				hql.append(" or LOWER(app.relativeDiscipline) like :discipline1" + i + " ");
			}
			hql.append(" )");
		}
		int resultStatus,saveStatus;
		if(auditStatus != -1){
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			map.put("auditStatus",  saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				hql.append(" and vari.deptInstAuditStatus =:auditStatus and vari.deptInstAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and vari.universityAuditStatus =:auditStatus and vari.universityAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and vari.provinceAuditStatus =:auditStatus and vari.provinceAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and vari.finalAuditStatus =:auditStatus and vari.finalAuditResult =:auditResult");
			}
		}
		session.put("auditStatusauditStatus", auditStatus);
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
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isApproved != -1){
			if(isApproved == 0){
				hql.append(" and vari.finalAuditStatus != 3 ");
			}else{
				hql.append(" and vari.finalAuditStatus = 3 and vari.finalAuditResult =:isApproved ");
				map.put("isApproved", isApproved);
			}
		}
		if(applicantSubmitStatus != -1 && (accountType.within(AccountType.EXPERT, AccountType.STUDENT)) ) {
			map.put("submitStatus",  applicantSubmitStatus);
			hql.append(" and vari.applicantSubmitStatus =:submitStatus");
		}
		session.put("grantedMap", map);
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 12;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 13;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 14;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 15;
		}else{
			columnLabel = 0;
		}
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.getSelectClause() + ", vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
		HqlTool hqlToolWhere = new HqlTool( hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(57);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
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
		if (projectNumber != null && !projectNumber.isEmpty()) {
			searchQuery.put("projectNumber", projectNumber);
		}
		if(projectName!=null && !projectName.isEmpty()){
			searchQuery.put("projectName", projectName);
		}
		if(memberName!=null && !memberName.isEmpty()){
			searchQuery.put("memberName", memberName);
		}
		if(selectIssue != null && selectIssue.size() > 0){
			searchQuery.put("selectIssue", selectIssue);
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
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			searchQuery.put("dtypeNames", dtypeNames);
		}
		if(discipline != null && !discipline.isEmpty()){
			searchQuery.put("discipline", discipline);
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
		if(divisionName!=null && !divisionName.isEmpty()){
			searchQuery.put("divisionName", divisionName);
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			searchQuery.put("provinceName", provinceName);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
		if (auditStatus != -1) {
			searchQuery.put("auditStatus",  auditStatus);
		}
		if (projectStatus != -1) {
			searchQuery.put("projectStatus", projectStatus);
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isApproved != -1){
			searchQuery.put("isApproved", isApproved);
		}
		if(applicantSubmitStatus != -1 && (accountType.within(AccountType.EXPERT, AccountType.STUDENT))) {
			searchQuery.put("applicantSubmitStatus",  applicantSubmitStatus);
		}
	}
	/**
	 * 弹出层修改变更项目成员
	 * @author 余潜玉
	 */
	public String toPopEditMember(){
		return SUCCESS;
	}

	/**
	 * 准备添加变更申请的公共处理
	 * @author 余潜玉
	 */
	public void doWithToAdd(ProjectGranted granted){
		//当前变更次数
		times = this.projectService.getVarTimes(projectid)+1;
		//默认变更事项
		defaultSelectCode = "";
		//默认项目名称
		chineseName = granted.getName();
		englishName = granted.getEnglishName();
		projectName = chineseName;
		//默认机构信息
		if(granted.getDepartment() != null){
			deptInstFlag = 2;//院系
			oldDeptInstId = granted.getDepartment().getId();
			deptInstId = granted.getDepartment().getId();
		}else if(granted.getInstitute() != null){
			deptInstFlag = 1;//研究基地
			oldDeptInstId = granted.getInstitute().getId();
			deptInstId = granted.getInstitute().getId();
		}
		//变更前经费
		if (granted.getProjectFee() != null) {
			oldFee = dao.query(ProjectFee.class, granted.getProjectFee().getId());
		}
		ageDeptInst="";
		if(granted.getAgencyName() != null)
		  ageDeptInst += granted.getAgencyName();
		if(granted.getDivisionName() != null)
		  ageDeptInst += granted.getDivisionName();
		//默认成果类别
		defaultSelectProductTypeCode = this.projectService.getProductTypeCodes(granted.getProductType());
		oldProductTypeCode = defaultSelectProductTypeCode;
		productTypeOther = granted.getProductTypeOther();
		oldProductTypeOther = granted.getProductTypeOther();
		//默认计划完成时间
		newDate1 = granted.getPlanEndDate();
		if(granted.getPlanEndDate() != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			planEndDate = df.format(granted.getPlanEndDate());
		}
	}
	/**
	 * 添加或修改项目变更的公共处理部分
	 * @param variation 项目变更对象
	 * @param flag 1:添加	2：修改
	 * @return 处理后的变更对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectVariation doWithAddOrModify(ProjectVariation variation, int flag) throws Exception{
		String orignFile = variation.getFile();
		if(note != null){
			variation.setNote(("A"+note).trim().substring(1));//保存备注（去掉后面的空格）
		}else{
			variation.setNote(null);
		}
		if(variationReason != null){
			variation.setNote(("A"+variationReason).trim().substring(1));//保存备注（去掉后面的空格）
		}else{
			variation.setNote(null);
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = (AuditInfo)this.projectService.getAuditInfo(loginer, 2, submitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		variation.edit(auditMap);//保存操作结果
		if(submitStatus == 3){//提交申请
			/* 以下代码为跳过部门审核*/
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			variation.edit(auditMap);//部门审核通过
			/* 结束 */
		}
		//保存上传的文件
		if (flag == 1) { //添加
			String groupId = "file_add";
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadVarFile(projectType(), projectid, varImportedStatus, curFile);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		} else if (flag == 2){  //修改
			String groupId = "file_" + variation.getId();
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadVarFile(projectType(), projectid, varImportedStatus, curFile);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		}
		//DMSS同步 
		if(orignFile != variation.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != variation.getFile() && !variation.getFile().isEmpty()){ //现在有文件
					projectService.checkInToDmss(variation);
				}else{ //现在没文件
					dmssService.deleteFile(variation.getDfs());
					variation.setDfs(null);
					dao.modify(variation);
				}
			}else{ //原来没有文件
				if(variation.getFile()!=null && !variation.getFile().isEmpty()){ //现在有文件
					String dfsId = projectService.uploadToDmss(variation);
					variation.setDfs(dfsId);
					dao.modify(variation);
				}
			}
		}
   		return variation;
	}
	
	/**
	 * 教育部录入变更，变更项目成员，根据人员类别、所在单位、姓名判断是否是数据库中已有人员
	 * @param memberType 成员类型
	 * @param memberUnit 所在单位
	 * @param memberName 姓名
	 * @return List 匹配结果 {0:PersonId；1：人员姓名；2：所在高校/单位；3：所在院系；4：所属研究机构}
	 */
	public String personMatch(){
		List personList = new ArrayList<String>();
		Map map = new HashMap();
		map.put("memberType", memberType);
		map.put("memberUnit", memberUnit);
		map.put("memberName", memberName);
		if (memberType.equals("teacher")) {//教师在Teacher表和Person表中进行匹配
			personList = dao.query("select p.id, p.name, u.name, d.name, i.name from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u where u.name = :memberUnit and p.name = :memberName ", map);
		} else if (memberType.equals("expert")) {
			personList = dao.query("select p.id, p.name, e.agencyName, e.divisionName from Expert e join e.person p where e.agencyName = :memberUnit and p.name = :memberName ", map);
		} else if (memberType.equals("student")) {
			personList = dao.query("select p.id, p.name, u.name, d.name, i.name from Student s left join s.person p left join s.department d left join s.institute i left join s.university u where u.name = :memberUnit and p.name = :memberName ", map);
		}
		jsonMap.put("personList", personList);
		return SUCCESS;
	}
	
	/**
	 * 准备修改变更的公共处理
	 * @author 余潜玉
	 */
	public void doWithToModify(ProjectGranted granted, ProjectVariation variation){
		defaultSelectApproveVarCode = this.projectService.getVarCanApproveItem(variation.getFinalAuditResultDetail());
		//当前变更次数
		times = this.projectService.getVarTimes(projectid);
		//项目机构信息
		if(variation.getChangeAgency() == 1){//变更机构
			defaultSelectCode += "02,";
			if(variation.getNewDepartment() != null){
				deptInstFlag = 2;//院系
				deptInstId = variation.getNewDepartment().getId();
			}else if(variation.getNewInstitute() != null){
				deptInstFlag = 1;//研究机构
				deptInstId = variation.getNewInstitute().getId();
			}
			if(variation.getOldDepartment() != null){
				oldDeptInstId = variation.getOldDepartment().getId();
			}else if(variation.getOldInstitute() != null){
				oldDeptInstId = variation.getOldInstitute().getId();
			}
			ageDeptInst = variation.getNewAgencyName() + variation.getNewDivisionName();
		}else{
			//默认机构信息
			if(granted.getDepartment() != null){
				deptInstFlag = 2;//院系
				oldDeptInstId = granted.getDepartment().getId();
				deptInstId = granted.getDepartment().getId();
			}else if(granted.getInstitute() != null){
				deptInstFlag = 1;//研究基地
				oldDeptInstId = granted.getInstitute().getId();
				deptInstId = granted.getInstitute().getId();
			}
			ageDeptInst = "";
			if(granted.getAgencyName() != null)
			  ageDeptInst += granted.getAgencyName();
			if(granted.getDivisionName() != null)
			  ageDeptInst += granted.getDivisionName();
		}
		//项目成果信息
		String productTypeNames;
		if(variation.getChangeProductType()==1){//变更成果形式
			defaultSelectCode+="03,";
			productTypeNames = variation.getNewProductType();
			productTypeOther = variation.getNewProductTypeOther();
			oldProductTypeOther = variation.getOldProductTypeOther();
		}else{
			productTypeNames = granted.getProductType();
			productTypeOther = granted.getProductTypeOther();
			oldProductTypeOther = granted.getProductTypeOther();
		}
		defaultSelectProductTypeCode = this.projectService.getProductTypeCodes(productTypeNames);
		oldProductTypeCode = this.projectService.getProductTypeCodes(granted.getProductType());
		//项目名称信息
		if(variation.getChangeName() == 1){//变更项目名称
			defaultSelectCode += "04,";
			chineseName = variation.getNewName();
			englishName = variation.getNewEnglishName();
		}else{
			chineseName = granted.getName();
			englishName = granted.getEnglishName();
		}
		projectName = granted.getName();
		if(variation.getChangeContent() == 1)//研究内容有重大调整
			defaultSelectCode += "05,";
		if(variation.getPostponement() == 1){//延期
			defaultSelectCode += "06,";
			newDate1 = variation.getNewOnceDate();

			//文件上传
			
			//将已有附件加入文件组，在编辑页面显示
			String groupId = "file_postponementPlan_" + variation.getId();
			uploadService.resetGroup(groupId);
			if (variation.getPostponementPlanFile() != null) {
				String[] tempFileRealpath = variation.getPostponementPlanFile().split("; ");
				//遍历要修改的已有的文件
				for (int i = 0; i < tempFileRealpath.length; i++) {
					String filePath = tempFileRealpath[i];
					String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
					if (fileRealpath != null && new File(fileRealpath).exists()) {
						uploadService.addFile(groupId, new File(fileRealpath));
					} else if(variation.getPostponementPlanDfs() != null && !variation.getPostponementPlanDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
						try {
							InputStream downloadStream = dmssService.download(variation.getPostponementPlanDfs());
							String sessionId = ServletActionContext.getRequest().getSession().getId();
							File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
							dir.mkdirs();
							String fileName = variation.getPostponementPlanFile().substring(variation.getPostponementPlanFile().lastIndexOf("/") + 1);
							File downloadFile = new File(dir, fileName);
							FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
							uploadService.addFile(groupId, downloadFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}else{
			newDate1 = granted.getPlanEndDate();
		}
		//项目计划完成时间
		if(granted.getPlanEndDate() != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			planEndDate = df.format(granted.getPlanEndDate());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(granted.getPlanEndDate());
//			cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//			Date newDate2= cal.getTime();
//			String limitedDate = df.format(newDate2);
//			request.setAttribute("limitedDate", limitedDate);//限制日期
		}
		if(variation.getStop() == 1)//自行终止项目
			defaultSelectCode += "07,";
		if(variation.getWithdraw() == 1)//申请撤项
			defaultSelectCode += "08,";
		if (variation.getChangeFee() == 1) {//经费变更	
			defaultSelectCode += "09,";
			if (variation.getOldProjectFee() != null) {
				oldFee = dao.query(ProjectFee.class, variation.getOldProjectFee().getId());
			}
			newFee = dao.query(ProjectFee.class, variation.getNewProjectFee().getId());
		}else {
			if (granted.getProjectFee() != null) {
				oldFee = dao.query(ProjectFee.class, granted.getProjectFee().getId());
			}
		}
		if(variation.getOther() == 1){//其他变更
			defaultSelectCode += "20,";
			otherInfo = variation.getOtherInfo();
		}
		if(defaultSelectCode.trim().length()!=0){
			defaultSelectCode = defaultSelectCode.substring(0, defaultSelectCode.length() - 1);
		}
		note = variation.getNote();
		varResult = variation.getFinalAuditResult();
		varDate = variation.getFinalAuditDate();
		varImportedOpinion = variation.getFinalAuditOpinion();
		varOpinionFeedback = variation.getFinalAuditOpinionFeedback();
		variationReason = variation.getVariationReason();
		

		//文件上传
		//将已有附件加入文件组，在编辑页面显示
		String groupId2 = "file_" + variation.getId();
		uploadService.resetGroup(groupId2);
		if (variation.getFile() != null) {
			String[] tempFileRealpath = variation.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId2, new File(fileRealpath));
				} else if(variation.getDfs() != null && !variation.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(variation.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = variation.getFile().substring(variation.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId2, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 提交变更申请的公共处理
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectVariation doWithSubmit(ProjectVariation variation){
		//File varWordFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + variation.getFile());
		//projectService.importVarXMLData(projectid, varWordFile);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		variation.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		variation.edit(auditMap);//部门审核通过
		/* 结束 */
		return variation;	
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
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除项目变更申请
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete() {
		for(int i = 0; i < entityIds.size(); i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i = 0; i < entityIds.size(); i++){
			ProjectVariation variation = (ProjectVariation)this.projectService.getCurrentVariationByGrantedId(entityIds.get(i));
			if(variation != null){
				if(loginer.getCurrentType().compareWith(AccountType.ADMINISTRATOR) > 0  &&  variation.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				this.projectService.deleteVariation(variation);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void validateDelete(){
		String info ="";
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DELETE_NULL);
			info += ProjectInfo.ERROR_MID_DELETE_NULL;
		}
		String appId = this.projectService.getApplicationIdByGrantedId(entityIds.get(0)).trim();
		//校验业务设置状态
		varStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (varStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 添加或修改项目变更结果的公共处理
	 * @param variation 项目变更对象
	 * @param flag 1:添加  2:修改
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public ProjectVariation doWithAddOrModifyResult(ProjectVariation variation, int flag) throws Exception{
		String orignFile = variation.getFile();
		variation.setCreateMode(1);//设为导人数据
		Date submitDate = this.projectService.setDateHhmmss(varDate);
		if (flag==1) {
			variation.setCreateDate(new Date());
		}else {
			variation.setUpdateDate(new Date());
		}
		variation.setApplicantSubmitDate(submitDate);
		variation.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = (AuditInfo)this.projectService.getAuditInfo(loginer, varResult, varImportedStatus, null);
		variation.setFinalAuditor(auditInfo.getAuditor());
		variation.setFinalAuditorName(auditInfo.getAuditorName());
		variation.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		variation.setFinalAuditorDept(auditInfo.getAuditorDept());
		variation.setFinalAuditorInst(auditInfo.getAuditorInst());
		variation.setFinalAuditResult(varResult);
		variation.setFinalAuditStatus(varImportedStatus);
		if(varImportedOpinion != null && varImportedOpinion.trim().length() > 0){
			variation.setFinalAuditOpinion(("A" + varImportedOpinion).trim().substring(1));
		}else{
			variation.setFinalAuditOpinion(null);
		}
		if(variationReason != null && variationReason.trim().length() > 0){
			variation.setVariationReason(("A" + variationReason).trim().substring(1));
		}else{
			variation.setVariationReason(null);
		}
		
		if(varOpinionFeedback != null && varOpinionFeedback.trim().length() > 0){
			variation.setFinalAuditOpinionFeedback(("A" + varOpinionFeedback).trim().substring(1));
		}else{
			variation.setFinalAuditOpinionFeedback(null);
		}
		if(varResult == 2){//同意
			String varSelectIssues = "";
			for(int i = 0; i < varSelectIssue.size(); i++){
				varSelectIssues += varSelectIssue.get(i) + ",";
			}
			variation.setFinalAuditResultDetail(this.projectService.getVarApproveItem(varSelectIssues));
		}else{
			variation.setFinalAuditResultDetail(null);
		}
		//保存上传的文件
		if (flag == 1) { //添加
			String groupId = "file_add";
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadVarFile(projectType(), projectid, varImportedStatus, curFile);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		} else if (flag == 2){  //修改
			String groupId = "file_" + variation.getId();
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadVarFile(projectType(), projectid, varImportedStatus, curFile);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		}
		//DMSS同步 
		if(orignFile != variation.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != variation.getFile() && !variation.getFile().isEmpty()){ //现在有文件
					projectService.checkInToDmss(variation);
				}else{ //现在没文件
					dmssService.deleteFile(variation.getDfs());
					variation.setDfs(null);
				}
			}else{ //原来没有文件
				if(variation.getFile()!=null && !variation.getFile().isEmpty()){ //现在有文件
					String dfsId = projectService.uploadToDmss(variation);
					variation.setDfs(dfsId);
				}
			}
		}
   		return variation;
	}
	
	/**
	 * 提交录入的变更结果
	 * @param variation 项目变更对象
	 * @author 余潜玉
	 */
	public void doWithSubmitResult(ProjectVariation variation){
		variation.setUpdateDate(new Date());
		AuditInfo auditInfo = (AuditInfo)this.projectService.getAuditInfo(loginer, 0, 3, null);
		variation.setFinalAuditor(auditInfo.getAuditor());
		variation.setFinalAuditorName(auditInfo.getAuditorName());
		variation.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		variation.setFinalAuditorDept(auditInfo.getAuditorDept());
		variation.setFinalAuditorInst(auditInfo.getAuditorInst());
		variation.setFinalAuditStatus(3);
		dao.modify(variation);
	}

	/**
	 * 编辑变更校验
	 * @param type 校验类型：11准备添加;	12添加;	21准备修改; 22修改;3提交
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void doWithValidateEdit(int type){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid);
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
			}else if(granted.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
			}
			if(!this.projectService.getDireIdByAppId(appId, granted.getMemberGroupNumber()).contains(loginer.getPerson().getId())){//当前用户不是负责人
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
			}
		}
		//校验业务设置状态
		varStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (varStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
		}
		if(type == 11 || type == 12){
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
			}
		}
		if(type == 12 || type == 22){
			if(varId == null || varId.trim().length() == 0){
				if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
				}
			}
			if (type == 12) { //添加
				String groupId = "file_add";
				if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				}
			} 	
//				if(null == fileIds || fileIds.length == 0){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//				}
//				}else if(fileIds.length > 1){
//					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//				}
			if(submitStatus != 2 && submitStatus !=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_SUBMITSTATUS_NULL);
			}
		}else if(type == 21 || type == 3){
			if(varId == null || varId.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MODIFY_APPLY_NULL);
			}
		}
		/*
		// 上传word宏的校验
		if(submitStatus == 3 || type == 3) {
			File curFile = null;
			if (submitStatus == 3 && fileIds != null && fileIds.length == 1) {
				Map<String, Object> sc = ActionContext.getContext().getApplication();
				String sessionId = request.getSession().getId();
				String basePath = ApplicationContainer.sc.getRealPath((String)sc.get("tempUploadPath") + "/" + sessionId);
				File path = new File(basePath + "/" + fileIds[0]);
				if (path.exists()) {
					Iterator it = FileUtils.iterateFiles(path, null, false);
					curFile = it.hasNext() ? (File)it.next() : null;
				}
			}
			if(type == 3) {
				variation = (GeneralVariation)this.dao.query(GeneralVariation.class, varId.trim());
				curFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + variation.getFile());
			}
			if(curFile != null) {
				String wordError = projectService.checkWordFileLegal(projectid, curFile, 2);
				if(wordError != null) {
					System.out.println("wordError " + wordError);
					this.addFieldError(GlobalInfo.ERROR_INFO, wordError);
					info += wordError;
				}
			}
		}*/
	}
	/**
	 * 编辑变更结果校验
	 * @param type 校验类型：11:准备添加; 12添加; 21准备修改;	22修改;	3提交
	 * @author 余潜玉
	 */
	public void doWithValidateEditResult(int type){
		if(projectid == null || "".equals(projectid)){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_NOT_GRANTED);
		}else{
			ProjectGranted general = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(general == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
			}else if(general.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
			}else if(general.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
			}else if(general.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
			}
		}
		if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过结项
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
		}
		if(type == 11 || type == 12){
			ProjectVariation variation = this.projectService.getCurrentVariationByGrantedId(projectid);
			if (null!=variation) {
				if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
				}
			}
		}
		if(type == 12 || type == 22){
			if(varResult != 1 && varResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_RESULT_NULL);
			}
			if(varResult == 2){//同意变更
				if(varSelectIssue == null || varSelectIssue.size() == 0){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SELECT_ISSUE_NULL);
				}
			}
			if(varDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DATE_NULL);
			}
		}else if(type == 21 || type == 3){
			if(varId == null || varId.trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MODIFY_APPLY_NULL);
			}
		}
	}
	/**
	 * 保存变更机构相关信息
	 * @param granted 项目立项对象  
	 * @param variation 项目变更对象 
	 * @param selectIssues 选中的变更项
	 * @param flag 1:添加	2：修改
	 * @return 处理后的变更对象
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public ProjectVariation setVariationInfo(ProjectGranted granted, ProjectVariation variation, String selectIssues, int flag) throws Exception{
		String orignFile = variation.getPostponementPlanFile();
		if(selectIssues.indexOf("02") != -1){//变更机构
			variation = this.projectService.setVariationAgencyInfo(variation, granted.getId(), deptInstFlag, deptInstId);
		}else if(defaultSelectCode.indexOf("02") != -1){//之前变更机构，现在不变更
			variation.setChangeAgency(2);
			variation.setOldAgency(null);
			variation.setOldAgencyName(null);
			variation.setOldDepartment(null);
			variation.setOldInstitute(null);
			variation.setOldDivisionName(null);
			variation.setNewDepartment(null);
			variation.setNewInstitute(null);
			variation.setNewAgency(null);
			variation.setNewAgencyName(null);
			variation.setNewDivisionName(null);
		}
		if(selectIssues.indexOf("03")!= -1){//变更成果形式
			variation.setChangeProductType(1);
			String productTypeNames = this.projectService.getProductTypeNames(selectProductType);
			if(productTypeNames != null && productTypeNames.contains("其他")){
				variation.setNewProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
				if(granted.getProductType() != null && granted.getProductType().equals(productTypeNames) && productTypeOther.equals(granted.getProductTypeOther())){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_SAME);
				}
			}else{
				variation.setNewProductTypeOther(null);
				if(granted.getProductType() != null && granted.getProductType().equals(productTypeNames)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_SAME);
				}
			}
			variation.setOldProductType(granted.getProductType());
			variation.setOldProductTypeOther(granted.getProductTypeOther());
			variation.setNewProductType(productTypeNames);
		}else if(defaultSelectCode.indexOf("03")!= -1){//之前变更成果形式，现在不变更
			variation.setChangeProductType(2);
			variation.setOldProductType(null);
			variation.setOldProductTypeOther(null);
			variation.setNewProductType(null);
			variation.setNewProductTypeOther(null);
		}
		if(selectIssues.indexOf("04") != -1){//变更项目名称
			if(granted.getName() != null && granted.getName().equals(chineseName)){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_NAME_SAME);
			}
			variation.setChangeName(1);
			variation.setOldName(granted.getName());
			variation.setOldEnglishName(granted.getEnglishName());
			variation.setNewName(chineseName);
			variation.setNewEnglishName(englishName);
		}else if(defaultSelectCode.indexOf("04") != -1){//之前变更项目名称，现在不变更
			variation.setChangeName(2);
			variation.setOldName(null);
			variation.setOldEnglishName(null);
			variation.setNewName(null);
			variation.setNewEnglishName(null);
		}
		if(selectIssues.indexOf("05") != -1){//研究内容有重大调整
			variation.setChangeContent(1);
		}else if(defaultSelectCode.indexOf("05") != -1){//之前变更研究内容，现在不变更
			variation.setChangeContent(2);
		}
		if(selectIssues.indexOf("06") != -1){//延期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(granted.getPlanEndDate() != null && df.format(granted.getPlanEndDate()).equals(df.format(newDate1))){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_END_TIME_SAME);
			}
			variation.setPostponement(1);
			variation.setOldOnceDate(granted.getPlanEndDate());
			variation.setNewOnceDate(newDate1);
			//上传变更延期项目研究计划
			if (flag == 1) {
				String groupId = "file_postponementPlan_add";
				List<String> files = new ArrayList<String>();
				for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
					//只能上传一个，在js中作了限制
					File curFile = fileRecord.getOriginal();
					String savePath = this.projectService.uploadVarPlanfile(projectType(), projectid, varImportedStatus, curFile);
					//将文件放入list中暂存
					files.add(savePath);
					fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
				}
				variation.setPostponementPlanFile(StringTool.joinString(files.toArray(new String[0]), "; "));
				uploadService.flush(groupId);
			} else if (flag == 2) {
				String groupId = "file_postponementPlan_" + variation.getId();
				List<String> files = new ArrayList<String>();
				for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
					//只能上传一个，在js中作了限制
					File curFile = fileRecord.getOriginal();
					String savePath = this.projectService.uploadVarPlanfile(projectType(), projectid, varImportedStatus, curFile);
					//将文件放入list中暂存
					files.add(savePath);
					fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
				}
				variation.setPostponementPlanFile(StringTool.joinString(files.toArray(new String[0]), "; "));
				uploadService.flush(groupId);
			}
			//DMSS同步 
			if(orignFile != variation.getPostponementPlanFile() && dmssService.getStatus()){ //文件已修改
				if(null != orignFile && !orignFile.isEmpty()){//原来有文件
					if(null != variation.getPostponementPlanFile() && !variation.getPostponementPlanFile().isEmpty()){ //现在有文件
						projectService.checkInToDmss(variation);
					}else{ //现在没文件
						dmssService.deleteFile(variation.getPostponementPlanDfs());
						variation.setPostponementPlanDfs(null);
						dao.modify(variation);
					}
				}else{ //原来没有文件
					if(variation.getPostponementPlanFile()!=null && !variation.getPostponementPlanFile().isEmpty()){ //现在有文件
						String dfsId = projectService.uploadToDmss(variation);
						variation.setPostponementPlanDfs(dfsId);
						dao.modify(variation);
					}
				}
			}
		}else if(defaultSelectCode.indexOf("06") != -1){//之前申请延期，现在不申请
			variation.setPostponement(2);
			variation.setOldOnceDate(null);
			variation.setNewOnceDate(null);
			if(variation.getPostponementPlanFile()!=null &&!variation.getPostponementPlanFile().trim().isEmpty()){
				FileTool.fileDelete(variation.getFile());
			}
			variation.setPostponementPlanFile(null);
		}
		if(selectIssues.indexOf("07") != -1){//自行终止项目
			variation.setStop(1);
		}else if(defaultSelectCode.indexOf("07") != -1){//之前申请自行终止项目，现在不申请
			variation.setStop(2);
		}
		if(selectIssues.indexOf("08") != -1){//申请撤项
			variation.setWithdraw(1);
		}else if(defaultSelectCode.indexOf("08") != -1){//之前申请撤项，现在不申请
			variation.setWithdraw(2);
		}
		
		if(selectIssues.indexOf("09") != -1){//变更经费预算
			ProjectFee newProjectFee = new ProjectFee();
			ProjectFee oldProjectFee = new ProjectFee();
			newProjectFee = projectService.setProjectFee(newFee);
			if (granted.getProjectFee() != null) {
				oldProjectFee = dao.query(ProjectFee.class, granted.getProjectFee().getId());
//				newProjectFee.setBookNote(oldProjectFee.getBookNote());
//				newProjectFee.setConferenceNote(oldProjectFee.getConferenceNote());
//				newProjectFee.setConsultationNote(oldProjectFee.getConsultationNote());
//				newProjectFee.setDataNote(oldProjectFee.getDataNote());
//				newProjectFee.setDeviceNote(oldProjectFee.getDeviceNote());
//				newProjectFee.setIndirectNote(oldProjectFee.getIndirectNote());
//				newProjectFee.setInternationalNote(oldProjectFee.getInternationalNote());
//				newProjectFee.setLaborNote(oldProjectFee.getLaborNote());
//				newProjectFee.setOtherNote(oldProjectFee.getOtherNote());
//				newProjectFee.setPrintNote(oldProjectFee.getPrintNote());
//				newProjectFee.setTravelNote(oldProjectFee.getTravelNote());
				variation.setOldProjectFee(oldProjectFee);
			}
			dao.add(newProjectFee);
			variation.setChangeFee(1);
			variation.setNewProjectFee(newProjectFee);
		}else if(defaultSelectCode.indexOf("09") != -1){//之前变更经费预算，现在不变更
			variation.setChangeFee(2);
		}
		
		
//		if(selectIssues.indexOf("40") != -1){//其他变更
		if(selectIssues.indexOf("20") != -1){//其他变更
			variation.setOther(1);
			variation.setOtherInfo(otherInfo);
		}else if(defaultSelectCode.indexOf("20") != -1){//之前申请其他变更，现在不申请
			variation.setOther(2);
			variation.setOtherInfo(null);
		}
		return variation;
	}
	/**
	 * 变更项目成员（含负责人）的校验公共部分
	 * @param type 1:走流程	2：录入
	 * @author 余潜玉
	 */
	public void validateMember(ProjectMember member, int type){
		if(member.getMemberType() == -1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_MEMBER_TYPE_NULL);
		}
//		if(member.getSpecialistTitle() == null || member.getSpecialistTitle().equals("-1") || member.getSpecialistTitle().trim().isEmpty()){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_SPECIALIST_TITLE_NULL);
//		}
		if(member.getMajor() != null && member.getMajor().length() > 50){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_MAJOR_OUT);
		}
//		if(member.getWorkMonthPerYear()<0 || member.getWorkMonthPerYear()>12){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_WORK_MONTH_WRONG);
//		}
		if(member.getWorkDivision()!=null && member.getWorkDivision().length()>200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_DIVISION_OUT);
		}
		if(member.getIsDirector() == -1){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_IS_DIRECTOR_NULL);
		}
		if(type == 1){//走流程
			if(member.getMemberName() == null || member.getMemberName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_MEMBER_NAME_NULL);
			}
			if(member.getIdcardType() == null || "-1".equals(member.getIdcardType().trim())){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_TYPE_NULL);
			}
			if(member.getIdcardNumber() == null || member.getIdcardNumber().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_NUMBER_NULL);
			}else if(member.getIdcardNumber().trim().length() > 18){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_IDCARD_NUMBER_OUT);
			}
			if (member.getGender() == null || (!member.getGender().trim().equals("男") && !member.getGender().trim().equals("女"))){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_GENDER_NULL);
			}
			if(this.projectService.isPersonMatch(member.getIdcardType(), member.getIdcardNumber(), member.getMemberName(), member.getGender()) == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_EXCEPTION);
			}
			if(member.getDivisionType() == -1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PERSON_DIVISION_TYPE_NULL);
			}
			if(member.getAgencyName() == null || member.getAgencyName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_UNIT_NULL);
			}
			if(member.getDivisionName() == null || member.getDivisionName().trim().isEmpty()){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_DEPT_INST_NULL);
			}
		}else if(type == 2){//录入
			if(member.getMember() == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MEMBER_NULL);
			}
		}
	}
	/**
	 * 变更的校验公共部分(用于变更申请的添加与修改以及录入变更结果的添加与修改的校验)
	 * @param selectIssueItem 选中的变更项
	 * @author 余潜玉
	 */
	public void validateVarNotContianMember(String selectIssueItem){
		if(selectIssueItem.equals("02")){//变更机构
			if(deptInstId == null || "".equals(deptInstId)){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEPT_INST_NULL);
			}else if(deptInstId.equals(oldDeptInstId)){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEPT_INST_SAME);
			}
		}
		if(selectIssueItem.equals("03")){//变更成果形式
			if(this.selectProductType == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PRODUCT_TYPE_NULL);
			}else if(this.selectProductType.contains("otherProductType")){
				if(productTypeOther == null || productTypeOther.trim().isEmpty()){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_NULL);
				}else if(productTypeOther.trim().length() > 50){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_PRODUCT_TYPE_OTHER_OUT);
				}
			}
		}
		if(selectIssueItem.equals("04")){
			if(this.chineseName.equals("")){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_NAME_NULL);
			}else if(this.chineseName.length() > 50){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_NAME_OUT);
			}
			 if(this.englishName != null && this.englishName.length() > 50){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_ENG_NAME_OUT);
			 }
		}
		if(selectIssueItem.equals("06")){
			if(this.newDate1 == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_END_TIME_NULL);
			}
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class,projectid);
			if(granted.getPlanEndDate() != null){
//						Date limitedDate1 = new java.text.SimpleDateFormat("yyyy-MM-dd").parse((String)request.getParameter("limitedDate"));
//						int d1 = this.newDate1.compareTo(limitedDate1);
				int d2 = this.newDate1.compareTo(granted.getPlanEndDate());
//						if(d1 > 0){
//							this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_TIME_PASS);
//						}
				if(d2 < 0){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_PROJECT_TIME_INVALIDATE);
				}
			}
		}
		if(selectIssueItem.equals("20")){//其他变更
			if(null == otherInfo || otherInfo.length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OTHER_NULL);
			}
		}
	}
	/**
	 * 校验变更意见
	 */
	public void validateOpinion(){
		if(varImportedOpinion != null && varImportedOpinion.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OPINION_OUT);
		}
		if(varOpinionFeedback != null && varOpinionFeedback.trim().length() > 200){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_OPINION_OUT);
		}
	}
	
	/**
	 * 校验查看负责人
	 */
	@SuppressWarnings("unchecked")
	public void validateViewDir(){
		if(entityId == null || entityId.trim().length()==0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_VIEW_NULL);
		}
	}
	
	/**
	 * 查看变更其他详细信息
	 * @author 余潜玉
	 */
	public String viewOther(){
		ProjectVariation variation = (ProjectVariation)this.dao.query(ProjectVariation.class, varId);
		otherInfo = variation.getOtherInfo();
		return SUCCESS;
	}
	
	/**
	 * 查看变更其他详细信息
	 * @author 余潜玉
	 */
	public String viewFee(){
		oldFee = (ProjectFee)this.dao.query(ProjectFee.class, varId);
		return SUCCESS;
	}
	
	/**
	 * 下载变更申请书模板
	 */
	public String downloadTemplate(){
		return SUCCESS;
	}
	
	/**
	 * 变更申请书模板文件下载流
	 */
	public InputStream getTargetTemplate() throws Exception{
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		String filename = "/temp/" + sessionId + "/general/2011variation.zip";
		savePath = new String("2011variation.zip".getBytes(), "ISO-8859-1");
		return ApplicationContainer.sc.getResourceAsStream(filename);
	}
	
	/**
	 * 模板是否存在校验
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		if(!projectService.createVariationZip(projectid, loginer.getPerson().getId(), sessionId, projectType())) {
			addActionError("变更申请文件生成失败");
			return ERROR;
		}
		String filename = "/temp/" + sessionId + "/general/2011variation.zip";
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		return SUCCESS;
	}

	/**
	 * 下载项目变更申请书
	 * @author 余潜玉
	 */
	public String downloadApply()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 变更申请表下载流
	 * @author 余潜玉
	 */
	public InputStream getTargetFile() throws Exception{
		InputStream downloadStream = null;
		if (entityId == null) {
			return downloadStream;
		}
		ProjectVariation variation = (ProjectVariation) this.dao.query(ProjectVariation.class, entityId);
		savePath = variation.getFile();
		String filename="";
		if(savePath != null && savePath.length()!=0){
			filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
			savePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(variation.getDfs());
			}
		 }
		return downloadStream;
	}
	
	/**
	 * 变更申请表是否存在校验
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String validateFile()throws Exception{
		if (null == entityId || entityId.trim().isEmpty()) {//id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} else {
			ProjectVariation variation = (ProjectVariation) this.dao.query(ProjectVariation.class, this.entityId);
			if(null == variation){//变更不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(variation.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				savePath = variation.getFile();
				String filename = new String(savePath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == variation.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != variation.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 项目变更延期项目计划书下载
	 * @author 肖雅
	 */
	public String downloadPostponement()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 变更延期项目计划书下载流
	 * @author 肖雅
	 */
	public InputStream getPostponementFile() throws Exception{
		InputStream downloadStream = null;
		if (entityId==null) {
			return downloadStream;
		}
		ProjectVariation variation = (ProjectVariation) this.dao.query(ProjectVariation.class, entityId);
		savePath = variation.getPostponementPlanFile();
		String filename="";
		if(savePath != null && savePath.length()!=0){
			filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
			savePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(variation.getPostponementPlanDfs());
			}
		 }
		return downloadStream;
	}
	
	/**
	 * 变更延期项目计划书是否存在校验
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public String validatePostponementFile()throws Exception{
		if (null == entityId || entityId.trim().isEmpty()) {//id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} else {
			ProjectVariation variation = (ProjectVariation) this.dao.query(ProjectVariation.class, this.entityId);
			if(null == variation){//变更不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(variation.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				savePath = variation.getPostponementPlanFile();
				String filename = new String(savePath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == variation.getPostponementPlanDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != variation.getPostponementPlanDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 准备导入上传电子文档
	 * @author 肖雅
	 */
	public String toUploadFileResult(){
		ProjectVariation variation = (ProjectVariation)this.dao.query(ProjectVariation.class, variId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		if (variation.getFile() != null) {
			String[] tempFileRealpath = variation.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(variation.getDfs() != null && !variation.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(variation.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = variation.getFile().substring(variation.getFile().lastIndexOf("/") + 1);
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
	
	/**
	 * 导入上传电子文档
	 * @author 肖雅
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public String uploadFileResult() throws Exception{
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectVariation variation;
		varImportedStatus = 3;
		variation = (ProjectVariation)this.dao.query(ProjectVariation.class, variId);
		String orignFile = variation.getFile();
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadVarFileResult(projectType(), projectid, varImportedStatus, curFile, variId);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		variation.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		dao.modify(variation);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != variation.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != variation.getFile()){ //现在有文件
					projectService.checkInToDmss(variation);
				}else{ //现在没文件
					dmssService.deleteFile(variation.getDfs());
					variation.setDfs(null);
					dao.modify(variation);
				}
			}else{ //原来没有文件
				if(variation.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(variation);
					variation.setDfs(dfsId);
					dao.modify(variation);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 进入项目变更报表导出设置页面预处理
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
		Calendar cal = Calendar.getInstance();
		endDate = cal.getTime();
		cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		startDate = cal.getTime();
		return SUCCESS;
	}
	
	/**
	 * 导出变更一览表
	 * @return 变更一览表的excel文件
	 * @author 王燕
	 */
	public String confirmExportOverView(){
		return SUCCESS;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		if("general".equals(projectType())){
			header = "教育部人文社会科学研究一般项目变更情况一览表";
		}else if ("instp".equals(projectType())){
			header = "教育部人文社会科学研究基地项目变更情况一览表";
		}else if ("post".equals(projectType())){
			header = "教育部人文社会科学研究后期资助项目变更情况一览表";
		}else if ("key".equals(projectType())){
			header = "教育部人文社会科学研究重大攻关项目变更情况一览表";
		}else if ("entrust".equals(projectType())){
			header = "教育部人文社会科学研究委托应急课题变更情况一览表";
		}	
		String hql4Sel = "";
		StringBuffer hql4Export = new StringBuffer();
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
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
					"依托基地",
					"项目负责人",
					"变更事项",
					"变更内容",
					"是否同意",
					"变更时间",
					"项目状态",
					"备注"
				};
//			hql4Export.append("select vari.id, app.name, gra.applicantName, uni.name, ins.name, sub.name, app.year, gra.number, '变更事项', '变更内容', " +
//					"vari.finalAuditResult, to_char(vari.finalAuditDate,'yyyy-MM-dd'), gra.status, vari.finalAuditOpinionFeedback from " + variationClassName() + " all_vari, "+ variationClassName() + " vari join " +
//					"vari.granted gra left outer join gra.university uni left outer join gra.institute ins join gra.application app left outer join app.subtype sub where 1=1 " +
//					"and vari.finalAuditDate is not null");
			hql4Sel = "select vari.id, app.name, gra.applicantName, uni.name, ins.name, sub.name, app.year, gra.number, '变更事项', '变更内容', " +
					"vari.finalAuditResult, to_char(vari.finalAuditDate,'yyyy-MM-dd'), gra.status, vari.finalAuditOpinionFeedback ";  
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
					"变更事项",
					"变更内容",
					"是否同意",
					"变更时间",
					"项目状态",
					"备注"
				};
//			hql4Export.append("select vari.id, app.name, gra.applicantName, uni.name, sub.name, app.year, gra.number, '变更事项', '变更内容', " +
//					"vari.finalAuditResult, to_char(vari.finalAuditDate,'yyyy-MM-dd'), gra.status, vari.finalAuditOpinionFeedback from " + variationClassName() + " vari join " +
//					"vari.granted gra left outer join gra.university uni join gra.application app left outer join app.subtype sub where 1=1 " +
//					"and vari.finalAuditDate is not null");
			hql4Sel = "select vari.id, app.name, gra.applicantName, uni.name, sub.name, app.year, gra.number, '变更事项', '变更内容', " +
					"vari.finalAuditResult, to_char(vari.finalAuditDate,'yyyy-MM-dd'), gra.status, vari.finalAuditOpinionFeedback ";
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql4Export = this.projectService.getVarHql(hql4Sel, listHql3(), accountType);
		}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
			hql4Export = this.projectService.getVarHql(hql4Sel, listHql2(), accountType);
		}else{//教育部及系统管理员
			hql4Export = this.projectService.getVarHql(hql4Sel, listHql2(), accountType);
		}
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		StringBuffer sb = new StringBuffer();
		sb.append(hql4Export);
		Map  parMap = (Map) session.get("grantedMap");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			Map searchQuery = new HashMap();
			searchQuery = (Map) session.get("searchQuery");
			if (startDate==null||endDate==null) {
				return HSSFExport.commonExportExcel(new ArrayList(), header, title);
			}
			parMap.put("startDate", df.format(startDate));
			parMap.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				sb.append(" and vari.deptInstAuditDate is not null and to_char(vari.deptInstAuditDate,'yyyy-MM-dd')>=:startDate  ");
				sb.append(" and to_char(vari.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				sb.append(" and vari.universityAuditDate is not null and to_char(vari.universityAuditDate,'yyyy-MM-dd')>=:startDate ");
				sb.append(" and to_char(vari.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				sb.append(" and vari.provinceAuditDate is not null and to_char(vari.provinceAuditDate,'yyyy-MM-dd')>=:startDate ");
				sb.append(" and to_char(vari.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				sb.append(" and ( (vari.finalAuditDate is not null and to_char(vari.finalAuditDate,'yyyy-MM-dd')>=:startDate ");
				sb.append(" and to_char(vari.finalAuditDate,'yyyy-MM-dd')<=:endDate) ");
			}
			int resultStatus,saveStatus;
			if (session.containsKey("auditStatusauditStatus")) {
				auditStatus = (Integer) session.get("auditStatusauditStatus");
			}
			if(auditStatus == -1){
				auditStatus = 0;
			}
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			parMap.put("auditStatus",  saveStatus);
			parMap.put("auditResult", resultStatus);
			parMap.put("startDate", df.format(startDate));
			parMap.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				sb.append(" or (vari.deptInstAuditStatus =:auditStatus and vari.deptInstAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				sb.append(" or (vari.universityAuditStatus =:auditStatus and vari.universityAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.PROVINCE)){
				sb.append(" or (vari.provinceAuditStatus =:auditStatus and vari.provinceAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				sb.append(" or (vari.finalAuditStatus =:auditStatus and vari.finalAuditResult =:auditResult ) )");
			}
		}
		if (null != (String) session.get("whereHql")) {
			String whereString = (String) session.get("whereHql");
			sb.append(whereString);
		}
		
		HqlTool hqlTool = new HqlTool(sb.toString());
		sb.append(" group by " + hqlTool.getSelectClause() + ", vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate)");
		List list = dao.query(sb.toString(), parMap);
		
		//导出结果组装
		String[] varItemInfo = new String[]{
				"变更项目成员（含负责人）", "变更项目管理机构", "变更成果形式", "变更项目名称", "变更内容有重大调整",
				"延期", "自行中止项目", "申请撤项", "其他"
		};
		
		String[] varContentInfo = new String[]{"延期至"};
		for(int i = 0; i < list.size(); i++){
			Object[] oo	= (Object[]) list.get(i);
			String varId = (String)oo[0];
			Map parMap2 = new HashMap();
			parMap2.put("varId", varId);
			
			List variList = dao.query("select vari.changeMember, vari.changeAgency, vari.changeProductType, vari.changeName, vari.changeContent, " +
				"vari.postponement, vari.stop, vari.withdraw, vari.other from " + variationClassName() + " vari where vari.id =:varId", parMap2);
			
			//变更事项拼接
			String item = "";
			if("instp".equals(projectType())){
				if(variList.size() > 0){
					Object[] varItems = (Object[])variList.get(0);
					for(int j = 0; j < varItems.length; j++){
						int varItemValue = (Integer)varItems[j];
						if(varItemValue == 1){//如果值为1，则添加当前变更事项至item，否则不添加
							item += ("".equals(item)) ? varItemInfo[j] : "; " + varItemInfo[j];
						}
					}
				}
				oo[8] = item;//变更事项
				
				//变更内容，拼接”延期至”与延期后完成时间
				List variList4Content = dao.query("select to_char(vari.newOnceDate,'yyyy-MM-dd') from " + variationClassName() + " vari where vari.id =:varId", parMap2);
				String content = "";//变更内容
				if(variList4Content.size() > 0){
					String varItems = (String) variList4Content.get(0);
					for(int j = 0; j < variList4Content.size(); j++){
						content = (null != varItems && !varItems.equals("null") && !varItems.isEmpty()) ? varContentInfo[j] + varItems : "";
					}
				}
				oo[9] = content;//变更内容
				list.set(i, oo);
			} else {
				if(variList.size() > 0){
					Object[] varItems = (Object[])variList.get(0);
					for(int j = 0; j < varItems.length; j++){
						int varItemValue = (Integer)varItems[j];
						if(varItemValue == 1){//如果值为1，则添加当前变更事项至item，否则不添加
							item += ("".equals(item)) ? varItemInfo[j] : "; " + varItemInfo[j];
						}
					}
				}
				oo[7] = item;//变更事项
				
				//变更内容，拼接”延期至”与延期后完成时间
				List variList4Content = dao.query("select to_char(vari.newOnceDate,'yyyy-MM-dd') from " + variationClassName() + " vari where vari.id =:varId", parMap2);
				String content = "";//变更内容
				if(variList4Content.size() > 0){
					String varItems = (String) variList4Content.get(0);
					for(int j = 0; j < variList4Content.size(); j++){
						content = (null != varItems && !varItems.equals("null") && !varItems.isEmpty()) ? varContentInfo[j] + varItems : "";
					}
				}
				oo[8] = content;//变更内容
				list.set(i, oo);
			}
			
			
			//拼接同意变更事项（如果同意变更且只同意部分变更事项，则在“同意变更”后拼接已同意变更的事项）
			List finalAuditResultDetailList = dao.query("select vari.finalAuditResultDetail from  " + variationClassName() + " vari where vari.id =:varId", parMap2);
			if(finalAuditResultDetailList.size() > 0){
				String finalAuditResultDetail = (String) finalAuditResultDetailList.get(0);
				String varApproveName = "";
				if (null != finalAuditResultDetail){
					varApproveName = this.projectService.getVarApproveNameForExport(finalAuditResultDetail);
				}else{
					varApproveName = "";
				}
				if("instp".equals(projectType())){
					if(null != oo[10] && (Integer)oo[10] == 1){
						oo[10] = "不同意变更";
					}else if(null != oo[10] && (Integer)oo[10] == 2){
						String item1 = item.replaceAll("[^\\w\\u4e00-\\u9fa5а]+", "");//将申请的变更事项中非汉字、字母、数字、下划线的内容置为空，此处只留汉字，去除分号
						if (varApproveName != null) {
							String varApproveName1 = varApproveName.replaceAll("[^\\w\\u4e00-\\u9fa5а]+", "");//将同意的变更事项中非汉字、字母、数字、下划线的内容置为空，此处只留汉字，去除分号
							//比较变更申请的变更事项和同意变更的变更事项的长度，若相同则不显示；不同，则显示已经同意的变更事项
							if(varApproveName1.length() == item1.length()){
								oo[10] = "同意变更";
							}else {
								oo[10] = "同意变更" + varApproveName;
							}
						} else {
							oo[9] = "待审";
						}
					}	
				}else {
					if(null != oo[9] && (Integer)oo[9] == 1){
						oo[9] = "不同意变更";
					}else if(null != oo[9] && (Integer)oo[9] == 2){
						String item1 = item.replaceAll("[^\\w\\u4e00-\\u9fa5а]+", "");//将非汉字、字母、数字、下划线的内容置为空，此处只留汉字，去除分号
						if (varApproveName != null) {
							String varApproveName1 = varApproveName.replaceAll("[^\\w\\u4e00-\\u9fa5а]+", "");//将非汉字、字母、数字、下划线的内容置为空，此处只留汉字，去除分号
							//比较变更申请的变更事项和同意变更的变更事项的长度，若相同则不显示；不同，则显示已经同意的变更事项
							if(varApproveName1.length() == item1.length()){
								oo[9] = "同意变更";
							}else {
								oo[9] = "同意变更" + varApproveName;
							}
						} else {
							oo[9] = null;
						}
					} else {
						oo[9] = "待审";
					}
				}
			}
			
		}
		
		List dataList = new ArrayList();
		int index = 1;
		if("instp".equals(projectType())){
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];
				
				//如果是不同意变更，才显示备注信息,否则不显示
				if(o[10] != "不同意变更"){
					o[13] = "";
				}
				data[0] = index++;
				data[1] = o[0];//项目结项ID
				data[2] = o[1];//项目名称
				data[3] = o[5];//项目类别
				data[4] = o[6];//项目年度
				data[5] = o[7];//项目批准号
				data[6] = o[3];//依托高校
				data[7] = o[4];//依托基地
				data[8] = o[2];//项目负责人
				data[9] = o[8];//变更事项
				data[10] = o[9];//变更内容
				data[11] = o[10];//是否同意
				data[12] = o[11];//变更时间
				data[13] = o[12] != null && (Integer)o[12] == 1 ? "在研" : o[12] != null && (Integer)o[12] == 2 ? "已结项" : o[12] != null && (Integer)o[12] == 3 ? "已中止" : o[12] != null && (Integer)o[12] == 4 ? "已撤项" : "";//项目状态
				data[14] = o[13];//备注
				dataList.add(data);
			}
		}else {
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];
				
				//如果是不同意变更，才显示备注信息,否则不显示
				if(o[9] != "不同意变更"){
					o[12] = "";
				}
				
				data[0] = index++;
				data[1] = o[0];//项目结项ID
				data[2] = o[1];//项目名称
				data[3] = o[4];//项目类别
				data[4] = o[5];//项目年度
				data[5] = o[6];//项目批准号
				data[6] = o[3];//依托高校
				data[7] = o[2];//项目负责人
				data[8] = o[7];//变更事项
				data[9] = o[8];//变更内容
				data[10] = o[9];//是否同意
				data[11] = o[10];//变更时间
				data[12] = o[11] != null && (Integer)o[11] == 1 ? "在研" : o[11] != null && (Integer)o[11] == 2 ? "已结项" : o[11] != null && (Integer)o[11] == 3 ? "已中止" : o[11] != null && (Integer)o[11] == 4 ? "已撤项" : "";//项目状态
				data[13] = o[12];//备注
				dataList.add(data);
			}
		}
		return HSSFExport.commonExportExcel(dataList, header, title);
	}

	public String getDefaultSelectCode() {
		return defaultSelectCode;
	}
	public void setDefaultSelectCode(String defaultSelectCode) {
		this.defaultSelectCode = defaultSelectCode;
	}
	public List<String> getSelectIssue() {
		return selectIssue;
	}
	public void setSelectIssue(List<String> selectIssue) {
		this.selectIssue = selectIssue;
	}
	public List<String> getSelectProductType() {
		return selectProductType;
	}
	public void setSelectProductType(List<String> selectProductType) {
		this.selectProductType = selectProductType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public int getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(int submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getDefaultSelectProductTypeCode() {
		return defaultSelectProductTypeCode;
	}
	public void setDefaultSelectProductTypeCode(String defaultSelectProductTypeCode) {
		this.defaultSelectProductTypeCode = defaultSelectProductTypeCode;
	}
	public String getDeptInstId() {
		return deptInstId;
	}
	public void setDeptInstId(String deptInstId) {
		this.deptInstId = deptInstId;
	}
	public int getDeptInstFlag() {
		return deptInstFlag;
	}
	public void setDeptInstFlag(int deptInstFlag) {
		this.deptInstFlag = deptInstFlag;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public Date getNewDate1() {
		return newDate1;
	}
	public void setNewDate1(Date newDate1) {
		this.newDate1 = newDate1;
	}
	@SuppressWarnings("rawtypes")
	public List getProjectList() {
		return projectList;
	}
	@SuppressWarnings("rawtypes")
	public void setProjectList(List projectList) {
		this.projectList = projectList;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<String> getVariationIds() {
		return variationIds;
	}
	public void setVariationIds(List<String> variationIds) {
		this.variationIds = variationIds;
	}
	public String getVarId() {
		return varId;
	}
	public void setVarId(String varId) {
		this.varId = varId;
	}
	public String getAgeDeptInst() {
		return ageDeptInst;
	}
	public void setAgeDeptInst(String ageDeptInst) {
		this.ageDeptInst = ageDeptInst;
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
	public String getOldDeptInstId() {
		return oldDeptInstId;
	}
	public void setOldDeptInstId(String oldDeptInstId) {
		this.oldDeptInstId = oldDeptInstId;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String[] getFileIdsforVarPost() {
		return fileIdsforVarPost;
	}
	public void setFileIdsforVarPost(String[] fileIdsforVarPost) {
		this.fileIdsforVarPost = fileIdsforVarPost;
	}
	public String[] getVarFileId() {
		return varFileId;
	}
	public void setVarFileId(String[] varFileId) {
		this.varFileId = varFileId;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public int getVarResult() {
		return varResult;
	}
	public void setVarResult(int varResult) {
		this.varResult = varResult;
	}
	public int getVarResultFlag() {
		return varResultFlag;
	}
	public void setVarResultFlag(int varResultFlag) {
		this.varResultFlag = varResultFlag;
	}
	public Date getVarDate() {
		return varDate;
	}
	public void setVarDate(Date varDate) {
		this.varDate = varDate;
	}
	public int getVarImportedStatus() {
		return varImportedStatus;
	}
	public void setVarImportedStatus(int varImportedStatus) {
		this.varImportedStatus = varImportedStatus;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberUnit() {
		return memberUnit;
	}
	public void setMemberUnit(String memberUnit) {
		this.memberUnit = memberUnit;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getVarImportedOpinion() {
		return varImportedOpinion;
	}
	public void setVarImportedOpinion(String varImportedOpinion) {
		this.varImportedOpinion = varImportedOpinion;
	}
	public String getOldProductTypeCode() {
		return oldProductTypeCode;
	}
	public void setOldProductTypeCode(String oldProductTypeCode) {
		this.oldProductTypeCode = oldProductTypeCode;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getVarOpinionFeedback() {
		return varOpinionFeedback;
	}
	public void setVarOpinionFeedback(String varOpinionFeedback) {
		this.varOpinionFeedback = varOpinionFeedback;
	}
	public String getProductTypeOther() {
		return productTypeOther;
	}
	public void setProductTypeOther(String productTypeOther) {
		this.productTypeOther = productTypeOther;
	}
	public String getOldProductTypeOther() {
		return oldProductTypeOther;
	}
	public void setOldProductTypeOther(String oldProductTypeOther) {
		this.oldProductTypeOther = oldProductTypeOther;
	}
	public List<String> getVarSelectIssue() {
		return varSelectIssue;
	}
	public void setVarSelectIssue(List<String> varSelectIssue) {
		this.varSelectIssue = varSelectIssue;
	}
	public String getDefaultSelectApproveVarCode() {
		return defaultSelectApproveVarCode;
	}
	public void setDefaultSelectApproveVarCode(String defaultSelectApproveVarCode) {
		this.defaultSelectApproveVarCode = defaultSelectApproveVarCode;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public Date getNewOnceDate() {
		return newOnceDate;
	}
	public void setNewOnceDate(Date newOnceDate) {
		this.newOnceDate = newOnceDate;
	}
	public String getVarContent() {
		return varContent;
	}
	public void setVarContent(String varContent) {
		this.varContent = varContent;
	}
	public String getVariId() {
		return variId;
	}
	public void setVariId(String variId) {
		this.variId = variId;
	}
	public ProjectFee getOldFee() {
		return oldFee;
	}
	public void setOldFee(ProjectFee oldFee) {
		this.oldFee = oldFee;
	}
	public ArrayList<Object> getVarListForSelect() {
		return varListForSelect;
	}
	public void setVarListForSelect(ArrayList<Object> varListForSelect) {
		this.varListForSelect = (ArrayList<Object>) varListForSelect;
	}
	public String getVariationReason() {
		return variationReason;
	}
	public void setVariationReason(String variationReason) {
		this.variationReason = variationReason;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	
	public Date getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	public Date getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}
	public Date getAuditStartDate() {
		return auditStartDate;
	}
	public void setAuditStartDate(Date auditStartDate) {
		this.auditStartDate = auditStartDate;
	}
	public Date getAuditEndDate() {
		return auditEndDate;
	}
	public void setAuditEndDate(Date auditEndDate) {
		this.auditEndDate = auditEndDate;
	}
	public String getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	public int getVarResultPublish() {
		return varResultPublish;
	}
	public void setVarResultPublish(int varResultPublish) {
		this.varResultPublish = varResultPublish;
	}
	
	
	
}