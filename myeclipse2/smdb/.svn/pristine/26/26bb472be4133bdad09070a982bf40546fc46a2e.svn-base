package csdc.service.imp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Log;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.ProjectGranted;
import csdc.service.IViewService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;


/**
 * 供弹出层调用的查看实现类
 * @author 龚凡
 */
@SuppressWarnings("unchecked")
public class ViewService extends BaseService implements IViewService {

	/**
	 * 根据账号ID获得其它模块查看时的数据，包括(账号名、启用状态、所属单位、
	 * 所属人员、账号类型)
	 * @param entityId
	 * @return map
	 */
	public Map viewAccount(String entityId) {
		Map map = new HashMap();

		String name = "";// 账号名称
		String status = "";// 启用状态
		String type = "";// 账号类型
		String belongUnit = "";// 所属单位
		String belongPerson = "";// 所属人员
		String errorInfo = "";

		Account account = (Account) dao.query(Account.class, entityId);
		if (account == null) {
			errorInfo = "该账号已不存在";
		} else {
			Passport passport = (Passport)dao.query(Passport.class, account.getPassport().getId());
			if(passport != null) {
				name = passport.getName();
			}
			status = (account.getStatus() == 1 ? "启用" : "停用");
			AccountType atype = account.getType();
			int isPrincipal = account.getIsPrincipal();
			if (atype.equals(AccountType.ADMINISTRATOR)) {
				type = "系统管理员账号";
			} else if (atype.equals(AccountType.MINISTRY)) {
				if (isPrincipal == 1) {
					type = "部级主账号";
				} else {
					type = "部级子账号";
				}
			} else if (atype.equals(AccountType.PROVINCE)) {
				if (isPrincipal == 1) {
					type = "省级主账号";
				} else {
					type = "省级子账号";
				}
			} else if (atype.equals(AccountType.MINISTRY_UNIVERSITY) || atype.equals(AccountType.LOCAL_UNIVERSITY)) {
				if (isPrincipal == 1) {
					type = "校级主账号";
				} else {
					type = "校级子账号";
				}
			} else if (atype.equals(AccountType.DEPARTMENT)) {
				if (isPrincipal == 1) {
					type = "院系主账号";
				} else {
					type = "院系子账号";
				}
			} else if (atype.equals(AccountType.INSTITUTE)) {
				if (isPrincipal == 1) {
					type = "基地主账号";
				} else {
					type = "基地子账号";
				}
			} else if (atype.equals(AccountType.EXPERT)) {
				type = "外部专家账号";
			} else if (atype.equals(AccountType.TEACHER )) {
				type = "教师账号";
			} else if (atype.equals(AccountType.STUDENT)) {
				type = "学生账号";
			} else {
				type = "未知类型";
			}
			
			StringBuffer hql = new StringBuffer();
			Map parMap = new HashMap();
			if (atype.equals(AccountType.EXPERT) || atype.equals(AccountType.TEACHER) || atype.equals(AccountType.STUDENT)) {// 专家、教师、学生
				hql.append("select p.name from Person p where p.id = :belongId");
				parMap.put("belongId", this.getBelongIdByAccount(account));
				belongPerson = (String) dao.query(hql.toString(), parMap).get(0);
			} else if (atype.equals(AccountType.ADMINISTRATOR)) {
			} else {
				if (isPrincipal == 1) {// 主账号
					if (atype.equals(AccountType.DEPARTMENT)) {// 院系
						hql.append("select d.name from Department d where d.id = :belongId");
						parMap.put("belongId", account.getDepartment().getId());						
						belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
					} else if (atype.equals(AccountType.INSTITUTE)) {// 基地
						hql.append("select i.name from Institute i where i.id = :belongId");
						parMap.put("belongId", account.getInstitute().getId());
						belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
					} else {// 部、省、校
						hql.append("select a.name from Agency a where a.id = :belongId");
						parMap.put("belongId", account.getAgency().getId());
						belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
					}
				} else {// 子账号
					if (atype.equals(AccountType.DEPARTMENT)) {// 院系
						hql.append("select p.name from Person p, Officer o where o.id = :belongId and o.person.id = p.id");
						parMap.put("belongId", this.getBelongIdByAccount(account));
						belongPerson = (String) dao.query(hql.toString(), parMap).get(0);
					} else if (atype.equals(AccountType.INSTITUTE)) {// 基地
						hql.append("select p.name from Person p, Officer o where o.id = :belongId and o.person.id = p.id");
						parMap.put("belongId", this.getBelongIdByAccount(account));
						belongPerson = (String) dao.query(hql.toString(), parMap).get(0);
					} else {// 部、省、校
						hql.append("select p.name from Person p, Officer o where o.id = :belongId and o.person.id = p.id");
						parMap.put("belongId", this.getBelongIdByAccount(account));
						belongPerson = (String) dao.query(hql.toString(), parMap).get(0);
					}
				}
			}
		}

		map.put("name", name);
		map.put("status", status);
		map.put("type", type);
		map.put("belongUnit", belongUnit);
		map.put("belongPerson", belongPerson);
		map.put("errorInfo", errorInfo);
		return map;
	}
	
