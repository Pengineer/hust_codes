package csdc.action.system.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.bean.Message;
import csdc.tool.InputValidate;
import csdc.tool.RequestIP;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MessageInfo;
import csdc.tool.mail.MailController;

public class Inner extends MessageAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select m.id, m.title, m.authorName, m.createDate, m.viewCount, COUNT(*), lastm.authorName, lastm.createDate, m.auditStatus from Message m, Message lastm, Message allm where m.id = m.replyTopic.id and m.id = allm.replyTopic.id ";
	private static final String GROUP_BY = " group by m.id, m.title, m.authorName, m.createDate, m.viewCount, lastm.authorName, lastm.createDate, m.auditStatus having lastm.createDate = MAX(allm.createDate) ";
	private static final String TMP_MESSAGE_ID = "messageId";
	private static final String[] COLUMN = {
		"m.title",
		"m.authorName, m.title",
		"m.createDate, m.title",
		"m.viewCount, m.title",
		"COUNT(*), m.title",
		"lastm.createDate desc, m.title",
		"m.auditStatus, m.title"
	};// 排序列
	private static final String PAGE_NAME = "messageInnerPage";// 列表页面名称
	
	private String keyword1, keyword2, keyword3, keyword4;// 用于高级检索
	private Date startDate, endDate;// 用于高级检索
	private int status;// 留言入口(0--添加,1--回复,2--引用)；审核状态
	private int type,isOpen;
	private int anonymous, auditStatus;// 是否匿名留言, 审核状态
	private String contentHeader;// 回复留言头
	private String[] sendList;// 发送的邮件列表
	private Message replyMessage;// 回帖
	private Properties participants; //本帖参与者的姓名、邮箱信息
	private Mail mail;

	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private MailController mailController;

	protected InputValidate inputValidate=new InputValidate();//校验工具类

	public InputValidate getInputValidate() {
		return inputValidate;
	}
	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}
	/**
	 * 进入添加留言页面
	 * @return
	 */
	public String toAdd() {
		message = new Message();
		String[] tmp = messageService.getEntityNameEmail(loginer.getCurrentAccountId());
		if (tmp != null) {
			message.setAuthorName(tmp[0]);
			message.setEmail(tmp[1]);
		}
		if (status == 1 || status == 2) {
			replyMessage = (Message) dao.query(Message.class, entityId);
			message.setIsOpen(replyMessage.getIsOpen());// 回帖的公开性默认与被回复者同
			participants = messageService.getParticipants(entityId);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String add() {
		message.setAccount(loginer.getAccount());// 发件者
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		if (anonymous == 1){
			message.setAuthorName("匿名");
		} else {
			message.setAuthorName(accountBelong);
		}
		request = ServletActionContext.getRequest();
		message.setIp(RequestIP.getRequestIp(request));

		entityId = messageService.addMessage(message);

		if (message.getReplyTopic() == null || message.getReplyTopic().getId().isEmpty()) {// 如果是添加留言
			message.setReplyTopic(message);
		} else {
			entityId = message.getReplyTopic().getId();// 记录主贴ID
		}
		
		message.setContent((contentHeader == null ? "" : contentHeader) + message.getContent());
		message.setAuditStatus(2);// 内网留言默认审核通过
		
		dao.modify(message);

		if(mail.getSendTo() != null){
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						mail.setSendTo(mail.getSendTo());
						mail.setReplyTo("serv@csdc.info");// 认证地址
						addEmail();
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});
			mailController.send(mail.getId());// 发送邮件
		}
		return SUCCESS;
	}

	/*
	 * 组装邮件
	 */
	public void addEmail()throws Exception {
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		mail.setSubject("[SMDB] " + message.getTitle());
		if(mail.getSubject().length() > 100){
			mail.setSubject(mail.getSubject().substring(0, 100));
		}
		mail.setBody("<head><style type=\"text/css\">.blockquote {background-color:white;border-color:#D5E5E8;border-style: solid;border-width: 1px 1px 1px 3px;color:#81888C;margin: 5px 5px 5px 5px;padding: 5px 5px 5px 5px;width: 80%;}.reply {color: #81888C;}</style></head>" + message.getContent());
		mail.setIsHtml(1);
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);// 创建时间
		mail.setAccountBelong(accountBelong);
		dao.add(mail); // 向数据库添加邮件记录
		
	}
	
	
	/**
	 * 添加留言校验
	 */
	public void validateAdd() {
		if (message.getTitle() == null || message.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TITLE_NULL);
		} else if (message.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TITLE_ILLEGAL);
		}
		if (!inputValidate.checkEmail(mail.getSendTo())){
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
		}
		if (message.getContent() == null || message.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_BODY_NULL);
		}
	}


	/**
	 * 进入修改留言页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toModify() {
		Map session = ActionContext.getContext().getSession();
		message = (Message) dao.query(Message.class, entityId);
		session.put(TMP_MESSAGE_ID, message.getId());
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
	@SuppressWarnings("unchecked")
	@Transactional
	public String modify() {
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get(TMP_MESSAGE_ID);
		String[] tmp = messageService.getEntityNameEmail(loginer.getCurrentAccountId());
		entityId = messageService.modifyMessage(entityId, message, tmp);
		session.remove(TMP_MESSAGE_ID);
		return SUCCESS;
	}

	/**
	 * 修改新闻校验
	 */
	public void validateModify() {
		if (message.getTitle() == null || message.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TITLE_NULL);
		} else if (message.getTitle().trim().length() > 40) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TITLE_ILLEGAL);
		}
		if (!inputValidate.checkEmail(mail.getSendTo())){
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_EMAIL_ILLEGAL);
		}
		if (message.getContent() == null || message.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_BODY_NULL);
		}
	}

	/**
	 * 进入查看
	 */
	public String toView() {
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
		messages = messageService.viewMessage(entityId);// 主贴及其回复
		message.setViewCount(message.getViewCount() + 1);
//		dao.modify(message);

		ArrayList innerOrOuter = new ArrayList();
		for (int i = 0; i < messages.size(); i++) {
			Account account = ((Message)messages.get(i)).getAccount();
			innerOrOuter.add(account == null ? 0 : 1);
		}
		jsonMap.put("message", message);
		jsonMap.put("messages", messages);
		jsonMap.put("innerOrOuter", innerOrOuter);
//		backToList(pageName(), -1, entityId);
		return SUCCESS;
	}

	/**
	 * 删除留言
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public String delete() {
		message = (Message) dao.query(Message.class, entityIds.get(0));
		if (message.getId().equals(message.getReplyTopic().getId())) {// 如果删除的是主贴
			jsonMap.put("type", 1);
		} else {
			jsonMap.put("type", 0);
		}
		messageService.deleteMessage(entityIds);
//		if (type == 1) {
//			backToList(pageName(), pageNumber, null);
//		} else {
//			backToList(pageName(), -1, entityIds.get(0));
//		}
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
	 * 审核留言
	 * @return
	 */
	public String toggleOpen() {
		messageService.toggleOpen(entityId, status);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void validatetoggleOpen() {
		if (entityId == null || entityIds.isEmpty()) {
			jsonMap.put(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TOGGLE_OPEN_NULL);
			this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_TOGGLE_OPEN_NULL);
		}
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
		hql.append(HQL);
		hql.append(" and ");
		if (searchType == 1) {// 按留言标题检索
			hql.append(" LOWER(m.title) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按留言正文检索
			hql.append(" LOWER(m.content) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按留言者检索
			hql.append(" LOWER(m.authorName) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(m.title) like :keyword or LOWER(m.content) like :keyword or LOWER(m.authorName) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		if(loginer.getAccount().getType().compareTo(AccountType.MINISTRY) > 0){
			hql.append(" and m.auditStatus = 2 ");
		}
		hql.append(GROUP_BY);
		return new Object[]{
			hql.toString(),
			map,
			5,
			null
		};
//		this.simpleSearch(hql, map, ORDER_BY, 5, 0, PAGE_NAME);
//		return SUCCESS;
	}

	/**
	 * 进入高级检索页面
	 */
//	public String toAdvSearch() {
//		return SUCCESS;
//	}

	/**
	 * 高级检索
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword1 != null && !keyword1.isEmpty()) {
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(m.title) like :keyword1 ");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(m.content) like :keyword2 ");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {
			keyword3 = keyword3.toLowerCase();
			hql.append(" and LOWER(m.authorName) like :keyword3 ");
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {
			keyword4 = keyword4.toLowerCase();
			hql.append(" and LOWER(m.type.id) = :keyword4 ");
			map.put("keyword4", keyword4);
		}
		if(startDate == null){
			
		}
		if (startDate != null) {
			hql.append(" and m.createDate > :startDate");
			map.put("startDate", startDate);
		}
		if (endDate != null) {
			hql.append(" and m.createDate < :endDate");
			map.put("endDate", endDate);
		}
		hql.append(GROUP_BY);
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
//		this.advSearch(hql, map, ORDER_BY, 4, 0, PAGE_NAME);
//		return SUCCESS;
	}

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != keyword1 && !keyword1.isEmpty()){
			searchQuery.put("title", keyword1);
		}
		if(null != keyword2 && !keyword2.isEmpty()){
			searchQuery.put("body", keyword2);
		}
		if (null != keyword3 && !keyword3.isEmpty()) {
			searchQuery.put("author", keyword3);
		}
		if (null != keyword4 && !keyword4.isEmpty()) {
			searchQuery.put("type", keyword4);
		}
		
		if(isOpen == 0 || isOpen == 1){
			searchQuery.put("IsOpen", isOpen);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
	}
	
//	/**
//	 * 高级检索留言校验
//	 */
//	public void validateAdvSearch() {
//		if(startDate != null && endDate != null) {
//			if (startDate.compareTo(endDate) >= 0) {
//				this.addFieldError(GlobalInfo.ERROR_INFO, MessageInfo.ERROR_DATE_ILLEGAL);
//			}
//		}
//	}
//	
	/**
	 * 进入审核页面
	 */
	public String toAudit() {
		return SUCCESS;
	}
	
	/**
	 * 审核留言
	 */
	public String audit() {
		messageService.auditMessage(entityIds, auditStatus);
		return SUCCESS;
	}

	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContentHeader() {
		return contentHeader;
	}
	public void setContentHeader(String contentHeader) {
		this.contentHeader = contentHeader;
	}
	public String[] getSendList() {
		return sendList;
	}
	public void setSendList(String[] sendList) {
		this.sendList = sendList;
	}
	public Message getReplyMessage() {
		return replyMessage;
	}
	public Properties getParticipants() {
		return participants;
	}
	public void setParticipants(Properties participants) {
		this.participants = participants;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public int getAnonymous() {
		return anonymous;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	
	public String pageName(){
		return PAGE_NAME;
	}
	public String[] column(){
		return COLUMN;
	}
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public String HQL() {
		return HQL;
	}
}
