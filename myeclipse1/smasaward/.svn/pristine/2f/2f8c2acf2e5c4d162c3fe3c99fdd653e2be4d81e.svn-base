package csdc.tool.bean;

/**
 * web 服务数据同步模块 参数变量信息对象
 * 进入条相关控制
 * 每次操作（可能多批次请求，或者逐个请求）产生一个这样一个同步信息对象
 * 在本次操作结束，则式样此对象
 * @author zhangnan
 * 2015-5-27
 */
public class SynchronizationInfo {
	
	private int taskType = 0;//任务类型[0:默认； 1：批量获取方式；2：逐条同步方式]
	
	private long totalNum; //总数据量[默认0] *必须设置
	private long currentNum;//当前数据量 [默认0] * 必须设置
	private int runStatus = 0;//运行状态[ 0：默认；1:执行(继续)；2：暂停；3：取消；4：完成]

	//当taskType =1的时候，一下参数有效
	private int batchIndex;//当前正在执行的批次 [默认0]，基于1开始
	private int batchSize;//执行批次的大小[默认0，建议500 ~ 1000]，基于1开始
	private int batchTotal;//当前任务总共多少批次 [默认0],可以根据 可以根据totalNum 和 batchSize计算出来
	
	
	//各个状态信息变量初始化
	public SynchronizationInfo() {
		this.totalNum = 0;//
		this.currentNum = 0;
		this.runStatus = 1;
		
		this.taskType = 0;
		this.batchTotal = 0;
		this.batchIndex = 0;
		this.batchSize = 0;
	}
	/**
	 * 
	 * @param taskType 任务类型[0:默认； 1：批量获取方式；2：逐条同步方式]
	 * 如果是批量类型，则还需要设置batchTotal，batchIndex变量。
	 * @param totalNum 总数量
	 */
	public SynchronizationInfo(int taskType, int totalNum) {
		this.totalNum = totalNum;
		this.currentNum = 0;
		
		this.runStatus = 1;
		
		this.taskType = taskType;
		this.batchTotal = 0;
		this.batchIndex = 0;
		this.batchSize = 0;
	}
	/**
	 * 所有变量初始化
	 */
	public void setInitialization() {
		this.totalNum = 0;
		this.currentNum = 0;
		this.runStatus = 1;
		
		this.taskType = 0;
		this.batchTotal = 0;
		this.batchIndex = 0;
		this.batchSize = 0;
	}
	
	/**
	 * 更具执行情况，判断运行状态
	 */
	public void update() {
		if (this.currentNum == this.totalNum) {
			this.runStatus = 4;
		}
	}
	
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public long getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(long currentNum) {
		this.currentNum = currentNum;
	}
	/**
	 * 当前处理条数自++
	 */
	public void setCurrentNumPlusPlus() {
		this.currentNum++;
	}
	public int getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}
	public int getTaskType() {
		return taskType;
	}
	/**
	 * 任务类型[0:默认； 1：批量获取方式；2：逐条同步方式]
	 * @param taskType
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getBatchTotal() {
		if (1 == taskType) {
			batchTotal = (int) (totalNum/batchSize + 1) ;
		}
		return batchTotal;
	}

	public int getBatchIndex() {
		return batchIndex;
	}
	public void setBatchIndex(int batchIndex) {
		this.batchIndex = batchIndex;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
}
