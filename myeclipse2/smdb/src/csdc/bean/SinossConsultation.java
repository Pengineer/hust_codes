package csdc.bean;

import java.util.Date;

public class SinossConsultation {

	private String id;//研究报告id
	private String projectName;//项目名字
	private String name;//研究报告名称
	private String firstAuthor;//第一作者
	private String otherAuthor;//其他作者
	private String subject;//一级学科编号
	private String disciplineType;//一级学科名称
	private String isMark;//是否标注教育部人文社科项目资助
	private String commitUnit;//提交单位
	private Date commitDate;//提交时间
	private String isAccept;//是否被采纳
	private String acceptObj;//采纳对象
	private String note;//备注
	private String projectId;//项目id
	private SinossProjectMidinspection projectMidinspection;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstAuthor() {
		return firstAuthor;
	}
	public void setFirstAuthor(String firstAuthor) {
		this.firstAuthor = firstAuthor;
	}
	public String getOtherAuthor() {
		return otherAuthor;
	}
	public void setOtherAuthor(String otherAuthor) {
		this.otherAuthor = otherAuthor;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getIsMark() {
		return isMark;
	}
	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}
	public String getCommitUnit() {
		return commitUnit;
	}
	public void setCommitUnit(String commitUnit) {
		this.commitUnit = commitUnit;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public String getAcceptObj() {
		return acceptObj;
	}
	public void setAcceptObj(String acceptObj) {
		this.acceptObj = acceptObj;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public SinossProjectMidinspection getProjectMidinspection() {
		return projectMidinspection;
	}
	public void setProjectMidinspection(SinossProjectMidinspection projectMidinspection) {
		this.projectMidinspection = projectMidinspection;
	}

	
}
