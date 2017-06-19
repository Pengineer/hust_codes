package csdc.bean;

import java.util.Set;

public class TaskConfig implements java.io.Serializable {

	private static final long serialVersionUID = -4941498974837976248L;
	private String id;// 任务配置表（PK）
	private int isAuto;// 是否为自动执行[0：手动; 1：自动]
	private String name;// 任务配置名
	private String executeTime;// 执行日期（Monday:13:00）
	private int interval;// 执行时间间隔（单位分钟）
	
	private Set<TaskTaskConfig> taskTaskConfig;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<TaskTaskConfig> getTaskTaskConfig() {
		return taskTaskConfig;
	}

	public void setTaskTaskConfig(Set<TaskTaskConfig> taskTaskConfig) {
		this.taskTaskConfig = taskTaskConfig;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}