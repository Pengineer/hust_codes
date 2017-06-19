package junit.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.hust.service.PersonService;

public class SpringAOPTest {

	/*
	 * ����ע���AOP����
	 */
	@Test
	public void test1() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	//	WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(ApplicationContainer.sc);  web�����»�ȡSpring������Bean��ͨ������Դ������Կ���Spring��
		PersonService personService = (PersonService) ctx.getBean("personService");
		personService.save();
	}
	
	
	/*
	 * ����Xml��AOP����
	 */
	@Test
	public void test2() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonService personService = (PersonService) ctx.getBean("personService1");
		personService.save();
	}

}
