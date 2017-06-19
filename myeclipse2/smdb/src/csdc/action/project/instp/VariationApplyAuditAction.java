package csdc.action.project.instp;

/**
 * 项目变更审核管理
 * @author 肖雅
 */
public class VariationApplyAuditAction extends csdc.action.project.VariationApplyAuditAction {

	private static final long serialVersionUID = 6501329881611241590L;
	private static final String BUSINESS_TYPE = "024";//基地项目变更业务编号
	private static final String PROJECT_TYPE = "instp";
	private static final int CHECK_APPLICATION_FLAG = 19;
	private static final int CHECK_GRANTED_FLAG = 22;
	
	public String projectType(){
		return VariationApplyAuditAction.PROJECT_TYPE;
	}
	public String businessType(){
		return VariationApplyAuditAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return VariationApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return VariationApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}