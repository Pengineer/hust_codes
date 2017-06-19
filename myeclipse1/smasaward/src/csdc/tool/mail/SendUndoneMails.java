package csdc.tool.mail;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.Application;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import csdc.action.mail.MailAction;
import csdc.bean.Mail;
import csdc.bean.SystemConfig;
import csdc.dao.HibernateBaseDao;
import csdc.tool.ApplicationContainer;

/**
 * 发送“未发送”的邮件
 * @author xuhan
 */
public class SendUndoneMails{

	@Autowired
	private HibernateBaseDao dao;

	@Autowired
	private MailController mailController;
	
	public void send() {
		List<Serializable> mailIds = dao.query("select mail.id from Mail mail where mail.status = 0 order by mail.id");
		for (Serializable mailId: mailIds) {
			mailController.send(mailId);
		}
	}
}
