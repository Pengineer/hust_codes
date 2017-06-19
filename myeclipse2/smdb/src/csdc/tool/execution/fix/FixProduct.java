package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.InstpEndinspection;
import csdc.bean.InstpMidinspection;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Expert;
import csdc.bean.GeneralEndinspection;
import csdc.bean.GeneralMidinspection;
import csdc.bean.Paper;
import csdc.bean.PostEndinspection;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IBaseService;
import csdc.tool.execution.Execution;

public class FixProduct extends Execution {
	
	@Autowired
	public IBaseService baseService;
	
	@Autowired
	public IHibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		//fixAuthorType
		fixMidinspectionId();
		fixEndinspectionId();
	}
	
	/**
	 * 修复成果项目中检id
	 */
	@SuppressWarnings("unchecked")
	protected void fixMidinspectionId() {
		String projectTypeId = "", grantedId = "";
		//论文
		List<Paper> papers = baseService.query("select p from Paper p left outer join fetch p.projectType pt where p.midinspectionId is null and p.granted is not null and pt.id is not null and p.isMidinspection = 1");
		for(Paper paper : papers) {
			projectTypeId = paper.getType().getId(); 
			grantedId = paper.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralMidinspection gm = (GeneralMidinspection) dao.iterate("from GeneralMidinspection gm where gm.granted.id = ? order by gm.finalAuditDate", grantedId).next();
					paper.setMidinspectionId(gm.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpMidinspection bm = (InstpMidinspection) dao.iterate("from InstpMidinspection bm where bm.granted.id = ? order by bm.finalAuditDate", grantedId).next();
					paper.setMidinspectionId(bm.getId());
				} catch (NoSuchElementException e) {}
			}
		}
		//著作
		List<Book> books = baseService.query("select b from Book b left outer join fetch b.projectType pt where b.midinspectionId is null and b.granted is not null and pt.id is not null and b.isMidinspection = 1");
		for(Book book : books) {
			projectTypeId = book.getType().getId(); 
			grantedId = book.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralMidinspection gm = (GeneralMidinspection) dao.iterate("from GeneralMidinspection gm where gm.granted.id = ? order by gm.finalAuditDate", grantedId).next();
					book.setMidinspectionId(gm.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpMidinspection bm = (InstpMidinspection) dao.iterate("from InstpMidinspection bm where bm.granted.id = ? order by bm.finalAuditDate", grantedId).next();
					book.setMidinspectionId(bm.getId());
				} catch (NoSuchElementException e) {}
			}
		}
		//研究报告
		List<Consultation> consultations = baseService.query("select c from Consultation c left outer join fetch c.projectType pt where c.midinspectionId is null and c.granted is not null and pt.id is not null and c.isMidinspection = 1");
		for(Consultation consultation : consultations) {
			projectTypeId = consultation.getType().getId(); 
			grantedId = consultation.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralMidinspection gm = (GeneralMidinspection) dao.iterate("from GeneralMidinspection gm where gm.granted.id = ? order by gm.finalAuditDate", grantedId).next();
					consultation.setMidinspectionId(gm.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpMidinspection bm = (InstpMidinspection) dao.iterate("from InstpMidinspection bm where bm.granted.id = ? order by bm.finalAuditDate", grantedId).next();
					consultation.setMidinspectionId(bm.getId());
				} catch (NoSuchElementException e) {}
			}
		}
	}
	
	/**
	 * 修复成果项目结项id
	 */
	@SuppressWarnings("unchecked")
	protected void fixEndinspectionId() {
		String projectTypeId = "", grantedId = "";
		//论文
		List<Paper> papers = baseService.query("select p from Paper p left outer join fetch p.projectType pt where p.endinspectionId is null and p.granted is not null and pt.id is not null and p.isEndinspection = 1");
		for(Paper paper : papers) {
			projectTypeId = paper.getType().getId(); 
			grantedId = paper.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralEndinspection ge = (GeneralEndinspection) dao.iterate("from GeneralEndinspection ge where ge.granted.id = ? order by ge.finalAuditDate", grantedId).next();
					paper.setEndinspectionId(ge.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpEndinspection be = (InstpEndinspection) dao.iterate("from InstpEndinspection be where be.granted.id = ? order by be.finalAuditDate", grantedId).next();
					paper.setEndinspectionId(be.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("post")){//后期资助项目
				try{
					PostEndinspection pe = (PostEndinspection) dao.iterate("from PostEndinspection pe where pe.granted.id = ? order by pe.finalAuditDate", grantedId).next();
					paper.setEndinspectionId(pe.getId());
				} catch (NoSuchElementException e) {}
			}
		}
		//著作
		List<Book> books = baseService.query("select b from Book b left outer join fetch b.projectType pt where b.endinspectionId is null and b.granted is not null and pt.id is not null and b.isEndinspection = 1");
		for(Book book : books) {
			projectTypeId = book.getType().getId(); 
			grantedId = book.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralEndinspection ge = (GeneralEndinspection) dao.iterate("from GeneralEndinspection ge where ge.granted.id = ? order by ge.finalAuditDate", grantedId).next();
					book.setEndinspectionId(ge.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpEndinspection be = (InstpEndinspection) dao.iterate("from InstpEndinspection be where be.granted.id = ? order by be.finalAuditDate", grantedId).next();
					book.setEndinspectionId(be.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("post")){//后期资助项目
				try{
					PostEndinspection pe = (PostEndinspection) dao.iterate("from PostEndinspection pe where pe.granted.id = ? order by pe.finalAuditDate", grantedId).next();
					book.setEndinspectionId(pe.getId());
				} catch (NoSuchElementException e) {}
			}
		}
		//研究报告
		List<Consultation> consultations = baseService.query("select c from Consultation c left outer join fetch c.projectType pt where c.endinspectionId is null and c.granted is not null and pt.id is not null and c.isEndinspection = 1");
		for(Consultation consultation : consultations) {
			projectTypeId = consultation.getType().getId(); 
			grantedId = consultation.getGranted();
			if(projectTypeId.equals("general")){//一般项目
				try {
					GeneralEndinspection ge = (GeneralEndinspection) dao.iterate("from GeneralEndinspection ge where ge.granted.id = ? order by ge.finalAuditDate", grantedId).next();
					consultation.setEndinspectionId(ge.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("instp")){//基地项目
				try{
					InstpEndinspection be = (InstpEndinspection) dao.iterate("from InstpEndinspection be where be.granted.id = ? order by be.finalAuditDate", grantedId).next();
					consultation.setEndinspectionId(be.getId());
				} catch (NoSuchElementException e) {}
			} else if(projectTypeId.equals("post")){//后期资助项目
				try{
					PostEndinspection pe = (PostEndinspection) dao.iterate("from PostEndinspection pe where pe.granted.id = ? order by pe.finalAuditDate", grantedId).next();
					consultation.setEndinspectionId(pe.getId());
				} catch (NoSuchElementException e) {}
			}
		}
	}
	
	/**
	 * 修复成果第一作者类别
	 */
	@SuppressWarnings("unchecked")
	protected void fixAuthorType() {
		//论文
		List<Paper> papers = baseService.query("from Paper p where p.author.id is not null");
		for(Paper paper : papers) {
			if(null != paper.getAuthor() && !"".equals(paper.getAuthor().getId())){
				Map map = new HashMap();
				map.put("personid", paper.getAuthor().getId());
				String hql = "from Teacher t where t.person.id= :personid ";
				List teachers = baseService.query(hql, map);
				if(teachers.size() > 0) {//作者为教师
					if(null != paper.getUniversity()) {
						hql += "and t.university.id= :universityId ";
						map.put("universityId", paper.getUniversity().getId());
						if(null != paper.getDepartment()) {
							hql += "and t.department.id= :departmentId ";
							map.put("departmentId", paper.getDepartment().getId());
						} else if(null != paper.getInstitute()) {
							hql += "and t.institute.id= :instituteId ";
							map.put("instituteId", paper.getInstitute().getId());
						}
					}
					Teacher teacher = (Teacher)baseService.queryUnique(hql, map);
					if(null != teacher) {
						paper.setAuthorType(1);
					}
				} else {
					hql = "from Student stu where stu.person.id= :personid ";
					List students = baseService.query("select stu.id from Student stu where stu.person.id= :personid", map);
					if(students.size() > 0) {//作者为学生
						if(null != paper.getUniversity()) {
							hql += "and stu.university.id= :universityId ";
							map.put("universityId", paper.getUniversity().getId());
							if(null != paper.getDepartment()) {
								hql += "and stu.department.id= :departmentId ";
								map.put("departmentId", paper.getDepartment().getId());
							} else if(null != paper.getInstitute()) {
								hql += "and stu.institute.id= :instituteId ";
								map.put("instituteId", paper.getInstitute().getId());
							}
						}
						Student student = (Student)baseService.queryUnique(hql, map);
						if(null != student) {
							paper.setAuthorType(3);
						} 
					} else {//作者为外部专家
						hql = "from Expert exp where exp.person.id= :personid and exp.agencyName= :agencyName and exp.divisionName= :divisionName";
						map.put("agencyName", paper.getAgencyName());
						map.put("divisionName", paper.getDivisionName());
						Expert expert = (Expert)baseService.queryUnique(hql, map);
						if(null != expert) {
							paper.setAuthorType(2);
						}
						
					}
				}
			}
		}
		//著作
		List<Book> books = baseService.query("from Book b where b.author.id is not null");
		for(Book book : books) {
			if(null != book.getAuthor() && !"".equals(book.getAuthor().getId())){
				Map map = new HashMap();
				map.put("personid", book.getAuthor().getId());
				String hql = "from Teacher t where t.person.id= :personid ";
				List teachers = baseService.query(hql, map);
				if(teachers.size() > 0) {//作者为教师
					if(null != book.getUniversity()) {
						hql += "and t.university.id= :universityId ";
						map.put("universityId", book.getUniversity().getId());
						if(null != book.getDepartment()) {
							hql += "and t.department.id= :departmentId ";
							map.put("departmentId", book.getDepartment().getId());
						} else if(null != book.getInstitute()) {
							hql += "and t.institute.id= :instituteId ";
							map.put("instituteId", book.getInstitute().getId());
						}
					}
					Teacher teacher = (Teacher)baseService.queryUnique(hql, map);
					if(null != teacher) {
						book.setAuthorType(1);
					}
				} else {
					hql = "from Student stu where stu.person.id= :personid ";
					List students = baseService.query("select stu.id from Student stu where stu.person.id= :personid", map);
					if(students.size() > 0) {//作者为学生
						if(null != book.getUniversity()) {
							hql += "and stu.university.id= :universityId ";
							map.put("universityId", book.getUniversity().getId());
							if(null != book.getDepartment()) {
								hql += "and stu.department.id= :departmentId ";
								map.put("departmentId", book.getDepartment().getId());
							} else if(null != book.getInstitute()) {
								hql += "and stu.institute.id= :instituteId ";
								map.put("instituteId", book.getInstitute().getId());
							}
						}
						Student student = (Student)baseService.queryUnique(hql, map);
						if(null != student) {
							book.setAuthorType(3);
						} 
					} else {//作者为外部专家
						hql = "from Expert exp where exp.person.id= :personid and exp.agencyName= :agencyName and exp.divisionName= :divisionName";
						map.put("agencyName", book.getAgencyName());
						map.put("divisionName", book.getDivisionName());
						Expert expert = (Expert)baseService.queryUnique(hql, map);
						if(null != expert) {
							book.setAuthorType(2);
						}
						
					}
				}
			}
		}
		//研究报告
		List<Consultation> consultations = baseService.query("from Consultation c where c.author.id is not null");
		for(Consultation consultation : consultations) {
			if(null != consultation.getAuthor() && !"".equals(consultation.getAuthor().getId())){
				Map map = new HashMap();
				map.put("personid", consultation.getAuthor().getId());
				String hql = "from Teacher t where t.person.id= :personid ";
				List teachers = baseService.query(hql, map);
				if(teachers.size() > 0) {//作者为教师
					if(null != consultation.getUniversity()) {
						hql += "and t.university.id= :universityId ";
						map.put("universityId", consultation.getUniversity().getId());
						if(null != consultation.getDepartment()) {
							hql += "and t.department.id= :departmentId ";
							map.put("departmentId", consultation.getDepartment().getId());
						} else if(null != consultation.getInstitute()) {
							hql += "and t.institute.id= :instituteId ";
							map.put("instituteId", consultation.getInstitute().getId());
						}
					}
					Teacher teacher = (Teacher)baseService.queryUnique(hql, map);
					if(null != teacher) {
						consultation.setAuthorType(1);
					}
				} else {
					hql = "from Student stu where stu.person.id= :personid ";
					List students = baseService.query("select stu.id from Student stu where stu.person.id= :personid", map);
					if(students.size() > 0) {//作者为学生
						if(null != consultation.getUniversity()) {
							hql += "and stu.university.id= :universityId ";
							map.put("universityId", consultation.getUniversity().getId());
							if(null != consultation.getDepartment()) {
								hql += "and stu.department.id= :departmentId ";
								map.put("departmentId", consultation.getDepartment().getId());
							} else if(null != consultation.getInstitute()) {
								hql += "and stu.institute.id= :instituteId ";
								map.put("instituteId", consultation.getInstitute().getId());
							}
						}
						Student student = (Student)baseService.queryUnique(hql, map);
						if(null != student) {
							consultation.setAuthorType(3);
						} 
					} else {//作者为外部专家
						hql = "from Expert exp where exp.person.id= :personid and exp.agencyName= :agencyName and exp.divisionName= :divisionName";
						map.put("agencyName", consultation.getAgencyName());
						map.put("divisionName", consultation.getDivisionName());
						Expert expert = (Expert)baseService.queryUnique(hql, map);
						if(null != expert) {
							consultation.setAuthorType(2);
						}
					}
				}
			}
		}
	}
}