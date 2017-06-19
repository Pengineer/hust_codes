package csdc.action.person.officer;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Abroad;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Education;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Work;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.PersonInfo;
import csdc.tool.info.RightInfo;
import csdc.tool.merger.OfficerMerger;

@Transactional
public class InstituteAction extends OfficerAction {

	private static final long serialVersionUID = 2267030733918211072L;

	private final static String HQL = "select p.name, p.gender, i.name, i.id, u.name, u.id, o.id, o.position, p.id, p.isKey from Officer o left join o.person p left join o.institute i left join i.subjection u left join u.province so ";
	private final static String[] COLUMN = new String[]{
			"p.name",
			"p.gender, p.name",
			"i.name, p.name",
			"u.name, p.name",
			"o.position, p.name",
			"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "instituteOfficerPage";
	private final static String[] SEARCH_CONDITIONS = new String[]{
			"LOWER(p.name) like :keyword",
			"LOWER(i.name) like :keyword",
			"LOWER(u.name) like :keyword",
			"LOWER(o.position) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "o.id";//缓存id
	private Integer idType = 10;//基地管理人员id类型
	
	/**
	 * 构造基地管理人员的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR) || loginer.getCurrentType().equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
			hql.append(" where ");
		} else if (loginer.getCurrentType().equals(AccountType.PROVINCE)) {// 省级账号
			hql.append(" left join i.type sys where (u.type = 4 or sys.code = '02' or sys.code = '03') and u.subjection.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append(" where u.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else if (loginer.getCurrentType().equals(AccountType.INSTITUTE) && loginer.getIsPrincipal() == 1) {// 基地账号
			hql.append(" where i.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else {
			hql.append(" where 1 = 0 and ");
		}
		hql.append(" i.id is not null ");
	}
	
	public boolean addInner(){
		officer.setPerson(person);
		
		institute = (Institute) dao.query(Institute.class, instituteId);
		if (institute == null || !personService.checkIfUnderControl(loginer, institute.getId(), 5, true)){
			return false;
		}
		officer.setInstitute(institute);
		officer.setAgency(institute.getSubjection());
		
		dao.add(officer);
		entityId = officer.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("officerId", entityId);
		List result = dao.query("select officer, person, i, ag from Officer officer, Person person, Institute i, Agency ag where officer.id = :officerId and officer.person.id = person.id and officer.institute.id = i.id and i.subjection.id = ag.id ", paraMap);

		officer = (Officer) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		institute = (Institute) ((Object[]) result.get(0))[2];
		agency = (Agency) ((Object[]) result.get(0))[3];
		
		jsonMap.put("officer", officer);		
		jsonMap.put("person", person);
		jsonMap.put("instituteName", institute.getName());
		jsonMap.put("instituteId", institute.getId());
		jsonMap.put("subjectionName", agency.getName());
		jsonMap.put("subjectionId", agency.getId());
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("officerId", entityId);
		paraMap.put("officerId", entityId);
		List result = dao.query("select officer, person, i, ag from Officer officer, Person person, Institute i, Agency ag where officer.id = :officerId and officer.person.id = person.id and officer.institute.id = i.id and i.subjection.id = ag.id ", paraMap);

		officer = (Officer) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		institute = (Institute) ((Object[]) result.get(0))[2];
		agency = (Agency) ((Object[]) result.get(0))[3];

		instituteName = institute.getName();
		instituteId = institute.getId();
		subjectionName = agency.getName();
		subjectionId = agency.getId();
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
		
		//institute = (Institute) personService.query(Institute.class, instituteId);	//允许修改所在单位
		institute = (Institute) dao.query(Institute.class, originOfficer.getInstitute().getId());	//不允许修改所在单位
		if (institute == null || !personService.checkIfUnderControl(loginer, institute.getId(), 5, true)){
			return false;
		}
		originOfficer.setInstitute(institute);
		originOfficer.setAgency(institute.getSubjection());
				
		dao.modify(originOfficer);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String toMerge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{		
		Map paraMap = new HashMap();
		List result = new ArrayList();
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
		institute = (Institute)dao.queryUnique("select a from  Officer o left join o.institute a where o.id = :officerId",paraMap);
		
		instituteName = institute.getName();
		instituteId = institute.getId();
		subjectionName = agency.getName();
		subjectionId = agency.getId();
		
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

	

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getSubjectionName() {
		return subjectionName;
	}

	public void setSubjectionName(String subjectionName) {
		this.subjectionName = subjectionName;
	}

	public String getSubjectionId() {
		return subjectionId;
	}

	public void setSubjectionId(String subjectionId) {
		this.subjectionId = subjectionId;
	}

	@Override
	public String pageBufferId() {
		return InstituteAction.PAGE_BUFFER_ID;
	}
	
	@Override
	public Integer idType() {
		return idType;
	}
}
