package edu.hust.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//ͨ��Session�����������û��ĵ�¼
public class ServletLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//��ȡ��ҳ�б��ύ������*����*����
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//�������ݺ����ݿ��д洢�����ݽ��бȽϣ��ж��û��Ƿ����
		ArrayList<User> list = DB.getAllUser();	
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getUsername().equals(username) && list.get(i).getPassword().equals(password)){
				//���û����ڣ��򴴽�Session����һ��ʱ���ڱ����û��ĵ�¼��Ϣ(Ĭ����Ự��������һ�£���������ͼ�꣬�ͱ�ʾ����һ���Ự������������ܵ��κβ�������������Ự�������¿�һ����ҳ����һ���Ự�ڶ��Ṳ��һ��Session)
				HttpSession session = request.getSession();
				//��ת����½�ɹ�����
				response.sendRedirect("/ServletDemo2/loginsucess.jsp");
				return;
			}
		} 
		out.print("<font color=red>�û��������벻���ڣ����������룡</font>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

class DB{
	public static ArrayList<User> list = new ArrayList<User>();
	static{
		list.add(new User("aaa","123"));
		list.add(new User("bbb","123"));
		list.add(new User("ccc","123"));
	}
	
	public static ArrayList<User> getAllUser(){
		return list;
	}
}
