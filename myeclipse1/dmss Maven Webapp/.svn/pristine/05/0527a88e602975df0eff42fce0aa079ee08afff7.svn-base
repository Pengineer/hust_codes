package org.cdsc.solrcloud;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.csdc.model.Document;

public class DataGenerator {
	public static String SOLR_SERVER = "http://192.168.88.90:8080/solr/mycollection";
	public static void main(String[] args) throws IOException, SolrServerException {
		String allDir = "E:\\系自主创新\\研究生论文\\分类测试1";
		Collection<File> allFiles = FileUtils.listFiles(new File(allDir), null, true);
		SolrServer server = new HttpSolrServer(SOLR_SERVER);
		int n = 0;
		for (File f:allFiles) {
			if(f.isFile()){
				SolrInputDocument solrDoc = new SolrInputDocument();
				solrDoc.addField("id", f.hashCode());
				solrDoc.addField("name", f.getName());
				solrDoc.addField("core0", FileUtils.readFileToString(f,"GBK"));
				server.add(solrDoc);
			}
			System.out.println(n++);
		}
		server.commit();		
	}
}
