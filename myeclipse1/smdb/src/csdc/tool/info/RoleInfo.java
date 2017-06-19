package csdc.tool.info;

/**
 * 角色模块常量定义
 * @author 龚凡	2010-09-16
 */
public class RoleInfo {

	public static final String ERROR_UNDEFAULT_ROLL = "000000000000000";// 非默认角色类型字符串

	public static final int[][] ERROR_DEFAULT_ACCOUNT_TYPE = {// 账号类别与角色默认分配属性值的对应关系
		//AccountType中的枚举类型下标是从0 开始的
		{1, 1},// 部级主账号
		{1, 0},// 部级子账号
		{2, 1},// 省级主账号
		{2, 0},// 省级子账号
		{3, 1},// 部属高校主账号
		{3, 0},// 部属高校子账号
		{4, 1},// 地方高校主账号
		{4, 0},// 地方高校子账号
		{5, 1},// 院系主账号
		{5, 0},// 院系子账号
		{6, 1},// 基地主账号
		{6, 0},// 基地子账号
		{7, 1},// 外部专家账号
		{8, 1},// 教师账号
		{9, 1}// 学生账号
	};// 角色默认设置的账号类别

	public static final String ERROR_NAME_EXIST = "该角色名已存在";
	public static final String ERROR_NAME_NULL = "角色名称不得为空";
	public static final String ERROR_NAME_ILLEGAL = "角色名不得超过100个字符";

	public static final String ERROR_DESC_NULL = "角色描述不得为空";
	public static final String ERROR_DESC_ILLEGAL = "角色描述不得超过2000个字符";

	public static final String ERROR_ROLL_NULL = "该角色已不存在";

	public static final String ERROR_VIEW_NULL = "请选择要查看的角色";
	public static final String ERROR_MODIFY_NULL = "请选择要修改的角色";
	public static final String ERROR_DELETE_NULL = "请选择要删除的角色";

	public static final String ERROR_DEFAULT_ACCOUNT_TYPE_ERROR = "默认所属账号类别信息错误";
	public static final String ERROR_DEFAULT_AGENCY_ID_ERROR = "默认所属机构信息错误";
	public static final String ERROR_DEFAULT_APPLYTO_ERROR = "默认机构角色应用账号类型错误";

}
