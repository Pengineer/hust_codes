package csdc.bean;

import org.apache.struts2.json.annotations.JSON;
/**
 * 
 * @author maowh
 *
 */
public class DevrptMember extends ProjectMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 8359940398328553037L;
	private DevrptApplication application;//发展报告申请
	
	/**
	 * 发展报告成员构造器
	 * 鉴别器字段(projectType='devrpt')
	 */
	public DevrptMember() {
		this.setProjectType("devrpt");
	}
	/**
	 * 获取项目对应成员类名
	 */
	@JSON(serialize = false)
	public String getMemberClassName(){
		return "DevrptMember";
	}
	
	public DevrptMember clone() throws CloneNotSupportedException{
		return (DevrptMember) super.clone();
	}
	@JSON(serialize=false)
	public DevrptApplication getApplication() {
		return application;
	}
	public void setApplication(ProjectApplication application) {
		this.application = (DevrptApplication)application;
	}

}