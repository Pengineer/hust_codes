package edu.hust.dao.impl;

import edu.hust.dao.PersonDao;

// @Componnet
public class PersonDaoBean implements PersonDao {
	
	public void add(){
		System.out.println("from DAO----add()");
	}
}
