package edu.hust.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//通过encodeURL()方法将Session添加到URL地址的尾部。经测试发现，搜狗浏览器禁止cookie的功能形同虚设，因为 cookie被禁止后，通过封装URL，我们在网页源码中是可以看到SessionID的，而搜狗浏览器看不到，IE可以看到，说明在搜狗浏览器中SessionID还是通过cookie传递给了浏览器，而没有添加到URL地址末尾
public class SessionDemo1_2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		request.getSession();//创建Session
		
		//通过URL回送SessionID，以防止用户由于禁止cookie而带来的麻烦，encodeURL方法会自动将SessionID添加在封装的URL后面
		String url1 = response.encodeURL("/ServletDemo2/SessionDemo1"); 
		String url2 = response.encodeURL("/ServletDemo2/SessionDemo2");
		
		out.print("<a href='"+url1+"'>购买</a>");
		out.print("   <a href='"+url2+"'>结账</a>");
		
	/*	out.print("<a href='/ServletDemo2/SessionDemo1'>购买</a>");
		out.print("  <a href='/ServletDemo2/SessionDemo2'>结账</a>");*/
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
