package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Officer;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 将院系和基地均非空的教师分成两个教师
 * 将院系和基地均非空的管理人员分成两个管理人员
 * @author xuhan
 *
 */
public class Fix20120227 extends Execution {

	@Autowired
	private IHibernateBaseDao dao;

	@Override
	protected void work() throws Throwable {
		List<Teacher> teachers = dao.query("select t from Teacher t where t.department is not null and t.institute is not null");
		for (Teacher teacher : teachers) {
			Teacher instTeacher = new Teacher();
			instTeacher.setPerson(teacher.getPerson());
			instTeacher.setPosition(teacher.getPosition());
			instTeacher.setStartDate(teacher.getStartDate());
			instTeacher.setInstitute(teacher.getInstitute());
			instTeacher.setUniversity(teacher.getUniversity());
			instTeacher.setType("兼职人员");
			dao.add(instTeacher);
			
			teacher.setInstitute(null);
		}
		dao.flush();
		dao.clear();

		List<Officer> officers = dao.query("select o from Officer o where o.department is not null and o.institute is not null");
		for (Officer officer : officers) {
			Officer instOfficer = new Officer();
			instOfficer.setPerson(officer.getPerson());
			instOfficer.setPosition(officer.getPosition());
			instOfficer.setStartDate(officer.getStartDate());
			instOfficer.setInstitute(officer.getInstitute());
			instOfficer.setAgency(officer.getAgency());
			instOfficer.setType("兼职人员");
			dao.add(instOfficer);

			officer.setInstitute(null);
		}
		
		
	}

}
