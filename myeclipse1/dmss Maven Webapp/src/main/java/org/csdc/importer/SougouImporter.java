package org.csdc.importer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.csdc.dao.JdbcDao;
import org.csdc.model.Account;
import org.csdc.service.imp.AccountService;
import org.csdc.service.imp.CategoryService;
import org.csdc.service.imp.ImportService;
import org.csdc.service.imp.IndexService;
import org.csdc.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * 搜狗数据导入器
 * @author jintf
 * @date 2014-6-15
 */
@Component
@Transactional
public class SougouImporter {
	
private String basePath = "E:\\project\\dmss";

	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ImportService importService;
	
	public void importTxt(String type){
		String categoryPath = "/mergeFileTest/"+type;
		String categoryId = categoryService.getCatgeoryIdByCategoryString(categoryPath,true);
		Account account = accountService.getAccountByAccountName("admin");
		
		File dir = new File(basePath+"/"+type);
		String[] fileNames = dir.list();
		System.out.println("共导入："+fileNames.length);
		for (int i = 0; i < fileNames.length; i++) {
			try {
				importService.importDocument(new File(basePath+"/"+type+"/"+fileNames[i]),FileTool.getFilePrefix(fileNames[i]) ,"MergeFile", "test;"+type, categoryId, account,categoryPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("当前导入："+i);
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		SougouImporter importer = (SougouImporter) ac.getBean("sougouImporter");
		importer.importTxt("招聘");
		System.out.println("招聘");
	}
}
