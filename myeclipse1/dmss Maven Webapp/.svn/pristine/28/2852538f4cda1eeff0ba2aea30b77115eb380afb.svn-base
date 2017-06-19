/**
 * 
 */
package org.csdc.domain;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.model.Account;
import org.csdc.service.imp.AccountService;

/**
 * 密码重置表单
 * @author jintf
 * @date 2014-6-15
 */
public class ResetPasswordForm {

	private String oldPassword;
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public  Map validate(Account account,AccountService accountService,HttpServletRequest request){
		Map validateErrorMap=new HashMap();
		boolean isPasswordError = accountService.checkPassword(account,oldPassword);
		if(isPasswordError){
			validateErrorMap.put("error","原始密码错误");
		}
		
		return validateErrorMap;
	}
}
