package csdc.action.project.general;

/**
 * 社科项目中检申请审核管理
 * 实现了父类所有的抽象方法及一般项目中检审核特有方法
 * @author 余潜玉
 */
public class MidinspectionApplyAuditAction extends csdc.action.project.MidinspectionApplyAuditAction {

	private static final long serialVersionUID = 1L;
	private static final String BUSINESS_TYPE = "012";//一般项目中检审核对应的业务类型名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//判断一般项目立项是否在管辖范围内的标志位

	@Override
	public String projectType(){
		return MidinspectionApplyAuditAction.PROJECT_TYPE;
	}
	@Override
	public String businessType(){
		return MidinspectionApplyAuditAction.BUSINESS_TYPE;
	}
	@Override
	public int checkApplicationFlag(){
		return MidinspectionApplyAuditAction.CHECK_APPLICATION_FLAG;
	}
	@Override
	public int checkGrantedFlag(){
		return MidinspectionApplyAuditAction.CHECK_GRANTED_FLAG;
	}
}
