package csdc.action.project.post;

import csdc.action.project.post.AnninspectionApplyAuditAction;

/**
 * 社科项目年检申请审核管理
 * @author 王懿
 */
public class AnninspectionApplyAuditAction extends csdc.action.project.AnninspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "035";//基地项目年检业务编号
	private static final String PROJECT_TYPE = "post";
	private static final int CHECK_APPLICATION_FLAG = 20;
	private static final int CHECK_GRANTED_FLAG = 23;

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
