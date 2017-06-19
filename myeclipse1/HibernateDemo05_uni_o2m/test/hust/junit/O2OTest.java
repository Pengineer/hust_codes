package hust.junit;

import hust.bean.Group;
import hust.bean.Person;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * one-to-many单向关联
 * 1，注解：Group-Person，注解加在
 * 2，xml：Country-Province
 * 
 * 数据库表外键设计原则：在多的那一方加外键
 */

public class O2OTest {
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	@Test
	public void testDatas() {
		SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Group g = (Group)session.get(Group.class,1);
		Set<Person> persons = g.getPerson();
		for(Person p : persons) {
			System.out.println(p.getName());
		}
		session.getTransaction().commit();
		sf.close();
	}

}
