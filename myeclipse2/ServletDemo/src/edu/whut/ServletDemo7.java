package edu.whut;

/*ͨ��ServletContext��ȡ��Դ�ļ�
 *���䣺һ����Դ�����ļ������֣�һ����.xml��һ����.properties��
 *      ��������ļ��������֮�����й�ϵ�ģ�����XML�ļ�����������ļ��������֮����û�й�ϵ�ģ�
 *      ����properties�ļ��� 
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
	
	//ͨ��ServletContext��ȡ��Դ�ļ�,ע���ȡproperties��Դ�ļ��ľ���д��
	public void test1() throws IOException{
		//***************��Դ�Ƿ��ڷ���������ģ����Ҫע����Դ·����д��***********
		InputStream inStream = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
		Properties props = new Properties();
		props.load(inStream);
		
		String name = props.getProperty("name");
		String age = props.getProperty("age");
		System.out.println("name="+name+",age="+age);	
	} 
	
	//�˷����ǵ��͵Ĵ���д����FileInputStream�����·�����������·����������������·����startup.bat��
	public void test2() throws IOException{
		FileInputStream fis = new FileInputStream("/WEB-INF/classes/db.properties");
		Properties props = new Properties();
		props.load(fis);
		
		String name = props.getProperty("name");
		String age = props.getProperty("age");
		System.out.println("name="+name+",age="+age);
	}
	
	//���test2�ĸĽ���������FileInputStream���洫�����·������һ�ô����Ƕ�ȡ��Դ�ļ���
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
