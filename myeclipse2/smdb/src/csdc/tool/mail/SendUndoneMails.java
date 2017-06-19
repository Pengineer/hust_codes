package csdc.tool.mail;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.HibernateBaseDao;

/**
 * 发送“未发送”的邮件
 * @author xuhan
 */
public class SendUndoneMails  {

	@Autowired
	private HibernateBaseDao dao;

	@Autowired
	private MailController mailController;

	public void send() {
		List<Serializable> mailIds = dao.query("select mail.id from Mail mail where mail.status = 0 order by mail.id");
		for (Serializable mailId : mailIds) {
			mailController.send(mailId);
		}
	}
}
