package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * filterһ����web.xml�ļ��в��𣬾ͻ����<url-pattern>��ǩ��ָ����վ��������
 * 
 * filter�����ֵ���Ӧ�ã�
 * 1��������filter�и������������Ƿ����chain.doFilter(request,response)���������Ƿ���Ŀ����Դִ�У�
 * 2������Ŀ����Դִ��֮ǰ�����Զ�request/response��Ԥ��������Ŀ����Դִ�У�
 * 3����Ŀ����Դִ�к󣬿��Բ���Ŀ����Դ��ִ�н�����Ӷ�ʵ��һЩ����Ĺ��ܡ�
 */

public class FilterDemo1 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//������վ���ڷ������˶�response��Ԥ����ʹ�ñ������µ�Servlet������������
		/*response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		*/
		System.out.println("������1����ǰ!");
		chain.doFilter(request, response);  //��Ŀ����Դִ�У�����
		System.out.println("������1���к�!"); 
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
