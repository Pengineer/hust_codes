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
		
//		BeanFactory container = new XmlBeanFactory(new ClassPathResource("beans.xml")); //获取IoC容器
//		上面一行可以解读为下面三行，但是这种基于Xml配置的bean管理方式对“基于注解的依赖注入”的支持性不好（无法注入，报错），因此一般会使用下面的ctx容器获取方式。
//		DefaultListableBeanFactory container = new DefaultListableBeanFactory();  
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(container);  
//		reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
		
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");//获取IoC容器：解析Xml文档，实例化所有的bean对象
		/*	
		PersonService personservice1 = (PersonService) ctx.getBean("personService");
		PersonService personservice2 = (PersonService) ctx.getBean("personService");
		personservice1.save();
		
		//默认getbean得到的是同一个实例化对象（scope=singleton）,如果在Xml中申明bean时，指明scope="prototype"，那么每次getbean得到的将是一个全新的对象
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
