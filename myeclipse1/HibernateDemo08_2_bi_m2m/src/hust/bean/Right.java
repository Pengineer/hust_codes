package hust.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_right2")
public class Right {

	private int id;
	private String name;
	
	private Set<RoleRight> roleRights;
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="increment")
	@GeneratedValue(generator="idGenerator")
	@Column(name="c_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="c_name", length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="right",cascade=CascadeType.ALL)
	public Set<RoleRight> getRoleRights() {
		return roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}
	
}
