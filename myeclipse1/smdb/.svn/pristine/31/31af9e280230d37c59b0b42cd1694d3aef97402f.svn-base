package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.EntrustApplication;
import csdc.bean.EntrustGranted;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 委托项目查找辅助类
 * @author pengliang
 *
 */
@Component
public class EntrustProjectFinder {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	/**
	 * [项目名称+负责人姓名+项目年度] -> [application.id]
	 */
	private Map<String, String> applicationMap;
	
	/**
	 * [立项编号] -> [granted.id]
	 */
	private Map<String, String> pNumberMap;
	
	/**
	 * [立项编号 + 项目名称] -> [granted.id]
	 */
	private Map<String, String> pNumberAndNameMap;
	
	/**
	 * 置空辅助类，释放部分内存
	 */
	public void reset() {
		applicationMap = null;
		pNumberMap = null;
	}
	
	/**
	 * 根据[立项编号]，返回一个granted(PO)
	 * @param number 立项批准号
	 * @return
	 */
	public EntrustGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (EntrustGranted) (grantedId == null ? null : dao.query(EntrustGranted.class, grantedId));
	}
	
	/**
	 * 根据[立项编号 + 项目名称]，返回一个granted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public EntrustGranted findGranted(String number, String name) {
		if (pNumberAndNameMap == null) {
			initGranted();
		}
		String grantedId = pNumberAndNameMap.get(number + name);
		return (EntrustGranted) (grantedId == null ? null : dao.query(EntrustGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public EntrustApplication findApplication(String projectName, String applicantName, int year) {
		if (applicationMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", "") + year);
		String applicationId = applicationMap.get(key);
		return (EntrustApplication) (applicationId == null ? null : dao.query(EntrustApplication.class, applicationId));
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		applicationMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select application.name, application.year, mem.member.name, application.id from EntrustApplication application left join application.entrustMember mem where mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc");
		list.addAll(dao.query("select granted.name, application.year, mem.member.name, application.id from EntrustGranted granted, EntrustApplication application left join application.entrustMember mem where granted.application.id = application.id and granted.name <> application.name and mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);

			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			
			applicationMap.put(projectName + applicantName + year, applicationId);
		}

		System.out.println("init EntrustApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		pNumberAndNameMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select granted.number, granted.id, granted.name from EntrustGranted granted");
		for (Object[] o : list) {
			String number = (String) o[0];
			String grantedId = (String) o[1];
			String name = (String) o[2];
			
			pNumberMap.put(number, grantedId);
			pNumberAndNameMap.put(number + name, grantedId);
		}

		System.out.println("init EntrustGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}	
}
