package edu.whut.reflect;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

public class structDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//1，加载配置文件
		InputStream inputstream = new FileInputStream("config.properties");
		/*当配置文件在源文件下时：InputStream inputstream = structDemo.class.getClassLoader().getResourceAsStream("edu/whut/reflect/config.properties");
		 *                 或则：InputStream inputstream = structDemo.class.getResourceAsStream("config.properties");*/
		Properties pro = new Properties();
		pro.load(inputstream);
		inputstream.close();
		
		//2，获取配置文件属性信息，加载属性类的字节码文件并创建实例对象
		String className = pro.getProperty("className");
		Collection collection = (Collection) Class.forName(className).newInstance();
		//不用反射时写法：Collection collection = new Hashset();
		
		//3，调用的属性类的方法进行相应操作
		Person p1 = new Person("zhang",20);
		Person p2 = new Person("li",21);
		Person p3 = new Person("li",20);
		
		collection.add(p1);
		collection.add(p2);
		collection.add(p3);
		collection.add(p1);
		
		System.out.println(collection.size());
	}

}
