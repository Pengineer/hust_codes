package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Log implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private User user;// 对应的账号
	private String username;// 用户名称
	private Date date;// 操作时间
	private String ip;// 操作IP
	private String eventCode;// 事件代码
	private String eventDescription;// 事件描述
	private int isStatistic;// 是否进入统计(1是，0否)
	private String request;// 请求对象的序列化
	private String response;// 响应对象的序列化
	private String data;// 详细情况
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public int getIsStatistic() {
		return isStatistic;
	}
	public void setIsStatistic(int isStatistic) {
		this.isStatistic = isStatistic;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JSON(serialize=false)
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	@JSON(serialize=false)
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	@JSON(serialize=false)
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}