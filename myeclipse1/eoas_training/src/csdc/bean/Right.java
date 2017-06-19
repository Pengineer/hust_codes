package csdc.bean;

import java.util.Set;

public class Right {

	private String id;
	private String name;	//权限
	private String description;	//权限描述
	private String code;//权限代码
	private String nodeValue;//节点值
	private Set<Role_Right> role_right;	//角色权限
	private Set<Right_Action> right_action;	//权限action
	
	public Set<Role_Right> getRole_right() {
		return role_right;
	}
	public void setRole_right(Set<Role_Right> role_right) {
		this.role_right = role_right;
	}
	public Set<Right_Action> getRight_action() {
		return right_action;
	}
	public void setRight_action(Set<Right_Action> right_action) {
		this.right_action = right_action;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	public String getNodeValue() {
		return nodeValue;
	}
}