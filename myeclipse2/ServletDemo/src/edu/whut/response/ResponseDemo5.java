package edu.whut.response;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**控制浏览器自动刷新网页*/

public class ResponseDemo5 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//test1(response);
		test3(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	//浏览器每隔三秒刷新一次网页：通过设置请求消息头来完成
	public void test1(HttpServletResponse response) throws IOException{
		response.setHeader("refresh", "3");
		
		String num = new Random().nextInt(1000) + "";
		response.getOutputStream().write(num.getBytes());
	}
	
	//实现网页的自动跳转（注册时，经常会看到）
	public void test2(HttpServletResponse response) throws IOException{
		String data = "登陆成功，3秒后自动跳转，如没有，请点击<a href=''><i>这里</i></a>";
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("refresh", "3;url='/ServletDemo/1.html'");
		response.getWriter().write(data);//由于data里面有html语句，因此要用字符流将data以字符串的形式输出
	}
	
	//真正实际开发中使用的自动跳转技术：浏览器数据由jsp发送,参见ServletDemo6.java
	public void test3(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String message = "<meta http-equiv='refresh' content='3;url=/ServletDemo/index.jsp'>登陆成功，3秒后自动跳转，如没有，请点击<a href='/ServletDemo/index.jsp'><i>这里</i></a>";
		this.getServletContext().setAttribute("message", message);
		this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}

}
