package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 各类项目评审表
 * @author fengcl
 */
public class ProjectApplicationReview {
	private Integer id;
	private ProjectApplication project;
	private Expert reviewer;
	private Integer priority;//优先级
	private Integer isManual;//是否是手工匹配的
	private Integer year;
	private Date matchTime = new Date();
	private String type;//项目类型 general；instp
	private Double score;//评审分数
	private String grade;//建议等级
	private String opinion;//评审意见
	private Date date;//评审时间
	private String qualitativeOpinion;//定性意见
	private String reviewerName;//评审人姓名
	private String agencyName;//单位名称
	private String divisionName;//部门名称
	private Integer reviewerType;//评审人类型：0默认，1教师，	2外部专家
	private String idcardType;//证件类别
	private String idcardNumber;//证件号
	private String gender;//性别
	private Integer divisionType;//部门类别[1研究基地， 2院系, 3外部单位]
	private Double innovationScore;//创新和突破得分(满分50分)
	private Double scientificScore;//科学性和规范性得分(满分25分)
	private Double benefitScore;//价值和效益得分(满分25分)
	
	public Integer getIsManual() {
		return isManual;
	}
	public void setIsManual(Integer isManual) {
		this.isManual = isManual;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public ProjectApplication getProject() {
		return project;
	}
	public void setProject(ProjectApplication project) {
		this.project = project;
	}
	@JSON(serialize=false)
	public Expert getReviewer() {
		return reviewer;
	}
	public void setReviewer(Expert reviewer) {
		this.reviewer = reviewer;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	public Integer getReviewerType() {
		return reviewerType;
	}
	public void setReviewerType(Integer reviewerType) {
		this.reviewerType = reviewerType;
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
	public ProjectApplicationReview(){}

	/**
	 * 匹配结果快速构造器
	 * @param reviewerId 评审家Id
	 * @param projectId 评审对象Id
	 * @param isManual 是否手动添加结果 0：自动；1：手工
	 * @param year 所属年份
	 */
	public ProjectApplicationReview(Object reviewerId, Object projectId, Object isManual, Object year, Object type){
		this.reviewer = new Expert();
		this.reviewer.setId((String) reviewerId);
		this.isManual = (Integer)isManual;
		this.year = (Integer)year;
		this.project = new ProjectApplication();
		this.project.setId((String) projectId);
		this.type = (String) type;
	}


}
