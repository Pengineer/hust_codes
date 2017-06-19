package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */

public class PostEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private PostEndinspection endinspection;//结项申请
	
	/**
	 * 后期资助项目结项评审构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostEndinspectionReview() {
		this.setProjectType("post");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public PostEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (PostEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "PostEndinspectionReview";
	}
}