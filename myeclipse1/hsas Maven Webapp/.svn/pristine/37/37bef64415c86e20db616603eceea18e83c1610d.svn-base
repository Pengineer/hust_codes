package csdc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import csdc.model.Account;
import csdc.tool.bean.LoginInfo;

public interface IAccountService extends IBaseService{
	
	/**
	 * 根据账号名查找账号相关信息
	 * @param accountName 账号名
	 * @return 账号存在，返回该账号相关信息，不存在返回null
	 */
	public Account getAccountByName(String accountName);
	
	/**
	 * 根据账号id查找账号相关信息
	 * @param accountId账号
	 * @return 账号存在，返回该账号相关信息，不存在返回null
	 */
	public Account getAccountById(String accountId);
	
	/**
	 * 根据账号名获得账号权限
	 * @param name账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	public List<String> getRightByAccountId(String accountId);
	
	/**
	 * 根据账号名获得账号权限
	 * @param accountName账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	public List<String> getRightByAccountName(String accountName);
	
	
	/**
	 * 分配角色
	 * @param loginer当前登录对象
	 * @param accountIds待分配角色账号ID集合
	 * @param roleIds分配角色ID集合
	 * @param type区分是列表(1)调用分配角色，还是查看页面(0)调用分配角色
	 */
	public void assignRole(LoginInfo loginer, List<String> accountIds, String[] roleIds, int type);

	
	/**
	 * 根据账号id获得其角色名称，并组成一个以中文逗号隔开的字符串
	 * @param accountId账号ID
	 * @return 角色名称字符串
	 */
	public String getRoleName(String accountId);
	
	/**
	 * 检测账号名是否存在
	 * @param accountName账号名
	 * @return true存在，false不存在
	 */
	public boolean checkAccountName(String accountName);
	
	/**
	 * 查看账号信息
	 * @param account待查看的账号
	 * @param jsonMap返回前端的数据
	 * @param loginer当前登录对象
	 * @return jsonMap包含相关数据的map对象
	 */
	public Map viewAccount(Account account, Map jsonMap, LoginInfo loginer);

	
	/**
	 * 获取当前通过认证的账号名称
	 * @return
	 */
	
	public String securityUsername() ;
	
	/**
	 * 添加账号
	 * @param account 账号
	 * @return
	 */
	public String addAccount(Account account);
	
	/**
	 * 
	 * 修改账号
	 * @param account 新账号
	 * @param oldAccount  旧账号
	 * @return
	 */
	public String modifyAccount(Account account, Account oldAccount);
	
	/**
	 * 启用账号
	 * @param ids  账号集
	 * @param date 有效期
	 */
	public void enable(List<String> ids, Date date);
	
	/**
	 * 停用账号
	 * @param ids 账号集
	 */
	public void disable(List<String> ids);
	
	/**
	 * 删除账号
	 * @param ids
	 */
	public void delete(List<String> ids);

}
