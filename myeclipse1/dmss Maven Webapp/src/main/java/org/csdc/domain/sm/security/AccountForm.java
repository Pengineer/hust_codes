package org.csdc.domain.sm.security;

/**
 * 账号表单
 * @author jintf
 * @date 2014-6-15
 */
public class AccountForm {
	private String id;
	private String accountName;
	private String startDate;
	private String expireDate;
	private Integer maxSession;
	private String lastLoginDate;
	private String lastLoginIp;
	private String allowedIp;
	private Integer status;
	private String refusedIp;
	private Integer loginCount;
	private String retrivePasswordStartDate;
	private Boolean passwordWarning;
	private String securityQuestion;
	private String securityAnswer;
	private String theme;
	
	private String personId;
	private String name;
	private String mobilePhone;
	private String email;
	private String agency;
	private String duty;
	private String idCard;
	private String qq;
	private String lastModifiedDate;
	
	private String roleIds;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getAllowedIp() {
		return allowedIp;
	}
	public void setAllowedIp(String allowedIp) {
		this.allowedIp = allowedIp;
	}
	
	public String getRefusedIp() {
		return refusedIp;
	}
	public void setRefusedIp(String refusedIp) {
		this.refusedIp = refusedIp;
	}
	
	public String getRetrivePasswordStartDate() {
		return retrivePasswordStartDate;
	}
	public void setRetrivePasswordStartDate(String retrivePasswordStartDate) {
		this.retrivePasswordStartDate = retrivePasswordStartDate;
	}
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Integer getMaxSession() {
		return maxSession;
	}
	public void setMaxSession(Integer maxSession) {
		this.maxSession = maxSession;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Boolean getPasswordWarning() {
		return passwordWarning;
	}
	public void setPasswordWarning(Boolean passwordWarning) {
		this.passwordWarning = passwordWarning;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
