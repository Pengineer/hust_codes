package edu.whut.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * responseʵ�������ض���
 */
public class ResponseDemo7 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.sendRedirect("/ServletDemo/index.jsp");
		return;//����ˣ��ض��������ת����Ҫ
		
		/*sendRedirectʵ��ԭ��
		 * response.setStatus(302);//302��ʾ�����ض���
		 * response.setHeader("location", "/ServletDemo/index.jsp");
		 */
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
