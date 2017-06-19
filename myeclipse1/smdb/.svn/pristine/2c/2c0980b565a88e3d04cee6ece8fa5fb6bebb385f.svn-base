package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 邮件<br>
 * 包含一封邮件需要的所有属性
 * @author xuhan
 *
 */
public class Mail {
	private String id;			//UID
	private String sendTo;		//*收件人列表(收件人之间用';'分格)
	private String subject;		//*邮件主题
	private String body;		//*邮件正文
	private int isHtml;			//*是否Html(1是;0否)
	private Date createDate;	//邮件创建时间
	private Date finishDate;	//邮件完全发送成功时间
	private int sendTimes;		//已发尝试发送过多少次
	private Account account; 	// 邮件创建者
	private String accountBelong;//账号所属人员或机构
	private String attachment; 	//附件
	private String attachmentName; //附件原文件名
	private String cc;			//*抄送人列表(之间用'; '分格)
	private String bcc;			//*密送人列表(之间用'; '分格)
	private String from;		//发件人
	private String replyTo;		//回复地址
	private int status;			//状态(0待发送（未进入发送队列），1准备发送（已进入发送队列），2发送中，3发送成功，4已取消)
	private String log;			//重发记录
	private String dfs;//邮件附件云存储位置

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAccountBelong() {
		return accountBelong;
	}

	public void setAccountBelong(String accountBelong) {
		this.accountBelong = accountBelong;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(int isHtml) {
		this.isHtml = isHtml;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getDfs() {
		return dfs;
	}

	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
	
}
