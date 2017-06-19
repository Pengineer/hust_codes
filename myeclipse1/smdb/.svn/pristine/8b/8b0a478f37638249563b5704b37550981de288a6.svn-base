package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */
public class KeyGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -753899892099541608L;

	private KeyApplication application;// 项目申请

	private Set<KeyAnninspection> keyAnninspection;//项目年检
	private Set<KeyMidinspection> keyMidinspection;//项目中检
	private Set<KeyEndinspection> keyEndinspection;//项目结项
	private Set<KeyVariation> keyVariation;// 项目变更
	private Set<KeyFunding> keyFunding;// 项目拨款
	
	/**
	 * 重大攻关项目立项构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyGranted() {
		this.setProjectType("key");
	}
	
	static {
    	typeMap.put("key", "教育部重大攻关项目");
    	sortTypeMap();
    }
	
	/**
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<KeyAnninspection> getKeyAnninspection() {
		return keyAnninspection;
	}
	public void setKeyAnninspection(Set<KeyAnninspection> keyAnninspection) {
		this.keyAnninspection = keyAnninspection;
	}
	
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getKeyAnninspection() == null) {
			this.setKeyAnninspection((new HashSet<KeyAnninspection>()));
		}
		this.getKeyAnninspection().add((KeyAnninspection) anninspection);
		((KeyAnninspection) anninspection).setGranted(this);
	}
	/**
	 * 获取项目中检对象集合
	 */
	@JSON(serialize = false)
	public Set<KeyMidinspection> getKeyMidinspection() {
		return keyMidinspection;
	}
	public void setKeyMidinspection(Set<KeyMidinspection> keyMidinspection) {
		this.keyMidinspection = keyMidinspection;
	}
	/**
	 * 添加项目中检
	 */
	public void addMidinspection(ProjectMidinspection midinspection) {
		if (this.getKeyMidinspection() == null) {
			this.setKeyMidinspection((new HashSet<KeyMidinspection>()));
		}
		this.getKeyMidinspection().add((KeyMidinspection) midinspection);
		((KeyMidinspection) midinspection).setGranted(this);
	}
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public Set<KeyEndinspection> getKeyEndinspection() {
		return keyEndinspection;
	}
	public void setKeyEndinspection(Set<KeyEndinspection> keyEndinspection) {
		this.keyEndinspection = keyEndinspection;
	}
	/**
	 * 添加项目结项
	 */
	public void addEndinspection(ProjectEndinspection endinspection) {
		if (this.getKeyEndinspection() == null) {
			this.setKeyEndinspection(new HashSet<KeyEndinspection>());
		}
		this.getKeyEndinspection().add((KeyEndinspection) endinspection);
		((KeyEndinspection) endinspection).setGranted(this);
	}

	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getKeyVariation() == null) {
			this.setKeyVariation(new HashSet<KeyVariation>());
		}
		this.getKeyVariation().add((KeyVariation)variation);
		((KeyVariation)variation).setGranted(this);
	}
	/**
	 * 添加一个拨款
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getKeyFunding() == null) {
			this.setKeyFunding(new HashSet<KeyFunding>());
		}
		this.getKeyFunding().add((KeyFunding)funding);
		((KeyFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "KeyApplication";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "KeyAnninspection";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "KeyGranted";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		return "KeyMidinspection";
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "KeyEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "KeyEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "KeyVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "KeyFunding";
	}
	
	@JSON(serialize = false)
	public KeyApplication getApplication() {
		return application;
	}
	public void setApplication(KeyApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<KeyVariation> getKeyVariation() {
		return keyVariation;
	}
	public void setKeyVariation(Set<KeyVariation> keyVariation) {
		this.keyVariation = keyVariation;
	}
	@JSON(serialize = false)
	public Set<KeyFunding> getKeyFunding() {
		return keyFunding;
	}
	public void setKeyFunding(Set<KeyFunding> keyFunding) {
		this.keyFunding = keyFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectAnninspection> getAnninspection() {
		return getKeyAnninspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectMidinspection> getMidinspection() {
		return getKeyMidinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspection> getEndinspection() {
		return getKeyEndinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getKeyVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getKeyFunding();
	}

}