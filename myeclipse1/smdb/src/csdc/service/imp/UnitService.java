package csdc.service.imp;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.BankAccount;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.InstituteFunding;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.SystemOption;
import csdc.service.IProductService;
import csdc.service.IUnitService;
import csdc.service.ext.IProjectExtService;
import csdc.service.ext.IUnitExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.InputValidate;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.info.UnitInfo;
import csdc.tool.merger.DepartmentMerger;
import csdc.tool.merger.InstituteMerger;

/**
 * 机构service实现类
 * @author 江荣国
 * @author fengcl
 * @version 2011.04.16
 * @version 2012.06.28
 */
@SuppressWarnings("unchecked")
@Transactional
public class UnitService extends BaseService implements IUnitService, IUnitExtService {
	@Autowired
	private IProductService productService;
	@Autowired
	private IProjectExtService projectExtService;
	
	/**
	 * 获取教育部的id(仅根据机构代码为360)
	 * @return 教育部id
	 */
	public String getMOEId(){
		List<String> list = dao.query("select ag.id from Agency ag where ag.code='360'");
		return (list.size() > 0) ? list.get(0) : null;
	}
	
	/**
	 * 获取教育部对象(仅根据机构代码为360)
	 * @return 教育部对象
	 */
	public Agency getMOE(){
		List<Agency> list = dao.query("from Agency ag where ag.code='360'");
		return (list.size() > 0) ? list.get(0) : null;
	}
	
