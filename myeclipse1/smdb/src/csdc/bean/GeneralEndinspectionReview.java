package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class GeneralEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private GeneralEndinspection endinspection;//结项申请
	
	/**
	 * 一般项目结项评审构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralEndinspectionReview() {
		this.setProjectType("general");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public GeneralEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (GeneralEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "GeneralEndinspectionReview";
	}
}