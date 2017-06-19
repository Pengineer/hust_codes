package org.cdsc.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialArray;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.csdc.service.imp.SearchService;
import org.csdc.tool.FileTool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ClassifyTest {
	public static void main(String[] args) throws IOException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		SearchService searchService = (SearchService) ac.getBean("searchService");
		searchService.buildClassifyMap();
		String text1 = FileTool.readFileContent("E:\\系自主创新\\研究生论文\\分类测试文本\\test\\IT\\IT_7003.txt").toString();
		String type =searchService.classify(getContentWordMap(text1));
		System.out.println(type);
		System.out.println("...");
	}
	
	public static Map<String, Integer> getContentWordMap(String content){
		Map<String, Integer> frequencies = new HashMap<String, Integer>();
		int freq = 2;
		Analyzer analyzer = new IKAnalyzer();
		Map<String, Integer> map = new HashMap<String, Integer>();
		TokenStream ts = null;
		try{
			ts = analyzer.tokenStream("myfield", new StringReader(content));
		    CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			ts.reset(); 
			while (ts.incrementToken()) {
				String key = term.toString();
				if(map.containsKey(key)){
					map.put(key, map.get(key)+1);
				}else {
					map.put(key, 1);
				}
			}
			ts.end();  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//释放TokenStream的所有资源
			if(ts != null){
		      try {
				ts.close();
		      } catch (IOException e) {
				e.printStackTrace();
		      }
			}
	    }
		
		for(String key:map.keySet()){
			if(map.get(key) >= freq){
				frequencies.put(key,map.get(key));
			}
		}
		return frequencies;
	}
}
