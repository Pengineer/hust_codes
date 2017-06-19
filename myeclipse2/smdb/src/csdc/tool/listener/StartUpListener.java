package csdc.tool.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import csdc.bean.SystemConfig;
import csdc.dao.HibernateBaseDao;
import csdc.tool.ApplicationContainer;
import csdc.tool.SpringBean;

public class StartUpListener implements ServletContextListener {
	private SystemConfig systemConfig;
	
	/* 监听服务器启动 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		ApplicationContainer.sc = sc;
		addSystemConfig(sc, "teacherRegister", "0");//是否开放教师注册 0 不开放，1 开放
		addSystemConfig(sc, "allowedIp", null);
		addSystemConfig(sc, "refusedIp", null);
		addSystemConfig(sc, "maxSession", "20");// 最大连接数
		addSystemConfig(sc, "queryRows", "200");// 每次查询行数
		addSystemConfig(sc, "numReview", "1");// 评审专家个数
		addSystemConfig(sc, "rows", "10");// 每页大小
		addSystemConfig(sc, "awardSessio n", "6");// 当前奖励届次
		addSystemConfig(sc, "youthFee", "7");// 青年基金项目经费
		addSystemConfig(sc, "planFee", "9");// 规划基金项目经费
		addSystemConfig(sc, "currentYear", "2012");// 当前年份
		addSystemConfig(sc, "tempUploadPath", "/temp");// 临时文件上传根目录
		addSystemConfig(sc, "UserPictureUploadPath", "/upload/person/photo");// 个人照片保存路径
		addSystemConfig(sc, "NewsFileUploadPath", "/upload/news");// 新闻附件保存路径
		addSystemConfig(sc, "NoticeFileUploadPath", "/upload/notice");// 通知附件保存路径
		addSystemConfig(sc, "MailFileUploadPath", "/upload/mail");// 邮件附件保存路径
		addSystemConfig(sc, "productFirstAuditLevel", "4");// 【账号类型】成果初审级别(高校初审【注意：4含部属高校、地方高校】)
		addSystemConfig(sc, "productFinalAuditLevel", "2");// 【账号类型】成果终审级别(部级终审)
		addSystemConfig(sc, "DWUpdateTime", "");// 数据仓库更新时间

		addSystemOption(sc, "sex", "sexList", 3);// 性别
		addSystemOption(sc, "newsType", "newsItems", 1);// 获取新闻类别
		addSystemOption(sc, "noticeType", "noticeItems", 1);// 获取通知类别
		addSystemOption(sc, "messageType", "messageItems", 1);// 获取留言类别
		addSystemOption(sc, "variation", "varItems", 2);// 获取变更事项
		addSystemOption(sc, "productType", "pdtItems", 2);// 获取成果类别

		sc.setAttribute("systemVersion", "11704");//系统版本号
		//sc.setAttribute("jsVersion", ".js?ver=7531");//测试使用js版本号
		//sc.setAttribute("jsVersion", "-min.js?ver=7531");//发布使用js版本号
		
	}

	/* 监听服务器关闭 */
	public void contextDestroyed(ServletContextEvent event) {
	}


	/**
	 * 查找是否有系统配置信息，有则读取，没有则set默认值（value）
	 * @param sc
	 * @param key 
	 * @param value 默认值
	 */
	private void addSystemConfig(ServletContext sc, String key, Object defaultValue) {
		HibernateBaseDao dao = (HibernateBaseDao) SpringBean.getBean("hibernateBaseDao");
		List list = dao.query("select sc from SystemConfig sc where sc.key = '"+ key +"'");
		if(list.size() != 0){
			String value = ((SystemConfig) list.get(0)).getValue();
			sc.setAttribute(key, value);
		} else {
			sc.setAttribute(key, defaultValue);
		}
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
			list = dao.query("select s from SystemOption s where s.systemOption.standard = '" + standard + "' and s.systemOption.code is null and s.isAvailable = 1 order by s.code asc");
		} else if(type == 2){
			list = dao.query("select s.code, s.name from SystemOption s where s.systemOption.standard = '" + standard + "' and s.systemOption.code is null and s.isAvailable = 1 order by s.code asc");
		} else {
			list = dao.query("select s.name from SystemOption s where s.systemOption.standard = '" + standard + "' and s.systemOption.code is null and s.isAvailable = 1 order by s.code asc");
		}
		sc.setAttribute(key, list);
	}
	
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
}
