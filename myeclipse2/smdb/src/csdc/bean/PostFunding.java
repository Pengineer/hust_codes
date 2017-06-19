package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class PostFunding extends ProjectFunding implements java.io.Serializable {

	private static final long serialVersionUID = -7808210654277985711L;
	public PostGranted granted;//后期资助项目
	/**
	 * 后期资助项目经费构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostFunding() {
		this.setProjectType("post");
	}
	
	@JSON(serialize=false)
	public PostGranted getGranted() {
		return granted;
	}
	public void setGranted(ProjectGranted granted) {
		this.granted = (PostGranted)granted;
	}
}
