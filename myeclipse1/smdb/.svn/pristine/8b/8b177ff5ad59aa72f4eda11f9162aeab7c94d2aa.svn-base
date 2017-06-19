package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Teacher implements java.io.Serializable {

	private static final long serialVersionUID = -5671740335900471795L;
	private String id;// ID
	private Person person;// 人员主表
	private String staffCardNumber;// 工作证号
	private String position;// 职务
	private Integer workMonthPerYear;// 每年工作月数
	private Date startDate;// 定职时间
	private Date endDate;// 离职时间
	private String type;// 人员类型(是否兼职)
	private Department department;// 所属院系
	private Institute institute;// 所属研究机构
	private Agency university;// 所属高校
	private String divisionName;//部门名称
	private String agencyName;//机构名称
	private int createType;//0:系统已有; 1:新注册; 2:系统已有且被注册

	public Teacher(String id){
		this.id = id;
	}

	public Teacher(){}

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
	public String getStaffCardNumber() {
		return staffCardNumber;
	}
	public void setStaffCardNumber(String staffCardNumber) {
		this.staffCardNumber = staffCardNumber;
	}
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getWorkMonthPerYear() {
		return workMonthPerYear;
	}

	public void setWorkMonthPerYear(Integer workMonthPerYear) {
		this.workMonthPerYear = workMonthPerYear;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public int getCreateType() {
		return createType;
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
}
