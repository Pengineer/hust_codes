package junit.test;

import org.junit.Test;

import edu.hust.jdkproxy.JDKProxyFactory;
import edu.hust.service.PersonService;
import edu.hust.service.impl.PersonServiceBean;

public class JDKProxyTest {

	@Test
	public void test() {
		JDKProxyFactory factory = new JDKProxyFactory();
		
		//���ɵĴ������������Ǵ������͵����ࣨһ����Ŀ�����ӿ����ͽ��գ�
		System.out.println(factory.createProxyObject(new PersonServiceBean()).getClass().getSuperclass());
		PersonService personService1 = (PersonService) factory.createProxyObject(new PersonServiceBean());		
		personService1.save("");//����invoke()������
		
		System.out.println("--------------");
		
		PersonService personService2 = (PersonService) factory.createProxyObject(new PersonServiceBean("xxx"));		
		personService2.save("");
		
		System.out.println("--------------");
		
		//���ܳ��ִ�����󽻲�����󣬷���ǰһ����TargetObject�ᱻ��һ�����ǡ�
		personService1.save("");		
		
	}

}
