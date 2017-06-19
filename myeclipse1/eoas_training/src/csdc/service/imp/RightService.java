package csdc.service.imp;

import java.util.List;

import csdc.bean.Right;
import csdc.mappers.RightMapper;
import csdc.service.IRightService;

public class RightService extends BaseService implements IRightService {	
	private RightMapper rightDao;
	
	public int add(Right right){
		return rightDao.insert(right);
	}
	public void delete(String id){
		rightDao.delete(id);
	}
	
	public void modify(Right right) {
		rightDao.modify(right);
	}
	
	public Right select(String id) {
		return (Right) rightDao.select(id);
	}
	
	public List<Right> listAll() {
		return rightDao.listAll();
	}

	public List<Right> selectRightByRoleId(String roleId) {
		return rightDao.selectRightByRoleId(roleId);
	}
	
	public RightMapper getRightDao() {
		return rightDao;
	}
	public void setRightDao(RightMapper rightDao) {
		this.rightDao = rightDao;
	}
}