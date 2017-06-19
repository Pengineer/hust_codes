package csdc.tool.execution.importer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectApplicationReview;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.reader.JxlExcelReader;

/**
 * 一般项目评审结果更新导入
 * @author fengcl
 *
 */
public class GeneralReviewerUpdateImporter extends Importer{

	private JxlExcelReader reader;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * [项目名称+项目编号 到 项目ID]
	 */
	private HashMap<String, String> projectNameNumberToIdMap;
	
	/**
	 * [专家名称+专家编号 到 专家ID]
	 */
	private HashMap<String, String> expertNameNumberToIdMap;
	
	/**
	 * [项目名称+项目编号 到 专家ID与评审结果对象的映射]
	 */
	private HashMap<String, HashMap<String, Integer>> projectToReviewersMap;
	
	/**
	 * 项目年度
	 */
	private int year = 2013;

	/**
	 * reader重置
	 * @throws Exception
	 */
	private void resetReader() throws Exception {
		reader.readSheet(0);
	}
	
	@Override
	public void work() throws Exception {
		initData();
		initProjectMap();
		importData();
	}
	
	private void importData() throws Exception {
		resetReader();
		
		while (next(reader)) {
//			System.out.println(reader.getCurrentRowIndex() + "/" + reader.getRowNumber());
			
			String key = StringTool.replaceBlank(G + ((null == A) ? "" : A));	//项目名称+项目编号
			HashMap<String, Integer> expertPR = projectToReviewersMap.get(key);	//数据库中专家集合
			if (expertPR == null) {
				System.out.println("项目[" + key + "]在数据库中找不到评审专家！");
			}
			if (projectNameNumberToIdMap.get(key) == null) {
				System.out.println("数据问题：" + key + "/" + projectNameNumberToIdMap.get(key));
				continue;
			}
			HashSet<String> expertIds = new HashSet<String>();					//Excel中专家集合
			expertIds.add(getExpertId(AJ + AK));
			expertIds.add(getExpertId(AN + AO));
			expertIds.add(getExpertId(AR + AS));
			expertIds.add(getExpertId(AV + AW));
			expertIds.add(getExpertId(AZ + BA));
			//遍历excel中的专家集合，添加数据库中没有的评审专家
			for (String expertId : expertIds) {
				if (null == expertPR || !expertPR.containsKey(expertId)) {
					ProjectApplicationReview gr = new ProjectApplicationReview(expertId, projectNameNumberToIdMap.get(key), 1, year, "general"); 
					dao.add(gr);
					System.out.println("新增：" + expertId + "-" + projectNameNumberToIdMap.get(key) + "-" + 1 + "-" + year);
				}
			}
			//遍历数据库中的专家集合，删除excel中没有的评审专家
			if (null != expertPR) {
				for (Entry<String, Integer> entry : expertPR.entrySet()) {
					if (!expertIds.contains(entry.getKey())) {
						dao.delete(ProjectApplicationReview.class, entry.getValue());
						System.out.println("删除：" + entry.getKey() + "-" + projectNameNumberToIdMap.get(key) + "-" + 1 + "-" + year + "/" + entry.getValue());
					}
				}
			}
		}
	}
	
	/**
	 * 初始化项目评审信息
	 */
	private void initData(){
		projectToReviewersMap = new HashMap<String, HashMap<String, Integer>>();
		List<Object[]> list = dao.query("select p.id, p.projectName, p.number, pr.reviewer.id, pr.id from ProjectApplicationReview pr, ProjectApplication p where p.type = 'general' and pr.project.id = p.id and pr.year = ? order by p.id", year);
		for (Object[] obj : list) {
			String key = StringTool.replaceBlank((String)obj[1] + ((null==obj[2]) ? "" : (String)obj[2])); 
			HashMap<String, Integer> expertPR = projectToReviewersMap.get(key);
			if(null == expertPR){
				expertPR = new HashMap<String, Integer>();
			}
			expertPR.put((String)obj[3], (Integer)obj[4]);//专家id <-> 项目评审结果id
			projectToReviewersMap.put(key, expertPR);
		}
	}
	
	/**
	 * 初始化项目信息
	 */
	private void initProjectMap(){
		projectNameNumberToIdMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select p.id, p.projectName, p.number from ProjectApplication p where p.type = 'general' and p.isReviewable = 1 and p.year = ?", year);
		for (Object[] obj : list) {
			projectNameNumberToIdMap.put(StringTool.replaceBlank((String)obj[1] + ((null==obj[2]) ? "" : (String)obj[2])), (String)obj[0]);
		}
	}
	
	/**
	 * 初始化专家信息
	 */
	private void initExpertMap(){
		expertNameNumberToIdMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select e.id, e.name, e.number from Expert e where e.type = '专职人员'");
		for (Object[] obj : list) {
			expertNameNumberToIdMap.put(StringTool.replaceBlank((String)obj[1] + String.valueOf(obj[2])), (String)obj[0]);
		}
	}
	
	/**
	 * 根据[专家名称+专家编号] 获取 专家ID
	 * @param input	专家名称+专家编号
	 * @return	 专家ID
	 */
	private String getExpertId(String input){
		if (null == expertNameNumberToIdMap) {
			initExpertMap();
		}
		String key = StringTool.replaceBlank(input);
		String expertId = expertNameNumberToIdMap.get(key);
		if (null == expertId) {//找不到专家
			System.out.println("[专家姓名+专家编号]：" + key);
		}
		return expertId;
	}
	
	public GeneralReviewerUpdateImporter() {
	}
	
	public GeneralReviewerUpdateImporter(String file) {
		reader = new JxlExcelReader(file);
	}
}
