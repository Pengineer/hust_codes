package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptMidinspection extends ProjectMidinspection implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private DevrptGranted granted;//项目立项
	
	
	/**
	 * 发展报告中检构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptMidinspection() {
		this.setProjectType("devrpt");
	}
	/**
	 * 获取项目对应中检类名
	 */
	@JSON(serialize = false)
	public String getMidinspectionClassName(){
		return "DevrptMidinspection";
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

}