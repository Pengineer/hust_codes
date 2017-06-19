package csdc.action.person.officer;

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

import csdc.action.person.PersonAction;
import csdc.bean.Abroad;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Education;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Work;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.bean.FileRecord;
import csdc.tool.merger.OfficerMerger;


public abstract class OfficerAction extends PersonAction {

	protected static final long serialVersionUID = 6072247796685882212L;
	protected Officer officer;// 管理人员对象
	protected List<String> officerIds;
	protected String officerId;		//管理员ID
	
	protected List<Officer> incomeOfficers;
	
	protected String departmentName;
	protected String departmentId;
	protected String universityName;
	protected String universityId;
	
	protected String instituteName;
	protected String instituteId;
	protected String subjectionName;
	protected String subjectionId;
	/**
	 * 跳转到合并页面
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String toMerge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{		
		Map paraMap = new HashMap();
		OfficerMerger officerMerger =(OfficerMerger) SpringBean.getBean("officerMerger");		
		Serializable targetId = entityId;
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		officerMerger.mergeOfficer(targetId, new ArrayList<Serializable>(incomeIds));
		//合并结果返回至前台
		person = officerMerger.getTmpPerson();
		officer = officerMerger.getTmpOfficer();
		paraMap.clear();
		paraMap.put("officerId", officer.getId());
		agency = (Agency) dao.queryUnique("select a from  Officer o left join o.agency a where o.id = :officerId",paraMap);
		unitName = agency.getName();
		unitId = agency.getId();
		
		List<Officer> incomeOfficers = new ArrayList<Officer>();
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeOfficers.add(dao.query(Officer.class,incomeIds.get(i)));
		}
		ebs = new ArrayList<Education>();
		wes = new ArrayList<Work>();
		aes = new ArrayList<Abroad>();
		for (int i = 0; i < incomeOfficers.size(); i++) {
			ebs.addAll(incomeOfficers.get(i).getPerson().getEducation());
			wes.addAll(incomeOfficers.get(i).getPerson().getWork());
			aes.addAll(incomeOfficers.get(i).getPerson().getAbroad());
		}	

		//将已有的照片加入文件组，以在编辑页面显示
		String groupId = "photo_" + person.getId();
		uploadService.resetGroup(groupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
		if (photoFileRealPath != null) {
			uploadService.addFile(groupId, new File(photoFileRealPath));
		}
		for (int i = 1; i < incomeOfficers.size(); i++) {		
			String otherPhotoFileRealPath = ApplicationContainer.sc.getRealPath(incomeOfficers.get(i).getPerson().getPhotoFile());
			if (otherPhotoFileRealPath != null) {
				uploadService.addFile(groupId, new File(otherPhotoFileRealPath));
			}
		}	
		return SUCCESS;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		Officer originOfficer = (Officer) dao.query(Officer.class, entityId);
		Person originPerson = (Person) dao.query(Person.class, originOfficer.getPerson().getId());

		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().equals(person.getName())){
			personService.updatePersonName(originPerson.getId(), person.getName());
		}
		
		originOfficer.setPosition(officer.getPosition());
		originOfficer.setStaffCardNumber(officer.getStaffCardNumber());
		originOfficer.setStartDate(officer.getStartDate());
		originOfficer.setEndDate(officer.getEndDate());
		originOfficer.setType(officer.getType());
		
		//修改机构
		if(unitId != null){
			agency = (Agency) dao.query(Agency.class, unitId); //不允许修改所在单位
			originOfficer.setAgency(agency);
		}
		if(departmentId != null){
			department = (Department) dao.query(Department.class, departmentId);	//不允许修改所在单位
			originOfficer.setDepartment(department);
			originOfficer.setAgency(department.getUniversity());
		}
		if(instituteId != null){
			institute = (Institute) dao.query(Institute.class, instituteId);	//允许修改所在单位
			originOfficer.setInstitute(institute);
			originOfficer.setAgency(institute.getSubjection());
		}
		//处理照片
		String groupId = "photo_" + originPerson.getId();
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			person.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}

		personService.modifyPerson(person, originPerson);
		dao.modify(originOfficer);
		
		OfficerMerger officerMerger =(OfficerMerger) SpringBean.getBean("officerMerger");		
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		incomeIds.remove(entityId);
		officerMerger.doMerge(entityId, new ArrayList<Serializable>(incomeIds),selectedAccountId);
		modifyInformation(originPerson);//在合并之后执行
		uploadService.flush(groupId);
		return SUCCESS;			
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String fetchMergeData() {
		incomeOfficers = new ArrayList<Officer>();
		List<Person> incomePerson = new ArrayList<Person>();
		List<Agency> incomeAgency = new ArrayList<Agency>();		
		List<String> incomeInstitutes = new ArrayList<String>();
		List<String> incomeDepartments = new ArrayList<String>();
		List<String> unitIds = new ArrayList<String>();
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		for (int i = 0; i < incomeIds.size(); i++) {
			incomeOfficers.add(dao.query(Officer.class, incomeIds.get(i)));
		}
		for (int i = 0; i < incomeOfficers.size(); i++) {
			Hibernate.initialize(incomeOfficers.get(i).getPerson());
			Hibernate.initialize(incomeOfficers.get(i).getAgency());			
			if(incomeOfficers.get(i).getInstitute()!=null){
				unitIds.add(incomeOfficers.get(i).getInstitute().getId());
				incomeInstitutes.add(incomeOfficers.get(i).getInstitute().getSubjection().getName()+" "+incomeOfficers.get(i).getInstitute().getName());
			}else if(incomeOfficers.get(i).getDepartment()!=null){
				unitIds.add(incomeOfficers.get(i).getDepartment().getId());
				incomeDepartments.add(incomeOfficers.get(i).getDepartment().getName()+" "+incomeOfficers.get(i).getDepartment().getUniversity().getName());
			}else{
				unitIds.add(incomeOfficers.get(i).getAgency().getId());
			}
			incomePerson.add(incomeOfficers.get(i).getPerson());
			incomeAgency.add(incomeOfficers.get(i).getAgency());			
		}		
		jsonMap.put("incomeOfficers", incomeOfficers);
		jsonMap.put("incomePerson", incomePerson);
		jsonMap.put("incomeAgencies", incomeAgency);
		jsonMap.put("incomeInstitutes", incomeInstitutes);
		jsonMap.put("incomeDepartments", incomeDepartments);
		jsonMap.put("unitIds", unitIds);
		return SUCCESS;
	}
	
	
	@Transactional
	public String checkIfIsOfficer() {
		Object[] result = personService.checkIfIsOfficer(idcardNumber, name, officerId);
		//将已有的照片加入文件组，以在编辑页面显示
		if(result[1] != null && result[1] instanceof Person){
			person = (Person) result[1];
			String groupId = "photo_person_add";
			uploadService.resetGroup(groupId);
			String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
			if (photoFileRealPath != null) {
				uploadService.addFile(groupId, new File(photoFileRealPath));
			}
		}
		jsonMap.put("result", result);
		return SUCCESS;
	}
	
	/**
	 * 校验管理人员
	 */
	public void validateOfficer(Officer officer) {
		if (officer.getStaffCardNumber() == null || officer.getStaffCardNumber().trim().isEmpty()){
//			this.addFieldError("officer.staffCardNumber", "职工号不应为空");
		}
		if (officer.getPosition() != null && officer.getPosition().trim().length() > 20){
			this.addFieldError("officer.position", "行政职务过长");
		}
//		if (officer.getType() == null || officer.getType().trim().isEmpty()){
//			this.addFieldError("officer.type", "人员类型不得为空");
//		} else if (!officer.getType().matches("(专职人员)|(兼职人员)|(离职人员)")){
//			this.addFieldError("officer.type", "人员类型错误");
//		}
		if (officer.getStartDate() != null && officer.getEndDate() != null && officer.getStartDate().compareTo(officer.getEndDate()) > 0){
			this.addFieldError("officer.startDate", "入职时间不应晚于离职时间");
		}
	}

	public void validateAdd(){
		validateBasicInfo(person, true);
		validateOfficer(officer);
		validateContactInfo(person, true);
		validateAddress(homeAddress);
		validateAddress(officeAddress);
	}
	
	public void validateModify() {
		validateBasicInfo(person, false);
		validateOfficer(officer);
		validateContactInfo(person, false);
		validateAddress(homeAddress);
		validateAddress(officeAddress);
		validateEWA();
	}
	
	public void validateMerge() {
		validateModify();
	}
	
	public Officer getOfficer() {
		return officer;
	}
	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public String getOfficerId() {
		return officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public List<Education> getEbs() {
		return ebs;
	}

	public void setEbs(List<Education> ebs) {
		this.ebs = ebs;
	}

	public List<Work> getWes() {
		return wes;
	}

	public void setWes(List<Work> wes) {
		this.wes = wes;
	}

	public List<Abroad> getAes() {
		return aes;
	}

	public void setAes(List<Abroad> aes) {
		this.aes = aes;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getUniversityId() {
		return universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

}
