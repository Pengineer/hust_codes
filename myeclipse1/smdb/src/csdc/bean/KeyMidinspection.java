package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 重大攻关项目中检
 * 申请及各级审核状态字段：0默认，1退回，2暂存，3提交
 * 各级审核结果字段：0未审核，1不同意，2同意
 * @author 肖雅
 */
public class KeyMidinspection extends ProjectMidinspection implements java.io.Serializable  {
	
	private static final long serialVersionUID = -353156205556425710L;
	private KeyGranted granted;//项目立项
	
	/**
	 * 重大攻关项目中检构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyMidinspection() {
		this.setProjectType("key");
	}
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName(){
		return "KeyMidinspection";
	}
	/**
	 * 获取项目立项对象
	 */
	@JSON(serialize=false)
	public KeyGranted getGranted() {
		return granted;
	}
	
	/**
	 * 关联项目立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (KeyGranted) granted;
	}
}