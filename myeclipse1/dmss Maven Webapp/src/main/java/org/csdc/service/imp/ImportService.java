package org.csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.csdc.model.Account;
import org.csdc.model.Category;
import org.csdc.model.Document;
import org.csdc.tool.FileTool;
import org.csdc.tool.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据导入业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class ImportService extends DocumentService{
	
	/**
	 * 文档导入
	 * @param file 文件
	 * @param title 标题
	 * @param author 作者
	 * @param tags 标签
	 * @param categoryId 分类ID
	 * @param account 账号
	 * @param categoryString 分类字符串
	 * @return
	 * @throws IOException
	 */
	public String importDocument(File file,String title,String author,String tags,String categoryId,Account account,String categoryString) throws IOException{				
		//创建文档
		Document document = new Document();
		Category category = baseDao.query(Category.class,categoryId);	
		document.getCategories().add(category);
		document.setSourceAuthor(author);
		document.setTags(tags);
		document.setCategoryString(categoryString);
		document.setCreator(account);
		document.setAccountName(account.getName());
		document.setTitle(title);
		document.setType(FileTool.getExtension(file).toLowerCase());
		String md5 = MD5.getMD5(file); 
		document.setFingerprint(md5);
		document.setPath(md5toPath(md5));
		document.setFileSize(file.length());
		document.setCreatedDate(new Date());
		document.setLastModifiedDate(new Date());
		document.setVersion(1);
		document.setLocked(false);
		document.setDeleted(false);
		document.setImmutable(false);
		document.setIndexed(false);
		if(application.isHadoopOn()){	
			String [] o =hdfs.push(file.getAbsolutePath());
			document.setBlockId(o[0]);
			document.setFileId(o[1]);
		}else{
			FileUtils.copyFile(file, new File(getFetchPath(document)));
		}
		return (String)baseDao.add(document);
	}

}
