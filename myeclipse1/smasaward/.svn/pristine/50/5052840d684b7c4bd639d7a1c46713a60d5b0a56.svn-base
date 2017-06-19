package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import csdc.dao.IHibernateBaseDao;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.SynchronizationInfo;
import csdc.tool.execution.Execution;

public abstract class SynchronizationProjectData extends Execution {

	protected int sessionRecords = 0;//处理的条目，基于1开始,清空后变为0
	protected final int SESSION_RECORDS_MAX = 20000;//每写入多少条目数，就清一下dao

	protected IHibernateBaseDao dao;

	//////////////////////////////////////////////////////////////////////

	/**
	 * 调用检查是否根据条件flush&clear dao
	 * true则强制flush&clear
	 * false则不强制处理：
	 * 若添加条数达到SESSION_RECORDS_MAX，则flush并清空session
	 * @param suppressClear 为true则强制flush&clear
	 */
	protected void clearDao( boolean suppressClear) {
		
		if ((++sessionRecords > SESSION_RECORDS_MAX && !suppressClear) || suppressClear) {
			dao.flush();
			dao.clear();
			sessionRecords = 0;
			System.out.println("sessionRecords: " + sessionRecords);
		}
	}
	
	/**
	 * 获取elementRoot中的子节点信息
	 * @param elementRoot
	 * @param tag
	 * @return
	 */
	protected String getArgumentByTag(Element elementRoot, String tag) {
		 String context = null;
		 context = elementRoot.elementText(tag);
		 if (context != null) {
			return context;
		}
		return "null";
	}
	
	/**
	 * 根据乱七八糟的日期字符串分析出正确日期
	 * @param rawDate
	 * @return
	 */
	protected Date _date(String rawDate){
		if (rawDate == null) {
			return null;
		}
		rawDate = StringTool.toDBC(rawDate).trim();
		if (rawDate.isEmpty()) {
			return null;
		}
		if (rawDate.matches("(19|20)\\d{4}") || rawDate.matches("20\\d{4}")) {
			Integer year = Integer.parseInt(rawDate) / 100;
			Integer month = Integer.parseInt(rawDate) % 100;
			if (month >= 1 && month <= 12) {
				return new Date(year - 1900, month - 1, 1);
			}
		}
		if (rawDate.matches("-?\\d{5,6}")) {
			//Excel中的日期在常规格式下的值(1900-1-1过去的天数 + 2)
			return new Date(Long.parseLong(rawDate) * 86400000L - 2209161600000L);
		} else if (rawDate.matches("-?\\d{7,}")) {
			//1970-1-1 00:00:00过去的毫秒数
			return new Date(Long.parseLong(rawDate));
		}
		String tmp[] = rawDate.replaceAll("\\D+", " ").trim().split("\\s+");
		if (tmp.length == 0 || tmp[0].isEmpty()) {
			return null;
		}
		Integer mid;
		Integer year = Integer.parseInt(tmp[0]);
		Integer month = tmp.length > 1 ? Integer.parseInt(tmp[1]) : 1;
		Integer date = tmp.length > 2 ? Integer.parseInt(tmp[2]) : 1;
		if (month > 31) {
			mid = month; month = year; year = mid;
		} else if (date > 31) {
			mid = date; date = year; year = mid;
		}
		if (month > 12) {
			mid = date;	date = month; month = mid;
		}
		if (year < 10) {
			year += 2000;
		} else if (year < 100) {
			year += 1900;
		}
		return new Date(year - 1900, month - 1, date);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 打印中的结果并清空
	 * @param badIdsList
	 */
	protected void printRecordedListContent(List<String> badIdsList) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String fileName = "F:\\Poj_TEMP\\" + timeStamp + "_badIdsList.txt";
		if (!badIdsList.isEmpty()) {
			//打印id
			StringBuffer badidStringBuffer = new StringBuffer();
			for (int i = 0; i < badIdsList.size(); i++) {
				badidStringBuffer.append(badIdsList.get(i) );
				if (i != badIdsList.size() - 1) {
					badidStringBuffer.append(";");//英文间隔字符
				}
			}
			FileTool.saveAsFile(badidStringBuffer.toString(), fileName);
			System.out.println(badidStringBuffer.toString());//打印错误的id
			//清空
			badIdsList.clear();
		} else {
			FileTool.saveAsFile("*****没有入库异常数据记录信息*****", fileName);
			System.out.println("*****没有入库异常数据记录信息*****");
		}
	}
	
	/**
	 * 传递控制命令
	 * @param controlOrder 0：默认；1:执行(继续)；2：暂停；3：取消；
	 * 目前系统设计以上接受三种控制命令
	 * @return
	 */
	public abstract SynchronizationInfo updateCurrentSynchronizationInfo(Integer controlOrder);
	
}
