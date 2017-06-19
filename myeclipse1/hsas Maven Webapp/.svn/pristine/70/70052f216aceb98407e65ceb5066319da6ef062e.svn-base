package csdc.tool.mail;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;


import csdc.model.Mail;
import csdc.tool.ApplicationContainer;

/**
 * 邮件任务
 * @author xuhan
 *
 */
public class MailTask implements Runnable {
	
	private Mail mail;
	
	/**
	 * 真正的邮件发送者，使用Spring的邮件支持
	 */
	@Autowired
	private JavaMailSenderImpl sender;
	
	/**
	 * 最多发送次数
	 */
	private static int MAX_ATTEMPT_NUMBER = 5;
	
	
	public MailTask(Mail mail, JavaMailSenderImpl sender) {
		this.mail = mail;
		this.sender = sender;
	}
	
	public MailTask() {
	}
	
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		/*
		 * 如果发送过程中抛出异常，则等待一分钟后重发该邮件。总共最多尝试MAX_ATTEMPT_NUMBER次。 
		 */
		int failTime = 0;
		while (true) {
			try {
		    	System.out.println(sdf.format(new Date()) + " 开始第" + (failTime + 1) + "次发送邮件:" + mail.getId());
				send();
		    	System.out.println(sdf.format(new Date()) + " 第" + (failTime + 1) + "次发送邮件成功:" + mail.getId());
				return;
			} catch (Throwable t) {
		    	System.out.println(sdf.format(new Date()) + " 第" + (failTime + 1) + "次发送邮件失败:" + mail.getId());
		    	t.printStackTrace();
				if (++failTime >= MAX_ATTEMPT_NUMBER) {
					throw new RuntimeException(t);
				}
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void send() throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = sender.createMimeMessage();
		
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

		if (mail.getSendTo() != null) {
			for (String to : getDistinctAddresses(mail.getSendTo())) {
				helper.addTo(to);
			}
		}
		if (mail.getCc() != null) {
			for (String cc : getDistinctAddresses(mail.getCc())) {
				helper.addCc(cc);
			}
		}
		if (mail.getBcc() != null) {
			for (String bcc : getDistinctAddresses(mail.getBcc())) {
				helper.addBcc(bcc);
			}
		}
		if (mail.getFrom() != null) {
			helper.setFrom(mail.getFrom());
		} else if (mail.getReplyTo() != null) {
			helper.setFrom(mail.getReplyTo());
		} else {
			helper.setFrom("CSDC");
		}
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), mail.getIsHtml() != 0);
		helper.setReplyTo(mail.getReplyTo());
		
		if (mail.getAttachment() != null) {
			for (String attachment : mail.getAttachment().split("\\s*[;；]\\s*")) {
				FileSystemResource file = new FileSystemResource(ApplicationContainer.sc.getRealPath(attachment));
				if(file.exists()){
					helper.addAttachment(new String(mail.getAttachmentName().getBytes("gb2312"), "ISO8859-1"), file);
				}
			}
		}

		sender.send(message);
	}
	
	/**
	 * 判重地址列表中的重复项、去除非法地址
	 */
	private List<String> getDistinctAddresses(String address){
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

	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}

}
