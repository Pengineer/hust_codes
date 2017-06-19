package csdc.bean;

import java.util.Date;

public class Salary {
	private String id;
	private String fixedSalary;//固定基本工资
	private String bonus;//奖金
	private String taxes;//税金
	private String subsidies;//补贴
	private Date releaseTime;//薪酬发放时间
	private Date time;//薪酬所属的年月
	private String salary;//实际发放工资金额
	private Date auditTime;//审核时间
	private int status;//审核状态
	private Account auditor;//审核人
	private Account account;//被审核员工
	private String note;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFixedSalary() {
		return fixedSalary;
	}
	public void setFixedSalary(String fixedSalary) {
		this.fixedSalary = fixedSalary;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getTaxes() {
		return taxes;
	}
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}
	public String getSubsidies() {
		return subsidies;
	}
	public void setSubsidies(String subsidies) {
		this.subsidies = subsidies;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Account getAuditor() {
		return auditor;
	}
	public void setAuditor(Account auditor) {
		this.auditor = auditor;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}