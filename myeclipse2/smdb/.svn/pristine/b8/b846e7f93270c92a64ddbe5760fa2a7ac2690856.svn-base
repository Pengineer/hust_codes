package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class EntrustFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public EntrustGranted granted;//委托（应急）课题
	
	/**
	 * 委托（应急）课题经费构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustFunding() {
		this.setProjectType("entrust");
	}
	@JSON(serialize=false)
	public EntrustGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (EntrustGranted)granted;
	}
}
