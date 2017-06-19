package org.cdsc.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexCreator {
	public static void main(String[] args) {
		Analyzer analyzer = new IKAnalyzer(true);
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try{
			directory = FSDirectory.open(new File("F:\\solr_home\\solr\\collection1\\data\\index")); 
			//配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_46 , analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			/*iwriter = new IndexWriter(directory , iwConfig);
			Document doc = new Document();
			doc.add(new StringField("ID", "10000", Field.Store.YES));
			doc.add(new TextField("title", "如果觉得这种哲学描述太抽象的话，原文中有一个关于Unix中断处理的例子，非常生动。一位MIT的教授一直困恼于Syscall处理时间过长出现中断时如何保护用户进程某些状态，从而让用户进程能继续执行。他问新泽西人，Unix是怎么处理这个问题。新泽西人说，Unix只支持大多数Syscall处理时间较短的情况，如果时间太长出现中断Syscall不能完成，那就会返回一个错误码，让用户重新调用Syscall。但MIT人不喜欢这个解决方案，因为这不是“正确的做法”。", Field.Store.YES));
			//iwriter.addDocument(doc);
			iwriter.close();*/
			//搜索过程**********************************
		    //实例化搜索器   
			ireader = DirectoryReader.open(directory);
			isearcher = new IndexSearcher(ireader);			
			System.out.println("共有文档"+ireader.numDocs());
			String keyword = "处理时间过长出现中断时";			
			//使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser(Version.LUCENE_46, "title",  analyzer);
			qp.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = qp.parse(keyword);
			System.out.println("Query = " + query);
			
			//搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query , 5);
			System.out.println("命中：" + topDocs.totalHits);
			//输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("内容：" + targetDoc.toString());
				System.out.println(scoreDocs[i].score);
			}	
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
