package edu.hust.action.parameter;

/**
 * ��ȡURL�ύ�������������(������ͨ��URIֱ�ӷ���ĳһ��Actionʱ)
 * 
 *  ��struts2�У���ȡURL�ύ���������������ֻ��Ҫ�ڶ�Ӧ��Action�ж��������ͬ���ƵĲ����Ϳ����ˣ�ͬʱΪ�������setter������
 * �����Ҫ��JSP�л�ȡ��������Ӧ�����getter������һ�㶼��ӣ�
 *
 */
public class URLParameter {
	private int id;
	private String name;
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
