package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class Expert implements java.io.Serializable {

	private static final long serialVersionUID = -6875308138758358180L;
	private String id;// ID
	private Person person;// 人员主表
	private String agencyName;// 所在单位名称
	private String divisionName;// 所在部门名称
	private String position;// 职务
	private String type;// 人员类型

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}