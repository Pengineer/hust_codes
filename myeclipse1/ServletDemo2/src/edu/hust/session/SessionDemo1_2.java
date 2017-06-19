package edu.hust.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//ͨ��encodeURL()������Session��ӵ�URL��ַ��β���������Է��֣��ѹ��������ֹcookie�Ĺ�����ͬ���裬��Ϊ cookie����ֹ��ͨ����װURL����������ҳԴ�����ǿ��Կ���SessionID�ģ����ѹ��������������IE���Կ�����˵�����ѹ��������SessionID����ͨ��cookie���ݸ������������û����ӵ�URL��ַĩβ
public class SessionDemo1_2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		request.getSession();//����Session
		
		//ͨ��URL����SessionID���Է�ֹ�û����ڽ�ֹcookie���������鷳��encodeURL�������Զ���SessionID����ڷ�װ��URL����
		String url1 = response.encodeURL("/ServletDemo2/SessionDemo1"); 
		String url2 = response.encodeURL("/ServletDemo2/SessionDemo2");
		
		out.print("<a href='"+url1+"'>����</a>");
		out.print("   <a href='"+url2+"'>����</a>");
		
	/*	out.print("<a href='/ServletDemo2/SessionDemo1'>����</a>");
		out.print("  <a href='/ServletDemo2/SessionDemo2'>����</a>");*/
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
