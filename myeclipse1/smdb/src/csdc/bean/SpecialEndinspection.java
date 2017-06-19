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
public class SpecialEndinspection extends ProjectEndinspection implements java.io.Serializable {
	
	private static final long serialVersionUID = -7930625041394111661L;
	private SpecialGranted granted;//项目立项
	private Set<SpecialEndinspectionReview> specialEndinspectionReview;//结项专家评审详细信息

	/**
	 * 一般项目结项构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialEndinspection() {
		this.setProjectType("special");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "SpecialEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "SpecialEndinspectionReview";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public SpecialGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	@JSON(serialize=false)
	public void setGranted(ProjectGranted granted) {
		this.granted = (SpecialGranted) granted;
	}
	public void setGranted(SpecialGranted granted) {
		this.granted = granted;
	}
	@JSON(serialize=false)
	public Set<SpecialEndinspectionReview> getSpecialEndinspectionReview() {
		return specialEndinspectionReview;
	}
	public void setSpecialEndinspectionReview(
			Set<SpecialEndinspectionReview> specialEndinspectionReview) {
		this.specialEndinspectionReview = specialEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getSpecialEndinspectionReview();
	}

}