package csdc.service;

import java.util.List;
import java.util.Set;

import csdc.bean.Right;
import csdc.bean.Role;

public interface IRoleRightService extends IBaseService{
	public Set<String> getUserRight(String userid);
	public boolean checkRolename(String rolename);
	public boolean checkRightname(String rightname);
	public String addRole(Role role, List<String> rightids);
	public void modifyRole(Role role, List<String> rightids);
	public void deleteRole(List<String> roleids);
	public void modifyUserRole(String userid, List<String> roleids);
	public String addRight(Right right, String[] actionurlarray, String[] actiondesarray);
	public void modifyRight(Right right, String[] actionurlarray, String[] actiondesarray);
	public void deleteRight(List<String> rightids);
}