package csdc.bean;

import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
/**
 * 基地项目中检类
 * @author 王燕
 */
public class InstpMidinspection extends ProjectMidinspection implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private InstpGranted granted;//项目立项
	
	
	/**
	 * 基地项目中检构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpMidinspection() {
		this.setProjectType("instp");
	}
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName(){
		return "InstpMidinspection";
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

}