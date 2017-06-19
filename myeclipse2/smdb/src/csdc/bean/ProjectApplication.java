package csdc.bean;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;
import csdc.tool.bean.AuditInfo;

public abstract class ProjectApplication{
	private String id;
	private String type;//项目类型
	private String productType;//成果形式
	private String productTypeOther;//其他成果形式
	private String name;//项目中文名
	private String englishName;//项目英文名
	private Agency university;//依托高校
	private Department department;//依托院系
	private Institute institute;//依托研究机构
	private String agencyName;//依托单位名
	private String divisionName;//依托部门名
	private SystemOption province;// 高校所在省
	private String provinceName;//高校所在省
	//@CheckSystemOptionStandard("projectType")
	private SystemOption subtype;//项目子类
	private int year;//项目年份
	private String file;//项目申请书
	private String note;//备注
	private String keywords;//关键字
	private String summary;//摘要
	//@CheckSystemOptionStandard("researchType")
	private SystemOption researchType;//研究类型
	private String disciplineType;//学科门类
	private String discipline;//学科
	private String relativeDiscipline;//相关学科
	private Date planEndDate;//计划完成时间
	private Double otherFee;//其他经费
	private Double applyFee;//申请经费
	private String applicantId;//申请人Id
	private String applicantName;//申请人姓名
	private int isReviewable;//是否可审
	private int status;//流程状态 （无需导入）
	private int applicantSubmitStatus;//申请新建状态（无需导入）
	private Date applicantSubmitDate;//申请新建时间
	private int deptInstAuditStatus;//院系/研究机构审核状态
	private int deptInstAuditResult;//院系/研究机构审核结果
	private String deptInstAuditorName;//院系/研究机构审核人姓名
	private Officer deptInstAuditor;//院系/研究机构审核人
	private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
	private Institute deptInstAuditorInst;//院系/研究机构审核人所在研究机构
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private int universityAuditStatus;//高校审核状态（无需导入）
	private int universityAuditResult;//高校审核结果（无需导入）
	private String universityAuditorName;//高校审核人姓名（无需导入）
	private Officer universityAuditor;//高校审核人id（无需导入）
	private Agency universityAuditorAgency;//高校审核人所在机构id（无需导入）
	private Date universityAuditDate;//高校审核时间（无需导入）
	private String universityAuditOpinion;//高校审核意见（无需导入）
	private int provinceAuditStatus;//省审核状态（无需导入）
	private int provinceAuditResult;//省审核结果（无需导入）
	private String provinceAuditorName;//省审核人姓名（无需导入）
	private Officer provinceAuditor;//省审核人id（无需导入）
	private Agency provinceAuditorAgency;//省审核人所在机构id（无需导入）
	private Date provinceAuditDate;//省审核时间（无需导入）
	private String provinceAuditOpinion;//省审核意见（无需导入）
	private int ministryAuditStatus;//部级审核状态（无需导入）
	private int ministryAuditResult;//部级审核结果（无需导入）
	private String ministryAuditorName;//部级审核人姓名（无需导入）
	private Officer ministryAuditor;//部级审核人id（无需导入）
	private Agency ministryAuditorAgency;//部级审核人所在机构（无需导入）
	private Date ministryAuditDate;//部级审核时间（无需导入）
	private String ministryAuditOpinion;//部级审核意见（无需导入）
	private int reviewStatus;//评审状态（无需导入）
	private int reviewResult;//评审结果（无需导入）
	private String reviewerName;//评审人姓名（无需导入）
	private Officer reviewer;//评审人id（无需导入）
	private Agency reviewerAgency;//评审人所在机构id（无需导入）
	private Date reviewDate;//评审时间（无需导入）
	private String reviewOpinion;//评审意见（无需导入）
	private Integer reviewWay;//评审方式：0默认，1 通讯评审，2 会议评审
	private Double reviewTotalScore;//评审总分（无需导入）
	private Double reviewAverageScore;//评审均分（无需导入）
	//@CheckSystemOptionStandard("reviewGrade")
	private SystemOption reviewGrade;//等级
	private String reviewOpinionQualitative;//定性意见
	private int finalAuditStatus;//最终审核状态
	private int finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人姓名（无需导入）
	private Officer finalAuditor;//最终审核人id（无需导入）
	private Agency finalAuditorAgency;//最终审核人所在机构（无需导入）
	private Department finalAuditorDept;//最终审核人所在院系
	private Institute finalAuditorInst;//最终审核人所在基地（无需导入）
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private int isImported;//是否导入数据：1是，0否
	private Date importedDate;//导入时间
	private ProjectFee projectFee;//经费概算明细
	private int isPublished;
	
