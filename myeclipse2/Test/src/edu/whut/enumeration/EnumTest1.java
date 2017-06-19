package edu.whut.enumeration;

import org.junit.Test;

/**
 * @Description：枚举类
 */
public class EnumTest1 {

	@Test
	public void test(){
		System.out.println(stu2.A.getvalue());
	}
	
	/*
	 * 无成员方法的枚举类，其中A,B,C,D,E,F都是类类型的变量，是values1类型的对象
	 */
	enum stu1{
		A,B,C,D,E,F; //相当于new了6个values1对象
	}
	/*
	 * 有构造函数和成员方法的枚举类
	 */
	enum stu2{
		A("90-100"),B("80-89"),C("70-79"),D("60-69"),E("1-59"),F("0");
		
		private String value;
		private stu2(String value){
			this.value = value;
		}
		
		public String getvalue(){
			return this.value;
		} 
	}

}
