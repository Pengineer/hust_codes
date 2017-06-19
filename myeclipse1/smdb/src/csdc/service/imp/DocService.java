package csdc.service.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.IHibernateBaseDao;
import csdc.service.IDocService;
import csdc.tool.ApplicationContainer;
import csdc.tool.crawler.CrawlerExecutor;
import csdc.tool.crawler.DocumentUpdater;
import csdc.tool.filter.FileFilterTool;

/**
 * @author yingao
 *
 *	smdb文档上传至dmss的服务类
 */
public class DocService extends BaseService implements IDocService{
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	protected DmssService dmssService;
	
	@Autowired
	private CrawlerExecutor crawlerExecutor;
	
	@Autowired
	DocumentUpdater documentUpdater;
	
	int totalFileNum = 0;//文件总数计数器
	
	Map jsonMap;
	

	@Override
	public void upload(String[] paths) {
		documentUpdater.setPaths(paths);
		crawlerExecutor.execute(documentUpdater);
	}
	
	
	public  Map getCurrentProgress() {
		Map jsonMap = new HashMap();
		jsonMap.put("TaskCount", documentUpdater.getTaskCount());
		jsonMap.put("CompleteTaskCount", documentUpdater.getCompleteTaskCount());
		jsonMap.put("ActiveCount", documentUpdater.getActiveCount());
		return jsonMap;
	}
	
	// TODO 获取数据库中C_FILE字段不为空的记录总数
	public Map<String, int[]> getDataFileNum() {
		List<String> checkTables = new ArrayList<String>();
		checkTables.add("AwardApplication");
		Map<String, int[]> resultMap = new HashMap<String, int[]>();
		
		//所有有文件附件的表，一共
		//APPLICANT  AWARD_APPLICATION  MAIL  MESSAGE  NEWS  NOTICE  PERSON  PRODUCT  PEOJECT_ANNINSPECTION
		//PROJECT_DATA  PROJECT_ENDINSPECTION  PROJECT_GRANTED  PROJECT_MIDINSPECTION  PROJECT_VARIATION
		//STATISTIC_REPORT TEMPLATE
		resultMap.put("ApplicantFile", checkTable("Applicant", "file", "dfs"));
		resultMap.put("AwardApplicationFile", checkTable("AwardApplication", "file", "dfs"));
		resultMap.put("MailAttachement", checkTable("Mail", "attachment", "dfs"));
		resultMap.put("MessageContent", checkTable("Message", "content", "dfs"));
		resultMap.put("NewsFile", checkTable("News", "attachment", "dfs"));
		resultMap.put("NoticeFile", checkTable("Notice", "attachment", "dfs"));
		resultMap.put("PersonFile", checkTable("Person", "photoFile", "photoDfs"));
		resultMap.put("NoticeFile", checkTable("Notice", "attachment", "dfs"));
		resultMap.put("ProductFile", checkTable("Product", "file", "dfs"));
		resultMap.put("ProjectAnninspectionFile", checkTable("ProjectAnninspection", "file", "dfs"));
		resultMap.put("ProjectDataFile", checkTable("ProjectData", "file", "dfs"));
		resultMap.put("ProjectEndinspectionFile", checkTable("ProjectEndinspection", "file", "dfs"));
		resultMap.put("ProjectGrantedFile", checkTable("ProjectGranted", "file", "dfs"));
		resultMap.put("ProjectMidinspectionFile", checkTable("ProjectMidinspection", "file", "dfs"));
		resultMap.put("ProjectVariationFile", checkTable("ProjectVariation", "file", "dfs"));
		resultMap.put("ProjectVariationPostFile", checkTable("ProjectVariation", "postponementPlanFile", "postponementPlanDfs"));
		resultMap.put("TemplateFile", checkTable("Template", "templateFile", "dfs"));
		
//		resultMap.put("", checkTable(tableName, fileField, dfsField))
		return resultMap;
	}
	
	
	
	
	/**
	 * 统计表中相关字段
	 * @param tableName 需要检查的数据表
	 * @param fileField 需要检查的字段名
	 * @param dfsField
	 * @return result[0] 两个字段都不为空，result[1] file为空 dfs不为空，result[2] file为空 dfs为空，result[3] file不为空 dfs为空
	 */
	public int[] checkTable(String tableName, String fileField ,String dfsField) {
		int[] result = {0,0,0,0};//保存
		String dfsId = "dfs";
		String hql0 = "select count(*) from " + tableName + " a where a." + fileField +" is not null and a."+ dfsField + " is null";
		result[0] = (int) dao.count(hql0);
		
		//数据库中这种情况为0
//		String hql1 = "select count(*) from " + tableName + " a where a." + fileField +" is null and a."+ dfsField + " is not null";
//		result[1] = (int) dao.count(hql1);
		
		//这种情况不用处理
//		String hql2 = "select count(*) from " + tableName + " a where a." + fileField +" is null and a."+ dfsField + " is null";
//		result[2] = (int) dao.count(hql2);
		
		String hql3 = "select count(*) from " + tableName + " a where a." + fileField +" is not null and a."+ dfsField + " is null";
		result[3] = (int) dao.count(hql3);
		return result;
	}
	
	
	/**
	 * 获取目录树结构，并携带文件夹名称、路径、子文件夹，文件夹内文件数目
	 * @return
	 */
	public List<Map> getDirTree() {
		List<Map> listMap = new ArrayList<Map>();		
//		File rootFile = new File(ApplicationContainer.sc.getRealPath("upload"));
		File rootFile = new File("d:\\testDir");
		getDir(rootFile,listMap);
		return listMap;
	}
	
	public void getDir(File file, List<Map> listMap) {
		Map map = new HashMap();
		map.put("name", file.getName());
		map.put("path", file.getPath());
		List<Map> l = new ArrayList<Map>();
		map.put("child", l);
		File[] files = file.listFiles();
		if (file.listFiles(new FileFilterTool()).length > 0) {
			map.put("fileNum", file.listFiles(new FileFilterTool()).length);
		} else {
			map.put("fileNum", 0);
		}
		listMap.add(map);
		for (int i = 0;files!=null && i <  files.length ; i++ ) {
			if (files[i].isDirectory()) {
				getDir(files[i], l);
			}
		}
}
	
	/**
	 * 获取目录下文件数目，包括自文件夹的文件（递归实现）
	 * @param path
	 * @return int
	 */
	public int getDirFileNum(String path) {
		File rootFile = new File(ApplicationContainer.sc.getRealPath(path));
		totalFileNum = 0;
		getFileNum(rootFile);
		return totalFileNum;
	}
	
	public void getFileNum(File file) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				getFileNum(subFile);
			}
		} else {
			totalFileNum++;
		}
	}

	public void getDir(File file) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				getFileNum(subFile);
			}
		} else {
			totalFileNum++;
		}
	}
	public int getUploadedFileNum() {
		// TODO Auto-generated method stub
		return 0;
	}



}


