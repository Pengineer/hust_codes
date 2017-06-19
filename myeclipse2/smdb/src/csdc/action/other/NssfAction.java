package csdc.action.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.BaseAction;
import csdc.service.imp.NssfService;

public class NssfAction extends BaseAction {

	private static final long serialVersionUID = 6459717508852974162L;

	@Autowired
	private NssfService nssfService;

	private final static String HQL = 
			"select " +
			"	nssf.name, " +
			"	nssf.number, " +
			"	nssf.type, " +
			"	nssf.disciplineType, " +
			"	nssf.startDate, " +
			"	nssf.applicant, " +
			"	nssf.specialityTitle, " +
			"	nssf.unit, " +
			"	nssf.unitType, " +
			"	nssf.province, " +
			"	nssf.belongSystem, " +
			"	nssf.productName, " +
			"	nssf.productType, " +
			"	nssf.productLevel, " +
			"	nssf.endDate, " +
			"	nssf.certificate, " +
			"	nssf.press, " +
			"	nssf.publishDate, " +
			"	nssf.author, " +
			"	nssf.prizeObtained, " +
			"	nssf.singleSubject, " +
			"	nssf.topic, " +
			"	nssf.subject, " +
			"	nssf.description, " +
			"	nssf.report, " +
			"	nssf.experts, " +
			"	nssf.noIdentifyReason, " +
			"	nssf.planEndDate, " +
			"	nssf.status " +
			"from " +
			"	Nssf nssf " +
			"where 1=1 ";

	private final static String[] COLUMN = new String[]{
		"nssf.name",
		"nssf.number",
		"nssf.type",
		"nssf.disciplineType",
		"nssf.startDate",
		"nssf.applicant",
		"nssf.specialityTitle",
		"nssf.unit",
		"nssf.unitType",
		"nssf.province",
		"nssf.belongSystem",
		"nssf.productName",
		"nssf.productType",
		"nssf.productLevel",
		"nssf.endDate",
		"nssf.certificate",
		"nssf.press",
		"nssf.publishDate",
		"nssf.author",
		"nssf.prizeObtained",
		"nssf.singleSubject",
		"nssf.topic",
		"nssf.subject",
		"nssf.description",
		"nssf.report",
		"nssf.experts",
		"nssf.noIdentifyReason",
		"nssf.planEndDate",
		"nssf.status"
	};// 排序列

	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(nssf.name) like :keyword",
		"LOWER(nssf.number) like :keyword",
		"LOWER(nssf.type) like :keyword",
		"LOWER(nssf.disciplineType) like :keyword",
		"LOWER(to_char(nssf.startDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.applicant) like :keyword",
		"LOWER(nssf.specialityTitle) like :keyword",
		"LOWER(nssf.unit) like :keyword",
		"LOWER(nssf.unitType) like :keyword",
		"LOWER(nssf.province) like :keyword",
		"LOWER(nssf.belongSystem) like :keyword",
		"LOWER(nssf.productName) like :keyword",
		"LOWER(nssf.productType) like :keyword",
		"LOWER(nssf.productLevel) like :keyword",
		"LOWER(to_char(nssf.endDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.certificate) like :keyword",
		"LOWER(nssf.press) like :keyword",
		"LOWER(to_char(nssf.publishDate, 'yyyy-MM-dd')) like :keyword",
		"LOWER(nssf.author) like :keyword",
		"LOWER(nssf.prizeObtained) like :keyword",
		"LOWER(nssf.singleSubject) like :keyword",
		"LOWER(nssf.topic) like :keyword",
		"LOWER(nssf.subject) like :keyword",
		"LOWER(nssf.description) like :keyword",
		"LOWER(nssf.report) like :keyword",
		"LOWER(nssf.experts) like :keyword",
		"LOWER(nssf.noIdentifyReason) like :keyword",
		"LOWER(to_char(nssf.planEndDate, 'yyyy-MM-dd')) like :keyword"
	};

	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式

	@Override
	public String pageName() {
		return "nssfPage";
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
		long finishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is not null ");
		long unfinishedCount = dao.count("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is null ");
		long runningCount = nssfService.getRunningTasks().size();
		Date lastUpdateDate = (Date) dao.queryUnique("select max(task.finishTime) from CrawlTask task where task.taskType = 'nssf' ");
		if (lastUpdateDate == null) {
			lastUpdateDate = new Date(0);
		}
		jsonMap.put("finishedCount", finishedCount);
		jsonMap.put("unfinishedCount", unfinishedCount);
		jsonMap.put("runningCount", runningCount);
		jsonMap.put("lastUpdateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdateDate));
		jsonMap.put("isCrawlerOutdated", nssfService.isCrawlerOutdated());
		return SUCCESS;
	}
	
	public void update() throws Throwable {
		nssfService.update();
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	@Override
	public String pageBufferId() {
		return "nssf.id";
	}
	

}
