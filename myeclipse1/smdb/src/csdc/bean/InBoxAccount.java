package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 站内信记录管理模块
 * @author yxj
 */
public class InBoxAccount implements java.io.Serializable{
	
	private static final long serialVersionUID = -4363618778866914644L;
	private String id;//主键id	
	private InBox inBoxId;//站内信id
	
	private Account recId;//接受者帐号
	private Integer status;//站内信查看状态0:未读 1:已读
	private Date readTime;//站内信查看时间

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public InBox getInBoxId() {
		return inBoxId;
	}
	public void setInBoxId(InBox inBoxId) {
		this.inBoxId = inBoxId;
	}
	
	@JSON(serialize=false)
	public Account getRecId() {
		return recId;
	}
	public void setRecId(Account recId) {
		this.recId = recId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
}