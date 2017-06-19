package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectEndinspection implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String certificate;//结项证书编号
	private String file;//终结报告书文件
	private Date applicantSubmitDate;//结项申请人申请时间
	private int isApplyNoevaluation;//是否申请免鉴定：1是，0否
	private int isApplyExcellent;//是否申请项目优秀成果：1是，0否
	private Integer finalProductType;//最终成果形式[1论文，2著作，3研究咨询报告，4电子出版物]//（暂不同步，smdb无数据）
	private String memberName;//主要参加人姓名
	private String ministryAuditorName;//部级审核人名称
	private Date ministryAuditDate;//部级审核时间
	private String ministryAuditOpinion;//部级审核意见
	private int ministryResultEnd;//部级结项审核结果
	private int ministryResultNoevaluation;//部级免鉴定审核结果
	private int ministryResultExcellent;//部级优秀成果审核结果
	private String finalAuditorName;//最终审核人姓名
	private Date finalAuditDate;//最终审核时间
	private int finalAuditResultEnd;//结项最终审核结果
	private int finalAuditResultNoevaluation;//免鉴定最终审核结果
	private int finalAuditResultExcellent;//优秀成果最终审核结果
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private String reviewerName;//评审人姓名
	private Date reviewDate;//评审时间
	private Double reviewTotalScore;//总分
	private Double reviewAverageScore;//平均分
	private Integer reviewWay;//评审方式：0默认，1 通讯评审，2 会议评审
	private int reviewResult;//评审结果
	private String reviewOpinion;//评审意见
	private String reviewGrade;//等级
	private String reviewOpinionQualitative;//定性意见
	private String type;//项目类型 general instp
	
	private String importedProductInfo;//导入/录入结项成果统计信息（成果形式/成果总数/满足结项要求的成果数量，多个用英文分号与空格隔开，如：论文/2/1; 著作/1/1）
	private String importedProductTypeOther;//导入/录入结项其他成果形式
	
	private int isImported;//是否导入数据：[0：走流程smas同步；1：smas直接录入产生]
	private Date importedDate;//导入时间
	
	private ProjectFee projectFee;//经费概算明细		
	private ProjectGranted granted;//项目立项

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}
	public int getIsApplyNoevaluation() {
		return isApplyNoevaluation;
	}
	public void setIsApplyNoevaluation(int isApplyNoevaluation) {
		this.isApplyNoevaluation = isApplyNoevaluation;
	}
	public int getIsApplyExcellent() {
		return isApplyExcellent;
	}
	public void setIsApplyExcellent(int isApplyExcellent) {
		this.isApplyExcellent = isApplyExcellent;
	}
	public Integer getFinalProductType() {
		return finalProductType;
	}
	public void setFinalProductType(Integer finalProductType) {
		this.finalProductType = finalProductType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMinistryAuditorName() {
		return ministryAuditorName;
	}
	public void setMinistryAuditorName(String ministryAuditorName) {
		this.ministryAuditorName = ministryAuditorName;
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
	public int getMinistryResultEnd() {
		return ministryResultEnd;
	}
	public void setMinistryResultEnd(int ministryResultEnd) {
		this.ministryResultEnd = ministryResultEnd;
	}
	public int getMinistryResultNoevaluation() {
		return ministryResultNoevaluation;
	}
	public void setMinistryResultNoevaluation(int ministryResultNoevaluation) {
		this.ministryResultNoevaluation = ministryResultNoevaluation;
	}
	public int getMinistryResultExcellent() {
		return ministryResultExcellent;
	}
	public void setMinistryResultExcellent(int ministryResultExcellent) {
		this.ministryResultExcellent = ministryResultExcellent;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getFinalAuditDate() {
		return finalAuditDate;
	}
	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}
	public int getFinalAuditResultEnd() {
		return finalAuditResultEnd;
	}
	public void setFinalAuditResultEnd(int finalAuditResultEnd) {
		this.finalAuditResultEnd = finalAuditResultEnd;
	}
	public int getFinalAuditResultNoevaluation() {
		return finalAuditResultNoevaluation;
	}
	public void setFinalAuditResultNoevaluation(int finalAuditResultNoevaluation) {
		this.finalAuditResultNoevaluation = finalAuditResultNoevaluation;
	}
	public int getFinalAuditResultExcellent() {
		return finalAuditResultExcellent;
	}
	public void setFinalAuditResultExcellent(int finalAuditResultExcellent) {
		this.finalAuditResultExcellent = finalAuditResultExcellent;
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
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFinalAuditorName() {
		return finalAuditorName;
	}
	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}
	public int getIsImported() {
		return isImported;
	}
	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}
	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}
	@JSON(serialize=false)
	public ProjectFee getProjectFee() {
		return projectFee;
	}
	public void setProjectFee(ProjectFee projectFee) {
		this.projectFee = projectFee;
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public ProjectGranted getGranted() {
		return granted;
	}
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = granted;
	}
	public String getImportedProductInfo() {
		return importedProductInfo;
	}
	public void setImportedProductInfo(String importedProductInfo) {
		this.importedProductInfo = importedProductInfo;
	}
	public String getImportedProductTypeOther() {
		return importedProductTypeOther;
	}
	public void setImportedProductTypeOther(String importedProductTypeOther) {
		this.importedProductTypeOther = importedProductTypeOther;
	}
}
