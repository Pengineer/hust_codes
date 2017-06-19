package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 专利成果类
 */
public class Patent extends Product implements Serializable {
	
	private static final long serialVersionUID = -2900863322112745909L;
    private String applicationNumber;//申请号
    private Date applicationDate;//申请日
    private String publicNumber;//公开号
    private Date publicDate;//公开日
    private String classNumber;//分类号
    private String priorityNumber;//优先权号
    private String inventorName;//发明人姓名
    private String independentClaims;//独立权利要求
    private String categotyType;//范畴分类
    private String summary;//摘要
    private String countriesProvinceCode;//国省代码
    
    static {
    	typeMap.put("patent", "专利");
    }
    
    /**
	 * 专利构造器
	 * 鉴别器字段(productType='paper')
	 */
    public Patent() {
    	this.setProductType("patent");
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getPublicNumber() {
		return publicNumber;
	}

	public void setPublicNumber(String publicNumber) {
		this.publicNumber = publicNumber;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public String getPriorityNumber() {
		return priorityNumber;
	}

	public void setPriorityNumber(String priorityNumber) {
		this.priorityNumber = priorityNumber;
	}

	public String getInventorName() {
		return inventorName;
	}

	public void setInventorName(String inventorName) {
		this.inventorName = inventorName;
	}

	public String getIndependentClaims() {
		return independentClaims;
	}

	public void setIndependentClaims(String independentClaims) {
		this.independentClaims = independentClaims;
	}

	public String getCategotyType() {
		return categotyType;
	}

	public void setCategotyType(String categotyType) {
		this.categotyType = categotyType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCountriesProvinceCode() {
		return countriesProvinceCode;
	}

	public void setCountriesProvinceCode(String countriesProvinceCode) {
		this.countriesProvinceCode = countriesProvinceCode;
	}	
		
}