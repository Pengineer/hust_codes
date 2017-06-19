package csdc.bean;

import java.util.Date;

public class Attendance {
	private String id;
	private Account account;
	private Date signupTime;//签到时间
	private Date logoutTime;//签退时间
	private String note;//备注
	private int type;//请假类型
	private Date applyTime;//申请时间
	private int days;//天数
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private int status;//审批状态
	private Account auditAccount;//证明人
	private int flag;//考勤标记(考勤标记(1:签到签离,2:请假,3:出差,4:加班))
	private Date auditDateTime;//审核时间
	private String auditOpinion;//审核意见
	private int auditResult;//审核结果
	
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
	public Date getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}
	public Date getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Account getAuditAccount() {
		return auditAccount;
	}
	public void setAuditAccount(Account auditAccount) {
		this.auditAccount = auditAccount;
	}
	public Date getAuditDateTime() {
		return auditDateTime;
	}
	public void setAuditDateTime(Date auditDateTime) {
		this.auditDateTime = auditDateTime;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public int getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(int auditResult) {
		this.auditResult = auditResult;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

}