package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 招聘申请表
 * @author suwb
 *
 */
public class Applicant implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键	
	private String idCardNumber;//身份证号
	private String email;//邮箱
	//人员信息
	private String name;//姓名
	private String gender;//性别
	private String ethnic;//民族
	private String birthplace;//籍贯（省、市、自治区）
	private Date birthday;//生日
	private String membership;//政治面貌
	private String mobilePhone;//电话
	private String qq;//qq
	private String address;//地址
	private String photoFile;//照片
	//招聘自有信息
	private String file;//附件
	private String dfs;//文件云存储位置
	private String major;//专业
	private String education;//学历
	private Date applicantDate;//申请时间
	private Date auditDate;//审核时间
	//招聘状态信息
	private int status;//申请状态[0：已申请（默认状态）；1：审核通过；2：审核不通过]
	//外键
	private Job job;//申请职位
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	@JSON(format="yyyy-MM-dd")
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		if (photoFile != null){
			photoFile = photoFile.replaceAll("^[/\\\\]+", "");
		}
		this.photoFile = photoFile;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApplicantDate() {
		return applicantDate;
	}
	public void setApplicantDate(Date applicantDate) {
		this.applicantDate = applicantDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
}
