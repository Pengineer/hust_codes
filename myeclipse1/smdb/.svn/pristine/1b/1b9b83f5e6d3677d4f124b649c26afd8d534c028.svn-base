package csdc.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;
import csdc.tool.SpringBean;
import csdc.tool.crawler.Crawler;
import csdc.tool.crawler.CrawlerExecutor;
import csdc.tool.crawler.NsfcCrawler;
import csdc.tool.crawler.NsfcTaskUpdater;
import csdc.tool.crawler.validator.NsfcPageValidator;
import csdc.tool.crawler.validator.PageValidator;

/**
 * 国家自科基金项目相关服务
 * @author Isun
 *
 */
@Component
public class NsfcService {
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;

	/**
	 * 更新国家自科基金项目数据
	 * 如果当前没有未完成的任务，则新增一批次任务。
	 * 然后启动所有未完成的任务。
	 */
	public void update(int startYear, int endYear) {
		NsfcCrawler.flag = false;
		NsfcTaskUpdater nsfcTaskUpdater = (NsfcTaskUpdater) SpringBean.getBean("nsfcTaskUpdater");
		nsfcTaskUpdater.setStartYear(startYear);
		nsfcTaskUpdater.setEndYear(endYear);
		crawlerExecutor.execute(nsfcTaskUpdater);
	}
	
	/**
	 * 获取正在工作的抓取任务
	 * @return
	 */
	public List<CrawlTask> getRunningTasks() {
		List<CrawlTask> tasks = new ArrayList<CrawlTask>();
		for (Crawler crawler : crawlerExecutor.getRunningCrawlers()) {
			if (crawler.getCrawlTask().getTaskType().equals("nsfc")) {
				tasks.add(crawler.getCrawlTask());
			}
		}
		return tasks;
	}
	
	/**
	 * 清空队列中等待任务，尝试取消当前正在执行的抓取任务
	 */
	public void cancelNow() {
		NsfcCrawler.flag = true;
		crawlerExecutor.shutdownNow();
	}
	
	/**
	 * 爬虫是否已失效
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public boolean isCrawlerOutdated() {
		PageValidator validator;
		try {
			validator = PageValidator.getInstance(NsfcPageValidator.class);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return validator.isOutdated();
	}

}
