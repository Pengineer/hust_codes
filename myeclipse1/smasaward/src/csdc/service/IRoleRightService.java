package csdc.service;

import java.util.List;
import java.util.Set;

import csdc.bean.Right;
import csdc.bean.Role;

public interface IRoleRightService extends IBaseService{
	public Set<String> getUserRight(String userId);
	public boolean checkRoleName(String roleName);
	public boolean checkRightName(String rightName);
	public String addRole(Role role, List<String> rightIds);
	public void modifyRole(Role role, List<String> rightIds);
	public void deleteRole(List<String> roleIds);
	public void modifyUserRole(String userId, List<String> roleIds);
	public String addRight(Right right, String[] actionurlarray, String[] actiondesarray);
	public void modifyRight(Right right, String[] actionurlarray, String[] actiondesarray);
	public void deleteRight(List<String> rightIds);
}