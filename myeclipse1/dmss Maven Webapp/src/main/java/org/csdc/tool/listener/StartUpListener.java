package org.csdc.tool.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.csdc.tool.PDFConverter;

/**
 * Servlet上下文监听器
 * @author jintf
 * @date 2014-6-16
 */
public class StartUpListener implements ServletContextListener{
	
	/**
	 * 上下文初始化
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			PDFConverter.startService();	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 上下文销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		PDFConverter.stopService();
		
	}

}
