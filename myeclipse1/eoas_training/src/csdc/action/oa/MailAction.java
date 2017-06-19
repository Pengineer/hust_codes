package csdc.action.oa;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;

import csdc.bean.Account;
import csdc.bean.common.*;
import csdc.bean.Mail;
import csdc.service.IBaseService;
import csdc.service.imp.MailService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;
import csdc.tool.mail.MailController;

public class MailAction extends ActionSupport{
	
	private Mail mail;
	private Account account;
	private Map jsonMap = new HashMap();
	private String mailId;
	private boolean sendNow=true;
	private IBaseService baseService;
	@Autowired
	private MailController mailController;

	/**
	 * 使用PROPAGATION_REQUIRES_NEW传播特性的编程式事务模板
	 */
	private TransactionTemplate txTemplateRequiresNew;
	
	/**
	 * 根据mail成员创建新邮件，并存储至数据库
	 * @return 跳转成功
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String createMail() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Visitor visitor = (Visitor)session.get("visitor");
		Map map = new HashMap();
		map.put("name", "admin");
		mail.setAccount(visitor == null ? (Account)(baseService.list(Account.class, map)) : visitor.getAccount());
		mail.setCreateDate(new Date());
		mail.setSendTimes(0);
		mail.setSendTo(mail.getSendTo());
		mail.setFrom("serv@nadr.hust.edu.cn");
		mail.setReplyTo("serv@nadr.hust.edu.cn");// 认证地址
		addEmail();// 组装邮件
		baseService.add(mail);
		if (sendNow){
			mailController.send(mail.getId());
		}
		return SUCCESS;
	}
	
	public String toList() {
		return SUCCESS;
	}

	public String list() {
		ArrayList<Mail> mailList = new ArrayList<Mail>();
		List<Object[]> mList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		Visitor visitor = (Visitor) session.get("visitor");
		Map map = new HashMap();
		map.put("accountId",  visitor.getAccount().getId());
		mailList = (ArrayList<Mail>) baseService.list(Mail.class, map);
		String[] item;
		for (Mail m : mailList) {
			item = new String[5];
			item[0] = m.getSendTo();
			item[1] = m.getSubject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != m.getCreateDate()) {
				item[2] = sdf.format(m.getCreateDate());
			}
			item[3] = m.getId();
			mList.add(item);
		}
		jsonMap.put("aaData", mList);
		return SUCCESS;
	}

	public String toAdd() {
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
					
/*					String to = mail.getSendTo();
					if (to.equals("请填写地址，用分号隔开")) {
						to = "";
					}
					mail.setSendTo(to);
					mail.setSubject("[SMDB] " + mail.getSubject());*/
					mail.setFrom("serv@nadr.hust.edu.cn");
					mail.setReplyTo("serv@nadr.hust.edu.cn");// 认证地址
					addEmail();// 组装邮件
				} catch (Exception e) {
					status.setRollbackOnly();
				}
			}
		});
			try {
				mailController.send(mail.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
	}
	
	/**
	 * 组装邮件
	 * 
	 * @throws Exception
	 */
	private void addEmail() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Account account = (Account) session.get("account");
		mail.setAccount(account);// 发件者

		Date createDate = new Date(System.currentTimeMillis());
		mail.setCreateDate(createDate);
		mail.setIsHtml(1);
		//邮件内容自动换行，后台将"\r\n"替换成 "<br/>"
		String  bodyAdd = mail.getBody();
		String  body = bodyAdd.replaceAll("\r\n", "<br/>");
		mail.setBody(body);
		try {
			baseService.add(mail); // 向数据库添加邮件记录
		} catch (Exception e) {
			e.printStackTrace();
		}
		//处理附件
/*		String groupId = "file_add";
 *

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
		uploadService.flush(groupId);*/

	}

	public String delete() {
		baseService.delete(Mail.class, mailId);
		return SUCCESS;
	}
	
	public String view() {
		
		try {
			mail = (Mail) baseService.load(Mail.class, mailId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}
	
	public MailController getMailController() {
		return mailController;
	}

	public void setMailController(MailController mailController) {
		this.mailController = mailController;
	}
	
	public TransactionTemplate getTxTemplateRequiresNew() {
		return txTemplateRequiresNew;
	}

	public void setTxTemplateRequiresNew(TransactionTemplate txTemplateRequiresNew) {
		this.txTemplateRequiresNew = txTemplateRequiresNew;
	}
	
	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
}
