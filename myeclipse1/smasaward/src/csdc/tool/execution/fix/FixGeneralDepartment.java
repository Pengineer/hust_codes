package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.ProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;

public class FixGeneralDepartment extends Execution{

	@Autowired
	private IHibernateBaseDao dao;
	
	@Override
	protected void work() throws Throwable {
		List<ProjectApplication> list = (List<ProjectApplication>)dao.query("select ge from ProjectApplication ge where ge.type = 'general' and ge.year = '2014' and ge.department is not null");
		for(ProjectApplication ge : list){
			if(!ge.getDepartment().isEmpty() && ge.getDepartment() != null){
				String department = ge.getDepartment().replace(ge.getUniversityName(), "");
				department = department.replaceAll("(?<=\\()[^\\)]+", "");//去掉括号中的内容
				department = department.replaceAll("[^\u4e00-\u9fa5a-zA-Z《》]", "");//留下中文和英文以及书名号的完整内容
				ge.setDepartment(department);
				dao.modify(ge);				
			}
		}
		
	}

}
