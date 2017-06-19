package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Abroad implements java.io.Serializable {

	private static final long serialVersionUID = 5677443935062851128L;
	private String id;// ID
	private Person person;//
	private String countryRegion;// 国家或地区
	private String workUnit;// 单位
	private String purpose;// 目的
	private Date startDate;// 开始时间
	private Date endDate;// 结束时间

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
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
