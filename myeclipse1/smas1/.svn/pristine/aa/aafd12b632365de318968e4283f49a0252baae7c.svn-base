package csdc.action.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Log;
import csdc.service.ILogService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.LogInfo;

/**
 * 日志管理模块，实现的功能包括：删除、查看。
 * @author 龚凡
 * @version 2011.04.11
 */
@SuppressWarnings("unchecked")
public class LogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private ILogService logService;// 日志管理接口
	private Log log;// 日志对象
	// type复用，区分是按账号级别统计还是账号名称
	private String keyword1, keyword2;// 高级检索关键字
	private Date startDate, endDate;// 高级检索accountType
	private int logType;// -1: 所有账号类型; 0: 指定名称时的单个账号;  1--10 :代表指定账号类型
	private String username;// 账号名称
	private Map map;// 显示统计信息的对象
	private static final String HQL = "select l.id, l.date, l.username, user.id, l.ip, l.eventDescription from Log l left join l.user user where 1=1 ";
	private static final String[] COLUMN = {
			"l.date desc",
			"l.username, l.date desc",
			"l.ip, l.date desc",
			"l.eventDescription, l.date desc",
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "logPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "l.id";// 上下条查看时用于查找缓存的字段

	public String pageName() {
		return LogAction.PAGE_NAME;
	}
	public String[] column() {
		return LogAction.COLUMN;
	}
	public String HQL() {
		return HQL;
	}
	public String dateFormat() {
		return LogAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return LogAction.PAGE_BUFFER_ID;
	}

	/**
	 * 删除日志
	 */
	@Transactional
	public String delete() {
		logService.deleteLog(entityIds);// 删除日志
		
		// 更新日志页面对象
//		if (pageNumber > 0) {// 如果指定了页码，则按页码更新
//			backToList(PAGE_NAME, pageNumber, null);
//		} else {// 如果未指定页码，则按第一个ID号更新
//			backToList(PAGE_NAME, -1, entityIds.get(0));
//		}
		return SUCCESS;
	}

	/**
	 * 删除校验
	 */
	public String validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 日志ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, LogInfo.ERROR_DELETE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 进入查看
	 */
	public String toView() {
//		if(pageNumber>0){
//			backToList(pageName(),pageNumber);
//		}
		return SUCCESS;
	}

	/**
	 * 进入查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 日志ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, LogInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看详情
	 */
	public String view() {
		log = logService.viewLog(entityId);// 查询日志
		if (log == null) {// 日志不存在，返回错误信息
			jsonMap.put(GlobalInfo.ERROR_INFO, LogInfo.ERROR_LOG_NULL);
			return INPUT;
		} else {// 日志存在，存入jsonMap，并更新日志页面对象
			jsonMap.put("log", log);
			if (log.getUser() != null) {// 账号存在，则将账号ID存入jsonMap
				jsonMap.put("userId", log.getUser().getId());
			}
			if (log.getRequest() != null) {
				JSONObject request = JSONObject.fromObject(log.getRequest());
				jsonMap.put("request", request);
				if(request.get("parameters") != null) {
					jsonMap.put("parameters", request.get("parameters").toString());
				} else {
					jsonMap.put("parameters", null);
				}
			} else {
				jsonMap.put("request", null);
			}
			if (log.getResponse() != null) {
				JSONObject response = JSONObject.fromObject(log.getResponse());
				jsonMap.put("response", response);
				if(response.get("locale") != null) {
					jsonMap.put("locale", response.get("locale").toString());
				} else {
					jsonMap.put("locale", null);
				}
			} else {
				jsonMap.put("response", null);
			}
			if (log.getData() != null) {
				JSONObject data = JSONObject.fromObject(log.getData());
				jsonMap.put("data", data);
			} else {
				jsonMap.put("data", null);
			}
			return SUCCESS;
		}
	}

	/**
	 * 查看校验
	 */
	public String validateView() {
		if (entityId == null || entityId.isEmpty()) {// 日志ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, LogInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 处理初级检索条件，拼装查询语句。
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		
		// 处理检索参数
		Map map = new HashMap();
		map.put("keyword", "%" + keyword + "%");
		// 拼接检索条件
		StringBuffer hql = new StringBuffer();
		hql.append(HQL);
		hql.append(" and ");
		if (searchType == 1) {// 按账号名称检索
			hql.append(" LOWER(l.username) like :keyword ");
		} else if (searchType == 2) {// 按事件描述检索
			hql.append(" LOWER(l.eventDescription) like :keyword ");
		} else {// 按上述字段检索
			hql.append(" (LOWER(l.username) like :keyword or LOWER(l.eventDescription) like :keyword) ");
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
		// 调用初级检索功能
//		this.simpleSearch(hql, map, ORDER_BY, 0, 0, PAGE_NAME);
//		return SUCCESS;
	}

	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (keyword1 != null && !keyword1.isEmpty()) {// 按事件描述检索
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(l.eventDescription) like :keyword1 ");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {// 按账号名称检索
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(l.accountName) like :keyword2 ");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (startDate != null) {// 设置日志检索开始时间
			hql.append(" and l.date > :startDate");
			map.put("startDate", startDate);
		}
		if (endDate != null) {// 设置日志检索结束时间
			hql.append(" and l.date < :endDate");
			map.put("endDate", endDate);
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
//		this.advSearch(hql, map, ORDER_BY, 0, 0, PAGE_NAME);// 调用高级检索功能
//		return SUCCESS;
	}
	

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if (null != keyword1 && !keyword1.isEmpty()) {
			searchQuery.put("keyword1", keyword1);
		}
		if (null != keyword2 && !keyword2.isEmpty()) {
			searchQuery.put("keyword2", keyword2);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != startDate) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (null != endDate) {
			searchQuery.put("endDate", df.format(endDate));
		}
	}
		
	public Log getLog() {
		return log;
	}
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Map getMap() {
		return map;
	}

}
