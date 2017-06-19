package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 同一人员（person_id相同）的teacher表中若有多条数据，至少某条数据具备院系（或基地）所属关系，则他的相同高校的其他院系（或其他基地）任职信息多余，可以删除。
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120711A extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	

	@Override
	protected void work() throws Throwable {
		//设置teacher.divisionName
		while (true) {
			List<Teacher> teachers = dao.query("from Teacher t left join fetch t.department left join fetch t.institute where t.divisionName is null", 0, 10000);
			System.out.println(teachers.size());
			if (teachers.isEmpty()) {
				break;
			}
			for (Teacher teacher : teachers) {
				if (teacher.getDepartment() != null) {
					teacher.setDivisionName(teacher.getDepartment().getName());
				} else if (teacher.getInstitute() != null) {
					teacher.setDivisionName(teacher.getInstitute().getName());
				}
			}
			dao.flush();
			dao.clear();
		}
		
		//删除教师多余的在“其他院系”的任职信息
		String otherDeptName = "其他院系";
		List<Teacher> teachers = dao.query("select t1 from Teacher t1 where t1.department.name = ? and exists (select t2.id from Teacher t2 where t2.person.id = t1.person.id and t2.university.id = t1.university.id and t2.department.name <> ? )", otherDeptName, otherDeptName);
		System.out.println("other teachers size: " + teachers.size());
		for (Teacher teacher : teachers) {
			dao.delete(teacher);
		}
		
	}

}
