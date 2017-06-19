package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * @author maowh
 *
 */
public class DevrptApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private DevrptApplication application;//结项申请
	
	/**
	 * 发展报告申请评审构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptApplicationReview() {
		this.setProjectType("devrpt");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public DevrptApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (DevrptApplication)application;
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName() {
		return "DevrptApplicationReview";
	}
}