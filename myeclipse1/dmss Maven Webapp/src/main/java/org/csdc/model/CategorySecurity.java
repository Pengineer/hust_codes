package org.csdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 分类目录操作权限配置表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_CATEGORY_SECURITY")
public class CategorySecurity  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@ManyToOne(targetEntity=Category.class)
	@JoinColumn(name="C_CATEGORY_ID")  
    private Category category;
    
	@ManyToOne(targetEntity=Role.class)
	@JoinColumn(name="C_ROLE_ID")  
    private Role role;
    
    @Column(name="C_WRITE",precision=1, scale=0)
    private Boolean write;
    
    
    @Column(name="C_DOWNLOAD",precision=1, scale=0)
    private Boolean download;
    
    @Column(name="C_ADD_CATEGORY",precision=1, scale=0)
    private Boolean addCategory;
    
    @Column(name="C_DELETE",precision=1, scale=0)
    private Boolean delete;
    
    @Column(name="C_RENAME",precision=1, scale=0)
    private Boolean rename;
    
    @Column(name="C_SECURITY",precision=1, scale=0)
    private Boolean security;
    
    @Column(name="C_CHECK_OUT",precision=1, scale=0)
    private Boolean checkOut;
    
    @Column(name="C_CHECK_IN",precision=1, scale=0)
    private Boolean checkIn;
    
    @Column(name="C_READ",precision=1, scale=0)
    private Boolean read;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getWrite() {
		return write;
	}

	public void setWrite(Boolean write) {
		this.write = write;
	}

	public Boolean getDownload() {
		return download;
	}

	public void setDownload(Boolean download) {
		this.download = download;
	}


	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getRename() {
		return rename;
	}

	public void setRename(Boolean rename) {
		this.rename = rename;
	}

	public Boolean getSecurity() {
		return security;
	}

	public void setSecurity(Boolean security) {
		this.security = security;
	}

	public Boolean getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Boolean checkOut) {
		this.checkOut = checkOut;
	}

	public Boolean getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Boolean checkIn) {
		this.checkIn = checkIn;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getAddCategory() {
		return addCategory;
	}

	public void setAddCategory(Boolean addCategory) {
		this.addCategory = addCategory;
	}
    
    
}