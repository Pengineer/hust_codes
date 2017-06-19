package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class SubInstitute implements java.io.Serializable {

	private static final long serialVersionUID = -4028106200142865803L;
	private String id;// ID
	private String name;// 名称
	private Institute institute;// 所属研究机构
	private String researchField;// 研究领域
	private Person director;// 负责人

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
	@JSON(serialize=false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public String getResearchField() {
		return researchField;
	}
	public void setResearchField(String researchField) {
		this.researchField = researchField;
	}
	@JSON(serialize=false)
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}