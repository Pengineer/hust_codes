package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */

public class InstpEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private InstpEndinspection endinspection;//结项申请
	
	/**
	 * 基地项目结项评审构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpEndinspectionReview() {
		this.setProjectType("instp");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public InstpEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (InstpEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "InstpEndinspectionReview";
	}
}