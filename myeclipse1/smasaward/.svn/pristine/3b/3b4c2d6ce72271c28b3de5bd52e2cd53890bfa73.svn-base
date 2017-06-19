package csdc.tool.listener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import csdc.bean.SystemOption;
import csdc.bean.University;
import csdc.dao.HibernateBaseDao;
import csdc.service.IBaseService;
import csdc.service.IGeneralService;
import csdc.tool.ApplicationContainer;
import csdc.tool.Customized;
import csdc.tool.SpringBean;
import csdc.tool.SystemConfigLoader;
import csdc.tool.mail.MailController;
import csdc.tool.tableKit.imp.DisciplineKit;
import csdc.tool.tableKit.imp.UniversityKit;

public class StartUpListener implements ServletContextListener {

	/* 监听服务器启动 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("系统启动");
		
		ServletContext sc = event.getServletContext();
		ApplicationContainer.sc = sc;
		IBaseService baseService = (IBaseService) SpringBean.getBean("baseService", sc);
		IGeneralService generalService = (IGeneralService) SpringBean.getBean("generalService", sc);
		
//		ExpertKit.setBaseService(baseService);
//		ProjectKit.setBaseService(baseService);
		DisciplineKit.setBaseService(baseService);
		Customized.setBaseService(baseService);
		Customized.setGeneralService(generalService);
		UniversityKit.setBaseService(baseService);
		
		// 获取系统权限与url对应关系表
		List<Object> rightUrl = new ArrayList<Object>();
		rightUrl = baseService.query("select right0.name, right_action.actionurl from Right right0, RightUrl right_action where right0.id = right_action.right.id order by right_action.actionurl asc");
		sc.setAttribute("rightUrl", rightUrl);
		
		// 民族列表
		List<Object> ethnic = baseService.query("from SystemOption s where s.parent.id = 'sysoption00001' and s.isAvailable = 1 order by s.id asc");
		sc.setAttribute("ethnic", ethnic);
		
		// 性别
		List<Object> gender = baseService.query("from SystemOption s where s.parent.id = 'sysoption00007' and s.isAvailable = 1 order by s.id asc");
		sc.setAttribute("gender", gender);
		
		//专家职称（教授、副教授）对应专家职称等级（正高级、高级、副高级、中级、助理级、员级）
		List<Object[]> specialityTitleRank = baseService.query("select so.code,so.name,so.description from SystemOption so where so.standard = 'GBT8561-2001' and so.description is not null");
		sc.setAttribute("specialityTitleRank", specialityTitleRank);
		
		// 高校名称 -> 高校代码
		List<University> uall = baseService.query("from University u order by u.name asc");
		HashMap<String, String> univNameCodeMap = new LinkedHashMap<String, String>();
		HashMap<String, String> univCodeNameMap = new LinkedHashMap<String, String>();
		for (University university : uall) {
			univNameCodeMap.put(university.getName(), university.getCode());
		}
		Comparator c = Collator.getInstance(Locale.CHINA);
		Set<String> univNameSet = univNameCodeMap.keySet();
		Object[] univNameArray = univNameSet.toArray();
	    //升序排列   
	    Arrays.sort(univNameArray,c);   
	    for (int i = 0; i < univNameArray.length; i++) {
			univCodeNameMap.put(univNameCodeMap.get(univNameArray[i].toString()), univNameArray[i].toString());
		}
		sc.setAttribute("univNameCodeMap", univNameCodeMap);
		sc.setAttribute("univCodeNameMap", univCodeNameMap);
		
		// 所有学科
		List<SystemOption> disall = baseService.query("from SystemOption s where s.standard = '[GBT13745-2009]' or s.standard = '[GBT13745-1992]' and s.isAvailable = 1 order by s.standard asc");
		sc.setAttribute("disall", disall);
		// 学科代码 -> 学科名称
		HashMap<String, String> disMap = new HashMap<String, String>();
		for (SystemOption so : disall) {
			disMap.put(so.getCode(), so.getName());
		}
		sc.setAttribute("disMap", disMap);
		
		//获取专业职称的二级节点值
		Map<String,String> specialistTitlemap = new HashMap();
		List<SystemOption> systemOptionList = baseService.query("from SystemOption s where s.standard = 'GBT8561-2001' and s.parent.id != '4028d88a380233c701380235bd760001' and s.id != '4028d88a380233c701380235bd760001' order by s.code asc ");
		if(systemOptionList.size() > 0){
			specialistTitlemap = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				specialistTitlemap.put(systemOption.getCode()+"/"+systemOption.getName(), systemOption.getCode()+"/"+systemOption.getName());
			}
		}		
		sc.setAttribute("specialistTitlemap", specialistTitlemap);
		addSystemOption(sc, "variation", "varItems", 2);// 获取变更事项
		addSystemOption(sc, "productType", "pdtItems", 2);// 获取成果类别
		
		sc.setAttribute("systemVersion", "11305");//系统版本号
		
		SystemConfigLoader.load();
	}

	/* 监听服务器关闭 */
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("系统关闭");
		ServletContext sc = event.getServletContext();
		MailController mailController = (MailController) SpringBean.getBean("mailController", sc);
		mailController.cancelAll();
	}
	
	/**
	 * 读取系统选项表信息
	 * @param sc
	 * @param id sysOption的standard
	 * @param key set进sc的key
	 * @param type 1:获取整个对象 2:获取code和name 3:获取name
	 */
	private void addSystemOption(ServletContext sc, String standard, String key, Integer type) {
		HibernateBaseDao dao = (HibernateBaseDao) SpringBean.getBean("hibernateBaseDao");
		List list;
		if(type == 1){
			list = dao.query("select s from SystemOption s where s.parent.standard = '" + standard + "' and s.parent.code is null and s.isAvailable = 1 order by s.code asc");
		} else if(type == 2){
			list = dao.query("select s.code, s.name from SystemOption s where s.parent.standard = '" + standard + "' and s.parent.code is null and s.isAvailable = 1 order by s.code asc");
		} else {
			list = dao.query("select s.name from SystemOption s where s.parent.standard = '" + standard + "' and s.parent.code is null and s.isAvailable = 1 order by s.code asc");
		}
		sc.setAttribute(key, list);
	}
}
