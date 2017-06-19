package csdc.action.person;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
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
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.bean.Work;
import csdc.service.IMessageAuxiliaryService;
import csdc.service.IProductService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.merger.TeacherMerger;

@Transactional
public class TeacherAction extends PersonAction {

	private static final long serialVersionUID = 2267030733918211072L;

	private final static String HQL = "select p.name, p.gender, t.id, u.name, u.id, d.name, d.id, i.name, i.id, t.position, p.id, p.isKey from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u left join u.province so ";
	
	/**
	 * 表示各排序列
	 * 1: 可以写任意多列
	 * 2: 可以在列后写asc或者desc，不写则默认为asc
	 */
	private final static String[] COLUMN = new String[]{
			"p.name",
			"p.gender, p.name",
			"u.name, p.name",
			"CONCAT(d.name, i.name), p.name",
			"t.position, p.name",
			"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "teacherPage";
	private final static String[] SEARCH_CONDITIONS = new String[]{
			"LOWER(p.name) like :keyword",
			"LOWER(u.name) like :keyword",
			"LOWER(CONCAT(d.name, i.name)) like :keyword",
			"LOWER(t.position) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "t.id";//缓存id

	private Integer idType = 12;//教师id类型

	private Teacher teacher;
	private List<Teacher> teachers;

	private String specialityTitleId;
	List<Integer> unitType;
	List<String> DIName_subjectionName;
	private IProductService productService;
	private IProjectExtService projectExtService;
	
	private List<Teacher> incomeTeachers;//用于人员合并
	
	@Autowired
	protected IMessageAuxiliaryService messageAssistService;

	/**
	 * 构造教师的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
			hql.append(" left join p.academic a where ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号，根据教师所在单位是否归省管进行判断
			hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4) and u.subjection.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据教师所在单位是否归校管进行判断
			hql.append(" where u.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT)) {// 院系账号，根据教师是否在当前院系判断
			hql.append(" where d.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE)) {// 基地账号，根据教师是否在当前基地判断
			hql.append(" where i.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else {
			hql.append(" where p.id = :unitId and ");
			map.put("unitId", baseService.getBelongIdByLoginer(loginer));
//			hql.append(" where 1 = 0 and ");
		}
		hql.append(" t.type = '专职人员' ");
	}

	public boolean addInner(){		
		List<Agency> list;
		Map paraMap = new HashMap();
		paraMap.put("did", teacher.getDepartment().getId());
		list = dao.query("select ag from Department d left join d.university ag where d.id = :did", paraMap);
		if (!list.isEmpty()){
			if (!personService.checkIfUnderControl(loginer, teacher.getDepartment().getId(), 4, true)) {
				return false;
			}
			teacher.setUniversity(list.get(0));
		} else {
			paraMap.clear();
			paraMap.put("iid", teacher.getInstitute().getId());
			list = dao.query("select ag from Institute i left join i.subjection ag where i.id = :iid", paraMap);
			if (!personService.checkIfUnderControl(loginer, teacher.getInstitute().getId(), 5, true)) {
				return false;
			}
			teacher.setUniversity(list.get(0));
		}
		
		teacher.setPerson(person);
		dao.add(teacher);
		entityId = teacher.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("teacherId", entityId);
		List result = dao.query("select person, academic from Teacher teacher, Person person left join person.academic academic where teacher.id = :teacherId and teacher.person.id = person.id", paraMap);

		person = (Person) ((Object[]) result.get(0))[0];
		academic = (Academic) ((Object[]) result.get(0))[1];
		
		//专家类型处理
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
		List<Object[]> teachersForView = dao.query("select t, dsub.name, dsub.id, d.name, d.id, isub.name, isub.id, i.name, i.id from Person person left join person.teacher t left join t.department d left join d.university dsub left join t.institute i left join i.subjection isub where person.id = :personId order by t.type asc, t.startDate desc", paraMap);

		paraMap.clear();
		paraMap.put("personId", person.getId());

		result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		jsonMap.put("person_education", result);

		result = dao.query("select we from Work we where we.person.id = :personId", paraMap);
		jsonMap.put("person_work", result);

		result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
		jsonMap.put("person_abroad", result);

		jsonMap.put("teachers", teachersForView);
		jsonMap.put("person", person);
		jsonMap.put("academic", academic);
		String[] postdoctorStatus = new String[]{"否","在站","出站"};
		if(null != academic && null != academic.getPostdoctor() && academic.getPostdoctor()>-1 && academic.getPostdoctor()<3){
			jsonMap.put("postdoctor", postdoctorStatus[academic.getPostdoctor()]);
		}else{
			jsonMap.put("postdoctor", "");
		}
		result  = this.productService.getProductListByEntityId(1, person.getId());
		jsonMap.put("person_product", result);
		result = this.projectExtService.getProjectListByEntityId(1, person.getId());
		jsonMap.put("person_project", result);
		if (loginer.getCurrentType().equals(AccountType.TEACHER)) {
			result = messageAssistService.getSameSearchData(person.getId());
			jsonMap.put("search_list", result);
		}
		
		
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		
		session.put("teacherId", entityId);
		paraMap.put("teacherId", entityId);
		List result = dao.query("select p, a from Teacher t left join t.person p left join p.academic a where t.id = :teacherId", paraMap);
		paraMap.clear();
		person = (Person) ((Object[]) result.get(0))[0];
		academic = (Academic) ((Object[]) result.get(0))[1];
		if (academic!=null && academic.getSpecialityTitle() != null && academic.getSpecialityTitle().contains("/")) {
			paraMap.put("standard", "GBT8561-2001");
			String code = academic.getSpecialityTitle().substring(0, academic.getSpecialityTitle().lastIndexOf("/"));
			paraMap.put("code", code);
			specialityTitleId = (String) dao.query("select s.systemOption.id from SystemOption s where s.standard = :standard and s.code = :code",paraMap).get(0);
		}
		session.put("personId", person.getId());
		paraMap.clear();
		paraMap.put("personId", person.getId());
		List<Object[]> tmpList = dao.query("select t, dsub.name, dsub.id, d.name, d.id, isub.name, isub.id, i.name, i.id from Person person left join person.teacher t left join t.department d left join d.university dsub left join t.institute i left join i.subjection isub where person.id = :personId order by t.type asc, t.startDate desc", paraMap);
		teachers = new ArrayList<Teacher>();
		unitType = new ArrayList<Integer>();
		DIName_subjectionName = new ArrayList<String>();
		for (Object[] o : tmpList) {
			if (accountExtService.checkIfUnderControl(loginer, ((Teacher) o[0]).getId(), 12, true)){
				teachers.add((Teacher) o[0]);
				if (o[1] != null){
					unitType.add(0);
					DIName_subjectionName.add(o[1] + " " + o[3]);
				} else {
					unitType.add(1);
					DIName_subjectionName.add(o[5] + " " + o[7]);
				}
			}
		}
		HashSet tids = new HashSet();
		for (Teacher t : teachers){
			tids.add(t.getId());
		}
		session.put("tids", tids);
	}
	
	
	
	/**
	 * 处理教师列表，重写
	 * 因为教师即有可能属于机构又有可能属于院系,因此要分情况对查询出来的数据进行处理
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
			item[9] = o[9];//职务
			item[10] = o[10];//personid
			item[11] = o[11];//iskey
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
		validateTeacher(teacher);
		validateContactInfo(person, true);
	}

	public void validateModify(){
		validateBasicInfo(person, false);

		if (teachers != null){
			for (Teacher teacher : teachers) {
				validateTeacher(teacher);
			}
		} else {
			teachers = new ArrayList<Teacher>();
		}

		validateContactInfo(person, false);
		validateAcademicInfo(academic);
		validateBankInfo(person, false);
		validateEWA();
	}
	
	public void validateMerge(){
		validateModify();
	}

	/**
	 * 完成修改
	 * @return
	 */
	public String modify(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();

		entityId = (String) session.get("personId");
		
		Person originPerson = (Person) dao.queryUnique("select p from Person p where p.id = ?", entityId);
		
		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().equals(person.getName())){	
			personService.updatePersonName(originPerson.getId(), person.getName());
		}
		
		if (!person.getIdcardNumber().equals(originPerson.getIdcardNumber()) && personService.checkIdcard(person.getIdcardNumber())){
			return ERROR;
		}
		
		//处理照片
		String groupId = "photo_" + originPerson.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			person.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}


		HashSet tids = (HashSet) session.get("tids"); //原有的teacher记录
		HashSet curTids = new HashSet(); //当前的teacher记录
		int fulltimeCnt = 0;
		for (Teacher t : teachers){
			if (!tids.contains(t.getId())){
				//return ERROR;
				t.setPerson(originPerson);
				List<Agency> list;
				paraMap.clear();
				paraMap.put("did", t.getDepartment().getId());
				list = dao.query("select ag from Department d left join d.university ag where d.id = :did", paraMap);
				if (!list.isEmpty()){
					if (!personService.checkIfUnderControl(loginer, t.getDepartment().getId(), 4, true)) {
						return ERROR;
					}
					t.setUniversity(list.get(0));
				} else {
					paraMap.clear();
					paraMap.put("iid", t.getInstitute().getId());
					list = dao.query("select ag from Institute i left join i.subjection ag where i.id = :iid", paraMap);
					if (!personService.checkIfUnderControl(loginer, t.getInstitute().getId(), 5, true)) {
						return ERROR;
					}
					t.setUniversity(list.get(0));
				}
				dao.add(t);
				tids.add(t.getId());
			}			
			curTids.add(t.getId());
			fulltimeCnt += t.getType().equals("专职人员") ? 1 : 0;
		}
		for(Iterator it = tids.iterator(); it.hasNext();)
		{
			String tid = (String) it.next();
			if (!curTids.contains(tid)){
				paraMap.clear();
				paraMap.put("teacherId", tid);
				dao.execute("delete from Teacher t where t.id = :teacherId", paraMap);
			}
		}
		
		if(originPerson.getIdcardNumber() != null && !originPerson.getIdcardNumber().isEmpty()){
			String fullTimeId = personService.checkIfIsFulltimeTeacher(originPerson.getIdcardNumber());
			fulltimeCnt += fullTimeId != null && !curTids.contains(fullTimeId) ? 1 : 0;
	
			if (fulltimeCnt > 1){
				return ERROR;
			}
		}

		personService.modifyPerson(person, originPerson);
		
		for (Teacher teacher : teachers) {			
			teacher.setPerson(originPerson);
			if (teacher.getDepartment().getId() != null && !teacher.getDepartment().getId().isEmpty()){
				department = (Department) dao.query(Department.class, teacher.getDepartment().getId());
				if (!personService.checkIfUnderControl(loginer, teacher.getDepartment().getId(), 4, true)) {
					//对此院系没有权限
					return ERROR;
				}
				teacher.setUniversity(department.getUniversity());
			} else {
				institute = (Institute) dao.query(Institute.class, teacher.getInstitute().getId());
				if (!personService.checkIfUnderControl(loginer, teacher.getInstitute().getId(), 5, true)) {
					//对此基地没有权限
					return ERROR;
				}
				teacher.setUniversity(institute.getSubjection());
			}
			//处理前台传过来的空的departmentId和instituteId
			if(teacher.getDepartment().getId().isEmpty())
				teacher.setDepartment(null);
			if(teacher.getInstitute().getId().isEmpty())
				teacher.setInstitute(null);
			dao.modify(teacher);
		}

		if (originPerson.getAcademic() != null) {
			dao.delete(originPerson.getAcademic());
			dao.flush();
		}
		academic.setPerson(originPerson);
		dao.merge(academic);
		
		modifyInformation(originPerson);
		uploadService.flush(groupId);

		for(Iterator it = curTids.iterator(); it.hasNext();)
		{
			entityId = (String) it.next();
			return SUCCESS;
		}
		return "deleteAll";
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
		List result = new ArrayList();
		TeacherMerger teacherMerger =(TeacherMerger) SpringBean.getBean("teacherMerger");		
		Serializable targetId = entityId;
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		teacherMerger.mergeTeacher(targetId, new HashSet<Serializable>(incomeIds));
		//合并结果返回至前台
		person = teacherMerger.getTmpPerson();
		//查询教师
		teachers = new ArrayList<Teacher>();
		unitType = new ArrayList<Integer>();
		DIName_subjectionName = new ArrayList<String>();
		paraMap.put("personId", person.getId());
		//初始化teacher的相关记录
		paraMap.clear();
		paraMap.put("incomeTeacherIds", incomeIds);
		List<Teacher> allTeachers = dao.query("select t from Teacher t where t.person.id in(select distinct p.person.id  from Teacher p where p.id in (:incomeTeacherIds)) ",paraMap);
		teachers.addAll(allTeachers);
		for (int i = 0; i < teachers.size(); i++) {			
			if (teachers.get(i).getDepartment() != null){
				unitType.add(0);
				DIName_subjectionName.add(teachers.get(i).getDepartment().getUniversity().getName() + " " + teachers.get(i).getDepartment().getName());
			} else {
				unitType.add(1);
				DIName_subjectionName.add(teachers.get(i).getInstitute().getSubjection().getName() + " " + teachers.get(i).getInstitute().getName());
			}

		}
		
		HashSet tids = new HashSet();
		for (Teacher t : teachers){
			tids.add(t.getId());
		}
		session.put("tids", tids);

		academic = teacherMerger.getTmpAcademic();	
		specialityTitleId = personService.getSpecialityTitleId(academic);
		
		List<Teacher> incomeTeachers = new ArrayList<Teacher>();
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeTeachers.add(dao.query(Teacher.class,new ArrayList<Serializable>(incomeIds).get(i)));
		}
		ebs = new ArrayList<Education>();
		wes = new ArrayList<Work>();
		aes = new ArrayList<Abroad>();
		for (int i = 0; i < incomeTeachers.size(); i++) {
			ebs.addAll(incomeTeachers.get(i).getPerson().getEducation());
			wes.addAll(incomeTeachers.get(i).getPerson().getWork());
			aes.addAll(incomeTeachers.get(i).getPerson().getAbroad());
		}
		

