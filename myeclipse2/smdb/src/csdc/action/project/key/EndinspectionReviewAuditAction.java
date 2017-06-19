package csdc.action.project.key;


/**
 * 社科项目结项申请评审审核管理
 * @author 肖雅
 */
public class EndinspectionReviewAuditAction extends csdc.action.project.EndinspectionReviewAuditAction {
	
	private static final long serialVersionUID = 1L;
	private static final String ENDINSPECTION_CLASS_NAME = "KeyEndinspection";
	private static final String PROJECT_TYPE = "key";
	private static final String BUSINESS_TYPE = "043";
	private static final int CHECK_APPLICATION_FLAG = 24;
	private static final int CHECK_GRANTED_FLAG = 25;
	
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
