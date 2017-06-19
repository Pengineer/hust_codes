package csdc.service;

import java.util.List;
import java.util.Properties;

import csdc.bean.Message;

public interface IMessageService extends IBaseService {

	/**
	 * 添加留言
	 * @param message留言对象
	 * @return messageId留言对象ID
	 */
	public String addMessage(Message message);

	/**
	 * 修改留言
	 * @param orgMessageId原始留言ID
	 * @param message待修改的留言对象
	 * @param modifier修改者信息
	 * @return 返回主贴ID
	 */
	public String modifyMessage(String orgMessageId, Message message, String[] modifier);

	/**
	 * 删除留言
	 * @param messageIds待删除的留言ID集合
	 */
	public void deleteMessage(List<String> messageIds);

	/**
	 * 查看留言
	 * @param messageId待查看的新闻ID
	 * @return message留言对象
	 */
	public List<Message> viewMessage(String messageId);

	/**
	 * 留言状态，对外网留言而言，通过则设为公开
	 * @param messageId待审核的留言
	 * @param isOpen审核状态(0不通过，1通过)
	 */
	public void toggleOpen(String messageId, int status);

	/**
	 * 审核留言
	 * @param messageId待审核的留言
	 * @param auditStatus审核状态(0未审，1不通过，2通过)
	 */
	public void auditMessage(List<String> ids, int auditStatus);

	/**
	 * 根据账号ID获取账号所属实体的名称及邮箱
	 * @param accountId 账号ID
	 * @return entityNameEmail 账号所属实体名称及邮箱
	 */
	public String[] getEntityNameEmail(String accountId);

	/**
	 * 根据留言ID获得需要发送邮件的列表
	 * @param messageId留言ID
	 * @return 待发送邮件的信息列表
	 */
	public Properties getParticipants(String messageId);
}