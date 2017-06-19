package csdc.action.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.ProjectApplication;
import csdc.service.IKeyService;
import csdc.service.IProductService;
import csdc.service.IProjectService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 我的项目管理
 *
 */
public class ProjectAction extends BaseAction {

	private static final long serialVersionUID = 8829071233244273703L;
	private static final String[] HQL = {
		"select app.id, app.name, uni.id, app.agencyName, '一般项目', so.name, app.year, " +
		"mem.memberSn, '0', app.applicantId, app.applicantName, 'general' from GeneralApplication app, GeneralMember mem "+
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1 " +
		"order by app.name asc, app.id asc",//一般项目未立项查询
		
		"select app.id, app.name, uni.id, app.agencyName, app.type, so.name, app.year, " +
		"mem.memberSn, '0', app.applicantId, app.applicantName, 'general' from ProjectApplication app, ProjectMember mem "+
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.applicantSubmitStatus = 2 " +
		"order by app.name asc, app.id asc",//申报暂存
		
		"select app.id, gra.name, uni.id, uni.name, '一般项目', so.name, app.year, " +
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName, 'general' from GeneralGranted gra, GeneralMember mem left outer join "+
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " +
		"order by gra.status asc, gra.id asc",//一般项目已立项查询
		
		"select app.id, app.name, uni.id, app.agencyName, '基地项目', so.name, app.year, " + 
		"mem.memberSn, '0', app.applicantId, app.applicantName, 'instp' from InstpApplication app, InstpMember mem " +
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//基地未立项项目查询
		
		"select app.id, gra.name, uni.id, uni.name, '基地项目', so.name, app.year, " + 
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName, 'instp' from InstpGranted gra, InstpMember mem left outer join " +
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " +
		"order by gra.status asc, gra.id asc",//基地已立项项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '后期资助项目', so.name, app.year, " +
	    "mem.memberSn, '0', app.applicantId, app.applicantName, 'post' from PostApplication app, PostMember mem " +
	    "left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//后期资助未立项项目查询

		"select app.id, gra.name, uni.id, uni.name, '后期资助项目', so.name, app.year, " +
	    "mem.memberSn, gra.status, gra.applicantId, gra.applicantName, 'post' from PostGranted gra, PostMember mem left outer join " +
	    "gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc",//后期资助已立项项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '重大攻关项目', so.name, app.year, " +
	    "mem.memberSn, '0', app.applicantId, app.applicantName, 'key' from KeyApplication app, KeyMember mem " +
	    "left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.researchType so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//重大攻关未中标项目查询

		"select app.id, gra.name, uni.id, uni.name, '重大攻关项目', so.name, app.year, " +
	    "mem.memberSn, gra.status, gra.applicantId, gra.applicantName, 'key' from KeyGranted gra, KeyMember mem left outer join " +
	    "gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join app.researchType so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc",//重大攻关已中标项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '委托应急课题', so.name, app.year, " +
		"mem.memberSn, '0', app.applicantId, app.applicantName, 'entrust' from EntrustApplication app, EntrustMember mem " +
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//委托应急课题未立项项目查询
		
		"select app.id, gra.name, uni.id, uni.name, '委托应急课题', so.name, app.year, " +
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName, 'entrust' from EntrustGranted gra, EntrustMember mem left outer join " +
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join app.subtype so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc"//委托应急课题未立项项目查询
	};
	
