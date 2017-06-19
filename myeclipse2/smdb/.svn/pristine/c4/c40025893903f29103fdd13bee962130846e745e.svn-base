package csdc.tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

public class SessionContext {
	
	
	private static SessionContext instance;
	private Map<String, HttpSession> mymap;

	private SessionContext() {
		mymap = new ConcurrentHashMap();
	}
	
	/**
	 * 清除无用的session，释放内存
	 */
	public static class ClearIdleSessionsJob extends QuartzJobBean {
		private static final long ONE_MINUTE = 60 * 1000;
		
		@Override
		protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			for (HttpSession httpSession : SessionContext.getInstance().getSessions()) {
				long freezeLength = new Date().getTime() - httpSession.getLastAccessedTime();
				if (httpSession.getAttribute("client_ip") == null) {
					//IP为空的
					httpSession.invalidate();
				} else if (httpSession.getAttribute(GlobalInfo.LOGINER) == null) {
					if (freezeLength > 3 * ONE_MINUTE) {
						//未登录且发呆时间超过3分钟的
						httpSession.invalidate();
					}
				} else if (freezeLength > 60 * ONE_MINUTE) {
					//已登录且发呆时间超过60分钟的
					httpSession.invalidate();
				}
			}
		}
	}
	
	public synchronized static SessionContext getInstance() {
		if (instance == null) {
			instance = new SessionContext();
		}
		return instance;
	}

	public synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	public synchronized void DelSession(String sessionId) {
		mymap.remove(sessionId);
	}

	public synchronized HttpSession getSession(String sessionId) {
		return sessionId == null ? null : mymap.get(sessionId);
	}
	
	public synchronized List<HttpSession> getSessions() {
		return new ArrayList(mymap.values());
	}

	/**
	 * 获取指定账号当前活动的session数
	 * @param username 账号
	 * @return number 该账号当前session数
	 */
	public synchronized int getSessionNumber(String username) {
		int number = 0;// 计数
		
		/**
		 * 遍历mymap对象，统计登录账号为username的session个数
		 */
		for (HttpSession session : mymap.values()) {
			if (session != null) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(GlobalInfo.LOGINER);
				if (loginInfo != null && loginInfo.getPassport() != null && loginInfo.getPassport().getName().equals(username)) {
					number++;
				}
			}
		}
		return number;
	}
}