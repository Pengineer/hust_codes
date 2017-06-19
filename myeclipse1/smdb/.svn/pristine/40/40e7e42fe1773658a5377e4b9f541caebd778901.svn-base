package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 周中坚
 */
public class Passport implements java.io.Serializable {

	private static final long serialVersionUID = -1551934492856403052L;
	private String id;// ID
	private String name;// 账号名
	private Integer status;// 通行证状态
	private String bindEmail;// 绑定的邮箱
	private String bindPhone;// 绑定的手机号
	private String password;// 密码
	private String passwordRetrieveCode;// 密码重置校验码
	private String emailVerifyCode;//邮箱校验码
	private String phoneVerifyCode;//手机校验码
	private String activateVerifyCode;//激活校验码
	private Date passwordRetrieveCodeStartDate;// 密码重置码生成时间
	private Date activateVerifyCodeStartDate;// 密码重置码生成时间
	private Integer passwordWarning;//是否提示修改密码
	private Integer emailVerified;//邮箱校验状态[1：已验证；0：未验证]
	private Integer phoneVerified;//手机校验状态[1：已验证；0：未验证]
	private Integer activateVerified;//账号激活状态[1：已验证；0：未验证]
	private String passwordQuestion;//密码问题
	private String passwordAnswer;//密码答案
	private Integer maxSession;// 最大使用数
	private String allowedIp;// 允许登录ip
	private String refusedIp;// 拒绝登录ip
	private String lastLoginIp;// 上次登录IP
	private Date lastLoginDate;// 上次登录时间
	private String lastLoginAccount;// 上次登录账号
	private Date startDate;// 开始（注册或创建）时间
	private Date expireDate;// 通行证有效期
	private Integer loginCount;// 登录次数
	private Set<Account> account;
	private Set<Log> log;

	
	/**
	 * 默认的构造方法
	 */
	public Passport() {
	}
	/**
	 *  
	 * @param id
	 */
	public Passport(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getName() {
		return name;
	}
	@JSON(serialize=false)
	public Set<Log> getLog() {
		return log;
	}
	public void setLog(Set<Log> log) {
		this.log = log;
	}
	public String getPassword() {
		return password;
	}
	public String getPasswordRetrieveCode() {
		return passwordRetrieveCode;
	}
	public Date getPasswordRetrieveCodeStartDate() {
		return passwordRetrieveCodeStartDate;
	}
	public Integer getMaxSession() {
		return maxSession;
	}
	public String getAllowedIp() {
		return allowedIp;
	}
	public String getRefusedIp() {
		return refusedIp;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public Integer getPasswordWarning() {
		return passwordWarning;
	}
	public String getPasswordQuestion() {
		return passwordQuestion;
	}
	public String getPasswordAnswer() {
		return passwordAnswer;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getBindEmail() {
		return bindEmail;
	}
	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
	}
	public String getEmailVerifyCode() {
		return emailVerifyCode;
	}
	public void setEmailVerifyCode(String emailVerifyCode) {
		this.emailVerifyCode = emailVerifyCode;
	}
	public String getPhoneVerifyCode() {
		return phoneVerifyCode;
	}
	public void setPhoneVerifyCode(String phoneVerifyCode) {
		this.phoneVerifyCode = phoneVerifyCode;
	}
	public Integer getEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}
	public Integer getPhoneVerified() {
		return phoneVerified;
	}
	public void setPhoneVerified(Integer phoneVerified) {
		this.phoneVerified = phoneVerified;
	}
	public String getBindPhone() {
		return bindPhone;
	}
	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPasswordRetrieveCode(String passwordRetrieveCode) {
		this.passwordRetrieveCode = passwordRetrieveCode;
	}
	public void setPasswordRetrieveCodeStartDate(Date passwordRetrieveCodeStartDate) {
		this.passwordRetrieveCodeStartDate = passwordRetrieveCodeStartDate;
	}
	public void setMaxSession(Integer maxSession) {
		this.maxSession = maxSession;
	}
	public void setAllowedIp(String allowedIp) {
		this.allowedIp = allowedIp;
	}
	public void setRefusedIp(String refusedIp) {
		this.refusedIp = refusedIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public void setPasswordWarning(Integer passwordWarning) {
		this.passwordWarning = passwordWarning;
	}
	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}
	public String getLastLoginAccount() {
		return lastLoginAccount;
	}
	public void setLastLoginAccount(String lastLoginAccount) {
		this.lastLoginAccount = lastLoginAccount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}
	@JSON(serialize=false)
	public Set<Account> getAccount() {
		return account;
	}
	public void setAccount(Set<Account> account) {
		this.account = account;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public  Integer getStatus(){
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JSON(format="yyyy-MM-dd hh:mm:ss")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public String getActivateVerifyCode() {
		return activateVerifyCode;
	}
	public void setActivateVerifyCode(String activateVerifyCode) {
		this.activateVerifyCode = activateVerifyCode;
	}
	public Date getActivateVerifyCodeStartDate() {
		return activateVerifyCodeStartDate;
	}
	public void setActivateVerifyCodeStartDate(Date activateVerifyCodeStartDate) {
		this.activateVerifyCodeStartDate = activateVerifyCodeStartDate;
	}
	public Integer getActivateVerified() {
		return activateVerified;
	}
	public void setActivateVerified(Integer activateVerified) {
		this.activateVerified = activateVerified;
	}
	
}
