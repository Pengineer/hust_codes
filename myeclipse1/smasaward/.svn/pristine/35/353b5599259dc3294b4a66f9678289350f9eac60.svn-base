package csdc.bean;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 项目表
 * @author fengcl
 *
 */
public class ProjectApplication implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String universityCode;	//高校代码
	private String universityName;	//高校名称
	private String discipline;		//学科代码
	private Integer year;				//年份
	private String members;			//项目成员
	private String director;			//项目负责人
	
	private String warningReviewer;	//评审警告信息
	
	private String number;			//项目编号 [对应社科网的项目编号信息]
	private String sinossID;	//社科网项目申报id[对应社科网的项目项目申报id]
	
	private String projectName;		//项目名称
	private String projectType;		//项目类别,对smdb段的项目子类
	private String disciplineType;	//学科类型
	private String warning;			//警告信息
	private String reason;			//退评原因
	
	private Integer isReviewable;			//是否参评
	private Integer isGranted;			//是否立项
	private Date applyDate;			//申请日期
	private Date planEndDate;			//计划结束日期
	private String file;				//申报文件
	private String auditStatus;		//审核状态
	//TODO 最终成果形式改用两个字段表示，与smdb保持一致
	private String finalResultType;	//最终成果形式
	private String productType;//成果形式
	private String productTypeOther;//其他成果形式
	
	private String job;				//职务
	private Date birthday;			//生日
	private String title;				//项目负责人职称
	private String note;				//备注
	
	private String firstAuditResult;	//初审结果
	private Date firstAuditDate;		//初审时间
	
	//部级初评审核后信息smas保留字段，同步至smdb
	
	private String ministryAuditorName;//部级审核人姓名（excel导入）
	private User ministryAuditor;//部级审核人id（excel导入）
	private Integer ministryAuditStatus;//部级审核状态（excel导入）
	private Integer ministryAuditResult;//部级审核结果（excel导入）
	private Date ministryAuditDate;//部级审核时间（excel导入）
	private String ministryAuditOpinion;//部级审核意见（excel导入）
	
	
	private Set<ProjectApplicationReview> applicationReview;
	private Set<ProjectApplicationReviewUpdate> applicationReviewUpdate;
	
	private String idcard;
	private String email;
	private String mobile;
	private String phone;
	private String postcode;
	private String address;
	private String foreign;
	private String degree;
	private String education;
	private String department;
	private String gender;
	
	private String otherFee;
	private String applyFee;
	private String researchType;// 研究类型
	private String topic;// 项目主题[不需要同步]
	private String subType;// 项目子类[不需要同步]topic,subtype只存在于“type=general,且 projectType=专项项目”，这项目不需要同步
	private Date addDate;
	private Integer isGranting;//拟立项标志
	private Integer voteNumber;//投票数
	private Double score;//评审分数
	
	private String informTitle;//举报标题
	private String informContent;//举报事由
	private String informer;//举报人
	private String informerPhone;//举报人电话
	private String informerEmail;//举报人邮箱
	private Date informDate;//举报时间
	private Date publicDate;//项目公示时间
	private Date grantedDate;//项目立项时间
	private String approvalNumber;//项目批准号
	private String approveFee;//项目批准经费
	
	//基地特有
	private String instituteName;		//基地名称
	private String disciplineDirection;	//学科方向
	private String directorUniversity;	//项目负责人所在高校名称
	private String directorDivisionName;	//负责人所属部门（院系/基地）名称
	
	private String type;//项目类型 general instp
	
	//最终审核结果及意见
	private Integer finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人姓名
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private User finalAuditor;//最终审核人id

	//专家评审信息
	private String reviewerName;//评审人姓名
	private Date reviewDate;//评审时间
	private Double reviewTotalScore;//评审总分
	private Double reviewAverageScore;//评审均分
	private Integer reviewWay;//评审方式：0默认，1 通讯评审，2 会议评审
	private Integer reviewResult;//评审结果
	private String reviewOpinion;//评审意见
	private String reviewGrade;//等级
	private String reviewOpinionQualitative;//定性意见
	private ProjectFee projectFee;//经费概算明细
	
	private Integer isImported;//是否导入数据：[0：走流程smas同步；1：smas直接录入产生]
	private Date importedDate;//导入时间
	
	
	public ProjectApplication(){}

	public ProjectApplication(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUniversityCode() {
		return universityCode;
	}
	public void setUniversityCode(String universityCode) {
		this.universityCode = universityCode;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getIsReviewable() {
		return isReviewable;
	}
	public void setIsReviewable(Integer isReviewable) {
		this.isReviewable = isReviewable;
	}
	public Integer getIsGranted() {
		return isGranted;
	}
	public void setIsGranted(Integer isGranted) {
		this.isGranted = isGranted;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getFinalResultType() {
		return finalResultType;
	}
	public void setFinalResultType(String finalResultType) {
		this.finalResultType = finalResultType;
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeOther() {
		return productTypeOther;
	}
	public void setProductTypeOther(String productTypeOther) {
		this.productTypeOther = productTypeOther;
	}
	public String getFirstAuditResult() {
		return firstAuditResult;
	}
	public void setFirstAuditResult(String firstAuditResult) {
		this.firstAuditResult = firstAuditResult;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getFirstAuditDate() {
		return firstAuditDate;
	}
	public void setFirstAuditDate(Date firstAuditDate) {
		this.firstAuditDate = firstAuditDate;
	}
	public String getWarningReviewer() {
		return warningReviewer;
	}
	public void setWarningReviewer(String warningReviewer) {
		this.warningReviewer = warningReviewer;
	}
	
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getForeign() {
		return foreign;
	}

	public void setForeign(String foreign) {
		this.foreign = foreign;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	public String getApplyFee() {
		return applyFee;
	}

	public void setApplyFee(String applyFee) {
		this.applyFee = applyFee;
	}

	public String getResearchType() {
		return researchType;
	}

	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getIsGranting() {
		return isGranting;
	}

	public void setIsGranting(Integer isGranting) {
		this.isGranting = isGranting;
	}

	public Integer getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(Integer voteNumber) {
		this.voteNumber = voteNumber;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getInformTitle() {
		return informTitle;
	}

	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}

	public String getInformContent() {
		return informContent;
	}

	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}

	public String getInformer() {
		return informer;
	}

	public void setInformer(String informer) {
		this.informer = informer;
	}

	public String getInformerPhone() {
		return informerPhone;
	}

	public void setInformerPhone(String informerPhone) {
		this.informerPhone = informerPhone;
	}

	public String getInformerEmail() {
		return informerEmail;
	}

	public void setInformerEmail(String informerEmail) {
		this.informerEmail = informerEmail;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getGrantedDate() {
		return grantedDate;
	}

	public void setGrantedDate(Date grantedDate) {
		this.grantedDate = grantedDate;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getApproveFee() {
		return approveFee;
	}

	public void setApproveFee(String approveFee) {
		this.approveFee = approveFee;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getDisciplineDirection() {
		return disciplineDirection;
	}

	public void setDisciplineDirection(String disciplineDirection) {
		this.disciplineDirection = disciplineDirection;
	}

	public String getDirectorUniversity() {
		return directorUniversity;
	}

	public void setDirectorUniversity(String directorUniversity) {
		this.directorUniversity = directorUniversity;
	}

	public String getDirectorDivisionName() {
		return directorDivisionName;
	}

	public void setDirectorDivisionName(String directorDivisionName) {
		this.directorDivisionName = directorDivisionName;
	}

	
	public Integer getFinalAuditResult() {
		return finalAuditResult;
	}

	public void setFinalAuditResult(Integer finalAuditResult) {
		this.finalAuditResult = finalAuditResult;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getFinalAuditDate() {
		return finalAuditDate;
	}

	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}

	public String getFinalAuditOpinion() {
		return finalAuditOpinion;
	}

	public void setFinalAuditOpinion(String finalAuditOpinion) {
		this.finalAuditOpinion = finalAuditOpinion;
	}

	public String getFinalAuditOpinionFeedback() {
		return finalAuditOpinionFeedback;
	}

	public void setFinalAuditOpinionFeedback(String finalAuditOpinionFeedback) {
		this.finalAuditOpinionFeedback = finalAuditOpinionFeedback;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Double getReviewTotalScore() {
		return reviewTotalScore;
	}

	public void setReviewTotalScore(Double reviewTotalScore) {
		this.reviewTotalScore = reviewTotalScore;
	}

	public Double getReviewAverageScore() {
		return reviewAverageScore;
	}

	public void setReviewAverageScore(Double reviewAverageScore) {
		this.reviewAverageScore = reviewAverageScore;
	}

	public Integer getReviewWay() {
		return reviewWay;
	}

	public void setReviewWay(Integer reviewWay) {
		this.reviewWay = reviewWay;
	}

	public String getReviewOpinion() {
		return reviewOpinion;
	}

	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}

	public String getReviewGrade() {
		return reviewGrade;
	}

	public void setReviewGrade(String reviewGrade) {
		this.reviewGrade = reviewGrade;
	}

	public String getReviewOpinionQualitative() {
		return reviewOpinionQualitative;
	}

	public void setReviewOpinionQualitative(String reviewOpinionQualitative) {
		this.reviewOpinionQualitative = reviewOpinionQualitative;
	}

	@JSON(serialize = false)
	public ProjectFee getProjectFee() {
		return projectFee;
	}

	public void setProjectFee(ProjectFee projectFee) {
		this.projectFee = projectFee;
	}

	public String getFinalAuditorName() {
		return finalAuditorName;
	}

	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
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

	public void setReviewResult(Integer reviewResult) {
		this.reviewResult = reviewResult;
	}

	public void setIsImported(Integer isImported) {
		this.isImported = isImported;
	}

	public Integer getReviewResult() {
		return reviewResult;
	}

	public Integer getIsImported() {
		return isImported;
	}

	public String getSinossID() {
		return sinossID;
	}

	public void setSinossID(String sinossID) {
		this.sinossID = sinossID;
	}

	public Integer getMinistryAuditStatus() {
		return ministryAuditStatus;
	}

	public void setMinistryAuditStatus(Integer ministryAuditStatus) {
		this.ministryAuditStatus = ministryAuditStatus;
	}

	public Integer getMinistryAuditResult() {
		return ministryAuditResult;
	}

	public void setMinistryAuditResult(Integer ministryAuditResult) {
		this.ministryAuditResult = ministryAuditResult;
	}

	public String getMinistryAuditorName() {
		return ministryAuditorName;
	}

	public void setMinistryAuditorName(String ministryAuditorName) {
		this.ministryAuditorName = ministryAuditorName;
	}

	public User getMinistryAuditor() {
		return ministryAuditor;
	}

	public void setMinistryAuditor(User ministryAuditor) {
		this.ministryAuditor = ministryAuditor;
	}

	public Date getMinistryAuditDate() {
		return ministryAuditDate;
	}

	public void setMinistryAuditDate(Date ministryAuditDate) {
		this.ministryAuditDate = ministryAuditDate;
	}

	public String getMinistryAuditOpinion() {
		return ministryAuditOpinion;
	}

	public void setMinistryAuditOpinion(String ministryAuditOpinion) {
		this.ministryAuditOpinion = ministryAuditOpinion;
	}
	
	public User getFinalAuditor() {
		return finalAuditor;
	}

	public void setFinalAuditor(User finalAuditor) {
		this.finalAuditor = finalAuditor;
	}
}
