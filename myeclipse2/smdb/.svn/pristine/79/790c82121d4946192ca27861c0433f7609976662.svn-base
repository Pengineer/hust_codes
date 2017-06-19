package csdc.tool.dataMining.hotspot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import csdc.tool.ApplicationContainer;
import csdc.tool.IKAnalyzer.LuceneIKAnalyzer;

/**
 * 项目研究热点分析器：基于 LuceneIKAnalyzer 实现
 * @author fengcl
 *
 */
public class ProjectHotspotAnalyzer {

	/**
	 * 项目的主要字段名fieldName，用于分词和全文检索
	 */
	private final static String fieldName = "name";
	
	/**
	 * 索引文件的目录路径
	 */
	private String indexPath;
	
	/**
	 * 构造器：项目研究热点分析器
	 * @param hotstopType	研究热点类型（项目类型）
	 */
	public ProjectHotspotAnalyzer(String hotstopType){
		//获取索引文件的路径
		this.indexPath = ApplicationContainer.sc.getRealPath("/dataMining/resources/hotspot/" + hotstopType);
	}
	
	/**
	 * 创建索引
	 * @param projects	原始数据集	
	 * @return
	 */
	public boolean createIndex(List<Object[]> projects){
		boolean success = false;
		long begin = System.currentTimeMillis();
		if(projects != null && projects.size() > 0){
			// 实例化IKAnalyzer分词器
			Analyzer analyzer = new IKAnalyzer(true);
			// 指定索引文件目录，建立索引对象
			Directory directory = null;
			// 索引写入器
			IndexWriter indexWriter = null;
			try {
				directory = FSDirectory.open(new File(indexPath));
				// 构造索引写入器的配置，指定分词器和创建模式
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_40, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();
				for (Object[] project : projects) {
					Document document = new Document();
					document.add(new StringField("id", (String) project[0], Field.Store.YES));		// 类型为StringField的属性不进行分词处理
					document.add(new TextField(fieldName, (String) project[1], Field.Store.YES));	// 类型为TextField的属性进行分词处理，这里只需要对项目名称进行分词
					document.add(new StringField("agencyName", (project[2] == null) ? "" : (String) project[2], Field.Store.YES));
					document.add(new StringField("applicant", (project[3] == null) ? "" : (String) project[3], Field.Store.YES));
					document.add(new StringField("year", (project[4] == null) ? "" : project[4] + "", Field.Store.YES));
					indexWriter.addDocument(document);
				}
				System.out.println("数据索引完成！ 耗时：" + (System.currentTimeMillis() - begin) + "ms");
				success = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (indexWriter != null) {
					try {
						indexWriter.close();
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
		return success;
	}
	
	/**
	 * 判断索引文件是否存在
	 * @return
	 */
	public boolean isExistIndexFile(){
		return LuceneIKAnalyzer.isExistIndexFile(indexPath);
	}
	
	/**
	 * 获取 topk 研究热点 （研究主题 -> 包含该研究主题的项目数）
	 * @param indexPath
	 * @param topK
	 * @return
	 */
	public Map<String, Integer> fetchTopKTermFreq(int topK){
		Map<String, Integer> termFrequency = null;
		try {
			termFrequency = LuceneIKAnalyzer.fetchTopKTermToDocFreq(indexPath, fieldName, topK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return termFrequency;
	}
	
	/**
	 * 根据研究主题进行检索，获取对应的项目数量
	 * @param keyword	研究主题
	 * @return
	 */
	public List<Object[]> search(String keyword){
		// 检索结果：项目数据
		List<Object[]> searchResults = null;
		
		// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
		List<Document> documents = LuceneIKAnalyzer.searchWithSingleField(fieldName, keyword, indexPath, 0);
		
//		List<Document> documents = LuceneIKAnalyzerUtil.searchWithMultiField(new String[]{fieldName}, keyword, indexPath, 0);
		
//		SortField[] sortFileds = new SortField[2];
//		sortFileds[0] = new SortField("year", Type.STRING, false);
//		sortFileds[1] = new SortField(fieldName, Type.STRING, false);
//		List<Document> documents = LuceneIKAnalyzerUtil.searchWithSort(new String[]{fieldName}, keyword, indexPath, 0, sortFileds);
		
		if (documents != null && !documents.isEmpty()) {
			searchResults = new ArrayList<Object[]>();
			for (Document document : documents) {
				// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
				searchResults.add(new Object[]{document.get("id"), document.get(fieldName), document.get("agencyName"), document.get("applicant"), document.get("year")});
			}
		}
		
		return searchResults; 
	}
}
