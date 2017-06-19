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
	
	//常用的一些方法
	public void test1(HttpServletRequest request){
		//获取URI地址：/ServletDemo/RequestDemo1
		System.out.println(request.getRequestURI());
		//获取URL地址：http://localhost:8080/ServletDemo/RequestDemo1
		System.out.println(request.getRequestURL());
		//返回请求行中的参数部分：http://localhost:8080/ServletDemo/RequestDemo1?name=xxx得到name=xxx
		System.out.println(request.getQueryString());
		//获取发送请求的客户端IP地址
		System.out.println(request.getRemoteAddr());
		//获取发送请求的客户端完整主机名		
		System.out.println(request.getRemoteHost());
		//获取发送请求的客户端使用的网络端口号
		System.out.println(request.getRemotePort());
		//获取请求方式
		System.out.println(request.getMethod());
	}
	
	/**获取请求数据和请求头*/
	public void test2(HttpServletRequest request){
		//获取单个请求头的单个值
		String code = request.getHeader("Accept-Encoding");
		System.out.println("Accept-Encoding="+code);
		//获取单个请求头的多个数据
		Enumeration codes = request.getHeaders("Accept-Encoding");
		while(codes.hasMoreElements())
		{
			String code1 = (String) codes.nextElement();
			System.out.println(code1);
		}
		//获取所有请求头的名称和值
		Enumeration headers = request.getHeaderNames();
		while(headers.hasMoreElements())
		{
			String name = (String) headers.nextElement();
			String value = request.getHeader(name);
			System.out.println(name+"="+value);			
		}
	}
	
	//客户机向服务器传递参数数据（两种方式：1，超链接+？参数        2，表单），注意一般获取请求数据时，必须先检查后使用
	public void test3(HttpServletRequest request){
		//获取一个参数数据
		String username = request.getParameter("username");
		if(username!=null && !username.trim().equals("")){
			System.out.println(username);
		}
				
		//获取所有的参数数据
		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements())
		{
			String name = (String) params.nextElement();
			String value = request.getParameter(name);//如果有有多个相同的name对应不同的value，就要使用request.getParameterValues()方法，返回一个存放value的String数组
			System.out.println(name+"="+value);
		}
		
		//通过map集合来获取数据（在框架中用的非常之多）
		
		//当要获取上传的文件数据时，使用inputStream
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
