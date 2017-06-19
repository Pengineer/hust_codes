package edu.hust.service.impl;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;

public class PersonServiceBean2 implements PersonService{
	private String username;
	private PersonDao personDao;  //在service层注入Dao层的对象（通过构造方法的方式）
	
	public PersonServiceBean2(PersonDao personDao, String username){
		this.personDao = personDao;
		this.username = username;
	}
	
	public void save(){
		personDao.add();
		System.out.println(username);
	}
}
