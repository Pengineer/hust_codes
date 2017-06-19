package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 肖雅
 */
public class KeyMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 4056579564684643407L;
	private KeyApplication application;//重大攻关项目申请
	private int isSubprojectDirector;//是否子课题负责人:1是；0否
	
	/**
	 * 重大攻关项目成员构造器
	 * 鉴别器字段(projectType='key')
	 */
	public KeyMember() {
		this.setProjectType("key");
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "KeyMember";
	}
	public KeyMember clone() throws CloneNotSupportedException{
		return (KeyMember) super.clone();
	}
	
	@JSON(serialize=false)
	public KeyApplication getApplication() {
		return application;
	}
	
	public void setApplication(ProjectApplication application) {
		this.application = (KeyApplication)application;
	}

	public int getIsSubprojectDirector() {
		return isSubprojectDirector;
	}

	public void setIsSubprojectDirector(int isSubprojectDirector) {
		this.isSubprojectDirector = isSubprojectDirector;
	}
	
}