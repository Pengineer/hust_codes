package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */

public class EntrustApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private EntrustApplication application;//申报申请
	
	/**
	 *委托（应急）课题申报评审构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustApplicationReview() {
		this.setProjectType("entrust");
	}
	/**
	 * 关联项目申报对象
	 */
	@JSON(serialize=false)
	public EntrustApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (EntrustApplication)application;
	}
	
	/**
	 * 获取项目对应申报类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "EntrustApplicationReview";
	}
}