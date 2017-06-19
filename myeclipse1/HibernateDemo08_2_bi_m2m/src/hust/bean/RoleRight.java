package hust.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_role2_right2")
public class RoleRight {
	
	private int id;
	private String description;
	private Role role;
	private Right right;
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="increment")
	@GeneratedValue(generator="idGenerator")
	@Column(name="c_id", nullable=false, length=40)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="c_description", length=20)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name="c_role_id",nullable=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@ManyToOne
	@JoinColumn(name="c_right_id",nullable=false)
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	
}
