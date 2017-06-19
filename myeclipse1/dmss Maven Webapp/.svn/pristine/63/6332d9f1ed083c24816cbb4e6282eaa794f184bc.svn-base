package org.csdc.domain.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.service.imp.AccountService;

/**
 * 注册表单
 * @author jintf
 * @date 2014-6-15
 */
public class RegisterForm {
	private String accountName;
	private String password;
	private String repassword;
	private String securityQuestion;
	private String securityAnswer;
	private String name;
	private String mobilePhone;
	private String email;
	private String qq;
	private String code;
	private String idCard;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @param accountService
	 * @param request
	 * @return "account name already registered" 1
	 * "mail box already registered" 2
	 * "idCard already exist" 3
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  Map validate(AccountService accountService,HttpServletRequest request){
		Map validateErrorMap=new HashMap();
		boolean isUsernameExist = accountService.checkAccountName(accountName);
		if(isUsernameExist){
			validateErrorMap.put("registerError", 1);
		}
		boolean isEmailExist = accountService.checkEmail(email);
		if(isEmailExist){
			validateErrorMap.put("registerError", 2);
		}
		boolean isIdCardExist = accountService.checkIdCard(idCard);
		if (isIdCardExist) {
			validateErrorMap.put("registerError", 3);
		}
		return validateErrorMap;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}


