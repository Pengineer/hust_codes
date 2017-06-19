package org.csdc.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.csdc.bean.LoginInfo;
import org.csdc.bean.GlobalInfo;

@SuppressWarnings("unchecked")
public class SessionContext {
	private static SessionContext instance;
	private HashMap mymap;

	private SessionContext() {
		mymap = new HashMap();
	}

	public static SessionContext getInstance() {
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

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mymap.get(session_id);
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
		Iterator iter = mymap.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    Object val = entry.getValue();
		    if (val != null) {
		    	HttpSession session = (HttpSession) val;
		    	Object o = session.getAttribute(GlobalInfo.loginer);
		    	if (o != null) {
		    		LoginInfo loginInfo = (LoginInfo) o;
		    		if (loginInfo.getAccount().getName().equals(username)) {
		    			number++;
		    		}
		    	}
		    }
		}
		
		return number;
	}
	
}