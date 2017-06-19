package csdc.tool.listener;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.io.FileUtils;

import csdc.tool.SessionContext;




public class SessionListener implements HttpSessionListener {
	private static Object lock = new boolean[0];
	private SessionContext myc = SessionContext.getInstance();

	
	/* 监听session创建 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		myc.AddSession(session);
	}

	/* 监听session销毁 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();

		ServletContext sc = session.getServletContext();

		//清除临时文件
		try {
			File dir = new File(sc.getRealPath(sc.getAttribute("tempUploadPath") + "/" + session.getId()));
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}