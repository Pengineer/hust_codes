package csdc.tool.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import csdc.bean.SystemConfig;
import csdc.service.IBaseService;
import csdc.tool.SpringBean;

public class StartUpListener implements ServletContextListener {

	/* 监听服务器启动 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("系统启动");
		
		ServletContext sc = event.getServletContext();
		IBaseService baseservice = (IBaseService) SpringBean.getBean("baseService", sc);
		
		// 获取系统权限与url对应关系表
		List<Object> rightUrl = new ArrayList<Object>();
		rightUrl = baseservice.query("select right0.name, right_action.actionurl from Right right0, RightUrl right_action where right0.id = right_action.right.id order by right_action.actionurl asc");
		sc.setAttribute("rightUrl", rightUrl);
		
		// 个人信息上传照片路径
		String UserPictureUploadPath = ((SystemConfig) baseservice.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00007'").get(0)).getValue();
		sc.setAttribute("UserPictureUploadPath", UserPictureUploadPath);
		
		// 每页显示记录个数
		String DisplayNumberEachPage = ((SystemConfig) baseservice.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00012'").get(0)).getValue();
		sc.setAttribute("DisplayNumberEachPage", DisplayNumberEachPage);
		
		// 民族列表
		List<Object> ethnic = baseservice.query("from SystemOption s where s.parent.id = 'sysoption00001' and s.isAvailable = 1 order by s.id asc");
		sc.setAttribute("ethnic", ethnic);
		
		// 性别
		List<Object> gender = baseservice.query("from SystemOption s where s.parent.id = 'sysoption00007' and s.isAvailable = 1 order by s.id asc");
		sc.setAttribute("gender", gender);
		

	}

	/* 监听服务器关闭 */
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("系统关闭");
	}
}