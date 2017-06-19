package edu.hust.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.hust.service.imp.UserRegisterService;

@Component("registerAction")
@Scope("prototype")
public class RegisterAction {
	
	private String username;
	private String password;
	
	@Resource
	private UserRegisterService userRegisterService;
	
	public String addUser() {
		return userRegisterService.addUser(username, password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
