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
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;

/**
 * 功能描述：
 *     根据子类的指定的list数据生成相应的列表
 * @author 杨发强
 */

public abstract class DbListAction extends ActionSupport implements ServletRequestAware, SessionAware {


	private static final long serialVersionUID = 1L;

	@Autowired
	protected IBaseService baseService;
	@Autowired
	protected HibernateBaseDao dao;
	protected Map jsonMap = new HashMap();// json对象容器
	protected List laData;// 处理后的列表数据
	protected int totalRows;// 总记录数
	protected int pageSize;// 页面大小
	protected int startPageNumber;// 当前页所在区段的起始页
	protected Integer pageNumber;// 页码
	protected int pageBackNumber;// 每次查询的页数
	protected int sortColumn;// 排序列标号
	protected int sortColumnLabel;// 降序还是升序0降序，1升序
	protected int searchType;// 初级检索类别
	protected String keyword;// 初级检索关键字
	protected int update;//是否强制初始化pager
	protected HttpServletRequest request;// 请求的request对象
	protected Map session;//session对象
	protected LoginInfo loginer;// 当前登录账号信息对象

	
	
	/**
	 * 指定该列表的名字。一个名字的列表对应一个Pager，因此在同一个httpSession中，一个名字的列表只有一个。
	 * @return
	 */
	public abstract String pageName();
	/**
	 * 获取指定列表的数据
	 * 各个子类里面实现
	 */
	public abstract List<Object[]> listData();
	
	/**
	 * 进入列表之前预处理
	 */
	public abstract void toListCondition();


	public String toList() {
		toListCondition();
		fetchPager(update != 0);
		return SUCCESS;
	}
	
	/**
	 * 获取列表数据，并放入jsonMap
	 * @author xuhan
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
		
		//Step1: 组装数据
		startPageNumber = 1;
		laData = new ArrayList();// 处理之后的列表数据
		List<Object[]> dataList = listData();
		Object[] o;// 列表数据的一行
		String[] item;//列表数据一行中的每一字段
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间格式化对象
		String datestr;// 格式化之后的时间字符串
		
		// 遍历列表数据，按照指定格式，格式化其中的时间字段，其它字段转化为字符串
		for (Object p : dataList) {
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
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		pager.setTargetPageNumber(pageNumber);
		return SUCCESS;
	}
	
	
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
	
	private void initPager() {
		sortColumn = 0;
		
		// 从session中读取page对象
		Map session = ActionContext.getContext().getSession();
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
	 * @author xuhan
	 */
	private Pager fetchPager(boolean update) {
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		// 判断page类是否存在
		if (!update && pager != null) {
			return pager;
		} else {
//			initPager(simpleSearchCondition());
			initPager();
			return (Pager) ActionContext.getContext().getSession().get(pageName());
		}
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}
	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	public List getLaData() {
		return laData;
	}
	public void setLaData(List laData) {
		this.laData = laData;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartPageNumber() {
		return startPageNumber;
	}
	public void setStartPageNumber(int startPageNumber) {
		this.startPageNumber = startPageNumber;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageBackNumber() {
		return pageBackNumber;
	}
	public void setPageBackNumber(int pageBackNumber) {
		this.pageBackNumber = pageBackNumber;
	}
	public int getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}

	public int getSortColumnLabel() {
		return sortColumnLabel;
	}
	public void setSortColumnLabel(int sortColumnLabel) {
		this.sortColumnLabel = sortColumnLabel;
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
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.loginer = (LoginInfo) request.getSession().getAttribute(GlobalInfo.LOGINER);
		this.request = request;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
