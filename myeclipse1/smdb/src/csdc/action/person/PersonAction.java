package csdc.action.person;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Abroad;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.BankAccount;
import csdc.bean.Department;
import csdc.bean.Education;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.bean.Work;
import csdc.service.IPersonService;
import csdc.service.IUploadService;
import csdc.service.ext.IAccountExtService;
import csdc.service.ext.IUnitExtService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.InputValidate;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.PersonInfo;
import csdc.tool.info.RightInfo;

@Transactional
public abstract class PersonAction extends BaseAction {

	private static final long serialVersionUID = 8105337914266085482L;
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式

	private InputValidate inputValidate = new InputValidate();//校验工具类

	protected String unitName;// 机构名称
	protected String unitId;// 机构ID
	protected String deptName;// 部门名称
	protected String name;//姓名
	protected Integer age;//年龄
	protected String staffCardNumber;//工作证号
	protected String gender;// 性别
	protected String position;// 职务
	protected String specialityTitle;//专业职称
	protected String disciplineType;//学科门类
	protected String provName;//高校所属省份
	protected File photo;
	protected String photoFileName;// 上传文件的文件名
	protected String photoContentType;// 上传文件的类型
	protected Person person;
	protected Person originPerson;
	
	protected Account account;
	protected Agency agency;
	protected Department department;
	protected Institute institute;
	protected String idcardNumber;	//人员证件号
	protected Academic academic;
	protected String personId;
	protected Integer isKey;
	
	protected String checkedIds; //用于人员合并
	 
	protected IPersonService personService;
	protected IUnitExtService unitExtService;// 机构管理接口
	protected IAccountExtService accountExtService;// 账号管理接口
	
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	private List sexList;

	//异步文件上传所需
	protected String[] fileIds;//标题提交上来的特征码list
	protected String uploadKey;//文件上传授权码
	
	protected Map<String, String> optionalPassportNames = new HashMap<String, String>(); //key为账号，value为通行证名
	protected String selectedAccountId;
	
	protected List<Education> ebs;	//教育背景
	protected List<Work> wes;	//工作经历
	protected List<Abroad> aes;	//出国（境）经历
	protected List<BankAccount> bankList = new ArrayList<BankAccount>(); //银行信息组
	protected List<Address> homeAddress = new ArrayList<Address>();//家庭住址组
	protected List<Address> officeAddress = new ArrayList<Address>();//办公地址组
	
	public abstract String HQL();
	public abstract String pageName();
	public abstract String[] searchConditions();
	public abstract String[] column();
	
	/**
	 * 构造简单查询的基础HQL
	 */
	public abstract void simpleSearchBaseHql(StringBuffer hql,Map map);
	
	public abstract boolean addInner();
	public abstract void viewInner();
	public abstract void toModifyInner();
	public abstract boolean modifyInner();
	
	public abstract Integer idType();
	public String dateFormat() {
		return PersonAction.DATE_FORMAT;
	}

