package csdc.tool.crawler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;

/**
 * 爬虫调度控制器
 * @author xuhan
 *
 */
@Component
public class CrawlerExecutor extends ThreadPoolExecutor {
	
	/**
	 * 最大可以同时工作的任务数
	 */
	private static final int MAX_TASK_NUMBER = 20; 
	
	/**
	 * 正在执行的任务的taskType+arguments
	 */
	private Map<String, Crawler> currentTasks = new ConcurrentHashMap<String, Crawler>();
	
	public CrawlerExecutor() {
		super(MAX_TASK_NUMBER, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	/**
	 * 根据任务的taskType获取相应的Crawler实例，并执行
	 * @param task
	 */
	public void addTask(CrawlTask task) {
		Crawler crawler = CrawlerFactory.getCrawler(task);
		if (!currentTasks.containsKey(task.getTaskType() + task.getArguments())) {
			execute(crawler);
		}
	}
	
	public Collection<Crawler> getRunningCrawlers() {
		return currentTasks.values();
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) { 
		if (r instanceof Crawler) {
			Crawler crawler = (Crawler) r;
			if (currentTasks.containsKey(crawler.description())) {
				crawler.setCancelled(true);
			} else {
				currentTasks.put(crawler.description(), crawler);
			}
		}
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) { 
		if (r instanceof Crawler) {
			Crawler crawler = (Crawler) r;
			if (!crawler.isCancelled()) {
				currentTasks.remove(crawler.description());
			}
		}
	}

}
