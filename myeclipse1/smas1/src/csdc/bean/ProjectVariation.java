package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String file;//变更申请文件
	private Date applicantSubmitDate;//新建申请时间
	private String variationReason;//变更申请原因
	private String universityAuditResultDetail;//高校审核结果详情
	private String provinceAuditResultDetail;//省厅审核结果详情
	//变更事项
	private int changeMember;//变更项目成员:默认0；1变更；2不变更
	private String oldMembers;//变更前项目成员
	private String newMembers;//变更后项目成员
	private int changeAgency;//变更管理机构
	private String oldAgencyName;//原机构名
	private String newAgencyName;//新机构名
	private int changeProductType;//变更成果形式
	private String oldProductType;//原成果形式
	private String oldProductTypeOther;//原其他成果形式
	private String newProductType;//新成果形式
	private String newProductTypeOther;//新其他成果形式	
	private int changeName;//变更项目名称
	private String oldName;//原名称
	private String newName;//新名称
	private int changeContent;//变更研究内容
	private int postponement;//延期：每次只能延期一年，最多延期两次
	private Date oldOnceDate;//延期一次前计划完成时间
	private Date newOnceDate;//延期一次后计划完成时间
	private int stop;//自行中止
	private int withdraw;//申请撤项
	private int other;//其他
	private String otherInfo;//其他信息
	private String postponementPlanFile;//变更延期研究计划
	private int changeFee;//变更经费
	private ProjectFee oldProjectFee;//原经费预算id
	private ProjectFee newProjectFee;//新经费预算id
	private int finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人姓名
	private String finalAuditResultDetail;//最终审核结果详情
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private String type;//项目类型 general instp
	
	private int isImported;//是否导入数据：[0：走流程smas同步；1：smas直接录入产生]
	private Date importedDate;//导入时间
	
	private ProjectGranted granted;//项目立项

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getVariationReason() {
		return variationReason;
	}
	public void setVariationReason(String variationReason) {
		this.variationReason = variationReason;
	}
	public String getUniversityAuditResultDetail() {
		return universityAuditResultDetail;
	}
	public void setUniversityAuditResultDetail(String universityAuditResultDetail) {
		this.universityAuditResultDetail = universityAuditResultDetail;
	}
	public String getProvinceAuditResultDetail() {
		return provinceAuditResultDetail;
	}
	public void setProvinceAuditResultDetail(String provinceAuditResultDetail) {
		this.provinceAuditResultDetail = provinceAuditResultDetail;
	}
	public int getChangeMember() {
		return changeMember;
	}
	public void setChangeMember(int changeMember) {
		this.changeMember = changeMember;
	}
	public String getOldMembers() {
		return oldMembers;
	}
	public void setOldMembers(String oldMembers) {
		this.oldMembers = oldMembers;
	}
	public String getNewMembers() {
		return newMembers;
	}
	public void setNewMembers(String newMembers) {
		this.newMembers = newMembers;
	}
	public int getChangeAgency() {
		return changeAgency;
	}
	public void setChangeAgency(int changeAgency) {
		this.changeAgency = changeAgency;
	}
	public String getOldAgencyName() {
		return oldAgencyName;
	}
	public void setOldAgencyName(String oldAgencyName) {
		this.oldAgencyName = oldAgencyName;
	}
	public String getNewAgencyName() {
		return newAgencyName;
	}
	public void setNewAgencyName(String newAgencyName) {
		this.newAgencyName = newAgencyName;
	}
	public int getChangeProductType() {
		return changeProductType;
	}
	public void setChangeProductType(int changeProductType) {
		this.changeProductType = changeProductType;
	}
	public String getOldProductType() {
		return oldProductType;
	}
	public void setOldProductType(String oldProductType) {
		this.oldProductType = oldProductType;
	}
	public String getOldProductTypeOther() {
		return oldProductTypeOther;
	}
	public void setOldProductTypeOther(String oldProductTypeOther) {
		this.oldProductTypeOther = oldProductTypeOther;
	}
	public String getNewProductType() {
		return newProductType;
	}
	public void setNewProductType(String newProductType) {
		this.newProductType = newProductType;
	}
	public String getNewProductTypeOther() {
		return newProductTypeOther;
	}
	public void setNewProductTypeOther(String newProductTypeOther) {
		this.newProductTypeOther = newProductTypeOther;
	}
	public int getChangeName() {
		return changeName;
	}
	public void setChangeName(int changeName) {
		this.changeName = changeName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public int getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(int changeContent) {
		this.changeContent = changeContent;
	}
	public int getPostponement() {
		return postponement;
	}
	public void setPostponement(int postponement) {
		this.postponement = postponement;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getOldOnceDate() {
		return oldOnceDate;
	}
	public void setOldOnceDate(Date oldOnceDate) {
		this.oldOnceDate = oldOnceDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getNewOnceDate() {
		return newOnceDate;
	}
	public void setNewOnceDate(Date newOnceDate) {
		this.newOnceDate = newOnceDate;
	}
	public int getStop() {
		return stop;
	}
	public void setStop(int stop) {
		this.stop = stop;
	}
	public int getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getPostponementPlanFile() {
		return postponementPlanFile;
	}
	public void setPostponementPlanFile(String postponementPlanFile) {
		this.postponementPlanFile = postponementPlanFile;
	}
	public int getChangeFee() {
		return changeFee;
	}
	public void setChangeFee(int changeFee) {
		this.changeFee = changeFee;
	}
	@JSON(serialize=false)
	public ProjectFee getOldProjectFee() {
		return oldProjectFee;
	}
	public void setOldProjectFee(ProjectFee oldProjectFee) {
		this.oldProjectFee = oldProjectFee;
	}
	@JSON(serialize=false)
	public ProjectFee getNewProjectFee() {
		return newProjectFee;
	}
	public void setNewProjectFee(ProjectFee newProjectFee) {
		this.newProjectFee = newProjectFee;
	}
	public int getFinalAuditResult() {
		return finalAuditResult;
	}
	public void setFinalAuditResult(int finalAuditResult) {
		this.finalAuditResult = finalAuditResult;
	}
	public String getFinalAuditResultDetail() {
		return finalAuditResultDetail;
	}
	public void setFinalAuditResultDetail(String finalAuditResultDetail) {
		this.finalAuditResultDetail = finalAuditResultDetail;
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
}
