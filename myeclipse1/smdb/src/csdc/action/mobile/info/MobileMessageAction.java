package csdc.action.mobile.info;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.action.mobile.MobileAction;
import csdc.bean.Mail;
import csdc.bean.Message;
import csdc.bean.SystemOption;
import csdc.service.IMessageService;
import csdc.service.IMobileInfoService;
import csdc.service.IMobileService;
import csdc.tool.RequestIP;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MessageInfo;
import csdc.tool.mail.MailController;

/**
 * mobile留言簿模块
 * @author suwb
 *
 */
public class MobileMessageAction extends MobileAction{
	
	private static final long serialVersionUID = 1L;
	private static final String PAGENAME = "mobileMessagePage"; 
	private final String MessageHql = "select m.id, m.title, m.authorName, m.createDate, m.auditStatus from Message m, Message lastm, Message allm where m.id = m.replyTopic.id and m.id = allm.replyTopic.id group by m.id, m.title, m.authorName, m.createDate, m.viewCount, lastm.authorName, lastm.createDate, m.auditStatus having lastm.createDate = MAX(allm.createDate)";
	private final String updateMessageHql = "select m.id, m.title, m.authorName, m.createDate, m.auditStatus from Message m, Message lastm, Message allm where m.id = m.replyTopic.id and m.id = allm.replyTopic.id and m.createDate >= :timestamp group by m.id, m.title, m.authorName, m.createDate, m.viewCount, lastm.authorName, lastm.createDate, m.auditStatus having lastm.createDate = MAX(allm.createDate)";
	@Autowired
	private IMobileInfoService mobileInfoService;
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private MailController mailController;
	@Autowired
	private IMobileService mobileService;
	@Autowired
	private IMessageService messageService;
	
	private Message message = new Message();// 留言对象
	private Message replyMessage;// 回帖
	private String contentHeader;// 回复留言头
	private int status;// 留言入口(0--添加,1--回复,2--引用)；审核状态

	//用于留言的添加修改和回复
	private String phone;//联系电话
	private int isOpen;//仅内部留言时可选
	private int isAnonymous;//仅内部留言时可选
	private String typeId;//留言类型id
	private String title;//留言标题
	private String content;//留言正文
	private String email;//邮箱
	private String name;//首页未登录留言时用户填写的发布者名称
	private int type;//类别[1：内部留言；2：外网留言；3：用户反馈]
	private String timestamp;//用于更新留言的时间戳
	
	/**
	 * 留言列表
	 * @return
	 */
	public String simpleSearch(){
		search(MessageHql, null);
		return SUCCESS;
	}
	
	/**
	 * 更新留言
	 * @return
	 * @throws ParseException 
	 */
	public String update() throws ParseException{
		HashMap map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date stampDate = df.parse(timestamp);
		map.put("timestamp", stampDate);
		search(updateMessageHql, map);
		return SUCCESS;
	}

	/**
	 * 外网留言
	 * @return
	 */
	public String toAddOuter(){
		Map<String,String> msgType = mobileService.getSystemOptionMapAsId("messageType", null);//获取留言类型的id与name对应关系
		jsonMap.put("mType", msgType);
		return SUCCESS;
	}
	
