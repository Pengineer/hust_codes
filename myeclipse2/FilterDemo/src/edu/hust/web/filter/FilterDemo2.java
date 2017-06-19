package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * FilterChain:һ��web���̿��ܴ��ڶ��filter�����е�filter���һ��FilterChain����������web.xml�ļ��У���������filter���нϸ�
 *             �����ȼ�������ǰ���filterִ�����غ��������У������filter�ǲ���ִ�����صġ�
 *             
 *             ���FilterDemo1��FilterDemo2��ServletDemo1���۲�ִ��˳�򣬰����������ڲ�����ǰ���ִ��˳��
 */

public class FilterDemo2 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("������2����ǰ!!");
		chain.doFilter(request, response);
		System.out.println("������2���к�!!");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}
