package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 项目成员基类
 */
public abstract class ProjectMember{

	private String id;
	private String projectType;//项目类型
	private Institute institute;//所在研究机构
	private Agency university;//所在高校
	private Department department;//所在院系
	private Person member;//成员
	private String memberName;//成员姓名
	private String agencyName;//单位名称
	private String divisionName;//部门名称
	private String specialistTitle;//职称
	private String major;//专业
	private int isDirector;//是否负责人:1是；0否
	private int memberSn;//成员序号，从1开始
	private Integer workMonthPerYear;// 每年工作月数
	private String workDivision;//分工情况
	private Integer memberType; //成员类型:1教师;2专家;3学生
	private String idcardType;// 证件类型
	private String idcardNumber;// 证件号
	private String gender;// 性别
	private Integer groupNumber;//组编号
	private Integer divisionType;//部门类别[1研究基地， 2院系]
	private String applicationId;
	private Date birthday;
	private String position;
	private String education;
	private Integer type;
	
//	/**
//	 * 获取项目申请对象
//	 */
//	@JSON(serialize=false)
//	public abstract ProjectApplication getApplication();
	
//	/**
//	 * 关联项目申请对象
//	 */
//	public abstract void setApplication(ProjectApplication application);
	
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public abstract String getMemberClassName();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	@JSON(serialize=false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Person getMember() {
		return member;
	}
	public void setMember(Person member) {
		this.member = member;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public String getSpecialistTitle() {
		return specialistTitle;
	}
	public void setSpecialistTitle(String specialistTitle) {
		this.specialistTitle = specialistTitle;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public int getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(int isDirector) {
		this.isDirector = isDirector;
	}
	public int getMemberSn() {
		return memberSn;
	}
	public void setMemberSn(int memberSn) {
		this.memberSn = memberSn;
	}
	public String getWorkDivision() {
		return workDivision;
	}
	public void setWorkDivision(String workDivision) {
		this.workDivision = workDivision;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getWorkMonthPerYear() {
		return workMonthPerYear;
	}
	public void setWorkMonthPerYear(Integer workMonthPerYear) {
		this.workMonthPerYear = workMonthPerYear;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	public Integer getDivisionType() {
		return divisionType;
	}
	public void setDivisionType(Integer divisionType) {
		this.divisionType = divisionType;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}

}