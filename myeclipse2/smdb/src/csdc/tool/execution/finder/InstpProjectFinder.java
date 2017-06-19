package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 基地项目查找辅助类
 * @author xuhan
 *
 */
@Component
public class InstpProjectFinder {
	
	@Autowired
	private IHibernateBaseDao dao;

	/**
	 * [项目名称+负责人姓名] -> [application.id]
	 */
	private Map<String, String> pNameMap;
	
	/**
	 * [项目年度+负责人姓名] -> [application.id]
	 */
	private Map<String, String> pYearMap;
	
	/**
	 * [立项编号] -> [granted.id]
	 */
	private Map<String, String> pNumberMap;
	
	/**
	 * [立项编号 + 项目名称] -> [granted.id]
	 */
	private Map<String, String> pNumberAndNameMap;
	
	
	
	/**
	 * 根据[立项编号]，返回一个granted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public InstpGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (InstpGranted) (grantedId == null ? null : dao.query(InstpGranted.class, grantedId));
	}
	
	/**
	 * 根据[立项编号 + 项目名称]，返回一个granted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public InstpGranted findGranted(String number, String name) {
		if (pNumberAndNameMap == null) {
			initGranted();
		}
		String grantedId = pNumberAndNameMap.get(number + name);
		return (InstpGranted) (grantedId == null ? null : dao.query(InstpGranted.class, grantedId));
	}
	
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public InstpApplication findApplication(String projectName, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", ""));
		String applicationId = pNameMap.get(key);
		return (InstpApplication) (applicationId == null ? null : dao.query(InstpApplication.class, applicationId));
	}
	
	/**
	 * 根据[项目年度]和[一个负责人姓名]，返回一个application(PO)
	 * @param year
	 * @param applicantName
	 * @return
	 */
	public InstpApplication findApplication(int year, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(year + applicantName.replaceAll("\\d+", ""));
		String applicationId = pYearMap.get(key);
		return (InstpApplication) (applicationId == null ? null : dao.query(InstpApplication.class, applicationId));
	}
	
	
	
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		pNumberAndNameMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select granted.number, granted.id, granted.name from InstpGranted granted");
		for (Object[] o : list) {
			String number = (String) o[0];
			String grantedId = (String) o[1];
			String name = (String) o[2];
			
			pNumberMap.put(number, grantedId);
			pNumberAndNameMap.put(number + name, grantedId);
		}

		System.out.println("init InstpGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		pNameMap = new HashMap<String, String>();
		pYearMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select application.name, application.year, mem.member.name, application.id from InstpApplication application left join application.instpMember mem where mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc");
		list.addAll(dao.query("select granted.name, application.year, mem.member.name, application.id from InstpGranted granted, InstpApplication application left join application.instpMember mem where granted.application.id = application.id and granted.name <> application.name and mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			
			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			
			pNameMap.put(projectName + applicantName, applicationId);
			pYearMap.put(year + applicantName, applicationId);
		}

		System.out.println("init InstpApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