	/**
	 * 查看通行证
	 */
	public Map viewPassport(String entityId) {
		Map map = new HashMap();
		String name = "";// 账号名称
		String status = "";// 启用状态
		Date expireDate = null;
		String errorInfo = "";
		Integer num = 0;//关联账号个数
		Passport passport = dao.query(Passport.class, entityId);
		if (passport == null) {
			errorInfo = "该通行证已不存在";
		}else {
			List accountList = dao.query("select a.id from Account a where a.passport.id = ?", entityId);
			num = accountList.size();
			name = passport.getName();
			status = (passport.getStatus() == 1 ? "启用" : "停用");
			expireDate = passport.getExpireDate();
		}
		map.put("name", name);
		map.put("status", status);
		map.put("expireDate", DateFormat.getDateInstance().format(expireDate));
		map.put("num", num);
		map.put("errorInfo", errorInfo);
		return map;
		
	}
	/**
	 * 根据人员ID获得其它模块查看时的数据，包括(姓名、性别、出生年月、
	 * 所在机构、邮箱)
	 * @param type, entityId
	 * @return map
	 */
	public Map viewPerson(int type, String entityId) {
		// type =	// 1-agency子账号-officer_agency_id;
					// 2-department子账号-officer_department_id;
					// 3-institute子账号-officer_institute_id;
					// 4-外部专家-expert;
					// 5-教师-teacher.
					// 6-学生-student.
					// 7-教师或学生或外部专家.

		Map map = new HashMap();

		String name = "";// 姓名
		String gender = "";// 性别
		Date birthday = null;// 出生年月
		String belongUnit = "";// 所在机构
		String belongDept = "";// 所在部门
		String email = "";// 邮箱
		String phone = "";// 办公电话
		String mobilePhone = ""; //手机
		String errorInfo = "";

		Person person = (Person) dao.query(Person.class, entityId);
		if (person == null) {
			errorInfo = "该人员已不存在";
		} else {
			StringBuffer hql = new StringBuffer();
			Map parMap = new HashMap();
			parMap.put("entityId", entityId);
			if(type == 1){
				hql.append("select ag.name, ag.sname from Person p, Agency ag, Officer o where p.id = :entityId and o.person.id = p.id and ag.id = o.agency.id");
				System.out.println("1******" + hql);
			}
			else if(type == 2){
				hql.append("select ag.name, d.name from Person p, Officer o, Department d, Agency ag where p.id = :entityId and o.person.id = p.id and d.id = o.department.id and d.university.id = ag.id");
				System.out.println("2******" + hql);
			}
			else if(type == 3){
				hql.append("select ag.name, i.name from Person p, Officer o, Institute i, Agency ag where p.id = :entityId and o.person.id = p.id and i.id = o.institute.id and i.subjection.id = ag.id");
				System.out.println("3******" + hql);
			}
			else if(type == 4){
				hql.append("select e.agencyName,e.divisionName,e.divisionName from Person p, Expert e where p.id = :entityId and e.person.id = p.id");
				System.out.println("4******" + hql);
			}
			else if(type == 5){
				hql.append("select u.name,i.name,d.name from Person p, Teacher t left outer join t.university u left outer join t.institute i left outer join t.department d where p.id = :entityId and t.person.id = p.id ");
				System.out.println("5******" + hql);
			}
			else if(type == 6){
				hql.append("select u.name,i.name,d.name from Person p, Student s left outer join s.university u left outer join s.institute i left outer join s.department d where p.id = :entityId and s.person.id = p.id ");
				System.out.println("6******" + hql);
			}
			else if(type == 7){
				hql.append("select tu.name,su.name,e.agencyName,ti.name,td.name,si.name,sd.name,e.divisionName from Person p left outer join p.teacher t" +
						" left outer join p.studentForPerson s left outer join p.expert e left outer join t.university tu left outer join t.institute ti left outer join t.department td" +
						" left outer join s.university su left outer join s.institute si left outer join s.department sd where p.id = :entityId");
			}
			else{
				errorInfo = "错误的人员类型";
			}
			try{
				if(type == 1 || type == 2 || type == 3){
					Object[] names = (Object[])dao.query(hql.toString(), parMap).get(0);
					belongUnit = (String)names[0];
					belongDept = (String)names[1];
				}
				else if(type == 4 || type == 5 ||type == 6){
					Object[] names = (Object[])dao.query(hql.toString(), parMap).get(0);
					belongUnit = (String)names[0];
					if(names[1]!=null)
						belongDept = (String)names[1];
					else if(names[2]!=null)
						belongDept = (String)names[2];
				}
				else if(type == 7){
					Object[] names = (Object[])dao.query(hql.toString(), parMap).get(0);
					for(int i=0;i<3;i++){
						if(names[i] != null){
							belongUnit = (String)names[i];
							break;
						}
					}
					for(int i=3;i<8;i++){
						if(names[i] != null){
							belongDept = (String)names[i];
							break;
						}
					}
				}
				else{
					Object[] names = (Object[])dao.query(hql.toString(), parMap).get(0);
					if((String)names[0]!=null){
						belongUnit = (String)names[0];
					}else if((String)names[3]!=null){
						belongUnit = (String)names[3];
					}
					if(names[1]!=null)
						belongDept = (String)names[1];
					else if(names[2]!=null)
						belongDept = (String)names[2];
					else if(names[4]!=null)
						belongDept = (String)names[4];
					else if(names[5]!=null)
						belongDept = (String)names[5];
				}
			}catch(Exception e){
			}
			name = person.getName();
			gender = person.getGender();
			birthday = person.getBirthday();
			email = person.getEmail();
			phone = person.getOfficePhone();
			mobilePhone = person.getMobilePhone();
		}

		map.put("name", name);
		map.put("gender", gender);
		map.put("birthday", birthday);
		map.put("belongUnit", belongUnit);
		map.put("belongDept", belongDept);
		map.put("email", email);
		map.put("phone", phone);
		map.put("mobilePhone", mobilePhone);
		map.put("errorInfo", errorInfo);
		return map;
	}

