package csdc.bean;

public class Role {

	private String id;// 角色id
	private String name; // 权限名称
	private String description; // 权限描述

	// ---get & set ---
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

}