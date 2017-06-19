package csdc.tool.execution.fix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import csdc.bean.ProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.TitleImporter;

/**
 * smas职称修复【标准职称-->职称代码/标准职称】
 * @author suwb
 *
 */
public class FixTitle  extends Execution{
	
	@Autowired
	private IHibernateBaseDao dao;
	
	private Map<String, String> titleMap;
	private String file = "F:\\SINOSS\\20140314_教育部社会科学研究管理平台数据交换职称代码.xls";

	/**
	 * 一般项目
	 */
//	@Override
//	protected void work() throws Throwable {
//		TitleImporter importer = new TitleImporter(file);
//		titleMap = importer.run();
//		List<General> list = dao.query("select g from General g");
//		for(General pro : list){
//			if(pro.getTitle() != null){
//				if(titleMap.containsKey(pro.getTitle())){
//					String title = titleMap.get(pro.getTitle());
//					pro.setTitle(title);
//					dao.modify(pro);
//				}
//				else System.out.println(pro.getTitle());				
//			}
//		}
//	}
	
	/**
	 * 基地项目
	 */
	@Override
	protected void work() throws Throwable {
		TitleImporter importer = new TitleImporter(file);
		titleMap = importer.run();
		List<ProjectApplication> list = dao.query("select i from ProjectApplication i where i.type = 'instp'");
		for(ProjectApplication pro : list){
			if(pro.getTitle() != null){
				if(titleMap.containsKey(pro.getTitle())){
					String title = titleMap.get(pro.getTitle());
					pro.setTitle(title);
					dao.modify(pro);
				}
				else System.out.println(pro.getTitle());				
			}
		}
	}

}
