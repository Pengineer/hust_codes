package edu.whut.response;

/*��Servletʹ��outputStream�ֽ�������������ݵ�ע������*/

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test3(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	//�������ʾ����
	public void test1(HttpServletResponse response) throws IOException{
		String data = "�й�";
		OutputStream outstream = response.getOutputStream();
		outstream.write(data.getBytes("UTF-8"));//ָ��������UTF-8��ʽ���͸������
	}
	
	//ָ����������뷽ʽ��Ĭ����GB2312��
	public void test2(HttpServletResponse response) throws IOException{
		String data = "�й�";
		response.setHeader("content-type", "text/html;charset=UTF-8");//ָ��������ı��뷽ʽ��д��text/html,charset=UTF-8�����������ʾ����
		OutputStream outstream = response.getOutputStream();
		outstream.write(data.getBytes("UTF-8"));//ָ��������UTF-8��ʽ���͸������
	}
	
	//��html�е�meta��ǩ��ģ��һ��http��Ӧͷ�����������������Ϊ
	public void test3(HttpServletResponse response) throws IOException{
		String data = "�й�";
		OutputStream outstream = response.getOutputStream();
		outstream.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'>".getBytes());
		outstream.write(data.getBytes("UTF-8"));
	}
}
