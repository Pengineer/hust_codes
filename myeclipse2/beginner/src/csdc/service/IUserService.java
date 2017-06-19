package csdc.service;

import java.util.Date;
import java.util.List;

import csdc.bean.User;

public interface IUserService extends IBaseService{
	public boolean checkUsername(String username);
	public boolean checkEmail(String email);
	public boolean checkPassword(String username,String password);
	public boolean checkIdcard(String username,String idcard);
	public User getByUsername(String username);
	public void deleteGroupUser(List<String> userids);
	public void enableGroupUser(List<String> userids, Date validity, int userstatus, List<String> rolesid);
	public void disableGroupUser(List<String> userids);
}
