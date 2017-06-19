package edu.hust.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.hust.dao.HibernateDao;
import edu.hust.model.User;
import edu.hust.service.IUserRegisterService;

@Component("userRegister")
public class UserRegisterService implements IUserRegisterService {
	
	private HibernateDao dao;//set方法上进行对象属性的注入
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String addUser(String username, String password) {
		List<User> users = (List<User>)dao.query("select u from User u where u.username=?", username);
		if(users.size() > 0) {
			return "RegisterFail";
		} 
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		dao.add(user);
		return "RegisterSuccess";
	}

	@Resource 
	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}

}
