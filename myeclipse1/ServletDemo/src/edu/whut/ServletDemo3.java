package edu.whut;

/**关于线程安全问题的讨论:当有多用户使用共享数据时，就会发生线程安全问题。*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.org.mozilla.javascript.internal.Synchronizer;

public class ServletDemo3 extends HttpServlet {

	int i = 0 ;  //共享数据
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 解决方案一：使用同步代码块
		 * 
		 * 新问题：不能实现多户用同时访问。
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
		
		/*解决方案二：继承一个标记接口SingleThreadModel，当有多户用访问时，它不是单一的创建多个
		 * 线程，而是给每一个线程创建一个独立的Servlet，这样就不存在数据共享，但是，相当耗资源，
		 * 因此已过时。
		 */
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
