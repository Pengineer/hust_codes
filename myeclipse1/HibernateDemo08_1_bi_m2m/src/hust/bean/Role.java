package hust.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_role")
public class Role {

	private int id;
	private String name;
	
	private Set<Right> rights;
	

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

	@ManyToMany
	@JoinTable(name="t_role_right",
	           joinColumns={@JoinColumn(name="c_role_id")},
	           inverseJoinColumns={@JoinColumn(name="c_right_id")})
	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	
}
