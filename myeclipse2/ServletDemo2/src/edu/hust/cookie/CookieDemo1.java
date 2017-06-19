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
     1,  һ��Cookieֻ�ܱ�ʶһ����Ϣ�������ٺ���һ����ʶ����Ϣ�����ƣ�NAME��������ֵ��VALUE���� 
	 2,һ��WEBվ����Ը�һ��WEB��������Ͷ��Cookie��һ��WEB�����Ҳ���Դ洢���WEBվ���ṩ��Cookie��
	 3,�����һ��ֻ������300��Cookie��ÿ��վ�������20��Cookie��ÿ��Cookie�Ĵ�С����Ϊ4KB��
	 4,���������һ��cookie�����������͵��������Ĭ�����������һ���Ự�����cookie�����洢�����
	        �����ڴ��У����û��˳������֮�󼴱�ɾ������ϣ�����������cookie�洢�ڴ����ϣ�����Ҫʹ�� 
	   maxAge��������һ������Ϊ��λ��ʱ�䡣�����ʱЧ��Ϊ0�������������ɾ����cookie��
	 5,ע�⣬ɾ��cookieʱ��path����һ�£����򲻻�ɾ����
 */
//�����������ʾ�ϴη��ʵ�ʱ��

public class CookieDemo1 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");//������Ӧ���뷽ʽ
		response.setContentType("text/html;charset=UTF-8");//������Ӧ���ݵ�����
		
		PrintWriter out = response.getWriter();
		out.print("���ϴεķ���ʱ�䣺");
		
		//*****��������ȡ�����������Cookie����һ�η���û�У�
		Cookie[] cookies = request.getCookies();
		for(int i=0 ; cookies!=null&&i<cookies.length ; i++){
			 if(cookies[i].getName().equals("LastAccessTime")){
				 long cookievalue = new Long(cookies[i].getValue());
				 Date time = new Date(cookievalue);
				 out.print(time.toLocaleString());
			 }
		}
		
		out.print("<br/><a href='/ServletDemo2/CookieDemo2'>���ʱ��Cookie</a>");
		
		//*****������Ϊ����������µ�Cookie,�����͸�����������·���ʱ��
		Cookie cookie = new Cookie("LastAccessTime",System.currentTimeMillis()+"");
	    cookie.setMaxAge(1*30*24*3600);//����Cookie����Чʱ�䣬Ĭ���������������ʱ�䣨�رգ���ͬ
		cookie.setPath("/ServletDemo2");//Ĭ��Ҳ�Ǳ�Servlet��ֻҪ���ʱ�Servlet���ͻᴴ��Cookie
		response.addCookie(cookie);//�׵�
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
