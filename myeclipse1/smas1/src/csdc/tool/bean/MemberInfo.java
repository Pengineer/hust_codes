package csdc.tool.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MemberInfo {
	
	private Integer memberSn;//成员序号
	private Integer memberType; //成员类型:1教师;2专家;3学生
	private String memberName;//成员姓名
	private String gender;//性别
	private Date birthday; //生日
	private String idcardType;// 证件类型
	private String idcardNumber;// 证件号
	private String foreign;//外语
	private String education;//最后学历
	private String degree;//学位
	private String title;//职称
	private String agencyName;//单位名称
	private String divisionName;//部门名称
	private String job;//职务
	private String email;//邮箱
	private String mobile;//移动电话
	private String phone;//固定电话
	private String postcode;//邮编
	private String address;//地址
	private int isDirector;//是否负责人:1是；0否
	
	public Integer getMemberSn() {
		return memberSn;
	}
	public void setMemberSn(Integer memberSn) {
		this.memberSn = memberSn;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
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
	public int getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(int isDirector) {
		this.isDirector = isDirector;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getForeign() {
		return foreign;
	}
	public void setForeign(String foreign) {
		this.foreign = foreign;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
