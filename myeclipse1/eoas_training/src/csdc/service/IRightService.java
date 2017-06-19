package csdc.service;

import java.util.List;

import csdc.bean.Right;

public interface IRightService extends IBaseService {
	public int add(Right right);
	public void delete(String id);
	public void modify(Right right);
	public Right select(String id);
	public List<Right> listAll();
	public List<Right> selectRightByRoleId(String roleId);
}