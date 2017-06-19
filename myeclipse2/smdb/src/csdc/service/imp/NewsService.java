package csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.News;
import csdc.service.INewsService;
import csdc.tool.FileTool;

@Transactional
public class NewsService extends BaseService implements INewsService {

	/**
	 * 添加新闻
	 * @param news待添加的新闻对象
	 * @return newsId新闻对象ID
	 */
	public String addNews(News news) {
		news.setCreateDate(new Date());// 设置新闻添加时间
		news.setHtmlFile(null);
		
		String newsId = dao.add(news);
		return newsId;
	}

	/**
	 * 修改新闻
	 * @param orgNewsId原始新闻ID
	 * @param news待修改的新闻对象
	 */
	public void modifyNews(String orgNewsId, News news) {
		News orgNews = (News) dao.query(News.class, orgNewsId);// 获取原始新闻对象
		
		// 设置更新值
		orgNews.setTitle(news.getTitle());
		orgNews.setContent(news.getContent());
		orgNews.setIsOpen(news.getIsOpen());
		orgNews.setSource(news.getSource());
		orgNews.setType(news.getType());
		
		dao.modify(orgNews);
	}

	/**
	 * 删除新闻
	 * @param newsIds待删除的新闻ID集合
	 */
	public void deleteNews(List<String> newsIds) {
		if (newsIds != null && !newsIds.isEmpty()) {
			News news;
			String[] fileArray;
			for (String newsId : newsIds) {
				if (newsIds != null && !newsIds.isEmpty()) {
					news = (News) dao.query(News.class, newsId);
					if (news.getAttachment() != null) {
						fileArray = news.getAttachment().split("; ");
						if (fileArray != null && fileArray.length != 0) {
							for (String fileName : fileArray) {
								FileTool.fileDelete(fileName);// 删除附件
							}
						}
					}
					dao.delete(news);// 删除新闻
				}
			}
		}
	}

	/**
	 * 查看新闻
	 * @param newsId待查看的新闻ID
	 * @return news新闻对象
	 */
	@SuppressWarnings("unchecked")
	public News viewNews(String newsId) {
		Map map = new HashMap();
		map.put("newsId", newsId);
		List<News> newsList = dao.query("select n from News n left join fetch n.type sys left join fetch n.account a where n.id = :newsId", map);
		
		return (newsList.isEmpty() ? null : newsList.get(0));
	}
}