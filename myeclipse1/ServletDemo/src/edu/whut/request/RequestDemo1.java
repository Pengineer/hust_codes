package edu.whut.request;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//test1(request);
		//test2(request);
		test3(request);
	}
	
	//���õ�һЩ����
	public void test1(HttpServletRequest request){
		//��ȡURI��ַ��/ServletDemo/RequestDemo1
		System.out.println(request.getRequestURI());
		//��ȡURL��ַ��http://localhost:8080/ServletDemo/RequestDemo1
		System.out.println(request.getRequestURL());
		//�����������еĲ������֣�http://localhost:8080/ServletDemo/RequestDemo1?name=xxx�õ�name=xxx
		System.out.println(request.getQueryString());
		//��ȡ��������Ŀͻ���IP��ַ
		System.out.println(request.getRemoteAddr());
		//��ȡ��������Ŀͻ�������������		
		System.out.println(request.getRemoteHost());
		//��ȡ��������Ŀͻ���ʹ�õ�����˿ں�
		System.out.println(request.getRemotePort());
		//��ȡ����ʽ
		System.out.println(request.getMethod());
	}
	
	/**��ȡ�������ݺ�����ͷ*/
	public void test2(HttpServletRequest request){
		//��ȡ��������ͷ�ĵ���ֵ
		String code = request.getHeader("Accept-Encoding");
		System.out.println("Accept-Encoding="+code);
		//��ȡ��������ͷ�Ķ������
		Enumeration codes = request.getHeaders("Accept-Encoding");
		while(codes.hasMoreElements())
		{
			String code1 = (String) codes.nextElement();
			System.out.println(code1);
		}
		//��ȡ��������ͷ�����ƺ�ֵ
		Enumeration headers = request.getHeaderNames();
		while(headers.hasMoreElements())
		{
			String name = (String) headers.nextElement();
			String value = request.getHeader(name);
			System.out.println(name+"="+value);			
		}
	}
	
	//�ͻ�������������ݲ������ݣ����ַ�ʽ��1��������+������        2��������ע��һ���ȡ��������ʱ�������ȼ���ʹ��
	public void test3(HttpServletRequest request){
		//��ȡһ����������
		String username = request.getParameter("username");
		if(username!=null && !username.trim().equals("")){
			System.out.println(username);
		}
				
		//��ȡ���еĲ�������
		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements())
		{
			String name = (String) params.nextElement();
			String value = request.getParameter(name);//������ж����ͬ��name��Ӧ��ͬ��value����Ҫʹ��request.getParameterValues()����������һ�����value��String����
			System.out.println(name+"="+value);
		}
		
		//ͨ��map��������ȡ���ݣ��ڿ�����õķǳ�֮�ࣩ
		
		//��Ҫ��ȡ�ϴ����ļ�����ʱ��ʹ��inputStream
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
