package edu.hust.action;

/**依赖注入：为Action的属性注入值
 *         
 *struts2为Action中的属性提供了依赖注入功能，在struts2的配置文件中，我们可以很方便的为Action中的属性注入值。
 *
 *注意：属性必须提供setter方法。（如果希望在JSP页面中获取属性的值，应提供getter方法）
 */
public class DependencyInjectionAction {
	private String savepath;

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	
	public String execute(){
		
		return "success";	
	}
}
