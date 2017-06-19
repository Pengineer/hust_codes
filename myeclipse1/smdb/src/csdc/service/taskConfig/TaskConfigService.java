package csdc.service.taskConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Task;
import csdc.bean.TaskConfig;
import csdc.bean.TaskLog;
import csdc.bean.TaskTaskConfig;
import csdc.dao.HibernateBaseDao;
import csdc.service.imp.BaseService;
import csdc.tool.SpringBean;
import csdc.tool.listener.TaskConfigListener;

public class TaskConfigService extends BaseService {

	@Autowired
	protected HibernateBaseDao dao;

	/**
	 * 生成手动任务配置，并执行该任务配置
	 * @param taskConfigName 任务配置名称
	 * @param taskIds	任务的Ids
	 */
	public void createManualTaskConfig(String taskConfigName, List<String> taskIds) {
		Map paraMap = new HashMap();
		paraMap.put("taskIds", taskIds);
		List<Task> tasks = dao.query("select t from Task t where t.id in (:taskIds)",paraMap);
		TaskConfig taskConfig = new TaskConfig();
		taskConfig.setIsAuto(0);
		taskConfig.setName(taskConfigName);
		dao.add(taskConfig);
		int order = 1;
		for (Task task : tasks) {
			TaskTaskConfig taskTaskConfig = new TaskTaskConfig();
			taskTaskConfig.setOrder(order);
			order++;
			taskTaskConfig.setTask(task);
			taskTaskConfig.setTaskConfig(taskConfig);
			dao.add(taskTaskConfig);
		}
		executeTasks(tasks,taskConfig);
	}
	
	/**
	 * 生成自动任务配置
	 * @param taskConfigName	任务配置名称
	 * @param taskIds	任务的Ids
	 * @param executeTime	执行日期（Monday:13:00）
	 * @param interval	
	 * 备注：目前自动执行自动的时刻可选：1、固定执行日期；2、固定执行间隔 
	 * 二者只能选其一。
	 */
	public void createAutoTaskConfig(String taskConfigName, List<String> taskIds, String executeTime, int interval){
		
		Map paraMap = new HashMap();
		paraMap.put("taskIds", taskIds);
		List<Task> tasks = dao.query("select t from Task t where t.id in (:taskIds)",paraMap);
		TaskConfig taskConfig = new TaskConfig();
		taskConfig.setIsAuto(1);
		if (executeTime != null && !executeTime.equals("")) {
			taskConfig.setExecuteTime(executeTime);
		}else{
			taskConfig.setInterval(interval);
		}
		taskConfig.setName(taskConfigName);
		dao.add(taskConfig);
		int order = 1;
		for (Task task : tasks) {
			TaskTaskConfig taskTaskConfig = new TaskTaskConfig();
			taskTaskConfig.setOrder(order);
			order++;
			taskTaskConfig.setTask(task);
			taskTaskConfig.setTaskConfig(taskConfig);
			dao.add(taskTaskConfig);
		}
		
		//把自动执行的任务配置加入ScheduledExecutor中
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		TaskConfigListener.autoExecutorServicesMap.put(taskConfig.getName(), service);
		System.out.println("定时器已启动");
		AutoTaskRunnable autoTaskRunnable = new AutoTaskRunnable(taskConfig,tasks);
		if (taskConfig.getExecuteTime() != null) {//如果执行日期（Monday:13:00:00）不为空，则采用执行日期，在指定的执行日期执行该任务，时间间隔为星期。
			int taskDayOfWeek;
			int taskHour;
			int taskMinute;
			int taskSecond;
			Object[] timeObjects = taskConfig.getExecuteTime().split(":");
			if (timeObjects[0].toString().equals("Monday")) {
				taskDayOfWeek = 2;
			}else if (timeObjects[0].toString().equals("Tuesday")) {
				taskDayOfWeek = 3;
			}else if (timeObjects[0].toString().equals("Wednesday")) {
				taskDayOfWeek = 4;
			}else if (timeObjects[0].toString().equals("Thursday")) {
				taskDayOfWeek = 5;
			}else if (timeObjects[0].toString().equals("Friday")) {
				taskDayOfWeek = 6;
			}else if (timeObjects[0].toString().equals("Saturday")) {
				taskDayOfWeek = 7;
			}else {
				taskDayOfWeek = 8;
			}
			taskHour = Integer.parseInt(timeObjects[1].toString());
			taskMinute = Integer.parseInt(timeObjects[2].toString());
			taskSecond = Integer.parseInt(timeObjects[3].toString());
			
			//获取当前时间
			Calendar currentDate = Calendar.getInstance();
			long currentDateLong = currentDate.getTime().getTime();
			//计算满足条件的最近一次执行时间
			Calendar earliestDate = autoTaskRunnable.getEarliestDate(currentDate, taskDayOfWeek, taskHour, taskMinute, taskSecond);
			long earliestDateLong = earliestDate.getTime().getTime();
			//计算从当前时间到最近一次执行时间的时间间隔
			long delay = earliestDateLong - currentDateLong;
			//计算执行周期为一星期
			long period = 7 * 24 * 60 * 60 * 1000;
			//从现在开始delay毫秒之后，每隔一星期执行一次job1
			service.scheduleAtFixedRate(
					autoTaskRunnable, delay,
					period, TimeUnit.MILLISECONDS);
		}else{//如果执行日期（Monday:13:00:00）为空，则采用执行时间间隔（单位分钟），立即执行该任务，并按照设置的时间间隔，周期性的执行该任务
			service.scheduleWithFixedDelay(
					autoTaskRunnable, 0,
					taskConfig.getInterval() * 60 * 1000, TimeUnit.MILLISECONDS);
		}
	}
	
