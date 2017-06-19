package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class PostVariation extends ProjectVariation  implements java.io.Serializable {

	private static final long serialVersionUID = -2640979717111096552L;
	private PostGranted granted;//变更项目
	
	/**
	 * 后期资助项目变更构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostVariation() {
		this.setProjectType("post");
	}
	
	/**
	 * 获取项目对应变更类名
	 */
	@JSON(serialize = false)
	public String getVariationClassName(){
		return "PostVariation";
	}
	
	@JSON(serialize=false)
	public PostGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (PostGranted)granted;
	}
}