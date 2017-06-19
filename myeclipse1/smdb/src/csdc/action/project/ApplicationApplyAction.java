package csdc.action.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import csdc.bean.GeneralApplication;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectMidinspection;
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
 * 项目申请父类管理：实现了子类需要实现的抽象方法并实现了所有项目申请申请共用的相关方法
 * @author 余潜玉
 */
public abstract class ApplicationApplyAction extends ProjectBaseAction {

	private static final long serialVersionUID = -700148736686965249L;
	protected static final String HQL1 = "select app.id, app.name, app.applicantId, app.applicantName, " +
			"uni.id, app.agencyName, so.name, app.disciplineType, app.year, app.finalAuditResult, app.status, app.file, app.finalAuditStatus  ";
	protected String savePath;//文件上传下载路径
	protected int proApplicantSubmitStatus;//申请提交状态	2:暂存	3:提交
	protected Integer year;//项目年度
	protected int applyResult;//申请结果	1：不同意	2：同意
	protected Date appDate;//申请时间
	protected String projectName,researchType,projectSubtype,dtypeNames,applicant,university,projectTopic,divisionName,provinceName,memberName,discipline;//高级检索条件
	protected int deptInstFlag;//院系或研究机构标志位	1：研究机构	2：院系
	protected int isEstab,startYear,endYear;//高级检索条件
	protected String teInstFlag;
	protected int appResultPublish; //申报审核结果是否允许发布标志位  0：不允许发布 1：允许发布
	//异步文件上传所需
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	protected String[] fileIds;//标题提交上来的特征码list
	protected String[] appFileId;	//标题提交上来的特征码list,用于申请后上传申请书
	protected String uploadKey;//文件上传授权码
	protected int appFlag;//添加申请是否成功标志位	1：添加成功
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	private static final String[] CCOLUMNNAME = {
		"项目名称",
		"申请人",
		"依托高校",
		"项目子类",
		"学科门类",
		"项目年度",
		"提交状态",
		"审核状态",
		"审核状态",
		"审核状态",
		"审核状态",
		"评审状态",
		"申请状态",
		"申请时间",
		"审核时间",
		"审核时间",
		"审核时间",
		"申请时间"
	};
	private static final String[] COLUMN = {
		"app.name",
		"app.applicantName",
		"app.agencyName",
		"so.name",
		"app.disciplineType",
		"app.year desc",
		"app.applicantSubmitStatus",
		"app.deptInstAuditStatus, app.deptInstAuditResult",
		"app.universityAuditStatus, app.universityAuditResult",
		"app.provinceAuditStatus, app.provinceAuditResult",
		"app.ministryAuditStatus, app.ministryAuditResult",
		"app.reviewStatus, app.reviewResult",
		"app.finalAuditStatus, app.finalAuditResult",
		"app.applicantSubmitDate desc",
		"app.deptInstAuditDate desc",
		"app.universityAuditDate desc",
		"app.provinceAuditDate desc",
		"app.finalAuditDate desc",
		"app.firstAuditDate",
		"app.firstAuditResult"
	};//排序列
	public abstract String grantedClassName();//项目立项类类名
	public abstract String listHql2();
	public abstract String listHql3();
	public String[] column() {
		return ApplicationApplyAction.COLUMN;
	}
	public String[] columnName() {
		return ApplicationApplyAction.CCOLUMNNAME;
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
		if (null == session.get("appAssistMap") && cloumn != 2 && cloumn != 3 && cloumn != 5) {
			cloumn = 4;
		}
		if (column()[pager.getSortColumn()].contains(", ") || cloumn == 2 || cloumn == 3 || cloumn == 4 || cloumn == 5 ) {
			jsonMap = messageAssistService.projectListAssist(pager, column()[cloumn], columnName()[cloumn], "项目数量", "app.id");
		} else {
			jsonMap = (Map) session.get("appAssistMap");
		}
		session.put("appAssistMap", jsonMap);
		return SUCCESS;
	}
	/**
	 * 进入项目录入添加页面预处理
	 */
	public String toAddResult(){
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
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
		hql.append(this.projectService.getAppSimpleSearchHQLWordAdd(accountType));
		if(loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getAppSimpleSearchHQL(searchType));
		}
		hql.append(this.projectService.getAppSimpleSearchHQLAdd(accountType));
		session.put("applicationMap", map);
		if(null != mainFlag && !mainFlag.isEmpty()){//项目管理首页进入
			String searchHql = this.projectService.mainSearch(account, mainFlag, projectType());
			hql.append(searchHql);
		}
		//处理查询范围
		String addHql = projectService.applicationInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("applicationMap");
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.selectClause);
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 14;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 15;
//			hql.append(", app.finalAuditDate");
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 16;
		}else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)){
			columnLabel = 17;
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
	//选择单条申请数据发布状态
	@SuppressWarnings("unchecked")
	public String switchPublish() {
		ProjectApplication application = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		if(application != null){
			if(loginer.getCurrentType().compareTo(AccountType.ADMINISTRATOR) > 0 &&  application.getStatus() > 1){//除系统管理员外未提交的申请才可删除
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			}
			if(application.getFinalAuditResultPublish()==0&&appResultPublish==0)
				application.setFinalAuditResultPublish(1);
			else if(application.getFinalAuditResultPublish()==1&&appResultPublish==1)
				application.setFinalAuditResultPublish(0);
			dao.modify(application);
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_MID_NOT_EXIST);
		}
		return SUCCESS;
	}
	/**
	//取消公开发布
	public String notPublish() {
		for(int i = 0; i < entityIds.size(); i++){
			ProjectApplication application = (ProjectApplication)(dao.query(ProjectApplication.class, entityIds.get(i)));
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
			ProjectApplication application = (ProjectApplication)(dao.query(ProjectApplication.class, entityIds.get(i)));
			application.setFinalAuditResultPublish(0);
			dao.modify(application);
		}
		return SUCCESS;
	}
	**/
	
	//高级检索
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType accountType = loginer.getCurrentType();
		hql.append(HQL1);
		hql.append(this.projectService.getAppSimpleSearchHQLWordAdd(accountType));
		if(loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(listHql3());
		}else{//管理人员
			hql.append(listHql2());
		}
		if(projectName!=null && !projectName.isEmpty()){
			projectName = projectName.toLowerCase();
			hql.append(" and LOWER(app.name) like :projectName");
			map.put("projectName", "%" + projectName + "%");
		}
		if(!projectType().equals("key")){
			if(projectSubtype!= null && !projectSubtype.equals("-1")){
				projectSubtype = projectSubtype.toLowerCase();
				hql.append(" and LOWER(so.id) like :projectSubtype");
				map.put("projectSubtype", "%" + projectSubtype + "%");
			}
		}else{//重大攻关项目
			if(researchType!= null && !researchType.equals("-1")){
				researchType = researchType.toLowerCase();
				hql.append(" and LOWER(so.id) like :researchType");
				map.put("researchType", "%" + researchType + "%");
			}
		}
		if(projectType().equals("entrust") && projectTopic!=null && !projectTopic.equals("-1")){
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
		if(startYear!=-1){
			hql.append(" and app.year>=:startYear ");
			map.put("startYear", startYear);
		}
		if(endYear!=-1){
			hql.append(" and app.year<=:endYear ");
			map.put("endYear", endYear);
		}
		if(applicant!=null && !applicant.isEmpty()){
			applicant = applicant.toLowerCase();
			hql.append(" and LOWER(app.applicantName) like :applicant ");
			map.put("applicant", "%" + applicant + "%");
		}
		if(university!=null && !university.isEmpty()){
			university = university.toLowerCase();
			hql.append(" and LOWER(app.agencyName) like :university ");
			map.put("university", "%" + university + "%");
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			divisionName = divisionName.toLowerCase();
			hql.append(" and LOWER(app.divisionName) like :divisionName ");
			map.put("divisionName", "%" + divisionName + "%");
		}
		if(provinceName!=null && !provinceName.isEmpty()){
			provinceName = provinceName.toLowerCase();
			hql.append(" and LOWER(app.provinceName) like :provinceName ");
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
		if(isEstab != -1){
			if(isEstab == 1){
				hql.append(" and app.finalAuditResult=2 and app.finalAuditStatus=3");
			}
			if(isEstab == 2){
				hql.append(" and app.finalAuditResult=1 and app.finalAuditStatus=3");
			}
			if(isEstab == 3){
				hql.append(" and app.finalAuditStatus!=3");
			}
		}
		Map session = ActionContext.getContext().getSession();
		session.put("applicationMap", map);
		Account account = loginer.getAccount();
		//处理查询范围
		String addHql = this.projectService.applicationInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("applicationMap");
		HqlTool hqlTool = new HqlTool(hql.toString());
		hql.append(" group by " + hqlTool.selectClause);
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
		if(!projectType().equals("instp") && !projectType().equals("key") && projectTopic!=null && !projectTopic.equals("-1")){
			searchQuery.put("projectTopic", projectTopic);
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
		if(memberName!=null && !memberName.isEmpty()){
			searchQuery.put("memberName", memberName);
		}
		if(divisionName!=null && !divisionName.isEmpty()){
			searchQuery.put("divisionName", divisionName);
		}
		if(isEstab != -1){
			searchQuery.put("isEstab", isEstab);
		}
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
	 * 提交申请项目
	 * @auhtor 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectApplication application = (ProjectApplication)dao.query(ProjectApplication.class, entityId);//entityId为项目申请id
		if(application.getStatus() > 1){//未提交的申请才可提交
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
//		File endWordFile = new File(ApplicationContainer.sc.getRealPath("") + "/" + application.getFile());
//		projectService.importEndinspectionWordXMLData(endWordFile, projectid, projectType());
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 0, 3, null);
		auditMap.put("auditInfo",auditInfo);
		auditMap.put("isSubUni", 0);
		application.submit(auditMap);//提交操作结果
		/* 以下代码为跳过部门审核*/
		AuditInfo auditInfoDept = new AuditInfo();
		auditInfoDept.setAuditResult(2);
		auditInfoDept.setAuditStatus(3);
		auditMap.put("auditInfo",auditInfoDept);
		application.edit(auditMap);//部门审核通过
		/* 结束 */
		dao.modify(application);
		return SUCCESS;
	}

	/**
	 * 删除申请项目
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
			if(!this.projectService.checkIfUnderControl(loginer, entityIds.get(i).trim(), checkApplicationFlag(), true)){
				jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		for(int i=0;i<entityIds.size();i++){
			this.projectService.deleteProject(entityIds.get(i));
		}
		return SUCCESS;
	}

	/**
	 * 删除项目申请校验
	 */
	@SuppressWarnings("unchecked")
	public void validateDelete(){
		if (entityIds == null || entityIds.isEmpty()) {//删除项目申请id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_APPLY_NULL);
		}
	}
	
	/**
	 * 准备导入上传电子文档
	 * @author 肖雅
	 */
	public String toUploadFileResult(){
		ProjectApplication application = dao.query(ProjectApplication.class, entityId);//entityId为项目申请id
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		if (application.getFile() != null) {
			String[] tempFileRealpath = application.getFile().split("; ");
			//遍历要修改的已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath));
				} else if(application.getDfs() != null && !application.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(application.getDfs());
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = application.getFile().substring(application.getFile().lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId, downloadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		session.put("entityId", entityId);
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
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get("entityId");
		if(!this.projectService.checkIfUnderControl(loginer, entityId.trim(), checkApplicationFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		ProjectApplication application;
		application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		
		//保存上传的文件
		String orgFile = application.getFile();
		String groupId = "file_add";
		List<String> files = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			File curFile = fileRecord.getOriginal();
			String savePath = this.projectService.uploadAppFile(projectType(), application, curFile);
			//将文件放入list中暂存
			files.add(savePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(savePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		application.setFile(StringTool.joinString(files.toArray(new String[0]), "; "));
		dao.modify(application);
		uploadService.flush(groupId);
		//DMSS同步 
		if(orgFile != application.getFile() && dmssService.getStatus()){ //文件已修改
			if(null != orgFile && !orgFile.isEmpty()){//原来有文件
				if(null != application.getFile()){ //现在有文件
					projectService.checkInToDmss(application);
				}else{ //现在没文件
					dmssService.deleteFile(application.getDfs());
					application.setDfs(null);
				}
			}else{ //原来没有文件
				if(application.getFile()!=null){ //现在有文件
					String dfsId = projectService.uploadToDmss(application);
					application.setDfs(dfsId);
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
//		if(null == appFileId || appFileId.length == 0){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NULL);
//		}else if(appFileId.length > 1){
//			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_OUT);
//		}
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
		InputStream downloadStream = null;
		ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, entityId);
		savePath = application.getFile();
		String filename="";
		if(savePath!=null && savePath.length()!=0){
			filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
			savePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			//先从应用服务器本地获取文件流，若本地服务器不能获取，则从云存储中获取文件流
			if(ApplicationContainer.sc.getResourceAsStream(filename) != null) {
				downloadStream = ApplicationContainer.sc.getResourceAsStream(filename);
			} else {
				downloadStream = dmssService.download(application.getDfs());
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
			ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, this.entityId);
			if(null == application){//申请不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
			} else if(application.getFile()==null){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				savePath = application.getFile();
				String filename=new String(savePath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					if(null == application.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					} else if (null != application.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_FILE_NOT_EXIST);
					}
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
	 * 用于检验申请信息
	 * @param application 项目申请对象
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
//		if((deptInstFlag != 1 && deptInstFlag != 2) || (deptInstFlag == 1 && application.getInstitute().getId() == null) || (deptInstFlag == 2 && application.getDepartment().getId() == null)){
//			this.addFieldError("application.agencyName", ProjectInfo.ERROR_AGENCY_NULL);
//		}
		if(loginer.getCurrentType().equals(AccountType.MINISTRY)){//教育部录入
			if(application.getYear()<=0 || application.getYear()>=10000){
				this.addFieldError("application.year", ProjectInfo.ERROR_PROJECT_YEAR_WRONG);
			}
		}
		if(!application.getType().equals("key")){
			if(application.getSubtype().getId() == null || application.getSubtype().getId().equals("-1")){
				this.addFieldError("application.subType.id", ProjectInfo.ERROR_PROJECT_SUBTYPE_NULL);
			}
		}
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
		if(application.getApplyFee() != null && application.getApplyFee() < 0){
			this.addFieldError("application.applyFee", ProjectInfo.ERROR_PROJECT_APPLY_FEE_WRONG);
		}
		if(application.getOtherFee() != null && application.getOtherFee() < 0){
			this.addFieldError("application.otherFee", ProjectInfo.ERROR_PROJECT_OTHER_FEE_WRONG);
		}
//		if(application.getResearchType().getId() == null || application.getResearchType().getId().equals("-1")){
//			this.addFieldError("application.researchType.id", ProjectInfo.ERROR_PROJECT_RESEARCH_TYPE_NULL);
//		}
		if(application.getDisciplineType() == null || application.getDisciplineType().trim().isEmpty() ){
			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_NULL);
		}else if(application.getDisciplineType().length()>100){
			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_OUT);
		}
//		if(application.getDisciplineType() != null && application.getDisciplineType().length()>100){
//			this.addFieldError("application.displineType", ProjectInfo.ERROR_PROJECT_DISCIPLINE_TYPE_OUT);
//		}
//		if(application.getDiscipline() == null || application.getDiscipline().trim().isEmpty() ){
//			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_NULL);
//		}else if(application.getDiscipline().length()>100){
//			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_OUT);
//		}
//		if(application.getDiscipline() != null && application.getDiscipline().length()>100){
//			this.addFieldError("application.displine", ProjectInfo.ERROR_PROJECT_DISCIPLINE_OUT);
//		}
		if(application.getRelativeDiscipline() != null && application.getRelativeDiscipline().length()>100 ){
			this.addFieldError("application.relativedispline", ProjectInfo.ERROR_PROJECT_RELATIVE_DISCIPLINE_OUT);
		}
		if(application.getKeywords() != null && application.getKeywords().length()>100){
			this.addFieldError("application.keywords", ProjectInfo.ERROR_PROJECT_KEYWORDS_OUT);
		}
		if(application.getSummary() != null && application.getSummary().length()>200){
			this.addFieldError("application.summary", ProjectInfo.ERROR_PROJECT_SUMMARY_OUT);
		}
		if(application.getNote() != null && application.getNote().length()>200){
			this.addFieldError("application.note", ProjectInfo.ERROR_PROJECT_NOTE_OUT);
		}
	}
	
	/**
	 * 用于检验经费信息
	 * @param projectFee 项目申请经费概算明细
	 * @param applyFee 项目申请总经费
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
	 * @param application 项目申请对象
	 */
	public void validateGrantedInfo(ProjectGranted granted, ProjectApplication application){
		if(granted==null && application.getFinalAuditResult()==2){//立项没立项信息
			this.addActionError(GlobalInfo.ERROR_EXCEPTION_INFO);
		}else if(granted!=null && application.getFinalAuditResult()==2){//立项有立项信息
			if(granted.getNumber() == null || granted.getNumber().trim().isEmpty()){
				this.addFieldError("granted.number", ProjectInfo.ERROR_PROJECT_NUMBER_NULL);
			}else if(granted.getNumber().length()>40){
				this.addFieldError("granted.number", ProjectInfo.ERROR_PROJECT_NUMBER_OUT);
			}else if(!this.projectService.isGrantedNumberUnique(grantedClassName(), granted.getNumber(), application.getId())){
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
		if(member.getMemberType() == -1){
			this.addActionError(ProjectInfo.ERROR_PROJECT_MEMBER_TYPE_NULL);
		}
		//导入数据若无id，录入修改只能手动填写，此处为校验无id导入数据的修改数据
		if(type == 2){
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
		if(loginer.getCurrentType().equals(AccountType.MINISTRY)){//教育部录入
//			if(member.getMember() == null){
//				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_REVIEWER_NULL);
//			}
		}else{//其他
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
		}
//		if(member.getSpecialistTitle()==null || member.getSpecialistTitle().equals("-1") || member.getSpecialistTitle().trim().isEmpty()){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_SPECIALIST_TITLE_NULL);
//		}
//		if(member.getMajor()!=null && member.getMajor().length()> 50){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_MAJOR_OUT);
//		}
//		if(member.getWorkMonthPerYear() == null){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_NULL);
//		}else if(member.getWorkMonthPerYear()<0 || member.getWorkMonthPerYear()>12){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_WRONG);
//		}
//		if(member.getWorkMonthPerYear() != null && (member.getWorkMonthPerYear()<0 || member.getWorkMonthPerYear()>12)){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_WORK_MONTH_WRONG);
//		}
//		if(member.getWorkDivision()!=null && member.getWorkDivision().length()>200){
//			this.addActionError(ProjectInfo.ERROR_PROJECT_DIVISION_OUT);
//		}
		if(member.getIsDirector()!=0 && member.getIsDirector()!=1){
			this.addActionError(ProjectInfo.ERROR_PROJECT_IS_DIRECTOR_NULL);
		}
	}
	
	/**
	 * 添加申请公共处理
	 * @param application 申请对象
	 * @author 肖雅
	 * @return 处理后的申请对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProjectApplication doWithAddOrModify(ProjectApplication application){
		Map auditMap = new HashMap();
		AuditInfo auditInfo = this.projectService.getAuditInfo(loginer, 2, proApplicantSubmitStatus, null);
		auditMap.put("auditInfo", auditInfo);
		auditMap.put("isSubUni", 0);//传0 ：是否部署高校的判断在申请用不到，只在高校审核用到
		application.edit(auditMap);
		/* 以下代码为跳过部门审核*/
		if(proApplicantSubmitStatus == 3){//提交申请
			AuditInfo auditInfoDept = new AuditInfo();
			auditInfoDept.setAuditResult(2);
			auditInfoDept.setAuditStatus(3);
			auditMap.put("auditInfo",auditInfoDept);
			application.edit(auditMap);//部门审核通过
		}
		/* 结束 */
		return application;
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

	public int getIsEstab() {
		return isEstab;
	}

	public void setIsEstab(int isEstab) {
		this.isEstab = isEstab;
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
	public int getProApplicantSubmitStatus() {
		return proApplicantSubmitStatus;
	}
	public void setProApplicantSubmitStatus(int proApplicantSubmitStatus) {
		this.proApplicantSubmitStatus = proApplicantSubmitStatus;
	}
	public int getApplyResult() {
		return applyResult;
	}
	public void setApplyResult(int applyResult) {
		this.applyResult = applyResult;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public int getAppFlag() {
		return appFlag;
	}
	public void setAppFlag(int appFlag) {
		this.appFlag = appFlag;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String[] getAppFileId() {
		return appFileId;
	}
	public void setAppFileId(String[] appFileId) {
		this.appFileId = appFileId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
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
	public String getTeInstFlag() {
		return teInstFlag;
	}
	public void setTeInstFlag(String teInstFlag) {
		this.teInstFlag = teInstFlag;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public int getAppResultPublish() {
		return appResultPublish;
	}
	public void setAppResultPublish(int appResultPublish) {
		this.appResultPublish = appResultPublish;
	}
	
}