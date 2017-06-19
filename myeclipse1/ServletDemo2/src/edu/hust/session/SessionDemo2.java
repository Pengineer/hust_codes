package edu.hust.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();//如果打开浏览器第一次访问的是本Servlet，就会创建一个session，没有name属性，那么name=null；如果先访问了SessionDemo1，再访问本Servlet，那么浏览器就会带着session过来，不再创建session，这时session是有name属性值的
		String name = (String) session.getAttribute("name");
		out.print("您所购买的商品是："+name);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
