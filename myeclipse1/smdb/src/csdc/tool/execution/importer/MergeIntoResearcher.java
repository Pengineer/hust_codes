package csdc.tool.execution.importer;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.bcel.internal.generic.Select;

import csdc.bean.Agency;
import csdc.bean.Expert;
import csdc.bean.Researcher;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

public class MergeIntoResearcher extends Importer {
	

	@Override
	public void work() throws Exception {
		addStudent();
		addExpert();
		addTeacher();
	}

	private void addTeacher() {
		List<Teacher> teachers = dao.query("from Teacher");
		for (Teacher t : teachers) {
			Researcher researcher = new Researcher();
			researcher.setType(t.getType());
			researcher.setPerson(t.getPerson());
			researcher.setStartDate(t.getStartDate());
			researcher.setEndDate(t.getEndDate());
			researcher.setInstitute(t.getInstitute());
			researcher.setDepartment(t.getDepartment());
			researcher.setStudentCardNumber(t.getStaffCardNumber());
			researcher.setUniversity(t.getUniversity());
			researcher.setAgencyName(t.getAgencyName());
			researcher.setDivisionName(t.getDivisionName());
			researcher.setCreateType(t.getCreateType());
			dao.add(t);		
		}
		
	}

	private void addExpert() {
		List<Expert> experts = dao.query("from Expert");
		for (Expert e : experts) {
			Researcher researcher = new Researcher();
			researcher.setType(e.getType());
			researcher.setPerson(e.getPerson());
			researcher.setAgencyName(e.getAgencyName());
			researcher.setDivisionName(e.getDivisionName());
			dao.add(e);	
		}		
	}

	private void addStudent() {
		List<Student> students = dao.query("from Student");
		for (Student s : students) {
			Researcher researcher = new Researcher();
			researcher.setType(s.getType());
			researcher.setPerson(s.getPerson());
			researcher.setStartDate(s.getStartDate());
			researcher.setEndDate(s.getEndDate());
			researcher.setInstitute(s.getInstitute());
			researcher.setDepartment(s.getDepartment());
			researcher.setStudentCardNumber(s.getStudentCardNumber());
			researcher.setUniversity(s.getUniversity());
			researcher.setAgencyName(s.getAgencyName());
			researcher.setDivisionName(s.getDivisionName());
			dao.add(s);
		}
	}
	
}


