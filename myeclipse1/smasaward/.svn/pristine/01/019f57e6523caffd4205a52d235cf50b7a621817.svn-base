package csdc.tool;

import java.util.List;

import javax.servlet.ServletContext;

import csdc.bean.SystemConfig;
import csdc.service.IBaseService;

/**
 * 将SystemConfig表中的配置load进application
 * @author xuhan
 *
 */
@SuppressWarnings("unchecked")
public class SystemConfigLoader {
	
	public static void load() {
		ServletContext sc = ApplicationContainer.sc;
		
		IBaseService baseService = (IBaseService) SpringBean.getBean("baseService", sc);
		
		List<SystemConfig> configs = baseService.query("select sysconfig from SystemConfig sysconfig");
		for (SystemConfig systemConfig : configs) {
			try {
				sc.setAttribute(systemConfig.getKey(), Integer.parseInt(systemConfig.getValue()));
				System.err.println("系统配置[" + systemConfig.getKey() + ": " + systemConfig.getValue() +"]是数值类型，已转换成Integer并放入application");
			} catch (Exception e) {
				System.err.println("系统配置[" + systemConfig.getKey() + ": " + systemConfig.getValue() +"]不是是数值类型");
			}
		}
		
		/**
		 * 一般项目_评审专家_导入_开始时间
		 * 一般项目_评审专家_导入_结束时间
		 */
		String generalExpertStartDate = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00024'").get(0)).getValue();
		sc.setAttribute("GeneralReviewerImportedStartDate", generalExpertStartDate);		
		String generalExpertEndDate = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00025'").get(0)).getValue();
		sc.setAttribute("GeneralReviewerImportedEndDate", generalExpertEndDate);		
		
		/**
		 * 基地项目_评审专家_导入_开始时间
		 * 基地项目_评审专家_导入_结束时间
		 */
		String instpExpertStartDate = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00026'").get(0)).getValue();
		sc.setAttribute("InstpReviewerImportedStartDate", instpExpertStartDate);		
		String instpExpertEndDate = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00027'").get(0)).getValue();
		sc.setAttribute("InstpReviewerImportedEndDate", instpExpertEndDate);	
		
		/**
		 * 一般项目_评审专家_出生日期_开始时间
		 * 一般项目_评审专家_出生日期_结束时间
		 */
		String generalBirthdayStart = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00028'").get(0)).getValue();
		sc.setAttribute("GeneralReviewerBirthdayStartDate", generalBirthdayStart);		
		String generalBirthdayEnd = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00029'").get(0)).getValue();
		sc.setAttribute("GeneralReviewerBirthdayEndDate", generalBirthdayEnd);	
		
		/**
		 * 基地项目_评审专家_出生日期_开始时间
		 * 基地项目_评审专家_出生日期_结束时间
		 */
		String instpBirthdayStart = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00030'").get(0)).getValue();
		sc.setAttribute("InstpReviewerBirthdayStartDate", instpBirthdayStart);		
		String instpBirthdayEnd = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00031'").get(0)).getValue();
		sc.setAttribute("InstpReviewerBirthdayEndDate", instpBirthdayEnd);	
		
		// 个人信息上传照片路径
		String UserPictureUploadPath = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00007'").get(0)).getValue();
		sc.setAttribute("UserPictureUploadPath", UserPictureUploadPath);
		
		// 每页显示记录个数
		String rowsOfEachPage = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00012'").get(0)).getValue();
		int rows = Integer.parseInt(rowsOfEachPage);
		sc.setAttribute("rows", rows);// 每页大小pageSize
		sc.setAttribute("pages", 20);// 每次查询页数
		
		// 所有年份
		String allYears = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00013'").get(0)).getValue();
		sc.setAttribute("allYears", allYears.split("\\D+"));

		// 默认年份
		String defaultYear = ((SystemConfig) baseService.query("select sysconfig from SystemConfig sysconfig where sysconfig = 'sysconfig00014'").get(0)).getValue();
		sc.setAttribute("defaultYear", Integer.parseInt(defaultYear));
		
	}

}
