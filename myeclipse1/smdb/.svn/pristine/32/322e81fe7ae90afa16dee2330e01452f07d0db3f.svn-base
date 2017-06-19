package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class SpecialEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private SpecialEndinspection endinspection;//结项申请
	
	/**
	 * 一般项目结项评审构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialEndinspectionReview() {
		this.setProjectType("special");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public SpecialEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (SpecialEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "SpecialEndinspectionReview";
	}
}