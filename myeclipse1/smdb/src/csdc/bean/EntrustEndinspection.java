package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 委托（应急）课题结项
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 王燕
 *
 */
public class EntrustEndinspection extends ProjectEndinspection implements java.io.Serializable {
	
	private static final long serialVersionUID = -7930625041394111661L;
	private EntrustGranted granted;//项目立项
	private Set<EntrustEndinspectionReview> entrustEndinspectionReview;//结项专家评审详细信息

	/**
	 * 委托（应急）课题结项构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustEndinspection() {
		this.setProjectType("entrust");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "EntrustEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "EntrustEndinspectionReview";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public EntrustGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (EntrustGranted) granted;
	}
	@JSON(serialize=false)
	public Set<EntrustEndinspectionReview> getEntrustEndinspectionReview() {
		return entrustEndinspectionReview;
	}
	public void setEntrustEndinspectionReview(
			Set<EntrustEndinspectionReview> entrustEndinspectionReview) {
		this.entrustEndinspectionReview = entrustEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getEntrustEndinspectionReview();
	}

}