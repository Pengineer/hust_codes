package junit.test;

import org.junit.Test;

import edu.hust.service.impl.PersonServiceBean;
import edu.hust.springproxy.CGlibProxyFactory;

public class SpringEnhancerTest {
	
	@Test
	public void test() {
		CGlibProxyFactory factory = new CGlibProxyFactory();
		
		//ͨ���������ɵĶ�����Ŀ����������
		System.out.println(factory.createProxyObject(new PersonServiceBean()).getClass().getSuperclass());
		PersonServiceBean personServiceBean1 = (PersonServiceBean) factory.createProxyObject(new PersonServiceBean(""));
		personServiceBean1.save("");
		
		System.out.println("------------------------");
		
		PersonServiceBean personServiceBean2 = (PersonServiceBean) factory.createProxyObject(new PersonServiceBean());
		personServiceBean2.save("");
	}
}
