package org.cdsc.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.csdc.tool.FileTool;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class KNNTest {
	public static void main(String[] args) throws IOException {
		String text1 = FileTool.readFileContent("E:\\系自主创新\\研究生论文\\分类测试文本\\test\\汽车\\汽车_7010.txt").toString();
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
        Query query = mlt.like(new StringReader(text1),"content"); // 准备相似搜索了 
        TopDocs similarDocs = searcher.search(query, 10); // 开搜，做多10个结果  
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < similarDocs.scoreDocs.length; i++) {  
            Document doc = reader.document(similarDocs.scoreDocs[i].doc);  
            list.add(doc.getField("category").stringValue());
        }
	}
	
	/**
	 * 获取分类字符串集中最大的类别
	 * @param list
	 * @return
	 */
	public static String getMaxString(List<String> list){
		String max = "";
		Integer value = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String key: list) {
			if(map.containsKey(key)){
				map.put(key, map.get(key)+1);
			}else{
				map.put(key, 1);
			}
		}
		for (String key : map.keySet()) {
			if(map.get(key) > value){
				max = key;
				value = map.get(key);
			}
		}
		return max;
	}
}
