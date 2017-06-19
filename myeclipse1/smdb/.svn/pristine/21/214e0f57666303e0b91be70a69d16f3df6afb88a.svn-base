package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class GeneralVariation extends ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = -2640979717111096552L;
	private GeneralGranted granted;//变更项目
	
	/**
	 * 一般项目变更构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralVariation() {
		this.setProjectType("general");
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "GeneralVariation";
	}
	@JSON(serialize=false)
	public GeneralGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (GeneralGranted)granted;
	}
}
