package csdc.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.service.IBaseService;
import csdc.tool.HqlTool;
import csdc.tool.StringTool;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;

/**
 * 功能描述：
 *     初级检索、高级检索、排序、到指定页、改变页面大小、
 *     上条、下条、格式化列表时间、将列表数据放入jsonMap。
 * 变量描述：
 *     主要定义了列表相关的变量，如：列表数据、总页数、排序列
 *     等，以及当前登录账号级别、类别、所属等。
 * @author 龚凡	2011.04.07	移植cmis中上述功能的实现
 * @author 雷达	更新上下条的功能
 * @author 徐涵 	更新列表、上下条的功能
 */
@SuppressWarnings("unchecked")
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;
	
	protected IBaseService baseService;

	protected Map jsonMap = new HashMap();// json对象容器
	protected List<Object[]> pageList;// 初始查询列表数据
	protected List laData;// 处理后的列表数据
	protected Long totalRows;// 总记录数
	protected int pageSize;// 页面大小
	protected int startPageNumber;// 当前页所在区段的起始页
	protected Integer pageNumber;// 页码
	protected int pageBackNumber;// 每次查询的页数
	protected int sortColumn;// 排序列标号
	protected int sortColumnLabel;// 降序还是升序0降序，1升序
	protected int searchType;// 初级检索类别
	protected Integer update;// 返回列表时是否需要更新缓存(0或null:不需要    其他:需要)
	protected Integer listType;//1表示一般项目，2表示基地项目
	protected Integer businessType;//1表示申报项目，2表示立项项目，3表示中检项目，4表示变更项目，5表示结项项目
	protected String keyword;// 初级检索关键字
	protected String entityId;// 单个实体ID
	protected List<String> entityIds;// 多个实体ID
	protected String pageInfo;// 页面载入后，用于弹出的提示信息
	protected HttpServletRequest request;// 请求的request对象
	protected Map session;//session对象
	
	/**
	 * 专家若参评，至少需正式参评的项目数
	 */
	protected Integer expertProjectMin;
	
	/**
	 * 每位专家评审的最大项目数
	 */
	protected Integer expertProjectMax;
	
	/**
	 * 每个项目所需部属高校评审专家数
	 */
	protected Integer projectMinistryExpertNumber;
	
	/**
	 * 每个项目所需地方高校评审专家数
	 */
	protected Integer projectLocalExpertNumber;

	/**
	 * 每个项目所需总共专家数
	 */
	protected Integer projectExpertNumber;
	
	/**
	 * 为了复用初级检索、高级检索、到指定页、排序、改变页面大小、
	 * 上条、下条等功能，需要获取子类page名称、排序数组、时间格式、
	 * 初级检索action方法、上下条缓存ID名称等信息，定义以下几个抽象方法。
	 */
	public abstract String pageName();
	public abstract String[] column();
	public abstract String dateFormat();
	public abstract Object[] simpleSearchCondition();
	public abstract Object[] advSearchCondition();
	
	/**
	 * 唯一确定一条记录的entityId名，可以用于view、delete<br />
	 * 必须是hql查询语句中select子句中的一个成员
	 * @return
	 */
	public abstract String pageBufferId();
	
	/**
	 * 将列表功能的公共成员变量放入jsonMap对象中，包括：列表数据、
	 * 总记录数、页面大小、起始页码、当前页码、每次查询页数、
	 * 排序列标号、排序规则。
	 */
	public void jsonListPut() {
		// 从application对象中读取每次查询页数参数
//		Map application = ActionContext.getContext().getApplication();
//		pageBackNumber = (Integer) application.get("pages");
		pageBackNumber = 20;
		
		// 将列表相关的公共变量存入jsonMap对象
		jsonMap.put("laData", laData);
		jsonMap.put("totalRows", totalRows);
		jsonMap.put("pageSize", pageSize);
		jsonMap.put("startPageNumber", startPageNumber);
		jsonMap.put("pageNumber", pageNumber);
		jsonMap.put("pageBackNumber", pageBackNumber);
		jsonMap.put("sortColumn", sortColumn);
		jsonMap.put("sortColumnLabel", sortColumnLabel);

		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		if (pager != null && pager.getErrorInfo() != null) {
			jsonMap.put(GlobalInfo.ERROR_INFO, pager.getErrorInfo());
		}
	}

	/**
	 * 处理列表数据，如有需要，各模块可以重写此方法，本方法默认仅格式化时间。
	 */
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		Object[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new Object[o.length];
			for (int i = 0; i < o.length; i++) {
				if (o[i] == null) {// 如果字段值为空，则以""替换
					item[i] = "";
				} else {// 如果字段值非空，则做进一步判断
					if (o[i] instanceof Date) {// 如果字段为时间对象，则按照子类定义的时间格式格式化
						datestr = dateformat.format((Date) o[i]);
						item[i] = datestr;
					} else {// 如果字段非时间对象，则直接转化为字符串
						item[i] = o[i].toString();
					}
				}
			}
			laData.add(item);// 将处理好的数据存入laData
		}
	}

	/**
	 * 用于跳转到列表页面，并初始化检索类型、检索关键字、起始页码、
	 * 页面大小、检索URL等参数。
	 * @author xuhan
	 */
	public String toList() {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName());
		if (pager == null || update != null && update != 0) { 
			initPager(simpleSearchCondition());
			pager = (Pager) session.get(pageName());
			pager.setNeedUpdate(true);
		}
		if (entityId != null) {
			pager.setTargetEntityId(entityId.replaceAll("\\W.*", ""));
		} else if (pageNumber != null) {
			pager.setTargetPageNumber(pageNumber);
		}
		searchType = pager.getSearchType();
		keyword = pager.getKeyword();
		return SUCCESS;
	}
	
	public String simpleSearch() {
		initPager(simpleSearchCondition());
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		pager.setSearchType(searchType);
		pager.setKeyword(keyword);
		return SUCCESS;
	}
	
	/**
	 * 根据初始化的hql、parMap、defaultSortColumn、errorInfo、hql4Count生成一个pager对象放入session
	 * @author xuhan
	 * @param searchCondition 查询条件<br />
	 * query[0] = hql<br />
	 * query[1] = parMap<br />
	 * query[2] = defaultSortColumn<br />
	 * query[3] = errorInfo<br />
	 * query[4] = hql4Count	//查询总条数
	 */
	public void initPager(Object[] searchCondition) {
		String hql = (String) searchCondition[0];
		Map paraMap = (Map) searchCondition[1];
		sortColumn = (Integer) searchCondition[2];
		String errorInfo = (String) searchCondition[3];
		String hql4Count = (searchCondition.length >= 5) ? (String) searchCondition[4] : null;
		
		//校验、修正sortColumn的值
		if (sortColumn < 0 || sortColumn >= column().length) {
			sortColumn = 0;
		}
		
		// 从session中读取page对象
		Map session = ActionContext.getContext().getSession();
		Pager existingPager = (Pager) session.get(pageName());
		
		if (existingPager != null) {
			//沿用已有的排序列、排序方向、单页大小
			sortColumn = existingPager.getSortColumn();
			pageSize = existingPager.getPageSize();
		} else {
			//使用默认的排序列、排序方向、单页大小
			pageSize = (Integer) ActionContext.getContext().getApplication().get("rows");//页面大小
		}
		HqlTool hqlTool = new HqlTool(hql);
		hqlTool.setOrderClause(column()[sortColumn] + ", " + pageBufferId());
		if (existingPager != null && hqlTool.getOrder(0) != existingPager.getSortDirection()) {	//已有排序方向和默认排序方向不同
			hqlTool.setOrderClause(hqlTool.reverseOrder());
		}

		Pager pager = new Pager();
		pager.setHql(hqlTool.getHql());
		pager.setHql4Count(hql4Count);
		pager.setParaMap(paraMap);
		pager.setSortColumn(sortColumn);
		pager.setSortDirection(hqlTool.getOrder(0));
		pager.setPageSize(pageSize);
		pager.setSearchType(-1);
		pager.setKeyword("");
		pager.setErrorInfo(errorInfo);
		session.put(pageName(), pager);
	}
	
	/**
	 * 获取列表数据，并放入jsonMap
	 * @author xuhan
	 * @return
	 */
	public String list() {
		fetchListData();
		jsonListPut();
		return SUCCESS;
	}
	
	/**
	 * 遵照pager的指示获取列表数据，放入pager
	 * @author xuhan
	 */
	private void fetchListData() {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName());
		
		if (pager.getErrorInfo() != null) {
			return;
		}
		
		//Step1: 确定页码
		if (pager.getTargetEntityId() != null) {
			pageNumber = null;
			String tagetEntityId = pager.getTargetEntityId();
			pager.setTargetEntityId(null);

			//在缓存中找到了entityId
			if (pager.getIdsBuffer() != null) {
				for (int i = 0; i < pager.getIdsBuffer().size(); i++) {
					if (tagetEntityId.equals(pager.getIdsBuffer().get(i))) {
						pageNumber = pager.getStartPageNumber() + i / pager.getPageSize();
						break;
					}
				}
			}
			//没有在缓存中找到entityId
			if (pageNumber == null) {
				int curRowIndex = calcEntityRowIndex(pager.getHql(), pager.getParaMap(), tagetEntityId);
				pageNumber = curRowIndex / pager.getPageSize() + 1;
				pager.setNeedUpdate(true);
			}
		} else if (pager.getTargetPageNumber() != null) {
			Integer targetPageNumber = pager.getTargetPageNumber();
			pager.setTargetPageNumber(null);

			//判断目标pageNumber是否在当前缓存区段内，以决定是否需要更新缓存
			if (pager.getIdsBuffer() != null) {
				targetPageNumber = Math.max(targetPageNumber, 1);
				targetPageNumber = Math.min(targetPageNumber, (pager.getTotalRows().intValue() - 1) / pager.getPageSize() + 1);	//页码不能大于总页数
				int bufferedPages = (pager.getIdsBuffer().size() - 1) / pager.getPageSize() + 1;	//当前缓存的总页数
				int targetPageOffset = targetPageNumber - pager.getStartPageNumber();
				if (targetPageOffset < 0 || targetPageNumber > bufferedPages) {
					pager.setNeedUpdate(true);
				}
			} else {
				pager.setNeedUpdate(true);
			}
			pageNumber = targetPageNumber;
		}
		if (pageNumber == null && pager.getIdsBuffer() == null) {
			//无缓存 -> 第一页
			pageNumber = 1;
		} 		
		//Step2: 如需更新缓存，do it
		if (pager.isNeedUpdate() || pager.getIdsBuffer() == null) {
			Integer MaximumBufferedPageNumber = (Integer) ActionContext.getContext().getApplication().get("pages");	//最多缓存的页数
			try {
				totalRows = baseService.count(pager.getHql4Count() != null ? pager.getHql4Count() : pager.getHql(), pager.getParaMap());
			} catch (Exception e) {
				e.printStackTrace();
			}
			pageNumber = Math.max(pageNumber, 1);
			pageNumber = Math.min(pageNumber, (totalRows.intValue() - 1) / pager.getPageSize() + 1);	//不能大于总页数
			startPageNumber = Math.max(1, pageNumber - MaximumBufferedPageNumber / 2);
			try {
				pageList = baseService.query(pager.getHql(), pager.getParaMap(), (startPageNumber - 1) * pager.getPageSize(), MaximumBufferedPageNumber * pager.getPageSize());	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//初始化上下条缓存idsBuffer
			int pageBufferIdIndex = new HqlTool(pager.getHql()).getSelectedMemberIndex(pageBufferId());	//pageBufferId是select的第几个成员
			List idsBuffer = new ArrayList<String>();
			for (int i = 0; i < pageList.size(); i++) {
				idsBuffer.add(pageList.get(i)[pageBufferIdIndex]);
			}

			pageListDealWith();

			pager.setStartPageNumber(startPageNumber);
			pager.setTotalRows(totalRows);
			pager.setIdsBuffer(idsBuffer);
			pager.setLaData(laData);

			pager.setNeedUpdate(false);
		}
		totalRows = pager.getTotalRows();
		pageSize = pager.getPageSize();
		laData = pager.getLaData();
		startPageNumber = pager.getStartPageNumber();
		pageBackNumber = (laData.size() - 1) / pageSize + 1;
		sortColumn = pager.getSortColumn();
		sortColumnLabel = pager.getSortDirection();
	}

	/**
	 * 初级检索校验
	 */
	public void validateSimpleSearch() {
		if (keyword != null && keyword.length() > 100) {// 初级检索关键字则截断
			keyword = keyword.substring(0, 100);
		}
	}

	/**
	 * 用于跳转到高级检索页面
	 * (本方法未使用，使用的是直接获取数据方式，参考smdb)
	 */
	public String toAdvSearch() {
		return SUCCESS;
	}
	/**
	 * 直接调用获取高级检索节结果的数据
	 * @return
	 */
	public String advSearch() {
		initPager(advSearchCondition());
		return SUCCESS;
	}

	/**
	 * 排序，按照指定索引列排序。只接收排序索引列作为参数，
	 * 其它参数从page类中读取。
	 */
	public String sort() {
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());

		HqlTool hqlTool = new HqlTool(pager.getHql());
		if (pager.getSortColumn() == sortColumn) {
			hqlTool.setOrderClause(hqlTool.reverseOrder());
			pager.setSortDirection(1 - pager.getSortDirection());
		} else {
			hqlTool.setOrderClause(column()[sortColumn] + ", " + pageBufferId());
			pager.setSortColumn(sortColumn);
			pager.setSortDirection(hqlTool.getOrder(0));
		}
		pager.setHql(hqlTool.getHql());
		pager.setTargetPageNumber(1);
		pager.setNeedUpdate(true);
		
		return SUCCESS;
	}

	/**
	 * 排序校验
	 */
	public void validateSort() {
		if (sortColumn < 0 || sortColumn >= column().length) {// 排序列必须在合法范围，否则取第一列排序
			sortColumn = 0;
		}
	}

	/**
	 * 改变页面大小。只接收页面大小参数，其它参数从page类中读取。
	 */
	public String changePageSize() {
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		
		pager.setPageSize(pageSize);
		pager.setTargetPageNumber(1);
		pager.setNeedUpdate(true);

		return SUCCESS;
	}

	/**
	 * 改变页面大小校验
	 */
	public void validateChangePageSize() {
		if (pageSize != 10 && pageSize != 20 && pageSize != 50) {// 页面大小不不合法，则取默认值
			pageSize = (Integer) ActionContext.getContext().getApplication().get("rows");
		}
	}
	
	/**
	 * 到含有指定entityId的页。
	 * 可接受update参数，默认不强制更新缓存,update非零时强制更新缓存。
	 */
	public String toEntity() {
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		pager.setTargetEntityId(entityId);
		if (update != null && update != 0) {
			pager.setNeedUpdate(true);
		}
		return SUCCESS;
	}

	/**
	 * 到指定页。
	 * 可接受update参数，默认不强制更新缓存,update非零时强制更新缓存。
	 */
	public String toPage() {
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		pager.setTargetPageNumber(pageNumber);
		if (update != null && update != 0) {
			pager.setNeedUpdate(true);
		}
		return SUCCESS;
	}
	
	/**
	 * 上一条
	 * @throws Exception 
	 */
	public String prev() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName());
		if (pager == null) {
			// 判断page类是否存在
			initPager(simpleSearchCondition());
		}
		pager = (Pager) session.get(pageName());
		if (pager.getStartPageNumber() > 1 && pager.getIdsBuffer().get(0).equals(entityId)) {
			pager.setNeedUpdate(true);
		}
		pager.setTargetEntityId(entityId);
		fetchListData();

		String curEntityId = entityId;
		entityId = null;
		for (int i = 1; i < pager.getIdsBuffer().size(); i++) {
			if (pager.getIdsBuffer().get(i).equals(curEntityId)) {
				entityId = pager.getIdsBuffer().get(i - 1);
				break;
			}
		}
		if (entityId == null) {// 判断上条ID是否存在
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_PREV_INFO);
			return INPUT;
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 上一条校验
	 */
	public String validatePrev() {
		if (entityId == null || entityId.trim().isEmpty()) {// 当前实体ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	
	/**
	 * 下一条
	 * @throws Exception 
	 */
	public String next() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Pager pager = (Pager) session.get(pageName());
		if (pager == null) {
			// 判断page类是否存在
			initPager(simpleSearchCondition());
		}
		pager = (Pager) session.get(pageName());
		if (pager.getIdsBuffer() != null && (pager.getStartPageNumber() - 1) * pager.getPageSize() + pager.getIdsBuffer().size() < pager.getTotalRows()) {
			if (pager.getIdsBuffer().get(pager.getIdsBuffer().size() - 1).equals(entityId)) {
				pager.setNeedUpdate(true);
			}
		}
		pager.setTargetEntityId(entityId);
		fetchListData();

		String curEntityId = entityId;
		entityId = null;
		for (int i = 0; i < pager.getIdsBuffer().size() - 1; i++) {
			if (pager.getIdsBuffer().get(i).equals(curEntityId)) {
				entityId = pager.getIdsBuffer().get(i + 1);
				break;
			}
		}
		if (entityId == null) {// 判断下条ID是否存在
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_NEXT_INFO);
			return INPUT;
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 下一条校验
	 */
	public String validateNext() {
		if (entityId == null || entityId.trim().isEmpty()) {// 当前权限ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	
	/**
	 * 计算entityId所对应记录在hql中的排序规则下是第几条(0-indexed)
	 * @param hql
	 * @param paraMap
	 * @param entityId
	 * @return
	 * @author xuhan
	 */
	private int calcEntityRowIndex(String hql, Map paraMap, String entityId) {
		HqlTool hqlTool = new HqlTool(hql);
		String originWhereClause = hqlTool.getWhereClause();
		//如果orderClause包含count，则返回到第一页（简化处理）
		if (hqlTool.orderClause == null || hqlTool.orderClause.contains("count(")) {
			return 0;
		}
		
		//找到本条记录排序关键字的值
		String newWhereClause = pageBufferId() + " = :entityId";
		if (originWhereClause != null && !originWhereClause.isEmpty()) {
			newWhereClause += " and (" + originWhereClause + ")";
		}
		hqlTool.setSelectClause(StringTool.joinString(hqlTool.orderByVariables, ", "));
		hqlTool.setWhereClause(newWhereClause);
		paraMap.put("entityId", entityId);
		Object[] thisRecordValue = (Object[]) baseService.queryUnique(hqlTool.getHql("sfwgh"), paraMap);
		if (thisRecordValue == null) {
			//找不到本条，应该是被删了
			return 0;
		}
		for (int i = 0; i < thisRecordValue.length; i++) {
			paraMap.put("var" + i, thisRecordValue[i]);
		}
		
		//找到按当前排序规则排在当前记录前面的记录数
		newWhereClause = hqlTool.orderToWhere(false, thisRecordValue);
		if (originWhereClause != null && !originWhereClause.isEmpty()) {
			newWhereClause += " and (" + originWhereClause + ")";
		}
		Integer cnt = 0;
		hqlTool.setWhereClause(newWhereClause);
		try {                                                                      
			hqlTool.setSelectClause("count(*)");
			cnt = ((Number) baseService.queryUnique(hqlTool.getHql("sfwhg"), paraMap)).intValue();
		} catch (Exception e) {
			//有group by子句的hql用上述方法得不到正确的数目
			hqlTool.setSelectClause(pageBufferId());
			cnt = baseService.query(hqlTool.getHql("sfwhg"), paraMap).size();
		}
		return cnt;
	}
	
	
	/**
	 * 为各评审参数赋值
	 */
	protected void initConfig() {
		
		Map application = ActionContext.getContext().getApplication();
		
		expertProjectMin = (Integer) application.get("GeneralExpertProjectMin");
		
		expertProjectMax = (Integer) application.get("GeneralExpertProjectMax");
		
		projectMinistryExpertNumber = (Integer) application.get("GeneralMinistryExpertNumber");
		
		projectLocalExpertNumber = (Integer) application.get("GeneralLocalExpertNumber");
		
		projectExpertNumber = projectMinistryExpertNumber + projectLocalExpertNumber;

	}
	
	public Integer getProjectExpertNumber() {
		return projectExpertNumber;
	}
	public void setProjectExpertNumber(Integer projectExpertNumber) {
		this.projectExpertNumber = projectExpertNumber;
	}
	public Integer getExpertProjectMax() {
		return expertProjectMax;
	}
	public void setExpertProjectMax(Integer expertProjectMax) {
		this.expertProjectMax = expertProjectMax;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getTotalRows() {
		return totalRows;
	}
	public int getPageBackNumber() {
		return pageBackNumber;
	}
	public int getSortColumnLabel() {
		return sortColumnLabel;
	}
	public int getStartPageNumber() {
		return startPageNumber;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	public Map getJsonMap() {
		return jsonMap;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public List<String> getEntityIds() {
		return entityIds;
	}
	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}
	public List<Object[]> getPageList() {
		return pageList;
	}
	public void setPageList(List<Object[]> pageList) {
		this.pageList = pageList;
	}
	public List getLaData() {
		return laData;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}
	public Integer getUpdate() {
		return update;
	}
	public void setUpdate(Integer update) {
		this.update = update;
	}
	public void setListType(Integer listType){
		this.listType = listType;
	}
	public Integer getListType(){
		return listType;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
}