package csdc.action.project.entrust;

/**
 * 委托应急课题申请审核
 * @author 肖雅
 */
public class ApplicationApplyAuditAction extends csdc.action.project.ApplicationApplyAuditAction {

	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "051";//委托应急课题申请业务编号
	private static final String PROJECT_TYPE = "entrust";
	private static final int CHECK_APPLICATION_FLAG = 27;
	private static final int CHECK_GRANTED_FLAG = 28;

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
