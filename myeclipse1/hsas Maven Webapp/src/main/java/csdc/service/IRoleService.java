package csdc.service;

import java.util.List;

import org.dom4j.Document;

import csdc.model.Role;
import csdc.tool.bean.LoginInfo;


/**
 * 
 * 角色管理接口
 * @author huangxw
 *
 */
public interface IRoleService extends IBaseService{
	
	/**
	 * 判断角色名称是否存在
	 * @param roleName角色名称
	 * @return true存在，false不存在
	 */
	public boolean checkRoleName(String roleName);

	/**
	 * 添加角色
	 * @param role角色对象
	 * @param rightNodeValue角色权限节点
	 * @return roleId角色ID
	 */
	public String addRole(Role role, String[] rightNodeValue);

	/**
	 * 修改角色
	 * @param oldRole原始角色对象
	 * @param newRole更新角色对象
	 * @param rightNodeValue角色权限节点
	 * @param defaultAgencyIds新的默认机构id
	 * @return roleId角色ID
	 */
	public String modifyRole(Role oldRole, Role newRole, String[] rightNodeValue);

	/**
	 * 查找角色，包括角色创建者信息
	 * @param roleId角色ID
	 * @return role角色对象
	 */
	public Role viewRole(String roleId);

	/**
	 * 删除角色
	 * @param roleIds角色ID集合
	 */
	public void deleteRole(List<String> roleIds);

	/**
	 * 获得指定角色的权限节点值，若未指定角色，则获取所有权限的节点值
	 * @param roleId指定的角色ID
	 * @return list权限节点值集合
	 */
	public List<String> getRightNodeValue(String roleId);

	/**
	 * 生成权限树，并设置默认选中
	 * @param roleId指定的角色
	 * @param str默认选中
	 * @return 树结构文档
	 */
	public Document createRightXML(String roleId, List<String> str, LoginInfo loginer, String mainRoleId,String keyword);


}
