package csdc.action.oa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Right;
import csdc.bean.Role;
import csdc.bean.Role_Right;
import csdc.service.IAccountService;
import csdc.service.IBaseService;
import csdc.service.IRoleService;



public class RoleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String TMP_NODE_VALUE = "nodeValue";// 创建权限树时，缓存的已选项
	private static final String TMP_ENTITY_ID = "roleId";// 用于session缓存实体的ID名称
	private IBaseService baseService;
	private IRoleService roleService;
	private IAccountService accountService;
	private String accountId;

	private String id;// 角色id
	private String name;// 角色名称
	private String description;// 角色描述
	private Role role;
	private String rightNodeValues;// 角色分配权限时，用于记录权限节点值
	private String[] newNode = null;// 权限节点数组化，可考虑由struts2的自定义类型转换功能实现
	private Map jsonMap = new HashMap();
	private String tip, roleId; // 用于检索，提示信息等
	private List<Right> rights;// 已分配权限
	private List<Role> roles, noroles;
	private List<String> rolesId, rightsId;// 已分配角色ID,已分配权限ID

	public List<String> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<String> rolesId) {
		this.rolesId = rolesId;
	}

	public List<String> getRightsId() {
		return rightsId;
	}

	public void setRightsId(List<String> rightsId) {
		this.rightsId = rightsId;
	}

	/** 列表 */
	public String toList() {
		return SUCCESS;
	}

	public String list() throws Exception {
		ArrayList<Role> roleList = new ArrayList<Role>();
		List<Object[]> rList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		roleList = (ArrayList<Role>) baseService.list(Role.class, null);
		String[] item;
		for (Role r : roleList) {
			item = new String[3];
			item[0] = r.getName();
			item[1] = r.getDescription();
			item[2] = r.getId();
			rList.add(item);
		}
		jsonMap.put("aaData", rList);
		return SUCCESS;
	}

	/** 添加页面 */
	public String toAdd() {
		return SUCCESS;
	}

	/** 添加 */
	public String add() {
		if (rightNodeValues != null && !rightNodeValues.isEmpty()) {// 分配的权限非空，则将其封装为数组
			rightNodeValues = rightNodeValues.trim();
			newNode = rightNodeValues.split(",");
		}
		// 检测角色名是否存在
		if (roleService.checkRolename(role.getName())) {
			this.setTip("该角色名已存在！");
			return INPUT;
		}

		// 添加角色
		roleId = roleService.addRole(role, newNode);
		accountService.synRoleToActiviti();
		return SUCCESS;
	}

	/** 修改页面 */
	public String toModify() throws Exception {
		role = (Role) baseService.load(Role.class,roleId);
		Map session = ActionContext.getContext().getSession();

		session.put("roleId", roleId);
		// 将角色ID和角色拥有的权限节点存入session
		session.put(TMP_ENTITY_ID, roleId);
		List<String> rightNodeValue = baseService.list(Right.class, session);
		session.put(TMP_NODE_VALUE, rightNodeValue);
		return SUCCESS;
	}

	/** 修改 */
	public String modify() throws Exception {
		if (rightNodeValues != null && !rightNodeValues.isEmpty()) {// 分配的权限非空，则将其封装为数组
			rightNodeValues = rightNodeValues.trim();
			newNode = rightNodeValues.split(",");
		}
		// 修改角色基本信息
		Role mrole = new Role();
		mrole = (Role)baseService.load(Role.class,role.getId());
		if (!mrole.getName().equals(role.getName())) {
			// 检测角色名是否存在
			if (roleService.checkRolename(role.getName())) {
				this.setTip("该角色名已存在！");
				return INPUT;
			}
		}
		
		// 添加角色
		roleId = roleService.modifyRole(mrole, role, newNode);// 修改角色信息
		// 删除SESSION中的备用信息
		Map session = ActionContext.getContext().getSession();
		session.remove(TMP_NODE_VALUE);
		session.remove(TMP_ENTITY_ID);
		return SUCCESS;
	}

	/** 删除 */
	public String delete() throws Exception {
		Map map = new HashedMap();
		map.put("roleId", roleId);
		List<Role_Right> rr = null;
		try {
			rr = baseService.list(Role_Right.class, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Role_Right roleRight : rr) {
			baseService.delete(Role_Right.class, roleRight.getId());
		}
		baseService.delete(Role.class, roleId);
		
		Role role = (Role) baseService.load(Role.class, roleId);
		if(null != role) {
			jsonMap.put("result", 0);
		} else {
			jsonMap.put("result", 1);
		}
		return SUCCESS;
	}

	/**
	 * 创建权限树
	 */
	public String createRightTree() {
		List<String> str = (List<String>) ActionContext.getContext()
				.getSession().get(TMP_NODE_VALUE);// 获取选中节点
		Document doc = roleService.createRightXML(roleId, str);
		String content = doc.asXML();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 使用utf-8
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(content); // 通过Io流写到页面上去了
		// 必须返回空
		return null;
	}

	public String view() {
		role = (Role)baseService.load(Role.class,roleId);
		Map map = new HashMap();
		map.put("roleId", roleId);
		rights = baseService.list(Right.class, map);
		return SUCCESS;
	}

	public String viewAccountRole() {
		Map map = new HashMap();
		map.put("accountId", accountId);
		roles = baseService.list(Role.class, map);
		noroles = baseService.list(Role.class.getName() + ".listRoleByNotAccount", map);
		return SUCCESS;
	}

	/**
	 * 给用户分配角色
	 * 
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String accountRole() {
		// 根据用户ID找到其对象
		Account account = new Account();
		account = (Account)baseService.load(Account.class,accountId);
		// 根据用户ID找到用户角色对应表中的ID
		List<AccountRole> accountRoles = new ArrayList();
		Map map = new HashMap();
		map.put("accountId", account.getId());
		accountRoles = baseService.list(AccountRole.class.getName() + ".listAccountRole", map);
		// 删除该用户已有角色
		if (accountRoles.size() > 0) {
			for (int i = 0; i < accountRoles.size(); i++) {
				baseService.delete(AccountRole.class,accountRoles.get(i).getId());
			}
		}
		// 添加新分配的角色
		AccountRole accountRole = new AccountRole();
		if (rolesId.size() > 0) {
			for (int i = 0; i < rolesId.size(); i++) {
				accountRole.setAccount(account);
				System.out.println(rolesId.get(0));
				role = (Role) baseService.load(Role.class, rolesId.get(i));
				accountRole.setRole(role);
				baseService.add(accountRole);
			}
		}
		
		List<Long> roleIds = new ArrayList<Long>();
		for(String roleId : rolesId) {
			  Long i = Long.parseLong(roleId);
			  try {
				roleIds.add(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			accountService.saveUser(account, roleIds, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	
	// ----get && set---
	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRightNodeValues() {
		return rightNodeValues;
	}

	public void setRightNodeValues(String rightNodeValues) {
		this.rightNodeValues = rightNodeValues;
	}

	public String[] getNewNode() {
		return newNode;
	}

	public void setNewNode(String[] newNode) {
		this.newNode = newNode;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getNoroles() {
		return noroles;
	}

	public void setNoroles(List<Role> noroles) {
		this.noroles = noroles;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public static String getTmpNodeValue() {
		return TMP_NODE_VALUE;
	}

	public static String getTmpEntityId() {
		return TMP_ENTITY_ID;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	
	
}