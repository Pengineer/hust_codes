package org.csdc.controller.sm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;


import org.csdc.bean.JsonData;
import org.csdc.bean.GlobalInfo;
import org.csdc.controller.BaseController;
import org.csdc.domain.sm.SelfspaceModifyForm;
import org.csdc.model.Account;
import org.csdc.model.Person;
import org.csdc.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人中心管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/selfspace")
public class SelfspaceController extends BaseController {

	@Autowired
	private AccountService accountService;
	

	/**
	 * 进入修改个人信息页面
	 * @param request
	 * @param selfspaceModifyForm 个人中心修改表单
	 * @return
	 */
	@RequestMapping(value="/toModify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SELFSPACE_MODIFY')")    
    public Object toModify(HttpServletRequest request,SelfspaceModifyForm selfspaceModifyForm){
    	Map jsonMap = new HashMap();
		Account account = (Account) request.getSession().getAttribute(GlobalInfo.ACCOUNT_BUFFER);
    	Map validateErrorMap = selfspaceModifyForm.validate(account,accountService,request);
		if(!validateErrorMap.isEmpty()) {
    		jsonMap.put("validateErrors", validateErrorMap);
    	}else {
    		Map map = new HashMap();
    		map.put("belongId", account.getPerson().getId());
    		Person person = (Person) baseDao.query("select p from Person p where p.id = :belongId", map).get(0);
    		JsonConfig jsonConfig = new JsonConfig();  
        	jsonConfig.setExcludes( new String[]{ "account" } );   
    		jsonMap.put("person",new Object[]{JSONSerializer.toJSON(person,jsonConfig)});	//person必须放在最后
    		jsonMap.put("success", true);   		
    	}
    	return jsonMap;
    }
	

	
	/**
	 * 修改个人信息
	 * @param request
	 * @param selfspaceModifyForm 个人信息修改表单
	 * @return
	 */
	@RequestMapping(value="/modify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SELFSPACE_MODIFY')")
    public Object modify(HttpServletRequest request,SelfspaceModifyForm selfspaceModifyForm ){
    	JsonData jsonData = new JsonData();
		Account account = (Account) request.getSession().getAttribute(GlobalInfo.ACCOUNT_BUFFER);
		Map map = new HashMap();
		map.put("belongId", account.getPerson().getId());
		Person person = (Person) baseDao.query("select p from Person p where p.id = :belongId", map).get(0);
    	Map validateErrorMap = selfspaceModifyForm.validate(person,accountService,request);		
		if(!validateErrorMap.isEmpty()) {
			jsonData.success =false;
			jsonData.data.put("errors", validateErrorMap);
    	}else {
    		accountService.modifySelfspace(account,selfspaceModifyForm);
    		jsonData.success = true; 		
    	}
    	return jsonData;
    }  
	
	/**
	 * 密码修改
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/password/modify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SELFSPACE_PASSWORD')")
    public Object modifyPassword(@RequestParam String oldPassword,@RequestParam String newPassword,HttpServletRequest request){
		JsonData jsonData = new JsonData();
		Map validateErrorMap = new HashMap();
		Account account = (Account) request.getSession().getAttribute(AccountService.ACCOUNT_BUFFER);
		if(!accountService.checkPassword(account, oldPassword)){
			validateErrorMap.put("oldPassword_error", "原密码不正确");
		}
		if(!accountService.validatePassword(newPassword)){
			validateErrorMap.put("newPassword_error", "新密码不能为空或长度小于6位");
		}
		if(!validateErrorMap.isEmpty()) {
			jsonData.success =false;
			jsonData.data.put("errors", validateErrorMap);
    	}else {
    		accountService.modifyPassword(account, newPassword);
    		jsonData.success = true; 		
    	}
		return jsonData;
	}
}