	private static final String PAGE_NAME = "directProjectPage";//列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd";//列表页面时间格式
	private IProjectService projectService;//项目公共管理接口
	private IKeyService keyService;//重大攻关项目管理接口
	protected IProductService productService;//成果管理接口
	protected String projectid;//项目id
	protected Integer listType;//项目列表类型(1.申报列表；2.立项列表；3.中检列表；4.结项列表；5.变更列表；6项目管理系统首页进入；7我的项目列表；8评审项目结项列表；9用户信息中心首页进入；10评审项目申报列表；11年检列表);
	private String projectType;//项目类型
	private String selectedTab;//查看项目默认显示标签
	private Integer appStatus;//申报业务设置状态
	private Integer annStatus;//年检业务设置状态
	private Integer midStatus;//中检业务设置状态
	private Integer endStatus;//结项业务设置状态
	private Integer varStatus;//变更业务设置状态
	private Integer genappStatus;
	private Integer keyappStatus;
	private Integer insappStatus;
	private Integer posappStatus;
	private Integer entappStatus;
	private String type;
	public String pageName(){
		return ProjectAction.PAGE_NAME;
	}
	public String[] column(){
		return null;
	}
	public String dateFormat() {
		return ProjectAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return null;
	}
	public Object[] simpleSearchCondition(){
		return null;
	}
	public Object[] advSearchCondition(){
		return null;
	}
	
	
	/**
	 * 申报项目
	 */
	public String toAdd(){
		genappStatus = this.projectService.getBusinessStatus("011");
		insappStatus = this.projectService.getBusinessStatus("021");
		posappStatus = this.projectService.getBusinessStatus("031");
		keyappStatus = this.projectService.getBusinessStatus("041");
		entappStatus = this.projectService.getBusinessStatus("051");
		return SUCCESS;
		
	}
	
	/**
	 * 进入我的项目列表页面
	 */
	public String toSearchMyProject(){
		request.setAttribute("url", "searchMyProject");
		return SUCCESS;
	}
	
	/**
	 * 我的项目列表查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String searchMyProject(){
		Map map = new HashMap();
		map.put("belongId", baseService.getBelongIdByLoginer(loginer));
		this.pageList = new ArrayList();
		for(int i = 0; i < HQL.length; i++){
			StringBuffer hql = new StringBuffer(HQL[i]);
			List pageListTmp = dao.query(hql.toString(), map);
			pageList.addAll(pageListTmp);
		}
		this.pageListDealWith();
		jsonMap.put("laData", laData);
		return SUCCESS;
	}
	
	/**
	 * 进入我的项目查看页面预处理
	 */
	public String toViewMyProject(){
		//业务设置状态1：业务激活2：业务停止
		String projectTypeName = ERROR;
		this.projectid = this.projectService.getGrantedIdByAppId(entityId);
		ProjectApplication application = (ProjectApplication)this.dao.query(ProjectApplication.class, entityId);
		if(projectid != null && projectid.trim().length() > 0){
			selectedTab = "granted";
		}else{
			selectedTab = "application";
		}
		projectTypeName = application.getType();
		//业务设置状态1：业务激活2：业务停止
		if(!ERROR.equals(projectTypeName)){
			setAppStatus(this.projectService.getBusinessStatus(application.getType() + "1"));
			setAnnStatus(this.projectService.getBusinessStatus(application.getType() + "5"));
			midStatus = this.projectService.getBusinessStatus(application.getType() + "2", entityId);
			endStatus = this.projectService.getBusinessStatus(application.getType() + "3", entityId);
			varStatus = this.projectService.getBusinessStatus(application.getType() + "4", entityId);
		}
		return projectTypeName;
	}
	
