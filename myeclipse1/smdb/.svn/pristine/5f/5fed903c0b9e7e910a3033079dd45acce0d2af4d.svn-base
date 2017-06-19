package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */

public class InstpApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private InstpApplication application;//结项申请
	
	/**
	 * 基地项目申请评审构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpApplicationReview() {
		this.setProjectType("instp");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public InstpApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (InstpApplication)application;
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName() {
		return "InstpApplicationReview";
	}
}