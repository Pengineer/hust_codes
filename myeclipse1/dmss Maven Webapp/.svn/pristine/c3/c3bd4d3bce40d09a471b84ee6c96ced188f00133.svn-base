package org.cdsc;

import org.csdc.dao.IBaseDao;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {
	protected IBaseDao baseDao;
	protected ApplicationContext ac;
	
	@Before
	public void setUp() throws Exception {
		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		baseDao = (IBaseDao) ac.getBean("baseDao");
	}
	
	@After
	public void tearDown() throws Exception {

	}
}
