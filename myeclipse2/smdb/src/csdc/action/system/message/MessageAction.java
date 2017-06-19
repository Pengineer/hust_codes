package csdc.action.system.message;

import java.util.List;

import csdc.action.BaseAction;
import csdc.bean.Message;
import csdc.service.IMailService;
import csdc.service.IMessageService;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MessageInfo;

/**
 * 留言管理公共部分
 * @author 龚凡
 * @version 2011.01.24
 */
public abstract class MessageAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_BUFFER_ID = "m.id";// 上下条查看时用于查找缓存的字段
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected IMessageService messageService;// 留言管理接口
	protected IMailService mailService;// 邮件管理接口
	protected Message message;// 留言对象
	protected List<Message> messages;// 查看留言
	
	public abstract String HQL();
	public abstract String pageName();
	public abstract String[] column();
	
	public String dateFormat() {
		return MessageAction.DATE_FORMAT;
	}
	public String pageBufferId() {
		return MessageAction.PAGE_BUFFER_ID;
	}

	/**
	 * 查看留言校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_VIEW_NULL);
		}
	}
	public List<Message> getMessages() {
		return messages;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}

}