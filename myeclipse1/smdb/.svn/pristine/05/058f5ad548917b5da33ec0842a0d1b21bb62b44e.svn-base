package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class RoleRight implements java.io.Serializable {

	private static final long serialVersionUID = -1859298978363188601L;
	private String id;// ID
	private Role role;// 角色
	private Right right;// 权限

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
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	@JSON(serialize=false)
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}