package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

/**
 * @author 余潜玉 王燕
 */

public class AwardReview implements java.io.Serializable {

	private static final long serialVersionUID = -8902758700515280722L;
	private String id;//id
	private AwardApplication application;//奖励申请
	private Person reviewer;//评审专家
	//@CheckSystemOptionStandard("awardGrade")
	private SystemOption grade;//建议等级id
	private Integer reviewerSn;//专家序号
	private int submitStatus;//提交状态  	0默认，2暂存，3提交
	private Double meaningScore;//研究内容意义和前沿性得分(满分20分)
	private Double innovationScore;//主要创新和学术价值分数(满分30分)
	private Double influenceScore;//学术影响或效益方法得分(满分30分)
	private Double methodScore;//研究方法和学术规范得分(满分20分)
	private Double score;//评审总分
	private String opinion;//评审意见
	private Date date;//评审时间
	private String reviewerName;//评审人姓名
	private Agency university;//评审专家所在高校
	private Department department;//评审专家所在院系
	private Institute institute;//评审专家所在研究机构
	private String agencyName;//单位名称
	private String divisionName;//部门名称	
	private Integer reviewType;//评审类型：0默认，1专家，2教育部，3省厅，4高校
	private Integer reviewerType;//评审人类型
	private Integer isManual;//是否手动分配：1是，0否
	private String idcardType;//证件类别
	private String idcardNumber;//证件号
	private String gender;//性别
	private Integer divisionType;//部门类别[1研究基地， 2院系, 3外部单位]
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public AwardApplication getApplication() {
		return application;
	}
	public void setApplication(AwardApplication application) {
		this.application = application;
	}
	@JSON(serialize=false)
	public Person getReviewer() {
		return reviewer;
	}
	public void setReviewer(Person reviewer) {
		this.reviewer = reviewer;
	}
	@JSON(serialize=false)
	public SystemOption getGrade() {
		return grade;
	}
	public void setGrade(SystemOption grade) {
		this.grade = grade;
	}
	public Integer getReviewerSn() {
		return reviewerSn;
	}
	public void setReviewerSn(Integer reviewerSn) {
		this.reviewerSn = reviewerSn;
	}
	public int getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(int submitStatus) {
		this.submitStatus = submitStatus;
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
	public Double getMeaningScore() {
		return meaningScore;
	}
	public void setMeaningScore(Double meaningScore) {
		this.meaningScore = meaningScore;
	}
	public Double getInnovationScore() {
		return innovationScore;
	}
	public void setInnovationScore(Double innovationScore) {
		this.innovationScore = innovationScore;
	}
	public Double getInfluenceScore() {
		return influenceScore;
	}
	public void setInfluenceScore(Double influenceScore) {
		this.influenceScore = influenceScore;
	}
	public Double getMethodScore() {
		return methodScore;
	}
	public void setMethodScore(Double methodScore) {
		this.methodScore = methodScore;
	}
	public Integer getReviewType() {
		return reviewType;
	}
	public void setReviewType(Integer reviewType) {
		this.reviewType = reviewType;
	}
	public Integer getReviewerType() {
		return reviewerType;
	}
	public void setReviewerType(Integer reviewerType) {
		this.reviewerType = reviewerType;
	}
	public Integer getIsManual() {
		return isManual;
	}
	public void setIsManual(Integer isManual) {
		this.isManual = isManual;
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
}