package csdc.action.system.execution;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import csdc.tool.SpringBean;
import csdc.tool.execution.Execution;

public class ExecutionAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private String executionBeanName;
	
	private long costTime;
	
	public String toExecute() {
		return SUCCESS;
	}
	
	/**
	 * 事务处理
	 */
	@Transactional
	public String execute() {
		long begin = System.currentTimeMillis();
		try {
			for (String beanName : executionBeanName.split("\\W+")) {
				System.out.println("Executioin start : " + beanName);
				((Execution)SpringBean.getBean(beanName)).excute();
				System.out.println("Executioin finish: " + beanName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			costTime = System.currentTimeMillis() - begin;
		}
		return SUCCESS;
	}
	
	/**
	 * 无事务处理
	 */
	public String excuteNoTransaction() {
		long begin = System.currentTimeMillis();
		try {
			for (String beanName : executionBeanName.split("\\W+")) {
				System.out.println("Executioin start : " + beanName);
				((Execution)SpringBean.getBean(beanName)).excuteNoTransaction();
				System.out.println("Executioin finish: " + beanName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			costTime = System.currentTimeMillis() - begin;
		}
		return SUCCESS;
	}
	
	public String getExecutionBeanName() {
		return executionBeanName;
	}
	public void setExecutionBeanName(String executionBeanName) {
		this.executionBeanName = executionBeanName;
	}
	public long getCostTime() {
		return costTime;
	}
	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}
}
