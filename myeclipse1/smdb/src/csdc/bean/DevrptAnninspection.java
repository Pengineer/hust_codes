package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptAnninspection extends ProjectAnninspection implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private DevrptGranted granted;//项目立项
	
	/**
	 * 发展报告年检构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptAnninspection() {
		this.setProjectType("devrpt");
	}
	/**
	 * 发展报告对应年检类名
	 */
	@JSON(serialize = false)
	public String getAnninspectionClassName(){
		return "DevrptAnninspection";
	}
	/**
	 * 发展报告立项对象
	 */
	@JSON(serialize=false)
	public DevrptGranted getGranted() {
		return granted;
	}
	
	/**
	 * 发展报告立项对象
	 */
	public void setGranted(ProjectGranted granted) {
		this.granted = (DevrptGranted) granted;
	}

}