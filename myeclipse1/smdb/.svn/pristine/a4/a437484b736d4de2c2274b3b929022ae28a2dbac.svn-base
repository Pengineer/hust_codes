package csdc.bean;

import org.apache.struts2.json.annotations.JSON;

public class TaskTaskConfig implements java.io.Serializable {

	private static final long serialVersionUID = -1859298978363188601L;
	private String id;// 主键id
	private Task task;// 任务id（FK）
	private TaskConfig taskConfig;// 任务配置id（FK）
	private int order;// 任务执行顺序
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskConfig getTaskConfig() {
		return taskConfig;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = taskConfig;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@JSON(serialize=false)
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}