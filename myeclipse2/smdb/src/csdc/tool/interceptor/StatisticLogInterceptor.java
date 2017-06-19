package csdc.tool.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import csdc.bean.Account;
import csdc.bean.Log;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.LogProperty;
import csdc.tool.RequestIP;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 统计模块日志拦截器
 */
public class StatisticLogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		String resultCode = invocation.invoke();
		
		// 只记录action的访问情况
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String url = nameSpace.substring(1) + "/" + actionName;
		
		Map<String,String> logInfo = LogProperty.STATISTIC_LOG_CODE_URL_MAP.get(url+session.get("statisticType"));
		
		if (logInfo != null) {
			LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
			Account account = loginer == null ? null : loginer.getAccount();
			
			if (account != null) {// 登录用户操作日志记录
				List<Log> logs = (List<Log>) session.get("myLog");
				
				if (logs == null) {
					logs = new ArrayList<Log>();
					session.put("myLog", logs);
				}
				
				Log log = new Log();
				
				log.setAccount(loginer.getAccount());
				log.setAccountName(loginer.getPassport().getName());
				log.setDate(new Date());
				log.setIp(RequestIP.getRequestIp(ServletActionContext.getRequest()));
				log.setEventCode(logInfo.get("eventCode"));
				log.setEventDscription(logInfo.get("description"));
				log.setIsStatistic(1);
				
				logs.add(log);
				
				// 当达到指定存储时，将日志写入数据库，并清除当前缓存的日志
				if (logs.size() == GlobalInfo.LOG_NUMBER_EACH_TIME) {
					for(Log l : logs) {
						dao.add(l);
					}
					logs.clear();
				}
			}
		}
		return resultCode;
	}

}
