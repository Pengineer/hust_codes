package csdc.bean;

import java.util.Date;
import java.util.Set;

public class Asset {
	
	private String id;	
	private String name;	//名称
	private String type;	//类型
	private Date datetime = new Date();	//购置日期
	private String spec;	//详细配置
	private int status;	//状态
	private Double price;	//价格
	private Account rsper;	//责任人id
	private Account pcher;	//购置人id
	private String assetNumber;	//资产编号
	private Date begtime;	//责任开始时间
	private String usage;	//使用用途
	
	private Set<AssetVariation> assetvariations;
	

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getBegtime() {
		return begtime;
	}
	public void setBegtime(Date begtime) {
		this.begtime = begtime;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Account getRsper() {
		return rsper;
	}
	public void setRsper(Account rsper) {
		this.rsper = rsper;
	}
	public Account getPcher() {
		return pcher;
	}
	public void setPcher(Account pcher) {
		this.pcher = pcher;
	}
	public void setAssetvariations(Set<AssetVariation> assetvariations) {
		this.assetvariations = assetvariations;
	}
	public Set<AssetVariation> getAssetvariations() {
		return assetvariations;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getUsage() {
		return usage;
	}
}