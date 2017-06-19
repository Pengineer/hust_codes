package csdc.action.project;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectGranted;
import csdc.service.IMessageAuxiliaryService;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.HqlTool;
import csdc.tool.StringTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.FileRecord;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;
/**
 * 项目年检父类管理
 * @author 肖雅
 */
public abstract class AnninspectionApplyAction extends ProjectBaseAction{
	
	private static final long serialVersionUID = 1L;
	protected static final String HQL1 = "select app.id, gra.id, gra.name, uni.id, uni.name, gra.applicantId, gra.applicantName, " +
		"so.name, app.disciplineType, app.year, ann.status, ann.file, ann.id, ann.finalAuditStatus, ann.finalAuditResult ";
	protected ProjectAnninspection anninspection;	
	protected String filepath;//文件路径
	protected int annApplicantSubmitStatus;//提交状态
	protected int annResult;//年检结果	1：不合格	2：合格
	protected Integer annYear;//年检年度
	protected int annImportedStatus;//年检结果录入状态
	protected String annImportedOpinion;//录入年检意见
	protected String annOpinionFeedback;//录入年检意见（反馈给项目负责人）
	protected Date annDate;//年检时间
	protected String note;//备注
	protected String projectName,projectNumber,researchType,projectSubtype,projectTopic,dtypeNames,
	applicant,university,divisionName,provinceName,memberName,discipline;//高级检索条件
	protected int applicantSubmitStatus;//高级检索条件
	protected int startYear,endYear,auditStatus,isApproved,projectStatus;//高级检索条件
	protected Date startDate,endDate;////高级检索条件
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;//标题提交上来的特征码list
	protected String[] annFileId;	//标题提交上来的特征码list,用于结项后上传申请书
	protected String uploadKey;//文件上传授权码
	protected int annFlag;//添加年检是否成功标志位	1：添加成功
	protected int timeFlag = 1;//业务时间是否有效标志位  1：有效
	private String annId;//年检id
	
	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"负责人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"提交状态",
		"年检状态",
		"年检状态",
		"年检状态",
		"年检状态",
		"年检时间",
		"年检时间",
		"年检时间",
		"年检时间",
		"年检时间"
	};
	private static final String[] COLUMN = {
		"gra.name",
		"gra.applicantName",
		"uni.name",
		"so.name",
		"app.disciplineType",
		"app.year",
		"ann.applicantSubmitStatus",
		"ann.deptInstAuditStatus, ann.deptInstAuditResult",
		"ann.universityAuditStatus, ann.universityAuditResult",
		"ann.provinceAuditStatus, ann.provinceAuditResult",
		"ann.finalAuditStatus, ann.finalAuditResult",
		"ann.applicantSubmitDate desc",
		"ann.deptInstAuditDate desc",
		"ann.universityAuditDate desc",
		"ann.provinceAuditDate desc",
		"ann.finalAuditDate desc"
	};//排序列
	public abstract String listHql2();
	public abstract String listHql3();
	
	public String[] columnName() {
		return AnninspectionApplyAction.CCOLUMNNAME;
	}
	public String[] column() {
		return AnninspectionApplyAction.COLUMN;
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
		if (null == session.get("annAssistMap") && cloumn != 2 && cloumn != 3 && cloumn != 5) {
			cloumn = 4;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 || cloumn == 4 || cloumn == 5) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "ann.id");
		} else {
			jsonMap = (Map) session.get("annAssistMap");
		}
		session.put("annAssistMap", jsonMap);
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
		hql.append(this.projectService.getAnnSearchHQLWordAdd(accountType));
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getAnnSimpleSearchHQL(searchType));
		}
		hql.append(this.projectService.getAnnSimpleSearchHQLAdd(accountType));
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
			hql.append(" group by " + hqlTool.getSelectClause() + " having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
		} else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", ann.applicantSubmitDate having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
		}
		System.out.println(hql.toString());
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
			null
		};
	}
	
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
		hql.append(this.projectService.getAnnSearchHQLWordAdd(accountType));
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
		if(memberName!=null && !memberName.isEmpty()){
			memberName = memberName.toLowerCase();
			hql.append(" and LOWER(mem.memberName) like :memberName ");
			map.put("memberName", "%" + memberName + "%");
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
				hql.append(" and ann.deptInstAuditDate is not null and to_char(ann.deptInstAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append(" and ann.universityAuditDate is not null and to_char(ann.universityAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append(" and ann.provinceAuditDate is not null and to_char(ann.provinceAuditDate,'yyyy-MM-dd')>=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and ann.finalAuditDate is not null and to_char(ann.finalAuditDate,'yyyy-MM-dd')>=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				if(startDate == null){
					hql.append(" and ann.deptInstAuditDate is not null ");
				}
				hql.append(" and to_char(ann.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				if(startDate == null){
					hql.append(" and ann.universityAuditDate is not null ");
				}
				hql.append(" and to_char(ann.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){
				if(startDate == null){
					hql.append(" and ann.provinceAuditDate is not null ");
				}
				hql.append(" and to_char(ann.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				if(startDate == null){
					hql.append(" and ann.finalAuditDate is not null ");
				}
				hql.append(" and to_char(ann.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		int resultStatus,saveStatus;
		if(auditStatus!=-1){
			saveStatus=auditStatus/10;
			resultStatus=auditStatus%10;
			map.put("auditStatus",  saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){ 
				hql.append("  and ann.deptInstAuditStatus =:auditStatus and ann.deptInstAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				hql.append("  and ann.universityAuditStatus =:auditStatus and ann.universityAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.PROVINCE)){
				hql.append("  and ann.provinceAuditStatus =:auditStatus and ann.provinceAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(" and ann.finalAuditStatus =:auditStatus and ann.finalAuditResult =:auditResult");
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
				hql.append(" and ann.finalAuditStatus != 3 ");
			}else{
				hql.append(" and ann.finalAuditStatus = 3 and ann.finalAuditResult =:isApproved ");
				map.put("isApproved", isApproved);
			}
		}
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" and (ann.status >= 1 or ann.createMode = 1 or ann.createMode = 2) ");
			if(applicantSubmitStatus != -1){
				map.put("submitStatus",  applicantSubmitStatus);
				hql.append(" and ann.applicantSubmitStatus =:submitStatus");
			}
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql.append(" and (ann.status >= 2 or ann.createMode = 1 or ann.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(" and (ann.status >= 3 or ann.createMode = 1 or ann.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql.append(" and (ann.status >= 4 or ann.createMode = 1 or ann.createMode = 2) ");
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql.append(" and (ann.status >= 5 or ann.createMode = 1 or ann.createMode = 2) ");
		}
		session.put("grantedMap", map);
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("grantedMap");
		if(accountType.equals(AccountType.INSTITUTE)){
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
		if (accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(" group by " + hqlTool.getSelectClause() + " having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
		} else {
			hql.append(" group by " + hqlTool.getSelectClause() + ", ann.applicantSubmitDate having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate)");
		}
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
		if (memberName != null && !memberName.isEmpty()) {
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
		if(provinceName!=null && !provinceName.isEmpty()){
			searchQuery.put("provinceName", provinceName);
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			searchQuery.put("divisionName", divisionName);
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
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			searchQuery.put("submitStatus",  applicantSubmitStatus);
		}
	}
	
	public String toAdd(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		annYear = this.projectService.getBusinessAnnDefaultYear(businessType(), "businessType");
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return isTimeValidate();
	}
	/**
	 * 添加项目年检申请公共处理
	 * @param anninspection 年检对象
	 * @author 肖雅
	 * @return 处理后的项目年检对象
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectAnninspection doWithAdd(ProjectAnninspection anninspection) throws Exception{
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAnnFile(projectType(), projectid, annApplicantSubmitStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		anninspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
   		anninspection.setYear(annYear);
		if(note != null){
			anninspection.setNote(("A"+note).trim().substring(1));
		}else{
			anninspection.setNote(null);
		}
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, annApplicantSubmitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
		anninspection.edit(auditMap);//保存操作结果
		/* 以下代码为跳过部门审核*/
		if(annApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			anninspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(anninspection);
		anninspection.setDfs(dfsId);
		return anninspection;
	}
	public void validateAdd(){
		validateEdit(1);
	}
	
	/**
	 * 准备修改年检申请
	 * @author 肖雅
	 */
	public String toModify(){
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		//年检业务状态1：业务激活0：业务停止
		AccountType accountType = loginer.getCurrentType();
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		deadline  = this.projectService.checkIfTimeValidate(accountType, businessType(), appId);
		ProjectAnninspection anninspection = (ProjectAnninspection)this.projectService.getCurrentAnninspectionByGrantedId(projectid);
		if (anninspection.getProjectFee() != null) {
			projectFeeAnn = dao.query(ProjectFee.class, anninspection.getProjectFee().getId());
		}
		annId = anninspection.getId();
		annYear = this.projectService.getBusinessAnnDefaultYear(businessType(), "businessType");
		note = anninspection.getNote();
		
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + anninspection.getId();
		uploadService.resetGroup(groupId);
		if (anninspection.getFile() != null) {
			String[] tempFileRealpath = anninspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(anninspection.getDfs() != null && !anninspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(anninspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = anninspection.getFile().substring(anninspection.getFile().lastIndexOf("/") + 1);
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
	 * 修改年检申请
	 * @author 肖雅
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modify() throws Exception {
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectAnninspection anninspection = (ProjectAnninspection) this.dao.query(ProjectAnninspection.class, annId);
		String orignFile = anninspection.getFile();
		if(anninspection.getStatus() > 1){//未提交的申请才可修改
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		ProjectFee oldprojectFeeAnn = null;
		if (anninspection.getProjectFee() != null) {
			oldprojectFeeAnn = dao.query(ProjectFee.class, anninspection.getProjectFee().getId());
			oldprojectFeeAnn = this.projectService.updateProjectFee(oldprojectFeeAnn,projectFeeAnn);
			dao.modify(oldprojectFeeAnn);
		}else {
			oldprojectFeeAnn = this.projectService.setProjectFee(projectFeeAnn);
			oldprojectFeeAnn.setType(3);
			dao.add(oldprojectFeeAnn);
		}
		anninspection.setProjectFee(oldprojectFeeAnn);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, annApplicantSubmitStatus, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		anninspection.edit(auditMap);//保存操作结果
		/* 以下代码为跳过部门审核*/
		if(annApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			anninspection.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		//保存上传的文件
		String groupId = "file_" + anninspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAnnFile(projectType(), projectid, annApplicantSubmitStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		anninspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
   		anninspection.setYear(annYear);
		if(note != null){
			anninspection.setNote(("A"+note).trim().substring(1));
		}else{
			anninspection.setNote(null);
		}
		dao.modify(anninspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != anninspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != anninspection.getFile()){ //现在有文件
					projectService.checkInToDmss(anninspection);
				}else{ //现在没文件
					dmssService.deleteFile(anninspection.getDfs());
					anninspection.setDfs(null);
					dao.modify(anninspection);
				}
			}else{ //原来没有文件
				if(anninspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(anninspection);
					anninspection.setDfs(dfsId);
					dao.modify(anninspection);
				}
			}
		}
		annFlag= 1;
		return SUCCESS;
	}
	public void validateModify(){
		validateEdit(2);
	}
	
	/**
	 * 提交年检申请
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectAnninspection anninspection = (ProjectAnninspection)this.projectService.getCurrentAnninspectionByGrantedId(projectid);
		if(anninspection.getStatus() > 1){//未提交的申请才可提交
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
		//File midWordFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + midinspection.getFile());
		//projectService.importMidXMLData(projectid, midWordFile);
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		anninspection.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		anninspection.edit(auditMap);//部门审核通过
		/* 结束 */
		dao.modify(anninspection);
		return SUCCESS;
	}
	
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
	 * 编辑年检校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked"})
	public void validateEdit(int type){
		String info = "";
		String appId = this.projectService.getApplicationIdByGrantedId(projectid).trim();
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			anninspection = this.projectService.getCurrentAnninspectionByGrantedId(projectid);
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
			if(!this.projectService.getDireIdByAppId(appId, granted.getMemberGroupNumber()).contains(baseService.getBelongIdByLoginer(loginer))){//当前用户是负责人
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0 || this.projectService.getPendingEndinspectionByGrantedId(this.projectid).size()>0){//存在已通过或待处理结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
			if(type == 1){//添加时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), annYear, "", projectid)){//年检是否在已在指定年度审核
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info += ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(annYear, projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}else if(type == 2){//修改时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), annYear, anninspection.getId(), projectid)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info+=ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(annYear, projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}else if(type == 3){//提交时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), anninspection.getYear(), anninspection.getId(), projectid)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info+=ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(anninspection.getYear(), projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}
		}
		//校验业务设置状态
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (annStatus == 0){
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
		if(type == 1){//添加年检
			if(!this.projectService.getPendingAnninspectionByGrantedId(projectid).isEmpty()){//有未处理年检
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_DEALING);
				info += ProjectInfo.ERROR_ANN_DEALING;
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
		if(type == 1 || type == 2){//添加或修改年检申请
			if(this.projectService.getPassEndinspectionByGrantedId(this.projectid).size()>0){//项目已经结项
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_END_PASS);
				info += ProjectInfo.ERROR_END_PASS;
			}
			if(note != null && note.length() > 200){
				this.addFieldError("note", ProjectInfo.ERROR_ANN_NOTE_OUT);
				info += ProjectInfo.ERROR_ANN_NOTE_OUT;
			}
			if(annApplicantSubmitStatus != 2 && annApplicantSubmitStatus != 3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
		}
		// 上传年检word宏的校验
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
//				System.out.println("校验年检word...");
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
	 * 删除年检申请
	 * @auhtor 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete() {
		for(int i=0;i < entityIds.size();i++){
			if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(entityIds.get(0)), checkGrantedFlag(), true)){
				this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			ProjectAnninspection anninspection = (ProjectAnninspection)this.projectService.getCurrentAnninspectionByGrantedId(entityIds.get(i));
			if(anninspection != null){
				if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  anninspection.getStatus() > 1){//除系统管理员外未提交的申请才可删除
					this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				}
				this.projectService.deleteAnninspection(anninspection);
			}else{
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_NOT_EXIST);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void validateDelete(){
		String info ="";
		String appId = this.projectService.getApplicationIdByGrantedId(entityIds.get(0)).trim();
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_DELETE_NULL);
			info += ProjectInfo.ERROR_ANN_DELETE_NULL;
		}
		//校验业务设置状态
		annStatus = this.projectService.getBusinessStatus(businessType(), appId);
		if (annStatus == 0){
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
	 * 准备添加年检结果
	 * @author 肖雅
	 */
	public String toAddResult(){
		annDate = new Date();
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	/**
	 * 添加和总监结果公共部分
	 * @param anninspection 年检对象
	 * @auhtor 肖雅
	 * @return 处理后的年检对象
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public ProjectAnninspection doWithAddResult(ProjectAnninspection anninspection) throws Exception{
		 //保存上传的文件
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAnnFile(projectType(), projectid, annImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		anninspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		Date submitDate = this.projectService.setDateHhmmss(annDate);
		anninspection.setApplicantSubmitDate(submitDate);
		anninspection.setYear(annYear);
		anninspection.setFinalAuditDate(submitDate);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, annResult, annImportedStatus, null);
		anninspection.setCreateDate(new Date());
		anninspection.setFinalAuditor(auditInfo.getAuditor());
		anninspection.setFinalAuditorName(auditInfo.getAuditorName());
		anninspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		anninspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		anninspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		anninspection.setCreateMode(1);//设为导入数据
		anninspection.setFinalAuditStatus(this.annImportedStatus);
		anninspection.setFinalAuditResult(this.annResult);
		if(annImportedOpinion != null && annImportedOpinion.trim().length() > 0){
			anninspection.setFinalAuditOpinion(("A" + annImportedOpinion).trim().substring(1));
		}else{
			anninspection.setFinalAuditOpinion(null);
		}
		if(annOpinionFeedback != null && annOpinionFeedback.trim().length() > 0){
			anninspection.setFinalAuditOpinionFeedback(("A" + annOpinionFeedback).trim().substring(1));
		}else{
			anninspection.setFinalAuditOpinionFeedback(null);
		}
		uploadService.flush(groupId);
		String dfsId = projectService.uploadToDmss(anninspection);
		anninspection.setDfs(dfsId);
		return anninspection;
	}
	
	/**
	 * 准备修改年检结果
	 * @author 肖雅
	 */
	public String toModifyResult(){
		ProjectAnninspection anninspection = (ProjectAnninspection)this.projectService.getCurrentPendingImpAnninspectionByGrantedId(this.projectid);
		if (anninspection.getProjectFee() != null) {
		projectFeeAnn = dao.query(ProjectFee.class, anninspection.getProjectFee().getId());
		}
		annDate = anninspection.getFinalAuditDate();
		annYear = anninspection.getYear();
		annId = anninspection.getId();
		annImportedOpinion = anninspection.getFinalAuditOpinion();
		annOpinionFeedback = anninspection.getFinalAuditOpinionFeedback();
		this.annResult = anninspection.getFinalAuditResult();
		//文件上传
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + anninspection.getId();
		uploadService.resetGroup(groupId);
		if (anninspection.getFile() != null) {
			String[] tempFileRealpath = anninspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(anninspection.getDfs() != null && !anninspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(anninspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = anninspection.getFile().substring(anninspection.getFile().lastIndexOf("/") + 1);
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
	 * 添加年检结果
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
		anninspection = (ProjectAnninspection)this.projectService.getCurrentPendingImpAnninspectionByGrantedId(this.projectid);
		String orignFile = anninspection.getFile();
		if (anninspection.getProjectFee() != null) {
			projectFeeAnn = dao.query(ProjectFee.class, anninspection.getProjectFee().getId());
			projectFeeAnn = this.doWithAddResultFee(projectFeeAnn);
			dao.modify(projectFeeAnn);
		}
		//保存上传的文件
		String groupId = "file_" + anninspection.getId();
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			//只能上传一个，在js中作了限制
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAnnFile(projectType(), projectid, annImportedStatus, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		anninspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		
		Date submitDate = this.projectService.setDateHhmmss(annDate);
		anninspection.setApplicantSubmitDate(submitDate);
		anninspection.setFinalAuditDate(submitDate);
		anninspection.setYear(annYear);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, annResult, annImportedStatus, null);
		anninspection.setUpdateDate(new Date());
		anninspection.setFinalAuditor(auditInfo.getAuditor());
		anninspection.setFinalAuditorName(auditInfo.getAuditorName());
		anninspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		anninspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		anninspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		anninspection.setFinalAuditStatus(this.annImportedStatus);
		anninspection.setFinalAuditResult(this.annResult);
		if(annImportedOpinion != null && annImportedOpinion.trim().length() > 0){
			anninspection.setFinalAuditOpinion(("A" + annImportedOpinion).trim().substring(1));
		}else{
			anninspection.setFinalAuditOpinion(null);
		}
		if(annOpinionFeedback != null && annOpinionFeedback.trim().length() > 0){
			anninspection.setFinalAuditOpinionFeedback(("A" + annOpinionFeedback).trim().substring(1));
		}else{
			anninspection.setFinalAuditOpinionFeedback(null);
		}
		dao.modify(anninspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != anninspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile){//原来有文件
				if(null != anninspection.getFile()){ //现在有文件
					projectService.checkInToDmss(anninspection);
				}else{ //现在没文件
					dmssService.deleteFile(anninspection.getDfs());
					anninspection.setDfs(null);
					dao.modify(anninspection);
				}
			}else{ //原来没有文件
				if(anninspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(anninspection);
					anninspection.setDfs(dfsId);
					dao.modify(anninspection);
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 提交年检结果
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		if(!this.projectService.checkIfUnderControl(loginer, this.projectService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		anninspection = (ProjectAnninspection)this.projectService.getCurrentPendingImpAnninspectionByGrantedId(this.projectid);
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		anninspection.setUpdateDate(new Date());
		anninspection.setFinalAuditor(auditInfo.getAuditor());
		anninspection.setFinalAuditorName(auditInfo.getAuditorName());
		anninspection.setFinalAuditorAgency(auditInfo.getAuditorAgency());
		anninspection.setFinalAuditorDept(auditInfo.getAuditorDept());
		anninspection.setFinalAuditorInst(auditInfo.getAuditorInst());
		anninspection.setFinalAuditStatus(3);
		dao.modify(anninspection);
		return SUCCESS;
	}
	
	/**
	 * 添加年检结果校验
	 * @author 肖雅
	 */
	public void validateAddResult(){
		validateEditResult(1);
	}
	
	/**
	 * 修改年检结果校验
	 * @author 肖雅
	 */
	public void validateModifyResult(){
		validateEditResult(2);
	}
	
	/**
	 * 提交年检结果校验
	 * @author 肖雅
	 */
	public void validateSubmitResult(){
		validateEditResult(3);
	}
	
	/**
	 * 准备导入上传电子文档
	 * @author 肖雅
	 */
	public String toUploadFileResult(){
		ProjectAnninspection anninspection = (ProjectAnninspection)this.dao.query(ProjectAnninspection.class, annId);
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		if (anninspection.getFile() != null) {
			String[] tempFileRealpath = anninspection.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(anninspection.getDfs() != null && !anninspection.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(anninspection.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = anninspection.getFile().substring(anninspection.getFile().lastIndexOf("/") + 1);
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
		ProjectAnninspection anninspection;
		annImportedStatus = 3;
		anninspection = (ProjectAnninspection)this.dao.query(ProjectAnninspection.class, annId);
		String orignFile = anninspection.getFile();
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAnnFileResult(projectType(), projectid, annImportedStatus, curFile, annId);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		anninspection.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		dao.modify(anninspection);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orignFile != anninspection.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orignFile && !orignFile.isEmpty()){//原来有文件
				if(null != anninspection.getFile()){ //现在有文件
					projectService.checkInToDmss(anninspection);
				}else{ //现在没文件
					dmssService.deleteFile(anninspection.getDfs());
					anninspection.setDfs(null);
					dao.modify(anninspection);
				}
			}else{ //原来没有文件
				if(anninspection.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(anninspection);
					anninspection.setDfs(dfsId);
					dao.modify(anninspection);
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
//		if(null == annFileId || annFileId.length == 0){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//		}else if(annFileId.length > 1){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//		}
	}
	
	/**
	 * 下载模板
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
		String filename = "/temp/" + sessionId + "/general/2011anninspection.zip";
		filepath = new String("2011anninspection.zip".getBytes(), "ISO-8859-1");
		return ApplicationContainer.sc.getResourceAsStream(filename);
	}
	
	/**
	 * 文件是否存在校验
	 */
	public String validateTemplate()throws Exception{
//		String sessionId = ServletActionContext.getRequest().getSession().getId();
//		if(!projectService.createAnninspectionZip(projectid, loginer.getCurrentBelongId(), sessionId, projectType())) {
//			return ERROR;
//		}
//		String filename = "/temp/" + sessionId + "/general/2011anninspection.zip";
//		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
//			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
//		}
		//System.out.println(">>>>>>>>>>>>gshn");
		//File file1 = new File("F:/教育部人文社会科学研究项目终结报告书.doc");
		// 测试上传导入
		//generalService.importEndinspectionWordXMLData(file1, "4028d898348ebb7d01348ec273200019", projectType());
		return SUCCESS;
	}
	
	/**
	 * 项目年检申请表下载
	 * @author 肖雅
	 */
	public String downloadApply()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 文件下载流
	 * @author 肖雅
	 */
	public InputStream getTargetFile() throws Exception{
		InputStream downloadStream = null;
		ProjectAnninspection anninspection = (ProjectAnninspection) this.dao.query(ProjectAnninspection.class, entityId);
		filepath = anninspection.getFile();
		String filename="";
		if(filepath != null && filepath.length()!=0){
			filename=new String(filepath.getBytes("iso8859-1"),"utf-8");
			filepath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(anninspection.getDfs());
			}
		 }
		return downloadStream;
	}
	
	/**
	 * 文件是否存在校验
	 * @author 肖雅
	 */
	@SuppressWarnings("unchecked")
	public String validateFile()throws Exception{
		if (null == entityId || entityId.trim().isEmpty()) {//id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} else {
			ProjectAnninspection anninspection = (ProjectAnninspection) this.dao.query(ProjectAnninspection.class, this.entityId);
			if(null == anninspection){//年检不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(anninspection.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				filepath = anninspection.getFile();
				String filename = new String(filepath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == anninspection.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != anninspection.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑年检结果校验
	 * @param type 校验类型：1添加；2修改;3提交
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked"})
	public void validateEditResult(int type){
		String info ="";
		if (projectid == null || projectid.isEmpty()) {//项目id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info += GlobalInfo.ERROR_EXCEPTION_INFO;
		}else{
			ProjectGranted granted = (ProjectGranted) this.dao.query(ProjectGranted.class, projectid);
			anninspection = this.projectService.getCurrentAnninspectionByGrantedId(projectid);
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
			if(type == 1){//添加时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), annYear, "", projectid)){//年检是否在已在指定年度审核
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info += ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(annYear, projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}else if(type == 2){//修改时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), annYear, anninspection.getId(), projectid)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info+=ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(annYear, projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}else if(type == 3){//提交时校验
				if(!this.projectService.isAuditReport(granted.getAnninspectionClassName(), anninspection.getYear(), anninspection.getId(), projectid)){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_AUDIT_ALREADY);
					info+=ProjectInfo.ERROR_ANN_AUDIT_ALREADY;
				}
				if(!this.projectService.isEarlyGranted(anninspection.getYear(), projectid)){//年检年度是否早于项目年度
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_EARLY_GRANTED);
					info += ProjectInfo.ERROR_ANN_EARLY_GRANTED;
				}
			}
		}
		if(type==1 || type==2){//添加修改时校验
			if(annImportedStatus!=2 && annImportedStatus!=3){
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info += GlobalInfo.ERROR_EXCEPTION_INFO;
			}
			if(annYear == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_RESULT_NULL);
				info += ProjectInfo.ERROR_ANN_RESULT_NULL;
			}
			if(annResult!=2 && annResult!=1){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_RESULT_NULL);
				info += ProjectInfo.ERROR_ANN_RESULT_NULL;
			}
			if(annDate == null){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_DATE_NULL);
				info += ProjectInfo.ERROR_ANN_DATE_NULL;
			}
			if(annImportedOpinion != null && annImportedOpinion.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_OPINION_OUT);
				info += ProjectInfo.ERROR_ANN_OPINION_OUT;
			}
			if(annOpinionFeedback != null && annOpinionFeedback.trim().length() > 200){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ANN_OPINION_OUT);
				info += ProjectInfo.ERROR_ANN_OPINION_OUT;
			}
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getAnnApplicantSubmitStatus() {
		return annApplicantSubmitStatus;
	}
	public void setAnnApplicantSubmitStatus(int annApplicantSubmitStatus) {
		this.annApplicantSubmitStatus = annApplicantSubmitStatus;
	}
	public int getAnnResult() {
		return annResult;
	}
	public void setAnnResult(int annResult) {
		this.annResult = annResult;
	}
	public Integer getAnnYear() {
		return annYear;
	}
	public void setAnnYear(Integer annYear) {
		this.annYear = annYear;
	}
	public int getAnnImportedStatus() {
		return annImportedStatus;
	}
	public void setAnnImportedStatus(int annImportedStatus) {
		this.annImportedStatus = annImportedStatus;
	}
	public String getAnnImportedOpinion() {
		return annImportedOpinion;
	}
	public void setAnnImportedOpinion(String annImportedOpinion) {
		this.annImportedOpinion = annImportedOpinion;
	}
	public String getAnnOpinionFeedback() {
		return annOpinionFeedback;
	}
	public void setAnnOpinionFeedback(String annOpinionFeedback) {
		this.annOpinionFeedback = annOpinionFeedback;
	}
	public Date getAnnDate() {
		return annDate;
	}
	public void setAnnDate(Date annDate) {
		this.annDate = annDate;
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
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
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
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public int getAnnFlag() {
		return annFlag;
	}
	public void setAnnFlag(int annFlag) {
		this.annFlag = annFlag;
	}
	public int getTimeFlag() {
		return timeFlag;
	}
	public void setTimeFlag(int timeFlag) {
		this.timeFlag = timeFlag;
	}
	public ProjectAnninspection getAnninspection() {
		return anninspection;
	}
	public void setAnninspection(ProjectAnninspection anninspection) {
		this.anninspection = anninspection;
	}
	public String[] getAnnFileId() {
		return annFileId;
	}
	public void setAnnFileId(String[] annFileId) {
		this.annFileId = annFileId;
	}
	public String getAnnId() {
		return annId;
	}
	public void setAnnId(String annId) {
		this.annId = annId;
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
	
}
