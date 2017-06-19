package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * @author 王燕
 */
public class InstituteFunding implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;// ID
	private Institute institute;//基地id
	private Double projectFee;//研究项目费
	private Double dataFee;//资料费
	private Double conferenceFee;//会议费
	private Double journalFee;//期刊费
	private Double netFee;//网络费
	private Double databaseFee;//数据库费
	private Double awardFee;//奖励费费
	private Double fee;//合计
	public Date date;//拨款时间
	public String attn;//经办人
	public String note;//备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize = false)
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public Double getProjectFee() {
		return projectFee;
	}
	public void setProjectFee(Double projectFee) {
		this.projectFee = projectFee;
	}
	public Double getDataFee() {
		return dataFee;
	}
	public void setDataFee(Double dataFee) {
		this.dataFee = dataFee;
	}
	public Double getConferenceFee() {
		return conferenceFee;
	}
	public void setConferenceFee(Double conferenceFee) {
		this.conferenceFee = conferenceFee;
	}
	public Double getJournalFee() {
		return journalFee;
	}
	public void setJournalFee(Double journalFee) {
		this.journalFee = journalFee;
	}
	public Double getDatabaseFee() {
		return databaseFee;
	}
	public void setDatabaseFee(Double databaseFee) {
		this.databaseFee = databaseFee;
	}
	public Double getAwardFee() {
		return awardFee;
	}
	public void setAwardFee(Double awardFee) {
		this.awardFee = awardFee;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public Double getNetFee() {
		return netFee;
	}
	public void setNetFee(Double netFee) {
		this.netFee = netFee;
	}

}