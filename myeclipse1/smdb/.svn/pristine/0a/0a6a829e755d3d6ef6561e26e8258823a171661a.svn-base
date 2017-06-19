package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Log implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Account account;// 对应的账号
	private Passport passport;// 对应通行证
	private String accountName;// 账号名称
	private Date date;// 操作时间
	private String ip;// 操作IP
	private String eventCode;// 事件代码
	private String eventDscription;// 事件描述
	private int isStatistic;// 是否进入统计(1是，0否)
	private String request;// 请求对象的序列化
	private String response;// 响应对象的序列化
	private String data;//日志记录的目标对发生变化后的详细情况
	private String changedData;// 日志记录的目标对发生变化后的详细情况
	private String accountBelong;// 账号所属
	
	public String getAccountBelong() {
		return accountBelong;
	}
	public void setAccountBelong(String accountBelong) {
		this.accountBelong = accountBelong;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@JSON(serialize=false)
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss.SSS")
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
	public String getEventDscription() {
		return eventDscription;
	}
	public void setEventDscription(String eventDescription) {
		this.eventDscription = eventDescription;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	@JSON(serialize=false)
	public String getChangedData() {
		return changedData;
	}
	public void setChangedData(String changedData) {
		this.changedData = changedData;
	}
}