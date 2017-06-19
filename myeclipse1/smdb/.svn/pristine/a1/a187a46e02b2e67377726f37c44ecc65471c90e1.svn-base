package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class BankAccount implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;            //银行账号id
	private String ids;           //银行账号组id（可重复）
	private Integer sn;           //银行账号序号
	private String bankName;      //开户银行（包含支行的完整名称）
	private String bankCupNumber; //银联行号
	private String accountNumber; //银行账号
	private String accountName;   //银行户名
	private Integer isDefault;    //是否默认[1：是；0：否]
	private Integer createMode;   //数据创建模式[0：系统流程创建；1：系统录入创建；2：外部导入创建]
	private Date createDate;      //数据创建时间
	private Date updateDate;      //数据更新时间
	private SystemOption province;//所在省
	private SystemOption city;    //所在市
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCupNumber() {
		return bankCupNumber;
	}
	public void setBankCupNumber(String bankCupNumber) {
		this.bankCupNumber = bankCupNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getCreateMode() {
		return createMode;
	}
	public void setCreateMode(Integer createMode) {
		this.createMode = createMode;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public SystemOption getProvince() {
		return province;
	}
	public void setProvince(SystemOption province) {
		this.province = province;
	}
	public SystemOption getCity() {
		return city;
	}
	public void setCity(SystemOption city) {
		this.city = city;
	}
	
}
