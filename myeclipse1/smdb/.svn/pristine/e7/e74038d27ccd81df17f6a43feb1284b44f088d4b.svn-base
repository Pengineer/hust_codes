package csdc.tool.execution;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

/**
 * 专用于一次性执行的需求
 * @author xuhan
 *
 */
public abstract class Execution {
	
	/**
	 * 同时只能进行一个作业
	 */
	private boolean busy = false;
	
	/**
	 * 该方法启动工作任务，保证工作任务在一个事务内，为原子操作
	 */
	@Transactional
	public void excute() {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return;
		}
		try {
			busy = true;
			work();
		} catch (Throwable e) {
			e.printStackTrace();
			//此处转译为RuntimeException，使Spring能够回滚
			throw new RuntimeException(e);
		} finally {
			busy = false;
		}
	}
	
	/**
	 * 该方法启动工作任务，无事务处理
	 */
	public void excuteNoTransaction() {
		if (busy) {
			System.err.println("有执行作业正在进行，请等待该工作结束后再执行!");
			return;
		}
		try {
			busy = true;
			work();
		} catch (Throwable e) {
			e.printStackTrace();
			//此处转译为RuntimeException，使Spring能够回滚
			throw new RuntimeException(e);
		} finally {
			busy = false;
		}
	}
	
	/**
	 * 该方法确定要执行的具体工作
	 */
	protected abstract void work() throws Throwable;
	
	/**
	 * 下面是自动化入库需要用到的方法
	 * @return
	 * @throws Throwable
	 */
	public int getTotalNum() throws Throwable {
		return 0; 
	}
	
	public int getCurrentNum() throws Throwable {
		return 0;
	}
	
	public Map getIllegalException() throws Throwable {
		return null;
	}
	
	public void setStatus(int status) throws Throwable {
		
	}
	
	public int getIsFinished() {
		return 0;
	}
	
	public int getTotalImportNum() {
		return 0;
	}
}
