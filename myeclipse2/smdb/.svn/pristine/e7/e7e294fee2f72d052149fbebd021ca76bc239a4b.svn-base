package csdc.action.system.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.SessionContext;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.Pager;
import csdc.tool.info.GlobalInfo;

public class VisitorAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Map jsonMap = new HashMap();// json对象容器
	private List laData;// 处理后的列表数据
	private int totalRows;// 总记录数
	private int pageSize;// 页面大小
	private int startPageNumber;// 当前页所在区段的起始页
	private Integer pageNumber;// 页码
	private int pageBackNumber;// 每次查询的页数
	private int sortColumn;// 排序列标号
	private int sortColumnLabel;// 降序还是升序0降序，1升序
	private int searchType;// 初级检索类别
	private String keyword;// 初级检索关键字
	protected int update;//是否强制初始化pager
	private int flag;//1:指定的访客提出；2：全部踢出
	
	private List<String> entityIds;// 多个实体ID
	
	/**
	 * 让指定session的下线
	 * @return
	 */
	@Transactional
	public String evict(){
		SessionContext sc = SessionContext.getInstance();
		if (flag == 1) {
			for (String entityId : entityIds) {
				//不能让自己下线
				if (!ServletActionContext.getRequest().getSession().getId().equals(entityId)) {
					sc.getSession(entityId).invalidate();
				}
			}
		} else if (flag == 2) {
			List<HttpSession> sessions = sc.getSessions();
			for (HttpSession session : sessions) {
				if (!ServletActionContext.getRequest().getSession().getId().equals(session.getId())) {
					session.invalidate();
				}
			}
		} 
		return SUCCESS;
	}

	public String toList() {
		fetchPager(update != 0);
		return SUCCESS;
	}
	
	public String simpleSearch() {
		initPager();
		Pager pager = (Pager) ActionContext.getContext().getSession().get(pageName());
		pager.getSearchQuery().put("searchType", searchType);
		pager.getSearchQuery().put("keyword", keyword);
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
		SessionContext sc = SessionContext.getInstance();
		List<HttpSession> sessions = sc.getSessions();

		sortColumnLabel = pager.getSortDirection();
		sortColumn = pager.getSortColumn();
		
		//Step1: 查询列表数据
		startPageNumber = 1;
		laData = new ArrayList<Object[]>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (HttpSession session : sessions) {
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(GlobalInfo.LOGINER);
			String accountName = loginInfo == null || loginInfo.getPassport().getName() == null ? "" : loginInfo.getPassport().getName();
			String ip = session.getAttribute("client_ip") == null ? "" : (String) session.getAttribute("client_ip");
			Long activeLength = (session.getLastAccessedTime() - session.getCreationTime()) / 1000;
			Long freezeLength = (new Date().getTime() - session.getLastAccessedTime()) / 1000;
			Map searchMap = pager.getSearchQuery();
			searchType = (Integer) searchMap.get("searchType");
			keyword = (String) searchMap.get("keyword");
			if (searchType == 0) {
				if (!accountName.contains(keyword)) {
					continue;
				}
			} else if (searchType == 1) {
				if (!ip.contains(keyword)) {
					continue;
				}
			} else{
				if (!accountName.contains(keyword) && !ip.contains(keyword)) {
					continue;
				}
			}
		/*	if (pager.getSearchType() == 0) {
				if (!accountName.contains(pager.getKeyword())) {
					continue;
				}
			} else if (pager.getSearchType() == 1) {
				if (!ip.contains(pager.getKeyword())) {
					continue;
				}
			}*/
			laData.add(new Object[] {
				session.getId(),
				accountName,	//账号名
				ip,	//IP
				sdf.format(new Date(session.getCreationTime())),	//到访时刻
				activeLength / 60 + "分" + activeLength % 60 + "秒",	//活动时长
				freezeLength / 60 + "分" + freezeLength % 60 + "秒",	//发呆时长
				activeLength,
				freezeLength
			});
		}
		
		Collections.sort(laData, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				Comparable v1 = (Comparable) o1[sortColumn];
				Comparable v2 = (Comparable) o2[sortColumn];
				return sortColumnLabel == 1 ? v1.compareTo(v2) : v2.compareTo(v1);
			}
		});

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
		sortColumn = 7;
		
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
			initPager();
			return (Pager) ActionContext.getContext().getSession().get(pageName());
		}
	}
	

	public String pageName() {
		return "visitorMonitorPage";
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
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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
	public List<String> getEntityIds() {
		return entityIds;
	}
	public void setEntityIds(List<String> entityIds) {
		this.entityIds = entityIds;
	}
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
}
