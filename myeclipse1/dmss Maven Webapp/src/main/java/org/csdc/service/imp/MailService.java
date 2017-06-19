package org.csdc.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.csdc.model.Mail;
import org.csdc.tool.FileTool;
import org.csdc.tool.Mailer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class MailService extends BaseService {

	/**
	 * 添加邮件
	 * @param mail新增邮件对象
	 * @return mailId
	 */
	public String addMail(Mail mail) throws Exception {
		//if (mail.getAccount() == null){
		//	mail.setAccount((Account) baseDao.query(Account.class, "admin"));
		//}
		mail.setCreatedDate(new Date());// 发件时间
		mail.setFinishedDate(null);// 完成时间
		mail.setSended(null);
		mail.setSendTimes(0);
		mail.setStatus(0);
		String id = (String)baseDao.add(mail);// 添加邮件
		return id;
	}

	/**
	 * 查找邮件，包括外键创建者
	 * @param mailId
	 * @return mail邮件对象
	 */
	public Mail viewMail(String mailId) {
		if (mailId == null || mailId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("mailId", mailId);
			List<Mail> mails = baseDao.query("select m from Mail m left join fetch m.account a where m.id = :mailId", map);
			return mails.isEmpty() ? null : mails.get(0);
		}
	}

	/**
	 * 批量删除邮件，同时删除邮件中的附件
	 * @param mailIds待删除的邮件ID集合
	 */
	public void deleteMail(List<String> mailIds) {
		if (mailIds != null && !mailIds.isEmpty()) {
			Mail mail;
			String[] fileArray;
			for (String mailId : mailIds) {
				if (mailId != null && !mailId.isEmpty()) {
					mail = (Mail) baseDao.query(Mail.class, mailId);
					if (mail.getAttachment() != null) {
						fileArray = mail.getAttachment().split("; ");
						if (fileArray != null && fileArray.length != 0) {
							for (String fileName : fileArray) {
								FileTool.fileDelete(fileName);// 删除附件
							}
						}
					}
					baseDao.delete(mail);// 删除邮件
				}
			}
		}
	}

	/**
	 * 暂停发送邮件
	 * @param id待暂停的邮件ID集合
	 */
	public void pauseSend(String id) {
		Mail mail = (Mail) baseDao.query(Mail.class, id);
		if (mail.getStatus() == 0) {// 发送中的邮件才能暂停发送
			mail.setStatus(1);
			baseDao.modify(mail);
			Mailer.curSendTo.remove(id);// 从发送队列中删除该邮件的ID
		}
	}

	/**
	 * 手动发送邮件
	 * @param id待发送的邮件ID集合
	 */
	public void sendEmail(String id) {
		Mail mail = (Mail) baseDao.query(Mail.class, id);
		if (mail.getStatus() == 1) {// 暂停中的邮件才能发送
			mail.setStatus(0);
			baseDao.modify(mail);
			try {
				Mailer.send(mail);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 发送所有未完成发送的邮件
	 * @throws Exception
	 */
	public void sendAllMails() throws Exception {
		List lm = baseDao.query("select mail from Mail mail where mail.status = 0");
		for (int i = 0; i < lm.size(); i++) {
			Mail mail = (Mail) lm.get(i);
			Mailer.send(mail);
		}
	}


}
