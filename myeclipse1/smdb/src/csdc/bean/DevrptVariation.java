package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptVariation extends ProjectVariation implements java.io.Serializable {

	private static final long serialVersionUID = -2640979717111096552L;
	private DevrptGranted granted;//变更项目
	
	/**
	 * 发展报告变更构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptVariation() {
		this.setProjectType("devrpt");
	}
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "DevrptVariation";
	}
	@JSON(serialize=false)
	public DevrptGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (DevrptGranted)granted;
	}
}
