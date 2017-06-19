package csdc.tool.execution.fix;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.ProjectApplication;
import csdc.bean.SystemOption;
import csdc.tool.execution.importer.Importer;

/**
 * 补充申报表中省份信息
 * @author pengliang
 *
 */

@Component
public class FixProjectProvinceInfo20150901 extends Importer{

	@Override
	protected void work() throws Throwable {
		List<ProjectApplication> applications = dao.query("select app from ProjectApplication app where app.province is null or app.provinceName is null");
		
		Set<String> msg = new HashSet<String>();
		int current=0;
		int size = applications.size();
		for(ProjectApplication application : applications) {
			System.out.println((++current) + "/" + size  + "::" + application.getName());
			Agency university = application.getUniversity();
			if (university != null) {
				SystemOption province = university.getProvince();
				application.setProvince(province);
				application.setProvinceName(province.getName());
				dao.modify(application);
			} else {
				msg.add(application.getName() + ":" + application.getYear());
			}
		}
		
		if(msg.size() > 0) {
			System.out.println(msg.toString().replaceAll("\\s+", "\r"));
		}
	}

}
