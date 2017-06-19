package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 后期资助项目结项
 * @author 王燕
 *
 */
public class PostEndinspection extends ProjectEndinspection implements java.io.Serializable {
	private static final long serialVersionUID = -7930625041394111661L;
	private PostGranted granted;
	private Set<PostEndinspectionReview> postEndinspectionReview;
	
	/**
	 * 后期资助项目结项构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostEndinspection() {
		this.setProjectType("post");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
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
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public PostGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (PostGranted) granted;
	}
	@JSON(serialize=false)
	public Set<PostEndinspectionReview> getPostEndinspectionReview() {
		return postEndinspectionReview;
	}
	public void setPostEndinspectionReview(
			Set<PostEndinspectionReview> postEndinspectionReview) {
		this.postEndinspectionReview = postEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getPostEndinspectionReview();
	}

}