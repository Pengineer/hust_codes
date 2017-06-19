package csdc.bean;

import java.util.Date;

public class Article {
	private String id;
	private Account account;
	private String title;
	private String slideAbstract;
	private String content;
	private Date createdDate;
	private SystemOption systemOption;
	private String source;
	private Boolean isOpen;
	private String attachment;
	private String attachmentName;
	private String imgHomeshow;
	private Integer viewCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlideAbstract() {
		return slideAbstract;
	}
	public void setSlideAbstract(String slideAbstract) {
		this.slideAbstract = slideAbstract;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public SystemOption getSystemOption() {
		return systemOption;
	}
	public void setSystemOption(SystemOption systemOption) {
		this.systemOption = systemOption;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
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
	public String getImgHomeshow() {
		return imgHomeshow;
	}
	public void setImgHomeshow(String imgHomeshow) {
		this.imgHomeshow = imgHomeshow;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
}