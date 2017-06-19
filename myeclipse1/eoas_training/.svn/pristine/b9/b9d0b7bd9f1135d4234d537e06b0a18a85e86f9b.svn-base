package csdc.tool.mail;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.bean.Mail;
import csdc.dao.IBaseDao;
import csdc.tool.SpringBean;


/**
 * 邮件发送控制器，由ThreadPoolExecutor提供并发支持
 * @author xuhan
 */
public class MailController extends ThreadPoolExecutor {
	
	/**
	 * 发送速度需要考虑到邮件发送失败的情况（即发件服务器出现故障，或者发件账号出现故障）。<br>
	 * 目前使用的方法是：每个邮件发送任务成功后，都将waitingLength设为1分钟；如发送失<br>
	 * 败，则将waitingLength增加1分钟。之后，让发送邮件的线程sleep，时长waitingLength.
	 */
	private long waitingLength = 0;
	
	@Autowired
	private IBaseDao baseDao;


	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	private TransactionTemplate txTemplateRequiresNew;
	
	/**
	 * 等待发送中的邮件任务，即其中包含的邮件状态全部为“准备发送”<br>
	 * 当取消发送，或者开始进行发送时，都应将邮件从此移出
	 * mail.id -> mailTask
	 */
	private Map<String, MailTask> scheduledMailTasks = new ConcurrentHashMap<String, MailTask>();
	
	/**
	 * 具有sendingThreadNumber个线程的ThreadPoolExecutor，超过sendingThreadNumber个数的任务全部在LinkedBlockingQueue里排队
	 * @param sendingThreadNumber 可以同时工作的发送线程数
	 */
	public MailController(int sendingThreadNumber) {
		super(sendingThreadNumber, 0x7fffffff, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}
	
	/**
	 * Spring要求bean必须要有默认构造器
	 */
	public MailController() {
		super(1, 0x7fffffff, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	public void init() {
		System.out.println("邮件发送服务启动……");
	}
	
	/**
	 * 将邮件添加至发送队列
	 * 只能对“未发送”状态的邮件操作
	 * @param mailId
	 * @throws InterruptedException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public synchronized void send(String mailId) {
		/*
		 * 每次使用mailId从数据库查询出最新状态的mail实例
		 */
		final Mail mail = (Mail) baseDao.load(Mail.class, mailId);
/*		dao.evict(mail);*/
		
		/*
		 * 只能对“未发送”状态的邮件操作
		 */
		if (mail != null && mail.getStatus() == 0) {
			/*
			 * 将邮件状态设为“准备发送”
			 * 这里希望对邮件表的更改立即生效，即立刻flush、立刻结束事务，故使用了REQUIRES_NEW的传播特性。
			 * 如果仅仅由其他类调用该方法(send)，则该方法上的事务注解应该已经够用。但MailController
			 * 类内的调用会使该注解失效，因此这里使用编程式事务来强制实现REQUIRES_NEW的传播特性。
			 */
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						mail.setStatus(1);
					} catch (Exception ex) {
						status.setRollbackOnly();
					}
				}
			});
			try {
				MailTask mailTask = (MailTask) SpringBean.getBean("mailTask");
			} catch (Exception e) {
				e.printStackTrace();
			}
			MailTask mailTask = (MailTask) SpringBean.getBean("mailTask");
			mailTask.setMail(mail);
			scheduledMailTasks.put(mailId, mailTask);
			
			super.execute(mailTask);
		}
	}
	
	/**
	 * 暂停发送邮件
	 * 只能对“准备发送”状态的邮件操作
	 * @param mailId
	 * @throws InterruptedException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public synchronized void cancel(String mailId) {
//		MailTask mailTask = scheduledMailTasks.get(mailId);
		final Mail mail = (Mail) baseDao.load(Mail.class, mailId);
		/*dao.evict(mail);*/
		if(mail != null && mail.getStatus() == 1){
//		if (mailTask != null) {
			/*
			 * 编程式事务。和 send方法中的道理一样。
			 */
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						mail.setStatus(0);
					} catch (Exception ex) {
						status.setRollbackOnly();
					}
				}
			});
			baseDao.modify(mail);
//			super.remove(mailTask);
//	    	scheduledMailTasks.remove(mailId);
		}
	}
	
	/**
	 * 取消发送所有处于“准备发送”状态的邮件
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public synchronized void cancelAll() {
		HashSet<String> mailIds = new HashSet<String>(scheduledMailTasks.keySet());
		for (String mailId : mailIds) {
			try {
				cancel(mailId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		waitingLength = 0;
	}

	/**
	 * 在发送线程即将发送前执行
	 */
	@Override
    protected void beforeExecute(Thread t, Runnable r) {
    	MailTask mailTask = (MailTask) r;
    	Mail mail = mailTask.getMail();

    	mail.setStatus(2);
    	mail.setSendTimes(mail.getSendTimes() + 1);
    	baseDao.modify(mail);
    	
    	scheduledMailTasks.remove(mail.getId());
    }

	/**
	 * 在发送线程即完成发送后（无论失败）执行
	 */
	@Override
    protected void afterExecute(Runnable r, Throwable t) {
    	MailTask mailTask = (MailTask) r;
    	Mail mail = mailTask.getMail();
		if (t == null) {
			//发送正常
			mail.setStatus(3);
			if(null == mail.getFinishDate()){
				mail.setFinishDate(new Date());
			} else {
				mail.setFinishDate(mail.getFinishDate());
			}
			waitingLength = 60000;
		} else {
			//发送异常
			mail.setStatus(0);
			t.printStackTrace();
			
			//将等待时间延长一分钟，但最多等待五分钟
			waitingLength = Math.min(waitingLength + 60000, 60000 * 5);
		}
		baseDao.modify(mail);
		try {
			Thread.sleep(waitingLength);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}
}
