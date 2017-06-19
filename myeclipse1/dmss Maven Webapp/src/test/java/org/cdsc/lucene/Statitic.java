package org.cdsc.lucene;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Statitic {
	/*
	 * 统计，实现以下功能 (1) 统计term在整个collection中的文档频度(document frequency, DF)； (2)
	 * 统计term在整个collection中出现的词次(term frequency in whole collection)； (3)
	 * 统计term在某个文档中出现的频度(term frequency, TF)； (4) 列出term在某文档中出现的位置(position)；
	 * (5) 整个collection中文档的个数；
	 */

	public static void printIndex(IndexReader reader) throws Exception {

		// 显示document数
		System.out.println(new Date() + "\n");
		System.out.println(reader + "\t该索引共含 " + reader.numDocs() + "篇文档\n");

		for (int i = 0; i < reader.numDocs(); i++) {
			System.out.println("文档" + i + "：" + reader.document(i) + "\n");
		}
		
		// 枚举term，获得<document, term freq, position* >信息
		Term term = new Term("text", "中文");
		long termFreq= reader.totalTermFreq(term);

		System.out.println("词条频度"+termFreq);
		/*while (termEnum.next()) {
			System.out.println("\n" + termEnum.term().field() + "域中出现的词语："
					+ termEnum.term().text());
			System.out.println(" 出现改词的文档数=" + termEnum.docFreq());

			TermPositions termPositions = reader.termPositions(termEnum.term());
			int i = 0;
			int j = 0;
			while (termPositions.next()) {
				System.out.println("\n" + (i++) + "->" + "    文章编号:"
						+ termPositions.doc() + ", 出现次数:"
						+ termPositions.freq() + "    出现位置：");
				for (j = 0; j < termPositions.freq(); j++)
					System.out.println("[" + termPositions.nextPosition() + "]");
				System.out.println("\n");
			}

			
			 * TermDocs termDocs=reader.termDocs(termEnum.term());
			 * while(termDocs.next()){
			 * System.out.println((i++)+"->DocNo:"+termDocs.doc()+
			 * ",Freq:"+termDocs.freq()); }
			 
		}*/

	}

	public static void main(String args[]) throws Exception {
		String fieldName = "text";
		 //检索内容
		String text = "中文IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。中文分词";
		
		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			//建立内存索引对象
			directory = new RAMDirectory();	 
			
			//配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_40 , analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory , iwConfig);
			//写入索引
			Document doc = new Document();
			doc.add(new StringField("ID", "10000", Field.Store.YES));
			doc.add(new TextField(fieldName, text, Field.Store.YES));
			iwriter.addDocument(doc);
			//
			Document doc1 = new Document();
			doc1.add(new StringField("ID", "11", Field.Store.YES));
			doc1.add(new TextField(fieldName, "中文你好", Field.Store.YES));			
			iwriter.addDocument(doc1);
			
			iwriter.close();
			
			
	
			ireader = DirectoryReader.open(directory);
			printIndex(ireader);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(ireader != null){
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(directory != null){
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

	}
}