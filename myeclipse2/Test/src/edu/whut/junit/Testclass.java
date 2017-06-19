package edu.whut.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @Description：为Prototypeclass类创建的测试类。
 * 			当需要测试整个Testclass测试类中的方法时，可以再outline窗体中，选中Testclass，右击选择Run as > Junit Test;
 * 			如果只需要测试每一个方法，我们可以在outline窗口中选中该方法，然后右击选择Run as > Junit Test;
 * 
 * @attention：注意before/after 和 beforeclass/afterclass 的区别，前者是在每一个测试方法在运行都会成对执行一次，后者是
 * 			        在整个Testclass测试类运行前和运行后执行，只执行一次。其次，后则必须是静态的。
 */

public class Testclass {
	Prototypeclass p ;
	
	@BeforeClass
	public static void beforeclass(){
		System.out.println("beforeclass");
	}
	
	/*
	 * @Function:测试类运行前，会首先自动调用before方法
	 */
	@Before
	public void before(){
		p = new Prototypeclass();
		System.out.println("before");
	}
	
	/*
	 * @Function:测试Prototypeclass类中的method1方法
	 */
	@Test
	public void testmethod1(){
		p.method1();
		System.out.println("method1 run");
	}
	
	/*
	 * @Function:测试Prototypeclass类中的method2方法
	 */
	@Test
	public void testmethod2(){
		p.method2();
		System.out.println("method2 run");
	}
	
	/*
	 * Assert是一个断言类，里面有许多断言函数，比如：断言对象是否相等，值是否相等，对象是否为空等；
	 * 		断言方法分带消息的和不带消息的，代消息的就是当实际值与期望值不一致时，输出错误消息
	 */
	@Test
	public void testmethod3(){
		Assert.assertEquals("测试返回值错误", "method3", p.method3());
	}
	
	/*
	 * 测试结束时，系统会自动调用after方法
	 */
	@After
	public void after(){
		p = null;
		System.out.println("after");
	}
	
	@AfterClass
	public static void afterclass(){
		System.out.println("afterclass");
	}
}
