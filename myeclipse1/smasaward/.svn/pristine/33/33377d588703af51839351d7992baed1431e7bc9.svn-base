package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectMidinspection  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String file;//中检申请书文件
	private Date applicantSubmitDate;//中检申请新建时间
	private String progress;//研究工作进展情况
	private String productIntroduction;//1到2项代表性成果简介
	private int finalAuditResult;//最终审核结果
	private String finalAuditorName;//最终审核人姓名
	private Date finalAuditDate;//最终审核时间
	private String finalAuditOpinion;//最终审核人意见
	private String finalAuditOpinionFeedback;//最终审核意见（反馈给项目负责人）
	private String type;//项目类型 general instp
	
	private ProjectFee projectFee;//中检经费明细	
	private ProjectGranted granted;//项目立项

	private int isImported;//是否导入数据：[0：走流程smas同步；1：smas直接录入产生]
	private Date importedDate;//导入时间

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
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getProductIntroduction() {
		return productIntroduction;
	}
	public void setProductIntroduction(String productIntroduction) {
		this.productIntroduction = productIntroduction;
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
}
