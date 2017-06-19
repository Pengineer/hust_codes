package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * filter一旦在web.xml文件中部署，就会根据<url-pattern>标签对指定网站进行拦截
 * 
 * filter的三种典型应用：
 * 1，可以在filter中根据条件决定是否调用chain.doFilter(request,response)方法，即是否让目标资源执行；
 * 2，在让目标资源执行之前，可以对request/response作预处理，再让目标资源执行；
 * 3，在目标资源执行后，可以补充目标资源的执行结果，从而实现一些特殊的功能。
 */

public class FilterDemo1 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//拦截网站后，在服务器端对response做预处理，使得本工程下的Servlet输出乱码的问题
		/*response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		*/
		System.out.println("过滤器1放行前!");
		chain.doFilter(request, response);  //让目标资源执行，放行
		System.out.println("过滤器1放行后!"); 
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
