package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 著作成果类
 */
public class Book extends Product implements Serializable {
	
	private static final long serialVersionUID = -7699249996513134195L;
    private Integer isTranslation;//是否为译文
    private double wordNumber;//字数
    private String publishUnit;//出版单位
    private String publishRegion;//出版地区
    //@CheckSystemOptionStandard("productType")
    private SystemOption type;//著作类型  
    private Date publishDate;//出版时间
    private int publicationStatus;//是否已提交待出版的样书（或已出版的书籍）：1是，0否
    private Integer isEnglish;//是否译成英文：1是，0否
    private String originalLanguage;//原著语言
    private String isbn;//isbn号
    private String cip;//cip号
    
    
    static {
    	typeMap.put("book", "著作");
    }
    
    /**
	 * 著作成果构造器
	 * 鉴别器字段(productType='book')
	 */
    public Book() {
		this.setProductType("book");
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
	public double getWordNumber() {
		return wordNumber;
	}
	public void setWordNumber(double wordNumber) {
		this.wordNumber = wordNumber;
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
	public int getPublicationStatus() {
		return publicationStatus;
	}
	public void setPublicationStatus(int publicationStatus) {
		this.publicationStatus = publicationStatus;
	}
	public String getOriginalLanguage() {
		return originalLanguage;
	}
	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}
	public String getPublishRegion() {
		return publishRegion;
	}
	public void setPublishRegion(String publishRegion) {
		this.publishRegion = publishRegion;
	}

	public Integer getIsEnglish() {
		return isEnglish;
	}

	public void setIsEnglish(Integer isEnglish) {
		this.isEnglish = isEnglish;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}
	
}