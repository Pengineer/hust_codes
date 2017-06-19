package edu.whut.introspector;

/**
 * JavaBean是专门用来处理反射中的关于成员属性的操作方法
 */

public class Person {
	public String name;
	public int age ;
	//本类一共有4个成员属性（两个字段），ABC、name、age、class（从Object继承）
	public String getABC(){
		return "ABC";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