	/**
	 * 执行任务配置中的任务，并写入任务日志中
	 * @param tasks 任务列表
	 * @param taskConfig 任务配置
	 */
	public void executeTasks(List<Task> tasks,TaskConfig taskConfig){
		TaskLog taskLog = new TaskLog();
		taskLog.setBeginDate(new Date());
		taskLog.setTaskConfig(taskConfig);
		dao.add(taskLog);
		try {
			for (Task task : tasks) {
				String beanName = task.getClassName();
				ITaskService iTaskService = (ITaskService) SpringBean.getBean(beanName);
				iTaskService.executeTask(task);
			}
		} catch (Exception e) {
			taskLog.setIsSuccess(0);
			taskLog.setEndDate(new Date());
			dao.addOrModify(taskLog);
			return;
		}
		taskLog.setEndDate(new Date());
		taskLog.setIsSuccess(1);
		dao.addOrModify(taskLog);
	}
	
	//获取自动任务配置列表
	public List<TaskConfig> gainAutoTaskConfigList(){
		List<TaskConfig> taskConfigs = dao.query("select tc from TaskConfig tc where tc.isAuto = 1");
		return taskConfigs;
	}
	
	//获取当前正在执行的任务配置列表
	public List<TaskConfig> gainExecutingTaskConfigList(){
		List<TaskConfig> taskConfigs = dao.query("select tc from TaskLog tl left join tl.taskConfig tc where tl.isSuccess is null and tl.beginDate is not null and tl.endDate is null");
		return taskConfigs;
	}
 
	//获取所有的任务配置列表
	public List<TaskConfig> gainTaskConfigList(){
		List<TaskConfig> taskConfigs = dao.query("select tc from TaskConfig tc");
		return taskConfigs;
	}
	
	//获取任务列表
	public List<Task> gainTaskList(){
		List<Task> tasks = dao.query("select task from Task task");
		return tasks;
	}
	
