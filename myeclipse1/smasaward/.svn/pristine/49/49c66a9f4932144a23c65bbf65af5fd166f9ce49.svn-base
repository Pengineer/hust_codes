package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Expert {
	
	private String id;
	private String name;	// 姓名
	private String universityCode;	// 高校代码
	private String gender;	// 性别
	private Date birthday;// 生日
	private String specialityTitle;// 专业职称
	private String idCardType;	// 证件类型
	private String idCardNumber;// 证件号码
	private String discipline;// 学科代码(包括三级)
	private String homePhone;// 家庭电话
	private String mobilePhone;// 手机
	private String officePhone;// 办公电话
	private String officeFax;// 办公传真
	private String email;// 邮箱
	private String moeProject;	//承担教育部项目情况
	private String nationalProject;	//承担国家社科项目情况
	private String job;// 行政职务
	private String isDoctorTutor;	//是否博导
	private String postDoctor;	//博士后（空、出站、在站）
	private String department;	//所在院系所
	private String degree;	//最后学位
	private String lastEducation;//最后学历
	private String computerLevel;//计算机操作水平 （精通、熟练、一般、较差）
	private String language;// 外语语种
	private String ethnicLanguage;//民族语言
	private String abroad;//出国经历
	private String introduction;//个人简介
	private String positionLevel;// 岗位等级
	private String award;// 人才奖励资助情况
	private String researchField;// 学术研究方向
	private String partTime;	// 学术兼职
	private String officeAddress;// 通讯地址
	private String officePostcode;// 办公邮编
	private String remark;// 备注
	private String warning;// 警告
	private Integer isReviewer;// 是否参评专家
	private Integer expertType;// 专家类型（1内部专家，2外部专家）
	private int number;// 专家编号
	private String rating;	//评价等级
	private String generalReviewYears;	//过往参评年份
	private String generalApplyYears;	//过往一般项目申请年份
	private String instpApplyYears;	//过往基地项目申请年份
	private String reason;	 //退评原因
	private int isKey;// 专家编号
	private Date importedDate;// 导入时间
	private String type;	//人员类型[专职人员、兼职人员、离职人员]
	private String usedName;   //曾用名[中文名/英文名]

	private Set<ProjectApplicationReview> applicationReview;
	private Set<ProjectApplicationReviewUpdate> applicationReviewUpdate;
	
	public Expert(){}

	public Expert(String id) {
		this.id = id;
	}
	
	@Override  
	public boolean equals(Object obj) {   
		if (this == obj) return true;   
		if (obj == null) return false;   
		if (getClass() != obj.getClass()) return false;   
		final Expert expert = (Expert) obj;   
		return this.id.equals(expert.getId());
	}
	
	@Override  
	public int hashCode() {
		return this.id.hashCode();
	}   

	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
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

	public String getUniversityCode() {
		return universityCode;
	}
	public void setUniversityCode(String universityCode) {
		this.universityCode = universityCode;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSpecialityTitle() {
		return specialityTitle;
	}
	public void setSpecialityTitle(String specialityTitle) {
		this.specialityTitle = specialityTitle;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMoeProject() {
		return moeProject;
	}
	public void setMoeProject(String moeProject) {
		this.moeProject = moeProject;
	}
	public String getNationalProject() {
		return nationalProject;
	}
	public void setNationalProject(String nationalProject) {
		this.nationalProject = nationalProject;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIsDoctorTutor() {
		return isDoctorTutor;
	}
	public void setIsDoctorTutor(String isDoctorTutor) {
		this.isDoctorTutor = isDoctorTutor;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	public String getPartTime() {
		return partTime;
	}
	public void setPartTime(String partTime) {
		this.partTime = partTime;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getOfficePostcode() {
		return officePostcode;
	}
	public void setOfficePostcode(String officePostcode) {
		this.officePostcode = officePostcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsReviewer() {
		return isReviewer;
	}
	public void setIsReviewer(Integer isReviewer) {
		this.isReviewer = isReviewer;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGeneralReviewYears() {
		return generalReviewYears;
	}

	public void setGeneralReviewYears(String generalReviewYears) {
		this.generalReviewYears = generalReviewYears;
	}

	public String getGeneralApplyYears() {
		return generalApplyYears;
	}

	public void setGeneralApplyYears(String generalApplyYears) {
		this.generalApplyYears = generalApplyYears;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getIsKey() {
		return isKey;
	}
	public void setIsKey(int isKey) {
		this.isKey = isKey;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getOfficeFax() {
		return officeFax;
	}
	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
	}
	public String getPostDoctor() {
		return postDoctor;
	}
	public void setPostDoctor(String postDoctor) {
		this.postDoctor = postDoctor;
	}
	public String getLastEducation() {
		return lastEducation;
	}
	public void setLastEducation(String lastEducation) {
		this.lastEducation = lastEducation;
	}
	public String getComputerLevel() {
		return computerLevel;
	}
	public void setComputerLevel(String computerLevel) {
		this.computerLevel = computerLevel;
	}
	public String getEthnicLanguage() {
		return ethnicLanguage;
	}
	public void setEthnicLanguage(String ethnicLanguage) {
		this.ethnicLanguage = ethnicLanguage;
	}
	public String getAbroad() {
		return abroad;
	}
	public void setAbroad(String abroad) {
		this.abroad = abroad;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getExpertType() {
		return expertType;
	}

	public void setExpertType(Integer expertType) {
		this.expertType = expertType;
	}

	public String getInstpApplyYears() {
		return instpApplyYears;
	}

	public void setInstpApplyYears(String instpApplyYears) {
		this.instpApplyYears = instpApplyYears;
	}
	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}
	@JSON(serialize=false)
	public Set<ProjectApplicationReview> getApplicationReview() {
		return applicationReview;
	}

	public void setApplicationReview(Set<ProjectApplicationReview> applicationReview) {
		this.applicationReview = applicationReview;
	}
	@JSON(serialize=false)
	public Set<ProjectApplicationReviewUpdate> getApplicationReviewUpdate() {
		return applicationReviewUpdate;
	}

	public void setApplicationReviewUpdate(
			Set<ProjectApplicationReviewUpdate> applicationReviewUpdate) {
		this.applicationReviewUpdate = applicationReviewUpdate;
	}
}
