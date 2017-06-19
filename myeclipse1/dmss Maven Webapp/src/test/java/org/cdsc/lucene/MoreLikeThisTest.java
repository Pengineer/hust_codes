package org.cdsc.lucene;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.csdc.tool.FileTool;
import org.wltea.analyzer.lucene.IKAnalyzer;

import antlr.collections.impl.Vector;

public class MoreLikeThisTest {
	 public static void main(String[] args) throws Throwable {  
		 	String text1 = FileTool.readFileContent("E:\\系自主创新\\研究生论文\\分类测试文本\\sample\\汽车\\汽车_877.txt").toString();
	        String indexDir = "F:\\solr_home\\solr\\collection1\\data\\index";  
	        FSDirectory directory = FSDirectory.open(new File(indexDir));  
	        IndexReader reader = IndexReader.open(directory);  
	        IndexSearcher searcher = new IndexSearcher(reader); // 为相似搜索准备的searcher 
	        int numDocs = reader.maxDoc(); // 所有图书  
	        MoreLikeThis mlt = new MoreLikeThis(reader); // 相似搜索组件登场  
	        mlt.setAnalyzer(new IKAnalyzer(true));
	        mlt.setFieldNames(new String[] { "content" }); // 找“标题”和“作者”相似的  
	        mlt.setMinTermFreq(1); // 默认值是2，建议自己做限制，否则可能查不出结果  
	        mlt.setMinDocFreq(1); // 默认值是5，建议自己做限制，否则可能查不出结果  	        
	        /*int docID =100;
	       Document doc = reader.document(docID); // 逐一过所有图书        
	        System.out.println(doc.getField("content").stringValue());
	        Terms vector = reader.getTermVector(1, "content");
	        TermsEnum termsEnum = MultiFields.getTerms(reader, "content").iterator(null);;
	        termsEnum = vector.iterator(termsEnum);
	        Map<String, Integer> frequencies = new HashMap();
	        BytesRef text = null;
	        while ((text = termsEnum.next()) != null) {
	            String term = text.utf8ToString();
	            int freq = (int) termsEnum.totalTermFreq();
	            frequencies.put(term, freq);
	           // terms.add(term);
	        }
	        System.out.println(frequencies);*/
	        Document doc =null;
	       FieldType type = new FieldType();
	       type.setIndexed(true);
	       type.setStored(true);
            Query query = mlt.like(new StringReader("汽车发动机"),"content"); // 准备相似搜索了 
            System.out.println("query=" + query);  
            TopDocs similarDocs = searcher.search(query, 1); // 开搜，做多10个结果  
            if (similarDocs.totalHits == 0)  
                System.out.println(" None like this"); // 只要结果不为空，就按这个打印出来  
            for (int i = 0; i < similarDocs.scoreDocs.length; i++) {  
                //if (similarDocs.scoreDocs[i].doc != docID) { // 记着把自己排除掉哦  
                    doc = reader.document(similarDocs.scoreDocs[i].doc);  
                    System.out.println(" -> " + doc.getField("title").stringValue()+":"+similarDocs.scoreDocs[i].score);
                   // System.out.println(doc.getField("id").stringValue());
                //}  
            }  
            System.out.println(searcher.explain(query, similarDocs.scoreDocs[0].doc));
           System.out.println(similarDocs.getMaxScore()); 
           System.out.println(similarDocs.scoreDocs[0].doc);
	        reader.close();  
	        directory.close();  
	    }  
}
