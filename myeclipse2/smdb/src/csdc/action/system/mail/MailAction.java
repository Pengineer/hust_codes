package csdc.action.system.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.action.BaseAction;
import csdc.bean.Mail;
import csdc.bean.Passport;
import csdc.service.IMailService;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.MailInfo;
import csdc.tool.mail.MailController;
import csdc.tool.mail.MailTask;
import csdc.tool.mail.SendUndoneMails;

/**
 * 系统邮件管理,暂时只实现系统邮件的相关功能
 * 
 * @author 龚凡
 */
public class MailAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final String HQL = "select m.id, m.subject, a.id, p.name, m.createDate, m.finishDate, m.status, m.accountBelong from Mail m left join m.account a left join a.passport p where 1=1 ";// 查询语句主体
	private static final String[] COLUMN = { "m.subject", "p.name, m.subject", "m.createDate desc, m.subject", "m.finishDate, m.subject", "m.status, m.subject", "m.accountBelong, m.subject" };// 用于拼接的排序列
	private static final String PAGE_NAME = "mailPage";// 列表页面名称
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	private static final String PAGE_BUFFER_ID = "m.id";// 上下条查看时用于查找缓存的字段
	private Mail mail;

	@Autowired
	private MailController mailController;
	@Autowired
	private SendUndoneMails sendUndoneMails;
	@Autowired
	protected IUploadService uploadService; // 异步文件上传
	private IMailService mailService;// 邮件管理接口
	private String keyword1, keyword2, keyword3, keyword4;// 高级检索不同字段关键字
	private Date createDate1, createDate2;// 创建时间
	private int status;// 邮件状态，附件索引号
	// 异步文件上传所需
	private String[] fileIds;// 标题提交上来的特征码list
	private String uploadKey;// 文件上传授权码
	private String attachmentName;// 附件下载文件名
	private InputStream targetFile;// 附件下载流
	private List recieverType;// 收件人群

	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	private TransactionTemplate txTemplateRequiresNew;

	public String pageName() {
		return PAGE_NAME;
	}

	public String[] column() {
		return COLUMN;
	}

	public String HQL() {
		return HQL;
	}

	public String dateFormat() {
		return MailAction.DATE_FORMAT;
	}

	public String pageBufferId() {
		return MailAction.PAGE_BUFFER_ID;
	}

	/**
	 * 进入添加页面
	 * 
	 * @return
	 */
	public String toAdd() {
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 添加邮件
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String add() throws Exception {
		txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {// 获取收件人地址
					String to = mail.getSendTo();
					if (to.equals("请填写地址，用分号隔开")) {
						to = "";
					}
					List<String> emails = mailService.generateEmailList(recieverType);
					String address = getDistinctAddresses(emails);
					String sTo = distinctAddresses(to).get(0);
					String sendTo = sTo + address;
					mail.setSendTo(sendTo);
					mail.setSubject("[SMDB] " + mail.getSubject());
					mail.setReplyTo("serv@csdc.info");// 认证地址
					addEmail();// 组装邮件
				} catch (Exception e) {
					status.setRollbackOnly();
				}
			}
		});
			mailController.send(mail.getId());
			return SUCCESS;
	}
	/**
	 * 取消发送邮件
	 */
	public String cancel(){
		mail = (Mail) dao.query(Mail.class, entityId);
		mail.setStatus(4);
		dao.modify(mail);
		jsonMap.put(GlobalInfo.ERROR_INFO, "邮件已经被永久取消发送！");
    	return SUCCESS;
	}
	/**
	 * 判重地址列表中的重复项、去除非法地址
	 */
	private String getDistinctAddresses(List<String> address){
		if (address == null){
			address = new ArrayList<String>();
		}
		
		Set<String> result = new TreeSet<String>(address);
		/*
		 * 地址允许包含分号和空白字符之外的所有字符
		 * 但实际上合法的email地址规则(http://en.wikipedia.org/wiki/Email_address#Syntax)
		 * 非常难判断，于是使用一个较宽松的规则
		 */
		StringBuffer resultAddress = new StringBuffer();
		for (String string : result) {
			if(Pattern.matches("[^\\s;；]+@[^\\s;；]+", string)){
				resultAddress.append(string);
			}
		}
		return resultAddress.toString();
	}
	
	/**
	 * 判重地址列表中的重复项、去除非法地址
	 */
	private List<String> distinctAddresses(String address){
		if (address == null){
			address = "";
		}
		Set<String> result = new TreeSet<String>();
		/*
		 * 地址允许包含分号和空白字符之外的所有字符
		 * 但实际上合法的email地址规则(http://en.wikipedia.org/wiki/Email_address#Syntax)
		 * 非常难判断，于是使用一个较宽松的规则
		 */
		Matcher matcher = Pattern.compile("[^\\s;；]+@[^\\s;；]+").matcher(address);
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return new ArrayList<String>(result);
	}

	/**
	 * 组装邮件
	 * 
	 * @throws Exception
	 */
	private void addEmail() throws Exception {
		mail.setAccount(loginer.getAccount());// 发件者
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);
		mail.setAccountBelong(accountBelong);
		mail.setIsHtml(1);
		//邮件内容自动换行，后台将"\r\n"替换成 "<br/>"
		String  bodyAdd = mail.getBody();
		String  body = bodyAdd.replaceAll("\r\n", "<br/>");
		mail.setBody(body);
		entityId = dao.add(mail); // 向数据库添加邮件记录
		//处理附件
		String groupId = "file_add";
		String savePath = (String) ApplicationContainer.sc.getAttribute("MailFileUploadPath");
		List<String> attachments = new ArrayList<String>();
		List<String> attachmentNames = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = FileTool.getTrueFilename(savePath, entityId, fileRecord.getOriginal());//文件在数据库中的存储地址（包括存储路径和原有的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			attachments.add(newFilePath);
			attachmentNames.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		mail.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
		mail.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
		dao.merge(mail);
		uploadService.flush(groupId);

	}

	public void addEmaillValidate() {
		if (mail.getSubject().trim() == null || mail.getSubject().trim().isEmpty()) {// 邮件主题不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SUBJECT_NULL);
		} else if (mail.getSubject().length() > 100) {// 邮件主题不得超过100个字符
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SUBJECT_ILLEGAL);
		}
		if (mail.getBody().trim() == null || mail.getBody().trim().isEmpty()) {// 邮件正文不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_BODY_NULL);
		}
		if (mail.getSendTo().trim() == null || mail.getSendTo().trim().isEmpty() || recieverType == null) {// 收件人不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SEND_TO_NULL);
		} else if (recieverType.contains("others")) {
			String[] addr = mail.getSendTo().split("; ");
			for (int i = 0; i < addr.length; i++) {// 校验邮箱的写法是否有错
				if (!Pattern.matches("^.{1,99}@.{2,100}$", addr[i])) {
					this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SEND_TO_ILLEGAL);
					break;
				}
			}
		}
	}

	/**
	 * 添加邮件校验
	 */
	public void validateAdd() {
		this.addEmaillValidate();
	}

	/**
	 * 进入查看页面
	 * 
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 进入查看页面校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 查看邮件ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_VIEW_NULL);
		}
	}

	/**
	 * 查看邮件
	 * 
	 * @throws IOException
	 * @throws IOException
	 */
	public String view() throws IOException {
		mail = mailService.viewMail(entityId);
		if (mail == null) {// 校验邮件是否存在
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_MAIL_NULL);
			return INPUT;
		} else {
			if (mail.getAttachmentName() != null && mail.getAttachment() != null) {// 处理附件名称用于显示，去掉最后一个"; "
				mail.setAttachmentName(mail.getAttachmentName().substring(0, mail.getAttachmentName().length()));
				// 获取存在附件大小
				List<String> attachmentSizeList = new ArrayList<String>();
				String[] attachPath = mail.getAttachment().split("; ");
				InputStream is = null;
				for (String path : attachPath) {
					is = ServletActionContext.getServletContext().getResourceAsStream(path);
					if (null != is) {
						attachmentSizeList.add(baseService.accquireFileSize(is.available()));
					} else {// 附件不存在
						attachmentSizeList.add(null);
					}
					jsonMap.put("attachmentSizeList", attachmentSizeList);
				}
			}
			mail.setAttachment(null);
			jsonMap.put("mail", mail);
			if (null != mail.getAccount()) {
				Passport passport = (Passport) dao.query(Passport.class, mail.getAccount().getPassport().getId());
				jsonMap.put("accountname", passport == null ? null : passport.getName());
			} else {
				jsonMap.put("accountname", null);
			}
			jsonMap.put("accountid", mail.getAccount() == null ? null : mail.getAccount().getId());
			jsonMap.put("accounttype", mail.getAccount() == null ? null : mail.getAccount().getType());
			jsonMap.put("isPrincipal", mail.getAccount() == null ? null : mail.getAccount().getIsPrincipal());
			return SUCCESS;
		}
	}

	/**
	 * 查看校验
	 */
	public String validateView() {
		if (entityId == null || entityId.trim().isEmpty()) {// 查看邮件ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_VIEW_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	public String download() throws UnsupportedEncodingException {
		mail = (Mail) dao.query(Mail.class, entityId);
		if (mail == null) {
			addActionError("邮件不存在！");
			return INPUT;
		} else {
			if (mail.getAttachmentName() == null || mail.getAttachment() == null) {// 检查是否有附件
//				request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				addActionError(GlobalInfo.ERROR_ATTACH_NULL);
				return INPUT;
			} else {
				String[] attachName = mail.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = mail.getAttachment().split("; ");// 封转附件路径为数组
				if (attachName.length < status + 1) {// 检查索引的附件是否存在
					addActionError(GlobalInfo.ERROR_ATTACH_NULL);
//					request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					return INPUT;
				} else {
					attachmentName = attachName[status];
					attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
					targetFile = ServletActionContext.getServletContext().getResourceAsStream(attachPath[status]);
					return SUCCESS;
				}
			}
		}
	}

	/**
	 * 文件是否存在校验
	 */
	public String validateFile() throws Exception {
		if (null == entityId || entityId.trim().isEmpty()) {// 通知id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
		} else {
			Mail mail = (Mail) dao.query(Mail.class, this.entityId);
			if (null == mail) {// 通知不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if (mail.getAttachmentName() == null || mail.getAttachment() == null) {// 路径或文件名错误
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
			} else {// 路径有，但文件不存在
				String[] attachName = mail.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = mail.getAttachment().split("; ");// 封转附件路径为数组
				attachmentName = attachName[status];
				attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
				targetFile = ServletActionContext.getServletContext().getResourceAsStream(attachPath[status]);
				if (targetFile == null) {
					jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				}
			}
		}
		return SUCCESS;
	}

	public void validateDownload() {
		if (entityId == null || entityId.trim().isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_BELONG_NULL);
		}
	}

	/**
	 * 删除邮件
	 */
	@Transactional
	public String delete() {
		try {
			mailService.deleteMail(entityIds);// 删除邮件
			return SUCCESS;
		} catch (Exception e) {
			jsonMap.put(GlobalInfo.ERROR_INFO, "删除失败！");
			return INPUT;
		}
	}

	/**
	 * 删除邮件校验
	 */
	public String validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 邮件删除ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_DELETE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	
	
	/**
	 * 初级检索
	 * 
	 * @return SUCCESS返回列表页面,INPUT信息提示
	 */
	public Object[] simpleSearchCondition() {
		if (keyword == null) {// 预处理关键字
			keyword = "";
		} else {
			keyword = keyword.toLowerCase();
		}
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		hql.append(" and ");
		if (searchType == 1) {// 按主题检索
			hql.append(" LOWER(m.subject) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按正文检索
			hql.append(" LOWER(m.body) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按创建账号检索
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {// 按创建者检索
			hql.append(" LOWER(m.accountBelong) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {
			hql.append(" (LOWER(m.subject) like :keyword or LOWER(m.body) like :keyword or LOWER(p.name) like :keyword or LOWER(m.accountBelong) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] { hql.toString(), map, 2, null };
		// this.simpleSearch(hql, map, ORDER_BY, 2, 1, PAGE_NAME);
		// return SUCCESS;
	}

	/**
	 * 处理高级检索用的查询语句等
	 */
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer(HQL());
		Map map = new HashMap();
		if (keyword1 != null && !keyword1.isEmpty()) {
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(m.subject) like :keyword1");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(m.body) like :keyword2");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {
			keyword3 = keyword3.toLowerCase();
			hql.append(" and LOWER(p.name) like :keyword3");
			map.put("keyword3", "%" + keyword3 + "%");
		}
		if (keyword4 != null && !keyword4.isEmpty()) {
			keyword4 = keyword4.toLowerCase();
			hql.append(" and LOWER(m.accountBelong) like :keyword4");
			map.put("keyword4", "%" + keyword4 + "%");
		}
		if (createDate1 != null) {
			hql.append(" and m.createDate > :createDate1");
			map.put("createDate1", createDate1);
		}
		if (createDate2 != null) {
			hql.append(" and m.createDate < :createDate2");
			map.put("createDate2", createDate2);
		}
		if (status != 0) {
			hql.append(" and m.status = :status");
			map.put("status", status - 1);
		}
		return new Object[] { hql.toString(), map, 0, null };
		// this.advSearch(hql, map, ORDER_BY, 2, 1, PAGE_NAME);
		// return SUCCESS;
	}

	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * 
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		if (null != keyword1 && !keyword1.isEmpty()) {
			searchQuery.put("keyword1", keyword1);
		}
		if (null != keyword2 && !keyword2.isEmpty()) {
			searchQuery.put("keyword2", keyword2);
		}
		if (null != keyword3 && !keyword3.isEmpty()) {
			searchQuery.put("keyword3", keyword3);
		}
		if (null != keyword4 && !keyword4.isEmpty()) {
			searchQuery.put("keyword4", keyword4);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != createDate1) {
			searchQuery.put("createDate1", df.format(createDate1));
		}
		if (null != createDate2) {
			searchQuery.put("createDate2", df.format(createDate2));
		}
		if (status != 0) {
			searchQuery.put("status", status);
		}
	}

	public void validateAdvSearch() {
		if (status < 0 || status > 4) {
			status = 0;
		}
	}

	/**
	 * 暂停发送邮件（把邮件状态设置为0）
	 */
	public String pauseSend() {
		mail = (Mail) dao.query(Mail.class, entityId);
		mail.setStatus(1);
		dao.modify(mail);
		mailController.cancel(entityId);
		jsonMap.put(GlobalInfo.ERROR_INFO, "邮件已成功被暂停发送！");
		return SUCCESS;
	}

	/**
	 * 暂停发送校验
	 */
	public String validatePauseSend() {
		if (entityId == null || entityId.isEmpty()) {// 暂停发送邮件ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_PAUSE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}

	/**
	 * 手动发送邮件
	 */
	public String send() {
		mailController.send(entityId);
		jsonMap.put(GlobalInfo.ERROR_INFO, "发送成功！");
		return SUCCESS;
	}

	/**
	 * 手动发送校验
	 */
	public String validateSend() {
		if (entityId == null || entityId.isEmpty()) {// 手动发送邮件ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SEND_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	/**
	 * 进入重发页面
	 */
	public String toSendAgain(){
		mail = (Mail) dao.query(Mail.class, entityId);
		String sendTo = mail.getSendTo();
		String send = "";
		if (sendTo != null && !sendTo.isEmpty()) {
			send = sendTo + ";";
		}
		mail.setSendTo(send);
		return SUCCESS;
	}

	public void validateToSendAgain() {
		if (entityId == null || entityId.isEmpty()) {// 重新发送邮件ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SEND_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, MailInfo.ERROR_SEND_NULL);
		}
	}

	/**
	 * 重新发送邮件(只能对发送成功的邮件进行操作)
	 */
	public String sendAgain() {
		final Mail mail = (Mail) dao.query(Mail.class, entityId);
		dao.evict(mail);
//		for (String entityId : entityIds) {
			if (mail != null && mail.getStatus() == 3) {
				txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							String name = loginer.getPassport().getName();//重发者
							mail.setStatus(0);
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String agDate = df.format(new Date());
							if (mail.getLog() == null) {
								mail.setLog("");
							}
							mail.setLog(mail.getLog() + "["+ name + " 于 " + agDate + " 重发此邮件]<br>");
							dao.modify(mail);
						} catch (Exception e) {
							status.setRollbackOnly();
						}
					}
				});
			}
				mailController.send(mail.getId());
				jsonMap.put(GlobalInfo.ERROR_INFO, "重发成功！");
				return SUCCESS;
//		}
	}

	
	/**
	 * 发送所有未完成发送的邮件
	 * 
	 * @throws Exception
	 */
	public String sendAll() {
		sendUndoneMails.send();
		return SUCCESS;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
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

	public Date getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(Date createDate1) {
		this.createDate1 = createDate1;
	}

	public Date getCreateDate2() {
		return createDate2;
	}

	public void setCreateDate2(Date createDate2) {
		this.createDate2 = createDate2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String[] getFileIds() {
		return fileIds;
	}

	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}

	public String getUploadKey() {
		return uploadKey;
	}

	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}

	public InputStream getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(InputStream targetFile) {
		this.targetFile = targetFile;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public List getRecieverType() {
		return recieverType;
	}

	public void setRecieverType(List recieverType) {
		this.recieverType = recieverType;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}

	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}

}
