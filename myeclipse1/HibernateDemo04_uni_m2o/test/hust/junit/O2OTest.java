package hust.junit;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * many-to_one单向关联
 * 1，注解：Group-Person
 * 2，xml：Country-Province
 * 
 * 数据库表外键设计原则：在多的那一方加外键
 */

public class O2OTest {
	
private static SessionFactory sf = null;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	//有外键关系的两张表做关联查询，使用第一种（left join）
	@Test
	public void testFind() {
		Session session = sf.openSession();
		session.beginTransaction();
		List list1 = session.createQuery("select p.name from Person p left join p.group").list();
		System.out.println(list1.size());
		List list2 = session.createQuery("select p.name from Person p where p.group.name like '%%'").list();
		System.out.println(list2.size());
		session.getTransaction().commit();
		session.close();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}

}
