package org.csdc.importer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.csdc.dao.IBaseDao;
import org.csdc.model.Document;
import org.csdc.service.imp.IndexService;
import org.csdc.tool.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * 索引导入器
 * 功能说明：
 *   执行main函数实现为文档添加索引，添加索引为多线程过程
 * @author jintf
 * @date 2014-6-15
 */
@Component
@Transactional
public class IndexImporter {
	@Autowired
	private IndexService indexService;
	
	public static final String SOLR_SERVER = "http://192.168.88.151:8080/solr/dmss";
	
	@Autowired
	private IBaseDao baseDao;
	
	public void importIndex(){	
		List<Document> documents = baseDao.query("select d from Document d where d.sourceAuthor='搜狗工作室' and d.indexed=0 and d.deleted=0  order by d.createdDate asc",0,10000);
		for (Integer i = 0; i < 10; i++) {
			startThread(i.toString(),documents.subList(i*1000, (i+1)*1000));
		}
	}
	
	public void startThread(final String name,final List<Document> documents){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				indexService.createIndex(documents);
				System.out.println("end:"+name);
			}
		}).start();
	}
	
	public void deleteAllIndex(){
		String url = SOLR_SERVER+"/update";
		Map param = new HashMap();
		param.put("stream.body", "<delete><query>*:*</query></delete>");
		param.put("stream.contentType", "text/xml;charset=utf-8");
		param.put("commit", true);
		HttpTool.post(url,param);
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		IndexImporter importer = (IndexImporter) ac.getBean("indexImporter");
		Date startDate = new Date();
		importer.importIndex();
		System.out.println("cost:"+(new Date().getTime()-startDate.getTime())/1000);
	}
}
