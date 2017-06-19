package csdc.tool.info;

/**
 * 账号模块常量定义
 * @author 龚凡	2010-09-16
 */
public class AccountInfo {

	public static final String ERROR_MODIFY_NULL = "请选择要修改的账号";
	public static final String ERROR_MODIFYP_NULL = "请选择要修改的通行证";
	public static final String ERROR_MODIFY_DENY = "您无权修改此账号";
	public static final String ERROR_VIEW_NULL = "请选择要查看的账号";
	public static final String ERROR_PASSPORT_NULL = "请选择要查看的通行证";
	public static final String ERROR_VIEW_DENY = "您无权查看此账号";
	public static final String ERROR_DELETE_NULL = "请选择要删除的账号";
	public static final String ERROR_DELETE_DENY = "您无权删除此账号";
	public static final String ERROR_ENABLE_NULL = "请选择要启用的账号";
	public static final String ERROR_ENABLE_DENY = "您无权启用此账号";
	public static final String ERROR_DISABLE_NULL = "请选择要停用的账号";
	public static final String ERROR_DISABLE_DENY = "您无权停用此账号";
	public static final String ERROR_DISTRIC_NULL = "请选择要进行角色分配的账号";
	public static final String ERROR_DISTRIC_DENY = "您无权给此账号分配角色";
	public static final String ERROR_DISTRIC_ILLEGAL = "存在不合法的权限";
	public static final String ERROR_RETRIEVE_NULL = "请选择要重置密码的账号";
	public static final String ERROR_RETRIEVE_DENY = "您无权重置此账号密码";
	public static final String ERROR_ROLL_NULL = "请选择角色";
	public static final String ERROR_ACCOUNT_NOT_CONSISTENT = "欲分配角色的账号类型必须一致";
	public static final String ERROR_MODIFY_PASSWORD_SUCCESS = "修改密码成功";

	public static final String ERROR_ACCOUNT_EXIST = "您选择的对象已有账号";
	public static final String ERROR_ACCOUNT_NULL = "该账号已不存在";
	public static final String ERROR_NAME_EXIST = "该账号名已存在";
	public static final String ERROR_NAME_NULL = "账号名不得为空";
	public static final String ERROR_NAME_ILLEGAL = "账号长度3到40位";
	public static final String ERROR_PASSWORD_NULL = "密码不得为空";
	public static final String ERROR_PASSWORD_ILLEGAL = "密码长度限制为3-20位";
	public static final String ERROR_NEW_PASSWORD_NULL = "新密码不得为空";
	public static final String ERROR_REPASSWORD_NULL = "重复密码不得为空";
	public static final String ERROR_REPASSWORD_ILLEGAL = "两次输入的密码不一致";
	public static final String ERROR_STATUS_ILLEGAL = "非法的账号状态";
	public static final String ERROR_EXPIREDATE_NULL = "有效期不得为空";
	public static final String ERROR_EXPIREDATE_ILLEGAL = "有效期不得早于当前日期";
	public static final String ERROR_BELONG_NULL = "待创建账号的对象不得为空";
	public static final String ERROR_BELONG_ILLEGAL = "待创建账号的机构或人员已不存在或不在管理范围";
	public static final String ERROR_IP_ILLEGAL = "您输入的ip不合法";
	
}
