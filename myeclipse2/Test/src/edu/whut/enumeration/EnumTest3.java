package edu.whut.enumeration;

import org.junit.Test;
/**
 * 
 * @Description:枚举常用方法举例
 * 
 * name(): 获取枚举值得名称
 * ordinal():获取枚举值的位置
 * valueOf():将字符串转成枚举类型
 * values():遍历枚举值
 */
public class EnumTest3 {
	@Test
	public void test(){
		System.out.println(stu.A.name());
		System.out.println(stu.A.ordinal());
		
		System.out.println("----------------------------");
		
		//用处：由于客户端提交的表单数据都是字符串类型的，因此我们可以将某些字符串转成枚举类型，看它是否属于我们定义的枚举类,若不是，会抛出异常
		String str = "B";
		stu s = stu.valueOf(stu.class, str);
		System.out.println(s.name());
		
		System.out.println("----------------------------");
		
		stu[] stus = stu.values();
		for(stu S : stus){
			System.out.println(S);
		}
	}
	
	enum stu{
		A("90-100"),B("80-89"),C("70-79"),D("60-69"),E("1-59"),F("0");
		
		private String value;
		private stu(String value){
			this.value = value;
		}
		
		public String getvalue(){
			return this.value;
		} 
	}
}
