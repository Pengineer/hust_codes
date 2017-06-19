package edu.hust.web.filter.example;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * ���ȫվ����������(post��ʽ�ύ)
 * 
 * �� Spring����У��ͼ����˱���ʵ�ֵ�������ܣ�org.springframework.web.filter.CharacterEncodingFilter�����������ֻ��Ҫ��
 * web.xml������һ�¹������Ϳ����ˡ����������ӿ��Լ�smdb��web.xml��
 */

public class CharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig = null;
	private String defaultCharset = "UTF-8";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//FilterConfig����web.xml�й��ڱ�filter������������Ϣ
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String charset = filterConfig.getInitParameter("charset");//��ȡ�����ļ��е���Ϣ
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
