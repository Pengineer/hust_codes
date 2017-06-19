package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * FilterChain:一个web工程可能存在多个filter，所有的filter组成一个FilterChain（链），在web.xml文件中，先声明的filter具有较高
 *             的优先级；而且前面的filter执行拦截后，若不放行，后面的filter是不会执行拦截的。
 *             
 *             结合FilterDemo1，FilterDemo2，ServletDemo1来观察执行顺序，包括过滤器内部放行前后的执行顺序
 */

public class FilterDemo2 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("过滤器2放行前!!");
		chain.doFilter(request, response);
		System.out.println("过滤器2放行后!!");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}
