package csdc.service;

import csdc.bean.Message;
import csdc.bean.News;
import csdc.bean.Notice;


public interface IMobileInfoService extends IBaseService {
	
	/**
	 * 通过新闻id获取新闻实体
	 * @param entityId 新闻id
	 * @return
	 */
	public News getNewsById(String entityId);
	
	/**
	 * 通过通知id获取通知实体
	 * @param entityId 通知id
	 * @return
	 */
	public Notice getNoticeById(String entityId);
	
	/**
	 * 通过留言id获取留言实体
	 * @param entityId 留言id
	 * @return
	 */
	public Message getMessageById(String entityId);
}
