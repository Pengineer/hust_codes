package csdc.action.fundList;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.FundList;
import csdc.bean.ProjectFunding;
import csdc.service.IFundListService;
import csdc.service.IProjectFundService;
import csdc.service.IProjectService;
import csdc.service.imp.MailService;
import csdc.tool.HqlTool;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;




public abstract class FundListBaseAction extends BaseAction {

	/**拨款清单管理的基类
	 * 定义了子类需要实现的抽象方法并实现所有类别项目的公用方法
	 * @author 肖宁
	 */
	private static final long serialVersionUID = 1839992081200023745L;
	protected IProjectService projectService;//项目管理接口
	protected IFundListService fundListService;//清单管理接口
	protected IProjectFundService projectFundService;//拨款管理接口
	private static String HQL1 = "select fl.id, fl.name, fl.attn, fl.createDate, fl.year, fl.status, fl.note ";
	public static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "fl.id";//缓存id
	private int expFlag,searchForUnitFundListFlag;//0：普通列表导出；1:高级检索导出；高校查看页面检索标志位
	private MailController mailController;
	private MailService mailService;
	protected String mainFlag,keyword4unit;//首页进入列表参数
	protected FundList fundList;//清单
	protected ProjectFunding projectFunding;//项目拨款对象
	protected String listName,projectSubtype,note,fundId,researchType;//清单名称，项目子类，清单备注，项目拨款ID，项目研究类型
	protected int projectYear,searchType4unit;//项目年度，按学校查看页面初级检索的类型
	protected double rate,fee;//拨款比率，拨款金额
	public abstract String fundType();//拨款类型
	public abstract String projectType();//项目类别
	public abstract String listHql2();
	public abstract String pageName(); 
	private static final String[] COLUMN = new String[] {
		"fl.name",
		"fl.attn",
		"fl.createDate",
		"fl.year",
		"fl.status",
		"fl.note",
	};
	
	
	public String dateFormat() {
		return FundListBaseAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return FundListBaseAction.PAGE_BUFFER_ID;
	}
	public String[] column() {
		return FundListBaseAction.COLUMN;
	}
	public IProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	public IFundListService getFundListService() {
		return fundListService;
	}
	public void setFundListService(IFundListService fundListService) {
		this.fundListService = fundListService;
	}
	public IProjectFundService getProjectFundService() {
		return projectFundService;
	}
	public void setProjectFundService(IProjectFundService projectFundService) {
		this.projectFundService = projectFundService;
	}
	public static void setHQL1(String hQL1) {
		HQL1 = hQL1;
	}
	public static String getHQL1() {
		return HQL1;
	}
	
