package csdc.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 抓取任务记录
 * @author xuhan
 *
 */
public class CrawlTask {
	
	private String id;
	
	/**
	 * 爬虫的类型，用于确定工作类
	 */
	private String taskType;
	
	/**
	 * 一个抓取任务的参数（使用json格式）
	 */
	private String arguments;
	
	/**
	 * 抓取进度（例如：下次即将抓取的页码）
	 */
	private String progress;
	
	/**
	 * 任务发布时间
	 */
	private Date assignTime;
	
	/**
	 * 任务完成时间
	 */
	private Date finishTime;
	
	/**
	 * 日志（记录爬取中遇到的各种值得记录的信息）
	 */
	private String log;
	
	public void addLog(String log) {
		if (this.log == null) {
			this.log = "";
		}
		this.log += new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + log + "\n";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
}
