package csdc.tool.beanutil.mergeStrategy;

import java.util.Date;

/**
 * 取较精确的那个日期<br />
 * 精确度按如下递减：<br />
 * 日非1的 > 月非1的 > 其他 > null
 * 
 * @author xuhan
 */
public class PreciseDate implements MergeStrategy<Date> {

	@SuppressWarnings("deprecation")
	public Date merge(Date date1, Date date2) {
		int preciseLevel1 = date1 == null ? 0 : date1.getDate() != 1 ? 3 : date1.getMonth() != 0 ? 2 : 1;
		int preciseLevel2 = date2 == null ? 0 : date2.getDate() != 1 ? 3 : date2.getMonth() != 0 ? 2 : 1;
		return preciseLevel1 >= preciseLevel2 ? date1 : date2;
	}

}
