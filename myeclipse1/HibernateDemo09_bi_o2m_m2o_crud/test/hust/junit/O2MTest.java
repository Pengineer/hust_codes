package hust.junit;

import hust.bean.Group;
import hust.bean.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 双向关联的又一规律：加cascade属性（另一规律：在少的一方加mappedBy）。
 * 
 * cascade只能影响c-d-u操作，而不能影响r(读)操作，读由另一个属性fetch来管理。
 * 
 * fetch:
 * （1）get方式
 * 	 FetchType.EAGER，不管用到级联对象没有，都直接级联取出，即使session关闭，也可以取出级联对象（缓存）。
 *   FetchType.LAZY，当用到级联的对象时，再发SQL语句取出，如果用到级联对象之前session关闭，那么再通过本对象去取，就会抛出LazyInitializationException：no session。
 * （2）load方式
 * 	 此方式下，不管哪种模式必须在session关闭之前获取对象，因为load获取的是代理对象。
 * 	默认情况下，m2o是eager，o2m是lazy，因此，一般情况下，用默认的即可。（可以两遍都设置lazy，但是不要两遍都设置eager，否则会多发一些重复的SQL语句）
 * 	同样，在xml中为了防止多发一些重复的无效SQL，会在set标签上加一个inverse="true"属性，即关联关系在对方那里设置，取我就不用取对方，取对方必须要取我。
 * 
 * 删除：cascade=all的时候，级联增加和更新没什么问题，但是删除就会有问题，比如我要删除p1，它关联了g1，那么g1就会被删除，进一步的，如果g1关联了p2，那么p2也会被删除，
 * 也就是说，我删p1，会级联的把p2也删除了，这显然不合理；如果是删除根节点，那么保留的子节点可能就是垃圾数据，可能又需要级联。有两种解决方案：
 * （1）破坏p1和g1的关系，set(null)；
 * （2）使用HQL删除，可以避免级联删除。
 * 
 * 使用delete方法之前一定要确认级联关系，如果不确定，可以使用如下代码：
   public void execute(final String statement, final Map paraMap) {
		Query query = getSession().createQuery(statement);
		if (paraMap != null) {
			query.setProperties(paraMap);
		}
		query.executeUpdate();
	}
	
	
	补充（了解即可，用的极少）：在集合映射中，除了用set外，还可以用list和map
	（1）用list时，由于list是可以排序的，因此，可以使用一个注解@OrderBy("column ASC")；
	（2）用map时，值还是待获取的对象，键一般是待获取对象的某一个属性，由于map的键具有唯一性，因此map的值一般使用主键id，也是使用一个注解@MapKey(name="id")。
 */

public class O2MTest {
	
	private static SessionFactory sf = null;
	
	@BeforeClass
	public static void before() {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	@Test
	public void cascadeSave() {
		Group g = new Group();
		g.setName("g2");
		
		Person p = new Person();
		p.setName("p2");
		p.setGroup(g);
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		g.getPerson().add(p);
		session.save(g);  //级联储存，需要在Person的ManyTOOne上加cascade属性
		session.getTransaction().commit();
	}
	
	@Test //级联删除
	public void deleteTest1() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Person person = (Person)session.get(Person.class, 1);
		session.delete(person);
		session.getTransaction().commit();
	}
	
	@Test //破除级联关系
	public void deleteTest2() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Person person = (Person)session.get(Person.class, 1);
		person.setGroup(null);
		session.delete(person);
		session.getTransaction().commit();
	}
	
	@Test //使用HQL删除，可以避免级联
	public void deleteTest3() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from Person where id=1").executeUpdate();
		session.getTransaction().commit();
	}
	
	@AfterClass
	public static void after() {
		sf.close();
	}

}
