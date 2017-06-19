package eoas.service;

import eoas.bean.Account;

public interface IAccountService extends IBaseService {
    	
	public int add(Account account);
	public boolean checkByEmailAndPassword(String email, String password);
	public boolean checkAccount(String email);
}