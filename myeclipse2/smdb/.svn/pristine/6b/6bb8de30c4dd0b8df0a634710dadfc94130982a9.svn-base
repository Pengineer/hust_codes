package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * 一般项目成员表
 * @author 刘雅琴
 */
public class GeneralMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 8359940398328553037L;
	private GeneralApplication application;//一般项目申请
	
	/**
	 * 一般项目成员构造器
	 * 鉴别器字段(projectType='general')
	 */
	public GeneralMember() {
		this.setProjectType("general");
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "GeneralMember";
	}
	
	public GeneralMember clone() throws CloneNotSupportedException{
		return (GeneralMember) super.clone();
	}
	@JSON(serialize=false)
	public GeneralApplication getApplication() {
		return application;
	}
	public void setApplication(ProjectApplication application) {
		this.application = (GeneralApplication)application;
	}

}