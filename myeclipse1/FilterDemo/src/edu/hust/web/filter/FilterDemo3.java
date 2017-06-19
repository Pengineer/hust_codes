package edu.hust.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
 * ���ڹ������Ĵ��������٣�web���̲��𵽷�����ʱ������filter��ÿ�η���ʱ�����Ȼ��ж��Ƿ���Ҫ���ˣ�web.xml�����ã�������Ҫ����ִ��doFilter()������
 *                 ��web���̴ӷ�������ʧʱ�����磬remove deployʱ��һ���Ƿ������ر�ʱ����filter�����١�
 *                 
 * ���䣺��֮ǰ��tomcat�汾�У������ǵ�����������ʱ���ͻ��Զ�����webapps�µĹ��̣�Ҳ����˵���ڷ���������ʱ���ͻᴴ����filter�Ĺ��̵�filter��
 */
public class FilterDemo3 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("����filter������");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("����filter������");
	}

}
