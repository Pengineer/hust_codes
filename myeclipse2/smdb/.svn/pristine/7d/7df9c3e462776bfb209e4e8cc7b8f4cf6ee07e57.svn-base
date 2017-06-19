package csdc.tool.crawler;

import csdc.bean.CrawlTask;
import csdc.tool.SpringBean;

/**
 * 爬虫工厂，根据任务类型获取相应的爬虫实例
 * @author Isun
 *
 */
public class CrawlerFactory {
	
	/**
	 * 根据task类型获取相应Crawler实例
	 * @param task
	 * @return
	 */
	public static Crawler getCrawler(CrawlTask task) {
		String taskType = task.getTaskType();
		Crawler crawler = null;
		
		if (taskType.equals("nsfc")) {
			//国家自科基金
			crawler = (Crawler) SpringBean.getBean("nsfcCrawler");
		} else if (taskType.equals("nssf")) {
			//国家社科基金
			crawler = (Crawler) SpringBean.getBean("nssfCrawler");
		} else if (taskType.equals("base") || taskType.equals("special")|| taskType.equals("gener")) {
			//社科网附件下载
			crawler = (Crawler) SpringBean.getBean("attachmentDownload");
		} else {
			throw new RuntimeException("Unknown task type.");
		}
		crawler.setCrawlTask(task);
		
		return crawler;
	}

}
