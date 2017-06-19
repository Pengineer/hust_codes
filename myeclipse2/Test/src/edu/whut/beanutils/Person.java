package edu.whut.beanutils;

import java.util.Date;


/**
 * @Description：
 * edu.whut.introspector包中使用的是传统方法；
 * edu.whut.beanutils中使用的是第三方开发工具包（非SUN公司），专门针对属性的操作。
 */

public class Person {
	public String name;
	public int age ;
	public Date birthday;
	
	//本类一共有5个成员属性（两个字段），ABC、name、age、birthday、class（从Object继承）
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

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
