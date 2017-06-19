// ========================================================================
// 文件名：UserService.java
//
// 文件说明：
//	 本文件主要实现用户管理模块的功能接口的实现，本类继承BaseService。
//
// 历史记录：
// [日期]------[姓名]--[描述]
// 2009-12-02  雷达	   创建文件。
// ========================================================================
package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.Role;
import csdc.bean.User;
import csdc.bean.UserRole;
import csdc.service.IUserService;

public class UserService extends BaseService implements IUserService {
	
	// ==============================================================
	// 函数名：checkUsername
	// 函数描述：检测用户名是否存在
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkUsername(String username) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user.username from User user where user.username = :username");
		map.put("username", username);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}

	// ==============================================================
	// 函数名：checkEmail
	// 函数描述：检测邮箱是否已被注册
	// 返回值：true表示存在，false表示不存在。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkEmail(String email) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user.id from User user where user.mailboxverified = 1 and user.email = :email");
		map.put("email", email);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}

	// ==============================================================
	// 函数名：checkPassword
	// 函数描述：检测用户名和密码是否匹配
	// 返回值：true表示匹配，false表示不匹配。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkPassword(String username,String password){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user from User user where user.username = :username and user.password = :password");
		map.put("username", username);
		map.put("password", password);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}
	
	// ==============================================================
	// 函数名：checkIdcard
	// 函数描述：检测用户名和证件号是否匹配
	// 返回值：true表示匹配，false表示不匹配。
	// ==============================================================
	@SuppressWarnings("unchecked")
	public boolean checkIdcard(String username,String idcard){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user from User user where user.username = :username and user.idcardnumber = :idcard");
		map.put("username", username);
		map.put("idcard", idcard);
		List re = this.query(hql.toString(), map);
		return re.isEmpty() ? false : true;
	}

	// ==============================================================
	// 函数名：getByUsername
	// 函数描述：根据用户名查找用户
	// 返回值：返回用户对象
	// ==============================================================
	@SuppressWarnings("unchecked")
	public User getByUsername(String username) {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append("select user from User user where user.username = :username");
		map.put("username", username);
		return (User) this.query(hql.toString(), map).get(0);
	}

	/**
	 * 群删用户
	 * @param userIds待删除的用户ID集合
	 */
	public void deleteUsers(List<String> userIds) {
		for (int i = 0; i < userIds.size(); i++) {
			this.delete(User.class, userIds.get(i));
		}
	}

	/**
	 * 审批用户
	 * @param userids待审批的用户ID集合
	 * @param validity有效期
	 * @param userstatus区分是启用还是审批
	 * @param rolesid分配的角色ID集合
	 */
	public void enableGroupUser(List<String> userids, Date validity, int userstatus, List<String> rolesid) {
		Date approvetime = new Date();
		User user;
		for (int i = 0; i < userids.size(); i++) {
			user = (User) this.query(User.class, userids.get(i));
			// 设置账号状态为启用状态
			user.setUserstatus(1);
			// 设置账号处理时间
			user.setApprovetime(approvetime);
			// 设置账号有效期
			user.setExpiretime(validity);
			this.modify(user);
			// 若果是审批用户设置账号角色
			if (userstatus == 0){
				Role role;
				UserRole user_role = new UserRole();
				user_role.setUser(user);
				for (int j = 0; j < rolesid.size(); j++){
					role = (Role) this.query(Role.class, rolesid.get(j));
					user_role.setRole(role);
					this.add(user_role);
				}
			}
		}
	}

	/**
	 * 禁用用户
	 * @param userids待禁用的用户ID集合
	 */
	public void disableGroupUser(List<String> userids) {
		User user;
		for (int i = 0; i < userids.size(); i++) {
			user = (User) this.query(User.class, userids.get(i));
			user.setUserstatus(-1);
			this.modify(user);
		}
	}
}