package csdc.action.project.key;


/**
 * 社科项目结项申请审核管理
 * @author 肖雅
 */
public class EndinspectionApplyAuditAction extends csdc.action.project.EndinspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	
	private static final String BUSINESS_TYPE = "043";//重大攻关项目结项业务编号
	private static final String PROJECT_TYPE = "key";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	
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
