package hust.junit;

import hust.bean.Right;
import hust.bean.Role;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * many-to-many双向关联
 * （一）情况一：中间表只起两张主表的关系维护作用，即只有两个字段，两个字段均是外键，分别指向两张表，两个字段一起组成联合主键。
 * 		本情况下，不需要建立中间表的model（bean文件）。
 * 
 * 1，注解：以role-right为例。
 * 	 与m2o和o2m的双向关联不同的是，m2m的双向关联是对称的，因此两边的注解可以对调，一边加@JoinTable，另一边加mappedBy。@JoinTable确定具体的维护关系的形式，
 * 	mappedBy表明维护表间关系的任务已由两一张表的某一属性确定。（看具体代码注释）
 * 
 * hibernate自动生成的中间表：
 * create table t_teacher_student (
        c_teacher_id integer not null,
        c_student_id integer not null,
        primary key (c_student_id, c_teacher_id)
    ) ENGINE=InnoDB
    
 * 2，xml：以teacher-student为例。
 * 	在两者的xml文件中配置set集合标签，指明中间表名、指向本model的外键列名以及另一外键列名。注意两个xml文件中的列名要一致。	
 */

public class M2MTest {
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
	@Test
	public void testFunction() {
		SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Role r = (Role)session.get(Role.class, 1);
		Set<Right> rights = r.getRights();
		for (Right right : rights) {
			System.out.println(right.getName());
		}
		session.getTransaction().commit();
		sf.close();
	}
	
	@Test
	public void testFunction2() {
		SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Role r = (Role)session.get(Role.class, 1);
		Set<Right> set = r.getRights();
		Right ri= (Right)session.get(Right.class, 333);
		if (set == null) {
			set = new HashSet<Right>();
		}
		set.add(ri);
		session.getTransaction().commit();
		sf.close();
	}

}
