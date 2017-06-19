package hust.junit;


import hust.bean.Member;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * HQL:
 * （1）使用HQL时，导入的包不能是JPA（javax.persistence）或者其它的jar包，只能是Hibernate的；和注解导入的包不一样。（因为JPA还没有统一HQL）
 * 
 * （2）在HQL中，有外键关联的两个表连接时，join后面不能直接接类名，因为单纯从技术角度讲，有可能在同一个类中，有多个相同连接类型的成员变量，因此需要指定用哪一个成员变量做为连接条件，比如，在Member类中有两个Group属性：
 * 		private Group sportGroup;
 * 		private Group danceGroup;
 * 	       如果不指定属性名，直接使用Group，那么Hibernate是不知到使用哪个连接的。
 * （3）HQL支持子查询。
 * 
 * 
 * 补充，在HQL中不同类型的语句，执行时调用的方法不一样：
 * （1）普通查询：query.list()；//示例
 * （2）唯一性查询：query.uniqueResult()；
 * （3）更新删除：query.executeUpdate();
 * （4）关系查询：createSQLQuery(sql)；//不需要使用JdbcDAO，示例
 */
public class HQLTest {
	private static SessionFactory sf = null;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void beforeClass() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void test() {
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	@Test
	public void testJoin() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Query query = (Query) session.createQuery("select g.name from Member m left join m.group g where m.name='m1'");
		
		List<String> names = query.list();//执行查询，返回List集合
		for (String name : names) {
			System.out.println(name);
		}
		
		session.getTransaction().commit();
	}
	
	@Test
	public void testSQLquery1() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		SQLQuery sqlQuery = session.createSQLQuery("select c_id, c_name from t_member where c_name='m1'");
		List<Object[]> attributes = sqlQuery.list();
		for (Object attribute[] : attributes) {
			int id = (Integer)attribute[0];
			String name = (String)attribute[1];
			
			System.out.println(id + ":" + name);
		}
		
		session.getTransaction().commit();
	}
	
	@Test
	public void testSQLquery2() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		SQLQuery sqlQuery = session.createSQLQuery("select * from t_member where c_name='m1'");
		sqlQuery = sqlQuery.addEntity(Member.class);//将nativeSQL输出转换成类对象
		
		List<Member> members = sqlQuery.list();
		for (Member member : members) {
			System.out.println(member.getId() + ":" + member.getName());
		}
		
		session.getTransaction().commit();
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
