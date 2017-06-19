package org.csdc.domain.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csdc.service.imp.AccountService;

/**
 * 登录表单
 * @author jintf
 * @date 2014-6-15
 */
public class LoginForm {
    private String j_username;
    private String j_password;
    
    public String getUsername() {
        return j_username;
    }
    public void setUsername(String username) {
        this.j_username = username;
    }
    public String getPassword() {
        return j_password;
    }
    public void setPassword(String password) {
        this.j_password = password;
    }
    
    public  Map validate(AccountService accountService,HttpServletRequest request){
    	Map validateErrorMap=new HashMap();
    	int status = accountService.getAccountAuthenticationStatus(request);
    	switch (status) {
		case 0:
			
			break;
		case 1:
			validateErrorMap.put("loginError", "用户名为空");
			break;
		case 2:
			validateErrorMap.put("loginError", "密码为空");
			break;
		case 3:
			validateErrorMap.put("loginError", "验证码为空");
			break;
		case 4:
			validateErrorMap.put("loginError", "验证码错误");
			break;
		case 5:
			validateErrorMap.put("loginError", "用户名或密码错误");
			break;
		case 6:
			validateErrorMap.put("loginError", "该用户已停用");
			break;
		case 7:
			validateErrorMap.put("loginError", "此账号限制了本地IP登录");
			break;
		case 8:
			validateErrorMap.put("loginError", "此账号已达到同时在线最大数，无法继续登录");
			break;
		default:
			break;
		}
    	return validateErrorMap;	
    }
    
}

