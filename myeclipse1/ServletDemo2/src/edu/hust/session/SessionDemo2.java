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
		
		HttpSession session = request.getSession();//������������һ�η��ʵ��Ǳ�Servlet���ͻᴴ��һ��session��û��name���ԣ���ôname=null������ȷ�����SessionDemo1���ٷ��ʱ�Servlet����ô������ͻ����session���������ٴ���session����ʱsession����name����ֵ��
		String name = (String) session.getAttribute("name");
		out.print("�����������Ʒ�ǣ�"+name);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
