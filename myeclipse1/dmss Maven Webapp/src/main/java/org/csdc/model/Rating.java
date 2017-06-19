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
 * 文档评级表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_RATING" )
public class Rating  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_DATE",length=7)
    private Date date;
	
	@ManyToOne(targetEntity=Document.class)
	@JoinColumn(name="C_DOCUMENT_ID")  
    private Document document;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")  
    private Account account;
	
	@Column(name="C_VOTE",precision=1)
    private Integer vote;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}
	
	
}