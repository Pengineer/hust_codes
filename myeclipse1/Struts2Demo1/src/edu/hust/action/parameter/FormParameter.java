package edu.hust.action.parameter;

import edu.hust.bean.Person;

/**
 * ��ȡform���еĲ�����������ֱ�ӷ��ʵ���һ��JSPҳ�棩
 *
 * ��Strust1�������ύ��form��ActionServlet���Զ���form����װ�ص�һ��ActionForm��bean���У�Ȼ�󴫵ݸ�Action��
 * 
 * ��Struts2�У����ǲ���Ҫдһ��ActionForm�������Զ����������뵽Action�Ķ�Ӧ�����У���setter��������������ͬ��
 */
public class FormParameter {
	private int id;
	private String name;
	private Person person;  //�������Ը�ֵ(JSPҳ��ı���Ϊname�ټӸ����Լ���)
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String execute(){
		
		return "success";
	}
}
