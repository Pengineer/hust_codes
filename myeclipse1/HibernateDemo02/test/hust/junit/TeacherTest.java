package hust.junit;


import hust.bean.Teacher;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 一、openSession和getCurrentSession的区别：
 * 1，openSession每次打开的都是新的session，每次事物提交后都要手动close。
 * 2，getCurrentSession是从上下文获取当前session，上下文是由配置文件中的hibernate.current_session_context_class指定，一般使用thread，
 * 	 即从从当前线程中获取session对象（整合spring后由spring管理）。如果上下文中没有，就新建，如果有，则使用之；每次事物提交后会自动关闭session，不需要close。
 *   用途：界定事务边界。
 *
 * 二、对象的三种状态
 * 区别：有没有ID、ID在数据库中有没有、在内存中有没有。
 * new 对象 ——》 transient，对象仅在内存中有
 * transient ——》 save() or saveOrUpdate() ——》persistent，内存，缓存（内存的一个区域），数据库中均有（数据库：由于事务没有提交，因此只能在当前session中看到，其它会话看不到）
 * persistent ——》 evict() or close() or clear() 提交事务，关闭session——》detached，内存，数据库中有，缓存中没有（数据库：事务已提交，其它会话可以查看到）
 * 
 * detached ——》sf.getCurrentSession(); beginTransaction(); update() ——》persistent， Detached状态的对象可以再次与某个Session实例相关联而成为Persistent对象
 * persistent ——》 delete() ——》transient
 * 
 * 三、get() 和 load() 的区别
 * 1，load返回的是代理对象，真正用的对象时才发出SQL语句 ———— 延迟加载；
 * 2，get直接发出SQL，从数据库加载数据，不会延迟。
 * 
 * 四、Update()
 *   如注释。
 *   
 * 五、clear()：清空缓存，与数据库无关，不会发SQL。
 * 
 * 六、flush()：强制将数据库中的内容与缓存中的内容同步，与数据库有关，会发SQL。（默认在事务commit的时候同步）
 */
public class TeacherTest {
	private static SessionFactory sf = null;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void testGetSession() {
		Teacher teacher = new Teacher();
		teacher.setName("zz");
		teacher.setTitle("spro");
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(teacher);
		session.getTransaction().commit();
	}
	
	@Test
	public void testThreeStates() {
		Teacher teacher = new Teacher();
		teacher.setName("zz");
		teacher.setTitle("spro");
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		System.out.println(teacher.getId());  //transient
		session.save(teacher);
		System.out.println(teacher.getId());  //persistent
		session.getTransaction().commit();
		System.out.println(teacher.getId());  //detached
		System.out.println(session);
		
		Session session1 = sf.getCurrentSession();
		session1.beginTransaction();
		session1.delete(teacher);   //detached->persistent->transient
		session1.getTransaction().commit();//处于transient状态的对象在数据库中没有，因此事务提交后数据库中不会有对象的记录
		
		System.out.println(teacher.getId());
		System.out.println(session);
		System.out.println(session == session1);
	}
	
	@Test
	public void testGet() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Teacher teacher = (Teacher) session.get(Teacher.class, 1);//（返回对象，主键）
		session.getTransaction().commit();
		System.out.println(teacher.getName());
	}
	
	@Test
	public void testLoad() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Teacher teacher = (Teacher) session.load(Teacher.class, 1);
		session.getTransaction().commit();
		System.out.println(teacher.getName());//异常：org.hibernate.LazyInitializationException: could not initialize proxy - no Session
	}
	
	@Test
	public void testUpdate() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("update Teacher set name='mm' where id =1"); //HQL发出的SQL只会更新需要更新的字段；如果直接使用update()，那么在没有额外配置的情况下（XML：dynamic-update=true），会更新所有的字段
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
