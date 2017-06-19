package csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Account;
import csdc.bean.Mail;
import csdc.service.IMailService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.SignID;

@SuppressWarnings("unchecked")
@Transactional
public class MailService extends BaseService implements IMailService {

	/**
	 * 添加邮件
	 * @param mail新增邮件对象
	 * @return mailId
	 */
	public String addMail(Mail mail) throws Exception {
		if (mail.getAccount() == null){
			mail.setAccount((Account) dao.query(Account.class, "admin"));
		}
		mail.setCreateDate(new Date());// 发件时间
		mail.setFinishDate(null);// 完成时间
		mail.setSendTimes(0);
		mail.setStatus(0);
		String id = dao.add(mail);// 添加邮件
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
			List<Mail> mails = dao.query("select m from Mail m left join fetch m.account a where m.id = :mailId", map);
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
					mail = (Mail) dao.query(Mail.class, mailId);
					if (mail.getAttachment() != null) {
						fileArray = mail.getAttachment().split("; ");
						if (fileArray != null && fileArray.length != 0) {
							for (String fileName : fileArray) {
								FileTool.fileDelete(fileName);// 删除附件
							}
						}
					}
					dao.delete(mail);// 删除邮件
				}
			}
		}
	}


	/**
	 * 获取接收者邮箱列表
	 * 接收人员类型[由0和1组成的15位字符串，0表示不是对应类型不接收，1表示是对应类型接收，对应类型从左至右依次为：(1)部级机构、(2)部级管理人员、(3)省级机构、(4)省级管理人员、(5)部属高校、(6)部属高校管理人员、(7)地方高校、(8)地方高校管理人员、(9)高校院系、(10)高校院系管理人员、(11)研究机构、(12)研究机构管理人员、(13)外部专家、(14)内部专家、(15)学生]
	 * @param id待发送的邮件ID集合
	 */
	public List<String> generateEmailList(List recieverType) {
		List<String> emails = new ArrayList<String>();

//		//省厅管理员
//		if (recieverType.contains(0)){
//			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency ag where ag.type = 2 "));
//		}
//
//		//部属高校管理员
//		if (recieverType.contains(1)){
//			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency u where u.type = 3 and o.department.id is null and o.institute.id is null "));
//		}
//
//		//地方高校管理员
//		if (recieverType.contains(2)){
//			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency u where u.type = 4 and o.department.id is null and o.institute.id is null "));
//		}
//
//		//部属基地管理员
//		if (recieverType.contains(3)){
//			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.institute ins left join ins.type type where type.code = 1 "));
//		}
//
//		//省部共建基地管理员
//		if (recieverType.contains(4)){
//			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.institute ins left join ins.type type where type.code = 2 "));
//		}
		
		// 15类：(1)部级机构、(2)部级管理人员、(3)省级机构、(4)省级管理人员、(5)部属高校、(6)部属高校管理人员、(7)地方高校、(8)地方高校管理人员、(9)高校院系、(10)高校院系管理人员、(11)研究机构、(12)研究机构管理人员、(13)外部专家、(14)内部专家、(15)学生
		
		// 0: 部级机构
		if (recieverType.contains("ministry")){
			emails.addAll(dao.query("select ag.email from Agency ag where ag.type = 1 "));
		}

		// 1: 部级管理人员
		if (recieverType.contains("ministryOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency ag where ag.type = 1 "));
		}

		// 2: 省级机构
		if (recieverType.contains("province")){
			emails.addAll(dao.query("select ag.email from Agency ag where ag.type = 2 "));
		}

		// 3: 省级管理人员
		if (recieverType.contains("provinceOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency ag where ag.type = 2 "));
		}

		// 4: 部属高校
		if (recieverType.contains("ministryUniversity")){
			emails.addAll(dao.query("select ag.email from Agency ag where ag.type = 3 "));
		}

		// 5: 部属高校管理人员
		if (recieverType.contains("ministryUniversityOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency u where u.type = 3 and o.department.id is null and o.institute.id is null "));
		}

		// 6: 地方高校
		if (recieverType.contains("localUniversity")){
			emails.addAll(dao.query("select ag.email from Agency ag where ag.type = 4 "));
		}

		// 7: 地方高校管理人员
		if (recieverType.contains("localUniversityOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.agency u where u.type = 4 and o.department.id is null and o.institute.id is null "));
		}

		// 8: 院系
		if (recieverType.contains("department")){
			emails.addAll(dao.query("select d.email from Department d "));
		}

		// 9: 院系管理人员
		if (recieverType.contains("departmentOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.department d "));
		}

		// 10: 研究机构
		if (recieverType.contains("institute")){
			emails.addAll(dao.query("select i.email from Institute i "));
		}

		// 11: 研究机构管理人员
		if (recieverType.contains("instituteOfficer")){
			emails.addAll(dao.query("select p.email from Officer o left join o.person p left join o.institute ins "));
		}

		// 12: 教育系统外部专家
		if (recieverType.contains("expert")){
			emails.addAll(dao.query("select p.email from Expert e left join e.person p "));
		}

		// 13: 教师
		if (recieverType.contains("teacher")){
			emails.addAll(dao.query("select p.email from Teacher t left join t.person p "));
		}

		// 14: 学生
		if (recieverType.contains("student")){
			emails.addAll(dao.query("select p.email from Student s left join s.person p "));
		}
		return emails;
	}
	
	public String renameFile(String id, File file) {
		String fileName = file.getName();
		String extendName = fileName.substring(fileName.lastIndexOf("."));
		String realPath = ApplicationContainer.sc.getRealPath("");
		String realName = "mail_"+id+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"_"+ SignID.getRandomString(4) +extendName;
		String filepath = (String) ApplicationContainer.sc.getAttribute("MailFileUploadPath");
		realPath = realPath.replace('\\', '/');
		String path = realPath + filepath + "/" + realName;
	try {
		FileTool.mkdir_p(realPath + filepath + "/");
		File x = new File(path);
		FileUtils.copyFile(file, x);
		filepath = filepath + "/" + realName;
		return filepath;
	} catch (IOException e) {
		List filenames = getFiles(filepath);
		for (int i = 0; i < filenames.size(); i++) {
			String name = (String) filenames.get(i);
			if (name.contains(id)) {
				File file2 = new File(filepath+ name);
				file2.delete();
			}
		}
		e.printStackTrace();
		return null;
	}
}

	public List getFiles(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		List file_names = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {// 判断是否是目录
				file_names.add(files[i].getName() + "=>它是一个文件夹");
			}
			if (files[i].isHidden()) {// 判断是否是隐藏文件
				file_names.add(files[i].getName() + "=>它是一个隐藏文");
			}
			if (files[i].isFile() && (!files[i].isHidden())) {// 判断是否是文件并不能是隐藏文件
				file_names.add(files[i].getName());
			}
		}
		return file_names;
	}

}
