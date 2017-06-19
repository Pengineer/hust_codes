package hust.junit;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 继承映射：
 * hibernate提供了3中继承映射方式：SINGLE_TABLE、TABLE_PER_CLASS、JOINED。比如父类A表，子类B表和C表。
 * （1）SINGLE_TABLE：所有的信息都记录在一张表中，不同的子类通过一个字段区分；不同的子类bean是不需要id的，只需要加一个区分器和其特有的信息；子类需要extend父类。
 * （2）TABLE_PER_CLASS：会产生3张表，父类包含所有的信息，不同的子类拥有共有信息和其特有信息；不能使用自动生成的ID策略，因为不同的子类主键id必须保证不一样，
 * 						使用table生成策略；不同的子类bean是不需要id的，只需要其特有信息；子类需要extend父类。     （用的实在太少，仅仅了解而已）
 * （3）JOINED：最简单的一种映射。生成3张表，子表的主键来自父表；子表的bean只记录其特有信息。见示例代码。
 */
public class InheritTest {
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
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
