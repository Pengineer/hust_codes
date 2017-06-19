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
@Table(name="T_AGENCY")
@JsonIgnoreProperties({"password"})
public class Agency implements Serializable {
	private static final long serialVersionUID = -5510630957096098893L;

	@Id	
	@Column(name="C_ID", length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id; //评审专家id（PK）
	
	@Column(name="C_NAME", length=200)  
    private String name; //机构中文名称
	
	@Column(name="C_ENGLISH_NAME", length=200)  
    private String englishName; //机构英文名称
	
	@Column(name="C_CODE", length=40)  
    private String code; //机构代码
	
	@Column(name="C_ABBR", length=40)  
    private String abbr; //名称缩写
	
	@Column(name="C_ADDRESS", length=400)  
    private String address; //所在地址
	
	@Column(name="C_DIRECTOR_NAME", length=200)  
    private String directorName; //负责人姓名
	
	@ManyToOne
	@JoinColumn(name="C_DIRECTOR_ID")
	private Person directoryId; //负责人ID（FK）
	
	@Column(name="C_POSTCODE", length=40)  
    private String postCode; //邮编
	
	@Column(name="C_MOBILE_PHONE", length=400)  
    private String mobilePhone; //手机

	@Column(name="C_OFFICE_PHONE", length=400)  
    private String officePhone; //办公电话

	@Column(name="C_OFFICE_FAX", length=400)  
    private String officeFax; //办公传真

	@Column(name="C_EMAIL", length=400)  
    private String email; //电子邮箱

	@Column(name="C_HOMEPAGE", length=100)  
    private String homePage; //机构主页

	@Column(name="C_INTRODUCTION")  
    private String introduction; //机构简介
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_CREATE_DATE")
	private Date createDate; //创建时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_UPDATE_DATE")
	private Date updateDate; //数据更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public Person getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Person directoryId) {
		this.directoryId = directoryId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
