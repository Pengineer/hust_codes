package org.csdc.tool.listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.io.FileUtils;
import org.csdc.bean.Application;
import org.csdc.tool.SessionContext;
import org.csdc.tool.SpringBean;




/**
 * 会话监听器
 * @author jintf
 * @date 2014-6-16
 */
public class SessionListener {
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
		Application application = (Application) SpringBean.getBean("application");
		//清除临时文件
		try {
			File dir = new File(application.getTempPath() + "/" + session.getId());
			FileUtils.deleteDirectory(dir);			
			dir = new File(application.getTempPath() + "/" + session.getId().replaceAll("\\W+", ""));
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		myc.DelSession(session);
	}
	
}
