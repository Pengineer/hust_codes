package csdc.bean;

import java.util.Date;

public class Account {
	private String id;
	private String name;
	private String email;// 注册帐号，即邮箱地址
	private String password;// 密码
	private Date registerDate;
	private Date lastLoginDate;
	private String lastLoginIp;
	private Integer loginCount;
	private int mailboxVerified;
	private String mailboxVerifyCode;
	private int isSuperUser;
	private String belongId;// 账号所属人员id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public int getMailboxVerified() {
		return mailboxVerified;
	}
	public void setMailboxVerified(int mailboxVerified) {
		this.mailboxVerified = mailboxVerified;
	}
	public String getMailboxVerifyCode() {
		return mailboxVerifyCode;
	}
	public void setMailboxVerifyCode(String mailboxVerifyCode) {
		this.mailboxVerifyCode = mailboxVerifyCode;
	}
	public int getIsSuperUser() {
		return isSuperUser;
	}
	public void setIsSuperUser(int isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	public String getBelongId() {
		return belongId;
	}
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}
}