package csdc.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 研究报告成果类
 */
public class Consultation extends Product implements Serializable {
	
	private static final long serialVersionUID = -7699249996513134135L;
    private Integer isTranslation;//是否为译文
    private double wordNumber;//字数
    private String adoptType;//采纳类型
    private String useUnit;//使用单位
    private String supportProject;//支持课题
    private String evaluation;//评价
    private Date publicationDate;//发表时间
    private Integer isSecret;//是否涉密
    private Integer isAccepted;//是否被采纳
    
    static {
    	typeMap.put("consultation", "研究咨询报告");
    }
    
    /**
	 * 研究报告成果构造器
	 * 鉴别器字段(productType='consultation')
	 */
    public Consultation() {
		this.setProductType("consultation");
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
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getUseUnit() {
		return useUnit;
	}
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getAdoptType() {
		return adoptType;
	}
	public void setAdoptType(String adoptType) {
		this.adoptType = adoptType;
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
	public Integer getIsSecret() {
		return isSecret;
	}
	public void setIsSecret(Integer isSecret) {
		this.isSecret = isSecret;
	}

	public Integer getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Integer isAccepted) {
		this.isAccepted = isAccepted;
	}

}