package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import csdc.model.Account;
import csdc.service.IAccountService;
import csdc.tool.MD5;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;

@Service
@Transactional
public class AccountService extends BaseService implements IAccountService {
	
	public Account getAccountByName(String accountName) {
		Account account = (Account) dao.queryUnique("select acc from Account acc where acc.username =?", accountName);
		return account;
	}
	/**
	 * 获取当前通过认证的账号名称
	 */
	public String securityUsername() {
		// 获得当前通过认证的用户上下文信息
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 获得当前通过认证用户的账号名
		String username;
		if (principal instanceof UserDetails) {// 如果上下文信息为UserDails实例，则调用该接口的getUsername方法获取账号名
			username = ((UserDetails)principal).getUsername();
		} else {// 如果上下文信息不是UserDails实例，则它就是账号名
			username = principal.toString();
		}
		return username;
	}
	
	public String addAccount(Account account) {
		if (account.getType().equals("-1")) {
			account.setType("common");
		}
		account.setPassword(MD5.getMD5(account.getPassword()));
		account.setStatus(1);
		return dao.add(account).toString();
	}
	@Override
	public String modifyAccount(Account account, Account oldAccount) {
	
		if (!account.getIsSuperUser().equals(oldAccount.getIsSuperUser())) {
			oldAccount.setIsSuperUser(account.getIsSuperUser());
		}
		if (!account.getUsername().equals(oldAccount.getUsername())) {
			oldAccount.setUsername(account.getUsername());
		}
		if (!account.getType().equals(oldAccount.getType())) {
			oldAccount.setType(account.getType());
		}
		dao.modify(oldAccount);
		return oldAccount.getId();
	
	}
	
	
	@Override
	public void enable(List<String> ids, Date date) {
		
		Account account;
		for(String id :ids){
			
			account = dao.query(Account.class, id);
			account.setStatus(1); //设置为启用
			account.setExpireDate(date);//设置有效期
			dao.modify(account);
			
		}
	}
	
	@Override
	public void disable(List<String> ids) {
		// TODO Auto-generated method stub
		Account account;
		for(String id :ids) {
			
			account = dao.query(Account.class,id);
			account.setStatus(0);
			dao.modify(account);
			
		}
		
		
	}
	
	
	@Override
	public void delete(List<String> ids) {

		for(String id :ids){
			
			dao.delete(Account.class, id);
		}
	}
	
	/**
	 * 根据账号名获得账号权限
	 * @param name账号名
	 * @return 如果账号不存在，则返回null；
	 * 如果账号存在，则系统管理员账号返回所有权限；
	 * 其它账号，返回拥有角色所对应的权限。
	 * 返回结果均无重复项。
	 */
	@Override
	public List<String> getRightByAccountName(String accountName) {
		if (accountName == null || accountName.isEmpty()) {// 如果账号名为空，则直接返回null
			return null;
		} else {// 如果账号名非空，则先查找该账号对象，根据账号的类型读取相应的权限。
			Map map = new HashMap();
			map.put("name", accountName);
			Account account = (Account) dao.query("from Account ac where ac.username = :name", map).get(0);
			map.remove("name");
			
			if (account == null) {// 账号不存在，直接返回空
				return null;
			} else {// 账号存在，则根据账号类型查询相应的权限
				List<String> re;// 权限集合
				if (account.getIsSuperUser() == 1) {// 系统管理员读取所有权限
					re = dao.query("select distinct r.code from Right r");
				} else {// 其它账号，根据账号、角色、权限关联关系，读取自己拥有的权限
					map.put("accountId", account.getId());
					re = dao.query("select distinct r.code from Right r, RoleRight rr, AccountRole ar where ar.account.id = :accountId and ar.role.id = rr.role.id and rr.right.id = r.id", map);
				}
				return re;
			}
		}
	}
	
	
	@Override
	public Account getAccountById(String accountId) {

		if(accountId==null ||accountId.equals("")){
			
			return null;
		} else {
			
			return dao.query(Account.class, accountId);
		}
	}
	
	
	@Override
	public List<String> getRightByAccountId(String accountId) {
		
		if(accountId==null||accountId.equals("")){
			return null;
		}else {
			Map map = new HashMap();
			Account account = dao.query(Account.class, accountId);
			List<String> list;
			if(account.getType().equals(AccountType.SUPERADMIN)){
				list = dao.query("select r.code from Right r");
			}else {
				
				map.put("accountId", account.getId());
				list = dao.query("select distinct r.code from Right ,RoleRight rr,AccountRole ar where ar.account.id = :accountId and ar.role.id=rr.role.id and rr.right.id = r.id",map );
			}
			
			return list;
		}
		
	}
	
	
	@Override
	public void assignRole(LoginInfo loginer, List<String> accountIds,
			String[] roleIds, int type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getRoleName(String accountId) {
		
		return null;
	}
	
	
	@Override
	public boolean checkAccountName(String accountName) {

		if (accountName == null || accountName.isEmpty()) {// 如果账号名为空，则视为已存在
			return true;
		} else {// 如果账号名非空，则查询数据库，判断此账号名是否存在
			Map map = new HashMap();
			map.put("accountName", accountName);
			List<String> re = dao.query("select a.id from Account a  where a.name = :accountName", map);// 获取指定名称的账号
			return !re.isEmpty();
		}
	}
	@Override
	public Map viewAccount(Account account, Map jsonMap, LoginInfo loginer) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
