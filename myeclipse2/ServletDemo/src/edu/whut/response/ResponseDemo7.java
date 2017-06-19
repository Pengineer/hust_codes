package edu.whut.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * response实现请求重定向
 */
public class ResponseDemo7 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.sendRedirect("/ServletDemo/index.jsp");
		return;//别掉了，重定向和请求转发都要
		
		/*sendRedirect实现原理：
		 * response.setStatus(302);//302表示请求重定向
		 * response.setHeader("location", "/ServletDemo/index.jsp");
		 */
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
