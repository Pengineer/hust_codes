package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 一般项目结项
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 雷达
 *
 */
public class GeneralEndinspection extends ProjectEndinspection implements java.io.Serializable {
	
	private static final long serialVersionUID = -7930625041394111661L;
	private GeneralGranted granted;//项目立项
	private Set<GeneralEndinspectionReview> generalEndinspectionReview;//结项专家评审详细信息

	/**
	 * 一般项目结项构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralEndinspection() {
		this.setProjectType("general");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "GeneralEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "GeneralEndinspectionReview";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public GeneralGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	@JSON(serialize=false)
	public void setGranted(ProjectGranted granted) {
		this.granted = (GeneralGranted) granted;
	}
	public void setGranted(GeneralGranted granted) {
		this.granted = granted;
	}
	@JSON(serialize=false)
	public Set<GeneralEndinspectionReview> getGeneralEndinspectionReview() {
		return generalEndinspectionReview;
	}
	public void setGeneralEndinspectionReview(
			Set<GeneralEndinspectionReview> generalEndinspectionReview) {
		this.generalEndinspectionReview = generalEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getGeneralEndinspectionReview();
	}

}