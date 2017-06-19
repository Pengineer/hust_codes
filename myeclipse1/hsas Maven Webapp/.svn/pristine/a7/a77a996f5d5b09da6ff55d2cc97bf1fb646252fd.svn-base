package csdc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import csdc.model.Right;
import csdc.model.Role;

@Entity
@Table(name="T_ROLE_RIGHT")
public class RoleRight implements Serializable {
	private static final long serialVersionUID = 8354240756355315504L;

    private String id; //权限id（PK）
	private Role role;	//角色
	private Right right;	//权限

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

	@ManyToOne
	@JoinColumn(name="C_ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name="C_RIGHT_ID")
	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}
	
	
}
