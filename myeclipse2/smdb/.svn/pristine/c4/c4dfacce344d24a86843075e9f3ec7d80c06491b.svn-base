package csdc.service.imp;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.Message;
import csdc.bean.Passport;
import csdc.service.IMessageService;
import csdc.tool.bean.AccountType;

@Transactional
public class MessageService extends BaseService implements IMessageService {

	/**
	 * 添加留言
	 * @param message留言对象
	 * @return messageId留言对象ID
	 */
	public String addMessage(Message message) {
		message.setCreateDate(new Date());
		message.setLog(null);
		message.setSendEmail(0);
		message.setViewCount(0);

		if (message.getIp().equals("localhost") || message.getIp().equals("127.0.0.1") || message.getIp().equals("0:0:0:0:0:0:0:1")) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				message.setIp(addr.getHostAddress());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		String messageId = dao.add(message);
		return messageId;
	}

	/**
	 * 修改留言
	 * @param orgMessageId原始留言ID
	 * @param message待修改的留言对象
	 * @param modifier修改者信息
	 * @return 返回主贴ID
	 */
	public String modifyMessage(String orgMessageId, Message message, String[] modifier) {
		Message orgMessage = (Message) dao.query(Message.class, orgMessageId);
		orgMessage.setTitle(message.getTitle());
		orgMessage.setContent(message.getContent());
		orgMessage.setType(message.getType());
		orgMessage.setEmail(message.getEmail());
		orgMessage.setIsOpen(message.getIsOpen());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmpLog = orgMessage.getLog();
		orgMessage.setLog((tmpLog == null ? "" : tmpLog) + "[本帖由 " + modifier[0] + " 于 " + df.format(new Date()) + " 编辑]<br />");

		dao.modify(orgMessage);

		String entityId = orgMessage.getId().equals(orgMessage.getReplyTopic().getId()) ? orgMessage.getId() : orgMessage.getReplyTopic().getId();

		return entityId;
	}

	/**
	 * 删除留言
	 * @param messageIds待删除的留言ID集合
	 */
	public void deleteMessage(List<String> messageIds) {
		for (String entityId : messageIds) {
			dao.delete(Message.class, entityId);
		}
	}

	/**
	 * 查看留言
	 * @param messageId待查看的新闻ID
	 * @return message留言对象
	 */
	@SuppressWarnings("unchecked")
	public List<Message> viewMessage(String messageId) {
		Map map = new HashMap();
		map.put("topicId", messageId);
		List<Message> messages = dao.query("select m from Message m where m.replyTopic.id = :topicId order by m.createDate asc", map);
		return messages;
	}

	/**
	 * 留言状态，对外网留言而言，通过则设为公开
	 * @param messageId待审核的留言
	 * @param isOpen审核状态(0不通过，1通过)
	 */
	public void toggleOpen(String messageId, int status) {
		if (messageId != null && (status == 1 || status == 0)) {
			Message message = (Message) dao.query(Message.class, messageId);
			message.setIsOpen(status);
			dao.modify(message);
		}
	}

	/**
	 * 审核留言
	 * @param messageId待审核的留言
	 * @param auditStatus审核状态(0未审，1不通过，2通过)
	 */
	public void auditMessage(List<String> ids, int auditStatus) {
		Message message;
		for (int i = 0; i < ids.size(); i++) {// 遍历所有留言，设置审核状态
			message = (Message) dao.query(Message.class, ids.get(i));
			message.setAuditStatus(auditStatus);
			dao.modify(message);
		}
	}
	
	/**
	 * 根据账号ID获取账号所属实体的名称及邮箱
	 * @param accountId 账号ID
	 * @return entityNameEmail 账号所属实体名称及邮箱
	 */
	@SuppressWarnings("unchecked")
	public String[] getEntityNameEmail(String accountId) {
		String[] tmp = new String[]{"",""};
		
		Account account = (Account) dao.query(Account.class, accountId);
		
		if (account != null) {
			AccountType type = account.getType();
			int isPrincipal = account.getIsPrincipal();
			String name = null;
			Passport passport = (Passport)dao.query(Passport.class, account.getPassport().getId());
			if(passport != null) {
				name = passport.getName();
			}
			tmp[0] = name;
	
			List<Object> re = null;
			Map map = new HashMap();
	
			if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员没有所属信息，返回账号名称
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER)) {// 专家、教师返回人员姓名
				re = dao.query("select p.name, p.email from Person p where p.id = :entityId", map);
				map.put("entityId", this.getBelongIdByAccount(account));
			} else {
				if (isPrincipal == 1) {
					if (type.equals(AccountType.DEPARTMENT)) {// 院系
						re = dao.query("select d.name, d.email from Department d where d.id = :entityId", map);
						map.put("entityId", account.getDepartment().getId());
					} else if (type.equals(AccountType.INSTITUTE)) {// 基地
						re = dao.query("select i.name, i.email from Institute i where i.id = :entityId", map);
						map.put("entityId", account.getInstitute().getId());
					} else {
						re = dao.query("select a.sname, a.semail from Agency a where a.id = :entityId", map);
						map.put("entityId", account.getAgency().getId());
					}
				} else {// 管理人员返回人员姓名
					re = dao.query("select p.name, p.email from Officer o left join o.person p where o.id = :entityId", map);
					map.put("entityId", this.getBelongIdByAccount(account));
				}
			}
			if (re != null && !re.isEmpty()) {
				Object[] obj = (Object[]) re.get(0);
				if (obj[0] != null) {
					tmp[0] = obj[0].toString();
				}
				if (obj[1] != null) {
					tmp[1] = obj[1].toString();
				}
			}
		}
		return tmp;
	}

	/**
	 * 根据留言ID获得需要发送邮件的列表
	 * @param id留言ID
	 * @return 待发送邮件的信息列表
	 */
	@SuppressWarnings("unchecked")
	public Properties getParticipants(String id) {
		Message message = (Message) dao.query(Message.class, id);
		Map map = new HashMap();
		map.put("topicid", message.getReplyTopic().getId());
		List<Object[]> list = dao.query("select message.email, message.authorName from Message message where message.email is not null and message.replyTopic.id = :topicid order by message.email, message.authorName", map);
		Set<String> nameSet = new TreeSet<String>();
		Properties ret = new Properties();
		ret.clear();
		String tmp = "";
		for (int i = 0; i < list.size(); i++){
			String email = (String) list.get(i)[0];
			String name = (String) list.get(i)[1];
			nameSet.add(name);
			if (i == list.size() - 1 || !email.equals((list.get(i + 1))[0])) {
				tmp = nameSet.toString();
				tmp = tmp.substring(1, tmp.length() - 1);
				ret.put(email, tmp + " <" + email + ">");
				nameSet = new TreeSet<String>();
			}
		}
		//System.out.println("list-length: " + list.size());
		return ret;
	}
}