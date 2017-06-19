package edu.whut.reflect;

import java.lang.reflect.Array;

public class Demo {

	public static void main(String[] args) throws Exception{
		String str = "abc";
		
		//获取字节码的三种常见方法
		
		Class cls1 = str.getClass();
		
		Class cls2 = String.class;
		
		Class cls3 = Class.forName("java.lang.String");
		
		System.out.println(cls1 == cls2);
		System.out.println(cls1 == cls3);
		
		System.out.println(cls1.isPrimitive());  //String不是原始基本数据类型
		System.out.println(int.class.isPrimitive());
		
		System.out.println(int.class == Integer.class);//int和Integer
		System.out.println(int.class == Integer.TYPE);
		/*
		int a = Integer.parseInt("123");
		int b = Integer.valueOf("12");
		System.out.println(a + " ,  " + b);
		
		String s1 = 1 + "";
		//String s2 = a.toString();
		String s2 = new Integer(a).toString();
		*/
		
		int[] a = new int[]{1,2,3};
		printObject(a);
		printObject("abc");
	}
	//需求：打印对象的值
	//分析：如果是普通对象可直接打印，如果是数组必须要先判断。
	//		对数组的判断就使用到了反射技术,字节码的方法：Class.isArray();
	//      对接口,枚举的判断也用该方法。
	public static void printObject(Object obj) {
		Class cls = obj.getClass();
		if(cls.isArray()){
			int len = Array.getLength(obj);
			int num = Array.getInt(obj, 0);
			System.out.println(len+","+num);
		}
		else
			System.out.println(obj);
	}

}
