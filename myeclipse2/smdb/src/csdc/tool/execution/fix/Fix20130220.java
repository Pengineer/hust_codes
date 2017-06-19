package csdc.tool.execution.fix;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Nssf;
import csdc.bean.ProjectGranted;
import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;

/**
 * 基地项目查重标记，核实是否有项目未结项申报基地项目。
 * 查重范围：国家社科基金重大项目；教育部哲学社会科学研究重大课题攻关项目；教育部重点研究基地重大项目；教育部哲学后期资助项目。
 * @author wangyi
 * 
 */
@Component
public class Fix20130220 extends Execution {

	@Autowired
	private HibernateBaseDao dao;

	@Override
	public void work() throws Throwable {
		//基地、后期资助、重大攻关项目在研数据
		List<ProjectGranted> granteds = dao.query("from ProjectGranted granted where granted.projectType in ('instp', 'post', 'key') and granted.status = 1");
		
		for (int i = 0; i < granteds.size(); i++) {
			ProjectGranted granted = granteds.get(i);
			granted.setIsDupCheckInstp(1);
		}
		
		//国家社科基金重大项目在研数据
		List<Nssf> nssfs = dao.query("from Nssf nssf where nssf.type = '重大项目' and nssf.endDate is null");
		
		for (int i = 0;  i < nssfs.size(); i++) {
			Nssf nssf = nssfs.get(i);
			nssf.setIsDupCheckInstp(1);
		}
	}

}
