package csdc.action.system.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Log;
import csdc.service.ILogService;
import csdc.tool.Http;
import csdc.tool.bean.AccountType;
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
	private String keyword1, keyword2, keyword3, keyword4, keyword5, keyword6, keyword7;// 高级检索关键字
	private Date startDate, endDate;// 高级检索accountType
	private int logType;// -1: 所有账号类型; 0: 指定名称时的单个账号;  1--10 :代表指定账号类型
	private AccountType accountType;//统计的账号的级别( 系统管理员---学生)
	private String accountName;// 账号名称
	private Map map;// 显示统计信息的对象
	private String ip;
	private static final String HQL = "select l.id, l.date, l.accountName, a.id, l.ip, l.eventDscription, l.accountBelong from Log l left join l.account a where 1=1 ";
	private static final String[] COLUMN = {
			"l.date desc",
			"l.accountName, l.date desc",
			"l.ip, l.date desc",
			"l.eventDscription, l.date desc",
			"l.accountBelong"
	};// 用于拼接的排序列
	private static final String PAGE_NAME = "logPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";// 列表时间格式
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

	public String getIpAddress(){
		if (!session.containsKey("ip") || !ip.equals(session.get("ip"))) {
			Http http = new Http();
			String url = "http://ip.taobao.com/service/getIpInfo.php";
			Map<String, String> params = new HashMap<String, String>();
			params.put("ip", ip);
			String result = http.http(url, params);
			JSONObject jsonresult = new JSONObject().fromObject(result);
			jsonMap.put("code", jsonresult.get("code"));
			if ((Integer) jsonresult.get("code") == 0) {
				String data = jsonresult.getString("data");
				JSONObject jsonData = new JSONObject().fromObject(data);
				jsonMap.put("city", jsonData.get("city"));
			}
		} else {
			jsonMap = (Map) session.get("jsonMap");
		}
		session.put("ip", ip);
		session.put("jsonMap", jsonMap);
		return SUCCESS;
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
			if (log.getAccount() != null) {// 账号存在，则将账号ID存入jsonMap
				jsonMap.put("accountId", log.getAccount().getId());
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
				if(response.get("operationTime") != null) {
					jsonMap.put("operationTime", response.get("operationTime").toString());
				} else {
					jsonMap.put("operationTime", null);
				}
				if(response.get("responseStatus") != null) {
					jsonMap.put("responseStatus", response.get("responseStatus").toString());
				} else {
					jsonMap.put("responseStatus", null);
				}
			} else {
				jsonMap.put("response", null);
			}
			if (log.getData() != null) {
				String data = log.getData();
				jsonMap.put("data", data);
			} else {
				jsonMap.put("data", null);
			}
			if (log.getChangedData() != null) {
				String changedData = log.getChangedData();
				jsonMap.put("changedData", changedData);
			} else {
				jsonMap.put("changedData", null);
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
			hql.append(" LOWER(l.accountName) like :keyword ");
		} else if (searchType == 2) {// 按事件描述检索
			hql.append(" LOWER(l.eventDscription) like :keyword ");
		} else if(searchType == 3){
			hql.append(" LOWER(l.eventCode) like :keyword ");
		} else if(searchType == 4){
			hql.append(" LOWER(l.ip) like :keyword ");
		} else if(searchType == 5){
			hql.append(" LOWER(l.accountBelong) like :keyword ");
		} else {// 按上述字段检索
			hql.append(" (LOWER(l.accountName) like :keyword or LOWER(l.eventDscription) like :keyword) ");
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
			hql.append(" and LOWER(l.eventDscription) like :keyword1 ");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {// 按账号名称检索
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(l.accountName) like :keyword2 ");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {// 按账号名称检索
			keyword3 = keyword3.toLowerCase();
			hql.append(" and LOWER(l.ip) like :keyword3 ");
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {// 按账号名称检索
			keyword4 = keyword4.toLowerCase();
			hql.append(" and LOWER(l.accountBelong) like :keyword4 ");
			map.put("keyword4", "%" + keyword4 + "%");
		}
		if (keyword5 != null && !keyword5.isEmpty()) {// 按账号名称检索
			keyword5 = keyword5.toLowerCase();
			hql.append(" and LOWER(l.eventCode) like :keyword5 ");
			map.put("keyword5", "%" + keyword5 + "%");
		}
		if (keyword5 != null && !keyword5.isEmpty()) {// 按账号名称检索
			keyword5 = keyword5.toLowerCase();
			hql.append(" and LOWER(l.eventCode) like :keyword5 ");
			map.put("keyword5", "%" + keyword5 + "%");
		}
		if (keyword6 != null && !keyword6.isEmpty()) {// 按账号名称检索
			keyword6 = keyword6.toLowerCase();
			hql.append(" and LOWER(l.request) like :keyword6 ");
			map.put("keyword6", "%" + keyword6 + "%");
		}
		if (keyword7 != null && !keyword7.isEmpty()) {// 按账号名称检索
			keyword7 = keyword7.toLowerCase();
			hql.append(" and LOWER(l.response) like :keyword7 ");
			map.put("keyword7", "%" + keyword7 + "%");
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
		if (null != keyword3 && !keyword3.isEmpty()) {
			searchQuery.put("keyword3", keyword3);
		}
		if (null != keyword4 && !keyword4.isEmpty()) {
			searchQuery.put("keyword4", keyword4);
		}
		if (null != keyword5 && !keyword5.isEmpty()) {
			searchQuery.put("keyword5", keyword5);
		}
		if (null != keyword6 && !keyword6.isEmpty()) {
			searchQuery.put("keyword6", keyword6);
		}
		if (null != keyword7 && !keyword7.isEmpty()) {
			searchQuery.put("keyword7", keyword7);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != startDate) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (null != endDate) {
			searchQuery.put("endDate", df.format(endDate));
		}
	}
	

	/**
	 * 进入统计页面，初始化统计参数，加载初始条件下的统计结果
	 */
	public String toStatistic() {
		logType = -1;
		accountName = null;
		startDate = null;
		endDate = null;
		
		// 按初始参数统计一次日志
		map = logService.statistic(startDate, endDate, logType, accountType, accountName);
		return SUCCESS;
	}

	/**
	 * 统计信息
	 */
	@SuppressWarnings("null")
	public String statistic() {
		// 按指定参数统计日志
		AccountType accountType = AccountType.UNDEFINED;
		System.out.println(logType);
		if(logType >= 1 && logType <= 10){
			System.out.println("2 " + accountType.chageType(logType));
			accountType = accountType.chageType(logType);
		}
		map = logService.statistic(startDate, endDate, logType, accountType, accountName);
		return SUCCESS;
	}
	
	/**
	 * 统计条件校验
	 */
	public void validateStatistic() {
		if (logType < -1 || logType > 10) {// 账号类型越界，则置为所有账号
			logType = -1;
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
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}
	public String getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
	}
	public String getKeyword6() {
		return keyword6;
	}
	public void setKeyword6(String keyword6) {
		this.keyword6 = keyword6;
	}
	public String getKeyword7() {
		return keyword7;
	}
	public void setKeyword7(String keyword7) {
		this.keyword7 = keyword7;
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
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Map getMap() {
		return map;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

}
