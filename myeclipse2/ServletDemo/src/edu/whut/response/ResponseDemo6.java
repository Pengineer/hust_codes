package edu.whut.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**����������������ݣ�expires
 * �����ʵ����ݲ��ı�ʱ���Ϳ��Խ����ݽ��л��档
 * 
 * function:
 */

public class ResponseDemo6 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���ƻ���ʱ��(һСʱ),�����ļ��е����ݽ��л���
		response.setDateHeader("expires", System.currentTimeMillis()+1000*3600);
		
		String data = "aaa";
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
