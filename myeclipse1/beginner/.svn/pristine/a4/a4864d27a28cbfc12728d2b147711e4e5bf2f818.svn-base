package csdc.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期常用处理函数的公共类
 * @author 雷达
 *
 */
public class DatetimeTool {
	/**
	 * 
	 * @return 当前时间年月的字符串，eg 2010年1月 => 201001 
	 */
	public static String getYearMonthString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		Date nowDate = new Date();
		return df.format(nowDate);
	}
	
	/**
	 * 
	 * @return 当前时间年月日的字符串，eg 2010年1月1日 => 2010-1-1 
	 */
	public static String getYearMonthDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	/**
	 * 按照yyyy-mm-dd格式从字符串获取日期
	 * @param dt 源字符串
	 * @return 解析后的日期
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(String dt) {
		String num[] = dt.split("\\D");
		int year = 0, month = 0, day = 0;
		for (String st : num) {
			if (!st.isEmpty()){
				int tmp = Integer.parseInt(st);
				if (year == 0){
					year = tmp;
				} else if (month == 0){
					month = tmp;
				} else {
					day = tmp;
				}
			}
		}
		if (year >= 0 && year < 20)
			year += 2000;
		else if (year >= 20 && year <= 99)
			year += 1900;
		else if (year <= 0 || year > 2020)
			year = 1900;
		if (month <= 0 || month > 12)
			month = 1;
		if (day <= 0 || day > 31)
			day = 1;
		return new Date(year - 1900, month - 1, day);
	}
}
