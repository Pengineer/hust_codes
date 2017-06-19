package csdc.bean;


/**
 * 变更内容（与社科网对接接口类）
 *
 */
public class SinossModifyContent {
	
	private String id;//主键id
	private String modifyFieldMean;//变更业务属性
	private String beforeValue;//变更前内容
	private String afterValue;//变更后
	private String idNumber;//变更后负责人身份证号
	private SinossProjectVariation projectVariation;
	private String sinossId;//管理平台变更内容id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModifyFieldMean() {
		return modifyFieldMean;
	}
	public void setModifyFieldMean(String modifyFieldMean) {
		this.modifyFieldMean = modifyFieldMean;
	}
	public String getBeforeValue() {
		return beforeValue;
	}
	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}
	public String getAfterValue() {
		return afterValue;
	}
	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public SinossProjectVariation getProjectVariation() {
		return projectVariation;
	}
	public void setProjectVariation(SinossProjectVariation projectVariation) {
		this.projectVariation = projectVariation;
	}
	public String getSinossId() {
		return sinossId;
	}
	public void setSinossId(String sinossId) {
		this.sinossId = sinossId;
	}
	
}
