package edu.hust.web.filter.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * 解决全站的乱码问题(post方式提交)
 * 
 * 在 Spring框架中，就集成了本类实现的这个功能（org.springframework.web.filter.CharacterEncodingFilter），因此我们只需要在
 * web.xml中配置一下过滤器就可以了。（具体例子可以见smdb的web.xml）
 */

public class CharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig = null;
	private String defaultCharset = "UTF-8";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//FilterConfig就是web.xml中关于本filter的所有配置信息
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String charset = filterConfig.getInitParameter("charset");//获取配置文件中的信息
		if(charset == null){
			charset = defaultCharset;
		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
