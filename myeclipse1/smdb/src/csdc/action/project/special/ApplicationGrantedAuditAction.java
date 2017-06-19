package csdc.action.project.special;

public class ApplicationGrantedAuditAction extends csdc.action.project.ApplicationGrantedAuditAction{
	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "016";//专项任务项目立项审核对应的业务类型名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final int CHECK_APPLICATION_FLAG = 29;//判断专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//判断专项任务项目立项是否在管辖范围内的标志位

	@Override
	public String projectType() {
		return ApplicationGrantedAuditAction.PROJECT_TYPE;
	}

	@Override
	public int checkApplicationFlag() {
		return ApplicationGrantedAuditAction.CHECK_APPLICATION_FLAG;
	}

	@Override
	public int checkGrantedFlag() {
		return ApplicationGrantedAuditAction.CHECK_GRANTED_FLAG;
	}

	@Override
	public String businessType() {
		return ApplicationGrantedAuditAction.BUSINESS_TYPE;
	}

}
