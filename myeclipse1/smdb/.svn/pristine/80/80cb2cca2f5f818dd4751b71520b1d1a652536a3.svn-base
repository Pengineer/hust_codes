package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.bean.GeneralMember;
import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 部分教师所属的基地所在学校和教师的学校不同(即: teacher.department.university <> teacher.university)
 * 经调研，这种情况下，教师的"学校"是正确的(但实际上为部级或省级机构)，而院系所在学校错误。
 * 故，按照教师所在机构和院系名称，将该类教师调整为外部专家。
 * @author xuhan
 *
 */
public class Fix20120229 extends Execution {
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		List<Teacher> teachers = dao.query("select t from Teacher t where t.department.university.type <> 3 and t.department.university.type <> 4");
		for (Teacher teacher : teachers) {
			System.err.println(teacher.getPerson().getName() + " " + teacher.getUniversity().getName() + " - " + teacher.getDepartment().getUniversity().getName()+"|"+teacher.getDepartment().getName());
			Expert expert = new Expert();
			expert.setPerson(teacher.getPerson());
			expert.setAgencyName(teacher.getUniversity().getName());
			if (!teacher.getDepartment().getName().equals("其他院系")) {
				expert.setDivisionName(teacher.getDepartment().getName());
			}
			expert.setPosition(teacher.getPosition());
			expert.setType(teacher.getPerson().getExpert().isEmpty() ? "专职人员" : "兼职人员");
			dao.add(expert);
			dao.delete(teacher);
		}
		
		List<GeneralMember> members = dao.query("select mem from GeneralMember mem where mem.university.type <> 3 and mem.university.type <> 4");
		for (GeneralMember member : members) {
			member.getUniversity().getType();
			if (member.getAgencyName() == null) {
				throw new RuntimeException();
			}
			member.setUniversity(null);
			member.setDepartment(null);
			if ("其他院系".equals(member.getDivisionName())) {
				member.setDivisionName(null);
			}
		}
		
		
	}

}
