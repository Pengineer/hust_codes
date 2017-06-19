package csdc.action.project.special;


/**
 * 专项任务项目变更审核管理
 * 实现了父类所有的抽象方法及专项任务项目变更申请审核特有方法
 * @author 余潜玉
 */
public class VariationApplyAuditAction extends csdc.action.project.VariationApplyAuditAction {

	private static final long serialVersionUID = 6501329881611241590L;
	private static final String BUSINESS_TYPE = "014";//专项任务项目变更业务编号
	private static final String PROJECT_TYPE = "special";//专项任务项目类别号
	private static final int CHECK_APPLICATION_FLAG = 29;//专项任务项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 30;//专项任务项目立项是否在管辖范围内的标志位
	
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