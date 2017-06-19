package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.KeyApplication;
import csdc.bean.KeyGranted;
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
public class KeyProjectFinder {
	
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
	
	private Map<String, String> pNameGrantedMap;
	
	
	
	/**
	 * 根据[立项编号]，返回一个postGranted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public KeyGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (KeyGranted) (grantedId == null ? null : dao.query(KeyGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]，返回一个postGranted(PO)
	 * @param name 项目名称
	 * @return
	 */
	public KeyGranted findGrantedByName(String name) {
		if (pNameGrantedMap == null) {
			initGranted();
		}
		String grantedId = pNameGrantedMap.get(name);
		return (KeyGranted) (grantedId == null ? null : dao.query(KeyGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]，返回一个postApplication(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public KeyApplication findApplication(String projectName, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", ""));
		String applicationId = pNameMap.get(key);
		return (KeyApplication) (applicationId == null ? null : dao.query(KeyApplication.class, applicationId));
	}
	
	/**
	 * 根据[项目年度]和[一个负责人姓名]，返回一个keyApplication(PO)
	 * @param year
	 * @param applicantName
	 * @return
	 */
	public KeyApplication findApplication(int year, String applicantName) {
		if (pNameMap == null || pYearMap == null) {
			initApplication();
		}
		String key = StringTool.fix(year + applicantName.replaceAll("\\d+", ""));
		String applicationId = pYearMap.get(key);
		return (KeyApplication) (applicationId == null ? null : dao.query(KeyApplication.class, applicationId));
	}
	
	
	
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		pNameGrantedMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select kg.number, kg.id, kg.name from KeyGranted kg");
		for (Object[] o : list) {
			String number = (String) o[0];
			String grantedId = (String) o[1];
			String name = (String) o[2];
			
			pNumberMap.put(number, grantedId);
			pNameGrantedMap.put(name, grantedId);
		}

		System.out.println("init KeyGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		pNameMap = new HashMap<String, String>();
		pYearMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select ka.name, ka.year, km.member.name, ka.id from KeyApplication ka left join ka.keyMember km where km.isDirector = 1 order by ka.year asc, ka.finalAuditResult asc");
		list.addAll(dao.query("select kg.name, ka.year, km.member.name, ka.id from KeyGranted kg, KostApplication ka left join ka.keyMember km where kg.application.id = ka.id and kg.name <> ka.name and km.isDirector = 1 order by ka.year asc, ka.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			
			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			
			pNameMap.put(projectName + applicantName, applicationId);
			pYearMap.put(year + applicantName, applicationId);
		}

		System.out.println("init KeyApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}

