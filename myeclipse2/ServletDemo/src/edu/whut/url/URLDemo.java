package edu.whut.url;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * ���ڵ�ַ����д
 * ԭ����дһ��'/'��Ȼ�󿴵�ַ�Ǹ�˭�õģ������ַ�Ǹ��������õģ�'/'�ʹ���ǰwebӦ�ã������ַ
 *       �Ǹ�������õģ�'/'�ʹ�����վ��
 * 
 * */

public class URLDemo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//1������ת������Ȼ�ڷ������˷�����
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
		//2,�ض����������������
		response.sendRedirect("/ServletDemo/index.jsp");
		
		//3,��������ȡ��Դ��ַ�����������õ�
		this.getServletContext().getRealPath("/index.jsp");
		
		//4,��������ȡ��Դ�����󣬸��������õ�
		this.getServletContext().getResourceAsStream("/download/1.jpg");
		
		//5,
		/*
		 <a href="/ServletDemo/index.jsp">������</a>          ������������ת�õ�
		 
		 <form action="/ServletDemo/index.jsp"></form>        ������������ת�õ�  
		 
		 c:\\    Ӳ���ϵ���Դ����һ��б��
		 */
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
