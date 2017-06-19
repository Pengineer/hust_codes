package csdc.tool.crawler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;
import csdc.dao.HibernateBaseDao;

/**
 * 附件下载的任务更新器
 * @author suwb
 *
 */
@Component
@Scope("prototype")
public class AttachmentUpdater implements Runnable{
	
	@Autowired
	private HibernateBaseDao dao;
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	private List<String[]> list;
	
	private String attachmentType;//项目类型
	private String methodType;//数据类型

	/**
	 * 更新下载任务
	 * 如果当前没有未完的任务，则新增一批次任务（所有任务要一次性全部读到CrawlTask表中，暂时无法分批次读取然后循环执行）:注意每年修改where条件中的年度。
	 * 然后启动所有未完成的任务。
	 */
	public void run() {
		Map jsonMap = new HashMap();
		jsonMap.put("attachmentType", getAttachmentType());
		long undoneTasksNumber = dao.count("select task from CrawlTask task where task.taskType =:attachmentType and task.finishTime is null ", jsonMap);
		if (undoneTasksNumber == 0) {
			try {
				addTasks();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		List<CrawlTask> tasks = dao.query("select task from CrawlTask task where task.taskType =:attachmentType and task.finishTime is null ", jsonMap);
		for (CrawlTask crawlTask : tasks) {
			crawlerExecutor.addTask(crawlTask);	
		}
	}

	/**
	 * 查询所有代办的下载任务
	 */
	public void listInit(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		Map jsonMap = new HashMap();
		jsonMap.put("year", year);
		jsonMap.put("attachmentType", getAttachmentType());
		list = dao.query("select spa.batchId, spa.applyDocName from SinossProjectApplication spa where spa.year =:year and spa.typeCode =:attachmentType", jsonMap);
	}
	
	/**
	 * 添加新任务
	 * @throws Throwable
	 */
	public void addTasks() throws Throwable {
		if(list == null){
			listInit();
		}
		Map jsonMap = new HashMap();
		jsonMap.put("attachmentType", getAttachmentType());
		List<String> doneList = dao.query("select task.arguments from CrawlTask task where task.taskType =:attachmentType", jsonMap);
		for(int k=0; k<list.size(); k++){
			Object[] current = list.get(k);
			Map arguments = new HashMap();
			String batchId = current[0].toString();
			String applyDocName = current[1].toString();
			if (batchId == null) {
				batchId = "";
			}
			if (applyDocName == null) {
				applyDocName = "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String year = sdf.format(new Date());
			arguments.put("year", year);
			arguments.put("batchId", batchId);
			arguments.put("applyDocName", applyDocName);
			String argument = JSONObject.fromObject(arguments).toString();
			
			if(!doneList.toString().contains(argument)){
				addSingleTask(argument);
			}
		}
	}
	
	/**
	 * 添加一个新任务
	 * @param argument
	 */
	private void addSingleTask(String argument) {
		CrawlTask task = new CrawlTask();
		task.setTaskType(getAttachmentType());
		task.setProgress("1");
		task.setAssignTime(new Date());
		task.setArguments(argument);
		
		dao.add(task);
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

}
