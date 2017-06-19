package edu.whut.request;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**forward����ϸ��
 *
 *1����ʹ��close�����رպ󣬱�ʾ�����Ѿ���д���ͻ���������������������������request�����٣�����ô������������ת��������Ч��
 *2����test2��������ִ�е�һ��ת�������ݾ��Ѿ�д���ͻ���������ˣ���������ͽ����ˡ�
 */
public class RequestDemo3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//test1(request,response);
		test2(request,response);
	}
	
	//������Ч״̬�쳣�����һ��ת��֮ǰʹ��close����������������
	public void test1(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "cba";
		PrintWriter writer = response.getWriter();
		writer.write(data);
		writer.close();
		
		//���� һ�佫������Ч״̬�쳣�Ĳ�����IllegalStateException
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	} 
	
	//������Ч״̬�쳣�����������������ת������(�����зǳ�����)
	public void test2(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "def";
		if(true){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		request.getRequestDispatcher("/rd6.jsp").forward(request, response);
	}
	
	//test2�Ľ��������ÿ��д��һ��forwardʱ���ں������һ��return��
	public void test3(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "def";
		if(true){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/rd6.jsp").forward(request, response);
		return;
	}
	
	//forward�����response�������е�����
	public void test4(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "fed";
		PrintWriter writer = response.getWriter();
		writer.write(data);//������д��response��,��û��д�������
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);//���response������
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
