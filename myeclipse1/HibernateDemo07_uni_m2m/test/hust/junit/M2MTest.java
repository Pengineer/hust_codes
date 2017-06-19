package hust.junit;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * many-to-many单向关联
 * 1，注解：以Teacher-Student为例。通过Teacher可以获取他的所有学生，反之不可。
 * 	  在Teacher中有集合属性students，其上的注解形式如下：
 * 	 @ManyToMany
 * 	 @JoinTable(name="t_teacher_student", //指定中间表的表名
			    joinColumns={@JoinColumn(name="t_teacher_id")}, //指定中间表中指向t_teacher的列名（外键）
			    inverseJoinColumns={@JoinColumn(name="t_student_id")}) //指定中间表中指向t_student的列名（外键）
	因为是在Teacher类中，因此joinColumns中是指向Teacher的列名，inverseJoinColumns是指向另一个表的列名。
	之所以joinColumns是复数，是因为有时候有联合主键（非常少）
	
 * 2，xml：以Product-Order为例	
 */

public class M2MTest {
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}

}
