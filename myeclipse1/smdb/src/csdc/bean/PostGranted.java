package csdc.bean;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class PostGranted extends ProjectGranted implements java.io.Serializable {

	private static final long serialVersionUID = -2556504385303842690L;
	private PostApplication application;//项目申请

	private Set<PostAnninspection> postAnninspection;//项目年检
	private Set<PostVariation> postVariation;//项目变更
	private Set<PostEndinspection> postEndinspection;//项目结项
	private Set<PostFunding> postFunding;// 项目拨款
	
	/**
	 * 后期资助项目立项构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostGranted() {
		this.setProjectType("post");
	}
	
	static {
    	typeMap.put("post", "教育部后期资助项目");
    	sortTypeMap();
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
	 * 获取项目年检对象集合
	 */
	@JSON(serialize = false)
	public Set<PostAnninspection> getPostAnninspection() {
		return postAnninspection;
	}
	public void setPostAnninspection(Set<PostAnninspection> postAnninspection) {
		this.postAnninspection = postAnninspection;
	}
	/**
	 * 获取项目结项对象集合
	 */
	@JSON(serialize = false)
	public Set<PostEndinspection> getPostEndinspection() {
		return postEndinspection;
	}
	public void setPostEndinspection(Set<PostEndinspection> postEndinspection) {
		this.postEndinspection = postEndinspection;
	}
	/**
	 * 添加项目年检
	 */
	public void addAnninspection(ProjectAnninspection anninspection) {
		if (this.getAnninspection() == null) {
			this.setPostAnninspection((new HashSet<PostAnninspection>()));
		}
		this.getPostAnninspection().add((PostAnninspection) anninspection);
		((PostAnninspection) anninspection).setGranted(this);
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
			this.setPostEndinspection(new HashSet<PostEndinspection>());
		}
		this.getPostEndinspection().add((PostEndinspection) endinspection);
		((PostEndinspection)endinspection).setGranted(this);
	}
	
	/**
	 * 添加一个变更
	 */
	public void addVariation(ProjectVariation variation) {
		if (this.getVariation() == null) {
			this.setPostVariation(new HashSet<PostVariation>());
		}
		this.getPostVariation().add((PostVariation)variation);
		((PostVariation)variation).setGranted(this);
	}
	
	/**
	 * 添加一个拨款
	 * @param postGranted
	 */
	public void addFunding(ProjectFunding funding) {
		if (this.getFunding() == null) {
			this.setPostFunding(new HashSet<PostFunding>());
		}
		this.getPostFunding().add((PostFunding)funding);
		((PostFunding)funding).setGranted(this);
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationClassName(){
		return "PostApplication";
	}
	
	/**
	 * 获取项目对应立项子类类名
	 */
	@JSON(serialize = false)
	public String getGrantedClassName(){
		return "PostGranted";
	}
	
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName() {
		return "PostAnninspection";
	}
	
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName() {
		throw new RuntimeException("未实现的方法！");
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName() {
		return "PostEndinspection";
	}
	
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "PostEndinspectionReview";
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "PostVariation";
	}
	/**
	 * 获取项目对应拨款类名
	 */
	@JSON(serialize = false)
	public String getFundingClassName(){
		return "PostFunding";
	}
	
	@JSON(serialize = false)
	public PostApplication getApplication() {
		return application;
	}
	public void setApplication(PostApplication application) {
		this.application = application;
	}
	@JSON(serialize = false)
	public Set<PostVariation> getPostVariation() {
		return postVariation;
	}
	public void setPostVariation(Set<PostVariation> postVariation) {
		this.postVariation = postVariation;
	}
	@JSON(serialize = false)
	public Set<PostFunding> getPostFunding() {
		return postFunding;
	}
	public void setPostFunding(Set<PostFunding> postFunding) {
		this.postFunding = postFunding;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectAnninspection> getAnninspection() {
		return getPostAnninspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspection> getEndinspection() {
		return getPostEndinspection();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectVariation> getVariation() {
		return getPostVariation();
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectFunding> getFunding() {
		return getPostFunding();
	}

}