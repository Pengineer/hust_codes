package csdc.action.mobile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IAccountService;
import csdc.service.IMobileService;
import csdc.service.IProductService;
import csdc.service.IProjectService;
import csdc.service.ext.IUnitExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.ImageStringConvertTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;


/**
 * 个人信息模块
 * @author suwb
 *
 */
public class MobileSelfspaceAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileSelfspacePage";
	
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUnitExtService unitExtService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IMobileService mobileService;
	
	private static final String AVATAR_PATH = "upload/person/photo";
	private String avatarString;

	private String name;// 姓名
	private String englishName;// 英文名
	private String usedName;// 曾用名
	private String idcardType;// 证件类型
	private String idcardNumber;// 证件号
	private String gender;// 性别
	private String countryRegion;// 国家或地区
	private String ethnic;// 民族
	private String birthplace;// 籍贯（省、市、自治区）
	private Date birthday;// 生日
	private String membership;// 政治面貌
	private String homeAddress;// 住宅地址
	private String homePhone;// 住宅电话
	private String homePostcode;// 住宅邮编
	private String homeFax;// 住宅传真
	private String officePhone;// 办公电话
	private String officeFax;// 办公传真
	private String email;// 邮箱
	private String mobilePhone;// 电话
	private String qq;
	private String homepage;// 主页
	private String addressId;//地址的id
	
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	private static final String[] HQL_Project = {
		"select app.id, app.name, uni.id, app.agencyName, '一般项目', so.name, app.year, " +
		"mem.memberSn, '0', app.applicantId, app.applicantName from GeneralApplication app, GeneralMember mem "+
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1 " +
		"order by app.name asc, app.id asc",//一般项目未立项查询
		
		"select app.id, gra.name, uni.id, uni.name, '一般项目', so.name, app.year, " +
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName from GeneralGranted gra, GeneralMember mem left outer join "+
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " +
		"order by gra.status asc, gra.id asc",//一般项目已立项查询
		
		"select app.id, app.name, uni.id, app.agencyName, '基地项目', so.name, app.year, " + 
		"mem.memberSn, '0', app.applicantId, app.applicantName from InstpApplication app, InstpMember mem " +
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//基地未立项项目查询
		
		"select app.id, gra.name, uni.id, uni.name, '基地项目', so.name, app.year, " + 
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName from InstpGranted gra, InstpMember mem left outer join " +
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " +
		"order by gra.status asc, gra.id asc",//基地已立项项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '后期资助项目', so.name, app.year, " +
	    "mem.memberSn, '0', app.applicantId, app.applicantName from PostApplication app, PostMember mem " +
	    "left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//后期资助未立项项目查询

		"select app.id, gra.name, uni.id, uni.name, '后期资助项目', so.name, app.year, " +
	    "mem.memberSn, gra.status, gra.applicantId, gra.applicantName from PostGranted gra, PostMember mem left outer join " +
	    "gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join gra.subtype so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc",//后期资助已立项项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '重大攻关项目', so.name, app.year, " +
	    "mem.memberSn, '0', app.applicantId, app.applicantName from KeyApplication app, KeyMember mem " +
	    "left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.researchType so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//重大攻关未中标项目查询

		"select app.id, gra.name, uni.id, uni.name, '重大攻关项目', so.name, app.year, " +
	    "mem.memberSn, gra.status, gra.applicantId, gra.applicantName from KeyGranted gra, KeyMember mem left outer join " +
	    "gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join app.researchType so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc",//重大攻关已中标项目查询
		
		"select app.id, app.name, uni.id, app.agencyName, '委托应急课题', so.name, app.year, " +
		"mem.memberSn, '0', app.applicantId, app.applicantName from EntrustApplication app, EntrustMember mem " +
		"left outer join app.university uni left outer join app.department dep left outer join app.institute ins " +
		"left outer join app.subtype so where " +
		"mem.application.id=app.id and mem.member.id=:belongId and app.finalAuditStatus = 3 and app.finalAuditResult = 1" +
		"order by app.name asc, app.id asc",//委托应急课题未立项项目查询
		
		"select app.id, gra.name, uni.id, uni.name, '委托应急课题', so.name, app.year, " +
		"mem.memberSn, gra.status, gra.applicantId, gra.applicantName from EntrustGranted gra, EntrustMember mem left outer join " +
		"gra.application app left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins " +
		"left outer join app.subtype so where mem.application.id=app.id and mem.member.id=:belongId " + 
		"order by gra.status asc, gra.id asc"//委托应急课题未立项项目查询
	};
	private static final String HQL_Award =" select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, gr.name, aa.session, aa.applicant.id, un.id, aa.year, awt.name, '1' "+
			"from AwardGranted aw, AwardApplication aa left outer join aa.university un left join aa.subType awt, SystemOption gr, Product pr where aw.application.id=aa.id and gr.id=aw.grade.id and pr.id = aa.product.id";
	
	/**
	 * 查看我的资料
	 * 设置账号所属
	 * 机构主账号
	 * 机构子账号
	 * 外部专家账号
	 * 教师、学生账号
	 * 系统管理员账号
	 * 其它账号
	 * @return
	 * @throws Exception 
	 */
	public String viewMyself() throws Exception{
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		entityId = baseService.getBelongIdByLoginer(loginer);
		Account account = loginer.getAccount();
		AccountType type = account.getType();// 账号级别
		int isPrincipal = account.getIsPrincipal();// 账号类别
		if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {// 部级、省级、校级账号，需要查找agency信息
			if (isPrincipal == 1) {// 部级、省级、校级主账号查找机构信息
				Agency agency = (Agency) dao.query(Agency.class, entityId);
				//[基本信息 7]：单位名称，单位代码，单位类型，单位负责人，上级管理部门，所在省，所在市
				String uName = (agency.getName() == null)? "":agency.getName();
				jsonMap.put("uName", uName);//单位名称
				String uCode = (agency.getCode() == null)? "":agency.getCode();
				jsonMap.put("uCode", uCode);//单位代码
				//机构类型，将数字转化为名称字符串
				String[] str = {"部级","省级","部属高校","地方高校"};
				if(agency.getType()>0 && agency.getType()<5){//机构类型
					jsonMap.put("uType", str[agency.getType()-1]);
				}else{
					jsonMap.put("uType", "");
				}
				String directorName = (agency.getDirector() == null)? "":agency.getDirector().getName();
				jsonMap.put("directorName", directorName);////单位负责人
				if(agency.getSubjection() != null){
					String subjectionId = agency.getSubjection().getId();
					Agency subjection = dao.query(Agency.class, subjectionId);
					jsonMap.put("subjectionName", (subjection.getName() == null)? "":subjection.getName());//上级管理部门名称					
				}else jsonMap.put("subjectionName", "");
				if(agency.getProvince() != null){
					SystemOption province = dao.query(SystemOption.class, agency.getProvince().getId());
					jsonMap.put("provinceName", (province.getName() == null)? "":province.getName());//所在省
				}else jsonMap.put("provinceName","");
				if(agency.getCity() != null){
					SystemOption city = dao.query(SystemOption.class, agency.getCity().getId());
					jsonMap.put("cityName", (city.getName() == null)? "":city.getName());//所在市
				}else jsonMap.put("cityName","");
				//[社科管理部门 9]:部门名称，部门负责人，部门联系人，部门电话，部门传真，部门邮编 ，部门地址，部门邮箱，部门主页；
				String sName = (agency.getSname() == null)? "":agency.getSname();
				jsonMap.put("sName", sName);//社科管理部门名称
				if(agency.getSdirector() != null){
					Person person = dao.query(Person.class, agency.getSdirector().getId());
					jsonMap.put("sDirectorName", person.getName());//社科管理部门联系人	
				}else jsonMap.put("sDirectorName", "");
				if(agency.getSlinkman() != null){
					Person person = dao.query(Person.class, agency.getSlinkman().getId());
					jsonMap.put("sLinkmanName", person.getName());//社科管理部门联系人					
				}else jsonMap.put("sLinkmanName", "");
				String sPhone = (agency.getSphone() == null)? "":agency.getSphone();
				jsonMap.put("sPhone", sPhone);//社科管理部门电话
				String sFax = (agency.getSfax() == null)? "":agency.getSfax();
				jsonMap.put("sFax", sFax);//社科管理部门传真
				jsonMap.put("address", mobileService.getSAddress(agency));
				String sEmail = (agency.getSemail() == null)? "":agency.getSemail();
				jsonMap.put("sEmail", sEmail);//社科管理部门邮箱
				String sHomepage = (agency.getShomepage() == null)? "":agency.getShomepage();
				jsonMap.put("sHomepage", sHomepage);//社科管理部门主页
			} else {// 部级、省级、校级子账号查找机构和人员信息
				Officer officer = accountService.getOfficerByOfficerId(account.getOfficer().getId());
				String personId = officer.getPerson().getId();
				Person person = dao.query(Person.class, personId);
				jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
				jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
				jsonMap.put("userName", accountService.securityUsername());
				jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
				jsonMap.put("address", mobileService.getHomeAddress(person));
				jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
				jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
				jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
				jsonMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				jsonMap.put("agencyName", (null == officer.getAgency().getName()) ? "" : officer.getAgency().getName());
				jsonMap.put("agencySname", (null == officer.getAgency().getSname()) ? "" : officer.getAgency().getSname());
				jsonMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系账号，需要查找department信息
			if (isPrincipal == 1) {// 院系主账号查找院系信息
				Department department = (Department) dao.query(Department.class, entityId);
				//[基本信息 4]:院系名称，院系代码，院系负责人，上级管理部门
				String uName = (department.getName() == null) ? "" : department.getName();
				jsonMap.put("uName", uName);//院系名称
				String uCode = (department.getCode() == null) ? "" : department.getCode();
				jsonMap.put("uCode", uCode);//院系代码
				if(department.getDirector() != null){
					Person director = dao.query(Person.class, department.getDirector().getId());
					jsonMap.put("directorName", (director.getName() == null) ? "" : department.getName());
				}else jsonMap.put("directorName", "");//院系负责人
				if(department.getUniversity() != null){
					Agency subjection = dao.query(Agency.class, department.getUniversity().getId());
					jsonMap.put("subjectionName", (subjection.getName() == null)? "" : subjection.getName());
				}else jsonMap.put("subjectionName", "");//所属高校
				//[联系信息 7]：院系联系人，院系电话，院系传真，邮编，通信地址，院系邮箱，院系主页
				if(department.getLinkman() != null){
					Person linkman = dao.query(Person.class, department.getLinkman().getId());
					jsonMap.put("linkmanName", (linkman.getName() == null) ? "" : linkman.getName());
				}else jsonMap.put("linkmanName", "");//院系联系人
				String uPhone = (department.getPhone() == null) ? "" : department.getPhone();
				jsonMap.put("uPhone", uPhone);//院系电话
				String uFax = (department.getFax() == null) ? "" : department.getFax();
				jsonMap.put("uFax", uFax);//院系传真
				String uEmail = (department.getEmail() == null) ? "" : department.getEmail();
				jsonMap.put("uEmail", uEmail);//院系邮箱
				String uHomepage = (department.getHomepage() == null) ? "" : department.getHomepage();
				jsonMap.put("uHomepage", uHomepage);//院系主页
				jsonMap.put("address", mobileService.getAddress(department));
			} else {// 院系子账号查找院系和人员信息
				Officer officer = accountService.getOfficerByOfficerId(account.getOfficer().getId());
				String personId = officer.getPerson().getId();
				Person person = dao.query(Person.class, personId);
				jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
				jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
				jsonMap.put("userName", accountService.securityUsername());
				jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
				jsonMap.put("address", mobileService.getHomeAddress(person));
				jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
				jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
				jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
				jsonMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				jsonMap.put("agencyName", (null == officer.getAgency().getName()) ? "" : officer.getAgency().getName());
				jsonMap.put("agencySname", (null == officer.getAgency().getSname()) ? "" : officer.getAgency().getSname());
				jsonMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 基地账号，需要查找institute信息
			if (isPrincipal == 1) {// 基地主账号
				Institute institute = (Institute) dao.query(Institute.class, entityId);
				//[基本信息 7]:研究基地名称，研究基地代码，研究基地类型，研究基地负责人，上级管理部门，研究活动类型,所属学科门类
				String uName = (institute.getName() == null) ? "" : institute.getName();
				jsonMap.put("uName", uName);//研究基地名称
				String uCode = (institute.getCode() == null) ? "" : institute.getCode();
				jsonMap.put("uCode", uCode);//研究基地代码
				if(institute.getType()!= null){
					SystemOption instType = dao.query(SystemOption.class, institute.getType().getId());
					jsonMap.put("uType", (instType.getName()==null) ? "" : instType.getName());
				}else jsonMap.put("uType", "");//研究基地类型
				if(institute.getResearchActivityType() !=null ){
					String researchTypeName = unitExtService.getNamesByIds(institute.getResearchActivityType().getId());
					jsonMap.put("researchType", researchTypeName);
				}else{
					jsonMap.put("researchType", "");// 研究活动类型
				}
				String discipline = (institute.getDisciplineType() == null) ? "" : institute.getDisciplineType();
				jsonMap.put("discipline", discipline);//所属学科门类
				if(institute.getSubjection() != null){
					Agency subjection = dao.query(Agency.class, institute.getSubjection().getId());
					jsonMap.put("subjectionName", (subjection.getName() == null)? "" : subjection.getName());
				}else jsonMap.put("subjectionName", "");//上级管理部门
				if(institute.getDirector() != null){
					Person director = dao.query(Person.class, institute.getDirector().getId());
					jsonMap.put("directorName", (director.getName() == null) ? "" : director.getName());
				}else jsonMap.put("directorName", "");//研究基地负责人
				//[联系信息 7]：联系人，电话，传真，邮箱，主页，邮政编码 ，通信地址
				if(institute.getLinkman() != null){
					Person linkman = dao.query(Person.class, institute.getLinkman().getId());
					jsonMap.put("linkmanName", (linkman.getName() == null)? "" : linkman.getName());
				}else jsonMap.put("linkmanName", "");//联系人
				String uPhone = (institute.getPhone() == null) ? "" : institute.getPhone();
				jsonMap.put("uPhone", uPhone);//电话
				String uFax = (institute.getFax() == null) ? "" : institute.getFax();
				jsonMap.put("uFax", uFax);//传真
				String uEmail = (institute.getEmail() == null) ? "" : institute.getEmail();
				jsonMap.put("uEmail", uEmail);//邮箱
				String uHomepage = (institute.getHomepage() == null) ? "" : institute.getHomepage();
				jsonMap.put("uHomepage", uHomepage);//主页
				jsonMap.put("address", mobileService.getAddress(institute));
			} else {// 基地子账号
				Officer officer = accountService.getOfficerByOfficerId(account.getOfficer().getId());
				String personId = officer.getPerson().getId();
				Person person = dao.query(Person.class, personId);
				jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
				jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
				jsonMap.put("userName", accountService.securityUsername());
				jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
				jsonMap.put("address", mobileService.getHomeAddress(person));
				jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
				jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
				jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
				jsonMap.put("type", (null == officer.getType()) ? "" : officer.getType());// 人员类型
				jsonMap.put("agencyName", (null == officer.getAgency().getName()) ? "" : officer.getAgency().getName());
				jsonMap.put("agencySname", (null == officer.getAgency().getSname()) ? "" : officer.getAgency().getSname());
				jsonMap.put("position", (null == officer.getPosition()) ? "" : officer.getPosition());
			}
		} else if (type.equals(AccountType.EXPERT)) {// 外部专家账号，需要查找人员信息
			Expert expert = accountService.getExpertByPersonId(baseService.getBelongIdByAccount(account));
			String personId = expert.getPerson().getId();
			Person person = dao.query(Person.class, personId);
			jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
			jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
			jsonMap.put("userName", accountService.securityUsername());
			jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
			jsonMap.put("address", mobileService.getHomeAddress(person));
			jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
			jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
			jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
			jsonMap.put("position", (null == expert.getPosition()) ? "" : expert.getPosition());// 职务
			jsonMap.put("type", (null == expert.getType()) ? "" : expert.getType());// 人员类型
			jsonMap.put("agencyName", (null == expert.getAgencyName()) ? "" : expert.getAgencyName());// 所在单位名称
			jsonMap.put("divisionName", (null == expert.getDivisionName()) ? "" : expert.getDivisionName());// 所在部门名称
		} else if (type.equals(AccountType.TEACHER)) {// 教师账号，需要查找学校和人员信息
			Teacher teacher = accountService.getTeacherByPersonId(baseService.getBelongIdByAccount(account));
			String personId = teacher.getPerson().getId();
			Person person = dao.query(Person.class, personId);
			String agencyId = (null == teacher.getUniversity()) ? "" : teacher.getUniversity().getId();
			Agency agency = (Agency) dao.query(Agency.class , agencyId);
			jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
			jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
			jsonMap.put("userName", accountService.securityUsername());
			jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
			jsonMap.put("address", mobileService.getHomeAddress(person));
			jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
			jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
			jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
			jsonMap.put("position", (null == teacher.getPosition()) ? "" : teacher.getPosition());// 职务
			jsonMap.put("type", (null == teacher.getType()) ? "" : teacher.getType());// 人员类型
			jsonMap.put("universityName", (null == agency.getName()) ? "" : agency.getName());//所在高校
			jsonMap.put("divisionName", (null == teacher.getDivisionName()) ? "" : teacher.getDivisionName());//所在院系或基地
		} else if (type.equals(AccountType.STUDENT)) {// 学生账号，需要查找学校和人员信息
			Student student = accountService.getStudentByPersonId(baseService.getBelongIdByAccount(account));
			String personId = (null == student.getPerson()) ? "" : student.getPerson().getId();
			Person person = (Person) dao.query(Person.class, personId);
			String academicId = (null == person.getAcademic()) ? "" : person.getAcademic().getId();
			Academic academic = (Academic) dao.query(Academic.class , academicId);
			String agencyId = (null == student.getUniversity()) ? "" : student.getUniversity().getId();
			Agency agency = (Agency) dao.query(Agency.class , agencyId);
			jsonMap.put("name", (null == person.getName()) ? "" : person.getName());
			jsonMap.put("gender", (null == person.getGender()) ? "" : person.getGender());
			jsonMap.put("userName", accountService.securityUsername());
			jsonMap.put("homePhone", (null == person.getHomePhone()) ? "" : person.getHomePhone());
			jsonMap.put("address", mobileService.getHomeAddress(person));
			jsonMap.put("officePhone", (null == person.getOfficePhone()) ? "" : person.getOfficePhone());
			jsonMap.put("mobilePhone", (null == person.getMobilePhone()) ? "" : person.getMobilePhone());
			jsonMap.put("email", (null == person.getEmail()) ? "" : person.getEmail());
			jsonMap.put("major", (null == academic || null == academic.getMajor()) ? "" : academic.getMajor());// 职务
			jsonMap.put("type", (null == student.getType()) ? "" : student.getType());// 类型
			jsonMap.put("universityName", (null == agency.getName()) ? "" : agency.getName());//所在高校
			if(student.getDepartment() != null){
				String departmentId = student.getDepartment().getId();
				Department department = (Department) dao.query(Department.class, departmentId);
				jsonMap.put("divisionName", department.getName());//所在院系
			}
			else if(student.getInstitute() != null){
				String instituteId = student.getInstitute().getId();
				Institute institute = (Institute) dao.query(Institute.class, instituteId);
				jsonMap.put("divisionName", institute.getName());//所在基地
			}
		} else if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			jsonMap.put("userName", "admin");
			jsonMap.put("homePhone", "027-87558397");
			jsonMap.put("homeAddress", "湖北省武汉市洪山区珞瑜路1037号华中科技大学南一楼702室 ");
			jsonMap.put("homePostcode", "430074");
		} else {// 其它账号
			jsonMap.put("null", "");
		}
		return SUCCESS;			
	}
	
	//编辑我的资料
	public String modifyMyself(){
		Account account = loginer.getAccount();
		Person person = dao.query(Person.class,account.getPerson().getId());
		if (homePhone != null) {
			person.setHomePhone(homePhone);
		}		
		if (officePhone != null) {
			person.setOfficePhone(officePhone);
		}
		if (email != null) {
			person.setEmail(email);
		}
		if (mobilePhone != null) {
			person.setMobilePhone(mobilePhone);
		}
		if (addressId !=null){
			Address address = dao.query(Address.class,addressId);
			if(homeAddress !=null&&!homeAddress.equals(address.getAddress())){
				address.setAddress(homeAddress);
				address.setUpdateDate(new Date());
			}
			if(homePostcode !=null&&!homePostcode.equals(address.getPostCode())){
				address.setPostCode(homePostcode);
				address.setUpdateDate(new Date());
			}
			dao.modify(address);
		}
		dao.modify(person);
		return SUCCESS;
	}
	 
	/**
	 * 编辑我的资料
	 * @throws IOException 
	 */
	public String uploadAvatar(){
		Account account = loginer.getAccount();
		Person person = dao.query(Person.class,account.getPerson().getId());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = df.format(new Date());
		String avatarName = "person_photo_"+person.getId()+"_"+dateString+".jpg";
		String suffix = AVATAR_PATH + "/" +avatarName;
		String fullPath = ApplicationContainer.sc.getRealPath(suffix);
		
		File dir = new File(ApplicationContainer.sc.getRealPath(AVATAR_PATH));
		dir.mkdirs();
		
		if (!ImageStringConvertTool.GenerateImage(avatarString, fullPath)) {
			jsonMap = null;
		}
		person.setPhotoFile(suffix);
		dao.modify(person);
		jsonMap.put("photoFile", person.getPhotoFile());
		return SUCCESS;
	}
	public String getAvatar(){
		Account account = loginer.getAccount();
		Person person = dao.query(Person.class,account.getPerson().getId());
		jsonMap.put("photoFile", person.getPhotoFile());
		return SUCCESS;
	}
	/**
	 * 代办事宜		
	 * @return
	 */
	public String viewMyToDo(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		//研究人员待处理事宜
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			Map teacherProjectBean = new HashMap();
			Map gmap = projectService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "general");
			Map kmap = projectService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "key");
			Map bmap = projectService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "instp");
			Map pmap = projectService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "post");
			Map emap = projectService.getTeacherBusinessByAccount(loginer.getCurrentAccountId(), "entrust");
			if(gmap != null){
				teacherProjectBean.putAll(gmap);
			}else jsonMap.put("一般项目",null);
			if(kmap != null){
				teacherProjectBean.putAll(kmap);
			}else jsonMap.put("重大攻关",null);
			if(bmap != null){
				teacherProjectBean.putAll(bmap);
			}else jsonMap.put("基地项目",null);
			if(pmap != null){
				teacherProjectBean.putAll(pmap);
			}else jsonMap.put("后期资助",null);
			if(emap != null){
				teacherProjectBean.putAll(emap);
			}else jsonMap.put("委托应急",null);
			Iterator it = teacherProjectBean.entrySet().iterator(); 
			int i = 0;
			while (it.hasNext() && i < 3) {
				Map.Entry entry = (Map.Entry) it.next();
				jsonMap.put(entry.getKey(), entry.getValue());
				i++;
			}
		}
		//专家待评审事宜暂时不需要此功能
