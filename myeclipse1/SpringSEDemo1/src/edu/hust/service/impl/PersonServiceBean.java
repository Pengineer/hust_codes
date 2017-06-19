package edu.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;

/*
 * Spring检测到Bean的@Component，就会将该Bean实例化并且交由Spring容器管理（可以通过Spring器获取该Bean的实例对象）
 * 
 * 使用@Component给容器注入对象后，由于没有了Xml配置<bean>时的id，那么在getBean(String beanName)获取该Bean时，
 * beanName的默认取值是将class的类名第一个字母小写。当然我们可以在@Component后加上这个id。
 * 为了统一规范，我们在Xml中配置<bean>时，id也是一般这么来写的。
 */

@Component   //可以加 @Scope("prototype")来指定每次getBean得到的是一个全新的实例对象
public class PersonServiceBean implements PersonService {
	
	/*
	 * 如果PersonDao或则其实现子类被@Component注释或则在Xml中有所配置(已被交由Spring容器管理)，那么就可以使用@Autowired给本类的personDao属性注入实例对象。
	 */
	@Autowired
	private PersonDao personDao;
	
	public void service(){
		personDao.add();
	}
}
