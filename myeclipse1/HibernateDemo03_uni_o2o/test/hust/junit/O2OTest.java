package hust.junit;

import hust.bean.Husband;
import hust.bean.Wife;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * 单向One-To-One的表间关系
 * 1，使用注解
 * husband-wife：一个husband只能有一个wife。（测试发现必须在@JoinColumn在加unique=true，否则就是many-to-one）
 * 
 * 2，使用xml
 * student-studentIDcard：一个学生对应一个学生证，使用<many-to-one>标签 + unique=true的属性。
 * hibernate自动生成的约束语句如下：
 * alter table t_student add constraint UK_spjat6q20qb1p20l0np5md1q7  unique (c_studentIdcard_id)
 * alter table t_student add constraint FK_spjat6q20qb1p20l0np5md1q7 foreign key (c_studentIdcard_id) references t_studentIDcard (c_id)
 * 它与many-to-one的区别就是在外键上加了唯一性约束。
 * 
 */

public class O2OTest {
	
	private static SessionFactory sf = null;
	
	/*@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}*/

	@Test
	public void test() {
		/*Wife w = new Wife();
		w.setName("lyf");
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();*/
		
		Husband h =new Husband();
		h.setName("abcdaf");
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Wife w = (Wife)session.get(Wife.class, 1);
		h.setWife(w);
		session.save(h);
		session.getTransaction().commit();

	}
	
	@Test
	public void testStudent(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	/*@AfterClass
	public static void afterClass() {
		sf.close();
	}*/
}
