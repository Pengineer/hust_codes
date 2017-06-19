package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Student implements java.io.Serializable {

	private static final long serialVersionUID = 5195213863496889829L;
	private String id;// ID
	private Person person;// 人员主表
	private String type;// 类别
	private String status;// 学生状态[在读，毕业]
	private Date startDate;// 入学时间
	private Date endDate;// 毕业时间
	private Person tutor;// 导师
	private String project;// 参与项目
	private String thesisTitle;// 学位论文题目
	private String studentCardNumber;// 学生证号
	private Integer isExcellent;// 是否优秀学位论文
	private String excellentGrade;// 优秀学位论文等级
	private Integer excellentYear;// 优秀学位论文评选年度
	private String excellentSession;// 优秀学位论文评选届次
	private Integer thesisFee;// 论文经费
	private Department department;// 所属部门
	private Institute institute;// 所属研究机构
	private Agency university;// 所属高校
	private String divisionName;//部门名称
	private String agencyName;//机构名称

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
//	@JSON(serialize=false)
	public Person getTutor() {
		return tutor;
	}
	public void setTutor(Person tutor) {
		this.tutor = tutor;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getThesisTitle() {
		return thesisTitle;
	}
	public void setThesisTitle(String thesisTitle) {
		this.thesisTitle = thesisTitle;
	}
	public Integer getThesisFee() {
		return thesisFee;
	}
	public void setThesisFee(Integer thesisFee) {
		this.thesisFee = thesisFee;
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
	public Integer getIsExcellent() {
		return isExcellent;
	}
	public void setIsExcellent(Integer isExcellent) {
		this.isExcellent = isExcellent;
	}
	public String getExcellentGrade() {
		return excellentGrade;
	}
	public void setExcellentGrade(String excellentGrade) {
		this.excellentGrade = excellentGrade;
	}
	public Integer getExcellentYear() {
		return excellentYear;
	}
	public void setExcellentYear(Integer excellentYear) {
		this.excellentYear = excellentYear;
	}
	public String getExcellentSession() {
		return excellentSession;
	}
	public void setExcellentSession(String excellentSession) {
		this.excellentSession = excellentSession;
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
	
}
