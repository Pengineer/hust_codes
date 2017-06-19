package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.InstpMember;
import csdc.bean.GeneralMember;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.PostMember;
import csdc.bean.ProjectMember;
import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 同一人员（person_id相同）的teacher、officer有且仅有一条为专职人员
 * 
 * @author xuhan
 *
 */
@Component
public class Fix20120716 extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	

	@Override
	protected void work() throws Throwable {
		String s = "0123456789abcdef";
		for (int id = 0; id <= 0xff; id++) {
			List<Person> people = dao.query("select p from Person p left join fetch p.teacher left join fetch p.officer left join fetch p.projectMember where p.id like '%" + s.charAt(id / 16) + s.charAt(id % 16) + "' order by p.id");
			System.out.println(id + " - " + people.size());
			Person lastPerson = null;
			for (int i = 0; i < people.size(); i++) {
				Person person = people.get(i);
				if (person == lastPerson) {
					continue;
				}
				lastPerson = person;
				
				fixTeacher(person);
				fixOfficer(person);
				

			}
			dao.flush();
			dao.clear();
		}
	}
	
	private void fixTeacher(Person person) {
		if (person.getTeacher().isEmpty()) {
			return;
		}
//		System.out.println(person.getId() + " " + person.getTeacher().size() + " " + person.getName());

		//如有成员变更，只以最新一批的成员作参照
		List<ProjectMember> newestMember = new ArrayList<ProjectMember>();
		for (ProjectMember projectMember : person.getProjectMember()) {
			boolean sb = false;
			String applicationId = getApplicationId(projectMember);
			for (ProjectMember temp : person.getProjectMember()) {
				String tempApplicationId = getApplicationId(temp);
				//所属项目相同，组号又非最大，弃之
				if (applicationId.equals(tempApplicationId) &&
					projectMember.getGroupNumber() != null &&
					temp.getGroupNumber() != null &&
					projectMember.getGroupNumber() < temp.getGroupNumber()) {
					sb = true;
					break;
				}
			}
			if (!sb) {
				newestMember.add(projectMember);
			}
		}

		double maxMatchLevel = -1.0;
		Teacher bestMatchedTeacher = null;
		for (Teacher teacher : person.getTeacher()) {
			double matchLevel = 0.0;
			for (ProjectMember projectMember : newestMember) {
				if (projectMember.getDepartment() != null &&
					teacher.getDepartment() != null &&
					projectMember.getDepartment().getId().equals(teacher.getDepartment().getId())) {
					matchLevel += 2.0 / projectMember.getMemberSn();		
				} else if (projectMember.getInstitute() != null &&
					teacher.getInstitute() != null &&
					projectMember.getInstitute().getId().equals(teacher.getInstitute().getId())) {
					matchLevel += 2.0 / projectMember.getMemberSn();		
				} else if (projectMember.getUniversity().getId().equals(teacher.getUniversity().getId())) {
					matchLevel += 1.0 / projectMember.getMemberSn();
				}
			}
			if (matchLevel > maxMatchLevel) {
				maxMatchLevel = matchLevel;
				bestMatchedTeacher = teacher;
			}
		}
		
		if (bestMatchedTeacher != null) {
			for (Teacher teacher : person.getTeacher()) {
				if (teacher.getType().contains("专职")) {
					teacher.setType("兼职人员");
				}
			}
			bestMatchedTeacher.setType("专职人员");
		}
	}
	
	private void fixOfficer(Person person) {
		if (person.getOfficer().isEmpty()) {
			return;
		}
		Officer firstOfficer = person.getOfficer().iterator().next();
		
		for (Officer officer : person.getOfficer()) {
			if (officer.getType().contains("专职")) {
				return;
			}
		}
		firstOfficer.setType("专职人员");
	}

	private String getApplicationId(ProjectMember menber) {
		if (menber instanceof GeneralMember) {
			return ((GeneralMember)menber).getApplication().getId();
		}
		if (menber instanceof InstpMember) {
			return ((InstpMember)menber).getApplication().getId();
		}
		if (menber instanceof PostMember) {
			return ((PostMember)menber).getApplication().getId();
		}
		return null;
	}
}
