package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

/**
 * 项目立项—成果类
 * @author Administrator
 *
 */
public class ProjectProduct {
	
	private String id;//主键id
	private Product product;//成果对象
	private ProjectGranted projectGranted;//项目立项对象
	private int isMarkMoeSupport;//是否标注教育部社科项目资助
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@JSON(serialize=false)
	public ProjectGranted getProjectGranted() {
		return projectGranted;
	}
	public void setProjectGranted(ProjectGranted projectGranted) {
		this.projectGranted = projectGranted;
	}
	public int getIsMarkMoeSupport() {
		return isMarkMoeSupport;
	}
	public void setIsMarkMoeSupport(int isMarkMoeSupport) {
		this.isMarkMoeSupport = isMarkMoeSupport;
	}

}
