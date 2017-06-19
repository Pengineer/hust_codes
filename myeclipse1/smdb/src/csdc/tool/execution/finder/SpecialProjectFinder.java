package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.SpecialApplication;
import csdc.bean.SpecialGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 专项任务项目查找辅助类
 * @author pengliang
 *
 */
@Component
public class SpecialProjectFinder {
	
	@Autowired
	private IHibernateBaseDao dao;

	/**
	 * [项目名称+负责人姓名+项目年度] -> [application.id]
	 */
	private Map<String, String> applicationMap;
	
	private Map<String, String> applicationMapByYear;
	
	private Map<String, String> applicationMapByYearStatus;
	
	
	/**
	 * [立项编号] -> [granted.id]
	 */
	private Map<String, String> pNumberMap;
	
	/**
	 * [立项编号 + 项目名称] -> [granted.id]
	 */
	private Map<String, String> pNameMap;
	
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		applicationMap = null;
		applicationMapByYear = null;
		applicationMapByYearStatus = null;
		pNumberMap = null;
	}
	
	/**
	 * 根据[项目批准号]，返回一个granted(PO)
	 * @param number 项目批准号
	 * @return
	 */
	public SpecialGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (SpecialGranted) (grantedId == null ? null : dao.query(SpecialGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目批准号 + 项目名称]，返回一个granted(PO)
	 * @param number 项目批准号
	 * @return
	 */
	public SpecialGranted findGrantedByName(String number, String name) {
		if (pNumberMap == null) {
			initGranted();
		}
		String key = number + name;
		String grantedId = pNameMap.get(key);
		return (SpecialGranted) (grantedId == null ? null : dao.query(SpecialGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public SpecialApplication findApplication(String projectName, String applicantName, int year) {
		if (applicationMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", "") + year);
		String applicationId = applicationMap.get(key);
		return (SpecialApplication) (applicationId == null ? null : dao.query(SpecialApplication.class, applicationId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]和[审核状态码]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @param year
	 * @param status
	 * @return
	 */
	public SpecialApplication findApplication(String projectName, String applicantName, int year, int status) {
		if (applicationMapByYearStatus == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", "") + year + status);
		String applicationId = applicationMapByYearStatus.get(key);
		return (SpecialApplication) (applicationId == null ? null : dao.query(SpecialApplication.class, applicationId));
	}
	
	/**
	 * 根据[一个负责人的姓名]和[项目年度]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public SpecialApplication findApplication(String applicantName, int year) {
		if (applicationMap == null) {
			initApplication();
		}
		String key = applicantName.replaceAll("\\s+", "") + year;
		String applicationId = applicationMapByYear.get(key);
		return (SpecialApplication) (applicationId == null ? null : dao.query(SpecialApplication.class, applicationId));
	}
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		pNameMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select granted.number, granted.name, granted.id from SpecialGranted granted");
		for (Object[] o : list) {
			String number = (String) o[0];
			String name = (String) o[1];
			String grantedId = (String) o[2];
			String key = number + name;
			
			pNumberMap.put(number, grantedId);
			pNameMap.put(key, grantedId);
		}

		System.out.println("init SpecialGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		applicationMap = new HashMap<String, String>();
		applicationMapByYear = new HashMap<String, String>();
		applicationMapByYearStatus = new HashMap<String,String>();
		List<Object[]> list = dao.query("select application.name, application.year, mem.member.name, application.id, application.status from SpecialApplication application left join application.specialMember mem where mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc");
		list.addAll(dao.query("select granted.name, application.year, mem.member.name, application.id, application.status from SpecialGranted granted, SpecialApplication application left join application.specialMember mem where granted.application.id = application.id and granted.name <> application.name and mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			
			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			int status = (Integer)o[4];
			
			applicationMap.put(projectName + applicantName + year, applicationId);
			applicationMapByYear.put(applicantName + year, applicationId);
			applicationMapByYearStatus.put(projectName + applicantName + year + status, applicationId);
		}

		System.out.println("init SpecialApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
