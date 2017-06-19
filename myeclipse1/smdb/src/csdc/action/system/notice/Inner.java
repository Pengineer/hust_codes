package csdc.action.system.notice;

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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Mail;
import csdc.bean.Notice;
import csdc.bean.Passport;
import csdc.service.IMailService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.NoticeInfo;
import csdc.tool.mail.MailController;

public class Inner extends NoticeAction {

	private static final long serialVersionUID = 1L;
	private static final String HQL = "select n.id, n.title, sys.name, n.isOpen, n.isPop, a.id, p.name, n.createDate, n.viewCount, n.accountBelong from Notice n left join n.account a left join n.type sys left join a.passport p where 1=1 ";
	private static final String TMP_NOTICE_ID = "noticeId";
	private static final String[] COLUMN = {
		"n.title",
		"sys.name, n.title",
		"n.isOpen, n.title",
		"n.isPop, n.title",
		"p.name, n.title",
		"n.createDate desc, n.title",
		"n.viewCount, n.title",
		"n.accountBelong, n.title"
	};// 排序列
	private static final String PAGE_NAME = "noticeInnerPage";// 列表页面名称

	private IMailService mailService;// 邮件管理接口
	private Mail mail;
	private String keyword1, keyword2, keyword3, keyword4, keyword5;// 用于高级检索
	private int isOpen, isPop, viewFlag;// 用于高级检索
	private Date startDate, endDate;// 用于高级检索
	//异步文件上传所需
	private String uploadKey;// 文件上传授权码
	private String fileFileName;//文件名称
	private String savePath;//文件保存路径
	private String existingAttachment;	//已有的附件(修改通知时用于加载)
	private List recieverType;// 收件人群
	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	
	@Autowired
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private MailController mailController;
	
	
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}
	public List getRecieverType() {
		return recieverType;
	}
	public void setRecieverType(List recieverType) {
		this.recieverType = recieverType;
	}
	public static String[] getColumn() {
		return COLUMN;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String pageName(){
		return PAGE_NAME;
	}
	public String[] column(){
		return COLUMN;
	}
	public String HQL() {
		return HQL;
	}

	/**
	 * 进入添加通知页面
	 * @return
	 */
	public String toAdd() {
		flag = 0;
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	/**
	 * 添加通知
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String add() throws Exception {
		notice.setAccount(loginer.getAccount());
		String accountBelong = "";
		if (loginer.getCurrentBelongUnitName() != null) {
			accountBelong = loginer.getCurrentBelongUnitName();
		}
		if (loginer.getCurrentPersonName() != null) {
			accountBelong = loginer.getCurrentPersonName();
		}
		notice.setAccountBelong(accountBelong);
		entityId = noticeService.addNotice(recieverType, notice);
		
		//处理附件
		String groupId = "file_add";
		String savePath =  (String) ApplicationContainer.sc.getAttribute("NoticeFileUploadPath");
		List<String> attachments = new ArrayList<String>();
		List<String> attachmentNames = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = FileTool.getTrueFilename(savePath,entityId, fileRecord.getOriginal());
			String orginalFileName = fileRecord.getOriginal().getName();
			attachments.add(newFilePath);
			attachmentNames.add(orginalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));
		}
		notice.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
		notice.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
		dao.modify(notice);
		
		if(recieverType != null){
			String mailGroupId = "notice_mail_file_add";
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				String fileAbsolutePath = fileRecord.getOriginal().getAbsolutePath();
				File tempFile = new File(fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf(".")) + "_temp" +fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf(".")));
				FileUtils.copyFile(fileRecord.getOriginal(), tempFile);
				uploadService.addFile(mailGroupId, tempFile, fileRecord.getFileName());
			}
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {// 获取收件人地址
						StringBuffer sendTo = new StringBuffer(mail.getSendTo());
						List<String> emails = mailService.generateEmailList(recieverType);
						sendTo.append(" ").append(StringUtils.join(emails.toArray(new String[0]), " "));
						mail.setSendTo(sendTo.toString());
						mail.setReplyTo("serv@csdc.info");// 认证地址
						sendEmail(); //组装邮件
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});
			mailController.send(mail.getId());// 发送邮件
		}
		
		uploadService.flush(groupId);
		String dfsIds = noticeService.uploadToDmss(notice);
		notice.setDfs(dfsIds);
		return SUCCESS;	
	}

	public void sendEmail() {
		try {
			mail.setAccount(loginer.getAccount());// 发件者
			String accountBelong = "";
			if (loginer.getCurrentBelongUnitName() != null) {
				accountBelong = loginer.getCurrentBelongUnitName();
			}
			if (loginer.getCurrentPersonName() != null) {
				accountBelong = loginer.getCurrentPersonName();
			}
			mail.setAccountBelong(accountBelong);
			mail.setBody(notice.getContent());
			mail.setSubject("[SMDB] " + notice.getTitle());
			if(mail.getSubject().length() > 100){
				mail.setSubject(mail.getSubject().substring(0, 100));
			}
			Date createDate = new Date(System.currentTimeMillis());
			mail.setCreateDate(createDate);// 创建时间
			mail.setIsHtml(1);
			entityId = dao.add(mail); // 向数据库添加邮件记录
			
			//处理附件
			String groupId = "notice_mail_file_add";
			String savePath = (String) ApplicationContainer.sc.getAttribute("MailFileUploadPath");
			List<String> attachments = new ArrayList<String>();
			List<String> attachmentNames = new ArrayList<String>();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				String newFilePath = FileTool.getTrueFilename(savePath,entityId, fileRecord.getOriginal());//文件在数据库中的存储地址（包括存储路径和原来的文件名）
				String orignalFileName = fileRecord.getFileName();
				//将文件放入list中暂存
				attachments.add(newFilePath);
				attachmentNames.add(orignalFileName);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			mail.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
			mail.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
			dao.modify(mail);
			uploadService.flush(groupId);
			String dfsIds = mailService.uploadToDmss(mail);
			mail.setDfs(dfsIds);
			dao.modify(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加通知校验
	 */
	public void validateAdd() {
		if (notice.getTitle() == null || notice.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_TITLE_NULL);
		} else if (notice.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_TITLE_ILLEGAL);
		}
		if (notice.getSource().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_SOURCE_ILLEGAL);
		}
		if (notice.getIsOpen() != 0 && notice.getIsOpen() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_IS_OPEN);
		}
		if (notice.getContent() == null || notice.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_BODY_NULL);
		}
	}

	/**
	 * 进入修改通知页面
	 * @return
	 */
	public String toModify() {
		flag = 1;
		notice = noticeService.viewNotice(entityId);
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + notice.getId();
		uploadService.resetGroup(groupId);
		if (notice.getAttachmentName() != null && notice.getAttachment() != null) {
			String[] tempFileRealpath = notice.getAttachment().split("; ");
			String[] attchmentNames = notice.getAttachmentName().split("; ");
			String[] dfsIds = null;
			if(notice.getDfs() != null) {
				dfsIds = notice.getDfs().split("; ");
			}
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(tempFileRealpath[i]);
				if (fileRealpath != null && new File(fileRealpath).exists()) {
					uploadService.addFile(groupId, new File(fileRealpath), attchmentNames[i]);
				} else if(notice.getDfs() != null && !notice.getDfs().isEmpty() && dmssService.getStatus()) {//如果文件在本地服务器不存在，则从云存储中获取临时文件至服务器
					try {
						InputStream downloadStream = dmssService.download(dfsIds[i]);
						String sessionId = ServletActionContext.getRequest().getSession().getId();
						File dir = new File(ApplicationContainer.sc.getRealPath("temp" + "/" + sessionId.replaceAll("\\W+", "")));
						dir.mkdirs();
						String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
						File downloadFile = new File(dir, fileName);
						FileUtils.copyInputStreamToFile(downloadStream, downloadFile);
						uploadService.addFile(groupId, downloadFile, attchmentNames[i]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		session.put(TMP_NOTICE_ID, notice.getId());
		return SUCCESS;
		
	}

	/**
	 * 修改通知校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_MODIFY_NULL);
		}
	}

	/**
	 * 修改通知
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public String modify() throws Exception {
		Map session = ActionContext.getContext().getSession();
		entityId = (String) session.get(TMP_NOTICE_ID);
		noticeService.modifyNotice(entityId, notice);// 更新附件之外的信息
		Notice orgNotice = noticeService.viewNotice(entityId);
		
		//处理附件
		String orgDfs = orgNotice.getDfs();
		String groupId = "file_" + notice.getId();
		String savePath =  (String) ApplicationContainer.sc.getAttribute("NoticeFileUploadPath");
		List<String> attachments = new ArrayList<String>();
		List<String> attachmentNames = new ArrayList<String>();
		for(FileRecord fileRecord : uploadService.getGroupFiles(groupId)){
			String orignalFileName = fileRecord.getFileName();
			String newFilePath = FileTool.getAvailableFilename(savePath, fileRecord.getOriginal());
			attachments.add(newFilePath);
			attachmentNames.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));
		}
		orgNotice.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
		orgNotice.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
		orgNotice.setDfs(null);
		dao.modify(orgNotice);
		
		if(recieverType != null){
			String mailGroupId = "notice_mail_file_add";
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {//将file_add文件组中的文件，暂时复制到notice_mail_file_add文件组中，涉及临时拷贝文件
				String fileAbsolutePath = fileRecord.getOriginal().getAbsolutePath();
				File tempFile = new File(fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf(".")) + "_temp" +fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf(".")));
				FileUtils.copyFile(fileRecord.getOriginal(), tempFile);
				uploadService.addFile(mailGroupId, tempFile, fileRecord.getFileName());
			}
			txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {// 获取收件人地址
						StringBuffer sendTo = new StringBuffer(mail.getSendTo());
						List<String> emails = mailService.generateEmailList(recieverType);
						sendTo.append(" ").append(StringUtils.join(emails.toArray(new String[0]), " "));
						mail.setSendTo(sendTo.toString());
						mail.setReplyTo("serv@csdc.info");// 认证地址
						sendEmail(); //组装邮件
					} catch (Exception e) {
						status.setRollbackOnly();
					}
				}
			});
			mailController.send(mail.getId());// 发送邮件
			}
		session.remove(TMP_NOTICE_ID);
		uploadService.flush(groupId);
		//DMSS同步
		if(orgDfs != null && !orgDfs.isEmpty() && dmssService.getStatus()) {
			String[] orgDfsIds = orgDfs.split("; ");
			for(String orgDfsId : orgDfsIds) {
				dmssService.deleteFile(orgDfsId);
			}
		}
		String dfsIds = noticeService.uploadToDmss(orgNotice);
		orgNotice.setDfs(dfsIds);
		dao.modify(orgNotice);
		return SUCCESS;
		
	}
	
	/**
	 * 修改通知校验
	 */
	public void validateModify() {
		if (notice.getTitle() == null || notice.getTitle().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_TITLE_NULL);
		} else if (notice.getTitle().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_TITLE_ILLEGAL);
		}
		if (notice.getSource().trim().length() > 100) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_SOURCE_ILLEGAL);
		}
		if (notice.getIsOpen() != 0 && notice.getIsOpen() != 1) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_IS_OPEN);
		}
		if (notice.getContent() == null || notice.getContent().trim().equals("")) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_BODY_NULL);
		}
	}
	
	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 查看通知校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_VIEW_NULL);
		}
	}
	/**
	 * 查看通知
	 * @return
	 * @throws IOException 
	 */
	public String view() throws IOException {
		notice = noticeService.viewNotice(entityId);
		notice.setViewCount(notice.getViewCount() + 1);// 查看数量+1
		dao.modify(notice);
		if (notice.getAttachmentName() != null) {// 处理附件名称用于显示，去掉最后一个"; "
			notice.setAttachmentName(notice.getAttachmentName().substring(0, notice.getAttachmentName().length() - 2));
			//获取文件大小
			List<String> attachmentSizeList = new ArrayList<String>();
			String[] attachPath = notice.getAttachment().split("; ");
			String[] dfsIds = null;
			if(notice.getDfs() != null) {
				dfsIds = notice.getDfs().split("; ");
			}
			InputStream is = null;
			for (int i = 0; i < attachPath.length; i++) {
				String path = attachPath[i];
				is = ApplicationContainer.sc.getResourceAsStream(path);
				if (null != is) {
					attachmentSizeList.add(baseService.accquireFileSize(is.available()));
				} else if(notice.getDfs() != null && !notice.getDfs().isEmpty() && dmssService.getStatus()) {
					long fileSize = dmssService.accquireFileSize(dfsIds[i]);
					attachmentSizeList.add(baseService.accquireFileSize(fileSize));
				} else {// 附件不存在
					attachmentSizeList.add(null);
				}
				jsonMap.put("attachmentSizeList", attachmentSizeList);
				if(is != null) {
					is.close();
				}
			}
		}
		jsonMap.put("notice", notice);
		if(null != notice.getAccount()){
			Passport passport = (Passport) dao.query(Passport.class, notice.getAccount().getPassport().getId());
			jsonMap.put("accountName", passport == null ? null : passport.getName());
		} else {
			jsonMap.put("accountName", null);
		}
		jsonMap.put("noticeType", (notice.getType() == null ? null : notice.getType().getName()));
		return SUCCESS;
	}
	
	public String download() throws UnsupportedEncodingException {
		notice = (Notice) dao.query(Notice.class, entityId);
		if (notice == null) {
//			request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
			addActionError("通知已经不存在");
			return INPUT;
		} else {
			if (notice.getAttachmentName() == null || notice.getAttachment() == null) {// 检查是否有附件
//				request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
				addActionError(GlobalInfo.ERROR_ATTACH_NULL);
				return INPUT;
			} else {
				String[] attachName = notice.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = notice.getAttachment().split("; ");// 封转附件路径为数组
				String[] dfsIds = notice.getDfs().split("; ");//封转云存储位置为数组
				if (attachName.length < status + 1) {// 检查索引的附件是否存在
//					request.setAttribute(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					addActionError(GlobalInfo.ERROR_ATTACH_NULL);
					return INPUT;
				} else {
					attachmentName = attachName[status];
					attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
					targetFile = ApplicationContainer.sc.getResourceAsStream(attachPath[status]);
					if(targetFile == null) {
						targetFile = dmssService.download(dfsIds[status]);
					}
					return SUCCESS;
				}
			}
		}
	}
	
	/**
	 * 文件是否存在校验
	 */
	public String validateFile()throws Exception{
		System.out.println("1");
		if (null == entityId || entityId.trim().isEmpty()) {//通知id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
		} else {
			Notice notice = (Notice) dao.query(Notice.class, this.entityId);
			if(null == notice){//通知不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			} else if(notice.getAttachmentName() == null || notice.getAttachment() == null){//路径或文件名错误
				jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_FILE_NOT_MATCH);
			} else {//路径有，但文件不存在
				String[] attachName = notice.getAttachmentName().split("; ");// 封转附件名称为数组
				String[] attachPath = notice.getAttachment().split("; ");// 封转附件路径为数组
				attachmentName = attachName[status];
				attachmentName = new String(attachmentName.getBytes(), "ISO8859-1");
				targetFile = ApplicationContainer.sc.getResourceAsStream(attachPath[status]);
				if( targetFile == null ){
					if(null == notice.getDfs()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					} else if (null != notice.getDfs() && !dmssService.getStatus()) {
						jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_ATTACH_NULL);
					}
				}
				
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	@Transactional
	public String delete() {
		noticeService.deleteNotice(entityIds);
//		if (type == 1) {
//			backToList(pageName(), pageNumber, null);
//		} else {
//			backToList(pageName(), -1, entityIds.get(0));
//		}
		return SUCCESS;
	}
	
	/**
	 * 删除通知校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_DELETE_NULL);
		}
	}
	
	/**
	 * 初级检索
	 */
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
		if (searchType == 1) {// 按通知标题检索
			hql.append(" LOWER(n.title) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 2) {// 按通知正文检索
			hql.append(" LOWER(n.content) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 3) {// 按发布账号检索
			hql.append(" LOWER(p.name) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else if (searchType == 4) {// 按发布者检索
			hql.append(" LOWER(n.accountBelong) like :keyword ");
			map.put("keyword", "%" + keyword + "%");
		} else {// 按上述字段检索
			hql.append(" (LOWER(n.title) like :keyword or LOWER(n.content) like :keyword or LOWER(p.name) like :keyword or LOWER(n.accountBelong) like :keyword) ");
			map.put("keyword", "%" + keyword + "%");
		}
		return new Object[] {
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
	public String toAdvSearch() {
		return SUCCESS;
	}
	
	/**
	 * 高级检索
	 * @return
	 */
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if (keyword1 != null && !keyword1.isEmpty()) {
			keyword1 = keyword1.toLowerCase();
			hql.append(" and LOWER(n.title) like :keyword1 ");
			map.put("keyword1", "%" + keyword1 + "%");
		}
		if (keyword2 != null && !keyword2.isEmpty()) {
			keyword2 = keyword2.toLowerCase();
			hql.append(" and LOWER(n.content) like :keyword2 ");
			map.put("keyword2", "%" + keyword2 + "%");
		}
		if (keyword3 != null && !keyword3.isEmpty()) {
			keyword3 = keyword3.toLowerCase();
			hql.append(" and n.type.id = :keyword3 ");
			map.put("keyword3", keyword3);
		}
		if (keyword4 != null && !keyword4.isEmpty()) {
			keyword4 = keyword4.toLowerCase();
			hql.append(" and LOWER(p.name) like :keyword4 ");
			map.put("keyword4", "%" + keyword4 + "%");
		}
		if (keyword5 != null && !keyword5.isEmpty()) {
			keyword5 = keyword5.toLowerCase();
			hql.append(" and LOWER(n.accountBelong) like :keyword5 ");
			map.put("keyword5", "%" + keyword5 + "%");
		}
		if (startDate != null) {
			hql.append(" and n.createDate > :startDate");
			map.put("startDate", startDate);
		}
		if (endDate != null) {
			hql.append(" and n.createDate < :endDate");
			map.put("endDate", endDate);
		}
		if (isOpen == 0 || isOpen == 1) {
			hql.append(" and n.isOpen = :isOpen");
			map.put("isOpen", isOpen);
		}
		if (isPop == 0 || isPop == 1) {
			hql.append(" and n.isPop = :isPop");
			map.put("isPop", isPop);
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
//		this.advSearch(hql, map, ORDER_BY, 5, 0, PAGE_NAME);
//		return SUCCESS;
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != keyword1 && !keyword1.isEmpty()){
			searchQuery.put("title", keyword1);
		}
		if(null != keyword2 && !keyword2.isEmpty()){
			searchQuery.put("body", keyword2);
		}
		if (null != keyword3 && !keyword3.isEmpty()) {
			searchQuery.put("type", keyword3);
		}
		if(isOpen == 0 || isOpen == 1){
			searchQuery.put("isOpen", isOpen);
		}
		if(accountType.equals(AccountType.ADMINISTRATOR)){
			if(null != keyword4 && !keyword4.isEmpty()){
				searchQuery.put("account", keyword4);
			}
			if(null != keyword5 && !keyword5.isEmpty()){
				searchQuery.put("accountBelong", keyword5);
			}
		}
		if(isPop == 0 || isPop == 1){
			searchQuery.put("isPop", isPop);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
	}
	
	
	

	/**
	 * 高级检索通知校验
	 */
	public void validateAdvSearch() {
		if(startDate != null && endDate != null) {
			if (startDate.compareTo(endDate) >= 0) {
				this.addFieldError(GlobalInfo.ERROR_INFO, NoticeInfo.ERROR_DATE_ILLEGAL);
			}
		}
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
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getIsPop() {
		return isPop;
	}
	public void setIsPop(int isPop) {
		this.isPop = isPop;
	}
	public String getUploadKey() {
		return uploadKey;
	}
	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}
	public String getExistingAttachment() {
		return existingAttachment;
	}
	public void setExistingAttachment(String existingAttachment) {
		this.existingAttachment = existingAttachment;
	}
	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}
	public int getViewFlag() {
		return viewFlag;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public String getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}