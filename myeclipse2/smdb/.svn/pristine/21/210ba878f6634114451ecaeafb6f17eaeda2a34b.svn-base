package csdc.tool.crawler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 国家社科基金项目数据更新器，负责任务的更新、爬取
 * @author Isun
 *
 */
@Component
@Scope("prototype")
public class NssfTaskUpdater implements Runnable {
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;

	/**
	 * 更新国家自科基金项目数据
	 * 如果当前没有未完的任务，则新增一批次任务。
	 * 然后启动所有未完成的任务。
	 */
	public void run() {
		long undoneTasksNumber = dao.count("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is null  ");
		if (undoneTasksNumber == 0) {
			try {
				addTasks();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		List<CrawlTask> tasks = dao.query("select task from CrawlTask task where task.taskType = 'nssf' and task.finishTime is null  ");
		for (CrawlTask crawlTask : tasks) {
			crawlerExecutor.addTask(crawlTask);
		}
	}

	/**
	 * 添加新任务
	 * @throws Throwable
	 */
	public void addTasks() throws Throwable {
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost target = new HttpHost("gp.people.com.cn", 80, "http");
		HttpGet request = new HttpGet("/yangshuo/skygb/sk/index.php");
		
		String html = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(target, request);
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8);
		} finally {
			EntityUtils.consume(entity);
		}
		
		/*
		 * 按页切割任务。每个任务页数为max(10, 总页数/100)。
		 * 每个任务负责的页码为闭区间[startPage, endPage]。
		 */
		Integer totalPages = Integer.parseInt(StringTool.regFind(html, "\\d+/(\\d+) 页"));
		Integer pagePerTask = Math.max(10, totalPages / 100);
		Integer startPage = 1;
		while (startPage <= totalPages) {
			CrawlTask task = new CrawlTask();
			task.setTaskType("nssf");
			task.setProgress(startPage.toString());
			task.setAssignTime(new Date());
			
			Map arguments = new HashMap();
			arguments.put("startPage", startPage);
			arguments.put("endPage", Math.min(startPage + pagePerTask - 1, totalPages));
			task.setArguments(JSONObject.fromObject(arguments).toString());
			
			startPage += pagePerTask;
			
			dao.add(task);
		}
	}
	
}
