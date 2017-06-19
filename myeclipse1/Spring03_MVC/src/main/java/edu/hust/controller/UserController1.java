package edu.hust.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.hust.service.imp.UserRegisterService;

//直接在方法上定义访问路径

@Controller("userContrller1")
public class UserController1 {

	@Resource
	private UserRegisterService userRegisterService;
	
	//浏览器输入：http://localhost:8080/Spring_MVC/register1
	//返回的JSP所在目录为：/*.jsp，与value指定的路径同级
	@RequestMapping(value="/register1")
	public String addUser(String username, String password) {
		System.out.println("addUser");
		return userRegisterService.addUser(username, password);
	}
	
}
