package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * 关于过滤器的创建与销毁：web工程布署到服务器时，创建filter；每次访问时，首先会判断是否需要过滤（web.xml中配置），若需要，则执行doFilter()方法；
 *                 当web工程从服务器消失时（比如，remove deploy时，一般是服务器关闭时），filter被销毁。
 *                 
 * 补充：在之前的tomcat版本中，好像是当服务器启动时，就会自动加载webapps下的工程，也就是说，在服务器启动时，就会创建有filter的工程的filter。
 */
public class FilterDemo3 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("创建filter。。。");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("销毁filter。。。");
	}

}
