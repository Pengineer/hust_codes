package edu.whut;

/**将Servlet布署到服务器上后，启动服务器时，就会生成相应的.class文件
 * 
 * Servlet接口SUN公司定义了两个默认实现类，分别为：GenericServlet、HttpServlet。

 * HttpServlet指能够处理HTTP请求的servlet，它在原有Servlet接口上添加了一些与HTTP协议处理方法，
 * 它比Servlet接口的功能更为强大。因此开发人员在编写Servlet时，通常应继承这个类，而避免直接去实
 * 现Servlet接口。
 *
 * HttpServlet在实现Servlet接口时，覆写了service方法，该方法体内的代码会自动判断用户的请求方式，
 * 如为GET请求，则调用HttpServlet的doGet方法，如为Post请求，则调用doPost方法。因此，开发人员在
 * 编写Servlet时，通常只需要覆写doGet或doPost方法，而不要去覆写service方法。 
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
