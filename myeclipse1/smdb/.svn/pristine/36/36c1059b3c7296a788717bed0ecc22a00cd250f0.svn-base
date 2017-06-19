package csdc.action.system.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Message;
import csdc.bean.Account;
import csdc.tool.RequestIP;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MessageInfo;

public class Outer extends MessageAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select m.id, m.title, m.authorName, m.createDate from Message m where m.id = m.replyTopic.id and m.isOpen = 1 ";
	protected static final String ORDER_BY = " order by m.createDate desc, m.id desc";
	private static final String[] COLUMN = {
		"m.title",
		"m.authorName",
		"m.createDate desc"
	};// 排序列
	private static final String PAGE_NAME = "messageOuterPage";// 列表页面名称
	private int status;// 留言入口(0--添加,1--回复,2--引用)；审核状态
	private String contentHeader;// 回复留言头
	private Message replyMessage;// 回帖


	public String[] column() {
		return Outer.COLUMN;
	}
	public String pageName() {
		return Outer.PAGE_NAME;
	}

	/**
	 * 进入添加留言页面
	 * @return
	 */
	public String toAdd() {
		message = new Message();
		message.setAuditStatus(0);// 默认未审核状态
		if (status == 1 || status == 2) {
			replyMessage = (Message) dao.query(Message.class, entityId);
			message.setIsOpen(0);// 回帖的公开性默认为不公开
			if (status == 1) {
				contentHeader = "<div class = \"reply\">回复 " + replyMessage.getAuthorName() + ":<br /></div>";
			} else {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				contentHeader = "<blockquote class=\"blockquote\">"
					+ replyMessage.getAuthorName()
					+ " 在  "
					+ df.format(replyMessage.getCreateDate())
					+ " 说:<br />"
					+ replyMessage.getContent()
					+ "</blockquote>";
			}
		}
		return SUCCESS;
	}

	public void validateToAdd() {
		if ((status == 1 || status == 2) && entityId == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_REPLY_NULL);
		}
	}

	/**
	 * 添加留言
	 * @return
	 */
	@Transactional
	public String add() {
		message.setAccount(null);

		request = ServletActionContext.getRequest();
		message.setIp(RequestIP.getRequestIp(request));
		message.setIsOpen(0);

		entityId = messageService.addMessage(message);

		if (message.getReplyTopic() == null || message.getReplyTopic().getId().isEmpty()) {// 如果是添加留言
			message.setReplyTopic(message);
		} else {
			entityId = message.getReplyTopic().getId();// 记录主贴ID
		}

		dao.modify(message);
		
		return SUCCESS;
	}

	/**
	 * 添加留言校验
	 */
	public void validateAdd() {
	}

	/**
	 * 进入查看
	 */
	public String toView() {
//		if(pageNumber>0){
//			backToList(pageName(),pageNumber);
//		}
		return SUCCESS;
	}

	/**
	 * 查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看留言
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		message = (Message) dao.query(Message.class, entityId);// 欲查看的留言主贴
		if (message == null || message.getIsOpen() != 1) {// 留言不存在或不是外网留言
//			request.setAttribute(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_MESSAGE_NULL);
			addActionError(MessageInfo.ERROR_MESSAGE_NULL);
			return INPUT;
		}

		List<Message> allMessages = messageService.viewMessage(entityId);// 主贴及其回复
		messages = new ArrayList<Message>();
		for (int i = 0; i < allMessages.size(); i++){
			if (allMessages.get(i).getIsOpen() == 1) {
				messages.add(allMessages.get(i));
			}
		}
		ArrayList innerOrOuter = new ArrayList();
		for (int i = 0; i < messages.size(); i++) {
			Account account = ((Message)messages.get(i)).getAccount();
			innerOrOuter.add(account == null ? 0 : 1);
		}

		message.setViewCount(message.getViewCount() + 1);
		dao.modify(message);

		jsonMap.put("message", message);
		jsonMap.put("messages", messages);
		jsonMap.put("innerOrOuter", innerOrOuter);

		return SUCCESS;
	}

	/**
	 * 初级检索
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("keyword", "%" + keyword + "%");
		hql.append(HQL);
		hql.append(" and (LOWER(m.title) like :keyword or LOWER(m.content) like :keyword or LOWER(m.authorName) like :keyword) ");
		hql.append("");
		return new Object[]{
			hql.toString(),
			map,
			2,
			null
		};
//		this.simpleSearch(hql, map, ORDER_BY, 2, 0, PAGE_NAME);
//		return SUCCESS;
	}
	
	/**
	 * 高级检索，外网留言无
	 */
	@Override
	public Object[] advSearchCondition() {
		return null;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContentHeader() {
		return contentHeader;
	}
	public void setContentHeader(String contentHeader) {
		this.contentHeader = contentHeader;
	}
	public Message getReplyMessage() {
		return replyMessage;
	}
	public void setReplyMessage(Message replyMessage) {
		this.replyMessage = replyMessage;
	}
	public String HQL() {
		return HQL;
	}

}
