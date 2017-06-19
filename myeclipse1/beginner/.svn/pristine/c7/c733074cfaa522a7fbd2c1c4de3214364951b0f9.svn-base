// ========================================================================
// 文件名：RoleRightService.java
//
// 文件说明：
//     本文件主要实现角色与权限模块的功能接口的实现，本类继承BaseService。
//
// 历史记录：
// [日期]------[姓名]--[描述]
// 2009-12-02  雷达       创建文件。
// ========================================================================
package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csdc.bean.Right;
import csdc.bean.RightUrl;
import csdc.bean.Role;
import csdc.bean.RoleRight;
import csdc.bean.User;
import csdc.bean.UserRole;
import csdc.service.IRoleRightService;

public class RoleRightService extends BaseService implements IRoleRightService{

	// ==============================================================
	// 函数名：getUserRight
	// 函数描述：获取用户权限的集合
	// 返回值：用户权限的集合
	// ==============================================================
	@SuppressWarnings("unchecked")
	public Set<String> getUserRight(String userid) {
		String hql = "select rit.name from Right rit where rit.id in (select distinct role_right.right.id from RoleRight role_right where role_right.role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = '" + userid + "'))";
		List list = this.getBaseDao().query(hql);
		Set<String> userRight = new HashSet<String>(list);
		return userRight;
	}

	// ==============================================================
	// 函数名：checkRolename
	// 函数描述：检测角色名称是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkRolename(String rolename) {
		List re = this.query("select role.id from Role role where role.name = '" + rolename + "'");
		if(re.size() == 0)
			return false;
		else 
			return true;
	}

	// ==============================================================
	// 函数名：checkRightname
	// 函数描述：检测权限名称是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkRightname(String rightname){
		List re = this.query("select right0.id from Right right0 where right0.name = '" + rightname + "'");
		if(re.size() == 0)
			return false;
		else 
			return true;
	}

	/**
	 * 添加角色，同时添加角色权限对应关系
	 * @param role角色对象
	 * @param rightids权限ID集合
	 * @return roleid角色ID
	 */
	public String addRole(Role role, List<String> rightids) {
		// 添加角色
		String roleid = (String) this.add(role);
		// 添加新分配的权限
		if (rightids != null && !rightids.isEmpty()) {
			RoleRight roleright = new RoleRight();
			role.setId(roleid);
			roleright.setRole(role);
			Right right = new Right();
			for (int i = 0; i < rightids.size(); i++) {
				right.setId(rightids.get(i));
				roleright.setRight(right);
				this.add(roleright);
			}
		}
		return roleid;
	}

	/**
	 * 修改角色，同时修改角色权限对应关系
	 * @param role角色对象
	 * @param rightids权限ID集合
	 * @return roleid角色ID
	 */
	@SuppressWarnings("unchecked")
	public void modifyRole(Role role, List<String> rightids) {
		this.modify(role);
		// 获取角色的权限信息
		List<String> rolerightid = new ArrayList();
		rolerightid = this.query("select role_right.id from RoleRight role_right where role_right.role.id = '" + role.getId() + "'");
		// 删除该角色已有权限
		if (rolerightid.size() > 0) {
			for (int i = 0; i < rolerightid.size(); i++) {
				this.delete(RoleRight.class, rolerightid.get(i));
			}
		}
		// 添加新分配的权限
		if (rightids != null && !rightids.isEmpty()) {
			RoleRight roleright = new RoleRight();
			roleright.setRole(role);
			Right right = new Right();
			for (int i = 0; i < rightids.size(); i++) {
				right.setId(rightids.get(i));
				roleright.setRight(right);
				this.add(roleright);
			}
		}
	}

	/**
	 * 群删角色
	 * @param roleids角色ID集合
	 */
	public void deleteRole(List<String> roleids) {
		if (roleids != null && !roleids.isEmpty()) {
			for (int i = 0; i < roleids.size(); i++) {
				this.delete(Role.class, roleids.get(i));
			}
		}
	}

	/**
	 * 给用户分配角色
	 * @param userid用户ID
	 * @param roleids角色ID集合
	 */
	@SuppressWarnings("unchecked")
	public void modifyUserRole(String userid, List<String> roleids) {
		// 根据用户ID找到其对象
		User user = (User) this.query(User.class, userid);
		// 根据用户ID找到用户角色对应表中的ID
		List<String> userroleid = this.query("select user_role.id from UserRole user_role where user_role.user.id = '" + userid + "'");
		// 删除该用户已有角色
		if (userroleid != null && userroleid.size() > 0) {
			for (int i = 0; i < userroleid.size(); i++) {
				this.delete(UserRole.class, userroleid.get(i));
			}
		}
		// 添加新分配的角色
		UserRole userrole = new UserRole();
		userrole.setUser(user);
		Role role = new Role();
		if (roleids != null && roleids.size() > 0) {
			for (int i = 0; i < roleids.size(); i++) {
				role.setId(roleids.get(i));
				userrole.setRole(role);
				this.add(userrole);
			}
		}
	}

	/**
	 * 添加权限，同时添加权限与action的对应关系
	 * @param right权限对象
	 * @param actionurlarrayURL集合
	 * @param actiondesarray描述集合
	 * @return rightid权限ID
	 */
	public String addRight(Right right, String[] actionurlarray, String[] actiondesarray) {
		// 添加权限
		String rightid = (String) this.add(right);
		// 添加权限与url的对应关系
		if (actionurlarray != null && actiondesarray != null && actionurlarray.length == actiondesarray.length) {
			RightUrl right_action = new RightUrl();
			right.setId(rightid);
			for (int i=0; i<actionurlarray.length; i++){
				right_action.setActionurl(actionurlarray[i]);
				right_action.setDescription(actiondesarray[i]);
				right_action.setRight(right);
				this.add(right_action);
			}
		}
		return rightid;
	}

	/**
	 * 修改权限，同时修改权限与url的对应关系
	 * @param right权限对象
	 * @param actionurlarrayURL集合
	 * @param actiondesarray描述集合
	 */
	@SuppressWarnings("unchecked")
	public void modifyRight(Right right, String[] actionurlarray, String[] actiondesarray) {
		this.modify(right);
		List<String> pageList = this.query("select right_action.id from RightUrl right_action where right_action.right.id = '" + right.getId() + "'");
		// 删除原有action
		for (int i=0; i<pageList.size(); i++){
			this.delete(RightUrl.class, pageList.get(i));
		}
		// 添加新的action
		if (actionurlarray != null && actiondesarray != null && actionurlarray.length == actiondesarray.length) {
			RightUrl right_action = new RightUrl();
			right_action.setRight(right);
			for (int i=0; i<actionurlarray.length; i++){
				right_action.setActionurl(actionurlarray[i]);
				right_action.setDescription(actiondesarray[i]);
				this.add(right_action);
			}
		}
	}

	/**
	 * 群删权限
	 * @param rightids权限ID集合
	 */
	public void deleteRight(List<String> rightids) {
		if (rightids != null && !rightids.isEmpty()) {
			for (int i = 0; i < rightids.size(); i++) {
				this.delete(Right.class, rightids.get(i));
			}
		}
	}
}