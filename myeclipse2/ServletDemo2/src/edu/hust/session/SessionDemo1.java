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
		//浏览器访问服务器，执行该句时，会创建一个Session对象
		HttpSession session = request.getSession();
		//每一个Session都有一个JSessionID号，以此相互区分，并由服务器通过Cookie回送给用户（默认的cookie没有设置setMaxAge，因此要覆盖默认回送的Cookie）
		String sessionid = session.getId();
		Cookie cookie = new Cookie("JSESSIONID",sessionid);
		cookie.setPath("/ServletDemo2");
		cookie.setMaxAge(30*60);
		response.addCookie(cookie);//*********易掉**********
		session.setAttribute("name", "洗衣机");		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
