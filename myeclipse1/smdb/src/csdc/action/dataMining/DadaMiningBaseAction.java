package csdc.action.dataMining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;

/**
 * 数据挖掘基础服务类
 * @author fengcl
 *
 */
public abstract class DadaMiningBaseAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	protected int toDataBase;		        // 是否需要入库（1：入库；0：否）
	
	/**
	 * 获取列表数据，在具体子类中实现
	 * @return
	 */
	protected abstract List<Object[]> listData();
	
	/**
	 * 初级检索
	 */
	public String simpleSearch() {
		initPager();
		Pager pager = (Pager) session.get(pageName());
		pager.getSearchQuery().put("searchType", searchType);
		pager.getSearchQuery().put("keyword", keyword);
		return SUCCESS;
	}
	
	/**
	 * 获取列表数据，并放入jsonMap
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
	
	private void fetchListData() {
		Pager pager = fetchPager(false);

		sortColumnLabel = pager.getSortDirection();
		sortColumn = pager.getSortColumn();
		Map searchMap = pager.getSearchQuery();
		searchType = (Integer) searchMap.get("searchType");
		keyword = (String) searchMap.get("keyword");
		
		//Step1: 查询列表数据
		startPageNumber = 1;
		
		laData = listData();
		if (laData == null) {
			laData = new ArrayList<Object[]>();
		}
		
		totalRows = laData.size();
		pageSize = pager.getPageSize();

		//Step2: 确定页码
		pageNumber = pager.getTargetPageNumber();
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > (totalRows - 1) / pageSize + 1) {
			pageNumber = (totalRows - 1) / pageSize + 1;
		}
		pager.setTargetPageNumber(1);

		pageBackNumber = (laData.size() - 1) / pageSize + 1;
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
	 * 排序，按照指定索引列排序。只接收排序索引列作为参数，
	 * 其它参数从page类中读取。
	 */
	public String sort() {
		Pager pager = fetchPager(false);

		if (pager.getSortColumn() == sortColumn) {
			pager.setSortDirection(1 - pager.getSortDirection());
		} else {
			pager.setSortColumn(sortColumn);
			pager.setSortDirection(1);
		}
		pager.setTargetPageNumber(1);
		
		return SUCCESS;
	}

	/**
	 * 排序校验
	 */
	public void validateSort() {
		if (sortColumn != 1 && sortColumn != 2 && sortColumn != 3 && sortColumn != 6 && sortColumn != 7) {// 排序列必须在合法范围，否则取第一列排序
			sortColumn = 1;
		}
	}

	/**
	 * 改变页面大小。只接收页面大小参数，其它参数从page类中读取。
	 */
	public String changePageSize() {
		Pager pager = fetchPager(false);
		
		pager.setPageSize(pageSize);
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
	 * 到指定页。
	 * 可接受update参数，默认不强制更新缓存,update非零时强制更新缓存。
	 */
	public String toPage() {
		Pager pager = (Pager) session.get(pageName());
		pager.setTargetPageNumber(pageNumber);
		return SUCCESS;
	}
	
	
	/**
	 * 将列表功能的公共成员变量放入jsonMap对象中，包括：列表数据、
	 * 总记录数、页面大小、起始页码、当前页码、每次查询页数、
	 * 排序列标号、排序规则。
	 */
	public void jsonListPut() {
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
	
	private void initPager() {
		sortColumn = 7;
		
		// 从session中读取page对象
		Pager existingPager = (Pager) session.get(pageName());
		
		if (existingPager != null) {
			//沿用已有的排序列、排序方向、单页大小
			sortColumn = existingPager.getSortColumn();
			pageSize = existingPager.getPageSize();
		} else {
			//使用默认的排序列、排序方向、单页大小
			pageSize = Integer.parseInt((String)ActionContext.getContext().getApplication().get("rows"));
		}

		Pager pager = new Pager();
		pager.setSortColumn(sortColumn);
		pager.setSortDirection(1);
		pager.setPageSize(pageSize);
		pager.getSearchQuery().put("searchType", -1);
		pager.getSearchQuery().put("keyword","");
		session.put(pageName(), pager);
	}
	
	/**
	 * 获取pager实例，若没有，实例化并放入session
	 * @return
	 */
	private Pager fetchPager(boolean update) {
		Pager pager = (Pager) session.get(pageName());
		// 判断page类是否存在
		if (!update && pager != null) {
			return pager;
		} else {
			initPager();
			return (Pager) session.get(pageName());
		}
	}

	public int getToDataBase() {
		return toDataBase;
	}
	public void setToDataBase(int toDataBase) {
		this.toDataBase = toDataBase;
	}
}
