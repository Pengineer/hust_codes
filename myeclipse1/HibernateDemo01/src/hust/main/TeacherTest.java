package hust.main;

import hust.bean.Teacher;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class TeacherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Teacher teacher = new Teacher();
		teacher.setId(1);
		teacher.setName("ww");
		teacher.setTitle("pro");
		
		Configuration conf = new AnnotationConfiguration();//使用AnnotationConfiguration配置对象
		SessionFactory sessionFactory = conf.configure().buildSessionFactory();//前部分解析hibernate的配置文件，后部分是创建sessionFactory
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(teacher);
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
