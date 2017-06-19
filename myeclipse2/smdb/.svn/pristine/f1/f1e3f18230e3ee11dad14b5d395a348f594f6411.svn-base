package csdc.tool.bean;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;

/**
 * 保存通过验证的账号相关信息
 * @author 龚凡
 */
public class LoginInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7320679898332785351L;
	private Passport passport;// 通行证
	private Account account;// 账号信息
	private AccountType currentType;// 当前账号级别，后台使用
	private String currentTypeName;// 当前账号类型中文名称
	private int isPrincipal;// 当前账号类别，后台使用
	private int isSuperUser;// 记录登录账号是否系统管理员，供切换账号使用（切换账号之后保留切换回来的入口）
	private String currentAccountId;// 当前账号ID
	private String currentBelongUnitId;// 当前账号所属单位ID，后台使用
	private String currentBelongUnitName;// 当前账号所属单位名称
	private String currentPersonName;// 当前账号所属人员名称
	
//	private String currentBelongId;// 当前账号所属(直接从账号中读取的数据)，后台使用
	private Person person;//系统管理员、内部专家(教师、学生)、外部专家账号所属ID
	private Officer officer;//管理人员账号所属ID
	
	private Agency agency;//部级、省级、高校主账号所属ID
	private Department department;//院系主账号所属ID
	private Institute institute;//研究基地主账号所属ID
	

	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public AccountType getCurrentType() {
		return currentType;
	}
	public void setCurrentType(AccountType currentType) {
		this.currentType = currentType;
	}
	public int getIsPrincipal() {
		return isPrincipal;
	}
	public void setIsPrincipal(int isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
	public String getCurrentAccountId() {
		return currentAccountId;
	}
	public void setCurrentAccountId(String currentAccountId) {
		this.currentAccountId = currentAccountId;
	}
	public String getCurrentBelongUnitId() {
		return currentBelongUnitId;
	}
	public void setCurrentBelongUnitId(String currentBelongUnitId) {
		this.currentBelongUnitId = currentBelongUnitId;
	}
	public String getCurrentBelongUnitName() {
		return currentBelongUnitName;
	}
	public void setCurrentBelongUnitName(String currentBelongUnitName) {
		this.currentBelongUnitName = currentBelongUnitName;
	}
	public String getCurrentPersonName() {
		return currentPersonName;
	}
	public void setCurrentPersonName(String currentPersonName) {
		this.currentPersonName = currentPersonName;
	}
	
	public String getCurrentTypeName() {
		return currentTypeName;
	}
	public void setCurrentTypeName(String currentTypeName) {
		this.currentTypeName = currentTypeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getIsSuperUser() {
		return isSuperUser;
	}
	public void setIsSuperUser(int isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Officer getOfficer() {
		return officer;
	}
	public void setOfficer(Officer officer) {
		this.officer = officer;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
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
}