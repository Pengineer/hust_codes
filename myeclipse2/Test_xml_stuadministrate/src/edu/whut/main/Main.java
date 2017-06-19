package edu.whut.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.whut.student.Student;
import edu.whut.studentdao.StudentDAO;

/**
 * 此包的功能是用来和用户交互的，因此是最后完整的一步
 */
public class Main {
	
	//尽管抛出了异常，也要在程序中捕获，因为要给用户一个友好的提示
	
	private static BufferedReader buf;
	
	public static void main(String[]arg) throws IOException{
		try{
			System.out.println("添加用户：a  查询用户：b  删除用户：c");
			System.out.print("请输入操作类型（字母）：");
			
			buf = new BufferedReader(new InputStreamReader(System.in));
			
			String type = buf.readLine();
			if("a".equals(type)){
				addStudent();
			}else if("b".equals(type)){
				findStudent();
			}else if("c".equals(type)){
				deleteStudent();
			}else{
				System.out.println("不支持该功能！");
			}
		}catch(Exception e){
			System.out.println("对不起，出错了！");
		}
	}

	private static void deleteStudent() throws IOException {
		try{
			StudentDAO studao = new StudentDAO();
			System.out.print("请输入要删除的学生姓名：");
			String name = buf.readLine();
			boolean del = studao.delete(name);
			System.out.println(del);
		}catch(Exception e){
			System.out.println("对不起，出错了！");
		}
	}

	private static void findStudent() throws IOException {
		try{
			StudentDAO studao = new StudentDAO();
			System.out.print("请输入要查找的学生准考证号：");
			String examid = buf.readLine();
			Student stu = studao.find(examid);
			if(stu!=null){
				System.out.println("姓名："+stu.getName());
				System.out.println("准考证："+stu.getExamid());
				System.out.println("身份证："+stu.getIdcard());
				System.out.println("居住地："+stu.getLocation());
				System.out.println("成绩："+stu.getScore());
			}else
				System.out.println("所查学生信息不存在！");
		}catch(Exception e){
			System.out.println("对不起，出错了！");
		}
	}

	private static void addStudent() throws IOException{
		try{
			Student student = new Student();
			StudentDAO studao = new StudentDAO();
			
			System.out.print("请输入学生姓名：");
			String name = buf.readLine();//阻塞式，当遇到回车换行或者文件结束符时终止
			System.out.print("请输入准考证号：");
			String examid = buf.readLine();
			System.out.print("请输入身份证号：");
			String idcard = buf.readLine();
			System.out.print("请输入居住地址：");
			String location = buf.readLine();
			System.out.print("请输入考试成绩：");
			double score = Double.parseDouble(buf.readLine());
			
			student.setName(name);
			student.setExamid(examid);
			student.setIdcard(idcard);
			student.setLocation(location);
			student.setScore(score);
			
			studao.add(student);
			
		}catch(Exception e){
			System.out.println("对不起，出错了！");
		}
	}
}
