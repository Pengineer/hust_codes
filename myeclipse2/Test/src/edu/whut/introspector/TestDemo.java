package edu.whut.introspector;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 基本步骤：通过属性描述器来获取属性的set和get方法
 */

public class TestDemo {
	/*
	 * function:得到bean的所有属性
	 */
	@Test
	public void test1() throws Exception{
		BeanInfo info = Introspector.getBeanInfo(Person.class, Object.class);
		PropertyDescriptor[] prop = info.getPropertyDescriptors();
		for(PropertyDescriptor p : prop){
			System.out.println(p.getName());
		}
	}
	
	/*
	 * function:操作bean的指定属性
	 */
	@Test 
	public void test2() throws Exception{
		Person p = new Person();
		//创建age属性的属性描述器
		PropertyDescriptor des = new PropertyDescriptor("age", Person.class);
		//反射出写属性方法：public void setAge(int age)
		Method m1 = des.getWriteMethod();   
		m1.invoke(p, 23);
		
		Method m2 = des.getReadMethod();   //public int getAge()
		System.out.println(m2.invoke(p, null));
	}
	
	/*
	 * function:获取当前操作属性的类型
	 */
	@Test
	public void test3() throws Exception{
		Person p = new Person();
		PropertyDescriptor des = new PropertyDescriptor("age", Person.class);
		Class c = des.getPropertyType();
		System.out.println(c);
	} 
}
