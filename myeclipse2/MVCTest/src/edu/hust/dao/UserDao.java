package edu.hust.dao;

import edu.hust.domain.User;

public interface UserDao {

	void add(User user);

	//�����û����������û�����������ֵ
	User find(String username, String password);

	//����ע����û������ݿ����Ƿ����
	boolean isExist(String username);

}