package edu.hust.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.hust.model.User;
import edu.hust.service.imp.UserRegisterService;

/**
 * 将属性放入session中，默认情况下ModelMap里面返回给前台的属性的作用域是Request。要想放入session中，有两种方式：
 *
 * 1，通过@SessionAttributes注解 + ModelMap实现。
 * 
 * 2，使用Servlet-API。
 */

@Controller("userContrller2")
@SessionAttributes({"count","title"})
public class UserController2 {
	
	@RequestMapping(value="/register21")
	public String addUser(ModelMap map) {
		map.put("count", "10");        //作用域：Session + Request
		map.put("title", "professor"); //作用域：Session + Request
		map.put("desc", "xxx");        //作用域：Request
		return "Register";
	}
	
	@RequestMapping(value="/register22")
	public String addUser1(HttpSession session, HttpServletRequest request) {
		session.setAttribute("class", "1");            //作用域：Session
		request.setAttribute("class", "4");
		request.setAttribute("school", "2");           //作用域：Request
		request.getSession().setAttribute("stu", "3"); //作用域：Session
		return "Register";
	}
	
	@RequestMapping(value="/register23")
	public String addUser3(HttpSession session) {
		session.setAttribute("country", "1");            //作用域：Session
		return "Register";
	}
	
	@RequestMapping(value="/register24")
	public String addUser4(HttpSession session) {
		return "Register";
	}
	
	@Resource
	UserRegisterService userRegisterService;
	@RequestMapping(value="/register25")
	public String addUser5(HttpSession session) {
		userRegisterService.setName("asdf");
		session.setAttribute("userr", userRegisterService);
		return "Register";
	}
	
	@RequestMapping(value="/register26")
	public String addUser6(User user, String secondname) {
		System.out.println(user.getUsername());
		System.out.println(secondname);
		
		return "";
	}
	@RequestMapping(value="/register27")
	public String addUser7(User user, @RequestParam("secondname") String name) {
		System.out.println(user.getUsername());
		System.out.println(name);
		
		return "";
	}
}
