package csdc.bean;

import java.util.Date;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.struts2.json.annotations.JSON;

import csdc.tool.bean.AccountType;

/**
 * @author 龚凡
 */
public class Account implements java.io.Serializable {

	private static final long serialVersionUID = -1551934492791403052L;
	private String id;// ID
	@Enumerated(EnumType.STRING)
	private AccountType type;// 和原来数字类型的对应关系为：[1到10]对应[系统管理员, 部级, 省级, 部属高校, 地方高校, 高校院系, 研究基地, 外部专家, 教师, 学生]
	private int status;// 状态[0:停用;1:启用]
	private int isPrincipal;// 是否主账号，对于没有主子之分的账号（系统管理员、专家、教师、学生），统一设置为1。
							// 0: 否（子账号）
							// 1: 是（主账号）
	private int isLeapfrog;// 是否可跨级管理
							// 0: 否
							// 1: 是
	//账号所属
	//注意：研究人员在多个机构任职时，账号共用；管理人员在多个机构任职时，账号分开；当某人既是研究人员又是管理人员时，其研究人员与管理人员账号分开。
	private Person person;//系统管理员、研究人员（教师、学生、外部专家）账号所属人员ID
	private Officer officer;//管理人员账号所属管理人员ID，同时记录对应的人员ID
	private Agency agency;//部级、省级、高校主账号所属机构ID
	private Department department;//院系主账号所属院系ID，同时记录对用的机构ID
	private Institute institute;//研究基地主账号所属基地ID，同时记录对用的机构ID
	
	private Date startDate;// 开始（注册或创建）时间
	private Date expireDate;// 有效期
	private Date lastLoginDate;// 上次登录时间
	private String lastLoginSystem;// 上次登录系统
	private int loginCount;// 登录次数
	private int createType;//0:系统分配(默认) 1:用户注册
	private Passport passport;
	
	private Set<AccountRole> accountRole;
	private Set<Role> role;
	private Set<Mail> mail;
	private Set<News> news;
	private Set<Notice> notice;
	private Set<Notice> message;
	private Set<Log> log;

	/**
	 * 默认的构造方法
	 */
	public Account() {
	}

	/**
	 *  
	 * @param id
	 */
	public Account(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@JSON(serialize=false)
	public Set<AccountRole> getAccountRole() {
		return accountRole;
	}
	public void setAccountRole(Set<AccountRole> accountRole) {
		this.accountRole = accountRole;
	}
	public int getIsPrincipal() {
		return isPrincipal;
	}
	public void setIsPrincipal(int isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
	public int getIsLeapfrog() {
		return isLeapfrog;
	}
	public void setIsLeapfrog(int isLeapfrog) {
		this.isLeapfrog = isLeapfrog;
	}
	public String getLastLoginSystem() {
		return lastLoginSystem;
	}
	public void setLastLoginSystem(String lastLoginSystem) {
		this.lastLoginSystem = lastLoginSystem;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	@JSON(serialize=false)
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	@JSON(serialize=false)
	public Set<Mail> getMail() {
		return mail;
	}
	public void setMail(Set<Mail> mail) {
		this.mail = mail;
	}
	@JSON(serialize=false)
	public Set<News> getNews() {
		return news;
	}
	public void setNews(Set<News> news) {
		this.news = news;
	}
	@JSON(serialize=false)
	public Set<Notice> getNotice() {
		return notice;
	}
	public void setNotice(Set<Notice> notice) {
		this.notice = notice;
	}
	@JSON(serialize=false)
	public Set<Notice> getMessage() {
		return message;
	}
	public void setMessage(Set<Notice> message) {
		this.message = message;
	}
	@JSON(serialize=false)
	public Set<Log> getLog() {
		return log;
	}
	public void setLog(Set<Log> log) {
		this.log = log;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public int getCreateType() {
		return createType;
	}
	public void setCreateType(int createType) {
		this.createType = createType;
	}
	@JSON(serialize=false)
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	@JSON(serialize=false)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	@JSON(serialize=false)
	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}
	@JSON(serialize=false)
	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	@JSON(serialize=false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
}