package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class InstpMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 8359940398328553037L;
	private InstpApplication application;//基地项目申请
	private int isSubprojectDirector;//是否子项目负责人:1是；0否
	
	/**
	 * 基地项目成员构造器
	 * 鉴别器字段(projectType='instp')
	 */
	public InstpMember() {
		this.setProjectType("instp");
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "InstpMember";
	}
	public InstpMember clone() throws CloneNotSupportedException{
		return (InstpMember) super.clone();
	}
	
	@JSON(serialize=false)
	public InstpApplication getApplication() {
		return application;
	}
	
	public void setApplication(ProjectApplication application) {
		this.application = (InstpApplication)application;
	}

	public int getIsSubprojectDirector() {
		return isSubprojectDirector;
	}

	public void setIsSubprojectDirector(int isSubprojectDirector) {
		this.isSubprojectDirector = isSubprojectDirector;
	}
	
}