	public String toAdd(){
		String groupId = "photo_person_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}
	
	
	/**
	 * 校验人员基本信息
	 * @param person 人员
	 * @param inAdd true:添加	false:修改
	 */
	public void validateBasicInfo(Person person, boolean inAdd) {
//		person.setName(personService.regularNames(person.getName()));
		if (person.getName() == null || person.getName().trim().isEmpty()){
			this.addFieldError("person.name", "基本信息 —— 姓名不应为空");
		} else if(!Pattern.matches("^.{1,50}$", person.getName().trim())){
			this.addFieldError("person.name", "基本信息 —— 中文名不合法");
		}
//		if (person.getIdcardType() == null || person.getIdcardType().trim().isEmpty()){
//			this.addFieldError("person.idcardType", "基本信息 —— 证件类型不应为空");
//		}
//		if (person.getIdcardNumber() == null || person.getIdcardNumber().trim().isEmpty()){
//			this.addFieldError("person.idcardNumber", "基本信息 —— 证件号不应为空");
//		} else 
		if (person.getIdcardNumber().trim().length() > 18){
			this.addFieldError("person.idcardNumber", "基本信息 —— 证件号过长");
		}
		if(person.getIdcardType().equals("-1")){
			person.setIdcardType(null);
		}
		if (inAdd && this.getFieldErrors().isEmpty() && personService.checkIdcard(person.getIdcardNumber())){
			return;
		}
		if(person.getEnglishName() != null && !Pattern.matches("^[ a-zA-Z\n.]{0,200}$", person.getEnglishName().trim())){
			this.addFieldError("person.englishName", "基本信息 —— 英文名不合法");
		}
		if(person.getUsedName() != null && person.getUsedName().trim().length() > 50){
			this.addFieldError("person.usedName", "基本信息 —— 曾用名不合法");
		}
//		if (person.getGender() == null || !person.getGender().trim().equals("男") && !person.getGender().trim().equals("女")){
//			this.addFieldError("person.gender", "基本信息 —— 请选择性别");
//		}
		if (person.getCountryRegion() != null && person.getCountryRegion().trim().length() > 20){
			this.addFieldError("person.countryRegion", "基本信息 —— 国家或地区名称过长");
		}
		if (person.getEthnic() != null && person.getEthnic().trim().length() > 20){
			this.addFieldError("person.ethnic", "基本信息 —— 民族过长");
		}
		if (person.getBirthplace() != null && person.getBirthplace().trim().length() > 20){
			this.addFieldError("person.birthplace", "基本信息 —— 籍贯过长");
		}
		if (person.getBirthday() != null && person.getBirthday().compareTo(new Date()) > 0) {
			this.addFieldError("person.birthday", "基本信息 —— 不合理的出生日期");
		}
		if (person.getMembership() != null && person.getMembership().trim().length() > 20){
			this.addFieldError("person.membership", "基本信息 —— 政治面貌过长");
		}
	}

	/**
	 * 校验人员联系信息
	 */
	public void validateContactInfo(Person person, boolean inAdd) {
		if (inAdd && person.getIdcardNumber() != null && personService.checkIdcard(person.getIdcardNumber())){
			return;
		}
		if (person.getHomePhone() != null && !inputValidate.checkFixedphone(person.getHomePhone().trim())){
			this.addFieldError("person.homePhone", "联系信息 —— 住宅电话不合法");
		}
		if (person.getOfficePhone() != null && !inputValidate.checkFixedphone(person.getOfficePhone().trim())){
			this.addFieldError("person.officePhone", "联系信息 —— 办公电话不合法");
		}
		if (person.getOfficeFax() != null && !inputValidate.checkFixedphone(person.getOfficeFax().trim())){
			this.addFieldError("person.officeFax", "联系信息 —— 办公传真不合法");
		}
		if (person.getEmail() == null || person.getEmail().trim().isEmpty()){
			this.addFieldError("person.email", "联系信息 —— 邮箱不应为空");
		} else {
			String[] mail = person.getEmail().split(";");
			for (int i = 0; i < mail.length; i++) {
				String	email = mail[i];
				if(!inputValidate.checkEmail(email.trim())){
					this.addFieldError("person.email", "联系信息 —— 邮箱不合法");
				}
			}
		}
		if (person.getMobilePhone() != null && !inputValidate.checkCellphone(person.getMobilePhone().trim())){
			this.addFieldError("person.mobilePhone", "联系信息 —— 移动电话不合法");
		}
		if (person.getQq() != null && !inputValidate.checkMultiQQ(person.getQq().trim())){
			this.addFieldError("person.qq", "联系信息 —— QQ号不合法");
		}
		if(person.getMsn() != null && !inputValidate.checkMultiEmail(person.getMsn())){
			this.addFieldError("person.msn", "联系信息 —— msn不合法");
		}
		if(person.getHomepage() != null && !inputValidate.checkMultiHomepage(person.getHomepage())){
			this.addFieldError("person.homepage", "联系信息 —— 个人主页不合法");
		}
	}

	/**
	 * 校验人员地址信息
	 * @param list
	 */
	public void validateAddress(List<Address> list){
		for(Address address : list){
			if (address.getPostCode() != null && !inputValidate.checkPostcode(address.getPostCode())){
				this.addFieldError("person.homePostcode", "联系信息 —— 邮编不合法");
			}
			if (address.getAddress() != null && address.getAddress().trim().length() > 400){
				this.addFieldError("person.officeAddress", "联系信息 —— 地址过长");
			}
		}
	}
	
	/**
	 * 校验人员学术信息
	 */
	public void validateAcademicInfo(Academic academic) {
		if (academic.getEthnicLanguage() != null){
			if (!academic.getEthnicLanguage().equals("无") && !academic.getEthnicLanguage().matches("(.+/.+; )*.+/.+")){
//				this.addFieldError("academic.ethnicLanguage", "学术信息 —— 民族语言不合法");
			}
		}
		if (academic.getLanguage() != null){
			if (!academic.getLanguage().equals("无") && !academic.getLanguage().matches("(.+/.+; )*.+/.+")){
//				this.addFieldError("academic.language", "学术信息 —— 外语语种不合法");
			}
		}
		if (academic.getComputerLevel() != null){
			if ("-1".equals(academic.getComputerLevel().getId()) || academic.getComputerLevel().getId().isEmpty()) {
				academic.setComputerLevel(null);
			} 
		}
		if(academic.getExpertType() != null){
			if(academic.getExpertType().getId().equals("-1")){
				academic.setExpertType(null);
			}
		}
		if (academic.getDisciplineType() != null){
			if (academic.getDisciplineType().trim().length() > 100){
				this.addFieldError("academic.disciplineType", "学术信息 —— 学科门类过长，最长100字符");
			}
		}
		if (academic.getDiscipline() != null){
			if (academic.getDiscipline().trim().length() > 100){
				this.addFieldError("academic.discipline", "学术信息 —— 学科过长，最长100字符");
			}
		}
		if (academic.getRelativeDiscipline() != null){
			if (academic.getRelativeDiscipline().trim().length() > 100){
				this.addFieldError("academic.relativeDiscipline", "学术信息 —— 学科门类过长，最长100字符");
			}
		}
		if (academic.getResearchField() != null && academic.getResearchField().trim().length() > 200){
			this.addFieldError("academic.researchField", "学术信息 —— 研究领域过长，最长200字符");
		}
		if (academic.getMajor() != null && academic.getMajor().trim().length() > 50){
			this.addFieldError("academic.major", "学术信息 —— 所属专业过长，最长50字");
		}
		if (academic.getResearchSpeciality() != null && academic.getResearchSpeciality().trim().length() > 200){
			this.addFieldError("academic.researchSpeciality", "学术信息 —— 学术特长过长，最长200字符");
		}
		if (academic.getFurtherEducation() != null && academic.getFurtherEducation().trim().length() > 400){
			this.addFieldError("academic.furtherEducation", "学术信息 —— 进修情况过长，最长400字符");
		}
		if (academic.getParttimeJob() != null && academic.getParttimeJob().trim().length() > 400){
			this.addFieldError("academic.parttimeJob", "学术信息 —— 学术兼职过长，最长400字符");
		}
		if (academic.getSpecialityTitle() != null){
			if ("-1".equals(academic.getSpecialityTitle()) || academic.getSpecialityTitle().isEmpty()) {
				academic.setSpecialityTitle(null);
			} 
//			else if (!Pattern.matches("(教授)|(副教授)|(讲师)|(助教)|(初级Ⅰ)|(初级Ⅱ)|(辅助人员)", academic.getSpecialityTitle())){
//				this.addFieldError("academic.specialityTitle", "学术信息 —— 专业职称不合法");
//			}
		}
		if (academic.getPositionLevel() != null && academic.getPositionLevel().trim().length() > 20){
			this.addFieldError("academic.positionLevel", "学术信息 —— 岗位等级过长20，最长20字符");
		}
		if (academic.getTutorType() != null){
			if ("-1".equals(academic.getTutorType()) || academic.getTutorType().isEmpty()) {
				academic.setTutorType(null);
			} else if (!Pattern.matches("(博士生导师)|(硕士生导师)|(其他)", academic.getTutorType())) {
				this.addFieldError("academic.tutorType", "学术信息 —— 导师类型不合法");
			}
		}
		if (academic.getPostdoctor() != -1){
			if (academic.getPostdoctor() > 2 && academic.getPostdoctor() < 0){
				this.addFieldError("academic.postdoctor", "学术信息 —— 是否博士后数据不合法");
			}
		}
		if (academic.getIsReviewer() > 1 || academic.getIsReviewer() < 0){
			this.addFieldError("academic.isReviewer", "学术信息 —— 是否参评专家不应为空");
		}
		if (academic.getTalent() != null){
			if ("-1".equals(academic.getTalent()) || academic.getTalent().isEmpty()) {
				academic.setTalent(null);
			} 
		}
		if (academic.getLastEducation() != null){
			if ("-1".equals(academic.getLastEducation()) || academic.getLastEducation().isEmpty()) {
				academic.setLastEducation(null);
			} else if (!Pattern.matches("(大专)|(本科)|(研究生)", academic.getLastEducation())) {
				this.addFieldError("academic.lastEducation", "学术信息 —— 最后学历不合法");
			}
		}
		if (academic.getLastDegree() != null){
			if ("-1".equals(academic.getLastDegree()) || academic.getLastDegree().isEmpty()) {
				academic.setLastDegree(null);
			} else if (!Pattern.matches("(学士)|(硕士)|(博士)", academic.getLastDegree())) {
				this.addFieldError("academic.lastDegree", "学术信息 —— 最后学位不合法");
			}
		}
		if (-1 == (academic.getPostdoctor()) || academic.getPostdoctor().equals(null)) {
			academic.setPostdoctor(null);
		}else if(academic.getPostdoctor() > 2 || academic.getPostdoctor() < 0 ){
			this.addFieldError("academic.postdoctor", "学术信息——专家类型不合法");
		}
	}

	/**
	 * 校验外部专家信息
	 */
	public void validateExpert(Expert expert){
		if (expert.getType() == null || expert.getType().trim().isEmpty()){
			this.addFieldError("expert.type", "任职信息 —— 人员类型不得为空");
		} else if (!expert.getType().matches("(专职人员)|(兼职人员)|(离职人员)")){
			this.addFieldError("expert.type", "任职信息 —— 人员类型错误");
		}
		if (expert.getAgencyName() == null || expert.getAgencyName().trim().isEmpty()){
			this.addFieldError("expert.agencyName", "任职信息 —— 所在单位名称不应为空");
		}
	}

	/**
	 * 校验教师信息
	 */
	public void validateTeacher(Teacher teacher){
		if (teacher.getType() == null || teacher.getType().trim().isEmpty()){
			this.addFieldError("teacher.type", "任职信息 —— 人员类型不得为空");
		} else if (!teacher.getType().matches("(专职人员)|(兼职人员)|(离职人员)")){
			this.addFieldError("teacher.type", "任职信息 —— 人员类型错误");
		}
		if (teacher.getStartDate() != null && teacher.getEndDate() != null && teacher.getStartDate().compareTo(teacher.getEndDate()) > 0){
			this.addFieldError("teacher.startDate", "任职信息 —— 定职时间不应晚于离职时间");
		}
	}

	/**
	 * 校验学生信息
	 */
	public void validateStudentInfo(Student student){
		if (student.getType() == null || student.getType().trim().isEmpty()){
			this.addFieldError("student.type", "学生信息 —— 学生类别不得为空");
		} else if (!student.getType().matches("(硕士生)|(博士生)|(博士后)")){
			this.addFieldError("student.type", "学生信息 —— 学生类别错误");
		}
		if (student.getStatus() == null || student.getStatus().equals("-1")){
			this.addFieldError("student.status", "学生信息 —— 学生状态不得为空");
		} 
//		else if (!(student.getStatus() == 1 || student.getStatus() == 2)){
//			this.addFieldError("student.status", "学生信息 —— 学生状态错误");
//		}
		if (student.getStartDate() != null && student.getEndDate() != null && student.getStartDate().compareTo(student.getEndDate()) > 0){
			this.addFieldError("student.startDate", "学生信息 —— 入学时间不应晚于毕业时间");
		}
	}
	
	

	/**
	 * 校验学位论文
	 */
	public void validateThesisInfo(Student student){

	}
	
	/**
	 * 校验教育背景，工作经历，出国经历
	 */
	public void validateEWA(){
		if (ebs != null) {
			for (Education eb : ebs) {
				validateEducation(eb);
			}
		}
		if (wes != null) {
			for (Work we : wes) {
				validateWork(we);
			}
		}
		if (aes != null) {
			for (Abroad ae : aes) {
				validateAbroad(ae);
			}
		}
	}
	/**
	 * 校验教育背景
	 */
	public void validateEducation(Education education) {
//		if (education.getStartDate() == null) {
//			this.addFieldError("education.startDate", "教育背景 —— 请选择入学时间");
//		}
//		if (education.getEndDate() == null) {
//			this.addFieldError("education.endDate", "教育背景 —— 请选择学位授予时间");
//		}
		if (education.getStartDate() != null && education.getEndDate() != null && education.getStartDate().compareTo(education.getEndDate()) > 0){
			this.addFieldError("education.startDate", "教育背景 —— 入学时间不得晚于学位授予时间");
		}
//		if (education.getEducation() == null || education.getEducation().trim().isEmpty()){
//			this.addFieldError("education.education", "教育背景 —— 请选择学历");
//		} else if (!Pattern.matches("(大专)|(本科)|(研究生)", education.getEducation())){
//			this.addFieldError("education.education", "教育背景 —— 学历不合法");
//		}
//		if (education.getDegree() == null || education.getDegree().trim().isEmpty()){
//			this.addFieldError("education", "教育背景 —— 请选择学位");
//		} else if (!Pattern.matches("(学士)|(硕士)|(博士)", education.getDegree())){
//			this.addFieldError("education.degree", "教育背景 —— 学位不合法");
//		}
		if (education.getCountryRegion() != null && education.getCountryRegion().trim().length() > 20){
			this.addFieldError("education.countryRegion", "教育背景 —— 学位授予国家或地区名称过长，最长20字符");
		}
		if (education.getUniversity() != null && education.getUniversity().trim().length() > 50){
			this.addFieldError("education.univesity", "教育背景 —— 毕业高校名称过长，最长50字符");
		}
		if (education.getDepartment() != null && education.getDepartment().trim().length() > 50){
			this.addFieldError("education.department", "教育背景 —— 毕业所属院系过长，最长50字符");
		}
		if (education.getMajor() != null && education.getMajor().trim().length() > 50){
			this.addFieldError("education.major", "教育背景 —— 毕业所属专业过长，最长50字");
		}
	}
	
	/**
	 * 校验工作经历
	 */
	public void validateWork(Work work) {
//		if (work.getStartDate() == null) {
//			this.addFieldError("work.startDate", "工作经历 —— 请选择定职时间");
//		}
//		if (work.getEndDate() == null) {
//			this.addFieldError("work.endDate", "工作经历 —— 请选择离职时间");
//		}
		if (work.getStartDate() != null && work.getEndDate() != null && work.getStartDate().compareTo(work.getEndDate()) > 0){
			this.addFieldError("work.startDate", "工作经历 —— 定职时间不得晚于离职时间");
		}
		if (work.getPosition() != null && work.getPosition().trim().length() > 20){
			this.addFieldError("work.position", "工作经历 —— 职务过长20，最长20字符");
		}
		if (work.getStartDate() != null && work.getEndDate() != null && work.getStartDate().compareTo(work.getEndDate()) > 1){
			this.addFieldError("work.startDate", "工作经历 —— 定职时间不得晚于离职时间");
		}
	}


	/**
	 * 校验出国（境）经历
	 */
	public void validateAbroad(Abroad abroad) {
//		if (abroad.getStartDate() == null) {
//			this.addFieldError("abroad.startDate", "出国（境）经历 —— 请选择开始时间");
//		}
//		if (abroad.getEndDate() == null) {
//			this.addFieldError("abroad.endDate", "出国（境）经历 —— 请选择结束时间");
//		}
		if (abroad.getStartDate() != null && abroad.getEndDate() != null && abroad.getStartDate().compareTo(abroad.getEndDate()) > 0){
			this.addFieldError("abroad.startDate", "出国（境）经历 —— 开始时间不得晚于结束时间");
		}
//		if (abroad.getCountryRegion() == null || abroad.getCountryRegion().trim().isEmpty()){
//			this.addFieldError("abroad.countryRegion", "出国（境）经历 —— 请填写国家或地区");
//		} else 
			if (abroad.getCountryRegion().trim().length() > 20){
			this.addFieldError("abroad.countryRegion", "出国（境）经历 —— 国家或地区名称过长，最长20字符");
		}
//		if (abroad.getWorkUnit() == null || abroad.getWorkUnit().trim().isEmpty()){
//			this.addFieldError("abroad.workUnit", "出国（境）经历 —— 请填写机构");
//		} else 
			if (abroad.getWorkUnit().trim().length() > 50){
			this.addFieldError("abroad.worUnit", "出国（境）经历 —— 机构名称过长，最长50字符");
		}
		if (abroad.getPurpose() != null && abroad.getPurpose().trim().length() > 200){
			this.addFieldError("abroad.purpose", "出国（境）经历 —— 目的描述过长，最长200字符");
		}
	}
	
	/**
	 * 校验人员的银行信息
	 */
	public void validateBankInfo(List<BankAccount> bankList, boolean inAdd) {
		for(BankAccount bankAccount : bankList){
//			if (inAdd && person.getIdcardNumber() != null && personService.checkIdcard(person.getIdcardNumber())){
//				return;
//			}
			if (bankAccount.getBankName() != null && bankAccount.getBankName().trim().length() > 50){
				this.addFieldError("bankAccount.bankName", "银行信息 —— 开户银行过长");
			}
//			if (bankAccount.getBankBranch() != null && bankAccount.getBankBranch().trim().length() > 50){
//				this.addFieldError("bankAccount.bankBranch", "银行信息 —— 银行支行过长");
//			}
			if (bankAccount.getBankCupNumber() != null && bankAccount.getBankCupNumber().trim().length() > 40){
				this.addFieldError("bankAccount.bankCupNumber", "银行信息 —— 银联行号过长");
			}
			if (bankAccount.getAccountName() != null && bankAccount.getAccountName().trim().length() > 50){
				this.addFieldError("bankAccount.accountName", "银行信息 —— 银行户名过长");
			}
			if (bankAccount.getAccountNumber() != null && bankAccount.getAccountNumber().trim().length() > 40){
				this.addFieldError("bankAccount.accountNumber", "银行信息 —— 银行账号过长");
			}
		}
	}
	
	public String toggleKey() {
		if (personId != null && (isKey == 1 || isKey == 0)) {
			Person person = (Person) dao.query(Person.class, personId);
			person.setIsKey(isKey);
			dao.modify(person);
		}
		return SUCCESS;
	}

	public void validateToggleKey() {
		if (personId == null || personId.isEmpty()) {
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_TOGGLE_KEY_NULL);
			this.addFieldError(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_TOGGLE_KEY_NULL);
		}
	}
	
