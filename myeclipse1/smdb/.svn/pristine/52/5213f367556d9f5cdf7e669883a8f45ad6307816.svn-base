package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 基地项目结项
 * @author 王燕
 *
 */
public class InstpEndinspection extends ProjectEndinspection implements java.io.Serializable {
	private static final long serialVersionUID = -7930625041394111661L;
	private InstpGranted granted;//项目立项
	private Set<InstpEndinspectionReview> instpEndinspectionReview;//结项专家评审详细信息

	/**
	 * 基地项目结项构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpEndinspection() {
		this.setProjectType("instp");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "InstpEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "InstpEndinspectionReview";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public InstpGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (InstpGranted) granted;
	}
	@JSON(serialize=false)
	public Set<InstpEndinspectionReview> getInstpEndinspectionReview() {
		return instpEndinspectionReview;
	}
	public void setInstpEndinspectionReview(
			Set<InstpEndinspectionReview> instpEndinspectionReview) {
		this.instpEndinspectionReview = instpEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getInstpEndinspectionReview();
	}

}