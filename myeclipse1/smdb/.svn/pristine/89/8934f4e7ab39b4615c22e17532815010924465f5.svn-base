package csdc.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import csdc.service.ITotalSearchService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.IKAnalyzer.LuceneIKAnalyzer;

public class TotalSearchService extends BaseService implements ITotalSearchService{

	public Map totalSearch(String range, String keyword) {
		Map dataData = new HashMap();
		List<Object[]> infoData = null;
		List<Object[]> personData = null;
		List<Object[]> agencyData = null;
		List<Object[]> projectData = null;
		List<Object[]> productData = null;
		List<Object[]> awardData = null;
		if(range.equals("all")){
			range = "info person agency project product award";
		}
		if(range.contains("info")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/info");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"title", "date", "account", "content"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				infoData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					infoData.add(new Object[]{document.get("title"), document.get("date"), document.get("account"), document.get("content")});
				}
			}
		}
		if(range.contains("person")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/person");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"name", "gender", "agency", "position"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				personData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					personData.add(new Object[]{document.get("name"), document.get("gender"), document.get("agency"), document.get("position")});
				}
			}
		}
		if(range.contains("agency")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/agency");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"name", "code", "agency", "director"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				agencyData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					agencyData.add(new Object[]{document.get("name"), document.get("code"), document.get("agency"), document.get("director")});
				}
			}
		}
		if(range.contains("project")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/project");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"name", "applicant", "university", "type", "year"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				projectData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					projectData.add(new Object[]{document.get("name"), document.get("applicant"), document.get("university"), document.get("type"), document.get("year")});
				}
			}
		}
		if(range.contains("product")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/product");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"name", "author", "agency", "type"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				productData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					productData.add(new Object[]{document.get("name"), document.get("author"), document.get("agency"), document.get("type")});
				}
			}
		}
		if(range.contains("award")){
			String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/award");
			// 针对项目名称单个字段查询：根据keyword关键字检索相关项目
			String[] fieldNames = {"name", "author", "agency", "disciplineType"};
			List<Document> documents = LuceneIKAnalyzer.searchWithMultiField(fieldNames, keyword, indexPath, 0);
			if (documents != null && !documents.isEmpty()) {
				awardData = new ArrayList<Object[]>();
				for (Document document : documents) {
					// 组装项目列表数据：Document文档集 -> 检索结果数据（列表显示要求的格式）
					awardData.add(new Object[]{document.get("name"), document.get("author"), document.get("agency"), document.get("disciplineType")});
				}
			}
		}
		dataData.put("info", infoData);
		dataData.put("person", personData);
		dataData.put("agency", agencyData);
		dataData.put("project", projectData);
		dataData.put("product", productData);
		dataData.put("award", awardData);
		return dataData;
	}
	
	public boolean isExistIndexFile(String range){
		boolean isExist = false;
		if(!range.equals("all")){
			//根据索引文件segments.gen是否存在判断索引是否存在
			String fileName = ApplicationContainer.sc.getRealPath("/system/totalSearch/" + range + "/segments.gen");
			if(FileTool.isExsits(fileName)){
				isExist = true;
			}		
		}else {
			//根据索引文件segments.gen是否存在判断索引是否存在
			String fileName1 = ApplicationContainer.sc.getRealPath("/system/totalSearch/info/segments.gen");
			String fileName2 = ApplicationContainer.sc.getRealPath("/system/totalSearch/person/segments.gen");
			String fileName3 = ApplicationContainer.sc.getRealPath("/system/totalSearch/agency/segments.gen");
			String fileName4 = ApplicationContainer.sc.getRealPath("/system/totalSearch/project/segments.gen");
			String fileName5 = ApplicationContainer.sc.getRealPath("/system/totalSearch/product/segments.gen");
			String fileName6 = ApplicationContainer.sc.getRealPath("/system/totalSearch/award/segments.gen");
			if(FileTool.isExsits(fileName1) && FileTool.isExsits(fileName2) && FileTool.isExsits(fileName3) && FileTool.isExsits(fileName4) && FileTool.isExsits(fileName5) && FileTool.isExsits(fileName6)){
				isExist = true;
			}
		}
		return isExist;
	}
	
	public boolean updateIndex(String range){
		boolean success = false;
		long begin = System.currentTimeMillis();
		if(range.equals("all")){
			range = "info person agency project product award";
		}
		try {
			if(range.contains("info")){
				String newsHql = "select n.title, n.createDate, p.name, n.content from News n left join n.account a left join a.passport p";
				String noticeHql = "select n.title, n.createDate, p.name, n.content from Notice n left join n.account a left join a.passport p";
				String messageHql = "select m.title, m.createDate, m.authorName, m.content from Message m";
				List<Object[]> news = dao.query(newsHql);
				List<Object[]> notice = dao.query(noticeHql);
				List<Object[]> message = dao.query(messageHql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/info");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(news.size() > 0){
					System.out.println(news.size());
					for (Object[] singleNews : news) {
						Document document = new Document();
						document.add(new TextField("title", (singleNews[0] == null) ? "" : (String) singleNews[0], Field.Store.YES));
						document.add(new StringField("date", (singleNews[1] == null) ? "" : singleNews[1].toString(), Field.Store.YES));// 类型为TextField的属性进行分词处理，这里只需要对项目名称进行分词
						document.add(new TextField("account", (singleNews[2] == null) ? "" : (String) singleNews[2], Field.Store.YES));// 类型为StringField的属性不进行分词处理
						document.add(new TextField("content", (singleNews[3] == null) ? "" : (String) singleNews[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(notice.size() > 0){
					System.out.println(notice.size());
					for (Object[] singleNotice : notice) {
						Document document = new Document();
						document.add(new TextField("title", (singleNotice[0] == null) ? "" : (String) singleNotice[0], Field.Store.YES));
						document.add(new StringField("date", (singleNotice[1] == null) ? "" : singleNotice[1].toString(), Field.Store.YES));
						document.add(new TextField("account", (singleNotice[2] == null) ? "" : (String) singleNotice[2], Field.Store.YES));
						document.add(new TextField("content", (singleNotice[3] == null) ? "" : (String) singleNotice[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(message.size() > 0){
					System.out.println(message.size());
					for (Object[] singleMessage : message) {
						Document document = new Document();
						document.add(new TextField("title", (singleMessage[0] == null) ? "" : (String) singleMessage[0], Field.Store.YES));
						document.add(new StringField("date", (singleMessage[1] == null) ? "" : singleMessage[1].toString(), Field.Store.YES));
						document.add(new TextField("account", (singleMessage[2] == null) ? "" : (String) singleMessage[2], Field.Store.YES));
						document.add(new TextField("content", (singleMessage[3] == null) ? "" : (String) singleMessage[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				indexWriter.close();
				directory.close();
			}
			if(range.contains("person")){
				String MPUHql = "select p.name, p.gender, ag.name, o.position from Officer o left join o.person p left join o.agency ag left join o.department d left join o.institute i where d.name is null and i.name is null";
				String DepHql = "select p.name, p.gender, u.name, o.position from Officer o left join o.person p left join o.department d left join d.university u where d.name is not null";
				String InsHql = "select p.name, p.gender, u.name, o.position from Officer o left join o.person p left join o.institute i left join i.subjection u where i.name is not null";
				String ExpHql = "select p.name, p.gender, e.agencyName, ac.specialityTitle from Expert e left join e.person p left join p.academic ac";
				String TeaHql = "select p.name, p.gender, u.name, ac.specialityTitle from Teacher t left join t.person p left join t.institute i left join t.university u left join p.academic ac where t.type = '专职人员' ";
				String StuHql = "select p.name, p.gender, u.name, s.type from Student s left join s.person p left join s.university u";
				List<Object[]> MPUList = dao.query(MPUHql);
				List<Object[]> DepList = dao.query(DepHql);
				List<Object[]> InsList = dao.query(InsHql);
				List<Object[]> ExpList = dao.query(ExpHql);
				List<Object[]> TeaList = dao.query(TeaHql);
				List<Object[]> StuList = dao.query(StuHql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/person");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(MPUList.size() > 0){
					System.out.println(MPUList.size());
					for (Object[] MPUObject : MPUList) {
						Document document = new Document();
						document.add(new TextField("name", (MPUObject[0] == null) ? "" : (String) MPUObject[0], Field.Store.YES));
						document.add(new StringField("gender", (MPUObject[1] == null) ? "" : (String) MPUObject[1], Field.Store.YES));
						document.add(new TextField("agency", (MPUObject[2] == null) ? "" : (String) MPUObject[2], Field.Store.YES));
						document.add(new TextField("position", (MPUObject[3] == null) ? "" : (String) MPUObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					MPUList.clear();
				}
				if(DepList.size() > 0){
					System.out.println(DepList.size());
					for (Object[] DepObject : DepList) {
						Document document = new Document();
						document.add(new TextField("name", (DepObject[0] == null) ? "" : (String) DepObject[0], Field.Store.YES));
						document.add(new StringField("gender", (DepObject[1] == null) ? "" : (String) DepObject[1], Field.Store.YES));
						document.add(new TextField("agency", (DepObject[2] == null) ? "" : (String) DepObject[2], Field.Store.YES));
						document.add(new TextField("position", (DepObject[3] == null) ? "" : (String) DepObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					DepList.clear();
				}
				if(InsList.size() > 0){
					System.out.println(InsList.size());
					for (Object[] InsObject : InsList) {
						Document document = new Document();
						document.add(new TextField("name", (InsObject[0] == null) ? "" : (String) InsObject[0], Field.Store.YES));
						document.add(new StringField("gender", (InsObject[1] == null) ? "" : (String) InsObject[1], Field.Store.YES));
						document.add(new TextField("agency", (InsObject[2] == null) ? "" : (String) InsObject[2], Field.Store.YES));
						document.add(new TextField("position", (InsObject[3] == null) ? "" : (String) InsObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					InsList.clear();
				}
				if(ExpList.size() > 0){
					System.out.println(ExpList.size());
					for (Object[] ExpObject : ExpList) {
						Document document = new Document();
						document.add(new TextField("name", (ExpObject[0] == null) ? "" : (String) ExpObject[0], Field.Store.YES));
						document.add(new StringField("gender", (ExpObject[1] == null) ? "" : (String) ExpObject[1], Field.Store.YES));
						document.add(new TextField("agency", (ExpObject[2] == null) ? "" : (String) ExpObject[2], Field.Store.YES));
						document.add(new TextField("position", (ExpObject[3] == null) ? "" : (String) ExpObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					ExpList.clear();
				}
				if(TeaList.size() > 0){
					System.out.println(TeaList.size());
					for (Object[] TeaObject : TeaList) {
						Document document = new Document();
						document.add(new TextField("name", (TeaObject[0] == null) ? "" : (String) TeaObject[0], Field.Store.YES));
						document.add(new StringField("gender", (TeaObject[1] == null) ? "" : (String) TeaObject[1], Field.Store.YES));
						document.add(new TextField("agency", (TeaObject[2] == null) ? "" : (String) TeaObject[2], Field.Store.YES));
						document.add(new TextField("position", (TeaObject[3] == null) ? "" : (String) TeaObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					TeaList.clear();
				}
				if(StuList.size() > 0){
					System.out.println(StuList.size());
					for (Object[] StuObject : StuList) {
						Document document = new Document();
						document.add(new TextField("name", (StuObject[0] == null) ? "" : (String) StuObject[0], Field.Store.YES));
						document.add(new StringField("gender", (StuObject[1] == null) ? "" : (String) StuObject[1], Field.Store.YES));
						document.add(new TextField("agency", (StuObject[2] == null) ? "" : (String) StuObject[2], Field.Store.YES));
						document.add(new TextField("position", (StuObject[3] == null) ? "" : (String) StuObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
					StuList.clear();
				}
				indexWriter.close();
				directory.close();
			}
			if(range.contains("agency")){
				String MPUHql = "select ag.name, ag.code, ag.sname, sd.name from Agency ag left join ag.sdirector sd";
				String DepHql = "select de.name, de.code, un.name, di.name from Department de left join de.director di left join de.university un";
				String InsHql = "select ins.name, ins.code, su.name, di.name from Institute ins left join ins.director di left join ins.subjection su";
				List<Object[]> MPUList = dao.query(MPUHql);
				List<Object[]> DepList = dao.query(DepHql);
				List<Object[]> InsList = dao.query(InsHql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/agency");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(MPUList.size() > 0){
					System.out.println(MPUList.size());
					for (Object[] MPUObject : MPUList) {
						Document document = new Document();
						document.add(new TextField("name", (MPUObject[0] == null) ? "" : (String) MPUObject[0], Field.Store.YES));
						document.add(new StringField("code", (MPUObject[1] == null) ? "" : (String) MPUObject[1], Field.Store.YES));
						document.add(new TextField("agency", (MPUObject[2] == null) ? "" : (String) MPUObject[2], Field.Store.YES));
						document.add(new TextField("director", (MPUObject[3] == null) ? "" : (String) MPUObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(DepList.size() > 0){
					System.out.println(DepList.size());
					for (Object[] DepObject : DepList) {
						Document document = new Document();
						document.add(new TextField("name", (DepObject[0] == null) ? "" : (String) DepObject[0], Field.Store.YES));
						document.add(new StringField("code", (DepObject[1] == null) ? "" : (String) DepObject[1], Field.Store.YES));
						document.add(new TextField("agency", (DepObject[2] == null) ? "" : (String) DepObject[2], Field.Store.YES));
						document.add(new TextField("director", (DepObject[3] == null) ? "" : (String) DepObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(InsList.size() > 0){
					System.out.println(InsList.size());
					for (Object[] InsObject : InsList) {
						Document document = new Document();
						document.add(new TextField("name", (InsObject[0] == null) ? "" : (String) InsObject[0], Field.Store.YES));
						document.add(new StringField("code", (InsObject[1] == null) ? "" : (String) InsObject[1], Field.Store.YES));
						document.add(new TextField("agency", (InsObject[2] == null) ? "" : (String) InsObject[2], Field.Store.YES));
						document.add(new TextField("director", (InsObject[3] == null) ? "" : (String) InsObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				indexWriter.close();
				directory.close();
			}
			if(range.contains("project")){
				String hql = "select pa.name, pa.applicantName, pa.agencyName, pa.type, pa.year from ProjectApplication pa where pa.name is not null";
				List<Object[]> projectList = dao.query(hql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/project");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(projectList.size() > 0){
					System.out.println(projectList.size());
					for (Object[] projectObject : projectList) {
						Document document = new Document();
						document.add(new TextField("name", (projectObject[0] == null) ? "" : (String) projectObject[0], Field.Store.YES));
						document.add(new TextField("applicant", (projectObject[1] == null) ? "" : (String) projectObject[1], Field.Store.YES));
						document.add(new TextField("university", (projectObject[2] == null) ? "" : (String) projectObject[2], Field.Store.YES));
						String type = "";
						if(projectObject[3] != null){
							if(projectObject[3].equals("instp")){
								type = "基地项目";
							}else if(projectObject[3].equals("general")){
								type = "一般项目";
							}else if(projectObject[3].equals("post")){
								type = "后期资助项目";
							}else if(projectObject[3].equals("key")){
								type = "重大攻关项目";
							}
						}
						document.add(new TextField("type", type, Field.Store.YES));
						document.add(new StringField("year", (projectObject[4] == null) ? "" : (projectObject[4] + ""), Field.Store.YES));
						indexWriter.addDocument(document);
					}
					indexWriter.close();
					directory.close();
				}
			}
			if(range.contains("product")){
				String paperHql = "select p.chineseName, p.authorName, uni.name from Paper p left join p.university uni where p.submitStatus = 3";
				String bookHql = "select p.chineseName, p.authorName, uni.name from Book p left join p.university uni where p.submitStatus = 3";
				String conHql = "select p.chineseName, p.authorName, uni.name from Consultation p left join p.university uni where p.submitStatus = 3";
				String elecHql = "select p.chineseName, p.authorName, uni.name from Electronic p left join p.university uni where p.submitStatus = 3";
				String patentHql = "select p.chineseName, p.authorName, uni.name from Patent p left join p.university uni where p.submitStatus = 3";
				String otherHql = "select p.chineseName, p.authorName, uni.name from OtherProduct p left join p.university uni where p.submitStatus = 3";
				List<Object[]> paperList = dao.query(paperHql);
				List<Object[]> bookList = dao.query(bookHql);
				List<Object[]> conList = dao.query(conHql);
				List<Object[]> elecList = dao.query(elecHql);
				List<Object[]> patentList = dao.query(patentHql);
				List<Object[]> otherList = dao.query(otherHql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/product");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(paperList.size() > 0){
					System.out.println(paperList.size());
					for (Object[] paperObject : paperList) {
						Document document = new Document();
						document.add(new TextField("name", (paperObject[0] == null) ? "" : (String) paperObject[0], Field.Store.YES));
						document.add(new TextField("author", (paperObject[1] == null) ? "" : (String) paperObject[1], Field.Store.YES));
						document.add(new TextField("agency", (paperObject[2] == null) ? "" : (String) paperObject[2], Field.Store.YES));
						document.add(new TextField("type", "论文", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(bookList.size() > 0){
					System.out.println(bookList.size());
					for (Object[] bookObject : bookList) {
						Document document = new Document();
						document.add(new TextField("name", (bookObject[0] == null) ? "" : (String) bookObject[0], Field.Store.YES));
						document.add(new TextField("author", (bookObject[1] == null) ? "" : (String) bookObject[1], Field.Store.YES));
						document.add(new TextField("agency", (bookObject[2] == null) ? "" : (String) bookObject[2], Field.Store.YES));
						document.add(new TextField("type", "著作", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(conList.size() > 0){
					System.out.println(conList.size());
					for (Object[] conObject : conList) {
						Document document = new Document();
						document.add(new TextField("name", (conObject[0] == null) ? "" : (String) conObject[0], Field.Store.YES));
						document.add(new TextField("author", (conObject[1] == null) ? "" : (String) conObject[1], Field.Store.YES));
						document.add(new TextField("agency", (conObject[2] == null) ? "" : (String) conObject[2], Field.Store.YES));
						document.add(new TextField("type", "研究咨询报告", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(elecList.size() > 0){
					System.out.println(elecList.size());
					for (Object[] elecObject : elecList) {
						Document document = new Document();
						document.add(new TextField("name", (elecObject[0] == null) ? "" : (String) elecObject[0], Field.Store.YES));
						document.add(new TextField("author", (elecObject[1] == null) ? "" : (String) elecObject[1], Field.Store.YES));
						document.add(new TextField("agency", (elecObject[2] == null) ? "" : (String) elecObject[2], Field.Store.YES));
						document.add(new TextField("type", "电子出版物", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(patentList.size() > 0){
					System.out.println(patentList.size());
					for (Object[] patentObject : patentList) {
						Document document = new Document();
						document.add(new TextField("name", (patentObject[0] == null) ? "" : (String) patentObject[0], Field.Store.YES));
						document.add(new TextField("author", (patentObject[1] == null) ? "" : (String) patentObject[1], Field.Store.YES));
						document.add(new TextField("agency", (patentObject[2] == null) ? "" : (String) patentObject[2], Field.Store.YES));
						document.add(new TextField("type", "专利", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				if(otherList.size() > 0){
					System.out.println(otherList.size());
					for (Object[] otherObject : otherList) {
						Document document = new Document();
						document.add(new TextField("name", (otherObject[0] == null) ? "" : (String) otherObject[0], Field.Store.YES));
						document.add(new TextField("author", (otherObject[1] == null) ? "" : (String) otherObject[1], Field.Store.YES));
						document.add(new TextField("agency", (otherObject[2] == null) ? "" : (String) otherObject[2], Field.Store.YES));
						document.add(new TextField("type", "其他成果", Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				indexWriter.close();
				directory.close();
			}
			if(range.contains("award")){
				String hql = "select aa.productName, aa.applicantName, un.name, aa.disciplineType from AwardApplication aa left join aa.university un";
				List<Object[]> awardList = dao.query(hql);
				Analyzer analyzer = new IKAnalyzer(true);
				Directory directory = null;
				IndexWriter indexWriter = null;
				String indexPath = ApplicationContainer.sc.getRealPath("/system/totalSearch/award");
				directory = FSDirectory.open(new File(indexPath));
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();//更新前先删除老版本
				if(awardList.size() > 0){
					System.out.println(awardList.size());
					for (Object[] awardObject : awardList) {
						Document document = new Document();
						document.add(new TextField("name", (awardObject[0] == null) ? "" : (String) awardObject[0], Field.Store.YES));
						document.add(new TextField("author", (awardObject[1] == null) ? "" : (String) awardObject[1], Field.Store.YES));
						document.add(new TextField("agency", (awardObject[2] == null) ? "" : (String) awardObject[2], Field.Store.YES));
						document.add(new TextField("disciplineType", (awardObject[3] == null) ? "" : (String) awardObject[3], Field.Store.YES));
						indexWriter.addDocument(document);
					}
				}
				indexWriter.close();
				directory.close();
			}
			System.out.println("数据索引完成！ 耗时：" + (System.currentTimeMillis() - begin) + "ms");
			success = true;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return success;	
	}
}
