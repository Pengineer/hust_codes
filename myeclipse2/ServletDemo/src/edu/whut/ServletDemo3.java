package edu.whut;

/**�����̰߳�ȫ���������:���ж��û�ʹ�ù�������ʱ���ͻᷢ���̰߳�ȫ���⡣*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.org.mozilla.javascript.internal.Synchronizer;

public class ServletDemo3 extends HttpServlet {

	int i = 0 ;  //��������
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* �������һ��ʹ��ͬ�������
		 * 
		 * �����⣺����ʵ�ֶ໧��ͬʱ���ʡ�
		 */
		synchronized(this){
			i++;
			try {
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		response.getOutputStream().write((i+"").getBytes());
		
		/*������������̳�һ����ǽӿ�SingleThreadModel�����ж໧�÷���ʱ�������ǵ�һ�Ĵ������
		 * �̣߳����Ǹ�ÿһ���̴߳���һ��������Servlet�������Ͳ��������ݹ������ǣ��൱����Դ��
		 * ����ѹ�ʱ��
		 */
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
