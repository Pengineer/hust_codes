package csdc.tool.info;

/**
 * 业务模块常量定义
 * @author 肖雅	2011-12-29
 */
public class BusinessInfo {

	public static final String ERROR_TYPE_EXIST = "该类业务已存在";
	public static final String ERROR_TYPE_NULL = "业务类型不能为空";
	public static final String ERROR_SUBTYPE_NULL = "业务子类不能为空";
	public static final String ERROR_STATUS_NULL = "业务状态不能为空";
	public static final String ERROR_STARTDATE_NULL = "业务起始时间不能为空";
	public static final String ERROR_ENDYEAR_ERROR = "业务对象截止年份不得小于起始年份";
	public static final String ERROR_YEAR_NOT_EQUAL = "业务对象起止年份不一致";
	public static final String ERROR_YEAR_CROSS = "业务对象起止年份不得与其他业务交叉";
	public static final String ERROR_BUSINESSYEAR_NULL = "业务年份不能为空";
	
	public static final String ERROR_BUSINESS_NULL = "该业务已不存在";
	
	public static final String ERROR_VIEW_NULL = "请选择要查看的业务";
	
	public static final String ERROR_MODIFY_NULL = "请选择要修改的业务";
	public static final Object ERROR_DELETE_NULL = "请选择要删除的业务";

}