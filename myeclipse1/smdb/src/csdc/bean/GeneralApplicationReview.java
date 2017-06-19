package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */

public class GeneralApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private GeneralApplication application;//申请申请
	
	/**
	 * 一般项目申请评审构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralApplicationReview() {
		this.setProjectType("general");
	}

	/**
	 * 关联项目申请对象
	 */
	@JSON(serialize=false)
	public GeneralApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (GeneralApplication)application;
	}
	
	/**
	 * 获取项目对应申请类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "GeneralApplicationReview";
	}

}