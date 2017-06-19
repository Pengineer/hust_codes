package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class InstpGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private InstpApplication application;//项目申请

	private Set<InstpAnninspection> instpAnninspection;//项目年检
	private Set<InstpVariation> instpVariation;//项目变更
	private Set<InstpMidinspection> instpMidinspection;//项目中检
	private Set<InstpEndinspection> instpEndinspection;//项目结项
	private Set<InstpFunding> instpFunding;//项目拨款

	/**
	 * 基地项目立项构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpGranted() {
		this.setProjectType("instp");
	}
	
	static {
    	typeMap.put("instp", "教育部基地项目");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<InstpAnninspection> getInstpAnninspection() {
		return instpAnninspection;
	}
	public void setInstpAnninspection(Set<InstpAnninspection> instpAnninspection) {
		this.instpAnninspection = instpAnninspection;
	}
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize=false)
	public Set<InstpMidinspection> getInstpMidinspection() {
		return instpMidinspection;
	}
	public void setInstpMidinspection(Set<InstpMidinspection> instpMidinspection) {
		this.instpMidinspection = instpMidinspection;
	}
	
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize=false)
	public Set<InstpEndinspection> getInstpEndinspection() {
		return instpEndinspection;
	}
	public void setInstpEndinspection(Set<InstpEndinspection> instpEndinspection) {
		this.instpEndinspection = instpEndinspection;
	}	
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getAnninspection() == null) {
			this.setInstpAnninspection((new HashSet<InstpAnninspection>()));
		}
		this.getInstpAnninspection().add((InstpAnninspection) anninspection);
		((InstpAnninspection) anninspection).setGranted(this);
	}
	/**
	 * 添加项目中检
	 */	
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getMidinspection() == null) {
			this.setInstpMidinspection(new HashSet<InstpMidinspection>());
		}
		this.getInstpMidinspection().add((InstpMidinspection) midinspection);
		((InstpMidinspection) midinspection).setGranted(this);
	}

	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setInstpEndinspection(new HashSet<InstpEndinspection>());
		}
		this.getInstpEndinspection().add((InstpEndinspection) endinspection);
		((InstpEndinspection)endinspection).setGranted(this);
	}
	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getVariation() == null) {
			this.setInstpVariation(new HashSet<InstpVariation>());
		}
		this.getInstpVariation().add((InstpVariation)variation);
		((InstpVariation)variation).setGranted(this);
	}

	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setInstpFunding(new HashSet<InstpFunding>());
		}
		this.getInstpFunding().add((InstpFunding)funding);
		((InstpFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "InstpApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "InstpGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "InstpAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "InstpMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "InstpEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "InstpEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "InstpVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "InstpFunding";
	}
	@JSON(serialize=false)
	public InstpApplication getApplication() {
		return application;
	}
	public void setApplication(InstpApplication application) {
		this.application = application;
	}
	@JSON(serialize=false)
	public Set<InstpVariation> getInstpVariation() {
		return instpVariation;
	}
	public void setInstpVariation(Set<InstpVariation> instpVariation) {
		this.instpVariation = instpVariation;
	}
	@JSON(serialize=false)
	public Set<InstpFunding> getInstpFunding() {
		return instpFunding;
	}
	public void setInstpFunding(Set<InstpFunding> instpFunding) {
		this.instpFunding = instpFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectAnninspection> getAnninspection() {
		return getInstpAnninspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMidinspection> getMidinspection() {
		return getInstpMidinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspection> getEndinspection() {
		return getInstpEndinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getInstpVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getInstpFunding();
	}

}