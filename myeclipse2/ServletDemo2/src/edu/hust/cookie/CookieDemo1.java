package edu.hust.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  cookie:
     1,  一个Cookie只能标识一种信息，它至少含有一个标识该信息的名称（NAME）和设置值（VALUE）。 
	 2,一个WEB站点可以给一个WEB浏览器发送多个Cookie，一个WEB浏览器也可以存储多个WEB站点提供的Cookie。
	 3,浏览器一般只允许存放300个Cookie，每个站点最多存放20个Cookie，每个Cookie的大小限制为4KB。
	 4,如果创建了一个cookie，并将他发送到浏览器，默认情况下它是一个会话级别的cookie（即存储在浏览
	        器的内存中），用户退出浏览器之后即被删除。若希望浏览器将该cookie存储在磁盘上，则需要使用 
	   maxAge，并给出一个以秒为单位的时间。将最大时效设为0则是命令浏览器删除该cookie。
	 5,注意，删除cookie时，path必须一致，否则不会删除。
 */
//在浏览器中显示上次访问的时间

public class CookieDemo1 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");//设置响应编码方式
		response.setContentType("text/html;charset=UTF-8");//设置响应数据的类型
		
		PrintWriter out = response.getWriter();
		out.print("您上次的访问时间：");
		
		//*****服务器获取浏览器带来的Cookie（第一次访问没有）
		Cookie[] cookies = request.getCookies();
		for(int i=0 ; cookies!=null&&i<cookies.length ; i++){
			 if(cookies[i].getName().equals("LastAccessTime")){
				 long cookievalue = new Long(cookies[i].getValue());
				 Date time = new Date(cookievalue);
				 out.print(time.toLocaleString());
			 }
		}
		
		out.print("<br/><a href='/ServletDemo2/CookieDemo2'>清除时间Cookie</a>");
		
		//*****服务器为浏览器创建新的Cookie,并回送给浏览器，更新访问时间
		Cookie cookie = new Cookie("LastAccessTime",System.currentTimeMillis()+"");
	    cookie.setMaxAge(1*30*24*3600);//设置Cookie的有效时间，默认与浏览器的消亡时间（关闭）相同
		cookie.setPath("/ServletDemo2");//默认也是本Servlet。只要访问本Servlet，就会创建Cookie
		response.addCookie(cookie);//易掉
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
