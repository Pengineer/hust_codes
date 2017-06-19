package junit.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import edu.hust.service.PersonService;

public class SpringTest {

	@Test
	public void instanceSpring(){
		
//		BeanFactory container = new XmlBeanFactory(new ClassPathResource("beans.xml")); //��ȡIoC����
//		����һ�п��Խ��Ϊ�������У��������ֻ���Xml���õ�bean����ʽ�ԡ�����ע�������ע�롱��֧���Բ��ã��޷�ע�룬���������һ���ʹ�������ctx������ȡ��ʽ��
//		DefaultListableBeanFactory container = new DefaultListableBeanFactory();  
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(container);  
//		reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
		
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");//��ȡIoC����������Xml�ĵ���ʵ�������е�bean����
		/*	
		PersonService personservice1 = (PersonService) ctx.getBean("personService");
		PersonService personservice2 = (PersonService) ctx.getBean("personService");
		personservice1.save();
		
		//Ĭ��getbean�õ�����ͬһ��ʵ��������scope=singleton��,�����Xml������beanʱ��ָ��scope="prototype"����ôÿ��getbean�õ��Ľ���һ��ȫ�µĶ���
		System.out.println(personservice1 == personservice2);
		*/
		/*
		PersonService personservice = (PersonService) ctx.getBean("personService2");
		personservice.save();
		*/
		
		PersonService personservice = (PersonService) ctx.getBean("personService3");
		personservice.save();
		ctx.close();
		
	}
	
	@Test
	public void resourceLoaderTest() {
		try {
			PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();
			Resource[] res = p.getResources("classpath*:beans.xml");
			for(Resource r : res) {
				System.out.println(r.toString() + " .. " + r.getFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
