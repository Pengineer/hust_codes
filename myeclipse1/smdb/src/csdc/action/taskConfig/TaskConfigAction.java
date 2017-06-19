package csdc.action.taskConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Task;
import csdc.bean.TaskConfig;
import csdc.bean.TaskLog;
import csdc.service.taskConfig.TaskConfigService;

public class TaskConfigAction extends ActionSupport{
	/**
	 * author
	 * mwh
	 * 任务配置模块
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskConfigService taskConfigService;
	private String taskConfigId;//任务配置ID
	private String className;//任务配置中，要执行的类名
	private String methodName;//任务配置中，要执行的类中方法名
	private String taskName;//任务名
	private String parameters;//参数
	private String taskConfigName;//任务配置名
	private String executeTime;//执行日期（Monday:13:00:00）
	private int interval;//执行时间间隔（单位分钟）
	private Map jsonMap = new HashMap();// json对象容器
	
	/**
	 * 跳转到任务配置列表页面
	 * @return
	 */
	public String toTaskConfigListView() {
		return SUCCESS;
	}

	/**
	 * 跳转到任务列表页面
	 * @return
	 */
	public String toTaskListView() {
		return SUCCESS;
	}
	/**
	 *跳转到弹层页面
	 */
	public String toPopSelect() {
		return SUCCESS;
	}
	/**
	 * 生成自动任务配置
	 */
	public String createAutoTaskConfig() {
		String[] taskIdStrings = parameters.split("; ");
		List<String> taskIds = new ArrayList<String>();
		for (int i = 0; i < taskIdStrings.length; i++) {
			taskIds.add(taskIdStrings[i]);
		}
		taskConfigService.createAutoTaskConfig(taskConfigName, taskIds, executeTime, interval);
		return SUCCESS;
	}

	/**
	 * 生成手动任务配置
	 */
	public String createManualTaskConfig() {
		String[] taskIdStrings = parameters.split("; ");
		List<String> taskIds = new ArrayList<String>();
		for (int i = 0; i < taskIdStrings.length; i++) {
			taskIds.add(taskIdStrings[i]);
		}
		taskConfigService.createManualTaskConfig(taskConfigName, taskIds);
		return SUCCESS;
	}

	//获取自动任务配置的列表
	public void gainAutoTaskConfigList(){
		taskConfigService.gainAutoTaskConfigList();
	}
	
	//获取当前正在执行的任务配置列表
	public void gainExecutingTaskList(){
		taskConfigService.gainExecutingTaskConfigList();
	}
	
	//获取所有的任务配置列表
	public String gainTaskConfigList(){
		List<TaskConfig> taskConfigs = taskConfigService.gainTaskConfigList();
		List<String[]> taskConfigInfos = new ArrayList<String[]>();
		for (TaskConfig taskConfig : taskConfigs) {
			String[] taskConfigInfo = new String[4];
			taskConfigInfo[0] = taskConfig.getId();
			taskConfigInfo[1] = taskConfig.getName();
			taskConfigInfo[2] = taskConfig.getIsAuto() == 0 ? "手动" : "自动";
			TaskLog taskLog = taskConfigService.gainRecentlyTaskLog(taskConfig.getId());
			taskConfigInfo[3] = taskConfigService.getTaskConfigStatus(taskLog);
			taskConfigInfos.add(taskConfigInfo);
		}
		jsonMap.put("taskConfigInfos", taskConfigInfos);
		return SUCCESS;
	}
	
	//获取任务列表
	public String gainTaskList(){
		List<Task> tasks = taskConfigService.gainTaskList();
		List<String[]> taskInfos = new ArrayList<String[]>();
		for (Task task : tasks) {
			String[] taskInfo = new String[2];
			taskInfo[0] = task.getId();
			taskInfo[1] = task.getName();
			taskInfos.add(taskInfo);
		}
		jsonMap.put("taskInfos", taskInfos);
		return SUCCESS;
	}
	
	//删除任务配置
	//删除任务配置需考虑当前的任务配置是否正在执行，如果正在执行，则提示“正在执行，暂时不能删除”；否则删除与该taskconfig相关的tasklog，tasktaskconfig，并移出SecheduleExecutor.
	public String deleteTaskConfig(){
		String status = taskConfigService.deleteTaskConfig(taskConfigId);
		jsonMap.put("results", status.equals("error") ? "该事务正在执行，暂时不能删除" : "该事务已删除");
		return SUCCESS;
	}
	public String toViewTaskConfigInfo() {
		return SUCCESS;
	}
	//获取指定TaskConfig状态
	public String gainTaskConfigInfo(){
		TaskConfig taskConfig = taskConfigService.gainTaskConfig(taskConfigId);
		TaskLog taskLog = taskConfigService.gainRecentlyTaskLog(taskConfigId);
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		jsonMap.put("taskConfigName", taskConfig.getName());
		jsonMap.put("isAuto", taskConfig.getIsAuto() == 0 ? "手动" : "自动");
		jsonMap.put("executeTime", taskConfig.getExecuteTime());
		jsonMap.put("interval", taskConfig.getInterval());
		jsonMap.put("beginDate", taskLog == null ? "" : myFmt.format(taskLog.getBeginDate()));
		jsonMap.put("endDate", taskLog == null ? "" : taskLog.getEndDate() == null ? "" : myFmt.format(taskLog.getEndDate()));
		jsonMap.put("status", taskConfigService.getTaskConfigStatus(taskLog));
		if (taskConfig.getIsAuto() == 1) {
			jsonMap.put("successExecuteNum", taskConfigService.successExecuteNum(taskConfigId));
			jsonMap.put("nextExecuteDate", taskLog == null ? "" : myFmt.format(taskConfigService.nextExecuteDate(taskLog,taskConfig)));
		}
		return SUCCESS;
	}
	
	//创建任务
	public String createTask(){
		taskConfigService.createTask(className, methodName, parameters, taskName);
		return SUCCESS;
	}
	
	//修改任务配置
	public void modifyTaskConfig(){
		
	}
	
	//删除任务
	//TODO 删除任务需考虑任务与任务配置的关联关系
	public void deleteTask(){
		
	}
	
	public void cancelAutoTaskConfig(){
		
	}
	
	public void cancelManualTaskConfig(){
		
	}
	
	
	public TaskConfigService getTaskConfigService() {
		return taskConfigService;
	}

	public void setTaskConfigService(TaskConfigService taskConfigService) {
		this.taskConfigService = taskConfigService;
	}

	public String getTaskConfigId() {
		return taskConfigId;
	}

	public void setTaskConfigId(String taskConfigId) {
		this.taskConfigId = taskConfigId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getTaskConfigName() {
		return taskConfigName;
	}

	public void setTaskConfigName(String taskConfigName) {
		this.taskConfigName = taskConfigName;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
}
