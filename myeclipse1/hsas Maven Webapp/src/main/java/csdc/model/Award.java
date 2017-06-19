package csdc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_AWARD")
@JsonIgnoreProperties({"password"})
public class Award implements Serializable {
	private static final long serialVersionUID = 2055599312372233352L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //奖励申请id（PK）
	
	/*@OneToMany
	@JoinColumn(name="C_PRODUCT_ID")
	private Product product; //成果id（FK）
*/	
	@OneToMany
	@JoinColumn(name="C_PRODUCT_ID")
	private Set<Product> products = new HashSet<Product>();
	
	@Column(name="C_PRODUCT_NAME", length=800)
	private String productName; //成果名称
	
	@Column(name="C_PRODUCT_TYPE", length=200)
	private String productType; //成果类型
	
	@Column(name="C_FILE", length=100)
	private String file; //成果电子档文件
	
	@Column(name="C_PUBLICATION", length=400)
	private String publication; //出版社或发表刊物
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_PUBLISH_DATE")
	private Date publishDate; //出版或发表时间

	@Column(name="C_PARTNER", length=200)
	private String partner; //合作者
	
	@ManyToOne
	@JoinColumn(name="C_AGENCY_ID")
	private Agency agency; //依托机构id（FK）
	
	@Column(name="C_AGENCY_NAME", length=200)
	private String agencyName; //依托机构名称
	
	@Column(name="C_NUMBER", length=40)
	private String number; //成果编号

	@ManyToOne
	@JoinColumn(name="C_DISCIPLINE_ID")
	private SystemOption discipline; //学科代码（FK）
	
	@Column(name="C_GROUP", length=200)
	private String group; //分组信息
	
	@Column(name="C_SESSION")
	private Integer session; //申请届次

	@ManyToOne
	@JoinColumn(name="C_APPLICANT_ID")
	private Person applicant; //申请人id（FK）
	
	@Column(name="C_AUTHOR", length=200)
	private String author; //作者
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_APPLICANT_SUBMIT_DATE")
	private Date applicantSubmitDate; //申请人提交时间
	
	@Column(name="C_APPLICATION_STATUS")
	private Integer applicantStatus; //申请状态[0：默认；1：暂存；2：提交]
	
	@Column(name="C_APPLICATION_TYPE")
	private Integer applicantType; //申请类型[ 1：以个人名义申请；2：以团队、课题组、机构等名义申请]
	
	@ManyToOne
	@JoinColumn(name="C_FIRST_AUDITOR_ID")
	private Person firstAuditor; //初审人[管理员]id（FK）
	
	@Column(name="C_FIRST_AUDITOR_NAME", length=200)
	private String firstAuditorName; //初审人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_FIRST_AUDIT_DATE")
	private Date firstAuditDate; //初审时间
	
	@Column(name="C_FIRST_AUDIT_STATUS")
	private Integer firstAuditStatus; //初审状态[0：未审核；1：不同意；2：同意；3：退回]
	
	@Column(name="C_FIRST_AUDIT_OPINION", length=800)
	private String firstAuditOpinion; //初审意见
	
	@ManyToOne
	@JoinColumn(name="C_FIRST_REVIEWER_ID")
	private Person firstReviewer; //初评人[专家]id（FK）
	
	@Column(name="C_FIRST_REVIEWER_NAME", length=200)
	private String firstReviewerName; //初评人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_FIRST_REVIEW_DATE")
	private Date firstReviewDate; //初评时间
	
	@Column(name="C_FIRST_REVIEW_RESULT")
	private Integer firstReviewResult; //初评结果[0：未审核；1：不同意；2：同意；]
	
	@Column(name="C_FIRST_REVIEW_OPINION", length=800)
	private String firstReviewOpinion; //初评意见
	
	@ManyToOne
	@JoinColumn(name="C_SECOND_REVIEWER_ID")
	private Person secondReviewer; //复评人[专家]id（FK）
	
	@Column(name="C_SECOND_REVIEWER_NAME", length=200)
	private String secondReviewerName; //复评人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_SECOND_REVIEW_DATE")
	private Date secondReviewDate; //复评时间
	
	@ManyToOne
	@JoinColumn(name="C_SECOND_REVIEW_RESULT_ID")
	private SystemOption secondReviewResult; //复评结果[奖项等级]（FK）
	
	@Column(name="C_SECOND_REVIEW_OPINION", length=800)
	private String secondReviewOpinion; //复评意见
	
	@ManyToOne
	@JoinColumn(name="C_FINAL_AUDITOR_ID")
	private Person finalAuditor; //终审人[管理员]id（FK）
	
	@Column(name="C_FINAL_AUDITOR_NAME", length=200)
	private String finalAuditorName; //终审人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_FINAL_AUDIT_DATE")
	private Date finalAuditDate; //终审时间
	
