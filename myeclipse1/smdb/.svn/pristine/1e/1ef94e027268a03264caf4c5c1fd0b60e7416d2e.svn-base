package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */

public class PostApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private PostApplication application;//结项申请
	
	/**
	 * 后期资助项目申请评审构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostApplicationReview() {
		this.setProjectType("post");
	}
	/**
	 * 关联项目申请对象
	 */
	@JSON(serialize=false)
	public PostApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (PostApplication)application;
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName() {
		return "PostApplicationReview";
	}
}