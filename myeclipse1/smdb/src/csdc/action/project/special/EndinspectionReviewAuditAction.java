package csdc.action.project.special;


/**
 * 社科项目结项申请评审审核管理
 * @author wangming
 */
public class EndinspectionReviewAuditAction extends csdc.action.project.EndinspectionReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String ENDINSPECTION_CLASS_NAME = "SpecialEndinspection";
	private static final String PROJECT_TYPE = "special";
	private static final String BUSINESS_TYPE = "013";
	private static final int CHECK_APPLICATION_FLAG = 29;
	private static final int CHECK_GRANTED_FLAG = 30;
	
	public String[] column() {
		return null;
	}
	public String pageName() {
		return null;
	}
	public String simpleSearch() {
		return null;
	}
	public String endinspectionClassName(){
		return EndinspectionReviewAuditAction.ENDINSPECTION_CLASS_NAME;
	}
	public String projectType(){
		return EndinspectionReviewAuditAction.PROJECT_TYPE;
	}
	public String businessType(){
		return EndinspectionReviewAuditAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return EndinspectionReviewAuditAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return EndinspectionReviewAuditAction.CHECK_GRANTED_FLAG;
	}
}