	/**
	 * 修改教育背景,工作经历，出国经历等信息
	 * @param originPerson
	 */
	@SuppressWarnings("unchecked")
	protected void modifyInformation(Person originPerson) {
		Map paraMap = new HashMap();
		paraMap.put("personId", originPerson.getId());
		List result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		result.addAll(dao.query("select we from Work we where we.person.id = :personId", paraMap));
		result.addAll(dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap));
		for (Object entity : result) {
			dao.delete(entity);
		}

		if (ebs != null) {
			for (Education eb : ebs) {
				eb.setPerson(originPerson);
			}
			dao.add(ebs);
		}

		if (wes != null) {
			for (Work we : wes) {
				we.setPerson(originPerson);
			}
			dao.add(wes);
		}

		if (aes != null) {
			for (Abroad ae : aes) {
				ae.setPerson(originPerson);
			}
			dao.add(aes);
		}

	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != name && !name.isEmpty()){
			searchQuery.put("name", name);
		}
		if(null != gender && !gender.isEmpty()){
			searchQuery.put("gender", gender);
		}
		if (null != isKey ) {
			searchQuery.put("isKey", isKey);
		}
		if (null != age ) {
			searchQuery.put("age", age);
		}
		if(unitName != null && !unitName.isEmpty())	{
			searchQuery.put("unitName", unitName);
		}
		if(deptName != null && !deptName.isEmpty())	{
			searchQuery.put("deptName", deptName);
		}
		if(position != null && !position.isEmpty())	{
			searchQuery.put("position", position);
		}

		if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
			searchQuery.put("staffCardNumber", staffCardNumber);
		}
		
		if(specialityTitle != null && !specialityTitle.isEmpty())	{
			searchQuery.put("specialityTitle", specialityTitle);
		}
		if(disciplineType != null && !disciplineType.isEmpty())	{
			searchQuery.put("disciplineType", disciplineType);
		}
	}
	
	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}

	public void validateToView() {
		if (entityId == null || entityId.trim().isEmpty()) {// 查看officer ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看信息时校验ID不得为空
	 */
	public void validateView() {
		if (entityId == null || entityId.trim().isEmpty()) {// 查看部级管理员ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, RightInfo.ERROR_VIEW_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, RightInfo.ERROR_VIEW_NULL);
		}
	}
	
	/**
	 * 删除管理人员
	 * @return
	 */
	@Transactional
	public String delete() {
		for (String oid : entityIds) {
			if (!accountExtService.checkIfUnderControl(loginer, oid, idType(), false)) {
				jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
		}
		Integer personType = idType()>10 ?(idType()-9):1;
		int type = personService.deletePerson(personType,entityIds);
		if(type == 1){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_1);
			return INPUT;
		} else if(type == 2){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_2);
			return INPUT;
		} else if(type == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_3);
			return INPUT;
		} else if(type == 4){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_4);
			return INPUT;
		} else if(type == 5){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_5);
			return INPUT;
		} else if(type == 6){
			jsonMap.put(GlobalInfo.ERROR_INFO, PersonInfo.ERROR_PERSON_DELETE_CANNOT_6);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 人员初级检索条件
	 * 
	 * @return
	 */
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();

		simpleSearchBaseHql(hql,map);
		if (keyword != null && !keyword.trim().isEmpty()){
			// 处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = searchConditions();
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i) {
					hql.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag) {
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 人员高级检索
	 * @author 余潜玉
	 */
	@SuppressWarnings("deprecation")
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		simpleSearchBaseHql(hql, map);
		// 处理查询条件
		if(name != null && !name.isEmpty())	{
			name = name.toLowerCase();
			hql.append(" and  LOWER(p.name) like :name");
			map.put("name", "%" + name + "%");
		}
		if(gender != null && !"-1".equals(gender)){
			gender = gender.toLowerCase();
			hql.append(" and  LOWER(p.gender) like :gender");
			map.put("gender", "%" + gender + "%");
		}
		if(isKey == 0 || isKey == 1){
			hql.append(" and p.isKey = :isKey");
			map.put("isKey", isKey);
		}
		if(age != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Integer year = (new Date()).getYear();
			Integer year1 = year - age;
			Integer year2 = year - age - 1;
			Date date1 = new Date();
			Date date2 = new Date();
			date1.setYear(year1);
			date2.setYear(year2);
			String date1String = df.format(date1);
			String date2String = df.format(date2);
			System.out.println(date1String + " = 1 + 2 = " + date2String);
			hql.append(" and to_char(p.birthday, 'yyyy-MM-dd') <= :date1 and to_char(p.birthday, 'yyyy-MM-dd') >= :date2");
			map.put("date1", date1String);
			map.put("date2", date2String);
		}
		if(unitName != null && !unitName.isEmpty())	{
			unitName = unitName.toLowerCase();
			switch (idType()) {
			case 6:
			case 7:
			case 8:
				hql.append(" and  LOWER(ag.name) like :unitName");
				break;
			case 9:
			case 10:
				hql.append(" and  LOWER(u.name) like :unitName");
				break;
			case 11:
				hql.append(" and  LOWER(e.agencyName) like :unitName");
				break;
			case 12:
			case 13: 
				hql.append(" and  LOWER(u.name) like :unitName");
				break;
			default:
				break;
			}
			map.put("unitName", "%" + unitName + "%");
		}
		
		if(deptName != null && !deptName.isEmpty())	{
			deptName = deptName.toLowerCase();
			switch (idType()) {
			case 6:
			case 7:
			case 8:
				hql.append(" and  LOWER(ag.sname) like :deptName");
				break;
			case 9:
				hql.append(" and  LOWER(d.name) like :deptName");
				break;
			case 10:
				hql.append(" and  LOWER(i.name) like :deptName");
				break;
			case 11:
				hql.append(" and  LOWER(e.divisionName) like :deptName");
				break;
			case 12:
			case 13: 
				hql.append(" and LOWER(CONCAT(d.name, i.name)) like :deptName");
				break;
			default:
				break;
			}
			map.put("deptName", "%" + deptName + "%");
		}
		if(position != null && !position.isEmpty()){
			position = position.toLowerCase();
			switch (personType()) {
			case 1:  //管理人员
				hql.append(" and  LOWER(o.position) like :position");
				break;
			case 2:  //专家
				hql.append(" and  LOWER(e.position) like :position");
				break;
			case 3:  //教师
				hql.append(" and  LOWER(t.position) like :position");
				break;
			default:
				break;
			}
			map.put("position", "%" + position + "%");
		}
		if(staffCardNumber != null && !staffCardNumber.isEmpty())	{
			staffCardNumber = staffCardNumber.toLowerCase();
			hql.append(" and  LOWER(o.staffCardNumber) like :staffCardNumber");
			map.put("staffCardNumber", "%" + staffCardNumber + "%");
		}
		
		if(specialityTitle != null && !specialityTitle.isEmpty()){
			specialityTitle = specialityTitle.toLowerCase();
			hql.append(" and  LOWER(a.specialityTitle) like :specialityTitle");
			map.put("specialityTitle", "%" + specialityTitle + "%");
		}
		if(disciplineType != null && !disciplineType.isEmpty()){
			disciplineType = disciplineType.toLowerCase();
			hql.append(" and  LOWER(a.disciplineType) like :disciplineType");
			map.put("disciplineType", "%" + disciplineType + "%");
		}
		if(provName != null && !provName.isEmpty()){
			provName = provName.toLowerCase();
			hql.append(" and  LOWER(so.name) like :provName");
			map.put("provName", "%" + provName + "%");
		}
		
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	@Transactional
	public String add() {
		Person realPerson = personService.findPersonByIdcard(person.getIdcardNumber());
		if (realPerson != null) {
			return ERROR;//身份证被占用，直接报错，没必要继续让用户添加了-By suwb
		}
		person = personService.setBaseInfo(person);
		//处理照片
		String groupId = "photo_person_add";
		String savePath = (String) ApplicationContainer.sc.getAttribute("UserPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			person.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		person.setCreateDate(new Date());
		person.setCreateMode(1);
		dao.add(person);
		if(!addInner())
			return ERROR;
		//处理地址信息
		personService.setAddress(person, homeAddress, officeAddress);
		uploadService.flush(groupId);
		if(dmssService.getStatus()){
			String dfsId = personService.flushToDmss(person);
			person.setPhotoDfs(dfsId);
			dao.addOrModify(person);
		}
		return SUCCESS;
	}
	
	/**
	 * 查看详细信息
	 * 
	 * @return SUCCESS返回部级管理人员信息页面,INPUT返回信息提示页面
	 * @throws Exception 
	 */
	public String view() throws Exception {
		if (!loginer.getCurrentType().within(AccountType.EXPERT, AccountType.STUDENT)) {
			if (!accountExtService.checkIfUnderControl(loginer, entityId, idType(), true)) {
				return ERROR;
			}
		}
		viewInner();
		Person person = (Person) jsonMap.get("person");
		//当本地照片不存在，而云存储中又有时，从云存储下载至本地服务器
		if(person.getPhotoFile() != null && !person.getPhotoFile().isEmpty()) {
			InputStream is = ApplicationContainer.sc.getResourceAsStream(person.getPhotoFile());
			if(is == null) {
				if(person.getPhotoDfs() != null && !person.getPhotoDfs().isEmpty() && dmssService.getStatus()) {
					File photoFile = new File(ApplicationContainer.sc.getRealPath(person.getPhotoFile()));
					FileUtils.copyInputStreamToFile(dmssService.download(person.getPhotoDfs()), photoFile);
				}
			} else {
				is.close();
			}
		}
		List<String> passportids = personService.getPassportByPersonId(entityId, personType());
		List<String> acids = personService.getAccountByEntityId(entityId, personType());
		if (passportids.size() != 0) {//存在通行证那就一定存在账号
			Passport passport = dao.query(Passport.class, passportids.get(0));
			if(passport != null) {
				name = passport.getName();
			}
			jsonMap.put("passportId", passport.getId());
			jsonMap.put("accountName", name);
			if (!acids.isEmpty()) {
				account = (Account) dao.query(Account.class, acids.get(0));
				jsonMap.put("accountId", account.getId());
			} else {
				jsonMap.put("accountId", "");
			}
		} else {
			jsonMap.put("accountName", "");
			jsonMap.put("accountId", "");
			jsonMap.put("passportId", "");
		}
		Map paraMap = new HashMap();
		paraMap.put("personId", person.getId());
		List result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		jsonMap.put("person_education", result);
		result = dao.query("select we from Work we where we.person.id = :personId", paraMap);
		jsonMap.put("person_work", result);
		result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
		jsonMap.put("person_abroad", result);
		result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getHomeAddressIds());
		jsonMap.put("person_homeAddress", result);
		result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getOfficeAddressIds());
		jsonMap.put("person_officeAddress", result);
		return SUCCESS;
	}
	
	/**
	 * 跳转至修改页面
	 * @return
	 */
	public String toModify() {
		if (!accountExtService.checkIfUnderControl(loginer, entityId, idType(), true)) {
			return ERROR;
		}
		toModifyInner();
		Map paraMap = new HashMap();
		paraMap.put("personId", person.getId());
		List result = dao.query("select eb from Education eb where eb.person.id = :personId", paraMap);
		setEbs(result);

		result = dao.query("select we from Work we where we.person.id = :personId", paraMap);
		setWes(result);

		result = dao.query("select ae from Abroad ae where ae.person.id = :personId", paraMap);
		setAes(result);

		result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getHomeAddressIds());
		setHomeAddress(result);
		
		result = dao.query("select address from Address address where address.ids = ? order by address.sn asc ", person.getOfficeAddressIds());
		setOfficeAddress(result);
		
		//将已有的照片加入文件组，以在编辑页面显示
		String groupId = "photo_" + person.getId();
		uploadService.resetGroup(groupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(person.getPhotoFile());
		if (photoFileRealPath != null && new File(photoFileRealPath).exists()) {
			uploadService.addFile(groupId, new File(photoFileRealPath));
		} else if(person.getPhotoDfs() != null && !person.getPhotoDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
			try {
				InputStream downloadStream = dmssService.download(person.getPhotoDfs());
				String sessionId = ServletActionContext.getRequest().getSession().getId();
				File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
				dir.mkdirs();
				String fileName = person.getPhotoFile().substring(person.getPhotoFile().lastIndexOf("/") + 1);
				File downloadFile = new File(dir, fileName);
				FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
				uploadService.addFile(groupId, downloadFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
		
	/**
	 * 完成修改
	 */
	@Transactional
	public String modify() {
		if (!accountExtService.checkIfUnderControl(loginer, entityId, idType(), true)) {
			return ERROR;
		}	
		if(!modifyInner()){
			return ERROR;
		}
		//若姓名有变化，则维护项目成果奖励中的人员姓名冗余信息
		if (!originPerson.getName().equals(person.getName())){
			personService.updatePersonName(originPerson.getId(), person.getName());
		}
		
		if (!person.getIdcardNumber().equals(originPerson.getIdcardNumber()) && personService.checkIdcard(person.getIdcardNumber())) {
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
		person = personService.setBaseInfo(person);
		personService.modifyPerson(person, originPerson);
		modifyInformation(originPerson);
		//处理地址信息
		personService.resetAddress(originPerson, homeAddress, officeAddress);
		uploadService.flush(groupId);
		if(originPerson.getPhotoDfs() != null && !originPerson.getPhotoDfs().isEmpty() && dmssService.getStatus()) {
			dmssService.deleteFile(originPerson.getPhotoDfs());
		}
		if(dmssService.getStatus()){
			String dfsId = personService.flushToDmss(originPerson);
			originPerson.setPhotoDfs(dfsId);
			dao.modify(originPerson);
		}
		return SUCCESS;
	}
	
	public Integer personType(){
		return idType()>10 ?(idType()-9):1;
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public List getSexList() {
		return sexList;
	}
	public void setSexList(List sexList) {
		this.sexList = sexList;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public IUnitExtService getUnitExtService() {
		return unitExtService;
	}
	public void setUnitExtService(IUnitExtService unitExtService) {
		this.unitExtService = unitExtService;
	}
	public IPersonService getPersonService() {
		return personService;
	}
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}
	public IAccountExtService getAccountExtService() {
		return accountExtService;
	}
	public void setAccountExtService(IAccountExtService accountExtService) {
		this.accountExtService = accountExtService;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public Academic getAcademic() {
		return academic;
	}
	public void setAcademic(Academic academic) {
		this.academic = academic;
	}

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Integer getIsKey() {
		return isKey;
	}
	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}
	public InputValidate getInputValidate() {
		return inputValidate;
	}
	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public String[] getFileIds() {
		return fileIds;
	}
	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public String getDeptName() {
		return deptName;
	}
	public String getGender() {
		return gender;
	}
	public String getPosition() {
		return position;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getStaffCardNumber() {
		return staffCardNumber;
	}
	public void setStaffCardNumber(String staffCardNumber) {
		this.staffCardNumber = staffCardNumber;
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
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getCheckedIds() {
		return checkedIds;
	}
	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
	public Map<String, String> getOptionalPassportNames() {
		return optionalPassportNames;
	}
	public void setOptionalPassportNames(Map<String, String> optionalPassportNames) {
		this.optionalPassportNames = optionalPassportNames;
	}
	public String getSelectedAccountId() {
		return selectedAccountId;
	}
	public void setSelectedAccountId(String selectedAccountId) {
		this.selectedAccountId = selectedAccountId;
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
	public List<BankAccount> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankAccount> bankList) {
		this.bankList = bankList;
	}
	public List<Address> getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(List<Address> homeAddress) {
		this.homeAddress = homeAddress;
	}
	public List<Address> getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(List<Address> officeAddress) {
		this.officeAddress = officeAddress;
	}
}
