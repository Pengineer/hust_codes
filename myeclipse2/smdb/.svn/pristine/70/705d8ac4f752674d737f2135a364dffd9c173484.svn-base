package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

public class News implements java.io.Serializable {

	private static final long serialVersionUID = 2691572656556652434L;
	private String id;
	private Account account;//作者账号
	private String accountBelong;//账号所属人员或机构
	private String title;//标题
	private String content;//内容
	private Date createDate;//创建时间
	private String htmlFile;//网页地址
	//@CheckSystemOptionStandard("newsType")
	private SystemOption type;//类型
	private String source;//来源
	private int isOpen;//是否公开(1是;0否)
	private String attachment;//附件在数据库中的存储地址
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