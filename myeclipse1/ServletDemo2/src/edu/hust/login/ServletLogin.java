package edu.hust.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//通过Session技术来控制用户的登录
public class ServletLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//获取网页中表单提交过来的*参数*数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//将表单数据和数据库中存储的数据进行比较，判断用户是否存在
		ArrayList<User> list = DB.getAllUser();	
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getUsername().equals(username) && list.get(i).getPassword().equals(password)){
				//若用户存在，则创建Session，在一段时间内保存用户的登录信息(默认与会话生命周期一致，点击浏览器图标，就表示开启一个会话，在浏览器汇总的任何操作都属于这个会话，包括新开一个网页，在一个会话内都会共用一个Session)
				HttpSession session = request.getSession();
				//跳转到登陆成功界面
				response.sendRedirect("/ServletDemo2/loginsucess.jsp");
				return;
			}
		} 
		out.print("<font color=red>用户名或密码不存在，请重新输入！</font>");
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
