package csdc.action.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectApplicationReviewUpdate;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.bean.common.Visitor;
import csdc.service.IExpertService;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;


public abstract class ApplicationAction extends ProjectAction{


	private static final long serialVersionUID = 1L;
	protected ProjectApplication project;	//一般项目实体
	protected String projectId;				//项目id
	protected String discipline;			//一级学科代码字符串
	protected String selectExp;				//从专家树中选择的专家的ID
	protected List<String> expertIds;		//后台代码中应用的变量
	protected String selectedExpertIds;		//匹配时选择的专家ids	
	protected List nodesInfo;				//树节点列表
	protected String expertId;				//专家id，用于前后台交互
	protected List<SystemOption> disList;	//一级学科列表
	protected String uname;					//专家树页面检索用的高校名称
	protected String ename;					//专家树页面检索用的专家姓名
	protected int isFromExpert;				//是否从专家详情页面进入项目详情页面

	protected List pojList;					//项目列表
	protected int isReviewable;				//是否参与评审
	protected int exportAll;				//是否导出全部至xls
	protected int flag;						//项目状态，用于列表页面改变项目状态，0 不立项 → 1拟立项 → 2确定立项
	
	protected int deleteManualMatches;		//是否删除手工匹配对，1是，0否
	protected int deleteAutoMatches;		//是否删除自动匹配对，1是，0否
	
	protected long startTime;				//导出匹配更新结果所用起始时间
	protected long endTime;					//导出匹配更新结果所用截止时间
	protected String notReviewReason;		//退评原因
	
	//导出
	protected String fileFileName;			//导出文件名
	protected File xls;
	//评审审核
	protected int reviewAuditResult;//评审审核结果
	protected Date reviewAuditDate;//评审审核时间
	protected String reviewAuditOpinion;//评审审核意见
	protected String reviewAuditOpinionFeedback;//评审审核意见（反馈给项目负责人）
	protected Double approveFee;//项目批准经费
	protected String number;//批准号编号
	
	protected IExpertService expertService;	//专家业务接口对象
	

