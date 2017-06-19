package csdc.tool.execution.fix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Expert;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.importer.Tools;

/**
 * 修复专家学科代码
 * @author fengcl
 *
 */
public class FixExpertDiscipline extends Execution {
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private Tools tool;
	
	@Override
	protected void work() throws Throwable {
		List<Expert> experts = dao.query("from Expert");
		for (Expert expert : experts) {
			String discipline = expert.getDiscipline();
			if (null != discipline) {
				expert.setDiscipline(tool.transformDisc(discipline));
			}
		}
	}
	
}
