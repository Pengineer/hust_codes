package csdc.action.mobile.info;

import java.util.HashMap;
import java.util.Map;

import csdc.action.mobile.MobileAction;
import csdc.bean.News;
import csdc.bean.SystemOption;

/**
 * mobile新闻模块
 * @author suwb
 *
 */
public class MobileNewsAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileNewsPage"; 
	
	/**
	 * 新闻列表
	 */
	public String simpleSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		hql.append("select n.id, n.title, sys.name, a.id, p.name, n.createDate from News n left join n.account a left join n.type sys left join a.passport p ");
		hql.append(" order by n.createDate desc");
		search(hql, map);
		return SUCCESS;
	}
	
	/**
	 * 新闻查看
	 */
	@SuppressWarnings("unchecked")
	public String view(){
		News news = dao.query(News.class, entityId);
		String soId = news.getType().getId();
		SystemOption so = (SystemOption)dao.query(SystemOption.class, soId);
		Map dataMap = new HashMap();//返回客户端的数据
		dataMap.put("accountBelong", news.getAccountBelong());
		dataMap.put("attachment", news.getAttachment());
		dataMap.put("attachmentName", news.getAttachmentName());
		dataMap.put("content", news.getContent());
		dataMap.put("createDate", news.getCreateDate());
		dataMap.put("htmlFile", news.getHtmlFile());
		dataMap.put("isOpen", news.getIsOpen());
		dataMap.put("source", news.getSource());
		dataMap.put("title", news.getTitle());
		dataMap.put("viewCount", news.getViewCount());
		dataMap.put("type", so.getName());
		jsonMap.put("laData", dataMap);
		return SUCCESS;
	}
	
	@Override
	public String pageName() {
		return PAGENAME;
	}
}
