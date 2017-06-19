package edu.whut.response;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**����������Զ�ˢ����ҳ*/

public class ResponseDemo5 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//test1(response);
		test3(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	//�����ÿ������ˢ��һ����ҳ��ͨ������������Ϣͷ�����
	public void test1(HttpServletResponse response) throws IOException{
		response.setHeader("refresh", "3");
		
		String num = new Random().nextInt(1000) + "";
		response.getOutputStream().write(num.getBytes());
	}
	
	//ʵ����ҳ���Զ���ת��ע��ʱ�������ῴ����
	public void test2(HttpServletResponse response) throws IOException{
		String data = "��½�ɹ���3����Զ���ת����û�У�����<a href=''><i>����</i></a>";
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("refresh", "3;url='/ServletDemo/1.html'");
		response.getWriter().write(data);//����data������html��䣬���Ҫ���ַ�����data���ַ�������ʽ���
	}
	
	//����ʵ�ʿ�����ʹ�õ��Զ���ת�����������������jsp����,�μ�ServletDemo6.java
	public void test3(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String message = "<meta http-equiv='refresh' content='3;url=/ServletDemo/index.jsp'>��½�ɹ���3����Զ���ת����û�У�����<a href='/ServletDemo/index.jsp'><i>����</i></a>";
		this.getServletContext().setAttribute("message", message);
		this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}

}
