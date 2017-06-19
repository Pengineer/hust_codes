package csdc.tool.execution.fix;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 建立项目各个表间的主表间级外键关联
 * 
 * @author xuhan
 *
 */
@Component
public class PopulateProjectParentTableFK extends Execution {

	@Autowired
	private HibernateBaseDao dao;
	
	private List<String> filters = new ArrayList<String>();
	
	
	@Override
	protected void work() throws Throwable {
		initFilters();
		
		String[] beanNames = {
				"ProjectApplicationReview",
				"ProjectGranted",
				"ProjectMember",
				"ProjectMidinspection",
				"ProjectEndinspection",
				"ProjectAnninspection",
				"ProjectFunding",
				"ProjectVariation",
				"ProjectEndinspectionReview",
				"ProjectFee"
		};
		
		for (String beanName : beanNames) {
			for (String filter : filters) {
				List objs = dao.query("from " + beanName + " obj where " + filter.replace(":var", "obj.id"));
				System.out.println(beanName + " : " + filter + " - " + objs.size());
				dao.flush();
				dao.clear();
			}
		}
		
	}
	
	private void initFilters() {
		filters = new ArrayList<String>();
		String other = "";
		String s = "0123456789abcdef";
		for (int id = 0x00; id <= 0xff; id++) {
			filters.add(":var like '%" + s.charAt(id / 16) + s.charAt(id % 16) + "' ");
			if (other.length() > 0) {
				other += " and ";
			}
			other += " :var not like '%" + s.charAt(id / 16) + s.charAt(id % 16) + "' ";
		}
		filters.add(0, other);
	}

}
