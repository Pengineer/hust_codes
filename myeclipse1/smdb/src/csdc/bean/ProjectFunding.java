package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


/**
 * @author 刘雅琴
 */
public abstract class ProjectFunding {

	public String id;//id
	private String projectType;//项目类型
	public Date date;//拨款时间
	public Double fee;//经费额
	public String attn;//经办人
	public String note;//备注
	private String grantedId;
	private FundingList fundingList;
	private String type;//granted：立项经费；mid：中检经费；end：结项经费；first：一期经费；second：二期经费
	private int status;//0未拨款，1已拨款
	private String payee;//收款人姓名或收款单位名称
	private Person person;//收款人
	private String agencyName;//收款机构名称
	private Agency agency;//收款机构
	private Institute institute;//收款研究机构
	private Department department;//收款院系
	private AgencyFunding agencyFunding;//机构经费
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getAttn() {
		return attn;
	}
	public void setAttn(String attn) {
		this.attn = attn;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getGrantedId() {
		return grantedId;
	}
	public void setGrantedId(String grantedId) {
		this.grantedId = grantedId;
	}
	public FundingList getFundingList() {
		return fundingList;
	}
	public void setFundingList(FundingList fundingList) {
		this.fundingList = fundingList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
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
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public AgencyFunding getAgencyFunding() {
		return agencyFunding;
	}
	public void setAgencyFunding(AgencyFunding agencyFunding) {
		this.agencyFunding = agencyFunding;
	}
	
}
