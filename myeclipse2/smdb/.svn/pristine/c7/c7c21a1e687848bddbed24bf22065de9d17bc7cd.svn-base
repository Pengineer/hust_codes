package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

public class Notice implements java.io.Serializable {

	private static final long serialVersionUID = -8160060311886765727L;
	private String id;
	private Account account;//作者账号
	private String accountBelong;//账号所属人员或机构
	private String title;//标题
	private String content;//内容
	private Date createDate;//创建时间
	private String htmlFile;//网页地址
	//@CheckSystemOptionStandard("noticeType")
	private SystemOption type;//类型
	private String source;//来源
	private int isOpen;//是否公开(1是;0否)
	private int isPop;//是否弹出(1是;0否)
	private int validDays;//有效天数
	private String receiverType;//接收人员类型[由0和1组成的15位字符串，0表示不是对应类型不接收，1表示是对应类型接收，对应类型从左至右依次为：(1)部级机构、(2)部级管理人员、(3)省级机构、(4)省级管理人员、(5)部属高校、(6)部属高校管理人员、(7)地方高校、(8)地方高校管理人员、(9)高校院系、(10)高校院系管理人员、(11)研究机构、(12)研究机构管理人员、(13)外部专家、(14)内部专家、(15)学生]
	private int sendEmail;//是否发送邮件(1是;0否)
	private String attachment;//附件
	private String attachmentName;//附件原文件名
	private int viewCount;//查看次数
	
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
	public String getAccountBelong() {
		return accountBelong;
	}
	public void setAccountBelong(String accountBelong) {
		this.accountBelong = accountBelong;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	//@JSON(format="yyyy-MM-dd HH:mm:ss")
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getHtmlFile() {
		return htmlFile;
	}
	public void setHtmlFile(String htmlFile) {
		this.htmlFile = htmlFile;
	}
	@JSON(serialize=false)
	public SystemOption getType() {
		return type;
	}
	public void setType(SystemOption type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getIsPop() {
		return isPop;
	}
	public void setIsPop(int isPop) {
		this.isPop = isPop;
	}
	public int getValidDays() {
		return validDays;
	}
	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public int getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(int sendEmail) {
		this.sendEmail = sendEmail;
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
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}