	public void validateToList() {
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	public void validateToAdd() {
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 进入添加页面
	 * @return SUCCESS进入添加页面
	 */
	public String toAdd() {
		session.put("projectListType", listType);	
		return SUCCESS;
	}
	
	/**
	 * 添加项目
	 * @return SUCCESS跳转查看
	 */
	public String add() {
		listType = (Integer)session.get("projectListType");
		project.setWarning(null);
		project.setWarningReviewer(null);
		project.setYear((Integer) ApplicationContainer.sc.getAttribute("defaultYear"));
		project.setType(projectType());
		entityId = (String) baseService.add(project);
		isReviewable = project.getIsReviewable();
		return SUCCESS;
	}
	
	/**
	 * 加载校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要修改的项目");
		}
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 修改项目
	 * @return
	 */
	public String toModify() {
		session.put("projectId", entityId);
		session.put("projectListType", listType);
		project = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if (project == null) {
			request.setAttribute("errorInfo", "无效的项目");
			return INPUT;
		} else {
			return SUCCESS;
		}
	}
	
	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择项目");
		}
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 删除项目
	 * @return SUCCESS跳转列表
	 */
	public String delete() {
		try{
			projectService.deleteProjects(entityIds, Class.forName("csdc.bean." + className()));
			return SUCCESS;
		}catch(Exception e){
			jsonMap.put(GlobalInfo.ERROR_INFO, "删除不成功");
			return INPUT;
		}
	}
	
	/**
	 * 弹出层导出
	 * @return
	 */
	public String popExport(){
		Map session = ActionContext.getContext().getSession();
		Integer defaultYear = (Integer) session.get("defaultYear");
		List<Object> a = baseService.query("select distinct pru.matchTime from ProjectApplicationReviewUpdate pru where pru.type = '" + projectType() + "' and pru.matchTime = (select min(pru1.matchTime) from ProjectApplicationReviewUpdate pru1 where pru1.type = '" + projectType() + "' and pru1.year = " + defaultYear+ ")");
		if (a != null && a.size() > 0) {
			Date aDate = (Date) a.get(0);
			startTime = aDate.getTime();
		}
		List<Object> b = baseService.query("select distinct pru.matchTime from ProjectApplicationReviewUpdate pru where pru.type = '" + projectType() + "' and pru.matchTime = (select max(pru1.matchTime) from ProjectApplicationReviewUpdate pru1 where pru1.type = '" + projectType() + "' and pru1.year = " + defaultYear+ ")");
		if (b != null && b.size() > 0) {
			Date bDate = (Date) b.get(0);
			endTime = bDate.getTime();	
		}		
		return SUCCESS;
	}
	
	/**
	 * 填写退评原因
	 * @return
	 */
	public String toDisableReview() {
		session.put("entityIds", entityIds);
		return SUCCESS;
	}
	
	/**
	 * 设置为退评
	 * @return
	 */
	public String disableReview() {
		entityIds = (List<String>) session.get("entityIds");
		projectService.setReview(entityIds, 0, notReviewReason);
		return SUCCESS;
	}
	
	public void validateEnableReview() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要参评的项目");
		}
		if (isReviewable != 0 && isReviewable != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的参评状态");
		}
	}
	
	/**
	 * 设置为参评
	 * @return
	 */
	public String enableReview() {
		projectService.setReview(entityIds, 1, null);
		return SUCCESS;
	}
	
	public String toDeleteMatches(){
		return SUCCESS;
	}
	
	/**
	 * 删除已有匹配对所有信息
	 * @return
	 */
	@Transactional
	public String deleteMatches(){
		Integer defaultYear = (Integer) session.get("defaultYear");
		projectService.deleteProjectMatchInfos(deleteAutoMatches,deleteManualMatches,defaultYear,projectType());
		return SUCCESS;
	}
	
	/**
	 * 《公共方法》
	 * 进入手工选择专家页面<br>
	 * 给选定项目选择专家
	 * @return
	 */
	public String toSelectExpert() {
		String[] pojids = entityId.split("[^a-zA-Z0-9]+");
		String tmp = "'" + pojids[0] + "'";
		for(int i = 1; i < pojids.length; i++) 
			tmp += ", '" + pojids[i] + "'";
		Map paraMap = new HashMap();
		paraMap.put("defaultYear", session.get("defaultYear"));
		pojList = baseService.query("select p.id, p.projectName, p.projectType, p.director, u.name, p.discipline from ProjectApplication p, University u where p.type = '" + projectType() +"' and p.year = :defaultYear and p.universityCode = u.code and p.id in ( " + tmp + ") ", paraMap);
		if(pojList != null && !pojList.isEmpty()) {
			String value = null;
			for(int i = 0; i < pojList.size(); i++) {
				Object[] o = (Object[]) pojList.get(i);
				if (o[5] != null) {
					value = projectService.getDiscipline(o[5].toString());
				}
				o[5] = value;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 传入参数：entityid
	 * iframe里面内嵌此action可以获得专家树,可以post传值projectId及discipline
	 * 并初始化session中的参数
	 * @return
	 */
	public String showExpertTree() {
		if(entityId != null) {//entityId表示当前项目的id
			projectService.initExpertTree(entityId);
		} else {
			projectService.initExpertTree(null);
			session.put("expertTree_discipline1s", discipline);	//discipline表示一级学科代码
		}
		//获取所有的一级学科代码列表，用户页面学科代码checkbox的显示
		if(session.get("disList") == null) {
			disList = projectService.query("select s from SystemOption s where length(s.code) = 3 and s.standard like '%2009%' order by s.code asc ");
			session.put("disList", disList);
		} else {
			disList = (List<SystemOption>) session.get("disList");
		}
		return SUCCESS;
	}
	
	/**
	 * 导出项目一览表
	 * @return
	 * @throws Exception
	 */
	public String exportProject() {
		return SUCCESS;
	}
	
	/**
	 * 导出项目专家匹配表
	 * @return
	 * @throws Exception
	 */
	public String exportProjectReviewer() {
		return SUCCESS;
	}
	
	/**
	 * 导出专家调整结果
	 * @return
	 * @throws Exception
	 */
	public String exportMatchUpdate() {
		return SUCCESS;
	}
	
	/**
	 * 为一般项目匹配专家（自动匹配）
	 * (算法公共Action)
	 * @return
	 * @throws Exception 
	 */
	public String matchExpert() throws Exception {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		expertIds = new ArrayList<String>();
		for (String eid : selectedExpertIds.split("\\s*;\\s*")) {
			if (eid.length() > 0) {
				expertIds.add(eid);
			}
		}
		
		projectService.matchExpert(projectType(), currentYear, expertIds, null, null);
		return SUCCESS;
	}
	
	/**
	 * 项目批量"替换专家"
	 * 将一个专家的某些项目替给其他专家
	 * (算法公共Action)
	 * @return
	 * @throws Exception 
	 */
	public String batchReplaceExpert() throws Exception {
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		//选择的新专家
		Set<String> expertIds = new HashSet<String>();
		for (String eid : selectedExpertIds.split("\\s*;\\s*")) {
			if (eid.length() > 0) {
				expertIds.add(eid);
			}
		}
		
		String[] pIds = projectId.split("[^a-zA-Z0-9]+");//处理选中的项目ids
		List projectIds = Arrays.asList(pIds);
		
		//待剔除的专家
		List rejectExpertIds = new ArrayList();
		rejectExpertIds.add(selectExp);
		projectService.matchExpert(projectType(), currentYear, new ArrayList<String>(expertIds), projectIds, rejectExpertIds);
		//TODO 
//		projectService.updateWarningReviewer(projectIds, currentYear);//（合并前已经注释，是否保留需要进一步分析！！！注释调的原因？匹配警告更新已经在匹配最后实现。其他）
		//其他如果进行手动匹配，或者批量替换，或者移交之后都需要一次匹配更新（建议）
		System.out.println("批量替换结束！");
		return SUCCESS;
	}
	
	/**
	 * 项目指定由其他专家无条件接管
	 * (算法公共Action)
	 * @return
	 */
	public String transferMatch(){
		Map httpSession = ActionContext.getContext().getSession();
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");		//系统配置中默认显示的年份
		Integer displayedYear = (Integer) httpSession.get("defaultYear");	//首页上选择的显示年份
		if (!currentYear.equals(displayedYear)) {//如果两个年份不相同，禁止执行
			return ERROR;
		}
		
		//指定的专家
		Expert newExpert = (Expert) baseService.query(Expert.class, expertId);
		
		//处理选中的项目ids
		String[] pIds = projectId.split("[^a-zA-Z0-9]+");
		List projectIds = Arrays.asList(pIds);
		
		//该项目已存在的匹配结果
		Map paraMap = new HashMap();
		paraMap.put("entityId", entityId);	//projectIds有更新，需重新放入paraMap
		paraMap.put("projectIds", projectIds);	//projectIds有更新，需重新放入paraMap
		paraMap.put("year", currentYear);	//当前年份
		List<ProjectApplicationReview> prs = baseService.query("select pr from ProjectApplicationReview pr where pr.type = '" + projectType() +"' and pr.project.id in (:projectIds) and pr.reviewer.id = :entityId and pr.year = :year", paraMap);
		for (ProjectApplicationReview pr : prs) {
			//更新删除
			ProjectApplicationReviewUpdate pruDel = new ProjectApplicationReviewUpdate(pr, 0, 1);
			baseService.add(pruDel);
			//更新专家
			pr.setIsManual(1);
			pr.setMatchTime(new Date());
			pr.setReviewer(newExpert);
			baseService.modify(pr);
			//更新新增
			ProjectApplicationReviewUpdate pruAdd = new ProjectApplicationReviewUpdate(pr, 1, 1);
			baseService.add(pruAdd);
		}
		
		//更新警告信息
		projectService.projectUpdateWarningReviewer(projectType(), projectIds, currentYear);
		System.out.println("制定移交结束！");
		return SUCCESS;
	}
	
	/**
	 * 手工调整一般项目专家匹配
	 * 不经过多伦匹配过程，对所选专家专家与相聚简历关系
	 * 更新
	 * (算法公共Action)
	 * @return
	 */
	@Transactional
	public String reMatchExpert() {
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");	//系统配置中的默认显示年份
		List<ProjectApplication> projects = new ArrayList<ProjectApplication>();	//待手工调整的项目
		String[] projectIds = entityId.split("[;\\s]+");	//页面上选择的，待手工调整的项目的ID
		if(null == selectExp) 
			selectExp = "";
		String[] selectedExpertIds = selectExp.split("[;\\s]+");	//从专家树中选择的专家的ID
		Set<String> selectedReviewerIds = new HashSet(Arrays.asList(selectedExpertIds));//选择的专家的ID集合
		//TODO 选择的专家的数量不能超过5人
		if (selectedReviewerIds.size() > 5) {
			return "more";
		}
		for (String pid : projectIds) {
			ProjectApplication project = (ProjectApplication) baseService.query(ProjectApplication.class, pid);
			//只允许调整当前年的项目
			if (!project.getYear().equals(currentYear)) {
				continue;
			}
			projects.add(project);
			//该项目已存在的匹配结果
			List<ProjectApplicationReview> existingPrs = baseService.query("select pr from ProjectApplication p, ProjectApplicationReview pr where p.id = pr.project.id and p.id = ?", pid);
			Set<String> existingReviewerIds = new HashSet<String>();//该项目已存在的专家集合
			
			//在已有匹配结果中删除selectExp中没有的专家
			for (ProjectApplicationReview pr : existingPrs) {
				existingReviewerIds.add(pr.getReviewer().getId());
				if (!selectedReviewerIds.contains(pr.getReviewer().getId())) {
					ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 1);
					baseService.add(pru);
					baseService.delete(pr);
				}
			}
			//添加selectExpert中新加入的专家
			for (String selectedReviewerId : selectedReviewerIds) {
				if (!selectedReviewerId.trim().isEmpty() && !existingReviewerIds.contains(selectedReviewerId)) {
					ProjectApplicationReview pr = new ProjectApplicationReview(selectedReviewerId, pid, 1, project.getYear(), projectType());
					ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 1, 1);
					baseService.add(pr);
					baseService.add(pru);
				}
			}
		}
		projectService.projectUpdateWarningReviewer(projectType(), Arrays.asList(projectIds), currentYear);
		System.out.println("手动匹配结束！");
		return projectIds.length == 1 ? "one" : "more";
	}
	
	/**
	 * 专家查看页面删除(退评项目)
	 * (算法公共Action)
	 * @return
	 */
	public String deleteExpertProjects() {
		Map application = ActionContext.getContext().getApplication();
		Integer currentYear = (Integer) application.get("defaultYear");	//系统配置中的默认显示年份
		
		String[] projectIds = projectId.split("[^a-zA-Z0-9]+");//处理选中的项目ids
		int deletedProjectCnt = 0;//删除的项目数量
		for (String projectId : projectIds) {
			ProjectApplicationReview pr = (ProjectApplicationReview) projectService.queryUnique("select pr from ProjectApplicationReview pr where pr.project.id = ? and pr.reviewer.id = ? ", projectId, expertId);
			ProjectApplicationReviewUpdate pru = new ProjectApplicationReviewUpdate(pr, 0, 1);
			baseService.add(pru);
			projectService.delete(pr);
			deletedProjectCnt++;
		}
		//删除匹配对后，需要对所有项目的中匹配警告进行更新处理
		projectService.projectUpdateWarningReviewer(projectType(), Arrays.asList(projectIds), currentYear);
		jsonMap.put("expertId", expertId);		//当前专家id
		jsonMap.put("projectId", projectId);	//父页面的项目id
		jsonMap.put("deletedProjectCnt", deletedProjectCnt);	//从当前专家身上退评的项目数量
		return SUCCESS;
	}
	
	public String popAudit() {
		ProjectApplication application=(ProjectApplication) baseService.query(ProjectApplication.class,this.entityId);
		this.approveFee = this.projectService.getDefaultFee(application);
		this.number = getDefaultProjectNumber(entityId);
		reviewAuditDate = new Date();
		return SUCCESS;
	}
	
	/**
	 * 加载校验
	 */
	public void validateAudit() {
		String info = "";
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info+=GlobalInfo.ERROR_EXCEPTION_INFO;
		} else {
			ProjectApplication application = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
			if(application.getIsReviewable() != 1) {
				this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
				info+=GlobalInfo.ERROR_EXCEPTION_INFO;
			}
		}
		if(reviewAuditResult != 1 && reviewAuditResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_RESULT_NULL);
			info+=ProjectInfo.ERROR_PROJECT_RESULT_NULL;
		}
		if(reviewAuditDate == null){
			this.addFieldError("reviewAuditDate", ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL);
			info+=ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL;
		}
		if(reviewAuditOpinion != null && reviewAuditOpinion.length() > 2000){
			this.addFieldError("reviewAuditOpinion", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT);
			info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT;
		}
		if(reviewAuditOpinionFeedback != null && reviewAuditOpinionFeedback.length() > 200){
			this.addFieldError("reviewAuditOpinionFeedback", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT);
			info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 项目评审审核
	 * @return
	 */
	@Transactional
	public String audit() {
		ProjectApplication application = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if(reviewAuditOpinion != null){
			application.setFinalAuditOpinion(("A"+reviewAuditOpinion).trim().substring(1));
		} else {
			application.setFinalAuditOpinion(null);
		}
		if(reviewAuditOpinionFeedback != null){
			application.setFinalAuditOpinionFeedback(("A" + reviewAuditOpinionFeedback).trim().substring(1));
		}else{
			application.setFinalAuditOpinionFeedback(null);
		}
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		application.setFinalAuditResult(reviewAuditResult);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		application.setFinalAuditorName(visitor.getUser().getUsername());
		if(reviewAuditResult == 2){//同意立项
			application.setIsGranted(1);//确定立项
			application.setGrantedDate(new Date());
			ProjectGranted granted = new ProjectGranted(application.getType());
			projectService.setGrantedInfoFromApp(application, granted);
			granted.setNumber(number);
			granted.setApproveFee(approveFee);
			granted.setApproveDate(approveDate);
			granted.setApplication(application);
			baseService.add(granted);
		}
		return SUCCESS;
	}
	
	/**
	 * 查看评审审核详情
	 * @return
	 */
	public String auditView() {
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		reviewAuditResult = application.getFinalAuditResult();
		reviewAuditOpinion = application.getFinalAuditOpinion();
		reviewAuditDate = application.getFinalAuditDate();
		reviewAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
		return SUCCESS;
	}
	
	/**
	 * 准备修改评审审核
	 */
	public String toAuditModify() {
		ProjectApplication application=(ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		reviewAuditResult = application.getFinalAuditResult();
		reviewAuditOpinion = application.getFinalAuditOpinion();
		reviewAuditDate = application.getFinalAuditDate();
		reviewAuditOpinionFeedback = application.getFinalAuditOpinionFeedback();
		return SUCCESS;
	}
	/**
	 * 加载校验
	 */
	public void validateAuditModify() {
		String info = "";
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			info+=GlobalInfo.ERROR_EXCEPTION_INFO;
		}
		if(reviewAuditResult != 1 && reviewAuditResult != 2){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_PROJECT_RESULT_NULL);
			info+=ProjectInfo.ERROR_PROJECT_RESULT_NULL;
		}
		if(reviewAuditDate == null){
			this.addFieldError("reviewAuditDate", ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL);
			info+=ProjectInfo.ERROR_PROJECT_APPROVE_DATE_NULL;
		}
		if(reviewAuditOpinion != null && reviewAuditOpinion.length() > 2000){
			this.addFieldError("reviewAuditOpinion", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT);
			info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_OUT;
		}
		if(reviewAuditOpinionFeedback != null && reviewAuditOpinionFeedback.length() > 200){
			this.addFieldError("reviewAuditOpinionFeedback", ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT);
			info+=ProjectInfo.ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT;
		}
		if (info.length() > 0) {
			jsonMap.put(GlobalInfo.ERROR_INFO, info);
		}
	}
	
	/**
	 * 项目评审审核修改
	 * @return
	 */
	public String auditModify() {
		ProjectApplication application = (ProjectApplication) baseService.query(ProjectApplication.class, entityId);
		if(reviewAuditOpinion != null){
			application.setFinalAuditOpinion(("A"+reviewAuditOpinion).trim().substring(1));
		} else {
			application.setFinalAuditOpinion(null);
		}
		if(reviewAuditOpinionFeedback != null){
			application.setFinalAuditOpinionFeedback(("A" + reviewAuditOpinionFeedback).trim().substring(1));
		}else{
			application.setFinalAuditOpinionFeedback(null);
		}
		Date approveDate = this.projectService.setDateHhmmss(reviewAuditDate);
		application.setFinalAuditDate(approveDate);
		application.setFinalAuditResult(reviewAuditResult);
		Visitor visitor = (Visitor) ActionContext.getContext().getSession().get("visitor");
		application.setFinalAuditorName(visitor.getUser().getUsername());
		dao.modify(application);
		return SUCCESS;
	}
	
	
	public ProjectApplication getProject() {
		return project;
	}

	public void setProject(ProjectApplication project) {
		this.project = project;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getSelectExp() {
		return selectExp;
	}

	public void setSelectExp(String selectExp) {
		this.selectExp = selectExp;
	}

	public List<String> getExpertIds() {
		return expertIds;
	}

	public void setExpertIds(List<String> expertIds) {
		this.expertIds = expertIds;
	}

	public String getSelectedExpertIds() {
		return selectedExpertIds;
	}

	public void setSelectedExpertIds(String selectedExpertIds) {
		this.selectedExpertIds = selectedExpertIds;
	}

	public List getNodesInfo() {
		return nodesInfo;
	}

	public void setNodesInfo(List nodesInfo) {
		this.nodesInfo = nodesInfo;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public List<SystemOption> getDisList() {
		return disList;
	}

	public void setDisList(List<SystemOption> disList) {
		this.disList = disList;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	
	public int getIsFromExpert() {
		return isFromExpert;
	}

	public void setIsFromExpert(int isFromExpert) {
		this.isFromExpert = isFromExpert;
	}

	public List getPojList() {
		return pojList;
	}

	public void setPojList(List pojList) {
		this.pojList = pojList;
	}

	public int getIsReviewable() {
		return isReviewable;
	}

	public void setIsReviewable(int isReviewable) {
		this.isReviewable = isReviewable;
	}

	public int getExportAll() {
		return exportAll;
	}

	public void setExportAll(int exportAll) {
		this.exportAll = exportAll;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getDeleteManualMatches() {
		return deleteManualMatches;
	}

	public void setDeleteManualMatches(int deleteManualMatches) {
		this.deleteManualMatches = deleteManualMatches;
	}

	public int getDeleteAutoMatches() {
		return deleteAutoMatches;
	}

	public void setDeleteAutoMatches(int deleteAutoMatches) {
		this.deleteAutoMatches = deleteAutoMatches;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getNotReviewReason() {
		return notReviewReason;
	}

	public void setNotReviewReason(String notReviewReason) {
		this.notReviewReason = notReviewReason;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public File getXls() {
		return xls;
	}
	public void setXls(File xls) {
		this.xls = xls;
	}	
	public IExpertService getExpertService() {
		return expertService;
	}
	public void setExpertService(IExpertService expertService) {
		this.expertService = expertService;
	}

	public int getReviewAuditResult() {
		return reviewAuditResult;
	}

	public void setReviewAuditResult(int reviewAuditResult) {
		this.reviewAuditResult = reviewAuditResult;
	}

	public Date getReviewAuditDate() {
		return reviewAuditDate;
	}

	public void setReviewAuditDate(Date reviewAuditDate) {
		this.reviewAuditDate = reviewAuditDate;
	}

	public String getReviewAuditOpinion() {
		return reviewAuditOpinion;
	}

	public void setReviewAuditOpinion(String reviewAuditOpinion) {
		this.reviewAuditOpinion = reviewAuditOpinion;
	}

	public String getReviewAuditOpinionFeedback() {
		return reviewAuditOpinionFeedback;
	}

	public void setReviewAuditOpinionFeedback(String reviewAuditOpinionFeedback) {
		this.reviewAuditOpinionFeedback = reviewAuditOpinionFeedback;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getApproveFee() {
		return approveFee;
	}

	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}	
	
	
}
