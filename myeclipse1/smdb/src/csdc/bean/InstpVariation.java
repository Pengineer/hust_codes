package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class InstpVariation extends ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = -2640979717111096552L;
	private InstpGranted granted;//变更项目
	
	/**
	 * 基地项目变更构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpVariation() {
		this.setProjectType("instp");
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "InstpVariation";
	}
	@JSON(serialize=false)
	public InstpGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (InstpGranted)granted;
	}
}
