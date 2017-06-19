package csdc.action.mobile.info;

//import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.action.mobile.MobileAction;
import csdc.bean.Account;
import csdc.bean.Message;
import csdc.bean.Passport;
import csdc.bean.SystemOption;
import csdc.service.IMobileService;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MessageInfo;

/**
 * mobile留言簿模块
 * @author suwb
 *
 */
public class MobileMessageAction extends MobileAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileMessagePage"; 
	
	@Autowired
	private IMobileService mobileService;
	
	private Message message = new Message();// 留言对象
	private Message replyMessage;// 回帖
//	private Message mainMessage;// 回帖
	private String contentHeader;// 回复留言头
	private int status;// 留言入口(0--添加,1--回复,2--引用)；审核状态
//	private static final String TMP_MESSAGE_ID = "messageId";
//	
//	private HttpServletRequest request;
//	private HttpSession session;
//	private ServletContext application;

	//用于留言的添加修改和回复
	private String phone;
	private int isOpen;
	private int isAnonymous;
	private String typeId;
	private String title;
	private String content;
	private String email;
	
	/**
	 * 留言列表
	 * @return
	 */
	public String simpleSearch(){
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		hql.append("select m.id, m.title, m.authorName, m.createDate, m.auditStatus from Message m, Message lastm, Message allm where m.id = m.replyTopic.id and m.id = allm.replyTopic.id ");
		hql.append(" group by m.id, m.title, m.authorName, m.createDate, m.viewCount, lastm.authorName, lastm.createDate, m.auditStatus having lastm.createDate = MAX(allm.createDate) ");
		search(hql, map);
		return SUCCESS;
	}

	/**
	 * 添加/回复 检验
	 */
	public void validateToAdd() {
		if ((status == 1 || status == 2) && entityId == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_REPLY_NULL);
		}
	}

	public String toAdd(){
		Map<String,String> msgType = mobileService.getSystemOptionMap("messageType", null);//获取留言类型的id与name对应关系
		Set<String> mType = msgType.keySet();//获取id
		if(msgType.values().toString().equals("[社科管理留言, 系统操作留言]")){
			jsonMap.put("mType", mType);			
		}else {
			Object[] a = mType.toArray();
			String b = a[0].toString();
			String c = a[1].toString();
			String[] d = {c , b};
			jsonMap.put("mType", d);
		}
		return SUCCESS;
	}
	/**
	 * 添加/回复 留言
	 * @return
	 */
	public String add() {
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		message.setAccount(loginer.getAccount());// 发件者
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		if (isAnonymous == 1){
			message.setAuthorName("匿名");
		} else {
			message.setAuthorName(accountBelong);
		}
//		request = ServletActionContext.getRequest();
		
		SystemOption type = dao.query(SystemOption.class, typeId);//通过typeId获取SystemOption对象
		message.setType(type);
			
		message.setCreateDate(new Date());
		message.setLog(null);
		message.setSendEmail(0);
		message.setViewCount(0);
		message.setTitle(title);
		message.setContent(content);
//		message.setEmail(email);
		message.setIsOpen(isOpen);
		message.setPhone(phone);
		if (email != null && Pattern.matches("[^\\s;；]+@[^\\s;；]+", email)){
			message.setEmail(email);		
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
			return INPUT;
		}
		message.setAuditStatus(2);// 内网留言默认审核通过
		dao.add(message);

		if (status == 0) {// 如果是添加留言
			message.setReplyTopic(message);
		}else if(status == 1 || status ==2){//回复or引用
			replyMessage = (Message) dao.query(Message.class, entityId);
//			participants = this.getParticipants(entityId);
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
			message.setReplyMessage(replyMessage);
//			mainMessage = dao.query(Message.class, replyMessage.getReplyTopic().getId());
			message.setReplyTopic(replyMessage.getReplyTopic());
//			entityId = message.getReplyTopic().getId();// 记录主贴ID
			message.setContent((contentHeader == null ? "" : contentHeader) + message.getContent());
		}else return INPUT;
		
		dao.modify(message);
		return SUCCESS;
	}
	
	/**
	 * 进入修改留言页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toModify() {
		String HQL ="select m.id, m.title, m.email, m.phone, so.id, m.isOpen, m.authorName, m.content from Message m left join m.type so where m.id = :entityId"; 
		HashMap parMap = new HashMap();
		parMap.put("entityId", entityId);
		List<Object[]> dataList = dao.query(HQL, parMap) ;
		jsonMap.put("message", dataList);
		Map<String,String> msgType = mobileService.getSystemOptionMap("messageType", null);//获取留言类型的id与name对应关系
		Set<String> mType = msgType.keySet();
		if(msgType.values().toString().equals("[社科管理留言, 系统操作留言]")){
			jsonMap.put("mType", mType);			
		}else {
			Object[] a = mType.toArray();
			String b = a[0].toString();
			String c = a[1].toString();
			String[] d = {c , b};
			jsonMap.put("mType", d);
		}
//		Map session = ActionContext.getContext().getSession();
//		message = (Message) dao.query(Message.class, entityId);
//		String systemOptionId = message.getType().getId();
//		session.put(TMP_MESSAGE_ID, message.getId());
		return SUCCESS;
	}

	/**
	 * 修改留言校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改留言
	 * @return
	 */
	public String modify() {
		String[] tmp = this.getEntityNameEmail(loginer.getCurrentAccountId());		
		message = (Message) dao.query(Message.class, entityId);
		message.setAccount(loginer.getAccount());// 发件者
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		if (isAnonymous == 1){
			message.setAuthorName("匿名");
		} else {
			message.setAuthorName(accountBelong);
		}
		SystemOption type = dao.query(SystemOption.class, typeId);//通过typeId获取SystemOption对象
		message.setType(type);
		
		message.setTitle(title);
		message.setContent(content);
		if (email != null && Pattern.matches("[^\\s;；]+@[^\\s;；]+", email)){
			message.setEmail(email);		
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
			return INPUT;
		}
		message.setIsOpen(isOpen);
		message.setPhone(phone);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmpLog = new String();
		message.setLog((tmpLog == null ? "" : tmpLog) + "[本帖由 " + tmp[0] + " 于 " + df.format(new Date()) + " 编辑]<br />");

		dao.modify(message);
		return SUCCESS;
	}
	
	/**
	 * 查看留言
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		ArrayList<List> messages = new ArrayList<List>();
		List<String> entityIds = viewEntityId(entityId);// 主贴及其回复
		for(String entityId : entityIds){
			Map map = new HashMap();
			map.put("entityId", entityId);
			messages.addAll(dao.query("select m.id, m.title, m.authorName, m.createDate, m.content, m.auditStatus from Message m where m.id = :entityId", map));
		}
		jsonMap.put("messages", messages);
		return SUCCESS;
	}
	
	/**
	 * 删除留言
	 * @return
	 */
	public String deleteMessage() {
		message = (Message) dao.query(Message.class, entityId);
		if (message.getId().equals(message.getReplyTopic().getId())) {// 如果删除的是主贴
			List<String> entityIds = viewEntityId(entityId);
			for(String entityId : entityIds){
				dao.delete(Message.class, entityId);
			}
		}else{
			dao.delete(Message.class, entityId);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除留言校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_DELETE_NULL);
		}
	}	
	
	/**
	 * 由留言主贴id得到本帖所有id
	 * @param messageId待查看的留言ID
	 * @return entityIds本帖所有留言id
	 */
	@SuppressWarnings("unchecked")
	public List<String> viewEntityId(String messageId) {
		Map map = new HashMap();
		map.put("topicId", messageId);
		List<String> entityIds = dao.query("select m.id from Message m where m.replyTopic.id = :topicId order by m.createDate asc", map);
		return entityIds;
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
			String belongId = baseService.getBelongIdByAccount(account);
			String name = null;
			Passport passport = (Passport)dao.query(Passport.class, account.getPassport().getId());
			if(passport != null) {
				name = passport.getName();
			}
			tmp[0] = name;
	
			List<Object> re = null;
			Map map = new HashMap();
			map.put("entityId", belongId);
	
			if (type.equals(AccountType.ADMINISTRATOR)) {// 系统管理员没有所属信息，返回账号名称
			} else if (type.equals(AccountType.EXPERT) || type.equals(AccountType.TEACHER)) {// 专家、教师返回人员姓名
				re = dao.query("select p.name, p.email from Person p where p.id = :entityId", map);
			} else {
				if (isPrincipal == 1) {
					if (type.equals(AccountType.DEPARTMENT)) {// 院系
						re = dao.query("select d.name, d.email from Department d where d.id = :entityId", map);
					} else if (type.equals(AccountType.INSTITUTE)) {// 基地
						re = dao.query("select i.name, i.email from Institute i where i.id = :entityId", map);
					} else {
						re = dao.query("select a.sname, a.semail from Agency a where a.id = :entityId", map);
					}
				} else {// 管理人员返回人员姓名
					re = dao.query("select p.name, p.email from Officer o left join o.person p where o.id = :entityId", map);
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
	
	public void setIsOpen(int isOpen){
		this.isOpen = isOpen;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getIsOpen(){
		return isOpen;
	}
	
	public void setIsAnonymous(int isAnonymous){
		this.isAnonymous = isAnonymous;
	}
	
	public int getIsAnonymous(){
		return isAnonymous;
	}
	
	public void setTypeId(String typeId){
		this.typeId = typeId;
	}
	
	public String getTypeId(){
		return typeId;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public IMobileService getMobileService() {
		return mobileService;
	}

	public void setMobileService(IMobileService mobileService) {
		this.mobileService = mobileService;
	}
	
	@Override
	public String pageName() {
		return PAGENAME;
	}
}
