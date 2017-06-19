package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 一般项目年检
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 肖雅
 */
public class SpecialAnninspection extends ProjectAnninspection implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private SpecialGranted granted;//项目立项
	
	/**
	 * 一般项目年检构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialAnninspection() {
		this.setProjectType("special");
	}
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName(){
		return "SpecialAnninspection";
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
	public void setGranted(ProjectGranted granted) {
		this.granted = (SpecialGranted) granted;
	}
	
}