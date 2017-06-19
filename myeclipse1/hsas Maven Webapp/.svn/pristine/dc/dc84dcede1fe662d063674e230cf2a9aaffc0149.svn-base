package csdc.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_RIGHT" )
public class Right implements java.io.Serializable {
	private static final long serialVersionUID = 4080129964350262207L;

	
    private String id; //权限id（PK）
	
	
    private String name; //名称
	
	
    private String description; //描述
	
	
    private String nodeValue; //结点值
	
	
    private String code; //权限代码

	
   /* @ManyToMany(targetEntity=Role.class, mappedBy="rights",cascade = CascadeType.ALL)
    private Set<Role> roles;*/
	
	private Set<RoleRight> roleRights;

	
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

	@Column(name="C_NAME", length=200, nullable = false)  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="C_DESCRIPTION", length=800, nullable = false)  
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="C_CODE", length=100)  
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="C_NODE_VALUE", length=40)  
	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
    
	@JSON(serialize=false)
	@OneToMany(mappedBy="right",cascade=CascadeType.ALL)
	public Set<RoleRight> getRoleRights() {
		return roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}
}