	//删除任务配置需考虑当前的任务配置是否正在执行，如果正在执行，则提示“正在执行，暂时不能删除”；否则删除关联的表以及taskconfig表，并移出seceduleExecutor
	public String deleteTaskConfig(String taskConfigId){
		TaskConfig taskConfig = (TaskConfig) dao.queryUnique("select tc from TaskConfig tc where tc.id = '" + taskConfigId + "'");
		List<TaskConfig> currentExecutingTaskConfigs = gainExecutingTaskConfigList();
		int flag = 0;//判断该任务配置是否正在执行
		for (TaskConfig taskConfig2 : currentExecutingTaskConfigs) {
			if (taskConfig2.getName().equals(taskConfig.getName())) {
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			return "error";
		}else {
			if (taskConfig.getIsAuto() == 1) {
				//自动的则移除ScheduledExecutorService
				Map<String, ScheduledExecutorService> autoExecutorServicesMap = TaskConfigListener.autoExecutorServicesMap;
				ScheduledExecutorService seScheduledExecutorService = autoExecutorServicesMap.get(taskConfig.getName());
				if (seScheduledExecutorService != null) {
					seScheduledExecutorService.shutdown();
				}
			}
			//删掉数据库对应字段
			List<TaskTaskConfig> taskTaskConfigs = dao.query("select ttc from TaskTaskConfig ttc where ttc.taskConfig.id = '" + taskConfig.getId() + "' order by ttc.order asc");
			if (taskTaskConfigs != null && taskTaskConfigs.size() > 0) {
				for (TaskTaskConfig taskTaskConfig : taskTaskConfigs) {
					dao.delete(taskTaskConfig);
				}
			}

			List<TaskLog> taskLogs = dao.query("select tl from TaskLog tl where tl.taskConfig.id = '" + taskConfigId + "'");
			if (taskLogs != null && taskLogs.size() > 0) {
				for (TaskLog taskLog : taskLogs) {
					dao.delete(taskLog);
				}
			}
			dao.delete(taskConfig);
			return "success";
		}
	}
	
	/**
	 * 获取指定任务配置
	 * @param taskConfigId
	 * @return
	 */
	public TaskConfig gainTaskConfig(String taskConfigId){
		TaskConfig taskConfig = (TaskConfig) dao.queryUnique("select tc from TaskConfig tc where tc.id = '" + taskConfigId + "'");
		return taskConfig;
	}
	
	/**
	 * 获取指定任务配置的最新日志信息
	 * @param taskConfigId
	 * @return
	 */
	public TaskLog gainRecentlyTaskLog(String taskConfigId){
		List<TaskLog> taskLogs = dao.query("select tl from TaskLog tl where tl.taskConfig.id = '" + taskConfigId + "' order by tl.beginDate desc");
		if (taskLogs != null && taskLogs.size() > 0) {
			return taskLogs.get(0);
		}
		return null;
	}
	
	/**
	 *根据taskConfig对应的最新的taskLog，获取taskConfig的执行状态 
	 * @param taskLog
	 * @return
	 */
	public String getTaskConfigStatus(TaskLog taskLog){
		if (taskLog == null) {
			return "暂未执行";
		}
		if (taskLog.getIsSuccess() == null) {
			if (taskLog.getBeginDate() != null && taskLog.getEndDate() == null) {
				return "正在执行";
			}
		}else {
			if (taskLog.getIsSuccess() == 0) {
				return "执行失败";
			}else {
				return "已成功执行";
			}
		}
		return "";
	}
	
	/**
	 * 计算指定任务配置成功执行的次数
	 * @param taskConfigId
	 * @return
	 */
	public int successExecuteNum(String taskConfigId){
		List<TaskLog> taskLogs = dao.query("select tl from TaskLog tl where tl.taskConfig.id = '" + taskConfigId + "' and tl.isSuccess = 1");
		if (taskLogs != null && taskLogs.size() > 0) {
			return taskLogs.size();
		}
		return 0;
	}
	
	/**
	 * 根据taskConfig最近执行的日志，计算指定taskConfig下一次执行的时间
	 * @param taskLog
	 * @param taskConfig
	 * @return
	 */
	public Date nextExecuteDate(TaskLog taskLog, TaskConfig taskConfig){
		Date beginDate = taskLog.getBeginDate();
		if (taskConfig.getExecuteTime() == null) {
			return new Date(beginDate.getTime() + taskConfig.getInterval() * 60 * 1000); 
		}else {
			return new Date(beginDate.getTime() + 7 * 24 * 60 * 60 * 1000); 
		}
	}
	
	/**
	 * 创建任务
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @param taskName
	 */
	public void createTask(String className, String methodName, String parameters, String taskName) {
		Task task = new Task();
		task.setClassName(className);
		task.setMethodName(methodName);
		task.setName(taskName);
		task.setParameters(parameters);
		dao.add(task);
	}
	
	/**
	 * 根据taskName获取task
	 * @param taskName
	 * @return
	 */
	public Task gainTaskByTaskName(String taskName){
		Task task = (Task) dao.queryUnique("select task from Task task where task.name = '" + taskName + "'");
		return task;
	}
	
	public HibernateBaseDao getDao() {
		return dao;
	}

	public void setDao(HibernateBaseDao dao) {
		this.dao = dao;
	}
	
}