	/**
	 * 外网留言
	 * @return
	 */
	public String addOuter() {
		String mobileLoginTag = (String) session.get("mobileTag");
		if(mobileLoginTag.equals("android")){
			message.setSourceType(1);
		}else if(mobileLoginTag.equals("iphone")){
			message.setSourceType(2);
		}else jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_SOURCE_TYPE);
		request = ServletActionContext.getRequest();
		message.setIp(RequestIP.getRequestIp(request));
		message.setAuthorName(name);
		message.setIsOpen(0);
		message.setAuditStatus(0);
		SystemOption type = mobileInfoService.getSystemOptionById(typeId);
		message.setType(type);
		message.setCreateDate(new Date());
		message.setLog(null);
		message.setSendEmail(0);
		message.setViewCount(0);
		message.setTitle(title);
		message.setContent(content);
		message.setPhone(phone);
		if (email != null && Pattern.matches("[^\\s;；]+@[^\\s;；]+", email)){
			message.setEmail(email);		
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
			return INPUT;
		}
		dao.add(message);
		message.setReplyTopic(message);
		dao.modify(message);
		return SUCCESS;
	}
	/**
	 * 进入留言添加
	 * @return
	 */
	public String toAdd(){
		String[] tmp = messageService.getEntityNameEmail(loginer.getCurrentAccountId());//根据账号ID获取账号所属实体的名称及邮箱
		Map<String,String> msgType = mobileService.getSystemOptionMapAsId("messageType", null);//获取留言类型的id与name对应关系
		jsonMap.put("mType", msgType);
		jsonMap.put("entity", tmp);
		return SUCCESS;
	}
	
	/**
	 * 添加留言
	 * @return
	 */
	public String add() {
		String mobileLoginTag = (String) session.get("mobileTag");
		if(mobileLoginTag.equals("android")){
			message.setSourceType(1);
		}else if(mobileLoginTag.equals("iphone")){
			message.setSourceType(2);
		}else jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_SOURCE_TYPE);
		request = ServletActionContext.getRequest();
		message.setIp(RequestIP.getRequestIp(request));
		LoginInfo loginer = (LoginInfo) session.get(GlobalInfo.LOGINER);
		message.setAccount(loginer.getAccount());// 发件者
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		if (email != null && Pattern.matches("[^\\s;；]+@[^\\s;；]+", email)){
			message.setEmail(email);		
		}else{
			jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
			return INPUT;
		}
		if(type==1){//内网留言可选是否公开与匿名
			if (isAnonymous== 1){
				message.setAuthorName("匿名");
			} else {
				message.setAuthorName(accountBelong);
			}
			message.setIsOpen(isOpen);
			message.setAuditStatus(2);//内网留言默认审核通过
		}else if(type==3){//用户反馈不公开不匿名，发邮件至我的邮箱（苏文波）
			message.setAuthorName(accountBelong);
			message.setIsOpen(0);
			message.setAuditStatus(0);
			
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						addEmail(content, email);
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});
			String id = (String) session.get("mailId");
			mailController.send(id);// 发送邮件
		}
		
		SystemOption type = dao.query(SystemOption.class, typeId);//通过typeId获取SystemOption对象
		message.setType(type);
		message.setCreateDate(new Date());
		message.setLog(null);
		message.setSendEmail(0);
		message.setViewCount(0);
		message.setTitle(title);
		message.setContent(content);
		message.setPhone(phone);
		dao.add(message);
		message.setReplyTopic(message);
		dao.modify(message);
		return SUCCESS;
	}
	
	public String toReply(){
		String[] tmp = messageService.getEntityNameEmail(loginer.getCurrentAccountId());//根据账号ID获取账号所属实体的名称及邮箱
		Map<String,String> msgType = mobileService.getSystemOptionMapAsId("messageType", null);//获取留言类型的id与name对应关系
		jsonMap.put("mType", msgType);
		jsonMap.put("entity", tmp);
		return SUCCESS;
	}
	
	/**
	 * 回复留言[移动端暂时没有是否发送邮件的选项，默认不发送]
	 * @return
	 */
	public String reply() {
		String mobileLoginTag = (String) session.get("mobileTag");
		if(mobileLoginTag.equals("android")){
			message.setSourceType(1);
		}else if(mobileLoginTag.equals("iphone")){
			message.setSourceType(2);
		}else jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_SOURCE_TYPE);
		request = ServletActionContext.getRequest();
		message.setIp(RequestIP.getRequestIp(request));
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
		
		SystemOption type = mobileInfoService.getSystemOptionById(typeId);
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
		
		replyMessage = (Message) dao.query(Message.class, entityId);
//			participants = this.getParticipants(entityId);
		if (status == 1) {
			contentHeader = "<div class = \"reply\">回复 " + replyMessage.getAuthorName() + ":<br /></div>";
		} else if (status == 2){
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
		message.setReplyTopic(replyMessage.getReplyTopic());
		message.setContent((contentHeader == null ? "" : contentHeader) + message.getContent());
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
		Map<String,String> msgType = mobileService.getSystemOptionMapAsId("messageType", null);//获取留言类型的id与name对应关系
		jsonMap.put("mType", msgType);
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
		String[] tmp = messageService.getEntityNameEmail(loginer.getCurrentAccountId());
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
		Message message = dao.query(Message.class, entityId);
		message.setViewCount(message.getViewCount() + 1);
		dao.modify(message);
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
	 * 组装邮件
	 */
	public void addEmail(String content, String email)throws Exception {
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		Mail mail = new Mail();
		mail.setSendTo("913125735@qq.com");//用户反馈信息直接发送至我的邮箱(苏文波)
		mail.setReplyTo("serv@csdc.info");//认证地址
		mail.setSubject("[SMDB移动端用户反馈专用]来自  "+email+" 的用户反馈内容");
		mail.setBody(content);
		mail.setAccountBelong(accountBelong);
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);//创建时间
		mail.setIsHtml(1);//必须以html形式发送，否则中文乱码
		mail.setFinishDate(null);
		mail.setSendTimes(0);
		mail.setStatus(0);
		dao.add(mail);//向数据库添加邮件记录
		session.put("mailId", mail.getId());
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
