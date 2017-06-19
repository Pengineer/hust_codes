package org.csdc.tool;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SpringBean {

	/**
	 * 获取spring中bean的实例
	 * @param beanName
	 * @param sc
	 * @return
	 */
	public static Object getBean (String beanName, ServletContext sc) {
		return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(beanName);
	}
	
	/**
	 * 获取spring中bean的实例
	 * @param beanName
	 * @param sc
	 * @return
	 */
	public static Object getBean (String beanName) {
		return getBean(beanName, ApplicationContainer.sc);
	}
	
}
