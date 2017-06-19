// ========================================================================
// 文件名: RoleAction.java
//
// 文件说明:
//     本文件主要实现角色管理的功能，包括角色的添加、角色列表、角色信息的查看及
// 修改。主要用到service层的接口有IRoleRightService。各个action与页面的对应关
// 系查看struts-role.xml文件。
//
// 历史记录:
// 2010-03-09  龚凡                        整理代码与right分离.
// ========================================================================

package csdc.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Right;
import csdc.bean.Role;
import csdc.service.IRoleRightService;
import csdc.tool.Pager;

/**
 * 角色管理
 * @author 龚凡
 * @version 1.0 2010.03.31
 */
public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select role.id, role.name, role.description from Role role";
	private static final String column[] = {
			"role.name",
			"role.description"
	};// 排序用的列
	private IRoleRightService rolerightservice;// 角色与权限管理模块接口
	private Role role;// 角色信息，修改角色信息
	private List<Role> roles, noroles;// 已分配角色，未分配角色
	private String userid, username;// 用户ID，账号
	private List<String> roleids, rightids;// 已分配角色ID,已分配权限ID
	private String roleid;// 用于检索，提示信息等
	private int userstatus;// 用于分配角色后的跳转

	/**
	 * 角色列表
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String listRole() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		Pager page = (Pager) session.get("rolePage");
		String hql = HQL;
		if (page == null) {
			hql += " order by role.name asc, role.id asc";
			page = new Pager(rolerightservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		} else {
			// 判断是否由左侧菜单进入
			if (listLabel == 1) {
				hql += page.getHql().substring(page.getHql().indexOf(" order by "));
				page = new Pager(page.getTotalRows(), Integer.parseInt(DisplayNumberEachPage), hql);
			}
			if (pageNumber != 0) {
				page.setCurrentPage(pageNumber);
			}
		}
		pageList = rolerightservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		session.put("rolePage", page);
		this.initPageBuffer("rolePage", null);
		return SUCCESS;
	}

	/**
	 * 初级检索
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String simpleSearch() {
		ActionContext context = ActionContext.getContext();
		Map application = context.getApplication();
		Map session = context.getSession();
		String DisplayNumberEachPage = (String) application.get("DisplayNumberEachPage");
		keyword = keyword.toLowerCase();
		String hql = HQL;
		if (keyword != null && !keyword.isEmpty()) {
			hql += " where ";
			if (search_type == 1) {
				hql += "LOWER(role.name) like '%" + keyword + "%'";
			} else if (search_type == 2) {
				hql += "LOWER(role.description) like '%" + keyword + "%'";
			} else {
				hql += "(LOWER(role.name) like '%" + keyword + "%' or LOWER(role.description) like '%" + keyword + "%')";
			}
		}
		//System.out.println(hql);
		Pager page = (Pager) session.get("rolePage");
		if (page != null) {
			hql += page.getHql().substring(page.getHql().indexOf(" order by "));
		} else {
			hql += " order by role.name asc, role.id asc";
		}
		page = new Pager(rolerightservice.count(hql), Integer.parseInt(DisplayNumberEachPage), hql);
		session.put("rolePage", page);
		pageList = rolerightservice.list(page.getHql(), page.getStartRow(), page.getPageSize());
		this.initPageBuffer("rolePage", null);
		return SUCCESS;
	}

	/**
	 * 排序
	 * @return 列表页面
	 */
	public String sortRole() {
		if(ActionContext.getContext().getSession().get("rolePage") == null)
			listRole();
		this.sort("rolePage", column[columnLabel]);
		this.initPageBuffer("rolePage", null);
		return SUCCESS;
	}

	/**
	 * 不在有效范围，统一设为0
	 */
	public void validateSortRole() {
		if (columnLabel < 0 || columnLabel > column.length - 1) {
			columnLabel  = 0;
		}
	}

	/**
	 * 上一条
	 */
	public String prevRole() {
		if(ActionContext.getContext().getSession().get("rolePage") == null)
			listRole();
		roleid = this.prevRecord("rolePage", roleid);
		if (roleid == null || roleid.isEmpty()) {
			request.setAttribute("tip", "无效的角色");
		}
		return SUCCESS;
	}

	/**
	 * 上一条校验
	 */
	public void validatePrevRole() {
		if (roleid == null || roleid.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 下一条
	 */
	public String nextRole() {
		if(ActionContext.getContext().getSession().get("rolePage") == null)
			listRole();
		roleid = this.nextRecord("rolePage", roleid);
		if (roleid == null || roleid.isEmpty()) {
			request.setAttribute("tip", "无效的角色");
		}
		return SUCCESS;
	}

	/**
	 * 下一条校验
	 */
	public void validateNextRole() {
		if (roleid == null || roleid.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 进入角色添加页面
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String toAddRole() {
		List<Right> norights = rolerightservice.query("from Right right0 order by right0.name asc");
		Map session = ActionContext.getContext().getSession();
		session.put("norights", norights);
		session.put("rights", pageList);
		return SUCCESS;
	}

	/**
	 * 添加角色
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String addRole() {
		// 检测角色名是否存在
		if (rolerightservice.checkRolename(role.getName())) {
			request.setAttribute("tip", "该角色名已存在");
			return INPUT;
		}
		roleid = rolerightservice.addRole(role, rightids);
		Map session = ActionContext.getContext().getSession();
		session.remove("norights");
		session.remove("rights");
		this.refreshPager("rolePage");
		return SUCCESS;
	}

	/**
	 * 添加角色校验
	 */
	public void validateAddRole() {
		if (role.getName() == null || role.getName().isEmpty()) {
			this.addFieldError("knowError", "角色名称不得为空");
		}
		if (role.getDescription() == null || role.getDescription().isEmpty()) {
			this.addFieldError("knowError", "角色描述不得为空");
		}
		if (rightids == null || rightids.isEmpty()) {
			this.addFieldError("knowError", "角色权限不得为空");
		}
	}

	/**
	 * 删除角色
	 * @return 跳转成功
	 */
	public String deleteRole() {
		try {
			rolerightservice.delete(Role.class, roleid);
			this.refreshPager("rolePage");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("tip", "该角色不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDeleteRole() {
		if (roleid == null || roleid.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 群删角色
	 * @return 跳转成功
	 */
	public String groupDeleteRoles() {
		try {
			rolerightservice.deleteRole(roleids);
			this.refreshPager("rolePage");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("tip", "有角色不存在");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateGroupDeleteRoles() {
		if (roleids == null || roleids.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 查看角色
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String viewRole() {
		// 获取角色基本信息
		role = (Role) rolerightservice.query(Role.class, roleid);
		if (role == null) {
			request.setAttribute("tip", "不存在的角色");
			return INPUT;
		}
		// 获取角色的权限信息
		pageList = rolerightservice.query("from Right right0 where right0.id in (select role_right.right.id from RoleRight role_right where role_right.role.id = '"
			+ roleid + "') order by right0.name asc");
		return SUCCESS;
	}

	/**
	 * 查看校验
	 */
	public void validateViewRole() {
		if (roleid == null || roleid.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 进入角色修改页面
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String loadRole() {
		// 获取角色信息
		role = (Role) rolerightservice.query(Role.class, roleid);
		if (role == null) {
			request.setAttribute("tip", "不存在的角色");
			return INPUT;
		}
		// 获取角色的权限信息
		pageList = rolerightservice.query("from Right right0 where right0.id in (select role_right.right.id from RoleRight role_right where role_right.role.id = '"
			+ roleid + "') order by right0.name asc");
		// 根据角色ID找到不属于其的权限
		List<Right> norights = rolerightservice.query("from Right right0 where right0.id not in (select role_right.right.id from RoleRight role_right where role_right.role.id = '"
						+ roleid + "') order by right0.name asc");
		Map session = ActionContext.getContext().getSession();
		session.put("norights", norights);
		session.put("rights", pageList);
		return SUCCESS;
	}

	/**
	 * 加载校验
	 */
	public void validateLoadRole() {
		if (roleid == null || roleid.isEmpty()) {
			this.addFieldError("knowError", "请选择角色");
		}
	}

	/**
	 * 修改角色
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String modifyRole() {
		// 修改角色基本信息
		Role mrole = (Role) rolerightservice.query(Role.class, roleid);
		if (mrole == null) {
			request.setAttribute("tip", "不存在的角色");
			return INPUT;
		}
		if (!mrole.getName().equals(role.getName())) {
			// 检测角色名是否存在
			if (rolerightservice.checkRolename(role.getName())) {
				request.setAttribute("tip", "该角色名已存在");
				return INPUT;
			}
		}
		mrole.setName(role.getName());
		mrole.setDescription(role.getDescription());
		rolerightservice.modifyRole(mrole, rightids);
		Map session = ActionContext.getContext().getSession();
		session.remove("norights");
		session.remove("rights");
		return SUCCESS;
	}

	/**
	 * 修改角色校验
	 */
	public void validateModifyRole() {
		if (role.getName() == null || role.getName().isEmpty()) {
			this.addFieldError("knowError", "角色名称不得为空");
		}
		if (role.getDescription() == null || role.getDescription().isEmpty()) {
			this.addFieldError("knowError", "角色描述不得为空");
		}
		if (rightids == null || rightids.isEmpty()) {
			this.addFieldError("knowError", "角色权限不得为空");
		}
	}

	/**
	 * 进入给用户分配角色页面
	 * @return 跳转成功
	 */
	@SuppressWarnings("unchecked")
	public String viewUserRole() {
		// 根据用户ID找到其角色ID
		roles = rolerightservice.query("from Role role where role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = '"
				+ userid + "') order by role.name asc");
		// 根据用户ID找到不属于其的角色
		noroles = rolerightservice.query("from Role role where role.id not in (select user_role.role.id from UserRole user_role where user_role.user.id = '"
				+ userid + "') order by role.name asc");
		return SUCCESS;
	}

	/**
	 * 查看用户角色校验
	 */
	public void validateViewUserRole() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "请选择用户");
		}
	}

	/**
	 * 给用户分配角色
	 * @return 跳转成功
	 */
	public String userRole() {
		rolerightservice.modifyUserRole(userid, roleids);
		return SUCCESS;
	}

	/**
	 * 分配角色校验
	 */
	public void validateUserRole() {
		if (userid == null || userid.isEmpty()) {
			this.addFieldError("knowError", "请选择用户");
		}
		if (roleids == null || roleids.isEmpty()) {
			this.addFieldError("knowError", "角色不得为空");
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoleids() {
		return roleids;
	}
	public void setRoleids(List<String> roleids) {
		this.roleids = roleids;
	}
	public List<String> getRightids() {
		return rightids;
	}
	public void setRightids(List<String> rightids) {
		this.rightids = rightids;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
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
}