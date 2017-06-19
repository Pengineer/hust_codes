package edu.whut.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ����ת����ʹ��request���������ݴ���ת����Դ��
 *
 * ����ת���ص㣺1���ͻ���ֻ��һ������         2���������ַ��û�仯����ĳЩ��ת��ʱ�򣬱���Ҫ��ʾ�û�����ת����ҳʱ����Ҫ���ַ���仯�����Ǿ�ֻ�����ض���sendRedirect��������������ת��forward��
 * 
 * ����ת����Ӧ�ã�MVC���ģʽ��
 *     M��model��JavaBean��      V��View��jsp��     C��controller��Servlet��
 *     �������Servlet��������Servlet�յ�����������ݲ������ݽ���Javabean���з�װ��JavaBean����װ������ͨ��ת����������jsp����jsp������������ʾ
 */
public class RequestDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test1(request,response);
	}
	
	public void test1(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{	
		String data = "abc";
		request.setAttribute("data", data);//�����ݷ�װ��request���У�����ͨ��this.getServletContext.setAttribute()��
		request.getRequestDispatcher("/rqd2.jsp").forward(request, response);
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
