package eoas.service.imp;

import java.util.HashMap;
import java.util.Map;

import eoas.bean.Account;
import eoas.dao.AccountDao;
import eoas.service.IAccountService;

public class AccountService extends BaseService implements IAccountService  {
	
	private AccountDao accountDao;

	public int add(Account account) {
		return this.accountDao.insert(account);
	}
	
	/*
	 * 通过邮箱查找帐号
	 * @param email
	 * @return
	 */
	public boolean checkByEmailAndPassword(String email, String password) {
		if(accountDao.checkByEmailAndPassword( email,  password)!= null) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 检查新注册用户是否可用 
	 * @param email
	 * @return true可用  false不可用
	 */
	public boolean checkAccount(String email) {
		Map map = new HashMap();
		map.put("email", email);
		Account account = this.accountDao.findByEmail(email);
		return (account == null) ? true : false;
	}
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

}