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
 * 版本表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_VERSION")
public class Version  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_DATE",length=7)  
    private Date date;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")
    private Account account;
	
	@Column(name="C_COMMENT",length=200)  
    private String comment;
	
	@Column(name="C_VERSION",precision=10,scale=0)  
    private Integer version;
	
	@ManyToOne(targetEntity=Document.class)
	@JoinColumn(name="C_DOCUMENT_ID")
    private Document document;
	
	@Column(name="C_TYPE",length=20)  
    private String type;
	
	@Column(name="C_TITLE",length=200)  
    private String title;
	
	@Column(name="C_PATH",length=200)  
    private String path; //原来的分类
	
	@Column(name="C_BLOCK_ID",length=20)  
	private String blockId;
	
	@Column(name="C_FILE_ID",length=40)  
	private String fileId;
	
	@Column(name="C_FILE_SIZE",precision=10)  
	private Long fileSize;
  
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
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

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public Document toDocument(){
		Document doc = new Document();
		doc.setPath(this.getPath());
		doc.setFileId(this.getFileId());
		doc.setBlockId(this.getBlockId());
		doc.setFileSize(this.getFileSize());
		doc.setTitle(this.getTitle());
		doc.setType(this.getType());
		return doc;
	}
   
}