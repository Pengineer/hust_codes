package eoas.tool.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eoas.service.IBaseService;
import eoas.tool.ApplicationContainer;



public class StartUpListener implements ServletContextListener {

	/* 监听服务器启动 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		//System.out.println("系统启动");

		ServletContext sc = event.getServletContext();
		ApplicationContainer.sc = sc;
/*		IBaseService baseservice = (IBaseService) SpringBean.getBean("baseService", sc);
*/

		// 个人信息上传照片路径
/*		String UserPictureUploadPath = ((Sysconfig) baseservice.query("select sysconfig from Sysconfig sysconfig where sysconfig = 'sysconfig00007'").get(0)).getValue();*/
//		sc.setAttribute("UserPictureUploadPath", UserPictureUploadPath);

		
		// 临时上传目录
		sc.setAttribute("tempUploadPath", "/temp");
		sc.setAttribute("systemVersion", "10077");//系统版本号
	}

	/* 监听服务器关闭 */
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("系统关闭");
	}
}
