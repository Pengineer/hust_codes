package edu.hust.service.impl;

import edu.hust.service.PersonService;

public class PersonServiceBean1 implements PersonService{

	@Override
	public void save() {
		System.out.println("save()...");
	}

	@Override
	public void update(String name, Integer id) {
		System.out.println("update()...");		
	}

}
