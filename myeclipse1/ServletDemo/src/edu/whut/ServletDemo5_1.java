package edu.whut;

/*��ȡServletContext����ServletContext������Ӧ�ó���Ķ��󣬼�����Context���������
 *ʵ��ͬһwebӦ���µĸ�Servlet֮�����ݵĹ���*/

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo5_1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//getServletContext������GenericServlet����ʵ�ֵģ�HttpServlet��̳���GenericServlet��
		ServletContext scon = this.getServletContext();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
