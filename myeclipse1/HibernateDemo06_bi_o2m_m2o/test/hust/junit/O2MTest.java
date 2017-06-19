package hust.junit;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * one-to-many + many-to-one 的双向关联
 * 1，注解：Group-Person
 * 	 a,双向关联的注解有两种写法，以Group-Person为例，Group端为one-to-many，Person端为many-to-one。
 * 	 b,many-to-one如果不指定@joinColumn，就会自动为t_person创建一个group_c_id的外键列，因此一般在many-to-one端使用@joinColumn自定义外键名；
 * 	 c,one-to-many端如果仅仅使用@one-to-many，那么hibernate会生成3张表，当作m-to-m处理，解决方式有两种，详见Group的注解。
 * 		第一种方式指定外键名，但是该外键名必须与many-to-one指定的外键名相同，否则在person表名出现两个不同名的外键同时指向Group；
 * 		第二种方式使用mappedBy表明t_person表已经有指向t_group表的外键，此处不需要再建外键，也不需要再单独建一张关联表；注意mappedBy后接的是属性名，即维护两张表的关系的那个属性名。
 * 
 * 2，xml：Country-Province
 * 	 a,使用xml配置时，在Country端使用one-to-many要用key指定外键名，否则hibernate会在Province的主键上建一个指向Country主键的外键；
 * 	 b,在many-to-one中指定的column名一定要和one-to-many的key指定的column相同，否则会建多个外键（弊端：一旦要改外键名，就需要改两处）
 * 
 * （1）数据库表外键设计原则：在多的那一方加外键
 * 
 * （2）双向关联与单向关联在数据库层面是一样的，只是在java编程时不一样（是否可以由一方获取另一方）。
 * 
 * 
 */

public class O2MTest {
	
	@Test
	public void test(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}

}
