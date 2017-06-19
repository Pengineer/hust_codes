package csdc.bean;

import java.util.Date;


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
	private FundList fundList;
	private int type;//0默认，1立项，2中检，3结项
	private int status;//0未拨款，1已拨款
	private String fbankAccount;// 银行账号
	private String fcupNumber;// 银联行号
	private String fbank;// 开户银行
	private String fbankBranch;// 银行支行
	private String fbankAccountName;// 开户名称
	private String agencyName;//收款机构名称
	private Agency agency;//收款机构
	
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
	public FundList getFundList() {
		return fundList;
	}
	public void setFundList(FundList fundList) {
		this.fundList = fundList;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFbankAccount() {
		return fbankAccount;
	}
	public void setFbankAccount(String fbankAccount) {
		this.fbankAccount = fbankAccount;
	}
	public String getFcupNumber() {
		return fcupNumber;
	}
	public void setFcupNumber(String fcupNumber) {
		this.fcupNumber = fcupNumber;
	}
	public String getFbank() {
		return fbank;
	}
	public void setFbank(String fbank) {
		this.fbank = fbank;
	}
	public String getFbankBranch() {
		return fbankBranch;
	}
	public void setFbankBranch(String fbankBranch) {
		this.fbankBranch = fbankBranch;
	}
	public String getFbankAccountName() {
		return fbankAccountName;
	}
	public void setFbankAccountName(String fbankAccountName) {
		this.fbankAccountName = fbankAccountName;
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
}
