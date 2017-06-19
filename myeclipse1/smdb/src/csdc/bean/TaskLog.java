package csdc.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class TaskLog implements java.io.Serializable {

	private static final long serialVersionUID = -4941498974837976248L;
	private String id;// 任务执行日志id（PK）
	private Date beginDate;// 任务开始时间
	private Date endDate;// 任务结束时间
	private Integer isSuccess;// 是否成功执行[0:否; 1;是]
	private TaskConfig taskConfig;// 任务配置表id（FK）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public TaskConfig getTaskConfig() {
		return taskConfig;
	}

	public void setTaskConfig(TaskConfig taskConfig) {
		this.taskConfig = taskConfig;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}