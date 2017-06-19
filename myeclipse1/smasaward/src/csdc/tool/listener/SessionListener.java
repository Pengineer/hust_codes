package csdc.tool.listener;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import csdc.bean.Log;
import csdc.dao.HibernateBaseDao;
import csdc.tool.LogProperty;
import csdc.tool.SpringBean;

@SuppressWarnings("unchecked")
public class SessionListener implements HttpSessionListener {

	/* 监听session创建 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// 在application范围由一个HashSet集保存所有的session
		HashSet sessions = (HashSet) application.getAttribute("sessions");
		if (sessions == null) {
			sessions = new HashSet();
			application.setAttribute("sessions", sessions);
		}

		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
		
		//System.out.println("创建seesion, 总连接数：" + sessions.size());
	}

	/* 监听session销毁 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet) application.getAttribute("sessions");

		// 添加日志
		try {

			HibernateBaseDao dao = (HibernateBaseDao) SpringBean.getBean("hibernateBaseDao");
			
			Log loginLog = (Log) session.getAttribute("loginLog");// 登录日志
			List<Log> myLog = (List<Log>) session.getAttribute("myLog");// 剩余的操作日志
			
			if (loginLog != null) {// 如果登录日志存在，则记录剩余的操作日志和退出日志
				if (myLog != null) {// 如果剩余的操作日志不为空，则将其记录
					for (Log log : myLog) {
						dao.add(log);
					}
				}				
				// 记录退出日志，日志的账号、IP等从登录日志中获取
				Log logoutLog = new Log();
				logoutLog.setUser(loginLog.getUser());
				logoutLog.setUsername(loginLog.getUsername());
				logoutLog.setDate(new Date());
				logoutLog.setIp(loginLog.getIp());
				logoutLog.setEventCode(LogProperty.LOGOUT);
				logoutLog.setEventDescription("退出系统");
				logoutLog.setIsStatistic(1);
				dao.add(logoutLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 销毁的session均从HashSet集中移除
		if (sessions != null) {
			sessions.remove(session);
			//System.out.println("销毁seesion, 总连接数：" + sessions.size());
		}
	}
}