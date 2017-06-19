package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class AccountRole implements java.io.Serializable {

	private static final long serialVersionUID = 8837443881634684681L;
	private String id;// ID
	private Account account;// 账号
	private Role role;// 角色

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@JSON(serialize=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}