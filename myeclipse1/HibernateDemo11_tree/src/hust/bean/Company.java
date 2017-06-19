package hust.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_company")
public class Company {

	private int id;
	private String name;
	
	private Company parentCompany; //父节点
	private Set<Company> childrenCompanies = new HashSet<Company>();//子节点
	
	@Id
	@GenericGenerator(name="idGenerator",strategy="increment")
	@GeneratedValue(generator="idGenerator")
	@Column(name="c_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="c_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="c_parent_id")
	public Company getParentCompany() {
		return parentCompany;
	}
	public void setParentCompany(Company parentCompany) {
		this.parentCompany = parentCompany;
	}
	
	@OneToMany(mappedBy="parentCompany", cascade=CascadeType.ALL)
	public Set<Company> getChildrenCompanies() {
		return childrenCompanies;
	}
	public void setChildrenCompanies(Set<Company> childrenCompanies) {
		this.childrenCompanies = childrenCompanies;
	}
}
