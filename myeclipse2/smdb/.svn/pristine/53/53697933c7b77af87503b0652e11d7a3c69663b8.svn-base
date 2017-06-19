package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 龚凡
 */
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = -921603861303696602L;
	private String id;// ID
	private String name;// 名称
	private String description;// 描述
	private Account account;// 创建者
	private String code;// 角色代码
	private String parentId;//父角色ID
	private Integer isPrincipal;// 是否主角色，系统管理员创建的角色是主角色
							// 0: 否（子账号）
							// 1: 是（主账号）
	private String defaultAccountType;//默认账号类型,由0和1组成的15位字符串，0表示不是对应账号的默认角色，
										//1表示是对应账号的默认角色，对应账号从左至右依次为：部级主账号、部级子账号、
										//省级主账号、省级子账号、部属高校主账号、部属高校子账号、地方高校主账号、
										//地方高校子账号、高校院系主账号、高校院系子账号、研究机构主账号、
										//研究机构子账号、外部专家账号、教师账号、学生

	private Set<AccountRole> accountRole;
	private Set<RoleAgency> roleAgency;
	private Set<RoleRight> roleRight;

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Role OTHER = (Role) obj;
		if (this.getId().equals(OTHER.getId())) {
			return true;
		} else {
			return false;
		}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JSON(serialize=false)
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getDefaultAccountType() {
		return defaultAccountType;
	}
	public void setDefaultAccountType(String defaultAccountType) {
		this.defaultAccountType = defaultAccountType;
	}
	@JSON(serialize=false)
	public Set<AccountRole> getAccountRole() {
		return accountRole;
	}
	public void setAccountRole(Set<AccountRole> accountRole) {
		this.accountRole = accountRole;
	}
	@JSON(serialize=false)	
	public Set<RoleAgency> getRoleAgency() {
		return roleAgency;
	}
	public void setRoleAgency(Set<RoleAgency> roleAgency) {
		this.roleAgency = roleAgency;
	}
	@JSON(serialize=false)
	public Set<RoleRight> getRoleRight() {
		return roleRight;
	}
	public void setRoleRight(Set<RoleRight> roleRight) {
		this.roleRight = roleRight;
	}

	public Integer getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(Integer isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}