	public void getViewOfAgency(String entityId, Map jsonMap){
		Agency agency=(Agency) dao.query(Agency.class, entityId);
		jsonMap.put("agency",agency);
		String provinceName = (agency.getProvince() == null)? null:agency.getProvince().getName();
		jsonMap.put("provinceName",provinceName);
		String cityName = (agency.getCity() == null)? null:agency.getCity().getName();
		jsonMap.put("cityName", cityName);
		String subjectionName = (agency.getSubjection() == null)? null:agency.getSubjection().getName();
		jsonMap.put("subjectionName", subjectionName);
		String subjectionId =(agency.getSubjection() ==null)? null:agency.getSubjection().getId();
		jsonMap.put("subjectionId", subjectionId);
		String directorName = (agency.getDirector() == null)? null:agency.getDirector().getName();
		jsonMap.put("directorName",directorName);
		String directorId = (agency.getDirector() == null)? null:agency.getDirector().getId();
		jsonMap.put("directorId",directorId);
		String sDirectorName = (agency.getSdirector() == null)? null:agency.getSdirector().getName();
		jsonMap.put("sDirectorName", sDirectorName);
		String sDirectorId =(agency.getSdirector()==null)? null:agency.getSdirector().getId();
		jsonMap.put("sDirectorId",sDirectorId);
		String sLinkmanName = (agency.getSlinkman() == null)? null:agency.getSlinkman().getName();
		jsonMap.put("sLinkmanName", sLinkmanName);
		String sLinkmanId = (agency.getSlinkman() == null)? null:agency.getSlinkman().getId();
		jsonMap.put("sLinkmanId", sLinkmanId);
		String fDirectorName = agency.getFdirector();
		jsonMap.put("fDirectorName", fDirectorName);
		String fLinkmanName = agency.getFlinkman();
		jsonMap.put("fLinkmanName", fLinkmanName);
		//机构类型，将数字转化为名称字符串
		String[] str = {"部级","省级","部属高校","地方高校"};
		if(agency.getType()>0 && agency.getType()<5){
			jsonMap.put("typeName", str[agency.getType()-1]);
		}else{
			jsonMap.put("typeName", null);
		}
		if(agency.getType() ==3 || agency.getType()==4){ //查看学校
			List deptList = this.getDeptsByUnivId(entityId);
			jsonMap.put("deptList", deptList);
			String deptNum = this.getDeptNumByUnivId(entityId);
			jsonMap.put("deptNum",deptNum);
		}
		Account account=(Account)this.getAccountByUnitId(entityId,1);
		if(account != null){
			jsonMap.put("account",account);
			Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			jsonMap.put("passport", passport);
		} else {
			jsonMap.put("account", null);
			jsonMap.put("passport", null);
		}
		List doctorial = this.getDoctorialList(3, entityId);
		jsonMap.put("doctorial", doctorial);
		List discipline = this.getDisciplineList(3, entityId);
		jsonMap.put("discipline", discipline);
		String bankIds = agency.getBankIds();
		List bankList = dao.query("select ba from BankAccount ba where ba.ids = ? order by ba.sn asc ", bankIds);
		jsonMap.put("bankList", bankList);
		List commonAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getAddressIds());
		jsonMap.put("commonAddress", commonAddress);
		List financeAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getFaddressIds());
		jsonMap.put("financeAddress", financeAddress);
		List subjectionAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getSaddressIds());
		jsonMap.put("subjectionAddress", subjectionAddress);
	}
	
	public void getViewOfDepartment(String entityId, Map jsonMap){
		Department department=(Department) dao.query(Department.class, entityId);
		jsonMap.put("department", department);
		String universityName =(department.getUniversity() == null)?null:department.getUniversity().getName();
		jsonMap.put("universityName", universityName);
		String universityId =(department.getUniversity() ==null)?null:department.getUniversity().getId();
		jsonMap.put("universityId", universityId);
		String directorName =(department.getDirector() == null)?null:department.getDirector().getName();
		jsonMap.put("directorName", directorName);
		String directorId=(department.getDirector() ==null)?null:department.getDirector().getId();
		jsonMap.put("directorId", directorId);
		String linkmanName = (department.getLinkman() == null)?null:department.getLinkman().getName();
		jsonMap.put("linkmanName", linkmanName);
		String linkmanId =(department.getLinkman() ==null)?null:department.getLinkman().getId();
		jsonMap.put("linkmanId",linkmanId);
		Account account = (Account)this.getAccountByUnitId(entityId, 2);
		if(account != null){
			jsonMap.put("account", account);
			Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			jsonMap.put("passport", passport);
		} else {
			jsonMap.put("account", null);
			jsonMap.put("passport", null);
		}
		List doctorial = this.getDoctorialList(5, entityId);
		jsonMap.put("doctorial", doctorial);
		List discipline = this.getDisciplineList(5, entityId);
		jsonMap.put("discipline", discipline);
		//地址信息
		List<Address> address = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", department.getAddressIds());
		jsonMap.put("commonAddress", address);
	}
	
	public void getViewOfInstitute(String entityId, Map jsonMap){
		Institute institute = (Institute) dao.query(Institute.class, entityId);
		jsonMap.put("institute", institute);
		if(institute.getResearchActivityType() != null){
			SystemOption researchActivityType = (SystemOption) dao.query(SystemOption.class, institute.getResearchActivityType().getId());
			jsonMap.put("researchActivityType", researchActivityType.getName());// 研究活动类型
		} else {
			jsonMap.put("researchActivityType", null);
		}
		String subjectionName = (institute.getSubjection() == null)? null:institute.getSubjection().getName();
		jsonMap.put("subjectionName", subjectionName);
		String subjectionId =(institute.getSubjection() ==null)? null:institute.getSubjection().getId();
		jsonMap.put("subjectionId", subjectionId);
		String directorName = (institute.getDirector() == null)? null:institute.getDirector().getName();
		jsonMap.put("directorName",directorName);
		String directorId = (institute.getDirector() == null)? null:institute.getDirector().getId();
		jsonMap.put("directorId",directorId);
		String linkmanName = (institute.getLinkman() == null)? null:institute.getLinkman().getName();
		jsonMap.put("linkmanName", linkmanName);
		String linkmanId=(institute.getLinkman()==null)? null:institute.getLinkman().getId();
		jsonMap.put("linkmanId",linkmanId);
//		if(institute.getResearchArea() !=null){
//			String researchAreaName = unitService.getNamesByIds(institute.getResearchArea().getId());
//			jsonMap.put("researchAreaName", researchAreaName);
//		}else{
//			jsonMap.put("researchAreaName", null);
//		}
		String instType =(institute.getType()==null)? null:institute.getType().getName();
		jsonMap.put("instType",instType);
		Account account=(Account)this.getAccountByUnitId(entityId, 3); //账号信息
		if(account != null){
			jsonMap.put("account",account);
			Passport passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
			jsonMap.put("passport", passport);
		} else {
			jsonMap.put("account", null);
			jsonMap.put("passport", null);
		}
		// 博士点信息
		List doctorial = this.getDoctorialList(6, entityId);
		jsonMap.put("doctorial", doctorial);
		// 重点学科信息
		List discipline = this.getDisciplineList(6, entityId);
		jsonMap.put("discipline", discipline);
		// 项目信息
		List project = this.projectExtService.getProjectListByEntityId(4, entityId);
		jsonMap.put("institute_project", project);
		// 成果信息
		List product = this.productService.getProductListByEntityId(4, entityId);
		jsonMap.put("institute_product", product);
		// 人员信息
		List officer = this.getPersonList(4, 1, entityId);
		List teacher = this.getPersonList(4, 2, entityId);
		List student = this.getPersonList(4, 3, entityId);
		jsonMap.put("officer", officer);
		jsonMap.put("teacher", teacher);
		jsonMap.put("student", student);
		// 经费信息
		List funding = this.getInstituteFunding(entityId);
		jsonMap.put("funding", funding);
		//地址信息
		List<Address> address = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", institute.getAddressIds());
		jsonMap.put("commonAddress", address);
	}
	
	public void getModifyOfAgency(Map session, Agency agency, String directorId, String slinkmanId, String sdirectorId, String univOrganizerCode, String univOrganizer, String entityId){
		Agency oldAgency = (Agency) dao.query(Agency.class, entityId);
		oldAgency.setName(agency.getName());
		oldAgency.setEnglishName(agency.getEnglishName());
		oldAgency.setAbbr(agency.getAbbr());
		oldAgency.setCode(agency.getCode());
		oldAgency.setType(agency.getType());
		if (null != agency.getProvince().getId()) {
			SystemOption province = (SystemOption) dao.query(SystemOption.class, agency.getProvince().getId());
			oldAgency.setProvince(province);
		}
		if (null != agency.getCity().getId()) {
			SystemOption city = (SystemOption) dao.query(SystemOption.class, agency.getCity().getId());
			oldAgency.setCity(city);
		}
		if (agency.getType() == 3 && null != agency.getSubjection().getId()) {//高校才修改上级管理机构，部级省级默认为教育部
			Agency subjection = (Agency) dao.query(Agency.class, agency.getSubjection().getId());
			oldAgency.setSubjection(subjection);
		}
		if (null != directorId) {
			oldAgency.setDirector(this.getPersonByOfficerId(directorId));
		}
		if (null != slinkmanId) {
			oldAgency.setSlinkman(this.getPersonByOfficerId(slinkmanId));
		}
		if (null != sdirectorId) {
			oldAgency.setSdirector(this.getPersonByOfficerId(sdirectorId));
		}
		oldAgency.setIntroduction(agency.getIntroduction());
		oldAgency.setPhone(agency.getPhone());
		oldAgency.setFax(agency.getFax());
		oldAgency.setEmail(agency.getEmail());
		oldAgency.setHomepage(agency.getHomepage());
		oldAgency.setSname(agency.getSname());
		oldAgency.setSphone(agency.getSphone());
		oldAgency.setSfax(agency.getSfax());
		oldAgency.setSemail(agency.getSemail());
		oldAgency.setShomepage(agency.getShomepage());
		oldAgency.setFname(agency.getFname());
		oldAgency.setFdirector(agency.getFdirector());
		oldAgency.setFlinkman(agency.getFlinkman());
		oldAgency.setFphone(agency.getFphone());
		oldAgency.setFfax(agency.getFfax());
		oldAgency.setFemail(agency.getFemail());
		oldAgency.setStyle(agency.getStyle());
		oldAgency.setCategory(agency.getCategory());
		oldAgency.setReviewLevel(agency.getReviewLevel());
		oldAgency.setReviewScore(agency.getReviewScore());
		oldAgency.setAcronym(agency.getAcronym());
		
		// 在声明式事务中不能有两个相同id的实体。所以以下控制agency不让修改subjection/fdirector/flinkman的方法有问题。
		// 如果需要对这些值进行控制，就应该去掉jsp中的agency.id，将agency中的字段值set到旧的对象中再修改。
//		Agency agency2 = unitService.getAgencyById(entityId);
//		下面这句想干啥，不让改subjection么?
//		agency.setSubjection(agency2.getSubjection());
//		agency.setFdirector(agency2.getFdirector());
//		agency.setFlinkman(agency2.getFlinkman());
		//合并学校举办者代码与举办者，并以“代码/名称”的方式存入数据库
		StringBuffer organizer = new StringBuffer();
		if(null != univOrganizerCode && univOrganizerCode.trim().length()>0){
			organizer.append(univOrganizerCode+"/");
			if(null != univOrganizer && univOrganizer.trim().length()>0){
				organizer.append(univOrganizer);
			}
		}else{
			if(null != univOrganizer && univOrganizer.trim().length()>0){
				organizer.append("/"+univOrganizer);
			}
		}
		oldAgency.setOrganizer(organizer.toString());
		
		dao.modify(oldAgency);
	}
	
	public void getValidateModify(InputValidate inputValidate, List<Address> addressList, ActionSupport thisAction){
		for(Address address : addressList){
			if(null != address.getPostCode() && !inputValidate.checkPostcode(address.getPostCode().trim())){
				thisAction.addFieldError("address.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
			}
			if(null != address.getAddress() && address.getAddress().trim().length()>100){
				thisAction.addFieldError("address.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
			}
		}
	}
	
	public void getValidateModifyOfAgency(Agency agency, List<BankAccount> bankList, InputValidate inputValidate, ActionSupport thisAction) {
		if(null == agency.getName() || agency.getName().trim().length()>40 || agency.getName().trim().length()<1){
			thisAction.addFieldError("agency.name",UnitInfo.ERROR_NAME_ILLEGAL);
		}

		if(null != agency.getEnglishName() && (agency.getEnglishName().trim().length()>40 || agency.getEnglishName().trim().length()<0)){
			thisAction.addFieldError("agency.englishName",UnitInfo.ERROR_ENGLISHNAME_ILLEGAL);
		}

		if(null !=agency.getCode() && (agency.getCode().trim().length()>40 || agency.getCode().trim().length()<0)){
			thisAction.addFieldError("agency.code",UnitInfo.ERROR_CODE_ILLEGAL);
		}

		if(null != agency.getAbbr() && agency.getAbbr().trim().length()>40 || agency.getAbbr().trim().length()<0){
			thisAction.addFieldError("agency.abbr",UnitInfo.ERROR_ABBR_ILLEGAL);
		}

		if(agency.getType()>5 || agency.getType()<0){
			thisAction.addFieldError("agency.type",UnitInfo.ERROR_AGENCY_TYPE_ILLEAGER);
		}

		if(null == agency.getProvince().getId() || agency.getProvince().getId().trim().length()==0 ){
			thisAction.addFieldError("agency.province.id",UnitInfo.ERROR_PROVINCE_NULL);
		}else if(agency.getProvince().getId().trim().length()>40 || agency.getProvince().getId().trim().length()<0){
			thisAction.addFieldError("agency.province.id",UnitInfo.ERROR_PROVINCE_ILLEGAL);
		}

		if(null != agency.getCity().getId() && (agency.getCity().getId().trim().length()>40 || agency.getCity().getId().trim().length()<0 )){
			thisAction.addFieldError("agency.city",UnitInfo.ERROR_CITY_ILLEAGER);
		}

		if(agency.getType() == 3 || agency.getType() == 4) {
			if(null== agency.getSubjection() || null == agency.getSubjection() || "".equals(agency.getSubjection().getId().trim())){
				thisAction.addFieldError("agency.subjection.id",UnitInfo.ERROR_SUBJECTION_NULL);
			}
		}
		
		if(null != agency.getIntroduction() && agency.getIntroduction().trim().length()>20000){
			thisAction.addFieldError("agency.introduction",UnitInfo.ERROR_INTRODUCTION_ILLEGAL);
		}
		if(null != agency.getHomepage() && agency.getHomepage().trim().length()>60){
			thisAction.addFieldError("agency.homepage",UnitInfo.ERROR_HOMEPAGE_ILLEGAL);
		}

		if(null != agency.getShomepage() && agency.getShomepage().trim().length()>40){
			thisAction.addFieldError("agency.shomepage",UnitInfo.ERROR_SHOMEPAGE_ILLEGAL);
		}
		for(BankAccount bankAccount : bankList){
			if (bankAccount.getBankName() != null && bankAccount.getBankName().trim().length() > 50){
				thisAction.addFieldError("bankAccount.bankName", UnitInfo.ERROR_BANKNAME_ILLEGAL);
			}
			if (bankAccount.getBankCupNumber() != null && bankAccount.getBankCupNumber().trim().length() > 40){
				thisAction.addFieldError("bankAccount.bankCupNumber", UnitInfo.ERROR_FCUPNUMBER_ILLEGAL);
			}
			if (bankAccount.getAccountName() != null && bankAccount.getAccountName().trim().length() > 50){
				thisAction.addFieldError("bankAccount.accountName", UnitInfo.ERROR_BANKACCOUNTNAME_ILLEGAL);
			}
			if (bankAccount.getAccountNumber() != null && bankAccount.getAccountNumber().trim().length() > 40){
				thisAction.addFieldError("bankAccount.accountNumber", UnitInfo.ERROR_BANKACCOUN_ILLEGAL);
			}
		}
	}
	
	public void getModifyOfDepartment(Map session, Department department, String directorId, String linkmanId, String entityId){
		Department oldDepartment = (Department) dao.query(Department.class, entityId);
		oldDepartment.setName(department.getName());
		oldDepartment.setCode(department.getCode());
		oldDepartment.setIntroduction(department.getIntroduction());
		oldDepartment.setPhone(department.getPhone());
		oldDepartment.setFax(department.getFax());
		oldDepartment.setEmail(department.getEmail());
		oldDepartment.setHomepage(department.getHomepage());
		if(null != department.getUniversity().getId()){
			Agency university = (Agency) dao.query(Agency.class, department.getUniversity().getId());
			oldDepartment.setUniversity(university);
		}
		if (null != directorId) {
			oldDepartment.setDirector(this.getPersonByOfficerId(directorId));
		}
		if (null != linkmanId) {
			oldDepartment.setLinkman(this.getPersonByOfficerId(linkmanId));
		}
		oldDepartment.setUpdateDate(new Date());
		dao.modify(oldDepartment);
	}
	
	public void getValidateModifyOfDepartment(Department department, InputValidate inputValidate, ActionSupport thisAction){
		if(null == department.getName() || department.getName().trim().length()>40 || department.getName().trim().length()<0){
			thisAction.addFieldError("department.name", "院系名应为1~40个字符");
		}

		if(null !=department.getCode() && (department.getCode().trim().length()>40 || department.getCode().trim().length()<0)){
			thisAction.addFieldError("department.name","院系编号应为1~40个字符");
		}

		if(null == department.getUniversity().getId().trim() || "".equals(department.getUniversity().getId().trim())){
			thisAction.addFieldError("department.university.id","所属高校不能为空");
		}

		if(null != department.getHomepage() && department.getHomepage().trim().length()>60){
			thisAction.addFieldError("department.homepage", "主页最长为60个字符");
		}

		if(null !=department.getIntroduction() && department.getIntroduction().trim().length()>20000){
			thisAction.addFieldError("department.introduction", "院系简介最长为800个字符");
		}
	}
	
	public void getModifyOfInstitute(Map session, Institute institute, String directorId, String linkmanId, String entityId) {
		Institute oldInstitute = (Institute) dao.query(Institute.class, entityId);
		oldInstitute.setName(institute.getName().trim());
		oldInstitute.setEnglishName(institute.getEnglishName().trim());
		oldInstitute.setAbbr(institute.getAbbr().trim());
		oldInstitute.setCode(institute.getCode().trim());
		oldInstitute.setIntroduction(institute.getIntroduction());
		oldInstitute.setPhone(institute.getPhone());
		oldInstitute.setFax(institute.getFax());
		oldInstitute.setEmail(institute.getEmail());
		oldInstitute.setHomepage(institute.getHomepage());
		if(null != institute.getSubjection().getId()){
			Agency subjection = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
			oldInstitute.setSubjection(subjection);
		}
		if (null != directorId) {
			oldInstitute.setDirector(this.getPersonByOfficerId(directorId));
		}
		if (null != linkmanId) {
			oldInstitute.setLinkman(this.getPersonByOfficerId(linkmanId));
		}
		if (null != institute.getType().getId()) {
			SystemOption type = (SystemOption) dao.query(SystemOption.class, institute.getType().getId());
			oldInstitute.setType(type);
		}
		if (null != institute.getResearchActivityType() && null != institute.getResearchActivityType().getId()) {
			SystemOption researchActivityType = (SystemOption) dao.query(SystemOption.class, institute.getResearchActivityType().getId());
			oldInstitute.setResearchActivityType(researchActivityType);
		}
		oldInstitute.setApproveDate(institute.getApproveDate());
		oldInstitute.setApproveSession(institute.getApproveSession());
		oldInstitute.setForm(institute.getForm());
		oldInstitute.setDisciplineType(institute.getDisciplineType());
		oldInstitute.setResearchArea(institute.getResearchArea());
		oldInstitute.setChineseBookAmount(institute.getChineseBookAmount());
		oldInstitute.setForeignBookAmount(institute.getForeignBookAmount());
		oldInstitute.setChinesePaperAmount(institute.getChinesePaperAmount());
		oldInstitute.setForeignPaperAmount(institute.getForeignPaperAmount());
		oldInstitute.setIsIndependent(institute.getIsIndependent());
		oldInstitute.setOfficeArea(institute.getOfficeArea());
		oldInstitute.setDataroomArea(institute.getDataroomArea());
		oldInstitute.setUpdateDate(new Date());
		dao.modify(oldInstitute);
	}
	
	public void getValidateModifyOfInstitute(Institute institute, InputValidate inputValidate, ActionSupport thisAction){
		if(null == institute.getName() || institute.getName().trim().length()==0){
			thisAction.addFieldError("institute.name",UnitInfo.ERROR_NAME_NULL);
		}else if(institute.getName().trim().length()>40 || institute.getName().trim().length()<1){
			thisAction.addFieldError("institute.name",UnitInfo.ERROR_NAME_ILLEGAL);
		}

		if(null != institute.getEnglishName() && (institute.getEnglishName().trim().length()>200 || institute.getEnglishName().trim().length()<0)){
			thisAction.addFieldError("institute.englishName",UnitInfo.ERROR_ENGLISHNAME_ILLEGAL);
		}

		if(null !=institute.getCode() && (institute.getCode().trim().length()>40 || institute.getCode().trim().length()<0)){
			thisAction.addFieldError("institute.code",UnitInfo.ERROR_CODE_ILLEGAL);
		}

		if(null != institute.getAbbr()&& institute.getAbbr().trim().length()>40){
			thisAction.addFieldError("institute.abbr",UnitInfo.ERROR_ABBR_ILLEGAL);
		}

		if(null == institute.getType() || institute.getType().getId().trim().length()==0){
			thisAction.addFieldError("institute.type",UnitInfo.ERROR_INSTITUTE_TYPE_NULL);
		}else if(institute.getType().getId().trim().length()>40){
			thisAction.addFieldError("institute.type",UnitInfo.ERROR_INSTITUTE_TYPE_ILLEAGER);
		}

		if(null == institute.getSubjection().getId().trim() || "".equals(institute.getSubjection().getId().trim())){
			thisAction.addFieldError("institute.subjection.id",UnitInfo.ERROR_SUBJECTION_NULL);
		}

		if(null != institute.getDirector() && institute.getDirector().getId().trim().length()>40){
			thisAction.addFieldError("institute.director.id",UnitInfo.ERROR_DIRECTOR_ILLEGAL);
		}

		if( institute.getIsIndependent()<0 || institute.getIsIndependent()>1){
			thisAction.addFieldError("institute.isIndependent",UnitInfo.ERROR_ISINDEPENDENT_ILLEGAL);
		}

		if(null != institute.getApproveSession() && institute.getApproveSession().trim().length()>40){
			thisAction.addFieldError("institute.approveSession",UnitInfo.ERROR_APPROVESESSION_ILLEGAL);
		}

		//设立日期未作校验
		if(null != institute.getForm() && institute.getForm().trim().length()>40){
			thisAction.addFieldError("institute.form",UnitInfo.ERROR_FORM_ILLEGAL);
		}

		if(null != institute.getResearchActivityType().getId() && institute.getResearchActivityType().getId().trim().length()>40){
			thisAction.addFieldError("institute.researchActivityType",UnitInfo.ERROR_RESEARCHTYPE_ILLEGAL);
		}

		//所属学科门类,学术片未作校验

		//面积数量未作校验
		
		if(null != institute.getPhone() && !inputValidate.checkPhone(institute.getPhone().trim())){
			thisAction.addFieldError("institute.phone",UnitInfo.ERROR_PHONE_ILLEGAL);
		}

		if(null != institute.getEmail()){
			String[] mail = institute.getEmail().split(";");
			for (int i = 0; i < mail.length; i++) {
				String	email = mail[i];
				if(!inputValidate.checkEmail(email.trim())){
					thisAction.addFieldError("institute.email",UnitInfo.ERROR_EMAIL_ILLEGAL);
				}
			}
			
		}

		if(null != institute.getFax() && !inputValidate.checkFax(institute.getFax().trim())){
			thisAction.addFieldError("institute.fax",UnitInfo.ERROR_FAX_ILLEGAL);
		}

		if(null != institute.getHomepage() && institute.getHomepage().trim().length()>100){
			thisAction.addFieldError("institute.homepage",UnitInfo.ERROR_HOMEPAGE_ILLEGAL);
		}

		if(null != institute.getLinkman() && institute.getLinkman().getId().trim().length()>40){
			thisAction.addFieldError("institute.linkman.id",UnitInfo.ERROR_LINKMAN_ILLEGAL);
		}
	}
	
	/**
	 * 判断某账号是否能管理某类管理机构
	 * @param current 当前账号
	 * @param agencyType 管理机构类型1:部级，2:省级，3:校级
	 * @param agencyId 管理机构id,添加操作时为null
	 * @param subjectionId 上级机构id,主要针对校级，其它可置为null
	 * @return 
	 */
	public boolean checkAgencyLeadByAccount(Account current,int agencyType,String agencyId,String subjectionId,String currentBelongUnitId){
		AccountType type = current.getType();
		if (type.equals(AccountType.ADMINISTRATOR)) {
			
		} else if (type.equals(AccountType.MINISTRY)) {
			String MOEId = this.getMOEId();
			if(MOEId.equals(currentBelongUnitId)){//教育部管理员
			}else{ //中心管理员
				if(agencyType == 1 && !currentBelongUnitId.equals(agencyId)){
					return false;
				}
			}
		} else if (type.equals(AccountType.PROVINCE)) {
			if(agencyType == 1 || (agencyType ==2 && !currentBelongUnitId.equals(agencyId)) || ( (agencyType ==3 ||agencyType == 4) && !currentBelongUnitId.equals(subjectionId))){
				return false;
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {
			if(agencyType == 1 || agencyType == 2 || !currentBelongUnitId.equals(agencyId)){
				return false;
			}
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {
			if(agencyType == 1 || agencyType == 2 || !currentBelongUnitId.equals(agencyId)){
				return false;
			}
		} else {
			return false;
		}
//		switch(type.ordinal()){
//			case 1: { //系统管理员
//				break;
//			}
//			case 2: { //部级管理员
//				String MOEId = this.getMOEId();
//				if(MOEId.equals(currentBelongUnitId)){//教育部管理员
//					break;
//				}else{ //中心管理员
//					if(agencyType == 1 && !currentBelongUnitId.equals(agencyId)){
//						return false;
//					}
//					break;
//				}
//			}
//			case 3:{ //省级管理员
//				if(agencyType == 1 || (agencyType ==2 && !currentBelongUnitId.equals(agencyId)) || ( (agencyType ==3 ||agencyType == 4) && !currentBelongUnitId.equals(subjectionId))){
//					return false;
//				}else{
//					break;
//				}
//			}
//			case 4:{ //部属高校管理员
//				if(agencyType == 1 || agencyType == 2 || !currentBelongUnitId.equals(agencyId)){
//					return false;
//				}
//				break;
//			}case 5:{ //地方高校
//				if(agencyType == 1 || agencyType == 2 || !currentBelongUnitId.equals(agencyId)){
//					return false;
//				}
//				break;
//			}
//			default:{
//				return false;
//			}
//		}
		return true;
	}
	
	/**
	 * 由单位（各类管理机构和研究基地）Id获取该单位的主账号对象
	 * @type 1:agency; 2:department; 3:institute
	 */
	public Account getAccountByUnitId(String str, int type){
		StringBuffer hql = new StringBuffer();
		hql.append("from Account ac where 1=1");
		if (type == 1) {
			hql.append(" and ac.agency.id=:str");
		} else if (type == 2) {
			hql.append(" and ac.department.id=:str");
		} else if (type == 3) {
			hql.append(" and ac.institute.id=:str");
		}
		hql.append(" and ac.isPrincipal=1 order by ac.id");
		Map map = new HashMap();
		map.put("str", str);
		List list=dao.query(hql.toString(),map);
		if(list !=null && list.size()!=0){
		return (Account)list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 由学校获取院系列表
	 * @param univId
	 * @return
	 */
	public List getDeptsByUnivId(String univId){
		Map map = new HashMap();
		map.put("univId", univId);
		StringBuffer hql = new StringBuffer("select d.id, d.name, d.code, dir.id, dir.name, d.phone, d.fax, rownum " +
				"from Department d left join d.director dir left join d.university u where u.id = :univId ");
		List list = new ArrayList();
		List department;
		department = dao.query(hql.toString(),map);
		if(department.size()>0){
			for(int i=0;i<department.size();i++){
				list.add((Object[])department.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 由学校Id获取该校的院系数目
	 */
	public String getDeptNumByUnivId(String univId){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select count(*) from Department de where de.university.id =:univId");
		map.put("univId", univId);
		List list =  dao.query(hql.toString(),map);
		String num = list.get(0).toString();
		return num;
	}

	/**
	 * 管理机构高级检索
	 * str 检索范围 {null：全部；str:subjectionId}
	 * aName 机构名称
	 * aCode 机构代码
	 * directorName 负责人姓名
	 * provinceId 所在省
	 * aSname 部门名称
	 * sDirectorName 部门负责人
	 * pageName pager名称
	 */
	public Object[] advSearchAgency(String str,String aName,String aCode,String directorName, String type, String provinceId,String aSname,String sDirectorName,String pageName){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		if("ministryPage".equalsIgnoreCase(pageName)){
			hql.append("select ag.id,ag.code,ag.name,pe.name,sub.name,ag.phone,ag.fax,pe.id,sub.id from Agency ag left join ag.director pe left join ag.subjection sub left join ag.province pv where ag.type=1");
		}else if("provincePage".equalsIgnoreCase(pageName)){
			hql.append("select ag.id,ag.code,ag.name,pe.name,ag.sname,pr.name,ag.sphone,ag.sfax,pe.id,pr.id from Agency ag left join ag.director pe left join ag.sdirector pr left join ag.subjection so left join ag.province pv where ag.type=2");
		}else if("universityPage".equalsIgnoreCase(pageName)){ //高校列表
			hql.append("select ag.id,ag.code,ag.name,pe.name,ag.type,ag.sname,pr.name,ag.sphone,ag.sfax,pe.id,pr.id from Agency ag left join ag.director pe left join ag.sdirector pr left join ag.province pv where (ag.type=3 or ag.type=4) and 1=1 ");
		}else{
			return null;
		}
		if(null != str && !str.isEmpty()){
			hql.append(" and ag.subjection.id =:str and ag.type=4 ");
			map.put("str", str);
		}
		if(!aName.isEmpty()){ // 按名称搜索
			hql.append(" and LOWER(ag.name) like :agName");
			map.put("agName", '%'+aName+'%');
		}
		if(!aCode.isEmpty()){ //单位代码
			hql.append(" and LOWER(ag.code) like :agCode");
			map.put("agCode", '%'+aCode+'%');
		}
		if(!directorName.isEmpty()){ //负责人
			hql.append(" and LOWER(pe.name) like :director");
			map.put("director", '%'+directorName+'%');
		}
		if(!type.isEmpty()){ //高校类型
			int key=0;
			if("部属高校".indexOf(type)>=0 && "地方高校".indexOf(type)>=0){
				
			}else if("部属高校".indexOf(type)>=0){
				key = 3;
				hql.append(" and ag.type = :keyword");
				map.put("keyword", key);
			}else if("地方高校".indexOf(type)>=0){
				key = 4;
				hql.append(" and ag.type = :keyword");
				map.put("keyword", key);
			}else{
				
			}
		}
		if(!provinceId.isEmpty()&&!provinceId.equals("-1")){ //所在省
			hql.append(" and pv.id =:provinceId");
			map.put("provinceId",provinceId);
		}
		if(!aSname.isEmpty()){ //部门名称
			hql.append(" and ag.sname like :sName");
			map.put("sName", '%'+aSname+'%');
		}
		if(!sDirectorName.isEmpty()){ //部门负责人
			hql.append(" and LOWER(pr.name) like :sDirector");
			map.put("sDirector", '%'+sDirectorName+'%');
		}
		//保存map至session
		Map session = ActionContext.getContext().getSession();
		session.put("map",map);
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	/**
	 * 判断单位(各类管理机构或研究基地)名称是否已经存在
	 * @param objectName 对象名（如Agency)
	 * @param id 对象编号：添加时以null代替，编辑时即为该对象的编号
	 * @param name 单位名称
	 * @return true:不存在，false:存在
	 */
	public boolean checkUnitNameUnique(String objectName,String id,String name){
		StringBuffer str = new StringBuffer();
		Map map = new HashMap();
		str.append("select un.id from "+objectName+" un where un.name=:name");
		map.put("name", name);
		if(id !=null && id.trim().length()>0){
			str.append(" and un.id !=:id");
			map.put("id", id);
		}
		List list = dao.query(str.toString(),map);
		if(list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断研究基地名称是否已经存在
	 * @param objectName 对象名（如Institute)
	 * @param id 对象编号：添加时以null代替，编辑时即为该对象的编号
	 * @param name 单位名称
	 * @param subjectionId 上级单位id
	 * @return true:不存在，false:存在
	 */
	public boolean checkInsNameUnique(String objectName,String id,String name,String subjectionId){
		StringBuffer str = new StringBuffer();
		Map map = new HashMap();
		if(objectName.equals("Institute")){
			str.append("select un.id from "+objectName+" un where un.name=:name and un.subjection.id =:subjectionId");
		}else if(objectName.equals("Department")){
			str.append("select un.id from "+objectName+" un where un.name=:name and un.university.id =:subjectionId");
		}
		map.put("name", name);
		map.put("subjectionId", subjectionId);
		if(id !=null && id.trim().length()>0){
			str.append(" and un.id !=:id");
			map.put("id", id);
		}
		List list = dao.query(str.toString(),map);
		if(list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 由学科Id获取学科对象（系统选项表）
	 * @param id 学科Id
	 * @return
	 */
	public SystemOption getDisciplineById(String id){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from SystemOption so where so.isAvailable=1 and so.id=:id order by so.code asc ");
		map.put("id", id);
		List<SystemOption> list = dao.query(hql.toString(),map);
		return (SystemOption)list.get(0);
	}
	
	/**
	 * 由学科Id获取该学科的下级学科的对象list
	 * @param id 父学科Id
	 * @return
	 */
	public List<SystemOption> getDisciplinesById(String id){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from SystemOption so where so.isAvailable=1 and so.systemOption.id=:id order by so.code asc ");
		map.put("id", id);
		List<SystemOption> list = dao.query(hql.toString(),map);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 由学科(一级学科)Id获取该学科对应的学科树
	 * @param id 一级学科Id
	 */
	public Document createDisciplineXML(String id){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("tree");
		root.addAttribute("id", "0");
		Element item0 = root.addElement("item");
		if(id !=null && id.trim().length()>0){
			SystemOption dis = this.getDisciplineById(id);
			item0.addAttribute("text",dis.getCode() + dis.getName());
			item0.addAttribute("id",dis.getId());
		}else{ //未指定根学科，便初始化为所有学科
			item0.addAttribute("text", "所有学科");
			item0.addAttribute("id", "4028d88a296cfa9501296cfb59dd0dd0"); //09年代码表根节点编号
		}
		item0.addAttribute("im0", "folder_closed.gif");
		item0.addAttribute("im1", "folder_open.gif");
		item0.addAttribute("im2", "folder_closed.gif");
		item0.addAttribute("open", "1"); //展开该节点
		item0.addAttribute("check", "0");
		newItem(item0); //根据根节点递归各子节点
		return document;
	}
	/**
	 * 产生新节点
	 * @param item
	 */
	public void newItem(Element item){ //递归方法
		String id = item.attributeValue("id");
		List<SystemOption> dises = this.getDisciplinesById(id); //获取子学科
		if( dises != null && dises.size()>0){
			for (int i=0;i<dises.size();i++){
				SystemOption dis = dises.get(i);
				Element item2 = item.addElement("item");
				item2.addAttribute("id",dis.getId());
				item2.addAttribute("text",dis.getCode() + dis.getName());
				item2.addAttribute("im0", "folder_closed.gif");
				item2.addAttribute("im1", "folder_open.gif");
				item2.addAttribute("im2", "folder_closed.gif");
				newItem(item2);
			}
		}
	}

	// -------------------------------------- 院系部分代码 -----------------------------------------------------

	/**
	 * 判断某院系是否在指定账号的管理范围
	 * @param department
	 * @param current 当前用户账号
	 * @return
	 */
	public boolean checkDepartmentLeadByAccount(Department department,Account current,String currentBelongUnitId){
		AccountType type = current.getType();
		if (type.equals(AccountType.ADMINISTRATOR)) {
			
		} else if (type.equals(AccountType.MINISTRY)) {
			
		} else if (type.equals(AccountType.PROVINCE)) {
			//院系所在高校不在该省
			if(!checkUniversityLeadByProvince(department.getUniversity().getId(), currentBelongUnitId)){
				return false;
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {
			//院系不在该高校
			if(!department.getUniversity().getId().equals(currentBelongUnitId)){
				return false;
			}
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {
			//院系不在该高校
			if(!department.getUniversity().getId().equals(currentBelongUnitId)){
				return false;
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {
			if(!department.getId().equals(currentBelongUnitId)){
				return false;
			}
		} else {
			return false;
		}
//		switch(type.ordinal()){
//			case 1:// 系统管理员
//				break;
//			case 2:// 部级账号
//				break;
//			case 3:{// 省级账号
//				//院系所在高校不在该省
//				if(!checkUniversityLeadByProvince(department.getUniversity().getId(), currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			case 4:{ //部属高校账号
//				//院系不在该高校
//				if(!department.getUniversity().getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			case 5:{ //地方高校
//				//院系不在该高校
//				if(!department.getUniversity().getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			case 6:{ //院系账号
//				//该账号不属于该院系
//				if(!department.getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			default:
//				return false;
//		}
		return true;
	}
	
	/**
	 * 根据编号list删除机构
	 * @param ids
	 * @return 0:删除成功	1：因为人员不能删除 2 机构 3项目 4成果 5 奖励
	 * @author 余潜玉 周中坚（删除机构表中的accountId字段）
	 */
	public int deleteAgencyByIds(int type,List<String> entityIds){
		if(null !=entityIds && entityIds.size()>0){
			for(int i=0;i<entityIds.size();i++){
				int flag = this.canDelete(type,entityIds.get(i));
				if(flag != 0){//不能删除
					return flag;
				}
			}
			for(int i=0;i<entityIds.size();i++){
				Agency agency = (Agency)dao.query(Agency.class,entityIds.get(i));
				List<String> acids = this.getAccountByUnit(agency.getId(), 1);
				if(!acids.isEmpty()){//删除管理机构帐号
					this.deleteAccount(acids);
				}
				deleteAddress(agency);
				deleteBank(agency);
				dao.delete(Agency.class,entityIds.get(i));
			}
		}
		return 0;
	}
	/**
	 * 批量删除研究基地
	 * @return true:删除成功		false：不能删除
	 * @author 余潜玉
	 */
	public int deleteInstituteByIds(List<String> ids){
		if(null != ids && ids.size()>0){
			for(int i=0;i<ids.size();i++){
				int flag = this.canDelete(4, ids.get(i));
				if(flag != 0){
					return flag;
				}
			}
			for(int i=0;i<ids.size();i++){
				Institute institute = (Institute)dao.query(Institute.class,ids.get(i));
				List<String> acids = this.getAccountByUnit(institute.getId(), 2);
				if(!acids.isEmpty()){//删除研究基地帐号
					this.deleteAccount(acids);
				}
				deleteAddress(institute);
				dao.delete(Institute.class,ids.get(i));
			}
		}
		return 0;
	}

	/**
	 * 根据编号删除机构
	 * @param ids
	 * @return true:删除成功		false：不能删除
	 * @author 余潜玉
	 */
	public int deleteDeptByIds(List<String> ids){
		if(null !=ids && ids.size()>0){
			for(int i=0;i<ids.size();i++){
				int flag = this.canDelete(5, ids.get(i));
				if(flag != 0){
					return flag;
				}
			}
			for(int i=0;i<ids.size();i++){
				Department department = (Department)dao.query(Department.class,ids.get(i));
				List<String> acids = this.getAccountByUnit(department.getId(), 3);
				if(!acids.isEmpty()){//删除高校院系帐号
					this.deleteAccount(acids);
				}
				deleteAddress(department);
				dao.delete(Department.class,ids.get(i));
			}
		}
		return 0;
	}
	/**
	 * 根据机构id和机构类型获得账号id
	 * @param entityId 机构id（agencyId,instituteId,departmentId）
	 * @param unitType 1:agency;2:institute;3:department.
	 * @return List<String> acids
	 * @author 周中坚
	 */
	private List<String> getAccountByUnit(String entityId, int unitType) {
		List<String> acids = null;
		Map map = new HashMap();
		map.put("entityId", entityId);
		switch (unitType) {
			case 1:{//agency部省校级机构
				acids = dao.query("select ac.id from Account ac left join ac.agency ag where ag.id =:entityId and ac.type != 'DEPARTMENT' and ac.type != 'INSTITUTE' and ac.type != 'EXPERT' and ac.type != 'TEACHER' and ac.type != 'STUDENT' and ac.isPrincipal = 1", map);
				}
				break;
			case 2:{//研究机构
				acids = dao.query("select ac.id from Account ac where ac.institute.id =:entityId and ac.type = 'INSTITUTE' and ac.isPrincipal = 1", map);
				}
				break;
			case 3:{//高校院系
				acids = dao.query("select ac.id from Account ac where ac.department.id =:entityId and ac.type = 'DEPARTMENT' and ac.isPrincipal = 1", map);
				}
				break;
			default:
				break;
			}
		return acids;
	}
	
	/**
	 * 判断单位（各类管理机构或研究基地）中是否包含子元素
	 * @param type 1:center;2:province;3:university;4:institute;5:department
	 * @param unitid
	 * @return true:包含;false:不包含
	 * @author 余潜玉
	 */
	public int canDelete(int type,String unitid){
		Map map = new HashMap();
		map.put("unitid",unitid);
		if(type==1 || type==2 || type==3){ 
			//判断是否存在子单位
			List ages = dao.query("select age.id from Agency age where age.subjection.id=:unitid",map);
			List inss = dao.query("select ins.id from Institute ins where ins.subjection.id=:unitid",map); 
			List deps = dao.query("select dep.id from Department dep where dep.university.id=:unitid",map);
			if(ages.size() + inss.size() + deps.size() >0){
				return 2;
			}else{
				//判断机构下是否存在相关联的人员
				List ofis = dao.query("select ofi.id from Officer ofi where ofi.agency.id=:unitid",map);
				List teas = dao.query("select tea.id from Teacher tea where tea.university.id=:unitid",map); 
				List stus = dao.query("select stu.id from Student stu where stu.university.id=:unitid",map);
				if(ofis.size() + teas.size() + stus.size() > 0){
					return 1;
				}else{
					//判断是否存在相关联的项目
					List paps = dao.query("select pap.id from ProjectApplication pap where pap.university.id=:unitid or pap.universityAuditorAgency.id=:unitid or pap.provinceAuditorAgency.id=:unitid or pap.ministryAuditorAgency.id=:unitid or pap.reviewerAgency.id=:unitid or pap.finalAuditorAgency.id=:unitid",map);
					List pmes = dao.query("select pme.id from ProjectMember pme where pme.university.id=:unitid",map);
					List pvas = dao.query("select pva.id from ProjectVariation pva where pva.oldAgency.id=:unitid or pva.newAgency.id=:unitid or pva.universityAuditorAgency.id=:unitid or pva.provinceAuditorAgency.id=:unitid or pva.finalAuditorAgency.id=:unitid",map);
					List pers = dao.query("select per.id from ProjectEndinspectionReview per where per.university.id=:unitid",map);
					List pmds = dao.query("select pmd.id from ProjectMidinspection pmd where pmd.universityAuditorAgency.id=:unitid or pmd.provinceAuditorAgency.id=:unitid or pmd.finalAuditorAgency.id=:unitid", map);
					List peds = dao.query("select ped.id from ProjectEndinspection ped where ped.universityAuditorAgency.id=:unitid or ped.provinceAuditorAgency.id=:unitid or ped.ministryAuditorAgency.id=:unitid or ped.reviewerAgency.id=:unitid or ped.finalAuditorAgency.id=:unitid", map);
					if(paps.size() + pmes.size() + pvas.size() + pers.size() + pmds.size() + peds.size() > 0){
						return 3;
					}else{
//						//判断是否存在相关联的成果
//						List papers = dao.query("select pap.id from Paper pap where pap.university.id=:unitid or pap.ministryAuditorAgency.id=:unitid",map);
//						List boos = dao.query("select boo.id from Book boo where boo.university.id=:unitid or boo.ministryAuditorAgency.id=:unitid",map);
//						List cons = dao.query("select con.id from Consultation con where con.university.id=:unitid or con.ministryAuditorAgency.id=:unitid",map);
//						List eles = dao.query("select ele.id from Electronic ele where ele.university.id=:unitid or ele.ministryAuditorAgency.id=:unitid",map);
//						if(papers.size() + boos.size() + cons.size() + eles.size() > 0){//存在相关连成果信息
//							return 4;
//						}else{
							//判断是否存在相关联的奖励
							List aaps = dao.query("select aap.id from AwardApplication aap where aap.university.id=:unitid or aap.universityAuditorAgency.id=:unitid or aap.provinceAuditorAgency.id=:unitid or aap.ministryAuditorAgency.id=:unitid or aap.reviewerAgency.id=:unitid or aap.reviewAuditorAgency.id=:unitid or aap.finalAuditorAgency.id=:unitid",map);
							List ares = dao.query("select are.id from AwardReview are where are.university.id=:unitid",map);
							if(aaps.size() + ares.size() >0){
								return 5;
							}
//						}
					}
				}
			}
		}else if(type == 4){
			//判断机构下是否存在相关联的人员
			List ofis = dao.query("select ofi.id from Officer ofi where ofi.institute.id=:unitid",map);
			List teas = dao.query("select tea.id from Teacher tea where tea.institute.id=:unitid",map); 
			List stus = dao.query("select stu.id from Student stu where stu.institute.id=:unitid",map);
			if(ofis.size() + teas.size() + stus.size() > 0){
				return 1;
			}else{
				//判断是否存在相关联的项目
				List paps = dao.query("select pap.id from ProjectApplication pap where pap.university.id=:unitid or pap.universityAuditorAgency.id=:unitid or pap.provinceAuditorAgency.id=:unitid or pap.ministryAuditorAgency.id=:unitid or pap.reviewerAgency.id=:unitid or pap.finalAuditorAgency.id=:unitid",map);
				List pmes = dao.query("select pme.id from ProjectMember pme where pme.university.id=:unitid",map);
				List pvas = dao.query("select pva.id from ProjectVariation pva where pva.oldAgency.id=:unitid or pva.newAgency.id=:unitid or pva.universityAuditorAgency.id=:unitid or pva.provinceAuditorAgency.id=:unitid or pva.finalAuditorAgency.id=:unitid",map);
				List pers = dao.query("select per.id from ProjectEndinspectionReview per where per.university.id=:unitid",map);
				List pmds = dao.query("select pmd.id from ProjectMidinspection pmd where pmd.universityAuditorAgency.id=:unitid or pmd.provinceAuditorAgency.id=:unitid or pmd.finalAuditorAgency.id=:unitid", map);
				List peds = dao.query("select ped.id from ProjectEndinspection ped where ped.universityAuditorAgency.id=:unitid or ped.provinceAuditorAgency.id=:unitid or ped.ministryAuditorAgency.id=:unitid or ped.reviewerAgency.id=:unitid or ped.finalAuditorAgency.id=:unitid", map);
				if(paps.size() + pmes.size() + pvas.size() + pers.size() + peds.size() + pmds.size() > 0){
					return 3;
				}else{
					//判断是否存在相关联的成果
//					List papers = dao.query("select pap.id from Paper pap where pap.institute.id=:unitid or pap.deptInstAuditorInst.id=:unitid",map);
//					List boos = dao.query("select boo.id from Book boo where boo.institute.id=:unitid  or boo.deptInstAuditorInst.id=:unitid",map);
//					List cons = dao.query("select con.id from Consultation con where con.institute.id=:unitid or con.deptInstAuditorInst.id=:unitid",map);
//					List eles = dao.query("select ele.id from Electronic ele where ele.institute.id=:unitid or ele.deptInstAuditorInst.id=:unitid",map);
//					if(papers.size() + boos.size() + cons.size() + eles.size() > 0){//存在相关连成果信息
//						return 4;
//					}else{
						//判断是否存在相关联的奖励
						List aaps = dao.query("select aap.id from AwardApplication aap where aap.institute.id=:unitid or aap.deptInstAuditorInst.id=:unitid or aap.finalAuditorInst.id=:unitid",map);
						List ares = dao.query("select are.id from AwardReview are where are.institute.id=:unitid",map);
						if(aaps.size() + ares.size() >0){
							return 5;
						}else{
							//判断是否存在其他关联
							List coms = dao.query("select com.id from Committee com where com.institute.id=:unitid",map);
							List cacs = dao.query("select cac.id from CommitteeActivity cac where cac.institute.id=:unitid",map);
							List subs = dao.query("select sub.id from SubInstitute sub where sub.institute.id=:unitid",map);
							if(coms.size() + cacs.size() + subs.size() > 0){
								return 6;
							}
						}
//					}
				}
			}
		}else if(type == 5){
			//判断机构下是否存在相关联的人员
			List ofis = dao.query("select ofi.id from Officer ofi where ofi.department.id=:unitid",map);
			List teas = dao.query("select tea.id from Teacher tea where tea.department.id=:unitid",map); 
			List stus = dao.query("select stu.id from Student stu where stu.department.id=:unitid",map);
			if(ofis.size() + teas.size() + stus.size() > 0){
				return 1;
			}else{
				//判断是否存在相关联的项目
				List gaps = dao.query("select gap.id from GeneralApplication gap where gap.department.id=:unitid or gap.deptInstAuditorDept.id=:unitid or gap.finalAuditorDept.id=:unitid",map);
				List gmes = dao.query("select gme.id from GeneralMember gme where gme.department.id=:unitid",map);
				List gvas = dao.query("select gva.id from GeneralVariation gva where gva.oldInstitute.id=:unitid or gva.newInstitute.id=:unitid or gva.deptInstAuditorDept.id=:unitid or gva.finalAuditorDept.id=:unitid",map);
				List gers = dao.query("select ger.id from GeneralEndinspectionReview ger where ger.department.id=:unitid",map);
				List gmds = dao.query("select gmd.id from GeneralMidinspection gmd where gmd.deptInstAuditorDept.id=:unitid or gmd.finalAuditorDept.id=:unitid",map);
				List geds = dao.query("select ged.id from GeneralEndinspection ged where ged.deptInstAuditorDept.id=:unitid or ged.finalAuditorDept.id=:unitid",map);
				if(gaps.size() + gmes.size() + gvas.size() + gers.size() + gmds.size() + geds.size() > 0){
					return 3;
				}else{
					//判断是否存在相关联的成果
//					List paps = dao.query("select pap.id from Paper pap where pap.department.id=:unitid or pap.deptInstAuditorDept.id=:unitid",map);
//					List boos = dao.query("select boo.id from Book boo where boo.department.id=:unitid or boo.deptInstAuditorDept.id=:unitid",map);
//					List cons = dao.query("select con.id from Consultation con where con.department.id=:unitid or con.deptInstAuditorDept.id=:unitid",map);
//					List eles = dao.query("select ele.id from Electronic ele where ele.department.id=:unitid or ele.deptInstAuditorDept.id=:unitid",map);
//					if(paps.size() + boos.size() + cons.size() + eles.size() > 0){//存在相关连成果信息
//						return 4;
//					}else{
						//判断是否存在相关联的奖励
						List aaps = dao.query("select aap.id from AwardApplication aap where aap.department.id=:unitid or aap.deptInstAuditorDept.id=:unitid or aap.finalAuditorDept.id=:unitid",map);
						List ares = dao.query("select are.id from AwardReview are where are.department.id=:unitid",map);
						if(aaps.size() + ares.size() >0){
							return 5;
						}
//					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * 将income院系的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetDept 合并后保留的院系(PO)
	 * @param incomeDept 合并后删除的院系(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeDepartment(Department targetDept, List<Department> incomeDept) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		DepartmentMerger departmentMerger = (DepartmentMerger) SpringBean.getBean("departmentMerger", ApplicationContainer.sc);
		departmentMerger.mergeDepartment(targetDept, incomeDept);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeDepartment(Department targetDept, Department incomeDept) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Department> incomeDeptList = new ArrayList<Department>();
		incomeDeptList.add(incomeDept);
		mergeDepartment(targetDept, incomeDeptList);
	}

	/**
	 * 根据院系id列表合并院系
	 * @param ids
	 */
	@Deprecated
	public void mergeDepts(List<String> ids){
		if(null != ids && ids.size()>1){
			String deptId = ids.get(0); //以第一个院系有目标院系
			StringBuffer hql = new StringBuffer();
			hql.append("select de.name from Department de where de.id = '");
			hql.append(deptId);
			hql.append("'");
			List list = dao.query(hql.toString());
			String deptName="";
			if(list != null && list.size()>0){
				deptName = list.get(0).toString();
			}
			for(int i = 1;i<ids.size();i++){
				//处理账号表
				this.updateDeptHql1("Account", "belongId", ids.get(i), "belongId", deptId);
				//处理管理人员表
				this.updateDeptHql1("Officer", "department.id", ids.get(i), "department.id", deptId);
				//处理老师表
				this.updateDeptHql1("Teacher", "department.id", ids.get(i), "department.id", deptId);
				//处理学生表
				this.updateDeptHql1("Student", "department.id", ids.get(i), "department.id", deptId);
				//工作经历和教育背景暂不考虑
				
				//处理一般项目申请表
				this.updateDeptHql2("GeneralApplication", "department.id", ids.get(i), "department.id", deptId, "divisionName", deptName);
				//处理项目成员表
				this.updateDeptHql2("GeneralMember", "department.id", ids.get(i), "department.id", deptId, "divisionName", deptName);
				//处理项目变更表
				this.updateDeptHql2("GeneralVariation", "oldDeptId", ids.get(i), "oldDeptId", deptId, "oldDeptName", deptName);
				this.updateDeptHql2("GeneralVariation", "newDeptId", ids.get(i), "newDeptId", deptId, "newDeptName", deptName);
				//处理论文表
				this.updateDeptHql2("Paper", "department.id", ids.get(i),"department.id", deptId, "divisionName", deptName);
				//处理著作表
				this.updateDeptHql2("Book", "department.id", ids.get(i), "department.id", deptId, "divisionName", deptName);
				//处理研究咨询报告表
				this.updateDeptHql2("Consultation", "department.id", ids.get(i), "department.id", deptId, "divisionName", deptName);
				//处理奖励申请
				this.updateDeptHql2("AwardApplication", "department.id", ids.get(i), "department.id", deptId, "divisionName", deptName);
				if(this.canDelete(5,ids.get(i)) == 0){
					dao.delete(Department.class,ids.get(i));
				}
			}
		}
	}
	
	/**
	 * 将income基地的信息合并至target，将income的子表指向target(，并删除income)
	 * @param targetInst 合并后保留的基地(PO)
	 * @param incomeInst合并后删除的基地(PO)
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeInstitute(Institute targetInst, List<Institute> incomeInst) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		InstituteMerger instituteMerger = (InstituteMerger) SpringBean.getBean("instituteMerger", ApplicationContainer.sc);
		instituteMerger.mergeInstitute(targetInst, incomeInst);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void mergeInstitute(Institute targetInst, Institute incomeInst) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Institute> incomeInstList = new ArrayList<Institute>();
		incomeInstList.add(incomeInst);
		mergeInstitute(targetInst, incomeInstList);
	}

	/**
	 * 根据类名属性名更新表
	 * @param className 类名 
	 * @param property1 条件属性
	 * @param oldStr 条件值
	 * @param property2 目标属性
	 * @param newStr 目标值
	 */
	public void updateDeptHql1(String className, String property1, String oldStr, String property2, String newStr){
		StringBuffer hql = new StringBuffer();
		Map parMap = new HashMap();
		parMap.put("oldStr", oldStr);
		parMap.put("newStr", newStr);
		hql.append("update ");
		hql.append(className);
		hql.append(" cn set cn.");
		hql.append(property2);
		hql.append(" =:newStr");
		hql.append(" where cn.");
		hql.append(property1);
		hql.append(" =:oldStr");
		dao.execute(hql.toString(), parMap);
	}
	
	/**
	 * 根据类名属性名更新表
	 * @param className 类名 
	 * @param property1 条件属性
	 * @param oldStr 条件属性值
	 * @param target1 待改属性1
	 * @param newStr1 待改属性1值
	 * @param target2 待改属性2
	 * @param newStr2 目标属性2值
	 */
	public void updateDeptHql2(String className, String property1, String oldStr, String target1, String newStr1, String target2, String newStr2){
		StringBuffer hql= new StringBuffer();
		Map parMap = new HashMap();
		parMap.put("oldStr", oldStr);
		parMap.put("newStr1", newStr1);
		parMap.put("newStr2", newStr2);
		hql.append("update ");
		hql.append(className);
		hql.append(" cn set cn.");
		hql.append(target1);
		hql.append(" =:newStr1,");
		hql.append("cn.");
		hql.append(target2);
		hql.append(" =:newStr2");
		hql.append(" where cn.");
		hql.append(property1);
		hql.append(" =:oldStr");
		dao.execute(hql.toString(), parMap);
	}
	
	/**
	 * 判断多个院系是否同属于一个学校
	 * @param ids 院系id组成的list
	 * @return
	 */
	public boolean checkDeptsInSameUniv(List<String> ids){
		if(ids == null || ids.size()<2){
			return false;
		}else{
			String deptId = ids.get(0);//基准院系
			String univId ="";
			StringBuffer hql = new StringBuffer();
			hql.append("select de.university.id from Department de where de.id =:deId");
			Map parMap = new HashMap();
			parMap.put("deId", deptId);
			List<String> list = dao.query(hql.toString(),parMap);
			if(list != null && list.size()>0){
				if(list.get(0) != null && list.get(0).length()>0){
					univId = list.get(0);
				}else{
					return false;
				}
			}else{
				return false;
			}
			for(int i=1;i<ids.size();i++){
				parMap.put("univId", univId);
				if(ids.get(i) != null && ids.get(i).length()>0){
					StringBuffer hql1 = new StringBuffer();
					hql1.append("select de.id from Department de where de.id=:deId and de.university.id=:univId");
					parMap.put("deId", ids.get(i));
					List list1 = dao.query(hql1.toString(),parMap);
					if(list1 !=null && list1.size()>0){
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
			return true;
		}
	}
	

	/**
	 * 判断指定学校是否由指定省管辖，供UnitService中checkDepartmentLeadByAccount调用
	 * @param schoolid 学校编号
	 * @param provinceid 省编号
	 * @return true:指定学校在指定省;false:指定学校不在指定省
	 */
	public boolean checkUniversityLeadByProvince(String univid,String provinceid){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select ag.id from Agency ag where ag.id= :univid and ag.subjection.id=:provinceid and ag.type=4");
		map.put("univid", univid);
		map.put("provinceid", provinceid);
		List list=dao.query(hql.toString(),map);
		if (list.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 判断某个研究机构是否在指定账号的管辖范围
	 * @param institute 研究机构对象
	 * @param account 指定账号对象
	 * @return false:不在,true:在
	 */
	public boolean checkInstituteLeadByAccount(Institute institute,Account account,String currentBelongUnitId){
		AccountType type = account.getType();
		if (type.equals(AccountType.ADMINISTRATOR)) {
			
		} else if (type.equals(AccountType.MINISTRY)) {
			
		} else if (type.equals(AccountType.PROVINCE)) {
			//研究基地所在高校不在该省
			if(!checkUniversityInProvince(institute.getSubjection().getId(), currentBelongUnitId)){
				return false;
			}
			if(getAgencyTypeById(institute.getSubjection().getId()) == 3 ){//研究机构上级管理部门为部属高校
				if(!getCodeById(institute.getType().getId().toString()).equals("2") && !getCodeById(institute.getType().getId().toString()).equals("3")){//研究机构不是省属或省部共建
					return false;
				}
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {
			//研究基地不在该高校
			if(!institute.getSubjection().getId().equals(currentBelongUnitId)){
				return false;
			}
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {
			//研究基地不在该高校
			if(!institute.getSubjection().getId().equals(currentBelongUnitId)){
				return false;
			}
		} else if (type.equals(AccountType.INSTITUTE)) {
			//该账号不属于该基地
			if(!institute.getId().equals(currentBelongUnitId)){
				return false;
			}
		} else {
			return false;
		}

//		switch(type.ordinal()){
//			case 1:// 系统管理员
//				break;
//			case 2:// 部级账号
//				break;
//			case 3:{// 省级账号（省属研究机构及校（省属高校）属研究机构）
//				//研究基地所在高校不在该省
//				if(!checkUniversityInProvince(institute.getSubjection().getId(), currentBelongUnitId)){
//					return false;
//				}
//				if(getAgencyTypeById(institute.getSubjection().getId()) == 3 ){//研究机构上级管理部门为部属高校
//					if(!getCodeById(institute.getType().getId().toString()).equals("2") && !getCodeById(institute.getType().getId().toString()).equals("3")){//研究机构不是省属或省部共建
//						return false;
//					}
//				}
//				break;
//			}
//			case 4:{ //部属高校账号
//				//研究基地不在该高校
//				if(!institute.getSubjection().getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			case 5:{ //地方高校
//				//研究基地不在该高校
//				if(!institute.getSubjection().getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			case 7:{ //研究机构账号
//				//该账号不属于该基地
//				if(!institute.getId().equals(currentBelongUnitId)){
//					return false;
//				}
//				break;
//			}
//			default:
//				return false;
//		}
		return true;
	}

	/**
	 * 由管理机构编号得出其类型，供UnitService中checkInstituteLeadByAccount调用
	 * @param entityId
	 * @return
	 */
	public int getAgencyTypeById(String entityId){
		Map map = new HashMap();
		String hql = "select ag.type from Agency ag where ag.id=:id";
		map.put("id", entityId);
		List list = dao.query(hql,map);
		return Integer.parseInt(list.get(0).toString());
	}
	
	/**
	 * 判断指定高校是否在指定省，供UnitService中checkInstituteLeadByAccount调用
	 * @param univid 高校Id
	 * @param provinceid 省Id
	 */
	public boolean checkUniversityInProvince(String univid,String provinceid){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select ag.id from Agency ag where ag.id=:univid and ag.subjection.id=:provinceid");
		map.put("univid", univid);
		map.put("provinceid", provinceid);
		List list=dao.query(hql.toString(),map);
		if (list.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 由id字符串（多个id以‘;’隔开）获取选项表中的名称字符串
	 * @param ids
	 * @return
	 */
	public String getNamesByIds(String ids){
		if(ids !=null && ids.trim().length()>0){
			String[] id = ids.split(";");
			StringBuffer hql =  new StringBuffer();
			Map map = new HashMap();
			if(id.length>0){
				hql.append("select so.name from SystemOption so where");
				hql.append(" so.id in (:id)");
				map.put("id",id);
			}
			String hql2=hql.toString();
			List list = dao.query(hql2,map);
			StringBuffer str = new StringBuffer();
			if(list!=null && list.size()>0){
				for (int j=0;j<list.size();j++){
					str.append((String)list.get(j)+";");
				}
			}
			String str2 = str.toString();
			str2 = str2.substring(0,str.length()-1);
			return str2;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据"id+';'"组成的字符串获取对应的学科代码
	 * @param ids
	 * @return
	 */
	public String getDispCodesByIds(String ids){
		String[] id = ids.split(";");
		if(id.length>0){
			StringBuffer codes = new StringBuffer();
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select so.code,so.name from SystemOption so where 0=1 or");
			hql.append(" so.id in (:id)");
			map.put("id",id);
			List list = dao.query(hql.toString(),map);
			if(list != null && list.size()>0){
				for(int j=0;j<list.size();j++){
					codes.append((String)(((Object [])list.get(j))[0]));
					codes.append("/");
					codes.append((String)(((Object [])list.get(j))[1]));
					codes.append("; ");
				}
				String code =  codes.toString().substring(0,codes.length()-2);
				return code;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 由officerid获取对应人员对象
	 * @param offid
	 * @return
	 */
	public Person getPersonByOfficerId(String offid){
		String hql = "select o.person from Officer o where o.id = ?";
		Person person = (Person) dao.queryUnique(hql, offid);
		return person;
	}

	/**
	 * 由personId获取对应officerId
	 * @param id
	 * @return
	 */
	public String getOfficerIdByPersonId(String id){
		Map paraMap = new HashMap();
		String hql = "select ofi.id from Officer ofi where ofi.person.id=:id";
		paraMap.put("id",id);
		List list = dao.query(hql,paraMap);
		if(list !=null && list.size()>0){
			return (String)list.get(0);
		}else{
			return null;
		}
	}

	//-----------------------------------------dwr 系统选项表 ------------------------------------------
	/**
	 * 在系统选项表中根据父节点id获取子节点的（id,name)组成的list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSystemOptionListById(String id){
		Map<String,String> systemOption = Collections.synchronizedMap(new LinkedHashMap<String,String>());
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select sys.id,sys.name from SystemOption sys where sys.systemOption.id=:id order by sys.name ");
		map.put("id", id);
		List list=dao.query(hql.toString(),map);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				systemOption.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return systemOption;
	}

	/**
	 * 在系统选项表中根据父节点id获取子节点的（id,name)组成的list
	 * @param id
	 * @return
	 */
	public Map<String,String> getProvinceList(){
		Map<String,String> systemOption = Collections.synchronizedMap(new LinkedHashMap<String,String>());
		StringBuffer hql2 = new StringBuffer();
		hql2.append("select sys.id,sys.name from SystemOption sys,SystemOption so where sys.systemOption.id=so.id and so.standard='GBT2260-2007' and so.code is null order by sys.name ");
		List list=dao.query(hql2.toString());
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				systemOption.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return systemOption;
	}

	/**
	 * 获取研究基地类型列表；id,name均为类型名称（数据库中存的是名称）
	 * @return
	 */
	public Map<String,String> getInstituteType(){
		Map<String, String> typeList = new HashMap<String, String>();
		String hql="select sys.id, sys.name from SystemOption sys where sys.systemOption.standard='researchAgencyType' and sys.systemOption.code is null and sys.isAvailable=1 order by sys.name ";
		List list = dao.query(hql.toString());
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				typeList.put(((Object[])list.get(i))[0].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return typeList;
	}
	
	/**
	 * 选择研究机构类型时由系统选项表id获取对应的code，供UnitService中checkInstituteLeadByAccount调用
	 * @param id 机构类别id
	 * @return 机构类型编码
	 */
	public String getCodeById(String id){
		Map map = new HashMap();
		String hql = "select sys.code from SystemOption sys where sys.id=:id and sys.isAvailable=1";
		map.put("id",id);
		List list = dao.query(hql,map);
		return list.get(0).toString();
	}

	/**
	 * 获取一级学科列表
	 * @return
	 */
	public Map<String,String> getDisciplineOne(){
		Map<String,String> discpList = new TreeMap<String,String>();
		String hql = "select sys.id, sys.name, sys.code from SystemOption sys where sys.systemOption.name like '%2009%代码表%' order by sys.code asc "; //09年代码表
		List list = dao.query(hql);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				String key = ((Object[])list.get(i))[0].toString();
				String value = ((Object[])list.get(i))[1].toString();
				String code = ((Object[])list.get(i))[2].toString();
				discpList.put(key, code + value);
			}
		}
		return discpList;
	}
	
	/**
	 * 由以‘;’相隔的学科id组成的字符串获取对应的学科代码
	 */
	public String getDisciplineCodesByIds(String ids){
		String[] id = ids.split(";");
		if(id.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select sys.code from SystemOption sys where ");
			hql.append("sys.id in (:id)");
			map.put("id", id);
			String hql1=hql.toString();
			List list = dao.query(hql1,map);
		if(list !=null && list.size()>0){
			StringBuffer name = new StringBuffer();
			for(int j=0;j<list.size();j++){
				name.append(list.get(j).toString());
				name.append(";");
			}
			String names = name.toString();
			names = names.substring(0,names.length()-1);
			return names;
		}else{
			return null;
		}
	}else{
		return null;
	}
	}

	/**
	 * 根据学科编号序列获取”编号%名称%代码；“组成的序列，”%“，”；“为分隔符
	 * @param ids
	 * @return
	 */
	public String getDispIdCodeNameById(String ids){
		String[] id = ids.split(";");
		if(id !=null && id.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select sys.id,sys.code,sys.name from SystemOption sys where ");
			hql.append(" sys.id in(:id)");
			map.put("id", id);
			String hql1= hql.toString();
			hql1 += "order by sys.code";
			List list = dao.query(hql1,map);
			if(list != null && list.size()>0){
				StringBuffer str = new StringBuffer();
				for(int j=0; j<list.size(); j++){
					str.append(((Object[])list.get(j))[0].toString()+"%"+((Object[])list.get(j))[1].toString()+"%"+((Object[])list.get(j))[2].toString()+";");
				}
				String str1 = str.toString();
				str1 = str1.substring(0,str.length()-1);
				return str1;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 根据学科代码/名称序列获取”编号%名称%代码；“组成的序列，”%“，”；“分隔符
	 * @param ids
	 * @return
	 */
	public String getDispIdCodeNameByCodeName(String codeName){
		String[] code = codeName.replace("; ", "/").split("/");
		if(code !=null && code.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select sys.id,sys.code,sys.name from SystemOption sys where sys.standard like '%2009%' and ");
			hql.append(" sys.code in(:code)");
			map.put("code", code);
			String hql1= hql.toString();
			hql1 += "order by sys.code";
			List list = dao.query(hql1,map);
			if(list != null && list.size()>0){
				StringBuffer str = new StringBuffer();
				for(int j=0; j<list.size(); j++){
					str.append(((Object[])list.get(j))[0].toString()+"%"+((Object[])list.get(j))[1].toString()+"%"+((Object[])list.get(j))[2].toString()+"; ");
				}
				String str1 = str.toString();
				str1 = str1.substring(0,str.length()-2);
				return str1;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 由以‘;’隔开的学科Id组成的字符串获取对应的‘代码/名称; ’序列
	 * @param ids以‘;’隔开的学科Id组成的字符串
	 * @return
	 */
	public String getDisciplineCodeNamesByIds(String ids){
		String[] id = ids.split(";");
		if(id.length>0){
			StringBuffer hql = new StringBuffer();
			Map map = new HashMap();
			hql.append("select sys.code,sys.name from SystemOption sys where ");
			hql.append(" sys.id in(:id)");
			map.put("id", id);
			String hql1=hql.toString();
			List list = dao.query(hql1,map);
			if(list !=null && list.size()>0){
				StringBuffer codeName = new StringBuffer();
				for(int j=0;j<list.size();j++){
					codeName.append(((Object [])list.get(j))[0].toString());
					codeName.append("/");
					codeName.append(((Object [])list.get(j))[1].toString());
					codeName.append("; ");
				}
				String codeNames = codeName.toString();
				codeNames = codeNames.substring(0,codeNames.length()-2);
				return codeNames;
			}else{
				return null;
			}
		}else{
			return null;
	}
	}
	
	/**
	 * 在系统选项表中根据父节点id获取子节点的（id,name)组成的list
	 * @param id
	 * @return
	 */
	public Map<String,String> getSystemOptionCodeName(String parentId){
		Map<String,String> systemOption = Collections.synchronizedMap(new LinkedHashMap<String,String>());
		StringBuffer hql2 = new StringBuffer();
		hql2.append("select sys.code,sys.name from SystemOption sys,SystemOption so where sys.systemOption.id=so.id and so.id =:parentId order by sys.code ");
		Map parMap = new HashMap();
		parMap.put("parentId", parentId);
		List list=dao.query(hql2.toString(),parMap);
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				systemOption.put(((Object[])list.get(i))[0].toString()+"/"+((Object[])list.get(i))[1].toString(),((Object[])list.get(i))[1].toString());
			}
		}
		return systemOption;
	}
	
	/**
	 * 根据机构类型和id找到机构下博士点list
	 * @param type 机构类型(3:部属高校 4:地方高校 5:院系 6:研究基地)
	 * @param unitId 机构id
	 * @return doctorialList
	 */
	public List getDoctorialList(int type, String unitId){
		Map map = new HashMap();
		map.put("unitId", unitId);
		StringBuffer hql = new StringBuffer("select doc.id, doc.name, doc.code, doc.date, doc.discipline, doc.isKey, doc.introduction ");
		List list = new ArrayList();
		List doctorial;
		//博士点
		if(type == 3 || type == 4){// 部属高校&地方高校
			hql.append("from Doctoral doc left outer join doc.university uni where uni.id =:unitId");
		} else if(type == 5){// 院系
			hql.append("from Doctoral doc left outer join doc.university uni1 left outer join doc.department dep1," +
					"Department dep2 left outer join dep2.university uni2 " +
					"where uni1.id = uni2.id and dep1.id = dep2.id and uni1.id =:unitId");
		} else{// 研究基地
			hql.append("from Doctoral doc left outer join doc.university uni1 left outer join doc.institute ins1," +
					"Institute ins2 left outer join ins2.subjection uni2 " +
					"where uni1.id = uni2.id and ins1.id = ins2.id and ins1.id =:unitId");
		};
		doctorial = dao.query(hql.toString(),map);
		if(doctorial.size()>0){
			for(int i=0;i<doctorial.size();i++){
				list.add((Object[])doctorial.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 根据机构类型和id找到机构下重点学科list
	 * @param type 机构类型 (3:部属高校 4:地方高校 5:院系 6:研究基地)
	 * @param unitId 机构id
	 * @return doctorialList
	 * @author zhouzj
	 */
	public List getDisciplineList(int type, String unitId){
		Map map = new HashMap();
		map.put("unitId", unitId);
		StringBuffer hql = new StringBuffer("select dis.id, dis.name, dis.code, dis.date, dis.discipline, dis.introduction ");
		List list = new ArrayList();
		List discipline;
		//博士点
		if(type == 3 || type == 4){// 部属高校&地方高校
			hql.append("from Discipline dis left outer join dis.university uni where uni.id =:unitId");
		} else if(type == 5){// 院系
			hql.append("from Discipline dis left outer join dis.university uni1 left outer join dis.department dep1," +
					"Department dep2 left outer join dep2.university uni2 " +
					"where uni1.id = uni2.id and dep1.id = dep2.id and uni1.id =:unitId");
		} else{// 研究基地
			hql.append("from Discipline dis left outer join dis.university uni1 left outer join dis.institute ins1," +
					"Institute ins2 left outer join ins2.subjection uni2 " +
					"where uni1.id = uni2.id and ins1.id = ins2.id and ins1.id =:unitId");
		};
		discipline = dao.query(hql.toString(),map);
		if(discipline.size()>0){
			for(int i=0;i<discipline.size();i++){
				list.add((Object[])discipline.get(i));
			}
		}
		return list;
	}
	/**
	 * 根据机构类型和id找到机构下重点学科list
	 * @param unitType 机构类型 (1:部属高校 2:地方高校 3:院系 4:研究基地)
	 * @param personType 返回的人员类型 (1:社科管理人员 2:教师 3:学生)
	 * @param unitId 机构id
	 * @return 对应类型的人员列表
	 */
	public List getPersonList(int unitType, int personType, String unitId){
		Map map = new HashMap();
		map.put("unitId", unitId);
		StringBuffer hql = new StringBuffer();
		List list = new ArrayList();
		List person;
		// 选择
		// 社科管理人员 
		String hql11 = "select p.id, p.name, o.staffCardNumber, o.position, to_char(o.startDate,'yyyy-mm-dd HH:mm:ss'), to_char(o.endDate,'yyyy-mm-dd HH:mm:ss'), o.type from " +
				"Officer o left outer join o.person p left outer join o.agency u left outer join " +
				"o.department d left outer join o.institute i where ";
		// 教师
		String hql12 = "select p.id, p.name, t.staffCardNumber, t.position, t.workMonthPerYear, to_char(t.startDate,'yyyy-mm-dd HH:mm:ss'), to_char(t.endDate,'yyyy-mm-dd HH:mm:ss'), " +
				"t.type from Teacher t left outer join t.person p left outer join t.university u left outer " +
				"join t.department d left outer join t.institute i where ";
		// 学生
		String hql13 = "select p.id, p.name, s.type, s.status, to_char(s.startDate,'yyyy-mm-dd HH:mm:ss'), to_char(s.endDate,'yyyy-mm-dd HH:mm:ss'), tutor.id, tutor.name, ac.major, " +
				"ac.researchField, s.project, s.thesisTitle, s.studentCardNumber from Student s left outer join " +
				"s.person p left outer join s.tutor tutor left outer join s.university u left outer join s.department d " +
				"left outer join s.institute i left join p.academic ac where ";
		// 
		String hql21 = "u.id =:unitId";
		String hql22 = "d.id =:unitId";
		String hql23 = "i.id =:unitId";
		if(unitType == 1 || unitType == 2){// 部属高校&地方高校
			if(personType == 1){
				hql.append(hql11);
			} else if(personType == 2){
				hql.append(hql12);
			} else if(personType == 3){
				hql.append(hql13);
			}
			hql.append(hql21);
		} else if(unitType == 3){// 院系
			if(personType == 1){
				hql.append(hql11);
			} else if(personType == 2){
				hql.append(hql12);
			} else if(personType == 3){
				hql.append(hql13);
			}
			hql.append(hql22);
		} else if(unitType == 4){// 研究基地
			if(personType == 1){
				hql.append(hql11);
			} else if(personType == 2){
				hql.append(hql12);
			} else if(personType == 3){
				hql.append(hql13);
			}
			hql.append(hql23);
		};
		person = dao.query(hql.toString(),map);
		if(person.size()>0){
			for(int i=0;i<person.size();i++){
				list.add((Object[])person.get(i));
			}
		}
		return list;
	}

	/**
	 * 根据研究基地id找到此研究基地的拨款信息
	 * @param instituteId
	 * @return funding list
	 */
	public List getInstituteFunding(String instituteId){
		List<InstituteFunding> fundings = dao.query("from InstituteFunding insf where insf.institute.id = ? order by insf.date desc", instituteId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List res = new ArrayList();
		for (InstituteFunding funding : fundings) {
			res.add(new Object[]{
				funding.getId(),
				funding.getProjectFee(),
				funding.getDataFee(),
				funding.getConferenceFee(),
				funding.getJournalFee(),
				funding.getNetFee(),
				funding.getDatabaseFee(),
				funding.getAwardFee(),
				funding.getFee(),
				sdf.format(funding.getDate()),
				funding.getAttn(),
				funding.getNote()
			});
		}
		return res;
	}
	
	/**
	 * 根据ids字符串获得这些机构的list
	 * @param checkedIds
	 * @param type 1高校 2院系 3基地
	 * @return
	 */
	public List getCheckedList(String checkedIds, Integer type) {
		List list = new ArrayList();
		String hql = "";
		Map parMap = new HashMap();
		String[] checkedIdStrings = checkedIds.split(",");
		for (int i = 0; i < checkedIdStrings.length; i++) {
			parMap.put("id", checkedIdStrings[i]);
			if(type == 1){
				hql = "select u.id, u.name, u.code from Agency u where u.id =:id";
			} else if(type == 2){
				hql = "select d.id, d.name, d.code, u.id, u.name from Department d left join d.university u where d.id =:id";
			} else if(type == 3){
				hql = "select i.id, i.name, i.code, u.id, u.name from Institute i left join i.subjection u where i.id =:id";
			}
			List checked = dao.query(hql.toString(), parMap);
			list.add(checked.get(0));
		}
		return list;
	}
	
	public Agency setBaseInfo(Agency agency){
		if("-1".equals(agency.getCategory())){
			agency.setCategory(null);
		}
		if("-1".equals(agency.getStyle())){
			agency.setStyle(null);
		}
		return agency;
	}
	
	public String getInstituteTypeByCode(Integer code){
		String codeString = "0"+code.toString();
		return (String) dao.queryUnique("select s.name from SystemOption s where s.code = ? and s.standard = 'researchAgencyType' ",codeString);	
	}

	public Agency setBankInfo(Agency agency, List<BankAccount> bankList){
		if(!bankList.isEmpty()){
			String bankIds = UUID.randomUUID().toString().replaceAll("-", "");
			agency.setBankIds(bankIds);
			int i = 1;
			for(BankAccount ba : bankList){
				if(ba.getIsDefault()==1){
					ba.setSn(1);
				}else {
					i++;
					ba.setSn(i);
				}
				ba.setCreateMode(1);
				ba.setCreateDate(new Date());
				ba.setIds(bankIds);
				dao.add(ba);
			}
		}
		return agency;
	}
	
	public Agency resetBankInfo(Agency agency, List<BankAccount> bankList){
		if(agency.getBankIds()==null || agency.getBankIds().isEmpty()){//修改前没有银行信息
			if(!bankList.isEmpty()){
				String bankIds = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setBankIds(bankIds);
				dao.modify(agency);
				int i = 1;
				for(BankAccount ba : bankList){
					if(ba.getIsDefault()==1){
						ba.setSn(1);
					}else {
						i++;
						ba.setSn(i);
					}
					ba.setCreateMode(1);
					ba.setCreateDate(new Date());
					ba.setIds(bankIds);
					dao.add(ba);
				}
			}
		}else {
			String bankIds = agency.getBankIds();
			if(!bankList.isEmpty()){
				List<String> oldIds = dao.query("select ba.id from BankAccount ba where ba.ids = ?", bankIds);//原银行信息的id集合
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(BankAccount ba : bankList){
					if(ba.getIsDefault()==1){
						ba.setSn(1);
					}else {
						i++;
						ba.setSn(i);
					}
					if(ba.getId().isEmpty()){//新增的银行信息
						ba.setCreateMode(1);
						ba.setCreateDate(new Date());
						ba.setIds(bankIds);
						dao.add(ba);
					}else {
						ba = modifyBankAccount(ba);
						if(isChanged(ba)){
							ba.setUpdateDate(new Date());
						}
						dao.modify(ba);
						newIds.add(ba.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条银行信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(BankAccount.class ,id));
					}
				}
			}else {//原有的银行信息全被删除了
				bankList = (List<BankAccount>)dao.query("select ba from BankAccount ba where ba.ids = ? ", bankIds);
				for(BankAccount ba : bankList){
					dao.delete(ba);
				}
				agency.setBankIds(null);
				dao.modify(agency);
			}
		}
		return agency;
	}
	
	public Agency setAddress(Agency agency, List<Address> commonAddress,
			List<Address> financeAddress, List<Address> subjectionAddress) {
		if(!commonAddress.isEmpty()){
			String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
			agency.setAddressIds(addressIds);
			int i = 1;
			for(Address address : commonAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(addressIds);
				dao.add(address);
			}
		}
		if(!financeAddress.isEmpty()){
			String faddressIds = UUID.randomUUID().toString().replaceAll("-", "");
			agency.setFaddressIds(faddressIds);
			int i = 1;
			for(Address address : financeAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(faddressIds);
				dao.add(address);
			}
		}
		if(!subjectionAddress.isEmpty()){
			String saddressIds = UUID.randomUUID().toString().replaceAll("-", "");
			agency.setSaddressIds(saddressIds);
			int i = 1;
			for(Address address : subjectionAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(saddressIds);
				dao.add(address);
			}
		}
		dao.modify(agency);
		return agency;
	}

	public Agency resetAddress(Agency agency, List<Address> commonAddress,
			List<Address> financeAddress, List<Address> subjectionAddress) {
		if(agency.getAddressIds() == null || agency.getAddressIds().isEmpty()){//修改前没有普通地址信息
			if(!commonAddress.isEmpty()){
				String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setAddressIds(addressIds);
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(addressIds);
					dao.add(address);
				}
				dao.modify(agency);
			}
		}else {
			String addressIds = agency.getAddressIds();
			if(!commonAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", addressIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(addressIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的普通住址信息全被删除了
				commonAddress = dao.query("select address from Address address where address.ids = ? ", addressIds);
				for(Address address : commonAddress){
					dao.delete(address);
				}
				agency.setAddressIds(null);
				dao.modify(agency);
			}
		}
		if(agency.getSaddressIds() == null || agency.getSaddressIds().isEmpty()){//修改前没有管理部门地址信息
			if(!subjectionAddress.isEmpty()){
				String saddressIds = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setSaddressIds(saddressIds);
				int i = 1;
				for(Address address : subjectionAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(saddressIds);
					dao.add(address);
				}
				dao.modify(agency);
			}
		}else {
			String saddressIds = agency.getSaddressIds();
			if(!subjectionAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", saddressIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : subjectionAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(saddressIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的管理部门地址全被删除了
				subjectionAddress = dao.query("select address from Address address where address.ids = ? ", saddressIds);
				for(Address address : subjectionAddress){
					dao.delete(address);
				}
				agency.setSaddressIds(null);
				dao.modify(agency);
			}
		}
		if(agency.getFaddressIds() == null || agency.getFaddressIds().isEmpty()){//修改前没有财务部门地址信息
			if(!financeAddress.isEmpty()){
				String faddressIds = UUID.randomUUID().toString().replaceAll("-", "");
				agency.setAddressIds(faddressIds);
				int i = 1;
				for(Address address : financeAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(faddressIds);
					dao.add(address);
				}
				dao.modify(agency);
			}
		}else {
			String faddressIds = agency.getAddressIds();
			if(!financeAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", faddressIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : financeAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(faddressIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的财务部门地址全被删除了
				financeAddress = dao.query("select address from Address address where address.ids = ? ", faddressIds);
				for(Address address : financeAddress){
					dao.delete(address);
				}
				agency.setFaddressIds(null);
				dao.modify(agency);
			}
		}
		return agency;
	}
	
	public Department setAddress(Department department, List<Address> commonAddress) {
		if(!commonAddress.isEmpty()){
			String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
			department.setAddressIds(addressIds);
			int i = 1;
			for(Address address : commonAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(addressIds);
				dao.add(address);
			}
		}
		dao.modify(department);
		return department;
	}

	public Department resetAddress(Department department, List<Address> commonAddress) {
		if(department.getAddressIds() == null || department.getAddressIds().isEmpty()){//修改前没有普通地址信息
			if(!commonAddress.isEmpty()){
				String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
				department.setAddressIds(addressIds);
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(addressIds);
					dao.add(address);
				}
				dao.modify(department);
			}
		}else {
			String addressIds = department.getAddressIds();
			if(!commonAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", addressIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(addressIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的普通住址信息全被删除了
				commonAddress = dao.query("select address from Address address where address.ids = ? ", addressIds);
				for(Address address : commonAddress){
					dao.delete(address);
				}
				department.setAddressIds(null);
				dao.modify(department);
			}
		}
		return department;
	}

	public Institute setAddress(Institute institute, List<Address> commonAddress) {
		if(!commonAddress.isEmpty()){
			String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
			institute.setAddressIds(addressIds);
			int i = 1;
			for(Address address : commonAddress){
				if(address.getIsDefault() == 1){
					address.setSn(1);
				}else {
					i++;
					address.setSn(i);
				}
				address.setCreateMode(1);
				address.setCreateDate(new Date());
				address.setIds(addressIds);
				dao.add(address);
			}
		}
		dao.modify(institute);
		return institute;
	}

	public Institute resetAddress(Institute institute, List<Address> commonAddress) {
		if(institute.getAddressIds() == null || institute.getAddressIds().isEmpty()){//修改前没有普通地址信息
			if(!commonAddress.isEmpty()){
				String addressIds = UUID.randomUUID().toString().replaceAll("-", "");
				institute.setAddressIds(addressIds);
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault() == 1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					address.setCreateMode(1);
					address.setCreateDate(new Date());
					address.setIds(addressIds);
					dao.add(address);
				}
				dao.modify(institute);
			}
		}else {
			String addressIds = institute.getAddressIds();
			if(!commonAddress.isEmpty()){
				List<String> oldIds = dao.query("select address.id from Address address where address.ids = ? ", addressIds);
				List<String> newIds = new ArrayList<String>();
				int i = 1;
				for(Address address : commonAddress){
					if(address.getIsDefault()==1){
						address.setSn(1);
					}else {
						i++;
						address.setSn(i);
					}
					if(address.getId().isEmpty()){//新增的地址信息
						address.setCreateMode(1);
						address.setCreateDate(new Date());
						address.setIds(addressIds);
						dao.add(address);
					}else {
						address = modifyAddress(address);
						if(addressIsChanged(address)){
							address.setUpdateDate(new Date());
						}
						dao.modify(address);
						newIds.add(address.getId());
					}
				}
				for(String id : oldIds){//判断本次修改中是否删除了某条地址信息
					if(!newIds.contains(id)){
						dao.delete(dao.query(Address.class ,id));
					}
				}
			}else {//原有的普通住址信息全被删除了
				commonAddress = dao.query("select address from Address address where address.ids = ? ", addressIds);
				for(Address address : commonAddress){
					dao.delete(address);
				}
				institute.setAddressIds(null);
				dao.modify(institute);
			}
		}
		return institute;
	}
	
	public List getAddress(Object o){
		List list = new ArrayList<String>();
		List<Address> addressList = new ArrayList<Address>(); 
		if(o instanceof Agency){
			Agency agency = (Agency)o;
			addressList = dao.query("select address from Address address where address.ids = ?", agency.getAddressIds());
		}else if(o instanceof Department){
			Department department = (Department)o;
			addressList = dao.query("select address from Address address where address.ids = ?", department.getAddressIds());
		}else if(o instanceof Institute){
			Institute institute = (Institute)o;
			addressList = dao.query("select address from Address address where address.ids = ?", institute.getAddressIds());
		}else {
			return list;
		}
		String address = "";
		String postcode = "";
		for(Address a : addressList){
			if(a.getAddress()!=null && !a.getAddress().isEmpty()){
				address = address + a.getAddress() + ";";
			}
			if(a.getPostCode()!=null && !a.getPostCode().isEmpty()){
				postcode = postcode + a.getPostCode() + ";";
			}
		}
		list.add(address.substring(0, address.length()-1));
		list.add(postcode.substring(0, postcode.length()-1));
		return list;
	}
	
	public List getSAddress(Agency agency){
		List list = new ArrayList<String>();
		List<Address> addressList = dao.query("select address from Address address where address.ids = ?", agency.getSaddressIds());
		String address = "";
		String postcode = "";
		for(Address a : addressList){
			if(a.getAddress()!=null && !a.getAddress().isEmpty()){
				address = address + a.getAddress() + ";";
			}
			if(a.getPostCode()!=null && !a.getPostCode().isEmpty()){
				postcode = postcode + a.getPostCode() + ";";
			}
		}
		list.add(address.substring(0, address.length()-1));
		list.add(postcode.substring(0, postcode.length()-1));
		return list;
	}
	
	public List getFAddress(Agency agency){
		List list = new ArrayList<String>();
		List<Address> addressList = dao.query("select address from Address address where address.ids = ?", agency.getFaddressIds());
		String address = "";
		String postcode = "";
		for(Address a : addressList){
			if(a.getAddress()!=null && !a.getAddress().isEmpty()){
				address = address + a.getAddress() + ";";
			}
			if(a.getPostCode()!=null && !a.getPostCode().isEmpty()){
				postcode = postcode + a.getPostCode() + ";";
			}
		}
		list.add(address.substring(0, address.length()-1));
		list.add(postcode.substring(0, postcode.length()-1));
		return list;
	}
	
	/**
	 * 判断数据库中的机构表结构是否有更改[机构合并前的校验]
	 */
	public boolean isAgencyTablesExpired(){
		Integer curAgencyLength =180;
		Class AgencyClass = Agency.class;
		System.out.println(AgencyClass.getMethods().length);
		if(AgencyClass.getMethods().length == curAgencyLength){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断数据库中的院系表结构是否有更改[院系合并前的校验]
	 */
	public boolean isDepartmentTablesExpired(){
		Integer curDepartmentLength =91;
		Class DepartmentClass = Department.class;
		System.out.println(DepartmentClass.getMethods().length);
		if(DepartmentClass.getMethods().length == curDepartmentLength){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断数据库中的基地表结构是否有更改[基地合并前的校验]
	 */
	public boolean isInstituteTablesExpired(){
		Integer curInstituteLength =132;
		Class InstituteClass = Institute.class;
		System.out.println(InstituteClass.getMethods().length);
		if(InstituteClass.getMethods().length == curInstituteLength){
			return true;
		}
		return false;
	}
	
	//补充银行对象中其他不由前台传递的数据
	private BankAccount modifyBankAccount(BankAccount bankAccount){
		BankAccount oldBankAccount = dao.query(BankAccount.class, bankAccount.getId());
		bankAccount.setCreateDate(oldBankAccount.getCreateDate());
		bankAccount.setCreateMode(oldBankAccount.getCreateMode());
		bankAccount.setIds(oldBankAccount.getIds());
		bankAccount.setUpdateDate(oldBankAccount.getUpdateDate());
		return bankAccount;
	}
	
	//补充地址对象中其他不由前台传递的数据
	private Address modifyAddress(Address address){
		Address oldAddress = dao.query(Address.class, address.getId());
		address.setCreateDate(oldAddress.getCreateDate());
		address.setCreateMode(oldAddress.getCreateMode());
		address.setIds(oldAddress.getIds());
		address.setUpdateDate(oldAddress.getUpdateDate());
		return address;
	}
	
	//原有的银行信息是否有更改[只判断用户可以选择的那5个字段]
	private boolean isChanged(BankAccount ba){
		BankAccount oldBA = dao.query(BankAccount.class, ba.getId());
		if(ba.getAccountName().equals(oldBA.getAccountName()) && ba.getAccountNumber().equals(oldBA.getAccountNumber()) && ba.getBankCupNumber().equals(oldBA.getBankCupNumber()) && ba.getBankName().equals(oldBA.getBankName()) && ba.getIsDefault().equals(oldBA.getIsDefault())){
			return false;
		}else return true;
	}
	
	//原有的地址信息是否更改[只判断用户可以选择的那3个字段]
	private boolean addressIsChanged(Address address){
		Address oldAddress = dao.query(Address.class, address.getId());
		if(address.getAddress().equals(oldAddress.getAddress()) && address.getPostCode().equals(oldAddress.getPostCode()) && address.getIsDefault().equals(oldAddress.getIsDefault()) ){
			return false;
		}else return true;
	}
	
	//删除机构相关联的地址信息
	private void deleteAddress(Object o){
		if(o instanceof Agency){
			Agency agency = (Agency)o;
			if(agency.getAddressIds()!=null && !agency.getAddressIds().isEmpty()){
				List<Address> addressList = dao.query("select address from Address address where address.ids = ?", agency.getAddressIds());
				dao.delete(addressList);
			}
			if(agency.getSaddressIds()!=null && !agency.getSaddressIds().isEmpty()){
				List<Address> addressList = dao.query("select address from Address address where address.ids = ?", agency.getSaddressIds());
				dao.delete(addressList);
			}
			if(agency.getFaddressIds()!=null && !agency.getFaddressIds().isEmpty()){
				List<Address> addressList = dao.query("select address from Address address where address.ids = ?", agency.getFaddressIds());
				dao.delete(addressList);
			}
		}else if(o instanceof Department){
			Department department = (Department)o;
			if(department.getAddressIds()!=null && !department.getAddressIds().isEmpty()){
				List<Address> addressList = dao.query("select address from Address address where address.ids = ?", department.getAddressIds());
				dao.delete(addressList);
			}
		}else if(o instanceof Institute){
			Institute institute = (Institute)o;
			if(institute.getAddressIds()!=null && !institute.getAddressIds().isEmpty()){
				List<Address> addressList = dao.query("select address from Address address where address.ids = ?", institute.getAddressIds());
				dao.delete(addressList);
			}
		}
	}
	
	//删除机构相关联的银行账号信息
	private void deleteBank(Agency agency){
		if(agency.getBankIds()!=null && !agency.getBankIds().isEmpty()){
			List<BankAccount> bankList = dao.query("select ba from BankAccount ba where ba.ids = ? ", agency.getBankIds());
			dao.delete(bankList);
		}
	}
	
}
