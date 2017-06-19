package hust.junit;


import hust.bean.Company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 树状结构设计：（smdb-SystenOption）
 * 核心：在同一个类中使用many-to-one和one-to-many，而且都是关联自身.
 */
public class CompanyTest {
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
	public void testSave() {
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Company c1 = new Company();
		c1.setName("总公司");
		Company c2 = new Company();
		c2.setName("分公司1");
		Company c3 = new Company();
		c3.setName("分公司2");
		Company c4 = new Company();
		c4.setName("分公司2_1");
		
		c2.setParentCompany(c1);
		c3.setParentCompany(c1);
		c4.setParentCompany(c3);
		
		session.save(c1);
		session.save(c2);
		session.save(c3);
		session.save(c4);
		
		session.getTransaction().commit();
	}
	
	@Test
	public void testRead() {
		testSave();
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		
		Company company = (Company)session.get(Company.class, 1);
		print(company, 0);
		
		session.getTransaction().commit();
	}
	
	public void print(Company company, int level) {
		String preStr = "";
		for (int i=0; i<level; i++) {
			preStr += "--";
		}
		System.out.println(preStr + company.getName());
		for (Company c : company.getChildrenCompanies()) {
			print(c, level + 1);
		}
	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
