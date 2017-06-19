package csdc.action.person;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Abroad;
import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Education;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IProductService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.merger.StudentMerger;

@Transactional
public class StudentAction extends PersonAction {

	private static final long serialVersionUID = 2267030733918211072L;

	private final static String HQL = "select p.name, p.gender, s.id, u.name, u.id, d.name, d.id, i.name, i.id, s.type, p.id, p.isKey from Student s left join s.person p left join s.department d left join s.institute i left join s.university u left join u.province so ";
	private final static String[] COLUMN = new String[]{
			"p.name",
			"p.gender, p.name",
			"u.name, p.name",
			"CONCAT(d.name, i.name), p.name",
			"s.type, p.name",
			"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "studentPage";
	private final static String[] SEARCH_CONDITIONS = {
			"LOWER(p.name) like :keyword",
			"LOWER(u.name) like :keyword",
			"LOWER(CONCAT(d.name, i.name)) like :keyword",
			"LOWER(s.type) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "s.id";//缓存id

	private Integer idType = 13;//学生id类型
	
	private Student student;
	private String departmentId;
	private String specialityTitleId;
	private String instituteId;
	private IProductService productService;
	private IProjectExtService projectExtService;

	Integer unitType;
	String DIName_subjectionName;

	String tutorName;

	private List<Student> incomeStudents;//用于人员合并
	
	/**
	 * 构造部级管理人员的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
			hql.append(" where ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据学生所在单位是否归省管进行判断
			hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4) and u.subjection.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据学生所在单位是否归校管进行判断
			hql.append(" where u.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据学生是否在当前院系判断
			hql.append(" where d.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据学生是否在当前基地判断
			hql.append(" where i.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else {
			hql.append(" where 1 = 0 and ");
		}
		hql.append(" 1 = 1 ");
	}

	/**
	 * 处理学生列表，重写
	 */
	public void pageListDealWith() {
		laData = new ArrayList();
		Object[] o;
		for (Object p : pageList) {
			o = (Object[]) p;
			Object[] item = new Object[12];
			item[0] = o[0];
			item[1] = o[1];
			item[2] = o[2];
			item[3] = o[3];
			item[4] = o[4];
			item[9] = o[9];
			item[10] = o[10];
			item[11] = o[11];
			if (o[6] == null){
				item[5] = o[7];
				item[6] = o[8];
				item[7] = "linkI";
			} else {
				item[5] = o[5];
				item[6] = o[6];
				item[7] = "linkD";
			}
			laData.add(item);
		}
	}

	public void validateAdd(){
		validateBasicInfo(person, true);
		validateStudentInfo(student);
		validateContactInfo(person, true);
		validateAddress(homeAddress);
		validateAddress(officeAddress);
	}

	public boolean addInner(){
		if (departmentId != null && !departmentId.trim().isEmpty()){
			student.setDepartment(new Department(departmentId.trim()));
			department = (Department) dao.query(Department.class, departmentId);
			if (!personService.checkIfUnderControl(loginer, departmentId, 4, true)) {
				//对此院系没有权限
				return false;
			}
			student.setUniversity(department.getUniversity());
			student.setDivisionName(department.getName());
			student.setAgencyName(dao.query(Agency.class,department.getUniversity().getId()).getName());
		} else if (instituteId != null && !instituteId.trim().isEmpty()){
			student.setInstitute(new Institute(instituteId.trim()));
			institute = (Institute) dao.query(Institute.class, instituteId);
			if (!personService.checkIfUnderControl(loginer, instituteId, 5, true)) {
				//对此基地没有权限
				return false;
			}
			student.setUniversity(institute.getSubjection());
			student.setDivisionName(institute.getName());
			student.setAgencyName(dao.query(Agency.class,institute.getSubjection().getId()).getName());
		} else {
			return false;
		}
		
		student.setPerson(person);
		if (student.getTutor() != null){
			Person tutor = (Person) dao.query(Person.class, student.getTutor().getId());
			student.setTutor(tutor);
		}
		dao.add(student);
		entityId = student.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("studentId", entityId);
		List<Object[]> result = dao.query("select student, person, academic, tu from Student student left join student.tutor tu left join student.person person left join person.academic academic where student.id = :studentId", paraMap);
		
		student = (Student) result.get(0)[0];
		person = (Person) result.get(0)[1];
		academic = (Academic)result.get(0)[2];
		Person tutor = (Person)result.get(0)[3];//导师信息
		jsonMap.put("universityId", (student.getUniversity()!=null)?student.getUniversity().getId():null);
		jsonMap.put("departmentId", (student.getDepartment()!=null)?student.getDepartment().getId():null);
		jsonMap.put("instituteId", (student.getInstitute()!=null)?student.getInstitute().getId():null);
		jsonMap.put("tutorName", tutor==null?"":tutor.getName());
		jsonMap.put("tutorId", tutor==null?"":tutor.getId());
		
		if(null == academic || academic.getExpertType() == null){
			jsonMap.put("expertType", "");
		}else{
			SystemOption expertType =(SystemOption)dao.query(SystemOption.class,academic.getExpertType().getId());
			jsonMap.put("expertType",expertType.getName());
		}
		
		if(null == academic || academic.getComputerLevel() == null){
			jsonMap.put("computerLevel", "");
		}else{
			SystemOption computerLevel =(SystemOption) dao.query(SystemOption.class, academic.getComputerLevel().getId());
			jsonMap.put("computerLevel", computerLevel.getName());
		}
		
		paraMap.clear();
		paraMap.put("personId", person.getId());
		result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		jsonMap.put("person_education", result);
		result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
		jsonMap.put("person_abroad", result);
		result  = this.productService.getProductListByEntityId(1, person.getId());
		jsonMap.put("person_product", result);
		result = this.projectExtService.getProjectListByEntityId(1, person.getId());
		jsonMap.put("person_project", result);
		jsonMap.put("student", student);
		jsonMap.put("person", person);
		jsonMap.put("academic", academic);
		String[] postdoctorStatus = new String[]{"否","在站","出站"};
		if(null != academic && null != academic.getPostdoctor() && academic.getPostdoctor()>-1 && academic.getPostdoctor()<3){
			jsonMap.put("postdoctor", postdoctorStatus[academic.getPostdoctor()]);
		}else{
			jsonMap.put("postdoctor", "");
		}
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("studentId", entityId);
		paraMap.put("studentId", entityId);
		List<Object[]> result = dao.query("select student, person, dsub, d, isub, i, academic from Student student left join fetch student.tutor left join student.department d left join d.university dsub left join student.institute i left join i.subjection isub left join student.person person left join person.academic academic where student.id = :studentId", paraMap);
		paraMap.clear();
		student = (Student) result.get(0)[0];
		person = (Person) result.get(0)[1];
		academic = (Academic) result.get(0)[6];
		if (academic!=null && academic.getSpecialityTitle()!= null && academic.getSpecialityTitle().contains("/")) {
			paraMap.put("standard", "GBT8561-2001");
			String code = academic.getSpecialityTitle().substring(0, academic.getSpecialityTitle().lastIndexOf("/"));
			paraMap.put("code", code);
			specialityTitleId = (String) dao.query("select s.systemOption.id from SystemOption s where s.standard = :standard and s.code = :code",paraMap).get(0);
		}
		department = (Department) result.get(0)[3];
		institute = (Institute) result.get(0)[5];
		agency = (Agency) result.get(0)[result.get(0)[2] != null ? 2 : 4];
		DIName_subjectionName = agency.getName() + (department != null ? department.getName() : institute.getName());
		unitType = result.get(0)[2] != null ? 0 : 1;
		if (result.get(0)[2] != null){
			departmentId = department.getId();
		} else {
			instituteId = institute.getId();
		}
		if (student.getTutor() != null){
			tutorName = student.getTutor().getName();
		}
	}
	
	public boolean modifyInner(){
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get("studentId");
		Student originStudent = (Student) dao.query(Student.class, entityId);
		originPerson = originStudent.getPerson();
		
		if (departmentId != null && !departmentId.trim().isEmpty()){
			originStudent.setDepartment(new Department(departmentId.trim()));
			department = (Department) dao.query(Department.class, departmentId);
			if (!personService.checkIfUnderControl(loginer, departmentId, 4, true)) {
				//对此院系没有权限
				return false;
			}
			originStudent.setUniversity(department.getUniversity());
			originStudent.setDivisionName(department.getName());
			originStudent.setAgencyName(dao.query(Agency.class,department.getUniversity().getId()).getName());
		} else if (instituteId != null && !instituteId.trim().isEmpty()){
			originStudent.setInstitute(new Institute(instituteId.trim()));
			institute = (Institute) dao.query(Institute.class, instituteId);
			if (!personService.checkIfUnderControl(loginer, instituteId, 5, true)) {
				//对此基地的没有权限
				return false;
			}
			originStudent.setUniversity(institute.getSubjection());
			originStudent.setDivisionName(institute.getName());
			originStudent.setAgencyName(dao.query(Agency.class,institute.getSubjection().getId()).getName());
		} else {
			return false;
		}
		if (student.getTutor() != null){
			Teacher tutor = (Teacher) dao.query(Teacher.class, student.getTutor().getId());
			if (tutor!=null)
				originStudent.setTutor(tutor.getPerson());
		}
		originStudent.setType(student.getType());
		originStudent.setStatus(student.getStatus());
		originStudent.setStartDate(student.getStartDate());
		originStudent.setEndDate(student.getEndDate());
		originStudent.setProject(student.getProject());
		originStudent.setThesisTitle(student.getThesisTitle());
		originStudent.setStudentCardNumber(student.getStudentCardNumber());
		originStudent.setIsExcellent(student.getIsExcellent());
		originStudent.setExcellentGrade(student.getExcellentGrade());
		originStudent.setExcellentSession(student.getExcellentSession());
		originStudent.setExcellentYear(student.getExcellentYear());
		originStudent.setThesisFee(student.getThesisFee());
		
		personService.modifyPerson(person, originPerson);
		dao.modify(originStudent);
		
		//修改学术信息
		personService.modifyAcademic(originPerson, academic);
		return true;
	}

	public void validateModify(){
		validateBasicInfo(person, false);
		validateStudentInfo(student);
		validateContactInfo(person, false);
		validateAddress(homeAddress);
		validateAddress(officeAddress);
		validateAcademicInfo(academic);
		validateThesisInfo(student);
		validateEWA();
	}
	
	public void validateMerge(){
		validateModify();
	}

	/**
	 * 跳转到合并页面
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	public String toMerge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{		
		Map paraMap = new HashMap();
		StudentMerger studentMerger =(StudentMerger) SpringBean.getBean("studentMerger");		
		Serializable targetId = entityId;
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		studentMerger.mergeStudent(targetId, incomeIds);
		//合并结果返回至前台
		person = studentMerger.getTmpPerson();
		student = studentMerger.getTmpStudent();
		academic = studentMerger.getTmpAcademic();	
		specialityTitleId = personService.getSpecialityTitleId(academic);
		paraMap.put("studentId", student.getId());
		department = (Department) dao.queryUnique("select d from Student s left join s.department d where s.id=:studentId",paraMap);		
		institute = (Institute) dao.queryUnique("select d from Student s left join s.institute d where s.id=:studentId",paraMap);
		agency = (Agency) dao.queryUnique("select d from Student s left join s.university d where s.id=:studentId",paraMap);
		DIName_subjectionName = agency.getName() + (department != null ? department.getName() : institute.getName());
		unitType = department != null ? 0 : 1;
		if (department != null){
			departmentId = department.getId();
		} else {
			instituteId = institute.getId();
		}
		
		Person tutor = (Person) dao.queryUnique("select t from Student s left join s.tutor t where s.id=:studentId",paraMap );
		if(tutor!=null)
			tutorName = tutor.getName();
		
		
		paraMap.clear();
		paraMap.put("personId", person.getId());		
		List<Student> incomeStudents = new ArrayList<Student>();
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeStudents.add(dao.query(Student.class,incomeIds.get(i)));
		}
		ebs = new ArrayList<Education>();
		aes = new ArrayList<Abroad>();
		for (int i = 0; i < incomeStudents.size(); i++) {
			ebs.addAll(incomeStudents.get(i).getPerson().getEducation());
			aes.addAll(incomeStudents.get(i).getPerson().getAbroad());
		}
		

		//将已有的照片加入文件组，以在编辑页面显示
		String groupId = "photo_" + person.getId();
		uploadService.resetGroup(groupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
		if (photoFileRealPath != null) {
			uploadService.addFile(groupId, new File(photoFileRealPath));
		}
		for (int i = 1; i < incomeStudents.size(); i++) {		
			String otherPhotoFileRealPath = ApplicationContainer.sc.getRealPath(incomeStudents.get(i).getPerson().getPhotoFile());
			if (otherPhotoFileRealPath != null) {
				uploadService.addFile(groupId, new File(otherPhotoFileRealPath));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 执行学生合并
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		if (!accountExtService.checkIfUnderControl(loginer, entityId, 13, true)) {
			return ERROR;
		}

		Student originStudent = (Student) dao.query(Student.class, entityId);
		Person originPerson = (Person) dao.query(Person.class, originStudent.getPerson().getId());
		Academic orignAcademic =originPerson.getAcademic();
		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().trim().equals(person.getName().trim())){
			personService.updatePersonName(originPerson.getId(), person.getName());
		}

		/*if (!person.getIdcardNumber().equals(originPerson.getIdcardNumber()) && personService.checkIdcard(person.getIdcardNumber())) {
			return ERROR;
		}*/
		
		if (departmentId != null && !departmentId.trim().isEmpty()){
			originStudent.setDepartment(new Department(departmentId.trim()));
			department = (Department) dao.query(Department.class, departmentId);
			if (!personService.checkIfUnderControl(loginer, departmentId, 4, true)) {
				//对此院系没有权限
				return ERROR;
			}
			originStudent.setUniversity(department.getUniversity());
		} else if (instituteId != null && !instituteId.trim().isEmpty()){
			originStudent.setInstitute(new Institute(instituteId.trim()));
			institute = (Institute) dao.query(Institute.class, instituteId);
			if (!personService.checkIfUnderControl(loginer, instituteId, 5, true)) {
				//对此基地的没有权限
				return ERROR;
			}
			originStudent.setUniversity(institute.getSubjection());
		} else {
			return ERROR;
		}
		if (student.getTutor() != null){
			Person tutor = (Person) dao.query(Person.class, student.getTutor().getId());
			originStudent.setTutor(tutor);
		}
		originStudent.setType(student.getType());
		originStudent.setStatus(student.getStatus());
		originStudent.setStartDate(student.getStartDate());
		originStudent.setEndDate(student.getEndDate());
		originStudent.setProject(student.getProject());
		originStudent.setThesisTitle(student.getThesisTitle());
		originStudent.setStudentCardNumber(student.getStudentCardNumber());
		originStudent.setIsExcellent(student.getIsExcellent());
		originStudent.setExcellentGrade(student.getExcellentGrade());
		originStudent.setExcellentSession(student.getExcellentSession());
		originStudent.setExcellentYear(student.getExcellentYear());
		originStudent.setThesisFee(student.getThesisFee());
		
		//修改学术信息
		if(orignAcademic!=null){
			academic.setId(orignAcademic.getId());
		}
		academic.setPerson(originPerson);
		dao.merge(academic);

		//处理照片
		String groupId = "photo_" + originPerson.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			person.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		personService.modifyPerson(person, originPerson);
		dao.modify(originStudent);
	
		StudentMerger studentMerger =(StudentMerger) SpringBean.getBean("studentMerger");		
		Serializable targetId = entityId;
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		incomeIds.remove(targetId);
		studentMerger.doMerge(targetId, new ArrayList<Serializable>(incomeIds),selectedAccountId);
		modifyInformation(originPerson);
		uploadService.flush(groupId);
		return SUCCESS;
	}
	
	/**
	 * 获取合并记录的原始数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String fetchMergeData() {
		List<String> tutorNames = new ArrayList<String>();
		List<String> DIName_subjectionNames = new ArrayList<String>(); //可选所在单位名
		List<Integer> unitTypes = new ArrayList<Integer>();
		List<String> unitIds = new ArrayList<String>();
		List<String> tutorIds = new ArrayList<String>();
		
		incomeStudents = new ArrayList<Student>();
		List<Person> incomePerson = new ArrayList<Person>();
		List<Academic> incomeAcademic = new ArrayList<Academic>();				
		List<String> computerLevels = new ArrayList<String>();	
		List<String> expertTypes = new ArrayList<String>();	
		List<String> specialityTitleNames = new ArrayList<String>();	
		
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeStudents.add(dao.query(Student.class, incomeIds.get(i)));
		}
		for (int i = 0; i < incomeStudents.size(); i++) {
			Hibernate.initialize(incomeStudents.get(i).getPerson());
			Hibernate.initialize(incomeStudents.get(i).getTutor());
			incomePerson.add(incomeStudents.get(i).getPerson());			
			if(incomeStudents.get(i).getPerson().getAcademic() != null){
				incomeAcademic.add(incomeStudents.get(i).getPerson().getAcademic());	
				if(incomeStudents.get(i).getPerson().getAcademic().getComputerLevel() != null){
					computerLevels.add(incomeStudents.get(i).getPerson().getAcademic().getComputerLevel().getName());
				}else{
					computerLevels.add("");
				}
				if(incomeStudents.get(i).getPerson().getAcademic().getExpertType() != null){
					expertTypes.add(incomeStudents.get(i).getPerson().getAcademic().getExpertType().getName());
				}else{
					expertTypes.add("");
				}
				specialityTitleNames.add(personService.getSpecialityTitleName(incomeStudents.get(i).getPerson().getAcademic()));
			}else {
				incomeAcademic.add(new Academic());
				computerLevels.add("");
				expertTypes.add("");
				specialityTitleNames.add("");
			}	
		}
		
		for(int i = 0; i < incomeStudents.size(); i++) {
			department = incomeStudents.get(i).getDepartment();		
			institute = incomeStudents.get(i).getInstitute();
			agency = incomeStudents.get(i).getUniversity();
			DIName_subjectionName = agency.getName() + (department != null ? department.getName() : institute.getName());
			DIName_subjectionNames.add(DIName_subjectionName);
			if(department!=null){
				unitTypes.add(0);
				unitIds.add(department.getId());
			}else {
				unitTypes.add(1);
				unitIds.add(institute.getId());
			}
			if (incomeStudents.get(i).getTutor() != null){
				Person tutor = incomeStudents.get(i).getTutor();
				tutorNames.add(tutor.getName());
				tutorIds.add(tutor.getId());
			}
		}
		jsonMap.put("incomeStudents", incomeStudents);
		jsonMap.put("incomePerson", incomePerson);		
		jsonMap.put("incomeSubjectionNames",DIName_subjectionNames);
		jsonMap.put("incomeTutorNames", tutorNames);	
		jsonMap.put("incomeAcademics", incomeAcademic);	
		jsonMap.put("computerLevels", computerLevels);
		jsonMap.put("expertTypes", expertTypes);
		jsonMap.put("specialityTitleNames", specialityTitleNames);
		jsonMap.put("unitTypes", unitTypes);
		jsonMap.put("unitIds", unitIds);
		jsonMap.put("tutorIds", tutorIds);
		return SUCCESS;
	}
	
	@Override
	public String pageName(){
		return PAGE_NAME;
	}
	@Override
	public String[] column(){
		return COLUMN;
	}
	@Override
	public String HQL() {
		return HQL;
	}
	@Override
	public String dateFormat() {
		return null;
	}
	@Override
	public String[] searchConditions() {
		return SEARCH_CONDITIONS;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getSpecialityTitleId() {
		return specialityTitleId;
	}



	public void setSpecialityTitleId(String specialityTitleId) {
		this.specialityTitleId = specialityTitleId;
	}



	public Integer getUnitType() {
		return unitType;
	}

	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}

	public String getDIName_subjectionName() {
		return DIName_subjectionName;
	}

	public void setDIName_subjectionName(String dINameSubjectionName) {
		DIName_subjectionName = dINameSubjectionName;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IProjectExtService getProjectExtService() {
		return projectExtService;
	}

	public void setProjectExtService(IProjectExtService projectExtService) {
		this.projectExtService = projectExtService;
	}

	@Override
	public String pageBufferId() {
		return StudentAction.PAGE_BUFFER_ID;
	}
	
	@Override
	public Integer idType() {
		return idType;
	}
}
