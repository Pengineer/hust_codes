package csdc.bean;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import csdc.tool.bean.AuditInfo;

/**
 * 状态机STATUS_ARRAY是一个大小是[2][3][8]的三维数组。
 *一维：结果（1不同意，2同意）；
 *二维：操作（1退回，2暂存，3提交）；
 *三维：状态（1新建申请，2院系/研究机构审核，3校级审核，4省级审核，5部级审核，6评审，7评审结果审核，8最终审核）
 *数组中的0表示没有此操作
 * @author 余潜玉
 */
public class AwardApplication implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//id
	private Institute institute;//依托研究机构
	private Agency university;//依托学校
	private Department department;//依托院系
	private Person applicant;//申请人
	private String applicantName;//申请人姓名
	private Integer applicationType;//申请类型      1 ：个人名义申请      2 ：团队、课题组、机构等名义申请
	//@CheckSystemOptionStandard("subType")
	private SystemOption subType;//奖励类型
	private SystemOption type;//奖励类别
	private Product product;//成果
	private String productName;//成果名
	private String file;//申请书文件
	private String agencyName;//单位名称	
	private String divisionName;//部门名称
	private SystemOption province;//高校所在省
    private String provinceName;//高校所在省
	private Date applicantSubmitDate;//申请新建时间
	private int session;//申请届次
	private int applicantSubmitStatus;//申请流程状态						3
	private int deptInstAuditStatus;//院系/研究机构审核状态				3
	private int deptInstAuditResult;//院系/研究机构审核结果				2
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private String deptInstAuditorName;//院系/研究机构审核人姓名
	private Officer deptInstAuditor;//院系/研究机构审核人
	private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
	private Institute deptInstAuditorInst;//院系/研究机构审核人所在院系
	private String committeeAuditOpinion; //校学术委员会审核意见
	private int universityAuditStatus;//高校审核状态						3
	private int universityAuditResult;//高校审核结果						2
	private Date universityAuditDate;//高校审核时间
	private String universityAuditOpinion;//高校审核意见
	private String universityAuditorName;//高校审核人姓名
	private Officer universityAuditor;//高校审核人
	private Agency universityAuditorAgency;//高校审核人所在机构
	private int provinceAuditStatus;//省厅审核状态						3  部属高校不填
	private int provinceAuditResult;//省厅审核结果						2  部属高校不填
	private String provinceAuditorName;//省厅审核人姓名
	private Officer provinceAuditor;//省厅审核人
	private Agency provinceAuditorAgency;//省厅审核人所在机构
	private Date provinceAuditDate;//省厅审核时间
	private String provinceAuditOpinion;//省厅审核意见
	private int ministryAuditStatus;//部级审核状态						3
	private int ministryAuditResult;//部级审核结果						2
	private Date ministryAuditDate;//部级审核时间
	private String ministryAuditOpinion;//部级审核意见
	private String ministryAuditorName;//部级审核人姓名
	private Officer ministryAuditor;//部级审核人
	private Agency ministryAuditorAgency;//部级审核人所在机构
	private String prizeObtained;//成果获奖情况
	private String response;//成果社会反映
	private String adoption;//成果引用或被采纳意见
	private String introduction;//成果内容简介
	private String disciplineType;//学科门类										*
	private int status;//申请状态									9
	private int reviewStatus;//评审状态									3
	private int reviewResult;//评审结果									2
	private Date reviewDate;//评审时间
	private Double reviewTotalScore;//总分
	private Double reviewAverageScore;//平均分
	//@CheckSystemOptionStandard("awardGrade")
	private SystemOption reviewGrade;//建议获奖等级 
	private String reviewWay;//评审形式
	private String reviewOpinion;//评审意见
	private String reviewerName;//评审人姓名
	private Officer reviewer;//评审人
	private Agency reviewerAgency;//评审人所在机构
	private int reviewAuditStatus;//评审审核状态							3
	private int reviewAuditResult;//评审审核结果							2
	private Date reviewAuditDate;//评审审核时间
	private String reviewAuditOpinion;//评审审核意见
	private String reviewAuditorName;//评审审核人姓名
	private Officer reviewAuditor;//评审审核人
	private Agency reviewAuditorAgency;//评审审核人所在机构
	private int finalAuditStatus;//最终审核状态						3
	private int finalAuditResult;//最终审核结果						2
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给负责人）
	private String finalAuditorName;//最终审核人姓名
	private Officer finalAuditor;//评审审核人
	private Agency finalAuditorAgency;//评审审核人所在机构
	private Department finalAuditorDept;//评审审核人所在院系
	private Institute finalAuditorInst;//评审审核人所在研究机构
	private String number;//证书编号
	private Integer year;//获奖年度
	private String group;//分组情况
	private String note;//备注
	private String shelfNumber;//二次上架号
	private String dfs;//文件云存储位置
	private Integer createMode;//数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;//创建时间
	private Date updateDate;//数据更新时间
	
	private Set<AwardGranted> award;
	private Set<AwardReview> awardReview;//专家评审信息

	//定义状态机 	一维：结果（1不同意，2同意）；	二维：操作（1退回，2暂存，3提交）；
	//三维：状态（1新建申请，2院系/研究机构审核，3校级审核，4省级审核，5部级审核，6评审，7评审结果审核，8公示，9获奖）
	private static final int[][][] STATUS_ARRAY = new int[][][]
	   {
		{
			{0,1,2,3,4,0,0,0},
			{1,2,3,4,5,6,7,8},
			{2,2,3,4,5,7,7,8}//评审不同意一样跳到上一级
		},
		{
			{0,1,2,3,4,0,0,0},
			{1,2,3,4,5,6,7,8},
			{2,3,4,5,6,7,8,8}
		}
	   };
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public Person getApplicant() {
		return applicant;
	}
	public void setApplicant(Person applicant) {
		this.applicant = applicant;
	}
	@JSON(serialize=false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
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
	public String getReviewWay() {
		return reviewWay;
	}
	public void setReviewWay(String reviewWay) {
		this.reviewWay = reviewWay;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReviewAuditDate() {
		return reviewAuditDate;
	}
	public void setReviewAuditDate(Date reviewAuditDate) {
		this.reviewAuditDate = reviewAuditDate;
	}
	public String getReviewAuditOpinion() {
		return reviewAuditOpinion;
	}
	public void setReviewAuditOpinion(String reviewAuditOpinion) {
		this.reviewAuditOpinion = reviewAuditOpinion;
	}

	public int getSession() {
		return session;
	}
	public void setSession(int session) {
		this.session = session;
	}
	
	
	public String getShelfNumber() {
		return shelfNumber;
	}
	public void setShelfNumber(String shelfNumber) {
		this.shelfNumber = shelfNumber;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}
	public int getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}
	public void setApplicantSubmitStatus(int applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
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
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	public String getPrizeObtained() {
		return prizeObtained;
	}
	public void setPrizeObtained(String prizeObtained) {
		this.prizeObtained = prizeObtained;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getAdoption() {
		return adoption;
	}
	public void setAdoption(String adoption) {
		this.adoption = adoption;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getReviewAuditStatus() {
		return reviewAuditStatus;
	}
	public void setReviewAuditStatus(int reviewAuditStatus) {
		this.reviewAuditStatus = reviewAuditStatus;
	}
	public int getReviewAuditResult() {
		return reviewAuditResult;
	}
	public void setReviewAuditResult(int reviewAuditResult) {
		this.reviewAuditResult = reviewAuditResult;
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
	public String getFinalAuditOpinionFeedback() {
		return finalAuditOpinionFeedback;
	}
	public void setFinalAuditOpinionFeedback(String finalAuditOpinionFeedback) {
		this.finalAuditOpinionFeedback = finalAuditOpinionFeedback;
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
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}	

	@JSON(serialize=false)
	public SystemOption getType() {
		return type;
	}
	public void setType(SystemOption type) {
		this.type = type;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getDeptInstAuditorName() {
		return deptInstAuditorName;
	}
	@JSON(serialize=false)
	public Officer getDeptInstAuditor() {
		return deptInstAuditor;
	}
	@JSON(serialize=false)
	public Department getDeptInstAuditorDept() {
		return deptInstAuditorDept;
	}
	@JSON(serialize=false)
	public Institute getDeptInstAuditorInst() {
		return deptInstAuditorInst;
	}
	public String getUniversityAuditorName() {
		return universityAuditorName;
	}
	@JSON(serialize=false)
	public Officer getUniversityAuditor() {
		return universityAuditor;
	}
	@JSON(serialize=false)
	public Agency getUniversityAuditorAgency() {
		return universityAuditorAgency;
	}
	public String getProvinceAuditorName() {
		return provinceAuditorName;
	}
	@JSON(serialize=false)
	public Officer getProvinceAuditor() {
		return provinceAuditor;
	}
	@JSON(serialize=false)
	public Agency getProvinceAuditorAgency() {
		return provinceAuditorAgency;
	}
	public String getMinistryAuditorName() {
		return ministryAuditorName;
	}
	@JSON(serialize=false)
	public Officer getMinistryAuditor() {
		return ministryAuditor;
	}
	@JSON(serialize=false)
	public Agency getMinistryAuditorAgency() {
		return ministryAuditorAgency;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	@JSON(serialize=false)
	public Officer getReviewer() {
		return reviewer;
	}
	@JSON(serialize=false)
	public Agency getReviewerAgency() {
		return reviewerAgency;
	}
	public String getReviewAuditorName() {
		return reviewAuditorName;
	}
	@JSON(serialize=false)
	public Officer getReviewAuditor() {
		return reviewAuditor;
	}
	@JSON(serialize=false)
	public Agency getReviewAuditorAgency() {
		return reviewAuditorAgency;
	}
	public String getFinalAuditorName() {
		return finalAuditorName;
	}
	@JSON(serialize=false)
	public Officer getFinalAuditor() {
		return finalAuditor;
	}
	@JSON(serialize=false)
	public Agency getFinalAuditorAgency() {
		return finalAuditorAgency;
	}
	public void setDeptInstAuditorName(String deptInstAuditorName) {
		this.deptInstAuditorName = deptInstAuditorName;
	}
	public void setDeptInstAuditor(Officer deptInstAuditor) {
		this.deptInstAuditor = deptInstAuditor;
	}
	public void setDeptInstAuditorDept(Department deptInstAuditorDept) {
		this.deptInstAuditorDept = deptInstAuditorDept;
	}
	public void setDeptInstAuditorInst(Institute deptInstAuditorInst) {
		this.deptInstAuditorInst = deptInstAuditorInst;
	}
	public void setUniversityAuditorName(String universityAuditorName) {
		this.universityAuditorName = universityAuditorName;
	}
	public void setUniversityAuditor(Officer universityAuditor) {
		this.universityAuditor = universityAuditor;
	}
	public void setUniversityAuditorAgency(Agency universityAuditorAgency) {
		this.universityAuditorAgency = universityAuditorAgency;
	}
	public void setProvinceAuditorName(String provinceAuditorName) {
		this.provinceAuditorName = provinceAuditorName;
	}
	public void setProvinceAuditor(Officer provinceAuditor) {
		this.provinceAuditor = provinceAuditor;
	}
	public void setProvinceAuditorAgency(Agency provinceAuditorAgency) {
		this.provinceAuditorAgency = provinceAuditorAgency;
	}
	public void setMinistryAuditorName(String ministryAuditorName) {
		this.ministryAuditorName = ministryAuditorName;
	}
	public void setMinistryAuditor(Officer ministryAuditor) {
		this.ministryAuditor = ministryAuditor;
	}
	public void setMinistryAuditorAgency(Agency ministryAuditorAgency) {
		this.ministryAuditorAgency = ministryAuditorAgency;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public void setReviewer(Officer reviewer) {
		this.reviewer = reviewer;
	}
	public void setReviewerAgency(Agency reviewerAgency) {
		this.reviewerAgency = reviewerAgency;
	}
	public void setReviewAuditorName(String reviewAuditorName) {
		this.reviewAuditorName = reviewAuditorName;
	}
	public void setReviewAuditor(Officer reviewAuditor) {
		this.reviewAuditor = reviewAuditor;
	}
	public void setReviewAuditorAgency(Agency reviewAuditorAgency) {
		this.reviewAuditorAgency = reviewAuditorAgency;
	}
	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}
	public void setFinalAuditor(Officer finalAuditor) {
		this.finalAuditor = finalAuditor;
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
	@JSON(serialize=false)
	public Institute getFinalAuditorInst() {
		return finalAuditorInst;
	}
	public void setFinalAuditorInst(Institute finalAuditorInst) {
		this.finalAuditorInst = finalAuditorInst;
	}
	@JSON(serialize=false)
	public Set<AwardGranted> getAward() {
		return award;
	}
	public void setAward(Set<AwardGranted> award) {
		this.award = award;
	}
	@JSON(serialize=false)
	public Set<AwardReview> getAwardReview() {
		return awardReview;
	}
	public void setAwardReview(Set<AwardReview> awardReview) {
		this.awardReview = awardReview;
	}
	public Integer getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}
	public String getCommitteeAuditOpinion() {
		return committeeAuditOpinion;
	}
	public void setCommitteeAuditOpinion(String committeeAuditOpinion) {
		this.committeeAuditOpinion = committeeAuditOpinion;
	}
	@JSON(serialize=false)
	public SystemOption getSubType() {
		return subType;
	}
	public void setSubType(SystemOption subType) {
		this.subType = subType;
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
	/**
	 * 提交操作结果
	 * @param auditMap 审核参数，Map类型，键值有auditInfo,isSubUni等等
	 */
	@SuppressWarnings("rawtypes")
	public void edit(Map auditMap){
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int result = auditInfo.getAuditResult();//1：不同意	2：同意
		int status = auditInfo.getAuditStatus();//1：退回	2：暂存	3：提交
		saveResult(auditInfo);
		//评审提交不同意不用写最终结果
		if(getStatus() > 1 && getStatus() != 6 && result == 1 && status == 3){
			notAwarded();//不获奖
		}
		if(isSubUni == 1 && getStatus() == 3 && result == 2 && status == 3){//部属高校提交
			if(STATUS_ARRAY[1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus()]);
		}else{
			if(STATUS_ARRAY[1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[result - 1][status - 1][getStatus() - 1]);
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
			setReviewAuditDate(date);
			setReviewAuditResult(result);
			setReviewAuditStatus(status);
			setReviewAuditOpinion(opinion);
			setReviewAuditorName(auditorName);
			setReviewAuditor(auditor);
			setReviewAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 8){
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
	@SuppressWarnings("rawtypes")
	public void submit(Map auditMap) {
		AuditInfo auditInfo = (AuditInfo)auditMap.get("auditInfo");
		int isSubUni = (Integer)auditMap.get("isSubUni");
		int isAgree = getIsAgree();
		//评审提交不同意不用写最终结果
		if(getStatus() > 1 && getStatus() != 6 && isAgree == 1){
			notAwarded();//不获奖
		}
		changeStatus(auditInfo);
		if(isSubUni == 1 && getStatus() == 3 && isAgree == 2){//部属高校提交
			if(STATUS_ARRAY[isAgree - 1][2][getStatus()] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus()]);
		}else{
			if(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1] != 0)
				setStatus(STATUS_ARRAY[isAgree - 1][2][getStatus() - 1]);
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
			setReviewAuditDate(date);
			setReviewAuditStatus(status);
			setReviewAuditorName(auditorName);
			setReviewAuditor(auditor);
			setReviewAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getStatus() == 8){
			setFinalAuditDate(date);
			setFinalAuditStatus(status);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 退回
	 * @param isSubUni 是否隶属部属高校（只有审核才需要，其他可直接传0） 1:是	0：不是(只需要在审核的时候判断)
	 */
	public void back(int isSubUni) {
		backResult(isSubUni);
		if(isSubUni == 1 && getStatus() == 5){//部级退回
			if(STATUS_ARRAY[1][0][getStatus()-2] != 0)
				setStatus(STATUS_ARRAY[1][0][getStatus()-2]);
		}else{
			if(STATUS_ARRAY[1][0][getStatus()-1] != 0)
				setStatus(STATUS_ARRAY[1][0][getStatus()-1]);
		}
	}
	/**
	 * 设置不获奖
	 */
	private void notAwarded(){
		setFinalAuditResult(1);
		setFinalAuditStatus(3);
		if(getStatus() == 1){
			;
		}else if(getStatus() == 2){
			setFinalAuditDate(getDeptInstAuditDate());
			setFinalAuditOpinion(getDeptInstAuditOpinion());
			setFinalAuditorName(getDeptInstAuditorName());
			setFinalAuditor(getDeptInstAuditor());
			setFinalAuditorDept(getDeptInstAuditorDept());
			setFinalAuditorInst(getDeptInstAuditorInst());
		}else if(getStatus() == 3){
			setFinalAuditDate(getUniversityAuditDate());
			setFinalAuditOpinion(getUniversityAuditOpinion());
			setFinalAuditorName(getUniversityAuditorName());
			setFinalAuditor(getUniversityAuditor());
			setFinalAuditorAgency(getUniversityAuditorAgency());
		}else if(getStatus() == 4){
			setFinalAuditDate(getProvinceAuditDate());
			setFinalAuditOpinion(getProvinceAuditOpinion());
			setFinalAuditorName(getProvinceAuditorName());
			setFinalAuditor(getProvinceAuditor());
			setFinalAuditorAgency(getProvinceAuditorAgency());
		}else if(getStatus() == 5){
			setFinalAuditDate(getMinistryAuditDate());
			setFinalAuditOpinion(getMinistryAuditOpinion());
			setFinalAuditorName(getMinistryAuditorName());
			setFinalAuditor(getMinistryAuditor());
			setFinalAuditorAgency(getMinistryAuditorAgency());
		}else if(getStatus() == 6){
			setFinalAuditDate(getReviewDate());
			setFinalAuditOpinion(getReviewOpinion());
			setFinalAuditorName(getReviewerName());
			setFinalAuditor(getReviewer());
			setFinalAuditorAgency(getReviewerAgency());
		}else if(getStatus() == 7){
			setFinalAuditDate(getReviewAuditDate());
			setFinalAuditOpinion(getReviewAuditOpinion());
			setFinalAuditorName(getReviewAuditorName());
			setFinalAuditor(getReviewAuditor());
			setFinalAuditorAgency(getReviewAuditorAgency());
		}else if(getStatus() == 8){
			;
		}
		
	}
	
	/**
	 * 退回
	 * @param isSubUni 是否隶属部属高校（只有审核才需要，其他可直接传0） 1:是	0：不是
	 */
	private void backResult(int isSubUni){
		if(getStatus() == 1)//申请者
			;
		else if(getStatus() == 2) {//院系研究机构
			setApplicantSubmitStatus(1);
			setDeptInstAuditStatus(0);
			setDeptInstAuditResult(0);
			setDeptInstAuditDate(null);
			setDeptInstAuditOpinion(null);
			setDeptInstAuditorName(null);
			setDeptInstAuditor(null);
			setDeptInstAuditorDept(null);
			setDeptInstAuditorInst(null);
		}
		else if(getStatus() == 3) {//校级
			setDeptInstAuditStatus(1);
			setUniversityAuditStatus(0);
			setUniversityAuditResult(0);
			setUniversityAuditDate(null);
			setUniversityAuditOpinion(null);
			setUniversityAuditorName(null);
			setUniversityAuditor(null);
			setUniversityAuditorAgency(null);
		}
		else if(getStatus() == 4){//省厅
			setUniversityAuditStatus(1);
			setProvinceAuditStatus(0);
			setProvinceAuditResult(0);
			setProvinceAuditDate(null);
			setProvinceAuditOpinion(null);
			setProvinceAuditorName(null);
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
			setMinistryAuditDate(null);
			setMinistryAuditOpinion(null);
			setMinistryAuditorName(null);
			setMinistryAuditor(null);
			setMinistryAuditorAgency(null);
		}
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
			return getReviewAuditResult();
		else if(getStatus() == 8)
			return getFinalAuditResult();
		return 0;
	}
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
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