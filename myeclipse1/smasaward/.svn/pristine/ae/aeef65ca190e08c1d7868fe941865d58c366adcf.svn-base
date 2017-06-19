package csdc.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import org.apache.struts2.json.annotations.JSON;

public class ProjectGranted implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String number;// 项目批准号
	private String name;//项目名称
	private String researchType;//项目名称
	private String universityName;//学校名称
	private String universityCode;//学校代码
	private Date approveDate;//项目批准时间
	private Double approveFee;//项目批准经费
	private Date planEndDate;//计划完成时间
	private int status;//项目状态:1在研；2结项；3中止；4撤项
	private String subtype;//项目子类
	private Date endStopWithdrawDate;//结项、中止、撤项时间
	private String endStopWithdrawPerson;//结项、中止、撤项审核人
	private String endStopWithdrawOpinion;//结项、中止、撤项审核意见
	private String endStopWithdrawOpinionFeedback;//结项、中止、撤项审核意见（反馈给项目负责人）
	private String productType;//成果形式
	private String productTypeOther;//其他成果形式
	private String applicantName;//申请人姓名
	private Date applicantSubmitDate;//立项申请新建时间
	private String agencyName;
	private String divisionName;//依托部门名
	private Integer finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人姓名
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核人意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private String file;//立项计划书文件
	private ProjectFee projectFee;//经费预算明细
	private String type;//项目类型 general；instp
	
	private int isImported;//是否导入数据：[0：走流程smas同步；1：smas直接录入产生]
	private Date importedDate;//导入时间
	
	private ProjectApplication application;// 项目申请
	private Set<ProjectMidinspection> midinspection;//项目中检
	private Set<ProjectVariation> variation;// 项目变更
	private Set<ProjectEndinspection> endinspection;//项目结项
	private Set<ProjectFunding> projectFunding;// 项目拨款
	
	public ProjectGranted(){
	}
	public ProjectGranted(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getResearchType() {
		return researchType;
	}
	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getUniversityCode() {
		return universityCode;
	}
	public void setUniversityCode(String universityCode) {
		this.universityCode = universityCode;
	}
	@JSON(format = "yyyy-MM-dd")
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Double getApproveFee() {
		return approveFee;
	}
	public void setApproveFee(Double approveFee) {
		this.approveFee = approveFee;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
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
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public Date getApplicantSubmitDate() {
		return applicantSubmitDate;
	}
	public void setApplicantSubmitDate(Date applicantSubmitDate) {
		this.applicantSubmitDate = applicantSubmitDate;
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
	public Integer getFinalAuditResult() {
		return finalAuditResult;
	}
	public void setFinalAuditResult(Integer finalAuditResult) {
		this.finalAuditResult = finalAuditResult;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@JSON(serialize = false)
	public ProjectApplication getApplication() {
		return application;
	}
	public void setApplication(ProjectApplication application) {
		this.application = application;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JSON(serialize = false)
	public Set<ProjectMidinspection> getMidinspection() {
		return midinspection;
	}
	public void setMidinspection(Set<ProjectMidinspection> midinspection) {
		this.midinspection = midinspection;
	}
	@JSON(serialize = false)
	public Set<ProjectVariation> getVariation() {
		return variation;
	}
	public void setVariation(Set<ProjectVariation> variation) {
		this.variation = variation;
	}
	@JSON(serialize = false)
	public Set<ProjectEndinspection> getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(Set<ProjectEndinspection> endinspection) {
		this.endinspection = endinspection;
	}
	@JSON(serialize = false)
	public Set<ProjectFunding> getProjectFunding() {
		return projectFunding;
	}
	public void setProjectFunding(Set<ProjectFunding> projectFunding) {
		this.projectFunding = projectFunding;
	}
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getMidinspection() == null) {
			this.setMidinspection((new HashSet<ProjectMidinspection>()));
		}
		this.getMidinspection().add(midinspection);
		midinspection.setGranted(this);
	}
	
	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getVariation() == null) {
			this.setVariation(new HashSet<ProjectVariation>());
		}
		this.getVariation().add(variation);
		variation.setGranted(this);
	}
	
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setEndinspection(new HashSet<ProjectEndinspection>());
		}
		this.getEndinspection().add(endinspection);
		endinspection.setGranted(this);
	}
	
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getProjectFunding() == null) {
			this.setProjectFunding(new HashSet<ProjectFunding>());
		}
		this.getProjectFunding().add(funding);
		funding.setGranted(this);
	}	
}
