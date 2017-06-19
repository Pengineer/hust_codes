package csdc.tool.execution.finder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;

/**
 * 一般项目查找辅助类
 * @author xuhan
 *
 */
@Component
public class GeneralProjectFinder {
	
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
	 * 根据[立项编号]，返回一个granted(PO)
	 * @param number 立项编号
	 * @return
	 */
	public GeneralGranted findGranted(String number) {
		if (pNumberMap == null) {
			initGranted();
		}
		String grantedId = pNumberMap.get(number);
		return (GeneralGranted) (grantedId == null ? null : dao.query(GeneralGranted.class, grantedId));
	}
	
	/**
	 * 根据[项目名称]和[一个负责人的姓名]和[项目年度]，返回一个application(PO)
	 * @param projectName
	 * @param applicantName
	 * @return
	 */
	public GeneralApplication findApplication(String projectName, String applicantName, int year) {
		if (applicationMap == null) {
			initApplication();
		}
		String key = StringTool.fix(projectName + applicantName.replaceAll("\\d+", "") + year);
		String applicationId = applicationMap.get(key);
		return (GeneralApplication) (applicationId == null ? null : dao.query(GeneralApplication.class, applicationId));
	}
	
	private void initGranted() {
		Date begin = new Date();
		
		pNumberMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select granted.number, granted.id from GeneralGranted granted");
		for (Object[] o : list) {
			String number = (String) o[0];
			String grantedId = (String) o[1];
			
			pNumberMap.put(number, grantedId);
		}

		System.out.println("init GeneralGranted complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	private void initApplication() {
		Date begin = new Date();
		
		applicationMap = new HashMap<String, String>();
		List<Object[]> list = dao.query("select application.name, application.year, mem.member.name, application.id from GeneralApplication application left join application.generalMember mem where mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc");
		list.addAll(dao.query("select granted.name, application.year, mem.member.name, application.id from GeneralGranted granted, GeneralApplication application left join application.generalMember mem where granted.application.id = application.id and granted.name <> application.name and mem.isDirector = 1 order by application.year asc, application.finalAuditResult asc"));
		for (Object[] o : list) {
			String projectName = StringTool.fix((String) o[0]);
			
			int year = (Integer)o[1];
			String applicantName = StringTool.fix((String) o[2]).replaceAll("\\d+", "");
			String applicationId = (String) o[3];
			
			applicationMap.put(projectName + applicantName + year, applicationId);
		}

		System.out.println("init GeneralApplication complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
}
