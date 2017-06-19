package junit.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.hust.service.PersonService;

/*
 * ����ͨ���Զ�ɨ�跽ʽ�Ƿ��ܽ�ͨ��@Componentע�͵�Bean�ɹ�ע��Spring���������ܣ���personService��Ϊ�ա�
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
