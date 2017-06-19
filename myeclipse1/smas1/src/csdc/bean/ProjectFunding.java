package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;//主键id
	public Date date;//拨款时间
	public Double fee;//经费额
	public String attn;//经办人
	public String note;//备注
	private String agencyName;//收款机构名称
	private String fbankAccount;// 银行账号
	private String fbankAccountName;// 开户名称
	private String fbank;// 开户银行
	private String fcupNumber;// 银联行号
	private String fbankBranch;// 银行支行
	private int fundingType;//0默认，1立项，2中检，3结项
	private int status;//0未拨款，1已拨款
	private String type;//项目类型
	
	private ProjectFundingList projectFundingList;
	private ProjectGranted granted;//项目立项

		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getFbankAccount() {
		return fbankAccount;
	}
	public void setFbankAccount(String fbankAccount) {
		this.fbankAccount = fbankAccount;
	}
	public String getFbankAccountName() {
		return fbankAccountName;
	}
	public void setFbankAccountName(String fbankAccountName) {
		this.fbankAccountName = fbankAccountName;
	}
	public String getFbank() {
		return fbank;
	}
	public void setFbank(String fbank) {
		this.fbank = fbank;
	}
	public String getFcupNumber() {
		return fcupNumber;
	}
	public void setFcupNumber(String fcupNumber) {
		this.fcupNumber = fcupNumber;
	}
	public String getFbankBranch() {
		return fbankBranch;
	}
	public void setFbankBranch(String fbankBranch) {
		this.fbankBranch = fbankBranch;
	}
	public int getFundingType() {
		return fundingType;
	}
	public void setFundingType(int fundingType) {
		this.fundingType = fundingType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JSON(serialize=false)
	public ProjectFundingList getProjectFundingList() {
		return projectFundingList;
	}
	public void setProjectFundingList(ProjectFundingList projectFundingList) {
		this.projectFundingList = projectFundingList;
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public ProjectGranted getGranted() {
		return granted;
	}
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = granted;
	}
}
