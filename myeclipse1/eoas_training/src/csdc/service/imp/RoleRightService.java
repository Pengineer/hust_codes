package csdc.service.imp;

import java.util.List;

import csdc.bean.Role_Right;
import csdc.mappers.RoleRightMapper;
import csdc.service.IRoleRightService;


public class RoleRightService extends BaseService implements IRoleRightService {
	private RoleRightMapper roleRightDao;
	
	public void delete(String id) {
		roleRightDao.delete(id);
	}
	
	public List<Role_Right> selectRightByRoleId(String roleId) {
		return (List<Role_Right>)roleRightDao.selectRoleRightByRoleId(roleId);
	}

	public RoleRightMapper getRoleRightDao() {
		return roleRightDao;
	}

	public void setRoleRightDao(RoleRightMapper roleRightDao) {
		this.roleRightDao = roleRightDao;
	}


}