	@Column(name="C_FINAL_AUDIT_STATUS")
	private Integer finalAuditStatus; //终审状态[0：未审核；1：不同意；2：同意；3：退回]
	
	@Column(name="C_FINAL_AUDIT_OPINION", length=800)
	private String finalAuditOpinion; //终审意见
	
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

	/*public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}*/

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public SystemOption getDiscipline() {
		return discipline;
	}

	public void setDiscipline(SystemOption discipline) {
		this.discipline = discipline;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getSession() {
		return session;
	}

	public void setSession(Integer session) {
		this.session = session;
	}

	public Person getApplicant() {
		return applicant;
	}

	public void setApplicant(Person applicant) {
		this.applicant = applicant;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}

	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}

	public Integer getApplicantStatus() {
		return applicantStatus;
	}

	public void setApplicantStatus(Integer applicantStatus) {
		this.applicantStatus = applicantStatus;
	}

	public Integer getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(Integer applicantType) {
		this.applicantType = applicantType;
	}

	public Person getFirstAuditor() {
		return firstAuditor;
	}

	public void setFirstAuditor(Person firstAuditor) {
		this.firstAuditor = firstAuditor;
	}

	public String getFirstAuditorName() {
		return firstAuditorName;
	}

	public void setFirstAuditorName(String firstAuditorName) {
		this.firstAuditorName = firstAuditorName;
	}

	public Date getFirstAuditDate() {
		return firstAuditDate;
	}

	public void setFirstAuditDate(Date firstAuditDate) {
		this.firstAuditDate = firstAuditDate;
	}

	public Integer getFirstAuditStatus() {
		return firstAuditStatus;
	}

	public void setFirstAuditStatus(Integer firstAuditStatus) {
		this.firstAuditStatus = firstAuditStatus;
	}

	public String getFirstAuditOpinion() {
		return firstAuditOpinion;
	}

	public void setFirstAuditOpinion(String firstAuditOpinion) {
		this.firstAuditOpinion = firstAuditOpinion;
	}

	public Person getFirstReviewer() {
		return firstReviewer;
	}

	public void setFirstReviewer(Person firstReviewer) {
		this.firstReviewer = firstReviewer;
	}

	public String getFirstReviewerName() {
		return firstReviewerName;
	}

	public void setFirstReviewerName(String firstReviewerName) {
		this.firstReviewerName = firstReviewerName;
	}

	public Date getFirstReviewDate() {
		return firstReviewDate;
	}

	public void setFirstReviewDate(Date firstReviewDate) {
		this.firstReviewDate = firstReviewDate;
	}

	public Integer getFirstReviewResult() {
		return firstReviewResult;
	}

	public void setFirstReviewResult(Integer firstReviewResult) {
		this.firstReviewResult = firstReviewResult;
	}

	public String getFirstReviewOpinion() {
		return firstReviewOpinion;
	}

	public void setFirstReviewOpinion(String firstReviewOpinion) {
		this.firstReviewOpinion = firstReviewOpinion;
	}

	public Person getSecondReviewer() {
		return secondReviewer;
	}

	public void setSecondReviewer(Person secondReviewer) {
		this.secondReviewer = secondReviewer;
	}

	public String getSecondReviewerName() {
		return secondReviewerName;
	}

	public void setSecondReviewerName(String secondReviewerName) {
		this.secondReviewerName = secondReviewerName;
	}

	public Date getSecondReviewDate() {
		return secondReviewDate;
	}

	public void setSecondReviewDate(Date secondReviewDate) {
		this.secondReviewDate = secondReviewDate;
	}

	public SystemOption getSecondReviewResult() {
		return secondReviewResult;
	}

	public void setSecondReviewResult(SystemOption secondReviewResult) {
		this.secondReviewResult = secondReviewResult;
	}

	public String getSecondReviewOpinion() {
		return secondReviewOpinion;
	}

	public void setSecondReviewOpinion(String secondReviewOpinion) {
		this.secondReviewOpinion = secondReviewOpinion;
	}

	public Person getFinalAuditor() {
		return finalAuditor;
	}

	public void setFinalAuditor(Person finalAuditor) {
		this.finalAuditor = finalAuditor;
	}

	public String getFinalAuditorName() {
		return finalAuditorName;
	}

	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}

	public Date getFinalAuditDate() {
		return finalAuditDate;
	}

	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}

	public Integer getFinalAuditStatus() {
		return finalAuditStatus;
	}

	public void setFinalAuditStatus(Integer finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}

	public String getFinalAuditOpinion() {
		return finalAuditOpinion;
	}

	public void setFinalAuditOpinion(String finalAuditOpinion) {
		this.finalAuditOpinion = finalAuditOpinion;
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

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
}
