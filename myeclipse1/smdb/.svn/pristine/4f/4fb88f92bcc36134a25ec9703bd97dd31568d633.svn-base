package csdc.action.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import csdc.bean.EntrustFunding;
import csdc.bean.GeneralFunding;
import csdc.bean.GeneralGranted;
import csdc.bean.InstpFunding;
import csdc.bean.KeyFunding;
import csdc.bean.PostFunding;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.service.IMessageAuxiliaryService;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DoubleTool;
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
 * 项目中检父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目中检申请共用的相关方法
 * @author 余潜玉
 */
public abstract class MidinspectionApplyAction extends ProjectBaseAction{
	
	private static final long serialVersionUID = 1L;
	protected static final String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
	"so.name, app.disciplineType, app.year, midi.status, midi.file, midi.id, midi.finalAuditStatus, midi.finalAuditResult";
	protected ProjectMidinspection midinspection;	
	protected String filepath;//文件路径
	protected int midApplicantSubmitStatus;//提交状态
	protected int midResult;//中检结果	1：不同意	2：同意
	protected int midImportedStatus;//中检结果录入状态
	protected String midImportedOpinion;//录入中检意见
	protected String midOpinionFeedback;//录入中检意见（反馈给项目负责人）
	protected Date midDate;//中检时间
	protected String note;//备注
	protected String projectName,projectNumber,researchType,projectSubtype,projectTopic,dtypeNames,
	applicant,university,divisionName,provinceName,memberName,discipline;//高级检索条件
	protected int applicantSubmitStatus;//高级检索条件
	protected int startYear,endYear,auditStatus,isApproved,projectStatus;//高级检索条件
	protected Date startDate,endDate;////高级检索条件
	
