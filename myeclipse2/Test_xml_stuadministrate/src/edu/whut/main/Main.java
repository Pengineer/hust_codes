package edu.whut.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.whut.student.Student;
import edu.whut.studentdao.StudentDAO;

/**
 * �˰��Ĺ������������û������ģ���������������һ��
 */
public class Main {
	
	//�����׳����쳣��ҲҪ�ڳ����в�����ΪҪ���û�һ���Ѻõ���ʾ
	
	private static BufferedReader buf;
	
	public static void main(String[]arg) throws IOException{
		try{
			System.out.println("����û���a  ��ѯ�û���b  ɾ���û���c");
			System.out.print("������������ͣ���ĸ����");
			
			buf = new BufferedReader(new InputStreamReader(System.in));
			
			String type = buf.readLine();
			if("a".equals(type)){
				addStudent();
			}else if("b".equals(type)){
				findStudent();
			}else if("c".equals(type)){
				deleteStudent();
			}else{
				System.out.println("��֧�ָù��ܣ�");
			}
		}catch(Exception e){
			System.out.println("�Բ��𣬳����ˣ�");
		}
	}

	private static void deleteStudent() throws IOException {
		try{
			StudentDAO studao = new StudentDAO();
			System.out.print("������Ҫɾ����ѧ��������");
			String name = buf.readLine();
			boolean del = studao.delete(name);
			System.out.println(del);
		}catch(Exception e){
			System.out.println("�Բ��𣬳����ˣ�");
		}
	}

	private static void findStudent() throws IOException {
		try{
			StudentDAO studao = new StudentDAO();
			System.out.print("������Ҫ���ҵ�ѧ��׼��֤�ţ�");
			String examid = buf.readLine();
			Student stu = studao.find(examid);
			if(stu!=null){
				System.out.println("������"+stu.getName());
				System.out.println("׼��֤��"+stu.getExamid());
				System.out.println("���֤��"+stu.getIdcard());
				System.out.println("��ס�أ�"+stu.getLocation());
				System.out.println("�ɼ���"+stu.getScore());
			}else
				System.out.println("����ѧ����Ϣ�����ڣ�");
		}catch(Exception e){
			System.out.println("�Բ��𣬳����ˣ�");
		}
	}

	private static void addStudent() throws IOException{
		try{
			Student student = new Student();
			StudentDAO studao = new StudentDAO();
			
			System.out.print("������ѧ��������");
			String name = buf.readLine();//����ʽ���������س����л����ļ�������ʱ��ֹ
			System.out.print("������׼��֤�ţ�");
			String examid = buf.readLine();
			System.out.print("���������֤�ţ�");
			String idcard = buf.readLine();
			System.out.print("�������ס��ַ��");
			String location = buf.readLine();
			System.out.print("�����뿼�Գɼ���");
			double score = Double.parseDouble(buf.readLine());
			
			student.setName(name);
			student.setExamid(examid);
			student.setIdcard(idcard);
			student.setLocation(location);
			student.setScore(score);
			
			studao.add(student);
			
		}catch(Exception e){
			System.out.println("�Բ��𣬳����ˣ�");
		}
	}
}
