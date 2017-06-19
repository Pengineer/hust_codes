package csdc.tool.crawler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.CrawlTask;
import csdc.dao.HibernateBaseDao;
import csdc.dao.SqlBaseDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.crawler.validator.PageValidator;

/**
 * 爬虫父类
 * @author xuhan
 *
 */
public abstract class Crawler implements Runnable {

	protected CrawlTask crawlTask;
	
	private boolean cancelled;
	
	@Autowired
	protected HibernateBaseDao hibernateBaseDao;

	@Autowired
	protected SqlBaseDao sqlBaseDao;
	
	@Autowired
	protected BeanFieldUtils beanFieldUtils;
	
	protected static PoolingClientConnectionManager cm;

	protected static HttpClient httpClient;
	
	
	public static HttpClient getHttpClient() {
		if (httpClient == null) {
			cm = new PoolingClientConnectionManager();
			httpClient = new DefaultHttpClient(cm);

			// Increase max total connection
			cm.setMaxTotal(200);
			// Increase default max connection per route
			cm.setDefaultMaxPerRoute(100);

			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		}
		return httpClient;
	}
	
	public void run() {
		try {
			//如果已经被取消执行，则不执行
			if (cancelled) {
				return;
			}
			
			//如果即将执行时，该任务已经完成，则不执行
			hibernateBaseDao.refresh(crawlTask);
			if (crawlTask.getFinishTime() != null) {
				return;
			}
			//如果是附件下载的任务,则不需要页面校验
			if(crawlTask.getTaskType().contains("base") || crawlTask.getTaskType().contains("special")|| crawlTask.getTaskType().contains("gener")){
				work();
			}
			if (crawlTask.getTaskType().contains("upload")) {
				work();
			}
			else {
				PageValidator pageValidator = PageValidator.getInstance(getPageValidatorClass());
				if (pageValidator.valid()) {
					work();
				}				
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			crawlTask.addLog(sw.toString());
			hibernateBaseDao.addOrModify(crawlTask);
		}
	}

	abstract Class<? extends PageValidator> getPageValidatorClass();
	
	/**
	 * 具体的爬取工作
	 * @throws Exception
	 */
	abstract protected void work() throws Exception;

	public void setCrawlTask(CrawlTask crawlTask) {
		this.crawlTask = crawlTask;
	}
	public CrawlTask getCrawlTask() {
		return crawlTask;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * 任务类型+任务参数
	 * @return
	 */
	public String description() {
		return crawlTask.getTaskType() + crawlTask.getArguments();
	}
	
}
