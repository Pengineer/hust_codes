package csdc.service;

import java.util.List;

import csdc.bean.Account;
import csdc.bean.Right;

public interface IAccountService extends IBaseService {

/*	public Account findByEmail(String email);
	public boolean checkByEmailAndPassword(String email, String password);*/
	public boolean checkAccount(String email);
	
	/**
	 * 获取当前通过认证的账号名称
	 */
	public String securityUsername();
	
	// ==============================================================
	// 函数名：getAccountRight
	// 函数描述：获取用户权限的集合
	// 返回值：用户权限的集合
	// ==============================================================
	public List<String> getAccountRight(String accountId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*    *//**
     * 添加用户并[同步其他数据库]
     * <ul>
     * <li>step : 保存系统用户，同时设置和部门的关系</li>
     * <li>step : 同步用户信息到activiti的identity.User，同时设置角色</li>
     * </ul>
     * 
     * @param user              用户对象
     * @param roleIds           角色ID集合
     * @param synToActiviti     是否同步到Activiti数据库，通过配置文件方式设置，使用属性：account.user.add.syntoactiviti
     * @throws OrganizationNotFoundException    关联用户和部门的时候从数据库查询不到哦啊部门对象
     * @throws  Exception                       其他未知异常
     *//*
	public void saveUser(Account account, List<Long> roleIds, boolean synToActiviti)
            throws  Exception;
     
    *//**
     * 删除用户
     * @param userId        用户ID
     * @param synToActiviti     是否同步到Activiti数据库，通过配置文件方式设置，使用属性：account.user.add.syntoactiviti
     * @throws Exception
     *//*
    public void delete(String userId, boolean synToActiviti) throws Exception;
 
    *//**
     * 同步用户、角色数据到工作流
     * @throws Exception
     *//*
    public void synAllUserAndRoleToActiviti() throws Exception;
 
    *//**
     * 删除工作流引擎Activiti的用户、角色以及关系
     * @throws Exception
     *//*
    public void deleteAllActivitiIdentifyData() throws Exception;

	
	public void delete(String userId, boolean synToActiviti, boolean synToChecking)
			throws Exception;
	
	synRoleToActiviti*/
	
	public void delete(String userId, boolean synToActiviti, boolean synToChecking)
			throws Exception;
	
	
	/*
	 * 同步帐号
	 */
	public void saveUser(Account account, List<Long> roleIds, boolean synToActiviti)throws Exception;

	
	// 复制角色数据
	public void synRoleToActiviti();
}