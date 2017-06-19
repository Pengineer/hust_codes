package edu.whut.junit;

import org.junit.Test;

import edu.whut.exception.StudentNotFoundException;
import edu.whut.student.Student;
import edu.whut.studentdao.StudentDAO;



/**
 * 当写好底层模块时，不是立即去写顶层界面，而是去测试各个模块是否可以正常工作。
 * 
 * 本工程要测试的就是数据库的各个操作是否正常，即StudentDAO里面的方法，其它的包不需要测试。
 */
public class TestClass {

	@Test
	public void testAdd(){
		StudentDAO studao = new StudentDAO();
		Student student = new Student();
		student.setExamid("222");
		student.setIdcard("111");
		student.setLocation("China");
		student.setName("王五");
		student.setScore(70);
		studao.add(student);
	}
	@Test
	public void testfind(){
		StudentDAO studao = new StudentDAO();
		studao.find("222");//可以直接使用debug as junit ，提前在此行打断点，watch整条语句的返回值
	}
	
	@Test
	public void testDelete() throws StudentNotFoundException{
		StudentDAO studao = new StudentDAO();
		studao.delete("王五");//如果删除失败，会抛出异常信息
	}
	
}
