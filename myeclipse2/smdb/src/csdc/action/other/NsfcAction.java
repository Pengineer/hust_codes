package csdc.action.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.service.imp.NsfcService;

public class NsfcAction extends BaseAction {

	private static final long serialVersionUID = -6710507495994246679L;
	
	@Autowired
	private NsfcService nsfcService;

	private final static String HQL =
			"select " +
			"	nsfc.id, " +
			"	nsfc.name, " +
			"	nsfc.year, " +
			"	nsfc.number, " +
			"	nsfc.applyNumber, " +
			"	nsfc.applicant, " +
			"	nsfc.unit, " +
			"	nsfc.approvedFee, " +
			"	nsfc.startDate, " +
			"	nsfc.endDate, " +
			"	nsfc.grantType, " +
			"	nsfc.subGrantType, " +
			"	nsfc.grantDescription " +
			"from " +
			"	Nsfc nsfc " +
			"where 1=1 ";

	private final static String[] COLUMN = new String[]{
		"nsfc.name",
		"nsfc.year",
		"nsfc.number",
		"nsfc.applyNumber",
		"nsfc.applicant",
		"nsfc.unit",
		"nsfc.approvedFee",
		"nsfc.startDate",
		"nsfc.endDate",
		"nsfc.grantType",
		"nsfc.subGrantType",
		"nsfc.grantDescription"
	};// 排序列

	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(nsfc.name) like :keyword",
		"nsfc.year = :year",
		"LOWER(nsfc.number) like :keyword",
		"LOWER(nsfc.applyNumber) like :keyword",
		"LOWER(nsfc.applicant) like :keyword",
		"LOWER(nsfc.unit) like :keyword",
		"LOWER(nsfc.grantType) like :keyword",
		"LOWER(nsfc.subGrantType) like :keyword",
		"LOWER(nsfc.grantDescription) like :keyword"
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式

	@Override
	public String pageName() {
		return "nsfcPage";
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}

	@Override
	public Object[] simpleSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
		
		if (keyword != null && !keyword.trim().isEmpty()){
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			String[] sc = SEARCH_CONDITIONS;
			for (int i = 0; !flag && i < sc.length; i++) {
				if (searchType == i){
					hql.append(" and ").append(sc[i]);
					flag = true;
				}
				tmp.append(sc[i]).append(i < sc.length - 1 ? " or " : ") ");
			}
			if (!flag){
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
			map.put("year", keyword != null && keyword.matches("\\d{4}")? Integer.valueOf(keyword) : 0);
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	public String toViewTask() {
		return SUCCESS;
	}
	
	public String viewTask() {
		long finishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is not null ");
		long unfinishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is null ");
		long runningCount = nsfcService.getRunningTasks().size();
		Date lastUpdateDate = (Date) dao.queryUnique("select max(task.finishTime) from CrawlTask task where task.taskType = 'nsfc' ");
		if (lastUpdateDate == null) {
			lastUpdateDate = new Date(0);
		}
		jsonMap.put("finishedCount", finishedCount);
		jsonMap.put("unfinishedCount", unfinishedCount);
		jsonMap.put("runningCount", runningCount);
		jsonMap.put("lastUpdateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdateDate));
		jsonMap.put("isCrawlerOutdated", nsfcService.isCrawlerOutdated());
		return SUCCESS;
	}
	
	public String update() throws Throwable {
		nsfcService.update();
		return SUCCESS;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return "nsfc.id";
	}
	

}
