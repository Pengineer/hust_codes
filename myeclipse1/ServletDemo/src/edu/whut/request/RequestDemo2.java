package edu.whut.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 请求转发，使用request域对象把数据带给转发资源；
 *
 * 请求转发特点：1，客户端只发一次请求         2，浏览器地址栏没变化（在某些跳转的时候，比如要提示用户已跳转到首页时，就要求地址栏变化，这是就只能用重定向sendRedirect，而不能用请求转发forward）
 * 
 * 请求转发的应用：MVC设计模式中
 *     M：model（JavaBean）      V：View（jsp）     C：controller（Servlet）
 *     浏览器向Servlet发送请求，Servlet收到请求产生数据并将数据交给Javabean进行封装，JavaBean将封装的数据通过转发技术交给jsp，由jsp输出给浏览器显示
 */
public class RequestDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test1(request,response);
	}
	
	public void test1(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{	
		String data = "abc";
		request.setAttribute("data", data);//将数据封装到request域中（不是通过this.getServletContext.setAttribute()）
		request.getRequestDispatcher("/rqd2.jsp").forward(request, response);
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
