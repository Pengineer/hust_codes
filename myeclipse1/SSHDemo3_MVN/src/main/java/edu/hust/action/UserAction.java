package edu.hust.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import edu.hust.dao.HibernateDao;
import edu.hust.model.User;

@Component("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private List<User> users;
	@Resource
	private HibernateDao dao;
	
	public String listUsers() {
		users = dao.query("select u from User u");
		return SUCCESS;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
