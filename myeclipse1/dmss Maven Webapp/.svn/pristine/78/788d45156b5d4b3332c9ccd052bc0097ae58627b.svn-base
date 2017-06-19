package org.csdc.domain.sm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.model.Account;
import org.csdc.model.Person;
import org.csdc.service.imp.AccountService;


/**
 * 个人空间修改表单
 * @author jintf
 * @date 2014-6-15
 */
public class SelfspaceModifyForm {
	private String name;
	private String mobilePhone;
	private String email;
	private String qq;
	private String agency;
	private String duty;
	private String idCard;
	private String securityQuestion;
	private String securityAnswer;
	
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
	
	public  Map validate(Account account,AccountService accountService,HttpServletRequest request){
		Map validateErrorMap=new HashMap();				
		if(account == null){
			validateErrorMap.put("sessionError", "session为空");
		}
		return validateErrorMap;
	}	


	public  Map validate(Person person,AccountService accountService,HttpServletRequest request){
		Map validateErrorMap=new HashMap();	
		if(null == email || email.isEmpty()){
			validateErrorMap.put("email_error","邮箱不能为空");
		}
		if(null == idCard || idCard.isEmpty()){
			validateErrorMap.put("idCard_error","身份证不能为空");
		}	
		if(!validateErrorMap.isEmpty())
			return validateErrorMap;
		
		boolean isIdCardExist = accountService.checkModifyIdCard(person,idCard);
		if(isIdCardExist){
			validateErrorMap.put("idCard_error", "身份证已注册");
		}				
		boolean isEmailExist = accountService.checkModifyEmail(person,email);
		if(isEmailExist){
			validateErrorMap.put("email_error", "邮箱已注册");
		}
		return validateErrorMap;
	}		
}

	


