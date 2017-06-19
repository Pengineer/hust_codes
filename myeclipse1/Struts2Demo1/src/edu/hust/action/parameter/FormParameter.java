package edu.hust.action.parameter;

import edu.hust.bean.Person;

/**
 * 获取form表单中的参数（当我们直接访问的是一个JSP页面）
 *
 * 在Strust1中我们提交了form后，ActionServlet会自动将form数据装载到一个ActionForm（bean）中，然后传递给Action，
 * 
 * 在Struts2中，我们不需要写一个ActionForm，它会自动将数据载入到Action的对应属性中（有setter方法，属性名相同）
 */
public class FormParameter {
	private int id;
	private String name;
	private Person person;  //复杂属性赋值(JSP页面的表单中为name再加个属性即可)
	
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
