package org.csdc.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 文档表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_DOCUMENT")
public class Document  implements java.io.Serializable  {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_PATH",length=200)  
    private String path; //文件所在的路径
	
	@Column(name="C_LAST_MODIFIED_DATE",length=7)  
    private Date lastModifiedDate;
	
	@Column(name="C_DELETED",precision=1,scale=0)  
    private Boolean deleted;
	
	@Column(name="C_LOCKED",precision=1,scale=0)  
    private Boolean locked;
	
	@Column(name="C_BLOCK_ID",length=20)  
	private String blockId;
	
	@Column(name="C_FILE_ID",length=40)  
	private String fileId;
	
	@Column(name="C_IMMUTABLE",precision=1,scale=0)  
    private Boolean immutable;
	
	@Column(name="C_FINGERPRINT",length=40)  
    private String fingerprint;
	
	@Column(name="C_TITLE",length=200)  
    private String title;
	
	@Column(name="C_VERSION",length=20)  
    private Integer version;
	
	@Column(name="C_CREATED_DATE",length=7)  
    private Date createdDate;
	
	@Column(name="C_ACCOUNT_NAME",length=200)  
    private String accountName;
	
	@Column(name="C_CATEGORY_STRING")  
    private String categoryString;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")  
    private Account creator; //上传人
	
	@Column(name="C_STATUS",precision=1,scale=0)  
    private Integer status; //暂不用
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_LOCKED_ACCOUNT_ID") 
    private Account lockedAccount; //锁定人
	
	@Column(name="C_SOURCE_AUTHOR",length=40)  
    private String sourceAuthor;
	
	@Column(name="C_FILE_SIZE")  
    private Long fileSize;  //B
	
	@Column(name="C_INDEXED",precision=1,scale=0)  
    private Boolean indexed;
	
	@ManyToOne(targetEntity=Template.class)
	@JoinColumn(name="C_TEMPLATE_ID")
    private Template template;
	
	@Column(name="C_TAGS",length=1000)  
    private String tags;
	
	@Column(name="C_RATING",precision=2,scale=1)  
    private Double rating = 0.0;
	
	@Column(name="C_RECIPIENTS",length=1000)  
    private String recipients; //暂不用
	
	@Column(name="C_TYPE",length=7)  
    private String type;
    
	@ManyToMany(targetEntity=Category.class)  
    @JoinTable(name="T_DOC_CATEGORY",  
        joinColumns={@JoinColumn(name="C_DOCUMENT_ID")},   
        inverseJoinColumns={@JoinColumn(name="C_CATEGORY_ID")}) 
    private Set<Category> categories = new HashSet<Category>();
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "document")
	private Set<Version> versions = new HashSet<Version>();
	
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
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

    public Boolean getImmutable() {
        return this.immutable;
    }
    
    public void setImmutable(Boolean immutable) {
        this.immutable = immutable;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }
    
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

  
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountName() {
        return this.accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSourceAuthor() {
        return this.sourceAuthor;
    }
    
    public void setSourceAuthor(String sourceAuthor) {
        this.sourceAuthor = sourceAuthor;
    }

 

    public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getIndexed() {
        return this.indexed;
    }
    
    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }



    public String getTags() {
        return this.tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getRating() {
        return this.rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRecipients() {
        return this.recipients;
    }
    
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }


    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 文档的创建者，检入时的文档上传者。（统一为文档上传者）
     * @return
     */
	public Account getCreator() {
		return creator;
	}

	public void setCreator(Account account) {
		this.creator = account;
	}

	public Account getLockedAccount() {
		return lockedAccount;
	}

	public void setLockedAccount(Account lockedAccount) {
		this.lockedAccount = lockedAccount;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	/**
	 * 获取完整文件名（包括后缀）
	 * @return
	 */
	public String getFileName(){
		if( null == this.type || this.type.length() == 0){
			return this.title;
		}else {
			return this.title + "." + this.type;
		}
	}

	public String getCategoryString() {
		return categoryString;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}

	public Set<Version> getVersions() {
		return versions;
	}

	public void setVersions(Set<Version> versions) {
		this.versions = versions;
	}
	
}