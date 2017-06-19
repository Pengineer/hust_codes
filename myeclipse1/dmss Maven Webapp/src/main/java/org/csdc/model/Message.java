package org.csdc.model;
// default package

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
 * 消息表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_MESSAGE" )
public class Message  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@ManyToOne
	@JoinColumn(name="C_ACCOUNT_ID")  
    private Account account;
	
	@Deprecated
	@Column(name="C_AUTHOR_NAME",length=40)  
    private String authorName;
	
	@Column(name="C_TITLE",length=200)  
    private String title;
	
	@Column(name="C_CONTENT",length=1000)  
    private String content;
	
	@Column(name="C_CREATED_DATE",length=7)  
    private Date createdDate;
	
	@Column(name="C_IS_OPEN",precision=1, scale=0)  
    private Boolean isOpen;
	
	@Column(name="C_PRIORITY",precision=1, scale=0)  
    private Integer priority;


    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }


    public String getAuthorName() {
        return this.authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsOpen() {
        return this.isOpen;
    }
    
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getPriority() {
        return this.priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}