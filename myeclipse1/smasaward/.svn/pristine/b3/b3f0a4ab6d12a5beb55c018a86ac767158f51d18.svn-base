package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class UserRole {
	
	private String id;
	private User user;	//用户
	private Role role;	//角色
	
	@JSON(serialize=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@JSON(serialize=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}