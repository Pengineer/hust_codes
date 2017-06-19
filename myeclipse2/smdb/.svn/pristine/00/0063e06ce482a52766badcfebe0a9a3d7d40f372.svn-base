package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class KeyVariation extends ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = -7546642219350920921L;
	private KeyGranted granted;//变更项目
	
	/**
	 * 重大攻关项目变更构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyVariation() {
		this.setProjectType("key");
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "KeyVariation";
	}
	@JSON(serialize=false)
	public KeyGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (KeyGranted)granted;
	}
}
