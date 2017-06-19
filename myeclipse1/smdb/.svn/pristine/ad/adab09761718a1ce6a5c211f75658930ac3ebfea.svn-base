package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptEndinspection extends ProjectEndinspection implements java.io.Serializable {
	private static final long serialVersionUID = -7930625041394111661L;
	private DevrptGranted granted;//项目立项
	private Set<DevrptEndinspectionReview> devrptEndinspectionReview;//结项专家评审详细信息

	/**
	 * 发展报告结项构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptEndinspection() {
		this.setProjectType("devrpt");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "DevrptEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "DevrptEndinspectionReview";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public DevrptGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (DevrptGranted) granted;
	}
	@JSON(serialize=false)
	public Set<DevrptEndinspectionReview> getDevrptEndinspectionReview() {
		return devrptEndinspectionReview;
	}
	public void setDevrptEndinspectionReview(
			Set<DevrptEndinspectionReview> devrptEndinspectionReview) {
		this.devrptEndinspectionReview = devrptEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getDevrptEndinspectionReview();
	}

}