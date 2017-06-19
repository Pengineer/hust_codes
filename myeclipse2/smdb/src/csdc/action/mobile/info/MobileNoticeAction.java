package csdc.action.mobile.info;

import java.util.HashMap;
import java.util.Map;

import csdc.action.mobile.MobileAction;
import csdc.bean.News;
import csdc.bean.Notice;
import csdc.bean.SystemOption;

/**
 * mobile通知模块
 * @author suwb
 *
 */
public class MobileNoticeAction extends MobileAction{

	private static final long serialVersionUID = 7233242173420091499L;
	private static final String PAGENAME = "mobileNoticePage"; 
	
	public String simpleSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		hql.append("select n.id, n.title, sys.name, n.isOpen, a.id, p.name, n.createDate, n.viewCount, n.accountBelong from Notice n left join n.account a left join n.type sys left join a.passport p");
		hql.append(" order by n.createDate desc");
		search(hql, map);
		return SUCCESS;
	}
	
	public String view(){
		Notice notice = dao.query(Notice.class, entityId);
		String soId = notice.getType().getId();
		System.out.println(soId);
		SystemOption so = (SystemOption)dao.query(SystemOption.class, soId);
		Map dataMap = new HashMap();//返回客户端的数据
		dataMap.put("accountBelong", notice.getAccountBelong());
		dataMap.put("attachment", notice.getAttachment());
		dataMap.put("attachmentName", notice.getAttachmentName());
		dataMap.put("content", notice.getContent());
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

}
