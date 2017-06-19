package org.csdc.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_ROLE" )
public class Role  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=100)  
    private String name;
	
	@Column(name="C_DESCRIPTION",length=200)  
    private String description;
	
	@ManyToMany(mappedBy="roles")  
	private Set<Account> accounts;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="T_ROLE_RIGHT",  
        joinColumns={@JoinColumn(name="C_ROLE_ID")},   
        inverseJoinColumns={@JoinColumn(name="C_RIGHT_ID")}) 
	private Set<Right> rights;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="T_CATEGORY_SECURITY",  
        joinColumns={@JoinColumn(name="C_ROLE_ID")},   
        inverseJoinColumns={@JoinColumn(name="C_CATEGORY_ID")})
	private Set<Category> categories;
	
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
    
}