package edu.whut.reflect;

/**项目工程中的JRE System Library目录下就提供了系统的所有自定义Class类，供类加载器加载*/

public class ClassLoaderDemo {

	public static void main(String[] args) {
		//1，通过Class对象获取本类的类加载器，然后得到类加载器的Class类，然后获得类名
		System.out.println(
				ClassLoaderDemo.class.getClassLoader().getClass().getName());
		//输出：sun.misc.Launcher$AppClassLoader@1372a1a，说明本类类加载器的类名是AppClassLoader
		
		//2，获取系统自定义类的类加载器
		System.out.println(System.class.getClassLoader());
		//输出：null，说明System类的类加载器是BootStrap（嵌入在JVM中的最底层类加载器，由C++编写的非java类）
	
	}

}
