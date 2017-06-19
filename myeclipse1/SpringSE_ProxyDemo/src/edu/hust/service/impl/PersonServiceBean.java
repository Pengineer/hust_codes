package edu.hust.service.impl;

import edu.hust.service.PersonService;
/*
 * ����1����������ҵ�񷽷�
 *     2���ж��û��Ƿ���Ȩ�ޣ���Ȩ�޾�������ִ��ҵ�񷽷���û��Ȩ�޲�������ִ��ҵ�񷽷������Ƿ���Ȩ���Ǹ���user�Ƿ�Ϊ����λ�ж����ݣ�
 *     
 * ����1��if(user ��= null),��Ȼ���ַ�ʽ�����̫�һ�������ı䣬���еķ��������޸ġ�
 * ����2��ʹ��Java�����ṩ�Ĵ�����Proxy��ǰ����bean�Ŀ���������ӿڵġ����ͻ�  �� �������  �� Ŀ�����
 * ����3��ʹ�õ���������ṩ�Ĵ�����ơ���Ϊ����2��һ�����ϸ��Լ������Ŀ��������Ҫʵ��ĳ���ӿڡ�
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
