package csdc.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


import csdc.bean.Log;
import csdc.bean.User;
import csdc.bean.common.Visitor;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.LogProperty;
import csdc.tool.RequestIP;
import csdc.tool.info.GlobalInfo;

/**
 * 日志拦截器，根据配置的日志要求，记录用户日志
 * @author 龚凡
 * @version 2011.03.03
 */
public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		String resultCode = invocation.invoke();
		
		// 获取命名空间及action名，并拼成一个简略的URL
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String url = nameSpace.substring(1) + "/" + actionName;
		
		// 获取该URL对应的日志描述信息
		String[] logInfo = LogProperty.LOG_CODE_URL_MAP.get(url);
		
		if (logInfo != null) {// 如果不为空，则进行日志记录
			// 获取当前登录账号
			Visitor visitor = (Visitor) session.get("visitor");
			User user = visitor == null ? null : visitor.getUser();			
			if (user != null) {// 如果当前账号存在，则表示用户处于登录状态，记录日志
				if (url.equals("user/login")) {// 如果是登录日志，单独记录。因为退出不是action，为了记录退出日志，需要复用日志IP及account
					Log loginLog = (Log) session.get("loginLog");
					if (loginLog == null) {// 因为未退出，也可以回首页继续登录，此时不再记录登录日志
						loginLog = addLog(user, logInfo,1);
						session.put("loginLog", loginLog);// 将登录日志存入session备用
						dao.add(loginLog);// 保证登录信息最先记录
					}
				} else {// 其它日志，临时记录在用户的session中，当用户日志达到一定数量时，再一次写入数据库
					List<Log> logs = (List<Log>) session.get("myLog");
					if (logs == null) {// 如果日志缓存对象为空，则new一个
						logs = new ArrayList<Log>();
						session.put("myLog", logs);
					}
					Log log = addLog(user, logInfo, 0);// 创建一个日志对象
					logs.add(log);// 将日志对象添加到缓存列表中
					// 当达到指定存储时，将日志写入数据库，并清除当前缓存的日志
					if (logs.size() >= GlobalInfo.LOG_NUMBER_EACH_TIME) {
						for(Log l : logs) {
							dao.add(l);
						}
						logs.clear();
					}
				}
			} 
		}
		return resultCode;
	}

	/**
	 * 生成日志对象
	 * @param account当前账号对象
	 * @param logInfo日志信息描述
	 * @return log日志对象
	 * @throws Exception 
	 */
	private Log addLog(User user, String[] logInfo ,Integer flag) throws Exception {
		Log log = new Log();
		log.setUser(user);
		log.setUsername(user.getUsername());		
		log.setDate(new Date());
		log.setIp(RequestIP.getRequestIp(ServletActionContext.getRequest()));
		log.setEventCode(logInfo[0]);
		log.setEventDescription(logInfo[1]);
		log.setIsStatistic(1);
		log.setRequest(this.reqString(ServletActionContext.getRequest()));
		log.setResponse(this.respString(ServletActionContext.getResponse()));
		return log;
	}
	
	private String reqString(HttpServletRequest request) throws Exception {
		JSONObject mapObj = new JSONObject();
		mapObj.put("authType", request.getAuthType());
		mapObj.put("characterEncoding", request.getCharacterEncoding());
		mapObj.put("protocol", request.getProtocol());
		mapObj.put("server", request.getServerName() + ":" + request.getServerPort());
		mapObj.put("servletPath", request.getServletPath());
		mapObj.put("requestURI", request.getRequestURI());
		mapObj.put("parameters", this.getParemeters(request));
		return mapObj.toString();
	}
	
	
	private String respString(HttpServletResponse response) {
		JSONObject mapObj = new JSONObject();
		mapObj.put("characterEncoding", response.getCharacterEncoding());
		mapObj.put("contentType", response.getContentType());
		mapObj.put("locale", response.getLocale());
		mapObj.put("bufferSize", response.getBufferSize());
		return mapObj.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getParemeters(HttpServletRequest request) throws Exception {
		Map<String, String[]> map = request.getParameterMap();
		JSONObject jsonOjb = JSONObject.fromObject(map);
		if(map.isEmpty()){
			return jsonOjb.toString();
		}
		JSONObject mapObj = new JSONObject();
		for(String key : map.keySet()){
			mapObj.put(key, request.getParameter(key).toString());
		}
		return jsonOjb.toString();
	}
}
