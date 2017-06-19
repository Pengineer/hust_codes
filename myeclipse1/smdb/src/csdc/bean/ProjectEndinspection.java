package csdc.bean;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import csdc.tool.bean.AuditInfo;

public abstract class ProjectEndinspection {
	private String id;//主键id
	private String projectType;//项目类型
	private String certificate;//结项证书编号
	private String file;//终结报告书文件
	private int isApplyNoevaluation;//是否申请免鉴定：1是，0否
	private int isApplyExcellent;//是否申请项目优秀成果：1是，0否
	private int status;//流程状态：1新建结项申请，2院系/研究机构审核，3校级审核，4省级审核，5部级审核，6评审，7最终审核
	private int applicantSubmitStatus;//结项申请人申请状态
	private Date applicantSubmitDate;//结项申请人申请时间
	private int deptInstAuditStatus;//院系/研究机构审核状态
	private int deptInstResultEnd;//院系/研究机构结项审核结果
	private int deptInstResultNoevaluation;//院系/研究机构免鉴定审核结果
	private int deptInstResultExcellent;//院系/研究机构优秀成果审核结果
	private String deptInstAuditorName;//院系/研究机构审核人名称
	private Officer deptInstAuditor;//院系/研究机构审核人
	private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
	private Institute deptInstAuditorInst;//院系/研究机构审核人所在研究机构
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private int universityAuditStatus;//高校审核状态
	private int universityResultEnd;//高校结项审核结果
	private int universityResultNoevaluation;//高校免鉴定审核结果
	private int universityResultExcellent;//高校优秀成果审核结果
    private String universityAuditorName;//高校审核人名称
    private Officer universityAuditor;//高校审核人
    private Agency universityAuditorAgency;//高校审核人所在机构
	private Date universityAuditDate;//高校审核时间
	private String universityAuditOpinion;//高校审核意见
	private int provinceAuditStatus;//省厅审核状态
	private int provinceResultEnd;//省厅结项审核结果
	private int provinceResultNoevaluation;//省厅免鉴定审核结果
	private int provinceResultExcellent;//省厅优秀成果审核结果
    private String provinceAuditorName;//省厅审核人名称
    private Officer provinceAuditor;//省厅审核人
    private Agency provinceAuditorAgency;//省厅审核人所在机构
	private Date provinceAuditDate;//省厅审核时间
	private String provinceAuditOpinion;//省厅审核意见
	private int ministryAuditStatus;//部级审核状态
	private int ministryResultEnd;//部级结项审核结果
	private int ministryResultNoevaluation;//部级免鉴定审核结果
	private int ministryResultExcellent;//部级优秀成果审核结果
    private String ministryAuditorName;//部级审核人名称
    private Officer ministryAuditor;//部级审核人
    private Agency ministryAuditorAgency;//部级审核人所在机构
	private Date ministryAuditDate;//部级审核时间
	private String ministryAuditOpinion;//部级审核意见
	private String note;//备注
	private int reviewStatus;//评审状态
	private int reviewResult;//评审结果
	private Integer reviewWay;//评审方式：0默认，1 通讯评审，2 会议评审
	private String reviewerName;//评审人姓名
	private Officer reviewer;//评审人
	private Agency reviewerAgency;//评审人所在机构
	private Date reviewDate;//评审时间
	private String reviewOpinion;//评审意见
	private Double reviewTotalScore;//总分
	private Double reviewAverageScore;//平均分
	//@CheckSystemOptionStandard("reviewGrade")
	private SystemOption reviewGrade;//等级
	private String reviewOpinionQualitative;//定性意见
	private int finalAuditStatus;//最终审核状态
	private int finalAuditResultEnd;//结项最终审核结果
	private int finalAuditResultNoevaluation;//免鉴定最终审核结果
	private int finalAuditResultExcellent;//优秀成果最终审核结果
	private String finalAuditorName;//最终审核人名称
	private Officer finalAuditor;//最终审核人
	private Agency finalAuditorAgency;//最终审核人所在机构
	private Department finalAuditorDept;//最终审核人所在院系
	private Institute finalAuditorInst;//最终审核人所在研究机构
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private String memberName;//主要参加人姓名
	private String importedProductInfo;//导入/录入结项成果统计信息（成果形式/成果总数/满足结项要求的成果数量，多个用英文分号与空格隔开，如：论文/2/1; 著作/1/1）
	private String importedProductTypeOther;//导入/录入结项其他成果形式
	private int printCount;//打印次数
	private Integer finalProductType;//最终成果形式[1论文，2著作，3研究咨询报告，4电子出版物]
	private String finalProductId;//最终成果id
	private Set<ProjectEndinspectionProduct> projectEndinspectionProduct;//结项成果
	private ProjectFee projectFee;//结项经费明细
	private String grantedId;
	private int finalAuditResultPublish;//最终审核结果发布[0：否；1：是]
	private String dfs;//结项报告书文件云存储位置
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间
	/* 一般项目结项状态转移矩阵
	 * 第1维：0 不同意，1 同意
	 * 第2维：0  退回，1 暂存，2 提交
	 * 第3维：状态值
	 */
	private static final int[][][] STATUS_MATRIX =  new int[][][]
	{
		{
			{0, 1, 2, 3, 4, 0, 0},
			{1, 2, 3, 4, 5, 6, 7},
			{2, 2, 3, 4, 5, 7, 7}//评审不同意一样跳到上一级
		},
		{
			{0, 1, 2, 3, 4, 0, 0},
			{1, 2, 3, 4, 5, 6, 7},
			{2, 3, 4, 5, 6, 7, 7}
		}
	};
	/**
	 * 获取项目结项评审对象集合
	 */
	@JSON(serialize=false)
	public abstract Set<? extends ProjectEndinspectionReview> getEndinspectionReview();
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public abstract String getEndinspectionClassName();
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public abstract String getEndinspectionReviewClassName();
	
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public abstract ProjectGranted getGranted();
	
