package org.csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.csdc.model.Document;
import org.csdc.tool.FileTool;
import org.csdc.tool.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.tools.corba.se.idl.constExpr.And;

/**
 * 索引管理业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class IndexService extends BaseService{
	
	@Autowired
	private DocumentService documentService;
	/**
	 * 为所有未索引文件添加索引
	 */
	public void updateIndex(){
		List<Document> documents = baseDao.query("select d from Document d where d.deleted = false and d.indexed = false");
		try {
			SolrServer server = new HttpSolrServer(application.getSolrServerUrl());
			for (Document document:documents) {
				server.add(getSolrDoc(document));
				document.setIndexed(true);
				baseDao.modify(document);
			}
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 删除所有索引
	 */
	public void deleteAllIndex(){
		String url = application.getSolrServerUrl()+"/update";
		Map param = new HashMap();
		param.put("stream.body", "<delete><query>*:*</query></delete>");
		param.put("stream.contentType", "text/xml;charset=utf-8");
		param.put("commit", true);
		HttpTool.post(url,param);
		baseDao.execute("update Document d set d.indexed = false");
	}
	
	/**
	 * 重建所有索引
	 */
	public void rebuildAllIndex(){
		deleteAllIndex();
		updateIndex();
	}
	
	/**
	 * 从索引中删除指定ID的文档
	 * @param id
	 */
	public void deleteIndex(String id){
		String url = application.getSolrServerUrl()+"/update";
		Map param = new HashMap();
		param.put("stream.body", "<delete><id>"+id+"</id></delete>");
		param.put("stream.contentType", "text/xml;charset=utf-8");
		param.put("commit", true);
		HttpTool.post(url,param);
		//baseDao.execute("update Document d set d.indexed = false");
	}
	
	/**
	 * 更新指定ID的索引
	 * @param id
	 */
	public void updateIndex(String id){
		deleteIndex(id);
		createIndex(id);
	}
	
	/**
	 * 创建文档索引
	 * @param id 文档ID
	 */
	public void createIndex(String id){
		try {
		
			SolrServer server = new HttpSolrServer(application.getSolrServerUrl());
			Document document = baseDao.query(Document.class,id);
			server.add(getSolrDoc(document));
			document.setIndexed(true);
			baseDao.modify(document);
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void createIndex(List<Document> documents){
		try {
			SolrServer server = new HttpSolrServer(application.getSolrServerUrl());
		    for (Document document : documents) {
		    	server.add(getSolrDoc(document));
				document.setIndexed(true);
				baseDao.modify(document);
			}
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 把Document转为SolrDocument对象
	 * @param documentId 文档ID
	 * @return
	 */
	public SolrInputDocument getSolrDoc(Document document){
		SolrInputDocument solrDoc = new SolrInputDocument();
		solrDoc.addField("id", document.getId());
		solrDoc.addField("title", document.getTitle());
		solrDoc.addField("version", document.getVersion());
		solrDoc.addField("author", document.getSourceAuthor());
		solrDoc.addField("tags", document.getTags());
		solrDoc.addField("file_size", document.getFileSize());
		solrDoc.addField("rating", document.getRating());
		solrDoc.addField("created_date", document.getCreatedDate());
		solrDoc.addField("category", document.getCategoryString());
		solrDoc.addField("content_type", document.getType());
		solrDoc.addField("last_modified_date", document.getLastModifiedDate());
		solrDoc.addField("content", documentService.getDocumentText(document.getId()));
		return solrDoc;
	}

}
