package edu.hust.controller;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import edu.hust.service.imp.UserRegisterService;

/** 
 * 在类上定义本类的全局URL
 * @RequestMapping: RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
 * 1、value， method；
	value：指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
	method：指定请求的method类型， GET、POST、PUT、DELETE等；

 * 2、consumes，produces；
	consumes:指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；

 * 3、params，headers；
	params：指定request中必须包含某些参数值是，才让该方法处理。
	headers：指定request中必须包含某些指定的header值，才能让该方法处理请求。
*/

@Controller("userContrller")      //or @component
@RequestMapping("/register.do") //请求路径
public class UserController {

	@Resource
	private UserRegisterService userRegisterService;
	
	//浏览器输入：http://localhost:8080/Spring_MVC/register.do?method=addUser
	//返回的JSP所在目录为：/*.jsp
	@RequestMapping(params="method=addUser")
	public String addUser(String username, String password) { //表单的参数直接通过方法的参数传进来（struts是通过类的属性）
		System.out.println("addUser");
		return userRegisterService.addUser(username, password);
	}
	
	//如果在方法上通过@RequestMapping也指定路径，那么返回的jsp目录一定是/WEB_INF/register.do/返回JSP名称
	@RequestMapping("/register1")
	public String addUser1(String username, String password) {
		System.out.println("addUser");
		return userRegisterService.addUser(username, password);
	}
}
