// ========================================================================
// 文件名：RoleRightService.java
//
// 文件说明：
//	 本文件主要实现角色与权限模块的功能接口的实现，本类继承BaseService。
//
// 历史记录：
// [日期]------[姓名]--[描述]
// 2009-12-02  雷达	   创建文件。
// ========================================================================
package csdc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	public Set<String> getUserRight(String userId) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select rit.name from Right rit where rit.id in (select distinct role_right.right.id from RoleRight role_right where role_right.role.id in (select user_role.role.id from UserRole user_role where user_role.user.id = :userId))");
		map.put("userId", userId);
		List list = this.getBaseDao().query(hql.toString(), map);
		Set<String> userRight = new HashSet<String>(list);
		return userRight;
	}

	// ==============================================================
	// 函数名：checkroleName
	// 函数描述：检测角色名称是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkRoleName(String roleName) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select role.id from Role role where role.name = :roleName");
		map.put("roleName", roleName);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}

	// ==============================================================
	// 函数名：checkrightName
	// 函数描述：检测权限名称是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkRightName(String rightName){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select right0.id from Right right0 where right0.name = :rightName");
		map.put("rightName", rightName);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}

	/**
	 * 添加角色，同时添加角色权限对应关系
	 * @param role角色对象
	 * @param rightIds权限ID集合
	 * @return roleid角色ID
	 */
	public String addRole(Role role, List<String> rightIds) {
		// 添加角色
		String roleid = (String) this.add(role);
		// 添加新分配的权限
		if (rightIds != null && !rightIds.isEmpty()) {
			for (int i = 0; i < rightIds.size(); i++) {
				RoleRight roleright = new RoleRight();
				role.setId(roleid);
				roleright.setRole(role);
				Right right = new Right();
				right.setId(rightIds.get(i));
				roleright.setRight(right);
				this.add(roleright);
			}
		}
		return roleid;
	}

	/**
	 * 修改角色，同时修改角色权限对应关系
	 * @param role角色对象
	 * @param rightIds权限ID集合
	 * @return roleid角色ID
	 */
	@SuppressWarnings("unchecked")
	public void modifyRole(Role role, List<String> rightIds) {
		this.modify(role);
		// 获取角色的权限信息
		List<String> rolerightId = new ArrayList();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select role_right.id from RoleRight role_right where role_right.role.id = :roleid");
		map.put("roleid", role.getId());
		rolerightId = this.query(hql.toString(), map);
		// 删除该角色已有权限
		if (rolerightId.size() > 0) {
			for (int i = 0; i < rolerightId.size(); i++) {
				this.delete(RoleRight.class, rolerightId.get(i));
			}
		}
		// 添加新分配的权限
		if (rightIds != null && !rightIds.isEmpty()) {
			for (int i = 0; i < rightIds.size(); i++) {
				RoleRight roleright = new RoleRight();
				roleright.setRole(role);
				Right right = new Right();
				right.setId(rightIds.get(i));
				roleright.setRight(right);
				this.add(roleright);
			}
		}
	}

	/**
	 * 群删角色
	 * @param roleIds角色ID集合
	 */
	public void deleteRole(List<String> roleIds) {
		if (roleIds != null && !roleIds.isEmpty()) {
			for (int i = 0; i < roleIds.size(); i++) {
				this.delete(Role.class, roleIds.get(i));
			}
		}
	}

	/**
	 * 给用户分配角色
	 * @param userId用户ID
	 * @param roleIds角色ID集合
	 */
	@SuppressWarnings("unchecked")
	public void modifyUserRole(String userId, List<String> roleIds) {
		// 根据用户ID找到其对象
		User user = (User) this.query(User.class, userId);
		// 根据用户ID找到用户角色对应表中的ID
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user_role.id from UserRole user_role where user_role.user.id = :userId");
		map.put("userId", userId);
		List<String> userroleid = this.query(hql.toString(), map);
		// 删除该用户已有角色
		if (userroleid != null && userroleid.size() > 0) {
			for (int i = 0; i < userroleid.size(); i++) {
				this.delete(UserRole.class, userroleid.get(i));
			}
		}
		// 添加新分配的角色
		if (roleIds != null && roleIds.size() > 0) {
			for (int i = 0; i < roleIds.size(); i++) {
				UserRole userrole = new UserRole();
				userrole.setUser(user);
				Role role = new Role();
				role.setId(roleIds.get(i));
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
	 * @return rightId权限ID
	 */
	public String addRight(Right right, String[] actionurlarray, String[] actiondesarray) {
		// 添加权限
		String rightId = (String) this.add(right);
		// 添加权限与url的对应关系
		if (actionurlarray != null && actiondesarray != null && actionurlarray.length == actiondesarray.length) {
			right.setId(rightId);
			for (int i=0; i<actionurlarray.length; i++){
				RightUrl right_action = new RightUrl();
				right_action.setActionurl(actionurlarray[i]);
				right_action.setDescription(actiondesarray[i]);
				right_action.setRight(right);
				this.add(right_action);
			}
		}
		return rightId;
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
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select right_action.id from RightUrl right_action where right_action.right.id = :rightId");
		map.put("rightId", right.getId());
		List<String> pageList = this.query(hql.toString(), map);
		// 删除原有action
		for (int i=0; i<pageList.size(); i++){
			this.delete(RightUrl.class, pageList.get(i));
		}
		// 添加新的action
		if (actionurlarray != null && actiondesarray != null && actionurlarray.length == actiondesarray.length) {
			for (int i=0; i<actionurlarray.length; i++){
				RightUrl right_action = new RightUrl();
				right_action.setRight(right);
				right_action.setActionurl(actionurlarray[i]);
				right_action.setDescription(actiondesarray[i]);
				this.add(right_action);
			}
		}
	}

	/**
	 * 群删权限
	 * @param rightIds权限ID集合
	 */
	public void deleteRight(List<String> rightIds) {
		if (rightIds != null && !rightIds.isEmpty()) {
			for (int i = 0; i < rightIds.size(); i++) {
				this.delete(Right.class, rightIds.get(i));
			}
		}
	}
}