	/* 项目申报状态转移矩阵
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
	
//	/**
//	 * 获取项目立项对象集合
//	 */
//	@JSON(serialize=false)
//	public abstract Set<? extends ProjectGranted> getGranted();
	
	/**
	 * 获取项目成员对象集合
	 */
	@JSON(serialize=false)
	public abstract Set<? extends ProjectMember> getMember();//项目成员对应
	
	/**
	 * 获取项目经费对象集合
	 */
	@JSON(serialize=false)
	public abstract Set<? extends ProjectFee> getFee();//项目经费对应
	/**
	 * 获取项目对应申报类名
	 */
	@JSON(serialize = false)
	public abstract String getApplicationClassName();
	/**
	 * 获取项目对应申报评审类名
	 */
	@JSON(serialize = false)
	public abstract String getApplicationReviewClassName();
	/**
	 * 获取项目对应立项类名
	 */
	@JSON(serialize = false)
	public abstract String getGrantedClassName();
	
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public abstract String getMemberClassName();
	
	/**
	 * 添加一个立项
	 * @param projectGranted
	 */
	public abstract void addGranted(ProjectGranted granted);

	/**
	 * 添加一个成员
	 * @param instpMember
	 */
	public abstract void addMember(ProjectMember member);

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
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
	@JSON(serialize=false)
	public SystemOption getSubtype() {
		return subtype;
	}
	public void setSubtype(SystemOption subtype) {
		this.subtype = subtype;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@JSON(serialize=false)
	public SystemOption getResearchType() {
		return researchType;
	}
	public void setResearchType(SystemOption researchType) {
		this.researchType = researchType;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getRelativeDiscipline() {
		return relativeDiscipline;
	}
	public void setRelativeDiscipline(String relativeDiscipline) {
		this.relativeDiscipline = relativeDiscipline;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Double getApplyFee() {
		return applyFee;
	}
	public void setApplyFee(Double applyFee) {
		this.applyFee = applyFee;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public int getIsReviewable() {
		return isReviewable;
	}
	public void setIsReviewable(int isReviewable) {
		this.isReviewable = isReviewable;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
	}
	@JSON(format="yyyy-MM-dd")
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
	public int getUniversityAuditResult() {
		return universityAuditResult;
	}
	public void setUniversityAuditResult(int universityAuditResult) {
		this.universityAuditResult = universityAuditResult;
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
	public int getProvinceAuditStatus() {
		return provinceAuditStatus;
	}
	public void setProvinceAuditStatus(int provinceAuditStatus) {
		this.provinceAuditStatus = provinceAuditStatus;
	}
	public int getProvinceAuditResult() {
		return provinceAuditResult;
	}
	public void setProvinceAuditResult(int provinceAuditResult) {
		this.provinceAuditResult = provinceAuditResult;
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
	public int getMinistryAuditStatus() {
		return ministryAuditStatus;
	}
	public void setMinistryAuditStatus(int ministryAuditStatus) {
		this.ministryAuditStatus = ministryAuditStatus;
	}
	public int getMinistryAuditResult() {
		return ministryAuditResult;
	}
	public void setMinistryAuditResult(int ministryAuditResult) {
		this.ministryAuditResult = ministryAuditResult;
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
	public int getFinalAuditStatus() {
		return finalAuditStatus;
	}
	public void setFinalAuditStatus(int finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}
	public int getFinalAuditResult() {
		return finalAuditResult;
	}
	public void setFinalAuditResult(int finalAuditResult) {
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

	public int getIsImported() {
		return isImported;
	}
	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}
	
	public int getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
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
	public Institute getFinalAuditorInst() {
		return finalAuditorInst;
	}
	public void setFinalAuditorInst(Institute finalAuditorInst) {
		this.finalAuditorInst = finalAuditorInst;
	}
	public int getDeptInstAuditStatus() {
		return deptInstAuditStatus;
	}
	public void setDeptInstAuditStatus(int deptInstAuditStatus) {
		this.deptInstAuditStatus = deptInstAuditStatus;
	}
	public int getDeptInstAuditResult() {
		return deptInstAuditResult;
	}
	public void setDeptInstAuditResult(int deptInstAuditResult) {
		this.deptInstAuditResult = deptInstAuditResult;
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
	public Integer getReviewWay() {
		return reviewWay;
	}

	public void setReviewWay(Integer reviewWay) {
		this.reviewWay = reviewWay;
	}

	@JSON(serialize=false)
	public SystemOption getReviewGrade() {
		return reviewGrade;
	}

	public void setReviewGrade(SystemOption reviewGrade) {
		this.reviewGrade = reviewGrade;
	}
	
	@JSON(serialize=false)
	public String getReviewOpinionQualitative() {
		return reviewOpinionQualitative;
	}

	public void setReviewOpinionQualitative(String reviewOpinionQualitative) {
		this.reviewOpinionQualitative = reviewOpinionQualitative;
	}

	@JSON(serialize=false)
	public Agency getFinalAuditorAgency() {
		return finalAuditorAgency;
	}
	public void setFinalAuditorAgency(Agency finalAuditorAgency) {
		this.finalAuditorAgency = finalAuditorAgency;
	}
	@JSON(serialize=false)
	public Department getFinalAuditorDept() {
		return finalAuditorDept;
	}
	public void setFinalAuditorDept(Department finalAuditorDept) {
		this.finalAuditorDept = finalAuditorDept;
	}
	public Date getImportedDate() {
		return importedDate;
	}
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}
	public String getFinalAuditOpinionFeedback() {
		return finalAuditOpinionFeedback;
	}
	public void setFinalAuditOpinionFeedback(String finalAuditOpinionFeedback) {
		this.finalAuditOpinionFeedback = finalAuditOpinionFeedback;
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
			setDeptInstAuditResult(result);
			setDeptInstAuditStatus(status);
			setDeptInstAuditOpinion(opinion);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getStatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditResult(result);
			setUniversityAuditStatus(status);
			setUniversityAuditOpinion(opinion);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditResult(result);
			setProvinceAuditStatus(status);
			setProvinceAuditOpinion(opinion);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 5){
			setMinistryAuditDate(date);
			setMinistryAuditResult(result);
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
			setFinalAuditResult(result);
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
		setFinalAuditResult(1);
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
			setDeptInstAuditResult(0);
			setDeptInstAuditOpinion(null);
			setDeptInstAuditorName(null);
			setDeptInstAuditDate(null);
			setDeptInstAuditor(null);
			setDeptInstAuditorDept(null);
			setDeptInstAuditorInst(null);
		}
		else if(getStatus() == 3) {//校级
			setDeptInstAuditStatus(1);
			if(getDeptInstAuditResult() == 1){//之前审核提交不同意
				setFinalAuditResult(0);
			}
			setUniversityAuditStatus(0);
			setUniversityAuditResult(0);
			setUniversityAuditOpinion(null);
			setUniversityAuditorName(null);
			setUniversityAuditDate(null);
			setUniversityAuditor(null);
			setUniversityAuditorAgency(null);
		}
		else if(getStatus() == 4){//省厅
			setUniversityAuditStatus(1);
			setProvinceAuditStatus(0);
			setProvinceAuditResult(0);
			setProvinceAuditOpinion(null);
			setProvinceAuditorName(null);
			setProvinceAuditDate(null);
			setProvinceAuditor(null);
			setProvinceAuditorAgency(null);
		}
		else if(getStatus() == 5) {//部级
			if(isSubUni == 1) {
				setUniversityAuditStatus(1);
			}
			else if(isSubUni == 0) {
				setProvinceAuditStatus(1);
			}
			setMinistryAuditStatus(0);
			setMinistryAuditResult(0);
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
			return getDeptInstAuditResult();
		else if(getStatus() == 3)
			return getUniversityAuditResult();
		else if(getStatus() == 4)
			return getProvinceAuditResult();
		else if(getStatus() == 5)
			return getMinistryAuditResult();
		else if(getStatus() == 6)
			return getReviewResult();
		else if(getStatus() == 7)
			return getFinalAuditResult();
		return 0;
	}

	public void setProjectFee(ProjectFee projectFee) {
		this.projectFee = projectFee;
	}
	
	@JSON(serialize = false)
	public ProjectFee getProjectFee() {
		return projectFee;
	}

	@JSON(serialize=false)
	public SystemOption getProvince() {
		return province;
	}

	public void setProvince(SystemOption province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
