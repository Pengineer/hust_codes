package edu.whut;

/**��Servlet���𵽷������Ϻ�����������ʱ���ͻ�������Ӧ��.class�ļ�
 * 
 * Servlet�ӿ�SUN��˾����������Ĭ��ʵ���࣬�ֱ�Ϊ��GenericServlet��HttpServlet��

 * HttpServletָ�ܹ�����HTTP�����servlet������ԭ��Servlet�ӿ��������һЩ��HTTPЭ�鴦������
 * ����Servlet�ӿڵĹ��ܸ�Ϊǿ����˿�����Ա�ڱ�дServletʱ��ͨ��Ӧ�̳�����࣬������ֱ��ȥʵ
 * ��Servlet�ӿڡ�
 *
 * HttpServlet��ʵ��Servlet�ӿ�ʱ����д��service�������÷������ڵĴ�����Զ��ж��û�������ʽ��
 * ��ΪGET���������HttpServlet��doGet��������ΪPost���������doPost��������ˣ�������Ա��
 * ��дServletʱ��ͨ��ֻ��Ҫ��дdoGet��doPost����������Ҫȥ��дservice������ 
 * */


import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletDemo1 extends GenericServlet {

	@Override
	public void init() throws ServletException {
		
		super.init();
		
		System.out.println("init1");
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		res.getOutputStream().write("<font size=10 color=red>Hello,I was made by MyEclipse!</font>".getBytes());		
	}

}
