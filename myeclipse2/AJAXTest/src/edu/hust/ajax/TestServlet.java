package edu.hust.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* text/html：即服务器返回的是普通文本，而不是xml文件，用responseText对象返回  */

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		response.getWriter().print("�����Է�������-get");
		System.out.println("...........");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		PrintWriter out = response.getWriter();
		if("sa".equals(username)){
			out.print("<font color=red>�û����Ѵ���</font>");
		}else{
			out.print("<font color=green>�û������</font>");
		}
		System.out.println("...........");
	}

}
