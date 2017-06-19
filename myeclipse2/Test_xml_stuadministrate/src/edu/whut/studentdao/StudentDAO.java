package edu.whut.studentdao;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.whut.exception.StudentNotFoundException;
import edu.whut.student.Student;
import edu.whut.utils.XmlUtils;

public class StudentDAO {
	/*增加学生信息*/
	public void add(Student stu) {
		try {
			Document document = XmlUtils.getDocument();
			Element newstu = document.createElement("student");
			newstu.setAttribute("idcard", stu.getIdcard());
			newstu.setAttribute("examid", stu.getExamid());
			
			Element name = document.createElement("name");
			name.setTextContent(stu.getName());
			newstu.appendChild(name);
			
			Element location = document.createElement("location");
			location.setTextContent(stu.getLocation());
			newstu.appendChild(location);
			
			Element score = document.createElement("score");
			score.setTextContent(stu.getScore()+"");
			newstu.appendChild(score);
			
			document.getElementsByTagName("exam").item(0).appendChild(newstu);//易忘
			XmlUtils.write2XML(document);
			System.out.println("添加成功！");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}
	/*查找学生信息*/
	public Student find(String examid){
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("student");
			for(int i=0 ; i<list.getLength() ; i++){
				Element stuelement = (Element)list.item(i);
				if(stuelement.getAttribute("examid").equals(examid)){
					//找到对应学生，返回该学生的信息
					Student student = new Student();
					student.setExamid(examid);
					student.setIdcard(stuelement.getAttribute("idcard"));
					student.setLocation(stuelement.getElementsByTagName("location").item(0).getTextContent());
					student.setName(stuelement.getElementsByTagName("name").item(0).getTextContent());
					student.setScore(Double.parseDouble(stuelement.getElementsByTagName("score").item(0).getTextContent()));
					
					return student;
				}
			}		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 		
		return null;		
	}
	/*删除学生信息*/
	public boolean delete(String name) throws StudentNotFoundException{
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("name");
			for(int i=0 ; i<list.getLength() ; i++){
				if(list.item(i).getTextContent().equals(name)){
					//通过节点的爷爷删除节点的爸爸
					list.item(i).getParentNode().getParentNode().removeChild(list.item(i).getParentNode());
					XmlUtils.write2XML(document);
					return true;
				}
			}
			throw new StudentNotFoundException(name + "不存在");
		}catch(StudentNotFoundException e){
			throw e;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
