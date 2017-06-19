package org.csdc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_PERSON" )
public class Person  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_NAME",length=40)
    private String name;
	
	@Column(name="C_MOBILE_PHONE",length=40)
    private String mobilePhone;
	
	@Column(name="C_EMAIL",length=100)
    private String email;
	
	@Column(name="C_AGENCY",length=200)
    private String agency;
	
	@Column(name="C_DUTY",length=40)
    private String duty;
	
	@Column(name="C_ID_CARD",length=40)
    private String idCard;
	
	@Column(name="C_QQ",length=40)
    private String qq;
	
	@Column(name="C_LAST_MODIFIED_DATE",length=160)
    private Date lastModifiedDate;
     
    @OneToOne(mappedBy = "person")
    private Account account;

    public Person() {
    }

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

    public String getMobilePhone() {
        return this.mobilePhone;
    }
    
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAgency() {
        return this.agency;
    }
    
    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getDuty() {
        return this.duty;
    }
    
    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getIdCard() {
        return this.idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getQq() {
        return this.qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
   








}