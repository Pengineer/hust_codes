package hust.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_role2")
public class Role {

	private int id;
	private String name;
	
	private Set<RoleRight> roleRights;        
	
	//如果需要直接由role获取其所有的right，而不通过RoleRight，可以添加入下代码
	private Set<Right> rights;
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="t_role2_right2",//名称一定要和RoleRight中定义的一致
				joinColumns={@JoinColumn(name="c_role_id")},
				inverseJoinColumns={@JoinColumn(name="c_right_id")})
	public Set<Right> getRights() {
		return rights;
	}
	public void setRights(Set<Right> rights) {
//		this.rights = rights;
		throw new RuntimeException("不要使用Role对象直接设置权限，请使用RoleRight实现！！！");
	}

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

	@OneToMany(mappedBy="role", cascade=CascadeType.ALL)
	public Set<RoleRight> getRoleRights() {
		return roleRights;
	}

	public void setRoleRights(Set<RoleRight> roleRights) {
		this.roleRights = roleRights;
	}
	
}
