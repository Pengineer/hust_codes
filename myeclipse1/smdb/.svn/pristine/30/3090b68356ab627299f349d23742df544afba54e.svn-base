package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class FundingList implements Serializable{
	private static final long serialVersionUID = -4905445252277046314L;
	public String id;//id
	private String name;//清单名称
	private String attn;//经办人
	private Date createDate;//生成时间
	private int status;//清单状态 【0:未审核，1:已审核】
	private String note;//清单备注
	private String type;//清单类型：1工作经费WorkFunding 2项目经费ProjectFunding
	private String subType;//清单类型子类[common：日常经费；review：评审经费；award：奖励经费；publish：出版经费；communication：交流经费；training：培训经费；other：其它经费；general：一般项目；instp：基地项目；post：后期资助项目；special:xxxx：专项任务项目（具体专项名称）；devrpt：发展报告项目；key：重大攻关项目]
	private String subSubType;//2.1.1立项2.1.2中检2.1.3结项
	private Double rate;//拨款比率
	private Integer year;//拨款年度
	private FundingBatch fundingBatch;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubSubType() {
		return subSubType;
	}
	public void setSubSubType(String subSubType) {
		this.subSubType = subSubType;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	@JSON(serialize=false)
	public FundingBatch getFundingBatch() {
		return fundingBatch;
	}
	public void setFundingBatch(FundingBatch fundingBatch) {
		this.fundingBatch = fundingBatch;
	}

}
