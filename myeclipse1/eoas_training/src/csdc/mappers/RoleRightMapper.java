package csdc.mappers;

import java.util.List;

import csdc.bean.Role;
import csdc.bean.Role_Right;

public interface RoleRightMapper extends BaseMapper{
	 public List<Role_Right> selectRoleRightByRoleId(String roleId);
}