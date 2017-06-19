package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 刘雅琴
 */
public class GeneralFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public GeneralGranted granted;//一般项目
	
	/**
	 * 一般项目经费构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralFunding() {
		this.setProjectType("general");
	}
	@JSON(serialize=false)
	public GeneralGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (GeneralGranted)granted;
	}
}
