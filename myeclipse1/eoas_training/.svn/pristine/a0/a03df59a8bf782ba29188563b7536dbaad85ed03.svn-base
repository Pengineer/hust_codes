package csdc.tool.mail;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.mappers.MailMapper;


/**
 * 发送“未发送”的邮件
 * @author xuhan
 */
public class SendUndoneMails  {

	@Autowired
	private MailMapper mailDao;

	@Autowired
	private MailController mailController;

	public void send() {
		List<String> mailIds = mailDao.selectMailByStatus(0);
		for (String mailId : mailIds) {
			mailController.send(mailId);
		}
	}
}
