package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

/**
 * 业务表
 * @author 雷达、肖雅
 */
public class Business implements java.io.Serializable  {
	private static final long serialVersionUID = -7567419913197333532L;
	private String id;
	private String type;//业务类型
	//@CheckSystemOptionStandard("businessType")
	private SystemOption subType;//业务子类型 
	private Integer status;//业务状态：1受理中,2停止
	private Integer startYear;//业务对象起始年份
	private Integer startSession;//业务对象起始届次
	private Integer endYear;//业务对象截止年份
	private Integer endSession;//业务对象截止届次
	private Date startDate;//业务起始时间
	private Date applicantDeadline;//个人申报截止时间
	private Date deptInstDeadline;//部门审核截止时间
	private Date univDeadline;//高校审核截止时间
	private Date provDeadline;//省厅审核截止时间
	private Date reviewDeadline;//评审截止时间
	private Integer businessYear;//项目年份
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStartSession() {
		return startSession;
	}
	public void setStartSession(Integer startSession) {
		this.startSession = startSession;
	}
	public Integer getEndYear() {
		return endYear;
	}
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	public Integer getEndSession() {
		return endSession;
	}
	public void setEndSession(Integer endSession) {
		this.endSession = endSession;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApplicantDeadline() {
		return applicantDeadline;
	}
	public void setApplicantDeadline(Date applicantDeadline) {
		this.applicantDeadline = applicantDeadline;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDeptInstDeadline() {
		return deptInstDeadline;
	}
	public void setDeptInstDeadline(Date deptInstDeadline) {
		this.deptInstDeadline = deptInstDeadline;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUnivDeadline() {
		return univDeadline;
	}
	public void setUnivDeadline(Date univDeadline) {
		this.univDeadline = univDeadline;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getProvDeadline() {
		return provDeadline;
	}
	public void setProvDeadline(Date provDeadline) {
		this.provDeadline = provDeadline;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getReviewDeadline() {
		return reviewDeadline;
	}
	public void setReviewDeadline(Date reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SystemOption getSubType() {
		return subType;
	}
	public void setSubType(SystemOption subType) {
		this.subType = subType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStartYear() {
		return startYear;
	}
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	public Integer getBusinessYear() {
		return businessYear;
	}
	public void setBusinessYear(Integer businessYear) {
		this.businessYear = businessYear;
	}
}
