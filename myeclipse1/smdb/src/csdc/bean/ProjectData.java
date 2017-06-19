package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectData implements Serializable {

    private static final long serialVersionUID = -7699249996513144195L;
    private String id;
    //@CheckSystemOptionStandard("projectType")
    private SystemOption projectType;
    private String granted;
    private String endinspection;
    private String file;
    private String keywords;
    private String summary;
    private String introduction;
    private String producerName;
    private Agency university;
    private Department department;
    private Institute institute;
    private String agencyName;
    private String divisionName;
    private String surveyMethod;
    private String surveyField;
    private Date startDate;
    private Date endDate;
    private Date submitDate;
    private String note;
    private String dfs;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGranted() {
		return granted;
	}
	public void setGranted(String granted) {
		this.granted = granted;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getProducerName() {
		return producerName;
	}
	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}
	@JSON(serialize=false)
	public Agency getUniversity() {
		return university;
	}
	public void setUniversity(Agency university) {
		this.university = university;
	}
	@JSON(serialize=false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	@JSON(serialize=false)
	public SystemOption getProjectType() {
		return projectType;
	}
	public void setProjectType(SystemOption projectType) {
		this.projectType = projectType;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getSurveyMethod() {
		return surveyMethod;
	}
	public void setSurveyMethod(String surveyMethod) {
		this.surveyMethod = surveyMethod;
	}
	public String getSurveyField() {
		return surveyField;
	}
	public void setSurveyField(String surveyField) {
		this.surveyField = surveyField;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getEndinspection() {
		return endinspection;
	}
	public void setEndinspection(String endinspection) {
		this.endinspection = endinspection;
	}
	public String getDfs() {
		return dfs;
	}
	public void setDfs(String dfs) {
		this.dfs = dfs;
	}
}