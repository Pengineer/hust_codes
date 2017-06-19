package csdc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.bean.Passport;
import csdc.bean.Role;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;

/**
 * 账号管理接口
 * @author 龚凡
 * @version 2011.04.08
 */
@SuppressWarnings("unchecked")
public interface IAccountService extends IBaseService {

	/**
	 * 根据当前通行证信息获取可选择账号的相关信息
	 * @param 当前账号名称
	 * @return List数组，数组三列，分别记录系统中文名称、是否可用、用于选择匹配的字符串
	 */
	public List<String[]> userInfo(String username);
	
	/**
	 * 获取当前通过认证的账号名称
	 */
	public String securityUsername();
	
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
	 * 根据账号名获得账号权限
	 * @param name账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	public List<String> getRightByAccountId(String accountId);
	
	/**
	 * 检测账号名是否存在
	 * @param accountName账号名
	 * @return true存在，false不存在
	 */
	public boolean checkAccountName(String accountName);

	/**
	 * 根据账号的级别和类别，获取用于范围判断的entityIdType
	 * @param type账号级别
	 * @param isPrincipal账号类别
	 * @return 对应baseService中checkIfUnderControl方法需要的参数
	 * 返回值范围为-1到13
	 */
	public int getEntityIdType(AccountType type, int isPrincipal);
	

	/**
	 * 添加账号时，判断机构、人员是否已有账号。因为给专家、教师、学生
	 * 添加账号时，弹出层选择传递过来的参数是expertId、teacherId、
	 * studentId，此参数方便进行范围判断，但是account存储的是personId，
	 * 所以判断分为两类：
	 * 1、传递过来的参数是account中需要存储的belongId，此时查询账号表
	 * 进行判断。
	 * 2、传递过来的参数是expertId、teacherId、studentId等子表ID，则
	 * 需借助子表进行判断。
	 * @param id实体ID
	 * @param type账号级别(8、9、10查询子表进行判断)
	 * @return true有，false没有
	 */
	public boolean checkOwnAccount(String id, AccountType type, int isPrincipal);
	
	/**
	 * 根据账号名查找通行证
	 * @param userName账号名
	 * @return 该通行证不存在，则返回null；否则返回通行证对象
	 */
	public Passport getPassportByName(String userName);
	
	/**
	 * 根据账号名查找账号list
	 * @param userName账号名
	 * @return 该通行证不存在，则返回null；否则返回账号list
	 */
	public List<Account> getAccountListByName(String userName);
	
	/**
	 * 添加指定类型的账号
	 * @param belongEntityId账号所属ID
	 * @param account账号对象
	 * @param passport通行证对线
	 * @param loginer当前登录对象
	 * @return 账号ID
	 */
	public Map addAccount(String belongEntityId, Account account, Passport passport, LoginInfo loginer);
	
	/**
	 * 根据belongId和账号类型查找是否已有通行证
	 * @return 有返回passport 没有返回null
	 * @param belongId
	 * @param accountType
	 * @param accountPrincipal
	 * @return
	 */
	public Passport getPassportByBelongId(String belongId, AccountType accountType, Integer accountPrincipal);
	
	/*
	 * 教师账号注册
	 */
	public String register(Account account, Passport passport);
	
	/**
	 * 修改账号信息
	 * @param oldAccount oldPassport原始账号通行证
	 * @param account passport更新信息
	 * @param loginer当前登录对象
	 * @return 账号ID
	 */
	public String modifyAccount(Account oldAccount, Account account, Passport oldPassport, Passport passport, LoginInfo loginer);
	
	/**
	 * 修改通行证信息
	 * @param oldPassport原始通行证
	 * @param passport更新信息
	 * @param loginer当前登录对象
	 * @return 通行证ID
	 */
	public String modifyPassport(Passport oldPassport, Passport passport, LoginInfo loginer);


	/**
	 * 获取指定账号，所属的机构或人员名称及ID，目前就修改账号页面用。
	 * @param account指定的账号
	 * @return belongId所属实体ID belongName所属实体名称
	 */
	public String[] getAccountBelong(Account account);

	/**
	 * 查看账号信息
	 * @param account待查看的账号
	 * @param jsonMap返回前端的数据
	 * @param loginer当前登录对象
	 * @return jsonMap包含相关数据的map对象
	 */
	public Map viewAccount(Account account, Map jsonMap, LoginInfo loginer);

	/**
	 * 根据账号id获得其角色名称，并组成一个以中文逗号隔开的字符串
	 * @param accountId账号ID
	 * @return 角色名称字符串
	 */
	public String getRoleName(String accountId);

	/**
	 * 启用账号
	 * @param ids账号ID集合
	 * @param date有效期
	 */
	public void enable(List<String> ids, Date date);

	/**
	 * 停用账号
	 * @param ids账号ID集合
	 */
	public void disable(List<String> ids);

	/**
	 * 根据账号级别、类别、ID以及是否批量操作，获取已分配和可分配角色
	 * @param account账号对象
	 * @param type操作类别(1列表批量,0查看单个)
	 * @param loginer当前登录对象
	 * @return 可分配和已分配角色集合
	 */
	public List<Role>[] getAssignRole(Account account, int type, LoginInfo loginer, List<String> accountIds);
	
	/**
	 * 分配角色
	 * @param loginer当前登录对象
	 * @param accountIds待分配角色账号ID集合
	 * @param roleIds分配角色ID集合
	 * @param type区分是列表(1)调用分配角色，还是查看页面(0)调用分配角色
	 */
	public void assignRole(LoginInfo loginer, List<String> accountIds, String[] roleIds, int type);

	/**
	 * 修改账号密码
	 * @param passport待处理账号
	 * @param password新密码
	 */
	public void modifyPassword(Passport passport, String password);
	
	/**
	 * 根据传过来的account对象，查找相应子表中的email
	 * @param account待查找email的account对象
	 * @return email(若为空，则返回"")
	 */
	public String getEmailByAccount(Account account);

	public Mail email(Account account, Mail mail, String bindEmail, LoginInfo loginer, String subject, String body, int isHtml);

	public Mail retrieveCode(Account account, LoginInfo loginer, String path);
	public Mail bindEmailCode(Account account, LoginInfo loginer, String path, String bindEmail,String bindEmailVerifyCode);

}
