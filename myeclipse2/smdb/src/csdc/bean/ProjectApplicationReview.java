package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

public abstract class ProjectApplicationReview{
	private String id;//id
	private String projectType;//项目类型
	private Person reviewer;//评审专家
	private Integer isManual;//是否手动分配：1是，0否
	private Integer reviewerSn;//专家序号
	private Integer submitStatus;//提交状态：0默认，2暂存，3提交
	//@CheckSystemOptionStandard("reviewGrade")
	private SystemOption grade;//建议等级
	private Double score;//评审分数
	private String opinion;//评审意见
	private Date date;//评审时间
	private String qualitativeOpinion;//定性意见
	private String reviewerName;//评审人姓名
	private Agency university;//评审专家所在高校
	private Department department;//评审专家所在院系
	private Institute institute;//评审专家所在研究机构
	private String agencyName;//单位名称
	private String divisionName;//部门名称
	private int reviewType;//评审类型：0默认，1专家，2教育部，3省厅，4高校
	private int reviewerType;//评审人类型：0默认，1教师，	2外部专家
	private String idcardType;//证件类别
	private String idcardNumber;//证件号
	private String gender;//性别
	private Integer divisionType;//部门类别[1研究基地， 2院系, 3外部单位]
	private Double innovationScore;//创新和突破得分(满分50分)
	private Double scientificScore;//科学性和规范性得分(满分25分)
	private Double benefitScore;//价值和效益得分(满分25分)
	
	private String applicationId;

//	/**
//	 * 获取项目申报对象
//	 */
//	@JSON(serialize=false)
//	public abstract ProjectApplication getApplication();
//
//	/**
//	 * 关联项目结项对象
//	 */
//	public abstract void setEndinspection(ProjectEndinspection endinspection);
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public abstract String getApplicationReviewClassName();
	
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
	public Person getReviewer() {
		return reviewer;
	}
	public void setReviewer(Person reviewer) {
		this.reviewer = reviewer;
	}
	public Integer getIsManual() {
		return isManual;
	}
	public void setIsManual(Integer isManual) {
		this.isManual = isManual;
	}
	public Integer getReviewerSn() {
		return reviewerSn;
	}
	public void setReviewerSn(Integer reviewerSn) {
		this.reviewerSn = reviewerSn;
	}
	public Integer getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}
	@JSON(serialize=false)
	public SystemOption getGrade() {
		return grade;
	}
	public void setGrade(SystemOption grade) {
		this.grade = grade;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getQualitativeOpinion() {
		return qualitativeOpinion;
	}
	public void setQualitativeOpinion(String qualitativeOpinion) {
		this.qualitativeOpinion = qualitativeOpinion;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
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
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
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
	public int getReviewType() {
		return reviewType;
	}
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
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

	public Integer getDivisionType() {
		return divisionType;
	}

	public void setDivisionType(Integer divisionType) {
		this.divisionType = divisionType;
	}

	public Double getInnovationScore() {
		return innovationScore;
	}

	public void setInnovationScore(Double innovationScore) {
		this.innovationScore = innovationScore;
	}

	public Double getScientificScore() {
		return scientificScore;
	}

	public void setScientificScore(Double scientificScore) {
		this.scientificScore = scientificScore;
	}

	public Double getBenefitScore() {
		return benefitScore;
	}

	public void setBenefitScore(Double benefitScore) {
		this.benefitScore = benefitScore;
	}

	public int getReviewerType() {
		return reviewerType;
	}

	public void setReviewerType(int reviewerType) {
		this.reviewerType = reviewerType;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	

}