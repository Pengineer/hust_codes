package csdc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_MAIL")
@JsonIgnoreProperties({"password"})
public class Mail implements Serializable {
	private static final long serialVersionUID = -5309693962229322770L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //邮件id（PK）
	
	@Column(name="C_SEND_TO")
	private String sendTo; //收件人列表
	
	@Column(name="C_SUBJECT", length=200)
	private String subject; //邮件主题
	
	@Column(name="C_BODY")
	private String body; //邮件正文（若包含插入文件，如：插图等，其文件名命名规范参考C_ATTACHMENT字段）
	
	@Column(name="C_IS_HTML", nullable = false)  
	private Integer isHtml; //是否HTML[1：是；0：否]
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_CREATE_DATE", nullable = false)
	private Date createDate; //创建时间
	
	@Column(name="C_SEND_TIMES")  
	private Integer sendTimes; //尝试发送次数
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_FINISH_DATE")
	private Date finishDate; //成功时间
	
	@ManyToOne
	@JoinColumn(name="C_ACCOUNT_ID")
	private Account account; //邮件创建者账号id（FK）
	
	@Column(name="C_STATUS", nullable = false)  
	private Integer status; //邮件状态[0：待发送（未进入发送队列）；1：准备发送（已进入发送队列）；2：发生中；3：已发送；4：已取消]
	
	@Column(name="C_ATTACHMENT", length=1000)
	private String attachment; //附件
	
	@Column(name="C_ATTACHMENT_NAME", length=2000)
	private String attachmentName; //附件原文件名
	
	@Column(name="C_ACCOUNT_BELONG", length=200, nullable = false)
	private String accountBelong; //账号所属机构或人员
	
	@Column(name="C_FROM", length=20)
	private String from; //发件邮箱
	
	@Column(name="C_REPLY_TO", length=100)
	private String replyTo; //回复邮箱(身份认证）
	
	@Column(name="C_BCC")
	private String bcc; //密送人[多个用英文分号与空格隔开]
	
	@Column(name="C_LOG", length=4000)
	private String log; //重发记录
	
	@Column(name="C_CC")
	private String cc; //抄送人[多个用英文分号与空格隔开]
	
	@Column(name="C_DFS", length=200)
	private String dfs; //邮件附件云存储位置[多个用英文分号与空格隔开]

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
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

	public Integer getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(Integer isHtml) {
		this.isHtml = isHtml;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAccountBelong() {
		return accountBelong;
	}

	public void setAccountBelong(String accountBelong) {
		this.accountBelong = accountBelong;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getDfs() {
		return dfs;
	}

	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
}
