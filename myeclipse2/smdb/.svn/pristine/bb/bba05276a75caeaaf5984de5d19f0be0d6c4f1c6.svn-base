package csdc.bean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;
import csdc.tool.bean.AuditInfo;

/**
 * 项目立项基类
 */
public abstract class ProjectGranted {
	
	private String id;//主键id
	private String projectType;//项目类型
	private String number;// 项目批准号
	private String name;//项目名称
	private String englishName;//项目英文名称
	private Agency university;//依托高校
	private Department department;//依托院系
	private Institute institute;//依托研究机构
	private String agencyName;//依托单位名
	private String divisionName;//依托部门名
	private SystemOption province;// 高校所在省
	private String provinceName;//高校所在省
	private Date approveDate;//项目批准时间
	private Date importedDate;//导入时间
	private Date endStopWithdrawDate;//结项、中止、撤项时间
	private String endStopWithdrawPerson;//结项、中止、撤项审核人
	private String endStopWithdrawOpinion;//结项、中止、撤项审核意见
	private String endStopWithdrawOpinionFeedback;//结项、中止、撤项审核意见（反馈给项目负责人）
	private Double approveFee;//项目批准经费
	private int status;//项目状态:1在研；2结项；3中止；4撤项
	//@CheckSystemOptionStandard("projectType")
	private SystemOption subtype;//项目子类
	private String note; //备注
	private int isImported;//是否导入数据：1是，0否
	private int isDupCheckGeneral;//是否进行一般项目重项检查：1是，0否
	private int isDupCheckInstp;//是否进行基地项目重项检查：1是，0否
	private String applicantId;//申请人id
	private String applicantName;//申请人姓名
	private String productType;//成果形式
	private String productTypeOther;//其他成果形式
	private Date planEndDate;//计划完成时间
	private Integer memberGroupNumber;//项目成员组编号
	private Double approveFeeMinistry;//教育部资助经费（万）
	private Double approveFeeUniversity;//招标学校配套经费（万）
	private Double approveFeeSubjection;//教育厅及有关部委资助经费（万）
	private Double approveFeeOther;//其他来源（万）
	private Set<ProjectProduct> projectProduct;//项目相关成果
	private String applicationId;
	private ProjectFee projectFee;//经费预算明细
	
	private Integer auditstatus;//立项业务状态
	private Integer applicantSubmitStatus;//立项申请新建状态
	private Date applicantSubmitDate;//立项申请新建时间
	private Integer deptInstAuditStatus;//院系/研究机构审核状态
	private Integer deptInstAuditResult;//院系/研究机构中检审核结果
	private String deptInstAuditorName;//院系/研究机构审核人名称
	private Officer deptInstAuditor;//院系/研究机构审核人
	private Department deptInstAuditorDept;//院系/研究机构审核人所在院系
	private Institute deptInstAuditorInst;//院系/研究机构审核人所在研究机构
	private String deptInstAuditOpinion;//院系/研究机构审核意见
	private Date deptInstAuditDate;//院系/研究机构审核时间
	private Integer universityAuditStatus;//高校审核状态
	private Integer universityAuditResult;//高校审核结果
    private String universityAuditorName;//高校审核人名称
    private Officer universityAuditor;//高校审核人
    private Agency universityAuditorAgency;//高校审核人所在机构
	private String universityAuditOpinion;//高校审核意见
	private Date universityAuditDate;//高校审核时间
	private Integer provinceAuditStatus;//省厅审核状态
	private Integer provinceAuditResult;//省厅审核结果
    private String provinceAuditorName;//省厅审核人名称
    private Officer provinceAuditor;//省厅审核人
    private Agency provinceAuditorAgency;//省厅审核人所在机构
	private String provinceAuditOpinion;//省厅审核意见
	private Date provinceAuditDate;//省厅审核时间
	private Integer finalAuditStatus;//最终审核状态
	private Integer finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人名称
	private Officer finalAuditor;//最终审核人
	private Agency finalAuditorAgency;//最终审核人所在机构
	private Department finalAuditorDept;//最终审核人所在院系
	private Institute finalAuditorInst;//最终审核人所在研究机构
	private String finalAuditOpinion;//最终审核人意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private Date finalAuditDate;//最终审核时间
	private String file;//立项计划书文件
	private Integer auditType;//审核类型[0：走流程审核；1：教育部直接录入]
	private Date importAuditDate;//教育部录入立项计划时间
	private int isPublished;
	
