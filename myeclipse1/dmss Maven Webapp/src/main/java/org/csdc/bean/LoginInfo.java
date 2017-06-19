package org.csdc.bean;

import org.csdc.model.Account;



/**
 * 保存通过验证的账号相关信息
 * @author jintf
 * @date 2014-6-15
 */
public class LoginInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7320679898332785351L;
	private Account account;// 账号信息

	private int isAdmin;// 当前账号级别，后台使用

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}