package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.PostApplication;
import csdc.bean.PostGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 后期资助项目查找辅助类
 * @author xuhan
 *
 */
@Component
public class PostProjectFinder {
	
	@Autowired
	private IHibernateBaseDao dao;

	/**
	 * [项目名称+负责人姓名] -> [postApplication.id]
	 */
	private Map<String, String> pNameMap;
	
	/**
	 * [项目年度+负责人姓名] -> [postApplication.id]
	 */
	private Map<String, String> pYearMap;
	
	/**
	 * [立项编号] -> [postGranted.id]
	 */
	private Map<String, String> pNumberMap;
	
	/**
	 * 根据[立项编号]，返回一个postGranted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public PostGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (PostGranted) (grantedId == null ? null : dao.query(PostGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]，返回一个postApplication(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public PostApplication findApplication(String projectName, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", ""));
		String applicationId = pNameMap.get(key);
		return (PostApplication) (applicationId == null ? null : dao.query(PostApplication.class, applicationId));
	}
	
	/**
	 * 根据[项目年度]和[一个负责人姓名]，返回一个postApplication(PO)
	 * @param year
	 * @param applicantName
	 * @return
	 */
	public PostApplication findApplication(int year, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(year + applicantName.replaceAll("\\d+", ""));
		String applicationId = pYearMap.get(key);
		return (PostApplication) (applicationId == null ? null : dao.query(PostApplication.class, applicationId));
	}
	
	
	
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select pg.number, pg.id from PostGranted pg");
		for (Object[] o : list) {
			String number = (String) o[0];
			String grantedId = (String) o[1];
			
			pNumberMap.put(number, grantedId);
		}

		System.out.println("init PostGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		pNameMap = new HashMap<String, String>();
		pYearMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select pa.name, pa.year, pm.member.name, pa.id from PostApplication pa left join pa.postMember pm where pm.isDirector = 1 order by pa.year asc, pa.finalAuditResult asc");
		list.addAll(dao.query("select pg.name, pa.year, pm.member.name, pa.id from PostGranted pg, PostApplication pa left join pa.postMember pm where pg.application.id = pa.id and pg.name <> pa.name and pm.isDirector = 1 order by pa.year asc, pa.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			
			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			
			pNameMap.put(projectName + applicantName, applicationId);
			pYearMap.put(year + applicantName, applicationId);
		}

		System.out.println("init PostApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
