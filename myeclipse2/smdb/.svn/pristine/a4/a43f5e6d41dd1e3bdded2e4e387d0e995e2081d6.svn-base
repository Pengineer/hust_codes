package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 重大攻关项目结项
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 肖雅
 *
 */
public class KeyEndinspection extends ProjectEndinspection implements java.io.Serializable {
	
	private static final long serialVersionUID = 5878303201324441568L;
	private KeyGranted granted;//中标项目
	private Set<KeyEndinspectionReview> keyEndinspectionReview;//结项专家评审详细信息

	/**
	 * 重大攻关项目结项构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyEndinspection() {
		this.setProjectType("key");
	}
	/**
	 * 获取项目对应结项类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionClassName(){
		return "KeyEndinspection";
	}
	/**
	 * 获取项目对应结项评审类名
	 */
	@JSON(serialize = false)
	public String getEndinspectionReviewClassName(){
		return "KeyEndinspectionReview";
	}
	/**
	 * 获取项目中标对象
	 */
	@JSON(serialize=false)
	public KeyGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目中标对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (KeyGranted) granted;
	}
	@JSON(serialize=false)
	public Set<KeyEndinspectionReview> getKeyEndinspectionReview() {
		return keyEndinspectionReview;
	}
	public void setKeyEndinspectionReview(
			Set<KeyEndinspectionReview> keyEndinspectionReview) {
		this.keyEndinspectionReview = keyEndinspectionReview;
	}
	@JSON(serialize = false)
	@Override
	public Set<? extends ProjectEndinspectionReview> getEndinspectionReview() {
		return getKeyEndinspectionReview();
	}

}