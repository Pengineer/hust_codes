package csdc.action.project.special;

/**
 * 专项任务项目年检申请审核管理
 * @author wangming
 */
public class AnninspectionApplyAuditAction extends csdc.action.project.AnninspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "015";//专项任务项目中检业务编号
	private static final String PROJECT_TYPE = "special";
	private static final int CHECK_APPLICATION_FLAG = 29;
	private static final int CHECK_GRANTED_FLAG = 30;

	@Override
	public String projectType(){
		return AnninspectionApplyAuditAction.PROJECT_TYPE;
	}
	@Override
	public String businessType(){
		return AnninspectionApplyAuditAction.BUSINESS_TYPE;
	}
	@Override
	public int checkApplicationFlag(){
		return AnninspectionApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	@Override
	public int checkGrantedFlag(){
		return AnninspectionApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}
