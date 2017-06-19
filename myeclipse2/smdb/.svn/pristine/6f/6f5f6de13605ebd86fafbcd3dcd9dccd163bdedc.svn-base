package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 论文成果类
 */
public class Paper extends Product implements Serializable {

	private static final long serialVersionUID = -7699249996513144195L;
	//@CheckSystemOptionStandard("productType")
	private SystemOption type;// 论文类型
	private Integer isTranslation;// 是否为译文
	private Double wordNumber;// 字数（千）
	private String publication;// 发表刊物/会议名称
	//@CheckSystemOptionStandard("publicationLevel")
	private String volume;// 卷号
	private String number;// 期号
	private String page;// 起止页码
	//@CheckSystemOptionStandard("publicationScope")
	private SystemOption publicationScope;// 发表范围
	private String issn;// ISSN号
	private String cn;// CN号
	private String index;// 被引情况,索引类型与索引次数用”/”隔开，多个用英文分号与空格隔开，如：SSCI/3; CSSCI/2
	private Date publicationDate;// 发表时间/会议时间

	static {

		typeMap.put("paper", "论文");
	}

	/**
	 * 论文成果构造器 鉴别器字段(productType='paper')
	 */
	public Paper() {
		this.setProductType("paper");
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@JSON(serialize = false)
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

	public Double getWordNumber() {
		return wordNumber;
	}

	public void setWordNumber(Double wordNumber) {
		this.wordNumber = wordNumber;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@JSON(serialize = false)
	public SystemOption getPublicationScope() {
		return publicationScope;
	}

	public void setPublicationScope(SystemOption publicationScope) {
		this.publicationScope = publicationScope;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
}