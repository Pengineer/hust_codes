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
 * 会话表
 * @author jintf
 * @date 2014-6-15
 */
@Entity
@Table(name="T_SESSION" )
public class Session  implements java.io.Serializable {

	@Id	
	@Column(name="C_ID",length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id;
	
	@Column(name="C_SID",length=40)
    private String sid;
	
	@Column(name="C_START_DATE",length=7)
    private Date startDate;
	
	@Column(name="C_END_DATE",length=7)
    private Date endDate;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="C_ACCOUNT_ID")  
    private Account account;
	
	@Column(name="C_IP",length=40)
    private String ip;
	
	@Column(name="C_EVENT_COUNT",precision=10,scale=0)
    private Integer eventCount;
	
	@Column(name="C_ONLINE_TIME",precision=10,scale=0)
    private Integer onlineTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getEventCount() {
		return eventCount;
	}

	public void setEventCount(Integer eventCount) {
		this.eventCount = eventCount;
	}

	public Integer getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Integer onlineTime) {
		this.onlineTime = onlineTime;
	}

	

}