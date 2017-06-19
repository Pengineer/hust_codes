package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Right implements java.io.Serializable {

	private static final long serialVersionUID = -4941498974837976248L;
	private String id;// ID
	private String name;// 名称
	private String description;// 描述
	private String code;// 权限代码
	private String nodevalue;// 权限节点值

	private Set<RoleRight> roleRight;

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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNodevalue() {
		return nodevalue;
	}
	public void setNodevalue(String nodevalue) {
		this.nodevalue = nodevalue;
	}
	@JSON(serialize=false)
	public Set<RoleRight> getRoleRight() {
		return roleRight;
	}
	public void setRoleRight(Set<RoleRight> roleRight) {
		this.roleRight = roleRight;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}