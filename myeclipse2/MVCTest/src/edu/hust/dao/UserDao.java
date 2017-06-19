package edu.hust.dao;

import edu.hust.domain.User;

public interface UserDao {

	void add(User user);

	//查找用户，并返回用户的所有属性值
	User find(String username, String password);

	//查找注册的用户在数据库中是否存在
	boolean isExist(String username);

}