package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import csdc.bean.validation.CheckSystemOptionStandard;

public class Academic implements java.io.Serializable {
	private static final long serialVersionUID = -3005862700795767205L;
	private String id;
	private Person person;
	private String ethnicLanguage;// 民族语言
	private String language;// 外语语种
	private String disciplineType;// 学科门类
	private String discipline;// 学科
	private String relativeDiscipline;// 相关学科
	private String researchField;// 研究领域
	private String major;// 专业
	private String researchSpeciality;// 学术特长
	private String furtherEducation;// 进修情况
	private String parttimeJob;		//学术兼职
	private String specialityTitle;// 专业职称
	private String positionLevel;// 岗位等级
	private String tutorType;// 导师类型
	private Integer postdoctor;// 是否博士后（0否、1在站、2出站）

	private int isReviewer;// 是否参评专家
	private String reviewLevel;//参评优先级
	private Double reviewScore;//参评评分
	private String talent;// 人才类型
	private String lastEducation;// 最后学历 （大专本科研究生）
	private String lastDegree;// 最后学位 （学士硕士博士）
	//@CheckSystemOptionStandard("expertType")
	private SystemOption expertType; //专家类别（社科委专家等）
	private String countryRegion;// 学位授予国家或地区
	private Date degreeDate;// 学位授予时间
	//@CheckSystemOptionStandard("computerLevel")
	private SystemOption computerLevel; //计算机操作水平 
	public String getId() {
		return id;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getDegreeDate() {
		return degreeDate;
	}
	public void setDegreeDate(Date degreeDate) {
		this.degreeDate = degreeDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEthnicLanguage() {
		return ethnicLanguage;
	}
	public void setEthnicLanguage(String ethnicLanguage) {
		this.ethnicLanguage = ethnicLanguage;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDisciplineType() {
		return disciplineType;
	}
	public void setDisciplineType(String disciplineType) {
		this.disciplineType = disciplineType;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getRelativeDiscipline() {
		return relativeDiscipline;
	}
	public void setRelativeDiscipline(String relativeDiscipline) {
		this.relativeDiscipline = relativeDiscipline;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getResearchSpeciality() {
		return researchSpeciality;
	}
	public void setResearchSpeciality(String researchSpeciality) {
		this.researchSpeciality = researchSpeciality;
	}
	public String getFurtherEducation() {
		return furtherEducation;
	}
	public void setFurtherEducation(String furtherEducation) {
		this.furtherEducation = furtherEducation;
	}
	public String getParttimeJob() {
		return parttimeJob;
	}
	public void setParttimeJob(String parttimeJob) {
		this.parttimeJob = parttimeJob;
	}
	public String getSpecialityTitle() {
		return specialityTitle;
	}
	public void setSpecialityTitle(String specialityTitle) {
		this.specialityTitle = specialityTitle;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getTutorType() {
		return tutorType;
	}
	public void setTutorType(String tutorType) {
		this.tutorType = tutorType;
	}
//	public int getPostdoctor() {
//		return postdoctor;
//	}
//	public void setPostdoctor(int postdoctor) {
//		this.postdoctor = postdoctor;
//	}
//	public void setPostdoctor(String postdoctor) {
//		this.postdoctor = Integer.parseInt(postdoctor);
//	}
	
	public int getIsReviewer() {
		return isReviewer;
	}
	public String getReviewLevel() {
		return reviewLevel;
	}
	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}
	public Double getReviewScore() {
		return reviewScore;
	}
	public void setReviewScore(Double reviewScore) {
		this.reviewScore = reviewScore;
	}
	public Integer getPostdoctor() {
		return postdoctor;
	}
	public void setPostdoctor(Integer postdoctor) {
		this.postdoctor = postdoctor;
	}
	
	public void setIsReviewer(int isReviewer) {
		this.isReviewer = isReviewer;
	}
	public void setIsReviewer(String isReviewer) {
		this.isReviewer = Integer.parseInt(isReviewer);
	}
	public String getTalent() {
		return talent;
	}
	public void setTalent(String talent) {
		this.talent = talent;
	}
	public String getLastEducation() {
		return lastEducation;
	}
	public void setLastEducation(String lastEducation) {
		this.lastEducation = lastEducation;
	}
	public String getLastDegree() {
		return lastDegree;
	}
	public void setLastDegree(String lastDegree) {
		this.lastDegree = lastDegree;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@JSON(serialize=false)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	@JSON(serialize=false)
	public SystemOption getExpertType() {
		return expertType;
	}
	public void setExpertType(SystemOption expertType) {
		this.expertType = expertType;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	@JSON(serialize=false)
	public SystemOption getComputerLevel() {
		return computerLevel;
	}
	public void setComputerLevel(SystemOption computerLevel) {
		this.computerLevel = computerLevel;
	}

}
