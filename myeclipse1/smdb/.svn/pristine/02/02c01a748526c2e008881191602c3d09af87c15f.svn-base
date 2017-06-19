package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Education implements java.io.Serializable {

	private static final long serialVersionUID = 2390342915787374312L;
	private String id;// ID
	private Person person;
	private String education;// 学历
	private String degree;// 学位
	private String countryRegion;// 学位国家或地区
	private String university;// 毕业学校名称
	private String department;// 毕业所属院系
	private String major;// 毕业所学专业
	private Date startDate;// 入学时间
	private Date endDate;// 学位授予（毕业）时间


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
