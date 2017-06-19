package edu.whut.response;

/*在Servlet使用outputStream字节流输出中文数据的注意事项*/

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test3(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	//浏览器显示乱码
	public void test1(HttpServletResponse response) throws IOException{
		String data = "中国";
		OutputStream outstream = response.getOutputStream();
		outstream.write(data.getBytes("UTF-8"));//指定数据以UTF-8形式发送给浏览器
	}
	
	//指定浏览器编码方式（默认是GB2312）
	public void test2(HttpServletResponse response) throws IOException{
		String data = "中国";
		response.setHeader("content-type", "text/html;charset=UTF-8");//指定浏览器的编码方式，写成text/html,charset=UTF-8，浏览器会提示下载
		OutputStream outstream = response.getOutputStream();
		outstream.write(data.getBytes("UTF-8"));//指定数据以UTF-8形式发送给浏览器
	}
	
	//用html中的meta标签来模拟一个http响应头，来控制浏览器的行为
	public void test3(HttpServletResponse response) throws IOException{
		String data = "中国";
		OutputStream outstream = response.getOutputStream();
		outstream.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'>".getBytes());
		outstream.write(data.getBytes("UTF-8"));
	}
}
