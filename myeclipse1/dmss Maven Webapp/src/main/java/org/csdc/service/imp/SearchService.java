package org.csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.response.JSONResponseWriter;
import org.csdc.bean.SearchForm;
import org.csdc.domain.fs.SearchResponse;
import org.csdc.tool.FileTool;
import org.csdc.tool.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.coremedia.iso.boxes.ItemLocationBox.Item;
/**
 * 全文检索业务类
 * @author jintf
 * @date 2014-6-16
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class SearchService extends BaseService{
	public Map<String, Map> classifyMap = new HashMap<String, Map>();
	
	/**
	 * 返回分类后的目录
	 * @param docId
	 */
	public String classify(Map<String, Integer> wordMap){
		String type="";
		double a = 0f;
		for(String category: classifyMap.keySet()){
			double tmp = computeAngle(wordMap, category);
			if(tmp >a){
				a = tmp;
				type = category;
			}
		}
		return type;
	}
	
	/**
	 * 构建类别向量
	 * @throws IOException
	 */
	public void buildClassifyMap() throws IOException{
		FSDirectory directory = FSDirectory.open(new File(application.getParameter("INDEX_DIR")));  
		IndexReader reader = IndexReader.open(directory);
		for (int i = 0; i < reader.maxDoc(); i++) {
			Document doc = reader.document(i);
			String categroy = doc.get("category");
			Map vectorMap = (Map) classifyMap.get(categroy);
			if(vectorMap==null){
				vectorMap = new HashMap();
				classifyMap.put(categroy, vectorMap);
			}
			addTermFreqToMap(vectorMap, getDocWordMap(i,reader));
		}	
	}
	
	/**
	 * 计算文档与指定分类的向量余弦值
	 * @param words 词组
	 * @param category 分类名
	 * @return
	 */
	public double computeAngle(String [] words,String category){
		Map vectorMap = classifyMap.get(category);
		int dot = 0;
		int squares = 0;
		for (String word: words) {
			int categoryWordFreq = 0;
			if(vectorMap.containsKey(word)){
				categoryWordFreq = (Integer) vectorMap.get(word);
			}
			dot += categoryWordFreq;
			//squares += categoryWordFreq*categoryWordFreq;
		}
		for(Object x : vectorMap.values()){
			squares += (Integer)x*(Integer)x;
		}
		
		double denominator ;
		if(squares == words.length){
			denominator = squares;
		}else{
			denominator = Math.sqrt(squares)*Math.sqrt(words.length);
		}
		double ratio = dot / denominator;
			
		return ratio;
	}
	
	/**
	 * 计算文档分词结果Map与指定分类的向量余弦值
	 * @param wordMap
	 * @param category
	 * @return
	 */
	public double computeAngle(Map<String, Integer> wordMap,String category){
		Map vectorMap = classifyMap.get(category);
		int dot = 0;
		int squares = 0,wSquares = 0;
		for (String word: wordMap.keySet()) {
			int categoryWordFreq = 0;
			if(vectorMap.containsKey(word)){
				categoryWordFreq = (Integer) vectorMap.get(word);
			}
			dot += categoryWordFreq * wordMap.get(word);
			wSquares += wordMap.get(word) * wordMap.get(word);
		}
		for(Object x : vectorMap.values()){
			squares += (Integer)x*(Integer)x;
		}
		
		double denominator = Math.sqrt(squares)*Math.sqrt(wSquares);
		double ratio = dot / denominator;
			
		return ratio;
	}
	
	/**
	 * 获取文档项频率
	 * @param docId
	 */
	public Map<String, Integer> getDocWordMap(Integer docId,IndexReader reader){
		Map<String, Integer> frequencies = new HashMap();
		try {
			Document doc = reader.document(docId); // 逐一过所有图书        
			Terms vector = reader.getTermVector(1, "content");
			TermsEnum termsEnum = MultiFields.getTerms(reader, "content").iterator(null);;
			termsEnum = vector.iterator(termsEnum);
			BytesRef text = null;
			while ((text = termsEnum.next()) != null) {
			    String term = text.utf8ToString();
			    int freq = (int) termsEnum.totalTermFreq();
			    if(freq>1)
			    	frequencies.put(term, freq);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return frequencies;
	}

	
	/**
	 * 为每个聚类聚集项频率
	 * @param vectorMap
	 * @param freqMap
	 */
	public void addTermFreqToMap(Map<String,Integer> vectorMap,Map<String,Integer> freqMap){
		Set<String> keys = freqMap.keySet();
		for(String key : keys){
			if(vectorMap.containsKey(key)){
				vectorMap.put(key, new Integer(vectorMap.get(key)+freqMap.get(key)));
			}else{
				vectorMap.put(key, new Integer(freqMap.get(key)));
			}
		}
	}
	

	/**
	 * 获取相似文档
	 * @param id 源文档ID
	 * @return 相似文档集合
	 */
	public List<org.csdc.model.Document> getSimilarDocs(String id){
		List<org.csdc.model.Document> docs = new ArrayList<org.csdc.model.Document>();
		Map params = new HashMap();
		params.put("indent", true);
		params.put("wt", "json");
		params.put("q", "id:"+id);
		params.put("mlt", true);
		params.put("mlt.mindf",1);
		params.put("mlt.mintf", 1);
		params.put("mlt.fl", "content");
		params.put("mlt.count", 10);
		String url = application.getSolrServerUrl()+"/select";
		JSONObject jsonObject = HttpTool.getJson(url,params);
		JSONArray jsonDocs = new JSONArray();
		try {
			jsonDocs = jsonObject.getJSONArray("moreLikeThis").getJSONObject(1).getJSONArray("docs");
		} catch (Exception e) {
			System.out.println("警告：没有找到相似文档");
			e.printStackTrace();
			return docs;
		}
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < jsonDocs.size(); i++) {
			ids.add(jsonDocs.getJSONObject(i).getString("id"));
		}
		Map queryMap = new HashMap();
		queryMap.put("ids", ids);
		List resList = baseDao.query("select d.id,d.title,d.sourceAuthor,d.type,d.categoryString from Document d where d.id in :ids",queryMap);
		for (Object o: resList ) {
			Object[] item = (Object[]) o;
			org.csdc.model.Document document = new org.csdc.model.Document();
			document.setId(item[0].toString());
			document.setTitle(item[1].toString());
			document.setSourceAuthor(item[2].toString());
			document.setType(item[3].toString());
			document.setPath(item[4].toString());
			docs.add(document);
		}
		return docs;
	}
	
	
}
