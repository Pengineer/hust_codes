package csdc.bean;

import java.util.Date;


/**
 * 发展报告项目
 * @author maowh
 *
 */
public class DevelopmentReport {
	
	private String id;
	private String name;//项目名称
	private String applicant;//负责人
	private Integer year;//立项年度
	private String number;//项目编号
	private String type;//项目类型
	private String topic;//模块
	private String university;//学校名称
	private String universityId;//学校id
	private String guide;//对应指南
	private int isDupCheckGeneral;//是否进行一般项目重项检查：1是，0否 
	private Date importDate;//导入时间
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date date) {
		this.importDate = date;
	}
	public int getIsDupCheckGeneral() {
		return isDupCheckGeneral;
	}
	public void setIsDupCheckGeneral(int isDupCheckGeneral) {
		this.isDupCheckGeneral = isDupCheckGeneral;
	}	
	
}
