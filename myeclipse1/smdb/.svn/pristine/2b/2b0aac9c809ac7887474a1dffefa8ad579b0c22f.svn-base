package csdc.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.CrawlTask;
import csdc.tool.SpringBean;
import csdc.tool.crawler.Crawler;
import csdc.tool.crawler.CrawlerExecutor;
import csdc.tool.crawler.NssfTaskUpdater;
import csdc.tool.crawler.validator.NssfPageValidator;
import csdc.tool.crawler.validator.PageValidator;

/**
 * 国家社科基金项目相关服务
 * @author Isun
 *
 */
@Component
public class NssfService {
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;

	/**
	 * 更新国家社科基金项目数据
	 * 如果当前没有未完成的任务，则新增一批次任务。
	 * 然后启动所有未完成的任务。
	 */
	public void update() {
		NssfTaskUpdater nssfTaskUpdater = (NssfTaskUpdater) SpringBean.getBean("nssfTaskUpdater");
		crawlerExecutor.execute(nssfTaskUpdater);
	}
	
	/**
	 * 获取正在工作的抓取任务
	 * @return
	 */
	public List<CrawlTask> getRunningTasks() {
		List<CrawlTask> tasks = new ArrayList<CrawlTask>();
		for (Crawler crawler : crawlerExecutor.getRunningCrawlers()) {
			if (crawler.getCrawlTask().getTaskType().equals("nssf")) {
				tasks.add(crawler.getCrawlTask());
			}
		}
		return tasks;
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
			validator = PageValidator.getInstance(NssfPageValidator.class);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return validator.isOutdated();
	}

	/**
	 * 查询当年年份形成年份列表，起始年份为2005
	 * @return 年份列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> getYearMap(){
		Map<Integer, Integer> yearMap = new LinkedHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int y = Integer.parseInt(year);
		for(int i=2005; i<y; i++){
			yearMap.put(i, i);
		}
		yearMap.put(y, y);
		return yearMap;
	}
}
