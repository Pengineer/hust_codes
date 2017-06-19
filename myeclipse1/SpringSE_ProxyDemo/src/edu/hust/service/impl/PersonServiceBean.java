package edu.hust.service.impl;

import edu.hust.service.PersonService;
/*
 * 需求：1，拦截所有业务方法
 *     2，判断用户是否有权限，有权限就允许他执行业务方法，没有权限不允许他执行业务方法。（是否有权限是根据user是否为空座位判断依据）
 *     
 * 方法1：if(user ！= null),显然这种方式灵活性太差，一旦条件改变，所有的方法都得修改。
 * 方法2：使用Java本身提供的代理类Proxy，前提是bean的开发是面向接口的。（客户  → 代理对象  → 目标对象）
 * 方法3：使用第三方框架提供的代理机制。因为方法2有一个很严格的约束就是目标对象必须要实现某个接口。
 */

public class PersonServiceBean implements PersonService {
	private String user;
	
	public PersonServiceBean(){}
	
	public PersonServiceBean(String user){
		this.user = user;
	}
	
	public String getUser(){
		return user;
	}

	@Override
	public void save(String name) {
		System.out.println("save()...");
	}

	@Override
	public void update(String name, Integer personid) {
		System.out.println("update()...");
	}

	@Override
	public String getPersonName(Integer personid) {
		System.out.println("getPersonName()...");
		return null;
	}

}
