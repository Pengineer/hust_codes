package edu.hust.action.parameter;

/**
 * 获取URL提交过来的请求参数(当我们通过URI直接访问某一个Action时)
 * 
 *  在struts2中，获取URL提交的请求参数，我们只需要在对应的Action中定义具有相同名称的参数就可以了，同时为参数添加setter方法。
 * （如果要在JSP中获取参数，还应该添加getter方法，一般都添加）
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
