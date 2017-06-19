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
 * 邮件表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_MAIL" )
public class Mail  implements java.io.Serializable {
	
	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_SEND",length=40)  
	private String send;
	
	@Column(name="C_SENDTO",length=2000)  
    private String sendto;
	
	@Column(name="C_SENDED",length=2000)  
    private String sended;
	
	@Column(name="C_SUBJECT",length=400)  
    private String subject;
	
	@Column(name="C_BODY",length=65535)  
    private String body;
	
	@Column(name="C_IS_HTML",precision=1, scale=0)  
    private Boolean isHtml;
	
	@Column(name="C_CREATED_DATE",length=7)  
    private Date createdDate;
	
	@Column(name="C_SEND_TIMES",precision=2, scale=0)  
    private Integer sendTimes;
	
	@Column(name="C_FINISHED_DATE",length=7)  
    private Date finishedDate;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")  
    private Account account;
	
	@Column(name="C_STATUS",precision=1, scale=0)  
    private Integer status;
	
	@Column(name="C_ATTACHMENT",length=1000)  
    private String attachment;
	
	@Column(name="C_ATTACHMENT_NAME",length=10000)  
    private String attachmentName;

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getSendto() {
        return this.sendto;
    }
    
    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getSended() {
        return this.sended;
    }
    
    public void setSended(String sended) {
        this.sended = sended;
    }

    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsHtml() {
        return this.isHtml;
    }
    
    public void setIsHtml(Boolean isHtml) {
        this.isHtml = isHtml;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getSendTimes() {
        return this.sendTimes;
    }
    
    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Date getFinishedDate() {
        return this.finishedDate;
    }
    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }


    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAttachment() {
        return this.attachment;
    }
    
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentName() {
        return this.attachmentName;
    }
    
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
   








}