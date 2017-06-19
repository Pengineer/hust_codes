package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 电子出版物成果类
 */
public class Electronic extends Product implements java.io.Serializable {

	private static final long serialVersionUID = 5275809584074150748L;
    private Integer isTranslation;//是否为翻译电子出版物
    private String publishUnit;//出版单位
    //@CheckSystemOptionStandard("productType")
    private SystemOption type;//电子出版物类型 
    private Date publishDate;//出版时间
    private String useUnit;//使用单位
    private Date useDate;//使用时间
    private String publishRegion;//出版地址
    private String isbn;//ISBN号
    
    static {
    	typeMap.put("electronic", "电子出版物");
    }
    
    /**
	 * 电子出版物构造器
	 * 鉴别器字段(productType='electronic')
	 */
    public Electronic() {
    	this.setProductType("electronic");
	}
    
    @JSON(serialize=false)
	public SystemOption getType() {
		return type;
	}
	public void setType(SystemOption type) {
		this.type = type;
	}
	public Integer getIsTranslation() {
		return isTranslation;
	}
	public void setIsTranslation(Integer isTranslation) {
		this.isTranslation = isTranslation;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishUnit() {
		return publishUnit;
	}
	public void setPublishUnit(String publishUnit) {
		this.publishUnit = publishUnit;
	}
	public String getUseUnit() {
		return useUnit;
	}
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getPublishRegion() {
		return publishRegion;
	}
	public void setPublishRegion(String publishRegion) {
		this.publishRegion = publishRegion;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}