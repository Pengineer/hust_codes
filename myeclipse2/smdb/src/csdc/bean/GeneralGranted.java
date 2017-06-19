package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 刘雅琴
 */
public class GeneralGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private GeneralApplication application;// 项目申请

	private Set<GeneralAnninspection> generalAnninspection;//项目年检
	private Set<GeneralMidinspection> generalMidinspection;//项目中检
	private Set<GeneralEndinspection> generalEndinspection;//项目结项
	private Set<GeneralVariation> generalVariation;// 项目变更
	private Set<GeneralFunding> generalFunding;// 项目拨款
	
	/**
	 * 一般项目立项构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralGranted() {
		this.setProjectType("general");
	}
	
	static {
    	typeMap.put("general", "教育部一般项目");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<GeneralAnninspection> getAnninspection() {
		return generalAnninspection;
	}
	public void setAnninspection(Set<GeneralAnninspection> anninspection) {
		this.generalAnninspection = anninspection;
	}
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public Set<GeneralMidinspection> getMidinspection() {
		return generalMidinspection;
	}
	public void setMidinspection(Set<GeneralMidinspection> midinspection) {
		this.generalMidinspection = midinspection;
	}
	
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public Set<GeneralEndinspection> getEndinspection() {
		return generalEndinspection;
	}
	public void setEndinspection(Set<GeneralEndinspection> endinspection) {
		this.generalEndinspection = endinspection;
	}
	
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getAnninspection() == null) {
			this.setAnninspection((new HashSet<GeneralAnninspection>()));
		}
		this.getAnninspection().add((GeneralAnninspection) anninspection);
		((GeneralAnninspection) anninspection).setGranted(this);
	}
	
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getMidinspection() == null) {
			this.setMidinspection((new HashSet<GeneralMidinspection>()));
		}
		this.getMidinspection().add((GeneralMidinspection) midinspection);
		((GeneralMidinspection) midinspection).setGranted(this);
	}
	
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setEndinspection(new HashSet<GeneralEndinspection>());
		}
		this.getEndinspection().add((GeneralEndinspection) endinspection);
		((GeneralEndinspection) endinspection).setGranted(this);
	}

	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getGeneralVariation() == null) {
			this.setGeneralVariation(new HashSet<GeneralVariation>());
		}
		this.getGeneralVariation().add((GeneralVariation)variation);
		((GeneralVariation)variation).setGranted(this);
	}
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setGeneralFunding(new HashSet<GeneralFunding>());
		}
		this.getGeneralFunding().add((GeneralFunding)funding);
		((GeneralFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "GeneralApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "GeneralGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "GeneralAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "GeneralMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "GeneralEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "GeneralEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "GeneralVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "GeneralFunding";
	}
	
	@JSON(serialize = false)
	public GeneralApplication getApplication() {
		return application;
	}
	public void setApplication(GeneralApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<GeneralAnninspection> getGeneralAnninspection() {
		return generalAnninspection;
	}
	public void setGeneralAnninspection(
			Set<GeneralAnninspection> generalAnninspection) {
		this.generalAnninspection = generalAnninspection;
	}
	@JSON(serialize = false)
	public Set<GeneralMidinspection> getGeneralMidinspection() {
		return generalMidinspection;
	}
	public void setGeneralMidinspection(
			Set<GeneralMidinspection> generalMidinspection) {
		this.generalMidinspection = generalMidinspection;
	}
	@JSON(serialize = false)
	public Set<GeneralEndinspection> getGeneralEndinspection() {
		return generalEndinspection;
	}
	public void setGeneralEndinspection(
			Set<GeneralEndinspection> generalEndinspection) {
		this.generalEndinspection = generalEndinspection;
	}
	@JSON(serialize = false)
	public Set<GeneralVariation> getGeneralVariation() {
		return generalVariation;
	}
	public void setGeneralVariation(Set<GeneralVariation> generalVariation) {
		this.generalVariation = generalVariation;
	}
	@JSON(serialize = false)
	public Set<GeneralFunding> getGeneralFunding() {
		return generalFunding;
	}
	public void setGeneralFunding(Set<GeneralFunding> generalFunding) {
		this.generalFunding = generalFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getGeneralVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getGeneralFunding();
	}

}