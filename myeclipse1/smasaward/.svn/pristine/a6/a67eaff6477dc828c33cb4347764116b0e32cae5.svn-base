package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectFundingList implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public String id;//id
	private String name;//清单名称
	private String attn;//经办人
	private Date createDate;//生成时间
	private int status;//清单状态 【0:未审核，1:已审核】
	private String note;//清单备注
	private String fundType;//拨款类型【granted、mid、end】
	private Double rate;//拨款比率
	private Double total;//清单总金额
	private int projectNumber;//项目总数
	private int year;//拨款年度
	private String type;//项目类型
	
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
	public String getAttn() {
		return attn;
	}
	public void setAttn(String attn) {
		this.attn = attn;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getRate() {
		return rate;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTotal() {
		return total;
	}
	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}
	public int getProjectNumber() {
		return projectNumber;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getYear() {
		return year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}