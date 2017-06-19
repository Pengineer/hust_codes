package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public DevrptGranted granted;//发展报告
	
	/**
	 * 发展报告拨款构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptFunding() {
		this.setProjectType("devrpt");
	}
	@JSON(serialize=false)
	public DevrptGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (DevrptGranted)granted;
	}
}
