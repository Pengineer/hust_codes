package csdc.action.project.special;


/**
 * 专项任务项目结项申请审核管理
 * 实现了父类所有的抽象方法及专项任务项目结项申请审核特有方法
 * @author wangming
 */
public class EndinspectionApplyAuditAction extends csdc.action.project.EndinspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	
	private static final String BUSINESS_TYPE = "013";//专项任务项目结项申请审核对应的业务类型名称
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final int CHECK_APPLICATION_FLAG = 29;//专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//专项任务项目立项是否在管辖范围内的标志位
	
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
