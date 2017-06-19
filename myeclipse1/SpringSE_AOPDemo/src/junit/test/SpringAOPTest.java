package junit.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.hust.service.PersonService;

public class SpringAOPTest {

	/*
	 * 基于注解的AOP测试
	 */
	@Test
	public void test1() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	//	WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(ApplicationContainer.sc);  web工程下获取Spring容器及Bean（通过跟踪源代码可以看出Spring）
		PersonService personService = (PersonService) ctx.getBean("personService");
		personService.save();
	}
	
	
	/*
	 * 基于Xml的AOP测试
	 */
	@Test
	public void test2() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonService personService = (PersonService) ctx.getBean("personService1");
		personService.save();
	}

}
