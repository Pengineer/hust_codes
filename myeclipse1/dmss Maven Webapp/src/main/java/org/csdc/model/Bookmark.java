package org.csdc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 书签表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_BOOKMARK")
public class Bookmark  implements java.io.Serializable {
	
	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_TITLE", length=200)  
    private String title;
	
	@Column(name="C_DESCRIPTION", length=400)  
    private String description;
	
	@Column(name="C_TYPE", length=20)  
    private String type;
	
	@Deprecated
	@Column(name="C_FILE_TYPE", length=20)  
    private String fileType;
	
	@ManyToOne(targetEntity=Category.class)
	@JoinColumn(name="C_CATEGORY_ID")  
    private Category category;
	
	@Column(name="C_LAST_MODIFIED_DATE", length=7)  
    private Date lastModifiedDate;
	
	@Column(name="C_DELETED", precision=1, scale=0)  
    private Boolean deleted;
	
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")  
	private Account account;
	
	@ManyToOne(targetEntity=Document.class)
	@JoinColumn(name="C_DOCUMENT_ID")  
    private Document document;

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }
    
    /**
     * 书签类型
     * 如果为文档，则是文档类型，若是分类目录，统一为"folder"
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
    
}