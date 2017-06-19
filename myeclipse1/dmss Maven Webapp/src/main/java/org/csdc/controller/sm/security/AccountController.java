package org.csdc.controller.sm.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.domain.sm.security.AccountForm;
import org.csdc.model.Account;
import org.csdc.model.Category;
import org.csdc.model.Person;
import org.csdc.model.Right;
import org.csdc.model.Role;
import org.csdc.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账号管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping ("/sm/security/account")
public class AccountController extends BaseController{	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 账号列表
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_VIEW')")      
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,a.name,p.name,p.email,p.agency,p.duty,a.loginCount,a.lastLoginDate,a.status from Account a left join a.person p order by a.name,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("name","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,a.name,p.name,p.email,p.agency,p.duty,a.loginCount,a.lastLoginDate,a.status from Account a left join a.person p where LOWER(a.name) like :name or LOWER(p.name) like :name or LOWER(p.email) like :name or LOWER(p.duty) like :name or LOWER(p.agency) like :name order by a.name,a.id";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
    }
    
    /**
     * 添加一个账号
     * @param form 账号表单
     * @return
     */
    @RequestMapping(value="/add", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_ADD')")     
    public Object add(AccountForm form){
    	JsonData jsonData = new JsonData(true);
    	if(accountService.checkAccountName(form.getAccountName()))
    		jsonData.errors.put("accountName", "用户名已存在");
    	if(accountService.checkEmail(form.getEmail())){
    		jsonData.errors.put("email", "邮箱已存在");
    	}
    	if(accountService.checkIdCard(form.getIdCard())){
    		jsonData.errors.put("idCard", "身份证已存在");
    	}
		if(!jsonData.errors.isEmpty()) {
    		jsonData.success = false;
    	}else {
    		accountService.addOrModifyAccount(form); 		
    	}
    	return jsonData;
    }
    
    /**
     * 进入修改账号
     * @param id 要修改的账号ID
     * @return
     */
    @RequestMapping(value="/toModify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_MODIFY')")
    public Object toModify(@RequestParam String id){
    	Map jsonMap = new HashMap();    	
    	jsonMap.put("form", accountService.getAccountForm(id));
    	jsonMap.put("success", true);
    	return jsonMap;
    }

    /**
     * 修改账号
     * @param form 账号表单
     * @return
     */
    @RequestMapping(value="/modify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_MODIFY')")
    public Object modify(AccountForm form){
    	JsonData jsonData = new JsonData();
    	Account account = baseDao.query(Account.class,form.getId());   
    	Person person = baseDao.query(Person.class,account.getPerson().getId());
    	if(!account.getName().equals(form.getName())){
    		if(accountService.checkAccountName(form.getName()))
    			jsonData.errors.put("accountName", "用户名已存在");
    	}
    	if(!person.getEmail().equals(form.getEmail())){
    		if(accountService.checkEmail(form.getEmail()))
    			jsonData.errors.put("email", "邮箱已存在");
    	}
    	if(null!=person.getIdCard() && !person.getIdCard().equals(form.getIdCard())){
    		if(accountService.checkIdCard(form.getIdCard()))
    			jsonData.errors.put("idCard", "身份证号已存在");
    	}
    	if(jsonData.errors.isEmpty()){
    		accountService.addOrModifyAccount(form);
    	}
    	return jsonData;
    }
   
    /**
     * 删除一个账号
     * @param id 账号ID
     * @return
     */
    @RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_DELETE')")    
    public Object delete(@PathVariable String id){
    	JsonData jsonData = new JsonData();
    	accountService.deleteAccount(id);
    	return jsonData;
    }    

   
    /**
     * 角色选择列表
     */
    @RequestMapping(value="toAdd/roleList", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_ADD')")  
    public Object toAddRoleList(HttpServletRequest request){
    	Account account = getSessionAccount(request);
    	List<Role> roles = baseDao.query("from Role");
    	List<Object[]> list = new ArrayList<Object[]>();
    	for(Role role:roles){
    		Object[] o = new Object[4];
    		o[0] = role.getId();
    		o[1] = role.getName();
    		o[2] = role.getDescription();
    		o[3] = false;
    		list.add(o);
    	}
    	Map result = new HashMap();
		result.put("data", list);
		result.put("totalCount", list.size());
		return result;
    }
    
    /**
     * 修改页面角色选择列表
     * @param request
     * @param accountId
     * @return
     */
    @RequestMapping(value="toModify/roleList", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ACCOUNT_MODIFY')")  
    public Object toModifyRoleList(HttpServletRequest request,@RequestParam String accountId){
    	List list = accountService.getSelectedRoles(accountId);
    	Map result = new HashMap();
		result.put("data", list);
		result.put("totalCount", list.size());
		return result;
    }
    
    @RequestMapping(value="/assignRole", produces="application/json")
    @ResponseBody
    public void assignRole(@RequestParam String roleIdString,HttpServletRequest request){
    	Account account = accountService.getSessionAccount(request);
    	accountService.assignRole(account, roleIdString);
    }
    
    @RequestMapping(value="/getRights", produces="application/json")
    @ResponseBody
    public Object getRights(HttpServletRequest request){
    	Account account = getSessionAccount(request);
    	return accountService.getRightMapByAccountName(account.getName());
    }
    
}


