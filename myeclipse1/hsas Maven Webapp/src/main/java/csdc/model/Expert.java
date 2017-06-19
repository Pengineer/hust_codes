package csdc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_EXPERT" )
public class Expert implements Serializable {
	private static final long serialVersionUID = 7151871470737916338L;

	@Id	
	@Column(name="C_ID", length=40)  
	@GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String id; //评审专家id（PK）
	
	@OneToOne
	@JoinColumn(name="C_PERSON_ID")
	private Person person; //人员id（FK）
	
	@Column(name="C_SPECIALITY_TITLE", length=200)  
    private String specialityTitle; //专业职称
	
	@Column(name="C_RESEARCH_FIELD", length=400)  
    private String researchField; //学术研究方向
	
	@ManyToOne
	@JoinColumn(name="C_DISCIPLINE_ID")
	private SystemOption disciplineId; //人员id（FK）
	
	@Column(name="C_DEGREE", length=40)  
    private String degree; //最后学位
	
	@Column(name="C_LANGUAGE", length=400)  
    private String language; //外语语种
	
	@Column(name="C_IS_DOCTOR_TUTOR")  
    private Integer isDoctorTutor; //是否博导[1：是；0：否]
	
	@Column(name="C_REVIEWER_YEAR", length=200)  
    private String reviewerYear; //参评年份
	
	@Column(name="C_TYPE", length=40)  
    private String type; //人员类型[专职人员、兼职人员]

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getSpecialityTitle() {
		return specialityTitle;
	}

	public void setSpecialityTitle(String specialityTitle) {
		this.specialityTitle = specialityTitle;
	}

	public String getResearchField() {
		return researchField;
	}

	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}

	public SystemOption getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(SystemOption disciplineId) {
		this.disciplineId = disciplineId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getIsDoctorTutor() {
		return isDoctorTutor;
	}

	public void setIsDoctorTutor(Integer isDoctorTutor) {
		this.isDoctorTutor = isDoctorTutor;
	}

	public String getReviewerYear() {
		return reviewerYear;
	}

	public void setReviewerYear(String reviewerYear) {
		this.reviewerYear = reviewerYear;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
