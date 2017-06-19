package csdc.model;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_PERSON")
public class Person implements java.io.Serializable {
	private static final long serialVersionUID = -4428555607314534636L;

	@Id	
	@Column(name="C_ID", unique = true, nullable = false, length=40)
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
	private String id; //人员信息id（PK）
	
	@Column(name="C_NAME", length=200)
	private String name; //中文名[汉字]
	
	@Column(name="C_ENGLISH_NAME", length=200)
	private String englishName; //英文名[可包含字母、空格“.”，区分大小写]
	
	@Column(name="C_USED_NAME", length=200)
	private String usedName; //曾用名[同中文名/英文名]
	
	@Column(name="C_POSITION", length=80)
	private String position; //行政职务
	
	@ManyToOne
	@JoinColumn(name="C_AGENCY_ID", nullable = false)
	private SystemOption agencyId; //机构（高校）代码（FK）
	
	@Column(name="C_AGENCY_NAME", length=400)
	private String agencyName; //机构（高校）名称
	
	@Column(name="C_DEPARTMENT", length=400)
	private String department; //所在部门（院系）
	
	@Column(name="C_PHOTO_FILE", length=100)
	private String photoFile; //照片文件路径
	
	@Column(name="C_GENDER", length=40)
	private String gender; //性别[男、女]
	
	@Column(name="C_COUNTRY_REGION", length=80)
	private String countryRegion; //国家或地区[略]
	
	@Column(name="C_ETHNIC", length=80)
	private String ethnic; //民族[略]
	
	@Column(name="C_BIRTHPLACE", length=80)
	private String birthplace; //籍贯[略]
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="C_BIRTHDAY")
	private Date birthday; //出生日期
	
	@Column(name="C_MEMBERSHIP", length=80)
	private String membership; //政治面貌[中共党员、共青团员、群众、民主党派成员]
	
	@Column(name="C_HOME_PHONE", length=400)
	private String homePhone; //住宅电话
	
	@Column(name="C_OFFICE_PHONE", length=400)
	private String officePhone; //办公电话
	
	@Column(name="C_OFFICE_FAX", length=400)
	private String officeFax; //办公传真
	
	@Column(name="C_EMAIL", length=400)
	private String email; //邮箱
	
	@Column(name="C_MOBILE_PHONE", length=400)
	private String mobilePhone; //手机
	
	@Column(name="C_QQ", length=40)
	private String qq; //QQ
	
	@Column(name="C_MSN", length=200)
	private String msn; //MSN
	
	@Column(name="C_HOMEPAGE", length=100)
	private String homepage; //主页
	
	@Column(name="C_IDCARD_TYPE", length=40)
	private String idcardType; //证件类型[身份证、军官证、护照、其他]
	
	@Column(name="C_IDCARD_NUMBER", length=80)
	private String idcardNumber; //证件号码[身份证号可包含数字、大写字母]
	
	@Column(name="C_INTRODUCTION")
	private String introduction; //个人简介
	
	@Column(name="C_HOME_FAX", length=400)
	private String homeFax; //住宅传真[0开头区号-传真号码，0开头区号共3或4字节，传真号码6-8字节]
	
	@Column(name="C_CREATE_TYPE")
	private Integer createType; //创建类型[0：系统已有；1：新注册；2：系统已有且被注册]
	
	@Column(name="C_PEN_NAME", length=200)
	private String penName; //笔名
	
	@Column(name="C_PHOTO_DFS", length=100)
	private String photoDfs; //照片DFS存储路径，为一个uuid值，该值为DMSS中文档ID
	
	@Column(name="C_BANK_ACCOUNT_IDS", length=40)
	private String bankAccountIds; //银行账号组id
	
	@Column(name="C_HOME_ADDRESS_IDS", length=40)
	private String homeAddressIds; //住宅地址组id
	
	@Column(name="C_OFFICE_ADDRESS_IDS", length=40)
	private String officeAddressIds; //办公地址组id
	
	@Column(name="C_CREATE_MODE")
	private Integer createMode; //数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	
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

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public SystemOption getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(SystemOption agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getHomeFax() {
		return homeFax;
	}

	public void setHomeFax(String homeFax) {
		this.homeFax = homeFax;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName(String penName) {
		this.penName = penName;
	}

	public String getPhotoDfs() {
		return photoDfs;
	}

	public void setPhotoDfs(String photoDfs) {
		this.photoDfs = photoDfs;
	}

	public String getBankAccountIds() {
		return bankAccountIds;
	}

	public void setBankAccountIds(String bankAccountIds) {
		this.bankAccountIds = bankAccountIds;
	}

	public String getHomeAddressIds() {
		return homeAddressIds;
	}

	public void setHomeAddressIds(String homeAddressIds) {
		this.homeAddressIds = homeAddressIds;
	}

	public String getOfficeAddressIds() {
		return officeAddressIds;
	}

	public void setOfficeAddressIds(String officeAddressIds) {
		this.officeAddressIds = officeAddressIds;
	}

	public Integer getCreateMode() {
		return createMode;
	}

	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
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
