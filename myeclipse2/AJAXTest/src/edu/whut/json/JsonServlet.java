package edu.whut.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 从服务器端接收数据的时候，那些数据的格式必须是浏览器可以理解的，服务器端的编程语言只能以三种格式返回数据：HTML（普通文本），XML，JSON；
 * json:JavaScript Object Notation，是一中简单的数据格式，比xml更轻巧。json是JavaScript的原生格式，这意味着在Javascript中处理json数据不需要
 *      任何特殊的API或工具包。
 * json的格式：对象是一个无序的“名称/值”的集合。一个对象以‘{’开始，‘}’结束，每个名称后跟一个‘:’，“名称/值”之间使用‘,’隔开。
 * 
 * 比如，一个集合（对象）：{名称1:值1,名称2:值2}
 * 并列数据的集合用方括号表示：
 *                 [
 *                    {名称1:值1,名称2:值2},
 *                    {名称1:值1,名称2:值2}
 *                 ]
 * 元素值可具有的类型：string，number，object，array，true，false，null
 * 
 * 
 * JSON在传输过程中也是一种文本字符串。它也被存储在 responseText属性中。为了读取存储在 responseText 属性中的 JSON 数据，需要根据 JavaScript 的 eval 语句。
 * 函数 eval会把一个字符串当作它的参数。然后这个字符串会被当作 JavaScript代码来执行。因为 JSON 的字符串就是由JavaScript代码构成的，所以它本身是可执行的；
 */
public class JsonServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String jsonstr = "{pid:1, pname:'wuhan'}";
		out.print(jsonstr);
	}

}