	/**
	 * 关联项目立项对象
	 */
	public abstract void setGranted(ProjectGranted granted);
	
	
	public String getGrantedId() {
		return grantedId;
	}
	public void setGrantedId(String grantedId) {
		this.grantedId = grantedId;
	}
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

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String Project() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDeptInstAuditStatus() {
		return deptInstAuditStatus;
	}
	public void setDeptInstAuditStatus(int deptInstAuditStatus) {
		this.deptInstAuditStatus = deptInstAuditStatus;
	}
	public int getDeptInstResultEnd() {
		return deptInstResultEnd;
	}
	public void setDeptInstResultEnd(int deptInstResultEnd) {
		this.deptInstResultEnd = deptInstResultEnd;
	}
	public int getDeptInstResultNoevaluation() {
		return deptInstResultNoevaluation;
	}
	public void setDeptInstResultNoevaluation(int deptInstResultNoevaluation) {
		this.deptInstResultNoevaluation = deptInstResultNoevaluation;
	}
	public int getDeptInstResultExcellent() {
		return deptInstResultExcellent;
	}
	public void setDeptInstResultExcellent(int deptInstResultExcellent) {
		this.deptInstResultExcellent = deptInstResultExcellent;
	}
	public String getDeptInstAuditorName() {
		return deptInstAuditorName;
	}
	public void setDeptInstAuditorName(String deptInstAuditorName) {
		this.deptInstAuditorName = deptInstAuditorName;
	}
	@JSON(serialize=false)
	public Officer getDeptInstAuditor() {
		return deptInstAuditor;
	}
	public void setDeptInstAuditor(Officer deptInstAuditor) {
		this.deptInstAuditor = deptInstAuditor;
	}
	@JSON(serialize=false)
	public Department getDeptInstAuditorDept() {
		return deptInstAuditorDept;
	}
	public void setDeptInstAuditorDept(Department deptInstAuditorDept) {
		this.deptInstAuditorDept = deptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Institute getDeptInstAuditorInst() {
		return deptInstAuditorInst;
	}
	public void setDeptInstAuditorInst(Institute deptInstAuditorInst) {
		this.deptInstAuditorInst = deptInstAuditorInst;
	}
	public Date getDeptInstAuditDate() {
		return deptInstAuditDate;
	}
	public void setDeptInstAuditDate(Date deptInstAuditDate) {
		this.deptInstAuditDate = deptInstAuditDate;
	}
	public String getDeptInstAuditOpinion() {
		return deptInstAuditOpinion;
	}
	public void setDeptInstAuditOpinion(String deptInstAuditOpinion) {
		this.deptInstAuditOpinion = deptInstAuditOpinion;
	}
	public int getUniversityResultEnd() {
		return universityResultEnd;
	}
	public void setUniversityResultEnd(int universityResultEnd) {
		this.universityResultEnd = universityResultEnd;
	}
	public int getUniversityResultNoevaluation() {
		return universityResultNoevaluation;
	}
	public void setUniversityResultNoevaluation(int universityResultNoevaluation) {
		this.universityResultNoevaluation = universityResultNoevaluation;
	}
	public int getUniversityResultExcellent() {
		return universityResultExcellent;
	}
	public void setUniversityResultExcellent(int universityResultExcellent) {
		this.universityResultExcellent = universityResultExcellent;
	}
	public int getProvinceResultEnd() {
		return provinceResultEnd;
	}
	public void setProvinceResultEnd(int provinceResultEnd) {
		this.provinceResultEnd = provinceResultEnd;
	}
	public int getProvinceResultNoevaluation() {
		return provinceResultNoevaluation;
	}
	public void setProvinceResultNoevaluation(int provinceResultNoevaluation) {
		this.provinceResultNoevaluation = provinceResultNoevaluation;
	}
	public int getProvinceResultExcellent() {
		return provinceResultExcellent;
	}
	public void setProvinceResultExcellent(int provinceResultExcellent) {
		this.provinceResultExcellent = provinceResultExcellent;
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
	public String getReviewOpinionQualitative() {
		return reviewOpinionQualitative;
	}
	public void setReviewOpinionQualitative(String reviewOpinionQualitative) {
		this.reviewOpinionQualitative = reviewOpinionQualitative;
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
	public Date getUniversityAuditDate() {
		return universityAuditDate;
	}
	public void setUniversityAuditDate(Date universityAuditDate) {
		this.universityAuditDate = universityAuditDate;
	}
	public String getUniversityAuditOpinion() {
		return universityAuditOpinion;
	}
	public void setUniversityAuditOpinion(String universityAuditOpinion) {
		this.universityAuditOpinion = universityAuditOpinion;
	}
	public Date getProvinceAuditDate() {
		return provinceAuditDate;
	}
	public void setProvinceAuditDate(Date provinceAuditDate) {
		this.provinceAuditDate = provinceAuditDate;
	}
	public String getProvinceAuditOpinion() {
		return provinceAuditOpinion;
	}
	public void setProvinceAuditOpinion(String provinceAuditOpinion) {
		this.provinceAuditOpinion = provinceAuditOpinion;
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
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}
	public int getUniversityAuditStatus() {
		return universityAuditStatus;
	}
	public void setUniversityAuditStatus(int universityAuditStatus) {
		this.universityAuditStatus = universityAuditStatus;
	}
	public int getProvinceAuditStatus() {
		return provinceAuditStatus;
	}
	public void setProvinceAuditStatus(int provinceAuditStatus) {
		this.provinceAuditStatus = provinceAuditStatus;
	}
	public int getMinistryAuditStatus() {
		return ministryAuditStatus;
	}
	public void setMinistryAuditStatus(int ministryAuditStatus) {
		this.ministryAuditStatus = ministryAuditStatus;
	}
	public int getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
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
	@JSON(serialize=false)
	public SystemOption getReviewGrade() {
		return reviewGrade;
	}
	public void setReviewGrade(SystemOption reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
	public int getFinalAuditStatus() {
		return finalAuditStatus;
	}
	public void setFinalAuditStatus(int finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}
	public int getFinalAuditResultEnd() {
		return finalAuditResultEnd;
	}
	public void setFinalAuditResultEnd(int finalAuditResultEnd) {
		this.finalAuditResultEnd = finalAuditResultEnd;
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
	public int getFinalAuditResultPublish() {
		return finalAuditResultPublish;
	}
	public void setFinalAuditResultPublish(int finalAuditResultPublish) {
		this.finalAuditResultPublish = finalAuditResultPublish;
	}
	
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getPrintCount() {
		return printCount;
	}
	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	public String getUniversityAuditorName() {
		return universityAuditorName;
	}
	public void setUniversityAuditorName(String universityAuditorName) {
		this.universityAuditorName = universityAuditorName;
	}
	@JSON(serialize=false)
	public Officer getUniversityAuditor() {
		return universityAuditor;
	}
	public void setUniversityAuditor(Officer universityAuditor) {
		this.universityAuditor = universityAuditor;
	}
	public String getProvinceAuditorName() {
		return provinceAuditorName;
	}
	public void setProvinceAuditorName(String provinceAuditorName) {
		this.provinceAuditorName = provinceAuditorName;
	}
	@JSON(serialize=false)
	public Officer getProvinceAuditor() {
		return provinceAuditor;
	}
	public void setProvinceAuditor(Officer provinceAuditor) {
		this.provinceAuditor = provinceAuditor;
	}
	public String getMinistryAuditorName() {
		return ministryAuditorName;
	}
	public void setMinistryAuditorName(String ministryAuditorName) {
		this.ministryAuditorName = ministryAuditorName;
	}
	@JSON(serialize=false)
	public Officer getMinistryAuditor() {
		return ministryAuditor;
	}
	public void setMinistryAuditor(Officer ministryAuditor) {
		this.ministryAuditor = ministryAuditor;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	@JSON(serialize=false)
	public Officer getReviewer() {
		return reviewer;
	}
	public void setReviewer(Officer reviewer) {
		this.reviewer = reviewer;
	}
	public String getFinalAuditorName() {
		return finalAuditorName;
	}
	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}
	@JSON(serialize=false)
	public Officer getFinalAuditor() {
		return finalAuditor;
	}
	public void setFinalAuditor(Officer finalAuditor) {
		this.finalAuditor = finalAuditor;
	}
	@JSON(serialize=false)
	public Department getFinalAuditorDept() {
		return finalAuditorDept;
	}
	public void setFinalAuditorDept(Department finalAuditorDept) {
		this.finalAuditorDept = finalAuditorDept;
	}
	@JSON(serialize=false)
	public Institute getFinalAuditorInst() {
		return finalAuditorInst;
	}
	public void setFinalAuditorInst(Institute finalAuditorInst) {
		this.finalAuditorInst = finalAuditorInst;
	}
	@JSON(serialize=false)
	public Agency getUniversityAuditorAgency() {
		return universityAuditorAgency;
	}
	public void setUniversityAuditorAgency(Agency universityAuditorAgency) {
		this.universityAuditorAgency = universityAuditorAgency;
	}
	@JSON(serialize=false)
	public Agency getProvinceAuditorAgency() {
		return provinceAuditorAgency;
	}
	public void setProvinceAuditorAgency(Agency provinceAuditorAgency) {
		this.provinceAuditorAgency = provinceAuditorAgency;
	}
	@JSON(serialize=false)
	public Agency getMinistryAuditorAgency() {
		return ministryAuditorAgency;
	}
	public void setMinistryAuditorAgency(Agency ministryAuditorAgency) {
		this.ministryAuditorAgency = ministryAuditorAgency;
	}
	@JSON(serialize=false)
	public Agency getReviewerAgency() {
		return reviewerAgency;
	}
	public void setReviewerAgency(Agency reviewerAgency) {
		this.reviewerAgency = reviewerAgency;
	}
	@JSON(serialize=false)
	public Agency getFinalAuditorAgency() {
		return finalAuditorAgency;
	}
	public void setFinalAuditorAgency(Agency finalAuditorAgency) {
		this.finalAuditorAgency = finalAuditorAgency;
	}
	public Integer getReviewWay() {
		return reviewWay;
	}
	public void setReviewWay(Integer reviewWay) {
		this.reviewWay = reviewWay;
	}
	public Integer getFinalProductType() {
		return finalProductType;
	}
	public void setFinalProductType(Integer finalProductType) {
		this.finalProductType = finalProductType;
	}
	public String getFinalProductId() {
		return finalProductId;
	}
	public void setFinalProductId(String finalProductId) {
		this.finalProductId = finalProductId;
	}
	@JSON(serialize=false)
	public Set<ProjectEndinspectionProduct> getProjectEndinspectionProduct() {
		return projectEndinspectionProduct;
	}
	public void setProjectEndinspectionProduct(
			Set<ProjectEndinspectionProduct> projectEndinspectionProduct) {
		this.projectEndinspectionProduct = projectEndinspectionProduct;
	}
	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等
	 */
	@SuppressWarnings("unchecked")
	public void edit(Map auditMap){
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int result = auditInfo.getAuditResult();//1：不同意	2：同意
		int status = auditInfo.getAuditStatus();//1：退回	2：暂存	3：提交
		saveResult(auditInfo);
		//评审提交不同意不用写最终结果
		if(getStatus() > 1 && getStatus() != 6 && result == 1 && status == 3){
			notApproved();//审批不通过
		}
		if(isSubUni == 1 && getStatus() == 3 && result == 2 && status == 3){//部属高校提交
			if(STATUS_MATRIX[1][2][getStatus()] != 0)
				setStatus(STATUS_MATRIX[result - 1][status - 1][getStatus()]);
		}else{
			if(STATUS_MATRIX[1][2][getStatus() - 1] != 0)
				setStatus(STATUS_MATRIX[result - 1][status - 1][getStatus() - 1]);
		}
		checkExcellentNotApproved();
		checkNoevaluationNotApproved();
	}
	
	/**
	 * 保存结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等
	 */
	private void saveResult(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int result = auditInfo.getAuditResult();
		int status = auditInfo.getAuditStatus();
		String opinion = auditInfo.getAuditOpinion();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getStatus() == 0){
			setStatus(1);
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(status);
		}else if(getStatus() == 1){
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(status);
		}else if(getStatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstResultEnd(result);
			setDeptInstAuditStatus(status);
			setDeptInstAuditOpinion(opinion);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityResultEnd(result);
			setUniversityAuditStatus(status);
			setUniversityAuditOpinion(opinion);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceResultEnd(result);
			setProvinceAuditStatus(status);
			setProvinceAuditOpinion(opinion);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
			setMinistryAuditDate(date);
			setMinistryResultEnd(result);
			setMinistryAuditStatus(status);
			setMinistryAuditOpinion(opinion);
			setMinistryAuditorName(auditorName);
			setMinistryAuditor(auditor);
			setMinistryAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 6){
			setReviewDate(date);
			setReviewResult(result);
			setReviewStatus(status);
			setReviewOpinion(opinion);
			setReviewerName(auditorName);
			setReviewer(auditor);
			setReviewerAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 7){
			setFinalAuditDate(date);
			setFinalAuditResultEnd(result);
			setFinalAuditStatus(status);
			setFinalAuditOpinion(opinion);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等(auditInfo中auditResult,auditOpinion可传为任意值)
	 */
	@SuppressWarnings("unchecked")
	public void submit(Map auditMap) {
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int isAgree = getIsAgree();
		//评审提交不同意不用写最终结果
		if(getStatus() > 1 && getStatus() != 6 && isAgree == 1){
			notApproved();//审核不通过
		}
		changeStatus(auditInfo);
		if(isSubUni == 1 && getStatus() == 3 && isAgree == 2){//部属高校提交
			if(STATUS_MATRIX[isAgree - 1][2][getStatus()] != 0)
				setStatus(STATUS_MATRIX[isAgree - 1][2][getStatus()]);
		}else{
			if(STATUS_MATRIX[isAgree - 1][2][getStatus() - 1] != 0)
				setStatus(STATUS_MATRIX[isAgree - 1][2][getStatus() - 1]);
		}
		checkExcellentNotApproved();
		checkNoevaluationNotApproved();
	}
	
	/**
	 * @param auditInfo 审核信息
	 */
	private void changeStatus(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int status = auditInfo.getAuditStatus();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getStatus() == 1){
			setApplicantSubmitStatus(status);
			setApplicantSubmitDate(date);
		}else if(getStatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstAuditStatus(status);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditStatus(status);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditStatus(status);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
			setMinistryAuditDate(date);
			setMinistryAuditStatus(status);
			setMinistryAuditorName(auditorName);
			setMinistryAuditor(auditor);
			setMinistryAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 6){
			setReviewDate(date);
			setReviewStatus(status);
			setReviewerName(auditorName);
			setReviewer(auditor);
			setReviewerAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 7){
			setFinalAuditDate(date);
			setFinalAuditStatus(status);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 退回
	 * @param isSubUni 是否部属高校项目（只有审核才需要，其他可直接传0）1:是，0：不是
	 */
	public void back(int isSubUni) {
		backResult(isSubUni);
		if(isSubUni == 1 && getStatus() == 5){//部级退回
			setStatus(3);
		}else{
			if(STATUS_MATRIX[1][0][getStatus()- 1]!= 0)
				setStatus(STATUS_MATRIX[1][0][getStatus()- 1]);
		}
	}
	
	/**
	 * 设置不同意
	 */
	private void notApproved(){
		setFinalAuditResultEnd(1);
		if(getIsApplyExcellent() == 1){//申请优秀成果
			setFinalAuditResultExcellent(1);
		}
		if(getIsApplyNoevaluation() == 1){//申请免鉴定
			setFinalAuditResultNoevaluation(1);
		}
		setFinalAuditStatus(3);
		if(getStatus() == 1){
			;
		}else if(getStatus() == 2){
			setFinalAuditorName(getDeptInstAuditorName());
			setFinalAuditOpinion(getDeptInstAuditOpinion());
			setFinalAuditDate(getDeptInstAuditDate());
			setFinalAuditor(getDeptInstAuditor());
			setFinalAuditorDept(getDeptInstAuditorDept());
			setFinalAuditorInst(getDeptInstAuditorInst());
		}else if(getStatus() == 3){
			setFinalAuditorName(getUniversityAuditorName());
			setFinalAuditOpinion(getUniversityAuditOpinion());
			setFinalAuditDate(getUniversityAuditDate());
			setFinalAuditor(getUniversityAuditor());
			setFinalAuditorAgency(getUniversityAuditorAgency());
		}else if(getStatus() == 4){
			setFinalAuditorName(getProvinceAuditorName());
			setFinalAuditOpinion(getProvinceAuditOpinion());
			setFinalAuditDate(getProvinceAuditDate());
			setFinalAuditor(getProvinceAuditor());
			setFinalAuditorAgency(getProvinceAuditorAgency());
		}else if(getStatus() == 5){
			setFinalAuditorName(getMinistryAuditorName());
			setFinalAuditOpinion(getMinistryAuditOpinion());
			setFinalAuditDate(getMinistryAuditDate());
			setFinalAuditor(getMinistryAuditor());
			setFinalAuditorAgency(getMinistryAuditorAgency());
		}else if(getStatus() == 6){
			setFinalAuditorName(getReviewerName());
			setFinalAuditOpinion(getReviewOpinion());
			setFinalAuditDate(getReviewDate());
			setFinalAuditor(getReviewer());
			setFinalAuditorAgency(getReviewerAgency());
		}else if(getStatus() == 7){
			;
		}
	}
	
	private void checkExcellentNotApproved(){
		if(getIsApplyExcellent() == 1){//申请优秀成果
			if((getFinalAuditResultExcellent()==1 && getFinalAuditStatus()==3) || (getMinistryResultExcellent()==1 && getMinistryAuditStatus()==3) || (getProvinceResultExcellent()==1 && getProvinceAuditStatus()==3) || (getUniversityResultExcellent()==1 && getUniversityAuditStatus()==3) || (getDeptInstResultExcellent()==1 && getDeptInstAuditStatus()==3)){
				setFinalAuditResultExcellent(1);//优秀成果不通过
			}
		}
	}
	
	private void checkNoevaluationNotApproved(){
		if(getIsApplyNoevaluation() == 1){//申请免鉴定
			if((getFinalAuditResultNoevaluation()==1 && getFinalAuditStatus()==3) || (getMinistryResultNoevaluation()==1 && getMinistryAuditStatus()==3) || (getProvinceResultNoevaluation()==1 && getProvinceAuditStatus()==3) || (getUniversityResultNoevaluation()==1 && getUniversityAuditStatus()==3) || (getDeptInstResultNoevaluation()==1 && getDeptInstAuditStatus()==3)){
				setFinalAuditResultNoevaluation(1);//免鉴定不通过
			}
		}
	}
		

	/**
	 * 退回
	 * @param isSubUni 是否部属高校项目（只有审核才需要，其他可直接传0） 1:是，0：不是
	 */
	private void backResult(int isSubUni){
		if(getStatus() == 1)//申请者
			;
		else if(getStatus() == 2) {//院系研究机构
			setApplicantSubmitStatus(1);
			setDeptInstAuditStatus(0);
			setDeptInstResultEnd(0);
			setDeptInstResultExcellent(0);
			setDeptInstResultNoevaluation(0);
			setDeptInstAuditOpinion(null);
			setDeptInstAuditorName(null);
			setDeptInstAuditDate(null);
			setDeptInstAuditor(null);
			setDeptInstAuditorDept(null);
			setDeptInstAuditorInst(null);
		}
		else if(getStatus() == 3) {//校级
			setDeptInstAuditStatus(1);
			if(getDeptInstResultExcellent() == 1){//之前审核提交不同意
				setFinalAuditResultExcellent(0);
			}
			if(getDeptInstResultNoevaluation() == 1){//之前审核提交不同意
				setFinalAuditResultNoevaluation(0);
			}
			setUniversityAuditStatus(0);
			setUniversityResultEnd(0);
			setUniversityResultExcellent(0);
			setUniversityResultNoevaluation(0);
			setUniversityAuditOpinion(null);
			setUniversityAuditorName(null);
			setUniversityAuditDate(null);
			setUniversityAuditor(null);
			setUniversityAuditorAgency(null);
		}
		else if(getStatus() == 4){//省厅
			setUniversityAuditStatus(1);
			if(getUniversityResultExcellent() == 1){//之前审核提交不同意
				setFinalAuditResultExcellent(0);
			}
			if(getUniversityResultNoevaluation() == 1){//之前审核提交不同意
				setFinalAuditResultNoevaluation(0);
			}
			setProvinceAuditStatus(0);
			setProvinceResultEnd(0);
			setProvinceResultExcellent(0);
			setProvinceResultNoevaluation(0);
			setProvinceAuditOpinion(null);
			setProvinceAuditorName(null);
			setProvinceAuditDate(null);
			setProvinceAuditor(null);
			setProvinceAuditorAgency(null);
		}
		else if(getStatus() == 5) {//部级
			if(isSubUni == 1) {
				setUniversityAuditStatus(1);
				if(getUniversityResultExcellent() == 1){//之前审核提交不同意
					setFinalAuditResultExcellent(0);
				}
				if(getUniversityResultNoevaluation() == 1){//之前审核提交不同意
					setFinalAuditResultNoevaluation(0);
				}
			}
			else if(isSubUni == 0) {
				setProvinceAuditStatus(1);
				if(getProvinceResultExcellent() == 1){//之前审核提交不同意
					setFinalAuditResultExcellent(0);
				}
				if(getProvinceResultNoevaluation() == 1){//之前审核提交不同意
					setFinalAuditResultNoevaluation(0);
				}
			}
			setMinistryAuditStatus(0);
			setMinistryResultEnd(0);
			setMinistryResultExcellent(0);
			setMinistryResultNoevaluation(0);
			setMinistryAuditOpinion(null);
			setMinistryAuditorName(null);
			setMinistryAuditDate(null);
			setMinistryAuditor(null);
			setMinistryAuditorAgency(null);
		}
		setFinalAuditOpinionFeedback(null);
	}
	
	/**
	 * 获取当前状态级别的是否同意信息
	 * @return 当前状态级别的是否同意
	 */
	private int getIsAgree() {
		if(getStatus() == 1)
			return 2;
		else if(getStatus() == 2)
			return getDeptInstResultEnd();
		else if(getStatus() == 3)
			return getUniversityResultEnd();
		else if(getStatus() == 4)
			return getProvinceResultEnd();
		else if(getStatus() == 5)
			return getMinistryResultEnd();
		else if(getStatus() == 6)
			return getReviewResult();
		else if(getStatus() == 7)
			return getFinalAuditResultEnd();
		return 0;
	}
	public void setProjectFee(ProjectFee projectFee) {
		this.projectFee = projectFee;
	}
	@JSON(serialize = false)
	public ProjectFee getProjectFee() {
		return projectFee;
	}
	public Integer getCreateMode() {
		return createMode;
	}
	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}