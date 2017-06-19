package org.csdc.controller.sm.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csdc.bean.GridModel;
import org.csdc.bean.JsonData;
import org.csdc.controller.BaseController;
import org.csdc.dao.IBaseDao;
import org.csdc.domain.sm.security.AddRoleForm;
import org.csdc.model.Category;
import org.csdc.model.Right;
import org.csdc.model.Role;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.RoleRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 角色管理控制器
 * @author jintf
 * @date 2014-6-15
 */
@Controller
@RequestMapping("/sm/security/role")
public class RoleController extends BaseController{
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private RoleRightService roleRightService;
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 角色列表显示
	 * @param request
	 * @return
	 */
    @RequestMapping(value="/list", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ROLE_VIEW')")	    
	public Object list(HttpServletRequest request){
    	Map paraMap = new HashMap();
    	String hql = "select a.id,a.name,a.description from Role a  order by a.name,a.id";
    	if(request.getParameter("search")!=null &&request.getParameter("search").length()>0){
    		paraMap.put("name","%"+ request.getParameter("search").toLowerCase()+"%");
    		hql = "select a.id,a.name,a.description from Role a where LOWER(a.name) like :name order by a.name,a.id ";
    	}
    	GridModel grid = new GridModel(request,baseDao, hql, paraMap);
    	return grid.getResults();
	}

    /**
     * 删除角色
     * @param id 角色ID
     * @return
     */
    @RequestMapping(value="/delete/{id}", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ROLE_DELETE')")    
    public Object delete(@PathVariable String id){
    	JsonData jsonData = new JsonData();
    	roleRightService.deleteRole(id);
    	return jsonData;
    }
    
    /**
     * 添加一个角色
     * @param form 角色表单
     * @return
     */
    @RequestMapping(value="/add", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ROLE_ADD')") 
    public Object add(AddRoleForm form){   	
    	JsonData jsonData = new JsonData();
    	boolean isExist = roleRightService.isExistRoleName(form.getName());
		if(isExist){
			jsonData.success = false;
			jsonData.errors.put("name","角色名已存在");
		}else{    	
    		roleRightService.addOrModifyRole(form);
    	}
    	return jsonData;
    }
    
    /**
     * 修改角色
     * @param form 角色表单
     * @return
     */
    @RequestMapping(value="/modify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ROLE_MODIFY')") 
    public Object modify(AddRoleForm form){   	
    	JsonData jsonData = new JsonData();
    	boolean isExist = false;
    	Role role = baseDao.query(Role.class,form.getId());   	
    	if(!role.getName().equals(form.getName())){
    		isExist = roleRightService.isExistRoleName(form.getName());
    	}
		if(isExist){
			jsonData.success = false;
			jsonData.errors.put("name","角色名已存在");
		}else{    	
			roleRightService.addOrModifyRole(form);
    	}
    	return jsonData;
    }
    
    /**
     * 进入修改角色
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping(value="/toModify", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_ROLE_MODIFY')") 
    public Object toModify(@RequestParam String roleId){
    	Map jsonMap = new HashMap();    	
    	jsonMap.put("role", roleRightService.getAddRoleForm(roleId));
    	jsonMap.put("success", true);
    	return jsonMap;
    }

    @RequestMapping(value="/toAdd/domainList", produces="application/json")
    @ResponseBody
	public Object toAddDomainList(HttpServletRequest request,HttpServletResponse response){    	
    	List<Category> domains = categoryService.getAllDomains();
    	List<Object[]> list = new ArrayList<Object[]>();
    	for(Category domain:domains){
    		Object[] o = new Object[4];
    		o[0] = domain.getId();
    		o[1] = domain.getName();
    		o[2] = domain.getDescription();
    		o[3] = false;
    		list.add(o);
    	}
    	Map result = new HashMap();
		result.put("data", list);
		result.put("totalCount", list.size());
		return result;
	}
    
    @RequestMapping(value="/toAdd/rightTree", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_RIGHT_VIEW')")     
	public Object toAddRightTree(HttpServletRequest request,HttpServletResponse response){    	
    	return roleRightService.createRightTree(new HashSet<Right>());	
	}
    
    @RequestMapping(value="/toModify/domainList", produces="application/json")
    @ResponseBody
	public Object toModifyDomainList(@RequestParam String roleId){    	
    	List list = roleRightService.getSelectedDomain(roleId);
    	Map result = new HashMap();
		result.put("data", list);
		result.put("totalCount", list.size());
		return result;
	}
    
    @RequestMapping(value="/toModify/rightTree", produces="application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SM_SECURITY_RIGHT_VIEW')")     
	public Object toModifyRightTree(@RequestParam String roleId){    	   	
    	return roleRightService.getSelectedRightTree(roleId);	
	}

}


