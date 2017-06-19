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
@Table(name="T_ARTICLE")
@JsonIgnoreProperties({"password"})
public class Article implements Serializable {
	private static final long serialVersionUID = 1356928190986631660L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //文章ID（PK）
	
	@ManyToOne
	@JoinColumn(name="C_ACCOUNT_ID")
	private Account account; //发布者账号（FK）
	
	@Column(name="C_TITLE", length=200, nullable = false)
	private String title; //标题
	
	@Column(name="C_ABSTRACT", length=200)
	private String abstractStr; //首页轮播新闻摘要
	
	@Column(name="C_CONTENT")
	private String content; //内容（若包含插入文件，如：插图等，其文件名命名规范参考C_ATTACHMENT字段）
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_CREATE_DATE")
	private Date createDate; //创建时间
	
	@ManyToOne
	@JoinColumn(name="C_TYPE_ID")
	private SystemOption typeID; //板块类型ID（FK）
	
	@Column(name="C_SOURCE", length=200)
	private String source; //来源
	
	@Column(name="C_IS_OPEN", nullable = false)  
	private Integer isOpen; //是否公开[1：是；0：否]
	
	@Column(name="C_ATTACHMENT", length=1000)
	private String attachment; //附件
	
	@Column(name="C_ATTACHMENT_NAME", length=2000)
	private String attachmentName; //附件原文件名
	
	@Column(name="C_IMG_HOMESHOW", length=1000)
	private String imgHomeshow; //首页轮播图片地址
	
	@Column(name="C_VIEW_COUNT")  
	private Integer viewCount; //查看次数
	
	@Column(name="C_AUTHOR", length=200)
	private String author; //作者
	
	@Column(name="C_EDITOR", length=200)
	private String editor; //文章编辑者

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

	public String getAbstractStr() {
		return abstractStr;
	}

	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SystemOption getTypeID() {
		return typeID;
	}

	public void setTypeID(SystemOption typeID) {
		this.typeID = typeID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
}
