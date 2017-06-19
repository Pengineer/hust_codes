package csdc.service;

import java.util.List;

import org.dom4j.Document;

import csdc.bean.Role;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.LogInfo;

/**
 * 角色管理接口
 * @author 龚凡
 * @version 2011.04.11
 */
public interface IRoleService extends IBaseService {

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
	public String addRole(Role role, String[] rightNodeValue, String defaultAgencyIds);

	/**
	 * 修改角色
	 * @param oldRole原始角色对象
	 * @param newRole更新角色对象
	 * @param rightNodeValue角色权限节点
	 * @param defaultAgencyIds新的默认机构id
	 * @return roleId角色ID
	 */
	public String modifyRole(Role oldRole, Role newRole, String[] rightNodeValue, String defaultAgencyIds);

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
	 * 根据角色所属机构ID，查找其名称，并按部、省、校的顺序返回
	 * @param agencyIds 待查找的机构ID字符串，以"; "分隔
	 * @return [][] 一维区分部、省、校；二维区分ID、NAME
	 */
	public String[][] getAgencyIdAndName(String agencyIds);

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
