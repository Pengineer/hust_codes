package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class InstpFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public InstpGranted granted;//基地项目
	
	/**
	 * 基地项目拨款构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpFunding() {
		this.setProjectType("instp");
	}
	@JSON(serialize=false)
	public InstpGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (InstpGranted)granted;
	}
}
