package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class EntrustGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private EntrustApplication application;// 项目申请

	private Set<EntrustEndinspection> entrustEndinspection;//项目结项
	private Set<EntrustVariation> entrustVariation;// 项目变更
	private Set<EntrustFunding> entrustFunding;// 项目拨款
	
	/**
	 * 委托（应急）课题立项构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustGranted() {
		this.setProjectType("entrust");
	}
	
	static {
    	typeMap.put("entrust", "教育部委托（应急）课题");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<? extends ProjectAnninspection> getAnninspection() {
		throw new RuntimeException("未实现的方法！");
	}
	public void setAnninspection(Set<ProjectAnninspection> anninspection) {
		throw new RuntimeException("未实现的方法！");
	}
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public Set<? extends ProjectMidinspection> getMidinspection() {
		throw new RuntimeException("未实现的方法！");
	}
	public void setMidinspection(Set<ProjectMidinspection> midinspection) {
		throw new RuntimeException("未实现的方法！");
	}

	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		throw new RuntimeException("未实现的方法！");
	}
	
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection endinspection) {
		throw new RuntimeException("未实现的方法！");
	}
	
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setEntrustEndinspection(new HashSet<EntrustEndinspection>());
		}
		this.getEntrustEndinspection().add((EntrustEndinspection) endinspection);
		((EntrustEndinspection) endinspection).setGranted(this);
	}

	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getVariation() == null) {
			this.setEntrustVariation(new HashSet<EntrustVariation>());
		}
		this.getEntrustVariation().add((EntrustVariation)variation);
		((EntrustVariation)variation).setGranted(this);
	}
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setEntrustFunding(new HashSet<EntrustFunding>());
		}
		this.getEntrustFunding().add((EntrustFunding)funding);
		((EntrustFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "EntrustApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "EntrustGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "EntrustAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "EntrustMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "EntrustEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "EntrustEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "EntrustVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "EntrustFunding";
	}
	
	@JSON(serialize = false)
	public EntrustApplication getApplication() {
		return application;
	}
	public void setApplication(EntrustApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<EntrustVariation> getEntrustVariation() {
		return entrustVariation;
	}
	public void setEntrustVariation(Set<EntrustVariation> entrustVariation) {
		this.entrustVariation = entrustVariation;
	}
	@JSON(serialize = false)
	public Set<EntrustFunding> getEntrustFunding() {
		return entrustFunding;
	}
	public void setEntrustFunding(Set<EntrustFunding> entrustFunding) {
		this.entrustFunding = entrustFunding;
	}
	@JSON(serialize = false)
	public Set<EntrustEndinspection> getEntrustEndinspection() {
		return entrustEndinspection;
	}
	public void setEntrustEndinspection(
			Set<EntrustEndinspection> entrustEndinspection) {
		this.entrustEndinspection = entrustEndinspection;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspection> getEndinspection() {
		return getEntrustEndinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getEntrustVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getEntrustFunding();
	}

}