package edu.whut.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//服务器获取浏览器提交的表单数据
public class RequestDemo4 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("gender"));
		System.out.println(request.getParameter("city"));
		System.out.println(request.getParameter("description"));
		System.out.println(request.getParameter("id"));
		
		String[] likes = request.getParameterValues("likes");
		for(String like : likes){
			System.out.println(like);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
