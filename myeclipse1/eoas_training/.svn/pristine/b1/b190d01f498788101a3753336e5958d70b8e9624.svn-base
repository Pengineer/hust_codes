package csdc.action.oa;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.Department;
import csdc.bean.Person;
import csdc.bean.PersonRoleDepartment;
import csdc.bean.Role;
import csdc.bean.Staff;
import csdc.service.IAccountService;
import csdc.service.IBaseService;
import csdc.service.IPersonService;
import csdc.service.IStaffService;

@SuppressWarnings("unchecked")
public class PersonAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private IBaseService baseService;
	private IPersonService personService;
	private IAccountService accountService;
	private String departmentId;
	
	private IStaffService staffService;
	private String personId;
	private String id;
	private String birthplace;
	private Date birthday;
	private String personEmail;// person中的email
	private String englishName;
	private String ethnic;
	private String homeAddress;
	private String idCardNumber;
	private String membership;
	private String mobilePhone;
	private String note;
	private String officeAddress;
	private String officePhone;
	private String qq;
	private String realName;
	private String sex;
	private String status;
	private String photoFile;
	private String photoName;
	
	private String accountType,password;// account中的账号类型，密码
	private String email;// account账号email
	private String personid, banknum, staffnum;
	private Date intime;
	private Map jsonMap = new HashMap();
	private Person person;
	private Staff staff;
	private Account account;
	private List<Department> departments;
	private Department department;
	private Role role;
	private List<PersonRoleDepartment> personRoleDepartment;

	/** 至列表页面*/
	public String toList() throws Exception {
		return SUCCESS;
	}
	
	/** 列表显示 */
	public String list() throws Exception {
		ArrayList<Person> personList = new ArrayList<Person>();
		personList = (ArrayList<Person>) baseService.list(Person.class, null);
		List<Object[]> pList = new ArrayList<Object[]>();
		String[] item;
		for (Person person : personList) {
			item = new String[7];
			item[0] = person.getRealName();
			item[1] = person.getSex();
			if(null != person.getDepartment()) {
				Department department = (Department) baseService.load(Department.class, person.getDepartment().getId());
				item[2] = department.getName();
			} else {
				item[2] = "尚未填写部门信息";
			}
			item[3] = person.getStaffnum();
			item[4] = person.getMobilePhone();
			if(null != person.getIntime()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				item[5] = sdf.format(person.getIntime());
			} else {
				item[5] = "尚未入职";
			}
			item[6] = person.getId();
			pList.add(item);
		}
		jsonMap.put("aaData", pList);
		return SUCCESS;
	}


	/** 添加页面 */
	public String toAdd() throws Exception {
		departments = baseService.list(Department.class, null);
		return SUCCESS;
	}

	/** 添加 */
	public String add() throws Exception {
/*		Person person = new Person();
		person.setBirthday(birthday);
		person.setBirthplace(birthplace);
		person.setPersonEmail(personEmail);
		person.setEnglishName(englishName);
		person.setEthnic(ethnic);
		person.setHomeAddress(homeAddress);
		person.setIdCardNumber(idCardNumber);
		person.setMembership(membership);
		person.setMobilePhone(mobilePhone);
		person.setNote(note);
		person.setSex(sex);
		person.setOfficeAddress(officeAddress);
		person.setOfficePhone(officePhone);
		person.setQq(qq);
		person.setRealName(realName);
		person.setStatus(status);
		person.setPhotoFile(photoFile);
		person.setPhotoName(photoName);*/
		baseService.add(person);
		Map session = ActionContext.getContext().getSession();
		Account account = (Account) session.get("account");
		account.setBelongId(person.getId());
		baseService.modify(account);
		return SUCCESS;

	}

	/** 修改页面 */
	public String modifyUI() throws Exception {
		// 准备数据
		Person person = personService.selectById(id);
		ActionContext.getContext().getValueStack().push(person);
		return "editUI";
	}
	
	public String toModify() {
		try {
			person = (Person)baseService.load(Person.class, personId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 修改 */
	@SuppressWarnings("unused")
	public String modify() throws Exception {
/*		Person person = personService.selectById(id);
		person.setBirthday(birthday);
		person.setBirthplace(birthplace);
		person.setPersonEmail(personEmail);
		person.setEnglishName(englishName);
		person.setEthnic(ethnic);
		person.setHomeAddress(homeAddress);
		person.setIdCardNumber(idCardNumber);
		person.setMembership(membership);
		person.setMobilePhone(mobilePhone);
		person.setNote(note);
		person.setSex(sex);
		person.setOfficeAddress(officeAddress);
		person.setOfficePhone(officePhone);
		person.setQq(qq);
		person.setRealName(realName);
		person.setStatus(status);
		person.setPhotoFile(photoFile);
		person.setPhotoName(photoName);
		personService.modify(person);*/
		department = (Department) baseService.load(Department.class, departmentId);
		person.setDepartment(department);
		baseService.modify(person);
		return SUCCESS;
	}

	/** 删除 */
	public String delete() throws Exception {
		personService.delete(id);
		return "toList";
	}

	/** 插入Staff页面 */
	public String addStaffUI() throws Exception {
		Person person = personService.selectById(id);
		ActionContext.getContext().getValueStack().push(person);
		return "staffUI";
	}

	/** 插入一个Staff */
	public String addStaff() throws Exception {
		// Account account = new Account();
		// Staff staff = new Staff();
		// staff.setPersonid(id);
		// staff.setBanknum(staff.getBanknum());
		// staff.setIntime(staff.getIntime());
		// account.setBelongid(id);
		// account.setEmail(email);
		// account.setPassword(MD5.getMD5(account.getPassword()));
		// account.setAccounttype(account.getAccounttype());
		//
		// staffService.add(staff);
		// accountService.add(account);

		return "topersonAccountList";
	}

	@SuppressWarnings("rawtypes")
	public String view() {
		Map session = ActionContext.getContext().getSession();
		String personId = ((Account) session.get("account")).getBelongId();
		person = (Person) baseService.load(Person.class, personId);
		return SUCCESS;
	}
	// ---get & set---
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	
	public IPersonService getPersonService() {
		return personService;
	}

	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public IStaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(IStaffService staffService) {
		this.staffService = staffService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getBanknum() {
		return banknum;
	}

	public void setBanknum(String banknum) {
		this.banknum = banknum;
	}

	public String getStaffnum() {
		return staffnum;
	}

	public void setStaffnum(String staffnum) {
		this.staffnum = staffnum;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<PersonRoleDepartment> getPersonRoleDepartment() {
		return personRoleDepartment;
	}

	public void setPersonRoleDepartment(
			List<PersonRoleDepartment> personRoleDepartment) {
		this.personRoleDepartment = personRoleDepartment;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}
