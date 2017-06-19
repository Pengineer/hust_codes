package hust.junit;

import hust.bean.Teacher;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TeacherTest {
	private static SessionFactory sf = null;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void testTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("zz");
		teacher.setTitle("spro");
		
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(teacher);
		session.getTransaction().commit();
		session.close();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
