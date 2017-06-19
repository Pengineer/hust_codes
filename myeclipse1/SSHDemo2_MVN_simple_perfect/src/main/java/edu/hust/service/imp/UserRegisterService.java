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
	
	@Override
	public String addUser(String username, String password) {
		List<User> users = (List<User>)dao.query("select u from User u where u.username=" + username);
		if(users.size() > 0) {
			return "fail";
		} 
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		dao.add(user);
		return "success";
	}

	@Resource //RegisterAction中对象属性的注入直接在属性上完成，不需要set方法
	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}

}
