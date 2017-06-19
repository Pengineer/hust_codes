package edu.whut.reflect;

/**
 * @Description:注意反射技术主要是用来做框架的，框架是基于配置文件的。属于重量级技术。
 */

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReflectPerson {
	/*************************获取构造函数*******************************************/
    /*
     * function:测试通过反射技术获取person类的无参构造函数，并通过该构造函数创建person实例对象
     * attention：反射技术中getConstructor只能获取public修饰的构造函数
     */
	@Test
	public void reflectDemo1_1() throws Exception{
		
		//将person类加载到内存中，获取person类的字节码
		Class clazz = Class.forName("edu.whut.reflect.Person");
		//获取字节码中的空参数构造函数
		Constructor con = clazz.getConstructor(null);
		//通过构造函数创建实例对象
		Person p = (Person) con.newInstance(null);
	} 
	
	/*
	 * function:获取带一个参数的构造函数
	 */
	@Test
	public void reflectDemo1_2() throws Exception{
		
		Class clazz = Class.forName("edu.whut.reflect.Person");
		//必须传入参数类型的字节码形式
		Constructor con = clazz.getConstructor(String.class);		
		Person p = (Person) con.newInstance("with paragram");
	}
	
	/*
	 * function:获取多个带参的构造函数
	 */
	@Test
	public void reflectDemo1_3() throws Exception{
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Constructor con = clazz.getConstructor(String.class,int.class);//int.class
		Person p = (Person) con.newInstance("Jhon",20);
	}
	
	/*
	 * function: 获取私有构造函数
	 * addition:1，getConstructor只能返回public修饰的构造函数，getDeclaredConstructor均可
	 * 			2，被私有化的成员一般是不可被外界访问的，但是在反射技术是可以的，即暴力反射：setAccessible
	 */
	@Test
	public void reflectDemo1_4() throws Exception{
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Constructor con = clazz.getDeclaredConstructor(List.class);
		con.setAccessible(true);//暴力反射，获取私有构造函数的实例对象
		Person p = (Person) con.newInstance(new ArrayList());
	}
	
	/*
	 * function:获取无参构造函数的另一种方法。等同于reflectDemo1
	 */
	@Test
	public void reflectDemo1_5() throws Exception{
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Person p = (Person) clazz.newInstance();
	}
	
	/************************获取成员方法********************************************/
	/*
	 * function：获取无参的成员方法。必须先有对象
	 */
	@Test
	public void reflectDemo2_1() throws Exception{
		Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getMethod("method1", null);
		mt.invoke(p, null);//执行method1方法
	}
	
	/*
	 * function:获取带参的成员方法。必须先有对象
	 */
	@Test
	public void reflectDemo2_2() throws Exception{
		Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getMethod("method2", int[].class);
		mt.invoke(p, new int[]{20,21});
	}
	
	/*
	 * function:获取有返回值类型的成员方法。必须先有对象
	 */
	@Test
	public void reflectDemo2_3() throws Exception{
		Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getMethod("method3", null);
		String str = (String) mt.invoke(p, null); 
		System.out.println(str);
	}
	
	/*
	 * function:获取私有成员方法。必须先有对象
	 */
	@Test
	public void reflectDemo2_4() throws Exception{
		Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getDeclaredMethod("method4", null);
		mt.setAccessible(true);
		mt.invoke(p, null);
	}
	
	/*
	 * function:获取静态成员方法。可以不必有对象，可有可无
	 */
	@Test
	public void reflectDemo2_5() throws Exception{
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getMethod("method5", null);
		mt.invoke(null, null);
	}
	/*
	 * function:获取main方法。
	 * attention：对参数为字符串数组的方法需注意，invoke(Object obj, Object... args)方法的第二个参数是一个
	 *            可变参数类型，而不是数组，因此传递一个new String[]{"Smith","Jhon"}，系统会将它拆分成两个String
	 *            类型参数，而不是当做成一个整体(**暂时不知道为什么reflectDemo2_2中的int数组可以直接传递？？？？**--已解决：因为21是int基本类型，不能单独作为对象存在，只有int[]数组才是对象)。
	 *            SUN这样的目的是为了兼容无可变参数的早期版本，早期 对于多参数成员方法，invoke的第二个参数是一个Object数组，
	 *            当invoke接收到一个数组时，会自动将该数组进行拆分，变成多个参数。          
	 *            解决方法: 1，将被拆的数组再次封装成Object类型的数组，这样对Object拆分时，就会得到一个String类型数组。
	 *                      2，将数组当成整体进行类型提升，变成*一个*Object对象。（java中一切数据都有其存在的类，这些类都是Object的子类）
	 */
	@Test
	public void reflectDemo2_6() throws Exception{
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Method mt = clazz.getMethod("main", String[].class);
		//mt.invoke(null, new String[]{"Smith","Jhon"});
		//mt.invoke(null, new Object[]{new String[]{"Smith","Jhon"}});  解决方法一
		mt.invoke(null, (Object)new String[]{"Smith","Jhon"});       // 解决方法二
	}
	
	/************************获取成员属性（字段）****************************************/
	@Test
	public void reflectDemo3_1() throws Exception{
		Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Field f = clazz.getField("str");
		Object value = f.get(p);//获取字段的值
		Class obj = f.getType();//获取字段类型
		if(obj == String.class)
			System.out.println(value);
		
		f.set(p, "ABC");//设置字段的值
		System.out.println(p.str);
	}
	@Test
	public void reflectDemo3_2() throws Exception{
		//Person p = new Person();
		Class clazz = Class.forName("edu.whut.reflect.Person");
		Field f = clazz.getField("a1");//f仅仅是字段对象，通过对象的方法获取值
		Object value = f.get(clazz.newInstance());
		Class type = f.getType();
		if(type == int[].class){
			System.out.println(value);
			int len = Array.getLength(value);
			int num = Array.getInt(value, 0);
			System.out.println(len+","+num);
		}
	}
}
