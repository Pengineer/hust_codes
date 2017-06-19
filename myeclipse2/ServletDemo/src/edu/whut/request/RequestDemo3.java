package edu.whut.request;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**forward方法细节
 *
 *1，当使用close将流关闭后，表示数据已经被写到客户端浏览器（本次请求结束，本次request被销毁），那么接下来的请求转发都是无效的
 *2，见test2方法。当执行第一次转发后，数据就已经写到客户端浏览器了，本次请求就结束了。
 */
public class RequestDemo3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//test1(request,response);
		test2(request,response);
	}
	
	//产生无效状态异常的情况一：转发之前使用close方法结束本次请求
	public void test1(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "cba";
		PrintWriter writer = response.getWriter();
		writer.write(data);
		writer.close();
		
		//下面 一句将导致无效状态异常的产生：IllegalStateException
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	} 
	
	//产生无效状态异常的情况二：出现两次转发请求(开发中非常常见)
	public void test2(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "def";
		if(true){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		request.getRequestDispatcher("/rd6.jsp").forward(request, response);
	}
	
	//test2的解决方法：每当写出一个forward时，在后面跟上一句return。
	public void test3(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "def";
		if(true){
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/rd6.jsp").forward(request, response);
		return;
	}
	
	//forward会清空response缓冲区中的内容
	public void test4(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String data = "fed";
		PrintWriter writer = response.getWriter();
		writer.write(data);//将数据写到response中,还没有写到浏览器
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);//清空response中内容
		return;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
