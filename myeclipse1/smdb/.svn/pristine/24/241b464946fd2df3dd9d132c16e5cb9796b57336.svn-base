package csdc.service;

import java.util.List;

import csdc.bean.News;
import csdc.bean.Product;

public interface INewsService extends IBaseService {

	/**
	 * 添加新闻
	 * @param news待添加的新闻对象
	 * @return newsId新闻对象ID
	 */
	public String addNews(News news);

	/**
	 * 修改新闻
	 * @param orgNewsId原始新闻ID
	 * @param news待修改的新闻对象
	 */
	public void modifyNews(String orgNewsId, News news);

	/**
	 * 删除新闻
	 * @param newsIds待删除的新闻ID集合
	 */
	public void deleteNews(List<String> newsIds);

	/**
	 * 查看新闻
	 * @param newsId待查看的新闻ID
	 * @return news新闻对象
	 */
	public News viewNews(String newsId);
	/**
	 * 将新闻附件上传到DMSS
	 * @param news
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String uploadToDmss(News news) throws Exception;

}