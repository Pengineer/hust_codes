package edu.hust.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* text/xml：即指明服务器返回的是xml文件，用responseXml对象返回  */

public class XmlFileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print("<?xml version='1.0' encoding='UTF-8'?>");
		out.print("<xml-body>");
		out.print("<province name='beijing'>");
		out.print("<city>������</city>");
		out.print("</province>");
		out.print("<province name='shanghai'>");
		out.print("<city>�ֶ�</city>");
		out.print("</province>");
		out.print("<province name='hubei'>");
		out.print("<city>�人</city>");
		out.print("</province>");
		out.print("</xml-body>");		
	}

}
