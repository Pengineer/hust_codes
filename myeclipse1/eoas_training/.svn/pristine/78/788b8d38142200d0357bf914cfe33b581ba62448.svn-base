package csdc.tool.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import csdc.bean.Account;
import csdc.bean.SystemOption;
import csdc.service.IBaseService;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;



public class StartUpListener implements ServletContextListener {

	/* 监听服务器启动 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		//System.out.println("系统启动");

		ServletContext sc = event.getServletContext();
		ApplicationContainer.sc = sc;
		IBaseService baseService = (IBaseService) SpringBean.getBean("baseService", sc);
		Map map = new HashMap();

//		// 获取系统权限与url对应关系表
//		List<Object> rightUrl = new ArrayList<Object>();
//		rightUrl = baseservice.query("select right0.name, right_action.actionurl from Right right0, Right_Action right_action where right0.id = right_action.right.id order by right_action.actionurl asc");
//		sc.setAttribute("rightUrl", rightUrl);

		// 民族列表
		ArrayList<Object> ethnic = new ArrayList<Object>();
		map.put("systemOptionId", "4028d88a2db7165d012db7180d3b0001");
		try {
			ethnic = (ArrayList<Object>) baseService.list(SystemOption.class.getName() + ".listSystemOptionMap", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.setAttribute("ethnic", ethnic);
		
		// 一级学科列表
		map.clear();
		ArrayList disciplineOne = new ArrayList();
		map.put("name", "%2009%代码表%");
		
			disciplineOne = (ArrayList) baseService.list(SystemOption.class.getName() + ".listSystemOptionMap", map);

		sc.setAttribute("disciplineOne", disciplineOne);
		
		
		// 个人信息上传照片路径
		sc.setAttribute("PersonPictureUploadPath", "/upload/user/image");

		// 临时上传目录
		sc.setAttribute("tempUploadPath", "/temp");
		sc.setAttribute("systemVersion", "10077");//系统版本号
		
		//供分配的人员列表
		List<Object> accountList = new ArrayList<Object>();
		accountList = baseService.list(Account.class, null);
		sc.setAttribute("accountList", accountList);
	}

	/* 监听服务器关闭 */
	public void contextDestroyed(ServletContextEvent event) {
		//System.out.println("系统关闭");
	}
}
