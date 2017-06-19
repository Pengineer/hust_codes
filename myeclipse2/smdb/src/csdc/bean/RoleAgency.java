package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class RoleAgency implements java.io.Serializable {

	private static final long serialVersionUID = 2243849607052729715L;
	private String id;// ID
	private Role role;// 账号
	private Agency defaultAgency;// 角色

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@JSON(serialize=false)
	public Agency getDefaultAgency() {
		return defaultAgency;
	}
	public void setDefaultAgency(Agency defaultAgency) {
		this.defaultAgency = defaultAgency;
	}
}