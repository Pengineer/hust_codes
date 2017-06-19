package edu.whut;

/*通过ServletContext实现数据的请求转发（有问题的转发方式，数据的转发一般不用Context域，
      而用request域,因为ServletContext是被WEB下的所有Servlet共享的，存在线程安全问题。）*/

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo6 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//创建数据
		String data = "The data of 1.jsp come from Demo6.";
		this.getServletContext().setAttribute("data", data);
		
		//获取转发器,将请求转发给1.jsp，由1.jsp将数据输出给浏览器（jsp可以封装java代码）
		RequestDispatcher dis = this.getServletContext().getRequestDispatcher("/1.jsp");
		//执行转发
		dis.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
