package csdc.action.mobile.info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.mobile.MobileAction;
import csdc.bean.Notice;
import csdc.bean.SystemOption;
import csdc.service.IMobileInfoService;

/**
 * mobile通知模块
 * @author suwb
 *
 */
public class MobileNoticeAction extends MobileAction{

	private static final long serialVersionUID = 7233242173420091499L;
	private static final String PAGENAME = "mobileNoticePage"; 
	private final String noticeHql = "select n.id, n.title, sys.name, n.isOpen, a.id, p.name, n.createDate, n.viewCount, n.accountBelong from Notice n left join n.account a left join n.type sys left join a.passport p order by n.createDate desc";
	private final String updateNoticeHql = "select n.id, n.title, sys.name, n.isOpen, a.id, p.name, n.createDate, n.viewCount, n.accountBelong from Notice n left join n.account a left join n.type sys left join a.passport p where n.createDate>=:timestamp order by n.createDate desc";
	private String timestamp;
	@Autowired
	private IMobileInfoService mobileInfoService;

	public String simpleSearch(){
		search(noticeHql, null);
		return SUCCESS;
	}
	
	public String update() throws ParseException{
		HashMap map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date stampDate = df.parse(timestamp);
		map.put("timestamp", stampDate);
		search(updateNoticeHql, map);
		return SUCCESS;
	}
	
	
	public String view(){
		Notice notice = mobileInfoService.getNoticeById(entityId);
		notice.setViewCount(notice.getViewCount() + 1);// 查看数量+1
		dao.modify(notice);
		SystemOption so = mobileInfoService.getSystemOptionById(notice.getType().getId());
		Map dataMap = new HashMap();//返回客户端的数据
		dataMap.put("accountBelong", notice.getAccountBelong());
		dataMap.put("attachment", notice.getAttachment());
		dataMap.put("attachmentName", notice.getAttachmentName());
		dataMap.put("content", "<style>span,p,h{font-size:13px !important}</style>"+notice.getContent());
		dataMap.put("createDate", notice.getCreateDate());
		dataMap.put("htmlFile", notice.getHtmlFile());
		dataMap.put("isOpen", notice.getIsOpen());
		dataMap.put("source", notice.getSource());
		dataMap.put("title", notice.getTitle());
		dataMap.put("viewCount", notice.getViewCount());
		dataMap.put("isPop", notice.getIsPop());
		dataMap.put("validDays", notice.getValidDays());
		dataMap.put("receiverType", notice.getReceiverType());
		dataMap.put("sendEmail", notice.getSendEmail());
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
