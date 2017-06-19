package csdc.tool.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 基本的全文检索实现：基于 lucene + IKAnalyzer 实现
 * @author fengcl
 *
 */
public class LuceneIKAnalyzer {
	
	/**
	 * 按文档：获取每个分词结果（keyword）对应的文档频次（keyword -> document的数量）
	 * @param indexReader	索引读取
	 * @param fieldName		域名
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Integer> fecthTermToDocFreq(String indexPath, String fieldName) throws IOException {
		Map<String, Integer> termFrequency = new HashMap<String, Integer>();
		 
	    Directory directory = FSDirectory.open(new File(indexPath));
		IndexReader indexReader = DirectoryReader.open(directory);
		
		// 根据索引读取器 和 检索域 获取当前域的所有词项
        TermsEnum termsEnum = MultiFields.getTerms(indexReader, fieldName).iterator(null);
        BytesRef thisTerm = null;
        while ((thisTerm = termsEnum.next()) != null) {// while循环，逐个词语进行遍历
            String termText = thisTerm.utf8ToString();
 
            if (termText.length() > 1) {// 只保留长度大于1的词语
	            DocsEnum docsEnum = termsEnum.docs(null, null);
	            // 频率统计，如果termFrequency中已包含当前词语，当前词对应的文档数量为已存在的频率 + 1；否则就等于1
	            while ((docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
	                if (termFrequency.containsKey(termText))
	                	termFrequency.put(termText, termFrequency.get(termText) + 1);
	                else
	                	termFrequency.put(termText, 1);
	            }
            }
        }
//	    System.out.println(termFrequency.toString());
	    return termFrequency;
	}
	
	/**
	 * 按词：获取所有的词频（keyword -> keyword的出现数量）
	 * 可能一个文档中出现keyword多次
	 * @param indexReader	索引读取
	 * @param fieldName		域名
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Integer> fecthTermsFreq(String indexPath, String fieldName) throws IOException {
	    Map<String, Integer> termFrequency = new HashMap<String, Integer>();
	 
	    Directory directory = FSDirectory.open(new File(indexPath));
		IndexReader indexReader = DirectoryReader.open(directory);
		
		// 根据索引读取器 和 检索域 获取当前域的所有词项
        TermsEnum termsEnum = MultiFields.getTerms(indexReader, fieldName).iterator(null);
        BytesRef thisTerm = null;
        while ((thisTerm = termsEnum.next()) != null) {// 逐个词语进行遍历
            String termText = thisTerm.utf8ToString();
 
            if (termText.length() > 1) {// 只保留长度大于1的词语
	            DocsEnum docsEnum = termsEnum.docs(null, null);
	            // 频率统计，如果termFrequency中已包含当前词语，当前词的频率数量为已存在的频率 + 当前频率；否则就等于当前频率
	            while ((docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
	                if (termFrequency.containsKey(termText))
	                	termFrequency.put(termText, termFrequency.get(termText) + docsEnum.freq());
	                else
	                    termFrequency.put(termText, docsEnum.freq());
	            }
            }
        }
//	    System.out.println(termFrequency.toString());
	    return termFrequency;
	 
	}
	
	/**
	 * 获取topK结果<br>
	 * [按文档]：获取每个分词结果（keyword）对应的文档频次（keyword -> document的数量）
	 * @param indexPath	索引文件路径
	 * @param fieldName	fieldName
	 * @param topK	前K条
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Integer> fetchTopKTermToDocFreq(String indexPath, String fieldName, int topK) throws IOException{
		Map<String, Integer> topKTermFrequency = null;
		Map<String, Integer> allTermFrequency = fecthTermToDocFreq(indexPath, fieldName);
		// 如果所有的分词结果集数量小于topK，直接返回
		if (allTermFrequency.size() <= topK) {
			topKTermFrequency = allTermFrequency;
		} else {
			// 如果所有的分词结果集数量大于topK，先对已有结果进行排序，然后取前topK返回
			topKTermFrequency = new LinkedHashMap<String, Integer>();
			
			// 对map的value值进行从大到小排序
			List<Entry<String,Integer>> wordsFreqList = new ArrayList<Entry<String,Integer>>(allTermFrequency.entrySet());
			Collections.sort(wordsFreqList, new Comparator<Entry<String, Integer>>() {
				public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
					return entry2.getValue() - entry1.getValue();
				}
			});
			List<Entry<String,Integer>> topKWordsFreqList = wordsFreqList.subList(0, topK);
			for(Entry<String, Integer> entry : topKWordsFreqList){
				topKTermFrequency.put(entry.getKey(), entry.getValue());
			} 
		}
		return topKTermFrequency;
	}

	/**
	 * 单个字段的查询：根据keyword关键字检索相关文档
	 * @param fieldName		字段名
	 * @param keyword		关键字
	 * @param indexPath		索引文件路径
	 * @param topK			如果topK 等于 0，取文档最大数
	 * @return
	 */
	public static List<Document> searchWithSingleField(String fieldName, String keyword, String indexPath, int topK) {

		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
		Directory directory = null;
		IndexReader indexReader = null;
		IndexSearcher indexSearcher = null;
		List<Document> documents = new ArrayList<Document>();

		// 实例化搜索器   
		try {
			directory = FSDirectory.open(new File(indexPath));	//实例化索引目录
			indexReader = DirectoryReader.open(directory);		//实例化索引读取器
			indexSearcher = new IndexSearcher(indexReader);		//实例化索引检索器

			QueryParser queryParser = new QueryParser(Version.LUCENE_44, fieldName, analyzer);
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);//检索的方式，这里采取 与（and）方式，其他的还有或（or）等方式
			Query query = queryParser.parse(keyword);
//			System.out.println("Query = " + query);

			// 如果topK 等于 0，再取文档最大数
			if (topK == 0) {
				topK = indexReader.maxDoc();
			}
			// 搜索相似度最高的topK条记录
			TopDocs topDocs = indexSearcher.search(query, topK);
//			// 信息展示
//			int totalCount = topDocs.totalHits;
//			System.out.println("共搜索出  " + totalCount + " 条记录");

			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			for (ScoreDoc scDoc : scoreDocs) {
				Document document = indexSearcher.doc(scDoc.doc);
//				System.out.println("document内容：" + document.toString());
				documents.add(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (indexReader != null) {
				try {
					indexReader.close();
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

		return documents;
	}

	
	/**
	 * 多个字段的查询：根据keyword关键字检索相关文档
	 * @param fieldNames	字段名1，字段名2，...
	 * @param keyword		关键字
	 * @param indexPath		索引文件路径
	 * @param topK			如果topK 等于 0，取文档最大数
	 * @return
	 */
	public static List<Document> searchWithMultiField(String[] fieldNames, String keyword, String indexPath, int topK) {

		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
		Directory directory = null;
		IndexReader indexReader = null;
		IndexSearcher indexSearcher = null;
		List<Document> documents = new ArrayList<Document>();

		// 实例化搜索器   
		try {
			directory = FSDirectory.open(new File(indexPath));
			indexReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);

			// 下面是创建QueryParser 查询解析器  
			// QueryParser支持单个字段的查询，但是MultiFieldQueryParser可以支持多个字段查询，建议用后者这样可以实现全文检索的功能。
			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_44, fieldNames, analyzer);
			queryParser.setDefaultOperator(MultiFieldQueryParser.AND_OPERATOR);
			Query query = queryParser.parse(keyword);
//			System.out.println("Query = " + query);

			// 如果topK 等于 0，再取文档最大数
			if (topK == 0) {
				topK = indexReader.maxDoc();
			}
			// 搜索相似度最高的topK条记录
			TopDocs topDocs = indexSearcher.search(query, topK);
//			// 信息展示
//			int totalCount = topDocs.totalHits;
//			System.out.println("共搜索出  " + totalCount + " 条记录");

			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			for (ScoreDoc scDoc : scoreDocs) {
				Document document = indexSearcher.doc(scDoc.doc);
//				System.out.println("document内容：" + document.toString());
				documents.add(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (indexReader != null) {
				try {
					indexReader.close();
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

		return documents;
	}

	/** 
     * 根据关键字实现全文检索 
	 * 多个字段的待排序的查询：根据keyword关键字检索相关文档
	 * @param fieldNames	字段名1，字段名2，...
	 * @param keyword		关键字
	 * @param indexPath		索引文件路径
	 * @param topK			如果topK 等于 0，取文档最大数
	 * @return
	 */ 
	public static List<Document> searchWithSort(String[] fieldNames, String keyword, String indexPath, int topK, SortField[] sortFileds) {  
        long begin = System.currentTimeMillis();  
        System.out.println("*****************查询索引开始**********************");  

		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
        Directory directory = null;
        IndexReader indexReader = null;  
        IndexSearcher indexSearcher = null;
        
        List<Document> documents = new ArrayList<Document>();
        try {  
        	directory = FSDirectory.open(new File(indexPath));
			indexReader = DirectoryReader.open(directory);
			// 创建搜索类  
			indexSearcher = new IndexSearcher(indexReader);  

			// 创建一个排序对象，其中SortField构造方法中，第一个是排序的字段，第二个是指定字段的类型，第三个是是否升序排列，true：升序，false：降序。  
			Sort sort = new Sort(sortFileds);
//			Sort sort = new Sort(new SortField[] {new SortField("fieldName1", Type.STRING, false),new SortField("fieldName2", Type.STRING, false) });  
            
			// 下面是创建QueryParser 查询解析器  
            // QueryParser支持单个字段的查询，但是MultiFieldQueryParser可以支持多个字段查询，建议用后者这样可以实现全文检索的功能。  
            QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_44, fieldNames, analyzer);  
            // 利用queryParser解析传递过来的检索关键字，完成Query对象的封装  
            Query query = queryParser.parse(keyword);   
			
            // 执行检索操作  
            // 如果topK 等于 0，再取文档最大数
 			if (topK == 0) {
 				topK = indexReader.maxDoc();
 			}
            TopDocs topDocs = indexSearcher.search(query, topK, sort);  
//            System.out.println("共搜索到 " + topDocs.totalHits + " 记录");  
  
            // 输出结果
 			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

 			for (ScoreDoc scDoc : scoreDocs) {
 				int docId = scDoc.doc;	// 内部编号 ,和数据库表中的唯一标识列一样
 				Document document = indexSearcher.doc(docId);	// 根据文档id找到文档  
// 				System.out.println("document内容：" + document.toString());
 				documents.add(document);
 			}
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } finally {  
        	if (indexReader != null) {
				try {
					indexReader.close();
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
        System.out.println("根据关键字" + keyword + "检索到的结果如下：");  
        System.out.println("全文索引文件成功，总共花费" + (System.currentTimeMillis() - begin) + "毫秒。");  
        System.out.println("*****************查询索引结束**********************");  

		return documents;
    }  
	
	/**
	 * 判断是否已经存在索引文件
	 * 
	 * @param indexPath
	 * @return
	 */
	public static boolean isExistIndexFile(String indexPath) {
		File file = new File(indexPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 文件名后缀
		String indexSufix = "/segments.gen";
		// 根据索引文件segments.gen是否存在判断是否是第一次创建索引
		File indexFile = new File(indexPath + indexSufix);
		return indexFile.exists();
	}
}
