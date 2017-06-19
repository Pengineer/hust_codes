package edu.hust.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component  //默认bean的id就是类名首字母小写
@Scope(value="prototype")
public class Group {
	
	String name;

	public void group(){
		System.out.println("group");
	}
	
	public Group(){
		name = "zhangsan";
	}
}
