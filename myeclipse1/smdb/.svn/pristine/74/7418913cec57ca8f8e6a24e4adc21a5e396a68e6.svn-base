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
import csdc.bean.GeneralGranted;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectData;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.SystemOption;
import csdc.dao.SystemOptionDao;
import csdc.service.IUploadService;
import csdc.service.ext.IPersonExtService;
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
 * 项目结项父类管理
 * 定义了子类需要实现的抽象方法并实现了所有项目结项申请共用的相关方法
 * @author 余潜玉
 */
public abstract class EndinspectionApplyAction extends ProjectBaseAction {
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	@Autowired
	protected IPersonExtService personExtService;

	private static final long serialVersionUID = -4553441992499898172L;
	protected static final String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
	"so.name, gra.status, app.year, endi.status, endi.file, endi.id, endi.finalAuditStatus, endi.finalAuditResultEnd";
	protected String filepath;//文件下载
	protected int uploadEndApply;//是否提交了结项申请书:0未提交；1已提交
	protected int endApplicantSubmitStatus;//结项提交状态
	protected ProjectData projectData;//结项研究数据
	protected int isApplyExcellent;//是否申请优秀成果
	protected int isApplyNoevaluation;//是否申请免鉴定
	protected int endResult;//结项结果：1不同意；2同意
	protected int endNoauditResult;//申请免鉴定结果
	protected int endExcellentResult;//申请优秀成果结果
	protected int endImportedStatus;//结项结果录入状态
	protected String endImportedOpinion;//录入结项意见
	protected String endOpinionFeedback;//录入结项意见（反馈给项目负责人）
	protected String endProductInfo;//结项成果信息
	protected String endMember;//结项主要参加人姓名
	protected Date endDate;//结项时间
	protected String endCertificate;//结项证书编号
	protected String note;//备注
	protected String projectName,projectNumber,researchType,projectSubtype,projectTopic,applicant,university,productType,productTypeOther,divisionName,provinceName,memberName,discipline;//高级检索条件
	protected int projectStatus,startYear,endYear,auditStatus,isApproved,reviewStatus,finalAuditStatus,applicantSubmitStatus;//高级检索条件
	protected Date startDate;////高级检索条件
	protected Integer repeatFlag;//高级检索条件,是否显示多条项目数据，1：是，0：否，默认否

	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;	//标题提交上来的特征码list
	protected String[] endFileId;	//标题提交上来的特征码list,用于结项后上传申请书
	protected String uploadKey;	//文件上传授权码
	protected int modifyFlag;//修改类别 1:结项审核结束后修改
	protected int endResultPublish; //结项审核结果是否允许发布标志位  0：不允许发布 1：允许发布
	protected int endFlag;//添加结项是否成功标志位	1：添加成功
	protected int varPending;//是否待处理变更 1：是 0：否
	protected int timeFlag = 1;//业务时间是否有效标志位  1：有效
	private String endId;//结项id
	private String fileFileName;

	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"负责人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"提交状态",
		"审核状态",
		"审核状态",
		"审核状态",
		"审核状态",
		"鉴定状态",
		"结项状态",
		"结项时间",
		"结项时间",
		"结项时间",
		"结项时间",
		"结项时间"
	};
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"gra.status",
		"app.year",
		"endi.applicantSubmitStatus",
		"endi.deptInstAuditStatus, endi.deptInstResultEnd",
		"endi.universityAuditStatus, endi.universityResultEnd",
		"endi.provinceAuditStatus, endi.provinceResultEnd",
		"endi.ministryAuditStatus, endi.ministryResultEnd",
		"endi.reviewStatus, endi.reviewResult",
		"endi.finalAuditStatus, endi.finalAuditResultEnd",
		"endi.applicantSubmitDate desc",
		"endi.deptInstAuditDate desc",
		"endi.universityAuditDate desc",
		"endi.provinceAuditDate desc",
		"endi.finalAuditDate desc",
	};//排序列
	public abstract String endinspectionClassName();//项目结项类类名
	public abstract String midinspectionClassName();//项目中检类类名
	public abstract String grantedClassName();//项目立项类类名
	public abstract String fundingClassName();//项目拨款类类名
	public abstract String listHql2();
	public abstract String listHql3();
	public String[] columnName() {
		return EndinspectionApplyAction.CCOLUMNNAME;
	}
	public String[] column() {
		return EndinspectionApplyAction.COLUMN;
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
		if (null == session.get("endAssistMap") && cloumn != 2 && cloumn != 3 && cloumn != 5) {
			cloumn = 4;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 || cloumn == 4 || cloumn == 5 ) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "app.id");
		} else {
			jsonMap = (Map) session.get("endAssistMap");
		}
		session.put("endAssistMap", jsonMap);
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
		AccountType accountType = account.getType();
		int columnLabel = 0;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(this.projectService.getEndSimpleSearchHQLWordAdd(accountType));
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getEndSimpleSearchHQL(searchType));	
		}
		hql.append(this.projectService.getEndSimpleSearchHQLAdd(accountType));
		session.put("grantedMap", map);
		if (null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 14;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 15;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 16;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 17;
		}else{
			columnLabel =  0;
		}
		HqlTool hqlTool = new HqlTool(hql.toString());
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" group by " + hqlTool.getSelectClause() + " having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		}else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		}
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(57);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
			null
		};
	}
	//选择单条结项数据发布状态
	@SuppressWarnings("unchecked")
	public String switchPublish() {
		ProjectEndinspection endinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		if(endinspection != null){
			if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  endinspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}
			if(endinspection.getFinalAuditResultPublish()==0&&endResultPublish==0)
				endinspection.setFinalAuditResultPublish(1);
			else if(endinspection.getFinalAuditResultPublish()==1&&endResultPublish==1)
				endinspection.setFinalAuditResultPublish(0);
			dao.modify(endinspection);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
		}
		return SUCCESS;
	}
	
	//公开发布
	public String publish() {
		for(int i=0;i<entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(entityIds.get(i));
			if(endinspection != null){
				endinspection.setFinalAuditResultPublish(1);
				dao.modify(endinspection);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NOT_EXIST);
			}
		}
		return SUCCESS;
	}
	/**
	//取消公开发布
	public String notPublish() {
		for(int i=0;i<entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(entityIds.get(i));
			if(endinspection != null){
				endinspection.setFinalAuditResultPublish(0);
				dao.modify(endinspection);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NOT_EXIST);
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
		hql.append(this.projectService.getEndAdvSearchHQLWordAdd(accountType));
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
		if (endCertificate != null && !endCertificate.isEmpty()) {
			endCertificate = endCertificate.toLowerCase();
			hql.append(" and LOWER(endi.certificate) like :endCertificate");
			map.put("endCertificate", "%" + endCertificate + "%");
		}
		if(projectName!=null && !projectName.isEmpty()){
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(gra.name) like :projectName");
			map.put("projectName", "%" + projectName + "%");
		}
		if(memberName!=null && !memberName.isEmpty()){
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
		if(productType != null && !productType.isEmpty()){
			String[] productTypes = productType.split(",");
			int len = productTypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("productType" + i, "%" + productTypes[i].toLowerCase() + "%");
					if(("其他").equals(productTypes[i]) && productTypeOther != null && !productTypeOther.isEmpty()){
						map.put("productTypeOther", "%" + productTypeOther + "%");
						hql.append("(LOWER(gra.productType) like :productType" + i + " and LOWER(gra.productTypeOther) like :productTypeOther)");
					}else{
						hql.append("LOWER(gra.productType) like :productType" + i);
					}
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
			hql.append(" and LOWER(gra.applicantName) like :applicant");
			map.put("applicant", "%" + applicant + "%");
		}
		if(university!=null && !university.isEmpty()){
			university = university.toLowerCase();
			hql.append(" and LOWER(uni.name) like :university");
			map.put("university", "%" + university + "%");
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			divisionName = divisionName.toLowerCase();
			hql.append(" and LOWER(gra.divisionName) :divisionName");
			map.put("divisionName", "%" + divisionName + "%");
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(gra.provinceName) like :provinceName");
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
				hql.append(" and endi.deptInstAuditDate is not null and to_char(endi.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and endi.universityAuditDate is not null and to_char(endi.universityAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and endi.provinceAuditDate is not null and to_char(endi.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and endi.finalAuditDate is not null and to_char(endi.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				if(startDate == null){
					hql.append(" and endi.deptInstAuditDate is not null ");
				}
				hql.append(" and to_char(endi.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(startDate == null){
					hql.append(" and endi.universityAuditDate is not null ");
				}
				hql.append(" and to_char(endi.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				if(startDate == null){
					hql.append(" and endi.provinceAuditDate is not null ");
				}
				hql.append(" and to_char(endi.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				if(startDate == null){
					hql.append(" and endi.finalAuditDate is not null ");
				}
				hql.append(" and to_char(endi.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		int resultStatus,saveStatus;
		if(auditStatus!=-1){
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			map.put("auditStatus",  saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){ 
				hql.append("  and endi.deptInstAuditStatus =:auditStatus and endi.deptInstResultEnd =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append("  and endi.universityAuditStatus =:auditStatus and endi.universityResultEnd =:auditResult");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append("  and endi.provinceAuditStatus =:auditStatus and endi.provinceResultEnd =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY)){
				hql.append(" and endi.ministryAuditStatus =:auditStatus and endi.ministryResultEnd =:auditResult");
			}
		}
		if(accountType.equals(AccountType.MINISTRY)){
			if(reviewStatus!=-1){
				saveStatus=reviewStatus/10;
				resultStatus=reviewStatus%10;
				map.put("reviewStatus",  saveStatus);
				map.put("reviewResult", resultStatus);
				hql.append(" and endi.reviewStatus =:reviewStatus and endi.reviewResult =:reviewResult");
			}
		}
		if(accountType.compareTo(AccountType.PROVINCE) < 0){
			if(finalAuditStatus!=-1){
				saveStatus=finalAuditStatus/10;
				resultStatus=finalAuditStatus%10;
				map.put("finalAuditStatus",  saveStatus);
				map.put("finalAuditResult", resultStatus);
				hql.append(" and endi.finalAuditStatus =:finalAuditStatus and endi.finalAuditResultEnd =:finalAuditResult");
			}
			session.put("finalAuditStatusfinalAuditStatus", finalAuditStatus);
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isApproved != -1){
			if(isApproved == 0){
				hql.append(" and endi.finalAuditStatus != 3 ");
			}else{
				hql.append(" and endi.finalAuditStatus = 3 and endi.finalAuditResultEnd =:isApproved ");
				map.put("isApproved", isApproved);
			}
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" and (endi.status >= 1 or endi.createMode=1 or endi.createMode=2) ");
			if(applicantSubmitStatus!=-1){
				map.put("submitStatus",  applicantSubmitStatus);
				hql.append(" and endi.applicantSubmitStatus =:submitStatus");
			}
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql.append(" and (endi.status >= 2 or endi.createMode=1 or endi.createMode=2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" and (endi.status >= 3 or endi.createMode=1 or endi.createMode=2) ");
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql.append(" and (endi.status >= 4 or endi.createMode=1 or endi.createMode=2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql.append(" and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) ");
		}
		session.put("grantedMap", map);
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 14;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 15;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 16;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 17;
		}else{
			columnLabel =  0;
		}
		HqlTool hqlTool = new HqlTool(hql.toString());
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" group by " + hqlTool.getSelectClause());
			
		}else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate ");
		}
		//是否重复显示项目数据
		if (repeatFlag==null||repeatFlag!=1) {
			hql.append(" having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		}
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
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
		if(endCertificate!=null && !endCertificate.isEmpty()){
			searchQuery.put("endCertificate", endCertificate);
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
		if (projectStatus != -1) {
			searchQuery.put("projectStatus", projectStatus);
		}
		if(productType != null && !productType.isEmpty()){
			searchQuery.put("productType", productType);
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
		if(discipline != null && !discipline.isEmpty()){
			searchQuery.put("discipline", discipline);
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
		if (accountType.equals(AccountType.MINISTRY) && reviewStatus != -1) {
			searchQuery.put("reviewStatus",  reviewStatus);
		}
		if (accountType.compareTo(AccountType.PROVINCE) < 0 && finalAuditStatus != -1) {
			searchQuery.put("finalAuditStatus",  finalAuditStatus);
		}
		if(accountType.compareTo(AccountType.MINISTRY) > 0 && isApproved != -1){
			searchQuery.put("isApproved", isApproved);
		}
		if(applicantSubmitStatus != -1 && (accountType.within(AccountType.EXPERT, AccountType.STUDENT))) {
			searchQuery.put("submitStatus",  applicantSubmitStatus);
		}
		if (repeatFlag!=null) {
			searchQuery.put("repeatFlag", repeatFlag);
		}
	}

	/**
	 * 下载项目结项申请书模板
	 */
	public String downloadTemplate(){
		return SUCCESS;
	}
	
	/**
	 * 模板文件下载流
	 */
	public InputStream getTargetTemplate() throws Exception{
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		String filename = "/temp/" + sessionId + "/general/2011endinspection.zip";
		filepath = new String("2011endinspection.zip".getBytes(), "ISO-8859-1");
		return ApplicationContainer.sc.getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验
	 */
	@SuppressWarnings("unchecked")
	public String validateTemplate()throws Exception{
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		if(!projectService.createEndinspectionZip(projectid, loginer.getPerson().getId(), sessionId, projectType())) {
			addActionError("结项申请文件生成失败");
			return ERROR;
		}
		String filename = "/temp/" + sessionId + "/general/2011endinspection.zip";
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
		}
		return SUCCESS;
	}
	
	/**
	 * 下载项目结项申请书
	 * @author 余潜玉
	 */
	public String downloadApply()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 结项申请书文件下载流
	 * @author 余潜玉
	 */
	public InputStream getTargetFile() throws Exception{
		InputStream downloadStream = null;
		if (entityId==null) {
			return downloadStream;
		}
		ProjectEndinspection endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, entityId);
		if (endinspection == null) {
			return downloadStream;
		}
		filepath = endinspection.getFile();
		String filename = "";
		if(filepath!=null && filepath.length()!=0){
			filename=new String(filepath.getBytes("iso8859-1"),"utf-8");
			filepath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(endinspection.getDfs());
			}
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
			ProjectEndinspection endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, this.entityId);
			if(null == endinspection){//结项不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(endinspection.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				filepath = endinspection.getFile();
				String filename = new String(filepath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == endinspection.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != endinspection.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 下载项目结项研究数据包
	 * @author 余潜玉
	 */
	public String downloadData()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 研究数据包下载流
	 * @author 余潜玉
	 */
	public InputStream getTargetData() throws Exception{
		String filename="";
		InputStream downloadStream = null;
		if (entityId==null) {
			return downloadStream;
		}
		ProjectData res = (ProjectData) this.dao.query(ProjectData.class, this.entityId);
		
		if(filepath!=null && filepath.length()!=0){
			filename = new String(filepath.getBytes("iso8859-1"),"utf-8");
			filepath = new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(res.getDfs());
			}
		}
		return downloadStream;
	}
	
	/**
	 * 研究数据包文件是否存在校验
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	public String validateData()throws Exception{
		if (null == entityId || entityId.trim().isEmpty()) {//id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} else {
			ProjectData res = (ProjectData) this.dao.query(ProjectData.class, this.entityId);
			if(null == res){//结项不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(res.getFile() == null || (!res.getFile().equals(filepath))){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				String filename =  new String(filepath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == res.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != res.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 进入项目结项申请添加页面预处理
	 * @author 余潜玉
	 */
	public String toAdd(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		String groupId1 = "file_add";
		String groupId2 = "file_research_add";
		uploadService.resetGroup(groupId1);
		uploadService.resetGroup(groupId2);
		return isTimeValidate();
	}
	
	/**
	 * 添加项目结项申请的公共处理部分
	 * @param endinspection 结项对象
	 * @author 肖雅
	 * @return 处理后的结项对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectEndinspection doWithAdd(ProjectEndinspection endinspection) throws Exception{
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile, endApplicantSubmitStatus);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(endinspection);
		endinspection.setDfs(dfsId);
		GeneralGranted projectGranted = (GeneralGranted) this.dao.query(GeneralGranted.class,projectid);
		String groupId2 = "file_research_add";
		List<String> files2 = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId2)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndDataFile(curFile,projectGranted);
			//将文件放入list中暂存
			files2.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		projectData.setFile(StringTool.joinString(files2.toArray(new String[0]), "; "));
		uploadService.flush(groupId2);
		String dfsId2 = projectService.uploadToDmss(projectData);
		projectData.setDfs(dfsId2);

		if(note != null){
			endinspection.setNote(("A"+note).trim().substring(1));
		}else{
			endinspection.setNote(null);
		}
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setIsApplyNoevaluation(isApplyNoevaluation);
		String id = dao.add(endinspection);
		endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, id);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, endApplicantSubmitStatus, null);
		int isSubUni = (Integer)this.projectService.isSubordinateUniversityGranted(projectid);
		auditMap.put("auditInfo", auditInfo);
		auditMap.put("isSubUni",isSubUni);
		endinspection.edit(auditMap);
		/* 以下代码为跳过部门审核*/
		if(endApplicantSubmitStatus == 3){//提交申请
			if(endinspection.getIsApplyExcellent() == 1){
				endinspection.setDeptInstResultExcellent(2);
			}
			if(endinspection.getIsApplyNoevaluation() == 1){
				endinspection.setDeptInstResultNoevaluation(2);
			}
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			endinspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		ProjectApplication application = (ProjectApplication)this.projectService.getApplicationFetchDetailByAppId(entityId);
		String code = "";
		if(projectType().equals("general")){
			code = "01";
		}else if(projectType().equals("instp")){
			code = "02";
		}else if(projectType().equals("post")){
			code = "03";
		}else if(projectType().equals("key")){
			code = "04";
		}else if(projectType().equals("entrust")){
			code = "05";
		}
		SystemOption ptype = (SystemOption)this.systemOptionDao.query("projectType", code);
		projectData.setProjectType(ptype);
		projectData.setGranted(projectid);
		projectData.setEndinspection(id);
		projectData.setKeywords(this.projectService.MutipleToFormat(projectData.getKeywords().trim()));
		projectData.setSummary(("A" + projectData.getSummary()).trim().substring(1));
		projectData.setIntroduction(("A" + projectData.getIntroduction()).trim().substring(1));
		projectData.setProducerName(application.getApplicantName());
		projectData.setUniversity(application.getUniversity());
		projectData.setAgencyName(application.getAgencyName());
		projectData.setDepartment(application.getDepartment());
		projectData.setInstitute(application.getInstitute());
		projectData.setDivisionName(application.getDivisionName());
		projectData.setSurveyField(projectData.getSurveyField().trim());
		projectData.setSurveyMethod(projectData.getSurveyMethod().trim());
		projectData.setSubmitDate(new Date());
		if(projectData.getNote()!= null){
			projectData.setNote(("A" + projectData.getNote()).trim().substring(1));
		}else{
			projectData.setNote(null);
		}
		this.dao.add(projectData);
		return endinspection;
	}
	
	/**
	 * 准备修改结项
	 * @return
	 */
	public String toModify(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//结项业务状态1：业务激活0：业务停止
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		AccountType accountType = loginer.getCurrentType();
		//结项申请截止时间
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		if (endinspection.getProjectFee() != null) {
			projectFeeEnd = dao.query(ProjectFee.class, endinspection.getProjectFee().getId());
		}
		isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		isApplyExcellent = endinspection.getIsApplyExcellent();
		note = endinspection.getNote();
		projectData = this.projectService.getProjectDataByEndId(endinspection.getId());
		endId = endinspection.getId();
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + endinspection.getId();
		uploadService.resetGroup(groupId);
		if (endinspection.getFile() != null) {
			String[] tempFileRealpath = endinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(endinspection.getDfs() != null && !endinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(endinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = endinspection.getFile().substring(endinspection.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		String groupId2 = "file_research_" + endinspection.getId();
		uploadService.resetGroup(groupId2);
		if (projectData.getFile() != null) {
			String[] tempFileRealpath = projectData.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(projectData.getDfs() != null && !projectData.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(projectData.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = projectData.getFile().substring(projectData.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId2, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return isTimeValidate();
	}
	
	/**
	 * 修改项目结项申请
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
		ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		String orgEndFile = endinspection.getDfs();
		if(endinspection.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		
		ProjectFee oldprojectFeeEnd = null;
		if (endinspection.getProjectFee() != null) {
			oldprojectFeeEnd = dao.query(ProjectFee.class, endinspection.getProjectFee().getId());
			oldprojectFeeEnd = this.projectService.updateProjectFee(oldprojectFeeEnd,projectFeeEnd);
			dao.modify(oldprojectFeeEnd);
		}else {
			oldprojectFeeEnd = this.projectService.setProjectFee(projectFeeEnd);
			oldprojectFeeEnd.setType(5);
			dao.add(oldprojectFeeEnd);
		}
		endinspection.setProjectFee(oldprojectFeeEnd);
		
		ProjectData oldProjectData = this.projectService.getProjectDataByEndId(endinspection.getId());
		String orgDataFile = oldProjectData.getDfs();
		//保存上传的文件
		//上传结项申请书
		String groupId = "file_" + endinspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile, endImportedStatus);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		uploadService.flush(groupId);
		//DMSS同步 
		if(orgEndFile != endinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orgEndFile){//原来有文件
				if(null != endinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(endinspection);
				}else{ //现在没文件
					dmssService.deleteFile(endinspection.getDfs());
					endinspection.setDfs(null);
					dao.modify(endinspection);
				}
			}else{ //原来没有文件
				if(endinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(endinspection);
					endinspection.setDfs(dfsId);
					dao.modify(endinspection);
				}
			}
		}
		GeneralGranted projectGranted = (GeneralGranted) this.dao.query(GeneralGranted.class,projectid);
		//上传研究数据包
		String groupId2 = "file_research_" + endinspection.getId();
		List<String> files2 = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId2)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndDataFile(curFile,projectGranted);
			//将文件放入list中暂存
			files2.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		oldProjectData.setFile(StringTool.joinString(files2.toArray(new String[0]), "; "));
		uploadService.flush(groupId2);
		//DMSS同步 
		if(orgDataFile != oldProjectData.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orgDataFile){//原来有文件
				if(null != oldProjectData.getFile()){ //现在有文件
					projectService.checkInToDmss(oldProjectData);
				}else{ //现在没文件
					dmssService.deleteFile(oldProjectData.getDfs());
					oldProjectData.setDfs(null);
					dao.modify(oldProjectData);
				}
			}else{ //原来没有文件
				if(oldProjectData.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(oldProjectData);
					oldProjectData.setDfs(dfsId);
					dao.modify(oldProjectData);
				}
			}
		}
		if(note != null){
			endinspection.setNote(("A"+note).trim().substring(1));
		}else{
			endinspection.setNote(null);
		}
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setIsApplyNoevaluation(isApplyNoevaluation);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, endApplicantSubmitStatus, null);
		int isSubUni = (Integer)this.projectService.isSubordinateUniversityGranted(projectid);
		auditMap.put("auditInfo", auditInfo);
		auditMap.put("isSubUni",isSubUni);
		endinspection.edit(auditMap);
		/* 以下代码为跳过部门审核*/
		if(endApplicantSubmitStatus == 3){//提交申请
			if(endinspection.getIsApplyExcellent() == 1){
				endinspection.setDeptInstResultExcellent(2);
			}
			if(endinspection.getIsApplyNoevaluation() == 1){
				endinspection.setDeptInstResultNoevaluation(2);
			}
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			endinspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		oldProjectData.setKeywords(this.projectService.MutipleToFormat(projectData.getKeywords().trim()));
		oldProjectData.setSummary(("A" + projectData.getSummary()).trim().substring(1));
		oldProjectData.setIntroduction(("A" + projectData.getIntroduction()).trim().substring(1));
		oldProjectData.setSurveyField(projectData.getSurveyField().trim());
		oldProjectData.setSurveyMethod(projectData.getSurveyMethod().trim());
		oldProjectData.setStartDate(projectData.getStartDate());
		oldProjectData.setEndDate(projectData.getEndDate());
		oldProjectData.setSubmitDate(new Date());
		if(projectData.getNote() != null){
			oldProjectData.setNote(("A" + projectData.getNote()).trim().substring(1));
		}else{
			oldProjectData.setNote(null);
		}
		this.dao.modify(oldProjectData);
		this.dao.modify(endinspection);
		endFlag = 1;
		return SUCCESS;
	}
	
	/**
	 * 提交项目结项申请
	 * @auhtor 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
		if(endinspection.getStatus() > 1){//未提交的申请才可提交
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		File endWordFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + endinspection.getFile());
		projectService.importEndinspectionWordXMLData(endWordFile, projectid, projectType());
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		endinspection.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		if(endinspection.getIsApplyExcellent() == 1){
			endinspection.setDeptInstResultExcellent(2);
		}
		if(endinspection.getIsApplyNoevaluation() == 1){
			endinspection.setDeptInstResultNoevaluation(2);
		}
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		endinspection.edit(auditMap);//部门审核通过
		/* 结束 */
		dao.modify(endinspection);
		return SUCCESS;
	}
	/**
	 * 添加结项申请的检验
	 * @author 余潜玉
	 */
	public void validateAdd(){
		this.validateEdit(1);
	}
	
	/**
	 * 修改结项申请的检验
	 * @author 余潜玉
	 */
	public void validateModify(){
		this.validateEdit(2);
	}
	
	
	/**
	 * @author 余潜玉
	 */
	public void validateSubmit(){
		this.validateEdit(3);
	}
	
	/**
	 * 编辑结项校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked" })
	public void validateEdit(int type){
		String info = "";
		info = this.doWithValidateEdit(type);
		int grantedYear = this.projectService.getGrantedYear(projectid);
		if(!"post".equals(projectType()) && !"entrust".equals(projectType())){
			int endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
			if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
				info += ProjectInfo.ERROR_END_CANNOT;
			}
			if(type == 1){//添加
				if(this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){//有未处理中检
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
					info += ProjectInfo.ERROR_ENDM_DEALING;
				}
			}else if(type == 3){//提交
				if(this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){//有未处理中检
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
					info += ProjectInfo.ERROR_ENDM_DEALING;
				}
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
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
	 * 项目结项申请校验公用方法
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	public String doWithValidateEdit(int type){
		String info ="";
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if (projectid == null || projectid.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_SUBMIT_NULL);
			info += ProjectInfo.ERROR_END_SUBMIT_NULL;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			if(!this.projectService.getDireIdByAppId(appId, granted.getMemberGroupNumber()).contains(loginer.getPerson().getId())){//当前用户是负责人
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(!this.projectService.getPassEndinspectionByGrantedId(projectid).isEmpty()){//没有已通过结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_ALREADY);
				info += ProjectInfo.ERROR_END_ALREADY;
			}
		}
		//校验业务设置状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (endStatus == 0){
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
		if(type == 1){//添加
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
				info += ProjectInfo.ERROR_VAR_DEALING;
			}
			String groupId = "file_add";
			if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				info += ProjectInfo.ERROR_FILE_NULL;
			} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				info += ProjectInfo.ERROR_FILE_OUT;
			}
			
			String groupId2 = "file_research_add";
			if (uploadService.getGroupFiles(groupId2).isEmpty() || uploadService.getGroupFiles(groupId2).size() == 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				info += ProjectInfo.ERROR_FILE_NULL;
			} else if (!uploadService.getGroupFiles(groupId2).isEmpty() && uploadService.getGroupFiles(groupId2).size() > 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				info += ProjectInfo.ERROR_FILE_OUT;
			}
			
//			if(null == fileIds || fileIds.length == 0){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//				info += ProjectInfo.ERROR_FILE_NULL;
//			}else if(fileIds.length > 2){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//				info += ProjectInfo.ERROR_FILE_OUT;
//			}
		}else if(type == 2){//修改
			ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(projectid);
			String groupId = "file_" + endinspection.getId();
			if (uploadService.getGroupFiles(groupId).isEmpty() || uploadService.getGroupFiles(groupId).size() == 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				info += ProjectInfo.ERROR_FILE_NULL;
			} else if (!uploadService.getGroupFiles(groupId).isEmpty() && uploadService.getGroupFiles(groupId).size() > 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				info += ProjectInfo.ERROR_FILE_OUT;
			}
			
			String groupId2 = "file_research_"+ endinspection.getId();
			if (uploadService.getGroupFiles(groupId2).isEmpty() || uploadService.getGroupFiles(groupId2).size() == 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
				info += ProjectInfo.ERROR_FILE_NULL;
			} else if (!uploadService.getGroupFiles(groupId2).isEmpty() && uploadService.getGroupFiles(groupId2).size() > 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
				info += ProjectInfo.ERROR_FILE_OUT;
			}
//			if(null != fileIds && fileIds.length > 2){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//				info += ProjectInfo.ERROR_FILE_OUT;
//			}
		}else if(type == 3){//提交
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_DEALING);
				info += ProjectInfo.ERROR_VAR_DEALING;
			}
		}
		if(type == 1 || type == 2){//添加或修改
			if(isApplyExcellent != 0 && isApplyExcellent != 1){
				this.addFieldError("isApplyExcellent", ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL);
				info += ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL;
			}
			if(isApplyNoevaluation != 0 && isApplyNoevaluation != 1){
				this.addFieldError("isApplyNoevaluation", ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL);
				info += ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL;
			}
			if(note!=null && note.length()>200){//备注
				this.addFieldError("note", ProjectInfo.ERROR_END_NOTE_OUT);
				info += ProjectInfo.ERROR_END_NOTE_OUT;
			}
			if(endApplicantSubmitStatus!=2 && endApplicantSubmitStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(projectData.getKeywords() == null || projectData.getKeywords().trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_KEYWORDS_NULL);
				info += ProjectInfo.ERROR_END_RESEARCH_KEYWORDS_NULL;
			}else if(projectData.getKeywords().length() > 100){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_KEYWORDS_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_KEYWORDS_OUT;
			}
			if(projectData.getSummary() == null || projectData.getSummary().trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_SUMMARY_NULL);
				info += ProjectInfo.ERROR_END_RESEARCH_SUMMARY_NULL;
			}else if(projectData.getSummary().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_SUMMARY_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_SUMMARY_OUT;
			}
			if(projectData.getIntroduction() == null || projectData.getIntroduction().trim().length() == 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_INTRODUCTION_NULL);
				info += ProjectInfo.ERROR_END_RESEARCH_INTRODUCTION_NULL;
			}else if(projectData.getIntroduction().length() > 20000){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_INTRODUCTION_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_INTRODUCTION_OUT;
			}
			if(projectData.getSurveyField().length() > 50){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_SURVEY_FIELD_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_SURVEY_FIELD_OUT;
			}
			if(projectData.getSurveyMethod().length() > 50){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_SURVEY_METHOD_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_SURVEY_METHOD_OUT;
			}
			if(projectData.getNote().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESEARCH_NOTE_OUT);
				info += ProjectInfo.ERROR_END_RESEARCH_NOTE_OUT;
			}
		}
		// 上传word宏的校验
//		if(endApplicantSubmitStatus == 3 || type == 3) {
//			File curFile = null;
//			if (endApplicantSubmitStatus == 3 && fileIds != null && fileIds.length == 1) {
//				Map<String, Object> sc = ActionContext.getContext().getApplication();
//				String sessionId = request.getSession().getId();
//				String basePath = ApplicationContainer.sc.getRealPath((String)sc.get("tempUploadPath") + "/" + sessionId);
//				File path = new File(basePath + "/" + fileIds[0]);
//				if (path.exists()) {
//					Iterator it = FileUtils.iterateFiles(path, null, false);
//					curFile = it.hasNext() ? (File)it.next() : null;
//				}
//			}
//			if(type == 3) {
//				ProjectEndinspection endinspection = this.projectService.getCurrentEndinspectionByGrantedId(projectid);
//				curFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + endinspection.getFile());
//			}
//			if(curFile != null) {
//				String wordError = projectService.checkWordFileLegal(projectid, curFile, 3);
//				if(wordError != null) {
//					System.out.println("wordError " + wordError);
//					this.addFieldError(GlobalInfo.ERROR_INFO, wordError);
//					info += wordError;
//				}
//			}
//		}
		return info;
	}
	
	/**
	 * 删除项目结项申请
	 * @auhtor 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete() {
		for(int i=0;i<entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(entityIds.get(i));
			if(endinspection != null){
				if(loginer.getCurrentType().compareWith(AccountType.ADMINISTRATOR) > 0 && endinspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				this.projectService.deleteEndinspection(endinspection);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NOT_EXIST);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void validateDelete(){
		String info ="";
		String appId = this.projectService.getApplicationIdByGrantedId(entityIds.get(0)).trim();
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DELETE_NULL);
			info += ProjectInfo.ERROR_END_DELETE_NULL;
			
		}
		//校验业务设置状态
		endStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (endStatus == 0){
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
	 * 进入项目结项结果录入添加页面预处理
	 * @author 余潜玉
	 */
	public String toAddResult(){
		endDate = new Date();
		endCertificate = this.projectService.getDefaultEndCertificate(endinspectionClassName());
		String groupId = "file_add";
		endMember = this.projectService.getMembersStringByGraId(projectid);
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	/**
	 * 添加和修改录入的项目结项结果的公共处理部分
	 * @param endinspection 结项对象
	 * @param granted 项目立项对象
	 * @param flag 1:添加  2:修改
	 * @return 新的endinspection对象
	 * @throws Exception 
	 */
	public ProjectEndinspection doWithAddAndModifyResult(ProjectEndinspection endinspection, ProjectGranted granted, int flag) throws Exception{
		String orignFile = endinspection.getFile();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, endResult, endImportedStatus, null);
		Date submitDate = this.projectService.setDateHhmmss(endDate);
		if (flag==1) {
			endinspection.setCreateDate(new Date());
		}else {
			endinspection.setUpdateDate(new Date());
		}
		endinspection.setApplicantSubmitDate(submitDate);
		endinspection.setFinalAuditDate(submitDate);
		endinspection.setFinalAuditor(auditInfo.getAuditor());
		endinspection.setFinalAuditorName(auditInfo.getAuditorName());
		endinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		endinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		endinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		endinspection.setCreateMode(1);//设为导入数据
		endinspection.setFinalAuditStatus(this.endImportedStatus);
		endinspection.setFinalAuditResultEnd(this.endResult);
		endinspection.setIsApplyExcellent(isApplyExcellent);
		endinspection.setIsApplyNoevaluation(isApplyNoevaluation);		
		if(endImportedOpinion != null && endImportedOpinion.trim().length() > 0){
			endinspection.setFinalAuditOpinion(("A" + endImportedOpinion).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinion(null);
		}
		if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 0){
			endinspection.setFinalAuditOpinionFeedback(("A" + endOpinionFeedback).trim().substring(1));
		}else{
			endinspection.setFinalAuditOpinionFeedback(null);
		}
		if(isApplyExcellent == 1){
			endinspection.setFinalAuditResultExcellent(endExcellentResult);
		}
		if(isApplyNoevaluation == 1){
			endinspection.setFinalAuditResultNoevaluation(endNoauditResult);
		}
		if(endResult == 2){
			endinspection.setCertificate(endCertificate.trim());
		}else{
			endinspection.setCertificate(null);
		}
		if(this.endImportedStatus == 3 && endResult == 2){
			granted.setStatus(2);
			granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
			this.dao.modify(granted);
		}
		 //保存上传的文件
		if (flag == 1) { //添加
			String groupId = "file_add";
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile, endImportedStatus);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		} else if (flag == 2){  //修改
			String groupId = "file_" + endinspection.getId();
			List<String> files = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				//只能上传一个，在js中作了限制
				File curFile = fileRecord.getOriginal();
				String savePath = this.projectService.uploadEndFile(projectType(), projectid, curFile, endImportedStatus);
				//将文件放入list中暂存
				files.add(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
			uploadService.flush(groupId);
		}
		//DMSS同步 
		if(orignFile != endinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != endinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(endinspection);
				}else{ //现在没文件
					dmssService.deleteFile(endinspection.getDfs());
					endinspection.setDfs(null);
				}
			}else{ //原来没有文件
				if(endinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(endinspection);
					endinspection.setDfs(dfsId);
				}
			}
		}		
		return endinspection;
	}
	
	/**
	 * 进入项目结项结果录入修改页面预处理
	 * @author 余潜玉
	 */
	public String toModifyResult(){
		ProjectEndinspection endinspection;
		if(modifyFlag == 0){//结项终审结束前修改
			endinspection = (ProjectEndinspection)this.projectService.getCurrentPendingImpEndinspectionByGrantedId(this.projectid);
		}else{//结项终审结束后修改
			endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(this.projectid);
		}
		if (endinspection.getProjectFee() != null) {
			projectFeeEnd = dao.query(ProjectFee.class, endinspection.getProjectFee().getId());
		}
		doWithToModifyResult(endinspection);
		this.endNoauditResult = endinspection.getFinalAuditResultNoevaluation();
		this.isApplyNoevaluation = endinspection.getIsApplyNoevaluation();
		this.endProductInfo = this.projectService.getProductTypeReal(endinspection.getImportedProductInfo(), endinspection.getImportedProductTypeOther());
		this.endMember = endinspection.getMemberName();
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + endinspection.getId();
		uploadService.resetGroup(groupId);
		if (endinspection.getFile() != null) {
			String[] tempFileRealpath = endinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(endinspection.getDfs() != null && !endinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(endinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = endinspection.getFile().substring(endinspection.getFile().lastIndexOf("/") + 1);
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
	 * 进入项目结项结果录入修改页面预处理公共方法
	 * @param endinspection
	 */
	public void doWithToModifyResult(ProjectEndinspection endinspection){
		this.endResult = endinspection.getFinalAuditResultEnd();
		this.endExcellentResult = endinspection.getFinalAuditResultExcellent();
		this.isApplyExcellent = endinspection.getIsApplyExcellent();
		if(endinspection.getFinalAuditResultEnd()==2){
			this.endCertificate = endinspection.getCertificate();
		}else{
			this.endCertificate = this.projectService.getDefaultEndCertificate(endinspectionClassName());
		}
		this.endDate = endinspection.getFinalAuditDate();
		this.endImportedOpinion = endinspection.getFinalAuditOpinion();
		this.endOpinionFeedback = endinspection.getFinalAuditOpinionFeedback();
		request.setAttribute("endId", endinspection.getId());
	}
	/**
	 * 修改录入的项目结项结果
	 * @author 余潜玉
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String modifyResult() throws Exception{
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectEndinspection endinspection;
		if(modifyFlag == 0){//结项终审结束前修改
			endinspection = (ProjectEndinspection)this.projectService.getCurrentPendingImpEndinspectionByGrantedId(this.projectid);
		}else{//结项终审结束后修改
			endinspection = (ProjectEndinspection)this.projectService.getCurrentEndinspectionByGrantedId(this.projectid);
		}
		if (endinspection.getProjectFee() != null) {
			projectFeeEnd = dao.query(ProjectFee.class, endinspection.getProjectFee().getId());
			projectFeeEnd = this.doWithAddResultFee(projectFeeEnd);
			dao.modify(projectFeeEnd);
		}
		if(!"post".equals(projectType())){//非后期资助项目（以下操作后期资助项目是在录入评审结果时处理的）
			endinspection.setMemberName(this.projectService.MutipleToFormat(this.personExtService.regularNames(this.endMember)));
			//设置成果相关信息
			endinspection = this.doWithProductInfo(endProductInfo, endinspection);
		}
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		endinspection = (ProjectEndinspection) this.doWithAddAndModifyResult(endinspection, granted, 2);
		if(modifyFlag != 0 && endResult == 1){//结项终审结束后修改
			granted.setStatus(1);
		}
		dao.modify(endinspection);
		
//		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			if (granted.getProjectType().equals("general")) {
//				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (generalFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的10%
////					generalFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					generalFunding.setStatus(0);
//					dao.modify(generalFunding);
//				}else {
//					GeneralFunding newGeneralFunding = new GeneralFunding();
////					newGeneralFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newGeneralFunding.setStatus(0);
//					newGeneralFunding.setType(3);
//					newGeneralFunding.setGranted(granted);
//					newGeneralFunding.setGrantedId(granted.getId());
//					newGeneralFunding.setProjectType(granted.getProjectType());
//					dao.add(newGeneralFunding);
//				}
//			}else if (granted.getProjectType().equals("instp")) {
//				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (instpFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的10%
////					instpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					instpFunding.setStatus(0);
//					dao.modify(instpFunding);
//				}else {
//					InstpFunding newInstpFunding = new InstpFunding();
////					newInstpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newInstpFunding.setStatus(0);
//					newInstpFunding.setType(3);
//					newInstpFunding.setGranted(granted);
//					newInstpFunding.setGrantedId(granted.getId());
//					newInstpFunding.setProjectType(granted.getProjectType());
//					dao.add(newInstpFunding);
//				}
//			}else if (granted.getProjectType().equals("key")) {
//				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (keyFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的10%
////					keyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					keyFunding.setStatus(0);
//					dao.modify(keyFunding);
//				}else {
//					KeyFunding newKeyFunding = new KeyFunding();
////					newKeyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newKeyFunding.setStatus(0);
//					newKeyFunding.setType(3);
//					newKeyFunding.setGranted(granted);
//					newKeyFunding.setGrantedId(granted.getId());
//					newKeyFunding.setProjectType(granted.getProjectType());
//					dao.add(newKeyFunding);
//				}
//			}else if (granted.getProjectType().equals("entrust")) {
//				EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (entrustFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的50%
////					entrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					entrustFunding.setStatus(0);
//					dao.modify(entrustFunding);
//				}else {
//					EntrustFunding newEntrustFunding = new EntrustFunding();
////					newEntrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					newEntrustFunding.setStatus(0);
//					newEntrustFunding.setType(3);
//					newEntrustFunding.setGranted(granted);
//					newEntrustFunding.setGrantedId(granted.getId());
//					newEntrustFunding.setProjectType(granted.getProjectType());
//					dao.add(newEntrustFunding);
//				}
//			}else if (granted.getProjectType().equals("post")) {
//				PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (postFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的50%
////					postFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					postFunding.setStatus(0);
//					dao.modify(postFunding);
//				}else {
//					PostFunding newPostFunding = new PostFunding();
////					newPostFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					newPostFunding.setStatus(0);
//					newPostFunding.setType(3);
//					newPostFunding.setGranted(granted);
//					newPostFunding.setGrantedId(granted.getId());
//					newPostFunding.setProjectType(granted.getProjectType());
//					dao.add(newPostFunding);
//				}
//			}
//		}
		return SUCCESS;
	}
	/**
	 * 提交录入的项目结项结果
	 * @author 刘雅琴
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		ProjectEndinspection endinspection = (ProjectEndinspection)this.projectService.getCurrentPendingImpEndinspectionByGrantedId(this.projectid);
		if(endinspection.getFinalAuditResultEnd() == 2 && !this.projectService.isEndNumberUnique(endinspectionClassName(), endinspection.getCertificate(), endinspection.getId())){
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
			return INPUT;
		}
		endinspection.setUpdateDate(new Date());
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		endinspection.setFinalAuditor(auditInfo.getAuditor());
		endinspection.setFinalAuditorName(auditInfo.getAuditorName());
		endinspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		endinspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		endinspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		endinspection.setFinalAuditStatus(3);
		if(endinspection.getFinalAuditResultEnd() == 2){//同意结项
//			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, this.projectid);
			granted.setStatus(2);
			granted.setEndStopWithdrawDate(endinspection.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(endinspection.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(endinspection.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(endinspection.getFinalAuditOpinionFeedback());
			this.dao.modify(granted);
		}
		dao.modify(endinspection);
		
//		if (endinspection.getFinalAuditResultEnd() == 2 && endinspection.getFinalAuditStatus() == 3) {
//			Map parmap = new HashMap();
//			parmap.put("grantedId", granted.getId());
//			if (granted.getProjectType().equals("general")) {
//				GeneralFunding generalFunding = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (generalFunding != null) {
//					//结项通过则添加结项拨款申请，金额默认为批准经费的10%
////					generalFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					generalFunding.setStatus(0);
//					dao.modify(generalFunding);
//				}else {
//					GeneralFunding newGeneralFunding = new GeneralFunding();
////					newGeneralFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newGeneralFunding.setStatus(0);
//					newGeneralFunding.setType(3);
//					newGeneralFunding.setGranted(granted);
//					newGeneralFunding.setGrantedId(granted.getId());
//					newGeneralFunding.setProjectType(granted.getProjectType());
//					dao.add(newGeneralFunding);
//				}
//			}else if (granted.getProjectType().equals("instp")) {
//				InstpFunding instpFunding = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (instpFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的10%
////					instpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					instpFunding.setStatus(0);
//					dao.modify(instpFunding);
//				}else {
//					InstpFunding newInstpFunding = new InstpFunding();
////					newInstpFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newInstpFunding.setStatus(0);
//					newInstpFunding.setType(3);
//					newInstpFunding.setGranted(granted);
//					newInstpFunding.setGrantedId(granted.getId());
//					newInstpFunding.setProjectType(granted.getProjectType());
//					dao.add(newInstpFunding);
//				}
//			}else if (granted.getProjectType().equals("key")) {
//				KeyFunding keyFunding = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (keyFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的10%
////					keyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					keyFunding.setStatus(0);
//					dao.modify(keyFunding);
//				}else {
//					KeyFunding newKeyFunding = new KeyFunding();
////					newKeyFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.1));
//					newKeyFunding.setStatus(0);
//					newKeyFunding.setType(3);
//					newKeyFunding.setGranted(granted);
//					newKeyFunding.setGrantedId(granted.getId());
//					newKeyFunding.setProjectType(granted.getProjectType());
//					dao.add(newKeyFunding);
//				}
//			}else if (granted.getProjectType().equals("entrust")) {
//				EntrustFunding entrustFunding = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (entrustFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的10%
////					entrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					entrustFunding.setStatus(0);
//					dao.modify(entrustFunding);
//				}else {
//					EntrustFunding newEntrustFunding = new EntrustFunding();
////					newEntrustFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					newEntrustFunding.setStatus(0);
//					newEntrustFunding.setType(3);
//					newEntrustFunding.setGranted(granted);
//					newEntrustFunding.setGrantedId(granted.getId());
//					newEntrustFunding.setProjectType(granted.getProjectType());
//					dao.add(newEntrustFunding);
//				}
//			}else if (granted.getProjectType().equals("post")) {
//				PostFunding postFunding = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId and p.type = 3 ", parmap);
//				if (postFunding != null) {
//					//中检通过则添加中检拨款申请，金额默认为批准经费的10%
////					postFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					postFunding.setStatus(0);
//					dao.modify(postFunding);
//				}else {
//					PostFunding newPostFunding = new PostFunding();
////					newPostFunding.setEndFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//					newPostFunding.setStatus(0);
//					newPostFunding.setType(3);
//					newPostFunding.setGranted(granted);
//					newPostFunding.setGrantedId(granted.getId());
//					newPostFunding.setProjectType(granted.getProjectType());
//					dao.add(newPostFunding);
//				}
//			}
//		}
		return SUCCESS;
	}
	
	/**
	 * 添加结项结果校验
	 * @author 余潜玉
	 */
	public void validateAddResult(){
		validateEditResult(1);
	}
	
	/**
	 * 修改结项结果校验
	 * @author 刘雅琴
	 */
	public void validateModifyResult(){
		validateEditResult(2);
	}
	
	/**
	 * 提交结项结果校验
	 * @author 刘雅琴
	 */
	public void validateSubmitResult(){
		validateEditResult(3);
	}

	/**
	 * 编辑结项结果校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked" })
	public void validateEditResult(int type){
		String info ="";
		int endAllow = 0;
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			if(granted == null){
				this.addFieldError(GlobalInfo.ERROR_INFO,GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}else if(granted.getStatus() == 3){//中止
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_STOP);
				info += ProjectInfo.ERROR_PROJECT_STOP;
			}else if(granted.getStatus() == 4){//撤项
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_PROJECT_REVOKE);
				info += ProjectInfo.ERROR_PROJECT_REVOKE;
			}
			if(!"post".equals(projectType()) && !"entrust".equals(projectType())){//后期资助项目和委托应急课题无中检
				int grantedYear = this.projectService.getGrantedYear(projectid);
				endAllow = this.projectService.getEndAllowByGrantedDate(grantedYear);
				if(this.projectService.getPassMidinspectionByGrantedId(this.projectid).size() == 0 && endAllow == 0){//中检未通过并且结项时间未开始
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CANNOT);
					info += ProjectInfo.ERROR_END_CANNOT;
				}
				if(type == 1 || type == 3){//添加或提交
					if(this.projectService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){//有未处理中检
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
						info += ProjectInfo.ERROR_ENDM_DEALING;
					}
				}
			}
		}
		if(type == 1){//添加时校验
			if(!this.projectService.getPendingEndinspectionByGrantedId(projectid).isEmpty()){//有未处理结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DEALING);
				info += ProjectInfo.ERROR_END_CANNOT;
			}
		}
		if(type == 1 || type == 3){//添加或提交
			if(this.projectService.getPendingVariationByGrantedId(this.projectid).size()>0){//有未处理变更
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDV_DEALING);
				info += ProjectInfo.ERROR_ENDV_DEALING;
			}
		}
		if(type==1 || type==2){//添加修改时校验
			if(isApplyExcellent != 0 && isApplyExcellent != 1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL);
				info += ProjectInfo.ERROR_END_IS_APPLY_EXCELLENT_NULL;
			}
			if(isApplyNoevaluation != 0 && isApplyNoevaluation != 1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL);
				info += ProjectInfo.ERROR_END_IS_APPLY_NOEVALUATION_NULL;
			}
//			if(endProductInfo == null){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PRODUCT_INFO_NULL);
//				info += ProjectInfo.ERROR_END_PRODUCT_INFO_NULL;
//			}
//			if(null == endMember || endMember.trim().isEmpty()){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_MEMBER_NULL);
//				info+=ProjectInfo.ERROR_END_MEMBER_NULL;
//			}else if(endMember.trim().length()>200){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_MEMBER_OUT);
//				info+=ProjectInfo.ERROR_END_MEMBER_OUT;
//			}
			if(null != endMember && !endMember.trim().isEmpty() && endMember.trim().length()>200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_MEMBER_OUT);
				info+=ProjectInfo.ERROR_END_MEMBER_OUT;
			}
			info += this.doWithValidateEditResult(type);
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 项目结项申请校验公用方法
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public String doWithValidateEditResult(int type){
		String info ="";
		if(type == 1 || (type == 2 && modifyFlag == 0)){
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//结项已通过
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_END_ALREADY);
				info += ProjectInfo.ERROR_END_ALREADY;
			}
		}
		if(type==1 || type==2){//添加修改时校验
			if(endResult != 1 && endResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_RESULT_NULL;
			}
			if(endResult ==2){
				if(endCertificate == null || endCertificate.trim().isEmpty()){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_NULL);
					info+=ProjectInfo.ERROR_END_CERTIFICATE_NULL;
				}else if(endCertificate.trim().length()>40){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_CERTIFICATE_OUT);
					info+=ProjectInfo.ERROR_END_CERTIFICATE_OUT;
				}
				if(type == 1){
					if(!this.projectService.isEndNumberUnique(endinspectionClassName(), endCertificate, "")){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
						info+=ProjectInfo.ERROR_END_NUMBER_EXIST;
					}
				}else if(type == 2){
					if(!this.projectService.isEndNumberUnique(endinspectionClassName(), endCertificate, this.projectService.getCurrentEndinspectionByGrantedId(this.projectid).getId())){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_NUMBER_EXIST);
						info+=ProjectInfo.ERROR_END_NUMBER_EXIST;
					}
				}
			}
			if(isApplyNoevaluation == 1 && endNoauditResult != 1 && endNoauditResult != 2){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_NOEVALUATION_RESULT_NULL;
			}
			if(isApplyExcellent == 1 && endExcellentResult != 1 && endExcellentResult != 2 && endExcellentResult != 0){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL);
				info+=ProjectInfo.ERROR_END_IS_EXCELLENT_RESULT_NULL;
			}
			if(endDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_DATE_NULL);
				info += ProjectInfo.ERROR_END_DATE_NULL;
			}
			if(endImportedOpinion != null && endImportedOpinion.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}
			if(endOpinionFeedback != null && endOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_OPINION_OUT);
				info += ProjectInfo.ERROR_END_OPINION_OUT;
			}
		}
		return info;
	}
	
	/**
	 * 对项目结项成果类别信息的公共处理
	 * @param endProductInfo 结项成果信息
	 * @param endinspection 结项对象
	 * @return 处理后的项目结项对象
	 */
	public ProjectEndinspection doWithProductInfo(String endProductInfo, ProjectEndinspection endinspection){
		if(endProductInfo != null && endProductInfo.contains("其他")){
			String[] proTypes = endProductInfo.split("\\(");
			if(proTypes.length > 1){
				String productTypeNames = proTypes[0];
				String[] productTypeothers = proTypes[1].split("\\)");
				String productTypeOther = productTypeothers[0];
				productTypeNames += productTypeothers[1];
				endinspection.setImportedProductInfo(productTypeNames);
				endinspection.setImportedProductTypeOther(this.projectService.MutipleToFormat(productTypeOther.trim()));
			}
		}else{
			endinspection.setImportedProductInfo(endProductInfo);
			endinspection.setImportedProductTypeOther(null);
		}
		return endinspection;
	}
	
	/**
	 * 准备导入上传电子文档
	 * @author 肖雅
	 */
	public String toUploadFileResult(){
		ProjectEndinspection endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		if (endinspection.getFile() != null) {
			String[] tempFileRealpath = endinspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(endinspection.getDfs() != null && !endinspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(endinspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = endinspection.getFile().substring(endinspection.getFile().lastIndexOf("/") + 1);
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
		ProjectEndinspection endinspection;
		endImportedStatus = 3;
		endinspection = (ProjectEndinspection)this.dao.query(ProjectEndinspection.class, endId);
		String orignFile = endinspection.getFile();
		//保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadEndFileResult(projectType(), projectid, curFile, endImportedStatus, endId);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		endinspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		dao.modify(endinspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != endinspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != endinspection.getFile()){ //现在有文件
					projectService.checkInToDmss(endinspection);
				}else{ //现在没文件
					dmssService.deleteFile(endinspection.getDfs());
					endinspection.setDfs(null);
					dao.modify(endinspection);
				}
			}else{ //原来没有文件
				if(endinspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(endinspection);
					endinspection.setDfs(dfsId);
					dao.modify(endinspection);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 导入上传电子文档校验
	 * @author 肖雅
	 */
	public void validateUploadFileResult(){
//		if(null == endFileId || endFileId.length == 0){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//		}else if(endFileId.length > 1){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//		}
	}
	
	/**
	 * 打印结项证书预处理
	 */
	public String printCertificate(){
		return SUCCESS;
	}
	
	/**
	 * 确认打印项目结项证书
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String confirmPrintCertificate(){
		ProjectGranted granted = (ProjectGranted)this.dao.query(ProjectGranted.class, projectid);
		String hql = "select endi from " + granted.getEndinspectionClassName() + " endi where endi.granted.id = ? and endi.finalAuditStatus = 3 and endi.finalAuditResultEnd = 2 ";
		List<ProjectEndinspection> endis = dao.query(hql, projectid);
		if(null != endis && endis.size() > 0){
			ProjectEndinspection endi = endis.get(0);
			endi.setPrintCount(endi.getPrintCount() + 1);
			dao.modify(endi);
		}
		return SUCCESS;
	}
	
	/**
	 * 打印结项一览表预处理
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String printOverView(){
		int type = Integer.parseInt(request.getParameter("type"));//1:打印; 2:导出
		request.setAttribute("type", type);
		if (type == 1) {
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
		}
//		return (type == 1) ? SUCCESS : "exportOverView";//打印/导出
		return SUCCESS;
	}
	
	/**
	 * 确认打印结项一览表
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String confirmPrintOverView(){
		int type = Integer.parseInt(request.getParameter("type"));//提交类型(1:打印; 2:导出)
		int printType = Integer.parseInt(request.getParameter("printType"));//打印类型(0:资料建档;1:报社科司;2:寄送学校)
		int univ = Integer.parseInt(request.getParameter("univ"));
		String univId = request.getParameter("univId").trim();//高校
		String subType = request.getParameter("subType").trim();//项目子类
		int projectStatus = Integer.parseInt(request.getParameter("projectStatus"));//项目状态
		
		
		if(univ == 0){//如果没有指定高校
			entityIds = dao.query("select uni.id from " + endinspectionClassName() + " endi, " + grantedClassName() + "  gra " +
				" left join gra.university uni where endi.granted.id = gra.id order by uni.name asc");
			List<String> ids = new ArrayList<String>();
			for(String id : entityIds){
				if(!ids.contains(id)){
					ids.add(id);
				}
			}
			entityIds = ids;
		} else {//如果指定需要打印或导出的高校
			entityIds = new ArrayList();
			entityIds.add(univId);
		}
		StringBuffer sb = new StringBuffer();
		String hql1 = "";
		if ("instp".equals(projectType())) {//如果是基地项目则需要查询“基地名称”
			hql1 = "select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult', ins.name, mem.agencyName from " + endinspectionClassName() + " endi, " + endinspectionClassName() + " all_endi, " +
					grantedClassName() + " gra left outer join gra.application app left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so, ProjectMember mem where mem.applicationId = gra.applicationId and gra.memberGroupNumber = mem.groupNumber and mem.isDirector =1 and uni.id = :entityId " +
					"and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
		} else if("key".equals(projectType())){//如果重大攻关项目，则需要从申请表查询研究类型
			hql1 = "select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult' from " + endinspectionClassName() + " endi, " + endinspectionClassName() + " all_endi, " +
					grantedClassName() + " gra left outer join gra.application app left outer join gra.university uni left outer join app.researchType so where uni.id = :entityId " +
					"and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
		}else{//其他项目，则直接从立项表查询项目子类
			hql1 = "select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult' from " + endinspectionClassName() + " endi, " + endinspectionClassName() + " all_endi, " +
					grantedClassName() + " gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so where uni.id = :entityId " +
					"and endi.granted.id = gra.id and all_endi.granted.id = gra.id ";
		}
		
		/*String hql1 = "select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
			"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
			"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount' from GeneralEndinspection endi left outer join " +
			"endi.granted gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so where uni.id = :entityId " +
			"and not exists (select 1 from GeneralEndinspection genend where genend.granted.id = gra.id and genend.applicantSubmitDate>endi.applicantSubmitDate)";*/
		
		sb.append(hql1);
		Map parMap = new HashMap();
		if ("key".equals(projectType())){//重大攻关
			if(!subType.equals("-1")){
				sb.append(" and app.researchType.id = :subType");
				parMap.put("subType", subType);
			}
		}else {//其他项目
			if(!subType.equals("-1")){
				sb.append(" and gra.subType.id = :subType");
				parMap.put("subType", subType);
			}
		}
		if (projectStatus != -1) {//项目状态
			parMap.put("projectStatus", projectStatus);
			if(projectStatus == 1){
				sb.append(" and gra.status = 1");
			}else if(projectStatus == 2){
				sb.append(" and gra.status = 2");
			}else if(projectStatus == 3){
				sb.append(" and gra.status = 3");
			}else if(projectStatus == 4){
				sb.append(" and gra.status = 4");
			}
		}
		//起止时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != startDate) {
			parMap.put("startDate", df.format(startDate));
			sb.append(" and endi.finalAuditDate is not null and to_char(endi.finalAuditDate,'yyyy-MM-dd')>=:startDate");
		}
		if (null != endDate) {
			parMap.put("endDate", df.format(endDate));
			if(startDate == null){
				sb.append(" and endi.finalAuditDate is not null ");
			}
			sb.append(" and to_char(endi.finalAuditDate,'yyyy-MM-dd')<=:endDate");
		}
		//项目年度
		if (startYear != -1) {
			parMap.put("startYear", startYear);
			sb.append(" and app.year>=:startYear ");
		}
		if (endYear != -1) {
			parMap.put("endYear", endYear);
			sb.append(" and app.year<=:endYear ");
		}
		sb.append(" and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) ");
		HqlTool hqlTool = new HqlTool(sb.toString());
		sb.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		
		List list = new ArrayList(), groupList = new ArrayList(); 
		for(String entityId : entityIds){
			if(null == entityId || entityId.isEmpty())
				continue;
			parMap.put("entityId", entityId);
			try {
				groupList = dao.query(sb.toString(), parMap);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < groupList.size(); i++){
				Object[] o = (Object[])groupList.get(i);
				int num = 0;//初始CSSCI等论文数量为0
				String prodInfo = (String)o[14];//导入/录入结项成果统计信息[成果类型/成果总数/满足结项要求的成果数量，多个用英文分号与空格隔开，如：论文/2/1; 著作/1/1]
				if(null != prodInfo){
					String[] items = prodInfo.split("；");
					for(String item : items){
						if(item.indexOf("论文") != -1){//indexOf 方法返回一个整数值，返回item中第一次出现"论文"的索引，正向搜索，如果未找到，则返回 -1。
							num = Integer.parseInt(item.substring(item.trim().lastIndexOf("/") + 1));//lastIndexOf 方法返回一个整数值，返回此向量中最后一次出现"/"的索引；如果此向量不包含"/"，则返回 -1。
							break;
						}
					}
				}
				o[13] = num;//CSSCI等论文数量
				
				//如果是不同意结项，才显示备注信息,否则不显示
				if((Integer)o[10] != 1){
					o[12] = "";
				}
				
				//查出结项次数
				String graId = (String)o[0];
				long cnt = dao.count("select count(*) from " + endinspectionClassName() + " endi where endi.granted.id = ? ", graId);
				o[16] = cnt;

				//中检情况
				if (!"post".equals(projectType()) && !"entrust".equals(projectType())) {
					String hqlString = "select mid.id from " + midinspectionClassName() + " mid where mid.granted.id = ? ";
					List midList = dao.query(hqlString, graId); 
					if (midList.size() > 0){
						String hqlString2 = "select mid.finalAuditResult from " + midinspectionClassName() + " mid, " + midinspectionClassName() + " all_mid where mid.granted.id = ? and all_mid.granted.id = mid.granted.id group by mid.finalAuditDate, mid.finalAuditResult having mid.finalAuditDate = max(all_mid.finalAuditDate)";
						List midResult = dao.query(hqlString2, graId);
						if (midResult.size() > 0) {
							o[23] = midResult.get(0);
						}
					}else{
						o[23] = null;
					}
				}
				
				//只用于导出，查出历次拨款-new（最多拨款三次，将三次拨款进行拼接，若没有则显示0）
				List<Object[]> fdsList = dao.query("select fu.fee from " + fundingClassName() + " fu where fu.granted.id = ? order by fu.date asc", graId);
				if(fdsList.size() == 3){
					o[20] = fdsList.get(0);
					o[21] = (fdsList.get(1) == null) ? "" : fdsList.get(1);
					o[22] = (fdsList.get(2) == null) ? "" : fdsList.get(2);
				}if(fdsList.size() == 2){
					o[20] = fdsList.get(0);
					o[21] = (fdsList.get(1) == null) ? "" : fdsList.get(1);
					o[22] = "0";
				}if(fdsList.size() == 1){
					o[20] = fdsList.get(0);
					o[21] = "0";
					o[22] = "0";
				}else if(fdsList.size() == 0){
					o[20] = "0";
					o[21] = "0";
					o[22] = "0";
				}
				groupList.set(i, o);
			}
			list.add(groupList);
		}
		
		session.remove(GlobalInfo.EXPORT_START_TIME);
		session.remove(GlobalInfo.EXPORT_END_TIME);
		
		request.setAttribute("printType", printType);
		request.setAttribute("dataList", list);
		
		return  SUCCESS;
	}
	
	/**
	 * 导出项目结项一览表
	 * @return
	 * @throws Exception
	 */
	public String exportOverView() {
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		String header = "";//表头
		if("general".equals(projectType())){
			header = "教育部人文社会科学研究一般项目结项情况一览表";
		}else if ("instp".equals(projectType())){
			header = "教育部人文社会科学研究基地项目结项情况一览表";
		}else if ("post".equals(projectType())){
			header = "教育部人文社会科学研究后期资助项目结项情况一览表";
		}else if ("key".equals(projectType())){
			header = "教育部人文社会科学研究重大攻关项目结项情况一览表";
		}else if ("entrust".equals(projectType())){
			header = "教育部人文社会科学研究委托应急课题结项情况一览表";
		}
		StringBuffer hql4Export = new StringBuffer();
		Account account = loginer.getAccount();
		AccountType accountType = account.getType();
		String[] title = new String[]{};//标题
		if ("instp".equals(projectType())){//若是基地项目则需要查询负责人所在高校和依托基地
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
					"负责人所在高校",
					"最终成果形式",
					"批准经费（万元",
					"第一次拨款（万元）",
					"第二次拨款（万元）",
					"第三次拨款（万元）",
					"终结报告书电子版",
					"是否申请优秀成果",
					"CSSCI等论文数量",
					"中检情况",
					"结项方式",
					"是否同意",
					"结项时间",
					"结项证书编号",
					"结项次数",
					"项目状态",
					"备注"
				};
//			hql4Export.append( "select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
//					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
//					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult', ins.name, mem.agencyName ");
			
		
			hql4Export.append("select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult', ins.name, mem.agencyName from " + endinspectionClassName() + " endi, " + endinspectionClassName() + " all_endi, " +
					grantedClassName() + " gra left outer join gra.application app left outer join gra.university uni left outer join gra.institute ins left outer join gra.subtype so, ProjectMember mem where mem.applicationId = gra.applicationId and gra.memberGroupNumber = mem.groupNumber and mem.isDirector =1  " +
					"and endi.granted.id = gra.id and all_endi.granted.id = gra.id " );
		
		
		}else if("key".equals(projectType())){//若是重大攻关项目，则无项目子类而是研究类型
			title = new String[]{
					"序号",
					"项目名称",
					"项目负责人",
					"依托高校",
					"依托基地",
					"研究类型",
					"项目批准号",
					"项目年度",
					"批准经费（万元）",
					"第一次拨款（万元）",
					"第二次拨款（万元）",
					"第三次拨款（万元）",
					"成果形式",
					"终结报告书电子版",
					"是否申请优秀成果",
					"CSSCI等论文数量",
					"中检情况",
					"结项方式",
					"是否同意",
					"结项时间",
					"结项证书编号",
					"结项次数",
					"项目状态",
					"备注"
				};
//			hql4Export.append("select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
//					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
//					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult' " ); 
			hql4Export.append("select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
			"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
			"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult' from " + endinspectionClassName() + " endi, " + endinspectionClassName() + " all_endi, " +
			grantedClassName() + " gra left outer join gra.application app left outer join gra.university uni left outer join gra.subtype so where  " +
			" endi.granted.id = gra.id and all_endi.granted.id = gra.id " );
		
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
					"批准经费（万元",
					"第一次拨款（万元）",
					"第二次拨款（万元）",
					"第三次拨款（万元）",
					"终结报告书电子版",
					"是否申请优秀成果",
					"CSSCI等论文数量",
					"中检情况",
					"结项方式",
					"是否同意",
					"结项时间",
					"结项证书编号",
					"结项次数",
					"项目状态",
					"备注"
				};
			hql4Export.append("select gra.id, uni.name, gra.applicantName, gra.name, so.name, gra.number, gra.approveFee, gra.productType, " +
					"endi.isApplyExcellent, endi.finalAuditResultNoevaluation, endi.finalAuditResultEnd, to_char(endi.finalAuditDate,'yyyy-MM-dd'), " +
					"endi.finalAuditOpinionFeedback, 'num', endi.importedProductInfo, endi.certificate, 'endCount', app.year, gra.status, endi.file, 'funds1', 'funds2', 'funds3', 'midResult' ");
			
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
				hql4Export.append(listHql3());
			}else{//管理人员
				hql4Export.append(listHql2());
			}
		}
		fileFileName = header + ".xls";
		fileFileName = new String(fileFileName.getBytes("UTF-8"), "ISO8859-1");
		
		//如果是数据导出，则获取session中得起止时间
		if(null != session.get(GlobalInfo.EXPORT_START_TIME) && null != session.get(GlobalInfo.EXPORT_END_TIME)){
			startDate = (Date)session.get(GlobalInfo.EXPORT_START_TIME);
			endDate = (Date)session.get(GlobalInfo.EXPORT_END_TIME);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(hql4Export);
		Map parMap = (Map) session.get("grantedMap");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(!loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {
			if (startDate==null||endDate==null) {
				return HSSFExport.commonExportExcel(new ArrayList(), header, title);
			}
			parMap.put("startDate", df.format(startDate));
			parMap.put("endDate", df.format(endDate));
			
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				sb.append(" and ( (endi.deptInstAuditDate is not null and to_char(endi.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
				sb.append(" and to_char(endi.deptInstAuditDate,'yyyy-MM-dd')<=:endDate )");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				sb.append(" and ( (endi.universityAuditDate is not null and to_char(endi.universityAuditDate,'yyyy-MM-dd')>=:startDate");
				sb.append(" and to_char(endi.universityAuditDate,'yyyy-MM-dd')<=:endDate) ");
			}else if(accountType.equals(AccountType.PROVINCE)){
				sb.append(" and ( (endi.provinceAuditDate is not null and to_char(endi.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
				sb.append(" and to_char(endi.provinceAuditDate,'yyyy-MM-dd')<=:endDate) ");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				sb.append(" and ( (endi.finalAuditDate is not null and to_char(endi.finalAuditDate,'yyyy-MM-dd')>=:startDate");
				sb.append(" and to_char(endi.finalAuditDate,'yyyy-MM-dd')<=:endDate) ");
			}
			int resultStatus,saveStatus;
			if(accountType.compareTo(AccountType.PROVINCE) < 0){
				if (session.containsKey("finalAuditStatusfinalAuditStatus")) {
					finalAuditStatus = (Integer) session.get("finalAuditStatusfinalAuditStatus");
				}
				if(finalAuditStatus == -1){
					finalAuditStatus = 0;
				}
				if(finalAuditStatus!=-1){
					saveStatus=finalAuditStatus/10;
					resultStatus=finalAuditStatus%10;
					parMap.put("finalAuditStatus",  saveStatus);
					parMap.put("finalAuditResult", resultStatus);
					sb.append(" or ( endi.finalAuditStatus =:finalAuditStatus and endi.finalAuditResultEnd =:finalAuditResult) )");
				}
			}
		}
		if (null != (String) session.get("whereHql")) {
			sb.append((String) session.get("whereHql"));
		}
		HqlTool hqlTool = new HqlTool(sb.toString());
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			sb.append(" group by " + hqlTool.getSelectClause() + " having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		}else {
			sb.append(" group by " + hqlTool.getSelectClause() + ", endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate)");
		}
		List groupList = dao.query(sb.toString(), parMap);
		List list = new ArrayList();

		for(int i = 0; i < groupList.size(); i++){
			Object[] o = (Object[])groupList.get(i);
			int num = 0;//初始CSSCI等论文数量为0
			String prodInfo = (String)o[14];//导入/录入结项成果统计信息[成果类型/成果总数/满足结项要求的成果数量，多个用英文分号与空格隔开，如：论文/2/1; 著作/1/1]
			if(null != prodInfo){
				String[] items = prodInfo.split("；");
				for(String item : items){
					if(item.indexOf("论文") != -1){//indexOf 方法返回一个整数值，返回item中第一次出现"论文"的索引，正向搜索，如果未找到，则返回 -1。
						num = Integer.parseInt(item.substring(item.trim().lastIndexOf("/") + 1));//lastIndexOf 方法返回一个整数值，返回此向量中最后一次出现"/"的索引；如果此向量不包含"/"，则返回 -1。
						break;
					}
				}
			}
			o[13] = num;//CSSCI等论文数量
			
			//如果是不同意结项，才显示备注信息,否则不显示
			if((Integer)o[10] != 1){
				o[12] = "";
			}
			
			//查出结项次数
			String graId = (String)o[0];
			long cnt = dao.count("select count(*) from " + endinspectionClassName() + " endi where endi.granted.id = ? ", graId);
			o[16] = cnt;
			//中检情况  后期资助和委托应急没有中检
			if (!"post".equals(projectType()) && !"entrust".equals(projectType())) {
				String hqlString = "select mid.id from " + midinspectionClassName() + " mid where mid.granted.id = ? ";
				List midList = dao.query(hqlString, graId); 
				if (midList.size() > 0){//有中检记录
					String hqlString2 = "select mid.finalAuditResult from " + midinspectionClassName() + " mid, " + midinspectionClassName() + " all_mid where mid.granted.id = ? and all_mid.granted.id = mid.granted.id group by mid.finalAuditDate, mid.finalAuditResult having mid.finalAuditDate = max(all_mid.finalAuditDate)";
					List midResult = dao.query(hqlString2, graId);
					if (midResult.size() > 0) {//有中检审核记录
						o[23] = midResult.get(0);
					} else {//未审核
						o[23] = null;
					}
				}else{
					o[23] = null;
				}
			}
			
			//只用于导出，查出历次拨款-new（最多拨款三次，将三次拨款进行拼接，若没有则显示0）
			List<Object[]> fdsList = dao.query("select fu.fee from " + fundingClassName() + " fu where fu.granted.id = ? order by fu.date asc", graId);
			if(fdsList.size() == 3){
				o[20] = fdsList.get(0);
				o[21] = (fdsList.get(1) == null) ? "" : fdsList.get(1);
				o[22] = (fdsList.get(2) == null) ? "" : fdsList.get(2);
			}if(fdsList.size() == 2){
				o[20] = fdsList.get(0);
				o[21] = (fdsList.get(1) == null) ? "" : fdsList.get(1);
				o[22] = "0";
			}if(fdsList.size() == 1){
				o[20] = fdsList.get(0);
				o[21] = "0";
				o[22] = "0";
			}else if(fdsList.size() == 0){
				o[20] = "0";
				o[21] = "0";
				o[22] = "0";
			}
			groupList.set(i, o);
		}
		list.add(groupList);
		
		List resultList = new ArrayList();
		for(Object listItem : list) {
			resultList.addAll((List)listItem);
		}
		List dataList = new ArrayList();
		Map<Object, Object[]> lastData = new HashMap<Object, Object[]>();
		int index = 1;
		if("instp".equals(projectType())){
			for (Object object : resultList) {
				Object[] o = (Object[]) object;
				Object[] data = null;
				if (lastData.containsKey(o[5])&&lastData.get(o[5])[3]!=null) {
					data =  lastData.get(o[5]);
					String univNames = data[3].toString(); 
					if(!univNames.contains((String)o[25])){
						univNames += "; " + o[25];
						data[3] = univNames;
					}
				} else {
					data = new Object[o.length];
					data[0] = index++;
					data[1] = o[0];//项目结项ID
					data[2] = o[3];//项目名称
					data[3] = o[4];//项目类别
					data[4] = o[17];//项目年度
					data[5] = o[5];//项目批准号
					data[6] = o[1];//依托高校
					data[7] = o[24];//依托基地
					data[8] = o[2];//项目负责人
					data[9] = o[25];//负责人所在高校
					data[10] = o[7];//成果形式
					data[11] = o[6];//批准经费（万元）
					data[12] = o[20];//第一次拨款（万元）
					data[13] = o[21];//第二次拨款（万元）
					data[14] = o[22];//第三次拨款（万元）
					data[15] = o[19]!= null ? "有" : "无";//是否有终结报告书电子版
					data[16] = o[8] != null && ((Integer)o[8] == 1) ? "是" : "否";//是否申请优秀成果
					data[17] = o[13] != null && (Integer)o[13] != 0 ? o[13] : "";//CSSCI等论文数量
					data[18] = o[23] != null && (Integer)o[23] == 1 ? "不同意中检" : o[23] != null && (Integer)o[23] == 2 ? "同意中检" : "未中检";//中检情况
					data[19] = o[9] != null && (Integer)o[9] == 2 ? "免于鉴定" : "鉴定结项";//结项方式
					data[20] = o[10] != null && (Integer)o[10] == 1 ? "不同意结项" : o[10] != null && (Integer)o[10] == 2 ? "同意结项" : "";//是否同意
					data[21] = o[11];//结项时间
					data[22] = o[15];//结项证书编号
					data[23] = o[16];//结项次数
					data[24] = o[18] != null && (Integer)o[18] == 1 ? "在研" : o[18] != null && (Integer)o[18] == 2 ? "已结项" : o[18] != null && (Integer)o[18] == 3 ? "已中止" : o[18] != null && (Integer)o[18] == 4 ? "已撤项" : "";//结项状态
					data[25] = o[12];//备注
					dataList.add(data);
				}
				lastData.put(o[5], data);
			}
		}else {
			for (Object object : resultList) {
				Object[] o = (Object[]) object;
				Object[] data = new Object[o.length];

				data[0] = index++;
				data[1] = o[0];//项目立项ID
				data[2] = o[3];//项目名称
				data[3] = o[4];//项目类别
				data[4] = o[17];//项目年度
				data[5] = o[5];//项目批准号
				data[6] = o[1];//依托高校
				data[7] = o[2];//项目负责人
				data[8] = o[7];//最终成果形式
				data[9] = o[6];//批准经费（万元）
				data[10] = o[20];//第一次拨款（万元）
				data[11] = o[21];//第二次拨款（万元）
				data[12] = o[22];//第三次拨款（万元）
				data[13] = o[19]!= null ? "有" : "无";//是否有终结报告书电子版
				data[14] = o[8] != null && ((Integer)o[8] == 1) ? "是" : "否";//是否申请优秀成果
				data[15] = o[13] != null && (Integer)o[13] != 0 ? o[13] : "";//CSSCI等论文数量
				if (!"post".equals(projectType()) && !"entrust".equals(projectType())) {//中检情况
					data[16] = o[23] != null && (Integer)o[23] == 1 ? "不同意中检" : o[23] != null && (Integer)o[23] == 2 ? "同意中检" : "未中检";//中检情况
				} else {
					data[16] = null;
				}
				data[17] = o[9] != null && (Integer)o[9] == 2 ? "免于鉴定" : "鉴定结项";//结项方式
				data[18] = o[10] != null && (Integer)o[10] == 1 ? "不同意结项" : o[10] != null && (Integer)o[10] == 2 ? "同意结项" : "";//是否同意
				data[19] = o[11];//结项时间
				data[20] = o[15];//结项证书编号
				data[21] = o[16];//结项次数
				data[22] = o[18] != null && (Integer)o[18] == 1 ? "在研" : o[18] != null && (Integer)o[18] == 2 ? "已结项" : o[18] != null && (Integer)o[18] == 3 ? "已中止" : o[18] != null && (Integer)o[18] == 4 ? "已撤项" : "";//项目状态
				data[23] = o[12];//备注
				dataList.add(data);
			}
		}
		return HSSFExport.commonExportExcel(dataList, header, title);
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getIsApplyExcellent() {
		return isApplyExcellent;
	}
	public void setIsApplyExcellent(int isApplyExcellent) {
		this.isApplyExcellent = isApplyExcellent;
	}
	public int getIsApplyNoevaluation() {
		return isApplyNoevaluation;
	}
	public void setIsApplyNoevaluation(int isApplyNoevaluation) {
		this.isApplyNoevaluation = isApplyNoevaluation;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
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
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
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
	public String getProjectTopic() {
		return projectTopic;
	}
	public void setProjectTopic(String projectTopic) {
		this.projectTopic = projectTopic;
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
	public int getEndApplicantSubmitStatus() {
		return endApplicantSubmitStatus;
	}
	public void setEndApplicantSubmitStatus(int endApplicantSubmitStatus) {
		this.endApplicantSubmitStatus = endApplicantSubmitStatus;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String[] getEndFileId() {
		return endFileId;
	}
	public void setEndFileId(String[] endFileId) {
		this.endFileId = endFileId;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public int getUploadEndApply() {
		return uploadEndApply;
	}
	public void setUploadEndApply(int uploadEndApply) {
		this.uploadEndApply = uploadEndApply;
	}
	public String getEndCertificate() {
		return endCertificate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndCertificate(String endCertificate) {
		this.endCertificate = endCertificate;
	}
	public int getEndImportedStatus() {
		return endImportedStatus;
	}
	public void setEndImportedStatus(int endImportedStatus) {
		this.endImportedStatus = endImportedStatus;
	}
	public String getEndProductInfo() {
		return endProductInfo;
	}
	public void setEndProductInfo(String endProductInfo) {
		this.endProductInfo = endProductInfo;
	}
	public String getEndMember() {
		return endMember;
	}
	public void setEndMember(String endMember) {
		this.endMember = endMember;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getEndImportedOpinion() {
		return endImportedOpinion;
	}
	public void setEndImportedOpinion(String endImportedOpinion) {
		this.endImportedOpinion = endImportedOpinion;
	}
	public int getFinalAuditStatus() {
		return finalAuditStatus;
	}
	public void setFinalAuditStatus(int finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}
	public int getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public ProjectData getProjectData() {
		return projectData;
	}
	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}
	public int getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(int endFlag) {
		this.endFlag = endFlag;
	}
	public String getEndOpinionFeedback() {
		return endOpinionFeedback;
	}
	public void setEndOpinionFeedback(String endOpinionFeedback) {
		this.endOpinionFeedback = endOpinionFeedback;
	}
	public int getEndResult() {
		return endResult;
	}
	public void setEndResult(int endResult) {
		this.endResult = endResult;
	}
	public int getEndNoauditResult() {
		return endNoauditResult;
	}
	public void setEndNoauditResult(int endNoauditResult) {
		this.endNoauditResult = endNoauditResult;
	}
	public int getEndExcellentResult() {
		return endExcellentResult;
	}
	public void setEndExcellentResult(int endExcellentResult) {
		this.endExcellentResult = endExcellentResult;
	}
	public void setVarPending(int varPending) {
		this.varPending = varPending;
	}
	public int getVarPending() {
		return varPending;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeOther() {
		return productTypeOther;
	}
	public void setProductTypeOther(String productTypeOther) {
		this.productTypeOther = productTypeOther;
	}
	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}
	public int getTimeFlag() {
		return timeFlag;
	}
	public String getEndId() {
		return endId;
	}
	public void setEndId(String endId) {
		this.endId = endId;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
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
	public Integer getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(Integer repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public int getEndResultPublish() {
		return endResultPublish;
	}
	public void setEndResultPublish(int endResultPublish) {
		this.endResultPublish = endResultPublish;
	}
	
}

