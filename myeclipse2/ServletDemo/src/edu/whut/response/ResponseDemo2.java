package edu.whut.response;

/**使用response的Writer字符流输出字符数据
            服务器给浏览器发送数据的流程：服务器先将数据写给response，然后response再将数据写到浏览器，
     当我们设置response.setHeader("content-type", "text/html;charset=UTF-8")时，其实是将浏览器和
  response的编码方式都设置了，但为了思路的清新，我们一般都会加上response.setCharacterEncoding来
     设置response的编码方式。
 */

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test2(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	//乱码
	public void test1(HttpServletResponse response) throws IOException{
		String data = "中国";
		Writer out = response.getWriter();
		out.write(data);
	}

	//一次性设置response和浏览器的编码方式
	public void test2(HttpServletResponse response) throws IOException{
		String data = "中国";
		response.setHeader("content-type", "text/html;charset=UTF-8");
		//或则response.setContentType("text/html;charset=UTF-8");		
		Writer out = response.getWriter();
		out.write(data);
	}
	
	//单独进行response的编码设置(开发时推荐使用)
	public void test3(HttpServletResponse response) throws IOException{
		String data = "中国";
		response.setCharacterEncoding("UTF-8");//单独进行response的编码设置
		response.setContentType("text/html;charset=UTF-8");		
		Writer out = response.getWriter();
		out.write(data);
	}
}
