package csdc.action.mobile.info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.mobile.MobileAction;
import csdc.bean.News;
import csdc.bean.SystemOption;
import csdc.service.IMobileInfoService;

/**
 * mobile新闻模块
 * @author suwb
 *
 */
public class MobileNewsAction extends MobileAction{

	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileNewsPage";
	private final String newsHql = "select n.id, n.title, sys.name, a.id, p.name, n.createDate from News n left join n.account a left join n.type sys left join a.passport p order by n.createDate desc";
	private final String updatNewsHql = "select n.id, n.title, sys.name, a.id, p.name, n.createDate from News n left join n.account a left join n.type sys left join a.passport p where n.createDate>=:timestamp order by n.createDate desc";
	private String timestamp;
	@Autowired
	private IMobileInfoService mobileInfoService;
	
	
	/**
	 * 新闻列表
	 */
	public String simpleSearch(){
		search(newsHql, null);
		return SUCCESS;
	}
	
	/**
	 * 新闻更新
	 * @throws ParseException 
	 */
	public String update() throws ParseException{
		HashMap map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date stampDate = df.parse(timestamp);
		map.put("timestamp", stampDate);
		search(updatNewsHql, map);
		return SUCCESS;
	}
	
	/**
	 * 新闻查看
	 */
	public String view(){
		News news = mobileInfoService.getNewsById(entityId);
		news.setViewCount(news.getViewCount() + 1);// 查看数量+1
		dao.modify(news);
		SystemOption so = mobileInfoService.getSystemOptionById(news.getType().getId());
		Map dataMap = new HashMap();//返回客户端的数据
		dataMap.put("accountBelong", news.getAccountBelong());
		dataMap.put("attachment", news.getAttachment());
		dataMap.put("attachmentName", news.getAttachmentName());
		dataMap.put("content", "<style>span,p,h{font-size:13px !important}</style>"+news.getContent());
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
