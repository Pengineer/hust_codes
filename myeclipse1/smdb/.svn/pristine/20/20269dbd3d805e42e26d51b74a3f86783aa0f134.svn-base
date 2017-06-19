package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author 王燕
 */
public class EntrustMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 8359940398328553037L;
	private EntrustApplication application;//委托（应急）课题申请
	
	/**
	 * 委托（应急）课题成员构造器
	 * 鉴别器字段(projectType='entrust')
	 */
	public EntrustMember() {
		this.setProjectType("entrust");
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "EntrustMember";
	}
	
	public EntrustMember clone() throws CloneNotSupportedException{
		return (EntrustMember) super.clone();
	}
	@JSON(serialize=false)
	public EntrustApplication getApplication() {
		return application;
	}
	public void setApplication(ProjectApplication application) {
		this.application = (EntrustApplication)application;
	}

}