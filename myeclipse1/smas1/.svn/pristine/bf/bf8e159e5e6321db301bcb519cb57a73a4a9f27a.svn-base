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

	private String id;
	private String to;			//*收件人列表(之间用'; '分格)
	private String cc;			//*抄送人列表(之间用'; '分格)
	private String bcc;			//*密送人列表(之间用'; '分格)
	private String from;		//发件人
	private String replyTo;		//回复地址(SMTP服务器的认证地址，如果使用北师大的服务器就必须使用moesk@bnu.edu.cn)
	private String subject;		//*邮件主题
	private String body;		//*邮件正文
	private int isHtml;			//*是否Html(1是;0否)
	private String attachment;	//附件列表(磁盘绝对文件路径,之间用'; '分格)
	private String attachmentName;	//附件原文件名
	private Date createDate;	//邮件创建时间
	private Date finishDate;	//邮件完全发送成功时间
	private int sendTimes;		//已尝试发送过多少次
	private int status;			//状态(0:未发送；1:等待发送；2:发送中；3:已发送)
	private String template;		//模板邮件的名称
	private String log;			//重发记录

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getId() {
		return id;
	}
	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	
}
