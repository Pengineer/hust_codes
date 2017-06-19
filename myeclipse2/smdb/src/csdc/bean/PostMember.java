package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class PostMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 8359940398328553037L;
	private PostApplication application;//后期资助项目申请
	
	/**
	 * 后期资助项目成员构造器
	 * 鉴别器字段(projectType='post')
	 */
	public PostMember() {
		this.setProjectType("post");
	}
	
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "PostMember";
	}
	
	public PostMember clone() throws CloneNotSupportedException{
		return (PostMember) super.clone();
	}

	@JSON(serialize=false)
	public PostApplication getApplication() {
		return application;
	}

	public void setApplication(ProjectApplication application) {
		this.application = (PostApplication)application;
	}
}