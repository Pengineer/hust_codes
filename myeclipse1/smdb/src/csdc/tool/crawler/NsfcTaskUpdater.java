package csdc.tool.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;
import csdc.dao.HibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 国家自科基金项目数据更新器，负责任务的更新、爬取
 * @author Isun
 *
 */
@Component
@Scope("prototype")
public class NsfcTaskUpdater implements Runnable {
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	private int startYear;
	private int endYear;


	/**
	 * 更新国家自科基金项目数据
	 * 如果当前没有未完的任务，则新增一批次任务。
	 * 然后启动所有未完成的任务。
	 */
	public void run() {
		long undoneTasksNumber = dao.count("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is null ");
		if (undoneTasksNumber == 0) {
			try {
				addTasks();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		List<CrawlTask> tasks = dao.query("select task from CrawlTask task where task.taskType = 'nsfc' and task.finishTime is null ");
		for (CrawlTask crawlTask : tasks) {
			crawlerExecutor.addTask(crawlTask);
		}
	}

	/**
	 * 添加新任务
	 * @throws Throwable
	 */
	public void addTasks() throws Throwable {
//		List<String> units = addUnits();
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");
		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list");
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} finally {
			EntityUtils.consume(entity);
		}
		
		List<String> years = new ArrayList<String>();
		while(true) {
			if (startYear <= endYear) {
				years.add(startYear + "");
				startYear++;
			} else {
				break;
			}
		}
		/*years.add("1997");//所有年度爬取效率太低，时间太长，采取增量爬取。
		List<String[]> rows = StringTool.regGroupAll(html, "<option value=\"(\\d{4})\">[0-9]{4}</option>");
		for (String[] row : rows) {
			years.add(row[1]);
		}*/
		//资助类别
		List<String[]> rows = StringTool.regGroupAll(html, "<option value=\"(\\d+)\">[^0-9]+?</option>");
		for (String year : years) {
			addSingleTask(year, null, null, null);
			for (String[] row : rows) {
				JSONArray subJsonArray = getSub(row[1], "grant");
				Map subMap = new HashMap();
				for (int i = 0; i < subJsonArray.size(); i++) {
					String code = subJsonArray.getJSONObject(i).get("subGrantCode").toString();
					JSONArray descriptionJsonArray = getSub(code, "sub");
					if (descriptionJsonArray.isEmpty()) {
						addSingleTask(year, row[1], code, null);
					} else {
						for (int j = 0; j < descriptionJsonArray.size(); j++) {
							String desCode = descriptionJsonArray.getJSONObject(j).get("subGrantCode").toString();
							addSingleTask(year, row[1], code, desCode);
						}
					}
					subMap.put(code, descriptionJsonArray);
				}
				if (subMap.size() == 0) {
					addSingleTask(year, row[1], null, null);
				}
			}
		}
	}
	
	/**
	 * 尝试添加一个nsfc抓取任务
	 * @param year 年份
	 * @param grantTypeId 资助类别
	 * @param subGrantTypeId 亚类说明
	 * @param grantDescription 附注说明
	 * @param undoneTasks 还未结束的任务的参数集合（这样的任务本次不再添加）
	 */
	private void addSingleTask(String year, String grantTypeId, String subGrantTypeId, String grantDescription) {
		CrawlTask task = new CrawlTask();
		task.setTaskType("nsfc");
		task.setProgress("1");
		task.setAssignTime(new Date());

		if (grantTypeId == null) {
			grantTypeId = "";
		}
		if (subGrantTypeId == null) {
			subGrantTypeId = "";
		}
		if (grantDescription == null) {
			grantDescription = "";
		}
		Map arguments = new HashMap();
		arguments.put("year", year);
		arguments.put("grantTypeId", grantTypeId);
		arguments.put("subGrantTypeId", subGrantTypeId);
		arguments.put("grantDescription", grantDescription);
		
		task.setArguments(JSONObject.fromObject(arguments).toString());
		
		dao.add(task);
	}

	/**
	 * 获取单位名称
	 * @throws Throwable 
	 */
	public List<String> addUnits() throws Throwable {
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");
		HttpGet request = new HttpGet("/egrantindex/funcindex/get-orginfo?type=search&_search=false&nd=&rows=2988&page=1&sidx=orgId&sord=asc&searchString=");
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} finally {
			EntityUtils.consume(entity);
		}
		
		List<String> units = new ArrayList<String>();
		int count = 0;
		List<String[]> rows = StringTool.regGroupAll(html, "setOrgName\\(.+?\\)");
		for (String[] row : rows) {
			units.add((row[0].replace("setOrgName('", "")).replace("')", ""));
			if ((row[0].replace("setOrgName('", "")).replace("')", "").contains("学")) {
				count++;
			} else {
				System.out.println((row[0].replace("setOrgName('", "")).replace("')", ""));
			}
		}
		System.out.println(count);
		return units;
	}
	
	/**
	 * 根据资助类别获取亚类说明或者根据亚类说明获取附注说明
	 * @param id 父节点id
	 * @param type 说明类别
	 * @throws Throwable 
	 */
	public static JSONArray getSub(String id, String type) throws Throwable {
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost target = new HttpHost("isisn.nsfc.gov.cn", 80, "http");
		HttpGet request = new HttpGet("/egrantindex/funcindex/get-allSubGrant?grantCode=" + id + "&type=" + type);
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
		} finally {
			EntityUtils.consume(entity);
		}
		JSONArray subJsonArray = JSONArray.fromObject(html);
		return subJsonArray;
	}
	
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	
}
