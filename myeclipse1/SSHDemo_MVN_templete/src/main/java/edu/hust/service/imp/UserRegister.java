package edu.hust.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.hust.dao.HibernateDao;
import edu.hust.service.IUserRegister;

@Component("userRegister")
public class UserRegister implements IUserRegister {
	
	private HibernateDao dao;
	
	@Override
	public String addUser() {
		
		return "success";
	}

	@Resource
	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}
	
}
