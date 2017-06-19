package csdc.action.person.officer;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Abroad;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Education;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Work;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.merger.OfficerMerger;

@Transactional
public class DepartmentAction extends OfficerAction {

	private static final long serialVersionUID = 2267030733918211072L;

	private final static String HQL = "select p.name, p.gender, d.name, d.id, u.name, u.id, o.id, o.position, p.id, p.isKey from Officer o left join o.person p left join o.department d left join d.university u left join u.province so ";
	private final static String[] COLUMN = new String[]{
			"p.name",
			"p.gender, p.name",
			"d.name, p.name",
			"u.name, p.name",
			"o.position, p.name",
			"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "departmentOfficerPage";
	private final static String[] SEARCH_CONDITIONS = new String[]{
			"LOWER(p.name) like :keyword",
			"LOWER(d.name) like :keyword",
			"LOWER(u.name) like :keyword",
			"LOWER(o.position) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "o.id";//缓存id
	private Integer idType = 9;//院系管理人员id类型

	/**
	 * 构造院系管理人员的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
			hql.append(" where ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
			hql.append(" where u.type = 4 and u.subjection.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append(" where u.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.DEPARTMENT) && loginer.getIsPrincipal() == 1) {// 院系账号
			hql.append(" where d.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else {
			hql.append(" where 1 = 0 and ");
		}
		hql.append(" d.id is not null ");
	}
	
	public boolean addInner(){
		officer.setPerson(person);
		department = (Department) dao.query(Department.class, departmentId);
		if (department == null || !personService.checkIfUnderControl(loginer, department.getId(), 4, true)){
			return false;
		}
		officer.setDepartment(department);
		officer.setAgency(department.getUniversity());
		officer.setDivisionName(department.getName());
		officer.setAgencyName(dao.query(Agency.class,department.getUniversity().getId()).getName());
		dao.add(officer);
		entityId = officer.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("officerId", entityId);
		List<Object[]> result = dao.query("select officer, person, d, uni from Officer officer, Person person, Department d, Agency uni where officer.id = :officerId and officer.person.id = person.id and officer.department.id = d.id and d.university.id = uni.id ", paraMap);

		officer = (Officer)result.get(0)[0];
		person = (Person)result.get(0)[1];
		department = (Department)result.get(0)[2];
		Agency university = (Agency)result.get(0)[3];
		
		jsonMap.put("officer", officer);		
		jsonMap.put("person", person);
		jsonMap.put("departmentId", department.getId());
		jsonMap.put("subjectionId", university.getId());
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("officerId", entityId);
		paraMap.put("officerId", entityId);
		List result = dao.query("select officer, person, d, ag from Officer officer, Person person, Department d, Agency ag where officer.id = :officerId and officer.person.id = person.id and officer.department.id = d.id and d.university.id = ag.id ", paraMap);

		officer = (Officer) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		department = (Department) ((Object[]) result.get(0))[2];
		agency = (Agency) ((Object[]) result.get(0))[3];

		departmentName = department.getName();
		departmentId = department.getId();
		universityName = agency.getName();
		universityId = agency.getId();
	}
	
	public boolean modifyInner(){
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get("officerId");
		Officer originOfficer = (Officer) dao.query(Officer.class, entityId);
		originPerson = (Person) dao.query(Person.class, originOfficer.getPerson().getId());
		
		originOfficer.setPosition(officer.getPosition());
		originOfficer.setStaffCardNumber(officer.getStaffCardNumber());
		originOfficer.setStartDate(officer.getStartDate());
		originOfficer.setEndDate(officer.getEndDate());
		originOfficer.setType(officer.getType());
		
		//department = (Department) personService.query(Department.class, departmentId);	//允许修改所在单位
		department = (Department) dao.query(Department.class, originOfficer.getDepartment().getId());	//不允许修改所在单位
		if (department == null || !personService.checkIfUnderControl(loginer, department.getId(), 4, true)){
			return false;
		}
		originOfficer.setDepartment(department);
		originOfficer.setAgency(department.getUniversity());
		originOfficer.setDivisionName(department.getName());
		originOfficer.setAgencyName(dao.query(Agency.class,department.getUniversity().getId()).getName());
		return true;
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
		OfficerMerger officerMerger =(OfficerMerger) SpringBean.getBean("officerMerger");		
		Serializable targetId = entityId;
		List<Serializable> incomeIds = new ArrayList<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		officerMerger.mergeOfficer(targetId, incomeIds);
		//合并结果返回至前台
		person = officerMerger.getTmpPerson();
		officer = officerMerger.getTmpOfficer();
		paraMap.clear();
		paraMap.put("officerId", officer.getId());
		agency = (Agency) dao.queryUnique("select a from  Officer o left join o.agency a where o.id = :officerId",paraMap);
		department = (Department)dao.queryUnique("select a from  Officer o left join o.department a where o.id = :officerId",paraMap);
		universityName = agency.getName();
		universityId = agency.getId();
		departmentName = department.getName();
		departmentId = department.getId();
		
		paraMap.clear();
		paraMap.put("incomeOfficerIds", incomeIds);
		//List<Officer> incomeOfficers = dao.query("select o from Officer o where o.id in (:incomeOfficerIds)", paraMap);
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
		for (int i = 0; i < incomeOfficers.size(); i++) {		
			String otherPhotoFileRealPath = ApplicationContainer.sc.getRealPath(incomeOfficers.get(i).getPerson().getPhotoFile());
			if (otherPhotoFileRealPath != null) {
				uploadService.addFile(groupId, new File(otherPhotoFileRealPath));
			}
		}
		
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
	public String[] searchConditions() {
		return SEARCH_CONDITIONS;
	}

	@Override
	public String pageBufferId() {
		return DepartmentAction.PAGE_BUFFER_ID;
	}
	@Override
	public Integer idType() {
		return idType;
	}
}
