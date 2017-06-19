package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 后期资助项目年检
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 肖雅
 */
public class PostAnninspection extends ProjectAnninspection implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private PostGranted granted;//项目立项
	
	/**
	 * 后期资助项目年检构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostAnninspection() {
		this.setProjectType("post");
	}
	/**
	 * 获取项目对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName(){
		return "PostAnninspection";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public PostGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (PostGranted) granted;
	}
}