	protected Date applyStartDate;
	protected Date applyEndDate;
	protected Date auditStartDate;
	protected Date auditEndDate;
	protected String isPublish;
	protected int midResultPublish; //中检审核结果是否允许发布标志位  0：不允许发布 1：允许发布
	//导出
	private String fileFileName;
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;//标题提交上来的特征码list
	protected String[] midFileId;	//标题提交上来的特征码list,用于中检后上传申请书
	protected String uploadKey;//文件上传授权码
	protected int midFlag;//添加中检是否成功标志位	1：添加成功
	protected int timeFlag = 1;//业务时间是否有效标志位  1：有效
	private String midId;//当前中检id

	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"负责人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"提交状态",
		"中检状态",
		"中检状态",
		"中检状态",
		"中检状态",
		"中检时间",
		"中检时间",
		"中检时间",
		"中检时间",
		"中检时间"
	};
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.disciplineType",
		"app.year",
		"midi.applicantSubmitStatus",
		"midi.deptInstAuditStatus, midi.deptInstAuditResult",
		"midi.universityAuditStatus, midi.universityAuditResult",
		"midi.provinceAuditStatus, midi.provinceAuditResult",
		"midi.finalAuditStatus, midi.finalAuditResult",
		"midi.applicantSubmitDate desc",
		"midi.deptInstAuditDate desc",
		"midi.universityAuditDate desc",
		"midi.provinceAuditDate desc",
		"midi.finalAuditDate desc"
	};//排序列
	public abstract String listHql2();
	public abstract String listHql3();
	public abstract String midinspectionClassName();//项目中检类名
	public String[] columnName() {
		return MidinspectionApplyAction.CCOLUMNNAME;
	}
	public String[] column() {
		return MidinspectionApplyAction.COLUMN;
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
		if (null == session.get("midAssistMap") && cloumn != 2 && cloumn != 3 && cloumn != 5) {
			cloumn = 4;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 || cloumn == 4 || cloumn == 5 ) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "app.id");
		} else {
			jsonMap = (Map) session.get("midAssistMap");
		}
		session.put("midAssistMap", jsonMap);
		return SUCCESS;
	}
	
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		Map session = ActionContext.getContext().getSession();
		Account account = loginer.getAccount();
		AccountType accountType = loginer.getCurrentType();
		int columnLabel = 0;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(this.projectService.getMidSearchHQLWordAdd(accountType));
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getMidSimpleSearchHQL(searchType));
		}
		hql.append(this.projectService.getMidSimpleSearchHQLAdd(accountType));
		session.put("grantedMap", map);
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
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
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" group by " + hqlTool.getSelectClause());
		} else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");	
		}
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
	
	//选择单条中检数据发布状态
	@SuppressWarnings("unchecked")
	public String switchPublish() {
		ProjectMidinspection midinspection = (ProjectMidinspection)dao.query(ProjectMidinspection.class, midId);
		if(midinspection != null){
			if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  midinspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}
			if(midinspection.getFinalAuditResultPublish()==0&&midResultPublish==0)
				midinspection.setFinalAuditResultPublish(1);
			else if(midinspection.getFinalAuditResultPublish()==1&&midResultPublish==1)
				midinspection.setFinalAuditResultPublish(0);
			dao.modify(midinspection);
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
		pubSql.append("select mid from "+midinspectionClassName()+" mid where 1=1");
		pubSql.append(" and mid.finalAuditResult!=0 ");
		if (applyStartDate != null) {
			pubSql.append("and mid.applicantSubmitDate >=:applyStartDate ");
			pubMap.put("applyStartDate", applyStartDate);
		}
		if (applyEndDate!=null) {
			pubSql.append("and mid.applicantSubmitDate <=:applyEndDate ");
			pubMap.put("applyEndDate", applyEndDate);
		}
		if(auditStartDate!=null) {
			pubSql.append("and mid.finalAuditDate >=:auditStartDate ");
			pubMap.put("auditStartDate",auditStartDate);
		}
		if(auditEndDate!=null) {
			pubSql.append("and mid.finalAuditDate <=:auditEndDate ");
			pubMap.put("auditEndDate",auditEndDate);
		}
		List<ProjectMidinspection> projectMidinspections = dao.query(pubSql.toString(), pubMap);
		if(projectMidinspections.size()!=0) {
			if(isPublish.equals("1")) {
				for (ProjectMidinspection projectMidinspection : projectMidinspections) {
					projectMidinspection.setFinalAuditResultPublish(1);
					dao.modify(projectMidinspection);
				}
			} else if(isPublish.equals("0")){
				for (ProjectMidinspection projectMidinspection : projectMidinspections) {
					projectMidinspection.setFinalAuditResultPublish(0);
					dao.modify(projectMidinspection);
				}
			}
		}
		return SUCCESS;
	}
	/**
//	取消公开发布
	public String notPublish() {
		for(int i=0;i<entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(entityIds.get(i));
			if(midinspection != null){
				if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  midinspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				midinspection.setFinalAuditResultPublish(0);
				dao.modify(midinspection);
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
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(this.projectService.getMidSearchHQLWordAdd(accountType));
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
		if(projectName!=null && !projectName.isEmpty()){
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
		if(startYear!=-1){
			hql.append(" and app.year>=:startYear");
			map.put("startYear", startYear);
		}
		if(endYear!=-1){
			hql.append(" and app.year<=:endYear");
			map.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(gra.applicantName) like :applicant ");
			map.put("applicant", "%" + applicant + "%");
		}
		if(memberName!=null && !memberName.isEmpty()){
			memberName = memberName.toLowerCase();
			hql.append(" and LOWER(mem.memberName) like :memberName ");
			map.put("memberName", "%" + memberName + "%");
		}
		if(university!=null && !university.isEmpty()){
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university ");
			map.put("university", "%" + university + "%");
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			divisionName = divisionName.toLowerCase();
			hql.append(" and LOWER(gra.divisionName) like :divisionName ");
			map.put("divisionName", "%" + divisionName + "%");
		}
		if(provinceName!=null && !provinceName.isEmpty()){
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				hql.append(" and midi.deptInstAuditDate is not null and to_char(midi.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and midi.universityAuditDate is not null and to_char(midi.universityAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and midi.provinceAuditDate is not null and to_char(midi.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and midi.finalAuditDate is not null and to_char(midi.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				if(startDate == null){
					hql.append(" and midi.deptInstAuditDate is not null ");
				}
				hql.append(" and to_char(midi.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(startDate == null){
					hql.append(" and midi.universityAuditDate is not null ");
				}
				hql.append(" and to_char(midi.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				if(startDate == null){
					hql.append(" and midi.provinceAuditDate is not null ");
				}
				hql.append(" and to_char(midi.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				if(startDate == null){
					hql.append(" and midi.finalAuditDate is not null ");
				}
				hql.append(" and to_char(midi.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		int resultStatus,saveStatus;
		if(auditStatus!=-1){
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			map.put("auditStatus",  saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){ 
				hql.append("  and midi.deptInstAuditStatus =:auditStatus and midi.deptInstAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append("  and midi.universityAuditStatus =:auditStatus and midi.universityAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append("  and midi.provinceAuditStatus =:auditStatus and midi.provinceAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and midi.finalAuditStatus =:auditStatus and midi.finalAuditResult =:auditResult");
			}
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
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isApproved != -1){
			if(isApproved == 0){
				hql.append(" and midi.finalAuditStatus != 3 ");
			}else{
				hql.append(" and midi.finalAuditStatus = 3 and midi.finalAuditResult =:isApproved ");
				map.put("isApproved", isApproved);
			}
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" and (midi.status >= 1 or midi.createMode = 1 or midi.createMode = 2) ");
			if(applicantSubmitStatus != -1){
				map.put("submitStatus",  applicantSubmitStatus);
				hql.append(" and midi.applicantSubmitStatus =:submitStatus");
			}
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql.append(" and (midi.status >= 2 or midi.createMode=1) ");
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" and (midi.status >= 3 or midi.createMode=1) ");
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql.append(" and (midi.status >= 4 or midi.createMode=1) ");
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql.append(" and (midi.status >= 5 or midi.createMode=1) ");
		}
		session.put("grantedMap", map);
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		if(accountType.equals(AccountType.INSTITUTE) ){
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
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" group by " + hqlTool.getSelectClause() + " having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
		} else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");	
		}
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
		if(memberName!=null && !memberName.isEmpty()){
			searchQuery.put("memberName", memberName);
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
			searchQuery.put("submitStatus",  applicantSubmitStatus);
		}
	}
	
	/**
	 * 进入项目中检申请添加页面预处理
	 * @author 余潜玉
	 */
	public String toAdd(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return isTimeValidate();
	}
	/**
	 * 添加项目中检申请公共处理
	 * @param midinspection 中检对象
	 * @author 余潜玉
	 * @return 处理后的项目中检对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectMidinspection doWithAdd(ProjectMidinspection midinspection) throws Exception{
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFile(projectType(), projectid, midApplicantSubmitStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		if(note != null){
			midinspection.setNote(("A"+note).trim().substring(1));
		}else{
			midinspection.setNote(null);
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, midApplicantSubmitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
		midinspection.edit(auditMap);//保存操作结果
		/* 以下代码为跳过部门审核*/
		if(midApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			midinspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(midinspection);
		midinspection.setDfs(dfsId);
		return midinspection;
	}
	
	/**
	 * 添加项目中检申请校验
	 */
	public void validateAdd(){
		validateEdit(1);
	}
	
	/**
	 * 进入项目中检申请修改页面预处理
	 * @author 余潜玉
	 */
	public String toModify(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//中检业务状态1：业务激活0：业务停止
		AccountType accountType = loginer.getCurrentType();
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(projectid);
		if (midinspection.getProjectFee() != null) {
			projectFeeMid = dao.query(ProjectFee.class, midinspection.getProjectFee().getId());
		}
		note = midinspection.getNote();
		midId = midinspection.getId();

		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + midinspection.getId();
		uploadService.resetGroup(groupId);
		if (midinspection.getFile() != null) {
			String[] tempFileRealpath = midinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(midinspection.getDfs() != null && !midinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(midinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = midinspection.getFile().substring(midinspection.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return isTimeValidate();
	}
	
	/**
	 * 修改项目中检申请
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modify() throws Exception {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(projectid);
		String orignFile = midinspection.getFile();
		if(midinspection.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		
		ProjectFee oldprojectFeeMid = null;
		if (midinspection.getProjectFee() != null) {
			oldprojectFeeMid = dao.query(ProjectFee.class, midinspection.getProjectFee().getId());
			oldprojectFeeMid = this.projectService.updateProjectFee(oldprojectFeeMid,projectFeeMid);
			dao.modify(oldprojectFeeMid);
		}else {
			oldprojectFeeMid = this.projectService.setProjectFee(projectFeeMid);
			oldprojectFeeMid.setType(4);
			dao.add(oldprojectFeeMid);
		}
		midinspection.setProjectFee(oldprojectFeeMid);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, midApplicantSubmitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		midinspection.edit(auditMap);//保存操作结果
		/* 以下代码为跳过部门审核*/
		if(midApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			midinspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		//保存上传的文件
		String groupId = "file_" + midinspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFile(projectType(), projectid, midApplicantSubmitStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		if(note != null){
			midinspection.setNote(("A"+note).trim().substring(1));
		}else{
			midinspection.setNote(null);
		}
		dao.modify(midinspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != midinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != midinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(midinspection);
				}else{ //现在没文件
					dmssService.deleteFile(midinspection.getDfs());
					midinspection.setDfs(null);
					dao.modify(midinspection);
				}
			}else{ //原来没有文件
				if(midinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(midinspection);
					midinspection.setDfs(dfsId);
					dao.modify(midinspection);
				}
			}
		}
		midFlag= 1;
		return SUCCESS;
	}
	
	/**
	 * 修改项目中检申请校验
	 */
	public void validateModify(){
		validateEdit(2);
	}
	
	/**
	 * 提交项目中检申请
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(projectid);
		if(midinspection.getStatus() > 1){//未提交的申请才可提交
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		//File midWordFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + midinspection.getFile());
		//projectService.importMidXMLData(projectid, midWordFile);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		midinspection.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		midinspection.edit(auditMap);//部门审核通过
		/* 结束 */
		dao.modify(midinspection);
		return SUCCESS;
	}
	
	/**
	 * 提交项目中检申请校验
	 */
	public void validateSubmit(){
		validateEdit(3);
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
	 * 编辑中检校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void validateEdit(int type){
		String info = "";
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			int grantedYear = this.projectService.getGrantedYear(projectid);
			int midForbid = ((new Date()).getYear() + 1900 > grantedYear + 3) ? 1 : 0;
			if(midForbid == 1){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_FORBID);
				info += ProjectInfo.ERROR_MID_FORBID;
			}
			if(!this.projectService.getDireIdByAppId(appId, granted.getMemberGroupNumber()).contains(loginer.getPerson().getId())){//当前用户是负责人
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0){//中检已通过
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_ALREADY);
				info += ProjectInfo.ERROR_MID_ALREADY;
			}
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0 || this.projectService.getPendingEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过或待处理结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
		}
		//校验业务设置状态
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (midStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if(type == 1){//添加中检
			if(!this.projectService.getPendingMidinspectionByGrantedId(projectid).isEmpty()){//有未处理中检
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DEALING);
				info += ProjectInfo.ERROR_MID_DEALING;
			}
			String groupId = "file_add";
			if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				info += ProjectInfo.ERROR_FILE_NULL;
			} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				info += ProjectInfo.ERROR_FILE_OUT;
			}