	public void setExpFlag(int expFlag) {
		this.expFlag = expFlag;
	}
	public int getExpFlag() {
		return expFlag;
	}
	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public FundList getFundList() {
		return fundList;
	}
	public void setFundList(FundList fundList) {
		this.fundList = fundList;
	}
	public ProjectFunding getProjectFunding() {
		return projectFunding;
	}
	public void setProjectFunding(ProjectFunding projectFunding) {
		this.projectFunding = projectFunding;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getProjectSubtype() {
		return projectSubtype;
	}
	public void setProjectSubtype(String projectSubtype) {
		this.projectSubtype = projectSubtype;
	}
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getProjectYear() {
		return projectYear;
	}
	public void setProjectYear(int projectYear) {
		this.projectYear = projectYear;
	}
	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getKeyword4unit() {
		return keyword4unit;
	}
	public void setKeyword4unit(String keyword4unit) {
		this.keyword4unit = keyword4unit;
	}
	public int getSearchType4unit() {
		return searchType4unit;
	}
	public void setSearchType4unit(int searchType4unit) {
		this.searchType4unit = searchType4unit;
	}
	public void setSearchForUnitFundListFlag(int searchForUnitFundListFlag) {
		this.searchForUnitFundListFlag = searchForUnitFundListFlag;
	}
	public int getSearchForUnitFundListFlag() {
		return searchForUnitFundListFlag;
	}
	public MailController getMailController() {
		return mailController;
	}
	public void setMailController(MailController mailController) {
		this.mailController = mailController;
	}
	public MailService getMailService() {
		return mailService;
	}
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	/**
	 * 初级检索
	 */
	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
		hql.append(listHql2());
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			map.put("keyword", "%" + keyword + "%");
			hql.append(this.projectService.getFundListSimpleSearchHQL(searchType));
		}
		Map session = ActionContext.getContext().getSession();
		session.put("fundListMap", map);
//		//处理查询范围
//		String addHql = this.projectService.grantedInSearch(account);
//		hql.append(addHql);
		map = (Map) session.get("fundListMap");
		expFlag =1;
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(54);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			3,
			null
		};
	}
	
	/**
	 * 高级检索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL1);
			hql.append(listHql2());
		Map session = ActionContext.getContext().getSession();
		session.put("fundListMap", map);
		Account account = loginer.getAccount();
		//处理查询范围
		String addHql = this.projectService.grantedInSearch(account);
		hql.append(addHql);
		map = (Map) session.get("fundListMap");
		HqlTool hqlToolWhere = new HqlTool(hql.toString());
		String whereHql = hqlToolWhere.getWhereClause().substring(81);
		session.put("whereHql", whereHql);
		return new Object[]{
			hql.toString(),
			map,
			3,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖宁
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		session.put("searchQuery", searchQuery);
	}
	
	/**
	 * 进入添加清单
	 */
	public String toAdd(){
		return SUCCESS;
	}
	
	/**
	 * 添加清单
	 */
	public String add() throws UnsupportedEncodingException{
		if (!request.getParameter("listName").isEmpty() && !request.getParameter("listName").equals(null)) {//清单名称
			listName = new String(request.getParameter("listName").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("rate").isEmpty() && !request.getParameter("rate").equals(null)) {//拨款比率
			rate = Double.parseDouble(request.getParameter("rate").trim());
		}
		if (!request.getParameter("projectYear").isEmpty() && !request.getParameter("projectYear").equals(null)) {//项目年度
			projectYear = Integer.parseInt(request.getParameter("projectYear").trim());
		}
		if (!request.getParameter("projectSubtype").isEmpty() && !request.getParameter("projectSubtype").equals(null)) {//项目子类
			projectSubtype = new String(request.getParameter("projectSubtype").getBytes("ISO-8859-1"),"UTF-8");
		}
		if (!request.getParameter("note").isEmpty() && !request.getParameter("note").equals(null)) {//项目备注
			note = new String(request.getParameter("note").getBytes("ISO-8859-1"),"UTF-8");
		}
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		int isPrincipal = loginer.getIsPrincipal();
		String attn = "";//经办人
		if (isPrincipal == 0) {
			attn = loginer.getCurrentPersonName();
		} else if (isPrincipal == 1) {
			attn = loginer.getCurrentBelongUnitName();
		}
		entityId = fundListService.add(listName, note, attn, projectType(), fundType(), rate, projectYear);
		fundList = dao.query(FundList.class, entityId);
		List pfList = projectFundService.getProjectFundsByYearAndSubType(projectYear,projectSubtype,projectType(),fundType());
		fundList = projectFundService.modifyByFundList(fundList, pfList, 0);
		return SUCCESS;
	}
	
	/**
	 * 进入修改清单
	 */
	public String toModify(){
		fundList = dao.query(FundList.class, entityId);
		return SUCCESS;
	}
	
	/**
	 * 进入清单查看
	 */
	public String toView(){//默认为按项目查看列表显示
		listForFundList = 1;
		searchForUnitFundListFlag = 0;
		Map session = ActionContext.getContext().getSession();
		session.put("searchForUnitFundListFlag", searchForUnitFundListFlag);
//		fundList = dao.query(FundList.class, entityId);
		return SUCCESS;
	}
	
	/**
	 * 查看清单详情
	 */
	public String view(){
		fundList = fundListService.getFundList(entityId);//清单实体
		List unitFundList = fundListService.getUnitFundList(entityId);//学校查看的列表数据
		jsonMap.put("fundList", fundList);
		jsonMap.put("unitFundList", unitFundList);
		return SUCCESS;
	}
	
	/**
	 * 按学校查看页面的检索
	 */
	public String searchForUnitFundList(){
		searchForUnitFundListFlag = 1;
		Map session = ActionContext.getContext().getSession();
		session.put("searchType4unit", searchType4unit);//检索类型
		session.put("keyword4unit", keyword4unit);//检索关键字
		session.put("searchForUnitFundListFlag", searchForUnitFundListFlag);//检索标志位
		List unitFundList = fundListService.getUnitFundList(entityId);//学校查看的列表数据
		jsonMap.put("unitFundList", unitFundList);
		return SUCCESS;
	}
	
	/**
	 * 删除清单
	 */
	public String delete(){
		for(int i=0;i<entityIds.size();i++){
			fundListService.deleteFundList(entityIds.get(i));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除未拨款清单中的拨款记录，即对清单进行编辑
	 */
	public String fundListDelete(){//删除未拨款清单中的拨款记录
		fundList = fundListService.getFundList(entityId);
		projectFundService.fundListDelete(fundList,entityIds);
		return SUCCESS;
	}
	
	/**
	 * 对清单进行拨款
	 */
	public String toAudit() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
//		System.out.println(loginer.getCurrentPersonName());
		String attn;//经办人
		if (loginer.getCurrentPersonName()!=null) {
			attn = loginer.getCurrentPersonName();
		}else {
			attn = loginer.getCurrentBelongUnitName();
		}
		fundList = fundListService.getFundList(entityId);//清单实体
		fundList = projectFundService.modifyForAudit(fundList,attn);//更新清单实体数据
		fundListService.addEmailToUnit(entityId,loginer);////按学校添加拨款通知邮件
		return SUCCESS;
	}
	
	/**
	 * 修改清单
	 */
	public String modify() throws UnsupportedEncodingException{
		if (!request.getParameter("listName").isEmpty() && !request.getParameter("listName").equals(null)) {
			listName = request.getParameter("listName");//清单名称
		}
		if (!request.getParameter("rate").isEmpty() && !request.getParameter("rate").equals(null)) {
			rate = Double.parseDouble(request.getParameter("rate").trim());//拨款比率
		}
		if (!request.getParameter("note").isEmpty() && !request.getParameter("note").equals(null)) {
			note = request.getParameter("note");//清单备注
		}
		if (!request.getParameter("fundListId").isEmpty() && !request.getParameter("fundListId").equals(null)) {
			entityId = request.getParameter("fundListId");
		}
		fundList = fundListService.modify(listName, note, rate, entityId);
		List pfList = projectFundService.getProjectFundsByFundList(fundList);//项目拨款对象集合
		fundList = projectFundService.modifyByFundList(fundList, pfList, 1);
		return SUCCESS;
	}
	
	/**
	 * 在清单查看页面进入修改某一条拨款金额
	 */
	public String toModifyFee() {
		projectFunding = dao.query(ProjectFunding.class, entityId);
		return SUCCESS;
	}
	
	/**
	 * 在清单查看页面修改某一条拨款金额
	 */
	public String  modifyFee() {
		if (!request.getParameter("fee").isEmpty() && !request.getParameter("fee").equals(null)) {
			fee = Double.parseDouble(request.getParameter("fee").trim());
		}
		if (!request.getParameter("fundId").isEmpty() && !request.getParameter("fundId").equals(null)) {
			fundId = request.getParameter("fundId");
		}
		fundList = projectFundService.modifyFee(fee,fundId,fundType());
		entityId = fundList.getId();
		return SUCCESS;
	}
}