	public Date getImportAuditDate() {
		return importAuditDate;
	}

	public void setImportAuditDate(Date importAuditDate) {
		this.importAuditDate = importAuditDate;
	}

	/**
	 * 状态机STATUS_ARRAY是一个大小是[2][3][5]的三维数组。
	 * 一维：结果（1不同意，2同意）；
	 * 二维：操作（1退回，2暂存，3提交）；
	 * 三维：状态（1新建申请，2院系/研究机构审核，3校级审核，4省级审核，5最终审核）
	 * 数组中的0表示没有此操作
	 */
	private static final int[][][] STATUS_ARRAY =  new int[][][]
	{
		{{0,1,2,3,4}, {1,2,3,4,5}, {2,2,3,4,5}},
		{{0,1,2,3,4}, {1,2,3,4,5}, {2,3,4,5,5}}
	};
	
	public static Map<String, String> typeMap = new LinkedHashMap<String, String>();
	
	public static void sortTypeMap () {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] type = {"general", "key", "instp", "post", "entrust"};
		for(int i = 0; i < type.length; i++) {
			if(null != typeMap.get(type[i])) {
				map.put(type[i], typeMap.get(type[i]));
			}
		}
		typeMap = map;
	}
	    
    public static String findTypeName(String projectType) {//通过项目类型获得中文名称
    	return typeMap.get(projectType);
    }
    
