package edu.whut.url;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 关于地址的书写
 * 原则：先写一个'/'，然后看地址是给谁用的，如果地址是给服务器用的，'/'就代表当前web应用；如果地址
 *       是给浏览器用的，'/'就代表网站。
 * 
 * */

public class URLDemo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//1，请求转发：显然在服务器端发生的
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
		//2,重定向：针对浏览器请求的
		response.sendRedirect("/ServletDemo/index.jsp");
		
		//3,服务器获取资源地址，给服务器用的
		this.getServletContext().getRealPath("/index.jsp");
		
		//4,服务器获取资源流对象，给服务器用的
		this.getServletContext().getResourceAsStream("/download/1.jpg");
		
		//5,
		/*
		 <a href="/ServletDemo/index.jsp">超链接</a>          给浏览器点击跳转用的
		 
		 <form action="/ServletDemo/index.jsp"></form>        给浏览器点击跳转用的  
		 
		 c:\\    硬盘上的资源用另一种斜杠
		 */
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