//			if(null == fileIds || fileIds.length == 0){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//				info += ProjectInfo.ERROR_FILE_NULL;
//			}else if(fileIds.length > 1){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//				info += ProjectInfo.ERROR_FILE_OUT;
//			}
		}
		if(type == 1 || type == 2){//添加或修改中检申请
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//项目已经结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
			if(note != null && note.length() > 200){
				this.addFieldError("note", ProjectInfo.ERROR_MID_NOTE_OUT);
				info += ProjectInfo.ERROR_MID_NOTE_OUT;
			}
			if(midApplicantSubmitStatus != 2 && midApplicantSubmitStatus != 3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
		}
		// 上传中检word宏的校验
//		if (fileIds != null && fileIds.length == 1 && type != 3) {
//			Map<String, Object> sc = ActionContext.getContext().getApplication();
//			File curFile = null;
//			ProjectMidinspection g = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(projectid);
//			if(type == 3) {//待定
//				curFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + g.getFile());
//			} else {
//			String sessionId = request.getSession().getId();
//			String basePath = ApplicationContainer.sc.getRealPath((String) sc.get("tempUploadPath") + "/" + sessionId);
//			File path = new File(basePath + "/" + fileIds[0]);
//			if (path.exists()){
//				Iterator it = FileUtils.iterateFiles(path, null, false);
//				curFile = it.hasNext()? (File)it.next() : new File("nicaiwobudao31416");
//				}
//			}
//			String fileName = curFile.getName();
//			if (curFile.exists() && !fileName.contains("|") && !fileName.contains(";") && !fileName.contains("\\") && !fileName.contains("/") ){
//				System.out.println("校验中检word...");
//				String wordErrorInfo = projectService.checkWordFileLegal(projectid, g.getProjectType(), curFile, 1);
//				if(wordErrorInfo != null) {
//					System.out.println("wordError " + wordErrorInfo);
//					this.addFieldError(GlobalInfo.ERROR_INFO, wordErrorInfo);
//					info += wordErrorInfo;
//				}
//			}
//		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 删除项目中检申请
	 * @auhtor 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete() {
		for(int i=0;i<entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentMidinspectionByGrantedId(entityIds.get(i));
			if(midinspection != null){
				if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  midinspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				this.projectService.deleteMidinspection(midinspection);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除项目中检申请校验
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete(){
		String info ="";
		String appId = this.projectService.getApplicationIdByGrantedId(entityIds.get(0)).trim();
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DELETE_NULL);
			info += ProjectInfo.ERROR_MID_DELETE_NULL;
		}
		//校验业务设置状态
		midStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (midStatus == 0){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_BUSINESS_STOP);
			info += ProjectInfo.ERROR_BUSINESS_STOP;
		}
		//校验业务时间是否有效
		AccountType accountType = loginer.getCurrentType();
		Date date = new Date();
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		if (deadline != null && deadline.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) < 0){//业务已过截止时间
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_TIME_INVALIDATE);
			info += ProjectInfo.ERROR_TIME_INVALIDATE;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 进入项目中检结果录入添加页面预处理
	 * @author 余潜玉
	 */
	public String toAddResult(){
		midDate = new Date();
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	/**
	 * 添加录入的项目中检结果的公共处理部分
	 * @param midinspection 中检对象
	 * @author 余潜玉
	 * @return 处理后的中检对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public ProjectMidinspection doWithAddResult(ProjectMidinspection midinspection) throws Exception{
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFile(projectType(), projectid, midImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		Date submitDate = this.projectService.setDateHhmmss(midDate);
		midinspection.setApplicantSubmitDate(submitDate);
		midinspection.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, midResult, midImportedStatus, null);
		midinspection.setCreateDate(new Date());
		midinspection.setFinalAuditor(auditInfo.getAuditor());
		midinspection.setFinalAuditorName(auditInfo.getAuditorName());
		midinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		midinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		midinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		midinspection.setCreateMode(1);//设为导入数据
		midinspection.setStatus(5);
		midinspection.setFinalAuditStatus(this.midImportedStatus);
		midinspection.setFinalAuditResult(this.midResult);
		if(midImportedOpinion != null && midImportedOpinion.trim().length() > 0){
			midinspection.setFinalAuditOpinion(("A" + midImportedOpinion).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinion(null);
		}
		if(midOpinionFeedback != null && midOpinionFeedback.trim().length() > 0){
			midinspection.setFinalAuditOpinionFeedback(("A" + midOpinionFeedback).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinionFeedback(null);
		}	
		uploadService.flush(groupId);
//		String dfsId = projectService.uploadToDmss(midinspection);
//		midinspection.setDfs(dfsId);
		return midinspection;
	}
	
	/**
	 * 进入项目中检结果录入修改页面预处理
	 * @author 余潜玉
	 */
	public String toModifyResult(){
		ProjectMidinspection midinspection = (ProjectMidinspection)this.projectService.getCurrentPendingImpMidinspectionByGrantedId(this.projectid);
		if (midinspection.getProjectFee() != null) {
			projectFeeMid = dao.query(ProjectFee.class, midinspection.getProjectFee().getId());
			}
		midDate = midinspection.getFinalAuditDate();
		midId = midinspection.getId();
		midImportedOpinion = midinspection.getFinalAuditOpinion();
		midOpinionFeedback = midinspection.getFinalAuditOpinionFeedback();
		this.midResult = midinspection.getFinalAuditResult();
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + midinspection.getId();
		uploadService.resetGroup(groupId);
		if (midinspection.getFile() != null) {
			String[] tempFileRealpath = midinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(midinspection.getDfs() != null && !midinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(midinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = midinspection.getFile().substring(midinspection.getFile().lastIndexOf("/") + 1);
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
	 * 修改录入的项目中检结果
	 * @author 肖雅
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modifyResult() throws Exception{
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		midinspection = (ProjectMidinspection)this.projectService.getCurrentPendingImpMidinspectionByGrantedId(this.projectid);
		String orignFile = midinspection.getFile();
		if (midinspection.getProjectFee() != null) {
			projectFeeMid = dao.query(ProjectFee.class, midinspection.getProjectFee().getId());
			projectFeeMid = this.doWithAddResultFee(projectFeeMid);
			dao.modify(projectFeeMid);
		}
		//保存上传的文件
		String groupId = "file_" + midinspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFile(projectType(), projectid, midImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		Date submitDate = this.projectService.setDateHhmmss(midDate);
		midinspection.setApplicantSubmitDate(submitDate);
		midinspection.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, midResult, midImportedStatus, null);
		midinspection.setUpdateDate(new Date());
		midinspection.setFinalAuditor(auditInfo.getAuditor());
		midinspection.setFinalAuditorName(auditInfo.getAuditorName());
		midinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		midinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		midinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		midinspection.setFinalAuditStatus(this.midImportedStatus);
		midinspection.setFinalAuditResult(this.midResult);
		if(midImportedOpinion != null && midImportedOpinion.trim().length() > 0){
			midinspection.setFinalAuditOpinion(("A" + midImportedOpinion).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinion(null);
		}
		if(midOpinionFeedback != null && midOpinionFeedback.trim().length() > 0){
			midinspection.setFinalAuditOpinionFeedback(("A" + midOpinionFeedback).trim().substring(1));
		}else{
			midinspection.setFinalAuditOpinionFeedback(null);
		}
		dao.modify(midinspection);
		
//		if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			if (granted.getProjectType().equals("general")) {
//				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (generalFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					generalFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					generalFunding.setStatus(0);
//					dao.modify(generalFunding);
//				}else {
//					GeneralFunding newGeneralFunding = new GeneralFunding();
////					newGeneralFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newGeneralFunding.setStatus(0);
//					newGeneralFunding.setType(2);
//					newGeneralFunding.setGranted(granted);
//					newGeneralFunding.setGrantedId(granted.getId());
//					newGeneralFunding.setProjectType(granted.getProjectType());
//					dao.add(newGeneralFunding);
//				}
//			}else if (granted.getProjectType().equals("instp")) {
//				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (instpFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					instpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					instpFunding.setStatus(0);
//					dao.modify(instpFunding);
//				}else {
//					InstpFunding newInstpFunding = new InstpFunding();
////					newInstpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newInstpFunding.setStatus(0);
//					newInstpFunding.setType(2);
//					newInstpFunding.setGranted(granted);
//					newInstpFunding.setGrantedId(granted.getId());
//					newInstpFunding.setProjectType(granted.getProjectType());
//					dao.add(newInstpFunding);
//				}
//			}else if (granted.getProjectType().equals("key")) {
//				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (keyFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					keyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					keyFunding.setStatus(0);
//					dao.modify(keyFunding);
//				}else {
//					KeyFunding newKeyFunding = new KeyFunding();
////					newKeyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newKeyFunding.setStatus(0);
//					newKeyFunding.setType(2);
//					newKeyFunding.setGranted(granted);
//					newKeyFunding.setGrantedId(granted.getId());
//					newKeyFunding.setProjectType(granted.getProjectType());
//					dao.add(newKeyFunding);
//				}
//			}
//		}
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != midinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != midinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(midinspection);
				}else{ //现在没文件
					dmssService.deleteFile(midinspection.getDfs());
					midinspection.setDfs(null);
					dao.modify(midinspection);
				}
			}else{ //原来没有文件
				if(midinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(midinspection);
					midinspection.setDfs(dfsId);
					dao.modify(midinspection);
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 提交录入的项目中检结果
	 * @author 刘雅琴
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		midinspection = (ProjectMidinspection)this.projectService.getCurrentPendingImpMidinspectionByGrantedId(this.projectid);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		midinspection.setUpdateDate(new Date());
		midinspection.setFinalAuditor(auditInfo.getAuditor());
		midinspection.setFinalAuditorName(auditInfo.getAuditorName());
		midinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		midinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		midinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		midinspection.setFinalAuditStatus(3);
		dao.modify(midinspection);
		
//		if (midinspection.getFinalAuditResult() == 2 && midinspection.getFinalAuditStatus() == 3) {
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			if (granted.getProjectType().equals("general")) {
//				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (generalFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					generalFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					generalFunding.setStatus(0);
//					dao.modify(generalFunding);
//				}else {
//					GeneralFunding newGeneralFunding = new GeneralFunding();
////					newGeneralFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newGeneralFunding.setStatus(0);
//					newGeneralFunding.setType(2);
//					newGeneralFunding.setGranted(granted);
//					newGeneralFunding.setGrantedId(granted.getId());
//					newGeneralFunding.setProjectType(granted.getProjectType());
//					dao.add(newGeneralFunding);
//				}
//			}else if (granted.getProjectType().equals("instp")) {
//				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (instpFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					instpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					instpFunding.setStatus(0);
//					dao.modify(instpFunding);
//				}else {
//					InstpFunding newInstpFunding = new InstpFunding();
////					newInstpFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newInstpFunding.setStatus(0);
//					newInstpFunding.setType(2);
//					newInstpFunding.setGranted(granted);
//					newInstpFunding.setGrantedId(granted.getId());
//					newInstpFunding.setProjectType(granted.getProjectType());
//					dao.add(newInstpFunding);
//				}
//			}else if (granted.getProjectType().equals("key")) {
//				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 2 ", parmap);
//				if (keyFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的40%
////					keyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					keyFunding.setStatus(0);
//					dao.modify(keyFunding);
//				}else {
//					KeyFunding newKeyFunding = new KeyFunding();
////					newKeyFunding.setMidFundFee(DoubleTool.mul(granted.getApproveFee(),0.4));
//					newKeyFunding.setStatus(0);
//					newKeyFunding.setType(2);
//					newKeyFunding.setGranted(granted);
//					newKeyFunding.setGrantedId(granted.getId());
//					newKeyFunding.setProjectType(granted.getProjectType());
//					dao.add(newKeyFunding);
//				}
//			}
//		}
		return SUCCESS;
	}
		
	/**
	 * 添加中检结果校验
	 * @author 余潜玉
	 */
	public void validateAddResult(){
		validateEditResult(1);
	}
	
	/**
	 * 修改中检结果校验
	 * @author 刘雅琴
	 */
	public void validateModifyResult(){
		validateEditResult(2);
	}
	
	/**
	 * 提交中检结果校验
	 * @author 刘雅琴
	 */
	public void validateSubmitResult(){
		validateEditResult(3);
	}
	
	/**
	 * 下载项目中检申请书模板
	 */
	public String downloadTemplate(){
		return SUCCESS;
	}
	
	/**
	 * 文件下载流
	 */
	public InputStream getTargetTemplate() throws Exception{
		//String filename = "/data/template/general/tpl_gen_mid_2008.doc";
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		String filename = "/temp/" + sessionId + "/general/2011midinspection.zip";
		filepath = new String("2011midinspection.zip".getBytes(), "ISO-8859-1");
		return ApplicationContainer.sc.getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		if(!projectService.createMidinspectionZip(projectid, loginer.getPerson().getId(), sessionId, projectType())) {
			addActionError("中检申请文件生成失败");
			return ERROR;
		}
		String filename = "/temp/" + sessionId + "/general/2011midinspection.zip";
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		//System.out.println(">>>>>>>>>>>>gshn");
		//File file1 = new File("F:/教育部人文社会科学研究项目终结报告书.doc");
		// 测试上传导入
		//generalService.importEndinspectionWordXMLData(file1, "4028d898348ebb7d01348ec273200019", projectType());
		return SUCCESS;
	}
	
	/**
	 * 下载项目中检申请书
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
		InputStream downloadStream = null;
		try {
			ProjectMidinspection midinspection = (ProjectMidinspection) this.dao.query(ProjectMidinspection.class, entityId);
			filepath = midinspection.getFile();
			String filename="";
			if(filepath != null && filepath.length()!=0){
				filename=new String(filepath.getBytes("iso8859-1"),"utf-8");
				filepath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
				//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
				if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
					downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
				} else {
					downloadStream = dmssService.download(midinspection.getDfs());
				}
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return downloadStream;
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
			ProjectMidinspection midinspection = (ProjectMidinspection) this.dao.query(ProjectMidinspection.class, this.entityId);
			if(null == midinspection){//中检不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(midinspection.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				filepath = midinspection.getFile();
				String filename = new String(filepath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == midinspection.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != midinspection.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑中检结果校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 刘雅琴
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void validateEditResult(int type){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 2){//结项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			int grantedYear = this.projectService.getGrantedYear(projectid);
			int midForbid = ((new Date()).getYear() + 1900 > grantedYear + 3) ? 1 : 0;
			if(midForbid == 1){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_FORBID);
				info += ProjectInfo.ERROR_MID_FORBID;
			}
		}
		if(type == 1){//添加时校验
			if(!this.projectService.getPendingMidinspectionByGrantedId(projectid).isEmpty()){//没有未处理中检
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DEALING);
				info += ProjectInfo.ERROR_MID_DEALING;
			}
		}
		if(type == 1 || type == 3){//添加提交时校验
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//项目已经结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
		}
		if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size()>0){//中检尚未通过
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_MID_ALREADY);
			info += ProjectInfo.ERROR_MID_ALREADY;
		}
		if(type==1 || type==2){//添加修改时校验
			if(midImportedStatus!=2 && midImportedStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(midResult!=2 && midResult!=1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_RESULT_NULL);
				info += ProjectInfo.ERROR_MID_RESULT_NULL;
			}
			if(midDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_DATE_NULL);
				info += ProjectInfo.ERROR_MID_DATE_NULL;
			}
			if(midImportedOpinion != null && midImportedOpinion.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_OPINION_OUT);
				info += ProjectInfo.ERROR_MID_OPINION_OUT;
			}
			if(midOpinionFeedback != null && midOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_OPINION_OUT);
				info += ProjectInfo.ERROR_MID_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 准备导入上传电子文档
	 * @author 肖雅
	 */
	public String toUploadFileResult(){
		ProjectMidinspection midinspection = (ProjectMidinspection)this.dao.query(ProjectMidinspection.class, midId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		if (midinspection.getFile() != null) {
			String[] tempFileRealpath = midinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(midinspection.getDfs() != null && !midinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(midinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = midinspection.getFile().substring(midinspection.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
//		session.put("entityId", entityId);
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
		ProjectMidinspection midinspection;
		midImportedStatus = 3;
		midinspection = (ProjectMidinspection)this.dao.query(ProjectMidinspection.class, midId);
		String orignFile = midinspection.getFile();
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadMidFileResult(projectType(), projectid, midImportedStatus, curFile, midId);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		midinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		dao.modify(midinspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != midinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != midinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(midinspection);
				}else{ //现在没文件
					dmssService.deleteFile(midinspection.getDfs());
					midinspection.setDfs(null);
					dao.modify(midinspection);
				}
			}else{ //原来没有文件
				if(midinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(midinspection);
					midinspection.setDfs(dfsId);
					dao.modify(midinspection);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 导出变更一览表
	 * @return 中检一览表的excel文件
	 * @author 王鸣
	 */
	public String confirmExportOverView(){
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		if("general".equals(projectType())){
			header = "教育部人文社会科学研究一般项目中检情况一览表";
		}else if ("instp".equals(projectType())){
			header = "教育部人文社会科学研究基地项目中检情况一览表";
		}else if ("post".equals(projectType())){
			header = "教育部人文社会科学研究后期资助项目中检情况一览表";
		}else if ("key".equals(projectType())){
			header = "教育部人文社会科学研究重大攻关项目中检情况一览表";
		}else if ("entrust".equals(projectType())){
			header = "教育部人文社会科学研究委托应急课题中检情况一览表";
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
					"是否同意",
					"中检时间",
					"项目状态",
					"备注"
				};
			hql4Sel = "select midi.id, app.name, gra.applicantName, uni.name, ins.name, so.name, app.year, gra.number," +
					"midi.finalAuditResult, to_char(midi.finalAuditDate,'yyyy-MM-dd'), gra.status, midi.finalAuditOpinionFeedback ";  
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
					"是否同意",
					"中检时间",
					"项目状态",
					"备注"
				};
			hql4Sel = "select midi.id, app.name, gra.applicantName, uni.name, so.name, app.year, gra.number, " +
					"midi.finalAuditResult, to_char(midi.finalAuditDate,'yyyy-MM-dd'), gra.status, midi.finalAuditOpinionFeedback ";
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql4Export = this.projectService.getMidHql(hql4Sel, listHql3(), accountType);
		}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下管理人员
			hql4Export = this.projectService.getMidHql(hql4Sel, listHql2(), accountType);
		}else{//教育部及系统管理员
			hql4Export = this.projectService.getMidHql(hql4Sel, listHql2(), accountType);
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
			if (startDate!=null|endDate!=null) {
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
					sb.append(" and midi.deptInstAuditDate is not null ");
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
					sb.append(" and midi.universityAuditDate is not null ");
				}else if(accountType.equals(AccountType.PROVINCE)){
					sb.append(" and midi.provinceAuditDate is not null ");
				}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
					sb.append(" and ( (midi.finalAuditDate is not null ");
				}
			}
			if (startDate!=null) {
				parMap.put("startDate", df.format(startDate));
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
					sb.append("and to_char(midi.deptInstAuditDate,'yyyy-MM-dd')>=:startDate ");
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
					sb.append("and to_char(midi.universityAuditDate,'yyyy-MM-dd')>=:startDate ");
				}else if(accountType.equals(AccountType.PROVINCE)){
					sb.append("and to_char(midi.provinceAuditDate,'yyyy-MM-dd')>=:startDate ");
				}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
					sb.append("and to_char(midi.finalAuditDate,'yyyy-MM-dd')>=:startDate ");
				}
			}
			if (endDate!=null) {
				parMap.put("endDate", df.format(endDate));
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
					sb.append(" and to_char(midi.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
					sb.append(" and to_char(midi.universityAuditDate,'yyyy-MM-dd')<=:endDate");
				}else if(accountType.equals(AccountType.PROVINCE)){
					sb.append(" and to_char(midi.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
				}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
					sb.append(" and to_char(midi.finalAuditDate,'yyyy-MM-dd')<=:endDate) ");
				}
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
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				sb.append(" or (midi.deptInstAuditStatus =:auditStatus and midi.deptInstAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				sb.append(" or (midi.universityAuditStatus =:auditStatus and midi.universityAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.PROVINCE)){
				sb.append(" or (midi.provinceAuditStatus =:auditStatus and midi.provinceAuditResult =:auditResult ) )");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				sb.append(" or (midi.finalAuditStatus =:auditStatus and midi.finalAuditResult =:auditResult ) )");
			}
		}
		if (null != (String) session.get("whereHql")) {
			String whereString = (String) session.get("whereHql");
			sb.append(whereString);
		}
		
		HqlTool hqlTool = new HqlTool(sb.toString());
		sb.append(" group by " + hqlTool.getSelectClause() + ", midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate)");
		List list = dao.query(sb.toString(), parMap);
		
		List dataList = new ArrayList();
		int index = 1;
		if("instp".equals(projectType())){
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];
				
				data[0] = index++;
				data[1] = o[0];//项目结项ID
				data[2] = o[1];//项目名称
				data[3] = o[5];//项目类别
				data[4] = o[6];//项目年度
				data[5] = o[7];//项目批准号
				data[6] = o[3];//依托高校
				data[7] = o[4];//依托基地
				data[8] = o[2];//项目负责人
				data[9] = o[8]!= null && (Integer)o[8] == 1 ? "不同意" : o[8] != null && (Integer)o[8] == 2 ? "同意" : o[8] != null && (Integer)o[8] == 0 ? "待审" : "";;//是否同意
				data[10] = o[9];//中检时间
				data[11] = o[10] != null && (Integer)o[10] == 1 ? "在研" : o[10] != null && (Integer)o[10] == 2 ? "已结项" : o[10] != null && (Integer)o[10] == 3 ? "已中止" : o[10] != null && (Integer)o[10] == 4 ? "已撤项" : "";//项目状态
				data[12] = o[11];//备注
				dataList.add(data);
			}
		}else {
			for (Object object : list) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];
				data[0] = index++;
				data[1] = o[0];//项目结项ID
				data[2] = o[1];//项目名称
				data[3] = o[4];//项目类别
				data[4] = o[5];//项目年度
				data[5] = o[6];//项目批准号
				data[6] = o[3];//依托高校
				data[7] = o[2];//项目负责人
				data[8] = o[7]!= null && (Integer)o[7] == 1 ? "不同意" : o[7] != null && (Integer)o[7] == 2 ? "同意" : o[7] != null && (Integer)o[7] == 0 ? "待审" : "";//是否同意
				data[9] = o[8];//变更时间
				data[10] = o[9] != null && (Integer)o[9] == 1 ? "在研" : o[9] != null && (Integer)o[9] == 2 ? "已结项" : o[9] != null && (Integer)o[9] == 3 ? "已中止" : o[9] != null && (Integer)o[9] == 4 ? "已撤项" : "";//项目状态
				data[11] = o[10];//备注
				dataList.add(data);
			}
		}
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getMidApplicantSubmitStatus() {
		return midApplicantSubmitStatus;
	}
	public void setMidApplicantSubmitStatus(int midApplicantSubmitStatus) {
		this.midApplicantSubmitStatus = midApplicantSubmitStatus;
	}
	public int getMidResult() {
		return midResult;
	}
	public void setMidResult(int midResult) {
		this.midResult = midResult;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	public int getMidImportedStatus() {
		return midImportedStatus;
	}
	public void setMidImportedStatus(int midImportedStatus) {
		this.midImportedStatus = midImportedStatus;
	}
	public String getMidImportedOpinion() {
		return midImportedOpinion;
	}
	public void setMidImportedOpinion(String midImportedOpinion) {
		this.midImportedOpinion = midImportedOpinion;
	}
	public String getMidOpinionFeedback() {
		return midOpinionFeedback;
	}
	public void setMidOpinionFeedback(String midOpinionFeedback) {
		this.midOpinionFeedback = midOpinionFeedback;
	}
	public Date getMidDate() {
		return midDate;
	}
	public void setMidDate(Date midDate) {
		this.midDate = midDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
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
	public String getProjectTopic() {
		return projectTopic;
	}
	public void setProjectTopic(String projectTopic) {
		this.projectTopic = projectTopic;
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
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
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
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
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
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String[] getMidFileId() {
		return midFileId;
	}
	public void setMidFileId(String[] midFileId) {
		this.midFileId = midFileId;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public int getMidFlag() {
		return midFlag;
	}
	public void setMidFlag(int midFlag) {
		this.midFlag = midFlag;
	}
	public int getTimeFlag() {
		return timeFlag;
	}
	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}
	public ProjectMidinspection getMidinspection() {
		return midinspection;
	}
	public void setMidinspection(ProjectMidinspection midinspection) {
		this.midinspection = midinspection;
	}
	public String getMidId() {
		return midId;
	}
	public void setMidId(String midId) {
		this.midId = midId;
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
	public int getMidResultPublish() {
		return midResultPublish;
	}
	public void setMidResultPublish(int midResultPublish) {
		this.midResultPublish = midResultPublish;
	}
	
}
