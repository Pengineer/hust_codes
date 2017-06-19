package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */
public class KeyFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -5357957005858089062L;
	public KeyGranted granted;//重大攻关项目
	
	/**
	 * 重大攻关项目经费构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyFunding() {
		this.setProjectType("key");
	}
	@JSON(serialize=false)
	public KeyGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (KeyGranted)granted;
	}
}
