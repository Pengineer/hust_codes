package csdc.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_ROLE")
public class Role implements java.io.Serializable {
	private static final long serialVersionUID = 468019683903319630L;

    private String id; //角色id（PK）
    private String name; //角色名称
    private String description; //角色描述
    private Set<RoleRight> roleRights;
	private Set<Account> accounts;

	@Id	
	@Column(name="C_ID", length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="C_NAME", length=200) 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="C_DESCRIPTION", length=200) 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany(targetEntity=Account.class, mappedBy = "roles")  
	@JSON(serialize=false)
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@JSON(serialize=false)
	@OneToMany(mappedBy="role",cascade=CascadeType.ALL)
	public Set<RoleRight> getRoleRights() {
		return roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}

}
