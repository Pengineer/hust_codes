package csdc.tool.execution;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Mail;
import csdc.bean.SystemConfig;
import csdc.dao.HibernateBaseDao;
import csdc.dao.JdbcDao;
import csdc.service.imp.ExpertService;
import csdc.service.imp.MailService;
import csdc.tool.ApplicationContainer;
import csdc.tool.StringTool;
import csdc.tool.execution.importer.Importer;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 生成通知各个学校完善专家库的邮件写入数据库
 * @author xuhan
 *
 */
public class GenerateExpertLibNoticeMails extends Importer {
	
	@Autowired 
	private ExpertService expertService;
	@Autowired
	private MailService mailService;
	private JdbcTemplateReader reader;
	private String type;
	@Autowired
	private HibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		//邮件内容模板
		type = ((SystemConfig) dao.query(SystemConfig.class, "sysconfig00034")).getValue();
		Map map = new HashMap();
		map.put("type", type);
		String body = dao.query("select m.body from Mail m where m.template = :type", map).get(0).toString();
//		Set<String> univCode = new HashSet<String>(dao.query("select distinct expert.universityCode from Expert expert"));
		reader.query(
		"select agency.c_code, agency.c_name, director.c_email, linkman.c_email " +
		"from t_agency agency " +
		"left join t_person director on agency.C_S_DIRECTOR_ID = director.c_id " +
		"left join t_person linkman on agency.C_S_LINKMAN_ID = linkman.c_id " +
		"where (director.c_email is not null or linkman.c_email is not null ) " +
		"and (agency.c_style like '%11%' or agency.c_style like '%12%' or agency.c_style like '%13%' or agency.c_style like '%14%' or agency.c_style like '%22%') " +
		"order by agency.c_code");//正常发送的sql
//		reader.query(
//				"select agency.c_code, agency.c_name, director.c_email, linkman.c_email , agency.C_S_EMAIL " +
//				"from t_agency agency " +
//				"left join t_person director on agency.C_S_DIRECTOR_ID = director.c_id " +
//				"left join t_person linkman on agency.C_S_LINKMAN_ID = linkman.c_id " +
//				"where (director.c_email is not null or linkman.c_email is not null or agency.C_S_EMAIL is not null) " +
//				"and to_char(agency.C_IMPORTED_DATE, 'yyyy-MM-dd') = '2014-11-25' " +
//				"order by agency.c_code");//补发邮件的hql
//		reader.query(
//				"select agency.c_code, agency.c_name, linkman.c_email " +
//				"from t_agency agency " +
//				"left join t_person linkman on agency.C_S_LINKMAN_ID = linkman.c_id " +
//				"where linkman.c_email is not null " +
//				"and to_char(agency.C_IMPORTED_DATE, 'yyyy-MM-dd') = '2014-11-25' " +
//				"order by agency.c_code");//补发邮件的hql
		while (next(reader)) {
//			if (!univCode.contains(A)) {//没有专家信息的学校不生成邮件
//				continue;
//			}
			Mail mail = new Mail();
			dao.add(mail);
			String id = mail.getId();
			String zipRealpath = expertService.createExpertZip(A, id);
			zipRealpath = zipRealpath.replace('\\', '/');
			String sysPath = ApplicationContainer.sc.getRealPath("temp");
			sysPath = sysPath.replace('\\', '/');
			File zipFile = new File(sysPath + zipRealpath);
			String zipPath = mailService.renameFile(mail.getId(), zipFile);
			String docRealPath = ApplicationContainer.sc.getRealPath("file/template/expert");
			docRealPath = docRealPath.replace('\\', '/');
			String path = docRealPath + "/" + "教育部人文社会科学专家信息表填报说明.doc";
			File docFile = new File(path);
			String docPath = mailService.renameFile(mail.getId(), docFile);
			if (C!=null && C.contains(";")) {
				mail.setTo(C.substring(0, C.indexOf(";")));
			} else {
				mail.setTo(C);
			}
			if (D!=null && D.contains(";")) {
				mail.setCc(D.substring(0, D.indexOf(";")));
			} else {
				mail.setCc(D);
			}
//			mail.setBcc("");//可以密送至发送邮件负责的邮箱
			mail.setReplyTo("moesk@bnu.edu.cn");
			mail.setSubject("教社科司函[2014]232号：关于进一步完善教育部人文社会科学专家库的通知");
			mail.setBody(body);
			mail.setIsHtml(1);
			mail.setCreateDate(new Date());
			List<String> attachments = new ArrayList<String>();
			List<String> attachmentNames = new ArrayList<String>();
			attachments.add(zipPath);
			attachments.add(docPath);
			Calendar cal = Calendar.getInstance();
			attachmentNames.add("MOEExpert" + cal.get(Calendar.YEAR) + "_" + A+ ".zip");
			attachmentNames.add("教育部人文社会科学专家信息表填报说明.doc");
			mail.setAttachment(StringTool.joinString(attachments.toArray(new String[0]), "; "));
			mail.setAttachmentName(StringTool.joinString(attachmentNames.toArray(new String[0]), "; "));
			dao.modify(mail);
		}
	}
	
	public GenerateExpertLibNoticeMails() {
	}
	
	public GenerateExpertLibNoticeMails(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
	
}
