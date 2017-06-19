package csdc.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;


@SuppressWarnings("unchecked")
public class SessionContext {
	private static SessionContext instance;
	private HashMap<String, HttpSession> mymap;

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

	public synchronized HttpSession getSession(String sessionId) {
		return (HttpSession) (sessionId == null ? null : mymap.get(sessionId));
	}

	public synchronized int getTotalSessionNumber() {
		return mymap.size();
	}
	
	public synchronized Set<String> getSessionIdSet() {
		return mymap.keySet();
	}
	

	
}