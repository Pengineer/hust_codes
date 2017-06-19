package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Researcher implements java.io.Serializable {

	private static final long serialVersionUID = 5195213863496889829L;
	private String id;// ID
	private String type;// 类别	
	private Person person;// 人员主表
	private Date startDate;// 入学时间
	private Date endDate;// 毕业时间
	private Integer workMonthPerYearInteger;//每年工作时间（月）[0-12]
	private Institute institute;// 所属研究机构
	private Department department;// 所属部门
	private String studentCardNumber;// 学生证号
	private Agency university;// 所属高校
	private String status;// 学生状态[在读，毕业]
	private String agencyName;//学校名称
	private String divisionName;//院系/基地名称
	private Integer createType;//创建类型[0：系统已有；1：新注册；2：系统已有且被注册]

	public String getStudentCardNumber() {
		return studentCardNumber;
	}
	public void setStudentCardNumber(String studentCardNumber) {
		this.studentCardNumber = studentCardNumber;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	@JSON(serialize=false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	@JSON(serialize=false)
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public Integer getWorkMonthPerYearInteger() {
		return workMonthPerYearInteger;
	}
	public void setWorkMonthPerYearInteger(Integer workMonthPerYearInteger) {
		this.workMonthPerYearInteger = workMonthPerYearInteger;
	}
	public Integer getCreateType() {
		return createType;
	}
	public void setCreateType(Integer createType) {
		this.createType = createType;
	}
}
