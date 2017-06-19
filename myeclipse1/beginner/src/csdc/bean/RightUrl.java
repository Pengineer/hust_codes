package csdc.bean;

public class RightUrl {
	private String id;
	private Right right;	//权限
	private String actionurl;	//Action的URL
	private String description;	//描述
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	public String getActionurl() {
		return actionurl;
	}
	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}