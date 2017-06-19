package edu.whut.reflect;

import java.util.List;

public class Person {
//-------------------构造函数------------------------------------------------------
	public Person(){
		System.out.println("Person()");
	}
	
	public Person(String name){
		System.out.println("Person(String name)---name="+name);
	}
	
	public Person(String name,int age){
		System.out.println("Person(String name,int age)---name="+name+",age="+age);
	}
	
	private Person(List list){
		System.out.println("Person(List list)");
	}
	
//-----------------成员方法--------------------------------------------------------
	
	public void method1(){
		System.out.println("method1()");
	}
	
	public void method2(int[] age){
		System.out.println("method2(int[] age)---age="+age[0]);
	}
	
	public String method3(){
		System.out.println("method3()");
		return "return";
	}
	
	private void method4(){
		System.out.println("private void method4()");
	}
	
	public static void method5(){
		System.out.println("static method5()");
	}
	
	public static void main(String[] arg){
		System.out.println("main---arg[0]="+arg[0]);
	}
	
	//-----------------成员字段--------------------------------------------------------
	public String str = "abc";
	public int[] a1 = new int[]{1,2,3};
	//字段只有被赋予了get和set方法之后才能称之为属性
}
