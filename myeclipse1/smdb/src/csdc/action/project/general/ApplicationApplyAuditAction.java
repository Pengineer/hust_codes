package csdc.action.project.general;

/**
 * 一般项目申请审核
 * @author 余潜玉
 */
public class ApplicationApplyAuditAction extends csdc.action.project.ApplicationApplyAuditAction {

	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "011";//项目申请审核对应的业务类型名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//判断一般项目立项是否在管辖范围内的标志位

	public String businessType(){
		return ApplicationApplyAuditAction.BUSINESS_TYPE;
	}
	public String projectType(){
		return ApplicationApplyAuditAction.PROJECT_TYPE;
	}
	public int checkApplicationFlag(){
		return ApplicationApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return ApplicationApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}
