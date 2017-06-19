package csdc.tool.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类用于存储教师账号登录项目管理系统后，
 * 待处理项目列表信息
 * @author 龚凡
 * @version 2011.01.20
 */
public class TeacherProjectBean {

	private String projectApplicationId;// 项目申请ID
	private String projectName;// 项目名称
	private String projectType;// 项目类型
	private List<String[]> projectBusiness;// 项目可处理的业务，代码及名称

	/**
	 * 构造方法，此类在实例化时，自动new一个projectBusiness对象
	 */
	public TeacherProjectBean() {
		projectBusiness = new ArrayList<String[]>();
	}
	
	public String getProjectApplicationId() {
		return projectApplicationId;
	}
	public void setProjectApplicationId(String projectApplicationId) {
		this.projectApplicationId = projectApplicationId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public List<String[]> getProjectBusiness() {
		return projectBusiness;
	}
	public void setProjectBusiness(List<String[]> projectBusiness) {
		this.projectBusiness = projectBusiness;
	}
}