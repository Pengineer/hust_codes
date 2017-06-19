package csdc.tool.info;

/**
 * 公共常量定义
 * @author 龚凡	2010-09-15
 */
public class GlobalInfo {

	// 全局异常提示信息
	public static final String ERROR_EXCEPTION_INFO = "有非法数据产生，请求终止！";

	// 排序列异常提示信息
	public static final String ERROR_SORT_COLUMN_INFO = "无效的排序列";

	// 检索关键字异常提示信息
	public static final String ERROR_KEYWORD_INFO = "检索关键字太长";

	// 页面大小异常提示信息
	public static final String ERROR_PAGE_SIZE_INFO = "每页显示数太大";

	// 上一条异常提示信息
	public static final String ERROR_PREV_INFO = "已到达第一条记录";

	// 下一条异常提示信息
	public static final String ERROR_NEXT_INFO = "已到达最后一条记录";

	// 查看记录为空提示信息
	public static final String ERROR_VIEW_NULL = "您查看的记录已不存在";

	// ajax错误提示变量名
	public static final String ERROR_INFO = "errorInfo";

	// session中登入对象
	public static final String LOGINER = "loginer";
	
	// 跨级与否
	public static final boolean IS_CROSS_LEVEL = true;//应在系统配置表中配置
	
	// 当日志达到此值时触发一次数据库写操作
	public static final int LOG_NUMBER_EACH_TIME = 10;//应在系统配置表中配置
	
	public static final String ERROR_ATTACH_NULL = "该附件已不存在";
	public static final String ERROR_ATTACH_BELONG_NULL = "附件所属不得为空";
	public static final String ERROR_FILE_NOT_MATCH = "下载文件出错！";
	
	//导出数据
	public static final String EXPORT_START_TIME = "exportStartTime";
	public static final String EXPORT_END_TIME = "exportEndTime";
	public static final String ERROR_IS_EXPORT_FORBIDDEN = "exportForbidden";
	public static final String ERROR_EXPORT_NOT_VALID = "exportNotValid";
	
}
