package csdc.action.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import csdc.action.BaseAction;
import csdc.bean.Mail;
import csdc.bean.common.Visitor;
import csdc.dao.HibernateBaseDao;
import csdc.tool.ApplicationContainer;
import csdc.tool.info.GlobalInfo;
import csdc.tool.mail.MailController;
import csdc.tool.mail.SendUndoneMails;

/**
 * 邮件管理
 * @author 徐涵
 */
public class MailAction extends BaseAction {
	
	private static final long serialVersionUID = 2073025750739414401L;
	private static final String HQL = "select mail.id, mail.subject, mail.createDate, mail.finishDate from Mail mail where mail.template is null and 1 = 1 ";
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";// 列表时间格式
	private static final String PAGENAME = "mailPage";
	private static final String PAGE_BUFFER_ID = "mail.id";//缓存id
	protected String attachmentName;// 附件下载文件名
    protected InputStream targetFile;// 附件下载流
    protected int status;// 附件索引号
    /**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
    @Autowired
	private TransactionTemplate txTemplateRequiresNew;
	@Autowired
	private HibernateBaseDao dao;
	private static final String column[] = {
		"mail.subject",
		"mail.createDate desc",
		"mail.finishDate desc"
	};// 排序用的列
	private final static String[] SEARCH_CONDITIONS = new String[]{
		"LOWER(mail.subject) like :keyword",
		"LOWER(mail.body) like :keyword"
	};//检索条件
	
	private Mail mail;
	
	@Autowired
	private MailController mailController;

	@Autowired
	private SendUndoneMails sendUndoneMails;

		
	/**
	 * 列表和初级检索条件
	 */
	@Override
	public Object[] simpleSearchCondition() {
		if (keyword != null) {
			keyword = keyword.toLowerCase().trim();// 预处理关键字
		}
		StringBuffer hql = new StringBuffer(HQL);
		Map map = new HashMap();
	
		if (keyword != null && !keyword.trim().isEmpty()){
			//处理查询条件
			boolean flag = false;
			StringBuffer tmp = new StringBuffer("(");
			for (int i = 0; !flag && i < SEARCH_CONDITIONS.length; i++) {
				if (searchType == i){
					hql.append(" and ").append(SEARCH_CONDITIONS[i]);
					flag = true;
				}
				tmp.append(SEARCH_CONDITIONS[i]).append(i < SEARCH_CONDITIONS.length - 1 ? " or " : ") ");
			}
			if (!flag){
				hql.append(" and ").append(tmp);
			}
			map.put("keyword", "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%");
		}
//		hql.append(" order by mail.finishDate desc ");
		// 调用初级检索功能
		return new Object[]{
			hql.toString(),
			map,
			2,
			null
		};
	}
	
	public void validateSimpleSearchCondition() {
	}
	
	/**
	 * 重新发送邮件(只能对发送成功的邮件进行操作)
	 */
	public String sendAgain() {
		final Mail mail = (Mail) dao.query(Mail.class, entityId);
		dao.evict(mail);
			if (mail != null && mail.getStatus() == 3) {
				txTemplateRequiresNew.execute(new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
							Visitor visitor = (Visitor) session.get("visitor");
							String name = visitor.getUser().getUsername();//重发者
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
	 * 进入查看页面
	 * @return
	 */
	public String toView(){
		return SUCCESS;
	}
	
	/**
	 * 查看邮件信息
	 * @return
	 * @throws IOException 
	 */
	public String view() throws IOException{
		mail = (Mail) baseService.query(Mail.class, entityId);
		if(mail == null){
			jsonMap.put(GlobalInfo.ERROR_INFO, "该邮件已不存在");
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
						try {
							attachmentSizeList.add(baseService.accquireFileSize(is.available()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {// 附件不存在
						attachmentSizeList.add(null);
					}
					jsonMap.put("attachmentSizeList", attachmentSizeList);
				}
			}
			jsonMap.put("mail", mail);
			return SUCCESS;
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

	/**
	 * 查看校验
	 */
	public void validateView() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择要查看的邮件");
		}
	}

	/**
	 * 进入添加邮件页面
	 * @return	SUCCESS进入添加页面
	 */
	public String toAdd() {
		return SUCCESS;
	}
	
	public void validateToAdd() {
	}
	
	/**
	 * 添加邮件
	 * @return
	 */
	public String add(){
		mail.setReplyTo("serv@csdc.info");// 认证地址
		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);
		//邮件内容自动换行，后台将"\r\n"替换成 "<br/>"
		String  bodyAdd = mail.getBody();
		String  body = bodyAdd.replaceAll("\r\n", "<br/>");
		mail.setBody(body);
		mail.setStatus(3);
		dao.add(mail);
		return SUCCESS;
	}
	/**
	 * 进入修改
	 * @return	SUCCESS进入修改页面
	 */
	public String toModify(){
		mail = (Mail) baseService.query(Mail.class, entityId);
		if (mail == null) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "无效的邮件");
			jsonMap.put(GlobalInfo.ERROR_INFO, "无效的邮件");
			return INPUT;
		} 
		return SUCCESS;
	}
	
	/**
	 * 校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择邮件");
		}
	}

	/**
	 * 修改邮件信息
	 * @return SUCCESS跳转查看
	 */
	public String modify(){
		Mail mmail = (Mail) baseService.query(Mail.class, mail.getId());

		baseService.modify(mmail);
		// 邮件改为不参与评审后，删除原有的参与评审关系
		entityId = mmail.getId();
		return SUCCESS;
	}

	/**
	 * 修改校验
	 */
	public void validateModify() {

	}

	/**
	 * 删除邮件
	 * @return SUCCESS跳转列表
	 */
	public String delete(){
		try{
			for (String entityId : entityIds) {
				baseService.delete(Mail.class, entityId);
			}
			return SUCCESS;
		}catch (Exception e){
			jsonMap.put(GlobalInfo.ERROR_INFO, "删除失败！");
			return INPUT;
		}
	}

	/**
	 * 删除校验
	 */
	public void validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {//删除的记录id不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, "请选择邮件");
			jsonMap.put(GlobalInfo.ERROR_INFO, "请选择邮件");
		}
	}
	
	/**
	 * 发送邮件
	 * @return
	 */
	public String send() {
		mailController.send(entityId);
		return SUCCESS;
	}

	/**
	 * 暂停发送邮件
	 * @return
	 */
	public String cancel() {
		mailController.cancel(entityId);
		return SUCCESS;
	}
	/**
	 * 发送所有未完成邮件
	 * @return
	 */
	public String sendAll() {
//		String path = ServletActionContext.getServletContext().getRealPath("/");
//		sendUndoneMails.send(path);
		sendUndoneMails.send();
		return SUCCESS;
	}
	
	public String getPath() {
		String path = ServletActionContext.getServletContext().getRealPath("/");
		return path;
	}
	
	/**
	 * 暂停发送所有邮件
	 * @return
	 */
	public String cancelAll() {
		mailController.cancelAll();
		return SUCCESS;
	}


	public List<Object[]> getPageList() {
		return pageList;
	}
	public void setPageList(List<Object[]> pageList) {
		this.pageList = pageList;
	}
	
	
	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String[] column() {
		return column;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
	@Override
	public String dateFormat() {
		return DATE_FORMAT;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public InputStream getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(InputStream targetFile) {
		this.targetFile = targetFile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}

	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}
}
