package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class KeyEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = -3270154671670208034L;
	private KeyEndinspection endinspection;//结项申请
	
	/**
	 * 重大攻关项目结项评审构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyEndinspectionReview() {
		this.setProjectType("key");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public KeyEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (KeyEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "KeyEndinspectionReview";
	}
}