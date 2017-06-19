package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Friend {

	private String id;// ID
	private Person person;//当前登陆者
	private Person friend;// 好友
	private String friendName;// 好友名称
	private String reason;// 验证信息
	private Integer type;// 0:等待验证，1：拒绝加为好友，2：通过验证
	private Date date;//成为好友的时间
	private String refuse;//拒绝的理由
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getFriend() {
		return friend;
	}
	public void setFriend(Person friend) {
		this.friend = friend;
	}
	@JSON(serialize=false)
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getRefuse() {
		return refuse;
	}
	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
	
}