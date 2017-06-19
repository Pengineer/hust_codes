package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 刘雅琴
 */
public class SpecialFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public SpecialGranted granted;//一般项目
	
	/**
	 * 一般项目经费构造器
	 * 鉴别器字段(projectType='special')
	 */
	public SpecialFunding() {
		this.setProjectType("special");
	}
	@JSON(serialize=false)
	public SpecialGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (SpecialGranted)granted;
	}
}
