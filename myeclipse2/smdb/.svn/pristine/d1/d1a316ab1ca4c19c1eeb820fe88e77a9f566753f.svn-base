package csdc.tool.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import csdc.action.SwfuploadAction;
import csdc.bean.Log;
import csdc.dao.HibernateBaseDao;
import csdc.tool.LogProperty;
import csdc.tool.SessionContext;
import csdc.tool.SpringBean;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SessionListener implements HttpSessionListener {
	@SuppressWarnings("rawtypes")
	public static Map userMap = new HashMap();
	private SessionContext myc = SessionContext.getInstance();

	/* 监听session创建 */
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		myc.AddSession(session);
	}

	/* 监听session销毁 */
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();

		ServletContext sc = session.getServletContext();

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
				logoutLog.setAccount(loginLog.getAccount());
				logoutLog.setAccountName(loginLog.getAccountName());
				logoutLog.setDate(new Date());
				logoutLog.setIp(loginLog.getIp());
				logoutLog.setEventCode(LogProperty.LOGOUT);
				logoutLog.setEventDscription("退出系统");
				logoutLog.setIsStatistic(1);
				dao.add(logoutLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//清除临时文件
		try {
			File dir = new File(sc.getRealPath(sc.getAttribute("tempUploadPath") + "/" + session.getId()));
			FileUtils.deleteDirectory(dir);
			
			dir = new File(sc.getRealPath(sc.getAttribute("tempUploadPath") + "/" + session.getId().replaceAll("\\W+", "")));
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//清除文件上传验证码
		SwfuploadAction.removeKeySessionPair((String) session.getAttribute("uploadKey"));
		
		myc.DelSession(session);
	}

}
