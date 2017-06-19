// ========================================================================
// 文件名: RoleAction.java
//
// 文件说明:
//	 本文件主要实现角色管理的功能，包括角色的添加、角色列表、角色信息的查看及
// 修改。主要用到service层的接口有IRoleRightService。各个action与页面的对应关
// 系查看struts-r.xml文件。
//
// 历史记录:
// 2010-03-09  龚凡						整理代码与right分离.
// ========================================================================

package csdc.action.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.action.BaseAction;
import csdc.bean.Right;
import csdc.bean.Role;
import csdc.bean.User;
import csdc.service.IRoleRightService;
import csdc.tool.info.GlobalInfo;

/**
 * 角色管理
 * @author 龚凡
 * @author fengcl
 * @version 1.0 2010.03.31
 * @version 1.1 2012.02.10
 */
@SuppressWarnings("unchecked")
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select r.id, r.name, r.description from Role r";
	private static final String PAGENAME = "rolePage";
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "r.id";//缓存id
	private static final String column[] = {
			"r.name",
			"r.description"
	};// 排序用的列
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private Role role;// 角色信息，修改角色信息
	private List<Role> roles, noroles;// 已分配角色，未分配角色
	private String userId, userName;// 用户ID，账号
	private List<String> roleIds, rightIds;// 已分配角色ID,已分配权限ID
	private int userstatus;// 用于分配角色后的跳转

	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		keyword = (keyword == null) ? "" : keyword.toLowerCase().trim();// 预处理关键字
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword != null && !keyword.isEmpty()) {
			hql.append(" where ");
			if (searchType == 1) {
				hql.append("LOWER(r.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {
				hql.append("LOWER(r.description) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else {
				hql.append("(LOWER(r.name) like :keyword or LOWER(r.description) like :keyword)");
				map.put("keyword", "%" + keyword + "%");
			}
		}
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			0,
			null,
			null
		};
	}
	
	/**
	 * 进入查看
	 * @return SUCCESS跳转查看页面
	 */
	public String toView(){
		return SUCCESS;
	}
	
	/**
	 * 校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要查看的角色");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择要查看的角色");
		}
	}
	
	/**
	 * 查看角色
	 * @return 跳转成功
	 */
	public String view(){
		// 获取角色基本信息
		role = (Role) rolerightservice.query(Role.class, entityId);
		if (role == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "不存在的角色");
			jsonMap.put(GlobalInfo.ERROR_INFO, "不存在的角色");
			return INPUT;
		}
		// 获取角色的权限信息
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from Right r where r.id in (select role_right.right.id from RoleRight role_right where role_right.role.id = :roleId) order by r.name asc");
		map.put("roleId", entityId);
		pageList = rolerightservice.query(hql.toString(), map);
		jsonMap.put("role", role);
		jsonMap.put("pageList", pageList);
		return SUCCESS;
	}
	
	/**
	 * 进入角色添加页面
	 * @return 跳转成功
	 */
	public String toAdd() {
		List<Right> rights = new ArrayList<Right>();
		List<Right> norights = rolerightservice.query("from Right r order by r.name asc");
		session.put("rights", rights);//已分配权限
		session.put("norights", norights);//未分配权限
		return SUCCESS;
	}

	/**
	 * 添加角色
	 * @return 跳转成功
	 */
	public String add() {
		// 检测角色名是否存在
		if (rolerightservice.checkRoleName(role.getName())) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "该角色名已存在");
			return INPUT;
		}
		entityId = rolerightservice.addRole(role, rightIds);
		session.remove("norights");
		session.remove("rights");
		return SUCCESS;
	}

	/**
	 * 添加角色校验
	 */
	public void validateAdd() {
		if (role.getName() == null || role.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色名称不得为空");
		}
		if (role.getDescription() == null || role.getDescription().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色描述不得为空");
		}
		if (rightIds == null || rightIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色权限不得为空");
		}
	}

	/**
	 * 进入角色修改页面
	 * @return 跳转成功
	 */
	public String toModify() {
		session.put("roleId", entityId);
		// 获取角色信息
		role = (Role) rolerightservice.query(Role.class, entityId);
		if (role == null) {
			request.setAttribute("tip", "不存在的角色");
			this.addFieldError(GlobalInfo.ERROR_INFO, "不存在的角色");
			return INPUT;
		}
		// 获取角色的权限信息
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from Right r where r.id in (select role_right.right.id from RoleRight role_right where role_right.role.id = :roleId) order by r.name asc");
		map.put("roleId", entityId);
		List<Right> rights  = rolerightservice.query(hql.toString(), map);
		// 根据角色ID找到不属于其的权限
		StringBuffer hql1 = new StringBuffer();
		Map map1 = new HashMap();
		hql1.append("from Right r where r.id not in (select role_right.right.id from RoleRight role_right where role_right.role.id = :roleId) order by r.name asc");
		map1.put("roleId", entityId);
		List<Right> norights = rolerightservice.query(hql1.toString(), map);
		session.put("norights", norights);
		session.put("rights", rights);
		return SUCCESS;
	}

	/**
	 * 修改加载校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要修改的角色");
		}
	}

	/**
	 * 修改角色
	 * @return 跳转成功
	 */
	public String modify() {
		entityId = (String) session.get("roleId");
		// 修改角色基本信息
		Role mrole = (Role) rolerightservice.query(Role.class, entityId);
		if (mrole == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "不存在的角色");
			return INPUT;
		}
		if (!mrole.getName().equals(role.getName())) {
			// 检测角色名是否存在
			if (rolerightservice.checkRoleName(role.getName())) {
				this.addFieldError(GlobalInfo.ERROR_INFO, "该角色名已存在");
				return INPUT;
			}
		}
		mrole.setName(role.getName());
		mrole.setDescription(role.getDescription());
		rolerightservice.modifyRole(mrole, rightIds);
		session.remove("norights");
		session.remove("rights");
		return SUCCESS;
	}

	/**
	 * 修改角色校验
	 */
	public void validateModify() {
		if (role.getName() == null || role.getName().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色名称不得为空");
		}
		if (role.getDescription() == null || role.getDescription().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色描述不得为空");
		}
		if (rightIds == null || rightIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "角色权限不得为空");
		}
	}

	/**
	 * 删除角色
	 * @return 跳转成功
	 */
	public String delete() {
		try {
			rolerightservice.deleteRole(entityIds);
			return SUCCESS;
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "有角色不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择角色");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择角色");
		}
	}
	
	/**
	 * 进入给用户分配角色页面
	 * @return 跳转成功
	 */
	public String viewUserRole() {
		// 根据用户ID找到用户名称
		userId = userId.trim();
		User user= (User) baseService.query(User.class, userId);
		userName = (user.getChinesename() != null && !user.getChinesename().equals("")) ? user.getChinesename() : user.getUsername();
		// 根据用户ID找到其角色ID
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("from Role role where role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = :userId) order by role.name asc");
		map.put("userId", userId);
		roles = rolerightservice.query(hql.toString(), map);
		session.put("roles", roles);
		// 根据用户ID找到不属于其的角色
		StringBuffer hql1 = new StringBuffer();
		Map map1 = new HashMap();
		hql1.append("from Role role where role.id not in (select user_role.role.id from UserRole user_role where user_role.user.id = :userId) order by role.name asc");
		map1.put("userId", userId);
		noroles = rolerightservice.query(hql1.toString(), map1);
		session.put("noroles", noroles);
		return SUCCESS;
	}

	/**
	 * 查看用户角色校验
	 */
	public void validateViewUserRole() {
		if (userId == null || userId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择用户");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择用户");
		}
	}

	/**
	 * 给用户分配角色
	 * @return 跳转成功
	 */
	public String userRole() {
		rolerightservice.modifyUserRole(userId, roleIds);
		return SUCCESS;
	}

	/**
	 * 分配角色校验
	 */
	public void validateUserRole() {
		if (userId == null || userId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择用户");
		}
		if (roleIds == null || roleIds.isEmpty() || roleIds.contains("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "已分配角色不得为空");
		}
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public List<String> getRightIds() {
		return rightIds;
	}
	public void setRightIds(List<String> rightIds) {
		this.rightIds = rightIds;
	}
	public int getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setRolerightservice(IRoleRightService rolerightservice) {
		this.rolerightservice = rolerightservice;
	}

	@Override
	public Object[] advSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
}