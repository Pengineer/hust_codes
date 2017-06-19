package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class EntrustVariation extends ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = -2640979717111096552L;
	private EntrustGranted granted;//变更项目
	
	/**
	 * 委托（应急）课题变更构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustVariation() {
		this.setProjectType("entrust");
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "EntrustVariation";
	}
	@JSON(serialize=false)
	public EntrustGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (EntrustGranted)granted;
	}
}