//	@JSON(serialize = false)
//	public abstract ProjectApplication getApplication();
    
    /**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public abstract Set<? extends ProjectAnninspection> getAnninspection();
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public abstract Set<? extends ProjectMidinspection> getMidinspection();
	
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public abstract Set<? extends ProjectEndinspection> getEndinspection();
	
	/**
	 * 获取项目变更对象集合
	 */
	@JSON(serialize = false)
	public abstract Set<? extends ProjectVariation> getVariation();
	
	/**
	 * 获取项目拨款对象集合
	 */
	@JSON(serialize = false)
	public abstract Set<? extends ProjectFunding> getFunding();
	
	/**
	 * 添加项目年检
	 */
	public abstract void addAnninspection(ProjectAnninspection anninspection);
	
	/**
	 * 添加项目中检
	 */
	public abstract void addMidinspection(ProjectMidinspection midinspection);
	
	/**
	 * 添加项目结项
	 */
	public abstract void addEndinspection(ProjectEndinspection endinspection);
	
	/**
	 * 添加项目变更
	 */
	public abstract void addVariation(ProjectVariation variation);
	
	/**
	 * 添加一个拨款
	 * @param postGranted
	 */
	public abstract void addFunding(ProjectFunding funding);
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public abstract String getApplicationClassName();
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public abstract String getGrantedClassName();
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public abstract String getAnninspectionClassName();
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public abstract String getMidinspectionClassName();
	
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
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public abstract String getVariationClassName();
	
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public abstract String getFundingClassName();
	
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndStopWithdrawDate() {
		return endStopWithdrawDate;
	}
	public void setEndStopWithdrawDate(Date endStopWithdrawDate) {
		this.endStopWithdrawDate = endStopWithdrawDate;
	}
	public String getEndStopWithdrawPerson() {
		return endStopWithdrawPerson;
	}
	public void setEndStopWithdrawPerson(String endStopWithdrawPerson) {
		this.endStopWithdrawPerson = endStopWithdrawPerson;
	}
	public String getEndStopWithdrawOpinion() {
		return endStopWithdrawOpinion;
	}
	public void setEndStopWithdrawOpinion(String endStopWithdrawOpinion) {
		this.endStopWithdrawOpinion = endStopWithdrawOpinion;
	}
	public String getEndStopWithdrawOpinionFeedback() {
		return endStopWithdrawOpinionFeedback;
	}
	public void setEndStopWithdrawOpinionFeedback(
			String endStopWithdrawOpinionFeedback) {
		this.endStopWithdrawOpinionFeedback = endStopWithdrawOpinionFeedback;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@JSON(serialize = false)
	public SystemOption getSubtype() {
		return subtype;
	}
	public void setSubtype(SystemOption subtype) {
		this.subtype = subtype;
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

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	@JSON(format="yyyy-MM-dd")
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public Integer getMemberGroupNumber() {
		return memberGroupNumber;
	}
	public void setMemberGroupNumber(Integer memberGroupNumber) {
		this.memberGroupNumber = memberGroupNumber;
	}
	@JSON(serialize = false)
	public Set<ProjectProduct> getProjectProduct() {
		return projectProduct;
	}

	public void setProjectProduct(Set<ProjectProduct> projectProduct) {
		this.projectProduct = projectProduct;
	}

	@JSON(serialize = false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	@JSON(serialize = false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize = false)
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

	public Double getApproveFeeMinistry() {
		return approveFeeMinistry;
	}

	public void setApproveFeeMinistry(Double approveFeeMinistry) {
		this.approveFeeMinistry = approveFeeMinistry;
	}

	public Double getApproveFeeUniversity() {
		return approveFeeUniversity;
	}

	public void setApproveFeeUniversity(Double approveFeeUniversity) {
		this.approveFeeUniversity = approveFeeUniversity;
	}

	public Double getApproveFeeSubjection() {
		return approveFeeSubjection;
	}

	public void setApproveFeeSubjection(Double approveFeeSubjection) {
		this.approveFeeSubjection = approveFeeSubjection;
	}

	public Double getApproveFeeOther() {
		return approveFeeOther;
	}

	public void setApproveFeeOther(Double approveFeeOther) {
		this.approveFeeOther = approveFeeOther;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public int getIsDupCheckGeneral() {
		return isDupCheckGeneral;
	}

	public void setIsDupCheckGeneral(int isDupCheckGeneral) {
		this.isDupCheckGeneral = isDupCheckGeneral;
	}

	public Date getImportedDate() {
		return importedDate;
	}

	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	public int getIsDupCheckInstp() {
		return isDupCheckInstp;
	}

	public void setIsDupCheckInstp(int isDupCheckInstp) {
		this.isDupCheckInstp = isDupCheckInstp;
	}

	public void setProjectFee(ProjectFee projectFee) {
		this.projectFee = projectFee;
	}
	@JSON(serialize = false)
	public ProjectFee getProjectFee() {
		return projectFee;
	}

	public Integer getAuditstatus() {
		return auditstatus;
	}

	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
	}

	public Integer getApplicantSubmitStatus() {
		return applicantSubmitStatus;
	}

	public void setApplicantSubmitStatus(Integer applicantSubmitStatus) {
		this.applicantSubmitStatus = applicantSubmitStatus;
	}

	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}

	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
	}

	public Integer getDeptInstAuditStatus() {
		return deptInstAuditStatus;
	}

	public void setDeptInstAuditStatus(Integer deptInstAuditStatus) {
		this.deptInstAuditStatus = deptInstAuditStatus;
	}

	public Integer getDeptInstAuditResult() {
		return deptInstAuditResult;
	}

	public void setDeptInstAuditResult(Integer deptInstAuditResult) {
		this.deptInstAuditResult = deptInstAuditResult;
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

	public String getDeptInstAuditorName() {
		return deptInstAuditorName;
	}

	public void setDeptInstAuditorName(String deptInstAuditorName) {
		this.deptInstAuditorName = deptInstAuditorName;
	}
	@JSON(serialize = false)
	public Officer getDeptInstAuditor() {
		return deptInstAuditor;
	}

	public void setDeptInstAuditor(Officer deptInstAuditor) {
		this.deptInstAuditor = deptInstAuditor;
	}
	@JSON(serialize = false)
	public Department getDeptInstAuditorDept() {
		return deptInstAuditorDept;
	}

	public void setDeptInstAuditorDept(Department deptInstAuditorDept) {
		this.deptInstAuditorDept = deptInstAuditorDept;
	}
	@JSON(serialize = false)
	public Institute getDeptInstAuditorInst() {
		return deptInstAuditorInst;
	}

	public void setDeptInstAuditorInst(Institute deptInstAuditorInst) {
		this.deptInstAuditorInst = deptInstAuditorInst;
	}

	public String getDeptInstAuditOpinion() {
		return deptInstAuditOpinion;
	}

	public void setDeptInstAuditOpinion(String deptInstAuditOpinion) {
		this.deptInstAuditOpinion = deptInstAuditOpinion;
	}

	public Date getDeptInstAuditDate() {
		return deptInstAuditDate;
	}

	public void setDeptInstAuditDate(Date deptInstAuditDate) {
		this.deptInstAuditDate = deptInstAuditDate;
	}

	public Integer getUniversityAuditStatus() {
		return universityAuditStatus;
	}

	public void setUniversityAuditStatus(Integer universityAuditStatus) {
		this.universityAuditStatus = universityAuditStatus;
	}

	public Integer getUniversityAuditResult() {
		return universityAuditResult;
	}

	public void setUniversityAuditResult(Integer universityAuditResult) {
		this.universityAuditResult = universityAuditResult;
	}

	public String getUniversityAuditorName() {
		return universityAuditorName;
	}

	public void setUniversityAuditorName(String universityAuditorName) {
		this.universityAuditorName = universityAuditorName;
	}
	@JSON(serialize = false)
	public Officer getUniversityAuditor() {
		return universityAuditor;
	}

	public void setUniversityAuditor(Officer universityAuditor) {
		this.universityAuditor = universityAuditor;
	}
	@JSON(serialize = false)
	public Agency getUniversityAuditorAgency() {
		return universityAuditorAgency;
	}

	public void setUniversityAuditorAgency(Agency universityAuditorAgency) {
		this.universityAuditorAgency = universityAuditorAgency;
	}

	public String getUniversityAuditOpinion() {
		return universityAuditOpinion;
	}

	public void setUniversityAuditOpinion(String universityAuditOpinion) {
		this.universityAuditOpinion = universityAuditOpinion;
	}

	public Date getUniversityAuditDate() {
		return universityAuditDate;
	}

	public void setUniversityAuditDate(Date universityAuditDate) {
		this.universityAuditDate = universityAuditDate;
	}

	public Integer getProvinceAuditStatus() {
		return provinceAuditStatus;
	}

	public void setProvinceAuditStatus(Integer provinceAuditStatus) {
		this.provinceAuditStatus = provinceAuditStatus;
	}

	public Integer getProvinceAuditResult() {
		return provinceAuditResult;
	}

	public void setProvinceAuditResult(Integer provinceAuditResult) {
		this.provinceAuditResult = provinceAuditResult;
	}

	public String getProvinceAuditorName() {
		return provinceAuditorName;
	}

	public void setProvinceAuditorName(String provinceAuditorName) {
		this.provinceAuditorName = provinceAuditorName;
	}
	@JSON(serialize = false)
	public Officer getProvinceAuditor() {
		return provinceAuditor;
	}

	public void setProvinceAuditor(Officer provinceAuditor) {
		this.provinceAuditor = provinceAuditor;
	}
	@JSON(serialize = false)
	public Agency getProvinceAuditorAgency() {
		return provinceAuditorAgency;
	}

	public void setProvinceAuditorAgency(Agency provinceAuditorAgency) {
		this.provinceAuditorAgency = provinceAuditorAgency;
	}

	public String getProvinceAuditOpinion() {
		return provinceAuditOpinion;
	}

	public void setProvinceAuditOpinion(String provinceAuditOpinion) {
		this.provinceAuditOpinion = provinceAuditOpinion;
	}

	public Date getProvinceAuditDate() {
		return provinceAuditDate;
	}

	public void setProvinceAuditDate(Date provinceAuditDate) {
		this.provinceAuditDate = provinceAuditDate;
	}

	public Integer getFinalAuditStatus() {
		return finalAuditStatus;
	}

	public void setFinalAuditStatus(Integer finalAuditStatus) {
		this.finalAuditStatus = finalAuditStatus;
	}

	public Integer getFinalAuditResult() {
		return finalAuditResult;
	}

	public void setFinalAuditResult(Integer finalAuditResult) {
		this.finalAuditResult = finalAuditResult;
	}

	public String getFinalAuditorName() {
		return finalAuditorName;
	}

	public void setFinalAuditorName(String finalAuditorName) {
		this.finalAuditorName = finalAuditorName;
	}
	@JSON(serialize = false)
	public Officer getFinalAuditor() {
		return finalAuditor;
	}

	public void setFinalAuditor(Officer finalAuditor) {
		this.finalAuditor = finalAuditor;
	}
	@JSON(serialize = false)
	public Agency getFinalAuditorAgency() {
		return finalAuditorAgency;
	}

	public void setFinalAuditorAgency(Agency finalAuditorAgency) {
		this.finalAuditorAgency = finalAuditorAgency;
	}
	@JSON(serialize = false)
	public Department getFinalAuditorDept() {
		return finalAuditorDept;
	}

	public void setFinalAuditorDept(Department finalAuditorDept) {
		this.finalAuditorDept = finalAuditorDept;
	}
	@JSON(serialize = false)
	public Institute getFinalAuditorInst() {
		return finalAuditorInst;
	}

	public void setFinalAuditorInst(Institute finalAuditorInst) {
		this.finalAuditorInst = finalAuditorInst;
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
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getFinalAuditDate() {
		return finalAuditDate;
	}

	public void setFinalAuditDate(Date finalAuditDate) {
		this.finalAuditDate = finalAuditDate;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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
		int auditstatus = auditInfo.getAuditStatus();//1：退回	2：暂存	3：提交
		saveResult(auditInfo);
		if(isSubUni == 1 && getAuditstatus() == 3 && result == 2 && auditstatus == 3){//部属高校提交
			if(STATUS_ARRAY[1][2][getAuditstatus()] != 0)
				setAuditstatus(STATUS_ARRAY[result - 1][auditstatus - 1][getAuditstatus()]);
		}else{
			if(STATUS_ARRAY[1][2][getAuditstatus() - 1] != 0)
				setAuditstatus(STATUS_ARRAY[result - 1][auditstatus - 1][getAuditstatus() - 1]);
		}
		if(getAuditstatus() > 1 && result == 1 && auditstatus == 3){
			notApproved();//审核不通过
		}
	}
	
	/**
	 *保存结果
	 * @param auditMap 审核参数，Map类型，键值有audit	Info,isSubUni等等
	 */
	private void saveResult(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int result = auditInfo.getAuditResult();
		int auditstatus = auditInfo.getAuditStatus();
		String opinion = auditInfo.getAuditOpinion();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getAuditstatus() == 0){
			setAuditstatus(1);
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(auditstatus);
		}else if(getAuditstatus() == 1){
			setApplicantSubmitDate(date);
			setApplicantSubmitStatus(auditstatus);
		}else if(getAuditstatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstAuditResult(result);
			setDeptInstAuditStatus(auditstatus);
			setDeptInstAuditOpinion(opinion);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getAuditstatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditResult(result);
			setUniversityAuditStatus(auditstatus);
			setUniversityAuditOpinion(opinion);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getAuditstatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditResult(result);
			setProvinceAuditStatus(auditstatus);
			setProvinceAuditOpinion(opinion);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getAuditstatus() == 5){
			setFinalAuditDate(date);
			setFinalAuditResult(result);
			setFinalAuditStatus(auditstatus);
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
		changeStatus(auditInfo);
		if(isSubUni == 1 && getAuditstatus() == 3 && isAgree == 2){//部属高校提交
			if(STATUS_ARRAY[isAgree - 1][2][getAuditstatus()] != 0)
				setAuditstatus(STATUS_ARRAY[isAgree - 1][2][getAuditstatus()]);
		}else{
			if(STATUS_ARRAY[isAgree - 1][2][getAuditstatus() - 1] != 0)
				setAuditstatus(STATUS_ARRAY[isAgree - 1][2][getAuditstatus() - 1]);
		}
		if(getAuditstatus() > 1 && isAgree == 1)
			notApproved();//不同意
	}
	
	/**
	 * @param auditInfo 审核信息
	 */
	private void changeStatus(AuditInfo auditInfo) {
		Date date = auditInfo.getAuditDate();
		int status = auditInfo.getAuditStatus();
		String auditorName = auditInfo.getAuditorName();
		Officer auditor = auditInfo.getAuditor();
		if(getAuditstatus() == 1){
			setApplicantSubmitStatus(status);
			setApplicantSubmitDate(date);
		}else if(getAuditstatus() == 2){
			setDeptInstAuditDate(date);
			setDeptInstAuditStatus(status);
			setDeptInstAuditorName(auditorName);
			setDeptInstAuditor(auditor);
			setDeptInstAuditorDept(auditInfo.getAuditorDept());
			setDeptInstAuditorInst(auditInfo.getAuditorInst());
		}else if(getAuditstatus() == 3){
			setUniversityAuditDate(date);
			setUniversityAuditStatus(status);
			setUniversityAuditorName(auditorName);
			setUniversityAuditor(auditor);
			setUniversityAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getAuditstatus() == 4){
			setProvinceAuditDate(date);
			setProvinceAuditStatus(status);
			setProvinceAuditorName(auditorName);
			setProvinceAuditor(auditor);
			setProvinceAuditorAgency(auditInfo.getAuditorAgency());
		}else if(getAuditstatus() == 5){
			setFinalAuditDate(date);
			setFinalAuditStatus(status);
			setFinalAuditorName(auditorName);
			setFinalAuditor(auditor);
			setFinalAuditorAgency(auditInfo.getAuditorAgency());
		}
	}
	
	/**
	 * 退回
	 * @param isSubUni 是否部属高校项目（只有审核才需要，其他可直接传0） 1:是	0：不是
	 */
	public void back(int isSubUni) {
		backResult(isSubUni);
		if(isSubUni == 1 && getAuditstatus() == 5){//部级退回
			if(STATUS_ARRAY[1][0][getAuditstatus()- 2]!= 0)
				setAuditstatus(STATUS_ARRAY[1][0][getAuditstatus()- 2]);
		}else{
			if(STATUS_ARRAY[1][0][getAuditstatus()- 1]!= 0)
				setAuditstatus(STATUS_ARRAY[1][0][getAuditstatus()- 1]);
		}
	}
	
	/**
	 * 设置不同意
	 */
	private void notApproved(){
		setFinalAuditResult(1);
		setFinalAuditStatus(3);
		if(getAuditstatus() == 1){
			;
		}else if(getAuditstatus() == 2){
			setFinalAuditorName(getDeptInstAuditorName());
			setFinalAuditOpinion(getDeptInstAuditOpinion());
			setFinalAuditDate(getDeptInstAuditDate());
			setFinalAuditor(getDeptInstAuditor());
			setFinalAuditorDept(getDeptInstAuditorDept());
			setFinalAuditorInst(getDeptInstAuditorInst());
		}else if(getAuditstatus() == 3){
			setFinalAuditorName(getUniversityAuditorName());
			setFinalAuditOpinion(getUniversityAuditOpinion());
			setFinalAuditDate(getUniversityAuditDate());
			setFinalAuditor(getUniversityAuditor());
			setFinalAuditorAgency(getUniversityAuditorAgency());
		}else if(getAuditstatus() == 4){
			setFinalAuditorName(getProvinceAuditorName());
			setFinalAuditOpinion(getProvinceAuditOpinion());
			setFinalAuditDate(getProvinceAuditDate());
			setFinalAuditor(getProvinceAuditor());
			setFinalAuditorAgency(getProvinceAuditorAgency());
		}else if(getAuditstatus() == 5){
			;
		}
		
	}

	/**
	 * 退回
	 * @param isSubUni（只有审核才需要，其他可直接传0） 是否部属高校项目 1:是	0：不是
	 */
	private void backResult(int isSubUni){
		if(getAuditstatus() == 1)//申请者
			;
		else if(getAuditstatus() == 2) {//院系研究机构
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
		else if(getAuditstatus() == 3) {//校级
			setDeptInstAuditStatus(1);
			setUniversityAuditStatus(0);
			setUniversityAuditResult(0);
			setUniversityAuditOpinion(null);
			setUniversityAuditorName(null);
			setUniversityAuditDate(null);
			setUniversityAuditor(null);
			setUniversityAuditorAgency(null);

		}
		else if(getAuditstatus() == 4){//省厅
			setUniversityAuditStatus(1);
			setProvinceAuditStatus(0);
			setProvinceAuditResult(0);
			setProvinceAuditOpinion(null);
			setProvinceAuditorName(null);
			setProvinceAuditDate(null);
			setProvinceAuditor(null);
			setProvinceAuditorAgency(null);
		}
		else if(getAuditstatus() == 5) {//部级
			if(isSubUni == 1) {
				setUniversityAuditStatus(1);
			}
			else if(isSubUni == 0) {
				setProvinceAuditStatus(1);
			}
			setFinalAuditStatus(0);
			setFinalAuditResult(0);
			setFinalAuditOpinion(null);
			setFinalAuditorName(null);
			setFinalAuditDate(null);
			setFinalAuditor(null);
			setFinalAuditorAgency(null);
		}
		setFinalAuditOpinionFeedback(null);
	}
	
	/**
	 * 获取当前状态级别的是否同意信息
	 * @return 当前状态级别的是否同意
	 */
	private int getIsAgree() {
		if(getAuditstatus() == 1)
			return 2;
		else if(getAuditstatus() == 2)
			return getDeptInstAuditResult();
		else if(getAuditstatus() == 3)
			return getUniversityAuditResult();
		else if(getAuditstatus() == 4)
			return getProvinceAuditResult();
		else if(getAuditstatus() == 5)
			return getFinalAuditResult();
		return 0;
	}
	
	
}