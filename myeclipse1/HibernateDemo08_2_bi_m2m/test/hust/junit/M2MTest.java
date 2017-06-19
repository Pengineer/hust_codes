package hust.junit;

import hust.bean.Right;
import hust.bean.Role;
import hust.bean.RoleRight;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * many-to-many双向关联
 * （二）情况二：中间表不仅起关系的维护作用，还存有其他字段信息。这时需要将多对多的关系拆分成两个一对多，而且三张表的model都需要写。
 *          （使用注解时，对于o2m/m2o，一般在多的一方做外键关系配置）
 * 
 * 1，注解：以t_role2 —— t_role2_right2 —— t_right2为例。
 * 
 * 2，xml：以t_teacher —— t_teacher_student —— t_student为例。
 * 
 * 补充：如果还是想直接通过A表获取B表数据，还可以加many-to-many，但是需要和自定义的中间表对应。见代码。此时生成的中间表如下：
 * create table t_role2_right2 (
        c_id integer not null,
        c_description varchar(20),
        c_right_id integer not null,
        c_role_id integer not null,
        primary key (c_role_id, c_right_id)
    ) ENGINE=InnoDB
    显然是有问题的，主键错误。这种情况可以通过事先手动建好表来解决，不要通过Hibernate自动生成。另外many-to-many默认是会建一张联合主键的表的，那么我们在修改表并修改
    配置文件hbm2ddl.auto=validate后，通过role的setRights方法直接增删改right是会报错的，因为它默认的主键是联合主键。但是通过Role查询它的right是没有问题。
    因此，个人觉得没必要加这个many-to-many，它仅仅是在查找时方便了一点。（可以将Role里面的setRights()方法执行中抛异常）
 *
 */

public class M2MTest {
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	@Test
	public void insertTest1(){
		SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Role r = new Role();
		r.setName("r2");
		
		Right rt = new Right();
		rt.setName("rt2");
		
		RoleRight rr = new RoleRight();
		rr.setRole(r);
		rr.setRight(rt);
		
		session.save(rt);
		session.save(rr);
		session.save(r);
		
		session.getTransaction().commit();
		sf.close();
	}
	
	@Test
	public void getTest() {
		SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Role role = (Role)session.get(Role.class, 1);
		Set<Right> rs = role.getRights();
		for (Right r : rs) {
			System.out.println(r.getName());
		}
		
		session.getTransaction().commit();
		sf.close();
	}
	
}
