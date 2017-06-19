package hust.main;

import hust.bean.Student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student student = new Student();
		student.setId(1);
		student.setName("zs");
		student.setAge(20);
		
		Configuration conf = new Configuration();
		SessionFactory sessionFactory = conf.configure().buildSessionFactory();//前部分解析hibernate的配置文件，后部分是创建sessionFactory
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(student);
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
