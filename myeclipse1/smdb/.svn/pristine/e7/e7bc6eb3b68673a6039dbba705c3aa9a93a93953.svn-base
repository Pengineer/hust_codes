package csdc.action.system.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.service.imp.TotalSearchService;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;

public class TotalSearchAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private String searchRange;//全站检索范围
	private String searchWord;//全站检索关键字
	private Map dataList;//检索结果
	private String dataType;//数据类型
	private Map linkedMap;
	private Integer isInit;

	@Autowired
	private TotalSearchService totalSearchService;
	
	//进入全站检索
	public String toTotalSearch(){
		return SUCCESS;
	}
	
	//初始化数据
	public void initData(){
		dataList = totalSearchService.totalSearch(searchRange, searchWord);
	}
	
	//初始化列表
	public String initList(){
		if(dataList == null){
			initData();
		}
//		Object[] a = dataList.keySet().toArray();
//		for(Object o : a){
//			if(dataList.get(o.toString()) != null){
//				jsonMap.put(o.toString(), "true");
//			}
//		}
		linkedMap = new LinkedHashMap();
		if(dataList.get("person") != null){
			linkedMap.put("person", "true");
		}
		if(dataList.get("agency") != null){
			linkedMap.put("agency", "true");
		}
		if(dataList.get("project") != null){
			linkedMap.put("project", "true");
		}
		if(dataList.get("product") != null){
			linkedMap.put("product", "true");
		}
		if(dataList.get("award") != null){
			linkedMap.put("award", "true");
		}
		if(dataList.get("info") != null){
			linkedMap.put("info", "true");
		}
		return SUCCESS;
	}
	
	//更新索引
	public String updateIndex(){
		if (totalSearchService.updateIndex(searchRange)) {
			jsonMap.put("hintInfo", "索引更新成功！");
		}else {
			jsonMap.put(GlobalInfo.ERROR_INFO, "索引更新失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 检测索引是否存在
	 * @return
	 */
	public String isExistIndex(){
		if (!totalSearchService.isExistIndexFile(searchRange)) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "索引不存在，请先更新索引！");
		} 
		return SUCCESS;
	}
	
	/**
	 * 用于获取列表数据，直接由JS调用
	 */
	protected List<Object[]> listData() {
		if(dataList == null){
			initData();
		}
		List<Object[]> list = new ArrayList<Object[]>();
		list = (List<Object[]>)dataList.get(dataType);
		return list;
	}
	
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
		Pager pager = fetchPager(true);

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
	
	public TotalSearchService getTotalSearchService() {
		return totalSearchService;
	}

	public void setTotalSearchService(TotalSearchService totalSearchService) {
		this.totalSearchService = totalSearchService;
	}

	public String getSearchRange() {
		return searchRange;
	}

	public void setSearchRange(String searchRange) {
		this.searchRange = searchRange;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public Map getDataList() {
		return dataList;
	}

	public void setDataList(Map dataList) {
		this.dataList = dataList;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Map getLinkedMap() {
		return linkedMap;
	}

	public void setLinkedMap(Map linkedMap) {
		this.linkedMap = linkedMap;
	}

	@Override
	public String pageName() {
		return "totalSearchPage";
	}

	@Override
	public String[] column() {
		return null;
	}

	@Override
	public String dateFormat() {
		return null;
	}

	@Override
	public Object[] simpleSearchCondition() {
		return null;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return null;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

}
