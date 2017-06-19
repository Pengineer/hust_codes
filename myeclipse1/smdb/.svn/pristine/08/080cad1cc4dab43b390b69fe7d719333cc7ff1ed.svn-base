package csdc.action.mobile.basis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.mobile.MobileAction;
import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.service.IMobilePersonService;
import csdc.service.IPersonService;
import csdc.service.IProductService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.bean.AccountType;

/**
 * mobile人员模块
 * @author fengcl
 */
@SuppressWarnings("unchecked")
public class MobilePersonAction extends MobileAction{

	@Autowired
	private IPersonService personService;
	@Autowired
	private IMobilePersonService mobilePersonService;
	@Autowired
	private IProjectExtService projectExtService;
	@Autowired
	private IProductService productService;
	private static final long serialVersionUID = -2561514583894277241L;
	private static final String PAGENAME = "mobilePersonPage";
	
	private static final String HQL4MPUCOMMON = "select p.name, ag.name, o.id, o.position, ag.sname, p.gender from Officer o left join o.person p left join o.agency ag";//部省校管理人员
	private static final String HQL4DEPARTMENT = "select p.name, u.name, o.id, o.position, d.name, p.gender from Officer o left join o.person p left join o.department d left join d.university u";//院系管理人员
	private static final String HQL4INSTITUTE = "select p.name, u.name, o.id, o.position, i.name, p.gender from Officer o left join o.person p left join o.institute i left join i.subjection u";//基地管理人员
	private final static String HQL4EXPERT = "select p.name, e.agencyName, e.id, ac.specialityTitle, e.divisionName, p.gender from Expert e left join e.person p left join p.academic ac";//专家
	private final static String HQL4TEACHER = "select p.name, u.name, t.id, ac.specialityTitle, CONCAT(d.name, i.name), p.gender from Teacher t left join t.person p left join t.department d left join t.institute i left join t.university u left join p.academic ac";//教师
	private final static String HQL4STUDENT = "select p.name, u.name, s.id, s.type, CONCAT(d.name, i.name), p.gender from Student s left join s.person p left join s.department d left join s.institute i left join s.university u";//学生
	
	private String personType;//人员类型
		
	private Person person;
	private Officer officer;
	private Agency agency;
	private Department department;
	private Institute institute;
	private Expert expert;
	private Teacher teacher;
	private Student student;

	private Academic academic;
	
	//用于高级检索
	private String name;//高级检索人员姓名
	private String gender;// 高级检索人员性别
	private String startAge;//高级检索人员开始年龄
	private String endAge;//高级检索人员结束年龄
	private String unitName;//高级检索人员机构
	private String deptName;//高级检索人员部门/院系
	private String position;// 高级检索人员职务
	private String specialityTitle;//高级检索人员专业职称
	private String disciplineType;//高级检索人员学科门类
	private String researchField;//高级检索人员研究领域
	private String staffCardNumber;//高级检索人员工作证号
	
	//用于查看详情
	private String personId;
	private String agencyId;
	private String academicId;	 