//		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.TEACHER)) {
//			Map teacherReviewBean = new HashMap();
//			Map gmap = projectService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "general");
//			Map kmap = projectService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "key");
//			Map bmap = projectService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "instp");
//			Map pmap = projectService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(),"post");
//			Map emap = projectService.getTeacherReviewProjectByAccount(loginer.getCurrentAccountId(), "entrust");
//			if(gmap != null){
//				teacherReviewBean.putAll(gmap);
//			}else jsonMap.put("一般项目",null);
//			if(kmap != null){
//				teacherReviewBean.putAll(kmap);
//			}else jsonMap.put("重大攻关",null);
//			if(bmap != null){
//				teacherReviewBean.putAll(bmap);
//			}else jsonMap.put("基地项目",null);
//			if(pmap != null){
//				teacherReviewBean.putAll(pmap);
//			}else jsonMap.put("后期资助",null);
//			if(emap != null){
//				teacherReviewBean.putAll(emap);
//			}else jsonMap.put("委托应急",null);
//			Iterator it = teacherReviewBean.entrySet().iterator(); 
//			int i = 0;
//			while (it.hasNext() && i < 3) {
//				Map.Entry entry = (Map.Entry) it.next();
//				jsonMap.put(entry.getKey(), entry.getValue());
//				i++;
//			}
//		}
		//管理人员待审核事宜( 非8-10，等级高于外部专家)
		if (loginer.getCurrentType().compareWith(AccountType.EXPERT) > 0) {
			Map gmap = projectService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "general");
			Map kmap = projectService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "key");
			Map bmap = projectService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "instp");
			Map pmap = projectService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "post");
			Map emap = projectService.getManagerBusinessByAccount(loginer.getCurrentAccountId(), "entrust");
			if(gmap != null){
				jsonMap.putAll(gmap);
			}else jsonMap.put("一般项目",null);
			if(kmap != null){
				jsonMap.putAll(kmap);
			}else jsonMap.put("重大攻关",null);
			if(bmap != null){
				jsonMap.putAll(bmap);
			}else jsonMap.put("基地项目",null);
			if(pmap != null){
				jsonMap.putAll(pmap);
			}else jsonMap.put("后期资助",null);
			if(emap != null){
				jsonMap.putAll(emap);
			}else jsonMap.put("委托应急",null);
		}
		return SUCCESS;
	}

	/**
	 * 查看我的项目(研究人员)
	 * @return
	 */
	public String viewMyProject(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			Map map = new HashMap();
			map.put("belongId", baseService.getBelongIdByLoginer(loginer));
			this.pageList = new ArrayList();
			for(int i = 0; i < HQL_Project.length; i++){
				StringBuffer hql = new StringBuffer(HQL_Project[i]);
				List pageListTmp = dao.query(hql.toString(), map);
				pageList.addAll(pageListTmp);
			}
		}
		jsonMap.put("laData", pageList);
		return SUCCESS;
	}
	
	/**
	 * 查看我的成果(研究人员)
	 * 研究人员、高校、院系、研究基地的成果列表
	 * @return
	 */
	public String viewMyProduct(){
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		entityId = baseService.getBelongIdByLoginer(loginer);
		Account account = loginer.getAccount();
		AccountType type = account.getType();// 账号级别
		int isPrincipal = account.getIsPrincipal();// 账号类别
		if(type.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			pageList = productService.getProductListByEntityId(1, entityId);
		}else if(type.equals(AccountType.INSTITUTE) && isPrincipal==1){//研究基地
			pageList = productService.getProductListByEntityId(4, entityId);
		}else if(type.equals(AccountType.DEPARTMENT) && isPrincipal==1){//院系
			pageList = productService.getProductListByEntityId(3, entityId);
		}else if(type.within(AccountType.MINISTRY_UNIVERSITY, AccountType.LOCAL_UNIVERSITY) && isPrincipal==1){//高校
			pageList = productService.getProductListByEntityId(2, entityId);
		}
		for (Object[] obj : pageList) {
			obj[2] = this.findTypeName((String)obj[2]);
		}
		jsonMap.put("laData", pageList);
		return SUCCESS;
	}
	
	/**
	 * 查看我的奖励(研究人员)
	 * @return
	 */
	public String viewMyAward(){
		Map session = ActionContext.getContext().getSession();
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		if (loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append(HQL_Award).append(" and aa.applicant.id =:personId ");
			map.put("personId", baseService.getBelongIdByLoginer(loginer));
			this.pageList = dao.query(hql.toString(), map);		
			for (Object[] obj : pageList) {
				obj[5] = findTypeName((String)obj[5]);
			}
		}
		jsonMap.put("laData", pageList);
		return SUCCESS;
	}
	
	/**
	 * 通过成果类型获得中文名称
	 * @param productType
	 * @return
	 */
	public String findTypeName(String productType){
		if(productType.equals("book")){
			return "著作";
		}else if(productType.equals("paper")){
			return "论文";
		}else if(productType.equals("consultation")){
			return "研究咨询报告";
		}else if(productType.equals("electronic")){
			return "电子出版物";
		}else if(productType.equals("patent")){
			return "专利";
		}else if(productType.equals("otherProduct")){
			return "其他成果";
		}else 
			return "";
	}

	@Override
	public String pageName() {
		return PAGENAME;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getHomePostcode() {
		return homePostcode;
	}

	public void setHomePostcode(String homePostcode) {
		this.homePostcode = homePostcode;
	}

	public String getHomeFax() {
		return homeFax;
	}

	public void setHomeFax(String homeFax) {
		this.homeFax = homeFax;
	}
	
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getAvatarString() {
		return avatarString;
	}

	public void setAvatarString(String avatarString) {
		this.avatarString = avatarString;
	}
}
