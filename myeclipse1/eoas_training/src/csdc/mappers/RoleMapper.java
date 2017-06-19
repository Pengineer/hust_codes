package csdc.mappers;

import java.util.List;

import csdc.bean.Role;
import csdc.bean.Role_Right;

public interface RoleMapper extends BaseMapper{
	public List<Role> listAll();
	public Role selectByName(String name);
	public List<Role_Right> selectRoleRightByRole(String roleId);
	public List<Role> selectByAccount(String id);
	public List<Role> selectNotByAccount(String id);
}
