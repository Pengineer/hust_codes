package edu.whut;

/*获取ServletContext对象：ServletContext是整个应用程序的对象，即它是Context域对象，用于
 *实现同一web应用下的各Servlet之间数据的共享*/

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo5_1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//getServletContext方法在GenericServlet类中实现的，HttpServlet类继承了GenericServlet类
		ServletContext scon = this.getServletContext();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
