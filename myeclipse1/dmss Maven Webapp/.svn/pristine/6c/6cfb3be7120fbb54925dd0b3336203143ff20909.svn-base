package org.csdc.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.csdc.dao.IBaseDao;
import org.csdc.model.Mail;
/**
 * 邮件发送线程
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
public class Mailer extends Thread{

	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等內容均添加到其中后再生成MimeMessage对象
	private String username;
	private String password;

	private String[] usernameList;	// 发信用户名列表
	private String[] passwordList;	// 发信密码列表
	private String[] fromList;		// 发信账号地址列表
	private String[] SmtpHostList;	// 发信smtp服务器列表

	static private IBaseDao baseDao;
	
	static int mailerIndex = Math.abs((int) System.currentTimeMillis());
	static public Set<String> curSendTo = new HashSet<String>();
	static int failNum = 0;
	static long deadDate = 0;

	private Mail mail;

	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public static void setBaseservice(IBaseDao baseDao) {
		Mailer.baseDao = Mailer.baseDao;
	}

	/**
	 * 账号信息为JSON格式，形如:[{username:u1, password:p1, from:f1, smtphost:s1}, {username:u2, password:p2, from:f2, smtphost:s2}]
	 * username: 账号
	 * password: 密码
	 *     from: 发件地址
	 * smtphost: SMTP服务器地址
	 */
	private void initMailerAccount() {
		String mailerAccount = (String) ApplicationContainer.sc.getAttribute("MailerAccount");
		JSONArray accounts = JSONArray.fromObject(mailerAccount);
		List uList = new ArrayList();
		List pList = new ArrayList();
		List fList = new ArrayList();
		List sList = new ArrayList();
		for (int i = 0; i < accounts.size(); i++) {
			JSONObject account = accounts.getJSONObject(i);
			uList.add(account.getString("username"));
			pList.add(account.getString("password"));
			fList.add(account.getString("from"));
			sList.add(account.getString("smtphost"));
		}
		usernameList = (String[]) uList.toArray(new String[0]);
		passwordList = (String[]) pList.toArray(new String[0]);
		fromList = (String[]) fList.toArray(new String[0]);
		SmtpHostList = (String[]) sList.toArray(new String[0]);
	}

	public void run(){
		initMailerAccount();

		String mailId = mail.getId();
		if (curSendTo.contains(mailId)){
			System.out.println("【操作终止】该邮件正在发送中!");
			return;
		}
		if (new Date().getTime() - deadDate < 900000){
			System.out.println("【操作终止】距上次全部账号失效还不足15分钟!");
			return;
		}

		synchronized (curSendTo) {
			curSendTo.add(mailId);
			failNum = 0;
			if (mail.getSendto() == null){
				curSendTo.remove(mailId);
				mail.setSendto("");
			}
		}
		String[] toList = mail.getSendto().split("[;\\s]+");
		String sended = mail.getSended();
		String sendTo = "";
		if (sended == null){
			sended = "";
		}
		mail.setSendTimes(-Math.abs(mail.getSendTimes()));
		baseDao.modify(mail);

		int i;
		for (i = 0; i < toList.length && failNum < 10; i++){
			if (toList[i].isEmpty()) {
				continue;
			}
			synchronized (curSendTo) {
				if (!curSendTo.contains(mailId)){
					break;
				}
			}
			int tryNum = 0;
			do {
				try {
					mailerIndex = (mailerIndex + 1) % usernameList.length;
					setSmtpHost(SmtpHostList[mailerIndex]);
					createMimeMessage();
					setFrom(fromList[mailerIndex]);
					setNamePass(usernameList[mailerIndex], passwordList[mailerIndex]);
					setNeedAuth(true);
					setTo(toList[i]);
					setSubject(mail.getSubject());
					setBody(mail.getBody(), mail.getIsHtml());

					if (mail.getAttachment() != null && mail.getAttachmentName() != null){
						String[] attachment = mail.getAttachment().split("[;\\s]+");
						String[] fileName = mail.getAttachmentName().split("[;\\s]+");
						for (int j = 0; j < attachment.length && j < fileName.length; ++j){
							if (!attachment[j].isEmpty() && !fileName[j].isEmpty()){
								System.out.println(attachment[j] + " " + fileName[j]);
								String pathName = ApplicationContainer.sc.getRealPath(attachment[j]);
								File attachFile = new File(pathName);
								if (attachFile != null && attachFile.exists()) {
									addFileAffix(pathName, fileName[j]);
								}
							}
						}
					}

					System.out.println("使用" + (mailerIndex + 1) + "号账号发送邮件!  " + new Date().toString());
					System.out.println("SMTP: " + SmtpHostList[mailerIndex]);
					System.out.println("账号: " + fromList[mailerIndex]);
					System.out.println("密码: " + passwordList[mailerIndex].replaceAll(".", "*"));
					System.out.println("收件人: " + toList[i]);
					mail.setSendTimes(mail.getSendTimes() - 1);
					sendout();

					sended += (sended.isEmpty() ? "" : ";") +  toList[i];
					tryNum = 100000;
					failNum = 0;
				} catch (Exception e) {
					failNum++;
					e.printStackTrace();
					System.out.println("发送失败,更换账号重试!\n\n\n");
				}
				tryNum++;
			} while (tryNum < 5);
			if (tryNum == 5){
				sendTo += (sendTo.isEmpty() ? "" : ";") +  toList[i];
			}
		}
		for (; i < toList.length; i++){
			sendTo += (sendTo.isEmpty() ? "" : ";") +  toList[i];
		}
		if (failNum >= 10){
			deadDate = new Date().getTime();
		}

		mail.setSendTimes(-mail.getSendTimes());
		mail.setFinishedDate(sendTo.isEmpty() ? new Date() : null);
		mail.setSended(sended);
		mail.setSendto(sendTo);
		if (sendTo.isEmpty()){
			mail.setStatus(2);
		}
		baseDao.modify(mail);
		curSendTo.remove(mailId);
	}


	public static void send(Mail mail) throws Exception {
		Mailer mailer = new Mailer();
		mail.setSubject("[qts] " + mail.getSubject());// 给邮件统一添加头
		if(mail.getSubject().length() > 100){
			mail.setSubject(mail.getSubject().substring(0, 100));
		}
		baseDao.modify(mail);
		mailer.setMail(mail);
		mailer.start();
	}
	
	/**
	 * 设置邮件服务器
	 *
	 * @param hostName 邮件服务器名
	 */
	public void setSmtpHost(final String hostName) {
		if (props == null)
			props = System.getProperties();

		props.put("mail.smtp.host", hostName); // 设置邮件服务器

	}

	/**
	 * 创建MIME邮件对象
	 */
	public boolean createMimeMessage() {
		try {
			// System.out.println("准备获取邮件会话对象");
			session = Session.getDefaultInstance(props, null);
		} catch (final Exception e) {
			System.err.println("获取邮件会话对象出错" + e);
			return false;
		}
		// System.out.println("准备创建MIME邮件对象");
		try {
			mimeMsg = new MimeMessage(session);
			mp = new MimeMultipart();
			return true;
		} catch (final Exception e) {
			// System.err.println("创建MIME邮件对象失败!" + e);
			return false;
		}
	}

	/**
	 * 设置是否需要身份验证
	 */
	public void setNeedAuth(final boolean need) {
		// System.out.println("设置SMTP身份验证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();

		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 设置用户名和密码
	 */
	public void setNamePass(final String name, final String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 设置邮件主题
	 */
	public boolean setSubject(final String mailSubject) {
		// System.out.println("设置邮件主题");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (final Exception e) {
			// System.err.println("设置邮件主题出错");
			return false;
		}
	}

	/**
	 * 设置邮件内容
	 */
	public boolean setBody(final String mailBody, final Boolean b_html) {
		try {
			final BodyPart bp = new MimeBodyPart();
			if (b_html )
				bp.setContent("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />" + mailBody, "text/html; charset=UTF-8");
			else
				bp.setText(mailBody);
			mp.addBodyPart(bp);
			return true;
		} catch (final Exception e) {
			// System.err.println("设置邮件正文出错" + e);
			return false;
		}
	}

	/**
	 * 增加邮件附件
	 */
	public boolean addFileAffix(final String filePath, final String fileName) {

		// System.out.println("增加邮件附件" + filename);

		try {
			final BodyPart bp = new MimeBodyPart();
			final FileDataSource fileds = new FileDataSource(filePath);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(MimeUtility.encodeText(fileName));

			mp.addBodyPart(bp);

			return true;
		} catch (final Exception e) {
			// System.err.println("增加邮件附件" + filename + "出错" + e);
			return false;
		}
	}

	/**
	 * 设置发信
	 */
	public boolean setFrom(final String from) {
		// System.out.println("设置发信");
		try {
			mimeMsg.setFrom(new InternetAddress(from));
			return true;
		} catch (final Exception e) {
			// System.out.println("设置发信人出");
			return false;
		}
	}

	/**
	 * 设置收信人
	 */
	public boolean setTo(final String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					(Address[]) InternetAddress.parse(to));
			return true;
		} catch (final Exception e) {
			// System.out.println("设置收信人出错");
			return false;
		}
	}

	/**
	 * 增加收信人
	 */
	public boolean addTo(final String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.addRecipients(Message.RecipientType.TO, to);
			return true;
		} catch (final MessagingException e) {
			// System.out.println("增加收信人出错");
			return false;
		}
	}

	/**
	 * 设置抄送人
	 */
	public boolean setCopyTo(final String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (final Exception e) {
			// System.out.println("设置抄送人出错!");
			return false;
		}
	}

	/**
	 * 增加抄送人
	 */
	public boolean addCopyTo(final String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.addRecipients(Message.RecipientType.CC, to);
			return true;
		} catch (final MessagingException e) {
			// System.out.println("增加抄送人出错");
			return false;
		}
	}

	/**
	 * 设置密送人
	 */
	public boolean setBlindCopyTo(final String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.BCC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (final Exception e) {
			// System.out.println("设置密送人出错!");
			return false;
		}
	}

	/**
	 * 增加密送人
	 */
	public boolean addBlindCopyTo(final String to) {
		if (to == null)
			return false;

		try {
			mimeMsg.addRecipients(Message.RecipientType.BCC, to);
			return true;
		} catch (final MessagingException e) {
			// System.out.println("增加密送人出错");
			return false;
		}
	}

	public void sendout() throws Exception {

		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		System.out.println("正在发送邮件...");
		final Session mailSession = Session.getInstance(props, null);
		final Transport transport = mailSession.getTransport("smtp");
		transport.connect((String) props.get("mail.smtp.host"), username, password);
		transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
		System.out.println("邮件发送成功!\n");
		transport.close();

	}
}

