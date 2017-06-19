package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class RoleRight {
	
	private String id;
	private Role role;	//角色
	private Right right;	//权限
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}