package csdc.tool.execution.finder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.ProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 基地项目辅助类
 * @author fengcl
 *
 */
@Component
public class InstpFinder {
	
	/**
	 * [项目名称+负责人姓名+项目年度] -> [项目id]
	 */
	private Map<String, String> projectMap;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 高校名称 -> 高校代码
	 */
	private Map<String, String> univNameCodeMap;
	
	/**
	 * 初始化projectMap
	 */
	private void initProjectMap() {
		long begin = System.currentTimeMillis();
		
		projectMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select p.id, p.projectName, p.director, p.year from ProjectApplication p where p.type = 'instp'");
		for (Object[] o : list) {
			String projectId = (String) o[0];
			String projectName = StringTool.fix((String) o[1]);
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			int year = (Integer) o[3];

			projectMap.put(projectName + applicantName + year, projectId);
		}
		
		System.out.println("init Instp complete! 共有["+ list.size() +"]个项目； Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]，返回一个instp(PO)
	 * @param projectName	//项目名称
	 * @param applicantName	//申请人姓名
	 * @param addIfNotFound	//没有找到，是否新增
	 * @return
	 */
	public ProjectApplication findInstp(String projectName, String applicantName, int year, boolean addIfNotFound) {
		if (projectMap == null) {
			initProjectMap();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", "") + year);
		String projectId = projectMap.get(key);
		
		ProjectApplication project = null;
		if (null != projectId) {
			project = (ProjectApplication) dao.query(ProjectApplication.class, projectId);
		}
		if (addIfNotFound && null == project) {
			System.out.println(applicantName + "/" + projectName);
			project = new ProjectApplication();
			project.setType("instp");
			projectMap.put(key, (String) dao.add(project));
		}
		return project;
	}
	
	/**
	 * 根据项目负责人所属学校和项目负责人获取每个负责人的[高校代码+项目负责人]组合
	 * @param directorUniversity	项目负责人所属学校
	 * @param director				项目负责人
	 * @return
	 */
	public String[] getKeys(String directorUniversity, String director, boolean needPrint) {
		if (null == univNameCodeMap) {
			univNameCodeMap = (Map<String, String>) ActionContext.getContext().getApplication().get("univNameCodeMap");
		}
		
		String[] universities = directorUniversity.split("[\\s;,、；]+");
		String[] directors = director.split("[\\s;,、；]+");
		int univCnt = universities.length;
		int directorCnt = directors.length;
		//根据名称获取高校代码
		String[] univCodes = new String[directorCnt];
		for (int i = 0; i < univCnt; i++) {
			String code = univNameCodeMap.get(universities[i]);
			if (null == code) {
				if (needPrint) {
					System.out.println(directors[i] + universities[i]);
				}
				continue; 
			}
			universities[i] = code;
			univCodes[i] = universities[i];
		}
		//补齐负责人所属学校代码数组
		for (int i = univCnt; i < directorCnt; i++) {
			univCodes[i] = universities[univCnt - 1];
		}
		//组合keys
		String[] keys = new String[directorCnt]; 
		for (int i = 0; i < directorCnt; i++) {
			keys[i] = univCodes[i] + StringTool.chineseCharacterFix(directors[i]);
		}
		
		return keys;
	}
}