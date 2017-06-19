package edu.whut.junit;
/**
 * @需要被测试的类
 */

public class Prototypeclass {
	
	public static void main(String[] args) {
		System.out.println("main method");
	}
	
	public void method1(){
		System.out.println("method1");
	}
	
	public void method2(){
		System.out.println("method2");
	}
	
	public String method3(){
		System.out.println("method3");
		return "method3";
	}

}
