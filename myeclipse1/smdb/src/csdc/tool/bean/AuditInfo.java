package csdc.tool.bean;

import java.util.Date;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Officer;

/**
 * 保存操作相关信息
 * @author 余潜玉
 */
public class AuditInfo implements java.io.Serializable {

	private static final long serialVersionUID = -1804080454891879525L;
	private String auditorName;//审核人姓名
	private Officer auditor;//审核人
	private Department auditorDept;//审核人所在院系
	private Agency auditorAgency;//审核人所在机构
	private Institute auditorInst;//审核人所在研究基地
	private Date auditDate;//审核时间
	private int auditStatus;//审核状态
	private int auditResult;//审核结果
	private String auditOpinion;//审核意见
	public String getAuditorName() {
		return auditorName;
	}
	public Department getAuditorDept() {
		return auditorDept;
	}
	public Institute getAuditorInst() {
		return auditorInst;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Officer getAuditor() {
		return auditor;
	}
	public void setAuditor(Officer auditor) {
		this.auditor = auditor;
	}
	public void setAuditorDept(Department auditorDept) {
		this.auditorDept = auditorDept;
	}
	public void setAuditorInst(Institute auditorInst) {
		this.auditorInst = auditorInst;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Agency getAuditorAgency() {
		return auditorAgency;
	}
	public void setAuditorAgency(Agency auditorAgency) {
		this.auditorAgency = auditorAgency;
	}
	
}