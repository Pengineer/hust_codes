package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 其他成果类
 */
public class OtherProduct extends Product implements Serializable {

	private static final long serialVersionUID = 1L;
    private String supportProject;//支持课题
    private String evaluation;//评价
    private Date pressDate;//发表时间
    private String subtype;//其他成果子类型
    private String publishUnit;//出版单位
    
    static {
    	typeMap.put("otherProduct", "其他成果");
    }
    
    /**
	 * 其他成果构造器
	 * 鉴别器字段(productType='otherProduct')
	 */
    public OtherProduct() {
    	this.setProductType("otherProduct");
	}

	public String getSupportProject() {
		return supportProject;
	}

	public void setSupportProject(String supportProject) {
		this.supportProject = supportProject;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPressDate() {
		return pressDate;
	}

	public void setPressDate(Date pressDate) {
		this.pressDate = pressDate;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getPublishUnit() {
		return publishUnit;
	}

	public void setPublishUnit(String publishUnit) {
		this.publishUnit = publishUnit;
	}
   
}