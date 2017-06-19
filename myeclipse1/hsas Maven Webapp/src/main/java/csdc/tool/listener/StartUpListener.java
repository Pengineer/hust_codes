package csdc.tool.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import csdc.tool.ApplicationContainer;

public class StartUpListener implements ServletContextListener{
	
	/**
	 * 上下文初始化
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {	
		ServletContext sc = event.getServletContext();
		ApplicationContainer.sc = sc;
		sc.setAttribute("queryRows", "200");
		sc.setAttribute("rows", "10");
		sc.setAttribute("FileUploadPath1", "upload/solicitPapers/forum");
		sc.setAttribute("FileUploadPath2", "upload/solicitPapers/topic");
		
	}
	
	/**
	 * 上下文销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {		
	}

}
