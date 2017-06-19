package edu.whut.junit;

import org.junit.Test;

import edu.whut.exception.StudentNotFoundException;
import edu.whut.student.Student;
import edu.whut.studentdao.StudentDAO;



/**
 * ��д�õײ�ģ��ʱ����������ȥд������棬����ȥ���Ը���ģ���Ƿ��������������
 * 
 * ������Ҫ���Եľ������ݿ�ĸ��������Ƿ���������StudentDAO����ķ����������İ�����Ҫ���ԡ�
 */
public class TestClass {

	@Test
	public void testAdd(){
		StudentDAO studao = new StudentDAO();
		Student student = new Student();
		student.setExamid("222");
		student.setIdcard("111");
		student.setLocation("China");
		student.setName("����");
		student.setScore(70);
		studao.add(student);
	}
	@Test
	public void testfind(){
		StudentDAO studao = new StudentDAO();
		studao.find("222");//����ֱ��ʹ��debug as junit ����ǰ�ڴ��д�ϵ㣬watch�������ķ���ֵ
	}
	
	@Test
	public void testDelete() throws StudentNotFoundException{
		StudentDAO studao = new StudentDAO();
		studao.delete("����");//���ɾ��ʧ�ܣ����׳��쳣��Ϣ
	}
	
}
