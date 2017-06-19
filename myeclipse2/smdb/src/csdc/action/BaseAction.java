package csdc.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import csdc.service.IMessageAuxiliaryService;
import csdc.tool.ApplicationContainer;
import csdc.tool.HqlTool;
import csdc.tool.StringTool;
import csdc.tool.bean.LoginInfo;
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
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	protected IBaseService baseService;
	
	@Autowired
	protected HibernateBaseDao dao;
	
	@Autowired
	protected IMessageAuxiliaryService messageAssistService;
	
	@Autowired
	protected DmssService dmssService;

	protected Map jsonMap = new HashMap();// json对象容器
	protected List<Object[]> pageList;// 初始查询列表数据
	protected List laData;// 处理后的列表数据
	protected int totalRows;// 总记录数
	protected int pageSize;// 页面大小
	protected int startPageNumber;// 当前页所在区段的起始页
	protected Integer pageNumber;// 目标页码
	protected int pageBackNumber;// 一次数据库查询最多返回的页数
	protected int sortColumn;// 排序列标号
	protected int sortColumnLabel;// 降序还是升序0降序，1升序
	protected int searchType;// 初级检索类别
	protected String keyword;// 初级检索关键字
	protected Map searchQuery;// 初级检索和高级检索的检索条件
	protected String entityId;// 单个实体ID
	protected List<String> entityIds;// 多个实体ID
	protected int update;//是否强制初始化pager
	protected String pageInfo;// 页面载入后，用于弹出的提示信息
	protected HttpServletRequest request;// 请求的request对象
	protected LoginInfo loginer;// 当前登录账号信息对象
	protected Map session;//session对象
	protected String errorInfo;
	protected int listForFundList;	//用于拨款清单的拨款列表显示
	
	/**
	 * 为了复用初级检索、高级检索、到指定页、排序、改变页面大小、
	 * 上条、下条等功能，需要获取子类page名称、排序数组、时间格式、
	 * 初级检索action方法、上下条缓存ID名称等信息，定义以下几个抽象方法。
	 */
	
	
	/**
	 * 指定该列表的名字。一个名字的列表对应一个Pager，因此在同一个httpSession中，一个名字的列表只有一个。
	 * @return
	 */
	public abstract String pageName();
	
	/**
	 * 指定可用于排序的条件。即HQL中的order by子句。执行查询时模块会自动将其拼接在模块指定的HQL(由simpleSearchCondition或advSearchCondition返回，不包含order by子句)后面，故其值中使用的别名必须能在上述HQL中的from子句中找到。支持多个排序条件（第一列相同时，使用第二列，依此类推。即和HQL的order by规则相同）。
	 * @return
	 */
	public abstract String[] column();
	
	public abstract String dateFormat();
	
	/**
	 * 指定列表数据查询的条件，要求返回的数组恰好包含四个元素：<br>
	 * 1.默认查询HQL(String):hql、<br>
	 * 2.查询参数(Map):paraMap、<br>
	 * 3.默认排序列(Integer):defaultColumn、<br>
	 * 4.错误信息(String):errorInfo。<br>
	 * hql可根据BaseAction的成员keyword和searchType拼接而成，即对应于初级检索的hql。<br>
	 * 这里的HQL只支持named parameter方式传参，不支持占位符(?)方式传参。参数以键值对形式存入paraMap。<br>
	 * 默认排序列指第一次进入列表时，未人为点击列表表头前的排序方式，其值对应于column()所返回数组的下标。<br>
	 * @return
	 */
	public abstract Object[] simpleSearchCondition();
	
	/**
	 * 和simpleSearchCondition()类似，但hql根据高级检索所用变量拼接而成。
	 * @return
	 */
	public abstract Object[] advSearchCondition();
	
	/**
	 * 唯一确定一条记录的entityId名，可以用于view、delete<br />
	 * 必须是hql查询语句中select子句中的一个成员
	 * @return
	 */
	public abstract String pageBufferId();
	
	/**
	 * 进入列表页面用。进入列表页面有从左侧菜单进入、查看页面返回两种方式。约定从左侧菜单进入时，仅保留页面大小和排序列，而将页码置1、清空检索条件；从查看页面返回时则保留所有现场。
	 */
	public String toList() {
		Pager pager = fetchPager(update != 0);
		/*
		 * 如果指定了entityId则跳到，该行所在的行；
		 * 否则如果制定了页码，则跳到指定页；
		 * 否则跳到第一页
		 */
		if (entityId != null) {
			pager.setTargetEntityId(entityId.replaceAll("\\W.*", ""));
		} else if (pageNumber != null) {
			pager.setTargetPageNumber(pageNumber);
		}
		searchQuery = pager.getSearchQuery();
		return SUCCESS;
	}
	
	/**
	 * 初级检索。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 * @return
	 */
	public String simpleSearch() {
		initPager(simpleSearchCondition());
		searchQuery = fetchPager(false).getSearchQuery();
		searchQuery.put("advFlag", 0);
		saveSimpleSearchQuery(searchQuery);
		return SUCCESS;
	}

	/**
	 * 调用fetchListData()获取列表正文数据，并放入jsonMap。
	 * @return
	 */
	public String list() {
		try {
			fetchListData();
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
		} finally {
			jsonListPut();
		}
		return SUCCESS;
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
	 * 高级检索。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 * @return
	 */
	public String advSearch() {
		initPager(advSearchCondition());
		searchQuery = fetchPager(false).getSearchQuery();
		searchQuery.put("advFlag", 1);
		saveAdvSearchQuery(searchQuery);
		return SUCCESS;
	}

	/**
	 * 排序。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 */
	public String sort() {
		Pager pager = fetchPager(false);

		/*
		 * 按照当前pager内的查询语句，实例化hql辅助工具
		 */
		HqlTool hqlTool = new HqlTool(pager.getHql());
		if (pager.getSortColumn() == sortColumn) {
			/*
			 * 如果点击的排序列和当前排序列相同，则对目前排序方向倒序
			 */
			hqlTool.setOrderClause(hqlTool.reverseOrder());
			pager.setSortDirection(1 - pager.getSortDirection());
		} else {
			/*
			 * 否则按照指定的列排序，方向和column()中指定的相同
			 */
			hqlTool.setOrderClause(column()[sortColumn] + ", " + pageBufferId());
			pager.setSortColumn(sortColumn);
			pager.setSortDirection(hqlTool.getOrder(0));
		}
		pager.setHql(hqlTool.getHql());
		
		/*
		 * 重新排序后一律翻到第一页
		 */
		pager.setTargetPageNumber(1);
		
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
	 * 改变页面大小。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 */
	public String changePageSize() {
		Pager pager = fetchPager(false);
		
		pager.setPageSize(pageSize);

		/*
		 * 改变页面大小后一律翻到第一页
		 */
		pager.setTargetPageNumber(1);

		return SUCCESS;
	}

	/**
	 * 改变页面大小校验
	 */
	public void validateChangePageSize() {
		if (pageSize != 10 && pageSize != 20 && pageSize != 50) {// 页面大小不不合法，则取默认值
			pageSize = Integer.parseInt((String)ActionContext.getContext().getApplication().get("rows"));
		}
	}
	
	/**
	 * 到含有指定entityId的页。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 */
	public String toEntity() {
		Pager pager = fetchPager(false);
		pager.setTargetEntityId(entityId);
		return SUCCESS;
	}

	/**
	 * 到指定页码。修改pager成员，待chain至list.action，调用fetchListData()获取数据。
	 */
	public String toPage() {
		Pager pager = fetchPager(false);
		pager.setTargetPageNumber(pageNumber);
		return SUCCESS;
	}
	
	/**
	 * 用于查看页面上一条，该action获取上条的entityId，并redirect到模块的view.action
	 */
	public String prev() {
		try {
			entityId = calcEntityIdByOffset(entityId, -1);
			return SUCCESS;
		} catch (IndexOutOfBoundsException e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_PREV_INFO);
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
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
	 * 用于查看页面下一条，该action获取下条的entityId，并redirect到模块的view.action
	 */
	public String next()  {
		try {
			entityId = calcEntityIdByOffset(entityId, 1);
			return SUCCESS;
		} catch (IndexOutOfBoundsException e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_NEXT_INFO);
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
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
	 * 将列表查询结果pageList进行处理，存入laData。默认的处理只有把Date类型的数据按照一定方式格式化。本方法默认仅格式化时间，各模块可按照自身需求任意处理，甚至可以使laData和pageList列数不同。
	 */
	public void pageListDealWith() {
		laData = new ArrayList();// 处理之后的列表数据
		Object[] o;// 缓存查询结果的一行
		String[] item;// 缓存查询结果一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat());// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历初始查询列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : pageList) {
			o = (Object[]) p;
			item = new String[o.length];
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
	 * 指定初级检索前，对检索条件的初始化。注意：这里searchQuery即是pager中的同名成员。<br>
	 * 由于大多数列表页面的初级检索均只有一个检索条件searchType和一个关键字keyWord，因此BaseAction对该方法有一个针对这两个变量的缺省实现。
	 * @param searchMap
	 */
	public void initSearchMap(Map searchQuery) {
		searchQuery.put("searchType", -1);
		searchQuery.put("keyWord", "");
	}
	
	/**
	 * 将初级检索条件存进pager<br>
	 * 如果列表的初级检索不是使用searchType和keyword这两个变量，请重写该方法<br>
	 * 将用户从页面指定的初级检索条件存入pager.searchQuery。<br>
	 * 注意：之所以要将检索条件存入pager，是为了在从查看页面回到列表页面时恢复现场。
	 */
	public void saveSimpleSearchQuery(Map searchQuery) {
		searchQuery.put("searchType", searchType);
		searchQuery.put("keyword", keyword);
	}

	/**
	 * 将高级检索条件存进pager<br>
	 * 仿照saveSimpleSearchQuery的方式由子类实现
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
	}

	///////////////////////////以下为本BaseAction辅助方法，不应单独调用////////////////////////////////////////
	
	/**
	 * 将列表功能的公共成员变量放入jsonMap对象中，包括：列表数据、
	 * 总记录数、页面大小、起始页码、当前页码、每次查询页数、
	 * 排序列标号、排序规则。
	 */
	private void jsonListPut() {
		// 将列表相关的公共变量存入jsonMap对象
		jsonMap.put("laData", laData);
		jsonMap.put("totalRows", totalRows);
		jsonMap.put("pageSize", pageSize);
		jsonMap.put("startPageNumber", startPageNumber);
		jsonMap.put("pageNumber", pageNumber);
		jsonMap.put("pageBackNumber", pageBackNumber);
		jsonMap.put("sortColumn", sortColumn);
		jsonMap.put("sortColumnLabel", sortColumnLabel);
	}

	/**
	 * 根据初始化的hql、parMap、defaultSortColumn、errorInfo、hql4Count生成一个pager对象放入session
	 * @param searchCondition 查询条件<br />
	 * query[0] = hql<br />
	 * query[1] = parMap<br />
	 * query[2] = defaultSortColumn<br />
	 * query[3] = errorInfo
	 */
	private void initPager(Object[] searchCondition) {
		String hql = (String) searchCondition[0];
		HashMap paraMap = (HashMap) searchCondition[1];
		
		int sortColumn;
		int pageSize;

		// 从session中读取page对象
		Pager existingPager = (Pager) session.get(pageName());
		
		if (existingPager != null) {
			//沿用已有的排序列、排序方向、单页大小
			sortColumn = existingPager.getSortColumn();
			pageSize = existingPager.getPageSize();
		} else {
			//使用默认的排序列、排序方向、单页大小
			sortColumn = (Integer) searchCondition[2];
			pageSize = Integer.parseInt((String)ActionContext.getContext().getApplication().get("rows"));
		}
		//校验、修正sortColumn的值
		if (sortColumn < 0 || sortColumn >= column().length) {
			sortColumn = 0;
		}
		
		HqlTool hqlTool = new HqlTool(hql);
		hqlTool.setOrderClause(column()[sortColumn] + ", " + pageBufferId());
		//已有排序方向和默认排序方向不同
		if (existingPager != null && hqlTool.getOrder(0) != existingPager.getSortDirection()) {	//已有排序方向和默认排序方向不同
			hqlTool.setOrderClause(hqlTool.reverseOrder());
		}
		Pager pager = new Pager();
		pager.setHql(hqlTool.getHql());
		pager.setParaMap(paraMap);
		pager.setSortColumn(sortColumn);
		pager.setSortDirection(hqlTool.getOrder(0));
		pager.setPageSize(pageSize);
		initSearchMap(pager.getSearchQuery());
		session.put(pageName(), pager);
	}
	
	/**
	 * 
	 * 辅助方法。按照pager的指示，查询指定区间的列表数据。查询过程分两步:<br>
	 * 1.	确定页码。由于支持按页码和entityId两种方式定位，因此如果通过后者定位，需要先根据entityId找到该行所在的页码，再统一通过页码查询。<br>
	 * 2.	查询指定页码区间的列表数据。
	 */
	private void fetchListData() {
		Pager pager = fetchPager(false);
		
		/*
		 * 一次查询最多返回的行数 
		 */
		int queryRows = Integer.valueOf(ApplicationContainer.sc.getAttribute("queryRows").toString());
		
		/*
		 * 一次查询最多返回的页数
		 */
		int maxPageBackNumber = queryRows / pager.getPageSize();
		
		/*
		 * 列表模块使用的二级缓存命名空间一律为listPage
		 */
		dao.setCacheRegion("listPage");
		try {
			totalRows = (int) dao.count(pager.getHql(), pager.getParaMap());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageSize = pager.getPageSize();
		
		if (queryRows % pageSize != 0) {
			throw new RuntimeException("queryRows 必须是  pageSize 的整数倍!");
		}
		
		//Step1: 确定页码
		if (pager.getTargetEntityId() != null) {
			/*
			 * 使用entityId定位
			 */
			pageNumber = calcRowIndex(pager.getTargetEntityId()) / pageSize + 1;
		} else {
			/*
			 * 直接使用页码
			 */
			pageNumber = pager.getTargetPageNumber();
		}
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > (totalRows - 1) / pageSize + 1) {
			pageNumber = (totalRows - 1) / pageSize + 1;
		}
		
		/*
		 * 使用完毕后，将定位用的两个参数清空。
		 * 这样下次调用toEntity或者toPage之后，这两个参数只有一个非空，定位指令才无歧义。
		 */
		pager.setTargetEntityId(null);
		pager.setTargetPageNumber(1);
		
		//Step2: 查询列表数据
		/*
		 * 根据页码获取所在数据段，尽量使二级缓存命中。分段算法解释见文档。
		 */
		startPageNumber = (pageNumber - 1) / maxPageBackNumber * maxPageBackNumber + 1;
		dao.setCacheRegion("listPage");
		pageList = dao.query(
			pager.getHql(),
			pager.getParaMap(),
			(startPageNumber - 1) * pageSize,
			queryRows
		);
		pageListDealWith();
		System.out.println(pager.getHql());
		pageBackNumber = (laData.size() - 1) / pageSize + 1;
		sortColumn = pager.getSortColumn();
		sortColumnLabel = pager.getSortDirection();
	}
	
	/**
	 * 获取session中现有的pager实例，若没有，实例化一个并放入session
	 * @param update 是否忽略session内的已有pager，强制重新实例化
	 * @return
	 */
	private Pager fetchPager(boolean update) {
		Pager pager = (Pager) session.get(pageName());
		// 判断page类是否存在
		if (!update && pager != null) {
			return pager;
		} else {
			/*
			 * 如果强制更新pager，或者session内未找到该列表的pager，则使用初级检索条件初始化一个pager实例，并放入session
			 */
			initPager(simpleSearchCondition());
			return (Pager) session.get(pageName());
		}
	}
	
	/**
	 * 根据列表当前的筛选规则和排序条件，获取queryEntityId偏移offset行的id。目前用于上下条获取所需行的id。
	 * @param queryEntityId
	 * @param offset
	 * @return 指定条的entityId
	 */
	private String calcEntityIdByOffset(String queryEntityId, int offset) {
		int queryRows = Integer.valueOf(ApplicationContainer.sc.getAttribute("queryRows").toString());
		
		/*
		 * queryEntityId所在行在当前排序、检索条件下是第几条数据
		 */
		int rowIndex = calcRowIndex(queryEntityId);
		if (rowIndex < 0) {
			/*
			 * 若找不到该条数据，最有可能的情况是，是该条数据被删
			 */
			throw new RuntimeException();
		}
		/*
		 * 找到改行所在数据段，尽量使二级缓存命中。分段算法解释见文档。
		 */
		int startRowIndex = Math.max(0, (rowIndex - queryRows / 4) / (queryRows / 2) * (queryRows / 2));
		Pager pager = fetchPager(false);
		dao.setCacheRegion("listPage");
		List<String> tmpEntityIds = dao.query(
			"select " + pageBufferId() + new HqlTool(pager.getHql()).getHql("fwgho"),
			pager.getParaMap(),
			startRowIndex,
			queryRows
		);
		return tmpEntityIds.get(rowIndex - startRowIndex + offset);
	}
	
	/**
	 * 得到entityId所对应记录在当前pager的排序规则下是第几条(0-indexed)， 并将结果存在pager.currentRowIndex内，加速下次查询。该操作分两步：<br>
	 * 1.	先在pager.currentRowIndex附近查找，如果找到则返回；<br>
	 * 2.	根据order by条件和当前行数据order by变量的值，拼装出查询在该条数据前条目数的语句，使用hql的count直接定位出改行的位置。(该功能由HqlTool. orderToWhere()提供支持，实现相对复杂，详情参见代码)<br>
	 * @param entityId
	 * @return 行号(0-indexed); 如果找不到，返回-1
	 */
	private int calcRowIndex(String entityId) {
		int queryRows = Integer.valueOf(ApplicationContainer.sc.getAttribute("queryRows").toString());
		Pager pager = fetchPager(false);

		//Step1: 先尝试pager.currentRowIndex附近的值
		int startRowIndex = Math.max(0, (pager.getCurrentRowIndex() - queryRows / 4) / (queryRows / 2) * (queryRows / 2));
		dao.setCacheRegion("listPage");
		List<Serializable> tmpEntityIds = dao.query(
			"select " + pageBufferId() + new HqlTool(pager.getHql()).getHql("fwgho"),
			pager.getParaMap(),
			startRowIndex,
			queryRows
		);
		/*
		 * 逐条匹配附近的entityId 
		 */
		for (int i = 0; i < tmpEntityIds.size(); i++) {
			if (tmpEntityIds.get(i).equals(entityId)) {
				pager.setCurrentRowIndex(startRowIndex + i);
				return startRowIndex + i;
			}
		}

		//Step2: 若未找到则使用order by转where的方法找出行号
		HqlTool hqlTool = new HqlTool(pager.getHql());
		String originWhereClause = hqlTool.getWhereClause();
		
		//找到本条记录排序关键字的值
		String newWhereClause = pageBufferId() + " = :entityId";
		if (originWhereClause != null && !originWhereClause.isEmpty()) {
			newWhereClause += " and (" + originWhereClause + ")";
		}
		hqlTool.setSelectClause(StringTool.joinString(hqlTool.orderByVariables, ", "));
		hqlTool.setWhereClause(newWhereClause);
		
		HashMap paraMap = (HashMap) pager.getParaMap().clone();
		paraMap.put("entityId", entityId);
		Object[] thisRecordValue = (Object[]) dao.queryUnique(hqlTool.getHql("sfwgh"), paraMap);
		if (thisRecordValue == null) {
			//找不到本条，应该被删
			return -1;
		}
		for (int i = 0; i < thisRecordValue.length; i++) {
			paraMap.put("var" + i, thisRecordValue[i]);
		}
		
		//找到按当前排序规则排在当前记录前面的记录数
		newWhereClause = hqlTool.orderToWhere(false, thisRecordValue);
		if (originWhereClause != null && !originWhereClause.isEmpty()) {
			newWhereClause += " and (" + originWhereClause + ")";
		}
		hqlTool.setWhereClause(newWhereClause);
		hqlTool.setSelectClause("1");
		
		int rowIndex = (int) dao.count(hqlTool.getHql("sfwhg"), paraMap);
		
		/*
		 * 将结果存在pager.currentRowIndex内，加速下次查询
		 */
		pager.setCurrentRowIndex(rowIndex);
		return rowIndex;
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
	public int getTotalRows() {
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
	public IBaseService getBaseService() {
		return baseService;
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
	public List getLaData() {
		return laData;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.loginer = (LoginInfo) request.getSession().getAttribute(GlobalInfo.LOGINER);
		this.request = request;
	}
	public void setLoginer(LoginInfo loginer) {
		this.loginer = loginer;
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
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
	public void setDao(HibernateBaseDao dao) {
		this.dao = dao;
	}
	public Map getSearchQuery() {
		return searchQuery;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public int getListForFundList() {
		return listForFundList;
	}

	public void setListForFundList(int listForFundList) {
		this.listForFundList = listForFundList;
	}
	
}
