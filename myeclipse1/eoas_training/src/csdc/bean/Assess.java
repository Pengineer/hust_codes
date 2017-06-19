package csdc.bean;

import java.util.Date;
public class Assess {
	public String id;
	public Account account;//被审核人
	public Account auditor;//审核人
	public String ascores;//考勤分数
	public String pscores;//工作表现分数
	public String total;//考核总评
	public Date createTime;//考核时间
	public int status;//审核状态
	public Date auditTime;//审核时间
	public int type;//考核类型
	public String note;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Account getAuditor() {
		return auditor;
	}
	public void setAuditor(Account auditor) {
		this.auditor = auditor;
	}
	public String getAscores() {
		return ascores;
	}
	public void setAscores(String ascores) {
		this.ascores = ascores;
	}
	public String getPscores() {
		return pscores;
	}
	public void setPscores(String pscores) {
		this.pscores = pscores;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}