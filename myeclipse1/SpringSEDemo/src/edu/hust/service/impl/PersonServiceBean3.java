package edu.hust.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.hust.dao.PersonDao;
import edu.hust.service.PersonService;
/* 在java代码中使用@Autowired或@Resource注解方式进行装配，这两个注解的区别是：@Autowired 默认按类型装配，
 * @Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
 *（1）
 * @Autowired注解是按类型装配依赖对象，默认情况下它要求依赖对象必须存在，如果允许null值，可以设置它required属性为false。
 * 如果我们想使用按名称装配，可以结合@Qualifier注解一起使用。如下：
    @Autowired  @Qualifier("personDaoBean")
    private PersonDao  personDao;
 *（2）
 * @Resource注解和@Autowired一样，也可以标注在字段或属性的setter方法上，但它默认按名称装配。名称可以通过@Resource的
 * name属性指定，如果没有指定name属性，当注解标注在字段上，即默认取字段的名称作为bean名称寻找依赖对象，当注解标注在属性的setter
 * 方法上，即默认取属性名作为bean名称寻找依赖对象。
    @Resource(name=“personDaoBean”)
    private PersonDao  personDao;//用于字段上

 * 注意：如果没有指定name属性，并且按照默认的名称仍然找不到依赖对象时， @Resource注解会回退到按类型装配。但一旦指定了name属性，
 *     就只能按名称装配了。
 *     
 * 补充：1，注解注入方式一般用在复杂类型属性上（对象等）。
 *     2，实际中经常是多种方式联合使用。
 *     3，较@Autowired，推荐使用@Resource，因为@Resource是javaEE自带的，与容器框架无关，而且使用更灵活
 */
public class PersonServiceBean3 implements PersonService {
	@Resource
	private PersonDao personDao;  //在service层注入Dao层的对象（通过注解的方式）
	
	@Resource(name="group")
	Group group1;
	
//	@Resource(name="group")
//	Group group1;
	
	public void save(){
//		personDao.add();
		group1.group();
	}
}
