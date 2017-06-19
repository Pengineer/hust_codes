package edu.hust.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.hust.dao.HibernateDao;
import edu.hust.model.User;
import edu.hust.service.IUserRegister;

@Component("userRegister")
public class UserRegister implements IUserRegister {
	
	private String username;
	private String password;
	private HibernateDao dao;
	
	@Override
	public String addUser() {
		List<User> users = (List<User>)dao.query("select u from User u where u.username=" + this.username);
		if(users.size() > 0) {
			return "fail";
		} 
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		dao.add(user);
		return "success";
	}

	@Resource
	public void setDao(HibernateDao dao) {
		this.dao = dao;
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
