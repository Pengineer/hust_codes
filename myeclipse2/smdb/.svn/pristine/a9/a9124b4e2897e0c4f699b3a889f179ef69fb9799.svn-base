package csdc.bean;


import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.ui.context.Theme;

import csdc.tool.bean.AccountType;

/**
 * 站内信管理模块
 * @author yangfq
 *
 */
public class InBox implements java.io.Serializable {
	
	private static final long serialVersionUID = -4363618778866914644L;
	private String id;// ID
	private String person;//当前登陆者
	private String friend;// 好友
	private String theme;//留言主题
	private Account sendId;//发送者账号
	private String sendName;//发送者姓名
	private Account recId;//接受者账号 0:所有人都接受信息
	private String recName;//接受者姓名
	private String message;//站内信内容
	private Integer status;//站内信的发送状态 
	private Integer viewStatus;//站内信的查看状态 0：未读； 1：已读
	
	private Date pDate;//站内信发送时间
	@Enumerated(EnumType.STRING)
	private AccountType accountType;// 多播时存储账号类型[系统管理员, 部级, 省级, 部属高校, 地方高校, 高校院系, 研究基地, 外部专家, 教师, 学生]
	private int isPrincipal;// 多播时存储是否主账号，对于没有主子之分的账号（系统管理员、专家、教师、学生），统一设置为1。
	private int sendType;// 站内信的发送方式[1：单播；2：多播；3：广播；4：好友之间的站内信]
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JSON(serialize=false)
	public Account getSendId() {
		return sendId;
	}
	public void setSendId(Account sendId) {
		this.sendId = sendId;
	}
	
	@JSON(serialize=false)
	public Account getRecId() {
		return recId;
	}
	
	public void setRecId(Account recId) {
		this.recId = recId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getpDate() {
		return pDate;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public int getIsPrincipal() {
		return isPrincipal;
	}
	public void setIsPrincipal(int isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getFriend() {
		return friend;
	}
	public void setFriend(String friend) {
		this.friend = friend;
	}
	public Integer getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}
	
	
}