	//隐藏类初始化法
	//OFFICERITEMS：社科管理人员列表
	private static final ArrayList OFFICERITEMS = new ArrayList();
	static{
		OFFICERITEMS.add("部级管理人员#1");
		OFFICERITEMS.add("省级管理人员#2");
		OFFICERITEMS.add("高校管理人员#3");
		OFFICERITEMS.add("院系管理人员#4");
		OFFICERITEMS.add("基地管理人员#5");
	}
	//RESEARCHERITEMS：社科研究人员列表
	private static final ArrayList RESEARCHERITEMS = new ArrayList();
	static{
		RESEARCHERITEMS.add("外部专家#1");
		RESEARCHERITEMS.add("教师#2");
		RESEARCHERITEMS.add("学生#3");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		List officerItems = null;
		List researcherItems = null;
		switch (accountType) {
		case ADMINISTRATOR://管理员
		case MINISTRY://部级管理人员
			officerItems = OFFICERITEMS;
			researcherItems = RESEARCHERITEMS;
			mainItems.put("Officer", officerItems);
			mainItems.put("Researcher", researcherItems);
			break;
		case PROVINCE://省级管理人员
			officerItems = OFFICERITEMS.subList(1, 5);
			researcherItems = RESEARCHERITEMS.subList(1,3);
			mainItems.put("Officer", officerItems);
			mainItems.put("Researcher", researcherItems);
			break;
		case LOCAL_UNIVERSITY:
		case MINISTRY_UNIVERSITY://高校管理人员	
			officerItems = OFFICERITEMS.subList(2, 5);
			researcherItems = RESEARCHERITEMS.subList(1,3);
			mainItems.put("Officer", officerItems);
			mainItems.put("Researcher", researcherItems);
			break;	
		case DEPARTMENT://院系管理人员	
			officerItems = OFFICERITEMS.subList(3, 4);
			researcherItems = RESEARCHERITEMS.subList(1,3);
			mainItems.put("Officer", officerItems);
			mainItems.put("Researcher", researcherItems);
			break;	
		case INSTITUTE://基地管理人员	
			officerItems = OFFICERITEMS.subList(4, 5);
			researcherItems = RESEARCHERITEMS.subList(1,3);
			mainItems.put("Officer", officerItems);
			mainItems.put("Researcher", researcherItems);
			break;
		case EXPERT://外部专家
		case TEACHER://教师
		case STUDENT://学生
			mainItems.put("无法查看人员列表", null);
			break;			
		}
		jsonMap.put("listItem", mainItems);
		return SUCCESS;	
	}
	
