package csdc.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Book;
import csdc.bean.Consultation;
import csdc.bean.Department;
import csdc.bean.Electronic;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMidinspection;
import csdc.bean.GeneralVariation;
import csdc.bean.Institute;
import csdc.bean.OtherProduct;
import csdc.bean.Paper;
import csdc.bean.Patent;
import csdc.bean.Person;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectApplicationReview;
import csdc.bean.ProjectData;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionReview;
import csdc.bean.ProjectFee;
import csdc.bean.ProjectFunding;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMember;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.Student;
import csdc.bean.SyncStatus;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IPersonService;
import csdc.service.IProjectService;
import csdc.service.ext.IProjectExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.DoubleTool;
import csdc.tool.FileTool;
import csdc.tool.WordTool;
import csdc.tool.ZipUtil;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.bean.SinossInterface;
import csdc.tool.info.WordInfo;
@Transactional
public class ProjectService extends BaseService implements IProjectService, IProjectExtService  {
	
	@Autowired
	protected IPersonService personService;
	
	
	//----------以下为各列表查询范围处理----------
	/**
	 * 处理项目申请查看范围
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String applicationInSearch(Account account){
//		String belongId = account.getBelongId();
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		Map session = ActionContext.getContext().getSession();
		Map map = (Map) session.get("applicationMap");
		StringBuffer hql = new StringBuffer();
		if(type.equals(AccountType.ADMINISTRATOR)){}//系统管理员
		else if(type.equals(AccountType.MINISTRY) ){//部级
			if(isPrincipal == 1){//主账号 
			} else {//子账号
				String ministryId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if (ministryId != null){
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.PROVINCE) ){//省级
			if(isPrincipal == 1){//主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", this.getBelongIdByAccount(account));
			}else{//子账号
				String provinceId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if(provinceId != null){
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				}else{
					hql.append(" and 1=0");
				}
			}
		} else if(type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			if (isPrincipal == 1){//主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", this.getBelongIdByAccount(account));
			} else {//子账号
				String universityId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if (universityId != null){
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		}else if(type.equals(AccountType.DEPARTMENT)){//院系
			if(isPrincipal == 1){//主账号
				hql.append(" and app.department.id=:belongId");
				map.put("belongId", account.getDepartment().getId());
			}else{//子账号
				String departmentId = this.getDepartmentIdByOfficerId(account.getOfficer().getId());
				if(departmentId != null){
					hql.append(" and app.department.id=:departmentId");
					map.put("departmentId", departmentId);
				}else{
					hql.append(" and 1=0");
				}
			}
		}else if(type.equals(AccountType.INSTITUTE)){//研究机构
			if(isPrincipal == 1){//主账号
				hql.append(" and app.institute.id=:belongId");
				map.put("belongId", account.getInstitute().getId());
			}else{//子账号
				String instituteId = this.getInstituteIdByOfficerId(account.getOfficer().getId());
				if(instituteId != null){
					hql.append(" and app.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				}else{
					hql.append(" and 1=0");
				}
			}
		}else if(type.within(AccountType.EXPERT, AccountType.STUDENT)){//外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = 1" );
			map.put("belongId", this.getBelongIdByAccount(account));
		}else{
			hql.append(" and 1=0");
		}
		session.put("applicationMap", map);
		return hql.toString();
	}
	
	/**
	 * 处理项目立项后查看范围（立项、年检、中检、结项、变更）
	 * @param account 当前账号对象
	 * @return 查询语句
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String grantedInSearch(Account account) {
		Map session = ActionContext.getContext().getSession();
		Map map = (Map) session.get("grantedMap");
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		StringBuffer hql = new StringBuffer();
		if (type.equals(AccountType.ADMINISTRATOR)) {
		}// 系统管理员
		else if (type.equals(AccountType.MINISTRY)) {// 部级
			if (isPrincipal == 1) {// 主账号
			} else {// 子账号
				String ministryId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if (ministryId != null) {
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.type=4 and uni.subjection.id=:belongId");
				map.put("belongId", this.getBelongIdByAccount(account));
			} else {// 子账号
				String provinceId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if (provinceId != null) {
					hql.append(" and uni.type=4 and uni.subjection.id=:provinceId");
					map.put("provinceId", provinceId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部属高校与地方高校
			if (isPrincipal == 1) {// 主账号
				hql.append(" and uni.id=:belongId");
				map.put("belongId", this.getBelongIdByAccount(account));
			} else {// 子账号
				String universityId = this.getAgencyIdByOfficerId(account.getOfficer().getId());
				if (universityId != null) {
					hql.append(" and uni.id=:universityId");
					map.put("universityId", universityId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.department.id=:belongId");
				map.put("belongId", account.getDepartment().getId());
			} else {// 子账号
				String departmentId = this.getDepartmentIdByOfficerId(account.getOfficer().getId());
				if (departmentId != null) {
					hql.append(" and gra.department.id=:departmentId");
					map.put("departmentId", departmentId);
				} else {
					hql.append(" and 1=0");
				}
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 研究机构
			if (isPrincipal == 1) {// 主账号
				hql.append(" and gra.institute.id=:belongId");
				map.put("belongId", account.getInstitute().getId());
			} else {// 子账号
				String instituteId = this.getInstituteIdByOfficerId(account.getOfficer().getId());
				if (instituteId != null) {
					hql.append(" and gra.institute.id=:instituteId");
					map.put("instituteId", instituteId);
				} else {
					hql.append(" and 1=0");
				}
			}

		} else if (type.within(AccountType.EXPERT, AccountType.STUDENT)) {// 外部专家与内部专家或学生
			hql.append(" and mem.member.id=:belongId and mem.groupNumber = gra.memberGroupNumber");
			map.put("belongId", this.getBelongIdByAccount(account));
		} else {
			hql.append(" and 1=0");
		}
		session.put("grantedMap", map);
		return hql.toString();
	}
	//----------以下为以定制列表形式获取项目信息----------
	/**
	 * 根据项目id获取项目拨款
	 * @param graId 项目id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public List getFundListByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql= "select rownum,fun.id,to_char(fun.date,'yyyy-MM-DD'),fun.fee,fun.attn,fun.note, fun.type, fun.status from " + projectGranted.getFundingClassName() + " fun where fun.granted.id=? order by fun.date desc, fun.id desc";
		List list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目id获取项目已拨款经费
	 * @param graId 项目id
	 * @return 项目拨款
	 */
	@SuppressWarnings("rawtypes")
	public Double getFundedFeeByGrantedId(String graId){
		Double fundedFee = 0.0;
		if(graId == null){
			return 0.0;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql= "from " + projectGranted.getFundingClassName() + " fun where fun.granted.id=? ";
		List list = dao.query(hql, graId);
		if (!list.isEmpty()) {
			ProjectFunding projectFunding = (ProjectFunding) list.get(0);
			if (projectFunding.getStatus() ==1) {
				fundedFee = DoubleTool.sum(fundedFee, projectFunding.getFee());
			}
			return fundedFee;
		}else {
			return 0.0;
		}	
	}
	
	//----------以下为设置项目经费相关信息----------
	/**
	 * 保存项目申请经费的相关字段的值
	 * @param projectFee 项目经费的对象
	 */
	public ProjectFee setProjectFee(ProjectFee projectFee){
		//申请经费概算信息
		if(projectFee != null){
			if(projectFee.getBookFee() != null){
				projectFee.setBookFee(projectFee.getBookFee());
			}
			if (projectFee.getBookNote() != null) {
				projectFee.setBookNote(projectFee.getBookNote());
			}
			if(projectFee.getDataFee() != null){
				projectFee.setDataFee(projectFee.getDataFee());
			}
			if (projectFee.getDataNote() != null) {
				projectFee.setDataNote(projectFee.getDataNote());
			}
			if(projectFee.getTravelFee() != null){
				projectFee.setTravelFee(projectFee.getTravelFee());
			}
			if (projectFee.getTravelNote() != null) {
				projectFee.setTravelNote(projectFee.getTravelNote());
			}
			if(projectFee.getDeviceFee() != null){
				projectFee.setDeviceFee(projectFee.getDeviceFee());
			}
			if (projectFee.getDeviceNote() != null) {
				projectFee.setDeviceNote(projectFee.getDeviceNote());
			}
			if(projectFee.getConferenceFee() != null){
				projectFee.setConferenceFee(projectFee.getConferenceFee());
			}
			if (projectFee.getConferenceNote() != null) {
				projectFee.setConferenceNote(projectFee.getConferenceNote());
			}
			if(projectFee.getConsultationFee() != null){
				projectFee.setConsultationFee(projectFee.getConsultationFee());
			}
			if (projectFee.getConsultationNote() != null) {
				projectFee.setConsultationNote(projectFee.getConsultationNote());
			}
			if(projectFee.getLaborFee() != null){
				projectFee.setLaborFee(projectFee.getLaborFee());
			}
			if (projectFee.getLaborNote() != null) {
				projectFee.setLaborNote(projectFee.getLaborNote());
			}
			if(projectFee.getPrintFee() != null){
				projectFee.setPrintFee(projectFee.getPrintFee());
			}
			if (projectFee.getPrintNote() != null) {
				projectFee.setPrintNote(projectFee.getPrintNote());
			}
			if(projectFee.getInternationalFee() != null){
				projectFee.setInternationalFee(projectFee.getInternationalFee());
			}
			if (projectFee.getInternationalNote() != null) {
				projectFee.setInternationalNote(projectFee.getInternationalNote());
			}
			if(projectFee.getIndirectFee() != null){
				projectFee.setIndirectFee(projectFee.getIndirectFee());
			}
			if (projectFee.getIndirectNote() != null) {
				projectFee.setIndirectNote(projectFee.getIndirectNote());
			}
			if(projectFee.getOtherFee() != null){
				projectFee.setOtherFee(projectFee.getOtherFee());
			}
			if (projectFee.getOtherNote() != null) {
				projectFee.setOtherNote(projectFee.getOtherNote());
			}
			if(projectFee.getTotalFee() != null){
				projectFee.setTotalFee(projectFee.getTotalFee());
			}
			if (projectFee.getFeeNote() != null) {
				projectFee.setFeeNote(projectFee.getFeeNote());
			}
			if (projectFee.getFundedFee() != null) {
				projectFee.setFundedFee(projectFee.getFundedFee());
			}
		}
		return projectFee;
	}
	
	//----------以下为设置项目相关信息----------
	/**
	 * 保存项目申请的相关字段的值
	 * @param application 项目申请的对象
	 */
	public ProjectApplication setAppBaseInfoFromApp(ProjectApplication application){
		//申请信息
		if(application != null){
			if(!application.getType().equals("key")){
				if(application.getName() != null){//中文名
					application.setName(this.personService.regularNames(application.getName().trim()));
				}
			}
			if(application.getEnglishName() != null){//英文名
				application.setEnglishName(application.getEnglishName().trim());
			}
			if(application.getKeywords() != null){//关键词
				application.setKeywords(this.MutipleToFormat(application.getKeywords().trim()));
			}
			if(application.getSummary() != null){//摘要
				application.setSummary(("A"+application.getSummary()).trim().substring(1));
			}
			if(application.getNote() != null){//备注
				application.setNote(("A"+application.getNote()).trim().substring(1));
			}
			if(application.getDisciplineType() != null){//学科门类
				application.setDisciplineType(application.getDisciplineType().trim());
			}
			if(application.getDiscipline() != null){//学科代码
				application.setDiscipline(application.getDiscipline().trim());
			}
			if(application.getRelativeDiscipline() != null){//相关学科代码
				application.setRelativeDiscipline(application.getRelativeDiscipline().trim());
			}
			if(application.getProductType() != null){//成果形式
				if(application.getProductType().contains("其他")){
					application.setProductTypeOther(this.MutipleToFormat(application.getProductTypeOther().trim()));
					String productType = application.getProductType().replace(", 其他", " ");
					application.setProductType(productType.replace(",", ";"));
				} else {
					application.setProductType(application.getProductType().replace(",", ";"));
					application.setProductTypeOther(null);
				}
			}else{
				application.setProductTypeOther(null);
			}
			if(application.getSubtype() != null && !"-1".equals(application.getSubtype().getId())){//项目子类
				SystemOption subtype = (SystemOption) dao.query(SystemOption.class, application.getSubtype().getId());
				application.setSubtype(subtype);
			}else{
				application.setSubtype(null);
			}	
			if(application.getResearchType() != null && !"-1".equals(application.getResearchType().getId())){//研究类别
				SystemOption researchType = (SystemOption) dao.query(SystemOption.class, application.getResearchType().getId());
				application.setResearchType(researchType);
			}else {
				application.setResearchType(null);
			}
		}
		return application;
	}
	
	/**
	 * 保存项目申请的机构信息
	 * @param oldApplication 原始项目申请的对象
	 * @param application 项目申请的对象
	 * @param deptInstFlag 1：研究基地	2：院系
	 * @return ProjectApplication 原始项目申请的对象
	 */
	public ProjectApplication setAppAgencyInfoFromApp(ProjectApplication oldApplication, ProjectApplication application, int deptInstFlag){
		if(oldApplication != null && application != null){
			if(deptInstFlag == 1){//研究基地
				Institute institute = (Institute)dao.query(Institute.class, application.getInstitute().getId());
				oldApplication.setDepartment(null);
				oldApplication.setInstitute(institute);
				if(institute != null){
					oldApplication.setDivisionName(institute.getName());
					Agency university = (Agency)dao.query(Agency.class, institute.getSubjection().getId());
					oldApplication.setUniversity(university);
					oldApplication.setAgencyName(university != null ? university.getName() : null);
					oldApplication.setProvince(university != null ? university.getProvince() : null);
					oldApplication.setProvinceName(university != null ? university.getProvince().getName() : null);
				}
			}else if(deptInstFlag == 2){//院系
				Department department = (Department)dao.query(Department.class, application.getDepartment().getId());
				oldApplication.setDepartment(department);
				oldApplication.setInstitute(null);
				if(department != null){
					oldApplication.setDivisionName(department.getName());
					Agency university = (Agency)dao.query(Agency.class, department.getUniversity().getId());
					oldApplication.setUniversity(university);
					oldApplication.setAgencyName(university != null ? university.getName() : null);
					oldApplication.setProvince(university != null ? university.getProvince() : null);
					oldApplication.setProvinceName(university != null ? university.getProvince().getName() : null);
				}
			}
		}
		return oldApplication;
	}
	
	/**
	 * 保存项目申请人信息
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @param loginer 登陆人对象
	 * @return
	 */
	public ProjectMember setApplicantInfoFromMember(ProjectMember applicant, LoginInfo loginer){
		AccountType accountType = loginer.getCurrentType();
		Account account = loginer.getAccount();
		Person person = dao.query(Person.class, this.getBelongIdByAccount(account));
		applicant.setMember(person);
		applicant.setMemberType(accountType.equals(AccountType.EXPERT) ? 2 : (accountType.equals(AccountType.TEACHER) ? 1 : 3));
		applicant.setMemberName(person.getName());
		applicant.setIdcardType(person.getIdcardType());
		applicant.setIdcardNumber(person.getIdcardNumber());
		applicant.setGender(person.getGender());
		if(accountType.equals(AccountType.EXPERT)){
			Expert expert = this.getExpertByPersonId(person.getId());
			applicant.setUniversity(null);
			applicant.setAgencyName(expert.getAgencyName());
			applicant.setDepartment(null);
			applicant.setInstitute(null);
			applicant.setDivisionType(3);
			applicant.setDivisionName(expert.getDivisionName());
		}else if(accountType.equals(AccountType.TEACHER)){
			Teacher teacher = this.getTeacherByPersonId(person.getId());
			applicant.setUniversity(teacher.getUniversity());
			applicant.setAgencyName(teacher.getUniversity().getName());
			applicant.setDepartment(teacher.getDepartment());
			applicant.setInstitute(teacher.getInstitute());
			if(teacher.getDepartment() != null){
				applicant.setDivisionType(2);
				applicant.setDivisionName(teacher.getDepartment().getName());
			}else if(teacher.getInstitute() != null){
				applicant.setDivisionType(1);
				applicant.setDivisionName(teacher.getInstitute().getName());
			}else{
				applicant.setDivisionType(null);
				applicant.setDivisionName(null);
			}
		}else if(accountType.equals(AccountType.ADMINISTRATOR)){
			Student student = this.getStudentByPersonId(person.getId());
			applicant.setUniversity(student.getUniversity());
			applicant.setAgencyName(student.getUniversity().getName());
			applicant.setDepartment(student.getDepartment());
			applicant.setInstitute(student.getInstitute());
			if(student.getDepartment() != null){
				applicant.setDivisionType(2);
				applicant.setDivisionName(student.getDepartment().getName());
			}else if(student.getInstitute() != null){
				applicant.setDivisionType(1);
				applicant.setDivisionName(student.getInstitute().getName());
			}else{
				applicant.setDivisionType(null);
				applicant.setDivisionName(null);
			}
		}
		return applicant;
	}
	/**
	 * 保存项目成员信息（新建项目成员）
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @param entityId新建成员时选择的personId
	 * @return 项目成员对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectMember setMemberInfoFromNewMember(ProjectMember member, String entityId){
		//人员信息
		if(member.getMemberName() != null && member.getDivisionType()!=null){//有成员id信息
			String personId = "";
			String divisionId = "";
			Person person = new Person();
			if (entityId.equals("-1")) {
				Map map = new HashMap();
				map.put("idcardType", member.getIdcardType() != null ? member.getIdcardType() : null);
				map.put("idcardNumber", member.getIdcardNumber() != null ? member.getIdcardNumber() : null);
				map.put("personName", member.getMemberName() != null ? member.getMemberName() : null);
				map.put("personType", member.getMemberType() != null ?  member.getMemberType() : null);
				map.put("gender", member.getGender() != null ? member.getGender() : null);
				if (member.getDivisionType() == 2) {//院系
					Department department = dao.query(Department.class, member.getDepartment().getId());
					map.put("divisionName",department.getName());
					map.put("agencyId", (department.getUniversity() != null) ? department.getUniversity().getId() : null);
					map.put("agencyName", (department.getUniversity() != null) ? department.getUniversity().getName() : null);
					map.put("departmentId", member.getDepartment().getId());
				} else {
					Institute institute = dao.query(Institute.class, member.getInstitute().getId());
					map.put("divisionName",institute.getName());
					map.put("agencyId", (institute.getSubjection() != null) ? institute.getSubjection().getId() : null);
					map.put("agencyName", (institute.getSubjection() != null) ? institute.getSubjection().getName() : null);
					map.put("instituteId", member.getInstitute().getId());
				}
				map.put("divisionType", member.getDivisionType());
				map.put("workMonthPerYear", member.getWorkMonthPerYear());
				map.put("specialistTitle", member.getSpecialistTitle());
				map = this.doWithNewPerson(map);
				personId = map.get("personId").toString();
				divisionId = map.get("divisionId").toString();
				person = (Person)dao.query(Person.class, personId);
				member.setMember(person);
			} else {
				if (entityId.equals("")) {
					person = dao.query(Person.class, member.getMember().getId());
				} else {
					person = dao.query(Person.class, entityId);
				}
				if(member.getDivisionType() == 1){//研究基地
					divisionId = member.getInstitute().getId();
				}else if(member.getDivisionType() == 2){//院系
					divisionId = member.getDepartment().getId();
				}
			}
			if(member.getDivisionType()!=null && member.getDivisionType() == 1){//研究基地
				Institute institute =(Institute)dao.query(Institute.class, divisionId);
				member.setInstitute(institute);
				member.setDivisionName(institute.getName());
				member.setDepartment(null);
				member.setUniversity(institute.getSubjection());
				member.setAgencyName(institute.getSubjection().getName());
			}else if(member.getDivisionType()!=null && member.getDivisionType() == 2){//院系
				Department department =(Department)dao.query(Department.class, divisionId);
				member.setDivisionName(department.getName());
				member.setInstitute(null);
				member.setDepartment(department);
				member.setUniversity(department.getUniversity());
				member.setAgencyName(department.getUniversity().getName());
			}
			if (null == member.getIdcardType()) {
				member.setIdcardType(person.getIdcardType());
			}
			if (null == member.getIdcardNumber()) {
				member.setIdcardNumber(person.getIdcardNumber());
			}
			if (null == member.getGender()) {
				member.setGender(person.getGender());
			}
		}else{//不含成员id信息
			member = setMemberInfoForNoProsonIdMember(member);
		}
		member.setGroupNumber(1);//将组号设为1
		if(member.getSpecialistTitle() != null && !"-1".equals(member.getSpecialistTitle())){//职称 
			member.setSpecialistTitle(member.getSpecialistTitle().trim());
		}else{
			member.setSpecialistTitle(null);
		}
		if(member.getMajor() != null){//专业
			member.setMajor(member.getMajor().trim());
		}
		if(member.getWorkDivision() != null){//分工情况
			member.setWorkDivision(member.getWorkDivision().trim());
		}
		if (member.getBirthday() != null) {
			member.setBirthday(member.getBirthday());
		}
		if (member.getPosition() != null) {
			member.setPosition(member.getPosition().trim());
		}
		if (member.getEducation() != null) {
			member.setEducation(member.getEducation().trim());
		}
		return member;
	}

	/**
	 * 根据变更成员的相关信息匹配库中人员
	 * 
	 */
	public List doWithPerson(Map personInfo){
		List<Object[]> personList = new ArrayList();
		Integer personType = (Integer)personInfo.get("personType");
		Integer divisionType = (Integer)personInfo.get("divisionType");
		String gender = (String)personInfo.get("gender");
		
		StringBuffer hql = new StringBuffer();
		if (personType == 1) {//教师
			hql.append("select p.id, p.name, t.agencyName, t.divisionName, t.position from Teacher t left outer join  t.person p where p.name = :personName and t.university.id = :agencyId");
			if(divisionType == 1){//研究基地
				hql.append(" and t.institute.id = :instituteId");
			}else if(divisionType == 2){//院系
				hql.append(" and t.department.id = :departmentId");
			}else{
				hql.append(" and 1=0");
			} 
			if (!gender.equals("-1")) {
				hql.append(" and (p.gender is null or p.gender=:gender)");
			}
			personList = dao.query(hql.toString(), personInfo);
		} else if (personType == 2) {//外部专家
			hql.append("select p.id, p.name, exp.agencyName, exp.divisionName, exp.position from Expert exp left outer join  exp.person p where p.name = :personName and exp.agencyName=:agencyName and exp.divisionName=:divisionName");
			if (!gender.equals("-1")) {
				hql.append("and (p.gender is null or p.gender=:gender)");
			}
			personList = dao.query(hql.toString(), personInfo);
		} else if (personType == 3) {//学生
			hql.append("select p.id, p.name, u.name, d.name, i.name from Student stu left outer join  stu.person p left join stu.department d left join stu.institute i left join stu.university u where p.name = :personName and stu.university.id=:agencyId");
			if(divisionType == 1){//研究基地
				hql.append(" and stu.institute.id =:instituteId");
			}else if(divisionType == 2){//院系
				hql.append(" and stu.department.id =:departmentId");
			}else{
				hql.append(" and 1=0");
			}
			if (!gender.equals("-1")) {
				hql.append(" and (p.gender is null or p.gender=:gender)");
			}
			personList = dao.query(hql.toString(), personInfo);
		}
		return personList;
	}
	
	/**
	 * 保存项目成员信息
	 * @param member 项目成员对象，member对象的member字段id为教师、专家或者学生id
	 * @return 项目成员对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectMember setMemberInfoFromMember(ProjectMember member){
		//人员信息
		if(member.getMember() != null && member.getMember().getId() != null){//有成员id信息
			if(member.getMemberType() == 1){//教师
				List<Teacher> list = this.getTeacherFetchPerson(member.getMember().getId());
				if(list != null && list.size() > 0){
					Teacher teacherM = list.get(0);
					member.setMember(teacherM.getPerson());
					member.setMemberName(teacherM.getPerson().getName());
					member.setIdcardType(teacherM.getPerson().getIdcardType());
					member.setIdcardNumber(teacherM.getPerson().getIdcardNumber());
					member.setGender(teacherM.getPerson().getGender());
					member.setUniversity(teacherM.getUniversity());
					member.setAgencyName(teacherM.getUniversity().getName());
					member.setDepartment(teacherM.getDepartment());
					member.setInstitute(teacherM.getInstitute());
					if(teacherM.getDepartment() != null){
						member.setDivisionType(2);
						member.setDivisionName(teacherM.getDepartment().getName());
					}else if(teacherM.getInstitute() != null){
						member.setDivisionType(1);
						member.setDivisionName(teacherM.getInstitute().getName());
					}else{
						member.setDivisionType(null);
						member.setDivisionName(null);
					}
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}else if(member.getMemberType() == 2){//专家
				List<Expert> list2 = this.getExpertFetchPerson(member.getMember().getId());
				if(list2!=null && list2.size()==1){
					Expert expertM = list2.get(0);
					member.setMember(expertM.getPerson());
					member.setMemberName(expertM.getPerson().getName());
					member.setIdcardType(expertM.getPerson().getIdcardType());
					member.setIdcardNumber(expertM.getPerson().getIdcardNumber());
					member.setGender(expertM.getPerson().getGender());
					member.setUniversity(null);
					member.setAgencyName(expertM.getAgencyName());
					member.setDepartment(null);
					member.setInstitute(null);
					member.setDivisionType(3);
					member.setDivisionName(expertM.getDivisionName());
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}else if(member.getMemberType() == 3){//学生
				List<Student> list3 = this.getStudentFetchPerson(member.getMember().getId());
				if(list3!=null && list3.size()>0){
					Student studentM = list3.get(0);
					member.setMember(studentM.getPerson());
					member.setMemberName(studentM.getPerson().getName());
					member.setIdcardType(studentM.getPerson().getIdcardType());
					member.setIdcardNumber(studentM.getPerson().getIdcardNumber());
					member.setGender(studentM.getPerson().getGender());
					member.setUniversity(studentM.getUniversity());
					member.setAgencyName(studentM.getUniversity().getName());
					member.setDepartment(studentM.getDepartment());
					member.setInstitute(studentM.getInstitute());
					if(studentM.getDepartment() != null){
						member.setDivisionType(2);
						member.setDivisionName(studentM.getDepartment().getName());
					}else if(studentM.getInstitute() != null){
						member.setDivisionType(1);
						member.setDivisionName(studentM.getInstitute().getName());
					}else{
						member.setDivisionType(null);
						member.setDivisionName(null);
					}
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}
		}else{//不含成员id信息
			member = setMemberInfoForNoProsonIdMember(member);
		}
		member.setGroupNumber(1);//将组号设为1
		if(member.getSpecialistTitle() != null && !"-1".equals(member.getSpecialistTitle())){//职称 
			member.setSpecialistTitle(member.getSpecialistTitle().trim());
		}else{
			member.setSpecialistTitle(null);
		}
		if(member.getMajor() != null){//专业
			member.setMajor(member.getMajor().trim());
		}
		if(member.getWorkDivision() != null){//分工情况
			member.setWorkDivision(member.getWorkDivision().trim());
		}
		if (member.getBirthday() != null) {
			member.setBirthday(member.getBirthday());
		}
		if (member.getPosition() != null) {
			member.setPosition(member.getPosition().trim());
		}
		if (member.getEducation() != null) {
			member.setEducation(member.getEducation().trim());
		}
		return member;
	}
	
	/**
	 * 保存项目成员信息
	 * @param member 项目成员对象，member对象的member字段id为person的id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProjectMember setMemberInfoFromPerson(ProjectMember member){
		if(member.getMember() != null && member.getMember().getId() != null){//有成员id信息
			if(member.getMemberType() == 1){//教师
				List<Teacher> list = this.getTeacherFetchPerson("", member.getMember().getId());
				if(list != null && list.size() > 0){
					Teacher teacherM = list.get(0);
					member.setMember(teacherM.getPerson());
					member.setMemberName(teacherM.getPerson().getName());
					member.setIdcardType(teacherM.getPerson().getIdcardType());
					member.setIdcardNumber(teacherM.getPerson().getIdcardNumber());
					member.setGender(teacherM.getPerson().getGender());
					member.setUniversity(teacherM.getUniversity());
					member.setAgencyName(teacherM.getUniversity().getName());
					member.setDepartment(teacherM.getDepartment());
					member.setInstitute(teacherM.getInstitute());
					if(teacherM.getDepartment() != null){
						member.setDivisionType(2);
						member.setDivisionName(teacherM.getDepartment().getName());
					}else if(teacherM.getInstitute() != null){
						member.setDivisionType(1);
						member.setDivisionName(teacherM.getInstitute().getName());
					}else{
						member.setDivisionType(null);
						member.setDivisionName(null);
					}
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}else if(member.getMemberType() == 2){//专家
				List<Expert> list2 = this.getExpertFetchPerson("", member.getMember().getId());
				if(list2!=null && list2.size()==1){
					Expert expertM = list2.get(0);
					member.setMember(expertM.getPerson());
					member.setMemberName(expertM.getPerson().getName());
					member.setIdcardType(expertM.getPerson().getIdcardType());
					member.setIdcardNumber(expertM.getPerson().getIdcardNumber());
					member.setGender(expertM.getPerson().getGender());
					member.setUniversity(null);
					member.setAgencyName(expertM.getAgencyName());
					member.setDepartment(null);
					member.setInstitute(null);
					member.setDivisionType(3);
					member.setDivisionName(expertM.getDivisionName());
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}else if(member.getMemberType() == 3){//学生
				List<Student> list3 = this.getStudentFetchPerson("", member.getMember().getId());
				if(list3!=null && list3.size()>0){
					Student studentM = list3.get(0);
					member.setMember(studentM.getPerson());
					member.setMemberName(studentM.getPerson().getName());
					member.setIdcardType(studentM.getPerson().getIdcardType());
					member.setIdcardNumber(studentM.getPerson().getIdcardNumber());
					member.setGender(studentM.getPerson().getGender());
					member.setUniversity(studentM.getUniversity());
					member.setAgencyName(studentM.getUniversity().getName());
					member.setDepartment(studentM.getDepartment());
					member.setInstitute(studentM.getInstitute());
					if(studentM.getDepartment() != null){
						member.setDivisionType(2);
						member.setDivisionName(studentM.getDepartment().getName());
					}else if(studentM.getInstitute() != null){
						member.setDivisionType(1);
						member.setDivisionName(studentM.getInstitute().getName());
					}else{
						member.setDivisionType(null);
						member.setDivisionName(null);
					}
				}else{
					member = setMemberInfoForNoProsonIdMember(member);
				}
			}
		}else{//不含成员id信息
			member = setMemberInfoForNoProsonIdMember(member);
		}
		member.setGroupNumber(1);//将组号设为1
		if(member.getSpecialistTitle() != null && !"-1".equals(member.getSpecialistTitle())){//职称 
			member.setSpecialistTitle(member.getSpecialistTitle().trim());
		}else{
			member.setSpecialistTitle(null);
		}
		if(member.getMajor() != null){//专业
			member.setMajor(member.getMajor().trim());
		}
		if(member.getWorkDivision() != null){//分工情况
			member.setWorkDivision(member.getWorkDivision().trim());
		}
		if (member.getBirthday() != null) {
			member.setBirthday(member.getBirthday());
		}
		if (member.getPosition() != null) {
			member.setPosition(member.getPosition().trim());
		}
		if (member.getEducation() != null) {
			member.setEducation(member.getEducation().trim());
		}
		return member;
	}
	
	/**
	 * 设置无人员id的成员信息
	 * @param 项目成员对象
	 * @param 赋值后的项目成员对象
	 */
	public ProjectMember setMemberInfoForNoProsonIdMember(ProjectMember member){
		if(member != null){
			member.setMember(null);
			member.setMemberName(member.getMemberName().trim());
			member.setIdcardType(member.getIdcardType()!=null?member.getIdcardType():null);
			member.setIdcardNumber(member.getIdcardNumber()!=null?member.getIdcardNumber():null);
			member.setGender(member.getGender()!=null?member.getGender():null);
			member.setUniversity(null);
			member.setAgencyName(member.getAgencyName()!=null?member.getAgencyName().trim():null);
			member.setDepartment(null);
			member.setInstitute(null);
			member.setDivisionType(null);
			member.setDivisionName(member.getDivisionName()!=null?member.getDivisionName().trim():null);
		}
		return member;
	}
	
	/**
	 * 设置项目成员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param member 项目成员
	 * @return ProjectMember 项目成员
	 */
	public ProjectMember setMemberPersonInfoFromMember(ProjectMember member){
		Person person = member.getMember();
		if(person == null || person.getId() == null || person.getId().trim().isEmpty() || member.getMemberType() == null || member.getMemberType() < 1){
			;
		}else if(member.getMemberType() == 1){//教师
			String teacherId = "";
			String tempUniversityId1 =null;
			if (member.getUniversity() != null) {
				tempUniversityId1 = member.getUniversity().getId();
			}
			
			teacherId = this.getTeacherIdByMemberAllUnit(person.getId(), tempUniversityId1, member.getDepartment(), member.getInstitute());
			if(teacherId == null){
				String tempUniversityId2 =null;
				if (member.getUniversity() != null) {
					tempUniversityId2 = member.getUniversity().getId();
				}
				teacherId = this.getTeacherIdByMemberPartUnit(person.getId(), tempUniversityId2);
			}
			person.setId((teacherId != null) ? teacherId : null);
			member.setMember(person);
		}else if(member.getMemberType() == 2){//外部专家
			String expertId = this.getExpertIdByPersonIdUnit(person.getId(), member.getAgencyName(), member.getDivisionName());
			if(expertId == null){
				expertId = this.getExpertIdByPersonId(person.getId());
			}
			person.setId((expertId != null) ? expertId : null);
			member.setMember(person);
		}else if(member.getMemberType() == 3){//学生
			String studentId = this.getStudentIdByPersonId(person.getId());
			person.setId((studentId != null) ? studentId : null);
			member.setMember(person);
		}
		return member;
	}
	
	/**
	 * 设置项目立项信息,用于走流程申请
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromApp(ProjectApplication application, ProjectGranted granted){
		if(application != null){
			granted.setStatus(1);
			granted.setProjectType(application.getType());
			granted.setName(application.getName());
			granted.setEnglishName(application.getEnglishName());
			granted.setUniversity(application.getUniversity());
			granted.setProvince(application.getUniversity().getProvince());
			granted.setProvinceName(application.getUniversity().getProvince().getName());
			granted.setDepartment(application.getDepartment());
			granted.setInstitute(application.getInstitute());
			granted.setAgencyName(application.getAgencyName());
			granted.setDivisionName(application.getDivisionName());
			granted.setApproveFee(application.getApplyFee());
			if(application.getSubtype() != null && !"-1".equals(application.getSubtype().getId())){//项目子类
				SystemOption subtype = (SystemOption) dao.query(SystemOption.class, application.getSubtype().getId());
				granted.setSubtype(subtype);
			}else{
				granted.setSubtype(null);
			}
			if(application.getSubsubtype() != null && !"-1".equals(application.getSubsubtype().getId())){//项目子子类
				SystemOption subsubtype = (SystemOption) dao.query(SystemOption.class, application.getSubtype().getId());
				granted.setSubsubtype(subsubtype);
			}else{
				granted.setSubsubtype(null);
			}
			granted.setNote(application.getNote());
			granted.setCreateMode(0);
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			granted.setPlanEndDate(application.getPlanEndDate());
			granted.setMemberGroupNumber(1);
		}
		return granted;
	}
	
	/**
	 * 设置项目立项信息,用于录入申请
	 * @param application 项目申请对象
	 * @param granted 项目立项对象
	 * @return ProjectGranted
	 */
	public ProjectGranted setGrantedInfoFromAppForImported(ProjectApplication application, ProjectGranted granted){
		if(application != null && granted != null){
			granted.setApplicantId(application.getApplicantId());
			granted.setApplicantName(application.getApplicantName());
			granted.setUniversity(application.getUniversity());
			granted.setProvince(application.getUniversity().getProvince());
			granted.setProvinceName(application.getUniversity().getProvince().getName());
			granted.setAgencyName(application.getAgencyName());
			granted.setDepartment(application.getDepartment());
			granted.setInstitute(application.getInstitute());
			granted.setDivisionName(application.getDivisionName());
			granted.setProductType(application.getProductType());
			granted.setProductTypeOther(application.getProductTypeOther());
			granted.setPlanEndDate(application.getPlanEndDate());
			granted.setName(application.getName());
			granted.setAuditType(1);
		}
		return granted;
	}
	
	/**
	 * 保存项目申请经费概算相关字段的值
	 * @param oldProjectFee 原始经费对象
	 * @param projectFee 页面经费对象
	 */
	public ProjectFee updateProjectFee(ProjectFee oldProjectFee, ProjectFee projectFee){
		if(oldProjectFee != null && projectFee != null){
			if(projectFee.getBookFee() != null){
				oldProjectFee.setBookFee(projectFee.getBookFee());
			}else {
				oldProjectFee.setBookFee(null);
			}
			if (projectFee.getBookNote() != null) {
				oldProjectFee.setBookNote(projectFee.getBookNote());
			}else {
				oldProjectFee.setBookNote(null);
			}
			if(projectFee.getDataFee() != null){
				oldProjectFee.setDataFee(projectFee.getDataFee());
			}else {
				oldProjectFee.setDataFee(null);
			}
			if (projectFee.getDataNote() != null) {
				oldProjectFee.setDataNote(projectFee.getDataNote());
			}else {
				oldProjectFee.setDataNote(null);
			}
			if(projectFee.getTravelFee() != null){
				oldProjectFee.setTravelFee(projectFee.getTravelFee());
			}else {
				oldProjectFee.setTravelFee(null);
			}
			if (projectFee.getTravelNote() != null) {
				oldProjectFee.setTravelNote(projectFee.getTravelNote());
			}else {
				oldProjectFee.setTravelNote(null);
			}
			if(projectFee.getDeviceFee() != null){
				oldProjectFee.setDeviceFee(projectFee.getDeviceFee());
			}else {
				oldProjectFee.setDeviceFee(null);
			}
			if (projectFee.getDeviceNote() != null) {
				oldProjectFee.setDeviceNote(projectFee.getDeviceNote());
			}else {
				oldProjectFee.setDeviceNote(null);
			}
			if(projectFee.getConferenceFee() != null){
				oldProjectFee.setConferenceFee(projectFee.getConferenceFee());
			}else {
				oldProjectFee.setConferenceFee(null);
			}
			if (projectFee.getConferenceNote() != null) {
				oldProjectFee.setConferenceNote(projectFee.getConferenceNote());
			}else {
				oldProjectFee.setConferenceNote(null);
			}
			if(projectFee.getConsultationFee() != null){
				oldProjectFee.setConsultationFee(projectFee.getConsultationFee());
			}else {
				oldProjectFee.setConsultationFee(null);
			}
			if (projectFee.getConsultationNote() != null) {
				oldProjectFee.setConsultationNote(projectFee.getConsultationNote());
			}else {
				oldProjectFee.setConsultationNote(null);
			}
			if(projectFee.getLaborFee() != null){
				oldProjectFee.setLaborFee(projectFee.getLaborFee());
			}else {
				oldProjectFee.setLaborFee(null);
			}
			if (projectFee.getLaborNote() != null) {
				oldProjectFee.setLaborNote(projectFee.getLaborNote());
			}else {
				oldProjectFee.setLaborNote(null);
			}
			if(projectFee.getPrintFee() != null){
				oldProjectFee.setPrintFee(projectFee.getPrintFee());
			}else {
				oldProjectFee.setPrintFee(null);
			}
			if (projectFee.getPrintNote() != null) {
				oldProjectFee.setPrintNote(projectFee.getPrintNote());
			}else {
				oldProjectFee.setPrintNote(null);
			}
			if(projectFee.getInternationalFee() != null){
				oldProjectFee.setInternationalFee(projectFee.getInternationalFee());
			}else {
				oldProjectFee.setInternationalFee(null);
			}
			if (projectFee.getInternationalNote() != null) {
				oldProjectFee.setInternationalNote(projectFee.getInternationalNote());
			}else {
				oldProjectFee.setInternationalNote(null);
			}
			if(projectFee.getIndirectFee() != null){
				oldProjectFee.setIndirectFee(projectFee.getIndirectFee());
			}else {
				oldProjectFee.setIndirectFee(null);
			}
			if (projectFee.getIndirectNote() != null) {
				oldProjectFee.setIndirectNote(projectFee.getIndirectNote());
			}else {
				oldProjectFee.setIndirectNote(null);
			}
			if(projectFee.getOtherFee() != null){
				oldProjectFee.setOtherFee(projectFee.getOtherFee());
			}else {
				oldProjectFee.setOtherFee(null);
			}
			if (projectFee.getOtherNote() != null) {
				oldProjectFee.setOtherNote(projectFee.getOtherNote());
			}else {
				oldProjectFee.setOtherNote(null);
			}
			if(projectFee.getTotalFee() != null){
				oldProjectFee.setTotalFee(projectFee.getTotalFee());
			}else {
				oldProjectFee.setTotalFee(0.0);
			}
			if (projectFee.getFundedFee() != null) {
				oldProjectFee.setFundedFee(projectFee.getFundedFee());
			}else {
				oldProjectFee.setFundedFee(0.0);
			}
			if (projectFee.getFeeNote() != null) {
				oldProjectFee.setFeeNote(projectFee.getFeeNote());
			}else {
				oldProjectFee.setFeeNote(null);
			}
		}
		return oldProjectFee;
	}
	
	/**
	 * 保存项目申请的相关字段的值
	 * @param oldApplication 原始项目申请的对象
	 * @param application 项目申请的对象
	 * @return ProjectApplication 原始项目申请的对象
	 */
	public ProjectApplication updateAppBaseInfoFromApp(ProjectApplication oldApplication, ProjectApplication application){
		if(oldApplication != null && application != null){
			if(!oldApplication.getType().equals("key")){
				if(application.getName() != null){//中文名
					oldApplication.setName(application.getName().trim());
				}else{
					oldApplication.setName(null);
				}
			}
			if(application.getEnglishName() != null){//英文名
				oldApplication.setEnglishName(application.getEnglishName().trim());
			}else{
				oldApplication.setEnglishName(null);
			}
			if(application.getKeywords() != null){//关键词
				oldApplication.setKeywords(this.MutipleToFormat(application.getKeywords().trim()));
			}else{
				oldApplication.setKeywords(null);
			}
			if(application.getSummary() != null){//摘要
				oldApplication.setSummary(("A"+application.getSummary()).trim().substring(1));
			}else{
				oldApplication.setSummary(null);
			}
			if(application.getNote() != null){//备注
				oldApplication.setNote(("A"+application.getNote()).trim().substring(1));
			}
			if(application.getDisciplineType() != null){//学科门类
				oldApplication.setDisciplineType(application.getDisciplineType().trim());
			}else{
				oldApplication.setDisciplineType(null);
			}
			if(application.getDiscipline() != null){//学科代码
				oldApplication.setDiscipline(application.getDiscipline().trim());
			}else{
				oldApplication.setDiscipline(null);
			}
			if(application.getRelativeDiscipline() != null){//相关学科代码
				oldApplication.setRelativeDiscipline(application.getRelativeDiscipline().trim());
			}else{
				oldApplication.setRelativeDiscipline(null);
			}
			if(application.getProductType() != null){//成果形式
				if(application.getProductType().contains("其他")){
					application.setProductTypeOther(this.MutipleToFormat(application.getProductTypeOther().trim()));
					String productType = application.getProductType().replace(", 其他", " ");
					application.setProductType(productType.replace(",", ";"));
				} else {
					application.setProductType(application.getProductType().replace(",", ";"));
					application.setProductTypeOther(null);
				}
			}else{
				oldApplication.setProductTypeOther(null);
			}
			if(application.getYear()>=0 || application.getYear()<=10000){//申请年份
				oldApplication.setYear(application.getYear());
			}
			if(!application.getType().equals("key")){
				if(application.getSubtype().getId() != null && !application.getSubtype().getId().equals("-1")){//子类
					SystemOption subtype = (SystemOption) dao.query(SystemOption.class, application.getSubtype().getId());
					oldApplication.setSubtype(subtype);
				}else{
					oldApplication.setSubtype(null);
				}
			}
			if(application.getResearchType().getId() != null && !application.getResearchType().getId().equals("-1")){//研究类别
				SystemOption researchType = (SystemOption) dao.query(SystemOption.class, application.getResearchType().getId());
				oldApplication.setResearchType(researchType);
			}else{
				oldApplication.setResearchType(null);
			}
			if(application.getPlanEndDate() != null){//计划完成时间
				oldApplication.setPlanEndDate(application.getPlanEndDate());
			}else{
				oldApplication.setPlanEndDate(null);
			}
			if(application.getApplicantSubmitDate() != null){//申请时间
				oldApplication.setApplicantSubmitDate(application.getApplicantSubmitDate());
			}else{
				oldApplication.setApplicantSubmitDate(null);
			}
			if(application.getApplyFee() != null){//申请经费
				oldApplication.setApplyFee(application.getApplyFee());
			}else{
				oldApplication.setApplyFee(null);
			}
			if(application.getOtherFee() != null){//其他来源经费
				oldApplication.setOtherFee(application.getOtherFee());
			}else{
				oldApplication.setOtherFee(null);
			}
		}
		return oldApplication;
	}
	
	/**
	 * 获取专业职称的二级节点值
	 * @return map (code/name 到 code/name 的映射)
	 */
	@Transactional
	public Map<String,String> getChildrenMapByParentIAndStandard(){
		Map<String,String> map = new HashMap();
		List<SystemOption> systemOptionList = dao.query("from SystemOption s where s.standard = 'GBT8561-2001' and s.systemOption.id != '4028d88a380233c701380235bd760001' and s.id != '4028d88a380233c701380235bd760001' ");
		if(systemOptionList.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				map.put(systemOption.getCode()+"/"+systemOption.getName(), systemOption.getCode()+"/"+systemOption.getName());
			}
		}
		return map;
	}
	/**
	 * 保存项目立项的相关字段的值
	 * @param oldGranted 原始项目立项的对象
	 * @param granted 项目立项的对象
	 * @return ProjectApplication 原始项目立项的对象
	 */
	public ProjectGranted updateGrantedInfoFromGranted(ProjectGranted oldGranted, ProjectGranted granted){
		oldGranted.setNumber(granted.getNumber().trim());
		if(!granted.getProjectType().equals("key") && granted.getSubtype().getId() != null && !granted.getSubtype().getId().equals("-1")){//项目子类
			SystemOption subtype = (SystemOption) dao.query(SystemOption.class, granted.getSubtype().getId());
			oldGranted.setSubtype(subtype);
		}else{
			oldGranted.setSubtype(null);
		}
		if(granted.getApproveDate() != null){//批准时间
			oldGranted.setApproveDate(granted.getApproveDate());
		}else{
			oldGranted.setApproveDate(null);
		}
		if(granted.getApproveFee() != null){//批准经费
			oldGranted.setApproveFee(granted.getApproveFee());
		}else{
			oldGranted.setApproveFee(null);
		}
		if(oldGranted.getStatus() == 0){//新建
			oldGranted.setStatus(1);
		}
		return oldGranted;
	}
	
	/**
	 * 根据项目负责人成员对象获得项目负责人的人员主表id和name
	 * @param dirMember 项目负责人成员对象
	 * @return String[2] 0:id	1:name
	 */
	@SuppressWarnings("rawtypes")
	public String[] getAppDirectorIdAndName(ProjectMember dirMember){
		String[] idAndName = new String[2];
		String applicantId = "";
		String applicantName = "";
		if(dirMember != null){
			List list = null;
			if(dirMember.getMember() != null && dirMember.getMember().getId() != null){//有成员id信息
				if(dirMember.getMemberType() == 1){//教师
					list = this.getTeacherFetchPerson(dirMember.getMember().getId());
				}else if(dirMember.getMemberType() == 2){//外部专家
					list = this.getExpertFetchPerson(dirMember.getMember().getId());
				}else if(dirMember.getMemberType() == 3){//学生
					list = this.getStudentFetchPerson(dirMember.getMember().getId());
				}
				if(list !=null && list.size()>0){
					if(dirMember.getMemberType() == 1){//教师
						applicantId =((Teacher)list.get(0)).getPerson().getId();
						applicantName = ((Teacher)list.get(0)).getPerson().getName();
					}else if(dirMember.getMemberType() == 2){//外部专家
						applicantId = ((Expert)list.get(0)).getPerson().getId();
						applicantName = ((Expert)list.get(0)).getPerson().getName();
					}else if(dirMember.getMemberType() == 3){//学生
						applicantId = ((Student)list.get(0)).getPerson().getId();
						applicantName = ((Student)list.get(0)).getPerson().getName();
					}
				}else{//未成功获取教师或学生信息
					applicantId = ""; 
					applicantName = dirMember.getMemberName().trim();
				}
			}else{
				applicantId = ""; 
				applicantName = dirMember.getMemberName().trim();
			}
		}
		idAndName[0] = applicantId;
		idAndName[1] = applicantName;
		return idAndName;
	}
	/**
	 * 根据项目申请对象获得项目所在部门的标志
	 * @param application 项目申请对象
	 * @return int 1：研究基地	2：院系
	 */
	public int getDeptInstFlagByApp(ProjectApplication application){
		int deptInstFlag = 0;
		if(application.getDepartment() != null && application.getDepartment().getId() != null){
			deptInstFlag = 2;
		}else if(application.getInstitute() != null && application.getInstitute().getId() != null){
			deptInstFlag = 1;
		}else{
			deptInstFlag = 0;
		}
		return deptInstFlag;
	}
//-------------------以下为对列表初级检索的处理-----------------
	//项目申请相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQL(int searchType){
		String hql = "";
		if(searchType == 1){//按项目名称查询
			hql = " and LOWER(app.name) like :keyword";
		}else if(searchType == 2){//按申请人查询
			hql = " and LOWER(app.applicantName) like :keyword";
		}else if(searchType == 3){//按依托高校查询
			hql = " and LOWER(app.agencyName) like :keyword";
		}else if(searchType == 4){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		}else if(searchType == 5){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		}else if(searchType == 6){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else{//按上述所有字段查询
			hql = " and (LOWER(app.name) like :keyword or LOWER(app.applicantName) like :keyword " +
					"or LOWER(app.agencyName) like :keyword or LOWER(so.name) like :keyword or " +
					"LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword )";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目申请申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", app.applicantSubmitStatus, app.applicantSubmitDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", app.deptInstAuditStatus, app.deptInstAuditResult, app.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", app.universityAuditStatus, app.universityAuditResult, app.universityAuditDate, app.reviewStatus, app.reviewResult ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", app.provinceAuditStatus, app.provinceAuditResult, app.provinceAuditDate, app.reviewStatus, app.reviewResult ";
		}
		else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)) {
			hql = ", app.ministryAuditStatus, app.ministryAuditResult, app.reviewStatus, app.reviewResult, app.finalAuditDate ";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目申请申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAppSimpleSearchHQLAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = " and (app.status >= 1 or app.createMode=1 or app.createMode=2) ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = " and (app.status >= 2 or app.createMode=1 or app.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = " and (app.status >= 3 or app.createMode=1 or app.createMode=2) ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = " and (app.status >= 4 or app.createMode=1 or app.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = " and (app.status >= 5 or app.createMode=1 or app.createMode=2) ";
		}
		return hql;
	}
	
	//项目申请评审相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目申请评审的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请评审的条件语句
	 */
	public String getAppRevSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1){// 按项目名称查询
			hql = " and LOWER(app.name) like :keyword";
		} else if (searchType == 2) {// 按依托高校查询
			hql = " and LOWER(app.agencyName) like :keyword";
		} else if (searchType == 3){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 4){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		} else if (searchType == 5){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and ( LOWER(app.name) like :keyword " +
					"or LOWER(app.agencyName) like :keyword " +
					"or LOWER(so.name) like :keyword or LOWER(app.disciplineType) like :keyword " +
					"or cast(app.year as string) like :keyword)";
		}
		return hql;
	}
	
	//项目立项相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目立项的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目立项的条件语句
	 */
	public String getGrantedSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1) {// 按批准号查询
			hql = " and LOWER(gra.number) like :keyword";
		} else if (searchType == 2){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 3) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword";
		} else if (searchType == 4) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 5){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 6){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		} else if (searchType == 7){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and (LOWER(gra.number) like :keyword or LOWER(gra.name) like :keyword " +
					"or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword " +
					"or LOWER(so.name) like :keyword or LOWER(app.disciplineType) like :keyword " +
					"or cast(app.year as string) like :keyword )";
		}
		return hql;
	}
	
	
	//项目拨款相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目拨款的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目立项的条件语句
	 */
	public String getFeeSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1) {// 按批准号查询
			hql = " and LOWER(gra.number) like :keyword";
		} else if (searchType == 2){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 3) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword";
		} else if (searchType == 4) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 5){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 6){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
//		} else if (searchType == 7){//按拨款类型查询
//			hql = " and LOWER(pf.type) like :keyword";
//		} else if (searchType == 8){//按拨款状态查询
//			hql = " and LOWER(pf.status) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and (LOWER(gra.number) like :keyword or LOWER(gra.name) like :keyword " +
			"or LOWER(gra.applicantName) like :keyword or LOWER(uni.name) like :keyword " +
			"or LOWER(so.name) like :keyword or cast(app.year as string) like :keyword)";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目拨款的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目立项的条件语句
	 */
	public String getFundListSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1) {// 按清单名称查询
			hql = " and LOWER(fl.name) like :keyword";
		} else if (searchType == 2){// 按经办人查询
			hql = " and LOWER(fl.attn) like :keyword";
		} else if (searchType == 3) {// 按清单状态查询
			hql = " and LOWER(fl.status) like :keyword";
		} else if (searchType == 4) {// 按清单备注查询
			hql = " and LOWER(fl.note) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and (LOWER(fl.name) like :keyword or LOWER(fl.attn) like :keyword " +
			"or LOWER(fl.status) like :keyword or LOWER(fl.note) like :keyword) " ;
		}
		return hql;
	}
	
	//项目年检相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目年检申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 2) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword ";
		} else if (searchType == 3) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 4){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 5){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		} else if (searchType == 6){//按项目年度查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and ( LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword " +
					"or LOWER(uni.name) like :keyword or LOWER(so.name) like :keyword " +
					" or LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword )";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级和高级检索项目年检申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", ann.applicantSubmitStatus, ann.applicantSubmitDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", ann.deptInstAuditStatus, ann.deptInstAuditResult, ann.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", ann.universityAuditStatus, ann.universityAuditResult, ann.universityAuditDate ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", ann.provinceAuditStatus, ann.provinceAuditResult, ann.provinceAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = ", ann.finalAuditDate, ann.finalAuditResult ";
		}
		else if(accountType.equals(AccountType.ADMINISTRATOR)){
			hql = ", ann.finalAuditDate, ann.finalAuditResult ";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目年检申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getAnnSimpleSearchHQLAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = " and (ann.status >= 1 or ann.createMode=1 or ann.createMode=2) ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = " and (ann.status >= 2 or ann.createMode=1 or ann.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = " and (ann.status >= 3 or ann.createMode=1 or ann.createMode=2) ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = " and (ann.status >= 4 or ann.createMode=1 or ann.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = " and (ann.status >= 5 or ann.createMode=1 or ann.createMode=2) ";
		}
		return hql;
	}
	
	//项目中检相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目中检申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 2) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword ";
		} else if (searchType == 3) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 4){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 5){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		} else if (searchType == 6){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and ( LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword " +
					"or LOWER(uni.name) like :keyword or LOWER(so.name) like :keyword " +
					" or LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword)";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级和高级检索项目中检申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", midi.applicantSubmitStatus, midi.applicantSubmitDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", midi.deptInstAuditStatus, midi.deptInstAuditResult, midi.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", midi.universityAuditStatus, midi.universityAuditResult, midi.universityAuditDate ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", midi.provinceAuditStatus, midi.provinceAuditResult, midi.provinceAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = ", midi.finalAuditDate, midi.finalAuditResult ";
		}
		else if(accountType.equals(AccountType.ADMINISTRATOR)){
			hql = ", midi.finalAuditDate, midi.finalAuditResult ";
		}
		return hql;
	}
	/**
	 * 获得当前登陆者的初级检索项目中检申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getMidSimpleSearchHQLAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = " and (midi.status >= 1 or midi.createMode=1 or midi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = " and (midi.status >= 2 or midi.createMode=1 or midi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = " and (midi.status >= 3 or midi.createMode=1 or midi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = " and (midi.status >= 4 or midi.createMode=1 or midi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = " and (midi.status >= 5 or midi.createMode=1 or midi.createMode=2) ";
		}
		return hql;
	}
	
	//项目结项相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目结项申请的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 2) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword ";
		} else if (searchType == 3) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 4){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 5){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and ( LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword " +
					"or LOWER(uni.name) like :keyword or LOWER(so.name) like :keyword " +
					"or cast(app.year as string) like :keyword)";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目结项申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", endi.applicantSubmitStatus, endi.applicantSubmitDate, endi.finalAuditDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", endi.deptInstAuditStatus, endi.deptInstResultEnd, endi.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", endi.universityAuditStatus, endi.universityResultEnd, endi.universityAuditDate,  endi.reviewStatus, endi.reviewResult ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", endi.provinceAuditStatus, endi.provinceResultEnd, endi.provinceAuditDate,  endi.reviewStatus, endi.reviewResult ";
		}
		else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)) {
			hql = ", endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate ";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的高级检索项目结项申请的补充字段查询语句
	 * @param accountType 账号类型
	 * @return 高级检索项目申请的条件语句
	 */
	public String getEndAdvSearchHQLWordAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = ", endi.applicantSubmitStatus, endi.applicantSubmitDate ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = ", endi.deptInstAuditStatus, endi.deptInstResultEnd, endi.deptInstAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = ", endi.universityAuditStatus, endi.universityResultEnd, endi.universityAuditDate ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = ", endi.provinceAuditStatus, endi.provinceResultEnd, endi.provinceAuditDate ";
		}
		else if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.ADMINISTRATOR)) {
			hql = ", endi.ministryAuditStatus, endi.ministryResultEnd, endi.reviewStatus, endi.reviewResult, endi.finalAuditDate ";
		}
		return hql;
	}
	
	/**
	 * 获得当前登陆者的初级检索项目结项申请的补充条件查询语句
	 * @param accountType 账号类型
	 * @return 初级检索项目申请的条件语句
	 */
	public String getEndSimpleSearchHQLAdd(AccountType accountType){
		String hql = "";
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql = " and (endi.status >= 1 or endi.createMode=1 or endi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) {
			hql = " and (endi.status >= 2 or endi.createMode=1 or endi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql = " and (endi.status >= 3 or endi.createMode=1 or endi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.PROVINCE)) {
			hql = " and (endi.status >= 4 or endi.createMode=1 or endi.createMode=2) ";
		}
		else if(accountType.equals(AccountType.MINISTRY)) {
			hql = " and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) ";
		}
		return hql;
	}
	
	//项目结项评审相关查询语句
	/**
	 * 获得当前登陆者的初级检索项目结项评审的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索项目结项评审的条件语句
	 */
	public String getEndRevSimpleSearchHQL(int searchType){
		String hql = "";
		if (searchType == 1){// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword";
		} else if (searchType == 2) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword";
		} else if (searchType == 3){//按项目类别查询
			hql = " and LOWER(so.name) like :keyword";
		} else if (searchType == 4){//按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword";
		} else if (searchType == 5){//按年份查询
			hql = " and cast(app.year as string) like :keyword";
		} else {// 按上述所有字段查询
			hql = " and ( LOWER(gra.name) like :keyword " +
					"or LOWER(uni.name) like :keyword " +
					"or LOWER(so.name) like :keyword or LOWER(app.disciplineType) like :keyword " +
					"or cast(app.year as string) like :keyword)";
		}
		return hql;
	}
	
	//项目变更相关查询语句
	/**
	 * 获得当前登陆者的初级检索变更的条件语句
	 * @param searchType 检索条件
	 * @return 初级检索变更的条件语句
	 */
	public String getVarSimpleSearchHQL(int searchType){
		String hql;
		if (searchType == 1) {// 按项目名称查询
			hql = " and LOWER(gra.name) like :keyword ";
		} else if (searchType == 2) {// 按负责人查询
			hql = " and LOWER(gra.applicantName) like :keyword ";
		} else if (searchType == 3) {// 按依托高校查询
			hql = " and LOWER(uni.name) like :keyword ";
		} else if (searchType == 4) {// 按项目类别查询
			hql = " and LOWER(so.name) like :keyword ";
		} else if (searchType == 5) {// 按学科门类查询
			hql = " and LOWER(app.disciplineType) like :keyword ";
		} else if (searchType == 6) {// 按项目年份查询
			hql = " and cast(app.year as string) like :keyword ";
		} else {// 按上述所有字段查询
			hql = " and (LOWER(gra.name) like :keyword or LOWER(gra.applicantName) like :keyword " +
					"or LOWER(uni.name) like :keyword or LOWER(so.name) like :keyword " +
					"or LOWER(app.disciplineType) like :keyword or cast(app.year as string) like :keyword) ";
		}
		return hql;
	}
	//----------以下为获得项目对象或id----------
	/**
	 * 根据项目申请id获取项目申请
	 * @param appId 项目申请id
	 * @return 项目申请
	 */
	@SuppressWarnings("unchecked")
	public ProjectApplication getApplicationFetchDetailByAppId(String appId){
		if(appId == null){
			return null;
		}
		String hql = "select app from ProjectApplication app left join fetch app.researchType left join fetch app.subtype " +
				"left join fetch app.university uni left join fetch app.department dep left join fetch app.institute ins where app.id = ?";
		List<ProjectApplication> list = dao.query(hql, appId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据申请id获取当前申请评审
	 * @param entityId 申请id
	 * @return 评审对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectApplicationReview getCurrentApplicationReviewByAppId(String entityId){
		if(entityId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev from " + projectApplication.getApplicationReviewClassName() + " appRev where appRev.application.id = ? ";
		List list = dao.query(hql, entityId);
		if(!list.isEmpty()){
			return (ProjectApplicationReview)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据当前评审id获取当前申请
	 * @param appRevId 当前申请评审id
	 * @return 申请对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectApplication getCurrentApplicationByAppRevId(String appRevId){
		if(appRevId == null){
			return null;
		}
		ProjectApplicationReview applicationReview = (ProjectApplicationReview)dao.query(ProjectApplicationReview.class, appRevId);
		String hql = "select appRev.application from " + applicationReview.getApplicationReviewClassName() + " appRev where appRev.id=? ";
		List list = dao.query(hql, appRevId);
		if(!list.isEmpty()){
			return (ProjectApplication)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据项目申请id获取通过申请
	 * @param entityId 项目申请id
	 * @return 通过申请
	 */
	@SuppressWarnings("rawtypes")
	public List getPassApplicationByAppId(String entityId){
		if(entityId == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select app from " + projectApplication.getApplicationClassName() + " app where app.id=? and " +
				"app.finalAuditResult=2 and app.finalAuditStatus=3";
		List list = dao.query(hql, entityId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取项目申请id
	 * @param graId 项目立项id
	 * @return 项目申请id
	 */
	@SuppressWarnings("unchecked")
	public String getApplicationIdByGrantedId(String graId){
		if(graId == null){
			return "none";
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select gra.application.id from " + projectGranted.getGrantedClassName() + " gra where gra.id=? ";
		List<String> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 由项目申请Id获取对应项目对象
	 * @param appId项目申请Id
	 * @return 项目对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectGranted getGrantedByAppId(String appId){
		if(appId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "from " + projectApplication.getGrantedClassName() + " gra where gra.application.id=?";
		List list = dao.query(hql, appId);
		if(!list.isEmpty()){
			return (ProjectGranted)list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目立项id获取项目
	 * @param graId 项目立项id
	 * @return 项目
	 */
	@SuppressWarnings("rawtypes")
	public ProjectGranted getGrantedFetchDetailByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		try {
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "from " + projectGranted.getGrantedClassName() + " gra left join fetch gra.subtype left join fetch gra.university un left join fetch gra.department de left join fetch gra.institute ins where gra.id =?";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectGranted)list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目申请id获取项目id
	 * @param appId 项目申请id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByAppId(String appId){
		if(appId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "select gra.id from " + projectApplication.getGrantedClassName() + " gra where gra.application.id=?";
		List<String> list = dao.query(hql, appId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目立项id获取项目中检id
	 * @param graId 项目立项id
	 * @return 项目中检id
	 */
	@SuppressWarnings("unchecked")
	public String getMidinspectionIdByGraId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select mid.id from " + projectGranted.getMidinspectionClassName() + " mid where mid.granted.id=?";
		List<String> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目立项id获取项目变更id列表(按变更申请时间逆序排列)
	 * @param graId 项目立项id
	 * @return 项目变更id列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVariationIdByGraId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select var.id from " + projectGranted.getVariationClassName() + " var where var.granted.id=?";
		List<String> list = dao.query(hql, graId);
		if(!list.isEmpty())
			return list;
		else
			return null;
	}
	
	/**
	 * 根据项目立项id获取项目结项id
	 * @param graId 项目立项id
	 * @return 项目结项id
	 */
	@SuppressWarnings("unchecked")
	public String getEndinspectionIdByGraId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select pend.id from " + projectGranted.getEndinspectionClassName() + " pend where pend.granted.id=?";
		List<String> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目结项id获取项目id
	 * @param endId 项目结项id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByEndId(String endId){
		if(endId == null){
			return "none";
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endi.granted.id from " + projectEndinspection.getEndinspectionClassName() + " endi where endi.id=?";
		List<String> list = dao.query(hql, endId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 根据项目年检id获取项目id
	 * @param annId 项目年检id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByAnnId(String annId){
		if(annId == null){
			return null;
		}
		ProjectAnninspection projectAnninspection = (ProjectAnninspection)dao.query(ProjectAnninspection.class, annId);
		String hql = "select ann.granted.id from " + projectAnninspection.getAnninspectionClassName() + " ann where ann.id=?";
		List<String> list = dao.query(hql, annId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 根据项目中检id获取项目id
	 * @param midId 项目中检id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByMidId(String midId){
		if(midId == null){
			return null;
		}
		ProjectMidinspection projectMidinspection = (ProjectMidinspection)dao.query(ProjectMidinspection.class, midId);
		String hql = "select midi.granted.id from " + projectMidinspection.getMidinspectionClassName() + " midi where midi.id=?";
		List<String> list = dao.query(hql, midId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	
	/**
	 * 根据项目变更id获取项目id
	 * @param varId 项目变更id
	 * @return 项目id
	 */
	@SuppressWarnings("unchecked")
	public String getGrantedIdByVarId(String varId){
		if(varId == null){
			return null;
		}
		ProjectVariation projectVariation = (ProjectVariation)dao.query(ProjectVariation.class, varId);
		String hql = "select vari.granted.id from " + projectVariation.getVariationClassName() + " vari where vari.id=?";
		List<String> list = dao.query(hql, varId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return "none";
		}
	}
	//----------以下为获得结项对象或id，包括所有结项、所有可见范围内结项、待审结项、通过结项、当前结项、当前待审录入/导入结项----------

	/**
	 * 根据项目立项id获取所有可见范围内结项
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内结项
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectEndinspection> getAllEndinspectionByGrantedIdInScope(String graId, AccountType accountType){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		StringBuffer hql = new StringBuffer("select endi from ").append(projectGranted.getEndinspectionClassName()).append(" endi left outer join fetch endi.reviewGrade so where endi.granted.id = ?");
		//根据账号类型查询项目结项
		if(accountType.equals(AccountType.MINISTRY)){//部级
			hql.append(" and (endi.status>=5 or endi.createMode=1 or endi.createMode=2)");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and (endi.status>=4 or endi.createMode=1 or endi.createMode=2)");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append(" and (endi.status>=3 or endi.createMode=1 or endi.createMode=2)");
		}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系与研究机构
			hql.append(" and (endi.status>=2 or endi.createMode=1 or endi.createMode=2)");
		}
		hql.append(" order by endi.applicantSubmitDate desc");
		List<ProjectEndinspection> list = dao.query(hql.toString(), graId);
		return list;
	}
	/**
	 * 根据立项id及评审人id获得都对应的所有结项
	 * @param graId 项目立项id
	 * @param reviewerId 评审人id
	 * @return 所有可见范围内结项
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectEndinspection> getAllEndinspectionByGrantedIdAndReviewerId(String graId, String reviewerId){
		if(graId == null || reviewerId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		StringBuffer hql = new StringBuffer();
		hql.append("select endi from ").append(projectGranted.getEndinspectionClassName()).append(" endi left outer join fetch endi.reviewGrade so, ")
		.append(projectGranted.getEndinspectionReviewClassName()).append(" endRev where endRev.endinspection.id=endi.id and endi.granted.id=? ")
		.append("and endRev.reviewer.id=? order by endi.applicantSubmitDate desc");
		List<ProjectEndinspection> list = dao.query(hql.toString(), graId, reviewerId);
		return list;
	}
	/**
	 * 根据项目立项id获取所有结项id
	 * @param graId 项目立项id
	 * @return 所有结项id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getAllEndinspectionIdByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi.id from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? " +
				"order by endi.applicantSubmitDate desc ";
		List<String> list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取未审结项
	 * @param graId 项目立项id
	 * @return 未审结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingEndinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? and " +
				"endi.finalAuditStatus!=3";
		List list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取通过结项
	 * @param graId 项目立项id
	 * @return 通过结项
	 */
	@SuppressWarnings("rawtypes")
	public List getPassEndinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? and " +
				"endi.finalAuditResultEnd=2 and endi.finalAuditStatus=3";
		List list = dao.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目id获取当前录入的未审结项
	 * @param graId 项目立项id
	 * @return 当前录入的未审结项
	 */
	@SuppressWarnings("rawtypes")
	public ProjectEndinspection getCurrentPendingImpEndinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? and " +
				"(endi.createMode=1 or endi.createMode=2) and endi.finalAuditStatus!=3 order by endi.applicantSubmitDate desc";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectEndinspection)list.get(0);
		}
		return null;
	}
	/**
	 * 根据项目立项id获取当前结项
	 * @param graId 项目立项id
	 * @return 当前结项
	 */
	@SuppressWarnings("unchecked")
	public ProjectEndinspection getCurrentEndinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? " +
				"order by endi.applicantSubmitDate desc";
		List<ProjectEndinspection> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据结项id获取当前鉴定
	 * @param endId 结项id
	 * @return 鉴定对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectEndinspectionReview getCurrentEndinspectionReviewByEndId(String endId){
		if(endId == null){
			return null;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev where endRev.endinspection.id = ? ";
		List list = dao.query(hql, endId);
		if(!list.isEmpty()){
			return (ProjectEndinspectionReview)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据当前鉴定id获取当前结项
	 * @param endRevId 当前结项鉴定id
	 * @return 结项对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectEndinspection getCurrentEndinspectionByEndRevId(String endRevId){
		if(endRevId == null){
			return null;
		}
		ProjectEndinspectionReview endinspectionReview = (ProjectEndinspectionReview)dao.query(ProjectEndinspectionReview.class, endRevId);
		String hql = "select endRev.endinspection from " + endinspectionReview.getEndinspectionReviewClassName() + " endRev where endRev.id=? ";
		List list = dao.query(hql, endRevId);
		if(!list.isEmpty()){
			return (ProjectEndinspection)list.get(0);
		}
		return null;
	}
	
	//----------以下为获取结项相关信息----------
	/**
	 * 通过结项申请id获得结项研究数据信息
	 * @param endId 结项申请id
	 * @return 研究数据对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectData getProjectDataByEndId(String endId){
		if(endId == null){
			return null;
		}
		List<ProjectData> ress = dao.query("select res from ProjectData res where res.endinspection=?", endId);
		if(ress.size() > 0){
			return ress.get(0);
		}else{
			return null;
		}
	}
	//----------以下为获得年度进展年度对象或id，包括所有年检、所有可见范围内年检、待审年检、通过年检、当前年检、当前待审录入/导入年检----------
	/**
	 * 根据项目立项id获取所有可见范围内年检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内年检
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectAnninspection> getAllAnninspectionByGrantedIdInScope(String graId, AccountType accountType){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		StringBuffer hql = new StringBuffer("select ann from ").append(projectGranted.getAnninspectionClassName()).append(" ann where ann.granted.id = ?");
		//根据账号类型查询项目中检
		if(accountType.equals(AccountType.MINISTRY)){//部级
			hql.append(" and (ann.status>=5 or ann.createMode=1 or ann.createMode=2)");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and (ann.status>=4 or ann.createMode=1 or ann.createMode=2)");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append(" and (ann.status>=3 or ann.createMode=1 or ann.createMode=2)");
		}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系与研究机构
			hql.append(" and (ann.status>=2 or ann.createMode=1 or ann.createMode=2)");
		}
		hql.append(" order by ann.finalAuditStatus asc, ann.provinceAuditStatus asc, ann.universityAuditStatus asc, " +
				"ann.deptInstAuditStatus asc, ann.applicantSubmitStatus asc, ann.year desc");
		List<ProjectAnninspection> list = dao.query(hql.toString(), graId);
		return list;
	}
	/**
	 * 根据项目立项id获取未审年检
	 * @param graId 项目立项id
	 * @return 未审年检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingAnninspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select ann from " + projectGranted.getAnninspectionClassName() + " ann where ann.granted.id=? and " +
				"ann.finalAuditStatus!=3 ";
		List list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 判断当前年检是否审核
	 * @param graId 项目立项id
	 * @return true:审核，false：未审核
	 */
	public Boolean getPassAnninspectionByGrantedId(String graId){
		if(graId == null){
			return false;
		}
		ProjectAnninspection anninspection = this.getCurrentAnninspectionByGrantedId(graId);
		if(anninspection.getFinalAuditStatus() == 3){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据项目立项id获取当前录入的未审年检
	 * @param graId 项目立项id
	 * @return 当前录入的未审年检
	 */
	@SuppressWarnings("rawtypes")
	public ProjectAnninspection getCurrentPendingImpAnninspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select ann from " + projectGranted.getAnninspectionClassName() + " ann where ann.granted.id=? and " +
				"(ann.createMode=1 or ann.createMode=2) and ann.finalAuditStatus!=3 order by ann.applicantSubmitDate desc";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectAnninspection)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据项目立项id获取立项经费拨款信息
	 * @param graId 项目立项id
	 * @return 立项经费拨款
	 */
	public ProjectFunding getProjectFundingByGraId(String graId) {
		if(graId == null){
			return null;
		}
		String hql = "from ProjectFunding pf where pf.grantedId=? and pf.type='grantedfund' and pf.status=0 ";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectFunding)list.get(0);
		}
		return null;
		
	}
	
	/**
	 * 根据项目立项id获取当前年检
	 * @param graId 项目立项id
	 * @return 当前的年检
	 */
	@SuppressWarnings("unchecked")
	public ProjectAnninspection getCurrentAnninspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select ann from " + projectGranted.getAnninspectionClassName() + " ann where ann.granted.id=? " +
				"order by ann.applicantSubmitDate desc, ann.year desc";
		List<ProjectAnninspection> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据项目年检id获取年检经费
	 * @param annIds 项目年检id
	 * @return 年检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeAnnByAnnId(String grantedId, List<String> annIds){
		List<Map> projectFees = new ArrayList<Map>();
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		
		for(String annId : annIds) {
			Map feeMap = new HashMap();
			Double approveFee = 0.0;
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
			feeMap.put("approveFee", approveFee);
//			feeMap.put("fundedFee", this.getFundedFeeByGrantedId(grantedId));
//			feeMap.put("toFundFee", DoubleTool.sub(approveFee, this.getFundedFeeByGrantedId(grantedId)));
			ProjectAnninspection projectAnninspection = dao.query(ProjectAnninspection.class , annId);
			if (projectAnninspection.getProjectFee() != null) {
				ProjectFee projectFee = dao.query(ProjectFee.class, projectAnninspection.getProjectFee().getId());
				feeMap.put("feeFlag", 1);
				feeMap.put("bookFee", projectFee.getBookFee());
				feeMap.put("bookNote", projectFee.getBookNote());
				feeMap.put("dataFee", projectFee.getDataFee());
				feeMap.put("dataNote", projectFee.getDataNote());
				feeMap.put("travelFee", projectFee.getTravelFee());
				feeMap.put("travelNote", projectFee.getTravelNote());
				feeMap.put("conferenceFee", projectFee.getConferenceFee());
				feeMap.put("conferenceNote", projectFee.getConferenceNote());
				feeMap.put("internationalFee", projectFee.getInternationalFee());
				feeMap.put("internationalNote", projectFee.getInternationalNote());
				feeMap.put("deviceFee", projectFee.getDeviceFee());
				feeMap.put("deviceNote", projectFee.getDeviceNote());
				feeMap.put("consultationFee", projectFee.getConsultationFee());
				feeMap.put("consultationNote", projectFee.getConsultationNote());
				feeMap.put("laborFee", projectFee.getLaborFee());
				feeMap.put("laborNote", projectFee.getLaborNote());
				feeMap.put("printFee", projectFee.getPrintFee());
				feeMap.put("printNote", projectFee.getPrintNote());
				feeMap.put("indirectFee", projectFee.getIndirectFee());
				feeMap.put("indirectNote", projectFee.getIndirectNote());
				feeMap.put("otherFee", projectFee.getOtherFee());
				feeMap.put("otherNote", projectFee.getOtherNote());
				feeMap.put("totalFee", projectFee.getTotalFee());
				feeMap.put("feeNote", projectFee.getFeeNote());
				feeMap.put("fundedFee", projectFee.getFundedFee());
				feeMap.put("toFundFee", DoubleTool.sub(approveFee, projectFee.getFundedFee()));
				feeMap.put("surplusFee", DoubleTool.sub(projectFee.getFundedFee(), projectFee.getTotalFee()));
			}else {
				feeMap.put("feeFlag", 0);
			}
			projectFees.add(feeMap);
		}
		return projectFees;
	}
	
	/**
	 * 根据项目年检id获取中检经费
	 * @param midIds 项目中检id
	 * @return 中检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeMidByMidId(String grantedId, List<String> midIds){
		List<Map> projectFees = new ArrayList<Map>();
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		
		for(String midId : midIds) {
			Map feeMap = new HashMap();
			Double approveFee = 0.0;
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
			feeMap.put("approveFee", approveFee);
//			feeMap.put("fundedFee", this.getFundedFeeByGrantedId(grantedId));
//			feeMap.put("toFundFee", DoubleTool.sub(approveFee, this.getFundedFeeByGrantedId(grantedId)));
			ProjectMidinspection projectMidinspection = dao.query(ProjectMidinspection.class , midId);
			if (projectMidinspection.getProjectFee() != null) {
				ProjectFee projectFee = dao.query(ProjectFee.class, projectMidinspection.getProjectFee().getId());
				feeMap.put("feeFlag", 1);
				feeMap.put("bookFee", projectFee.getBookFee());
				feeMap.put("bookNote", projectFee.getBookNote());
				feeMap.put("dataFee", projectFee.getDataFee());
				feeMap.put("dataNote", projectFee.getDataNote());
				feeMap.put("travelFee", projectFee.getTravelFee());
				feeMap.put("travelNote", projectFee.getTravelNote());
				feeMap.put("conferenceFee", projectFee.getConferenceFee());
				feeMap.put("conferenceNote", projectFee.getConferenceNote());
				feeMap.put("internationalFee", projectFee.getInternationalFee());
				feeMap.put("internationalNote", projectFee.getInternationalNote());
				feeMap.put("deviceFee", projectFee.getDeviceFee());
				feeMap.put("deviceNote", projectFee.getDeviceNote());
				feeMap.put("consultationFee", projectFee.getConsultationFee());
				feeMap.put("consultationNote", projectFee.getConsultationNote());
				feeMap.put("laborFee", projectFee.getLaborFee());
				feeMap.put("laborNote", projectFee.getLaborNote());
				feeMap.put("printFee", projectFee.getPrintFee());
				feeMap.put("printNote", projectFee.getPrintNote());
				feeMap.put("indirectFee", projectFee.getIndirectFee());
				feeMap.put("indirectNote", projectFee.getIndirectNote());
				feeMap.put("otherFee", projectFee.getOtherFee());
				feeMap.put("otherNote", projectFee.getOtherNote());
				feeMap.put("totalFee", projectFee.getTotalFee());
				feeMap.put("feeNote", projectFee.getFeeNote());
				feeMap.put("fundedFee", projectFee.getFundedFee());
				feeMap.put("toFundFee", DoubleTool.sub(approveFee, projectFee.getFundedFee()));
				feeMap.put("surplusFee", DoubleTool.sub(projectFee.getFundedFee(), projectFee.getTotalFee()));
			}else {
				feeMap.put("feeFlag", 0);
			}
			projectFees.add(feeMap);
		}
		return projectFees;
	}
	
	
	/**
	 * 根据项目年检id获取中检经费
	 * @param midIds 项目中检id
	 * @return 中检经费
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProjectFeeEndByEndId(String grantedId, List<String> endIds){
		List<Map> projectFees = new ArrayList<Map>();
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		
		for(String endId : endIds) {
			Map feeMap = new HashMap();
			Double approveFee = 0.0;
			if (projectGranted.getApproveFee() != null) {
				approveFee = projectGranted.getApproveFee();
			}
			feeMap.put("approveFee", approveFee);
//			feeMap.put("fundedFee", this.getFundedFeeByGrantedId(grantedId));
//			feeMap.put("toFundFee", DoubleTool.sub(approveFee,this.getFundedFeeByGrantedId(grantedId)));
			ProjectEndinspection projectEndinspection = dao.query(ProjectEndinspection.class , endId);
			if (projectEndinspection.getProjectFee() != null) {
				ProjectFee projectFee = dao.query(ProjectFee.class, projectEndinspection.getProjectFee().getId());
				feeMap.put("feeFlag", 1);
				feeMap.put("bookFee", projectFee.getBookFee());
				feeMap.put("bookNote", projectFee.getBookNote());
				feeMap.put("dataFee", projectFee.getDataFee());
				feeMap.put("dataNote", projectFee.getDataNote());
				feeMap.put("travelFee", projectFee.getTravelFee());
				feeMap.put("travelNote", projectFee.getTravelNote());
				feeMap.put("conferenceFee", projectFee.getConferenceFee());
				feeMap.put("conferenceNote", projectFee.getConferenceNote());
				feeMap.put("internationalFee", projectFee.getInternationalFee());
				feeMap.put("internationalNote", projectFee.getInternationalNote());
				feeMap.put("deviceFee", projectFee.getDeviceFee());
				feeMap.put("deviceNote", projectFee.getDeviceNote());
				feeMap.put("consultationFee", projectFee.getConsultationFee());
				feeMap.put("consultationNote", projectFee.getConsultationNote());
				feeMap.put("laborFee", projectFee.getLaborFee());
				feeMap.put("laborNote", projectFee.getLaborNote());
				feeMap.put("printFee", projectFee.getPrintFee());
				feeMap.put("printNote", projectFee.getPrintNote());
				feeMap.put("indirectFee", projectFee.getIndirectFee());
				feeMap.put("indirectNote", projectFee.getIndirectNote());
				feeMap.put("otherFee", projectFee.getOtherFee());
				feeMap.put("otherNote", projectFee.getOtherNote());
				feeMap.put("totalFee", projectFee.getTotalFee());
				feeMap.put("feeNote", projectFee.getFeeNote());
				feeMap.put("fundedFee", projectFee.getFundedFee());
				feeMap.put("toFundFee", DoubleTool.sub(approveFee, projectFee.getFundedFee()));
				feeMap.put("surplusFee", DoubleTool.sub(projectFee.getFundedFee(), projectFee.getTotalFee()));
			}else {
				feeMap.put("feeFlag", 0);
			}
			projectFees.add(feeMap);
		}
		return projectFees;
	}
	
	//----------以下为获得中检对象或id，包括所有中检、所有可见范围内中检、待审中检、通过中检、当前中检、当前待审录入/导入中检----------
	/**
	 * 根据项目立项id获取所有可见范围内中检
	 * @param graId 项目立项id
	 * @param accountType 帐号类别
	 * @return 所有可见范围内中检
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectMidinspection> getAllMidinspectionByGrantedIdInScope(String graId, AccountType accountType){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		StringBuffer hql = new StringBuffer("select midi from ").append(projectGranted.getMidinspectionClassName()).append(" midi where midi.granted.id = ?");
		//根据账号类型查询项目中检
		if(accountType.equals(AccountType.MINISTRY)){//部级
			hql.append(" and (midi.status>=5 or midi.createMode=1 or midi.createMode=2)");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and (midi.status>=4 or midi.createMode=1 or midi.createMode=2)");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append(" and (midi.status>=3 or midi.createMode=1 or midi.createMode=2)");
		}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系与研究机构
			hql.append(" and (midi.status>=2 or midi.createMode=1 or midi.createMode=2)");
		}
		hql.append(" order by midi.applicantSubmitDate desc");
		List<ProjectMidinspection> list = dao.query(hql.toString(), graId);
		return list;
	}
	/**
	 * 根据项目立项id获取所有中检id
	 * @param graId 项目立项id
	 * @return 所有中检id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getAllMidinspectionIdByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select midi.id from " + projectGranted.getMidinspectionClassName() + " midi where midi.granted.id=? " +
				"order by midi.applicantSubmitDate desc ";
		List<String> list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取未审中检
	 * @param graId 项目立项id
	 * @return 未审中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingMidinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select midi from " + projectGranted.getMidinspectionClassName() + " midi where midi.granted.id=? and " +
				"midi.finalAuditStatus!=3  ";
		List list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取通过中检
	 * @param graId 项目立项id
	 * @return 通过中检
	 */
	@SuppressWarnings("rawtypes")
	public List getPassMidinspectionByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select midi from " + projectGranted.getMidinspectionClassName() + " midi where midi.granted.id=? and " +
				"midi.finalAuditResult=2 and midi.finalAuditStatus=3";
		List list = dao.query(hql, graId);
		return list;
	}
	/**
	 * 根据项目立项id获取当前录入的未审中检
	 * @param graId 项目立项id
	 * @return 当前录入的未审中检
	 */
	@SuppressWarnings("rawtypes")
	public ProjectMidinspection getCurrentPendingImpMidinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select midi from " + projectGranted.getMidinspectionClassName() + " midi where midi.granted.id=? and " +
				"(midi.createMode=1 or midi.createMode=2) and midi.finalAuditStatus!=3 order by midi.applicantSubmitDate desc";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectMidinspection)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据项目立项id获取当前中检
	 * @param graId 项目立项id
	 * @return 当前的中检
	 */
	@SuppressWarnings("unchecked")
	public ProjectMidinspection getCurrentMidinspectionByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select midi from " + projectGranted.getMidinspectionClassName() + " midi where midi.granted.id=? " +
				"order by midi.applicantSubmitDate desc";
		List<ProjectMidinspection> list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	//----------以下为获得变更对象或id，包括所有变更、所有可见范围内变更、待审变更----------
	
	/**
	 * 根据项目立项id以及当前登陆者帐号类别获取登陆者管辖范围内的所有变更列表
	 * @param graId 项目立项id 
	 * @param accountType 帐号类别
	 * @return 登陆者管辖范围内的所有变更列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectVariation> getAllVariationByGrantedIdInScope(String graId, AccountType accountType){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		StringBuffer hql = new StringBuffer("select vari from ").append(projectGranted.getVariationClassName()).append(" vari where vari.granted.id = ?");
		//根据账号类型查询项目变更
		if(accountType.equals(AccountType.MINISTRY)){//部级
			hql.append(" and ((vari.status >= 2 and vari.universityAuditResult!=1  and vari.provinceAuditResult!=1)  or vari.createMode = 1 or vari.createMode = 2)");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and (vari.status >= 4 or vari.createMode = 1 or vari.createMode = 2)");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append(" and (vari.status >= 3 or vari.createMode = 1 or vari.createMode = 2)");
		}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系与研究机构
			hql.append(" and (vari.status >= 2 or vari.createMode = 1 or vari.createMode = 2)");
		}
		hql.append(" order by vari.applicantSubmitDate desc");
		List<ProjectVariation> varList = dao.query(hql.toString(), graId);
		return varList;
	}
	/**
	 * 根据项目立项id获取所有变更id
	 * @param graId 项目立项id
	 * @return 所有变更id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getAllVariationIdByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select vari.id from " + projectGranted.getVariationClassName() + " vari where vari.granted.id=? " +
				"order by vari.applicantSubmitDate desc ";
		List<String> list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取未审变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingVariationByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select vari from " + projectGranted.getVariationClassName() + " vari where vari.granted.id=? and " +
				"vari.finalAuditStatus!=3 ";
		List list = dao.query(hql, graId);

		return list;
	}
	
	/**
	 * 根据项目立项id获取已审并同意的变更
	 * @param graId 项目立项id
	 * @return 未审变更
	 */
	@SuppressWarnings("rawtypes")
	public List getAuditedVariationByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select vari from " + projectGranted.getVariationClassName() + " vari where vari.granted.id=? and " +
		"vari.finalAuditStatus=3 and vari.finalAuditResult=2";
		List list = dao.query(hql, graId);
		return list;
	}
	
	/**
	 * 根据项目立项id获取当前变更
	 * @param graId 项目立项id
	 * @return 当前变更
	 */
	@SuppressWarnings("rawtypes")
	public ProjectVariation getCurrentVariationByGrantedId(String graId){
		if(graId == null){
			return null;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select vari from " + projectGranted.getVariationClassName() + " vari where vari.granted.id=? " +
				"order by vari.applicantSubmitDate desc";
		List list = dao.query(hql, graId);
		if(!list.isEmpty()){
			return (ProjectVariation)list.get(0);
		}
		return null;
	}
	//----------以下为变更列表查询处理----------

	/**
	 * 获得当前登陆者的检索变更的查询语句
	 * @param HQL1  查询语句选择部分
	 * @param HQL2  查询语句条件部分
	 * @param accountType 帐号类别
	 * @return 检索变更的查询语句
	 */
	public StringBuffer getVarHql(String HQL1, String HQL2, AccountType accountType){
		StringBuffer hql = new StringBuffer();
		hql.append(HQL1);
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			hql.append(", vari.applicantSubmitStatus, vari.applicantSubmitDate ").append(HQL2);
			hql.append(" and mem.member.id = :belongId and mem.groupNumber = gra.memberGroupNumber and (vari.status >= 1 or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.INSTITUTE)) {
			hql.append(", vari.deptInstAuditStatus, vari.deptInstAuditResult, vari.deptInstAuditDate ").append(HQL2);
			hql.append(" and gra.institute.id = :belongId and (vari.status >= 2 or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.DEPARTMENT)){
			hql.append(", vari.deptInstAuditStatus, vari.deptInstAuditResult, vari.deptInstAuditDate ").append(HQL2);
			hql.append(" and gra.department.id = :belongId and (vari.status >= 2 or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
			hql.append(", vari.universityAuditStatus, vari.universityAuditResult, vari.universityAuditDate ").append(HQL2);
			hql.append(" and uni.id = :belongId and (vari.status >= 3 or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.PROVINCE)) {
			hql.append(", vari.provinceAuditStatus, vari.provinceAuditResult, vari.provinceAuditDate ").append(HQL2);
			hql.append(" and uni.type = 4 and uni.subjection.id = :belongId and (vari.status >= 4 or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.MINISTRY)) {
			hql.append(", vari.finalAuditDate ").append(HQL2);
//			hql.append(" and (vari.status >= 5 or vari.createMode=1 or vari.createMode=2) ");
			hql.append(" and ((vari.status >= 2 and vari.universityAuditResult!=1  and vari.provinceAuditResult!=1 ) or vari.createMode=1 or vari.createMode=2) ");
		}else if(accountType.equals(AccountType.ADMINISTRATOR)){
			hql.append(", vari.finalAuditDate ").append(HQL2);
		}else{
			hql.append(", vari.finalAuditDate ").append(HQL2).append(" and 1=0 ");
		}
		return hql;
	}
	
	//----------以下为获取变更相关信息----------
	/**
	 * 获得变更次数
	 * @param graId 项目立项id
	 * @return 系统已有的变更次数
	 */
	@SuppressWarnings("rawtypes")
	public int getVarTimes(String graId){
		if(graId == null){
			return 0;
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select vari from " + projectGranted.getVariationClassName() + " vari where vari.granted.id=?";
		List gvs = dao.query(hql, graId);
		return gvs.size();
	}
	/***************************以下方法非项目模块负责人，请勿修改******************************/
	/**
	 * 根据变更对象获得可以同意的变更事项(非项目模块负责人，不允许修改)
	 * @param variation 项目变更对象
	 * @return 可以同意的变更事项
	 */
	public String getVarCanApproveItem(ProjectVariation variation){
		String resultItem = "";
		if(variation != null){
			if(variation.getChangeMember() == 1){//变更项目成员（含负责人）
				resultItem += "01,";
			}
			if(variation.getChangeAgency() == 1){//变更管理机构
				resultItem += "02,";
			}
			if(variation.getChangeProductType() == 1){//变更成果形式
				resultItem += "03,";
			}
			if(variation.getChangeName() == 1){//变更项目名称
				resultItem += "04,";
			}
			if(variation.getChangeContent() == 1){//研究内容有重大调整
				resultItem += "05,";
			}
			if(variation.getPostponement() == 1){//延期
				resultItem += "06,";
			}
			if(variation.getStop() == 1){//自行中止项目
				resultItem += "07,";
			}
			if(variation.getWithdraw() == 1){//申请撤项
				resultItem += "08,";
			}
			if(variation.getChangeFee() == 1){//申请撤项
				resultItem += "09,";
			}
			if(variation.getOther() == 1){//其他
				resultItem += "20,";
			}
			if(resultItem.length() > 0){
				resultItem = resultItem.substring(0, resultItem.length() - 1);
			}
		}
		return resultItem;
	}
	
	/**
	 * 通过审核结果详情编码获得可以同意的变更事项字串(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param auditResultDetail	审核结果详情编码	长度为9，九位字符依次是：变更项目成员（含负责人）(0)、变更机构(1)、变更成果形式(2)、变更项目名称(3)、研究内容有重大调整(4)、延期(5)、自行终止项目(6)、申请撤项(7)、其他(19)这十类变更结果的标志位。	'1'表示同意 '0'表示不同意
	 * @return	可以同意的变更字串
	 */
	public String getVarCanApproveItem(String auditResultDetail){
		String resultItem = "";
		if(auditResultDetail != null && auditResultDetail.trim().length() >= 20){
			if(auditResultDetail.charAt(0) == '1'){//第1位变更项目成员（含负责人）结果
				resultItem += "01,";
			}
			if(auditResultDetail.charAt(1) == '1'){//第2位变更管理机构结果
				resultItem += "02,";
			}
			if(auditResultDetail.charAt(2) == '1'){//第3位变更成果形式结果
				resultItem += "03,";
			}
			if(auditResultDetail.charAt(3) == '1'){//第4位变更项目名称结果
				resultItem += "04,";
			}
			if(auditResultDetail.charAt(4) == '1'){//第5位研究内容有重大调整结果
				resultItem += "05,";
			}
			if(auditResultDetail.charAt(5) == '1'){//第6位延期
				resultItem += "06,";
			}
			if(auditResultDetail.charAt(6) == '1'){//第7位自行中止项目结果
				resultItem += "07,";
			}
			if(auditResultDetail.charAt(7) == '1'){//第8位申请撤项结果
				resultItem += "08,";
			}
			if(auditResultDetail.charAt(8) == '1'){//第9位申请经费变更
				resultItem += "09,";
			}
			if(auditResultDetail.charAt(19) == '1'){//第20位其他结果
				resultItem += "20,";
			}
			if(resultItem.length() > 0){
				resultItem = resultItem.substring(0, resultItem.length() - 1);
			}
		}
		return resultItem;
	}
	
	/**
	 * 通过同意变更事项的字串拼接成同意变更事项的编码(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varSelectApproveIssue	同意变更事项,多个以,隔开
	 * @return 同意变更事项的编码
	 */
	public String getVarApproveItem(String varSelectApproveIssue){
		if(varSelectApproveIssue != null && varSelectApproveIssue.length() > 0){
			String approveIssue = "";
			if(varSelectApproveIssue.contains("01")){//第1位变更项目成员（含负责人）结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("02")){//第2位变更管理机构结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("03")){//第3位变更成果形式结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("04")){//第4位变更项目名称结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("05")){//第5位研究内容有重大调整结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("06")){//第6位延期结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("07")){//第7位自行中止项目结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("08")){//第8位申请撤项结果
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("09")){//第9位申请经费变更
				approveIssue= approveIssue + "1";
			}else{
				approveIssue= approveIssue + "0";
			}
			if(varSelectApproveIssue.contains("20")){//第20位其他结果
				for (int i = 0; i < 10; i++) {
					approveIssue= approveIssue + "0";
				}
				approveIssue= approveIssue + "1";
			}else{
				for (int i = 0; i < 11; i++) {
					approveIssue= approveIssue + "0";
				}
			}
			return approveIssue;
			
		}else{
			return null;
		}
	}
	
	/**
	 * 根据同意变更事项详情编码获得同意变更事项的名称(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varApproveDetail 同意变更事项详情编码
	 * @return	同意变更事项的名称
	 */
	public String getVarApproveName(String varApproveDetail){
		if(varApproveDetail != null && varApproveDetail.trim().length() >= 20){
			String approveName = "";
			if(varApproveDetail.charAt(0) == '1'){//第1位变更项目成员（含负责人）结果
				approveName += "变更项目成员（含负责人）、";
			}
			if(varApproveDetail.charAt(1) == '1'){//第2位变更管理机构结果
				approveName += "变更管理机构、";
			}
			if(varApproveDetail.charAt(2) == '1'){//第3位变更成果形式结果
				approveName += "变更成果形式、";
			}
			if(varApproveDetail.charAt(3) == '1'){//第4位变更项目名称结果
				approveName += "变更项目名称、";
			}
			if(varApproveDetail.charAt(4) == '1'){//第5位研究内容有重大调整结果
				approveName += "研究内容有重大调整、";
			}
			if(varApproveDetail.charAt(5) == '1'){//第6位延期
				approveName += "延期、";
			}
			if(varApproveDetail.charAt(6) == '1'){//第7位自行中止项目结果
				approveName += "自行中止项目、";
			}
			if(varApproveDetail.charAt(7) == '1'){//第8位申请撤项结果
				approveName += "撤项、";
			}
			if(varApproveDetail.charAt(19) == '1'){//第20位其他结果
				approveName += "其他、";
			}
			if(approveName.length() > 0){
				approveName = approveName.substring(0, approveName.length() - 1);
			}
			approveName = "（同意" + approveName + "）";
			return approveName;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 用于变更导出
	 * 根据同意变更事项详情编码获得同意变更事项的名称(非项目模块负责人，请勿修改。要改则只允许扩展代码、不允许修改已有代码)
	 * @param varApproveDetail 同意变更事项详情编码
	 * @return	同意变更事项的名称
	 */
	public String getVarApproveNameForExport(String varApproveDetail){
		if(varApproveDetail != null && varApproveDetail.trim().length() >= 20){
			String approveName = "";
			if(varApproveDetail.charAt(0) == '1'){//第1位变更项目成员（含负责人）结果
				approveName += "变更项目成员（含负责人）; ";
			}
			if(varApproveDetail.charAt(1) == '1'){//第2位变更管理机构结果
				approveName += "变更项目管理机构; ";
			}
			if(varApproveDetail.charAt(2) == '1'){//第3位变更成果形式结果
				approveName += "变更成果形式; ";
			}
			if(varApproveDetail.charAt(3) == '1'){//第4位变更项目名称结果
				approveName += "变更项目名称; ";
			}
			if(varApproveDetail.charAt(4) == '1'){//第5位研究内容有重大调整结果
				approveName += "研究内容有重大调整; ";
			}
			if(varApproveDetail.charAt(5) == '1'){//第6位延期
				approveName += "延期; ";
			}
			if(varApproveDetail.charAt(6) == '1'){//第7位自行中止项目结果
				approveName += "自行中止项目; ";
			}
			if(varApproveDetail.charAt(7) == '1'){//第8位申请撤项结果
				approveName += "申请撤项; ";
			}
			if(varApproveDetail.charAt(19) == '1'){//第20位其他结果
				approveName += "其他; ";
			}
			if(approveName.length() > 0){
				approveName = approveName.substring(0, approveName.length() - 2);
				approveName = "（" + approveName + "）";
			}
			return approveName;
		}else{
			return null;
		}
	}
	
	/**
	 * 获得可以同意的变更事项List
	 * @param varItemString 变更事项id，多个以,隔开
	 * @return list[code][name]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getVarItemList(String varItemString){
		List list = new ArrayList();
		if(varItemString != null && varItemString.trim().length() > 0){
			Map map = new HashMap();
			StringBuffer hql = new StringBuffer();
			hql.append("select s.code, s.name from SystemOption s where s.standard ='variation' and s.systemOption.id is not null and s.isAvailable = 1");
			String[] codes = varItemString.split(",");
			int len = codes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("code" + i, codes[i]);
					hql.append("s.code =:code" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
			hql.append(" order by s.code asc");
			list = dao.query(hql.toString(), map);
		}
		return list;
	}
	/****************************end****************************/
	/**
	 * 获得变更列表中变更对象的所有相关id
	 * @param varList 变更列表
	 * @return 变更列表的所有相关id列表 ，每条记录为Object[12]，其中0、1:变更前、后责任人id  	2、3、4、5、6、7:变更前、后学校、院系、基地id 		8,9为变更前后负责人的姓名            10,11为变更前后经费ID
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllVariationRelIdNames(List<ProjectVariation> varList){
		List varRelIds = new ArrayList();
		if(varList != null && varList.size() > 0){
			for(int i=0; i<varList.size(); i++){
				ProjectVariation variation = varList.get(i);			
				Object[] varId = new Object[12];
				if(variation.getChangeMember() == 1){
					String appId = this.getApplicationIdByGrantedId(this.getGrantedIdByVarId(variation.getId()));
					if(variation.getOldMemberGroupNumber() != null){
						List<ProjectMember> oldMems = this.getMemberFetchUnit(appId, variation.getOldMemberGroupNumber());
						if(oldMems != null && ! oldMems.isEmpty()){
							String oldId = "";
							String oldName = "";
							for(int j = 0; j < oldMems.size(); j++){
								ProjectMember member = oldMems.get(j);
								oldId = oldId + member.getId() + "; ";
								oldName = oldName + member.getMemberName() + "; ";
							}
							oldId = oldId.substring(0, oldId.lastIndexOf("; "));
							oldName = oldName.substring(0, oldName.lastIndexOf("; "));
							varId[0] = oldId;
							varId[8] = oldName;
						}
					}
					if(variation.getNewMemberGroupNumber() != null){
						List<ProjectMember> newMems = this.getMemberFetchUnit(appId, variation.getNewMemberGroupNumber());
						if(newMems != null && ! newMems.isEmpty()){
							String newId = "";
							String newName = "";
							for(int j = 0; j < newMems.size(); j++){
								ProjectMember member = newMems.get(j);
								newId = newId + member.getId() + "; ";
								newName = newName + member.getMemberName() + "; ";
							}
							newId = newId.substring(0, newId.lastIndexOf("; "));
							newName = newName.substring(0, newName.lastIndexOf("; "));
							varId[1] = newId;
							varId[9] = newName;
						}
					}
				}
				if(variation.getChangeAgency() == 1){
					if(variation.getOldAgency() != null)
						varId[2] = variation.getOldAgency().getId();
					if(variation.getOldDepartment() != null)
						varId[3] = variation.getOldDepartment().getId();
					if(variation.getOldInstitute() != null){
						varId[4] = variation.getOldInstitute().getId();
					}
					if(variation.getNewAgency() != null)
						varId[5] = variation.getNewAgency().getId();
					if(variation.getNewDepartment() != null)
						varId[6] = variation.getNewDepartment().getId();
					if(variation.getNewInstitute() != null){
						varId[7] = variation.getNewInstitute().getId();
					}
				}
				if (variation.getChangeFee() == 1) {
					if (variation.getOldProjectFee() != null) {
						varId[10] = variation.getOldProjectFee().getId();
					}
					if (variation.getNewProjectFee() != null) {
						varId[11] = variation.getNewProjectFee().getId();
					}
				}
				varRelIds.add(i,varId);
			}
		}
		return varRelIds;
	}
	//----------以下为设置变更相关信息----------
	/**
	 * 保存变更机构时的相关信息
	 * @param variation 项目变更对象
	 * @param graId 立项id
	 * @param deptInstFlag 院系或研究机构标志位	1：研究机构	2:院系
	 * @param deptInstId 院系或研究机构id
	 * @return
	 */
	public ProjectVariation setVariationAgencyInfo(ProjectVariation variation, String graId, int deptInstFlag, String deptInstId){
		Object[] preVar = (Object[])dao.query("select uni.id,uni.name,dept.id,inst.id, " +
				"gra.divisionName from ProjectGranted gra left outer join gra.university uni " +
				"left outer join gra.department dept left outer join gra.institute inst " +
				"where gra.id=?", graId).get(0);
		variation.setChangeAgency(1);
		if(preVar[0]!=null)		variation.setOldAgency((Agency)dao.query(Agency.class,preVar[0].toString()));
		if(preVar[1]!=null)		variation.setOldAgencyName(preVar[1].toString());
		if(preVar[2]!=null)		variation.setOldDepartment((Department)dao.query(Department.class,preVar[2].toString()));
		if(preVar[3]!=null)		variation.setOldInstitute((Institute)dao.query(Institute.class,preVar[3].toString()));
		if(preVar[4]!=null)		variation.setOldDivisionName(preVar[4].toString());
		Object[] idnames = null;
		if(deptInstFlag == 2){//院系
			idnames = (Object[])dao.query("select un.id,un.name,dept.name from " +
					"Department dept, Agency un where dept.university.id=un.id and " +
					"dept.id=?", deptInstId).get(0);
			variation.setNewDepartment((Department)dao.query(Department.class, deptInstId));
			variation.setNewInstitute(null);
		}else if(deptInstFlag == 1){//研究机构
			idnames = (Object[])dao.query("select un.id,un.name,inst.name from " +
					"Institute inst, Agency un where inst.subjection.id=un.id and " +
					"inst.id=?", deptInstId).get(0);
			variation.setNewDepartment(null);
			variation.setNewInstitute((Institute)dao.query(Institute.class, deptInstId));
		}
		if(idnames[0] != null)	variation.setNewAgency((Agency)dao.query(Agency.class,idnames[0].toString()));
		if(idnames[1] != null)	variation.setNewAgencyName(idnames[1].toString());
		if(idnames[2] != null)	variation.setNewDivisionName(idnames[2].toString());
		return variation;
	}

	/**
	 * 变更项目信息
	 * @param variation 变更对象
	 */
	@SuppressWarnings("unchecked")
	public void variationProject(ProjectVariation variation){
		ProjectGranted granted = (ProjectGranted) dao.query(ProjectGranted.class,this.getGrantedIdByVarId(variation.getId()));
		String appId = this.getApplicationIdByGrantedId(granted.getId());
		String approveDetail = variation.getFinalAuditResultDetail();
		if(approveDetail == null || approveDetail.trim().length() < 9){
			return;
		}
		if(variation.getChangeMember() == 1 && approveDetail.charAt(0) == '1'){//变更责任人
			List<ProjectMember> members = this.getDirectorList(appId, variation.getNewMemberGroupNumber());
			String ids = "";
			String names = "";
			for(int i = 0 ; i < members.size(); i++){
				ProjectMember member = members.get(i);
				ids = ids + member.getMember().getId() + "; ";
				names = names + member.getMemberName() + "; ";
			}
			granted.setApplicantId(ids.substring(0, ids.lastIndexOf("; ")));
			granted.setApplicantName(names.substring(0, names.lastIndexOf("; ")));
			granted.setMemberGroupNumber(variation.getNewMemberGroupNumber());
		}
		if(variation.getChangeAgency()==1 && approveDetail.charAt(1) == '1'){//变更机构
			Agency universityn = (Agency) dao.query(Agency.class,variation.getNewAgency().getId());
			granted.setUniversity(universityn);
			granted.setProvince(universityn.getProvince());
			granted.setProvinceName(universityn.getProvince().getName());
			granted.setAgencyName(variation.getNewAgencyName());
			granted.setDivisionName(variation.getNewDivisionName());
			if(variation.getNewDepartment()!=null){
				Department department = (Department)dao.query(Department.class,variation.getNewDepartment().getId());
				granted.setDepartment(department);
				granted.setInstitute(null);
				
			}else if(variation.getNewInstitute()!=null){
				Institute institute = (Institute)dao.query(Institute.class,variation.getNewInstitute().getId());
				granted.setInstitute(institute);
				granted.setDepartment(null);
			}
		}
		if(variation.getChangeProductType() == 1 && approveDetail.charAt(2) == '1'){//变更成果形式
			granted.setProductType(variation.getNewProductType());
			granted.setProductTypeOther(variation.getNewProductTypeOther());
		}
		if(variation.getChangeName() == 1 && approveDetail.charAt(3) == '1'){//变更项目名称
			granted.setName(variation.getNewName());
			granted.setEnglishName(variation.getNewEnglishName());
		}
		if(variation.getPostponement() == 1 && approveDetail.charAt(5) == '1'){//延期
			granted.setPlanEndDate(variation.getNewOnceDate());
			
		}
		if(variation.getStop() == 1 && approveDetail.charAt(6) == '1'){//自行中止项目
			granted.setStatus(3);//项目中止
			granted.setEndStopWithdrawDate(variation.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(variation.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(variation.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(variation.getFinalAuditOpinionFeedback());
		}
		if(variation.getWithdraw() == 1 && approveDetail.charAt(7) == '1'){//申请撤项
			granted.setStatus(4);//撤项
			granted.setEndStopWithdrawDate(variation.getFinalAuditDate());
			granted.setEndStopWithdrawPerson(variation.getFinalAuditorName());
			granted.setEndStopWithdrawOpinion(variation.getFinalAuditOpinion());
			granted.setEndStopWithdrawOpinionFeedback(variation.getFinalAuditOpinionFeedback());
		}
		if(variation.getChangeFee() == 1 && approveDetail.charAt(8) == '1'){//变更经费预算
			if (granted.getApproveFee() != variation.getNewProjectFee().getTotalFee()) {
				granted.setApproveFee(variation.getNewProjectFee().getTotalFee());
//				Map parmap = new HashMap();
//				parmap.put("grantedId", granted.getId());
//				ProjectFunding grantedFundingold = null,grantedFunding = null;
//				if (granted.getProjectType().equals("general")) {
//					grantedFundingold = (GeneralFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId ", parmap);
//					grantedFunding = new GeneralFunding();
//				}else if (granted.getProjectType().equals("instp")) {
//					grantedFundingold = (InstpFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId ", parmap);
//					grantedFunding = new InstpFunding();
//				}else if (granted.getProjectType().equals("key")) {
//					grantedFundingold = (KeyFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId ", parmap);
//					grantedFunding = new KeyFunding();
//				}else if (granted.getProjectType().equals("entrust")) {
//					grantedFundingold = (EntrustFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId ", parmap);
//					grantedFunding = new EntrustFunding();
//				}else if (granted.getProjectType().equals("post")) {
//					grantedFundingold = (PostFunding) dao.queryUnique("from ProjectFunding p where p.grantedId = :grantedId ", parmap);
//					grantedFunding = new PostFunding();
//				}
//				if (grantedFundingold != null) {
//					if (grantedFundingold.getStatus() == 0) {
//						//立项则添加立项拨款申请，金额默认为批准经费的50%
////						grantedFundingold.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
//						grantedFundingold.setGrantedId(granted.getId());
//						grantedFundingold.setProjectType(granted.getProjectType());
//						dao.modify(grantedFundingold);
//					}
//				}else {
//					//立项则添加立项拨款申请，金额默认为批准经费的50%
////					grantedFunding.setGrantedFundFee(DoubleTool.mul(granted.getApproveFee(),0.5));
////					grantedFunding.setGranted(granted);
//					grantedFunding.setGrantedId(granted.getId());
//					grantedFunding.setProjectType(granted.getProjectType());
//					grantedFunding.setStatus(0);
//					dao.add(grantedFunding);
//				}
				
			}
			granted.setProjectFee(variation.getNewProjectFee());
		}
		dao.modify(granted);
			
	}
	
	/**
	 * 根据项目申请id及项目成员组编号对项目成员对应的人员、机构进行入库处理
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doWithNewMember(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return;
		}
		List<ProjectMember> members = this.getMember(appId, groupNumber);
		for(int i = 0; i < members.size(); i++){
			ProjectMember member = members.get(i);
			Map map = new HashMap();
			map.put("idcardType", member.getIdcardType());
			map.put("idcardNumber", member.getIdcardNumber());
			map.put("personName", member.getMemberName());
			map.put("personType", member.getMemberType());
			map.put("gender", member.getGender());
			map.put("agencyName", member.getAgencyName());
			map.put("agencyId", (member.getUniversity() != null) ? member.getUniversity().getId() : null);
			map.put("divisionName", member.getDivisionName());
			map.put("divisionType", member.getDivisionType());
			map.put("workMonthPerYear", member.getWorkMonthPerYear());
			map.put("specialistTitle", member.getSpecialistTitle());
			map = this.doWithNewPerson(map);
			String personId = map.get("personId").toString();
			String divisionId = map.get("divisionId").toString();
			Person person = (Person)dao.query(Person.class, personId);
			member.setMember(person);
			if(member.getDivisionType() == 1){//研究基地
				Institute institute =(Institute)dao.query(Institute.class, divisionId);
				member.setInstitute(institute);
				member.setDepartment(null);
			}else if(member.getDivisionType() == 2){//院系
				Department department =(Department)dao.query(Department.class, divisionId);
				member.setInstitute(null);
				member.setDepartment(department);
			}
			dao.modify(member);
		}
		this.deleteMulMember(appId, groupNumber);
		this.refreshMemberSn(appId, groupNumber);
	}
	//----------以下为获取项目成员或负责人信息----------
	/**
	 * 根据项目申请id及项目成员组编号获取所有负责人id的list
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 所有负责人id的list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDireIdByAppId(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "select per.id from " + projectApplication.getMemberClassName() + " mem left outer join mem.member per " +
				"where mem.application.id=? and mem.isDirector=1 and mem.groupNumber=? order by mem.memberSn asc";
		List<String> list = dao.query(hql, appId, groupNumber);
		return list;
	}
	
	/**
	 * 根据项目申请id获取项目成员
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	@SuppressWarnings("rawtypes")
	public List getMemberListByAppId(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "select mem.id,mem.isDirector,m.id,mem.memberName,mem.specialistTitle,mem.major,uni.id,mem.agencyName,dep.id,ins.id,mem.divisionName," +
				"mem.workMonthPerYear,mem.workDivision";
		if("instp".equals(projectApplication.getType()) || "key".equals(projectApplication.getType())){
			hql = hql + ", mem.isSubprojectDirector";
		}
		hql = hql + " from " + projectApplication.getMemberClassName() + " mem left outer join mem.member m left outer join mem.university uni left outer join mem.department dep " +
				"left outer join mem.institute ins where mem.application.id=? and mem.groupNumber=? order by mem.memberSn asc";
		List<Object[]> list = dao.query(hql, appId, groupNumber);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i)[3]+":"+list.get(i)[4]+"     ");
//		}
//		System.out.println("");
		
		//将申请数据->相关成员->项目主要成员信息中职称前面的代号去掉，只保留职称，如：123/经济师 应为 经济师；
		for (int i = 0; i < list.size(); i++) {
			Object[] object = list.get(i);
			String specialistTitle = (String) (object[4]!=null?object[4]:"");
			if (specialistTitle!=""&&specialistTitle.indexOf("/")!=-1) {
				object[4] = specialistTitle.substring(specialistTitle.indexOf("/")+1);
				list.remove(i);
				list.add(i, object);
			}
		}
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i)[3]+":"+list.get(i)[4]+"     ");
//		}
//		System.out.println("");
		return list;
	}
	/**
	 * 根据项目立项id获取项目成员(不含负责人)
	 * @param graId 项目立项id
	 * @return  项目成员，多个以分号（即;或；）隔开
	 */
	@SuppressWarnings("rawtypes")
	public String getMembersStringByGraId(String graId){
		if(graId == null){
			return new String();
		}
		ProjectGranted projectGranted = dao.query(ProjectGranted.class, graId);
		if (projectGranted==null) {
			return new String();
		}
		ProjectApplication projectApplication = dao.query(ProjectApplication.class, projectGranted.getApplicationId());
		StringBuffer membersString = new StringBuffer();
		Integer groupNumber = projectGranted.getMemberGroupNumber();
		String hql = "select mem.id,mem.isDirector,m.id,mem.memberName,mem.specialistTitle,mem.major,uni.id,mem.agencyName,dep.id,ins.id,mem.divisionName," +
				"mem.workMonthPerYear,mem.workDivision";
		if("instp".equals(projectApplication.getType()) || "key".equals(projectApplication.getType())){
			hql = hql + ", mem.isSubprojectDirector";
		}
		hql = hql + " from " + projectApplication.getMemberClassName() + " mem left outer join mem.member m left outer join mem.university uni left outer join mem.department dep " +
				"left outer join mem.institute ins where mem.isDirector = 0 and mem.application.id=? and mem.groupNumber=? order by mem.memberSn asc";
		List<Object[]> list = dao.query(hql, projectGranted.getApplicationId(), groupNumber);
		for (int i = 0; i < list.size(); i++) {
			if (i!=0) {
				membersString.append(";");
			}
			membersString.append(list.get(i)[3]);
			
		}
		return membersString.toString();
	}
	/**
	 * 获得项目成员
	 * @param appId项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	@SuppressWarnings("rawtypes")
	public List getMemberFetchUnit(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql ="select mem from " + projectApplication.getMemberClassName() + " mem left join fetch mem.member left join fetch mem.university left join fetch mem.department left outer join fetch mem.institute " +
				"where mem.application.id = ? and mem.groupNumber =? order by mem.isDirector desc, mem.memberSn asc";
		List members = dao.query(hql, appId, groupNumber);
		return members;
	}
	
	/**
	 * 根据项目申请id及项目成员组编号获得项目成员
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目成员
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectMember> getMember(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql ="select mem from " + projectApplication.getMemberClassName() + " mem where mem.application.id = ? and mem.groupNumber =? order by mem.memberSn asc";
		List<ProjectMember> members = dao.query(hql, appId, groupNumber);
		return members;
	}
	
	/**
	 * 获得项目成员对象
	 * @param appId 项目申请id
	 * @param personId 人员主表id
	 * @return 项目成员对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectMember getMember(String appId, String personId){
		if(appId == null || personId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "select mem from " + projectApplication.getMemberClassName() + " mem where mem.member.id=? and mem.application.id=?";
		List members = dao.query(hql, personId, appId);
		return (members.size() > 0) ? (ProjectMember)members.get(0) : null;
	}
	/**
	 * 根据项目申请id获得项目的最大成员组编号
	 * @param appId 项目申请id
	 * @return 项目的最大成员组编号
	 */
	@SuppressWarnings("rawtypes")
	public int getMaxGroupNumber(String appId){
		if(appId == null){
			return 0;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String hql = "select mem.groupNumber from " + projectApplication.getMemberClassName() + " mem where mem.application.id=? order by mem.groupNumber desc";
		List groupNumbers = dao.query(hql, appId);
		return (groupNumbers.size() > 0) ? (Integer)groupNumbers.get(0) : 0;
	}
	
	/**
	 * 根据项目申请id及项目成员组编号获得项目的非负责人项目成员列表
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目的非负责人项目成员列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProjectMember> getNoDirMembers(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		Map paraMap = new HashMap();
		paraMap.put("appId", appId);
		paraMap.put("groupNumber", groupNumber);
		String hql ="select mem from " + projectApplication.getMemberClassName() + " mem where mem.application.id = ? and mem.isDirector !=1 and mem.groupNumber=? order by mem.memberSn asc";
		List<ProjectMember> members = dao.query(hql, appId, groupNumber);
		return members;
	}
	/**
	 * 根据项目申请id及项目成员组编号消除项目成员中的重复成员(非负责人变更为负责人)
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	@SuppressWarnings("unchecked")
	public void deleteMulMember(String appId, Integer groupNumber){
		List<ProjectMember> dirMembers = this.getDirectorList(appId, groupNumber);
		for(int i = 0; i < dirMembers.size(); i++){
			ProjectMember dirMember = dirMembers.get(i);
			String hql = "select mem from " + dirMember.getMemberClassName() + " mem where mem.isDirector !=1 and mem.member.id=? and mem.application.id=? and mem.groupNumber=?";
			List<ProjectMember> existMembers = dao.query(hql, dirMember.getMember().getId(), appId, groupNumber);
			//删除重复项目成员
			for (ProjectMember projectMember : existMembers) {
				dao.delete(projectMember);
			}
		}
	}

	/**
	 * 对项目成员重新编码
	 * @param appId	项目申请id
	 * @param groupNumber 项目成员组编号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void refreshMemberSn(String appId, Integer groupNumber){
		if(appId != null && groupNumber != null){
			ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
			Map map = new HashMap();
			map.put("appId", appId);
			map.put("groupNumber", groupNumber);
			StringBuffer hql = new StringBuffer();
			hql.append("select mem from " + projectApplication.getMemberClassName() + " mem where mem.application.id=:appId and mem.groupNumber=:groupNumber order by mem.isDirector desc, mem.memberSn asc");
			List members = null;
			try {
				members = dao.query(hql.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(members.size() > 0){
				for(int i = 0; i < members.size(); i++){
					ProjectMember member = (ProjectMember)members.get(i);
					member.setMemberSn(i + 1);
					dao.modify(member);
				}
			}
		}
	}
	
	/**
	 *根据项目申请id获得项目的所有负责人列表
	 * @param appId 项目申请id
	 * @param groupNumber 项目成员组编号
	 * @return 项目负责人列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDirectorList(String appId, Integer groupNumber){
		if(appId == null || groupNumber == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		Map paraMap = new HashMap();
		paraMap.put("appId", appId);
		paraMap.put("groupNumber", groupNumber);
		String hql ="select mem from " + projectApplication.getMemberClassName() + " mem left outer join fetch mem.member per where mem.application.id = ? and mem.isDirector=1 and mem.groupNumber=? order by mem.memberSn asc";
		List members = dao.query(hql, appId, groupNumber);
		return members;
	}
	
/*******************************************end*******************************************/	
	
	//----------以下为获取申请评审信息----------
	/**
	 * 根据项目申请id及人员id获取判断是否项目评审人及评审组长
	 * @param entityId 项目申请id
	 * @param personId 人员id
	 * @return 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
	 */
	@SuppressWarnings("rawtypes")
	public Integer isAppReviewer(String entityId, String personId){
		Integer isReviewer = 0;
		if(entityId == null || entityId.trim().isEmpty()){
			return isReviewer;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev.reviewerSn from " + projectApplication.getApplicationClassName() + " app, " + projectApplication.getApplicationReviewClassName() 
			+ " appRev where appRev.application.id=app.id and app.id=? and appRev.reviewer.id=? order by app.applicantSubmitDate desc";
		List list = dao.query(hql, entityId, personId);
		if(!list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				if((Integer)list.get(i) == 1){
					isReviewer = 2;
				}else{
					isReviewer = 1;
				}
			}
		}
		return isReviewer;
	}
	
	/**
	 * 保存申请评审信息
	 * @param applicationReview 申请评审对象
	 * @return 更新后的申请评审对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectApplicationReview setAppReviewInfoFromAppReview(ProjectApplicationReview applicationReview){
		//人员信息
		if(applicationReview.getReviewer() != null && applicationReview.getReviewer().getId() != null){//有成员id信息
			if(applicationReview.getReviewerType() == 1){//教师
				List<Teacher> list = this.getTeacherFetchPerson(applicationReview.getReviewer().getId());
				if(list != null && list.size() > 0){
					Teacher teacherM = list.get(0);
					applicationReview.setReviewer(teacherM.getPerson());
					applicationReview.setReviewerName(teacherM.getPerson().getName());
					applicationReview.setIdcardType(teacherM.getPerson().getIdcardType());
					applicationReview.setIdcardNumber(teacherM.getPerson().getIdcardNumber());
					applicationReview.setGender(teacherM.getPerson().getGender());
					applicationReview.setUniversity(teacherM.getUniversity());
					applicationReview.setAgencyName(teacherM.getUniversity().getName());
					applicationReview.setDepartment(teacherM.getDepartment());
					applicationReview.setInstitute(teacherM.getInstitute());
					if(teacherM.getDepartment() != null){
						applicationReview.setDivisionType(2);
						applicationReview.setDivisionName(teacherM.getDepartment().getName());
					}else if(teacherM.getInstitute() != null){
						applicationReview.setDivisionType(1);
						applicationReview.setDivisionName(teacherM.getInstitute().getName());
					}else{
						applicationReview.setDivisionType(null);
						applicationReview.setDivisionName(null);
					}
				}
			}else if(applicationReview.getReviewerType() == 2){//专家
				List<Expert> list2 = this.getExpertFetchPerson(applicationReview.getReviewer().getId());
				if(list2!=null && list2.size()==1){
					Expert expertM = list2.get(0);
					applicationReview.setReviewer(expertM.getPerson());
					applicationReview.setReviewerName(expertM.getPerson().getName());
					applicationReview.setIdcardType(expertM.getPerson().getIdcardType());
					applicationReview.setIdcardNumber(expertM.getPerson().getIdcardNumber());
					applicationReview.setGender(expertM.getPerson().getGender());
					applicationReview.setUniversity(null);
					applicationReview.setAgencyName(expertM.getAgencyName());
					applicationReview.setDepartment(null);
					applicationReview.setInstitute(null);
					applicationReview.setDivisionType(3);
					applicationReview.setDivisionName(expertM.getDivisionName());
				}
			}else if(applicationReview.getReviewerType() == 3){//学生
				;
			}
		}else{//不含成员id信息
			;
		}
		applicationReview.setDate(new Date());
		applicationReview.setIsManual(1);//手动分配专家
		if (null != applicationReview.getInnovationScore()) {
			double score = applicationReview.getInnovationScore() + applicationReview.getScientificScore() + applicationReview.getBenefitScore();
			applicationReview.setScore(score);
			applicationReview.setGrade(this.getReviewGrade(score));
		}
		return applicationReview;
	}
	
	/**
	 * 设置项目申请评审人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 项目申请评审对象
	 * @return ProjectApplicationReview 项目申请评审对象
	 */
	public ProjectApplicationReview setReviewPersonInfoFromReview(ProjectApplicationReview review){
		Person person = review.getReviewer();
		if(person == null || person.getId() == null || person.getId().trim().isEmpty() || review.getReviewerType() < 1){
			;
		}else if(review.getReviewerType() == 1){//教师
			String teacherId = "";
			teacherId = this.getTeacherIdByMemberAllUnit(person.getId(), review.getUniversity().getId(), review.getDepartment(), review.getInstitute());
			if(teacherId == null){
				teacherId = this.getTeacherIdByMemberPartUnit(person.getId(), review.getUniversity().getId());
			}
			person.setId((teacherId != null) ? teacherId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 2){//外部专家
			String expertId = this.getExpertIdByPersonIdUnit(person.getId(), review.getAgencyName(), review.getDivisionName());
			if(expertId == null){
				expertId = this.getExpertIdByPersonId(person.getId());
			}
			person.setId((expertId != null) ? expertId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 3){//学生
			String studentId = this.getStudentIdByPersonId(person.getId());
			person.setId((studentId != null) ? studentId : null);
			review.setReviewer(person);
		}
		return review;
	}	
	
	/**
	 * 获取个人申请评审对象
	 * @param entityId 结项id
	 * @param personId 人员id
	 * @return 申请评审对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectApplicationReview getPersonalAppReview(String entityId, String personId){
		if(entityId == null || personId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev from " + projectApplication.getApplicationReviewClassName() + " appRev left outer join appRev.application app left outer join appRev.grade grad where app.id=? and appRev.reviewer.id=?";
		List list = dao.query(hql, entityId, personId);
		if(!list.isEmpty()){
			return (ProjectApplicationReview)list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取当前账号能看到的所有人申请评审
	 * @param entityId 申请id
	 * @param accountType 登陆者身份	1：系统管理员    2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 当前账号能看到的所有人申请评审
	 */
	@SuppressWarnings("rawtypes")
	public List getAllAppReviewList(String entityId ,AccountType accountType){
		if(entityId == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		StringBuffer hql = new StringBuffer();
		hql.append("select appRev.id, appRev.reviewerSn, appRev.reviewerName, appRev.score, appRev.grade.name, appRev.submitStatus, appRev.reviewType, appRev.opinion from ").append(projectApplication.getApplicationReviewClassName())
			.append(" appRev left outer join appRev.application app where app.id=? ");
		if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and appRev.reviewType > 2");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//校级
			hql.append(" and appRev.reviewType = 4");
		}else if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(" and appRev.reviewType = 1");
		}
		hql.append(" order by appRev.reviewerSn asc");
		return dao.query(hql.toString(), entityId);
	}
	
	/**
	 * 判断项目申请评审是否所有专家全部提交
	 * @param entityId 申请id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	@SuppressWarnings("rawtypes")
	public int isAllAppReviewSubmit(String entityId){
		if(entityId == null){
			return -1;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev.id from " + projectApplication.getApplicationReviewClassName() + " appRev left outer join " +
				"appRev.application app where app.id=? and appRev.submitStatus!=3";
		List list = dao.query(hql, entityId);
		if(list.size()>0){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 获取申请评审总分与均分
	 * @param entityId 申请id
	 * @return 申请评审总分与均分
	 */
	@SuppressWarnings("rawtypes")
	public double[] getAppReviewScore(String entityId){
		double[] scores = new double[2];
		if(entityId == null){
			return scores;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev.score from " + projectApplication.getApplicationReviewClassName() + " appRev left outer join " +
				"appRev.application app where app.id=?";
		double reviewTotalScore = 0;
		List reviewScores = dao.query(hql.toString(), entityId);
		for(int i=0; i<reviewScores.size(); i++){
			double score = (Double) reviewScores.get(i);
			reviewTotalScore += score;	
		}
		double reviewAvgScore = reviewTotalScore/reviewScores.size();
		scores[0] = reviewTotalScore;
		scores[1] = reviewAvgScore;
		return scores;
	}
	
	/**
	 * 判断项目申请是否有评审记录
	 * @param entityId 申请id
	 * @return -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	@SuppressWarnings("rawtypes")
	public int checkReviewFromAppReview(String entityId){
		int check = -1;//无评审记录
		if(entityId == null || entityId.trim().length() == 0){
			return check;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "from " + projectApplication.getApplicationReviewClassName() + " appRev where appRev.application.id=?";
		List reviews = dao.query(hql, entityId);
		if(reviews!=null && reviews.size()>0){
			ProjectApplicationReview review = (ProjectApplicationReview) reviews.get(0);
			if(review.getReviewType() == 1){
				check = 1;//有评审记录且是专家评审
			}else{
				int reviewType = review.getReviewType();
				int submitStatus = review.getSubmitStatus();
				check = reviewType * 10 + submitStatus;
			}
		}
		return check;
	}
	
	/**
	 * 查询申请评审专家姓名
	 * @param appRevId 评审id
	 * @return 专家姓名
	 */
	@SuppressWarnings("rawtypes")
	public String getReviewerNameFromAppReview(String appRevId){
		if(appRevId == null){
			return null;
		}
		String reviewerName = "";
		List reviewerNames = dao.query("select appRev.reviewerName from ProjectApplicationReview appRev where appRev.id=?", appRevId);
		if(reviewerNames!=null && reviewerNames.size()>0){
			reviewerName = (String) reviewerNames.get(0);
		}
		return reviewerName;
	}
	
	/**
	 * 通过申请id获得申请评审组长的评审信息
	 * @param entityId 申请id
	 * @return 评审组长的评审信息
	 */
	@SuppressWarnings("rawtypes")
	public ProjectApplicationReview getGroupDirectorReviewFromAppReview(String entityId){
		if(entityId == null){
			return null;
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev from " + projectApplication.getApplicationReviewClassName() + " appRev left join fetch appRev.university uni where appRev.application.id=? and appRev.reviewerSn=1";
		List appRev = dao.query(hql, entityId);
		if(appRev.size() > 0){
			return (ProjectApplicationReview)appRev.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据申请id获得申请的所有评审列表
	 * @param entityId 申请id
	 * @return 申请的所有评审列表
	 */
	@SuppressWarnings("rawtypes")
	public List getAllAppReviewByAppId(String entityId){
		if(entityId == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev from " + projectApplication.getApplicationReviewClassName() + " appRev left join fetch appRev.grade grad left join fetch appRev.reviewer rev where appRev.application.id=? order by appRev.reviewerSn asc";
		return dao.query(hql, entityId);
	}
	
	/**
	 * 获得申请评审意见列表
	 * @param entityId申请id
	 * @return 申请评审意见列表
	 */
	@SuppressWarnings("rawtypes")
	public List getReviewOpinionListFromAppReview(String entityId){
		if(entityId == null){
			return new ArrayList();
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, entityId);
		String hql = "select appRev.reviewer.name, appRev.opinion from " + projectApplication.getApplicationReviewClassName() + " appRev where appRev.application.id=?";
		return dao.query(hql, entityId);
	}
	
	/**
	 * 获得项目经费默认值
	 * @return 项目经费
	 */
	public Double getDefaultFee(ProjectApplication application){
		Map<String, Object> sc = ActionContext.getContext().getApplication();
		double approveFee = 0.0;
		if(!application.getType().equals("key")){
			SystemOption so = (SystemOption)dao.query(SystemOption.class, application.getSubtype().getId());
			String approveFeeName = so.getName();
			if(application.getType().equals("general")){
				if(approveFeeName.equals("青年基金项目")){//青年基金默认7万元
					approveFee = Double.parseDouble((String)sc.get("youthFee"));
				}else if(approveFeeName.equals("规划基金项目")){//规划基金默认9万元
					approveFee = Double.parseDouble((String)sc.get("planFee"));
				}else{
					approveFee = 0.0;
				}
			}else if(application.getType().equals("post")){
				if(approveFeeName.equals("重大项目")){//重大项目默认20万元
					approveFee = Double.parseDouble((String)sc.get("youthFee"));
				}else if(approveFeeName.equals("重点项目")){//重点项目默认12万元
					approveFee = Double.parseDouble((String)sc.get("planFee"));
				}else if(approveFeeName.equals("一般项目")){//一般项目默认9万元
					approveFee = Double.parseDouble((String)sc.get("planFee"));
				}else{
					approveFee = 0.0;
				}
			}else{
				approveFee = 0.0;
			}
		}
		return approveFee;
	}
	
//	/**
//	 * 根据项目申请id对申请评审对象对应的人员、机构进行入库处理
//	 * @param entityId 项目申请id
//	 */
//	@SuppressWarnings("unchecked")
//	public void doWithNewReviewFromAppReview(String entityId){
//		if(entityId == null){
//			return;
//		}
//		List<ProjectApplicationReview> reviews = this.getAllAppReviewByAppId(entityId);
//		for(int i = 0; i < reviews.size(); i++){
//			ProjectApplicationReview review = reviews.get(i);
//			Map map = new HashMap();
//			map.put("idcardType", review.getIdcardType());
//			map.put("idcardNumber", review.getIdcardNumber());
//			map.put("personName", review.getReviewerName());
//			map.put("personType", review.getReviewerType());
//			map.put("gender", review.getGender());
//			map.put("agencyName", review.getAgencyName());
//			map.put("agencyId", (review.getUniversity() != null) ? review.getUniversity().getId() : null);
//			map.put("divisionName", review.getDivisionName());
//			map.put("divisionType", review.getDivisionType());
//			map = this.doWithNewPerson(map);
//			String personId = map.get("personId").toString();
//			String divisionId = map.get("divisionId").toString();
//			Person person = (Person)dao.query(Person.class, personId);
//			review.setReviewer(person);
//			if(review.getDivisionType() == 1){//研究基地
//				Institute institute =(Institute)dao.query(Institute.class, divisionId);
//				review.setInstitute(institute);
//				review.setDepartment(null);
//			}else if(review.getDivisionType() == 2){//院系
//				Department department =(Department)dao.query(Department.class, divisionId);
//				review.setInstitute(null);
//				review.setDepartment(department);
//			}
//			dao.modify(review);
//		}
//	}

	//----------以下为获取结项鉴定信息----------
	/**
	 * 根据项目id获取通过鉴定
	 * @param graId 项目id
	 * @return 通过鉴定
	 */
	@SuppressWarnings("rawtypes")
	public List getPassReviewByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, graId);
		String hql = "select endi from " + projectGranted.getEndinspectionClassName() + " endi where endi.granted.id=? and " +
				"endi.reviewStatus=3 and endi.reviewResult=2 and endi.finalAuditResultEnd <> 1 order by endi.applicantSubmitDate desc";
		List list = dao.query(hql, graId);
		return list;
	}

	/**
	 * 根据项目id获取未审鉴定
	 * @param graId 项目id
	 * @return 未审鉴定
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPendingReviewByGrantedId(String graId){
		if(graId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("graId", graId);
		String hql = "select endi from PostEndinspection endi where endi.granted.id=:graId and " +
				"endi.reviewStatus!=3";
		List list = dao.query(hql, map);
		return list;
	}
	/**
	 * 根据项目结项id及人员id获取判断是否项目鉴定人及鉴定组长
	 * @param endId 项目结项id
	 * @param personId 人员id
	 * @return 0不是鉴定人；1是鉴定人但不是鉴定组长；2是鉴定人且是鉴定组长
	 */
	@SuppressWarnings("rawtypes")
	public Integer isEndReviewer(String endId, String personId){
		Integer isReviewer = 0;
		if(endId == null || endId.trim().isEmpty()){
			return isReviewer;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev.reviewerSn from " + projectEndinspection.getEndinspectionClassName() + " endi, " + projectEndinspection.getEndinspectionReviewClassName() 
			+ " endRev where endRev.endinspection.id=endi.id and endi.id=? and endRev.reviewer.id=? order by endi.applicantSubmitDate desc";
		List list = dao.query(hql, endId, personId);
		if(!list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				if((Integer)list.get(i) == 1){
					isReviewer = 2;
				}else{
					isReviewer = 1;
				}
			}
		}
		return isReviewer;
	}
	/**
	 * 保存结项鉴定信息
	 * @param endinspectionReview 结项鉴定对象
	 * @return 更新后的结项鉴定对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectEndinspectionReview setEndReviewInfoFromEndReview(ProjectEndinspectionReview endinspectionReview){
		//人员信息
		if(endinspectionReview.getReviewer() != null && endinspectionReview.getReviewer().getId() != null){//有成员id信息
			if(endinspectionReview.getReviewerType() == 1){//教师
				List<Teacher> list = this.getTeacherFetchPerson(endinspectionReview.getReviewer().getId());
				if(list != null && list.size() > 0){
					Teacher teacherM = list.get(0);
					endinspectionReview.setReviewer(teacherM.getPerson());
					endinspectionReview.setReviewerName(teacherM.getPerson().getName());
					endinspectionReview.setIdcardType(teacherM.getPerson().getIdcardType());
					endinspectionReview.setIdcardNumber(teacherM.getPerson().getIdcardNumber());
					endinspectionReview.setGender(teacherM.getPerson().getGender());
					endinspectionReview.setUniversity(teacherM.getUniversity());
					endinspectionReview.setAgencyName(teacherM.getUniversity().getName());
					endinspectionReview.setDepartment(teacherM.getDepartment());
					endinspectionReview.setInstitute(teacherM.getInstitute());
					if(teacherM.getDepartment() != null){
						endinspectionReview.setDivisionType(2);
						endinspectionReview.setDivisionName(teacherM.getDepartment().getName());
					}else if(teacherM.getInstitute() != null){
						endinspectionReview.setDivisionType(1);
						endinspectionReview.setDivisionName(teacherM.getInstitute().getName());
					}else{
						endinspectionReview.setDivisionType(null);
						endinspectionReview.setDivisionName(null);
					}
				}
			}else if(endinspectionReview.getReviewerType() == 2){//专家
				List<Expert> list2 = this.getExpertFetchPerson(endinspectionReview.getReviewer().getId());
				if(list2!=null && list2.size()==1){
					Expert expertM = list2.get(0);
					endinspectionReview.setReviewer(expertM.getPerson());
					endinspectionReview.setReviewerName(expertM.getPerson().getName());
					endinspectionReview.setIdcardType(expertM.getPerson().getIdcardType());
					endinspectionReview.setIdcardNumber(expertM.getPerson().getIdcardNumber());
					endinspectionReview.setGender(expertM.getPerson().getGender());
					endinspectionReview.setUniversity(null);
					endinspectionReview.setAgencyName(expertM.getAgencyName());
					endinspectionReview.setDepartment(null);
					endinspectionReview.setInstitute(null);
					endinspectionReview.setDivisionType(3);
					endinspectionReview.setDivisionName(expertM.getDivisionName());
				}
			}else if(endinspectionReview.getReviewerType() == 3){//学生
				;
			}
		}else{//不含成员id信息
			;
		}
		endinspectionReview.setDate(new Date());
		endinspectionReview.setIsManual(1);//手动分配专家
		if (null != endinspectionReview.getInnovationScore()) {
			double score = endinspectionReview.getInnovationScore() + endinspectionReview.getScientificScore() + endinspectionReview.getBenefitScore();
			endinspectionReview.setScore(score);
			endinspectionReview.setGrade(this.getReviewGrade(score));
		}
		return endinspectionReview;
	}
	/**
	 * 设置项目结项鉴定人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 项目结项鉴定对象
	 * @return ProjectEndinspectionReview 项目结项鉴定对象
	 */
	public ProjectEndinspectionReview setReviewPersonInfoFromReview(ProjectEndinspectionReview review){
		Person person = review.getReviewer();
		if(person == null || person.getId() == null || person.getId().trim().isEmpty() || review.getReviewerType() < 1){
			;
		}else if(review.getReviewerType() == 1){//教师
			String teacherId = "";
			teacherId = this.getTeacherIdByMemberAllUnit(person.getId(), review.getUniversity().getId(), review.getDepartment(), review.getInstitute());
			if(teacherId == null){
				teacherId = this.getTeacherIdByMemberPartUnit(person.getId(), review.getUniversity().getId());
			}
			person.setId((teacherId != null) ? teacherId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 2){//外部专家
			String expertId = this.getExpertIdByPersonIdUnit(person.getId(), review.getAgencyName(), review.getDivisionName());
			if(expertId == null){
				expertId = this.getExpertIdByPersonId(person.getId());
			}
			person.setId((expertId != null) ? expertId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 3){//学生
			String studentId = this.getStudentIdByPersonId(person.getId());
			person.setId((studentId != null) ? studentId : null);
			review.setReviewer(person);
		}
		return review;
	}	
	/**
	 * 获取个人结项鉴定对象
	 * @param endId 结项id
	 * @param personId 人员id
	 * @return 结项鉴定对象
	 */
	@SuppressWarnings("rawtypes")
	public ProjectEndinspectionReview getPersonalEndReview(String endId, String personId){
		if(endId == null || personId == null){
			return null;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev left outer join endRev.endinspection endi left outer join endRev.grade grad where endi.id=? and endRev.reviewer.id=?";
		List list = dao.query(hql, endId, personId);
		if(!list.isEmpty()){
			return (ProjectEndinspectionReview)list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取当前账号能看到的所有人结项鉴定
	 * @param endId 结项id
	 * @param accountType 登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 当前账号能看到的所有人结项鉴定
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewList(String endId ,AccountType accountType){
		if(endId == null){
			return new ArrayList();
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		StringBuffer hql = new StringBuffer();
		hql.append("select endRev.id, endRev.reviewerSn, endRev.reviewerName, endRev.score, endRev.grade.name, endRev.submitStatus, endRev.reviewType from ").append(projectEndinspection.getEndinspectionReviewClassName())
			.append(" endRev left outer join endRev.endinspection endi where endi.id=? ");
		if(accountType.equals(AccountType.PROVINCE)){//省级
			hql.append(" and endRev.reviewType > 2");
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//校级
			hql.append(" and endRev.reviewType = 4");
		}else if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员
			hql.append(" and endRev.reviewType = 1");
		}
		hql.append(" order by endRev.reviewerSn asc");
		return dao.query(hql.toString(), endId);
	}
	
	/**
	 * 判断项目结项鉴定是否所有专家全部提交
	 * @param endId 结项id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	@SuppressWarnings("rawtypes")
	public int isAllEndReviewSubmit(String endId){
		if(endId == null){
			return -1;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev.id from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev left outer join " +
				"endRev.endinspection endi where endi.id=? and endRev.submitStatus!=3";
		List list = dao.query(hql, endId);
		if(list.size()>0){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 获取结项鉴定总分与均分
	 * @param endId 结项id
	 * @return 结项鉴定总分与均分
	 */
	@SuppressWarnings("rawtypes")
	public double[] getEndReviewScore(String endId){
		double[] scores = new double[2];
		if(endId == null){
			return scores;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev.score from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev left outer join " +
				"endRev.endinspection endi where endi.id=?";
		double reviewTotalScore = 0;
		List reviewScores = dao.query(hql.toString(), endId);
		for(int i=0; i<reviewScores.size(); i++){
			double score = (Double) reviewScores.get(i);
			reviewTotalScore += score;	
		}
		double reviewAvgScore = reviewTotalScore/reviewScores.size();
		scores[0] = reviewTotalScore;
		scores[1] = reviewAvgScore;
		return scores;
	}
	
	/**
	 * 判断项目结项是否有鉴定记录
	 * @param endId 结项id
	 * @return -1无鉴定记录，1有鉴定记录且是专家鉴定，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	@SuppressWarnings("rawtypes")
	public int checkReview(String endId){
		int check = -1;//无评审记录
		if(endId == null || endId.trim().length() == 0){
			return check;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev where endRev.endinspection.id=?";
		List reviews = dao.query(hql, endId);
		if(reviews!=null && reviews.size()>0){
			ProjectEndinspectionReview ger = (ProjectEndinspectionReview) reviews.get(0);
			if(ger.getReviewType() == 1){
				check = 1;//有评审记录且是专家评审
			}else{
				int reviewType = ger.getReviewType();
				int submitStatus = ger.getSubmitStatus();
				check = reviewType * 10 + submitStatus;
			}
		}
		return check;
	}
		
	/**
	 * 获得存储形式的分数指标id
	 * @return 存储形式的分数指标id
	 */
	public String getReviewSpecificationIds(){
		List<SystemOption> specifications =this.getSOByParentName("一般项目结项评审指标");	
		String specificationIds = "";
		for(int i=0; i<specifications.size(); i++){
			specificationIds += specifications.get(i).getId();
			if(i != specifications.size()-1){
				specificationIds += "; ";
		       }
		}
		return specificationIds;
	}
	
	/**
	 * 根据分数计算等级
	 * @param score分数
	 * @return 等级
	 */
	public SystemOption getReviewGrade(double score){
		SystemOption grade = null;
		if(score > 90){
			grade = (SystemOption) soDao.query("reviewGrade", "01");
		}else if(score>=65 && score<=90){
			grade = (SystemOption) soDao.query("reviewGrade", "02");
		}else if(score < 65){
			grade = (SystemOption) soDao.query("reviewGrade", "03");
		}
		return grade;
	}
	
	/**
	 * 查询结项鉴定专家姓名
	 * @param endRevId 鉴定id
	 * @return 专家姓名
	 */
	@SuppressWarnings("rawtypes")
	public String getReviewerName(String endRevId){
		if(endRevId == null){
			return null;
		}
		String reviewerName = "";
		List reviewerNames = dao.query("select endRev.reviewerName from ProjectEndinspectionReview endRev where endRev.id=?", endRevId);
		if(reviewerNames!=null && reviewerNames.size()>0){
			reviewerName = (String) reviewerNames.get(0);
		}
		return reviewerName;
	}
	
	/**
	 * 通过结项id获得结项鉴定组长的鉴定信息
	 * @param endId 结项id
	 * @return 鉴定组长的鉴定信息
	 */
	@SuppressWarnings("rawtypes")
	public ProjectEndinspectionReview getGroupDirectorReview(String endId){
		if(endId == null){
			return null;
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev left join fetch endRev.university uni where endRev.endinspection.id=? and endRev.reviewerSn=1";
		List endRev = dao.query(hql, endId);
		if(endRev.size() > 0){
			return (ProjectEndinspectionReview)endRev.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据结项id获得结项的所有鉴定列表
	 * @param endId 结项id
	 * @return 结项的所有鉴定列表
	 */
	@SuppressWarnings("rawtypes")
	public List getAllEndReviewByEndId(String endId){
		if(endId == null){
			return new ArrayList();
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev left join fetch endRev.grade gra left join fetch endRev.reviewer rev where endRev.endinspection.id=? order by endRev.reviewerSn asc";
		return dao.query(hql, endId);
	}
	/**
	 * 获得结项鉴定意见列表
	 * @param endId结项id
	 * @return 结项鉴定意见列表
	 */
	@SuppressWarnings("rawtypes")
	public List getReviewOpinionList(String endId){
		if(endId == null){
			return new ArrayList();
		}
		ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class, endId);
		String hql = "select endRev.reviewer.name, endRev.opinion from " + projectEndinspection.getEndinspectionReviewClassName() + " endRev where endRev.endinspection.id=?";
		return dao.query(hql, endId);
	}

	/**
	 * 根据项目结项id对项目鉴定对象对应的人员、机构进行入库处理
	 * @param endId 项目结项id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doWithNewReview(String endId){
		if(endId == null){
			return;
		}
		List<ProjectEndinspectionReview> reviews = this.getAllEndReviewByEndId(endId);
		for(int i = 0; i < reviews.size(); i++){
			ProjectEndinspectionReview review = reviews.get(i);
			Map map = new HashMap();
			map.put("idcardType", review.getIdcardType());
			map.put("idcardNumber", review.getIdcardNumber());
			map.put("personName", review.getReviewerName());
			map.put("personType", review.getReviewerType());
			map.put("gender", review.getGender());
			map.put("agencyName", review.getAgencyName());
			map.put("agencyId", (review.getUniversity() != null) ? review.getUniversity().getId() : null);
			map.put("divisionName", review.getDivisionName());
			map.put("divisionType", review.getDivisionType());
			map = this.doWithNewPerson(map);
			String personId = map.get("personId").toString();
			String divisionId = map.get("divisionId").toString();
			Person person = (Person)dao.query(Person.class, personId);
			review.setReviewer(person);
			if(review.getDivisionType() == 1){//研究基地
				Institute institute =(Institute)dao.query(Institute.class, divisionId);
				review.setInstitute(institute);
				review.setDepartment(null);
			}else if(review.getDivisionType() == 2){//院系
				Department department =(Department)dao.query(Department.class, divisionId);
				review.setInstitute(null);
				review.setDepartment(department);
			}
			dao.modify(review);
		}
	}
	
	
	//------------------以下删除相关对象-----------
	
	/**
	 * 删除项目
	 * @param appId项目申请id
	 */
	public void deleteProject(String appId){
		ProjectApplication projectApplication = (ProjectApplication)(dao.query(ProjectApplication.class, appId));
		ProjectFee projectFeeApply = null;//项目申请经费
		ProjectFee projectFeeGranted = null;//项目立项经费
		if (projectApplication.getProjectFee() != null) {
			projectFeeApply = dao.query(ProjectFee.class, projectApplication.getProjectFee().getId());
		}
		//删除相关立项信息
		ProjectGranted projectGranted = (ProjectGranted)this.getGrantedByAppId(appId);
		if(projectGranted != null){
			if (projectGranted.getProjectFee() != null) {
				projectFeeGranted = dao.query(ProjectFee.class, projectGranted.getProjectFee().getId());
			}
			if(!"post".equals(projectGranted.getProjectType())){
				//删除相关中检信息
				List<String> midinspectionids = this.getAllMidinspectionIdByGrantedId(projectGranted.getId());
				if(midinspectionids.size()>0){
					for(int j=0;j<midinspectionids.size();j++){
						ProjectMidinspection projectMidinspection = (ProjectMidinspection)dao.query(ProjectMidinspection.class,midinspectionids.get(j));
						if(null != projectMidinspection.getFile() && !"".equals(projectMidinspection.getFile())){
							FileTool.fileDelete(projectMidinspection.getFile());//删除中检申请文件
						}
						if(null != projectMidinspection.getDfs() && !projectMidinspection.getDfs().isEmpty() && dmssService.getStatus()){
							dmssService.deleteFile(projectMidinspection.getDfs());
						}
					}
				}
			}
			//删除相关结项信息
			List<String> endinspectionids = this.getAllEndinspectionIdByGrantedId(projectGranted.getId());
			if(endinspectionids.size()>0){
				for(int j=0;j<endinspectionids.size();j++){
					ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.query(ProjectEndinspection.class,endinspectionids.get(j));
					//删除相关结项信息
					if(null != projectEndinspection.getFile() && !"".equals(projectEndinspection.getFile())){
						FileTool.fileDelete(projectEndinspection.getFile());//删除结项申请文件
					}
					if(null != projectEndinspection.getDfs() && !projectEndinspection.getDfs().isEmpty() && dmssService.getStatus()){
						dmssService.deleteFile(projectEndinspection.getDfs());
					}
				}
			}
			//删除相关变更信息
			List<String> variationids = this.getAllVariationIdByGrantedId(projectGranted.getId());
			if(variationids.size()>0){
				for(int j=0;j<variationids.size();j++){
					ProjectVariation gv = (ProjectVariation)dao.query(ProjectVariation.class,variationids.get(j));
					if(null != gv.getFile() && !"".equals(gv.getFile())){
						FileTool.fileDelete(gv.getFile());//删除变更申请文件
					}
					if (gv.getChangeFee() == 1 && gv.getFinalAuditResult() == 2) {//若果变更经费成功则删除变更前经费
						if (gv.getOldProjectFee() != null) {
							dao.delete(ProjectFee.class, gv.getOldProjectFee().getId());
						}
					}
					else {	//若果变更经费未成功则删除该条变更的变更后经费
						if (gv.getNewProjectFee() != null) {
							dao.delete(ProjectFee.class, gv.getNewProjectFee().getId());
						}
					}
//					if (gv.getChangeFee() == 1 && gv.getFinalAuditResultDetail().charAt(8) != '1') {	//若果变更经费未成功则删除该条变更的变更后经费
//						if (gv.getNewProjectFee() != null) {
//							dao.delete(ProjectFee.class, gv.getNewProjectFee().getId());
//						}
//					}
				}
			}
		}
		//最后删除申请信息
		if(null != projectApplication.getFile() && !"".equals(projectApplication.getFile())){
			FileTool.fileDelete(projectApplication.getFile());//删除申请文件
		}
		if(null != projectApplication.getDfs() && !projectApplication.getDfs().isEmpty() && dmssService.getStatus()){
			dmssService.deleteFile(projectApplication.getDfs());
		}
		dao.delete(projectApplication);
		if (projectFeeApply != null) {
			dao.delete(projectFeeApply);
		}
		if (projectFeeGranted != null) {
			dao.delete(projectFeeGranted);
		}
	}
	
	/**
	 * 删除年检
	 * @param anninspection 年检对象
	 */
	public void deleteAnninspection(ProjectAnninspection anninspection){
		String filename = anninspection.getFile();
		if(filename!=null && !"".equals(filename)){
			 FileTool.fileDelete(filename);
		}
		if(null != anninspection.getDfs() && !anninspection.getDfs().isEmpty() && dmssService.getStatus()){
			dmssService.deleteFile(anninspection.getDfs());
		}
		dao.delete(anninspection);
	}
	
	/**
	 * 删除中检
	 * @param midinspection 中检对象
	 */
	public void deleteMidinspection(ProjectMidinspection midinspection){
		String filename = midinspection.getFile();
		if(filename!=null && !"".equals(filename)){
			 FileTool.fileDelete(filename);
		}
		if(null != midinspection.getDfs() && !midinspection.getDfs().isEmpty() && dmssService.getStatus()){
			dmssService.deleteFile(midinspection.getDfs());
		}
		dao.delete(midinspection);
	}
	
	/**
	 * 删除结项
	 * @param endinspection 结项对象
	 */
	public void deleteEndinspection(ProjectEndinspection endinspection){
		String filename = endinspection.getFile();
		if(filename!=null && !"".equals(filename)){
			 FileTool.fileDelete(filename);
		}
		if(null != endinspection.getDfs() && !endinspection.getDfs().isEmpty() && dmssService.getStatus()){
			dmssService.deleteFile(endinspection.getDfs());
		}
		dao.delete(endinspection);
	}
	
	/**
	 * 删除变更
	 * @param variation变更对象
	 */
	public void deleteVariation(ProjectVariation variation){
		String filename = variation.getFile();
		if(filename!=null && !"".equals(filename)){
			 FileTool.fileDelete(filename);
		}
		if(null != variation.getDfs() && !variation.getDfs().isEmpty() && dmssService.getStatus()){
			dmssService.deleteFile(variation.getDfs());
		}
		if(variation.getChangeMember() == 1 && variation.getNewMemberGroupNumber() != null){
			String appId = this.getApplicationIdByGrantedId(this.getGrantedIdByVarId(variation.getId()));
			List<ProjectMember> oldMembers = this.getMember(appId, variation.getNewMemberGroupNumber());
			for (ProjectMember projectMember : oldMembers) {
				dao.delete(projectMember);
			}
		}
		dao.delete(variation);
	}
	//----------以下为隐藏申请、年检、中检、结项、变更信息----------
	/**
	 * 根据账号类别隐藏申请信息
	 * @param list
	 * @param accountType
	 * @param isReviewer
	 * @return 处理后的申请信息
	 */
	public ProjectApplication hideAppInfo(ProjectApplication application, AccountType accountType, int isReviewer) {
			//对审核的处理
			if(accountType.compareTo(AccountType.MINISTRY) > 0) {
				application.setMinistryAuditStatus(0);
				application.setMinistryAuditResult(0);
				application.setMinistryAuditDate(null);
				application.setMinistryAuditOpinion(null);
				application.setMinistryAuditorName(null);
			}
			if(accountType.compareTo(AccountType.PROVINCE) > 0) {
				application.setProvinceAuditStatus(0);
				application.setProvinceAuditResult(0);
				application.setProvinceAuditorName(null);
				application.setProvinceAuditDate(null);
				application.setProvinceAuditOpinion(null);
			}
			if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY) > 0) {
				application.setUniversityAuditStatus(0);
				application.setUniversityAuditResult(0);
				application.setUniversityAuditorName(null);
				application.setUniversityAuditDate(null);
				application.setUniversityAuditOpinion(null);
			}
			if(accountType.compareTo(AccountType.INSTITUTE) > 0) {
				application.setDeptInstAuditStatus(0);
				application.setDeptInstAuditResult(0);
				application.setDeptInstAuditorName(null);
				application.setDeptInstAuditDate(null);
				application.setDeptInstAuditOpinion(null);
			}
			//对评审的处理
			if((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) || (accountType.compareTo(AccountType.INSTITUTE) > 0 && isReviewer!=2)){//部门、研究人员(除评审组长)
				application.setReviewStatus(0);
				application.setReviewResult(0);
				application.setReviewerName(null);
				application.setReviewDate(null);
				application.setReviewOpinion(null);
				application.setReviewTotalScore(null);
				application.setReviewAverageScore(null);
				application.setReviewGrade(null);
				application.setReviewOpinionQualitative(null);
			}
			//对评审审核的处理
			if(accountType.compareTo(AccountType.MINISTRY) > 0){//部门至省厅管理人员、研究人员
				application.setFinalAuditorName(null);
				application.setFinalAuditOpinion(null);
				if(application.getFinalAuditStatus() != 3){
					application.setFinalAuditResult(0);
					application.setFinalAuditDate(null);
				}
			}
		return application;
	}
	
	/**
	 * 根据账号类别隐藏年检
	 * @param list
	 * @param accountType
	 * @return 处理后的年检
	 */
	public List<ProjectAnninspection> hideAnnInfo(List<ProjectAnninspection> list, AccountType accountType) {
		for (int i = 0; list!=null && i < list.size(); i++) {
			ProjectAnninspection anninspection = list.get(i);
			if(accountType.compareTo(AccountType.MINISTRY) > 0) {
				anninspection.setFinalAuditOpinion(null);
				anninspection.setFinalAuditorName(null);
				if(anninspection.getFinalAuditStatus() != 3){
					anninspection.setFinalAuditResult(0);
					anninspection.setFinalAuditDate(null);
				}
			}
			if(accountType.compareTo(AccountType.PROVINCE) > 0) {
				anninspection.setProvinceAuditStatus(0);
				anninspection.setProvinceAuditorName(null);
				anninspection.setProvinceAuditDate(null);
				anninspection.setProvinceAuditOpinion(null);
				anninspection.setProvinceAuditResult(0);
			}
			if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY) > 0) {
				anninspection.setUniversityAuditDate(null);
				anninspection.setUniversityAuditOpinion(null);
				anninspection.setUniversityAuditorName(null);
				anninspection.setUniversityAuditStatus(0);
				anninspection.setUniversityAuditResult(0);
			}
			if(accountType.compareTo(AccountType.INSTITUTE) > 0) {
				anninspection.setDeptInstAuditDate(null);
				anninspection.setDeptInstAuditOpinion(null);
				anninspection.setDeptInstAuditorName(null);
				anninspection.setDeptInstAuditStatus(0);
				anninspection.setDeptInstAuditResult(0);
			}
			list.set(i, anninspection);
		}
		return list;
	}
	
	/**
	 * 根据账号类别隐藏中检信息
	 * @param list
	 * @param accountType
	 * @return 处理后的中检信息
	 */
	public List<ProjectMidinspection> hideMidInfo(List<ProjectMidinspection> list, AccountType accountType) {
		for (int i = 0; list!=null && i < list.size(); i++) {
			ProjectMidinspection midinspection = list.get(i);
			if(accountType.compareTo(AccountType.MINISTRY) > 0) {
				midinspection.setFinalAuditOpinion(null);
				midinspection.setFinalAuditorName(null);
				if(midinspection.getFinalAuditStatus() != 3){
					midinspection.setFinalAuditResult(0);
					midinspection.setFinalAuditDate(null);
				}
			}
			if(accountType.compareTo(AccountType.PROVINCE) > 0) {
				midinspection.setProvinceAuditStatus(0);
				midinspection.setProvinceAuditorName(null);
				midinspection.setProvinceAuditDate(null);
				midinspection.setProvinceAuditOpinion(null);
				midinspection.setProvinceAuditResult(0);
			}
			if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY) > 0) {
				midinspection.setUniversityAuditDate(null);
				midinspection.setUniversityAuditOpinion(null);
				midinspection.setUniversityAuditorName(null);
				midinspection.setUniversityAuditStatus(0);
				midinspection.setUniversityAuditResult(0);
			}
			if(accountType.compareTo(AccountType.INSTITUTE) > 0) {
				midinspection.setDeptInstAuditDate(null);
				midinspection.setDeptInstAuditOpinion(null);
				midinspection.setDeptInstAuditorName(null);
				midinspection.setDeptInstAuditStatus(0);
				midinspection.setDeptInstAuditResult(0);
			}
			list.set(i, midinspection);
		}
		return list;
	}

	/**
	 * 根据账号类别隐藏结项信息
	 * @param list
	 * @param accountType
	 * @param isReviewer
	 * @return 处理后的结项信息
	 */
	public List<ProjectEndinspection> hideEndInfo(List<ProjectEndinspection> list, AccountType accountType, int isReviewer) {
		for (int i = 0; list!=null && i < list.size(); i++) {
			ProjectEndinspection endinspection = list.get(i);
			endinspection.setImportedProductInfo(this.getProductTypeReal(endinspection.getImportedProductInfo(), endinspection.getImportedProductTypeOther()));
			//对审核的处理
			if(accountType.compareTo(AccountType.MINISTRY) > 0) {
				endinspection.setMinistryAuditStatus(0);
				endinspection.setMinistryResultExcellent(0);
				endinspection.setMinistryResultNoevaluation(0);
				endinspection.setMinistryResultEnd(0);
				endinspection.setMinistryAuditDate(null);
				endinspection.setMinistryAuditOpinion(null);
				endinspection.setMinistryAuditorName(null);
			}
			if(accountType.compareTo(AccountType.PROVINCE) > 0) {
				endinspection.setProvinceAuditStatus(0);
				endinspection.setProvinceResultExcellent(0);
				endinspection.setProvinceResultNoevaluation(0);
				endinspection.setProvinceResultEnd(0);
				endinspection.setProvinceAuditorName(null);
				endinspection.setProvinceAuditDate(null);
				endinspection.setProvinceAuditOpinion(null);
			}
			if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY) > 0) {
				endinspection.setUniversityAuditStatus(0);
				endinspection.setUniversityResultExcellent(0);
				endinspection.setUniversityResultNoevaluation(0);
				endinspection.setUniversityResultEnd(0);
				endinspection.setUniversityAuditorName(null);
				endinspection.setUniversityAuditDate(null);
				endinspection.setUniversityAuditOpinion(null);
			}
			if(accountType.compareTo(AccountType.INSTITUTE) > 0) {
				endinspection.setDeptInstAuditStatus(0);
				endinspection.setDeptInstResultExcellent(0);
				endinspection.setDeptInstResultNoevaluation(0);
				endinspection.setDeptInstResultEnd(0);
				endinspection.setDeptInstAuditorName(null);
				endinspection.setDeptInstAuditDate(null);
				endinspection.setDeptInstAuditOpinion(null);
			}
			//对鉴定的处理
			if((accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)) || (accountType.compareTo(AccountType.INSTITUTE) > 0 && isReviewer!=2)){//部门、研究人员(除评审组长)
				endinspection.setReviewStatus(0);
				endinspection.setReviewResult(0);
				endinspection.setReviewerName(null);
				endinspection.setReviewDate(null);
				endinspection.setReviewOpinion(null);
				endinspection.setReviewTotalScore(null);
				endinspection.setReviewAverageScore(null);
				endinspection.setReviewGrade(null);
				endinspection.setReviewOpinionQualitative(null);
			}
			//对鉴定审核的处理
			if(accountType.compareTo(AccountType.MINISTRY) > 0){//部门至省厅管理人员、研究人员
				endinspection.setFinalAuditorName(null);
				endinspection.setFinalAuditOpinion(null);
				if(endinspection.getFinalAuditStatus() != 3){
					endinspection.setFinalAuditResultNoevaluation(0);
					endinspection.setFinalAuditResultExcellent(0);
					endinspection.setFinalAuditResultEnd(0);
					endinspection.setFinalAuditDate(null);
				}
			}
			list.set(i, endinspection);
		}
		return list;
	}
	
	/**
	 * 根据账号类别隐藏变更信息
	 * @param list
	 * @param accountType
	 * @return 处理后的变更信息
	 */
	public List<ProjectVariation> hideVarInfo(List<ProjectVariation> list, AccountType accountType) {
		for (int i = 0; list!=null && i < list.size(); i++) {
			ProjectVariation variation = list.get(i);
			if(accountType.compareTo(AccountType.MINISTRY) > 0) {
				variation.setFinalAuditOpinion(null);
				variation.setFinalAuditorName(null);
				if(variation.getFinalAuditStatus() != 3){
					variation.setFinalAuditResult(0);
					variation.setFinalAuditDate(null);
				}
			}
			if(accountType.compareTo(AccountType.PROVINCE) > 0) {
				variation.setProvinceAuditStatus(0);
				variation.setProvinceAuditResult(0);
				variation.setProvinceAuditorName(null);
				variation.setProvinceAuditDate(null);
				variation.setProvinceAuditOpinion(null);
			}
			if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY) > 0) {
				variation.setUniversityAuditStatus(0);
				variation.setUniversityAuditResult(0);
				variation.setUniversityAuditDate(null);
				variation.setUniversityAuditOpinion(null);
				variation.setUniversityAuditorName(null);
			}
			if(accountType.compareTo(AccountType.INSTITUTE) > 0) {
				variation.setDeptInstAuditStatus(0);
				variation.setDeptInstAuditResult(0);
				variation.setDeptInstAuditResultDetail(null);
				variation.setDeptInstAuditDate(null);
				variation.setDeptInstAuditOpinion(null);
				variation.setDeptInstAuditorName(null);
			}
			list.set(i, variation);
		}
		return list;
	}
	//----------以下为判断项目是否部属高校-------------------------
	/**
	 * 判断立项项目是否部属高校
	 * @param graId 项目立项id
	 * @return 1:部署高校, 0:地方高校
	 */
	@SuppressWarnings("rawtypes")
	public int isSubordinateUniversityGranted(String graId) {
		if(graId == null){
			return -1;
		}
		String hql = "select uni.type from ProjectGranted gra left outer join gra.university uni where gra.id = ? ";
		List l = dao.query(hql, graId);
		if(l != null && l.size() > 0) {
			int i = (Integer) l.get(0);
			if(i == 3) {
				return 1;
			}
		}
		return 0;
	}
	/**
	 * 判断申请项目是否部属高校
	 * @param appId 项目申请id
	 * @return 1:部署高校, 0:地方高校
	 */
	@SuppressWarnings("rawtypes")
	public int isSubordinateUniversityApplication(String appId){
		if(appId == null){
			return -1;
		}
		String hql = "select uni.type from ProjectApplication app left outer join app.university uni where app.id = ? ";
		List l = dao.query(hql, appId);
		if(l != null && l.size() > 0) {
			int i = (Integer) l.get(0);
			if(i == 3) {
				return 1;
			}
		}
		return 0;
	}
	//----------以下为dwr异步调用函数----------
	/**
	 * 判断项目批准号是否唯一
	 * @param grantedClassName 立项类名称
	 * @param number 项目批准号
	 * @param appId 项目申请id
	 * @return 项目批准号是否唯一
	 */
	@SuppressWarnings("unchecked")
	public boolean isGrantedNumberUnique(String grantedClassName, String number, String appId){
		if(number == null){
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		String hql;
		if(appId==null || appId.equals("")){
			map.put("number", number);
			hql = "select gra.id from " + grantedClassName + " gra where gra.number=:number";
		}else{
			map.put("number", number);
			map.put("appId", appId);
			hql = "select gra.id from " + grantedClassName + " gra where gra.number=:number and gra.application.id!=:appId";
		}
		List<String> list = dao.query(hql, map);
		if(list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断项目结项号是否唯一
	 * param endinspectionClassName 结项类名称
	 * @param number 项目结项号
	 * @param endId 结项id
	 * @return 项目结项号是否唯一true：唯一 false：不唯一
	 */
	@SuppressWarnings("unchecked")
	public boolean isEndNumberUnique(String endinspectionClassName, String number, String endId){
		if(number == null){
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		String hql;
		if(endId==null || endId.equals("")){
			map.put("number", number);
//			hql = "select endi.id from " + endinspectionClassName + " endi where endi.certificate=:number";
			hql = "select endi.id from ProjectEndinspection endi where endi.certificate=:number";
		}else{
			map.put("number", number);
			map.put("endId", endId);
//			hql = "select endi.id from " + endinspectionClassName + " endi where endi.certificate=:number and endi.id!=:endId";
			hql = "select endi.id from ProjectEndinspection endi where endi.certificate=:number and endi.id!=:endId";
		}
		List<String> list = dao.query(hql, map);
		if(list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断年检是否审核
	 * param anninspectionClassName 年检类名称
	 * @param year 报告年度
	 * @param annId 报告id
	 * @param graId 立项id
	 * @return 年检是否审核true:未审核 false:已审核
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isAuditReport(String anninspectionClassName, Integer year, String annId, String graId){
		if(year == null || graId == null || graId.isEmpty()){
			return false;
		}
		Map map = new HashMap();
		String hql;
		if(annId==null || annId.equals("")){
			map.put("year", year);
			map.put("graId", graId);
			hql = "select ann.id from " + anninspectionClassName + " ann where ann.year=:year and ann.granted.id=:graId and ann.finalAuditStatus=3";
		}else{
			map.put("year", year);
			map.put("annId", annId);
			map.put("graId", graId);
			hql = "select ann.id from " + anninspectionClassName + " ann where ann.year=:year and ann.granted.id=:graId and ann.id!=:annId and ann.finalAuditStatus=3";
		}
		List<String> list = dao.query(hql, map);
		if(list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
    /**
     * 判断年检年度是否早于项目年度
     * @param year 报告年度
     * @param graId 立项id
     * @return 年检年度是否早于项目年度true:不早于 false:早于
     */
	public boolean isEarlyGranted(Integer year, String graId){
		Integer grantedYear = this.getGrantedYear(graId);
		return year >= grantedYear;
	}
	
	/**
	 * 获得默认项目批准号
	 * @param grantedClassName 立项类名称
	 * @param applicationClassName 申请类名称
	 * @return 默认项目批准号
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public String getDefaultProjectNumber(String grantedClassName, String applicationClassName, String entityId){
		Date date = new Date();
		String year = (String.valueOf(date.getYear() + 1900)).substring(2);
		String code ="#";
		String dTcode = "XLX";
		List list1 = dao.query("select sub.name, app.disciplineType from " + applicationClassName + " app left outer join app.subtype sub where app.id = ?", entityId);
		if(list1.size() > 0){
			Object[] o = (Object[]) list1.get(0);
			String subName = (String)o[0];//项目子类名称
			if(applicationClassName.equals("GeneralApplication")){//一般项目
				if(subName.equals("青年基金项目")){
					code = "YJC";
				}else if(subName.equals("规划基金项目")){
					code = "YJA";
				}else if(subName.equals("自筹经费项目")){
					code = "YJE";
				}
				
				List list2 = dao.query("select app.university.id from " + applicationClassName + " app where app.id = ?", entityId);
				Object[] subcode= (Object[]) list2.get(0);
				String agencyId = (String)subcode[0];
				Agency university = (Agency) dao.query(Agency.class, agencyId);
				if(this.isIncludedWestProvince(university.getProvince().getId())==1){//西部项目
					if(subName.equals("青年基金项目")){
						code = "XJC";
					}else if(subName.equals("规划基金项目")){
						code = "XJA";
					}
				}else if(this.isXizangProvince(university.getProvince().getId())== 1){//新疆项目
					if(subName.equals("青年基金项目")){
						code = "XJJC";
					}else if(subName.equals("规划基金项目")){
						code = "XJJA";
					}else if(subName.equals("自筹经费项目")){
						code = "XJJE";
					}
				}else if(this.isXizangProvince(university.getProvince().getId())== 1){//西藏项目
					if(subName.equals("青年基金项目")){
						code = "XZJC";
					}else if(subName.equals("规划基金项目")){
						code = "XZJA";
					}else if(subName.equals("自筹经费项目")){
						code = "XZJE";
					}
				}
			}else if(applicationClassName.equals("InstpApplication")){//基地项目
				code = "JJD";
			}else if(applicationClassName.equals("PostApplication")){//后期资助项目
				code = "JHQ";
			}else if(applicationClassName.equals("SpecialApplication")){//专项任务
				code = "JD";
			}
			String disciplineType = (String)o[1];
			List list2 = dao.query("select syso.code from SystemOption syso where syso.name = ? and syso.standard = 'disciplineType'", disciplineType);
			if(list2.size() > 0){
				dTcode = (String) list2.get(0);//学科门类代码
			}
		}
		String projectNumber = "";
		if(applicationClassName.equals("PostApplication")){
			projectNumber = year + code;
		}else{
			projectNumber = year + code + dTcode;
		}
		int max = 0;
		Map map = new HashMap();
		map.put("projectNumber", projectNumber+"%");
		String hql = "select gra.number from " + grantedClassName + " gra where gra.number like :projectNumber order by gra.number desc";
		List cersF = dao.query(hql,map);
		if(cersF.size() > 0){
			String ExistGraNum = (String) cersF.get(0);
			String graNum = ExistGraNum.substring(projectNumber.length());
			max = Integer.parseInt(graNum) + 1;
		}else{
			max = 1;
		}	
		String strMax = max + "";
		while(strMax.length() < 3){
			strMax = "0" + strMax;
		}
		projectNumber = projectNumber + strMax;
		return projectNumber;
	}
	
	
	/**
	 * 获得默认结项证书编号
	 * @param endinspectionClassName 结项类名称
	 * @return 默认结项证书编号
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public String getDefaultEndCertificate(String endinspectionClassName){
		Date date = new Date();
		String year = String.valueOf(date.getYear() + 1900);
		String cer = "";
		String prifix = "";
		Map map = new HashMap();
		StringBuffer hql1 = new StringBuffer();
		if (endinspectionClassName.equals("InstpEndinspection")) {
			prifix = year + "JDD";
		}else {//一般项目和其他项目
			prifix = year + "JXZ";
		}
		map.put("cer", prifix + "%");
		hql1.append("select SUBSTRING(endi.certificate,8) from " + endinspectionClassName + 
				" endi where endi.certificate like :cer order by endi.certificate desc");
		List certificates = dao.query(hql1.toString(), map);
		for(int i=1; i<=certificates.size()+1; i++){
			String temp = i + "";
			while(temp.length()<4){
				temp = "0" + temp;
			}
			if (!certificates.contains(temp)) {
				cer = prifix + temp;
				break;
			}
		}
		return cer;
	}
	//----------------以下为上传文件----------------------
	/**
	 * 上传项目申请书文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @param submitStatus 提交状态
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadAppFile(String projectType, ProjectApplication application, File uploadFile) {
		try {
			String applicationClassName = "";
			if(projectType.equals("general")){
				applicationClassName = "GeneralApplication";
			}else if(projectType.equals("key")){
				applicationClassName = "KeyApplication";
			}else if(projectType.equals("instp")){
				applicationClassName = "InstpApplication";
			}else if(projectType.equals("post")){
				applicationClassName = "PostApplication";
			}else if(projectType.equals("entrust")){
				applicationClassName = "EntrustApplication";
			}else if(projectType.equals("special")){
				applicationClassName = "SpecialApplication";
			}
			
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
//			Map map = new HashMap();
//			map.put("appId", appId);
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			//高校代码
//			String code = (String)dao.query("select uni.code from " + applicationClassName + " app left outer join app.university uni where app.id = :appId ", map).get(0);
			String code = application.getUniversity().getCode();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
//			ProjectApplication application = (ProjectApplication) this.dao.query(ProjectApplication.class, appId);
			int year = application.getYear();
			String realName = projectType + "_app_" + year + "_" + code + "_" + date + extendName;
			String filepath = "project/" + projectType + "/app/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 上传结项研究数据包文件
	 * @param uploadFile 上传的文件
	 *  @param projectType  项目类别
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings("deprecation")
	public String uploadEndDataFile(File uploadFile,GeneralGranted projectGranted){
		try {
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			// 获取系统时间并转成字符串
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String dateformat = format.format(date);
			String year = String.valueOf(date.getYear() + 1900);
			String realName = projectGranted.getProjectType() + "_data_" + year +"_" + projectGranted.getNumber()+"_" +dateformat+ extendName;
//			"upload/project/PROJECTTYPE/data/YEAR/PROJECTTYPE_data_YEAR_PROJNUM_DATE"
			String filepath = "project/"+ projectGranted.getProjectType() + "/data/" +year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 研究人员上传年检书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadAnnFile(String projectType, String graId, int submitStatus, File uploadFile) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_ann_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/ann/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
//			importAnninspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 部级管理人员上传年检书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @param annId 年检书id
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadAnnFileResult(String projectType, String graId, int submitStatus, File uploadFile, String annId) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			ProjectAnninspection anninspection = (ProjectAnninspection) this.dao.query(ProjectAnninspection.class, annId);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(anninspection.getFinalAuditDate());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_ann_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/ann/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
//			importAnninspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 研究人员上传立项计划书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadGraFile(String projectType, String graId, int submitStatus, File uploadFile) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_gra_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/gra/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			importMidinspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 研究人员上传中检报告书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadMidFile(String projectType, String graId, int submitStatus, File uploadFile) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_mid_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/mid/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			importMidinspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 部级管理人员中检后上传中检报告书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param submitStatus 提交状态
	 * @param midId 当前中检id
	 * @param uploadFile上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String uploadMidFileResult(String projectType, String graId, int submitStatus, File uploadFile, String midId) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			ProjectMidinspection midinspection = (ProjectMidinspection) this.dao.query(ProjectMidinspection.class, midId);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(midinspection.getFinalAuditDate());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_mid_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/mid/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			importMidinspectionWordXMLData(x, graId, projectType);
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 研究人员上传结项申请书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @param submitStatus 提交状态
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadEndFile(String projectType, String graId, File uploadFile, int submitStatus) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			Map map = new HashMap();
			map.put("graId", graId);
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			//项目编号
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id =:graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_end_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/end/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importEndinspectionWordXMLData(x, graId, projectType);
				//importEndXMLData(pid, x);
			}
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 部级管理人员结项后录入上传结项申请书
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @param endId 当前结项id
	 * @param submitStatus 提交状态
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public String uploadEndFileResult(String projectType, String graId, File uploadFile, int submitStatus, String endId) {
		try {
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			Map map = new HashMap();
			map.put("graId", graId);
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			//项目编号
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			ProjectEndinspection endinspection = (ProjectEndinspection) this.dao.query(ProjectEndinspection.class, endId);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(endinspection.getFinalAuditDate());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_end_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/end/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importEndinspectionWordXMLData(x, graId, projectType);
				//importEndXMLData(pid, x);
			}
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 研究人员上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadVarFile(String projectType, String graId, int submitStatus, File uploadFile){
		try{
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			//项目编号
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_var_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/var/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			/*
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importVarXMLData(pid, x);
			}*/
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 部级管理人员上传变更文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param varId 当前变更id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadVarFileResult(String projectType, String graId, int submitStatus, File uploadFile, String variId){
		try{
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			//项目编号
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			ProjectVariation variation = (ProjectVariation) this.dao.query(ProjectVariation.class, variId);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(variation.getFinalAuditDate());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_var_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/var/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			/*
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importVarXMLData(pid, x);
			}*/
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 上传变更延期项目研究计划文件
	 * @param projectType 项目类别字符串
	 * @param graId 项目立项id
	 * @param uploadFile 上传的文件
	 * @return 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String uploadVarPlanfile(String projectType, String graId, int submitStatus, File uploadFile){
		try{
			String grantedClassName = "";
			if(projectType.equals("general")){
				grantedClassName = "GeneralGranted";
			}else if(projectType.equals("key")){
				grantedClassName = "KeyGranted";
			}else if(projectType.equals("instp")){
				grantedClassName = "InstpGranted";
			}else if(projectType.equals("post")){
				grantedClassName = "PostGranted";
			}else if(projectType.equals("entrust")){
				grantedClassName = "EntrustGranted";
			}else if(projectType.equals("special")){
				grantedClassName = "SpecialGranted";
			}
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String realPath = ApplicationContainer.sc.getRealPath("upload");
			Map map = new HashMap();
			map.put("graId", graId);
			//项目编号
			String gnumber = (String)dao.query("select gra.number from " + grantedClassName + " gra where gra.id = :graId ", map).get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			String realName = projectType + "_var_pos_" + year + "_" + gnumber + "_" + date + extendName;
			String filepath = "project/" + projectType + "/var/" + year + "/";
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			/*
			if(submitStatus == 3) {
				// 读取doc文件并导入
				importVarXMLData(pid, x);
			}*/
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//-----------以下为获得项目某些字段的信息---------
	/**
	 * 根据项目申请id获得项目主题名称
	 * @param appId 项目申请id
	 * @return  项目主题名称
	 */
	@SuppressWarnings("rawtypes")
	public String getProjectTopicNameByAppId(String appId){
		if(appId == null || appId.trim().length() == 0){
			return "";
		}
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		if(projectApplication != null && projectApplication.getApplicationClassName() == "EntrustApplication"){
			List list = dao.query("select top.name from " + projectApplication.getApplicationClassName() + " app, SystemOption top where app.topic.id=top.id and app.id = ?", projectApplication.getId());
			if(list != null && list.size() > 0){
				return (String)list.get(0);
			}
		}
		return "";
	}
	//----------以下为业务处理----------
	/**
	 * 获得当前业务设置状态,用于项目申请、选题（重大攻关项目）
	 * @return 业务状态1:业务激活中，0:业务停止
	 */
	@SuppressWarnings("rawtypes")
	public int getBusinessStatus(String code){
		String hql = "select b.status from Business b left outer join b.subType so where so.code=? and " +
			"so.standard = 'businessType'";
		List l = dao.query(hql, code);
		if(l != null && l.size() > 0) {
			int status = (Integer) l.get(0);
			return status;//1:业务激活中，0:业务停止
		}else{
			return 0;
		}
	}
	/**
	 * 进入添加立项计划申请时的处理
	 */
	public void doWithToAdd(ProjectGranted projectGranted){
		projectGranted.setAuditstatus(0);
		projectGranted.setApplicantSubmitStatus(0);
		projectGranted.setApplicantSubmitDate(null);
		
		projectGranted.setDeptInstAuditDate(null);
		projectGranted.setDeptInstAuditOpinion(null);
		projectGranted.setDeptInstAuditor(null);
		projectGranted.setDeptInstAuditorDept(null);
		projectGranted.setDeptInstAuditorInst(null);
		projectGranted.setDeptInstAuditorName(null);
		projectGranted.setDeptInstAuditResult(0);
		projectGranted.setDeptInstAuditStatus(0);
		
		projectGranted.setUniversityAuditDate(null);
		projectGranted.setUniversityAuditOpinion(null);
		projectGranted.setUniversityAuditor(null);
		projectGranted.setUniversityAuditorAgency(null);
		projectGranted.setUniversityAuditorName(null);
		projectGranted.setUniversityAuditorName(null);
		projectGranted.setUniversityAuditResult(0);
		projectGranted.setUniversityAuditStatus(0);
		
		projectGranted.setProvinceAuditDate(null);
		projectGranted.setProvinceAuditOpinion(null);
		projectGranted.setProvinceAuditor(null);
		projectGranted.setProvinceAuditorAgency(null);
		projectGranted.setProvinceAuditorName(null);
		projectGranted.setProvinceAuditResult(0);
		projectGranted.setProvinceAuditStatus(0);
		
		projectGranted.setFinalAuditDate(null);
		projectGranted.setFinalAuditOpinion(null);
		projectGranted.setFinalAuditOpinionFeedback(null);
		projectGranted.setFinalAuditor(null);
		projectGranted.setFinalAuditorAgency(null);
		projectGranted.setFinalAuditorDept(null);
		projectGranted.setFinalAuditorInst(null);
		projectGranted.setFinalAuditorName(null);
		projectGranted.setFinalAuditResult(0);
		projectGranted.setFinalAuditStatus(0);
		projectGranted.setFile(null);
		projectGranted.setAuditType(0);
		projectGranted.setCreateDate(null);
		
	}
	/**
	 * 获得当前业务设置状态,用于年检、中检、结项、变更、立项计划书
	 * @return 业务状态1:业务激活中，0:业务停止
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getBusinessStatus(String code, String appId){
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		Map map = new HashMap();
		map.put("code", code);
		map.put("appId", appId);
		String hql = "select b.status from Business b, " + projectApplication.getApplicationClassName() + " app left outer join b.subType so where so.code=? and " +
			"app.id=? and app.year >= b.startYear and (app.year <= b.endYear or b.endYear = -1)";
		List l = dao.query(hql, code, appId);
		if(l != null && l.size() > 0) {
			int status = (Integer) l.get(0);
			return status;//1:业务激活中，0:业务停止
		}else{
			return 0;
		}
	}
	
	/**
	 * 判断当前业务是否在时间有效期内，用于申请、选题（重大攻关项目）
	 * @param accountType 当前账号类型
	 * @param businessType 当前业务类型:011一般项目申请，021基地项目申请，031后期资助项目申请，041重大攻关项目招标，051委托应急课题申请
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	@SuppressWarnings("rawtypes")
	public String checkIfTimeValidate(AccountType accountType, String businessType){
		String deadline = "";//申请审核截止时间
		String hql = "select b.applicantDeadline, b.deptInstDeadline, b.univDeadline, b.provDeadline from Business b left outer join " +
				"b.subType so where so.code = ? and so.standard = 'businessType'";
		List list = dao.query(hql, businessType);
		if(list.size() != 0){
			if(accountType.compareTo(AccountType.MINISTRY) > 0){
				if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){
					Date date1 = (Date) ((Object[])list.get(0))[0];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
					Date date1 = (Date) ((Object[])list.get(0))[1];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
					Date date1 = (Date) ((Object[])list.get(0))[2];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.PROVINCE)){
					Date date1 = (Date) ((Object[])list.get(0))[3];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				return deadline;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 判断当前业务是否在时间有效期内，用于专家评审
	 * @param businessType 当前业务类型
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	@SuppressWarnings("rawtypes")
	public String checkIfTimeValidate(String businessType){
		String deadline = "";//申请审核截止时间
		String hql = "select b.reviewDeadline from Business b left outer join " +
				"b.subType so where so.code = ? and so.standard = 'businessType'";
		List list = dao.query(hql, businessType);
		if(list.size() != 0){
			Date date1 = (Date) list.get(0);
			if(date1 != null){
				deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
			}else{
				deadline = null;
			}
			return deadline;
		}else{
			return null;
		}
	}
	
	/**
	 * 判断当前业务是否在时间有效期内，用于年检、中检、结项、变更
	 * @param accountType 当前账号类型
	 * @param businessType 当前业务类型
	 * @param appId 当前项目申请id
	 * @return deadline 各级别审核截止时间，格式为字符串
	 */
	@SuppressWarnings("rawtypes")
	public String checkIfTimeValidate(AccountType accountType, String businessType, String appId){
		ProjectApplication projectApplication = (ProjectApplication)dao.query(ProjectApplication.class, appId);
		String deadline = "";//申请审核截止时间
		String hql = "select b.applicantDeadline, b.deptInstDeadline, b.univDeadline, b.provDeadline from Business b, " + projectApplication.getApplicationClassName() + " app " +
				"left outer join b.subType so where so.code = ? and app.id=? and app.year >= b.startYear and (app.year <= b.endYear or b.endYear = -1)";
		List list = dao.query(hql, businessType, appId);
		if(list.size() != 0){
			if(accountType.compareTo(AccountType.MINISTRY) > 0){
				if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)){
					Date date1 = (Date) ((Object[])list.get(0))[0];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
					Date date1 = (Date) ((Object[])list.get(0))[1];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
					Date date1 = (Date) ((Object[])list.get(0))[2];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				if(accountType.equals(AccountType.PROVINCE)){
					Date date1 = (Date) ((Object[])list.get(0))[3];
					if(date1 != null){
						deadline = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					}else{
						deadline = null;
					}
				}
				return deadline;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 获得当前业务对象设定的年度, 用于申请、选题
	 * @param code 当前业务的code
	 * @param standard 当前业务的standard
	 * @return 业务对象年度
	 */
	@SuppressWarnings("rawtypes")
	public Integer getBusinessDefaultYear(String code, String standard){
		String hql = "select b.startYear from Business b left outer join b.subType so where so.code = ? and so.standard = ?";
		List l = dao.query(hql, code, standard);
		if(l != null && l.size() == 1 && null != l.get(0)) {
			int year = (Integer) l.get(0);
			return year;
		}else{
			return null;
		}
	}

	/**
	 * 获得当前业务对象设定的年度, 用于年检
	 * @param code 当前业务的code
	 * @param standard 当前业务的standard
	 * @return 业务年份
	 */
	@SuppressWarnings("rawtypes")
	public Integer getBusinessAnnDefaultYear(String code, String standard){
		String hql = "select b.businessYear from Business b left outer join b.subType so where so.code = ? and so.standard = ?";
		List l = dao.query(hql, code, standard);
		if(l != null && l.size() == 1 && null != l.get(0)) {
			int year = (Integer) l.get(0);
			return year;
		}else{
			return null;
		}
	}
	
	//项目管理首页业务处理
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目处理业务列表
	 * @param accountId
	 * @return 当前账号所有要处理的项目业务，包括项目名称、项目类型、业务类型；
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getTeacherBusinessByAccount(String accountId, String projectType) {
		Map<String, String> projectMap = this.getMapByProjectType(projectType);
		String BusinessName = projectMap.get("BusinessName");
		String grantedClassName = projectMap.get("grantedClassName");
		String projectNumber = projectMap.get("projectNumber");
		Map<String, List> gmap = new HashMap<String, List>();
		List<List> resultList = new ArrayList<List>();
		List defaultList = new ArrayList();
		defaultList.add(0, "");
		defaultList.add(1, "--");
		for(int k = 0; k < 4; k++){
			resultList.add(k, defaultList);
		}
		Account account = (Account) dao.query(Account.class, accountId);
		AccountType accountType;//账号类型
//		String belongId;//账号所属id
		if(account == null){// 账号不存在
			return null;
		}else{
			accountType = account.getType();
//			belongId = account.getBelongId();
		}
		Map map = new HashMap();//参数map
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date());// 获取系统当前时间并转化为字符串
		if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
			// 从项目成员对应关系表中，找到当前教师或学生作为项目负责人的项目，从项目业务表中，根据项目申请年份处于业务对象处理年份之间
			// 以及业务是否处理中，当前时间处于个人业务办理时间之内
			map.clear();
			map.put("date", date);
			map.put("projectNumber", projectNumber);
			StringBuffer str = new StringBuffer("select so.name, so.code, b.startYear, b.endYear, b.startDate from Business b left outer join b.subType so " +
					"where b.status = 1 and so.standard = 'businessType' and so.systemOption.code =:projectNumber and to_char(b.startDate, 'yyyy-MM-dd')<= :date " +
					"and (to_char(b.applicantDeadline, 'yyyy-MM-dd')>= :date or b.applicantDeadline is null)");
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			if(business != null && !business.isEmpty()){
				int i = business.size();
				StringBuffer annDealHql = new StringBuffer();//年度查询待处理数
				StringBuffer midDealHql = new StringBuffer();//中检查询待处理数
				StringBuffer endDealHql = new StringBuffer();//结项查询待处理数
				StringBuffer varDealHql = new StringBuffer();//变更查询待处理数
				String addHql;//附加查询条件
				int deal = -1;//待处理数
				Map session = ActionContext.getContext().getSession();
				for(int j = 0; j < i; j++){
					Object[] o = (Object[]) business.get(j);
					String type = (String)o[1];
					int startYear = (Integer) o[2];//项目起始年份
					int endYear = (Integer) o[3];//项目终止年份
					Date startDate = (Date) o[4];//业务起始时间
				    if (type.equals(projectNumber + "2")){//项目中检
						//查询待处理0x2:012(一般项目中检);022(基地项目中检);042(重大攻关项目中检)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							map.put("belongId", this.getBelongIdByAccount(account));
							midDealHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join " +
									"app." + projectMap.get("memberSet") + " mem join gra." + projectMap.get("midiSet") + " midi where gra.status=1 and mem.member.id = :belongId and " +
									"mem.isDirector = 1 and midi.status<=1 and midi.finalAuditResult=0");
							addHql = this.grantedInSearch(account);
							midDealHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							midDealHql.append(" and (");
							midDealHql.append("(midi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							midDealHql.append(")");
						} else {
							midDealHql = new StringBuffer(midDealHql.toString().substring(0, midDealHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							midDealHql.append(" or (");
							midDealHql.append("midi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							midDealHql.append(")");
						}
						midDealHql.append(")");
					}else if(type.equals(projectNumber + "3")){//项目结项
						//查询待处理0x3:013(一般项目结项);023(基地项目结项);033(后期资助项目结项);043(重大攻关项目结项);053(委托应急课题结项)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							map.put("belongId", this.getBelongIdByAccount(account));
							endDealHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join " +
									"app." + projectMap.get("memberSet") + " mem join gra." + projectMap.get("endiSet") + " endi where gra.status=1 and mem.member.id = :belongId and " +
									"mem.isDirector = 1 and endi.status<=1 and endi.finalAuditResultEnd=0");
							addHql = this.grantedInSearch(account);
							endDealHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							endDealHql.append(" and (");
							endDealHql.append("(endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endDealHql.append(")");
						} else {
							endDealHql = new StringBuffer(endDealHql.toString().substring(0, endDealHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							endDealHql.append(" or (");
							endDealHql.append("endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j :"")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endDealHql.append(")");
						}
						endDealHql.append(")");
					}else if(type.equals(projectNumber + "4")){//项目变更
						//查询待处理0x4:014(一般项目变更);024(基地项目变更);034(后期资助项目变更);044(重大攻关项目变更);054(委托应急课题变更)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							map.put("belongId", this.getBelongIdByAccount(account));
							varDealHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join " +
									"app." + projectMap.get("memberSet") + " mem join gra." + projectMap.get("variSet") + " vari where gra.status=1 and mem.member.id = :belongId and " +
									"mem.isDirector = 1 and vari.status<=1 and vari.finalAuditResult=0");
							addHql = this.grantedInSearch(account);
							varDealHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							varDealHql.append(" and (");
							varDealHql.append("(vari.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j :"")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							varDealHql.append(")");
						} else {
							varDealHql = new StringBuffer(varDealHql.toString().substring(0, varDealHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							varDealHql.append(" or (");
							varDealHql.append("vari.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j :"")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							varDealHql.append(")");
						}
						varDealHql.append(")");
					}else if(type.equals(projectNumber + "5")){//项目年检
						//查询待处理0x5:015(一般项目年检);025(基地项目年检);035(后期资助项目年检);045(重大攻关项目年检)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							map.put("belongId", this.getBelongIdByAccount(account));
							annDealHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join " +
									"app." + projectMap.get("memberSet") + " mem join gra." + projectMap.get("annSet") + " ann where gra.status=1 and mem.member.id = :belongId and " +
									"mem.isDirector = 1 and ann.status<=1 and ann.finalAuditResult=0");
							addHql = this.grantedInSearch(account);
							annDealHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							annDealHql.append(" and (");
							annDealHql.append("(ann.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j :"")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							annDealHql.append(")");
						} else {
							annDealHql = new StringBuffer(annDealHql.toString().substring(0, annDealHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							annDealHql.append(" or (");
							annDealHql.append("ann.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j :"")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							annDealHql.append(")");
						}
						annDealHql.append(")");
					}else{
						;
					}
				}
				//年检待处理数量
				if(!annDealHql.toString().equals("")){
					List al5 = dao.query(annDealHql.toString(), map);
					if(al5.size()>0){
						deal = al5.size();
						List list5 = new ArrayList();
						list5.add(0, projectNumber + "5");
						list5.add(1, deal);
						resultList.set(0, list5);
					}
				}
				//中检待处理数量
				if(!midDealHql.toString().equals("")){
					List al1 = dao.query(midDealHql.toString(), map);
					if(al1.size()>0){
						deal = al1.size();
						List list1 = new ArrayList();
						list1.add(0, projectNumber + "2");
						list1.add(1, deal);
						resultList.set(1, list1);
					}
				}
				//结项待处理数量
				if(!endDealHql.toString().equals("")){
					List al2 = dao.query(endDealHql.toString(), map);
					if(al2.size()>0){
						deal = al2.size(); 
						List list2 = new ArrayList();
						list2.add(0, projectNumber + "3");
						list2.add(1, deal);
						resultList.set(2, list2);
					}
				}
				//变更待处理数量
				if(!varDealHql.toString().equals("")){
					List al3 = dao.query(varDealHql.toString(), map);
					if(al3.size()>0){
						deal = al3.size();
						List list3 = new ArrayList();
						list3.add(0, projectNumber + "4");
						list3.add(1, deal);
						resultList.set(3, list3);
					}
				}
			}
			int flag = 0;
			for(int i = 0; i < 4; i++){
				if(!resultList.get(i).get(1).equals("--") && (Integer)resultList.get(i).get(1) != 0){
					flag = 1;
					break;
				}
			}
			if(1 == flag){
				gmap.put(BusinessName, resultList);
				return gmap;
			}else{
				return null;
			}
		}else{//账号类型出错
			return null;
		}
	}
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目评审业务列表
	 * @param accountId
	 * @return 当前账号所有要处理的评审业务，包括需处理的评审总数及已待评审数；
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getTeacherReviewProjectByAccount(String accountId, String projectType){
		Map<String, String> projectMap = this.getMapByProjectType(projectType);
		String BusinessName = projectMap.get("BusinessName");
		String applicationClassName = projectMap.get("applicationClassName");
		String grantedClassName = projectMap.get("grantedClassName");
		String appRevClassName = projectMap.get("appRevClassName");
		String endRevClassName = projectMap.get("endRevClassName");
		String projectNumber = projectMap.get("projectNumber");
		Map<String, List> gmap = new HashMap<String, List>();
		List<List> resultList = new ArrayList<List>();
		List defaultList = new ArrayList();
		defaultList.add(0, "");
		defaultList.add(1, "--");
		for(int k = 0; k < 2; k++){
			resultList.add(k, defaultList);
		}
		AccountType accountType;//账号类型
//		String belongId;//账号所属id
		Account account = (Account) dao.query(Account.class, accountId);
		if(account == null){// 账号不存在
			return null;
		}else{
			accountType = account.getType();
//			belongId = account.getBelongId();
		}
		Map map = new HashMap();//参数map
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date());// 获取系统当前时间并转化为字符串
		if (accountType.equals(AccountType.ADMINISTRATOR)){//管理员
			return null;
		}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){//教师或外部专家
			map.clear();
			map.put("date", date);
			map.put("projectNumber", projectNumber);
			StringBuffer str = new StringBuffer("select so.name, so.code, b.startYear, b.endYear, b.startDate from Business b left outer join b.subType so where " +
					"b.status = 1 and so.standard = 'businessType' and so.systemOption.code =:projectNumber and to_char(b.startDate, 'yyyy-MM-dd') <= :date and " +
					"(to_char(b.reviewDeadline, 'yyyy-MM-dd') >= :date or b.reviewDeadline is null)");
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			if (business!=null && !business.isEmpty()){
				int i = business.size();//当前业务数
				StringBuffer appReviewAuditHql = new StringBuffer();//查询申请待评审数
				StringBuffer endReviewAuditHql = new StringBuffer();//查询结项待评审数
				int review = -1;//待评审数
				Map session = ActionContext.getContext().getSession();
				for(int j = 0; j < i; j++){
					Object[] o = (Object[]) business.get(j);
					String type = (String) o[1];//业务类型
					int startYear = (Integer) o[2];//项目起始年份
					int endYear = (Integer) o[3];//项目终止年份
					Date startDate = (Date) o[4];//业务起始时间
					if(type.equals(projectNumber + "1")){//项目申请
						//查询待评审0x1:011(一般项目申请);021(基地项目申请);031(后期资助项目申请)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("appReviewMap", map);
							map.put("belongId", this.getBelongIdByAccount(account) );
							map = (Map) session.get("appReviewMap");
							appReviewAuditHql = new StringBuffer("select distinct app.id from " + applicationClassName + " app, " + appRevClassName + " appRev " +
									"where appRev.application.id = app.id and appRev.reviewer.id=:belongId and app.status >=6 and " +
									"(appRev.submitStatus!=3 or (app.reviewStatus!=3 and appRev.reviewerSn=1))");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							appReviewAuditHql.append(" and (");
							appReviewAuditHql.append("(app.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							appReviewAuditHql.append(")");
						} else {
							appReviewAuditHql = new StringBuffer(appReviewAuditHql.toString().substring(0, appReviewAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							appReviewAuditHql.append(" or (");
							appReviewAuditHql.append("app.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							appReviewAuditHql.append(")");
						}
						appReviewAuditHql.append(")");
					}else if(type.equals(projectNumber + "3")){//项目结项
						//查询待评审0x3:013(一般项目结项);023(基地项目结项);033(后期资助项目结项)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("endReviewMap", map);
							map.put("belongId", this.getBelongIdByAccount(account));
							map = (Map) session.get("endReviewMap");
							endReviewAuditHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra, " + endRevClassName + " endRev join gra.application app join " +
									"gra." + projectMap.get("endiSet") + " endi where gra.status = 1 and endRev.endinspection.id = endi.id and endRev.reviewer.id=:belongId and endi.status >=6 and " +
									"(endRev.submitStatus!=3 or (endi.reviewStatus!=3 and endRev.reviewerSn=1))");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							endReviewAuditHql.append(" and (");
							endReviewAuditHql.append("(endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endReviewAuditHql.append(")");
						} else {
							endReviewAuditHql = new StringBuffer(endReviewAuditHql.toString().substring(0, endReviewAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							endReviewAuditHql.append(" or (");
							endReviewAuditHql.append("endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endReviewAuditHql.append(")");
						}
						endReviewAuditHql.append(")");
					}else{
						;
					}
				}
				//申请待评审数量
				if(!appReviewAuditHql.toString().equals("")){
					List al = dao.query(appReviewAuditHql.toString(), map);
					if(al.size()>0){
						review = al.size();
						List list1 = new ArrayList();
						list1.add(0, projectNumber + "1");
						list1.add(1, review);
						resultList.set(0, list1);
					}
				}
				//结项待评审数量
				if(!endReviewAuditHql.toString().equals("")){
					List al = dao.query(endReviewAuditHql.toString(), map);
					if(al.size()>0){
						review = al.size();
						List list2 = new ArrayList();
						list2.add(0, projectNumber + "3");
						list2.add(1, review);
						resultList.set(1, list2);
					}
				}
			}
			int flag = 0;
			for(int i = 0; i < 2; i++){
				if(!resultList.get(i).get(1).equals("--") && (Integer)resultList.get(i).get(1) != 0){
					flag = 1;
					break;
				}
			}
			if(1 == flag){
				gmap.put(BusinessName, resultList);
				return gmap;
			}else{
				return null;
			}
		}else{//账号类型出错
			return null;
		}
	}
	
	/**
	 * 根据账号ID获得登录首页中，用于显示的项目处理业务列表
	 * @param accountId
	 * @return 当前账号所有要处理的项目待审业务数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getManagerBusinessByAccount(String accountId, String projectType) {
		Map<String, String> projectMap = this.getMapByProjectType(projectType);
		String BusinessName = projectMap.get("BusinessName");
		String applicationClassName = projectMap.get("applicationClassName");
		String grantedClassName = projectMap.get("grantedClassName");
		String projectNumber = projectMap.get("projectNumber");
		
		Map<String, List> gmap = new HashMap<String, List>();
		List<List> resultList = new ArrayList<List>();
		List defaultList = new ArrayList();
		defaultList.add(0, "");
		defaultList.add(1, "--");
		for(int k = 0; k < 5; k++){
			resultList.add(k, defaultList);
		}
		Account account = (Account) dao.query(Account.class, accountId);
		AccountType accountType;//账号类型
		if (account == null){// 账号不存在
			return null;
		}else{
			accountType = account.getType();
		}
		Map map = new HashMap();//参数map
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date());// 获取系统当前时间
		if (accountType.equals(AccountType.ADMINISTRATOR)){//管理员
			return null;
		}else if(accountType.within(AccountType.MINISTRY, AccountType.INSTITUTE)){//各级管理人员
			map.clear();
			map.put("date", date);
			map.put("projectNumber", projectNumber);
			StringBuffer str = new StringBuffer("select so.name, so.code, b.startYear, b.endYear, b.startDate from Business b " +
				"left outer join b.subType so where b.status = 1 and so.standard = 'businessType' and so.systemOption.code =:projectNumber and " +
				"to_char(b.startDate, 'yyyy-MM-dd')<= :date");
			if (accountType.equals(AccountType.PROVINCE)){
				str.append(" and (to_char(b.provDeadline, 'yyyy-MM-dd')>=:date or b.provDeadline is null)");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				str.append(" and (to_char(b.univDeadline, 'yyyy-MM-dd')>=:date or b.univDeadline is null)");
			}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				str.append(" and (to_char(b.deptInstDeadline, 'yyyy-MM-dd')>=:date or b.deptInstDeadline is null)");
			}
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			if (business != null && !business.isEmpty()){
				int i = business.size();//当前业务数
				StringBuffer appAuditHql = new StringBuffer();//查询申请待审核数
				StringBuffer annAuditHql = new StringBuffer();//查询年检待审核数
				StringBuffer midAuditHql = new StringBuffer();//查询中检待审核数
				StringBuffer endAuditHql = new StringBuffer();//查询结项待审核数
				StringBuffer varAuditHql = new StringBuffer();//查询变更待审核数
				String addHql;//附加查询条件
				int audit = -1;//待审核数
				Map session = ActionContext.getContext().getSession();
				for(int j = 0; j < i; j++){
					Object[] o = (Object[]) business.get(j);//当前业务信息
					String type = (String) o[1];//业务类型
					int startYear = (Integer) o[2];//项目起始年份
					int endYear = (Integer) o[3];//项目终止年份
					Date startDate = (Date) o[4];//业务起始时间
					if(type.equals(projectNumber + "1")){//项目申请
						//查询待审核0x1:011(一般项目申请);021(基地项目申请);031(后期资助项目申请);041(重大攻关项目申请);051(委托应急课题项目申请)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("applicationMap", map);
							appAuditHql = new StringBuffer("select distinct app.id from " + applicationClassName + " app left outer join app.university uni " +
									"left outer join app.department dep left outer join app.institute ins where ");
							if (accountType.equals(AccountType.MINISTRY)){
								appAuditHql.append("(app.status >= 5 or app.createMode=1 or app.createMode=2) and (app.finalAuditStatus=0 or app.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								appAuditHql.append("app.status >= 4 and (app.provinceAuditStatus=0 or app.provinceAuditStatus=1 or app.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								appAuditHql.append("app.status >= 3 and (app.universityAuditStatus=0 or app.universityAuditStatus=1 or app.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								appAuditHql.append("app.status >= 2 and (app.deptInstAuditStatus=0 or app.deptInstAuditStatus=1 or app.deptInstAuditStatus=2)");
							}
							addHql = this.applicationInSearch(account);
							appAuditHql.append(addHql);
							map = (Map) session.get("applicationMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							appAuditHql.append(" and (");
							appAuditHql.append("(app.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							appAuditHql.append(")");
						} else {
							appAuditHql = new StringBuffer(appAuditHql.toString().substring(0, appAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							appAuditHql.append(" or (");
							appAuditHql.append("app.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							appAuditHql.append(")");
						}
						appAuditHql.append(")");
					}else if(type.equals(projectNumber + "2")){//项目中检
						//查询待审核0x2:012(一般项目中检);022(基地项目中检);042(重大攻关项目中检)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							midAuditHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join gra." + projectMap.get("midiSet") + " all_midi join gra." + projectMap.get("midiSet") +" midi "+
								"left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins where gra.status=1");
							if (accountType.equals(AccountType.MINISTRY)){
								midAuditHql.append(" and (midi.status >= 5 or midi.createMode=1 or midi.createMode=2) and (midi.finalAuditStatus=0 or midi.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								midAuditHql.append(" and midi.status >= 4 and (midi.provinceAuditStatus=0 or midi.provinceAuditStatus=1 or midi.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								midAuditHql.append(" and midi.status >= 3 and (midi.universityAuditStatus=0 or midi.universityAuditStatus=1 or midi.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								midAuditHql.append(" and midi.status >= 2 and (midi.deptInstAuditStatus=0 or midi.deptInstAuditStatus=1 or midi.deptInstAuditStatus=2)");
							}
							addHql = this.grantedInSearch(account);
							midAuditHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							midAuditHql.append(" and (");
							midAuditHql.append("(midi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							midAuditHql.append(")");
						} else {
							midAuditHql = new StringBuffer(midAuditHql.toString().substring(0, midAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							midAuditHql.append(" or (");
							midAuditHql.append("midi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							midAuditHql.append(")");
						}
						midAuditHql.append(")");
						midAuditHql.append(" group by gra.id, midi.applicantSubmitDate having midi.applicantSubmitDate = max(all_midi.applicantSubmitDate) ");
					}else if(type.equals(projectNumber + "3")){//项目结项
						//查询待审核0x3:013(一般项目结项);023(基地项目结项);033(后期资助项目结项);043(重大攻关项目结项);053(委托应急课题结项)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							endAuditHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join gra." + projectMap.get("endiSet") + " all_endi join gra." + projectMap.get("endiSet") + " endi " +
									"left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins where (gra.status=1 or gra.status=5)");
							if (accountType.equals(AccountType.MINISTRY)){
								endAuditHql.append(" and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) and (endi.ministryAuditStatus=0 or endi.ministryAuditStatus=2 or " +
										"endi.finalAuditStatus=0 or endi.finalAuditStatus=2) and endi.finalAuditStatus<>3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								endAuditHql.append(" and endi.status >= 4 and (endi.provinceAuditStatus=0 or endi.provinceAuditStatus=1 or endi.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								endAuditHql.append(" and endi.status >= 3 and (endi.universityAuditStatus=0 or endi.universityAuditStatus=1 or endi.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								endAuditHql.append(" and endi.status >= 2 and (endi.deptInstAuditStatus=0 or endi.deptInstAuditStatus=1 or endi.deptInstAuditStatus=2)");
							}
							addHql = this.grantedInSearch(account);
							endAuditHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							endAuditHql.append(" and (");
							endAuditHql.append("(endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endAuditHql.append(")");
						} else {
							endAuditHql = new StringBuffer(endAuditHql.toString().substring(0, endAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							endAuditHql.append(" or (");
							endAuditHql.append("endi.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							endAuditHql.append(")");
						}
						endAuditHql.append(")");
						endAuditHql.append("  group by gra.id, endi.applicantSubmitDate having endi.applicantSubmitDate = max(all_endi.applicantSubmitDate) ");
					}else if(type.equals(projectNumber + "4")){//项目变更
						//查询待审核0x4:014(一般项目变更);024(基地项目变更);034(后期资助项目变更);044(重大攻关项目变更);054(委托应急课题变更)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							varAuditHql = new StringBuffer("select distinct gra.id from " + grantedClassName + " gra join gra.application app join gra." + projectMap.get("variSet") + " all_vari join gra."+ projectMap.get("variSet") +" vari " +
									"left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins where gra.status=1");
							if(accountType.equals(AccountType.MINISTRY)){
								varAuditHql.append(" and (vari.status >= 5 or vari.createMode=1 or vari.createMode=2) and (vari.finalAuditStatus=0 or vari.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								varAuditHql.append(" and vari.status >= 4 and (vari.provinceAuditStatus=0 or vari.provinceAuditStatus=1 or vari.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								varAuditHql.append(" and vari.status >= 3 and (vari.universityAuditStatus=0 or vari.universityAuditStatus=1 or vari.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								varAuditHql.append(" and vari.status >= 2 and (vari.deptInstAuditStatus=0 or vari.deptInstAuditStatus=1 or vari.deptInstAuditStatus=2)");
							}
							addHql = this.grantedInSearch(account);
							varAuditHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							varAuditHql.append(" and (");
							varAuditHql.append("(vari.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							varAuditHql.append(")");
						} else {
							varAuditHql = new StringBuffer(varAuditHql.toString().substring(0, varAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							varAuditHql.append(" or (");
							varAuditHql.append("vari.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							varAuditHql.append(")");
						}
						varAuditHql.append(")");
						varAuditHql.append(" group by gra.id, vari.applicantSubmitDate having vari.applicantSubmitDate = max(all_vari.applicantSubmitDate) ");
					}else if(type.equals(projectNumber + "5")){//项目年检
						//查询待审核0x5:015(一般项目年检);025(基地项目年检);035(后期资助项目年检);045(重大攻关项目年检)
						counterMap.put(type, (null != counterMap.get(type)) ? counterMap.get(type) + 1 : 1);
						if (counterMap.get(type) == 1){//循环查询中只在第一次查询拼接的条件
							session.put("grantedMap", map);
							annAuditHql = new StringBuffer("select distinct gra.id, gra.name from " + grantedClassName + " gra join gra.application app join gra." + projectMap.get("annSet") + " all_ann join gra." + projectMap.get("annSet") + " ann " + 
								"left outer join gra.university uni left outer join gra.department dep left outer join gra.institute ins where gra.status=1");
							if (accountType.equals(AccountType.MINISTRY)){
								annAuditHql.append(" and (ann.status >= 5 or ann.createMode=1 or ann.createMode=2) and (ann.finalAuditStatus=0 or ann.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								annAuditHql.append(" and ann.status >= 4 and (ann.provinceAuditStatus=0 or ann.provinceAuditStatus=1 or ann.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								annAuditHql.append(" and ann.status >= 3 and (ann.universityAuditStatus=0 or ann.universityAuditStatus=1 or ann.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								annAuditHql.append(" and ann.status >= 2 and (ann.deptInstAuditStatus=0 or ann.deptInstAuditStatus=1 or ann.deptInstAuditStatus=2)");
							}
							addHql = this.grantedInSearch(account);
							annAuditHql.append(addHql);
							map = (Map) session.get("grantedMap");
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + j, startYear);
						map.put("endYear" + j, endYear);
						map.put("startDate" + j, startDate);
						if (counterMap.get(type) == 1) {
							annAuditHql.append(" and (");
							annAuditHql.append("(ann.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							annAuditHql.append(")");
						} else {
							annAuditHql = new StringBuffer(annAuditHql.toString().substring(0, annAuditHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							annAuditHql.append(" or (");
							annAuditHql.append("ann.applicantSubmitDate >= :startDate" + j)
								.append((startYear != -1) ? " and app.year >= :startYear" + j : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + j : "");
							annAuditHql.append(")");
						}
						annAuditHql.append(")");
						annAuditHql.append(" group by gra.id, gra.name, ann.applicantSubmitDate having ann.applicantSubmitDate = max(all_ann.applicantSubmitDate) ");
					}else{
						;
					}
				}
				//申请待审核数量
				if(!appAuditHql.toString().equals("")){
					List al1 = dao.query(appAuditHql.toString(), map);
					if(al1.size()>0){
						audit = al1.size();
						List list1 = new ArrayList();
						list1.add(0, projectNumber + "1");
						list1.add(1, audit);
						resultList.set(0, list1);
					}
				}
				//年检待审核数量
				if(!annAuditHql.toString().equals("")){
					List al5 = dao.query(annAuditHql.toString(), map);
					if(al5.size()>0){
						audit = al5.size();
						List list5 = new ArrayList();
						list5.add(0, projectNumber + "5");
						list5.add(1, audit);
						resultList.set(1, list5);
					}
				}
				//中检待审核数量
				if(!midAuditHql.toString().equals("")){
					List al2 = null;
					try {
						al2 = dao.query(midAuditHql.toString(), map);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					if(al2.size()>0){
						audit = al2.size();
						List list2 = new ArrayList();
						list2.add(0, projectNumber + "2");
						list2.add(1, audit);
						resultList.set(2, list2);
					}
				}
				//结项待审核数量
				if(!endAuditHql.toString().equals("")){
					List al3 = dao.query(endAuditHql.toString(), map);
					if(al3.size()>0){
						audit = al3.size();
						List list3 = new ArrayList();
						list3.add(0, projectNumber + "3");
						list3.add(1, audit);
						resultList.set(3, list3);
					}
				}
				//变更待审核数量
				if(!varAuditHql.toString().equals("")){
					List al4 = dao.query(varAuditHql.toString(), map);
					if(al4.size()>0){
						audit = al4.size();
						List list4 = new ArrayList();
						list4.add(0, projectNumber + "4");
						list4.add(1, audit);
						resultList.set(4, list4);
					}
				}
			}
			int flag = 0;
			for(int i = 0; i < 5; i++){
				if(!resultList.get(i).get(1).equals("--") && (Integer)resultList.get(i).get(1) != 0){
					flag = 1;
					break;
				}
			}
			if(1 == flag){
				gmap.put(BusinessName, resultList);
				return gmap;
			}else{
				return null;
			}
		}else{//账号类型出错
			return null;
		}
	}
	
	/**
	 * 根据账号ID获得登录首页中，用于显示专家评审（鉴定）截止时间
	 * @param accountId
	 * @return 专家评审（鉴定）截止时间
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getReviewDeadlineByAccount(String accountId, String projectType){ 
		Map<String, String> projectMap = this.getMapByProjectType(projectType);
		String appBusinessName = projectMap.get("appBusinessName");
		String endBusinessName = projectMap.get("endBusinessName");
		String grantedClassName = projectMap.get("grantedClassName");
		String projectNumber = projectMap.get("projectNumber");
		String appRevClassName = projectMap.get("appRevClassName");
		String endRevClassName = projectMap.get("endRevClassName");

		HashMap<String, List> gtmap = new HashMap<String, List>();
		List<List> appResultList = new ArrayList<List>();
		List<List> endResultList = new ArrayList<List>();
		Set<List> set = new HashSet<List>();
		AccountType accountType;//账号类型
		Account account = (Account) dao.query(Account.class, accountId);
		if (account == null){// 账号不存在
			return null;
		}else{
			accountType = account.getType();
		}
		String code = projectNumber;
		String appReviewDeadline = "";
		String endReviewDeadline = "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date());// 获取系统当前时间并转化为字符串
		if (accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){
			//申请待评审
			String appHql = "select b.reviewDeadline, b.startYear, b.endYear from " + appRevClassName + " appRev, Business b " +
					"left outer join appRev.application app where appRev.reviewer.id=? and appRev.submitStatus <> 3 and " +
					"to_char(b.reviewDeadline, 'yyyy-MM-dd')>=? and b.subType.code=? and b.startYear<=app.year and (b.endYear>=app.year or b.endYear = -1)";
			List appList = dao.query(appHql, this.getBelongIdByAccount(account), date, code + "1");
			if(appList.size()>0){
				for(int i = 0; i < appList.size(); i++){
					Object[] arr = (Object[])appList.get(i);
					Date deadline = (Date) arr[0];
					int startYear = (Integer) arr[1];
					int endYear = (Integer) arr[2];
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					appReviewDeadline = sdf.format(deadline);
					List list = new ArrayList();
					list.add(0, appReviewDeadline);
					list.add(1, startYear);
					list.add(2, endYear);
					set.add(list);
				}
			}
			if (null != set){
				appResultList.addAll(set);
			}
			//结项待鉴定
			String endHql = "select b.reviewDeadline, b.startYear, b.endYear from " + endRevClassName + " endRev, Business b, " + grantedClassName + " gra " +
				"left outer join endRev.endinspection endi left outer join gra.application app where endRev.reviewer.id=? " +
				"and endRev.submitStatus <> 3 and endi.granted.id = gra.id and to_char(b.reviewDeadline, 'yyyy-MM-dd')>=? and " +
				"b.subType.code=? and b.startYear<=app.year and (b.endYear>=app.year or b.endYear = -1)";
			List endList = dao.query(endHql, this.getBelongIdByAccount(account), date, code + "3");
			if(endList.size()>0){
				for(int i = 0; i < endList.size(); i++){
					Object[] arr = (Object[])endList.get(i);
					Date deadline = (Date) arr[0];
					int startYear = (Integer) arr[1];
					int endYear = (Integer) arr[2];
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					endReviewDeadline = sdf.format(deadline);
					List list = new ArrayList();
					list.add(0, endReviewDeadline);
					list.add(1, startYear);
					list.add(2, endYear);
					set.add(list);
				}
			}
			if (null != set){
				endResultList.addAll(set);
			}
			if(!appReviewDeadline.equals("")){
				gtmap.put(appBusinessName, appResultList);
			}
			if(!endReviewDeadline.equals("")){
				gtmap.put(endBusinessName, endResultList);
			}
			return gtmap;
		}else{//账号类型出错
			return null;
		}
	}
	
	/**
	 * 根据账号及项目管理系统首页的参数设置查询
	 * @param account 当前账号
	 * @param mainFlag 首页参数
	 * @return 查询条件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String mainSearch(Account account, String mainFlag, String projectType){
		AccountType accountType;//账号类型
		if(account == null){// 账号不存在
			return "";
		}else{
			accountType = account.getType();
		}
		Map<String, String> projectMap = this.getMapByProjectType(projectType);
		String projectNumber = projectMap.get("projectNumber");
		Map map = new HashMap();//参数map
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(new Date());
		if(accountType.equals(AccountType.ADMINISTRATOR)){//管理员
			return "";
		}else if(accountType.within(AccountType.MINISTRY, AccountType.INSTITUTE)){//各级管理人员
			map.clear();
			map.put("date", date);
			String businessType = "";
			if (null != mainFlag && !mainFlag.isEmpty()){
				businessType = mainFlag.substring(0, 3);
			}else{
				return "";
			}
			map.put("businessType", businessType);
			StringBuffer str = new StringBuffer("select b.startYear, b.endYear, b.startDate from Business b left outer join b.subType so where b.status = 1 and " +
					"so.code=:businessType and to_char(b.startDate, 'yyyy-MM-dd')<= :date");
			if(accountType.equals(AccountType.PROVINCE)){
				str.append(" and (to_char(b.provDeadline, 'yyyy-MM-dd')>=:date or b.provDeadline is null)");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
				str.append(" and (to_char(b.univDeadline, 'yyyy-MM-dd')>=:date or b.univDeadline is null)");
			}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
				str.append(" and (to_char(b.deptInstDeadline, 'yyyy-MM-dd')>=:date or b.deptInstDeadline is null)");
			}
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			
			if (null != business && !business.isEmpty()){
				StringBuffer searchHql = new StringBuffer();//返回值
				for(int i = 0; i < business.size(); i++) {
					Object[] o = (Object[]) business.get(i);
					int startYear = (Integer) o[0];//项目起始年份
					int endYear = (Integer) o[1];//项目终止年份
					Date startDate = (Date) o[2];//业务起始时间
					Map session = ActionContext.getContext().getSession();
					//根据账号及项目管理系统首页的参数设置查询
					if(mainFlag.equals(projectNumber + "11")){//项目申请已上报
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("applicationMap");
							session.put("applicationMap", map);
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (app.status >= 5 or vari.createMode=1 or vari.createMode=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and app.status >= 4 and app.createMode = 0");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and app.status >= 3 and app.createMode = 0");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and app.status >= 2 and app.createMode = 0");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "12")){//项目申请待审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("applicationMap");
							session.put("applicationMap", map);
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (app.status >= 5 or vari.createMode=1 or vari.createMode=2) and (app.finalAuditStatus=0 or app.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and app.status >= 4 and app.createMode = 0 and (app.provinceAuditStatus=0 or app.provinceAuditStatus=1 or app.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and app.status >= 3 and app.createMode = 0 and (app.universityAuditStatus=0 or app.universityAuditStatus=1 or app.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and app.status >= 2 and app.createMode = 0 and (app.deptInstAuditStatus=0 or app.deptInstAuditStatus=1 or app.deptInstAuditStatus=2)");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "13")){//项目申请已审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("applicationMap");
							session.put("applicationMap", map);
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (app.status >= 5 or vari.createMode=1 or vari.createMode=2) and app.finalAuditStatus=3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and app.status >= 4 and app.createMode = 0 and app.provinceAuditStatus=3");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and app.status >= 3 and app.createMode = 0 and app.universityAuditStatus=3");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and app.status >= 2 and app.createMode = 0 and app.deptInstAuditStatus=3");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "21")){//项目中检已上报
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (midi.status >= 5 or midi.createMode = 1)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and midi.status >= 4 and midi.createMode = 0");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and midi.status >= 3 and midi.createMode = 0");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and midi.status >= 2 and midi.createMode = 0");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "22")){//项目中检待审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (midi.status >= 5 or midi.createMode = 1) and (midi.finalAuditStatus=0 or midi.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and midi.status >= 4 and midi.createMode = 0 and (midi.provinceAuditStatus=0 or midi.provinceAuditStatus=1 or midi.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and midi.status >= 3 and midi.createMode = 0 and (midi.universityAuditStatus=0 or midi.universityAuditStatus=1 or midi.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and midi.status >= 2 and midi.createMode = 0 and (midi.deptInstAuditStatus=0 or midi.deptInstAuditStatus=1 or midi.deptInstAuditStatus=2)");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "23")){//项目中检已审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (midi.status >= 5 or midi.createMode = 1) and midi.finalAuditStatus=3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and midi.status >= 4 and midi.createMode = 0 and midi.provinceAuditStatus=3");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and midi.status >= 3 and midi.createMode = 0 and midi.universityAuditStatus=3");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and midi.status >= 2 and midi.createMode = 0 and midi.deptInstAuditStatus=3");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "32")){//项目结项待审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and (gra.status = 1 or gra.status = 5)");//status为5时表示鉴定状态
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) and (endi.ministryAuditStatus=0 or endi.ministryAuditStatus=2 or endi.finalAuditStatus=0 or endi.finalAuditStatus=2) and endi.finalAuditStatus<>3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and endi.status >= 4 and endi.createMode = 0 and (endi.provinceAuditStatus=0 or endi.provinceAuditStatus=1 or endi.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and endi.status >= 3 and endi.createMode = 0 and (endi.universityAuditStatus=0 or endi.universityAuditStatus=1 or endi.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and endi.status >= 2 and endi.createMode = 0 and (endi.deptInstAuditStatus=0 or endi.deptInstAuditStatus=1 or endi.deptInstAuditStatus=2)");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "33")){//项目结项已审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and (gra.status = 1 or gra.status = 5)");//status为5时表示鉴定状态
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (endi.status >= 5 or endi.createMode=1 or endi.createMode=2) and endi.finalAuditStatus=3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and endi.status >= 4 and endi.createMode = 0 and endi.provinceAuditStatus=3 and gra.status=1");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and endi.status >= 3 and endi.createMode = 0 and endi.universityAuditStatus=3 and gra.status=1");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and endi.status >= 2 and endi.createMode = 0 and endi.deptInstAuditStatus=3 and gra.status=1");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "41")){//项目变更已上报
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (vari.status >= 5 or vari.createMode = 1 or vari.createMode = 2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and vari.status >= 4 and vari.createMode = 0");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and vari.status >= 3 and vari.createMode = 0");
							}else if(accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and vari.status >= 2 and vari.createMode = 0");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "42")){//项目变更待审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (vari.status >= 5 or vari.createMode = 1 or vari.createMode = 2) and (vari.finalAuditStatus=0 or vari.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and vari.status >= 4 and vari.createMode = 0 and (vari.provinceAuditStatus=0 or vari.provinceAuditStatus=1 or vari.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and vari.status >= 3 and vari.createMode = 0 and (vari.universityAuditStatus=0 or vari.universityAuditStatus=1 or vari.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and vari.status >= 2 and vari.createMode = 0 and (vari.deptInstAuditStatus=0 or vari.deptInstAuditStatus=1 or vari.deptInstAuditStatus=2)");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "43")){//项目变更已审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (vari.status >= 5 or vari.createMode = 1 or vari.createMode = 2) and vari.finalAuditStatus=3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and vari.status >= 4 and vari.createMode = 0 and vari.provinceAuditStatus=3");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and vari.status >= 3 and vari.createMode = 0 and vari.universityAuditStatus=3");
							}else if(accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and vari.status >= 2 and vari.createMode = 0 and vari.deptInstAuditStatus=3");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "51")){//项目年检已上报
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (ann.status >= 5 or ann.createMode = 1 or ann.createMode = 2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and ann.status >= 4 and ann.createMode = 0");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and ann.status >= 3 and ann.createMode = 0");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and ann.status >= 2 and ann.createMode = 0");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "52")){//项目年检待审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (ann.status >= 5 or ann.createMode = 1 or ann.createMode = 2) and (ann.finalAuditStatus=0 or ann.finalAuditStatus=2)");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and ann.status >= 4 and ann.createMode = 0 and (ann.provinceAuditStatus=0 or ann.provinceAuditStatus=1 or ann.provinceAuditStatus=2)");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and ann.status >= 3 and ann.createMode = 0 and (ann.universityAuditStatus=0 or ann.universityAuditStatus=1 or ann.universityAuditStatus=2)");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and ann.status >= 2 and ann.createMode = 0 and (ann.deptInstAuditStatus=0 or ann.deptInstAuditStatus=1 or ann.deptInstAuditStatus=2)");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "53")){//项目年检已审核
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							session.put("grantedMap", map);
							searchHql.append(" and gra.status = 1");
							if(accountType.equals(AccountType.MINISTRY)){
								searchHql.append(" and (ann.status >= 5 or ann.createMode = 1 or ann.createMode = 2) and ann.finalAuditStatus=3");
							}else if(accountType.equals(AccountType.PROVINCE)){
								searchHql.append(" and ann.status >= 4 and ann.createMode = 0 and ann.provinceAuditStatus=3");
							}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
								searchHql.append(" and ann.status >= 3 and ann.createMode = 0 and ann.universityAuditStatus=3");
							}else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
								searchHql.append(" and ann.status >= 2 and ann.createMode = 0 and ann.deptInstAuditStatus=3");
							}
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else{//首页参数有误
						return "";
					}
				}
				return searchHql.toString();
			}else{
				return "";//找不到业务
			}
		}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER)){//研究人员待处理事宜
			map.clear();
			map.put("date", date);
			String businessType;
			if (null != mainFlag && !mainFlag.isEmpty()){
				businessType = mainFlag.substring(0, 3);
			}else{
				return "";
			}
			map.put("businessType", businessType);
			StringBuffer str = new StringBuffer("select b.startYear, b.endYear, b.startDate from Business b left outer join b.subType so " +
					"where b.status = 1 and so.code=:businessType and to_char(b.startDate, 'yyyy-MM-dd') <= :date " +
					"and (to_char(b.reviewDeadline, 'yyyy-MM-dd') >=:date or b.reviewDeadline is null)");
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			if(business!=null && !business.isEmpty()){
				StringBuffer searchHql = new StringBuffer();//返回值
				for(int i = 0; i < business.size(); i++) {
					Object[] o = (Object[]) business.get(i);
					int startYear = (Integer) o[0];//项目起始年份
					int endYear = (Integer) o[1];//项目终止年份
					Date startDate = (Date) o[2];//项目终止年份
					Map session = ActionContext.getContext().getSession();
					
					//根据账号及项目管理系统首页的参数设置查询
					if(mainFlag.equals(projectNumber + "25")){//项目中检待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and midi.status<=1 and midi.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "35")){//项目结项待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and endi.status<=1 and endi.finalAuditResultEnd=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "45")){//项目变更待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and vari.status<=1 and vari.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "55")){//项目年检待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and ann.status<=1 and ann.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "14")){//项目申请待评审
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("appReviewMap");
							searchHql.append(" and app.status >= 6 and (appRev.submitStatus!=3 " +
								"or (appRev.reviewerSn=1 and app.reviewStatus!=3))");
							session.put("endReviewMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("app.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "34")){//项目结项待评审
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("endReviewMap");
							searchHql.append(" and gra.status = 1 and endi.status >= 6 and (endRev.submitStatus!=3 " +
								"or (endRev.reviewerSn=1 and endi.reviewStatus!=3))");
							session.put("endReviewMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，业务开始日期
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else{//首页参数有误
						return "";
					}
				}
				return searchHql.toString();
			}else{
				return "";//找不到业务
			}
		}else if(accountType.equals(AccountType.STUDENT)){//学生
			map.clear();
			map.put("date", date);
			String businessType;
			if (null != mainFlag && !mainFlag.isEmpty()){
				businessType = mainFlag.substring(0, 3);
			}else{
				return "";
			}
			map.put("businessType", businessType);
			StringBuffer str = new StringBuffer("select b.startYear, b.endYear, b.startDate from Business b left outer join b.subType so where " +
					"b.status = 1 and so.code=:businessType and to_char(b.startDate, 'yyyy-MM-dd')<= :date and (to_char(b.applicantDeadline, 'yyyy-MM-dd')>=:date " +
					"or b.applicantDeadline is null) and (to_char(b.reviewDeadline, 'yyyy-MM-dd') >=:date or b.reviewDeadline is null)");
			String hql = str.toString();
			List business = dao.query(hql, map);
			Map<String, Integer> counterMap = new HashMap<String, Integer>();//计数容器
			
			if(business!=null && !business.isEmpty()){
				StringBuffer searchHql = new StringBuffer();//返回值
				for(int i = 0; i < business.size(); i++) {
					Object[] o = (Object[]) business.get(i);
					int startYear = (Integer) o[0];//项目起始年份
					int endYear = (Integer) o[1];//项目终止年份
					Date startDate = (Date) o[2];//项目终止年份
					Map session = ActionContext.getContext().getSession();
					
					//根据账号及项目管理系统首页的参数设置查询
					if(mainFlag.equals(projectNumber + "25")){//项目中检待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and midi.status<=1 and midi.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("midi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "35")){//项目结项待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and endi.status<=1 and endi.finalAuditResultEnd=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("endi.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "45")){//项目变更待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and vari.status<=1 and vari.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("vari.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else if(mainFlag.equals(projectNumber + "55")){//项目年检待处理
						counterMap.put(mainFlag, (null != counterMap.get(mainFlag)) ? counterMap.get(mainFlag) + 1 : 1);
						if (counterMap.get(mainFlag) == 1){//循环查询中只在第一次查询拼接的条件
							map = (Map) session.get("grantedMap");
							searchHql.append(" and gra.status = 1 and mem.isDirector = 1 and app.id = gra.application.id " +
								"and ann.status<=1 and ann.finalAuditResult=0");
							session.put("grantedMap", map);
						}
						//循环语句中多次拼接的条件：起始年份，截止年份，
						map.put("startYear" + i, startYear);
						map.put("endYear" + i, endYear);
						map.put("startDate" + i, startDate);
						if(counterMap.get(mainFlag) == 1) {
							searchHql.append(" and (");
							searchHql.append("(ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						} else {
							searchHql = new StringBuffer(searchHql.toString().substring(0, searchHql.toString().lastIndexOf(")")));//去掉hql末尾最后一个")"
							searchHql.append(" or (");
							searchHql.append("ann.applicantSubmitDate >= :startDate" + i)
								.append((startYear != -1) ? " and app.year >= :startYear" + i : "")
								.append((endYear != -1) ? " and app.year <= :endYear" + i : "");
							searchHql.append(")");
						}
						searchHql.append(")");
					}else{//首页参数有误
						return "";
					}
				}
				return searchHql.toString();
			}else{
				return "";//找不到业务
			}
		}else{//账号类型出错
			return "";
		}
	}
	
	/**
	 * 根据项目类型获得各个项目相关的字段名
	 * @param 项目类型
	 */
	public Map<String,String> getMapByProjectType(String projectType){
		if(null != projectType && projectType.trim().length() > 0){
			Map<String,String> projectMap = new LinkedHashMap<String,String>();
			if(projectType.equals("general")){
				projectMap.put("projectNumber", "01");
				projectMap.put("BusinessName", "一般项目");
				projectMap.put("appBusinessName", "一般项目申请");
				projectMap.put("endBusinessName", "一般项目结项");
				projectMap.put("applicationClassName", "GeneralApplication");
				projectMap.put("grantedClassName", "GeneralGranted");
				projectMap.put("appRevClassName", "GeneralApplicationReview");
				projectMap.put("endRevClassName", "GeneralEndinspectionReview");
				projectMap.put("grantedSet", "generalGranted");
				projectMap.put("memberSet", "generalMember");
				projectMap.put("feeSet", "generalFee");
				projectMap.put("appRevSet", "generalApplicationReview");
				projectMap.put("annSet", "generalAnninspection");
				projectMap.put("midiSet", "generalMidinspection");
				projectMap.put("endiSet", "generalEndinspection");
				projectMap.put("variSet", "generalVariation");
				projectMap.put("fundSet", "generalFunding");
				projectMap.put("endRevSet", "generalEndinspectionReview");
			}else if (projectType.equals("instp")) {
				projectMap.put("projectNumber", "02");
				projectMap.put("BusinessName", "基地项目");
				projectMap.put("appBusinessName", "基地项目申请");
				projectMap.put("endBusinessName", "基地项目结项");
				projectMap.put("applicationClassName", "InstpApplication");
				projectMap.put("grantedClassName", "InstpGranted");
				projectMap.put("appRevClassName", "InstpApplicationReview");
				projectMap.put("endRevClassName", "InstpEndinspectionReview");
				projectMap.put("grantedSet", "instpGranted");
				projectMap.put("memberSet", "instpMember");
				projectMap.put("feeSet", "instpFee");
				projectMap.put("appRevSet", "instpApplicationReview");
				projectMap.put("annSet", "instpAnninspection");
				projectMap.put("midiSet", "instpMidinspection");
				projectMap.put("endiSet", "instpEndinspection");
				projectMap.put("variSet", "instpVariation");
				projectMap.put("fundSet", "instpFunding");
				projectMap.put("endRevSet", "instpEndinspectionReview");
			}else if (projectType.equals("post")) {
				projectMap.put("projectNumber", "03");
				projectMap.put("BusinessName", "后期资助项目");
				projectMap.put("appBusinessName", "后期资助项目申请");
				projectMap.put("endBusinessName", "后期资助项目结项");
				projectMap.put("applicationClassName", "PostApplication");
				projectMap.put("grantedClassName", "PostGranted");
				projectMap.put("appRevClassName", "PostApplicationReview");
				projectMap.put("endRevClassName", "PostEndinspectionReview");
				projectMap.put("grantedSet", "postGranted");
				projectMap.put("memberSet", "postMember");
				projectMap.put("feeSet", "postFee");
				projectMap.put("appRevSet", "postApplicationReview");
				projectMap.put("annSet", "postAnninspection");
				projectMap.put("endiSet", "postEndinspection");
				projectMap.put("variSet", "postVariation");
				projectMap.put("fundSet", "postFunding");
				projectMap.put("endRevSet", "postEndinspectionReview");
			}else if (projectType.equals("key")) {
				projectMap.put("projectNumber", "04");
				projectMap.put("BusinessName", "重大攻关项目");
				projectMap.put("appBusinessName", "重大攻关项目招标");
				projectMap.put("endBusinessName", "重大攻关项目结项");
				projectMap.put("applicationClassName", "KeyApplication");
				projectMap.put("grantedClassName", "KeyGranted");
				projectMap.put("appRevClassName", "KeyApplicationReview");
				projectMap.put("endRevClassName", "KeyEndinspectionReview");
				projectMap.put("grantedSet", "keyGranted");
				projectMap.put("memberSet", "keyMember");
				projectMap.put("feeSet", "keyFee");
				projectMap.put("appRevSet", "keyApplicationReview");
				projectMap.put("annSet", "keyAnninspection");
				projectMap.put("midiSet", "keyMidinspection");
				projectMap.put("endiSet", "keyEndinspection");
				projectMap.put("variSet", "keyVariation");
				projectMap.put("fundSet", "keyFunding");
				projectMap.put("endRevSet", "keyEndinspectionReview");
			}else if (projectType.equals("entrust")) {
				projectMap.put("projectNumber", "05");
				projectMap.put("BusinessName", "委托应急课题");
				projectMap.put("appBusinessName", "委托应急课题申请");
				projectMap.put("endBusinessName", "委托应急课题结项");
				projectMap.put("applicationClassName", "EntrustApplication");
				projectMap.put("grantedClassName", "EntrustGranted");
				projectMap.put("appRevClassName", "EntrustApplicationReview");
				projectMap.put("endRevClassName", "EntrustEndinspectionReview");
				projectMap.put("grantedSet", "entrustGranted");
				projectMap.put("memberSet", "entrustMember");
				projectMap.put("feeSet", "entrustFee");
				projectMap.put("appRevSet", "entrustApplicationReview");
				projectMap.put("endiSet", "entrustEndinspection");
				projectMap.put("variSet", "entrustVariation");
				projectMap.put("fundSet", "entrustFunding");
				projectMap.put("endRevSet", "entrustEndinspectionReview");
			}
			return projectMap;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 系统选项表中获取子级列表格式为（id, name）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,String> getSubIdNameList(String str){
		if(str !=null && str.trim().length()>0){
			Map<String,String> subList = new LinkedHashMap<String,String>();
			String hql = "select sys.id, sys.name from SystemOption sys left join sys.systemOption so where so.name=:name order by sys.code asc";
			Map paraMap = new HashMap();
			paraMap.put("name", str);
			List list = dao.query(hql,paraMap);
			if(list != null && list.size()>0){
				for(int i=0;i<list.size();i++){
					String key = ((Object[])list.get(i))[0].toString();
					String value = ((Object[])list.get(i))[1].toString();
					subList.put(key, value);
				}
			}
			return subList;
		}else{
			return null;
		}
	}

	/**
	 * 查询当年年份形成年份列表，起始年份为2000
	 * @return 年份列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> getYearMap(){
		Map<Integer, Integer> yearMap = new LinkedHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int y = Integer.parseInt(year);
		for(int i=2000; i<y; i++){
			yearMap.put(i, i);
		}
		yearMap.put(y, y);
		return yearMap;
	}
	
	/**
	 * 查询当年年份形成年份列表，起始年份为2005
	 * @return 年份列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> getYearMapFrom2005(){
		Map<Integer, Integer> yearMap = new LinkedHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int y = Integer.parseInt(year);
		for(int i=2005; i<y; i++){
			yearMap.put(i, i);
		}
		yearMap.put(y, y);
		return yearMap;
	}
	
	//----------以下为获取教师、学生、专家的对象或id----------

	/**
	 * 获得人员id对应的所有教师id
	 * @param personid 人员主表id
	 * @return  教师id的list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTeacherIdList(String personid){
		if(personid == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("personid", personid);
		return dao.query("select t.id from Teacher t left outer join t.person p where p.id=:personid",map);
	}
	
	/**
	 * 获得教师及其人员
	 * @param teacherId 教师id
	 * @return  教师
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTeacherFetchPerson(String teacherId){
		if(teacherId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("teacherId", teacherId);
		return dao.query("from Teacher te left join fetch te.person left join fetch te.university left join fetch te.department " +
				"left join fetch te.institute where te.id=:teacherId",map);
	}
	
	/**
	 * 获得教师及其人员
	 * @param teacherId 教师id
	 * @param personId 人员id
	 * @return  教师
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTeacherFetchPerson(String teacherId, String personId){
		if(personId == null || teacherId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("teacherId", teacherId);
		map.put("personId", personId);
		return dao.query("from Teacher te left join fetch te.person per where te.id=:teacherId or per.id=:personId",map);
	}
	
	/**
	 * 根据项目成员人员id、高校id、院系id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getTeacherIdByMemberAllUnit(String personId, String universityId, Department department, Institute institute){
		if(personId == null ){
			return null;
		}
		StringBuffer hql = new StringBuffer("select tea.id from Teacher tea where tea.person.id=:personId ");
		Map map = new HashMap();
		map.put("personId", personId);
		if (universityId != null) {
			map.put("universityId", universityId);
			hql.append("and tea.university.id=:universityId ");
		}
		if(department != null){
			map.put("departmentId", department.getId());
			hql.append(" and tea.department.id=:departmentId");
		}else if(institute != null){
			map.put("instituteId", institute.getId());
			hql.append(" and tea.institute.id=:instituteId");
		}
		List<String> teacherIds = dao.query(hql.toString(), map);
		if(teacherIds.size() == 1){
			return teacherIds.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目成员人员id、机构名称、部门名称获得专家id
	 * @param personId 人员主表id
	 * @param agencyName 机构名称
	 * @param divisionName 部门名称
	 * @return 专家id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getExpertIdByPersonIdUnit(String personId, String agencyName, String divisionName){
		if(personId == null){
			return null;
		}
		StringBuffer hql = new StringBuffer("select exp.id from Expert exp where exp.person.id=:personId ");
		Map map = new HashMap();
		map.put("personId", personId);
		if(agencyName != null && agencyName.trim().length() > 0){
			map.put("agencyName", agencyName.trim());
			hql.append(" and exp.agencyName=:agencyName");
		}else if(divisionName != null && divisionName.trim().length() > 0){
			map.put("divisionName", divisionName.trim());
			hql.append(" and exp.divisionName=:divisionName");
		}
		List<String> expertIds = dao.query(hql.toString(), map);
		if(expertIds.size() == 1){
			return expertIds.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据项目成员人员id、高校id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getTeacherIdByMemberPartUnit(String personId, String universityId){
		StringBuffer hql = new StringBuffer("select tea.id from Teacher tea where tea.person.id=:personId ");
		Map map = new HashMap();
		map.put("personId", personId);
		if (universityId != null) {
			map.put("universityId", universityId);
			hql.append("and tea.university.id=:universityId ");
		}
		map.put("universityId", universityId);
		List<String> teacherIds = dao.query(hql.toString(), map);
		if(teacherIds.size() != 0){
			return teacherIds.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获得人员id对应的所有学生id
	 * @param personid 人员主表id
	 * @return  须生id的list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getStudentIdList(String personid){
		if(personid == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("personid", personid);
		return dao.query("select s.id from Student s left outer join s.person p where p.id=:personid",map);
	}
	
	/**
	 * 获得学生及其人员
	 * @param studnetId 学生id
	 * @return  学生
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getStudentFetchPerson(String studentId){
		if(studentId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("studentId", studentId);
		return dao.query("from Student st left join fetch st.person left join fetch st.university left join fetch st.department " +
				"left join fetch st.institute where st.id=:studentId",map);
	}
	
	/**
	 * 获得学生及其人员
	 * @param studentId 外部专家id
	 * @param personId 人员id
	 * @return  学生
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getStudentFetchPerson(String studentId, String personId){
		if(studentId == null || personId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("studentId", studentId);
		map.put("personId", personId);
		return dao.query("from Student st left join fetch st.person per where st.id=:studentId or per.id=:personId",map);
	}
	
	/**
	 * 获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @return  外部专家
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getExpertFetchPerson(String expertId){
		if(expertId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("expertId", expertId);
		return dao.query("from Expert exp left join fetch exp.person where exp.id =:expertId",map);
	}
	
	/**
	 * 获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @param personId 人员id
	 * @return  外部专家
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getExpertFetchPerson(String expertId, String personId){
		if(personId == null || expertId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("expertId", expertId);
		map.put("personId", personId);
		return dao.query("from Expert exp left join fetch exp.person per where exp.id =:expertId or per.id=:personId",map);
	}
	
	//----------以下为获取院系、基地的对象----------
	
	/**
	 * 获得院系及其所属高校
	 * @param deptId 院系id
	 * @return  院系
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getDeptFetchUniv(String deptId){
		if(deptId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("deptId", deptId);
		return dao.query("from Department dept left outer join fetch dept.university where dept.id=:deptId",map);
	}
	
	/**
	 * 获得基地及其所属高校
	 * @param instId 基地id
	 * @return  基地
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getInstFetchUniv(String instId){
		if(instId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("instId", instId);
		return dao.query("from Institute inst left outer join fetch inst.subjection where inst.id=:instId",map);
	}
	
	//-----------以下为其他信息查询----------

	/**
	 * 根据人员主表id获得该人员的id以及所在的单位和部门
	 * @param personid 人员主表id
	 * @param memberType 1:教师	2：外部专家	3：学生
	 * @return 长度为2的一维字符数组  [0]：人员id（可能是教师id或外部专家id或学生id）	[1]：人员所在的单位与部门 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] getPerIdAndUnitName(String personid, int memberType){
		String[] info = new String[2];
		if(personid == null){
			return info;
		}
		Map map1 = new HashMap();
		map1.put("personid", personid);
		Object[] dirAgen = new Object[4];
		String directorId = "";
		String directorAgency = "";
		if(memberType == 1){//教师
			List tealist = dao.query("select t.id,u.name,d.name,i.name from Teacher t left outer join t.person p " +
				"left outer join t.university u left outer join t.department d left outer join t.institute i where p.id=:personid",map1);
			if(!tealist.isEmpty()&&tealist!=null){
				dirAgen = (Object[]) tealist.get(0);
			}
		}else if(memberType == 2){//外部专家
			List stulist = dao.query("select e.id,e.agencyName,e.divisionName,e.divisionName from Expert e left outer join e.person p " +
					"where p.id=:personid",map1);
			if(!stulist.isEmpty()&&stulist!=null){
				dirAgen = (Object[]) stulist.get(0);
			}
		}else if(memberType == 3){//学生
			List stulist = dao.query("select s.id,u.name,d.name,i.name from Student s left outer join s.person p " +
					"left outer join s.university u left outer join s.department d left outer join s.institute i where p.id=:personid",map1);
			if(!stulist.isEmpty()&&stulist!=null){
				dirAgen = (Object[]) stulist.get(0);
			}
		}
		directorId = dirAgen[0].toString();
		if(dirAgen[1]!= null){
			directorAgency += dirAgen[1];
		}
		if(dirAgen[2]!= null){
			directorAgency += "  " + dirAgen[2];
		}else{
			directorAgency += "  " + dirAgen[3];
		}
		info[0] = directorId;
		info[1] = directorAgency;
		return info;
	}
	
	/**
	 * 由人员Id获取对应学术信息
	 * @param personId
	 * @return 学术信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Academic getAcademicByPersonId(String personId){
		if(personId == null){
			return null;
		}
		Map paraMap = new HashMap();
		paraMap.put("personId", personId);
		String hql = "from Academic ac where ac.person.id = :personId";
		List<Academic> list = dao.query(hql,paraMap);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 项目所在机构是否属于西部省份
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	@SuppressWarnings("rawtypes")
	public int isIncludedWestProvince(String provinceId){
		int flag = 0;
		String hql = "select so from SystemOption so where (code='500000' or code='510000' or code='520000' or code='530000' or code='610000' or " +
				"code='620000' or code='640000' or code='630000' or code='150000' or code='450000') and standard='GBT2260-2007' and so.id=?";
		List id = dao.query(hql, provinceId);
		if(id.size() > 0){
			flag = 1;
		}
		return flag;
	}
	
	/**
	 * 项目所在机构是否是新疆省
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	@SuppressWarnings("rawtypes")
	public int isXinjiangProvince(String provinceId){
		int flag = 0;
		String hql = "select so from SystemOption so where code='650000' and standard='GBT2260-2007' and so.id=?";
		List id = dao.query(hql, provinceId);
		if(id.size() > 0){
			flag = 1;
		}
		return flag;
	}
	
	/**
	 * 项目所在机构是否是西藏省
	 * @param provinceId项目所在机构省份id
	 * @return 是否属于结果，1：是，0：否
	 */
	@SuppressWarnings("rawtypes")
	public int isXizangProvince(String provinceId){
		int flag = 0;
		String hql = "select so from SystemOption so where code='540000' and standard='GBT2260-2007' and so.id=?";
		List id = dao.query(hql, provinceId);
		if(id.size() > 0){
			flag = 1;
		}
		return flag;
	}
	
	//----------以下为一些格式设置----------
	
	/**
	 * 获得成果形式id，多个以逗号隔开
	 * @param productTypeNames 成果形式名称 多个以英文分号和空格隔开
	 * @return 成果id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProductTypeCodes(String productTypeNames){
		String productTypeIds = "";
		if(productTypeNames != null && productTypeNames.length() > 0){
			Map map1 = new HashMap();
			String[] ptypeNames = productTypeNames.split("; ");
			for(int i=0;i<ptypeNames.length;i++){
				map1.put("ptypeName", ptypeNames[i]);
				List list = dao.query("select so.code from SystemOption so where so.name=:ptypeName and so.standard='productType' and so.systemOption.id is not null", map1);
				if(list!=null&&!list.isEmpty()){
					String ptypeid = (String) list.get(0);
					productTypeIds += ptypeid + ",";
				}
			}
			if(productTypeIds.length() > 0){
				productTypeIds = productTypeIds.substring(0, productTypeIds.length() - 1);
			}
		}
		return productTypeIds;
	}
	
	/**
	 * 获得成果形式名称，多个以逗号隔开
	 * @param productTypeIds 成果形式id List
	 * @return 成果名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProductTypeNames(List<String> productTypeIds){
		if(productTypeIds == null){
			return null;
		}
		String productTypeNames = "";
		int size = productTypeIds.size();
		Map map = new HashMap();
		if(size > 0){
			for(int m = 0; m < size; m++){
				map.clear();
				map.put("productTypeIds", productTypeIds.get(m));
				String productTypeName = (String)dao.query("select so.name from " +
						"SystemOption so where so.code=:productTypeIds and so.standard='productType'", map).get(0);
				if(!"".equals(productTypeNames)){
					productTypeNames+="; "+productTypeName;
				}else{
					productTypeNames += productTypeName;
				}
			}
		}
		return productTypeNames;
	}
	
	/**若年月日和当前时间相等则设置时间的时分秒为当前时间的时分秒
	 * 否则设置时间的时分秒为全零
	 * @param oriDate 原时间
	 * @return 处理后的时间
	 */
	public Date setDateHhmmss(Date oriDate){
		if(oriDate == null){
			return null;
		}else{
			String newDateString = "";
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowDateString = format.format(now);
			String oriDateString = format.format(oriDate);
			if(!(oriDateString.substring(0, 8).equals(nowDateString.substring(0, 8))))
				newDateString = oriDateString.substring(0, 8) + "000000";
			else
				newDateString = oriDateString.substring(0, 8) + nowDateString.substring(8);
			try {
				Date newDate = format.parse(newDateString);
				return newDate;
			} catch (ParseException e) {
				return oriDate;
			}
			
		}
	}
	
	/**
	 * 根据基地id获得基地所在高校名称
	 * @param instituteId 基地id
	 * @return 基地所在高校名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public String getUnivNameByInstId(String instituteId){
		if(instituteId == null || instituteId.trim().length() == 0){
			return null;
		}
		Map map = new HashMap();
		map.put("instituteId", instituteId);
		List<String> univNames = (List<String>)dao.query("select uni.name from Institute ins left outer join ins.subjection uni where ins.id=:instituteId",map);
		if(univNames.size() > 0){
			return univNames.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据登陆账号类别好的评审人身份
	 * @param accountType 登陆账号类别1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 评审人身份	0默认，1专家，2教育部，3省厅，4高校
	 */
	public int getReviewTypeByAccountType(AccountType accountType){
		int reviewType = 0;
		if(accountType.equals(AccountType.MINISTRY)){
			reviewType = 2;
		}else if(accountType.equals(AccountType.PROVINCE)){
			reviewType = 3;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			reviewType = 4;
		}else if(accountType.compareTo(AccountType.INSTITUTE) > 0){
			reviewType = 1;
		}
		return reviewType;
	}
	
	/**
	 * 根据成果类别及数量名称、成果类别及数量名称获得显示的实际成果类别
	 * @param productType 成果类别及数量名称
	 * @param productTypeOther	成果类别及数量名称
	 * @return	显示的实际成果类别
	 */
	public String getProductTypeReal(String productType, String productTypeOther){
		if(productType != null && productType.contains("其他") && productTypeOther != null && productTypeOther.trim().length() > 0){
			int productTypeindex = productType.indexOf("其他");
			String productType1 = productType.substring(0, productTypeindex);
			String productType2 = productType.substring(productTypeindex + 2);
			String productTypeReal = productType1 + "其他(" + productTypeOther +")" +productType2;
			return productTypeReal;
		}else{
			return productType;
		}
	}
	
	/**
	 * 字符串拼接
	 * @param arr 字符串数组
	 * @param sign 并接符号
	 * @author 肖雅
	 */
	public String join(List<String> arr, String sign){
		String result = "";
		for(int i = 0; i < arr.size(); i++){
			if(i < arr.size() - 1){
				result += (arr.get(i) + sign);
			} else {
				result += arr.get(i);
			}
		}
		return result;
	}
	

/*******************************************end*******************************************/
	
	/**
	 * 根据证件类别、证件号、人员姓名、人员性别判断这些信息是否符合数据库中的信息
	 * @param idcardType 证件类别
	 * @param idcardNumber 证件号
	 * @param name 人员姓名
	 * @param gender 人员性别
	 * @author 0:不匹配 1：匹配	2：不存在此人员
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int isPersonMatch(String idcardType, String idcardNumber, String name, String gender){
		if(idcardType == null || idcardType.trim().length() == 0){
			return 2;
		}else{
			Map map = new HashMap();
			map.put("idcardType", idcardType);
			map.put("idcardNumber", idcardNumber);
			List persons = dao.query("select per from Person per where per.idcardType=:idcardType and per.idcardNumber=:idcardNumber", map);
			if(persons != null && !persons.isEmpty()){//存在此人
				Person person = (Person)persons.get(0);
				if(person.getName().equals(name) && (person.getGender() == null || person.getGender().equals(gender))){//匹配
					return 1;
				}else{//不匹配
					return 0;
				}
			}else{//不存在此人
				return 2;
			}
		}
	}
	/**
	 * 根据立项id获得立项年份
	 * @param graId 立项id
	 * @return 立项年份
	 */
	@SuppressWarnings("deprecation")
	public int getGrantedYear(String graId){
		if(graId == null || graId.trim().length() == 0){
			return 0;
		}
		int grantedYear = 0;
		ProjectGranted granted = (ProjectGranted) dao.query(ProjectGranted.class, graId);
		if(granted != null && granted.getApproveDate() != null){
			grantedYear = granted.getApproveDate().getYear() + 1900;
		}else{
			String appId = this.getApplicationIdByGrantedId(graId);
			ProjectApplication application = (ProjectApplication)dao.query(ProjectApplication.class, appId);
			if(application != null){
				grantedYear = application.getYear();
			}else{
				;
			}
		}
		return grantedYear;
	}
	/**
	 * 根据立项年份判断中检是否禁止
	 * @param grantedYear 立项年份
	 * @return 1：禁止         0：未禁止
	 */
	@SuppressWarnings("deprecation")
	public int getMidForbidByGrantedDate(int grantedYear){
		Date now = new Date();
		int midForbid = (now.getYear() + 1900 > grantedYear + 3) ? 1 : 0;
		return midForbid;
	}
	
	/**
	 * 根据立项年份判断结项起始时间是否开始
	 * @param grantedYear 立项年份
	 * @return 1：开始         0：未开始
	 */
	@SuppressWarnings("deprecation")
	public int getEndAllowByGrantedDate(int grantedYear){
		Date now = new Date();
		Date grantedDate = (Date)now.clone();
		grantedDate.setYear(grantedYear - 1900 + 3);
		grantedDate.setMonth(6);
		grantedDate.setDate(1);
		int endAllow = (now.after(grantedDate)) ? 1 : 0;
		return endAllow;
	}
	//---------------------------以下为word宏的相关处理------------------------
	
	/**
	 * 检查word文件是否合法
	 * @param grantedId 项目立项id
	 * @param projectType 项目类型
	 * @param wordFile word文件
	 * @param businessType 业务类型
	 */
	public String checkWordFileLegal(String grantedId, String projectType, File wordFile, int businessType) {
		try {
			if(businessType < 1 || businessType > 3 || wordFile == null || grantedId == null) {
				return null;
			}
			if(projectType.equals("general")) {
				String[] prefix = {"<middata>", "<vardata>", "<enddata>"};
				String dataXMLStr = null;
				if(businessType == 3) {
					dataXMLStr = WordTool.getWordTableData(wordFile, 6, 0, 0);
				} else {
					dataXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
				}
				System.out.println("dataXMLStr " + dataXMLStr);
				// 文件合法性校验
				if(dataXMLStr == null || !dataXMLStr.startsWith(prefix[businessType - 1])) {
					return WordInfo.ERROR_WORD_ILLEGAL;
				}
				Document document = DocumentHelper.parseText(dataXMLStr);
				Element rootElement = document.getRootElement();
				Element docVersion = rootElement.element("docVersion");
				if(docVersion.getText().startsWith("v1.0.")) {
					// 检查保护校验
					if(dataXMLStr.indexOf("<contentVersion>") < 0 || dataXMLStr.indexOf("<docVersion>") < 0) {
						return WordInfo.ERROR_WORD_NOT_PROTECTED;
					}
					// 检查项目匹配
					GeneralGranted gg = (GeneralGranted) dao.query(GeneralGranted.class, grantedId);
					if(gg != null && !gg.getNumber().isEmpty()) {
						if(!rootElement.element("projectNumber").getText().trim().equals(gg.getNumber())) {
							return WordInfo.ERROR_WORD_PROJECT_INFO_NOT_MATCH;
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return WordInfo.ERROR_WORD_ILLEGAL;
		}
		return null;
	}
	
	/**
	 * 生成结项申请Zip文件
	 * @param grantedId 项目立项id
	 * @param loginerBelongId 登陆者所属Id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean createEndinspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType) {
		
		String realPath = ApplicationContainer.sc.getRealPath("/");
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("enddata");
		
		if(projectType.equals("general")) {
			GeneralGranted generalGranted = (GeneralGranted) dao.query(GeneralGranted.class, grantedId);
			if(generalGranted != null) {
				GeneralApplication generalApplication = (GeneralApplication) dao.queryUnique(" select ga from GeneralApplication ga, GeneralGranted gg where ga.id = gg.application.id and gg.id = ? ", grantedId);
		        boolean isMyProject = generalGranted.getApplicantId().contains(loginerBelongId) ? true : false;
		        
		        // 项目信息
		        Element project = root.addElement("project");
		        Element name = project.addElement("name");
		        name.setText(generalGranted.getName() == null || !isMyProject ? "" : generalGranted.getName());
		        Element applicantName = project.addElement("applicantName");
		        applicantName.setText(generalGranted.getApplicantName() == null || !isMyProject ? "" : generalGranted.getApplicantName());
		        Element approveFee = project.addElement("approveFee");
		        approveFee.setText(generalGranted.getApproveFee() == null || !isMyProject ? "" : generalGranted.getApproveFee().toString());
		        Double fundings = (Double) dao.queryUnique(" select SUM(gf.fee) from GeneralFunding gf group by gf.granted.id having gf.granted.id = ? ", grantedId);
		        Element allocateFee = project.addElement("allocateFee");
		        allocateFee.setText(fundings == null || !isMyProject ? "" : fundings.toString());
		        Element agencyName = project.addElement("agencyName");
		        agencyName.setText(generalGranted.getAgencyName() == null || !isMyProject ? "" : generalGranted.getAgencyName());
		        Element productType = project.addElement("productType");
		        productType.setText(generalGranted.getProductType() == null || !isMyProject ? "" : generalGranted.getProductType());
		        Element number = project.addElement("number");
		        number.setText(generalGranted.getNumber() == null || !isMyProject ? "" : generalGranted.getNumber());
		        Element approveDate = project.addElement("approveDate");
		        approveDate.setText(generalGranted.getApproveDate() == null || !isMyProject ? "" : (new SimpleDateFormat("yyyy-MM")).format(generalGranted.getApproveDate()));
		        Element planEndDate = project.addElement("planEndDate");
		        planEndDate.setText(generalGranted.getPlanEndDate() == null || !isMyProject ? "" : (new SimpleDateFormat("yyyy-MM")).format(generalGranted.getPlanEndDate()));
		        Element disciplineType = project.addElement("disciplineType");
		        disciplineType.setText(generalApplication.getDisciplineType() == null || !isMyProject ? "" : generalApplication.getDisciplineType());
		        
		        // 结项成果清单
		        if(isMyProject) {
		        	Element endProduct = root.addElement("endProduct");
		        	Element finalProductIndex = endProduct.addElement("finalProductIndex");
		        	finalProductIndex.setText("0");
		        	List<String> list = dao.query(" select ge.id from GeneralEndinspection ge where ge.granted.id = ? order by ge.applicantSubmitDate desc ", grantedId);
		        	List productList = new ArrayList();
		        	if(list.size() > 0){
			        	String geid = list.get(0);
			        	String paperHQL = " select p.id, '论文', p.chineseName, f.name, p.authorName, p.otherAuthorName, p.publication, p.publicationDate, p.wordNumber, p.disciplineType from Paper p, ProjectEndinspectionProduct pep left outer join p.form f where pep.product.id=p.id and pep.projectEndinspection.id = ? and p.submitStatus = 3 ";
			        	String bookHQL  = " select b.id, '著作', b.chineseName, f.name, b.authorName, b.otherAuthorName, b.publishUnit, b.publishDate, b.wordNumber, b.disciplineType from Book b, ProjectEndinspectionProduct pep left outer join b.form f where pep.product.id=b.id and pep.projectEndinspection.id = ? and b.submitStatus = 3 ";
			        	String consultationHQL  = " select c.id, '研究咨询报告', c.chineseName, f.name, c.authorName, c.otherAuthorName, c.agencyName, c.publicationDate, c.wordNumber, c.disciplineType from Consultation c, ProjectEndinspectionProduct pep left outer join c.form f where pep.product.id=c.id and pep.projectEndinspection.id = ? and c.submitStatus = 3 ";
			        	String electronicHQL  = " select e.id, '电子出版物', e.chineseName, f.name, e.authorName, e.otherAuthorName, e.agencyName, e.publishDate, e.disciplineType from Electronic e, ProjectEndinspectionProduct pep left outer join e.form f where pep.product.id=e.id and pep.projectEndinspection.id = ? and e.submitStatus = 3 ";
			        	String patentHQL  = " select p.id, '专利', p.chineseName, f.name, p.authorName, p.otherAuthorName, p.agencyName, p.publicDate, p.disciplineType from Patent p, ProjectEndinspectionProduct pep left outer join p.form f where pep.product.id=p.id and pep.projectEndinspection.id = ? and p.submitStatus = 3 ";
			        	String otherProductHQL  = " select o.id, '其他成果', o.chineseName, f.name, o.authorName, o.otherAuthorName, o.agencyName, o.pressDate, o.disciplineType from OtherProduct o, ProjectEndinspectionProduct pep left outer join o.form f where pep.product.id=o.id and pep.projectEndinspection.id = ? and o.submitStatus = 3 ";
			        	List papers = dao.query(paperHQL, geid);
			        	List books = dao.query(bookHQL, geid);
			        	List consultations = dao.query(consultationHQL, geid);
			        	List electronics = dao.query(electronicHQL, geid);
			        	List patents = dao.query(patentHQL, geid);
			        	List otherProducts = dao.query(otherProductHQL, geid);
			        	productList.addAll(papers);
			        	productList.addAll(books);
			        	productList.addAll(consultations);
			        	productList.addAll(electronics);
			        	productList.addAll(patents);
			        	productList.addAll(otherProducts);
		        	}
	        		for(int i = 1; i <= productList.size(); i++) {
	        			Object[] objects = (Object[]) productList.get(i - 1);
	        			Element product = endProduct.addElement("product" + i);
	        			Element Ptype = product.addElement("productType");
	        			Ptype.setText(objects[1] == null || !isMyProject ? "" : (String) objects[1]);
	        			Element PproductName = product.addElement("productName");
	        			PproductName.setText(objects[2] == null || !isMyProject ? "" : (String) objects[2]);
	        			Element Pform = product.addElement("form");
	        			Pform.setText(objects[3] == null || !isMyProject ? "" : (String) objects[3]);
	        			Element PauthorName = product.addElement("authorName");
	        			PauthorName.setText(objects[4] == null || !isMyProject ? "" : (String) objects[4]);
	        			Element PotherAuthorName = product.addElement("otherAuthorName");
	        			PotherAuthorName.setText(objects[5] == null || !isMyProject ? "" : (String) objects[5]);
	        			Element PpublishUnit = product.addElement("publishUnit");
	        			PpublishUnit.setText(objects[6] == null || !isMyProject ? "" : (String) objects[6]);
	        			Element PpublishDate = product.addElement("publishDate");
	        			PpublishDate.setText(objects[7] == null || !isMyProject ? "" : (new SimpleDateFormat("yyyy-MM")).format((Date) objects[7]));
	        			Element PwordNumber = product.addElement("wordNumber");
	        			PwordNumber.setText(objects[8] == null || ((Double)objects[8]).equals(0.0) || !isMyProject ? "" : objects[8].toString());
	        			Element PdisciplineType = product.addElement("disciplineType");
	        			PdisciplineType.setText(objects[9] == null || !isMyProject ? "" : objects[9].toString());
	        			Element PuseRange = product.addElement("useRange");
	        			PuseRange.setText("");
	        			Element PisDBData = product.addElement("isDBData");
	        			PisDBData.setText("1");
	        		}
		        }
			} else {
				return false;
			}
		}
		System.out.println(doc.asXML());
        try {
        	//生成临时目录
        	FileTool.mkdir_p(realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection");
        	// 生成生成包含项目信息的xml文件
        	String filePath = realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection/const.dat";
        	OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
        	out.write(doc.asXML());
        	out.flush();
        	out.close();
        	// 拷贝模板和dll文件至临时目录
			FileUtils.copyFile(new File(realPath + "data/support/support.dll"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection/suppot.dll"));
			FileUtils.copyFile(new File(realPath + "data/template/" + projectType + "/vba_gen_end_2008.doc"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection/教育部人文社会科学研究项目终结报告书.doc"));
			// Zip打包
			ZipUtil.zip(realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection", realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection.zip");
			// 删除临时文件夹
			FileTool.rm_fr(realPath + "temp/" + sessionId + "/" + projectType + "/2011endinspection");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	public boolean createAnninspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType) {
//		String realPath = ApplicationContainer.sc.getRealPath("/");
//		Document doc = DocumentHelper.createDocument();
//		Element root = doc.addElement("repdata");
//		if(projectType.equals("general")) {
//			GeneralGranted generalGranted = (GeneralGranted) query(GeneralGranted.class, grantedId);
//			if(generalGranted != null) {
//		        boolean isMyProject = generalGranted.getApplicantId().contains(loginerBelongId) ? true : false;
//		        Element name = root.addElement("name");
//		        name.setText(generalGranted.getName() == null || !isMyProject ? "" : generalGranted.getName());
//		        Element applicantName = root.addElement("applicantName");
//		        applicantName.setText(generalGranted.getApplicantName() == null || !isMyProject ? "" : generalGranted.getApplicantName());
//		        Element agencyName = root.addElement("agencyName");
//		        agencyName.setText(generalGranted.getAgencyName() == null || !isMyProject ? "" : generalGranted.getAgencyName());
//		        Element productType = root.addElement("productType");
//		        productType.setText(generalGranted.getProductType() == null || !isMyProject ? "" : generalGranted.getProductType());
//		        Element number = root.addElement("number");
//		        number.setText(generalGranted.getNumber() == null || !isMyProject ? "" : generalGranted.getNumber());
//			} 
//		} else {
//			return false;
//		}
//		System.out.println(doc.asXML());
//        try {
//        	//生成临时目录
//        	FileTool.mkdir_p(realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection");
//        	// 生成生成包含项目信息的xml文件
//        	String filePath = realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection/const.dat";
//        	OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
//        	out.write(doc.asXML());
//        	out.flush();
//        	out.close();
//        	// 拷贝模板和dll文件至临时目录
//			FileUtils.copyFile(new File(realPath + "data/support/support.dll"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection/suppot.dll"));
//			FileUtils.copyFile(new File(realPath + "data/template/" + projectType + "/vba_gen_ann_2008.doc"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection/教育部人文社会科学研究项目中期检查报告书.doc"));
//			// Zip打包
//			ZipUtil.zip(realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection", realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection.zip");
//			// 删除临时文件夹
//			FileTool.rm_fr(realPath + "temp/" + sessionId + "/" + projectType + "/2011anninspection");
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	/**
	 * 生成中检申请Zip文件
	 * @param grantedId 项目立项id
	 * @param loginerBelongId 登陆者所属Id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 */
	public boolean createMidinspectionZip(String grantedId, String loginerBelongId, String sessionId, String projectType) {
		String realPath = ApplicationContainer.sc.getRealPath("/");
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("middata");
		if(projectType.equals("general")) {
			GeneralGranted generalGranted = (GeneralGranted) dao.query(GeneralGranted.class, grantedId);
			if(generalGranted != null) {
		        boolean isMyProject = generalGranted.getApplicantId().contains(loginerBelongId) ? true : false;
		        Element name = root.addElement("name");
		        name.setText(generalGranted.getName() == null || !isMyProject ? "" : generalGranted.getName());
		        Element applicantName = root.addElement("applicantName");
		        applicantName.setText(generalGranted.getApplicantName() == null || !isMyProject ? "" : generalGranted.getApplicantName());
		        Element agencyName = root.addElement("agencyName");
		        agencyName.setText(generalGranted.getAgencyName() == null || !isMyProject ? "" : generalGranted.getAgencyName());
		        Element productType = root.addElement("productType");
		        productType.setText(generalGranted.getProductType() == null || !isMyProject ? "" : generalGranted.getProductType());
		        Element number = root.addElement("number");
		        number.setText(generalGranted.getNumber() == null || !isMyProject ? "" : generalGranted.getNumber());
			} 
		} else {
			return false;
		}
		System.out.println(doc.asXML());
        try {
        	//生成临时目录
        	FileTool.mkdir_p(realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection");
        	// 生成生成包含项目信息的xml文件
        	String filePath = realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection/const.dat";
        	OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
        	out.write(doc.asXML());
        	out.flush();
        	out.close();
        	// 拷贝模板和dll文件至临时目录
			FileUtils.copyFile(new File(realPath + "data/support/support.dll"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection/suppot.dll"));
			FileUtils.copyFile(new File(realPath + "data/template/" + projectType + "/vba_gen_mid_2008.doc"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection/教育部人文社会科学研究项目中期检查报告书.doc"));
			// Zip打包
			ZipUtil.zip(realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection", realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection.zip");
			// 删除临时文件夹
			FileTool.rm_fr(realPath + "temp/" + sessionId + "/" + projectType + "/2011midinspection");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 生成变更申请Zip文件
	 * @param grantedId 项目立项id
	 * @param loginerBelongId 登陆者所属Id
	 * @param sessionId sessionId
	 * @param projectType 项目类型
	 */
	public boolean createVariationZip(String grantedId, String loginerBelongId, String sessionId, String projectType) {
		String realPath = ApplicationContainer.sc.getRealPath("/");
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("vardata");
		if(projectType.equals("general")) {
			GeneralGranted generalGranted = (GeneralGranted) dao.query(GeneralGranted.class, grantedId);
			if(generalGranted != null) {
		        boolean isMyProject = generalGranted.getApplicantId().contains(loginerBelongId) ? true : false;
		        Element name = root.addElement("name");
		        name.setText(generalGranted.getName() == null || !isMyProject ? "" : generalGranted.getName());
		        Element applicantName = root.addElement("applicantName");
		        applicantName.setText(generalGranted.getApplicantName() == null || !isMyProject ? "" : generalGranted.getApplicantName());
		        Element agencyName = root.addElement("agencyName");
		        agencyName.setText(generalGranted.getAgencyName() == null || !isMyProject ? "" : generalGranted.getAgencyName());
		        Element productType = root.addElement("productType");
		        productType.setText(generalGranted.getProductType() == null || !isMyProject ? "" : generalGranted.getProductType());
		        Element number = root.addElement("number");
		        number.setText(generalGranted.getNumber() == null || !isMyProject ? "" : generalGranted.getNumber());
		        Element approveDate = root.addElement("approveDate");
		        approveDate.setText(generalGranted.getApproveDate() == null || !isMyProject ? "" : (new SimpleDateFormat("yyyy-MM")).format(generalGranted.getApproveDate()));
		        Element planEndDate = root.addElement("planEndDate");
		        planEndDate.setText(generalGranted.getPlanEndDate() == null || !isMyProject ? "" : (new SimpleDateFormat("yyyy-MM")).format(generalGranted.getPlanEndDate()));
			}
		} else {
			return false;
		}
		System.out.println(doc.asXML());
        try {
        	//生成临时目录
        	FileTool.mkdir_p(realPath + "temp/" + sessionId + "/" + projectType + "/2011variation");
        	// 生成生成包含项目信息的xml文件
        	String filePath = realPath + "temp/" + sessionId + "/" + projectType + "/2011variation/const.dat";
        	OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
        	out.write(doc.asXML());
        	out.flush();
        	out.close();
        	// 拷贝模板和dll文件至临时目录
			FileUtils.copyFile(new File(realPath + "data/support/support.dll"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011variation/suppot.dll"));
			FileUtils.copyFile(new File(realPath + "data/template/" + projectType + "/vba_gen_var_2008.doc"), new File(realPath + "temp/" + sessionId + "/" + projectType + "/2011variation/教育部人文社会科学研究项目重要事项变更申请表.doc"));
			// Zip打包
			ZipUtil.zip(realPath + "temp/" + sessionId + "/" + projectType + "/2011variation", realPath + "temp/" + sessionId + "/" + projectType + "/2011variation.zip");
			// 删除临时文件夹
			FileTool.rm_fr(realPath + "temp/" + sessionId + "/" + projectType + "/2011variation");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 结项word宏导入
	 */
	@SuppressWarnings("unchecked")
	public void importEndinspectionWordXMLData(File wordFile, String grantedId, String projectType) {
		if(projectType.equals("general")) {
			//System.out.println("import...");
			String endXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
			System.out.println("import..." + endXMLStr);
			if(endXMLStr != null && endXMLStr.startsWith("<enddata>")) {
				endXMLStr = endXMLStr.replace(">     </", "></");//去除xml空行
				List<String> dataList = dao.query(" select gg.number from GeneralMember gm, GeneralGranted gg where gm.application.id = gg.application.id and gm.isDirector = 1 and gg.id = ? ", grantedId);
				if(dataList != null) {
					String number = dataList.get(0);
					try {
						Document document = DocumentHelper.parseText(endXMLStr);
						Element rootElement = document.getRootElement();
						Element projectElement = rootElement.element("finalProject");
						if(projectElement != null) {
							if(number != null && projectElement.elementText("number") != null && number.toUpperCase().equals(projectElement.elementText("number").trim().toUpperCase())) {
								Element productElement = rootElement.element("endProduct");
								if(productElement != null) {
									System.out.println("import..." + productElement.asXML());
									updateEndinspectionProductData(productElement, grantedId, "general");
								}
								Element feeElement = rootElement.element("Fee");
								if(feeElement != null) {
									updateEndinspectionFeeData(feeElement, grantedId, "general");
								}
							}
						}
						//TODO 阶段性成果导入
						
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 导入结项成果信息
	 * @param productElement 成果清单的dom文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 * @author leida 2012-01-06
	 */
	public void updateEndinspectionProductData(Element productElement, String grantedId, String projectType) {
		if(projectType.equals("general")) {
			for(int i = 1; i <= 10; i++) {
				Element stepProductElement = productElement.element("product" + i);
				if(stepProductElement != null) {
					System.out.println(i + " " + stepProductElement.asXML());
					// 没有入库的做导入
					if(!stepProductElement.elementText("isDBData").equals("1")) {
						String productName = stepProductElement.elementText("productName");
						String authorName = stepProductElement.elementText("authorName");
						String productType = stepProductElement.elementText("productType");
						System.out.println(productName + " " + authorName);
						if(!mergeProduct(productName, authorName, grantedId, productType, projectType)) {
							@SuppressWarnings("unused")
							String id = createProduct(stepProductElement, grantedId, projectType);
							//TODO 候选信息放入缓存
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据人名和立项id匹配人
	 * @param grantedId
	 * @param authorName
	 * @param projectType
	 * @return 匹配的人的id，如果没匹配上则返回空
	 */
	@SuppressWarnings("unchecked")
	public String matchAuthorIdByGrantedId(String grantedId, String authorName, String projectType) {
		if(projectType.equals("general")) {
			GeneralGranted generalGranted = (GeneralGranted) dao.query(GeneralGranted.class, grantedId);
			List<String> list = dao.query(" select gm.member.id from GeneralMember gm where gm.application.id = ? and gm.memberName = ? ", generalGranted.getApplication().getId(), authorName);
			if(list != null && list.size() == 1) {
				return list.get(0);
			}
			GeneralApplication generalApplication = (GeneralApplication) dao.query(GeneralApplication.class, generalGranted.getApplication().getId());
			list = dao.query(" select p.id from Teacher t left outer join t.person p where t.university.id = ? and p.name = ?  ", generalApplication.getUniversity().getId(), authorName);
			if(list != null && list.size() == 1) {
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 给成果关联第一作者id
	 * @param personId 人员id
	 * @param productId 成果id
	 * @param productType 成果类别
	 * @author 张黎
	 */
	public boolean matchProductAuthorId(String personId, String productId, String productType) {
		Person person = (Person) dao.query(Person.class, personId);
		if(person == null) {
			return false;
		}
		if(productType != null) {
			if(productType.equals("论文")) {
				Paper paper = (Paper) dao.query(Paper.class, productId);
				paper.setAuthor(person);
				dao.modify(paper);
			} else if(productType.equals("著作")) {
				Book book = (Book) dao.query(Book.class, productId);
				book.setAuthor(person);
				dao.modify(book);
			} else if(productType.equals("研究咨询报告")) {
				Consultation consultation = (Consultation) dao.query(Consultation.class, productId);
				consultation.setAuthor(person);
				dao.modify(consultation);
			}else if(productType.equals("电子出版物")) {
				Electronic electronic = (Electronic) dao.query(Electronic.class, productId);
				electronic.setAuthor(person);
				dao.modify(electronic);
			}else if(productType.equals("专利")) {
				Patent patent = (Patent) dao.query(Patent.class, productId);
				patent.setAuthor(person);
				dao.modify(patent);
			}else if(productType.equals("其他成果")) {
				OtherProduct otherProduct = (OtherProduct) dao.query(OtherProduct.class, productId);
				otherProduct.setAuthor(person);
				dao.modify(otherProduct);
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * 根据未匹配负责人的成果id，生成候选人清单
	 * @param authorName
	 * @param universityCode
	 * @param projectType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCandidatePersonInfo(String authorName, String universityCode, String projectType) {
		if(projectType.equals("general")) {
			List list = dao.query(" select p.name, p.gender, p.birthday, u.name, a.name from Teacher t left outer join t.person p left outer join t.university u left outer join t.department d where p.name = ? and u.code = ? ", authorName, universityCode);
			return list;
		}
		return null;
	}
	
	
	/**
	 * 新建成果，不关联负责人id
	 * @param productElement 成果dom
	 * @param grantedId 立项id
	 * @param projectType 项目类别
	 */
	@SuppressWarnings("unchecked")
	public String createProduct(Element productElement, String grantedId, String projectType) {
		@SuppressWarnings("unused")
		String geid = null;
		if(projectType.equals("general")) {
			List<String> list = dao.query(" select ge.id from GeneralEndinspection ge where ge.granted.id = ? order by ge.applicantSubmitDate desc ", grantedId);
			geid = list.get(0);
		}
		String productType = productElement.elementText("productType");
		if(productType.endsWith("论文")) {
			Paper paper = new Paper();
			paper.setAuthorName(productElement.elementText("authorName"));
			paper.setChineseName(productElement.elementText("productName"));
			paper.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			paper.setPublication(productElement.elementText("publishUnit"));
			paper.setWordNumber(Double.valueOf(productElement.elementText("wordNumber")));
			paper.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			paper.setPublicationDate(date);
			paper.setCreateMode(0);
			paper.setSubmitStatus(3);
			paper.setSubmitDate(new Date());
			//TODO 关联立项和结项
			return dao.add(paper);
		} else if (productType.endsWith("著作")) {
			Book book = new Book();
			book.setAuthorName(productElement.elementText("authorName"));
			book.setChineseName(productElement.elementText("productName"));
			book.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			book.setPublishUnit(productElement.elementText("publishUnit"));
			book.setWordNumber(Double.valueOf(productElement.elementText("wordNumber")));
			book.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			book.setPublishDate(date);
			book.setCreateMode(0);
			book.setSubmitDate(new Date());
			book.setSubmitStatus(3);
			//TODO 关联立项和结项
			return dao.add(book);
		} else if(productType.endsWith("研究咨询报告")) {
			Consultation consultation = new Consultation();
			consultation.setAuthorName(productElement.elementText("authorName"));
			consultation.setChineseName(productElement.elementText("productName"));
			consultation.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			consultation.setWordNumber(Double.valueOf(productElement.elementText("wordNumber")));
			consultation.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			consultation.setPublicationDate(date);
			consultation.setCreateMode(0);
			consultation.setSubmitDate(new Date());
			consultation.setSubmitStatus(3);
			//TODO 关联立项和结项
			return dao.add(consultation);
		}else if (productType.endsWith("电子出版物")) {
			Electronic eletronic = new Electronic();
			eletronic.setAuthorName(productElement.elementText("authorName"));
			eletronic.setChineseName(productElement.elementText("productName"));
			eletronic.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			eletronic.setPublishUnit(productElement.elementText("publishUnit"));
			eletronic.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			eletronic.setPublishDate(date);
			eletronic.setCreateMode(0);
			eletronic.setSubmitDate(new Date());
			eletronic.setSubmitStatus(3);
			//TODO 关联立项和结项
			return dao.add(eletronic);
		} else if(productType.endsWith("专利")) {
			Patent patent = new Patent();
			patent.setAuthorName(productElement.elementText("authorName"));
			patent.setChineseName(productElement.elementText("productName"));
			patent.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			patent.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			patent.setPublicDate(date);
			patent.setCreateMode(0);
			patent.setSubmitDate(new Date());
			patent.setSubmitStatus(3);
			//TODO 关联立项和结项
			return dao.add(patent);
		} else if(productType.endsWith("其他成果")) {
			OtherProduct otherProduct = new OtherProduct();
			otherProduct.setAuthorName(productElement.elementText("authorName"));
			otherProduct.setChineseName(productElement.elementText("productName"));
			otherProduct.setOtherAuthorName(productElement.elementText("otherAuthorName"));
			otherProduct.setDisciplineType(productElement.elementText("disciplineType"));
			Date date = null;
			try {
				date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("publishDate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			otherProduct.setPressDate(date);
			otherProduct.setCreateMode(0);
			otherProduct.setSubmitDate(new Date());
			otherProduct.setSubmitStatus(3);
			//TODO 关联立项和结项
			return dao.add(otherProduct);
		}
		return null;
	}
	
	/**
	 * 将word中导入的新增成果和数据库中未提交的做比对，如果同名同负责人则合并成果，并置为提交状态
	 * @param productName 成果名
	 * @param authorName 第一作者名
	 * @param grantedId 立项id
	 * @param productType 成果类型
	 * @param projectType 项目类别
	 * @return 是否找到而且合并成功
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean mergeProduct(String productName, String authorName, String grantedId, String productType, String projectType) {
		if(projectType.equals("general")) {
			List paperList = dao.query(" select p.id, '论文', p.submitStatus from Paper p  where p.granted = ? and p.endinspectionId is not null and p.chineseName = ? and p.authorName = ? ", grantedId, productName, authorName);
        	List bookList = dao.query(" select b.id, '著作', b.submitStatus from Book b where b.granted = ? and b.endinspectionId is not null and b.chineseName = ? and b.authorName = ?  ", grantedId, productName, authorName);
        	List consultationList  = dao.query(" select c.id, '研究咨询报告', c.submitStatus from Consultation c where c.granted = ? and c.endinspectionId is not null and c.chineseName = ? and c.authorName = ?  ", grantedId, productName, authorName);
        	List electronicList = dao.query(" select e.id, '电子出版物', e.submitStatus from Electronic e where e.granted = ? and e.endinspectionId is not null and e.chineseName = ? and e.authorName = ?  ", grantedId, productName, authorName);
        	List patentList = dao.query(" select p.id, '专利', p.submitStatus from Patent p where p.granted = ? and p.endinspectionId is not null and p.chineseName = ? and p.authorName = ?  ", grantedId, productName, authorName);
        	List otherProductList = dao.query(" select o.id, '其他成果', o.submitStatus from OtherProduct o where b.granted = ? and o.endinspectionId is not null and o.chineseName = ? and o.authorName = ?  ", grantedId, productName, authorName);
        	List productList = new ArrayList();
        	productList.addAll(paperList);
        	productList.addAll(bookList);
        	productList.addAll(consultationList);
        	productList.addAll(electronicList);
        	productList.addAll(patentList);
        	productList.addAll(otherProductList);
        	for(int j = 0; j < productList.size(); j++) {
        		//Object[] objects = (Object[]) productList.get(j - 1);
        		//TODO 合并成果
        	}
		}
		return false;
	}
	
	/**
	 * 导入结项经费信息
	 * @param productElement 经费的dom文件
	 * @param applicationId 申请id
	 * @param projectType 项目类型
	 * @author leida 2012-01-06
	 */
	@SuppressWarnings("unchecked")
	public void updateEndinspectionFeeData(Element feeElement, String applicationId, String projectType){
		
		String feeId = (String) dao.queryUnique(" select gf.id from ProjectFee pf where pf.type = 5 and pf.projectType = ? and pf.applicationId = ? ", projectType, applicationId);
		ProjectFee projectFee = null;
		int flag = 0;
		if(feeId != null && !feeId.isEmpty()) {
//			if(projectType.equals("general")) {
//				projectFee = (GeneralFee) dao.query(GeneralFee.class, feeId);
//			}
		} else {
			flag = 1;
//			if(projectType.equals("general")) {
//				projectFee = new GeneralFee();
//			}
		}
		projectFee.setBookFee(getImportFeeValue(feeElement.elementText("bookFee").trim()));
		projectFee.setDataFee(getImportFeeValue(feeElement.elementText("dataFee").trim()));
		projectFee.setTravelFee(getImportFeeValue(feeElement.elementText("travelFee").trim()));
		projectFee.setDeviceFee(getImportFeeValue(feeElement.elementText("deviceFee").trim()));
		projectFee.setConferenceFee(getImportFeeValue(feeElement.elementText("conferenceFee").trim()));
		projectFee.setConsultationFee(getImportFeeValue(feeElement.elementText("consultationFee").trim()));
		projectFee.setLaborFee(getImportFeeValue(feeElement.elementText("laborFee").trim()));
		projectFee.setPrintFee(getImportFeeValue(feeElement.elementText("printFee").trim()));
//		projectFee.setManagementFee(getImportFeeValue(feeElement.elementText("managementFee").trim()));
		projectFee.setOtherFee(getImportFeeValue(feeElement.elementText("otherFee").trim()));
		
		projectFee.setBookNote(feeElement.elementText("bookNote").trim());
		projectFee.setDataNote(feeElement.elementText("dataNote").trim());
		projectFee.setTravelNote(feeElement.elementText("travelNote").trim());
		projectFee.setDeviceNote(feeElement.elementText("deviceNote").trim());
		projectFee.setConferenceNote(feeElement.elementText("conferenceNote").trim());
		projectFee.setConsultationNote(feeElement.elementText("consultationNote").trim());
		projectFee.setLaborNote(feeElement.elementText("laborNote").trim());
		projectFee.setPrintNote(feeElement.elementText("printNote").trim());
//		projectFee.setManagementNote(feeElement.elementText("managementNote").trim());
		projectFee.setOtherNote(feeElement.elementText("otherNote").trim());
		
		projectFee.setType(5);
		
		try {
			if(flag == 0) {
			} else {
				ProjectFee fee = projectFee;
				dao.modify(projectFee);
			}
			System.out.println("Word:更新项目经费信息成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Double getImportFeeValue(String str) {
		if(str == null || str.isEmpty()) {
			return 0.0;
		} else {
			try {
				return Double.parseDouble(str);
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}
	}
	
	/**
	 * 导入中检的word宏文件（新增）
	 * @param wordFile 中检的文件
	 */
	public void importMidinspectionWordFile(File wordFile) {
		//获取上传的xml数据
		String midXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
		if(midXMLStr != null && midXMLStr.startsWith("<middata>")) {
			try {
				//解析成dom对象
				Document document = DocumentHelper.parseText(midXMLStr);
				//获取根元素
				Element rootElement = document.getRootElement();
				//项目类型
				String pojType = rootElement.element("Project").elementText("pojType");
				//版本号
				if(rootElement.element("fileVersion").getText().startsWith("2012.1.")) {
					if(pojType.equals("教育部人文社会科学研究一般项目")) {
						String grantedId = (String) dao.queryUnique(" select gg.id from GeneralGranted gg where gg.number = ?", rootElement.element("Project").elementText("pojNumber"));
						GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, grantedId);
						if(granted.getName().equals(rootElement.element("Project").elementText("pojName"))) {
							GeneralMidinspection midinspection = new GeneralMidinspection();
							midinspection.setGranted(granted);
							midinspection = (GeneralMidinspection) doWithAddMidinspection(midinspection,wordFile);
							midinspection.setProgress(WordTool.getWordIndexData(wordFile, rootElement.element("Indexes").elementText("progress")));
							midinspection.setProductIntroduction(WordTool.getWordIndexData(wordFile, rootElement.element("Indexes").elementText("productIntroduction")));
							dao.add(midinspection);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public ProjectMidinspection doWithAddMidinspection(ProjectMidinspection midinspection, File wordFile){
		midinspection.setStatus(1);//新建
		midinspection.setApplicantSubmitDate(new Date());
		midinspection.setApplicantSubmitStatus(3);//提交
		midinspection.setCreateMode(1);
		return midinspection;
	}
	
	/**
	 * 导入教育部一般项目的结项申请书内容（新增）
	 * @param wordFile
	 */
//	public void importEndinspectionWordFile(File wordFile) {
//		//获取上传的xml数据
//		String endXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
//		if(endXMLStr != null && endXMLStr.startsWith("<enddata>")) {
//			try {
//				//解析成dom对象
//				Document document = DocumentHelper.parseText(endXMLStr);
//				//获取根元素
//				Element rootElement = document.getRootElement();
//				//项目类型
//				String pojType = rootElement.element("Project").elementText("pojType");
//				//版本号
//				if(rootElement.element("fileVersion").getText().startsWith("2012.1.")) {
//					if(pojType.equals("教育部人文社会科学研究一般项目")) {
//						//匹配项目id
//						String grantedId = (String) dao.queryUnique(" select gg.id from GeneralGranted gg where gg.number = ?", rootElement.element("Project").elementText("pojNumber"));
//						//获取立项类
//						GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, grantedId);
//						if(granted.getName().equals(rootElement.element("Project").elementText("pojName"))) {
//							GeneralEndinspection endinspection = new GeneralEndinspection();
//							endinspection.setGranted(granted);
//							endinspection = (GeneralEndinspection) doWithAddEndinspection(endinspection,wordFile);
//							//导入研究过程
//							endinspection.setProgress(WordTool.getWordIndexData(wordFile, rootElement.element("Indexes").elementText("progress")));
//							//导入最终成果简介
//							endinspection.setProductIntroduction(WordTool.getWordIndexData(wordFile, rootElement.element("Indexes").elementText("productIntroduction")));
//							dao.add(endinspection);
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 导入中检的word宏（更新）
	 * @param wordFile word文件
	 * @param grantedId 立项id
	 * @param projectType 项目类型
	 */
	public void importMidinspectionWordXMLData(File wordFile, String grantedId, String projectType) {
		if(projectType.equals("general")) {
			//获取上传的xml数据
			String midXMLStr = WordTool.getWordTableData(wordFile, 0, 0, 0);
			if(midXMLStr != null && midXMLStr.startsWith("<middata>")) {
				try {
					//解析成dom对象
					Document document = DocumentHelper.parseText(midXMLStr);
					//获取根元素
					Element rootElement = document.getRootElement();
					//匹配中检类
					@SuppressWarnings("unused")
					GeneralMidinspection generalMidinspection = (GeneralMidinspection)getCurrentMidinspectionByGrantedId(grantedId);
					//获取版本号
					Element docVersion = rootElement.element("docVersion");
					if(docVersion.getText().startsWith("v1.0.")) {
						Element indexesElement = rootElement.element("Indexes");
						String midProgress = rootElement.element("Indexes").elementText("progress");
						String[] indexes = midProgress.split("; ");
						//更新研究进展
						generalMidinspection.setProgress(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
						String midProductIntro = indexesElement.elementText("productIntroduction");
						indexes = midProductIntro.split("; ");
						//更新成果简介
						generalMidinspection.setProductIntroduction(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
						dao.modify(generalMidinspection);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void importVarXMLData(File varWordFile, String graId, String projectType) {
		String varXMLStr = WordTool.getWordTableData(varWordFile, 0, 0, 0);
		if(varXMLStr != null && varXMLStr.startsWith("<vardata>")) {
			Map<String, String> parMap = new HashMap<String, String>();
			parMap.put("ggid", graId);
			List dataList = dao.query(" select gm.member.id, gm.memberName, gg.number from GeneralMember gm, GeneralGranted gg where gm.application.id = gg.application.id and gm.isDirector = 1 and gg.id = :ggid ", parMap);
			if(dataList != null) {
				Object[] o = (Object[]) dataList.get(0);
				//String directorId = o[0].toString();
				String directorName = o[1].toString();
				String grantedNumber = o[2].toString();
				try {
					Document document = DocumentHelper.parseText(varXMLStr);
					Element rootElement = document.getRootElement();
					Element projectElement = rootElement.element("Project");
					if(projectElement != null) {
						// 判断项目批准号是否一致
						if(grantedNumber != null && projectElement.elementText("pojNum") != null && grantedNumber.toUpperCase().equals(projectElement.elementText("pojNum").trim().toUpperCase())) {
							Element directorElement = rootElement.element("Director");//获取项目负责人的xml
							if(directorElement != null) {
								// word中负责人是否与项目负责人一致
								if(directorName != null && directorElement.elementText("directorName") != null && directorName.equals(directorElement.elementText("directorName").trim())) {
									//updateDirectorData(directorId, directorElement);
								}
							}
						}
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 通过word宏中的xml信息更新项目负责人的信息
	 * @param personId 人员id
	 * @param directorElement 项目负责人的dom对象
	 * @author leida 2011-09-15
	 */
	@SuppressWarnings("unchecked")
	public void updateDirectorData(String personId, Element directorElement) {
		//更新人员基本信息
		Person person = (Person) dao.query(Person.class, personId);
		if(person != null) {
			// 合并办公电话、家庭电话、移动电话、Email
			person.setOfficePhone(WordTool.updateData(person.getOfficePhone(), directorElement.elementText("directorOfficePhone"), 2, 400));
			person.setHomePhone(WordTool.updateData(person.getHomePhone(), directorElement.elementText("directorHomePhone"), 2, 400));
			person.setMobilePhone(WordTool.updateData(person.getMobilePhone(), directorElement.elementText("directorMobilePhone"), 2, 400));
			person.setEmail(WordTool.updateData(person.getEmail(), directorElement.elementText("directorEmail"), 2, 400));
			// 覆盖办公邮编、办公地址、证件类型、证件号码、性别、生日
			// TODO 人员的地址信息数据结构待确定
			person.setOfficePostcode(WordTool.updateData(person.getOfficePostcode(), directorElement.elementText("directorOfficePostcode"), 1, -1));
			person.setOfficeAddress(WordTool.updateData(person.getOfficeAddress(), directorElement.elementText("directorOfficeAddress"), 1, -1));
			person.setIdcardType(WordTool.updateData(person.getIdcardType(), directorElement.elementText("directorIdcardType"), 1, -1));
			person.setIdcardNumber(WordTool.updateData(person.getIdcardNumber(), directorElement.elementText("directorIdcardNumber"), 1, -1));
			person.setGender(WordTool.updateData(person.getGender(), directorElement.elementText("directorGender"), 1, -1));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				person.setBirthday(format.parse(directorElement.elementText("directorBirthday")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				dao.modify(person);
				System.out.println("Word:更新项目负责人基本信息成功!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//更新人员学术信息
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("personId", person.getId());
		List<Academic> academics = dao.query(" select a from Academic a where a.person.id = :personId ", parMap);
		if(academics != null && !academics.isEmpty()) {
			Academic academic = academics.get(0);
			// 合并外语语种、学科
			academic.setLanguage(WordTool.updateData(academic.getLanguage(), directorElement.elementText("directorLanguage"), 2, 400));
			academic.setDiscipline(WordTool.updateData(academic.getDiscipline(), directorElement.elementText("directorDiscipline"), 2, 800));
			// 覆盖专业职称、最后学位、导师类型
			academic.setSpecialityTitle(WordTool.updateData(academic.getSpecialityTitle(), directorElement.elementText("directorSpecialityTitle"), 1, -1));
			academic.setLastDegree(WordTool.updateData(academic.getLastDegree(), directorElement.elementText("directorLastDegree"), 1, -1));
			if(directorElement.elementText("directorIsDrTutor").equals("1")) {
				academic.setTutorType("博士生导师");
			}
			//增补研究领域
			academic.setResearchField(WordTool.updateData(academic.getResearchField(), directorElement.elementText("directorResearchField"), 3, 2000));
			try {
				dao.modify(academic);
				System.out.println("Word:更新项目负责人教育信息成功!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//----------以下为以定制列表形式获取项目信息,人员、机构模块用到---------
	
		/**
		 * 得到研究人员、高校、院系、研究基地的项目列表
		 * @param type 类型(1:研究人员 2:高校 3:院系 4:研究基地)
		 * @param entityId 实体id
		 * @return 项目列表
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List getProjectListByEntityId(int type, String entityId){
			if(entityId == null){
				return new ArrayList();
			}
			Map map = new HashMap();
			map.put("entityId", entityId);
			StringBuffer generalHql, keyHql, instpHql, postHql, entrustHql;
			List list = new ArrayList();
			List general, key, instp, post, entrust;
			//一般项目
			generalHql = new StringBuffer("select app.id, gra.id, gra.number, gra.name, mem.member.id, " +
					"mem.memberName, app.university.id, app.agencyName, '一般项目', app.disciplineType, app.year, " +
					"gra.approveFee, gra.status, app.applicantId, app.applicantName, app.finalAuditResult, app.finalAuditStatus, app.name from GeneralApplication app, GeneralMember mem left join app.generalGranted gra " +
					"left outer join mem.university uni left outer join mem.department dep left outer join mem.institute ins " +
					"where mem.application.id=app.id and ");
			//重大攻关项目
			keyHql = new StringBuffer("select app.id, gra.id, gra.number, gra.name, mem.member.id, " +
						"mem.memberName, app.university.id, app.agencyName, '重大攻关项目', app.disciplineType, app.year, " +
						"gra.approveFee, gra.status, app.applicantId, app.applicantName, app.finalAuditResult, app.finalAuditStatus, app.name from KeyApplication app, KeyMember mem left join app.keyGranted gra " +
						"left outer join mem.university uni left outer join mem.department dep left outer join mem.institute ins " +
						"where mem.application.id=app.id and ");
			//基地项目
			instpHql = new StringBuffer("select app.id, gra.id, gra.number, gra.name, mem.member.id, " +
						"mem.memberName, app.university.id, app.agencyName, '基地项目', app.disciplineType, app.year, " +
						"gra.approveFee, gra.status, app.applicantId, app.applicantName, app.finalAuditResult, app.finalAuditStatus, app.name from InstpApplication app, InstpMember mem left join app.instpGranted gra " +
						"left outer join mem.university uni left outer join mem.department dep left outer join mem.institute ins " +
						"where mem.application.id=app.id and ");
			//后期资助项目
			postHql = new StringBuffer("select app.id, gra.id, gra.number, gra.name, mem.member.id, " +
					"mem.memberName, app.university.id, app.agencyName, '后期资助项目', app.disciplineType, app.year, " +
					"gra.approveFee, gra.status, app.applicantId, app.applicantName, app.finalAuditResult, app.finalAuditStatus, app.name from PostApplication app, PostMember mem left outer join app.postGranted gra " +
					"left outer join mem.university uni left outer join mem.department dep left outer join mem.institute ins " +
					"where mem.application.id=app.id and ");
			//后期资助项目
			entrustHql = new StringBuffer("select app.id, gra.id, gra.number, gra.name, mem.member.id, " +
					"mem.memberName, app.university.id, app.agencyName, '委托应急课题', app.disciplineType, app.year, " +
					"gra.approveFee, gra.status, app.applicantId, app.applicantName, app.finalAuditResult, app.finalAuditStatus, app.name from EntrustApplication app, EntrustMember mem left outer join app.entrustGranted gra " +
					"left outer join mem.university uni left outer join mem.department dep left outer join mem.institute ins " +
					"left outer join app.topic son where mem.application.id=app.id and ");
			if(type == 1){
				generalHql.append("mem.member.id=:entityId order by app.year desc ");
				keyHql.append("mem.member.id=:entityId order by app.year desc ");
				instpHql.append("mem.member.id=:entityId order by app.year desc ");
				postHql.append("mem.member.id=:entityId order by app.year desc ");
				entrustHql.append("mem.member.id=:entityId order by app.year desc ");
			} else if(type == 2){
				generalHql.append("uni.id=:entityId order by app.year desc ");
				keyHql.append("uni.id=:entityId order by app.year desc ");
				instpHql.append("uni.id=:entityId order by app.year desc ");
				postHql.append("uni.id=:entityId order by app.year desc ");
				entrustHql.append("uni.id=:entityId order by app.year desc ");
			} else if(type == 3){
				generalHql.append("mem.department.id=:entityId order by app.year desc ");
				keyHql.append("mem.department.id=:entityId order by app.year desc ");
				instpHql.append("mem.department.id=:entityId order by app.year desc ");
				postHql.append("mem.department.id=:entityId order by app.year desc ");
				entrustHql.append("mem.department.id=:entityId order by app.year desc ");
			} else {
				generalHql.append("mem.isDirector = 1 and ins.id=:entityId order by app.year desc ");
				keyHql.append("mem.isDirector = 1 and ins.id=:entityId order by app.year desc ");
				instpHql.append("mem.isDirector = 1 and ins.id=:entityId order by app.year desc ");
				postHql.append("mem.isDirector = 1 and ins.id=:entityId order by app.year desc ");
				entrustHql.append("mem.isDirector = 1 and ins.id=:entityId order by app.year desc ");
			}
			general = dao.query(generalHql.toString(),map);
			key = dao.query(keyHql.toString(),map);
			instp = dao.query(instpHql.toString(),map);
			post = dao.query(postHql.toString(),map);
			entrust = dao.query(entrustHql.toString(),map);
			if(general.size()>0){
				for(int i=0;i<general.size();i++){
					list.add((Object[])general.get(i));
				}
			}
			if(key.size()>0){
				for(int i=0;i<key.size();i++){
					list.add((Object[])key.get(i));
				}
			}
			if(instp.size()>0){
				for(int i=0;i<instp.size();i++){
					list.add((Object[])instp.get(i));
				}
			}
			if(post.size()>0){
				for(int i=0;i<post.size();i++){
					list.add((Object[])post.get(i));
				}
			}
			if(entrust.size()>0){
				for(int i=0;i<entrust.size();i++){
					list.add((Object[])entrust.get(i));
				}
			}
			for (Object object : list) {
				Object[] o = (Object[]) object;
				if (o[3] == null) {
					o[3] = o[17];
				} 
			}
			return list;
		}
		//----------以下为项目导出一览表用到---------
		/**
		 * 处理项目类型对应的code
		 * @param 项目类型
		 * @return 查询语句
		 */
		public String getProjectCodeByType(String projectType){
			Map<String, String> map = new HashMap<String, String>();
			map.put("general", "01");
			map.put("instp", "02");
			map.put("post", "03");
			map.put("key", "04");
			map.put("entrust", "05");
			return map.get(projectType);
		}
		//----------以下为中检列表查询处理----------

		/**
		 * 获得当前登陆者的检索中检的查询语句
		 * @param HQL1  查询语句选择部分
		 * @param HQL2  查询语句条件部分
		 * @param accountType 帐号类别
		 * @return 检索中检的查询语句
		 */
		public StringBuffer getMidHql(String HQL1, String HQL2, AccountType accountType){
			StringBuffer hql = new StringBuffer();
			hql.append(HQL1);
			if(accountType.within(AccountType.EXPERT, AccountType.STUDENT)) {
				hql.append(", midi.applicantSubmitStatus, midi.applicantSubmitDate ").append(HQL2);
				hql.append(" and mem.member.id = :belongId and mem.groupNumber = gra.memberGroupNumber and (midi.status >= 1 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.INSTITUTE)) {
				hql.append(", midi.deptInstAuditStatus, midi.deptInstAuditResult, midi.deptInstAuditDate ").append(HQL2);
				hql.append(" and gra.institute.id = :belongId and (midi.status >= 2 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.DEPARTMENT)){
				hql.append(", midi.deptInstAuditStatus, midi.deptInstAuditResult, midi.deptInstAuditDate ").append(HQL2);
				hql.append(" and gra.department.id = :belongId and (midi.status >= 2 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)) {
				hql.append(", midi.universityAuditStatus, midi.universityAuditResult, midi.universityAuditDate ").append(HQL2);
				hql.append(" and uni.id = :belongId and (midi.status >= 3 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.PROVINCE)) {
				hql.append(", midi.provinceAuditStatus, midi.provinceAuditResult, midi.provinceAuditDate ").append(HQL2);
				hql.append(" and uni.type = 4 and uni.subjection.id = :belongId and (midi.status >= 4 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.MINISTRY)) {
				hql.append(", midi.finalAuditDate ").append(HQL2);
				hql.append(" and (midi.status >= 5 or midi.createMode=1 or midi.createMode=2) ");
			}else if(accountType.equals(AccountType.ADMINISTRATOR)){
				hql.append(", midi.finalAuditDate ").append(HQL2);
			}else{
				hql.append(", midi.finalAuditDate ").append(HQL2).append(" and 1=0 ");
			}
			return hql;
		}

		//----------以下为判断审核人和当前登录人是否一致用到---------
		/**
		 * 处理申请审核人和所在机构信息
		 * @param 申请对象
		 * @return 信息list
		 */
		public List getAppAuditorInfo(ProjectApplication application,LoginInfo loginer){
			if(null != application){
				List appAuditorInfo = new ArrayList<String>();
				appAuditorInfo.add(0, application.getDeptInstAuditor() == null ? "" : application.getDeptInstAuditor().getId());//院系基地审核人id
				appAuditorInfo.add(1, application.getDeptInstAuditorDept() == null ? "" : application.getDeptInstAuditorDept().getId());//审核人所在院系id
				appAuditorInfo.add(2, application.getDeptInstAuditorInst() == null ? "" : application.getDeptInstAuditorInst().getId());//审核人所在基地id
				appAuditorInfo.add(3, application.getUniversityAuditor() == null ? "" : application.getUniversityAuditor().getId());//高校审核人id
				appAuditorInfo.add(4, application.getUniversityAuditorAgency() == null ? "" : application.getUniversityAuditorAgency().getId());//审核人所在高校id
				appAuditorInfo.add(5, application.getProvinceAuditor() == null ? "" : application.getProvinceAuditor().getId());//省厅审核人id
				appAuditorInfo.add(6, application.getProvinceAuditorAgency() == null ? "" : application.getProvinceAuditorAgency().getId());//审核人所在省厅id
				appAuditorInfo.add(7, application.getMinistryAuditor() == null ? "" : application.getMinistryAuditor().getId());//部级审核人id
				appAuditorInfo.add(8, application.getMinistryAuditorAgency() == null ? "" : application.getMinistryAuditorAgency().getId());//部级审核人机构id
				return appAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 处理立项计划审核人和所在机构信息
		 * @param 中检对象
		 * @return 信息list
		 */
		public List getGraAuditorInfo(ProjectGranted projectGranted, LoginInfo loginer){
			if(null != projectGranted){
				List graAuditorInfo = new ArrayList<String>();
				graAuditorInfo.add(0, projectGranted.getDeptInstAuditor() == null ? "" :projectGranted.getDeptInstAuditor().getId() );//院系基地审核人id
				graAuditorInfo.add(1, projectGranted.getDeptInstAuditorDept() == null ? "" : projectGranted.getDeptInstAuditorDept().getId());//审核人所在院系id
				graAuditorInfo.add(2, projectGranted.getDeptInstAuditorInst() == null ? "" : projectGranted.getDeptInstAuditorInst().getId());//审核人所在基地id
				graAuditorInfo.add(3, projectGranted.getUniversityAuditor() == null ? "" : projectGranted.getUniversityAuditor().getId());//高校审核人id
				graAuditorInfo.add(4, projectGranted.getUniversityAuditorAgency() == null ? "" : projectGranted.getUniversityAuditorAgency().getId());//审核人所在高校id
				graAuditorInfo.add(5, projectGranted.getProvinceAuditor() == null ? "" : projectGranted.getProvinceAuditor().getId());//省厅审核人id
				graAuditorInfo.add(6, projectGranted.getProvinceAuditorAgency() == null ? "" : projectGranted.getProvinceAuditorAgency().getId());//审核人所在省厅id
				graAuditorInfo.add(7, projectGranted.getFinalAuditor() == null ? "" : projectGranted.getFinalAuditor().getId());//最终审核人id
				graAuditorInfo.add(8, projectGranted.getFinalAuditorAgency() == null ? "" : projectGranted.getFinalAuditorAgency().getId());//最终审核人机构id
				return graAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 处理年检审核人和所在机构信息
		 * @param 年检对象
		 * @return 信息list
		 */
		public List getAnnAuditorInfo(ProjectAnninspection anninspection,LoginInfo loginer){
			if(null != anninspection){
				List annAuditorInfo = new ArrayList<String>();
				annAuditorInfo.add(0, anninspection.getDeptInstAuditor() == null ? "" : anninspection.getDeptInstAuditor().getId());//院系基地审核人id
				annAuditorInfo.add(1, anninspection.getDeptInstAuditorDept() == null ? "" : anninspection.getDeptInstAuditorDept().getId());//审核人所在院系id
				annAuditorInfo.add(2, anninspection.getDeptInstAuditorInst() == null ? "" : anninspection.getDeptInstAuditorInst().getId());//审核人所在基地id
				annAuditorInfo.add(3, anninspection.getUniversityAuditor() == null ? "" : anninspection.getUniversityAuditor().getId());//高校审核人id
				annAuditorInfo.add(4, anninspection.getUniversityAuditorAgency() == null ? "" : anninspection.getUniversityAuditorAgency().getId());//审核人所在高校id
				annAuditorInfo.add(5, anninspection.getProvinceAuditor() == null ? "" : anninspection.getProvinceAuditor().getId());//省厅审核人id
				annAuditorInfo.add(6, anninspection.getProvinceAuditorAgency() == null ? "" : anninspection.getProvinceAuditorAgency().getId());//审核人所在省厅id
				annAuditorInfo.add(7, anninspection.getFinalAuditor() == null ? "" : anninspection.getFinalAuditor().getId());//最终审核人id
				annAuditorInfo.add(8, anninspection.getFinalAuditorAgency() == null ? "" : anninspection.getFinalAuditorAgency().getId());//最终审核人机构id
				return annAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 处理中检审核人和所在机构信息
		 * @param 中检对象
		 * @return 信息list
		 */
		public List getMidAuditorInfo(ProjectMidinspection midinspection,LoginInfo loginer){
			if(null != midinspection){
				List midAuditorInfo = new ArrayList<String>();
				midAuditorInfo.add(0, midinspection.getDeptInstAuditor() == null ? "" :midinspection.getDeptInstAuditor().getId() );//院系基地审核人id
				midAuditorInfo.add(1, midinspection.getDeptInstAuditorDept() == null ? "" : midinspection.getDeptInstAuditorDept().getId());//审核人所在院系id
				midAuditorInfo.add(2, midinspection.getDeptInstAuditorInst() == null ? "" : midinspection.getDeptInstAuditorInst().getId());//审核人所在基地id
				midAuditorInfo.add(3, midinspection.getUniversityAuditor() == null ? "" : midinspection.getUniversityAuditor().getId());//高校审核人id
				midAuditorInfo.add(4, midinspection.getUniversityAuditorAgency() == null ? "" : midinspection.getUniversityAuditorAgency().getId());//审核人所在高校id
				midAuditorInfo.add(5, midinspection.getProvinceAuditor() == null ? "" : midinspection.getProvinceAuditor().getId());//省厅审核人id
				midAuditorInfo.add(6, midinspection.getProvinceAuditorAgency() == null ? "" : midinspection.getProvinceAuditorAgency().getId());//审核人所在省厅id
				midAuditorInfo.add(7, midinspection.getFinalAuditor() == null ? "" : midinspection.getFinalAuditor().getId());//最终审核人id
				midAuditorInfo.add(8, midinspection.getFinalAuditorAgency() == null ? "" : midinspection.getFinalAuditorAgency().getId());//最终审核人机构id
				return midAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 处理结项审核人和所在机构信息
		 * @param 结项对象
		 * @return 信息list
		 */
		public List getEndAuditorInfo(ProjectEndinspection endinspection,LoginInfo loginer){
			if(null != endinspection){
				List endAuditorInfo = new ArrayList<String>();
				endAuditorInfo.add(0, endinspection.getDeptInstAuditor() == null ? "" : endinspection.getDeptInstAuditor().getId());//院系基地审核人id
				endAuditorInfo.add(1, endinspection.getDeptInstAuditorDept() == null ? "" : endinspection.getDeptInstAuditorDept().getId());//审核人所在院系id
				endAuditorInfo.add(2, endinspection.getDeptInstAuditorInst() == null ? "" : endinspection.getDeptInstAuditorInst().getId());//审核人所在基地id
				endAuditorInfo.add(3, endinspection.getUniversityAuditor() == null ? "" : endinspection.getUniversityAuditor().getId());//高校审核人id
				endAuditorInfo.add(4, endinspection.getUniversityAuditorAgency() == null ? "" : endinspection.getUniversityAuditorAgency().getId());//审核人所在高校id
				endAuditorInfo.add(5, endinspection.getProvinceAuditor() == null ? "" : endinspection.getProvinceAuditor().getId());//省厅审核人id
				endAuditorInfo.add(6, endinspection.getProvinceAuditorAgency() == null ? "" : endinspection.getProvinceAuditorAgency().getId());//审核人所在省厅id
				endAuditorInfo.add(7, endinspection.getMinistryAuditor() == null ? "" : endinspection.getMinistryAuditor().getId());//部级审核人id
				endAuditorInfo.add(8, endinspection.getMinistryAuditorAgency() == null ? "" : endinspection.getMinistryAuditorAgency().getId());//审核人所在机构id
				return endAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 处理变更审核人和所在机构信息
		 * @param 变更对象
		 * @return 信息list
		 */
		public List getVarAuditorInfo(ProjectVariation variation,LoginInfo loginer){
			if(null != variation){
				List varAuditorInfo = new ArrayList<String>();
				varAuditorInfo.add(0, variation.getDeptInstAuditor() == null ? "" : variation.getDeptInstAuditor().getId());//院系基地审核人id
				varAuditorInfo.add(1, variation.getDeptInstAuditorDept() == null ? "" : variation.getDeptInstAuditorDept().getId());//审核人所在院系id
				varAuditorInfo.add(2, variation.getDeptInstAuditorInst() == null ? "" : variation.getDeptInstAuditorInst().getId());//审核人所在基地id
				varAuditorInfo.add(3, variation.getUniversityAuditor() == null ? "" : variation.getUniversityAuditor().getId());//高校审核人id
				varAuditorInfo.add(4, variation.getUniversityAuditorAgency() == null ? "" : variation.getUniversityAuditorAgency().getId());//审核人所在高校id
				varAuditorInfo.add(5, variation.getProvinceAuditor() == null ? "" : variation.getProvinceAuditor().getId());//省厅审核人id
				varAuditorInfo.add(6, variation.getProvinceAuditorAgency() == null ? "" : variation.getProvinceAuditorAgency().getId());//审核人所在省厅id
				varAuditorInfo.add(7, variation.getFinalAuditor() == null ? "" : variation.getFinalAuditor().getId());//最终审核人id
				varAuditorInfo.add(8, variation.getFinalAuditorAgency() == null ? "" : variation.getFinalAuditorAgency().getId());//最终审核人机构id
				return varAuditorInfo;
			}else {
				return null;
			}
		}
		
		/**
		 * 把各类项目附件上传到DMSS
		 * @param <T>
		 * @param project
		 * @return 返回dfsId
		 * @throws Exception
		 */
		public  <T> String uploadToDmss(T project) throws Exception {
			String file = (String) project.getClass().getMethod("getFile").invoke(project);
			if(dmssService.getStatus() && null != file && !file.isEmpty()) {
				ThirdUploadForm form = new ThirdUploadForm();
				String applicant = null;
				form.setTitle(getFileTitle(file));
				form.setFileName(getFileName(file));
				
				// TODO 某些类型的项目没有相应的方法，需要判断项目类型调用适当的方法
				if(project instanceof ProjectVariation) {
					applicant = ((ProjectVariation) project).getGranted().getApplicantName();
				} else if(project instanceof ProjectEndinspection) {
					applicant = ((ProjectEndinspection) project).getGranted().getApplicantName();
				} else if(project instanceof ProjectMidinspection ){
					applicant = ((ProjectMidinspection) project).getGranted().getApplicantName();
				} else {
					applicant = (String) project.getClass().getMethod("getApplicantName").invoke(project);
				}
				form.setSourceAuthor(applicant);
				form.setRating("5.0");
				form.setTags("");
				form.setCategoryPath(getDmssCategory(file));
				String dfsId =dmssService.upload(ApplicationContainer.sc.getRealPath(file), form);
				if (null == dfsId) {
					throw new RuntimeException();
				}
				return dfsId;
			} else {
				throw new RuntimeException();
			}
		}
		
		/**
		 * 把各类项目附件检入到MDSS
		 * @param product
		 * @return 返回dfsId
		 * @throws Exception
		 */
		public <T> String checkInToDmss(T project) throws Exception{
			String file = (String) project.getClass().getMethod("getFile").invoke(project);
			String orgDfsId = (String) project.getClass().getMethod("getDfs").invoke(project);
			if(null != file && null != orgDfsId){ //现在有文件
				ThirdCheckInForm form = new ThirdCheckInForm();
				form.setComment("更新了");
				form.setFileName(getFileName(file));
				form.setTitle(getFileTitle(file));
				form.setId(orgDfsId);
				String dfsId = dmssService.checkIn(ApplicationContainer.sc.getRealPath(file), form);
				if (null == dfsId) {
					throw new RuntimeException();
				}
				return dfsId;
			}else{
				throw new RuntimeException();
			}
		}
		/**
		 * 判断项目变更原因是否为中检延期
		 */
		boolean ifMidDefer(String varId) {
			ProjectVariation variation = (ProjectVariation)dao.query(ProjectVariation.class, varId);
			if(variation.getOtherInfo()!=null) {
				if(variation.getOtherInfo().trim().equals("推迟中期检查"))
					return true;
				else
					return false;
			} else {
				return false;
			}

		}
		/**
		 * 查询项目数据发布情况
		 * @param appId 项目申请Id;
		 * @return Map 项目数据对应C_SYNC_STATUS表中各接口的发布情况  存在一个Map里
		 */
		public Map getSynStatus(String appId) {
			//这个HashMap用来保存一个项目的所有接口的数据发布情况
			Map map = new HashMap();
			//查询项目申报审核结果发布情况
			Map appMap = new HashMap();
			String appHql = "select syn from SyncStatus syn where syn.projectId =:projectId and syn.interfaceName =:interfaceName order by syn.requestDate desc";
			appMap.put("projectId", appId);
			appMap.put("interfaceName", SinossInterface.APP_RESULT.toString());
			List<SyncStatus> appSynStatus = new ArrayList<SyncStatus>();
			appSynStatus = dao.query(appHql, appMap);
			if(!appSynStatus.isEmpty())
				map.put(SinossInterface.APP_RESULT, appSynStatus.get(0));
			//查询项目中检审核结果发布情况
			String graId = this.getGrantedIdByAppId(appId);
			String midId = this.getMidinspectionIdByGraId(graId);
			if(midId!=null) {
				Map midMap = new HashMap();
				String midHql = "select syn from SyncStatus syn where syn.midinspectionId =:midinspectionId and syn.interfaceName =:interfaceName order by syn.requestDate desc";
				midMap.put("midinspectionId", midId);
				midMap.put("interfaceName", SinossInterface.MID_RESULT.toString());
				List<SyncStatus> midResultSynStatus = dao.query(midHql, midMap);
				if(!midResultSynStatus.isEmpty())
					map.put(SinossInterface.MID_RESULT, midResultSynStatus.get(0));
			}
			//查询项目变更审核结果发布情况
			List<String> varIds = this.getVariationIdByGraId(graId);
			if(varIds!=null) {
				List<SyncStatus> varResultSynStatus = new ArrayList<SyncStatus>();
				for(String varId: varIds) {
					Map varMap =  new HashMap();
					String varHql = "select syn from SyncStatus syn where syn.variationId =:variationId and syn.interfaceName =:interfaceName order by syn.requestDate desc";
					varMap.put("variationId", varId);
					varMap.put("interfaceName", SinossInterface.VAR_RESULT.toString());
					List<SyncStatus> var = dao.query(varHql, varMap);
					if(!var.isEmpty())
						varResultSynStatus.add(var.get(0));
					//查询项目中检延期数据发布情况
					if(ifMidDefer(varId)){
						Map midDeferMap = new HashMap();
						midDeferMap.put("variationId", varId);
						midDeferMap.put("interfaceName", SinossInterface.MID_DEFER.toString());
						List<SyncStatus> midDeferSynStatus = dao.query(varHql, varMap);
						if(!midDeferSynStatus.isEmpty())
							map.put(SinossInterface.MID_DEFER, midDeferSynStatus.get(0));
					}
				}
				if(!varResultSynStatus.isEmpty())
					map.put(SinossInterface.VAR_RESULT, varResultSynStatus);
			}
			//查询项目结项审核结果发布情况
			String endId = this.getEndinspectionIdByGraId(graId);
			if(endId!=null) {
				Map endMap = new HashMap();
				String endHql = "select syn from SyncStatus syn where syn.endinspectionId =:endinspectionId and syn.interfaceName =:interfaceName order by syn.requestDate desc";
				endMap.put("endinspectionId", endId);
				endMap.put("interfaceName", SinossInterface.END_RESULT.toString());
				List<SyncStatus> endResultSynStatus = dao.query(endHql, endMap);
				if(!endResultSynStatus.isEmpty())
					map.put(SinossInterface.END_RESULT, endResultSynStatus.get(0));
			}
			//查询项目需要中检数据发布情况
			Map midRequiredMap = new HashMap();
			String midRequiredHql = "select syn from SyncStatus syn where syn.projectId =:projectId and syn.interfaceName =:interfaceName order by syn.requestDate desc";
			midRequiredMap.put("projectId", appId);
			midRequiredMap.put("interfaceName", SinossInterface.MID_REQUIRED.toString());
			List<SyncStatus> midRequiredSynStatus = dao.query(midRequiredHql, midRequiredMap);
			if(!midRequiredSynStatus.isEmpty())
				map.put(SinossInterface.MID_REQUIRED, midRequiredSynStatus.get(0));
			return map;
		}
}