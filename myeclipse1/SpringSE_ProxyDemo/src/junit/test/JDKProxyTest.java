package junit.test;

import org.junit.Test;

import edu.hust.jdkproxy.JDKProxyFactory;
import edu.hust.service.PersonService;
import edu.hust.service.impl.PersonServiceBean;

public class JDKProxyTest {

	@Test
	public void test() {
		JDKProxyFactory factory = new JDKProxyFactory();
		
		//生成的代理对象的类型是代理类型的子类（一般用目标对象接口类型接收）
		System.out.println(factory.createProxyObject(new PersonServiceBean()).getClass().getSuperclass());
		PersonService personService1 = (PersonService) factory.createProxyObject(new PersonServiceBean());		
		personService1.save("");//触发invoke()方法。
		
		System.out.println("--------------");
		
		PersonService personService2 = (PersonService) factory.createProxyObject(new PersonServiceBean("xxx"));		
		personService2.save("");
		
		System.out.println("--------------");
		
		//不能出现代理对象交叉的现象，否则前一个的TargetObject会被后一个覆盖。
		personService1.save("");		
		
	}

}
