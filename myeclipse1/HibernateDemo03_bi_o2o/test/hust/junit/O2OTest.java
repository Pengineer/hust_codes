package hust.junit;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/**
 * 双向One-To-One
 * 1，使用注解：在两个bean文件中引入双方对象，在被指向的对象（Wife）的husband属性的One-To-One标签后加上属性mappedBy="wife"。
 * 2，使用xml：在两个bean文件中引入双方对象，在被指向的对象（StudentIdcard）的xml中加上One-To-One标签和property-ref属性（Student中使用的是many-To-One），
 *   如果不加就会在。
 *   
 * 双向关联和单向关联在数据库层面没有区别，只是在Java代码上有区别（是否可以通过一方获取另一方：单向编程和双向编程）
 * 
 * 一对一在实际中是很少的，如果是一对一就可以放在一张表里面。
 */

public class O2OTest {
	
	@Test
	public void testStudent(){
		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);
	}
	
}
