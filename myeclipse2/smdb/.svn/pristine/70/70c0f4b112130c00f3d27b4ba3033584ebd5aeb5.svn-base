package csdc.action.person.officer;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Abroad;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
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
import csdc.tool.merger.PersonMerger;

@Transactional
public class MinistryAction extends OfficerAction {
	
	private static final long serialVersionUID = 2267030733918211072L;
	
	private final static String HQL = "select p.name, p.gender, ag.name, ag.sname, o.id, ag.id, o.position, p.id, p.isKey from Officer o left join o.person p left join o.agency ag ";
	private final static String[] COLUMN = {
		"p.name",
		"p.gender, p.name",
		"ag.name, p.name",
		"ag.sname, p.name",
		"o.position, p.name",
		"p.isKey desc, p.name"
	};// 排序列
	private final static String PAGE_NAME = "ministryOfficerPage";
	private final static String[] SEARCH_CONDITIONS = {
		"LOWER(p.name) like :keyword",
		"LOWER(ag.name) like :keyword",
		"LOWER(ag.sname) like :keyword",
		"LOWER(o.position) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "o.id";// 缓存id
	private Integer idType = 6;//部级管理人员id类型
	
	/**
	 * 构造部级管理人员的基础HQL
	 */
	@Override
	public void simpleSearchBaseHql(StringBuffer hql,Map map) {
		hql.append(HQL());
		if (loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)) {// 系统管理员
			hql.append(" where ");
		} else if (loginer.getCurrentType().equals(AccountType.MINISTRY) && loginer.getIsPrincipal() == 1) {// 部级账号
			hql.append(" where ag.id = :unitId and ");
			map.put("unitId", loginer.getCurrentBelongUnitId());
		} else {
			hql.append(" where 1 = 0 and ");
		}
		hql.append(" ag.type = 1 ");
	}
	
	public boolean addInner(){
		officer.setPerson(person);
		agency = (Agency) dao.query(Agency.class, unitId);
		if (agency == null || agency.getType() != 1 || !personService.checkIfUnderControl(loginer, agency.getId(), 1, true)) {
			return false;
		}
		officer.setAgency(agency);
		dao.add(officer);
		entityId = officer.getId();
		return true;
	}

	public void viewInner(){
		Map paraMap = new HashMap();
		paraMap.put("officerId", entityId);
		List result = dao.query("select officer, person, agency from Officer officer, Person person, Agency agency where officer.id = :officerId and officer.person.id = person.id and officer.agency.id = agency.id ", paraMap);

		officer = (Officer) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		agency = (Agency) ((Object[]) result.get(0))[2];
		
		jsonMap.put("officer", officer);		
		jsonMap.put("person", person);
		jsonMap.put("unitName", agency.getName());
		jsonMap.put("sectionName", agency.getSname());
		jsonMap.put("unitId", agency.getId());
	}
	
	public void toModifyInner(){
		Map paraMap = new HashMap();
		Map session = ActionContext.getContext().getSession();
		session.put("officerId", entityId);
		paraMap.put("officerId", entityId);
		List result = dao.query("select officer, person, agency from Officer officer, Person person, Agency agency where officer.id = :officerId and officer.person.id = person.id and officer.agency.id = agency.id", paraMap);

		officer = (Officer) ((Object[]) result.get(0))[0];
		person = (Person) ((Object[]) result.get(0))[1];
		agency = (Agency) ((Object[]) result.get(0))[2];

		unitName = agency.getName();
		unitId = agency.getId();
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
		//agency = (Agency) personService.query(Agency.class, unitId); // 允许修改所在单位
		agency = (Agency) dao.query(Agency.class, originOfficer.getAgency().getId()); //不允许修改所在单位
		if (agency == null || agency.getType() != 1 || !personService.checkIfUnderControl(loginer, agency.getId(), 1, true)) {
			return false;
		}
		originOfficer.setAgency(agency);
		dao.modify(originOfficer);
		return true;
	}
	
	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	@Override
	public String[] column() {
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
		return MinistryAction.PAGE_BUFFER_ID;
	}

	@Override
	public Integer idType() {
		return idType;
	}

}
