package csdc.bean;

import java.sql.Date;


public class Experience {
	
	private String id;
	private String company;//工作单位
	private Date companyStime;//工作开始时间
	private Date companyEtime;//工作结束时间
	private String position;//职位名称
	private String positionDescription;//职位描述
	private String project;//项目名称
	private Date projectStime;//项目开始时间
	private Date projectEtime;//项目结束时间
	private String dutyDescription;//职责描述
	private Resume resume;//简历
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getCompanyStime() {
		return companyStime;
	}
	public void setCompanyStime(Date companyStime) {
		this.companyStime = companyStime;
	}
	public Date getCompanyEtime() {
		return companyEtime;
	}
	public void setCompanyEtime(Date companyEtime) {
		this.companyEtime = companyEtime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositionDescription() {
		return positionDescription;
	}
	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Date getProjectStime() {
		return projectStime;
	}
	public void setProjectStime(Date projectStime) {
		this.projectStime = projectStime;
	}
	public Date getProjectEtime() {
		return projectEtime;
	}
	public void setProjectEtime(Date projectEtime) {
		this.projectEtime = projectEtime;
	}
	public String getDutyDescription() {
		return dutyDescription;
	}
	public void setDutyDescription(String dutyDescription) {
		this.dutyDescription = dutyDescription;
	}
	public Resume getResume() {
		return resume;
	}
	public void setResume(Resume resume) {
		this.resume = resume;
	}
}