	/**
	 * 根据人员ID获得其它模块查看时的数据，包括(机构名称、上级管理部门、通讯地址
	 * 邮编、邮箱)
	 * @param type, entityId
	 * @return map
	 */
	public Map viewAgency(int type, String entityId) {
		// type =	// 1-agency;
					// 2-department;
					// 3-institute;

		Map map = new HashMap();

		String name = "";// 机构名称
		String belongUnit = "";// 上级管理部门
		String linkmanName = "";//联系人
		String sname = ""; // 社科管理部门名称
		String semail = ""; //社科管理部门邮箱
		String sphone = ""; // 社科管理部门电话
		String sfax = ""; // 社科管理部分传真
		String errorInfo = "";

		if(type == 1){
			Agency agency = (Agency) dao.query(Agency.class, entityId);
			if (agency == null) {
				errorInfo = "该机构已不存在";
			} else {
				StringBuffer hql = new StringBuffer();
				Map parMap = new HashMap();
				parMap.put("entityId", entityId);
				hql.append("select ags.name from Agency ag left join ag.subjection ags where ag.id=:entityId");
				belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
				name = agency.getName();
				if(agency.getSlinkman() != null){
					linkmanName = ((Person) dao.query(Person.class, agency.getSlinkman().getId())).getName();
				} else {
					linkmanName = "";
				}
				sname = agency.getSname();
				semail = agency.getSemail();
				sphone = agency.getSphone();
				sfax = agency.getSfax();
			}
		}
		else if(type == 2){
			Department department = (Department) dao.query(Department.class, entityId);
			if (department == null) {
				errorInfo = "该院系已不存在";
			} else {
				StringBuffer hql = new StringBuffer();
				Map parMap = new HashMap();
				parMap.put("entityId", entityId);
				hql.append("select ag.name from Agency ag, Department d where d.id = :entityId and ag.id = d.university.id");
				belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
				name = department.getName();
				if(department.getLinkman() != null){
					linkmanName = ((Person) dao.query(Person.class, department.getLinkman().getId())).getName();
				} else {
					linkmanName = "";
				}
				semail = department.getEmail();
				sphone = department.getPhone();
				sfax = department.getFax();
			}
		}
		else if(type == 3){
			Institute institute = (Institute) dao.query(Institute.class, entityId);
			if (institute == null) {
				errorInfo = "该基地已不存在";
			} else {
				StringBuffer hql = new StringBuffer();
				Map parMap = new HashMap();
				parMap.put("entityId", entityId);
				hql.append("select ag.name from Agency ag, Institute i where i.id = :entityId and ag.id = i.subjection.id");
				belongUnit = (String) dao.query(hql.toString(), parMap).get(0);
				name = institute.getName();
				if(institute.getLinkman() != null){
					linkmanName = ((Person) dao.query(Person.class, institute.getLinkman().getId())).getName();
				} else {
					linkmanName = "";
				}
				semail = institute.getEmail();
				sphone = institute.getPhone();
				sfax = institute.getFax();
			}
		}
		else{
			errorInfo = "错误的机构类型";
		}
		map.put("name", name);
		map.put("belongUnit", belongUnit);
		map.put("linkmanName", linkmanName);
		map.put("sname", sname);
		map.put("semail", semail);
		map.put("sphone", sphone);
		map.put("sfax", sfax);
		map.put("errorInfo", errorInfo);
		return map;
	}

