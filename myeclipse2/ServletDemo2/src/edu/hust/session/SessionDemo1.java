package edu.hust.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��������ʷ�������ִ�иþ�ʱ���ᴴ��һ��Session����
		HttpSession session = request.getSession();
		//ÿһ��Session����һ��JSessionID�ţ��Դ��໥���֣����ɷ�����ͨ��Cookie���͸��û���Ĭ�ϵ�cookieû������setMaxAge�����Ҫ����Ĭ�ϻ��͵�Cookie��
		String sessionid = session.getId();
		Cookie cookie = new Cookie("JSESSIONID",sessionid);
		cookie.setPath("/ServletDemo2");
		cookie.setMaxAge(30*60);
		response.addCookie(cookie);//*********�׵�**********
		session.setAttribute("name", "ϴ�»�");		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
