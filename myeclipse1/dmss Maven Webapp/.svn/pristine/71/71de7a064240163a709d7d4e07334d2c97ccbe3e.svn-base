package org.csdc.model;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 权限表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_RIGHT" )
public class Right  implements java.io.Serializable {


	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=100)  
    private String name;
	
	@Column(name="C_DESCRIPTION",length=200)  
    private String description;
    
    
    @Column(name="C_CODE",length=100)  
    private String code;
    
    @ManyToOne
    @JoinColumn(name = "C_PARENT_ID")  
    private Right parent;

    @OneToMany(targetEntity = Right.class, cascade = { CascadeType.ALL }, mappedBy = "parent")  
    private Set<Right> rightChilds;
    
    @ManyToMany(targetEntity=Role.class, mappedBy="rights")
    private Set<Role> roles;
  
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

 
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

	public Right getParent() {
		return parent;
	}

	public void setParent(Right parent) {
		this.parent = parent;
	}

	public Set<Right> getRightChilds() {
		return rightChilds;
	}

	public void setRightChilds(Set<Right> rightChilds) {
		this.rightChilds = rightChilds;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
     


}