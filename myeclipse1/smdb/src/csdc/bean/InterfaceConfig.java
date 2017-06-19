package csdc.bean;

public class InterfaceConfig {

	private String id;//管理平台权限表id
	private	String serviceName;//服务名
	private String methodName;//方法名
	private int finalAuditResultPublish;//最终审核结果发布[0：否；1：是]
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
	public int getFinalAuditResultPublish() {
		return finalAuditResultPublish;
	}
	public void setFinalAuditResultPublish(int finalAuditResultPublish) {
		this.finalAuditResultPublish = finalAuditResultPublish;
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
