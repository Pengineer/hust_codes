package csdc.tool.execution.finder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.tool.Tool;

/**
 * [学校系统人员]查找辅助类<br>
 * NOTE:该类假定同校同名的是同一个人，以此为判重依据
 * @author xuhan
 *
 */
@Component
public class UnivPersonFinder {
	
	/**
	 * 本类专用的person辅助类
	 * @author xuhan
	 *
	 */
	private class PersonTemp {
		public String id;	//person.id
		public List<String> officerIds  = new ArrayList<String>();	//officer.id列表
		public List<String> teacherIds  = new ArrayList<String>();	//teacher.id列表
		public List<String> studentIds  = new ArrayList<String>();	//student.id列表
		
		public PersonTemp(){};
		public PersonTemp(String id) {
			this.id = id;
		};
	}
	
	/**
	 * [学校代码+姓名] -> [PersonTemp]
	 */
	private Map<String, PersonTemp> pMap;
	
	//////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		pMap = null;
		tool.reset();
	}
	
	/**
	 * 根据人名、机构(PO)，返回一个officer(PO)。若不存在，则新增一个返回。
	 */
	public Officer findOfficer(String personName, Agency agency) {
		return findOfficer(personName, agency, null, null);
	}

	/**
	 * 根据人名、院系(PO)，返回一个officer(PO)。若不存在，则新增一个返回。
	 */
	public Officer findOfficer(String personName, Department department) {
		return findOfficer(personName, department.getUniversity(), department, null);
	}

	/**
	 * 根据人名、基地(PO)，返回一个officer(PO)。若不存在，则新增一个返回。
	 */
	public Officer findOfficer(String personName, Institute institute) {
		return findOfficer(personName, institute.getSubjection(), null, institute);
	}
	
	/**
	 * 根据人名、机构(PO)、院系(PO)、基地(PO)，返回一个officer(PO)。若不存在，则新增一个返回。
	 */
	public Officer findOfficer(String personName, Agency agency, Department department, Institute institute) {
		if (agency.getType() != 3 && agency.getType() != 4) {
			throw new RuntimeException("传入的agency[" + agency.getName() + "]不是学校!");
		}
		if (department != null && institute != null) {
			throw new RuntimeException("一个管理人员不能同时从属于院系和基地!");
		}
		PersonTemp person = findPerson(personName, agency);
		
		for (String officerId : person.officerIds) {
			Officer officer = (Officer) dao.query(Officer.class, officerId);
			if (officer.getAgency().getId().equals(agency.getId())) {
				if (department != null) {
					if (officer.getDepartment() != null && department.getId().equals(officer.getDepartment().getId())) {
						return officer;
					}
				} else if (institute != null) {
					if (officer.getInstitute() != null && institute.getId().equals(officer.getInstitute().getId())) {
						return officer;
					}
				} else {
					if (officer.getDepartment() == null && officer.getInstitute() == null) {
						return officer;
					}
				}
			}
		}
		
		Officer officer = new Officer();
		officer.setType(person.officerIds.isEmpty() ? "专职人员" : "兼职人员");
		officer.setAgency(agency);
		officer.setDepartment(department);
		officer.setInstitute(institute);
		officer.setPerson((Person) dao.query(Person.class, person.id));
		
		String officerId = (String) dao.add(officer);
		person.officerIds.add(officerId);

		return officer;
	}
	
	/**
	 * 根据人名、高校(PO)，返回一个teacher(PO)。<br>
	 * 寻找该校任意一个姓名符合的teacher返回。<br>
	 * 若找不到，则新增之， 放进该校的“其他院系”里。
	 */
	public Teacher findTeacher(String personName, Agency university) {
		return findTeacher(personName, university, null, null);
	}

	/**
	 * 根据人名、院系(PO)，返回一个teacher(PO)。<br>
	 * 若找不到，则新增之， 放进该院系。
	 */
	public Teacher findTeacher(String personName, Department department) {
		return findTeacher(personName, department.getUniversity(), department, null);
	}

	/**
	 * 根据人名、基地(PO)，返回一个teacher(PO)。<br>
	 * 若找不到，则新增之， 放进该基地。
	 */
	public Teacher findTeacher(String personName, Institute institute) {
		return findTeacher(personName, institute.getSubjection(), null, institute);
	}

	/**
	 * 根据人名、高校(PO)、院系(PO)、基地(PO)，返回一个teacher(PO)。<br>
	 * 若传入的院系和基地都为空，则寻找该校任意一个名字符合的teacher返回。<br>
	 * 若找不到，则新增之。<br>
	 * 若department和institute均为空，则将此人放进该校的“其他院系”里。
	 */
	public Teacher findTeacher(String personName, Agency university, Department department, Institute institute) {
		if (university.getType() != 3 && university.getType() != 4) {
			throw new RuntimeException("传入的agency[" + university.getName() + "]不是学校!");
		}
		if (department != null && institute != null) {
			throw new RuntimeException("一个教师不能同时从属于院系和基地!");
		}
		PersonTemp person = findPerson(personName, university);
		
		for (String teacherId : person.teacherIds) {
			Teacher teacher = (Teacher) dao.query(Teacher.class, teacherId);
			if (teacher.getUniversity().getId().equals(university.getId())) {
				if (department != null) {
					if (teacher.getDepartment() != null && department.getId().equals(teacher.getDepartment().getId())) {
						return teacher;
					}
				} else if (institute != null) {
					if (teacher.getInstitute() != null && institute.getId().equals(teacher.getInstitute().getId())) {
						return teacher;
					}
				} else {
					return teacher;
				}
			}
		}
		
		Teacher teacher = new Teacher();
		teacher.setType(person.teacherIds.isEmpty() ? "专职人员" : "兼职人员");
		teacher.setUniversity(university);
		
		if (department != null) {
			teacher.setDepartment(department);
		} else if (institute != null) {
			teacher.setInstitute(institute);
		} else {
			Department otherDepartment = tool.getOtherDepartment(university);
			teacher.setDepartment(otherDepartment);
		}
		teacher.setPerson((Person) dao.query(Person.class, person.id));
		
		String teacherId = (String) dao.add(teacher);
		person.teacherIds.add(teacherId);

		return teacher;
	}
	
	/**
	 * 根据人名、高校(PO)，返回一个student(PO)。<br>
	 * 寻找该校任意一个姓名符合的student返回。<br>
	 * 若找不到，则新增之， 放进该校的“其他院系”里。
	 */
	public Student findStudent(String personName, Agency university) {
		return findStudent(personName, university, null, null);
	}

	/**
	 * 根据人名、院系(PO)，返回一个student(PO)。<br>
	 * 若找不到，则新增之， 放进该院系。
	 */
	public Student findStudent(String personName, Department department) {
		return findStudent(personName, department.getUniversity(), department, null);
	}

	/**
	 * 根据人名、基地(PO)，返回一个student(PO)。<br>
	 * 若找不到，则新增之， 放进该基地。
	 */
	public Student findStudent(String personName, Institute institute) {
		return findStudent(personName, institute.getSubjection(), null, institute);
	}

	/**
	 * 根据人名、高校(PO)、院系(PO)、基地(PO)，返回一个student(PO)。<br>
	 * 若传入的院系和基地都为空，则寻找该校任意一个名字符合的student返回。<br>
	 * 若找不到，则新增之。<br>
	 * 若department和institute均为空，则将此人放进该校的“其他院系”里。
	 */
	public Student findStudent(String personName, Agency university, Department department, Institute institute) {
		if (university.getType() != 3 && university.getType() != 4) {
			throw new RuntimeException("传入的agency[" + university.getName() + "]不是学校!");
		}
		if (department != null && institute != null) {
			throw new RuntimeException("一个学生不能同时从属于院系和基地!");
		}
		PersonTemp person = findPerson(personName, university);
		
		for (String studentId : person.studentIds) {
			Student student = (Student) dao.query(Student.class, studentId);
			if (student.getUniversity().getId().equals(university.getId())) {
				if (department != null) {
					if (student.getDepartment() != null && department.getId().equals(student.getDepartment().getId())) {
						return student;
					}
				} else if (institute != null) {
					if (student.getInstitute() != null && institute.getId().equals(student.getInstitute().getId())) {
						return student;
					}
				} else {
					return student;
				}
			}
		}
		
		Student student = new Student();
		student.setUniversity(university);
		
		if (department != null) {
			student.setDepartment(department);
		} else if (institute != null) {
			student.setInstitute(institute);
		} else {
			Department otherDepartment = tool.getOtherDepartment(university);
			student.setDepartment(otherDepartment);
		}
		student.setPerson((Person) dao.query(Person.class, person.id));
		student.setType("未知");		//非空限制 先填一个出去再改
		student.setStatus("在读"); 		//非空限制 先填一个出去再改
		
		String studentId = (String) dao.add(student);
		person.studentIds.add(studentId);

		return student;
	}
	
	private PersonTemp findPerson(String personName, Agency agency) {
		String fixedPersonName = StringTool.fix(personName);
		if (fixedPersonName.isEmpty()) {
			throw new RuntimeException("姓名不应为空");
		}
		if (pMap == null) {
			pMap = new HashMap<String, PersonTemp>();
			initOfficers();
			initTeachers();
			initStudents();
		}
		
		String key = agency.getCode() + fixedPersonName;
		PersonTemp personTemp = pMap.get(key);
		
		if (personTemp == null) {
			Person person = new Person();
			
			person.setName(personName);
			String personId = (String) dao.add(person);
			personTemp = new PersonTemp(personId);
			pMap.put(key, personTemp);
		}
		return personTemp;
	}

	
	////////////////////////////////////////////////////////////////////////////
	
	private void initOfficers() {
		Date begin = new Date();
		
		List<Object[]> list = dao.query("select o.agency.code, o.person.name, o.person.id, o.id from Officer o");
		for (Object[] o : list) {
			String agencyCode = (String) o[0];
			String personName = StringTool.fix((String) o[1]);
			String personId = (String) o[2];
			String officerId = (String) o[3];
			
			PersonTemp person = pMap.get(agencyCode + personName);
			if (person == null) {
				person = new PersonTemp();
				person.id = personId;
				pMap.put(agencyCode + personName, person);
			}
			person.officerIds.add(officerId);
		}

		System.out.println("initOfficer complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initTeachers() {
		Date begin = new Date();
		
		List<Object[]> list = dao.query("select t.university.code, t.person.name, t.person.id, t.id from Teacher t");
		for (Object[] o : list) {
			String universityCode = (String) o[0];
			String personName = StringTool.fix((String) o[1]);
			String personId = (String) o[2];
			String teacherId = (String) o[3];
			
			PersonTemp person = pMap.get(universityCode + personName);
			if (person == null) {
				person = new PersonTemp();
				person.id = personId;
				pMap.put(universityCode + personName, person);
			}
			person.teacherIds.add(teacherId);
		}
		
		System.out.println("initTeacher complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initStudents() {
		Date begin = new Date();
		
		List<Object[]> list = dao.query("select s.university.code, s.person.name, s.person.id, s.id from Student s");
		for (Object[] o : list) {
			String universityCode = (String) o[0];
			String personName = StringTool.fix((String) o[1]);
			String personId = (String) o[2];
			String studentId = (String) o[3];
			
			PersonTemp person = pMap.get(universityCode + personName);
			if (person == null) {
				person = new PersonTemp();
				person.id = personId;
				pMap.put(universityCode + personName, person);
			}
			person.studentIds.add(studentId);
		}
		
		System.out.println("initStudent complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
}
