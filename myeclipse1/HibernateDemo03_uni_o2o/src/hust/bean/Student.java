package hust.bean;

public class Student {
	
	private int id;
	private String name;
	
	private StudentIdcard stuIdcard;

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

	public StudentIdcard getStuIdcard() {
		return stuIdcard;
	}

	public void setStuIdcard(StudentIdcard stuIdcard) {
		this.stuIdcard = stuIdcard;
	}
}
