package csdc.tool.execution.finder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.ProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 一般项目辅助类
 * @author fengcl
 *
 */
@Component
public class GeneralFinder {
	/**
	 * [项目名称+负责人姓名+项目年度] -> [项目id]
	 */
	private Map<String, String> projectMap;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * 初始化projectMap
	 */
	private void initProjectMap() {
		long begin = System.currentTimeMillis();
		
		projectMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select p.id, p.projectName, p.director, p.year from ProjectApplication p where p.type = 'general'");
		for (Object[] o : list) {
			String projectId = (String) o[0];
			String projectName = StringTool.fix((String) o[1]);
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			int year = (Integer) o[3];

			projectMap.put(projectName + applicantName + year, projectId);
		}
		
		System.out.println("init General complete! 共有["+ list.size() +"]个项目； Used time: " + (System.currentTimeMillis() - begin) + "ms");
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]，返回一个general(PO)
	 * @param projectName	//项目名称
	 * @param applicantName	//申请人姓名
	 * @param addIfNotFound	//没有找到，是否新增
	 * @return
	 */
	public ProjectApplication findGeneral(String projectName, String applicantName, int year, boolean addIfNotFound) {
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
			project = new ProjectApplication();
			project.setType("general");
			projectMap.put(key, (String) dao.add(project));
		}
		return project;
	}
}
