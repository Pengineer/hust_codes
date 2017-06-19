package csdc.bean;

import java.util.Date;

public class PopularBook implements java.io.Serializable {

	private static final long serialVersionUID = -4693904152228316652L;
	
	private String id;
	private String name;//项目名称
	private String applicant;//负责人
	private Integer year;//立项年度
	private String number;//项目编号
	private String university;//学校名称
	private String universityId;//学校id
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
	public int getIsDupCheckGeneral() {
		return isDupCheckGeneral;
	}
	public void setIsDupCheckGeneral(int isDupCheckGeneral) {
		this.isDupCheckGeneral = isDupCheckGeneral;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

}