	/**
	 * 初级检索列表
	 */
	public String simpleSearch(){
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//登录者所属id
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if("Officer".equals(personType)){
			switch (listType) {
			case 1://部级管理人员
				hql.append(HQL4MPUCOMMON);//append()用途是当需要大量的字符串拼接时使用  优点效率比+=要高很多 （+=内存中是相当于创建副本重新赋值，StringBuffer是指针的引用）
				hql.append(" where ag.type = 1");//系统管理员查看所有
				if (accountType.equals(AccountType.MINISTRY) && loginer.getIsPrincipal() == 1) {// 部级账号
					hql.append(" and ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下无法查看
					//String 是字符串,它的比较用compareTo方法,它从第一位开始比较,如果遇到不同的字符,则马上返回这两个字符的ASCII值差值..返回值是int类型
					hql.append(" and 1 = 0");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 2://省级管理人员
				hql.append(HQL4MPUCOMMON);
				hql.append(" where ag.type = 2");// 系统管理员、部级账号查看所有
				if (accountType.equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级主账号
					hql.append(" and ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if(accountType.compareTo(AccountType.PROVINCE) > 0){//省级以下无法查看
					hql.append(" and 1 = 0");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 3://高校管理人员
				hql.append(HQL4MPUCOMMON);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where (ag.type = 3 or ag.type = 4)");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" where ag.type = 4 and ag.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && loginer.getIsPrincipal() == 1) {// 校级主账号
					hql.append(" where ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}
				hql.append(" and o.department.id is null and o.institute.id is null");//且不是院系和基地管理员
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 4://院系管理人员
				hql.append(HQL4DEPARTMENT);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where 1 = 1");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" where u.type = 4 and u.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
					hql.append(" where u.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.DEPARTMENT) && loginer.getIsPrincipal() == 1) {// 院系账号
					hql.append(" where d.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}
				hql.append(" and d.id is not null ");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(u.name) like :keyword or LOWER(d.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 5://基地管理人员
				hql.append(HQL4INSTITUTE);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where ");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" left join i.type sys where (u.type = 4 or sys.code = '02' or sys.code = '03') and u.subjection.id = :belongUnitId and ");
					parMap.put("belongUnitId", belongUnitId);
				} else if (loginer.getCurrentType().equals(AccountType.MINISTRY_UNIVERSITY) || loginer.getCurrentType().equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
					hql.append(" where u.id = :belongUnitId and ");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.INSTITUTE) && loginer.getIsPrincipal() == 1) {// 基地账号
					hql.append(" where i.id = :belongUnitId and ");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0 and ");
				}
				hql.append(" i.id is not null ");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(u.name) like :keyword or LOWER(i.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
		    }
		}
		else if ("Researcher".equals(personType)){
			switch(listType){
			case 1://外部专家
				hql.append(HQL4EXPERT);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
					hql.append(" where 1 = 1");
				} else {
					hql.append(" where 1 = 0");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(e.agencyName) like :keyword or LOWER(e.divisionName) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 2://教师
				hql.append(HQL4TEACHER);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
					hql.append(" where 1 = 1");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号，根据教师所在单位是否归省管进行判断
					hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4) and u.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据教师所在单位是否归校管进行判断
					hql.append(" where u.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.DEPARTMENT)) {// 院系账号，根据教师是否在当前院系判断
					hql.append(" where d.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.INSTITUTE)) {// 基地账号，根据教师是否在当前基地判断
					hql.append(" where i.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}
				hql.append(" and t.type = '专职人员' ");
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(u.name) like :keyword or LOWER(CONCAT(d.name, i.name)) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			case 3://学生
				hql.append(HQL4STUDENT);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
					hql.append(" where 1 = 1");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号，根据学生所在单位是否归省管进行判断
					hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4 ) and u.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据学生所在单位是否归校管进行判断
					hql.append(" where u.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.DEPARTMENT)) {// 院系账号，根据学生是否在当前院系判断
					hql.append(" where d.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.INSTITUTE)) {// 基地账号，根据学生是否在当前基地判断
					hql.append(" where i.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(p.name) like :keyword or LOWER(u.name) like :keyword or LOWER(CONCAT(d.name, i.name)) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				break;
			}
		}
		hql.append(" order by p.name asc");//默认按照姓名排序
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 高级检索
	 */
	public String advSearch(){
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//belongUnitId：subjectionId 检索范围
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if("Officer".equals(personType)){
			switch (listType) {
			case 1://部级管理人员
				hql.append(HQL4MPUCOMMON);
				hql.append(" where ag.type = 1");//系统管理员查看所有
				if (accountType.equals(AccountType.MINISTRY) && loginer.getIsPrincipal() == 1) {// 部级账号
					hql.append(" and ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下无法查看
					hql.append(" and 1 = 0");
				}
			
				// 处理查询条件
				if(unitName != null && !unitName.isEmpty())	{
					hql.append(" and LOWER(ag.name) like :unitName");
					parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
				}
				if(deptName != null && !deptName.isEmpty())	{
					hql.append(" and LOWER(ag.sname) like :deptName");
					parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
				}
				if(position != null && !position.isEmpty()){
					hql.append(" and LOWER(o.position) like :position");
					parMap.put("position", "%" + position.toLowerCase() + "%");
				}
				if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
					hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
					parMap.put("staffCardNumber", "%" + staffCardNumber.toLowerCase() + "%");
				}
				break;
			case 2://省级管理人员
				hql.append(HQL4MPUCOMMON);
				hql.append(" where ag.type = 2");// 系统管理员、部级账号查看所有
				if (accountType.equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级主账号
					hql.append(" and ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if(accountType.compareTo(AccountType.PROVINCE) > 0){//省级以下无法查看
					hql.append(" and 1 = 0");
				}

				// 处理查询条件
				if(unitName != null && !unitName.isEmpty())	{
					hql.append(" and LOWER(ag.name) like :unitName");
					parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
				}
				if(deptName != null && !deptName.isEmpty())	{
					hql.append(" and LOWER(ag.sname) like :deptName");
					parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
				}
				if(position != null && !position.isEmpty()){
					hql.append(" and LOWER(o.position) like :position");
					parMap.put("position", "%" + position.toLowerCase() + "%");
				}
				if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
					hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
					parMap.put("staffCardNumber", "%" + staffCardNumber.toLowerCase() + "%");
				}
				break;
			case 3://高校管理人员
				hql.append(HQL4MPUCOMMON);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where (ag.type = 3 or ag.type = 4)");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" where ag.type = 4 and ag.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if ((accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) && loginer.getIsPrincipal() == 1) {// 校级主账号
					hql.append(" where ag.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}
				hql.append(" and o.department.id is null and o.institute.id is null");//且不是院系和基地管理员
				
				// 处理查询条件
				if(unitName != null && !unitName.isEmpty())	{
					hql.append(" and LOWER(ag.name) like :unitName");
					parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
				}
				if(deptName != null && !deptName.isEmpty())	{
					hql.append(" and LOWER(ag.sname) like :deptName");
					parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
				}
				if(position != null && !position.isEmpty()){
					hql.append(" and LOWER(o.position) like :position");
					parMap.put("position", "%" + position.toLowerCase() + "%");
				}
				if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
					hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
					parMap.put("staffCardNumber", "%" + staffCardNumber.toLowerCase() + "%");
				}
				break;
			case 4://院系管理人员
				hql.append(HQL4DEPARTMENT);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where 1 = 1");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" where u.type = 4 and u.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
					hql.append(" where u.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.DEPARTMENT) && loginer.getIsPrincipal() == 1) {// 院系账号
					hql.append(" where d.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}

				// 处理查询条件
				if(unitName != null && !unitName.isEmpty())	{
					hql.append(" and LOWER(u.name) like :unitName");
					parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
				}
				if(deptName != null && !deptName.isEmpty())	{
					hql.append(" and LOWER(d.name) like :deptName");
					parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
				}
				if(position != null && !position.isEmpty()){
					hql.append(" and LOWER(o.position) like :position");
					parMap.put("position", "%" + position.toLowerCase() + "%");
				}
				if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
					hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
					parMap.put("staffCardNumber", "%" + staffCardNumber.toLowerCase() + "%");
				}
				break;
			case 5://基地管理人员
				hql.append(HQL4INSTITUTE);
				if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员、部级账号
					hql.append(" where 1 = 1");
				} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号
					hql.append(" left join i.type sys where (u.type = 4 or sys.code = '02' or sys.code = '03') and u.subjection.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
					hql.append(" where u.id = :unitId");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.equals(AccountType.INSTITUTE) && loginer.getIsPrincipal() == 1) {// 基地账号
					hql.append(" where i.id = :belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				} else {
					hql.append(" where 1 = 0");
				}

				// 处理查询条件
				if(unitName != null && !unitName.isEmpty())	{
					hql.append(" and LOWER(u.name) like :unitName");
					parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
				}
				if(deptName != null && !deptName.isEmpty())	{
					hql.append(" and LOWER(i.name) like :deptName");
					parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
				}
				if(position != null && !position.isEmpty()){
					hql.append(" and LOWER(o.position) like :position");
					parMap.put("position", "%" + position.toLowerCase() + "%");
				}
				if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
					hql.append(" and LOWER(o.staffCardNumber) like :staffCardNumber");
					parMap.put("staffCardNumber", "%" + staffCardNumber.toLowerCase() + "%");
				}
				break;
				}
			}
		else if("Researcher".equals(personType)){
			switch(listType){
			case 1://外部专家
			hql.append(HQL4EXPERT);
			if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号
				hql.append(" where 1 = 1");
			} else {
				hql.append(" where 1 = 0");
			}
			
			// 处理查询条件
			if(unitName != null && !unitName.isEmpty())	{
				hql.append(" and LOWER(e.agencyName) like :unitName");
				parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
			}
			if(deptName != null && !deptName.isEmpty())	{
				hql.append(" and LOWER(e.divisionName) like :deptName");
				parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
			}
			if(position != null && !position.isEmpty()){
				hql.append(" and LOWER(e.position) like :position");
				parMap.put("position", "%" + position.toLowerCase() + "%");
			}
			if(specialityTitle != null && !specialityTitle.isEmpty()){
				hql.append(" and LOWER(a.specialityTitle) like :specialityTitle");
				parMap.put("specialityTitle", "%" + specialityTitle.toLowerCase() + "%");
			}
			if(disciplineType != null && !disciplineType.isEmpty()){
				hql.append(" and LOWER(a.disciplineType) like :disciplineType");
				parMap.put("disciplineType", "%" + disciplineType.toLowerCase() + "%");
			}	
			break;
			case 2://教师
			hql.append(HQL4TEACHER);
			if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
				hql.append(" where 1 = 1");
			} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号，根据教师所在单位是否归省管进行判断
				hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4) and u.subjection.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据教师所在单位是否归校管进行判断
				hql.append(" where u.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.DEPARTMENT)) {// 院系账号，根据教师是否在当前院系判断
				hql.append(" where d.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.INSTITUTE)) {// 基地账号，根据教师是否在当前基地判断
				hql.append(" where i.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else {
				hql.append(" where 1 = 0");
			}
			hql.append(" and t.type = '专职人员' ");
		
			// 处理查询条件
			if(unitName != null && !unitName.isEmpty())	{
				hql.append(" and LOWER(u.name) like :unitName");
				parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
			}
			if(deptName != null && !deptName.isEmpty())	{
				hql.append(" and LOWER(CONCAT(d.name, i.name)) like :deptName");
				parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
			}
			if(position != null && !position.isEmpty()){
				hql.append(" and LOWER(t.position) like :position");
				parMap.put("position", "%" + position.toLowerCase() + "%");
			}
			if(specialityTitle != null && !specialityTitle.isEmpty()){
				hql.append(" and LOWER(a.specialityTitle) like :specialityTitle");
				parMap.put("specialityTitle", "%" + specialityTitle.toLowerCase() + "%");
			}
			if(disciplineType != null && !disciplineType.isEmpty()){
				hql.append(" and LOWER(a.disciplineType) like :disciplineType");
				parMap.put("disciplineType", "%" + disciplineType.toLowerCase() + "%");
			}
			break;
			case 3://学生
			hql.append(HQL4STUDENT);
			if (accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)) {// 系统管理员或部级账号，判断是否存在
				hql.append(" where 1 = 1");
			} else if (accountType.equals(AccountType.PROVINCE)) {// 省级账号，根据学生所在单位是否归省管进行判断
				hql.append(" left join i.type sys where (sys.code = '02' or sys.code = '03' or u.type = 4 ) and u.subjection.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号，根据学生所在单位是否归校管进行判断
				hql.append(" where u.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.DEPARTMENT)) {// 院系账号，根据学生是否在当前院系判断
				hql.append(" where d.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else if (accountType.equals(AccountType.INSTITUTE)) {// 基地账号，根据学生是否在当前基地判断
				hql.append(" where i.id = :belongUnitId");
				parMap.put("belongUnitId", belongUnitId);
			} else {
				hql.append(" where 1 = 0");
			}
			// 处理查询条件
			if(unitName != null && !unitName.isEmpty())	{
				unitName = unitName.toLowerCase();
				hql.append(" and LOWER(u.name) like :unitName");
				parMap.put("unitName", "%" + unitName.toLowerCase() + "%");
			}
			if(deptName != null && !deptName.isEmpty())	{
				deptName = deptName.toLowerCase();
				hql.append(" and LOWER(CONCAT(d.name, i.name)) like :deptName");
				parMap.put("deptName", "%" + deptName.toLowerCase() + "%");
			}
			if(position != null && !position.isEmpty()){
				position = position.toLowerCase();
				hql.append(" and LOWER(s.type) like :position");
				parMap.put("position", "%" + position.toLowerCase() + "%");
			}
			break;
			}
		}
		// 处理公共查询条件
		if(name != null && !name.isEmpty())	{
			hql.append(" and LOWER(p.name) like :name");
			parMap.put("name", "%" + name.toLowerCase() + "%");
		}
		if(gender != null && !gender.equals("--请选择--") && !gender.isEmpty()){
			hql.append(" and LOWER(p.gender) like :gender");
			parMap.put("gender", "%" + gender.toLowerCase() + "%");
		}

		//参数处理
		Integer startAge = null;
		Integer endAge = null;
		//trim 这是一个很常见的函数，他的所用是去掉字符序列左边和右边的空格
		if(null != this.startAge && Pattern.matches("\\d+", this.startAge.trim())){
			startAge = Integer.parseInt(this.startAge.trim());
		}//判断输入是否数字，是则转换，否则为null
		if(null != this.endAge && Pattern.matches("\\d+", this.endAge.trim())){
			endAge = Integer.parseInt(this.endAge.trim());
		}//判断输入是否数字，是则转换，否则为null
		
		if(startAge != null && endAge != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Integer year = cal.get(Calendar.YEAR);//获得当前年
			Integer year1 = year - startAge;
			Integer year2 = year - endAge - 1;
			Calendar date1 = Calendar.getInstance();
			Calendar date2 = Calendar.getInstance();
			date1.set(Calendar.YEAR, year1);
			date2.set(Calendar.YEAR, year2);
			String date1String = df.format(date1.getTime());
			String date2String = df.format(date2.getTime());
			hql.append(" and to_char(p.birthday, 'yyyy-MM-dd') <= :date1 and to_char(p.birthday, 'yyyy-MM-dd') >= :date2");
			parMap.put("date1", date1String);
			parMap.put("date2", date2String);
		}
		hql.append(" order by p.name ");//默认按照姓名排序

		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 人员详情查看
	 * @return
	 */
	public String view(){
		Map dataMap = new HashMap();//返回客户端的数据
		if("Officer".equals(personType)){
			officer = (Officer) dao.query(Officer.class, entityId);
			personId = (null == officer.getPerson()) ? "" : officer.getPerson().getId();
			person = (Person) dao.query(Person.class, personId);
			agencyId = (null == officer.getAgency()) ? "" : officer.getAgency().getId();
			agency = (Agency) dao.query(Agency.class, agencyId);
			dataMap.put("officer", officer);
			dataMap.put("person", person);
			dataMap.put("agency", agency);
			switch (listType) {
			case 1://部级管理人员
			case 2://省级管理人员
			case 3://高校管理人员
				//dataMap = mobilePersonService.getPersonInfo(entityId);
			/*	dataMap.put("person", person);
				dataMap.put("agency", agency);*/
				dataMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());// 行政职务
				dataMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				dataMap.put("agencyName", (null == agency.getName()) ? "" : agency.getName());  
				dataMap.put("agencySname", (null == agency.getSname()) ? "" : agency.getSname());
				dataMap.put("staffCardNumber", (null == officer.getStaffCardNumber()) ? "" :officer.getStaffCardNumber());
				
		
				break;
			case 4://院系管理人员
				String departmentId = (null == officer.getDepartment()) ? "" : officer.getDepartment().getId();
				department = (Department) dao.query(Department.class , departmentId);
				//dataMap = mobilePersonService.getPersonInfo(entityId);
				dataMap.put("department",department);
				dataMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());// 行政职务
				dataMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				dataMap.put("universityName", (null == agency.getName()) ? "" : agency.getName()); //学校名称 
				dataMap.put("departmentName", (null == department.getName()) ? "" : department.getName());  //院系名称
				//dataMap.put("membership", (null == department.getDirector()||null == department.getDirector().getMembership()) ? "" : department.getName());  //政治面貌
				break;
			case 5://基地管理人员
				String instituteId = (null == officer.getInstitute()) ? "" : officer.getInstitute().getId();
				institute = (Institute) dao.query(Institute.class, instituteId);
				//dataMap = mobilePersonService.getPersonInfo(entityId);
				
				dataMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());// 行政职务
				dataMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				dataMap.put("subjectionName", (null == agency.getName()) ? "" : agency.getName()); //上级管理部门名称 
				dataMap.put("instituteName", (null == institute.getName()) ? "" : institute.getName());  //基地名称
				break;
			}
			
		}
		else if("Researcher".equals(personType)){
			switch(listType){
			case 1://外部专家
				expert = (Expert) dao.query(Expert.class, entityId);
				personId = (null == expert.getPerson()) ? "" : expert.getPerson().getId();
				person = (Person) dao.query(Person.class, personId);
				academicId = (null == person.getAcademic()) ? "" : person.getAcademic().getId();
				academic = (Academic) dao.query(Academic.class , academicId);
				//dataMap = mobilePersonService.getExpertInfo(entityId);
				/*List result = this.projectExtService.getProjectListByEntityId(1, person.getId());
				dataMap.put("person_project", result);*/
				dataMap.put("person", person);
				dataMap.put("academic", academic);
				dataMap.put("position", (null == expert.getPosition()) ? "" : expert.getPosition());// 职务
				dataMap.put("type", (null == expert.getType()) ? "" : expert.getType());// 人员类型
				dataMap.put("agencyName", (null == expert.getAgencyName()) ? "" : expert.getAgencyName());// 所在单位名称
				dataMap.put("divisionName", (null == expert.getDivisionName()) ? "" : expert.getDivisionName());// 所在部门名称
				dataMap.put("specialityTitle", (null != academic && null != academic.getSpecialityTitle()) ? academic.getSpecialityTitle() : ""); // 专业职称 
				break;
			case 2://教师
				teacher = (Teacher) dao.query(Teacher.class, entityId);
				personId = (null == teacher.getPerson()) ? "" : teacher.getPerson().getId();
				person = (Person) dao.query(Person.class, personId);
				academicId = (null == person.getAcademic()) ? "" : person.getAcademic().getId();
				academic = (Academic) dao.query(Academic.class , academicId);
				agencyId = (null == teacher.getUniversity()) ? "" : teacher.getUniversity().getId();
				agency = (Agency) dao.query(Agency.class , agencyId);
				//dataMap = mobilePersonService.getTeacherInfo(entityId);
				/*paraMap = new HashMap();
				paraMap.put("teacherId", entityId);
				result = dao.query("select person, academic from Teacher teacher, Person person left join person.academic academic where teacher.id = :teacherId and teacher.person.id = person.id", paraMap);
				person = (Person) ((Object[]) result.get(0))[0];
				academic = (Academic) ((Object[]) result.get(0))[1];
				result  = this.productService.getProductListByEntityId(1, person.getId());
				dataMap.put("person_product", result);*/
				//dataMap.put("teachers", teachersForView);
				dataMap.put("person", person);
				dataMap.put("academic", academic);
				dataMap.put("position", (null == teacher.getPosition()) ? "" : teacher.getPosition());// 职务
				dataMap.put("type", (null == teacher.getType()) ? "" : teacher.getType());// 人员类型
				dataMap.put("universityName", (null == agency.getName()) ? "" : agency.getName());//所在高校
				dataMap.put("divisionName", (null == teacher.getDivisionName()) ? "" : teacher.getDivisionName());//所在院系或基地
				dataMap.put("specialityTitle", (null != academic && null != academic.getSpecialityTitle()) ? academic.getSpecialityTitle() : ""); // 专业职称
				break;
			case 3://学生
				student = (Student) dao.query(Student.class, entityId);
				personId = (null == student.getPerson()) ? "" : student.getPerson().getId();
				person = (Person) dao.query(Person.class, personId);
				academicId = (null == person.getAcademic()) ? "" : person.getAcademic().getId();
				academic = (Academic) dao.query(Academic.class , academicId);
				//tutor = (Tutor)dao.query(Tutor.class,)
				agencyId = (null == student.getUniversity()) ? "" : student.getUniversity().getId();
				agency = (Agency) dao.query(Agency.class , agencyId);
				//dataMap = mobilePersonService.getStudentInfo(entityId);
				String thesisTitle = (null == student.getThesisTitle()) ? "" : student.getThesisTitle();
				String thesisFee = (0 == student.getThesisFee()) ? "" : student.getThesisFee()+"";
				/*paraMap = new HashMap();
				paraMap.put("studentId", entityId);
				List<Object[]> result = dao.query("select student, person, academic, tu from Student student left join student.tutor tu left join student.person person left join person.academic academic where student.id = :studentId", paraMap);				
				student = (Student) result.get(0)[0];
				person = (Person) result.get(0)[1];
				academic = (Academic)result.get(0)[2];
				Person tutor = (Person)result.get(0)[3];//导师信息				
				 */
				dataMap.put("person", person);
				
				dataMap.put("academic", academic);
				dataMap.put("major", (null == academic || null == academic.getMajor()) ? "" : academic.getMajor());// 职务
				dataMap.put("type", (null == student.getType()) ? "" : student.getType());// 类型
				dataMap.put("universityName", (null == agency.getName()) ? "" : agency.getName());//所在高校
				if(student.getDepartment() != null){
					String departmentId = student.getDepartment().getId();
					department = (Department) dao.query(Department.class, departmentId);
					dataMap.put("divisionName", department.getName());//所在院系
				}
				else if(student.getInstitute() != null){
					String instituteId = student.getInstitute().getId();
					institute = (Institute) dao.query(Institute.class, instituteId);
					dataMap.put("divisionName", institute.getName());//所在基地
				}
				break;
			}
		}else if("All".equals(personType)){
			person = (Person)dao.query(Person.class,entityId);
 			//dataMap = mobilePersonService.getPersonInfo(entityId);
		}	
		//公共部分（person）
		if(null != person){
			dataMap.put("personName", (null == person.getName()) ? "" : person.getName());
			dataMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
			dataMap.put("birthday", (null == person.getBirthday()) ? "" : new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthday()));
			dataMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
			dataMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
			dataMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
			dataMap.put("englishName", (null == person.getEnglishName()) ? "" : person.getEnglishName());
			dataMap.put("usedName", (null == person.getUsedName()) ? "" : person.getUsedName());
			dataMap.put("membership", (null == person.getMembership()) ? "" : person.getMembership());
			dataMap.put("idcardType", (null == person.getIdcardType()) ? "" : person.getIdcardType());
			dataMap.put("idcardNumber", (null == person.getIdcardNumber()) ? "" : person.getIdcardNumber());
			dataMap.put("countryRegion", (null == person.getCountryRegion()) ? "" : person.getCountryRegion());
			dataMap.put("ethnic", (null == person.getEthnic()) ? "" : person.getEthnic());
			dataMap.put("birthplace", (null == person.getBirthplace()) ? "" : person.getBirthplace());
			dataMap.put("qq", (null == person.getQq()) ? "" : person.getQq());
			
			//增加人员信息
			Map paraMap = new HashMap();
			paraMap.put("personId", person.getId());
			List result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
			dataMap.put("person_education", result);
			result = dao.query("select we from Work we where we.person.id = :personId", paraMap);
			dataMap.put("person_work", result);
			result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
			dataMap.put("person_abroad", result);
			result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getHomeAddressIds());
			dataMap.put("person_homeAddress", result);
			result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getOfficeAddressIds());
			dataMap.put("person_officeAddress", result);
			result  = this.productService.getProductListByEntityId(1, person.getId());
			dataMap.put("person_product", result);
			result = this.projectExtService.getProjectListByEntityId(1, person.getId());
			dataMap.put("person_project", result);
			String bankIds = person.getBankIds();//银行账户组ID
			result = dao.query("select ba from BankAccount ba where ba.ids = ?", bankIds);
			dataMap.put("person_bankAccount", result);
			dataMap.put("academic", academic);
			String s = person.getOfficeAddressIds();
			if(person.getOfficeAddressIds()!=null){
				List list = personService.getOfficeAddress(person);
				dataMap.put("officeAddress", list.get(0));
				dataMap.put("officePostcode", list.get(1));
			}else {
				dataMap.put("officeAddress", "");
				dataMap.put("officePostcode", "");
			}
		}		
		jsonMap.put("laData", dataMap);
		return SUCCESS;
	}
	
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStartAge() {
		return startAge;
	}
	public void setStartAge(String startAge) {
		this.startAge = startAge;
	}
	public String getEndAge() {
		return endAge;
	}
	public void setEndAge(String endAge) {
		this.endAge = endAge;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSpecialityTitle() {
		return specialityTitle;
	}
	public void setSpecialityTitle(String specialityTitle) {
		this.specialityTitle = specialityTitle;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	public void setStaffCardNumber(String staffCardNumber) {
		this.staffCardNumber = staffCardNumber;
	}
	public String getStaffCardNumber() {
		return staffCardNumber;
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}
}