		//将已有的照片加入文件组，以在编辑页面显示
		String groupId = "photo_" + person.getId();
		uploadService.resetGroup(groupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
		if (photoFileRealPath != null) {
			uploadService.addFile(groupId, new File(photoFileRealPath));
		}
		for (int i = 1; i < incomeTeachers.size(); i++) {		
			String otherPhotoFileRealPath = ApplicationContainer.sc.getRealPath(incomeTeachers.get(i).getPerson().getPhotoFile());
			if (otherPhotoFileRealPath != null) {
				uploadService.addFile(groupId, new File(otherPhotoFileRealPath));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 执行教师合并
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		Map paraMap = new HashMap();
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
				
		String personId = dao.query(Teacher.class,entityId).getPerson().getId();		
		Person originPerson = (Person) dao.queryUnique("select p from Person p where p.id = ?", personId);
		personService.modifyPerson(person, originPerson);
		Set<Serializable> incomePersonIds = new HashSet<Serializable>(); 
		paraMap.put("incomeTeacherIds", new ArrayList<Serializable>(incomeIds));
		incomePersonIds.addAll(dao.query("select p.id from Teacher t left join t.person p where t.id in (:incomeTeacherIds)",paraMap));
		
		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().trim().equals(person.getName().trim())){
			personService.updatePersonName(originPerson.getId(), person.getName());
		}
		//处理照片
		String groupId = "photo_" + originPerson.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			originPerson.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(originPerson.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		//处理教师
		//删除所有相关教师
		paraMap.clear();
		paraMap.put("incomePersonIds", incomePersonIds);
		dao.delete(dao.query("select  t   from Teacher t left join t.person p where p.id in (:incomePersonIds)",paraMap));
		int fulltimeCnt = 0;
		for (Teacher t : teachers){
			t.setPerson(person);
			List<Agency> list;
			paraMap.clear();
			paraMap.put("did", t.getDepartment().getId());
			list = dao.query("select ag from Department d left join d.university ag where d.id = :did", paraMap);
			if (!list.isEmpty()){
				if (!personService.checkIfUnderControl(loginer, t.getDepartment().getId(), 4, true)) {
					return ERROR;
				}
				t.setUniversity(list.get(0));
			} else {
				paraMap.clear();
				paraMap.put("iid", t.getInstitute().getId());
				list = dao.query("select ag from Institute i left join i.subjection ag where i.id = :iid", paraMap);
				if (!personService.checkIfUnderControl(loginer, t.getInstitute().getId(), 5, true)) {
					return ERROR;
				}
				t.setUniversity(list.get(0));
			}
			dao.add(t);	
			fulltimeCnt += t.getType().equals("专职人员") ? 1 : 0;
		}
	
		
		
		//学术信息合并
		Academic orignAcademic =originPerson.getAcademic();
		if(orignAcademic!=null){
			academic.setId(orignAcademic.getId());
		}
		academic.setPerson(person);
		dao.merge(academic);
	
		TeacherMerger teacherMerger =(TeacherMerger) SpringBean.getBean("teacherMerger");		
		
		incomePersonIds.remove(personId);
		teacherMerger.doMerge(personId, incomePersonIds,selectedAccountId);
		modifyInformation(originPerson);
		uploadService.flush(groupId);
		entityId = (String)dao.queryUnique("select t.id from Teacher t left join t.person p where p.id=? and t.type='专职人员'",personId);
		if(entityId!=null)
		{
			return SUCCESS;
		}
		return "deleteAll";		
	}
	
	/**
	 * 获取合并记录的原始数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String fetchMergeData() {
		List<String> unitTypes = new ArrayList<String>();
		List<String> DIName_subjectionNames = new ArrayList<String>();
		incomeTeachers = new ArrayList<Teacher>();
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
		
		Map paraMap = new HashMap();
		List result = new ArrayList();
		
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeTeachers.add(dao.query(Teacher.class, incomeIds.get(i)));
		}
		for (int i = 0; i < incomeTeachers.size(); i++) {
			incomePerson.add(incomeTeachers.get(i).getPerson());
			if(incomeTeachers.get(i).getPerson().getAcademic() != null){
				incomeAcademic.add(incomeTeachers.get(i).getPerson().getAcademic());	
				if(incomeTeachers.get(i).getPerson().getAcademic().getComputerLevel() != null){
					computerLevels.add(incomeTeachers.get(i).getPerson().getAcademic().getComputerLevel().getName());
				}else{
					computerLevels.add("");
				}
				if(incomeTeachers.get(i).getPerson().getAcademic().getExpertType() != null){
					expertTypes.add(incomeTeachers.get(i).getPerson().getAcademic().getExpertType().getName());
				}else{
					expertTypes.add("");
				}			
				specialityTitleNames.add(personService.getSpecialityTitleName(incomeTeachers.get(i).getPerson().getAcademic()));
			}else {
				incomeAcademic.add(new Academic());
				computerLevels.add("");
				expertTypes.add("");
				incomeAcademic.add(new Academic());
			}			
			
			if (incomeTeachers.get(i).getDepartment() != null){
				unitTypes.add("院系");
				DIName_subjectionNames.add(incomeTeachers.get(i).getDepartment().getUniversity().getName() + " " + incomeTeachers.get(i).getDepartment().getName());
			} else {
				unitTypes.add("基地");
				DIName_subjectionNames.add(incomeTeachers.get(i).getInstitute().getSubjection().getName() + " " + incomeTeachers.get(i).getInstitute().getName());
			}

		}
						
		jsonMap.put("incomePerson", incomePerson);		
		jsonMap.put("incomeAcademics", incomeAcademic);
		jsonMap.put("computerLevels", computerLevels);
		jsonMap.put("expertTypes", expertTypes);
		jsonMap.put("specialityTitleNames", specialityTitleNames);
		jsonMap.put("DIName_subjectionNames", DIName_subjectionNames);
		jsonMap.put("unitTypes", unitTypes);
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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}


	public List<Integer> getUnitType() {
		return unitType;
	}


	public void setUnitType(List<Integer> unitType) {
		this.unitType = unitType;
	}


	public String getSpecialityTitleId() {
		return specialityTitleId;
	}


	public void setSpecialityTitleId(String specialityTitleId) {
		this.specialityTitleId = specialityTitleId;
	}


	public List<String> getDIName_subjectionName() {
		return DIName_subjectionName;
	}


	public void setDIName_subjectionName(List<String> name_subjectionName) {
		DIName_subjectionName = name_subjectionName;
	}

	@Override
	public String pageBufferId() {
		return TeacherAction.PAGE_BUFFER_ID;
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
	public Integer idType() {
		return idType;
	}

	@Override
	public boolean modifyInner() {
		//教师修改沿用原来方法
		return false;
	}
}
