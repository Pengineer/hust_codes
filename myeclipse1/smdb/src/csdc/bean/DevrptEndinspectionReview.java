package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptEndinspectionReview extends ProjectEndinspectionReview implements java.io.Serializable {

	private static final long serialVersionUID = 6725687535913677567L;
	private DevrptEndinspection endinspection;//结项申请
	
	/**
	 * 发展报告结项评审构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptEndinspectionReview() {
		this.setProjectType("devrpt");
	}
	/**
	 * 关联项目结项对象
	 */
	@JSON(serialize=false)
	public DevrptEndinspection getEndinspection() {
		return endinspection;
	}

	public void setEndinspection(ProjectEndinspection endinspection) {
		this.endinspection = (DevrptEndinspection)endinspection;
	}
	
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "DevrptEndinspectionReview";
	}
}