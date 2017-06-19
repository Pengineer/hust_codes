package edu.whut.enumeration;
/**
 * 
 * @Description:带抽象函数的枚举类
 *
 */
public class EnumTest2 {
	//与EnumTest1不同的是，此类没有使用测试方法，直接run as > java application。
	public static void main(String[] arg){
		System.out.println(stu.A.getGrade());
	} 
	
	enum stu{
		//枚举类型中，既要传递参数，又要覆写抽象方法
		A("90-100"){  
			public String getGrade(){
				return "优秀";
			}
		},
		
		B("80-89"){
			public String getGrade(){
				return "良好";
			}
		},
		
		C("70-79"){
			public String getGrade(){
				return "中等";
			}
		},
		
		D("60-69")
		{
			public String getGrade(){
				return "及格";
			}
		},
		
		E("1-59"){
			public String getGrade(){
				return "不及格";
			}
		},
		
		F("0"){
			public String getGrade(){
				return "缺考";
			}
		};
		
		private String value;
		
		//构造方法
		private stu(String value){  
			this.value = value;
		}
		
		//成员方法
		public String getValue(){
			return this.value;
		} 
		
		//抽象方法:每个分数段都有等级，但等级都不一样，因此用抽象方法，具体方法由对象实现
		public abstract String getGrade();
	}
}
