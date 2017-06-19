package hust.junit;


import hust.bean.School;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * 二级缓存配置文件，ehcache.xml：
 * 一、文件解释
 * <defaultCache
        maxElementsInMemory="10000"     //缓存中最多可缓存的对象数  
        eternal="false"                 //缓存中的对象可以被清除  （一般不会设置为true，可以将存活时间设置长一点）
        timeToIdleSeconds="120"         //缓存中某一对象120s时间内没有被使用，则自动清除
        timeToLiveSeconds="1200"        //缓存中的某一对象已经存在1200s后也会被自动清除（默认是120s，和空闲等待时间一样，显然不合理）
        overflowToDisk="true"           //如果缓存溢出，则放到磁盘上面（临时目录由<diskStore path="java.io.tmpdir"/>指定）
   />
    
  	在项目中如果不是要求很高，就不要开启二级缓存，主要是面试中用的多。
  	
 * 二、适合放在二级缓存中的数据：
  	（1）经常被访问；（2）数据量不大；（3）改动很少
  	
 * 三、设置某一类对象可以放在缓存中的注解方式：
  	@Cache(usage=CacheConcurrencyStrategy.WRITE_READ)
  	
 * 四、二级缓存的几个注意事项：
 * （1）load默认使用二级缓存（前提是在对应的类上加@Cache注解），iterator默认使用二级缓存；
 * （2）list默认往二级缓存里面加数据库，但是查询时不使用。
 * （3）如果要query用二级缓存，需打开查询缓存（当使用的查询SQL完全一样时，使用二级缓存：执行两次list，但是不会发两条SQL语句）：
 * 		<property name="hibernate.cache.use_query_cache">true</property>
 * 		然后调用Query的setCachable(true)方法指明使用二级缓存。个人感觉查询缓存没意义，因为数据库本身就设置有sql级别的缓存，当两次SQL相同时，会直接从数据库的
 *   缓存中读取数据。
 * 
 * 	
 */
public class CacheTest {
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
	
	@Test //不使用二级缓存：发出两条sql
	public void testCache1() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		List<School> s1 = session.createQuery("select s from School s where s.num<20").list();
		List<School> s2 = session.createQuery("select s from School s where s.num<20").list();
		
		session.getTransaction().commit();
	}
	
	@Test //使用二级缓存，但是没有打开查询缓存，list默认是不会使用二级缓存的，因此发出两条sql
	public void testCache2() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		List<School> s1 = session.createQuery("select s from School s where s.num<20").setCacheable(true).list();
		List<School> s2 = session.createQuery("select s from School s where s.num<20").setCacheable(true).list();
		
		session.getTransaction().commit();
	}
	
	@Test //使用二级缓存，同时打开查询缓存，list默认是不会使用二级缓存的，因此发出两条sql
	public void testCache3() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		List<School> s1 = session.createQuery("select s from School s where s.num<20").setCacheable(true).list();
		List<School> s2 = session.createQuery("select s from School s where s.num<20").setCacheable(true).list();
		
		session.getTransaction().commit();
	}
	
	@Test //load会默认使用二级缓存，不需打开查询缓存
	public void testCache4() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		School s1 = (School)session.load(School.class, 1);
		s1.getName();
		
		School s2 = (School)session.load(School.class, 1);
		s2.getName();
		
		session.getTransaction().commit();
	}
	
	@Test //load会默认使用二级缓存，即使在不同的session中（注意需要在School类上加@Cache）
	public void testCache5() {
		Session session1 = sf.getCurrentSession();
		session1.beginTransaction();
		School s1 = (School)session1.load(School.class, 1);
		s1.getName();
		session1.getTransaction().commit();
		
		Session session2 = sf.getCurrentSession();
		session2.beginTransaction();
		School s2 = (School)session2.load(School.class, 1);
		s2.getName();
		session2.getTransaction().commit();
	}

	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
