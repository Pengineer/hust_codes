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
@Table(name="T_PRODUCT")
@JsonIgnoreProperties({"password"})
public class Product implements Serializable {
	private static final long serialVersionUID = 7471030555609919429L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //成果id（PK）
	
	@Column(name="C_TYPE", length=20)
	private String type; //成果类型
	
	@Column(name="C_CHINESE_NAME", length=800, nullable = false)
	private String chineseName; //中文名称
	
	@Column(name="C_ENGLISH_NAME", length=400)
	private String englishName; //英文名称
	
	@ManyToOne
	@JoinColumn(name="C_DISCIPLINE_ID")
	private SystemOption discipline; //学科门类id（FK）
	
	@Column(name="C_FILE", length=100)
	private String file; //成果电子档文件
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_SUBMIT_DATE")
	private Date submitDate; //提交时间
	
	@ManyToOne
	@JoinColumn(name="C_AUTHOR_ID", nullable = false)
	private Person author; //作者id（FK）
	
	@Column(name="C_AUTHOR_NAME", length=200)
	private String authorName; //作者姓名
	
	@Column(name="C_AUTHOR_TYPE")  
	private Integer authorType; //第一作者类型[1：教师；2：专家；3：学生]
	
	@Column(name="C_OTHER_AUTHOR_NAME", length=800)
	private String otherAuthorName; //其他作者[多个用英文分号与空格隔开]
	
	@Column(name="C_KEYWORDS", length=400)
	private String keywords; //关键词[多个用英文分号与空格隔开]
	
	@ManyToOne
	@JoinColumn(name="C_AGENCY_ID")
	private Agency agency; //所属机构id（FK）
	
	@Column(name="C_AGENCY_NAME", length=200)
	private String agencyName; //机构名称
	
	@Column(name="C_SUBMIT_STATUS")  
	private Integer submitStatus; //提交状态[0：默认；1：保留；2：暂存；3：提交]
	
	@Column(name="C_INTRODUCTION")
	private String introduction; //简介
	
	@Column(name="C_ORG_NAME", length=200)
	private String orgName; //以团队、课题组、机构等名义申请时的团队名称
	
	@Column(name="C_ORG_OFFICE_PHONE", length=400)
	private String orgOfficePhone; //团队、课题组、机构等的办公电话
	
	@Column(name="C_ORG_OFFICE_ADDRESS", length=200)
	private String orgOfficeAddress; //团队、课题组、机构等的办公地址
	
	@Column(name="C_ORG_OFFICE_POSTCODE", length=40)
	private String orgOfficePostcode; //团队、机构、组织等的办公邮编
	
	@Column(name="C_ORG_EMAIL", length=400)
	private String orgEmail; //团队、组织、机构等的邮箱

	@ManyToOne
	@JoinColumn(name="C_ORG_DIRECTOR_ID")
	private Person orgDirector; //团队、机构、组织等的第一负责人id（FK）
	
	@Column(name="C_ORG_DIRECTOR_NAME", length=200)
	private String orgDirectorName; //团队、机构、组织等的第一负责人姓名
	
	@Column(name="C_ORG_MEMBER", length=800)
	private String orgMember; //团队、课题组、机构的其他成员[多个以英文分号与空格隔开]
	
	@Column(name="C_NOTE", length=800)
	private String note; //备注
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_CREATE_DATE")
	private Date createDate; //创建时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_UPDATE_DATE")
	private Date updateDate; //更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public SystemOption getDiscipline() {
		return discipline;
	}

	public void setDiscipline(SystemOption discipline) {
		this.discipline = discipline;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getAuthorType() {
		return authorType;
	}

	public void setAuthorType(Integer authorType) {
		this.authorType = authorType;
	}

	public String getOtherAuthorName() {
		return otherAuthorName;
	}

	public void setOtherAuthorName(String otherAuthorName) {
		this.otherAuthorName = otherAuthorName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Integer getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgOfficePhone() {
		return orgOfficePhone;
	}

	public void setOrgOfficePhone(String orgOfficePhone) {
		this.orgOfficePhone = orgOfficePhone;
	}

	public String getOrgOfficeAddress() {
		return orgOfficeAddress;
	}

	public void setOrgOfficeAddress(String orgOfficeAddress) {
		this.orgOfficeAddress = orgOfficeAddress;
	}

	public String getOrgOfficePostcode() {
		return orgOfficePostcode;
	}

	public void setOrgOfficePostcode(String orgOfficePostcode) {
		this.orgOfficePostcode = orgOfficePostcode;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public Person getOrgDirector() {
		return orgDirector;
	}

	public void setOrgDirector(Person orgDirector) {
		this.orgDirector = orgDirector;
	}

	public String getOrgDirectorName() {
		return orgDirectorName;
	}

	public void setOrgDirectorName(String orgDirectorName) {
		this.orgDirectorName = orgDirectorName;
	}

	public String getOrgMember() {
		return orgMember;
	}

	public void setOrgMember(String orgMember) {
		this.orgMember = orgMember;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
