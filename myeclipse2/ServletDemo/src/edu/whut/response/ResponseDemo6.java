package edu.whut.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**控制浏览器缓存数据：expires
 * 当访问的数据不改变时，就可以将数据进行缓存。
 * 
 * function:
 */

public class ResponseDemo6 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//控制缓存时间(一小时),将本文件中的数据进行缓存
		response.setDateHeader("expires", System.currentTimeMillis()+1000*3600);
		
		String data = "aaa";
		response.getWriter().write(data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
