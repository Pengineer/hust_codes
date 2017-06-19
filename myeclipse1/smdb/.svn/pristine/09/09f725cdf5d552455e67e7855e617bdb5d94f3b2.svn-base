package csdc.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期常用处理函数的公共类
 * @author 雷达
 * @autoor 龚凡	2010.12.09	更新时间字符串获取方法，让调用者可指定时间格式
 */
public class DatetimeTool {

	/**
	 * 获取系统当前时间，并按指定格式转换为字符串
	 * @formatString 格式化字符串
	 * @return 返回时间字符串
	 */
	public static String getDatetimeString(String formatString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(formatString);
			Date date = new Date();
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}
}