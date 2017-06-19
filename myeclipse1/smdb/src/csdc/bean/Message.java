package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Message implements java.io.Serializable {

	private static final long serialVersionUID = -8676062369919363268L;
	private String id;
	private Account account;//留言者账号
	private String authorName;//留言者姓名
	private String title;//标题
	//@CheckSystemOptionStandard("messageType")
	private SystemOption type;//类型
	private String content;//内容
	private String dfs;//文件云存储位置
	private Date createDate;//创建时间
	private int isOpen;//是否公开(1是;0否)
	private int sendEmail;//是否发送邮件(1是;0否)
	private String email;//邮箱
	private String phone;//电话
	private Message replyTopic;
	private Message replyMessage;
	private String ip;
	private String log;
	private String pictureUrl;
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	private int viewCount;
	private int auditStatus;//审核状态(0未审;1不通过;2通过)
	private int sourceType;//留言来源类型(0:PC端留言；1:安卓客户端留言；2:ios客户端留言)

	private Set<Message> messageForReplyTopic;
	private Set<Message> messageForReplyMessage;

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
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JSON(serialize=false)
	public SystemOption getType() {
		return type;
	}
	public void setType(SystemOption type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(int sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@JSON(serialize=false)
	public Message getReplyTopic() {
		return replyTopic;
	}
	public void setReplyTopic(Message replyTopic) {
		this.replyTopic = replyTopic;
	}
	@JSON(serialize=false)
	public Message getReplyMessage() {
		return replyMessage;
	}
	public void setReplyMessage(Message replyMessage) {
		this.replyMessage = replyMessage;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	@JSON(serialize=false)
	public Set<Message> getMessageForReplyTopic() {
		return messageForReplyTopic;
	}
	public void setMessageForReplyTopic(Set<Message> messageForReplyTopic) {
		this.messageForReplyTopic = messageForReplyTopic;
	}
	@JSON(serialize=false)
	public Set<Message> getMessageForReplyMessage() {
		return messageForReplyMessage;
	}
	public void setMessageForReplyMessage(Set<Message> messageForReplyMessage) {
		this.messageForReplyMessage = messageForReplyMessage;
	}
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
	public int getSourceType() {
		return sourceType;
	}
	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

}
