package org.csdc.importer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.csdc.dao.JdbcDao;
import org.csdc.model.Account;
import org.csdc.service.imp.AccountService;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * SMDB文档数据导入器
 * 
 * @author jintf
 * @date 2014-6-15
 */
@Component
public class SmdbImporter {
	private String basePath = "E:\\smdbhome\\";

	@Autowired
	private JdbcDao smdbDao;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImportService importService;

	public void importAward() {
		String categoryPath = "/SMDB/award/moesocial/app/2012";
		String categoryId = categoryService.getCatgeoryIdByCategoryString(
				categoryPath, true);
		Account account = accountService.getAccountByAccountName("smdb");
		// 查询成果申请人、所在机构、所属学科、成果名、申请年度、文件位置
		List<String[]> results = smdbDao
				.query("select C_APPLICANT_NAME,C_AGENCY_NAME,C_DISCIPLINE_TYPE,C_PRODUCT_NAME,C_YEAR,C_FILE from T_AWARD_APPLICATION");
		System.out.println("共导入：" + results.size());
		for (int i = 0; i < results.size(); i++) {
			String[] item = results.get(i);
			if (item[5] != null) {
				String title = item[3];
				title = title.replaceAll("[《》，,;；。.]", "");
				title = title.replaceAll("\\s+", "");
				String tag = item[1] + ";" + item[2] + ";" + item[4];
				try {
					importService.importDocument(new File(basePath
							+ File.separator + item[5]), item[3], item[0], tag,
							categoryId, account, categoryPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("not found!");
			}
			System.out.println("当前导入：" + i);
		}
	}

	/**
	 * 导入基地项目中检文档
	 */
	public void importProjectInstpMid(int year, String projectType) {
		String categoryPath = "/SMDB/project/" + projectType + "/mid/" + year;
		String categoryId = categoryService.getCatgeoryIdByCategoryString(
				categoryPath, true);
		Account account = accountService.getAccountByAccountName("smdb");
		// 查询中检年度，项目名，项目编号，中检审核人，中检审核时间，中检文件
		List<String[]> results = smdbDao
				.query("select  g.C_TYPE, g.C_NAME, g.C_NUMBER,mid.C_FINAL_AUDITOR_NAME,mid.C_FINAL_AUDIT_DATE  ,mid.C_FILE,app.C_APPLICANT_NAME from T_PROJECT_MIDINSPECTION mid,T_PROJECT_GRANTED  g,T_PROJECT_APPLICATION app where mid.C_GRANTED_ID = g.C_ID and g.C_APPLICATION_ID = app.C_ID and mid.C_PROJECT_TYPE='instp'and to_char(mid.C_APPLICANT_SUBMIT_DATE,'yyyy')='"
						+ year + "'");
		System.out.println("共导入：" + results.size());
		for (int i = 0; i < results.size(); i++) {
			String[] item = results.get(i);
			if (item[5] != null) {
				String projectName = item[1];
				projectName = projectName.replaceAll("[《》，,;；。.]", "");
				projectName = projectName.replaceAll("\\s+", "");
				String title = projectName + "_基地" + "_中检_" + year;
				String tag = item[0] + ";" + item[1] + ";" + item[2] + ";"
						+ item[3] + ";" + item[4];
				try {
					importService.importDocument(new File(basePath
							+ File.separator + item[5]), title, item[6], tag,
							categoryId, account, categoryPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("not found!");
			}
			System.out.println("当前导入：" + i);
		}
	}

	public void importProjectGeneralEnd(int year, String projectType) {
		String categoryPath = "/SMDB/project/" + projectType + "/end/" + year;
		String categoryId = categoryService.getCatgeoryIdByCategoryString(
				categoryPath, true);
		Account account = accountService.getAccountByAccountName("smdb");

		List<String[]> results = smdbDao
				.query("select  g.C_TYPE, g.C_NAME, g.C_NUMBER,e.C_FINAL_AUDITOR_NAME,e.C_FINAL_AUDIT_DATE  ,e.C_FILE,app.C_APPLICANT_NAME from T_PROJECT_ENDINSPECTION e,T_PROJECT_GRANTED  g,T_PROJECT_APPLICATION app where e.C_GRANTED_ID = g.C_ID and g.C_APPLICATION_ID = app.C_ID and e.C_PROJECT_TYPE='general'and to_char(e.C_APPLICANT_SUBMIT_DATE,'yyyy')= '"
						+ year + "' and e.C_FILE is not null");
		for (int i = 0; i < results.size(); i++) {
			String[] item = results.get(i);
			for (int j = 0; j < item.length; j++) {
				System.out.print(item[j]);
			}
			System.out.println();
			if (null != item[5]) {
				String projectName = item[1];
				projectName = projectName.replaceAll("[《》，,;；。.]", "");
				projectName = projectName.replaceAll("\\s+", "");
				String title = projectName + "_一般" + "_终检_" + year;
				String tag = item[0] + ";" + item[1] + ";" + item[2] + ";"
						+ item[3] + ";" + item[4];
				try {
					importService.importDocument(new File(basePath + File.separator
							+ item[5]), title, item[6], tag, categoryId, account,
							categoryPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("not found!");
			}
		}
		System.out.println("共导入：" + results.size());
	}

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SmdbImporter importer = (SmdbImporter) ac.getBean("smdbImporter");
		Date startDate = new Date();
		importer.importProjectGeneralEnd(2008, "general");
		System.out.println("cost:"
				+ (new Date().getTime() - startDate.getTime()) / 1000);
	}

}
