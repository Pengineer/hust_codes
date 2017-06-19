package csdc.action.project.entrust;


/**
 * 社科项目结项申请审核管理
 * @author 肖雅
 */
public class EndinspectionApplyAuditAction extends csdc.action.project.EndinspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	
	private static final String BUSINESS_TYPE = "053";//委托应急课题结项业务编号
	private static final String PROJECT_TYPE = "entrust";
	private static final int CHECK_APPLICATION_FLAG = 27;
	private static final int CHECK_GRANTED_FLAG = 28;
	
	public String projectType(){
		return EndinspectionApplyAuditAction.PROJECT_TYPE;
	}
	public String businessType(){
		return EndinspectionApplyAuditAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return EndinspectionApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return EndinspectionApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}
