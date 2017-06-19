package csdc.tool.crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.ApplicationContainer;

@Component
@Scope
public class DocumentUpdater implements Runnable {

	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	private String[] paths;//需要操作的文件夹路径
	
	List<String> filePaths = new ArrayList<String>();
	
	@Override
	public void run() {
		Map jsonMap = new HashMap();
		jsonMap.put("taskType", "upload");
		long undoneTasksNumber = dao.count("select task from CrawlTask task where task.taskType =:taskType" +
				" and task.finishTime is null ", jsonMap);
		if (undoneTasksNumber == 0) {
			try {
				addTasks();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		List<CrawlTask> tasks = dao.query("select task from CrawlTask task where task.taskType =:taskType" +
				" and task.finishTime is null ", jsonMap);
		for (CrawlTask crawlTask : tasks) {
			crawlerExecutor.addTask(crawlTask);
		}
	}

	/**
	 * 一次性添加任务，添加时判断该任务的argument是否执行过
	 */
	private void addTasks() {
		Map jsonMap = new HashMap();
		jsonMap.put("taskType", "upload");
		List<String> doneList = dao.query("select task.arguments from CrawlTask task where task.taskType =:taskType and task.finishTime is not null", jsonMap);
		for(String path : paths){
			filePaths.clear();
//			getFile(new File(ApplicationContainer.sc.getRealPath(path)));
			getFile(new File(path));
			for (String filePath : filePaths) {
				Map arguments = new HashMap();
				arguments.put("path", filePath);
				String argument = JSONObject.fromObject(arguments).toString();
				if(!doneList.toString().contains(argument)){
					addSingleTask(argument);
				}
			}
		}
		List<CrawlTask> tasks = dao.query("select task from CrawlTask task where task.taskType =:taskType" +
				" and task.finishTime is null ", jsonMap);
		for (CrawlTask crawlTask : tasks) {
			crawlerExecutor.addTask(crawlTask);
		}
	}
	
	public void getFile(File file) {// 递归获取路径下所有文件
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				getFile(subFile);
			}
		} else {
			filePaths.add(file.toString());
		}
	}
	
	public int getActiveCount() {
		return crawlerExecutor.getActiveCount();
		
	}
	
	public long getCompleteTaskCount() {
		return crawlerExecutor.getCompletedTaskCount();
	}
	
	public long getTaskCount() {
		return crawlerExecutor.getTaskCount();
	}

	private void addSingleTask(String argument) {
		CrawlTask task = new CrawlTask();
		task.setTaskType("upload");
		task.setProgress("1");
		task.setAssignTime(new Date());
		task.setArguments(argument);
		dao.add(task);
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}

	public static void main(String[] args) {
		DocumentUpdater documentUpdater = new DocumentUpdater();
		String[] tmp = {"D:\\webapp"};
		documentUpdater.setPaths(tmp);
		documentUpdater.addTasks();
	}
}
