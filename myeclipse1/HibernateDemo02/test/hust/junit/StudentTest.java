package hust.junit;

import hust.bean.Student;
import hust.bean.Teacher;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StudentTest {
	private static SessionFactory sf = null;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void testTeacher() {
		Student stu = new Student();
		stu.setName("pl");
		stu.setAge(20);
		
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(stu);
		session.getTransaction().commit();
		session.close();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
