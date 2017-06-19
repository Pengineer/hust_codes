package csdc.tool.info;

public class ProjectInfo {
	public static final String ERROR_PROJECT_NULL = "该项目已不存在";
	public static final String ERROR_VIEW_NULL = "请选择要查看的项目";
	public static final String ERROR_DELETE_NULL = "请选择要删除的项目";
	//项目基本操作错误提示
	public static final String ERROR_PROJECT_STOP = "该项目已中止，无法进行此操作！";
	public static final String ERROR_PROJECT_REVOKE = "该项目已撤项，无法进行此操作！";
	
	//申报相关错误提示
	public static final String ERROR_PROJECT_NAME_OUT = "项目名称最多为50个字！";
	public static final String ERROR_PROJECT_RESULT_NULL = "请选择申报结果！";
	public static final String ERROR_PROJECT_APPROVE_DATE_NULL = "批准时间不能为空！";
	public static final String ERROR_PROJECT_AUDIT_OPINION_OUT = "申报意见最多为2000个字！";
	public static final String ERROR_PROJECT_AUDIT_OPINION_FEEDBACK_OUT = "申报反馈意见最多为200个字！";
	public static final String ERROR_PROJECT_ALREADY_GRANTED = "项目已立项，请选择待审核的项目";
	
	//中检相关错误提示
	public static final String ERROR_MID_DELETE_NULL = "请选择删除的中检申请！";
	public static final String ERROR_MID_NOT_EXIST = "该中检申请不存在！";
	public static final String ERROR_MID_RESULT_NULL = "请选择中检结果！";
	public static final String ERROR_MID_SUBMIT_NULL = "请选择提交的中检申请！";
	public static final String ERROR_MID_AUDIT_SUBMIT_NULL = "请选择提交的中检审核！";
	public static final String ERROR_MID_AUDIT_BACK_NULL = "请选择退回的中检审核！";
	public static final String ERROR_MID_NOTE_OUT = "中检备注最多为200个字！";
	public static final String ERROR_MID_AUDIT_OPINION_OUT = "中检意见最多为200个字！";
	public static final String ERROR_MID_ALREADY = "您的中检申请已通过，不能再次申请！";
	public static final String ERROR_MID_DEALING = "该项目有中检申请正在处理，无法添加中检结果，请先处理完当前中检申请！";
	public static final String ERROR_MID_AUDIT_PRODUCT = "请先审核完该项目的所有中检成果！";
	public static final String ERROR_MID_DATE_NULL = "请填写中检时间！";
	public static final String ERROR_MID_OPINION_OUT = "中检意见最多为200个字！";
	public static final String ERROR_MID_FORBID = "中检已截止，不能录入数据！";
	
	//变更相关错误提示
	public static final String ERROR_VAR_NOT_GRANTED = "该项目没有立项,无法进行次操作！";
	public static final String ERROR_VAR_VIEW_NULL = "请选择相应的变更申请！";
	public static final String ERROR_VAR_AUDIT_RESULT_NULL = "请选择审核结果！";
	public static final String ERROR_VAR_DEALING = "该项目有变更申请正在处理，无法添加变更结果，请先处理完当前变更申请！";
	public static final String ERROR_VAR_AUDIT_OPINION_OUT = "变更审核意见最多为200个字！";
	public static final String ERROR_OPINION_FEEDBACK_OUT = "审核意见（反馈给项目负责人）不能超过200个字！";
	public static final String ERROR_VAR_SELECT_ISSUE_NULL = "请选择同意变更事项！";
	public static final String ERROR_END_IS_APPLY_EXCELLENT_NULL = "请选择是否申请优秀成果！";
			
	public static final String ERROR_VAR_RESULT_NULL = "请选择变更结果！";
	public static final String ERROR_VAR_DATE_NULL = "请填写变更时间！";
	public static final String ERROR_VAR_ISSUE_NULL = "变更事项不能为空！";
	public static final String ERROR_VAR_MEMBER_NULL = "变更项目负责人不能为空！";
	public static final String ERROR_VAR_DEPT_INST_NULL = "变更管理部门不能为空！";
	public static final String ERROR_VAR_DEPT_INST_SAME = "变更前后的管理部门不能一样！";
	public static final String ERROR_VAR_PRODUCT_TYPE_NULL = "变更成果形式不能为空！";
	public static final String ERROR_VAR_PRODUCT_TYPE_SAME = "变更前后的成果形式不能一样！";
	public static final String ERROR_VAR_PROJECT_NAME_SAME = "变更前后的项目名称不能一样！";
	public static final String ERROR_VAR_END_TIME_SAME = "变更前后的项目完成时间不能一样！";
	
	
	
	
	public static final String ERROR_PROJECT_PRODUCT_TYPE_OTHER_NULL = "请填写其他成果类别！";
	public static final String ERROR_PROJECT_PRODUCT_TYPE_OTHER_OUT = "其他成果类别最多为50个字！";
	public static final String ERROR_VAR_PROJECT_NAME_NULL = "变更项目名称不能为空！";
	public static final String ERROR_VAR_END_TIME_NULL = "变更时间不能为空！";
	public static final String ERROR_VAR_PROJECT_TIME_INVALIDATE = "延期时间不可小于计划时间！";
	public static final String ERROR_VAR_OTHER_NULL = "请选择变更其他的内容！";
	public static final String ERROR_VAR_OPINION_OUT = "变更意见最多为200个字！";
	
	//结项相关错误提示
	public static final String ERROR_END_PASS = "该项目已结项，不能进行此操作！";
	public static final String ERROR_END_CANNOT = "先中检且中检通过或者项目未超期才能申请结项！";
	public static final String ERROR_END_ALREADY = "您的结项申请已通过，不能再次申请！";
	public static final String ERROR_END_DEALING = "该项目有结项申请正在处理，无法进行此操作，请先处理完当前结项申请！";
	public static final String ERROR_ENDM_DEALING = "该项目有中检申请正在处理，无法进行此操作，请先处理完当前中检申请！";
	public static final String ERROR_ENDV_DEALING = "该项目有变更申请正在处理，无法进行此操作，请先处理完当前变更申请！";
	public static final String ERROR_END_IS_APPLY_NOEVALUATION_NULL = "请选择是否申请免鉴定！";
	public static final String ERROR_END_AUDIT_SUBMIT_NULL = "请选择提交的结项审核！";
	public static final String ERROR_END_AUDIT_OPINION_OUT = "结项意见最多为2000个字！";
	public static final String ERROR_END_AUDIT_OPINION_FEEDBACK_OUT = "结项反馈意见最多为200个字！";
	public static final String ERROR_END_RESULT_NULL = "请选择结项结果！";
	public static final String ERROR_END_IS_EXCELLENT_RESULT_NULL = "请选择优秀成果结果！";
	public static final String ERROR_END_IS_NOEVALUATION_RESULT_NULL = "请选择免鉴定结果！";
	public static final String ERROR_END_CERTIFICATE_NULL = "结项证书编号不能为空！";
	public static final String ERROR_END_CERTIFICATE_OUT = "结项证书编号长度不能超过40！";
	public static final String ERROR_END_NUMBER_EXIST = "项目结项证书编号已存在！";
	public static final String ERROR_END_MEMBER_OUT = "主要参加人不能超过200！";
	public static final String ERROR_END_DATE_NULL = "请填写结项时间！";
	public static final String ERROR_END_OPINION_OUT = "结项意见最多为2000个字！";
	
	
}
