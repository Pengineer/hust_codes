package edu.whut.response;

/**ʹ��response��Writer�ַ�������ַ�����
            ��������������������ݵ����̣��������Ƚ�����д��response��Ȼ��response�ٽ�����д���������
     ����������response.setHeader("content-type", "text/html;charset=UTF-8")ʱ����ʵ�ǽ��������
  response�ı��뷽ʽ�������ˣ���Ϊ��˼·�����£�����һ�㶼�����response.setCharacterEncoding��
     ����response�ı��뷽ʽ��
 */

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test2(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	//����
	public void test1(HttpServletResponse response) throws IOException{
		String data = "�й�";
		Writer out = response.getWriter();
		out.write(data);
	}

	//һ��������response��������ı��뷽ʽ
	public void test2(HttpServletResponse response) throws IOException{
		String data = "�й�";
		response.setHeader("content-type", "text/html;charset=UTF-8");
		//����response.setContentType("text/html;charset=UTF-8");		
		Writer out = response.getWriter();
		out.write(data);
	}
	
	//��������response�ı�������(����ʱ�Ƽ�ʹ��)
	public void test3(HttpServletResponse response) throws IOException{
		String data = "�й�";
		response.setCharacterEncoding("UTF-8");//��������response�ı�������
		response.setContentType("text/html;charset=UTF-8");		
		Writer out = response.getWriter();
		out.write(data);
	}
}
