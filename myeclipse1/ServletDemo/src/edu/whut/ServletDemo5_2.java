package edu.whut;

//����һ���������ݡ���ServletDemo5_3��ȡ����ʵ�����ݹ���

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo5_2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String data = "This data comes from ServletDemo5_2 used by all Servlet in this WEBApp through servletContext.";
		this.getServletContext().setAttribute("data", data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
