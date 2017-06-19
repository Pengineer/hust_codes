package edu.whut;

/*通过ServletContext读取资源文件
 *补充：一般资源配置文件有两种，一种是.xml，一种是.properties。
 *      如果配置文件里的数据之间是有关系的，就用XML文件；如果配置文件里的数据之间是没有关系的，
 *      就用properties文件。 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo7 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test3();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	//通过ServletContext读取资源文件,注意读取properties资源文件的经典写法
	public void test1() throws IOException{
		//***************资源是放在服务器里面的，因此要注意资源路径的写法***********
		InputStream inStream = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
		Properties props = new Properties();
		props.load(inStream);
		
		String name = props.getProperty("name");
		String age = props.getProperty("age");
		System.out.println("name="+name+",age="+age);	
	} 
	
	//此方法是典型的错误写法：FileInputStream里面的路径参数是相对路径，相对于虚拟机的路径（startup.bat）
	public void test2() throws IOException{
		FileInputStream fis = new FileInputStream("/WEB-INF/classes/db.properties");
		Properties props = new Properties();
		props.load(fis);
		
		String name = props.getProperty("name");
		String age = props.getProperty("age");
		System.out.println("name="+name+",age="+age);
	}
	
	//针对test2的改进方法：往FileInputStream里面传入绝对路径，另一用处就是读取资源文件名
	public void test3() throws IOException{
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/db.properties");
		String filename = path.substring(path.lastIndexOf("\\")+1);
		FileInputStream fis = new FileInputStream(path);
		Properties props = new Properties();
		props.load(fis);
		
		String name = props.getProperty("name");
		String age = props.getProperty("age");
		System.out.println("filename:"+filename);
		System.out.println("name="+name+",age="+age);
	}
	
}
