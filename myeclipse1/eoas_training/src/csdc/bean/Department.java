package csdc.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Title: Department Description:用户部门 Company: HUST-NADR
 * 
 * @author SHAO xiao
 * @date 2014-4-24
 */
public class Department {

	private String id;// 部门id
	private String name;// 部门名称

	private Department department;// 父级部门id
	
/*	private Set<Person> person = new HashSet<Person>();
	private Department parent;
	private Set<Department> children = new HashSet<Department>();*/

	// ---get & set---
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

/*	public Set<Person> getPerson() {
		return person;
	}

	public void setPerson(Set<Person> person) {
		this.person = person;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}*/




}
