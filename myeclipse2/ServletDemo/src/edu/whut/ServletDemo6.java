package edu.whut;

/*ͨ��ServletContextʵ�����ݵ�����ת�����������ת����ʽ�����ݵ�ת��һ�㲻��Context��
      ����request��,��ΪServletContext�Ǳ�WEB�µ�����Servlet����ģ������̰߳�ȫ���⡣��*/

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo6 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//��������
		String data = "The data of 1.jsp come from Demo6.";
		this.getServletContext().setAttribute("data", data);
		
		//��ȡת����,������ת����1.jsp����1.jsp������������������jsp���Է�װjava���룩
		RequestDispatcher dis = this.getServletContext().getRequestDispatcher("/1.jsp");
		//ִ��ת��
		dis.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