	/**
	 * 根据项目ID获得其它模块查看时的数据，包括(项目名称、项目类型、年度
	 * 负责人、依托高校)
	 * @param projectTypeId, entityId
	 * @return map
	 */
	public Map viewProject(String projectTypeId, String entityId) {
		Map map = new HashMap();
		Object[] project=new String[4];
		String errorInfo = "";
//		String code = "";
//		if(projectTypeId.equals("general")){
//			code = "01";
//		}else if(projectTypeId.equals("key")){
//			code = "04";
//		}else if(projectTypeId.equals("instp")){
//			code = "02";
//		}else if(projectTypeId.equals("post")){
//			code = "03";
//		}else if(projectTypeId.equals("entrust")){
//			code = "05";
//		}
		String projectType = ProjectGranted.findTypeName(projectTypeId);
		StringBuffer hql = new StringBuffer();
		Map parMap = new HashMap();
		parMap.put("entityId", entityId);
		if(projectTypeId.equals("general")){//一般项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from GeneralGranted gra, GeneralApplication app left outer join gra.university uni ")
				.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("key")){//重大攻关项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from KeyGranted gra, KeyApplication app left outer join gra.university uni ")
				.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("instp")){//基地项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from InstpGranted gra, InstpApplication app left outer join gra.university uni ")
				.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("post")){//后期资助项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from PostGranted gra, PostApplication app left outer join gra.university uni ")
				.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("entrust")){//委托应急课题
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from EntrustGranted gra, EntrustApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if (projectTypeId.endsWith("ByAppId")) {//根据项目申报ID查看项目，例如：projectTypeId = generalByAppId -> general 
			parMap.put("projectType", projectTypeId.substring(0, projectTypeId.indexOf("ByAppId")));
			hql.append("select pa.name, pa.year, pa.applicantName, ag.name, so.name from ProjectApplication pa left outer join pa.university ag left join pa.subtype so ")
				.append("where pa.id=:entityId and pa.type = :projectType");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
			projectType = (String) project[4];
		}else{
			errorInfo = "错误的项目类型";
		}
		map.put("name", project[0]);// 项目名称
		map.put("projectType", projectType);// 项目类型
		map.put("year", project[1]);// 年度
		map.put("memberName", project[2]);// 负责人
		map.put("agencyName", project[3]);// 依托高校
		map.put("errorInfo", errorInfo);
		return map;
	}
	
	
	/**
	 * 根据项目拨款ID及项目类别获得其它模块查看时的数据，包括(项目名称、项目类型、年度
	 * 负责人、依托高校)
	 * @param projectTypeId, entityId
	 * @return map
	 */
	public Map viewProjectFunding(String projectTypeId, String entityId) {
		Map map = new HashMap();
		Object[] project=new String[4];
		String errorInfo = "";
//		String code = "";
//		if(projectTypeId.equals("general")){
//			code = "01";
//		}else if(projectTypeId.equals("key")){
//			code = "04";
//		}else if(projectTypeId.equals("instp")){
//			code = "02";
//		}else if(projectTypeId.equals("post")){
//			code = "03";
//		}else if(projectTypeId.equals("entrust")){
//			code = "05";
//		}
//		SystemOption projectType = this.systemOptionDao.query("projectType", code);
		String projectType = ProjectGranted.findTypeName(projectTypeId);
		StringBuffer hql = new StringBuffer();
		Map parMap = new HashMap();
		parMap.put("entityId", entityId);
		if(projectTypeId.equals("general")){//一般项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from GeneralGranted gra, GeneralApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("key")){//重大攻关项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from KeyGranted gra, KeyApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("instp")){//基地项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from InstpGranted gra, InstpApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
		}else if(projectTypeId.equals("post")){//后期资助项目
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from PostGranted gra, PostApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
			;
		}else if(projectTypeId.equals("entrust")){//委托应急课题
			hql.append("select gra.name,app.year,gra.applicantName,uni.name from EntrustGranted gra, EntrustApplication app left outer join gra.university uni ")
			.append("where gra.id=:entityId and gra.application.id=app.id");
			project =(Object[])dao.query(hql.toString(), parMap).get(0);
			;
		}else{
			errorInfo = "错误的项目类型";
		}
		map.put("name", project[0]);// 项目名称
		map.put("projectType", projectType);// 项目类型
		map.put("year", project[1]);// 年度
		map.put("memberName", project[2]);// 负责人
		map.put("agencyName", project[3]);// 依托高校
		map.put("errorInfo", errorInfo);
		return map;
	}
	
	/**
	 * 查看日志
	 * @param entityId 日志ID
	 * @return map
	 */
	public Map viewLog(String entityId) {
		Map map = new HashMap();

		String accountName = "";
		String datestr = "";
		String ip = "";
		String eventCode = "";
		String eventDesc = "";
		
		Log log = (Log) dao.query(Log.class, entityId);
		if (log != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			datestr = dateformat.format(log.getDate());
			accountName = log.getAccountName();
			ip = log.getIp();
			eventCode = log.getEventCode();
			eventDesc = log.getEventDscription();
		}
		
		map.put("datestr", datestr);
		map.put("accountName", accountName);
		map.put("ip", ip);
		map.put("eventCode", eventCode);
		map.put("eventDesc", eventDesc);
		return map;
	}
	
	/**
	 * 备忘提醒
	 * @return map
	 */
	@SuppressWarnings("static-access")
	public Map viewMemo(int flag) {
		Map map = new HashMap(); //将map1 和 map2 放入map
		Map map1 = new HashMap();//将list中的每条记录的当前登录用户id 和list记录的条数放入map1
		
//		String HQL = "select m.id, m.title, m.content, m.updateTime, m.remindTime, m.isRemind, m.remindWay, m.week, m.month, " + 
//	    "m.startDateDay, m.endDateDay, m.excludeDate, m.startDateWeek, m.endDateWeek, m.reverseRemindTime, m.remindDay, m.startRemindTime from " +
//		"Memo m left outer join m.account a where 1=1 ";
		String HQL = "select m.id, m.title, m.content, m.updateTime, m.remindTime, m.isRemind, m.remindWay, m.week, m.month, " + 
	    "m.startDateDay, m.endDateDay, m.excludeDate, m.startDateWeek, m.endDateWeek from " +
		"Memo m left outer join m.account a where 1=1 ";
		StringBuffer hql = new StringBuffer();
		hql.append(HQL);
		LoginInfo loginer = (LoginInfo) ActionContext.getContext().getSession().get(GlobalInfo.LOGINER);
		map1.put("accountid", loginer.getCurrentAccountId());
		hql.append(" and m.account.id = :accountid and m.isRemind = 1 ");//当前登录用户只能看到自己的备忘且需要提醒的
		
		List list = (List) dao.query(hql.toString(), map1);
		
		GregorianCalendar calendar = new GregorianCalendar();//获取当前是周几(用于方式3:按周提醒)
		calendar.setTime(new Date());//获取当前系统日期，并格式化（用于方式1:按指定日期提醒）
		if(flag != 1){
			map = this.dealWithMemo(list, calendar);
		}else{//用户中心首页列表
//			int size = 0;//每天的条数
//			int totalSize = 0;//总条数
			Map<String, Map> parMap = new HashMap<String, Map>();
//			Map mapCnt = new HashMap();//备忘条数
			Map mapId = new HashMap(); //备忘id
			ArrayList memoIds = new ArrayList();
			Set setIds = new HashSet();
			for(int i = 0; i <= 30; i++){
				calendar.add(calendar.DAY_OF_YEAR, (i == 0)? 0 : 1);//在当前日期下加一天
				parMap = this.dealWithMemo(list, calendar);
//				mapCnt = parMap.get("map1");//获取当天要提醒的备忘条数
//				size = (mapCnt != null && mapCnt.get("memoCount") != null) ? Integer.parseInt(mapCnt.get("memoCount").toString()) : 0;
//				totalSize += size;
				
				//处理备忘记录
				mapId = parMap.get("map2");
				if(mapId != null && !mapId.isEmpty()){
					Set set = mapId.keySet(); 
					Iterator iterator = set.iterator(); 
					String key = null; 
					while(iterator.hasNext()) 
					{ 
					   key = (String)iterator.next();
//					   memoIds.add(key);//将备忘的id存入数组中
					   setIds.add(key);
					} 
				}
			}
//			setIds.addAll(memoIds);
//			memoIds.clear();
			memoIds.addAll(setIds);
			mapId.put("memoIds", memoIds);
//			String hql4HomeMemo = "select m.id, m.title, m.updateTime from Memo m left join m.account a where m.id in (:memoIds) order by m.updateTime desc, m.id desc";
			String hql4HomeMemo = "select m.id, m.title, m.updateTime from Memo m left join m.account a where m.id in (:memoIds)";
			List homeRemind = null;
			if(memoIds.size() != 0){
				homeRemind = dao.query(hql4HomeMemo, mapId, 0, 5);
			}
			map.put("homeRemind", homeRemind);
		}
		return map;
   }
	


	public Map dealWithMemo(List list, GregorianCalendar calendar){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		int weekday = calendar.get(Calendar.DAY_OF_WEEK)-1;//周日：0
		Date date = calendar.getTime();//获取当前日期
		Map map = new HashMap(); //将map1 和 map2 放入map
		Map map1 = new HashMap();//将list中的每条记录的当前登录用户id 和list记录的条数放入map1
		Map map2 = new HashMap();//将list中的每条记录的备忘id 和 备忘title放入map2
		int n1=0;//包含当天日期的备忘条数
		@SuppressWarnings("unused")
		int n3=0;//包含当前周次的备忘条数
		List memos = new ArrayList(); //定义一个memos用来存放满足条件的list条目
		for(int i = 0; i<list.size(); i++){//遍历所有条目
			Object[] ob = (Object[]) list.get(i); //将第i条记录转换成数组
			int remindType = Integer.parseInt(ob[6].toString());
			switch (remindType){
			case 1://判断是否是按指定日期提醒-方式1
				if(null!=ob[4] && !ob[4].toString().isEmpty()){
					if(dateformat.format(date).equals(dateformat.format((Date) ob[4])) ){//判断提醒日期是否与当前日期相同
						n1 = n1 + 1;//提醒日期与当天日期相同
						memos.add(list.get(i));//将满足条件的加入memos中
					}
				}
				break;
			case 2:
				if(dateformat.format(date).compareTo(dateformat.format((Date) ob[9]))>=0 && dateformat.format(date).compareTo(dateformat.format((Date) ob[10]))<=0){
					boolean isAdd = true;
					if(null==ob[11] || ob[11].toString().isEmpty()){
						n1 = n1 + 1;
						  memos.add(list.get(i));
					}
					else{
					String[] monthStrs = ((String) ob[11]).split("[;,]");
					for(String monthStr : monthStrs){
						Date monthDate = null;
						try {
							monthDate = dateformat.parse(monthStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if (dateformat.format(date).equals(dateformat.format(monthDate)) ){
							isAdd = false;
							break;
						}
			        }
					if(isAdd){
					  n1 = n1 + 1;
					  memos.add(list.get(i));
					}
				}
				}	
				break;
			case 3://按周
				String[] weekStrs = ((String) ob[7]).split("[;,]");
				for(String weekStr : weekStrs){
					int todayWeek = Integer.parseInt(weekStr.trim());
					if(todayWeek == weekday){
						if(dateformat.format(date).compareTo(dateformat.format((Date) ob[12]))>=0 && dateformat.format(date).compareTo(dateformat.format((Date) ob[13]))<=0){
							n1 = n1 + 1;
							memos.add(list.get(i));
						}
						else break;
					}
				}
				break;
			case 4://按月
				if(null!=ob[8] && !ob[8].toString().isEmpty()){
					String[] monthStrs = ((String) ob[8]).split("[;]");
					for(String monthStr : monthStrs){
						Date monthDate = null;
						try {
							monthDate = dateformat.parse(monthStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if (dateformat.format(date).equals(dateformat.format(monthDate)) ){
							n1 = n1 + 1;
							memos.add(list.get(i));
							}
						}
			     }
			     break;
//			case 5://按倒计时
//				if(null!=ob[14] && !ob[14].toString().isEmpty()){
//					if(dateformat.format(date).compareTo(dateformat.format((Date) ob[14]))>=0){//判断提醒时间已过或者与当前时间相同
//						n1 = n1 + 1;//提醒日期与当天日期相同
//						memos.add(list.get(i));//将满足条件的加入memos中
//					}
//				}
//				break;
//			case 3:{//判断是否是按周提醒-方式3
//				if(null!=ob[7] && !ob[7].toString().isEmpty()&& null!=ob[11] && null!=ob[12]){//判断week字段不为空
//					if(dateformat.format(nowDate).compareTo(dateformat.format((Date) ob[11]))>=0 && dateformat.format(nowDate).compareTo(dateformat.format((Date) ob[12]))<= 0 ){
//						String[] s = ob[7].toString().split("; ");//获取提醒按周提醒的值并分拆字符串
//						for(int j = 0; j<s.length; j++){
//							int newWeek = Integer.parseInt(s[j]);
//							if(newWeek == weekday-1){
//								n3 = n3 + 1;//星期有一个匹配则 n3 加 1
//								memos.add(list.get(i));//将满足条件的加入memos中
//							}
//						}
//					}
//				}
//			}
//			   break;
			default:
			   break;
			}
		}
		map1.put("memoCount", memos.size());//将满足条件的list记录的条数放入map1
		
		if(memos != null && !memos.isEmpty()){
			for(int j=0;j<memos.size();j++){
				Object[] o = (Object[]) memos.get(j);
				map2.put(o[0], o[1]);	
		      }
		}
		map.put("map1", map1);
		map.put("map2", map2);
		return map;
	}
	public Map viewMemo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查看团队
	 * @param entityId 团队ID
	 * @return map
	 */
	public Map viewOrganization(String entityId) {
		Map map = new HashMap();

		String name = "";
		String directorName = "";
		
		String agencyName = "";
		String divisionName = "";
		
		String discipline = "";
		String email = "";// 邮箱
		
		String officePhone = "";// 办公电话
		String officePostcode = "";// 办公邮编
		
		String member = "";
		
		Product organization = (Product) dao.query(Product.class, entityId);
		if (organization != null) {
			name = organization.getOrgName();
			Person director =  (Person)dao.query(Person.class, organization.getOrgPerson().getId());
			directorName = director.getName();
			agencyName = organization.getAgencyName();
			divisionName = organization.getDivisionName();
			discipline = organization.getDiscipline();
			email = organization.getOrgEmail();// 邮箱
			officePhone = organization.getOrgOfficePhone();// 办公电话
			officePostcode = organization.getOrgOfficePostcode();// 办公邮编
			member = organization.getOrgMember();
		}
		
		map.put("name", name);
		map.put("directorName", directorName);
		map.put("agencyName", agencyName);
		map.put("divisionName", divisionName);
		map.put("discipline", discipline);
		map.put("email", email);
		map.put("officePhone", officePhone);
		map.put("officePostcode", officePostcode);
		map.put("member", member);
		return map;
	}
}
