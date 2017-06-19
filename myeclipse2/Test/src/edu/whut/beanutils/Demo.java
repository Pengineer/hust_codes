package edu.whut.beanutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

public class Demo {
	/*
	 * function:设置属性值，不需要先通过Introspector描述器得到set方法。
	 */
	@Test
	public void test1() throws Exception{ 
		Person p = new Person();
	
		BeanUtils.setProperty(p, "name", "Smith");
		
		System.out.println(p.name);
		
	}
	
	/*
	 * function:根据实际中提交的表单数据（全部是String类型）来操作成员属性。
	 */
	@Test
	public void test2() throws Exception{ 
		Person p = new Person();
		//用户提交的表单数据，其中age本应该是int
		String name = "Jhon";
		String age = "23";
		//age本来是int类型的，但表单中是String类型，在赋值时，工具包会自动进行类型转换
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "age", age);
		
		System.out.println(p.name);
		System.out.println(p.age);
		
	}
	
	/*
	 * function:根据实际中提交的表单数据（全部是String类型）来操作成员属性。
	 *       ***但是该工具包默认只支持8种基本数据类型之间的转换，要想实现更复杂的转换，应该先
	 *       ***注册一个类型转换器。
	 */
	@Test
	public void test3() throws Exception{ 
		Person p = new Person();

		String name = "Jhon";
		String age = "23";
		String birthday = "1991-01-01";
		
		//注册类型转换器,将String类型转换成Data类型
		ConvertUtils.register(new Converter(){
			@Override
			public Object convert(Class type, Object value) {
				//没输入日期
				if(value==null)
					return null;
				//输入的不是字符串
				if(!(value instanceof String))
					throw new ConversionException("只支持String类型转Data类型");
				//输入的全是空格
				if(value=="")
					return null;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return format.parse((String)value);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
			
		}, Date.class);

		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "age", age);
		BeanUtils.setProperty(p, "birthday", birthday);
		
		System.out.println(p.name);
		System.out.println(p.age);
		System.out.println(p.birthday);
		//输出Tue Jan 01 00:00:00 CST 1991，而不是yyyy-MM-dd格式，是因为birthday是date类型，
		//sop打印时是作为字符串打印，也就是说，调用了toString方法
		System.out.println(p.birthday.toLocaleString());
	}
	
	/*
	 * function:使用工具包自带的日期转换类实现转换（有一个缺点：对空字符串输入不能检测）
	 */
	@Test
	public void test4() throws Exception{ 
		Person p = new Person();

		String name = "Jhon";
		String age = "23";
		String birthday = "2000-01-01";
		
		ConvertUtils.register(new DateLocaleConverter(),Date.class);//自带的日期转换类
		
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "age", age);
		BeanUtils.setProperty(p, "birthday", birthday);
		
		System.out.println(p.name);
		System.out.println(p.age);
		System.out.println(p.birthday);
		//输出Tue Jan 01 00:00:00 CST 1991，而不是yyyy-MM-dd格式，是因为birthday是date类型，
		//sop打印时是作为字符串打印，也就是说，调用了toString方法
		System.out.println(p.birthday.toLocaleString());
	}
	
	/*
	 * function:以上的方法都是通过BeanUtils.setProperty来设置属性。
	 *          还有一种方法 就是通过map集合通过BeanUtils.populate来给属性赋值。
	 */
	@Test
	public void test5() throws Exception{
		Map m = new HashMap();
		
		m.put("name", "Smith");
		m.put("age", "23");
		m.put("birthday", "2000-01-01");
		
		ConvertUtils.register(new DateLocaleConverter(),Date.class);//自带的日期转换类
		
		Person p = new Person();
		BeanUtils.populate(p, m);
		
		System.out.println(p.name);
		System.out.println(p.age);
		System.out.println(p.birthday);
	}
}
