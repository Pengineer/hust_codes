package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */

public class KeyApplicationReview extends ProjectApplicationReview implements java.io.Serializable {

	private static final long serialVersionUID = -4975617238525659084L;
	private KeyApplication application;//招标申请
	
	/**
	 * 重大攻关项目招标评审构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyApplicationReview() {
		this.setProjectType("key");
	}
	/**
	 * 关联项目招标对象
	 */
	@JSON(serialize=false)
	public KeyApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (KeyApplication)application;
	}
	
	/**
	 * 获取项目对应招标评审类名
	 */
	@JSON(serialize = false)
	public String getApplicationReviewClassName(){
		return "KeyApplicationReview";
	}
}