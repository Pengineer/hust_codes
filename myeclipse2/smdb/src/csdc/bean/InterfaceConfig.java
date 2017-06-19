package csdc.bean;

public class InterfaceConfig {

	private String id;//管理平台权限表id
	private	String serviceName;//服务名
	private String methodName;//方法名
	private int isPublished;//是否开放[0:不开放；1:开放]
	private String options;//配置
	private Right right;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(int isPublished) {
		this.isPublished = isPublished;
	}

	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	
	
}
