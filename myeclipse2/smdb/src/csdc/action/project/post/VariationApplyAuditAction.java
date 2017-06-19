package csdc.action.project.post;

/**
 * 项目变更审核管理
 * @author 王燕
 */
public class VariationApplyAuditAction extends csdc.action.project.VariationApplyAuditAction {

	private static final long serialVersionUID = 6501329881611241590L;
	private static final String BUSINESS_TYPE = "034";//后期资助项目变更业务编号
	private static final String PROJECT_TYPE = "post";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;
	
	public String businessType(){
		return VariationApplyAuditAction.BUSINESS_TYPE;
	}
	public String projectType(){
		return VariationApplyAuditAction.PROJECT_TYPE;
	}
	public int checkApplicationFlag(){
		return VariationApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return VariationApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}