	/**
	 * 我的项目查看
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewMyProject(){
		if(this.projectType.equals("general")){//一般项目
			Map session = ActionContext.getContext().getSession();
			csdc.action.project.general.ApplicationApplyAction applicationAction = new csdc.action.project.general.ApplicationApplyAction();
			applicationAction.setLoginer(loginer);
			applicationAction.setProjectid(this.projectid);
			applicationAction.setEntityId(this.entityId);
			applicationAction.setPageNumber(this.pageNumber);
			applicationAction.setProjectType(this.projectType);
			applicationAction.setListType(this.listType);
			applicationAction.setProjectService(projectService);
			applicationAction.setProductService(productService);
			applicationAction.setKeyService(keyService);
			applicationAction.setDao(dao);
			applicationAction.setBaseService(baseService);
			String result = applicationAction.view();
			this.jsonMap = (Map) session.get("projectViewJson");
			return result;
		}else if(this.projectType.equals("instp")){//基地项目
			Map session = ActionContext.getContext().getSession();
			csdc.action.project.instp.ApplicationApplyAction applicationAction = new csdc.action.project.instp.ApplicationApplyAction();
			applicationAction.setLoginer(loginer);
			applicationAction.setProjectid(this.projectid);
			applicationAction.setEntityId(this.entityId);
			applicationAction.setPageNumber(this.pageNumber);
			applicationAction.setProjectType(this.projectType);
			applicationAction.setListType(this.listType);
			applicationAction.setProjectService(projectService);
			applicationAction.setProductService(productService);
			applicationAction.setKeyService(keyService);
			applicationAction.setBaseService(baseService);
			applicationAction.setDao(dao);
			String result = applicationAction.view();
			this.jsonMap = (Map) session.get("projectViewJson");
			return result;
		}else if(this.projectType.equals("post")){//后期资助项目
			Map session = ActionContext.getContext().getSession();
			csdc.action.project.post.ApplicationApplyAction applicationAction = new csdc.action.project.post.ApplicationApplyAction();
			applicationAction.setLoginer(loginer);
			applicationAction.setProjectid(this.projectid);
			applicationAction.setEntityId(this.entityId);
			applicationAction.setPageNumber(this.pageNumber);
			applicationAction.setProjectType(this.projectType);
			applicationAction.setListType(this.listType);
			applicationAction.setProjectService(projectService);
			applicationAction.setProductService(productService);
			applicationAction.setKeyService(keyService);
			applicationAction.setBaseService(baseService);
			applicationAction.setDao(dao);
			String result = applicationAction.view();
			this.jsonMap = (Map) session.get("projectViewJson");
			return result;
		}else if(this.projectType.equals("key")){//重大攻关项目
			Map session = ActionContext.getContext().getSession();
			csdc.action.project.key.ApplicationApplyAction applicationAction = new csdc.action.project.key.ApplicationApplyAction();
			applicationAction.setLoginer(loginer);
			applicationAction.setProjectid(this.projectid);
			applicationAction.setEntityId(this.entityId);
			applicationAction.setPageNumber(this.pageNumber);
			applicationAction.setProjectType(this.projectType);
			applicationAction.setListType(this.listType);
			applicationAction.setProjectService(projectService);
			applicationAction.setProductService(productService);
			applicationAction.setKeyService(keyService);
			applicationAction.setBaseService(baseService);
			applicationAction.setDao(dao);
			String result = applicationAction.view();
			this.jsonMap = (Map) session.get("projectViewJson");
			return result;
		}else if(this.projectType.equals("entrust")){//委托应急课题
			Map session = ActionContext.getContext().getSession();
			csdc.action.project.entrust.ApplicationApplyAction applicationAction = new csdc.action.project.entrust.ApplicationApplyAction();
			applicationAction.setLoginer(loginer);
			applicationAction.setProjectid(this.projectid);
			applicationAction.setEntityId(this.entityId);
			applicationAction.setPageNumber(this.pageNumber);
			applicationAction.setProjectType(this.projectType);
			applicationAction.setListType(this.listType);
			applicationAction.setProjectService(projectService);
			applicationAction.setProductService(productService);
			applicationAction.setKeyService(keyService);
			applicationAction.setBaseService(baseService);
			applicationAction.setDao(dao);
			String result = applicationAction.view();
			this.jsonMap = (Map) session.get("projectViewJson");
			return result;
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
	}
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	public IKeyService getKeyService() {
		return keyService;
	}
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
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
	public String getSelectedTab() {
		return selectedTab;
	}
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getGenappStatus() {
		return genappStatus;
	}
	public void setGenappStatus(Integer genappStatus) {
		this.genappStatus = genappStatus;
	}
	public Integer getKeyappStatus() {
		return keyappStatus;
	}
	public void setKeyappStatus(Integer keyappStatus) {
		this.keyappStatus = keyappStatus;
	}
	public Integer getInsappStatus() {
		return insappStatus;
	}
	public void setInsappStatus(Integer insappStatus) {
		this.insappStatus = insappStatus;
	}
	public Integer getPosappStatus() {
		return posappStatus;
	}
	public void setPosappStatus(Integer posappStatus) {
		this.posappStatus = posappStatus;
	}
	public Integer getEntappStatus() {
		return entappStatus;
	}
	public void setEntappStatus(Integer entappStatus) {
		this.entappStatus = entappStatus;
	}
	
}
