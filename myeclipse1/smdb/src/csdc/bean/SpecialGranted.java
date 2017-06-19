package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 
 */
public class SpecialGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private SpecialApplication application;// 项目申请

	private Set<SpecialAnninspection> specialAnninspection;//项目年检
	private Set<SpecialMidinspection> specialMidinspection;//项目中检
	private Set<SpecialEndinspection> specialEndinspection;//项目结项
	private Set<SpecialVariation> specialVariation;// 项目变更
	private Set<SpecialFunding> specialFunding;// 项目拨款
	
	/**
	 * 一般项目立项构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialGranted() {
		this.setProjectType("special");
	}
	
	static {
    	typeMap.put("special", "教育部一般项目");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<SpecialAnninspection> getAnninspection() {
		return specialAnninspection;
	}
	public void setAnninspection(Set<SpecialAnninspection> anninspection) {
		this.specialAnninspection = anninspection;
	}
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public Set<SpecialMidinspection> getMidinspection() {
		return specialMidinspection;
	}
	public void setMidinspection(Set<SpecialMidinspection> midinspection) {
		this.specialMidinspection = midinspection;
	}
	
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public Set<SpecialEndinspection> getEndinspection() {
		return specialEndinspection;
	}
	public void setEndinspection(Set<SpecialEndinspection> endinspection) {
		this.specialEndinspection = endinspection;
	}
	
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getAnninspection() == null) {
			this.setAnninspection((new HashSet<SpecialAnninspection>()));
		}
		this.getAnninspection().add((SpecialAnninspection) anninspection);
		((SpecialAnninspection) anninspection).setGranted(this);
	}
	
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getMidinspection() == null) {
			this.setMidinspection((new HashSet<SpecialMidinspection>()));
		}
		this.getMidinspection().add((SpecialMidinspection) midinspection);
		((SpecialMidinspection) midinspection).setGranted(this);
	}
	
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setEndinspection(new HashSet<SpecialEndinspection>());
		}
		this.getEndinspection().add((SpecialEndinspection) endinspection);
		((SpecialEndinspection) endinspection).setGranted(this);
	}

	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getSpecialVariation() == null) {
			this.setSpecialVariation(new HashSet<SpecialVariation>());
		}
		this.getSpecialVariation().add((SpecialVariation)variation);
		((SpecialVariation)variation).setGranted(this);
	}
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setSpecialFunding(new HashSet<SpecialFunding>());
		}
		this.getSpecialFunding().add((SpecialFunding)funding);
		((SpecialFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "SpecialApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "SpecialGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "SpecialAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "SpecialMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "SpecialEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "SpecialEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "SpecialVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "SpecialFunding";
	}
	
	@JSON(serialize = false)
	public SpecialApplication getApplication() {
		return application;
	}
	public void setApplication(SpecialApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<SpecialAnninspection> getSpecialAnninspection() {
		return specialAnninspection;
	}
	public void setSpecialAnninspection(
			Set<SpecialAnninspection> specialAnninspection) {
		this.specialAnninspection = specialAnninspection;
	}
	@JSON(serialize = false)
	public Set<SpecialMidinspection> getSpecialMidinspection() {
		return specialMidinspection;
	}
	public void setSpecialMidinspection(
			Set<SpecialMidinspection> specialMidinspection) {
		this.specialMidinspection = specialMidinspection;
	}
	@JSON(serialize = false)
	public Set<SpecialEndinspection> getSpecialEndinspection() {
		return specialEndinspection;
	}
	public void setSpecialEndinspection(
			Set<SpecialEndinspection> specialEndinspection) {
		this.specialEndinspection = specialEndinspection;
	}
	@JSON(serialize = false)
	public Set<SpecialVariation> getSpecialVariation() {
		return specialVariation;
	}
	public void setSpecialVariation(Set<SpecialVariation> specialVariation) {
		this.specialVariation = specialVariation;
	}
	@JSON(serialize = false)
	public Set<SpecialFunding> getSpecialFunding() {
		return specialFunding;
	}
	public void setSpecialFunding(Set<SpecialFunding> specialFunding) {
		this.specialFunding = specialFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getSpecialVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getSpecialFunding();
	}

}