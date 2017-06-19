package csdc.tool.bean;

import csdc.model.Account;

/**
 * 保存通过验证的账号相关信息
 * @author 龚凡
 */
public class LoginInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7320679898332785351L;
	
	private Account account;// 账号信息	
	private int isSuperUser;// 记录登录账号是否系统管理员，供切换账号使用（切换账号之后保留切换回来的入口）
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getIsSuperUser() {
		return isSuperUser;
	}
	public void setIsSuperUser(int isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	
}