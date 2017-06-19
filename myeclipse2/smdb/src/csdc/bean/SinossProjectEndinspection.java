package csdc.bean;

import java.util.Date;

public class SinossProjectEndinspection {
	
	private String id;//项目结项id（PK）
	private String code;//项目编号
	private String name;//项目名称
	private String sinossProjectId;//管理平台项目申报id
	private String checkStatus;//审核状态
	private Date checkDate;//最后审核日期
	private String checker;//最后审核人
	private String checkInfo;//最后审核内容
	private Date applyDate;//结项申请日期
	private String finishReportId;//结项报告电子附件id
	private Date dumpDate;//数据读取时间
	private String dumpPerson;//数据读取操作人员
	private String from;//数据来源
	private Date importDate;//数据入库时间
	private String importPerson;//数据入库操作人员	
	private String sinossId;//管理平台项目结项id
	private ProjectApplication projectApplication;
	private ProjectEndinspection projectEndinspection;
	private ProjectGranted projectGranted;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinossProjectId() {
		return sinossProjectId;
	}
	public void setSinossProjectId(String sinossProjectId) {
		this.sinossProjectId = sinossProjectId;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getFinishReportId() {
		return finishReportId;
	}
	public void setFinishReportId(String finishReportId) {
		this.finishReportId = finishReportId;
	}
	public Date getDumpDate() {
		return dumpDate;
	}
	public void setDumpDate(Date dumpDate) {
		this.dumpDate = dumpDate;
	}
	public String getDumpPerson() {
		return dumpPerson;
	}
	public void setDumpPerson(String dumpPerson) {
		this.dumpPerson = dumpPerson;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public String getImportPerson() {
		return importPerson;
	}
	public void setImportPerson(String importPerson) {
		this.importPerson = importPerson;
	}
	
	public String getSinossId() {
		return sinossId;
	}
	public void setSinossId(String sinossId) {
		this.sinossId = sinossId;
	}
	public ProjectApplication getProjectApplication() {
		return projectApplication;
	}
	public void setProjectApplication(ProjectApplication projectApplication) {
		this.projectApplication = projectApplication;
	}
	public ProjectEndinspection getProjectEndinspection() {
		return projectEndinspection;
	}
	public void setProjectEndinspection(ProjectEndinspection projectEndinspection) {
		this.projectEndinspection = projectEndinspection;
	}
	public ProjectGranted getProjectGranted() {
		return projectGranted;
	}
	public void setProjectGranted(ProjectGranted projectGranted) {
		this.projectGranted = projectGranted;
	}

	
}
