package csdc.tool;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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

import csdc.bean.common.Mail;

public class Mailer extends Thread{

	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等內容均添加到其中后再生成MimeMessage对象

	private String[] usernameList ={
			"csdcmail7",
			"1204903290",
			"1336828330",
			"1343741968",
			"csdcmail1",
			"csdcmail2", 
			"csdcmail3", 
			"csdcmail4",
			"csdcmail5",
			"csdcmail6"
	};		// 发信用户名列表

	private String[] passwordList = {
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123",
			"csdc123"
	};		// 发信密码列表
	
	private String[] SMTPserver = {
			"163.com",
			"qq.com",
			"qq.com",
			"qq.com",
			"126.com",
			"126.com",
			"126.com",
			"163.com",
			"163.com",
			"163.com"
	};		// 发信smtp服务器列表

	static int mailerIndex = Math.abs((int) System.currentTimeMillis());
	static public Set<String> curSendTo = new HashSet<String>();
	static int failNum = 0;
	static long deadDate = 0;

	private String username, password;
	private Mail mail;
	
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void run(){
		int tryNum = 0;
		do {
			try {
				mailerIndex = (mailerIndex + 1) % usernameList.length;
				setSmtpHost("smtp." + SMTPserver[mailerIndex]);
				createMimeMessage();
				setFrom(usernameList[mailerIndex] + "@" + SMTPserver[mailerIndex]);
				setNamePass(usernameList[mailerIndex], passwordList[mailerIndex]);
				setNeedAuth(true);
				setTo(mail.getSendTo());
				setSubject(mail.getSubject());
				setBody(mail.getBody(), 1);

				System.out.println("使用" + (mailerIndex + 1) + "号账号发送邮件!  " + new Date().toString());
				System.out.println("SMTP: " + "smtp." + SMTPserver[mailerIndex]);
				System.out.println("账号: " + usernameList[mailerIndex] + "@" + SMTPserver[mailerIndex]);
				System.out.println("密码: " + passwordList[mailerIndex].replaceAll(".", "*"));
				System.out.println("收件人: " + mail.getSendTo());
				sendout();

				tryNum = 100000;
				failNum = 0;
			} catch (Exception e) {
				failNum++;
				System.out.println(e);
				System.out.println("发送失败,更换账号重试!\n\n\n");
			}
			tryNum++;
		} while (tryNum < 5);
		if (failNum >= 10){
			deadDate = new Date().getTime();
		}
	}

	public static void send(Mail mail) throws Exception {
		Mailer mailer = new Mailer();
		mailer.setMail(mail);
		mailer.start();
	}
	
	/**
	 * 判重收件人列表中的重复项、去除非法地址
	 * @return 跳转成功
	 */
	static public String getUniqueList(String st){
		String[] toList = st.split("[^a-zA-Z0-9-_\\.\\@]");
		String ret = "";
		Arrays.sort(toList, 0, toList.length);
		for (int i = 0; i < toList.length; i++){
			if (toList[i].matches("\\S+\\@\\S+") && (i == 0 || !toList[i].equals(toList[i-1]))){
				ret += ((ret.isEmpty()) ? "" : ";") + toList[i];
			}
		}
		System.out.println("ret: " + ret);
		return ret;
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
	public boolean setBody(final String mailBody, final int b_html) {
		try {
			final BodyPart bp = new MimeBodyPart();
			if (b_html > 0)
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
	public boolean addFileAffix(final String filename) {
		try {
			final BodyPart bp = new MimeBodyPart();
			final FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());

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
		transport.connect((String) props.get("mail.smtp.host"), username,
				password);
		transport.sendMessage(mimeMsg, mimeMsg
				.getRecipients(Message.RecipientType.TO));
		System.out.println("邮件发送成功!\n");
		transport.close();
	}
}
