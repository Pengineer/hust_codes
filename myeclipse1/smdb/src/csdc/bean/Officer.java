package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Officer implements java.io.Serializable {

	private static final long serialVersionUID = -847354414282511764L;
	private String id;// ID
	private Person person;// 人员主表
	private String position;// 行政职务
	private String staffCardNumber;// 工作证号
	private Date startDate;// 定职时间
	private Date endDate;// 离职时间
	private String type;// 人员类型
	private Agency agency;// 当前所属管理机构
	private Department department;// 当前所在部门
	private Institute institute;// 当前所属研究机构
	private Integer organ;// 所属学校或者社科处    1 学校    2 社科处
	private String divisionName;//部门名称
	private String agencyName;//机构名称
	
	public String getStaffCardNumber() {
		return staffCardNumber;
	}
	public void setStaffCardNumber(String staffCardNumber) {
		this.staffCardNumber = staffCardNumber;
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
	@JSON(serialize=false)
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public Integer getOrgan() {
		return organ;
	}
	public void setOrgan(Integer organ) {
		this.organ = organ;
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
}
