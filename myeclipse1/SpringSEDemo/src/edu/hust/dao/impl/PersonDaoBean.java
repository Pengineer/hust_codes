package edu.hust.dao.impl;

import edu.hust.dao.PersonDao;

public class PersonDaoBean implements PersonDao {
	
	@Override
	public void add(){
		System.out.println("add()...");
	}
}
