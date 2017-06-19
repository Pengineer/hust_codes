package junit.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.hust.service.PersonService;

/*
 * 测试通过自动扫描方式是否能将通过@Component注释的Bean成功注入Spring容器，若能，则personService不为空。
 * 
 */

public class SpringTest {

	@Test
	public void test() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonService personService = (PersonService) ctx.getBean("personServiceBean");
		System.out.println(personService);
		
		personService.service();
	}

}
