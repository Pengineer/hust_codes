package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class EntrustEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private EntrustEndinspection endinspection;//结项申请
	
	/**
	 * 委托（应急）课题结项评审构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustEndinspectionReview() {
		this.setProjectType("entrust");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public EntrustEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (EntrustEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "EntrustEndinspectionReview";
	}
}