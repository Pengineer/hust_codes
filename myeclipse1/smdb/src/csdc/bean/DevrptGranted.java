package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private DevrptApplication application;// 项目申请

	private Set<DevrptAnninspection> devrptAnninspection;//项目年检
	private Set<DevrptMidinspection> devrptMidinspection;//项目中检
	private Set<DevrptEndinspection> devrptEndinspection;//项目结项
	private Set<DevrptVariation> devrptVariation;// 项目变更
	private Set<DevrptFunding> devrptFunding;// 项目拨款
	
	/**
	 * 一般项目立项构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptGranted() {
		this.setProjectType("devrpt");
	}
	
	static {
    	typeMap.put("devrpt", "教育部发展报告项目");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<DevrptAnninspection> getAnninspection() {
		return devrptAnninspection;
	}
	public void setAnninspection(Set<DevrptAnninspection> anninspection) {
		this.devrptAnninspection = anninspection;
	}
	
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public Set<DevrptMidinspection> getMidinspection() {
		return devrptMidinspection;
	}
	public void setMidinspection(Set<DevrptMidinspection> midinspection) {
		this.devrptMidinspection = midinspection;
	}
	
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public Set<DevrptEndinspection> getEndinspection() {
		return devrptEndinspection;
	}
	public void setEndinspection(Set<DevrptEndinspection> endinspection) {
		this.devrptEndinspection = endinspection;
	}
	
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getAnninspection() == null) {
			this.setAnninspection((new HashSet<DevrptAnninspection>()));
		}
		this.getAnninspection().add((DevrptAnninspection) anninspection);
		((DevrptAnninspection) anninspection).setGranted(this);
	}
	
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getMidinspection() == null) {
			this.setMidinspection((new HashSet<DevrptMidinspection>()));
		}
		this.getMidinspection().add((DevrptMidinspection) midinspection);
		((DevrptMidinspection) midinspection).setGranted(this);
	}
	
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getEndinspection() == null) {
			this.setEndinspection(new HashSet<DevrptEndinspection>());
		}
		this.getEndinspection().add((DevrptEndinspection) endinspection);
		((DevrptEndinspection) endinspection).setGranted(this);
	}

	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getDevrptVariation() == null) {
			this.setDevrptVariation(new HashSet<DevrptVariation>());
		}
		this.getDevrptVariation().add((DevrptVariation)variation);
		((DevrptVariation)variation).setGranted(this);
	}
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setDevrptFunding(new HashSet<DevrptFunding>());
		}
		this.getDevrptFunding().add((DevrptFunding)funding);
		((DevrptFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "DevrptApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "DevrptGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "DevrptAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "DevrptMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "DevrptEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "DevrptEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "DevrptVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "DevrptFunding";
	}
	
	@JSON(serialize = false)
	public DevrptApplication getApplication() {
		return application;
	}
	public void setApplication(DevrptApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<DevrptAnninspection> getDevrptAnninspection() {
		return devrptAnninspection;
	}
	public void setDevrptAnninspection(
			Set<DevrptAnninspection> devrptAnninspection) {
		this.devrptAnninspection = devrptAnninspection;
	}
	@JSON(serialize = false)
	public Set<DevrptMidinspection> getDevrptMidinspection() {
		return devrptMidinspection;
	}
	public void setDevrptMidinspection(
			Set<DevrptMidinspection> devrptMidinspection) {
		this.devrptMidinspection = devrptMidinspection;
	}
	@JSON(serialize = false)
	public Set<DevrptEndinspection> getDevrptEndinspection() {
		return devrptEndinspection;
	}
	public void setDevrptEndinspection(
			Set<DevrptEndinspection> devrptEndinspection) {
		this.devrptEndinspection = devrptEndinspection;
	}
	@JSON(serialize = false)
	public Set<DevrptVariation> getDevrptVariation() {
		return devrptVariation;
	}
	public void setDevrptVariation(Set<DevrptVariation> devrptVariation) {
		this.devrptVariation = devrptVariation;
	}
	@JSON(serialize = false)
	public Set<DevrptFunding> getDevrptFunding() {
		return devrptFunding;
	}
	public void setDevrptFunding(Set<DevrptFunding> devrptFunding) {
		this.devrptFunding = devrptFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getDevrptVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getDevrptFunding();
	}


}